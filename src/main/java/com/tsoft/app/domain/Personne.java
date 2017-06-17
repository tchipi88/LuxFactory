/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.domain;

import com.tsoft.app.service.template.Label;
import com.tsoft.app.service.template.Libelle;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;

/**
 *
 * @author eisti
 */
@MappedSuperclass
public class Personne   extends AbstractAuditingEntity{
    
    @NotNull
    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    @Libelle
    @Label("Nom")
    private String lastName;
    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    @Label("Prénom")
    private String firstName;
    @Column
    private String telephone;
    @Email
    @Size(max = 100)
    @Column(length = 100, unique = true)
    private String email;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    

   

   

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    
    
    
    
    
}
