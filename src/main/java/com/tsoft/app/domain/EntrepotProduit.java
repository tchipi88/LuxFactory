/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.domain;

import com.tsoft.app.service.template.ReadOnly;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Formula;

/**
 *
 * @author tchipi
 */
@Entity
public class EntrepotProduit extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private Produit produit;
    @ManyToOne
    @NotNull
    private Entrepot entrepot;

    @Column
    @ReadOnly
    private Float stockTheorique;
    @Column
    private Float stockPhysique;
    @Column
    @Formula("stock_physique-stock_theorique")
    private Float ecart;

    @Column
    @NotNull
    private Float seuilAlerte;
    @Column
    @NotNull
    private Float seuilSurStockage;

    @Formula("stock_physique>seuil_sur_stockage")
    private Boolean EnSurchauffe;
    @Formula("stock_physique<seuil_alerte")
    private Boolean EnManque;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Entrepot getEntrepot() {
        return entrepot;
    }

    public void setEntrepot(Entrepot entrepot) {
        this.entrepot = entrepot;
    }

    public Float getStockTheorique() {
        return stockTheorique;
    }

    public void setStockTheorique(Float stockTheorique) {
        this.stockTheorique = stockTheorique;
    }

    public Float getStockPhysique() {
        return stockPhysique;
    }

    public void setStockPhysique(Float stockPhysique) {
        this.stockPhysique = stockPhysique;
    }

  
    public Float getEcart() {
        return ecart;
    }

    public void setEcart(Float ecart) {
        this.ecart = ecart;
    }

    public Float getSeuilAlerte() {
        return seuilAlerte;
    }

    public void setSeuilAlerte(Float seuilAlerte) {
        this.seuilAlerte = seuilAlerte;
    }

    public Float getSeuilSurStockage() {
        return seuilSurStockage;
    }

    public void setSeuilSurStockage(Float seuilSurStockage) {
        this.seuilSurStockage = seuilSurStockage;
    }

    public Boolean getEnSurchauffe() {
        return EnSurchauffe;
    }

    public void setEnSurchauffe(Boolean EnSurchauffe) {
        this.EnSurchauffe = EnSurchauffe;
    }

    public Boolean getEnManque() {
        return EnManque;
    }

    public void setEnManque(Boolean EnManque) {
        this.EnManque = EnManque;
    }

   

}
