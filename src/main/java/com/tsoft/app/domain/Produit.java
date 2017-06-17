package com.tsoft.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;

import com.tsoft.app.domain.enumeration.ProduitType;
import com.tsoft.app.service.template.Libelle;

/**
 * A Produit.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "produit")
@Inheritance(strategy = InheritanceType.JOINED)
public class Produit extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Libelle
    private String denomination;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ProduitType type = ProduitType.BIEN;

    @ManyToOne(optional = false)
    @NotNull
    private ProduitCategorie categorie;

    @NotNull
    private BigDecimal prix;

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    @ManyToOne
    private Unite unite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public ProduitType getType() {
        return type;
    }

    public Produit type(ProduitType type) {
        this.type = type;
        return this;
    }

    public void setType(ProduitType type) {
        this.type = type;
    }

    public ProduitCategorie getCategorie() {
        return categorie;
    }

    public Produit categorie(ProduitCategorie produitCategorie) {
        this.categorie = produitCategorie;
        return this;
    }

    public void setCategorie(ProduitCategorie produitCategorie) {
        this.categorie = produitCategorie;
    }

    public Unite getUnite() {
        return unite;
    }

    public Produit unite(Unite unite) {
        this.unite = unite;
        return this;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Produit produit = (Produit) o;
        if (produit.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, produit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Produit{"
                + "id=" + id
                + ", denomination='" + denomination + "'"
                + ", type='" + type + "'"
                + '}';
    }
}
