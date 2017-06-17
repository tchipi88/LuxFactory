/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.service;

import com.tsoft.app.config.ApplicationProperties;
import com.tsoft.app.domain.Commande;
import com.tsoft.app.domain.CommandeLigne;
import com.tsoft.app.domain.ProduitFournisseur;
import com.tsoft.app.domain.enumeration.EtatCommande;
import com.tsoft.app.domain.enumeration.TypeCommande;
import com.tsoft.app.repository.CommandeLigneRepository;
import com.tsoft.app.repository.CommandeRepository;
import com.tsoft.app.repository.ProduitFournisseurRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tchipi
 */
@Service
@Transactional
public class CommandeLigneService {

    @Autowired
    ProduitFournisseurRepository produitFournisseurRepository;

    @Autowired
    CommandeRepository commandeRepository;
    @Autowired
    CommandeLigneRepository commandeLigneRepository;

    @Autowired
    ApplicationProperties app;

    @Autowired
    EntrepotService entrepotService;

    public CommandeLigne create(CommandeLigne cl) throws Exception {

        //NE PAS AJOUTER PLUSIEURS FOIS LE MEME PRODUIT DANS DES LIGNES DANS UNE MEME COMMANDE
        if (!commandeLigneRepository.findByProduitAndCommande(cl.getProduit(), cl.getCommande()).isEmpty()) {
            throw new Exception("Produit deja ajouté");
        }
        //impossible d'ajouter des produits dans une commande ANNULE
        if (!EtatCommande.DEVIS.equals(cl.getCommande().getEtat())) {
            throw new Exception("Imposssible d'ajouter des produits à une commande dont le statut n'est plus DEVIS");
        }
        Commande c = commandeRepository.findOne(cl.getCommande().getId());
        if (cl.getPrixUnitaire() == null || cl.getPrixUnitaire().equals(BigDecimal.ZERO)) {
            // set price
            if (TypeCommande.VENTE.equals(c.getEtat())) {
                cl.setPrixUnitaire(cl.getProduit().getPrix());
            } else {
                //prix du fournisseur
                ProduitFournisseur pf = produitFournisseurRepository.findByFournisseurAndProduit(c.getFournisseur(), cl.getProduit());
                cl.setPrixUnitaire(pf != null ? pf.getPrixVente() : cl.getProduit().getPrix());
            }
        }else{
            if (cl.getPrixUnitaire().compareTo(cl.getProduit().getPrix())==-1){
                //@todo  add derogation to admin
                throw new Exception("Prix défini "+cl.getPrixUnitaire()+" inférieur au prix de vente "+cl.getProduit().getPrix()+ " du produit "+cl.getProduit().getDenomination() );
            }
        }

        //set entrepot
        if (cl.getEntrepot() == null) {
            cl.setEntrepot(entrepotService.findByLibelle(app.getSite().getNom()));
        }

        commandeLigneRepository.save(cl);
        BigDecimal prix = cl.getPrixUnitaire().multiply(BigDecimal.valueOf(cl.getQuantite()));
        c.setPrixHT(c.getPrixHT() != null ? c.getPrixHT().add(prix) : prix);
        Float coefTva = (app.getSite().getTva() + 100) / 100;
        c.setPrixTTC(c.getPrixHT().multiply(BigDecimal.valueOf(coefTva)));
        commandeRepository.save(c);
        return cl;
    }

    public CommandeLigne update(CommandeLigne cl) throws Exception {

        //impossible d'ajouter des produits dans une commande ANNULE
        if (!EtatCommande.DEVIS.equals(cl.getCommande().getEtat())) {
            throw new Exception("Imposssible d'ajouter des produits à une commande dont le statut n'est plus DEVIS");
        }
        Commande c = commandeRepository.findOne(cl.getCommande().getId());
// permet de modifier le prix HT d'une commande
        CommandeLigne clAvant = commandeLigneRepository.findOne(cl.getId());
        BigDecimal prix = clAvant.getPrixUnitaire().multiply(BigDecimal.valueOf(clAvant.getQuantite()));
        c.setPrixHT(c.getPrixHT() != null ? c.getPrixHT().subtract(prix) : prix);

        commandeLigneRepository.save(cl);
        prix = cl.getPrixUnitaire().multiply(BigDecimal.valueOf(cl.getQuantite()));
        c.setPrixHT(c.getPrixHT() != null ? c.getPrixHT().add(prix) : prix);
        Float coefTva = (app.getSite().getTva() + 100) / 100;
        c.setPrixTTC(c.getPrixHT().multiply(BigDecimal.valueOf(coefTva)));
        commandeRepository.save(c);
        return cl;
    }

    public void delete(Long id) throws Exception {
        CommandeLigne cl = commandeLigneRepository.findOne(id);
        if (!EtatCommande.DEVIS.equals(cl.getCommande().getEtat())) {
            throw new Exception("Imposssible de supprimer des produits à une commande dont le statut n'est plus DEVIS");
        }
        Commande c = commandeRepository.findOne(cl.getCommande().getId());
        BigDecimal prix = cl.getPrixUnitaire().multiply(BigDecimal.valueOf(cl.getQuantite()));
        c.setPrixHT(c.getPrixHT() != null ? c.getPrixHT().subtract(prix) : null);
        commandeRepository.save(c);
        commandeLigneRepository.delete(id);
    }

}
