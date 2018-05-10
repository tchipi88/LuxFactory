/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.repository;

import com.tsoft.app.domain.Fournisseur;
import com.tsoft.app.domain.Produit;
import com.tsoft.app.domain.ProduitFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the ProduitFournisseur entity.
 */
public interface ProduitFournisseurRepository extends JpaRepository<ProduitFournisseur, Long> {

    public ProduitFournisseur findByFournisseurAndProduit(Fournisseur fournisseur, Produit produit);

    

}




