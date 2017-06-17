/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.repository;

import com.tsoft.app.domain.CompteAnalytique;
import com.tsoft.app.domain.enumeration.CompteAnalytiqueType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the CompteAnalytique entity.
 */
public interface CompteAnalytiqueRepository extends JpaRepository<CompteAnalytique, Long> {

    public CompteAnalytique findByIntituleAndType(String nom, CompteAnalytiqueType type);

    

}




