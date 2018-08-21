package com.tsoft.app.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tsoft.app.domain.Commande;
import com.tsoft.app.domain.Facture;
import com.tsoft.app.repository.CommandeRepository;
import com.tsoft.app.repository.FactureRepository;

@Service
public class FactureService {
	
	@Autowired
	FactureRepository factureRepository;
	
	@Autowired
	CommandeRepository commandeRepository;
	
	@Transactional 
	public Facture save(Facture f) {
		
		Commande c = commandeRepository.findOne(f.getCommande().getId());
		
		c.setFacturee(true);
		
		return factureRepository.save(f);
		
	}

}
