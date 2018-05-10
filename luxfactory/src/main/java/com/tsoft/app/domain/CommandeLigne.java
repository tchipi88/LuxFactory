package com.tsoft.app.domain;

import com.tsoft.app.service.template.ReadOnly;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;
import org.hibernate.annotations.Formula;

/**
 * A CommandeLigne.
 */
@Entity
@Table(name = "commande_ligne")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommandeLigne extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantite")
    @NotNull
    private Float quantite;

    private Float quantiteLivree;


    @Column(name = "prix_unitaire", precision = 10, scale = 2)
    @ReadOnly
    private BigDecimal prixUnitaire;

    @Formula("prix_unitaire*quantite")
    private BigDecimal sousTotal;

    @ManyToOne(optional = false)
    @NotNull
    private Commande commande;

    @ManyToOne(optional = false)
    @NotNull
    private Produit produit;
    
    @NotNull
    @ManyToOne
    private Entrepot entrepot;

    public Entrepot getEntrepot() {
        return entrepot;
    }

    public void setEntrepot(Entrepot entrepot) {
        this.entrepot = entrepot;
    }
    
    

    public Float getQuantiteLivree() {
        return quantiteLivree;
    }

    public void setQuantiteLivree(Float quantiteLivree) {
        this.quantiteLivree = quantiteLivree;
    }

   
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getQuantite() {
        return quantite;
    }

    public void setQuantite(Float quantite) {
        this.quantite = quantite;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public CommandeLigne prixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
        return this;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Commande getCommande() {
        return commande;
    }

    public CommandeLigne commande(Commande commande) {
        this.commande = commande;
        return this;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Produit getProduit() {
        return produit;
    }

    public CommandeLigne produit(Produit produit) {
        this.produit = produit;
        return this;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public BigDecimal getSousTotal() {
        return sousTotal;
    }

    public void setSousTotal(BigDecimal sousTotal) {
        this.sousTotal = sousTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommandeLigne commandeLigne = (CommandeLigne) o;
        if (commandeLigne.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, commandeLigne.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CommandeLigne{"
                + "id=" + id
                + ", quantite='" + quantite + "'"
                + ", prixUnitaire='" + prixUnitaire + "'"
                + '}';
    }
}
