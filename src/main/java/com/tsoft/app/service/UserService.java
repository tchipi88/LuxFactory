package com.tsoft.app.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;
import com.tsoft.app.config.Constants;
import com.tsoft.app.domain.Role;
import com.tsoft.app.domain.User;
import com.tsoft.app.repository.PersistentTokenRepository;
import com.tsoft.app.repository.RoleRepository;
import com.tsoft.app.repository.UserRepository;
import com.tsoft.app.repository.search.UserSearchRepository;
import com.tsoft.app.security.SecurityUtils;
import com.tsoft.app.service.dto.UserDTO;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserSearchRepository userSearchRepository;

    private final PersistentTokenRepository persistentTokenRepository;

    private final RoleRepository authorityRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserSearchRepository userSearchRepository, PersistentTokenRepository persistentTokenRepository, RoleRepository authorityRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userSearchRepository = userSearchRepository;
        this.persistentTokenRepository = persistentTokenRepository;
        this.authorityRepository = authorityRepository;
    }

//    public User createUser(String login, String password, String firstName, String lastName, String email) {
//
//        User newUser = new User();
//        Role authority = authorityRepository.findByName(AuthoritiesConstants.USER);
//        Set<Role> authorities = new HashSet<>();
//        String encryptedPassword = passwordEncoder.encode(password);
//        newUser.setLogin(login);
//        // new user gets initially a generated password
//        newUser.setPassword(encryptedPassword);
//        newUser.setPrenom(firstName);
//        newUser.setNom(lastName);
//        newUser.setEmail(email);
//        // new user is not active
//        newUser.setActivated(false);
//        // new user gets registration key
//        authorities.add(authority);
//        newUser.setAuthorities(authorities);
//        userRepository.save(newUser);
//        userSearchRepository.save(newUser);
//        log.debug("Created Information for User: {}", newUser);
//        return newUser;
//    }

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setPrenom(userDTO.getFirstName());
        user.setNom(userDTO.getLastName());
      

   

        if (userDTO.getAuthorities() != null) {
            Set<Role> authorities = new HashSet<>();
            userDTO.getAuthorities().forEach(
                    authority -> authorities.add(authorityRepository.findByName(authority))
            );
            user.setRoles(authorities);
        }
        String encryptedPassword = passwordEncoder.encode(userDTO.getLogin());
        user.setPassword(encryptedPassword);
        user.setActivated(true);
        userRepository.save(user);
        userSearchRepository.save(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update basic information (first name, last name, email, language) for the
     * current user.
     *
     * @param firstName first name of user
     * @param lastName last name of user
     * @param email email id of user
     * @param langKey language key
     */
    public void updateUser(String firstName, String lastName, String email) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
            user.setPrenom(firstName);
            user.setNom(lastName);
            userSearchRepository.save(user);
            log.debug("Changed Information for User: {}", user);
        });
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update
     * @return updated user
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(userRepository
                .findOne(userDTO.getId()))
                .map(user -> {
                    user.setLogin(userDTO.getLogin());
                    user.setPrenom(userDTO.getFirstName());
                    user.setNom(userDTO.getLastName());
                    user.setActivated(userDTO.isActivated());

                   

                    Set<Role> managedAuthorities = user.getRoles();
                    managedAuthorities.clear();
                    userDTO.getAuthorities().stream()
                            .map(authorityRepository::findByName)
                            .forEach(managedAuthorities::add);
                    log.debug("Changed Information for User: {}", user);
                    return user;
                })
                .map(UserDTO::new);
    }

    public void deleteUser(String login) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            userRepository.delete(user);
            userSearchRepository.delete(user);
            log.debug("Deleted User: {}", user);
        });
    }

    public void changePassword(String password) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
            String encryptedPassword = passwordEncoder.encode(password);
            user.setPassword(encryptedPassword);
            log.debug("Changed password for User: {}", user);
        });
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithRolesByLogin(login);
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities(Long id) {
        return userRepository.findOneWithRolesById(id);
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities() {
        return userRepository.findOneWithRolesByLogin(SecurityUtils.getCurrentUserLogin()).orElse(null);
    }

    /**
     * Persistent Token are used for providing automatic authentication, they
     * should be automatically deleted after 30 days.
     * <p>
     * This is scheduled to get fired everyday, at midnight.
     * </p>
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void removeOldPersistentTokens() {
        LocalDate now = LocalDate.now();
        persistentTokenRepository.findByTokenDateBefore(now.minusMonths(1)).forEach(token -> {
            log.debug("Deleting token {}", token.getSeries());
            User user = token.getUser();
            user.getPersistentTokens().remove(token);
            persistentTokenRepository.delete(token);
        });
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     * </p>
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        ZonedDateTime now = ZonedDateTime.now();
        List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(now.minusDays(3));
        for (User user : users) {
            log.debug("Deleting not activated user {}", user.getLogin());
            userRepository.delete(user);
            userSearchRepository.delete(user);
        }
    }
}
