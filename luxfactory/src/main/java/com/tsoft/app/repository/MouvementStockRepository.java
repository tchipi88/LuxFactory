/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.repository;

import com.tsoft.app.domain.MouvementStock;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the MouvementStock entity.
 */
public interface MouvementStockRepository extends JpaRepository<MouvementStock, Long> {

    public Page<MouvementStock> findAllByDateTransactionBetween(LocalDateTime atTime, LocalDateTime atTime0, Pageable pageable);

    

}




