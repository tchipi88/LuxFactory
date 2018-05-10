 package com.tsoft.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;


/**
 * A Entrepot.
 */
@Entity
@Table(name = "entrepot")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Entrepot extends AbstractAuditingEntity{

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String libelle;

    @Column(name = "capactite")
    private Integer capactite;

   
    @ManyToOne
    private Employe responsable;
    
    @ManyToOne
    private Activites activite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

  

    public Integer getCapactite() {
        return capactite;
    }

    public Entrepot capactite(Integer capactite) {
        this.capactite = capactite;
        return this;
    }

    public void setCapactite(Integer capactite) {
        this.capactite = capactite;
    }

   
    public Employe getResponsable() {
        return responsable;
    }

    public Entrepot responsable(Employe employe) {
        this.responsable = employe;
        return this;
    }

    public void setResponsable(Employe employe) {
        this.responsable = employe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entrepot entrepot = (Entrepot) o;
        if (entrepot.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, entrepot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Entrepot{" +
            "id=" + id +
            ", capactite='" + capactite + "'" +
            '}';
    }

    public Activites getActivite() {
        return activite;
    }

    public void setActivite(Activites activite) {
        this.activite = activite;
    }
    
    
}
