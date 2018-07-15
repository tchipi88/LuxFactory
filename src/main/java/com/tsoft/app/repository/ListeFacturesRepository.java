/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.repository;

import com.tsoft.app.domain.ListeFactures;
import com.tsoft.app.domain.Procesverbal;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the ListeFactures entity.
 */
public interface ListeFacturesRepository extends JpaRepository<ListeFactures, Long> {

	 public Page<ListeFactures> findAllByClientAndFournisseurAndDateEmissionBetween(String client, String fournisseur, LocalDate fromDate,
	            LocalDate toDate, Pageable pageable);

}




