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
 * @author tchipnangngansopa
 */
@Entity
@Table(name = "liste_articles")
public class ListeArticles implements Serializable {

    @Id
    @Column(name = "ID")
    private Long id; // The row number!
    
    private String activite;
    private String entrepot;
    private String identifiant;
    private String unite;
    private LocalDate dateTransaction;
    private LocalDate lastOut;
    private LocalDate lastIn;
    private Float cumulEntree;
    private Float cumulSortie;
    private Float soldeInitial;
    private Float soldeFinal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    public String getEntrepot() {
        return entrepot;
    }

    public void setEntrepot(String entrepot) {
        this.entrepot = entrepot;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public LocalDate getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(LocalDate dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public LocalDate getLastOut() {
        return lastOut;
    }

    public void setLastOut(LocalDate lastOut) {
        this.lastOut = lastOut;
    }

    public LocalDate getLastIn() {
        return lastIn;
    }

    public void setLastIn(LocalDate lastIn) {
        this.lastIn = lastIn;
    }

    public Float getCumulEntree() {
        return cumulEntree;
    }

    public void setCumulEntree(Float cumulEntree) {
        this.cumulEntree = cumulEntree;
    }

    public Float getCumulSortie() {
        return cumulSortie;
    }

    public void setCumulSortie(Float cumulSortie) {
        this.cumulSortie = cumulSortie;
    }

    public Float getSoldeInitial() {
        return soldeInitial;
    }

    public void setSoldeInitial(Float soldeInitial) {
        this.soldeInitial = soldeInitial;
    }

    public Float getSoldeFinal() {
        return soldeFinal;
    }

    public void setSoldeFinal(Float soldeFinal) {
        this.soldeFinal = soldeFinal;
    }
    
    
    

}
