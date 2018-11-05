/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.repository;

import com.tsoft.app.domain.ListeArticles;
import com.tsoft.app.domain.Procesverbal;

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
 * Spring Data JPA repository for the Procesverbal entity.
 */
public interface ProcesverbalRepository extends JpaRepository<Procesverbal, Long> {

	 public Page<Procesverbal> findAllByIdentiteResponsableAcheteurNomAndIdentiteResponsable1PrestataireNomAndDatePvBetween(String client, String fournisseur, LocalDate fromDate,
	            LocalDate toDate, Pageable pageable);
	 
	 @Query("SELECT p FROM Procesverbal p WHERE (?1='' or p.identiteResponsableAcheteur.nom=?1) and (?2='' or p.identiteResponsable1Prestataire.nom=?2) and (?3 is null or p.datePv>=?3) and (?4 is null or p.datePv<=?4)")
	 public Page<Procesverbal> searchProcesVerbal(String identiteResponsableAcheteur, String identiteResponsable1Prestataire, LocalDate fromDate,LocalDate toDate, Pageable pageable);


}










