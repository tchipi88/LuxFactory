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
@Table(name = "mouvement_stock_init") 
public class MouvementStockInit implements Serializable{
     @Id
    @Column(name = "ID")
    private Long id; // The row number!

  

    @Column
    private Long produit;
    @Column
    private Long entrepotId;

    @Column
    private LocalDate firstDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProduit() {
        return produit;
    }

    public void setProduit(Long produit) {
        this.produit = produit;
    }

    public Long getEntrepotId() {
        return entrepotId;
    }

    public void setEntrepotId(Long entrepotId) {
        this.entrepotId = entrepotId;
    }

    public LocalDate getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(LocalDate firstDate) {
        this.firstDate = firstDate;
    }

    
}
