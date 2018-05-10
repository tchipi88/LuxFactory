package com.tsoft.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A ProduitFournisseur.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProduitFournisseur extends AbstractAuditingEntity{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prix_vente", precision=10, scale=2)
    private BigDecimal prixVente;

    @Min(value = 1)
    @Max(value = 30)
    @Column(name = "delai_fourniture")
    private Integer delaiFourniture;

    @ManyToOne(optional = false)
    @NotNull
    private Fournisseur fournisseur;

    @ManyToOne(optional = false)
    @NotNull
    private Produit produit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrixVente() {
        return prixVente;
    }

    public ProduitFournisseur prixVente(BigDecimal prixVente) {
        this.prixVente = prixVente;
        return this;
    }

    public void setPrixVente(BigDecimal prixVente) {
        this.prixVente = prixVente;
    }

    public Integer getDelaiFourniture() {
        return delaiFourniture;
    }

    public ProduitFournisseur delaiFourniture(Integer delaiFourniture) {
        this.delaiFourniture = delaiFourniture;
        return this;
    }

    public void setDelaiFourniture(Integer delaiFourniture) {
        this.delaiFourniture = delaiFourniture;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public ProduitFournisseur fournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
        return this;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Produit getProduit() {
        return produit;
    }

    public ProduitFournisseur produit(Produit produit) {
        this.produit = produit;
        return this;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProduitFournisseur produitFournisseur = (ProduitFournisseur) o;
        if (produitFournisseur.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, produitFournisseur.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProduitFournisseur{" +
            "id=" + id +
            ", prixVente='" + prixVente + "'" +
            ", delaiFourniture='" + delaiFourniture + "'" +
            '}';
    }
}
