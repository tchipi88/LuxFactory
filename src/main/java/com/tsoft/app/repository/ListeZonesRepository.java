/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.repository;

import com.tsoft.app.domain.ListeZones;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the ListeZones entity.
 */
public interface ListeZonesRepository extends JpaRepository<ListeZones, Long> {

    public Page<ListeZones> findAllByActiviteAndEntrepotAndResponsableContaining(String activite,String entrepot,String responsable, Pageable pageable);
     
    @Query("SELECT z FROM ListeZones z WHERE (?1='' or z.activite=?1) and (?2='' or z.entrepot=?2) and (?3='' or z.responsable=?3)")
    public Page<ListeZones> searchListesZones(String activite, String entrepot, String responsable, Pageable pageable);
    
}




