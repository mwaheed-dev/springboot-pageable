package com.scb.fimob.order.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OrderInstHk.
 */
@Entity
@Table(name = "order_inst_hk")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrderInstHk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 16)
    @Column(name = "inst_ref_nbr", length = 16, nullable = false)
    private String instRefNbr;

    @NotNull
    @Size(max = 16)
    @Column(name = "order_ref_nbr", length = 16, nullable = false)
    private String orderRefNbr;

    @NotNull
    @Size(max = 12)
    @Column(name = "security_code", length = 12, nullable = false)
    private String securityCode;

    @NotNull
    @Size(max = 3)
    @Column(name = "security_ccy", length = 3, nullable = false)
    private String securityCcy;

    @NotNull
    @Size(max = 1)
    @Column(name = "security_type", length = 1, nullable = false)
    private String securityType;

    @NotNull
    @Size(max = 1)
    @Column(name = "txn_type", length = 1, nullable = false)
    private String txnType;

    @Size(max = 3)
    @Column(name = "channel", length = 3)
    private String channel;

    @Size(max = 30)
    @Column(name = "order_ext_ref_id_1", length = 30)
    private String orderExtRefId1;

    @Size(max = 12)
    @Column(name = "cust_id", length = 12)
    private String custId;

    @Size(max = 30)
    @Column(name = "is_acct_nbr", length = 30)
    private String isAcctNbr;

    @NotNull
    @Size(max = 30)
    @Column(name = "bnd_acct_nbr", length = 30, nullable = false)
    private String bndAcctNbr;

    @NotNull
    @Size(max = 30)
    @Column(name = "inst_type", length = 30, nullable = false)
    private String instType;

    @NotNull
    @Size(max = 3)
    @Column(name = "inst_status", length = 3, nullable = false)
    private String instStatus;

    @NotNull
    @DecimalMax(value = "11")
    @Column(name = "quantity", precision = 21, scale = 2, nullable = false)
    private BigDecimal quantity;

    @DecimalMax(value = "11")
    @Column(name = "new_quantity", precision = 21, scale = 2)
    private BigDecimal newQuantity;

    @NotNull
    @DecimalMax(value = "11")
    @Column(name = "orig_ib_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal origIbPrice;

    @DecimalMax(value = "11")
    @Column(name = "new_ib_price", precision = 21, scale = 2)
    private BigDecimal newIbPrice;

    @NotNull
    @DecimalMax(value = "11")
    @Column(name = "orig_client_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal origClientPrice;

    @DecimalMax(value = "11")
    @Column(name = "new_client_price", precision = 21, scale = 2)
    private BigDecimal newClientPrice;

    @DecimalMax(value = "7")
    @Column(name = "bank_comm_amt", precision = 21, scale = 2)
    private BigDecimal bankCommAmt;

    @DecimalMax(value = "7")
    @Column(name = "new_bank_comm_amt", precision = 21, scale = 2)
    private BigDecimal newBankCommAmt;

    @DecimalMax(value = "7")
    @Column(name = "spread", precision = 21, scale = 2)
    private BigDecimal spread;

    @DecimalMax(value = "7")
    @Column(name = "new_spread", precision = 21, scale = 2)
    private BigDecimal newSpread;

    @Size(max = 1)
    @Column(name = "spread_override", length = 1)
    private String spreadOverride;

    @Size(max = 1)
    @Column(name = "new_spread_override", length = 1)
    private String newSpreadOverride;

    @DecimalMax(value = "11")
    @Column(name = "accrued_int", precision = 21, scale = 2)
    private BigDecimal accruedInt;

    @DecimalMax(value = "11")
    @Column(name = "new_accrued_int", precision = 21, scale = 2)
    private BigDecimal newAccruedInt;

    @DecimalMax(value = "11")
    @Column(name = "settlement_amt", precision = 21, scale = 2)
    private BigDecimal settlementAmt;

    @DecimalMax(value = "11")
    @Column(name = "new_settlement_amt", precision = 21, scale = 2)
    private BigDecimal newSettlementAmt;

    @NotNull
    @Size(max = 30)
    @Column(name = "sett_acct_nbr", length = 30, nullable = false)
    private String settAcctNbr;

    @Size(max = 30)
    @Column(name = "new_sett_acct_nbr", length = 30)
    private String newSettAcctNbr;

    @NotNull
    @Size(max = 3)
    @Column(name = "sett_ccy", length = 3, nullable = false)
    private String settCcy;

    @NotNull
    @Size(max = 3)
    @Column(name = "sett_ccy_new", length = 3, nullable = false)
    private String settCcyNew;

    @NotNull
    @Column(name = "trade_date", nullable = false)
    private Instant tradeDate;

    @NotNull
    @Column(name = "settlement_date", nullable = false)
    private Instant settlementDate;

    @NotNull
    @Size(max = 300)
    @Column(name = "inst_remarks", length = 300, nullable = false)
    private String instRemarks;

    @NotNull
    @Size(max = 8)
    @Column(name = "created_by", length = 8, nullable = false)
    private String createdBy;

    @Column(name = "created_ts")
    private Instant createdTs;

    @Size(max = 8)
    @Column(name = "updated_by", length = 8)
    private String updatedBy;

    @Column(name = "updated_ts")
    private Instant updatedTs;

    @Column(name = "updated_date")
    private ZonedDateTime updatedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrderInstHk id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstRefNbr() {
        return this.instRefNbr;
    }

    public OrderInstHk instRefNbr(String instRefNbr) {
        this.setInstRefNbr(instRefNbr);
        return this;
    }

    public void setInstRefNbr(String instRefNbr) {
        this.instRefNbr = instRefNbr;
    }

    public String getOrderRefNbr() {
        return this.orderRefNbr;
    }

    public OrderInstHk orderRefNbr(String orderRefNbr) {
        this.setOrderRefNbr(orderRefNbr);
        return this;
    }

    public void setOrderRefNbr(String orderRefNbr) {
        this.orderRefNbr = orderRefNbr;
    }

    public String getSecurityCode() {
        return this.securityCode;
    }

    public OrderInstHk securityCode(String securityCode) {
        this.setSecurityCode(securityCode);
        return this;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getSecurityCcy() {
        return this.securityCcy;
    }

    public OrderInstHk securityCcy(String securityCcy) {
        this.setSecurityCcy(securityCcy);
        return this;
    }

    public void setSecurityCcy(String securityCcy) {
        this.securityCcy = securityCcy;
    }

    public String getSecurityType() {
        return this.securityType;
    }

    public OrderInstHk securityType(String securityType) {
        this.setSecurityType(securityType);
        return this;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public String getTxnType() {
        return this.txnType;
    }

    public OrderInstHk txnType(String txnType) {
        this.setTxnType(txnType);
        return this;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getChannel() {
        return this.channel;
    }

    public OrderInstHk channel(String channel) {
        this.setChannel(channel);
        return this;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOrderExtRefId1() {
        return this.orderExtRefId1;
    }

    public OrderInstHk orderExtRefId1(String orderExtRefId1) {
        this.setOrderExtRefId1(orderExtRefId1);
        return this;
    }

    public void setOrderExtRefId1(String orderExtRefId1) {
        this.orderExtRefId1 = orderExtRefId1;
    }

    public String getCustId() {
        return this.custId;
    }

    public OrderInstHk custId(String custId) {
        this.setCustId(custId);
        return this;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getIsAcctNbr() {
        return this.isAcctNbr;
    }

    public OrderInstHk isAcctNbr(String isAcctNbr) {
        this.setIsAcctNbr(isAcctNbr);
        return this;
    }

    public void setIsAcctNbr(String isAcctNbr) {
        this.isAcctNbr = isAcctNbr;
    }

    public String getBndAcctNbr() {
        return this.bndAcctNbr;
    }

    public OrderInstHk bndAcctNbr(String bndAcctNbr) {
        this.setBndAcctNbr(bndAcctNbr);
        return this;
    }

    public void setBndAcctNbr(String bndAcctNbr) {
        this.bndAcctNbr = bndAcctNbr;
    }

    public String getInstType() {
        return this.instType;
    }

    public OrderInstHk instType(String instType) {
        this.setInstType(instType);
        return this;
    }

    public void setInstType(String instType) {
        this.instType = instType;
    }

    public String getInstStatus() {
        return this.instStatus;
    }

    public OrderInstHk instStatus(String instStatus) {
        this.setInstStatus(instStatus);
        return this;
    }

    public void setInstStatus(String instStatus) {
        this.instStatus = instStatus;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public OrderInstHk quantity(BigDecimal quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getNewQuantity() {
        return this.newQuantity;
    }

    public OrderInstHk newQuantity(BigDecimal newQuantity) {
        this.setNewQuantity(newQuantity);
        return this;
    }

    public void setNewQuantity(BigDecimal newQuantity) {
        this.newQuantity = newQuantity;
    }

    public BigDecimal getOrigIbPrice() {
        return this.origIbPrice;
    }

    public OrderInstHk origIbPrice(BigDecimal origIbPrice) {
        this.setOrigIbPrice(origIbPrice);
        return this;
    }

    public void setOrigIbPrice(BigDecimal origIbPrice) {
        this.origIbPrice = origIbPrice;
    }

    public BigDecimal getNewIbPrice() {
        return this.newIbPrice;
    }

    public OrderInstHk newIbPrice(BigDecimal newIbPrice) {
        this.setNewIbPrice(newIbPrice);
        return this;
    }

    public void setNewIbPrice(BigDecimal newIbPrice) {
        this.newIbPrice = newIbPrice;
    }

    public BigDecimal getOrigClientPrice() {
        return this.origClientPrice;
    }

    public OrderInstHk origClientPrice(BigDecimal origClientPrice) {
        this.setOrigClientPrice(origClientPrice);
        return this;
    }

    public void setOrigClientPrice(BigDecimal origClientPrice) {
        this.origClientPrice = origClientPrice;
    }

    public BigDecimal getNewClientPrice() {
        return this.newClientPrice;
    }

    public OrderInstHk newClientPrice(BigDecimal newClientPrice) {
        this.setNewClientPrice(newClientPrice);
        return this;
    }

    public void setNewClientPrice(BigDecimal newClientPrice) {
        this.newClientPrice = newClientPrice;
    }

    public BigDecimal getBankCommAmt() {
        return this.bankCommAmt;
    }

    public OrderInstHk bankCommAmt(BigDecimal bankCommAmt) {
        this.setBankCommAmt(bankCommAmt);
        return this;
    }

    public void setBankCommAmt(BigDecimal bankCommAmt) {
        this.bankCommAmt = bankCommAmt;
    }

    public BigDecimal getNewBankCommAmt() {
        return this.newBankCommAmt;
    }

    public OrderInstHk newBankCommAmt(BigDecimal newBankCommAmt) {
        this.setNewBankCommAmt(newBankCommAmt);
        return this;
    }

    public void setNewBankCommAmt(BigDecimal newBankCommAmt) {
        this.newBankCommAmt = newBankCommAmt;
    }

    public BigDecimal getSpread() {
        return this.spread;
    }

    public OrderInstHk spread(BigDecimal spread) {
        this.setSpread(spread);
        return this;
    }

    public void setSpread(BigDecimal spread) {
        this.spread = spread;
    }

    public BigDecimal getNewSpread() {
        return this.newSpread;
    }

    public OrderInstHk newSpread(BigDecimal newSpread) {
        this.setNewSpread(newSpread);
        return this;
    }

    public void setNewSpread(BigDecimal newSpread) {
        this.newSpread = newSpread;
    }

    public String getSpreadOverride() {
        return this.spreadOverride;
    }

    public OrderInstHk spreadOverride(String spreadOverride) {
        this.setSpreadOverride(spreadOverride);
        return this;
    }

    public void setSpreadOverride(String spreadOverride) {
        this.spreadOverride = spreadOverride;
    }

    public String getNewSpreadOverride() {
        return this.newSpreadOverride;
    }

    public OrderInstHk newSpreadOverride(String newSpreadOverride) {
        this.setNewSpreadOverride(newSpreadOverride);
        return this;
    }

    public void setNewSpreadOverride(String newSpreadOverride) {
        this.newSpreadOverride = newSpreadOverride;
    }

    public BigDecimal getAccruedInt() {
        return this.accruedInt;
    }

    public OrderInstHk accruedInt(BigDecimal accruedInt) {
        this.setAccruedInt(accruedInt);
        return this;
    }

    public void setAccruedInt(BigDecimal accruedInt) {
        this.accruedInt = accruedInt;
    }

    public BigDecimal getNewAccruedInt() {
        return this.newAccruedInt;
    }

    public OrderInstHk newAccruedInt(BigDecimal newAccruedInt) {
        this.setNewAccruedInt(newAccruedInt);
        return this;
    }

    public void setNewAccruedInt(BigDecimal newAccruedInt) {
        this.newAccruedInt = newAccruedInt;
    }

    public BigDecimal getSettlementAmt() {
        return this.settlementAmt;
    }

    public OrderInstHk settlementAmt(BigDecimal settlementAmt) {
        this.setSettlementAmt(settlementAmt);
        return this;
    }

    public void setSettlementAmt(BigDecimal settlementAmt) {
        this.settlementAmt = settlementAmt;
    }

    public BigDecimal getNewSettlementAmt() {
        return this.newSettlementAmt;
    }

    public OrderInstHk newSettlementAmt(BigDecimal newSettlementAmt) {
        this.setNewSettlementAmt(newSettlementAmt);
        return this;
    }

    public void setNewSettlementAmt(BigDecimal newSettlementAmt) {
        this.newSettlementAmt = newSettlementAmt;
    }

    public String getSettAcctNbr() {
        return this.settAcctNbr;
    }

    public OrderInstHk settAcctNbr(String settAcctNbr) {
        this.setSettAcctNbr(settAcctNbr);
        return this;
    }

    public void setSettAcctNbr(String settAcctNbr) {
        this.settAcctNbr = settAcctNbr;
    }

    public String getNewSettAcctNbr() {
        return this.newSettAcctNbr;
    }

    public OrderInstHk newSettAcctNbr(String newSettAcctNbr) {
        this.setNewSettAcctNbr(newSettAcctNbr);
        return this;
    }

    public void setNewSettAcctNbr(String newSettAcctNbr) {
        this.newSettAcctNbr = newSettAcctNbr;
    }

    public String getSettCcy() {
        return this.settCcy;
    }

    public OrderInstHk settCcy(String settCcy) {
        this.setSettCcy(settCcy);
        return this;
    }

    public void setSettCcy(String settCcy) {
        this.settCcy = settCcy;
    }

    public String getSettCcyNew() {
        return this.settCcyNew;
    }

    public OrderInstHk settCcyNew(String settCcyNew) {
        this.setSettCcyNew(settCcyNew);
        return this;
    }

    public void setSettCcyNew(String settCcyNew) {
        this.settCcyNew = settCcyNew;
    }

    public Instant getTradeDate() {
        return this.tradeDate;
    }

    public OrderInstHk tradeDate(Instant tradeDate) {
        this.setTradeDate(tradeDate);
        return this;
    }

    public void setTradeDate(Instant tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Instant getSettlementDate() {
        return this.settlementDate;
    }

    public OrderInstHk settlementDate(Instant settlementDate) {
        this.setSettlementDate(settlementDate);
        return this;
    }

    public void setSettlementDate(Instant settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getInstRemarks() {
        return this.instRemarks;
    }

    public OrderInstHk instRemarks(String instRemarks) {
        this.setInstRemarks(instRemarks);
        return this;
    }

    public void setInstRemarks(String instRemarks) {
        this.instRemarks = instRemarks;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public OrderInstHk createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedTs() {
        return this.createdTs;
    }

    public OrderInstHk createdTs(Instant createdTs) {
        this.setCreatedTs(createdTs);
        return this;
    }

    public void setCreatedTs(Instant createdTs) {
        this.createdTs = createdTs;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public OrderInstHk updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTs() {
        return this.updatedTs;
    }

    public OrderInstHk updatedTs(Instant updatedTs) {
        this.setUpdatedTs(updatedTs);
        return this;
    }

    public void setUpdatedTs(Instant updatedTs) {
        this.updatedTs = updatedTs;
    }

    public ZonedDateTime getUpdatedDate() {
        return this.updatedDate;
    }

    public OrderInstHk updatedDate(ZonedDateTime updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderInstHk)) {
            return false;
        }
        return id != null && id.equals(((OrderInstHk) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderInstHk{" +
            "id=" + getId() +
            ", instRefNbr='" + getInstRefNbr() + "'" +
            ", orderRefNbr='" + getOrderRefNbr() + "'" +
            ", securityCode='" + getSecurityCode() + "'" +
            ", securityCcy='" + getSecurityCcy() + "'" +
            ", securityType='" + getSecurityType() + "'" +
            ", txnType='" + getTxnType() + "'" +
            ", channel='" + getChannel() + "'" +
            ", orderExtRefId1='" + getOrderExtRefId1() + "'" +
            ", custId='" + getCustId() + "'" +
            ", isAcctNbr='" + getIsAcctNbr() + "'" +
            ", bndAcctNbr='" + getBndAcctNbr() + "'" +
            ", instType='" + getInstType() + "'" +
            ", instStatus='" + getInstStatus() + "'" +
            ", quantity=" + getQuantity() +
            ", newQuantity=" + getNewQuantity() +
            ", origIbPrice=" + getOrigIbPrice() +
            ", newIbPrice=" + getNewIbPrice() +
            ", origClientPrice=" + getOrigClientPrice() +
            ", newClientPrice=" + getNewClientPrice() +
            ", bankCommAmt=" + getBankCommAmt() +
            ", newBankCommAmt=" + getNewBankCommAmt() +
            ", spread=" + getSpread() +
            ", newSpread=" + getNewSpread() +
            ", spreadOverride='" + getSpreadOverride() + "'" +
            ", newSpreadOverride='" + getNewSpreadOverride() + "'" +
            ", accruedInt=" + getAccruedInt() +
            ", newAccruedInt=" + getNewAccruedInt() +
            ", settlementAmt=" + getSettlementAmt() +
            ", newSettlementAmt=" + getNewSettlementAmt() +
            ", settAcctNbr='" + getSettAcctNbr() + "'" +
            ", newSettAcctNbr='" + getNewSettAcctNbr() + "'" +
            ", settCcy='" + getSettCcy() + "'" +
            ", settCcyNew='" + getSettCcyNew() + "'" +
            ", tradeDate='" + getTradeDate() + "'" +
            ", settlementDate='" + getSettlementDate() + "'" +
            ", instRemarks='" + getInstRemarks() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdTs='" + getCreatedTs() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTs='" + getUpdatedTs() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
