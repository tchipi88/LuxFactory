/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.init;

import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.tsoft.app.domain.Role;
import com.tsoft.app.domain.User;
import com.tsoft.app.repository.RoleRepository;
import com.tsoft.app.repository.UserRepository;
import com.tsoft.app.security.AuthoritiesConstants;

/**
 *
 * @author tchipi
 */
@Component
@Transactional
public class SecurityRealData implements RealData {

    @Autowired
    UserRepository UserRepository;
    @Autowired
    RoleRepository profilRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
   

   

    @Override
    public void populateData(HttpServletRequest req) throws Exception {
        profilRepository.save(new Role(AuthoritiesConstants.ADMIN));
        profilRepository.save(new Role(AuthoritiesConstants.USER));

       
        User newUser = new User();
        Role profil = profilRepository.findByName(AuthoritiesConstants.ADMIN);
        Set<Role> authorities = new HashSet<>();
        String encryptedPassword = passwordEncoder.encode("admin");
        newUser.setLogin("admin");
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setNom(newUser.getLogin());
        // new user is not active
        newUser.setActivated(true);
        // new user gets registration key
        authorities.add(profil);
        newUser.setRoles(authorities);

      
        UserRepository.save(newUser);

    }

}
