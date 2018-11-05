/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.repository;

import com.tsoft.app.domain.Activites;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Activites entity.
 */
public interface ActivitesRepository extends JpaRepository<Activites, Long> {

	public Page<Activites> findAllByLibelleAndResponsableNom(String libelle, String responsable, Pageable pageable);
	
	@Query("SELECT a FROM Activites a WHERE (?1='' or a.libelle=?1) and (?2='' or a.responsable=?2)")
	public Page<Activites> searchActivites(String libelle, String responsable, Pageable pageable);
	
	public Page<Activites> findAllByLibelle(String libelle,Pageable pageablbe);

}




