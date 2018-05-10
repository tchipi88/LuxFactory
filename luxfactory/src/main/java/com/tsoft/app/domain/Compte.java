package com.tsoft.app.domain;

import com.tsoft.app.service.template.ReadOnly;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import org.hibernate.annotations.Formula;

/**
 * A Compte.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Compte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    @NotNull
    @Column(name = "intitule", nullable = false)
    private String intitule;

    @Column(name = "debit", precision = 10, scale = 2)
    @ReadOnly
    private BigDecimal debit=BigDecimal.ZERO;

    @Column(name = "credit", precision = 10, scale = 2)
    @ReadOnly
    private BigDecimal credit=BigDecimal.ZERO;

    @Formula("credit-debit")
    private BigDecimal solde;

    public BigDecimal getSolde() {
        return solde;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public Compte intitule(String intitule) {
        this.intitule = intitule;
        return this;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public Compte debit(BigDecimal debit) {
        this.debit = debit;
        return this;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public Compte credit(BigDecimal credit) {
        this.credit = credit;
        return this;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Compte compte = (Compte) o;
        if (compte.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, compte.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Compte{"
                + "id=" + id
                + ", intitule='" + intitule + "'"
                + ", debit='" + debit + "'"
                + ", credit='" + credit + "'"
                + '}';
    }
}
