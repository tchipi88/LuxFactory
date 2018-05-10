/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.service;

import com.tsoft.app.config.ApplicationProperties;
import com.tsoft.app.domain.Client;
import com.tsoft.app.domain.Commande;
import com.tsoft.app.domain.CommandeLigne;
import com.tsoft.app.domain.Compte;
import com.tsoft.app.domain.Facture;
import com.tsoft.app.domain.Fournisseur;
import com.tsoft.app.domain.MouvementStock;
import com.tsoft.app.domain.Procesverbal;
import com.tsoft.app.domain.ProduitFournisseur;
import com.tsoft.app.domain.enumeration.CompteAnalytiqueType;
import com.tsoft.app.domain.enumeration.EtatCommande;
import static com.tsoft.app.domain.enumeration.EtatCommande.BON_COMMANDE;
import static com.tsoft.app.domain.enumeration.EtatCommande.BON_LIVRAISON;
import static com.tsoft.app.domain.enumeration.EtatCommande.FACTUREE;
import static com.tsoft.app.domain.enumeration.EtatCommande.REGLE;
import com.tsoft.app.domain.enumeration.SensEcritureComptable;
import com.tsoft.app.repository.ClientRepository;
import com.tsoft.app.repository.CommandeLigneRepository;
import com.tsoft.app.repository.CommandeRepository;
import com.tsoft.app.repository.CompteRepository;
import com.tsoft.app.repository.FactureRepository;
import com.tsoft.app.repository.FournisseurRepository;
import com.tsoft.app.repository.ProcesverbalRepository;
import com.tsoft.app.repository.ProduitFournisseurRepository;
import java.io.File;
import java.time.LocalDateTime;
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
public class CommandeService {

    @Autowired
    ProduitFournisseurRepository produitFournisseurRepository;
    @Autowired
    MouvementStockService mouvementStockService;

    @Autowired
    EntrepotService entrepotService;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    CommandeRepository commandeRepository;
    @Autowired
    ProduitFournisseurRepository ProduitFournisseurRepository;

    @Autowired
    FournisseurRepository fournisseurRepository;

    @Autowired
    CommandeLigneRepository commandeLigneRepository;
    @Autowired
    FactureRepository factureRepository;
    @Autowired
    ProcesverbalRepository procesverbalRepository;
    @Autowired
    CompteRepository compteRepository;
    @Autowired
    CompteService cs;
    @Autowired
    EcritureCompteAnalytiqueService ecritureCompteAnalytiqueService;

    public Commande create(Commande commande) throws Exception {

        switch (commande.getType()) {
            case ACHAT: {
                //set client 
                Client c = clientRepository.findByNom(applicationProperties.getSite().getNom());
                if (c == null) {
                    c = new Client();
                    c.setNom(applicationProperties.getSite().getNom());
                    c.setTelephonePortable(applicationProperties.getSite().getTelephonePortable());
                    c.setAdresse(applicationProperties.getSite().getAdresse());
                    clientRepository.save(c);
                }

                commande.setClient(c);

                break;
            }
            case VENTE: {

                //set fournisseur 
                Fournisseur c = fournisseurRepository.findByNom(applicationProperties.getSite().getNom());
                if (c == null) {
                    c = new Fournisseur();
                    c.setNom(applicationProperties.getSite().getNom());
                    c.setTelephonePortable(applicationProperties.getSite().getTelephonePortable());
                    c.setAdresse(applicationProperties.getSite().getAdresse());
                    fournisseurRepository.save(c);
                }

                commande.setFournisseur(c);
                break;
            }
            default: {

            }
        }

        return commandeRepository.save(commande);
    }

    public Commande update(Commande commande) throws Exception {

        switch (commande.getType()) {
            case ACHAT: {
                switch (commande.getEtat()) {
                    case BON_LIVRAISON: {
                        if (!commande.isLivree()) {
                            //mouvement stock
                            for (CommandeLigne cl : commandeLigneRepository.findByCommandeId(commande.getId())) {
                                MouvementStock ms = new MouvementStock();
                                ms.setDateTransaction(LocalDateTime.now());
                                ms.setQuantite(cl.getQuantite());
                                ms.setProduit(cl.getProduit());

                                ms.setMotifTransaction("ACHAT");
                                //set price
                                ProduitFournisseur pf = produitFournisseurRepository.findByFournisseurAndProduit(commande.getFournisseur(), cl.getProduit());
                                cl.setPrixUnitaire(pf == null ? cl.getProduit().getPrix() : pf.getPrixVente());
                                ms.setEntrepotDepart(entrepotService.findByLibelle(commande.getFournisseur().getNom()));
                                ms.setEntrepotDestination(entrepotService.findByLibelle(commande.getClient().getNom()));

                                mouvementStockService.save(ms, false);

                                //creation ProcesVerbal
                                Procesverbal procesverbal = new Procesverbal();
                                procesverbal.setCommande(commande);
                                procesverbalRepository.save(procesverbal);
                            }
                        }
                        break;
                    }
                    case FACTUREE: {
                        if (!commande.isFacturee()) {
                            ecritureCompteAnalytiqueService.create(commande.getFournisseur(), CompteAnalytiqueType.FOURNISSEUR, commande.getPrixTTC(), SensEcritureComptable.C, "Achat commande N:" + commande.getId());

                            Compte compteAchat = cs.getCompteAchat();
                            Compte compteFournisseurs = cs.getCompteFournisseurs();
                            Compte compteTVADeductible = cs.getCompteTVADeductible();

                            compteAchat.setDebit(commande.getPrixHT().add(compteAchat.getDebit()));
                            compteFournisseurs.setCredit(commande.getPrixTTC().add(compteFournisseurs.getCredit()));
                            compteTVADeductible.setDebit(commande.getPrixTTC().subtract(commande.getPrixHT()).add(compteTVADeductible.getDebit()));

                            compteRepository.save(compteAchat);
                            compteRepository.save(compteFournisseurs);
                            compteRepository.save(compteTVADeductible);

                            //creation d'une facture 
                            Facture facture = new Facture();
                            facture.setCommande(commande);
                            factureRepository.save(facture);
                        }
                        break;
                    }
                }

                break;
            }
            case VENTE: {

                switch (commande.getEtat()) {
                    case FACTUREE: {
                        if (!commande.isFacturee()) {
                            ecritureCompteAnalytiqueService.create(commande.getClient(), CompteAnalytiqueType.CLIENT, commande.getPrixTTC(), SensEcritureComptable.D, "Vente commande N:" + commande.getId());

                            Compte compteClient = cs.getCompteClient();
                            Compte compteVente = cs.getCompteVente();
                            Compte compteTVACollecte = cs.getCompteTVACollecte();

                            compteClient.setDebit(commande.getPrixTTC().add(compteClient.getDebit()));
                            compteVente.setCredit(commande.getPrixHT().add(compteVente.getCredit()));
                            compteTVACollecte.setCredit(commande.getPrixTTC().subtract(commande.getPrixHT()).add(compteTVACollecte.getCredit()));

                            compteRepository.save(compteClient);
                            compteRepository.save(compteVente);
                            compteRepository.save(compteTVACollecte);
                        }
                        break;
                    }
                    case BON_COMMANDE: {
                        if (!commande.isCommandee()) {
                            //mouvement stock
                            for (CommandeLigne cl : commandeLigneRepository.findByCommandeId(commande.getId())) {
                                MouvementStock ms = new MouvementStock();
                                ms.setDateTransaction(LocalDateTime.now());
                                ms.setQuantite(cl.getQuantite());
                                ms.setProduit(cl.getProduit());

                                ms.setEntrepotDepart(entrepotService.findByLibelle(commande.getFournisseur().getNom()));
                                ms.setEntrepotDestination(cl.getEntrepot());
                                ms.setMotifTransaction("VENTE");

                                mouvementStockService.save(ms, true);
                            }
                        }
                        break;
                    }
                }

                break;
            }
            default: {

            }
        }

        switch (commande.getEtat()) {
            case BON_COMMANDE: {
                commande.setCommandee(true);
                break;
            }
            case BON_LIVRAISON: {
                commande.setLivree(true);
                break;
            }
            case FACTUREE: {
                commande.setFacturee(true);
                break;
            }
            case REGLE: {
                commande.setReglee(true);
                break;
            }
            default: {

            }
        }
        return commandeRepository.save(commande);
    }

    @Autowired
    protected ResourceLoader resourceLoader;

    @Autowired
    protected DataSource dataSource;

    public Resource print(Commande commande, EtatCommande typePrint) throws Exception {
        File uploadedfile = new File("." + File.separator + "reports");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }

        String type = (EtatCommande.BON_LIVRAISON.equals(typePrint) ? "BonLivraison" : "Facture");
        String destfile = uploadedfile.getAbsolutePath() + File.separator + type + "_Commande_" + commande.getId() + ".pdf";

        String reportfile = "classpath:com/itsolution/tkbr/reports/" + type + ".jasper";
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
