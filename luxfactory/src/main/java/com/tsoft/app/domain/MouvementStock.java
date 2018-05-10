package com.tsoft.app.domain;

import com.tsoft.app.service.template.ReadOnly;
import java.time.LocalDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;
import org.hibernate.annotations.Formula;

/**
 * A MouvementStock.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MouvementStock extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantite")
    private Float quantite;

    @NotNull
    @Column(name = "date_transaction", nullable = false)
    private LocalDateTime dateTransaction;

    @Lob
    @Column(name = "motif_transaction")
    private String motifTransaction;

    @ManyToOne(optional = false)
    @NotNull
    private Entrepot entrepotDepart;

    @ManyToOne(optional = false)
    @NotNull
    private Entrepot entrepotDestination;

    @ManyToOne(optional = false)
    @NotNull
    private Produit produit;

    @Column
    @ReadOnly
    private Float stockEntrepotDepart;
    @Formula("stock_entrepot_depart-quantite")
    private Float stockFinalEntrepotDepart;

    @Column
    @ReadOnly
    private Float stockEntrepotDestination;
    @Formula("stock_entrepot_destination+quantite")
    private Float stockFinalEntrepotDestination;

    public Float getStockEntrepotDepart() {
        return stockEntrepotDepart;
    }

    public void setStockEntrepotDepart(Float stockEntrepotDepart) {
        this.stockEntrepotDepart = stockEntrepotDepart;
    }

    public Float getStockFinalEntrepotDepart() {
        return stockFinalEntrepotDepart;
    }

    public void setStockFinalEntrepotDepart(Float stockFinalEntrepotDepart) {
        this.stockFinalEntrepotDepart = stockFinalEntrepotDepart;
    }

    public Float getStockEntrepotDestination() {
        return stockEntrepotDestination;
    }

    public void setStockEntrepotDestination(Float stockEntrepotDestination) {
        this.stockEntrepotDestination = stockEntrepotDestination;
    }

    public Float getStockFinalEntrepotDestination() {
        return stockFinalEntrepotDestination;
    }

    public void setStockFinalEntrepotDestination(Float stockFinalEntrepotDestination) {
        this.stockFinalEntrepotDestination = stockFinalEntrepotDestination;
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

    public LocalDateTime getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(LocalDateTime dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public String getMotifTransaction() {
        return motifTransaction;
    }

    public void setMotifTransaction(String motifTransaction) {
        this.motifTransaction = motifTransaction;
    }

    public Entrepot getEntrepotDepart() {
        return entrepotDepart;
    }

    public void setEntrepotDepart(Entrepot entrepotDepart) {
        this.entrepotDepart = entrepotDepart;
    }

    public Entrepot getEntrepotDestination() {
        return entrepotDestination;
    }

    public void setEntrepotDestination(Entrepot entrepotDestination) {
        this.entrepotDestination = entrepotDestination;
    }

    public Produit getProduit() {
        return produit;
    }

    public MouvementStock produit(Produit produit) {
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
        MouvementStock mouvementStock = (MouvementStock) o;
        if (mouvementStock.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, mouvementStock.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MouvementStock{"
                + "id=" + id
                + ", quantite='" + quantite + "'"
                + ", dateTransaction='" + dateTransaction + "'"
                + ", motifTransaction='" + motifTransaction + "'"
                + '}';
    }
}
