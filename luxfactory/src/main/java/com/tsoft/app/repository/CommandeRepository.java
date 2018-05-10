/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.repository;

import com.tsoft.app.domain.Commande;
import com.tsoft.app.domain.enumeration.TypeCommande;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Commande entity.
 */
public interface CommandeRepository extends JpaRepository<Commande, Long> {

    public Page<Commande> findAllByTypeAndDateEmissionBetween(TypeCommande type, LocalDate fromDate, LocalDate toDate, Pageable pageable);

    

}




