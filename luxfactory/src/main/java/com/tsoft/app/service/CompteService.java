/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.service;

import com.tsoft.app.domain.Compte;
import com.tsoft.app.repository.CompteRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class CompteService {

    @Autowired
    CompteRepository compteRepository;

    public Compte save(Compte compte) throws Exception {
        return compteRepository.save(compte);
    }

    public Compte getCompte(Integer numcompte, String intitule) throws Exception {
        Compte c = compteRepository.findOne(numcompte);
        if (c == null) {
            c = new Compte();
            c.setId(numcompte);
            c.setIntitule(intitule);
            c.setCredit(BigDecimal.ZERO);
            c.setDebit(BigDecimal.ZERO);
            return compteRepository.save(c);
        }
        return c;
    }

    public Compte getCompteClient() throws Exception {
        return getCompte(41, "Client");
    }

    public Compte getCompteVente() throws Exception {
        return getCompte(70, "Vente");
    }

    public Compte getCompteTVACollecte() throws Exception {
        return getCompte(443, "TVA collectée");
    }

    public Compte getCompteAchat() throws Exception {
        return getCompte(60, "Achat");
    }

    public Compte getCompteTVADeductible() throws Exception {
        return getCompte(445, "TVA déductible");
    }

    public Compte getCompteFournisseurs() throws Exception {
        return getCompte(40, "Fournisseurs");
    }

    public Compte getCompteCaisse() throws Exception {
        return getCompte(57, "Caisse");
    }

    public Compte getCompteBanque() throws Exception {
        return getCompte(52, "Banque");
    }
    public Compte getCompteCheque() throws Exception {
        return getCompte(51, " Valeurs à encaisser");
    }
    
    public Compte getCompteLoyer() throws Exception {
        return getCompte(622, " LOCATIONS ET CHARGES LOCATIVES");
    }
    public Compte getCompteTerrain() throws Exception {
        return getCompte(622, " LOCATIONS ET CHARGES LOCATIVES");
    }
    
     public Compte getComptePersonnel() throws Exception {
        return getCompte(422, " PERSONNEL, REMUNERATIONS DUES");
    }
    
   

}
