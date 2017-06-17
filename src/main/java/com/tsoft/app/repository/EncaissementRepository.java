/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.repository;

import com.tsoft.app.domain.Encaissement;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Encaissement entity.
 */
public interface EncaissementRepository extends JpaRepository<Encaissement, Long> {

    public Page<Encaissement> findAllByDateVersementBetween(LocalDate fromDate, LocalDate toDate, Pageable pageable);

    

}




