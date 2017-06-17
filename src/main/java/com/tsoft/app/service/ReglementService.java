/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.service;

import com.tsoft.app.domain.Commande;
import com.tsoft.app.domain.Compte;
import com.tsoft.app.domain.Decaissement;
import com.tsoft.app.domain.Encaissement;
import com.tsoft.app.domain.Reglement;
import com.tsoft.app.domain.enumeration.CaisseMouvementMotif;
import com.tsoft.app.domain.enumeration.CompteAnalytiqueType;
import static com.tsoft.app.domain.enumeration.PaymentMode.CHEQUE;
import static com.tsoft.app.domain.enumeration.PaymentMode.ESPECES;
import static com.tsoft.app.domain.enumeration.PaymentMode.VIREMENT;
import com.tsoft.app.domain.enumeration.SensEcritureComptable;
import com.tsoft.app.repository.CommandeRepository;
import com.tsoft.app.repository.ReglementRepository;
import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tchipi
 */
@Service
@Transactional
public class ReglementService {

    @Autowired
    ReglementRepository reglementRepository;

    @Autowired
    CompteService cs;
    @Autowired
    DecaissementService decaissementService;
    @Autowired
    EncaissementService encaissementService;
    @Autowired
    CommandeRepository commandeRepository;
    @Autowired
    EcritureCompteAnalytiqueService ecritureCompteAnalytiqueService;

    public Reglement save(Reglement r) throws Exception {

        if (r.getId() != null) {
            throw new Exception("Mise à jour des reglements interdites");
        }

        //http://www.compta-facile.com/comptabilisation-operations-bancaires/
        //Verifier que la commande n'est pas deja regle
        // if (r.getCommande().getReglements().stream().collect())
        //@todo  crediter la caisse
        BigDecimal totalttc = r.getMontant();

        switch (r.getCommande().getType()) {
            case ACHAT: {

                ecritureCompteAnalytiqueService.create(r.getCommande().getFournisseur(), CompteAnalytiqueType.FOURNISSEUR, totalttc, SensEcritureComptable.D,"Versement pour Achat N:"+r.getCommande().getId());

                Compte compteFournisseurs = cs.getCompteFournisseurs();
                compteFournisseurs.setDebit(totalttc.add(compteFournisseurs.getDebit()));
                cs.save(compteFournisseurs);

                switch (r.getMode()) {
                    case ESPECES: {

                        Compte compteCaisse = cs.getCompteCaisse();
                        compteCaisse.setCredit(totalttc.add(compteCaisse.getCredit()));
                        cs.save(compteCaisse);

                        Decaissement decaissement = new Decaissement();
                        decaissement.setMontant(r.getMontant());
                        decaissement.setDateVersement(r.getDateVersement());
                        decaissement.setModePaiement(r.getMode());
                        decaissement.setMotif(CaisseMouvementMotif.ACHAT);
                        decaissement.setCommentaires("Mvt commande " + r.getCommande().getId());

                        decaissementService.save(decaissement);

                        break;
                    }
                    case CHEQUE: {
                        Compte compteBanque = cs.getCompteBanque();
                        compteBanque.setCredit(totalttc.add(compteBanque.getCredit()));
                        cs.save(compteBanque);
                        break;
                    }
                    case VIREMENT: {
                        Compte compteBanque = cs.getCompteBanque();
                        compteBanque.setCredit(totalttc.add(compteBanque.getCredit()));
                        cs.save(compteBanque);
                        break;
                    }

                }

                break;
            }
            case VENTE: {

                ecritureCompteAnalytiqueService.create(r.getCommande().getClient(), CompteAnalytiqueType.CLIENT, totalttc, SensEcritureComptable.C,"Versement pour Vente N:"+r.getCommande().getId());

                Compte compteClient = cs.getCompteClient();
                compteClient.setCredit(totalttc.add(compteClient.getCredit()));
                cs.save(compteClient);

                switch (r.getMode()) {
                    case ESPECES: {

                        Compte compteCaisse = cs.getCompteCaisse();
                        compteCaisse.setDebit(totalttc.add(compteCaisse.getDebit()));
                        cs.save(compteCaisse);

                        Encaissement encaissement = new Encaissement();
                        encaissement.setMontant(r.getMontant());
                        encaissement.setDateVersement(r.getDateVersement());
                        encaissement.setModePaiement(r.getMode());
                        encaissement.setMotif(CaisseMouvementMotif.VENTE);
                        encaissement.setCommentaires("Mvt commande " + r.getCommande().getId());

                        encaissementService.save(encaissement);

                        break;
                    }
                    case CHEQUE: {
                        Compte compteCheque = cs.getCompteCheque();
                        compteCheque.setDebit(totalttc.add(compteCheque.getDebit()));
                        cs.save(compteCheque);
                        break;
                    }
                    case VIREMENT: {
                        Compte compteBanque = cs.getCompteBanque();
                        compteBanque.setDebit(totalttc.add(compteBanque.getDebit()));
                        cs.save(compteBanque);
                        break;
                    }
                }
                break;
            }
            default: {
                throw new Exception("Type de commande non spécifié");
            }
        }

        Commande commande = r.getCommande();
        commande.setMontantPaye(commande.getMontantPaye() == null ? r.getMontant() : commande.getMontantPaye().add(r.getMontant()));

        commandeRepository.save(commande);

        return reglementRepository.save(r);
    }
    
     @Autowired
    protected ResourceLoader resourceLoader;

    @Autowired
    protected DataSource dataSource;

    public Resource print(Reglement reglement) throws Exception {
        File uploadedfile = new File("." + File.separator + "reports");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }
        
        String destfile = uploadedfile.getAbsolutePath() + File.separator+"TicketReglement" + reglement.getId() + ".pdf";

        String reportfile = "classpath:reports/TicketReglement.jasper";
        //remplissage des parametres du report
        Map params = new HashMap();
        //fill report
        JasperPrint jp = JasperFillManager.fillReport(
                resourceLoader.getResource(reportfile).getInputStream(), //file jasper
                params, //params report
                dataSource.getConnection());  //datasource
        JasperExportManager.exportReportToPdfFile(jp, destfile);

        return resourceLoader.getResource("file:" + destfile);
    }

}
