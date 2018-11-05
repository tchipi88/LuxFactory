/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.repository;

import com.tsoft.app.domain.ListeFactures;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
	 
	 @Query("SELECT f FROM ListeFactures f WHERE (?1='' or f.client=?1) and (?2='' or f.fournisseur=?2) and (?3 is null or f.dateEmission>=?3) and (?4 is null or f.dateEmission<=?4)")
	 public Page<ListeFactures> searchFactures(String client, String fournisseur, LocalDate fromDate,LocalDate toDate, Pageable pageable);


}




