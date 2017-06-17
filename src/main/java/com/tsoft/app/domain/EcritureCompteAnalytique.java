/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.domain;

import com.tsoft.app.domain.enumeration.SensEcritureComptable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author tchipi
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EcritureCompteAnalytique extends AbstractAuditingEntity{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @ManyToOne
    private CompteAnalytique  compte;
    
    @NotNull
    private LocalDateTime dateEcriture;
    
    @NotNull
    private BigDecimal  montant;
    
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private  SensEcritureComptable  sensEcriture;
    
    @Lob
    private String libelleOperation;

    public String getLibelleOperation() {
        return libelleOperation;
    }

    public void setLibelleOperation(String libelleOperation) {
        this.libelleOperation = libelleOperation;
    }
    
    

    public CompteAnalytique getCompte() {
        return compte;
    }

    public void setCompte(CompteAnalytique compte) {
        this.compte = compte;
    }

    public LocalDateTime getDateEcriture() {
        return dateEcriture;
    }

    public void setDateEcriture(LocalDateTime dateEcriture) {
        this.dateEcriture = dateEcriture;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public SensEcritureComptable getSensEcriture() {
        return sensEcriture;
    }

    public void setSensEcriture(SensEcritureComptable sensEcriture) {
        this.sensEcriture = sensEcriture;
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
        if (!(object instanceof EcritureCompteAnalytique)) {
            return false;
        }
        EcritureCompteAnalytique other = (EcritureCompteAnalytique) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itsolution.tkbr.domain.MouvementCompteAnalytique[ id=" + id + " ]";
    }
    
}
