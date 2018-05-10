/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import org.joda.time.LocalDate;

/**
 *
 * @author tchipnangngansopa
 */
@Entity
public class Procesverbal extends AbstractAuditingEntity {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    @OneToOne
    private Commande commande;
    
    @NotNull
    private LocalDate datePv=LocalDate.now();
    
    private String lieuPv;
    
    private String identiteResponsableAcheteur;
    private String identiteResponsable1Prestataire;
    private String identiteResponsable2Prestataire;
    private String identiteResponsable3Prestataire;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public LocalDate getDatePv() {
        return datePv;
    }

    public void setDatePv(LocalDate datePv) {
        this.datePv = datePv;
    }

    public String getLieuPv() {
        return lieuPv;
    }

    public void setLieuPv(String lieuPv) {
        this.lieuPv = lieuPv;
    }

    public String getIdentiteResponsableAcheteur() {
        return identiteResponsableAcheteur;
    }

    public void setIdentiteResponsableAcheteur(String identiteResponsableAcheteur) {
        this.identiteResponsableAcheteur = identiteResponsableAcheteur;
    }

    public String getIdentiteResponsable1Prestataire() {
        return identiteResponsable1Prestataire;
    }

    public void setIdentiteResponsable1Prestataire(String identiteResponsable1Prestataire) {
        this.identiteResponsable1Prestataire = identiteResponsable1Prestataire;
    }

    public String getIdentiteResponsable2Prestataire() {
        return identiteResponsable2Prestataire;
    }

    public void setIdentiteResponsable2Prestataire(String identiteResponsable2Prestataire) {
        this.identiteResponsable2Prestataire = identiteResponsable2Prestataire;
    }

    public String getIdentiteResponsable3Prestataire() {
        return identiteResponsable3Prestataire;
    }

    public void setIdentiteResponsable3Prestataire(String identiteResponsable3Prestataire) {
        this.identiteResponsable3Prestataire = identiteResponsable3Prestataire;
    }
    
    
    
    
    
    
}
