/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.repository;

import com.tsoft.app.domain.ListeArticles;
import com.tsoft.app.domain.ListeZones;

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
 * Spring Data JPA repository for the ListeArticles entity.
 */
public interface ListeArticlesRepository extends JpaRepository<ListeArticles, Long> {

    public Page<ListeArticles> findAllByActiviteAndEntrepotAndDateTransactionBetween(String activite, String entrepot, LocalDate fromDate,
            LocalDate toDate, Pageable pageable);
    
    @Query("SELECT a FROM ListeArticles a WHERE (?1='' or a.activite=?1) and (?2='' or a.entrepot=?2) and (?3 is null or a.dateTransaction>=?3) and (?4 is null or a.dateTransaction<=?4)")
    public Page<ListeArticles> searchListesArticles(String activite, String entrepot, LocalDate fromDate,LocalDate toDate, Pageable pageable);

}
