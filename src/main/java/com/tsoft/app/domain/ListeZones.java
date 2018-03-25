/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author alexcheuko
 */
@Entity
@Table(name = "liste_zones")
public class ListeZones implements Serializable {

    @Id
    @Column(name = "ID")
    private Long id; // The row number!
    
    private String entrepot;
    private String activite;
    private String responsable;
    private Long produit_manque;
    private Long produit_exces;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public String getEntrepot() {
        return entrepot;
    }

    public void setEntrepot(String entrepot) {
        this.entrepot = entrepot;
    }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public Long getProduitManque() {
        return produit_manque;
    }

    public void setProduitManque(Long produit_manque) {
        this.produit_manque = produit_manque;
    }


    public Long getProduitExces() {
        return produit_exces;
    }

    public void setProduitExces(Long produit_exces) {
        this.produit_exces = produit_exces;
    }

    
}
