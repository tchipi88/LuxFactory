/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.repository;

import com.tsoft.app.domain.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Fournisseur entity.
 */
public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {

    public Fournisseur findByNom(String nom);

    

}




