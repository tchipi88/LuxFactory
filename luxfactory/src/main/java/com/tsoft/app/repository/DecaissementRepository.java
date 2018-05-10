/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.repository;

import com.tsoft.app.domain.Decaissement;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Decaissement entity.
 */
public interface DecaissementRepository extends JpaRepository<Decaissement, Long> {

    public Page<Decaissement> findAllByDateVersementBetween(LocalDate fromDate, LocalDate toDate, Pageable pageable);

    

}




