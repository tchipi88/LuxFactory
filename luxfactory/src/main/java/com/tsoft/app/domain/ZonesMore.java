/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author alexcheuko
 */
@Entity
@Table(name = "zones_more")
public class ZonesMore implements Serializable {

    @Id
    @Column(name = "ID")
    private Long id; // The row number!
    
    private Long entrepotId;
    private Long nb;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public Long getEntrepotId() {
        return entrepotId;
    }

    public void setEntrepotId(Long entrepotId) {
        this.entrepotId = entrepotId;
    }

    
    public Long getNb() {
        return nb;
    }

    public void setNb(Long nb) {
        this.nb = nb;
    }

   
    
}
