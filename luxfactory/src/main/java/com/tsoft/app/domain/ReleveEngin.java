/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author tchipi
 */
@Entity
public class ReleveEngin extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private Engin engin;

    @NotNull
    @Column
    private LocalDate dateReleve;
    @NotNull
    @Column
    private Integer compteurHoraire;
    @NotNull
    @Column
    private Float consoCarburant;

    public Engin getEngin() {
        return engin;
    }

    public void setEngin(Engin engin) {
        this.engin = engin;
    }

    public LocalDate getDateReleve() {
        return dateReleve;
    }

    public void setDateReleve(LocalDate dateReleve) {
        this.dateReleve = dateReleve;
    }

    public Integer getCompteurHoraire() {
        return compteurHoraire;
    }

    public void setCompteurHoraire(Integer compteurHoraire) {
        this.compteurHoraire = compteurHoraire;
    }

    public Float getConsoCarburant() {
        return consoCarburant;
    }

    public void setConsoCarburant(Float consoCarburant) {
        this.consoCarburant = consoCarburant;
    }

  

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
