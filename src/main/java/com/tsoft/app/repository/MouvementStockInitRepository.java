/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.repository;

import com.tsoft.app.domain.MouvementStockInit;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the MouvementStockInit entity.
 */
public interface MouvementStockInitRepository extends JpaRepository<MouvementStockInit, Long> {

    public Page<MouvementStockInit> findAllByEntrepotIdAndProduitAndFirstDateBetween(Long entrepotId, Long produit, LocalDate fromDate, LocalDate toDate, Pageable pageable);

    

}




