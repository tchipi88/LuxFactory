/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.service;


import com.tsoft.app.domain.Entrepot;
import com.tsoft.app.repository.EntrepotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class EntrepotService {

    @Autowired
    EntrepotRepository entrepotRepository;


    public Entrepot findByLibelle(String libelle) throws Exception{
         Entrepot e = entrepotRepository.findByLibelle(libelle);
          if (e == null) {
            e = new Entrepot();
            e.setLibelle(libelle);
            e.setCapactite(0);
            return entrepotRepository.save(e);
        }
        return e;
       
    }

    

}
