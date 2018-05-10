/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.repository;

import com.tsoft.app.domain.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    public Role findByName(String name);

    @EntityGraph(attributePaths = "privileges")
    Role findOneWithPrivilegesById(Long id);
    

}




