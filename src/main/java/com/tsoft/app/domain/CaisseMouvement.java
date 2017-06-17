/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.domain;

import com.tsoft.app.domain.enumeration.CaisseMouvementMotif;
import com.tsoft.app.domain.enumeration.PaymentMode;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author tchipi
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class CaisseMouvement extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Caisse caisse;

    @NotNull
    private LocalDate dateVersement;

    @Column
    @NotNull
    private BigDecimal montant;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private PaymentMode modePaiement;
    @Enumerated(EnumType.STRING)
    @NotNull
    private CaisseMouvementMotif motif;

    public CaisseMouvementMotif getMotif() {
        return motif;
    }

    public void setMotif(CaisseMouvementMotif motif) {
        this.motif = motif;
    }
    
    

    public PaymentMode getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(PaymentMode modePaiement) {
        this.modePaiement = modePaiement;
    }
    
    

    @Lob
    private String commentaires;

    public Caisse getCaisse() {
        return caisse;
    }

    public void setCaisse(Caisse caisse) {
        this.caisse = caisse;
    }

    public LocalDate getDateVersement() {
        return dateVersement;
    }

    public void setDateVersement(LocalDate dateVersement) {
        this.dateVersement = dateVersement;
    }

  

    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
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
        if (!(object instanceof CaisseMouvement)) {
            return false;
        }
        CaisseMouvement other = (CaisseMouvement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itsolution.tkbr.domain.CaisseMouvement[ id=" + id + " ]";
    }

}
