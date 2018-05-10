/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.domain;

import com.tsoft.app.domain.enumeration.EtatCaisse;
import com.tsoft.app.service.template.ReadOnly;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Formula;

/**
 *
 * @author tchipi
 */
@Entity
public class Caisse extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Employe gerant;

    @Enumerated(EnumType.STRING)
    private EtatCaisse etat=EtatCaisse.OUVERT;

    private BigDecimal soldeReel=BigDecimal.ZERO;
    @ReadOnly
    private BigDecimal sortie=BigDecimal.ZERO;
    @ReadOnly
    private BigDecimal entree=BigDecimal.ZERO;

    @Formula("entree-sortie")
    private BigDecimal soldeTheorique;

    public BigDecimal getSortie() {
        return sortie;
    }

    public void setSortie(BigDecimal sortie) {
        this.sortie = sortie;
    }

    public BigDecimal getEntree() {
        return entree;
    }

    public void setEntree(BigDecimal entree) {
        this.entree = entree;
    }

    public BigDecimal getSoldeTheorique() {
        return soldeTheorique;
    }

    public void setSoldeTheorique(BigDecimal soldeTheorique) {
        this.soldeTheorique = soldeTheorique;
    }

    public Employe getGerant() {
        return gerant;
    }

    public void setGerant(Employe gerant) {
        this.gerant = gerant;
    }

    public EtatCaisse getEtat() {
        return etat;
    }

    public void setEtat(EtatCaisse etat) {
        this.etat = etat;
    }

    public BigDecimal getSoldeReel() {
        return soldeReel;
    }

    public void setSoldeReel(BigDecimal soldeReel) {
        this.soldeReel = soldeReel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Caisse)) {
            return false;
        }
        Caisse other = (Caisse) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itsolution.tkbr.domain.Caisse[ id=" + id + " ]";
    }

}
