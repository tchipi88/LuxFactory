/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Alex Cheuko
 */
@Entity
public class ZoneProduit extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @NotNull
    private Produit produit;
    
    @ManyToOne
    @NotNull
    private Zone zone;
    
     @Column
    @NotNull
    private Float seuilHaut;
      
    @Column
    @NotNull
    private Float seuilBas;
    
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

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public Float getSeuilHaut() {
        return seuilHaut;
    }

    public void setSeuilHaut(Float seuilHaut) {
        this.seuilHaut = seuilHaut;
    }

    public Float getSeuilBas() {
        return seuilBas;
    }

    public void setSeuilBas(Float seuilBas) {
        this.seuilBas = seuilBas;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ZoneProduit)) {
            return false;
        }
        ZoneProduit other = (ZoneProduit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tsoft.app.domain.ZoneProduit[ id=" + id + " ]";
    }
    
}
