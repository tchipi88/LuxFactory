/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.repository;

import com.tsoft.app.domain.Commande;
import com.tsoft.app.domain.CommandeLigne;
import com.tsoft.app.domain.Produit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the CommandeLigne entity.
 */
public interface CommandeLigneRepository extends JpaRepository<CommandeLigne, Long> {

    public List<CommandeLigne> findByProduitAndCommande(Produit produit, Commande commande);

    public List<CommandeLigne> findByCommandeId(Long id);


    

}




