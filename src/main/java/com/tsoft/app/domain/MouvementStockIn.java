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
@Table(name = "mouvement_stock_in")  // A view!
public class MouvementStockIn implements Serializable{

    @Id
    @Column(name = "ID")
    private Long id; // The row number!

    @Column
    private Long entrepotId;

    @Column(name = "produit")
    private Long produit;

    @Column(name = "date_transaction")
    private LocalDate dateTransaction;

    @Column(name = "quantite")
    private Float quantite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntrepotId() {
        return entrepotId;
    }

    public void setEntrepotId(Long entrepotId) {
        this.entrepotId = entrepotId;
    }

  

    public Long getProduit() {
        return produit;
    }

    public void setProduit(Long produit) {
        this.produit = produit;
    }

    public LocalDate getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(LocalDate dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public Float getQuantite() {
        return quantite;
    }

    public void setQuantite(Float quantite) {
        this.quantite = quantite;
    }

   

}
