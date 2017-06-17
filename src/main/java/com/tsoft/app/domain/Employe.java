package com.tsoft.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Employe.
 */
@Entity
@Table(name = "employe")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "employe")
@Inheritance(strategy = InheritanceType.JOINED)
public class Employe extends AbstractAuditingEntity{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "lieu_naissance")
    private String lieuNaissance;

    @Column(name = "numero_cni")
    private Integer numeroCni;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "email")
    private String email;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "date_entree")
    private LocalDate dateEntree;

    @Column(name = "salaire", precision=10, scale=2)
    private BigDecimal salaire;

    @ManyToOne(optional = false)
    @NotNull
    private EmployeFonction fonction;
    
  

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Employe nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Employe prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public Employe dateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public Employe lieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
        return this;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public Integer getNumeroCni() {
        return numeroCni;
    }

    public Employe numeroCni(Integer numeroCni) {
        this.numeroCni = numeroCni;
        return this;
    }

    public void setNumeroCni(Integer numeroCni) {
        this.numeroCni = numeroCni;
    }

    public String getTelephone() {
        return telephone;
    }

    public Employe telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public Employe email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public Employe adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public LocalDate getDateEntree() {
        return dateEntree;
    }

    public Employe dateEntree(LocalDate dateEntree) {
        this.dateEntree = dateEntree;
        return this;
    }

    public void setDateEntree(LocalDate dateEntree) {
        this.dateEntree = dateEntree;
    }

    public BigDecimal getSalaire() {
        return salaire;
    }

    public Employe salaire(BigDecimal salaire) {
        this.salaire = salaire;
        return this;
    }

    public void setSalaire(BigDecimal salaire) {
        this.salaire = salaire;
    }

    public EmployeFonction getFonction() {
        return fonction;
    }

    public Employe fonction(EmployeFonction employeFonction) {
        this.fonction = employeFonction;
        return this;
    }

   
    

    public void setFonction(EmployeFonction employeFonction) {
        this.fonction = employeFonction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employe employe = (Employe) o;
        if (employe.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, employe.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Employe{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", prenom='" + prenom + "'" +
            ", dateNaissance='" + dateNaissance + "'" +
            ", lieuNaissance='" + lieuNaissance + "'" +
            ", numeroCni='" + numeroCni + "'" +
            ", telephone='" + telephone + "'" +
            ", email='" + email + "'" +
            ", adresse='" + adresse + "'" +
            ", dateEntree='" + dateEntree + "'" +
            ", salaire='" + salaire + "'" +
            '}';
    }
}
