/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.service;

import com.tsoft.app.domain.Caisse;
import com.tsoft.app.domain.Decaissement;
import com.tsoft.app.repository.DecaissementRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
@Transactional
public class DecaissementService {

    @Autowired
    DecaissementRepository decaissementRepository;
    @Autowired
    CaisseService caisseService;

    public Decaissement save(Decaissement decaissement) throws Exception {
        Caisse caisse = caisseService.getCaisse();
        if (decaissement.getCaisse() == null) {
            decaissement.setCaisse(caisse);
        }

        caisse.setSortie(caisse.getSortie().add(decaissement.getMontant()));
        caisseService.save(caisse);

        return decaissementRepository.save(decaissement);
    }

}
