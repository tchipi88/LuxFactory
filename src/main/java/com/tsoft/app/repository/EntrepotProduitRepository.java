/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.repository;

import com.tsoft.app.domain.Entrepot;
import com.tsoft.app.domain.EntrepotProduit;
import com.tsoft.app.domain.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the EntrepotProduit entity.
 */
public interface EntrepotProduitRepository extends JpaRepository<EntrepotProduit, Long> {

    public EntrepotProduit findByProduitAndEntrepot(Produit produit, Entrepot entrepotDepart);

    

}




