package com.tsoft.app.domain;

import com.tsoft.app.domain.enumeration.CompteAnalytiqueType;
import com.tsoft.app.service.template.ReadOnly;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A Compte.
 */
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "compteanalytique")
public class CompteAnalytique extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "intitule", nullable = false)
    private String intitule;

    @Column(name = "debit", precision = 10, scale = 2)
    @ReadOnly
    private BigDecimal debit;

    @Column(name = "credit", precision = 10, scale = 2)
    @ReadOnly
    private BigDecimal credit;

    @Formula("credit-debit")
    private BigDecimal solde;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CompteAnalytiqueType type;

    @NotNull
    @ManyToOne
    private Tiers tiers;

    public Tiers getTiers() {
        return tiers;
    }

    public void setTiers(Tiers tiers) {
        this.tiers = tiers;
    }
    
    

    public CompteAnalytiqueType getType() {
        return type;
    }

    public void setType(CompteAnalytiqueType type) {
        this.type = type;
    }

    public BigDecimal getSolde() {
        return solde;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public CompteAnalytique intitule(String intitule) {
        this.intitule = intitule;
        return this;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public CompteAnalytique debit(BigDecimal debit) {
        this.debit = debit;
        return this;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public CompteAnalytique credit(BigDecimal credit) {
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
        CompteAnalytique compte = (CompteAnalytique) o;
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
