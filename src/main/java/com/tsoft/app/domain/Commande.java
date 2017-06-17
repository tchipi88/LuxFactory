package com.tsoft.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tsoft.app.domain.enumeration.EtatCommande;
import com.tsoft.app.domain.enumeration.TypeCommande;
import com.tsoft.app.service.template.ReadOnly;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import org.hibernate.annotations.Formula;

/**
 * A Commande.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Commande extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_emission")
    @NotNull
    private LocalDate dateEmission;

    @Column
    private LocalDate dateLivraison;

    private LocalDate dateEcheance;

    @Min(value = 1)
    @Max(value = 30)
    @Column(name = "delai")
    private Integer delai;

    @ManyToOne
    private Employe superviseur;

    @Column(name = "prix_ht", precision = 10, scale = 2)
    @ReadOnly
    private BigDecimal prixHT;

    @Column(name = "prix_ttc", precision = 10, scale = 2)
    @ReadOnly
    private BigDecimal prixTTC;

    @Column(name = "montant_paye", precision = 10, scale = 2)
    @ReadOnly
    private BigDecimal montantPaye;
    @Formula("prix_ttc-montant_paye")
    private BigDecimal montantRestant;

    @NotNull
    @Column(nullable = false)
    private boolean livree = false;
    @NotNull
    @Column(nullable = false)
    private boolean commandee = false;
    @NotNull
    @Column(nullable = false)
    private boolean facturee = false;
    @NotNull
    @Column(nullable = false)
    private boolean reglee = false;

    public boolean isLivree() {
        return livree;
    }

    public void setLivree(boolean livree) {
        this.livree = livree;
    }

    public boolean isCommandee() {
        return commandee;
    }

    public void setCommandee(boolean commandee) {
        this.commandee = commandee;
    }

    public boolean isFacturee() {
        return facturee;
    }

    public void setFacturee(boolean facturee) {
        this.facturee = facturee;
    }

    public boolean isReglee() {
        return reglee;
    }

    public void setReglee(boolean reglee) {
        this.reglee = reglee;
    }
    
    

    public BigDecimal getMontantRestant() {
        return montantRestant;
    }

    public void setMontantRestant(BigDecimal montantRestant) {
        this.montantRestant = montantRestant;
    }

    public BigDecimal getMontantPaye() {
        return montantPaye;
    }

    public void setMontantPaye(BigDecimal montantPaye) {
        this.montantPaye = montantPaye;
    }

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    private EtatCommande etat;

    @Lob
    @Column(name = "commentaires")
    private String commentaires;

    @OneToMany(mappedBy = "commande")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CommandeLigne> commandeLignes = new HashSet<>();

    @OneToMany(mappedBy = "commande")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Reglement> reglements = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private Client client;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TypeCommande type;

    public TypeCommande getType() {
        return type;
    }

    public void setType(TypeCommande type) {
        this.type = type;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @ManyToOne(optional = false)
    @NotNull
    private Fournisseur fournisseur;

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public EtatCommande getEtat() {
        return etat;
    }

    public void setEtat(EtatCommande etat) {
        this.etat = etat;
    }

    public Employe getSuperviseur() {
        return superviseur;
    }

    public void setSuperviseur(Employe superviseur) {
        this.superviseur = superviseur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateEmission() {
        return dateEmission;
    }

    public void setDateEmission(LocalDate dateEmission) {
        this.dateEmission = dateEmission;
    }

    public Integer getDelai() {
        return delai;
    }

    public void setDelai(Integer delai) {
        this.delai = delai;
    }

    public BigDecimal getPrixHT() {
        return prixHT;
    }

    public void setPrixHT(BigDecimal prixHT) {
        this.prixHT = prixHT;
    }

    public BigDecimal getPrixTTC() {
        return prixTTC;
    }

    public void setPrixTTC(BigDecimal prixTTC) {
        this.prixTTC = prixTTC;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    public Set<CommandeLigne> getCommandeLignes() {
        return commandeLignes;
    }

    public void setCommandeLignes(Set<CommandeLigne> commandeLignes) {
        this.commandeLignes = commandeLignes;
    }

    public Set<Reglement> getReglements() {
        return reglements;
    }

    public void setReglements(Set<Reglement> reglements) {
        this.reglements = reglements;
    }

    public LocalDate getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(LocalDate dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public LocalDate getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(LocalDate dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Commande commande = (Commande) o;
        if (commande.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, commande.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Commande{"
                + "id=" + id
                + ", dateEmission='" + dateEmission + "'"
                + ", delai='" + delai + "'"
                + ", prixHT='" + prixHT + "'"
                + ", prixTTC='" + prixTTC + "'"
                + ", commentaires='" + commentaires + "'"
                + '}';
    }

}
