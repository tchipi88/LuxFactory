/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.service;

import com.tsoft.app.domain.Entrepot;
import com.tsoft.app.domain.Fournisseur;
import com.tsoft.app.repository.EntrepotRepository;
import com.tsoft.app.repository.FournisseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tchipi
 */
@Service
public class FournisseurService {

    @Autowired
    FournisseurRepository fournisseurRepository;
    @Autowired
    EntrepotService entrepotService;
    @Autowired
    EntrepotRepository entrepotRepository;

    @Transactional
    public Fournisseur create(Fournisseur fournisseur) throws Exception {
        entrepotService.findByLibelle(fournisseur.getNom());
        return fournisseurRepository.save(fournisseur);

    }

    public Fournisseur update(Fournisseur fournisseur) throws Exception {
        Fournisseur oldValue = fournisseurRepository.findOne(fournisseur.getId());
        if (!oldValue.getNom().equals(fournisseur.getNom())) {
            Entrepot e = entrepotService.findByLibelle(oldValue.getNom());
            e.setLibelle(fournisseur.getNom());
            entrepotRepository.save(e);
        }
        return fournisseurRepository.save(fournisseur);

    }

}
