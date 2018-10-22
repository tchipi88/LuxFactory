/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.repository;

import com.tsoft.app.domain.ProduitCategorie;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the ProduitCategorie entity.
 */
public interface ProduitCategorieRepository extends JpaRepository<ProduitCategorie, Long> {

	@Query("SELECT new map(pc.id as id,pc.libelle as libelle, count(p.denomination) as nbProduit) FROM ProduitCategorie pc LEFT JOIN Produit p ON pc.id = p.categorie GROUP BY pc.id, pc.libelle")
    public Page<ProduitCategorie> findProduitCount(Pageable pageable);
    

}




