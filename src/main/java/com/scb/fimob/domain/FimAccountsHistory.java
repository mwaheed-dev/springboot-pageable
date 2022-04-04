package com.scb.fimob.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FimAccountsHistory.
 */
@Entity
@Table(name = "fim_accounts_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FimAccountsHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "history_ts")
    private Instant historyTs;

    @NotNull
    @Size(max = 30)
    @Column(name = "cust_id", length = 30, nullable = false)
    private String custId;

    @NotNull
    @Size(max = 30)
    @Column(name = "reln_id", length = 30, nullable = false)
    private String relnId;

    @NotNull
    @Size(max = 5)
    @Column(name = "reln_type", length = 5, nullable = false)
    private String relnType;

    @Size(max = 10)
    @Column(name = "oper_inst", length = 10)
    private String operInst;

    @NotNull
    @Size(max = 30)
    @Column(name = "is_acct_nbr", length = 30, nullable = false)
    private String isAcctNbr;

    @NotNull
    @Size(max = 30)
    @Column(name = "bnd_acct_nbr", length = 30, nullable = false)
    private String bndAcctNbr;

    @Size(max = 10)
    @Column(name = "closing_id", length = 10)
    private String closingId;

    @Size(max = 10)
    @Column(name = "sub_segment", length = 10)
    private String subSegment;

    @Size(max = 10)
    @Column(name = "branch_code", length = 10)
    private String branchCode;

    @NotNull
    @Size(max = 10)
    @Column(name = "acct_status", length = 10, nullable = false)
    private String acctStatus;

    @Size(max = 3)
    @Column(name = "ctry_code", length = 3)
    private String ctryCode;

    @Size(max = 100)
    @Column(name = "acct_owners", length = 100)
    private String acctOwners;

    @Size(max = 200)
    @Column(name = "remarks", length = 200)
    private String remarks;

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

    public FimAccountsHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public FimAccountsHistory accountId(String accountId) {
        this.setAccountId(accountId);
        return this;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Instant getHistoryTs() {
        return this.historyTs;
    }

    public FimAccountsHistory historyTs(Instant historyTs) {
        this.setHistoryTs(historyTs);
        return this;
    }

    public void setHistoryTs(Instant historyTs) {
        this.historyTs = historyTs;
    }

    public String getCustId() {
        return this.custId;
    }

    public FimAccountsHistory custId(String custId) {
        this.setCustId(custId);
        return this;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getRelnId() {
        return this.relnId;
    }

    public FimAccountsHistory relnId(String relnId) {
        this.setRelnId(relnId);
        return this;
    }

    public void setRelnId(String relnId) {
        this.relnId = relnId;
    }

    public String getRelnType() {
        return this.relnType;
    }

    public FimAccountsHistory relnType(String relnType) {
        this.setRelnType(relnType);
        return this;
    }

    public void setRelnType(String relnType) {
        this.relnType = relnType;
    }

    public String getOperInst() {
        return this.operInst;
    }

    public FimAccountsHistory operInst(String operInst) {
        this.setOperInst(operInst);
        return this;
    }

    public void setOperInst(String operInst) {
        this.operInst = operInst;
    }

    public String getIsAcctNbr() {
        return this.isAcctNbr;
    }

    public FimAccountsHistory isAcctNbr(String isAcctNbr) {
        this.setIsAcctNbr(isAcctNbr);
        return this;
    }

    public void setIsAcctNbr(String isAcctNbr) {
        this.isAcctNbr = isAcctNbr;
    }

    public String getBndAcctNbr() {
        return this.bndAcctNbr;
    }

    public FimAccountsHistory bndAcctNbr(String bndAcctNbr) {
        this.setBndAcctNbr(bndAcctNbr);
        return this;
    }

    public void setBndAcctNbr(String bndAcctNbr) {
        this.bndAcctNbr = bndAcctNbr;
    }

    public String getClosingId() {
        return this.closingId;
    }

    public FimAccountsHistory closingId(String closingId) {
        this.setClosingId(closingId);
        return this;
    }

    public void setClosingId(String closingId) {
        this.closingId = closingId;
    }

    public String getSubSegment() {
        return this.subSegment;
    }

    public FimAccountsHistory subSegment(String subSegment) {
        this.setSubSegment(subSegment);
        return this;
    }

    public void setSubSegment(String subSegment) {
        this.subSegment = subSegment;
    }

    public String getBranchCode() {
        return this.branchCode;
    }

    public FimAccountsHistory branchCode(String branchCode) {
        this.setBranchCode(branchCode);
        return this;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getAcctStatus() {
        return this.acctStatus;
    }

    public FimAccountsHistory acctStatus(String acctStatus) {
        this.setAcctStatus(acctStatus);
        return this;
    }

    public void setAcctStatus(String acctStatus) {
        this.acctStatus = acctStatus;
    }

    public String getCtryCode() {
        return this.ctryCode;
    }

    public FimAccountsHistory ctryCode(String ctryCode) {
        this.setCtryCode(ctryCode);
        return this;
    }

    public void setCtryCode(String ctryCode) {
        this.ctryCode = ctryCode;
    }

    public String getAcctOwners() {
        return this.acctOwners;
    }

    public FimAccountsHistory acctOwners(String acctOwners) {
        this.setAcctOwners(acctOwners);
        return this;
    }

    public void setAcctOwners(String acctOwners) {
        this.acctOwners = acctOwners;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public FimAccountsHistory remarks(String remarks) {
        this.setRemarks(remarks);
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public FimAccountsHistory createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedTs() {
        return this.createdTs;
    }

    public FimAccountsHistory createdTs(Instant createdTs) {
        this.setCreatedTs(createdTs);
        return this;
    }

    public void setCreatedTs(Instant createdTs) {
        this.createdTs = createdTs;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public FimAccountsHistory updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTs() {
        return this.updatedTs;
    }

    public FimAccountsHistory updatedTs(Instant updatedTs) {
        this.setUpdatedTs(updatedTs);
        return this;
    }

    public void setUpdatedTs(Instant updatedTs) {
        this.updatedTs = updatedTs;
    }

    public String getRecordStatus() {
        return this.recordStatus;
    }

    public FimAccountsHistory recordStatus(String recordStatus) {
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
        if (!(o instanceof FimAccountsHistory)) {
            return false;
        }
        return id != null && id.equals(((FimAccountsHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FimAccountsHistory{" +
            "id=" + getId() +
            ", accountId='" + getAccountId() + "'" +
            ", historyTs='" + getHistoryTs() + "'" +
            ", custId='" + getCustId() + "'" +
            ", relnId='" + getRelnId() + "'" +
            ", relnType='" + getRelnType() + "'" +
            ", operInst='" + getOperInst() + "'" +
            ", isAcctNbr='" + getIsAcctNbr() + "'" +
            ", bndAcctNbr='" + getBndAcctNbr() + "'" +
            ", closingId='" + getClosingId() + "'" +
            ", subSegment='" + getSubSegment() + "'" +
            ", branchCode='" + getBranchCode() + "'" +
            ", acctStatus='" + getAcctStatus() + "'" +
            ", ctryCode='" + getCtryCode() + "'" +
            ", acctOwners='" + getAcctOwners() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdTs='" + getCreatedTs() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTs='" + getUpdatedTs() + "'" +
            ", recordStatus='" + getRecordStatus() + "'" +
            "}";
    }
}
