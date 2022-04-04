package com.scb.fimob.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FimSettAcct.
 */
@Entity
@Table(name = "fim_sett_acct")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FimSettAcct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "settacc_id")
    private String settaccId;

    @NotNull
    @Size(max = 15)
    @Column(name = "account_id", length = 15, nullable = false)
    private String accountId;

    @NotNull
    @Size(max = 30)
    @Column(name = "sett_acct_nbr", length = 30, nullable = false)
    private String settAcctNbr;

    @NotNull
    @Size(max = 3)
    @Column(name = "sett_ccy", length = 3, nullable = false)
    private String settCcy;

    @NotNull
    @Size(max = 10)
    @Column(name = "sett_acct_status", length = 10, nullable = false)
    private String settAcctStatus;

    @Size(max = 8)
    @Column(name = "created_by", length = 8)
    private String createdBy;

    @Column(name = "created_ts")
    private Instant createdTs;

    @Size(max = 8)
    @Column(name = "updated_by", length = 8)
    private String updatedBy;

    @Column(name = "updated_ts")
    private Instant updatedTs;

    @Size(max = 10)
    @Column(name = "record_status", length = 10)
    private String recordStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FimSettAcct id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSettaccId() {
        return this.settaccId;
    }

    public FimSettAcct settaccId(String settaccId) {
        this.setSettaccId(settaccId);
        return this;
    }

    public void setSettaccId(String settaccId) {
        this.settaccId = settaccId;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public FimSettAcct accountId(String accountId) {
        this.setAccountId(accountId);
        return this;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getSettAcctNbr() {
        return this.settAcctNbr;
    }

    public FimSettAcct settAcctNbr(String settAcctNbr) {
        this.setSettAcctNbr(settAcctNbr);
        return this;
    }

    public void setSettAcctNbr(String settAcctNbr) {
        this.settAcctNbr = settAcctNbr;
    }

    public String getSettCcy() {
        return this.settCcy;
    }

    public FimSettAcct settCcy(String settCcy) {
        this.setSettCcy(settCcy);
        return this;
    }

    public void setSettCcy(String settCcy) {
        this.settCcy = settCcy;
    }

    public String getSettAcctStatus() {
        return this.settAcctStatus;
    }

    public FimSettAcct settAcctStatus(String settAcctStatus) {
        this.setSettAcctStatus(settAcctStatus);
        return this;
    }

    public void setSettAcctStatus(String settAcctStatus) {
        this.settAcctStatus = settAcctStatus;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public FimSettAcct createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedTs() {
        return this.createdTs;
    }

    public FimSettAcct createdTs(Instant createdTs) {
        this.setCreatedTs(createdTs);
        return this;
    }

    public void setCreatedTs(Instant createdTs) {
        this.createdTs = createdTs;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public FimSettAcct updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTs() {
        return this.updatedTs;
    }

    public FimSettAcct updatedTs(Instant updatedTs) {
        this.setUpdatedTs(updatedTs);
        return this;
    }

    public void setUpdatedTs(Instant updatedTs) {
        this.updatedTs = updatedTs;
    }

    public String getRecordStatus() {
        return this.recordStatus;
    }

    public FimSettAcct recordStatus(String recordStatus) {
        this.setRecordStatus(recordStatus);
        return this;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FimSettAcct)) {
            return false;
        }
        return id != null && id.equals(((FimSettAcct) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FimSettAcct{" +
            "id=" + getId() +
            ", settaccId='" + getSettaccId() + "'" +
            ", accountId='" + getAccountId() + "'" +
            ", settAcctNbr='" + getSettAcctNbr() + "'" +
            ", settCcy='" + getSettCcy() + "'" +
            ", settAcctStatus='" + getSettAcctStatus() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdTs='" + getCreatedTs() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTs='" + getUpdatedTs() + "'" +
            ", recordStatus='" + getRecordStatus() + "'" +
            "}";
    }
}
