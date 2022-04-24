package com.scb.fimob.order.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.scb.fimob.order.domain.OrderInstHk} entity.
 */
public class OrderInstHkDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 16)
    private String instRefNbr;

    @NotNull
    @Size(max = 16)
    private String orderRefNbr;

    @NotNull
    @Size(max = 12)
    private String securityCode;

    @NotNull
    @Size(max = 3)
    private String securityCcy;

    @NotNull
    @Size(max = 1)
    private String securityType;

    @NotNull
    @Size(max = 1)
    private String txnType;

    @Size(max = 3)
    private String channel;

    @Size(max = 30)
    private String orderExtRefId1;

    @Size(max = 12)
    private String custId;

    @Size(max = 30)
    private String isAcctNbr;

    @NotNull
    @Size(max = 30)
    private String bndAcctNbr;

    @NotNull
    @Size(max = 30)
    private String instType;

    @NotNull
    @Size(max = 3)
    private String instStatus;

    @NotNull
    @DecimalMax(value = "11")
    private BigDecimal quantity;

    @DecimalMax(value = "11")
    private BigDecimal newQuantity;

    @NotNull
    @DecimalMax(value = "11")
    private BigDecimal origIbPrice;

    @DecimalMax(value = "11")
    private BigDecimal newIbPrice;

    @NotNull
    @DecimalMax(value = "11")
    private BigDecimal origClientPrice;

    @DecimalMax(value = "11")
    private BigDecimal newClientPrice;

    @DecimalMax(value = "7")
    private BigDecimal bankCommAmt;

    @DecimalMax(value = "7")
    private BigDecimal newBankCommAmt;

    @DecimalMax(value = "7")
    private BigDecimal spread;

    @DecimalMax(value = "7")
    private BigDecimal newSpread;

    @Size(max = 1)
    private String spreadOverride;

    @Size(max = 1)
    private String newSpreadOverride;

    @DecimalMax(value = "11")
    private BigDecimal accruedInt;

    @DecimalMax(value = "11")
    private BigDecimal newAccruedInt;

    @DecimalMax(value = "11")
    private BigDecimal settlementAmt;

    @DecimalMax(value = "11")
    private BigDecimal newSettlementAmt;

    @NotNull
    @Size(max = 30)
    private String settAcctNbr;

    @Size(max = 30)
    private String newSettAcctNbr;

    @NotNull
    @Size(max = 3)
    private String settCcy;

    @NotNull
    @Size(max = 3)
    private String settCcyNew;

    @NotNull
    private Instant tradeDate;

    @NotNull
    private Instant settlementDate;

    @NotNull
    @Size(max = 300)
    private String instRemarks;

    @NotNull
    @Size(max = 8)
    private String createdBy;

    private Instant createdTs;

    @Size(max = 8)
    private String updatedBy;

    private Instant updatedTs;

    private ZonedDateTime updatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstRefNbr() {
        return instRefNbr;
    }

    public void setInstRefNbr(String instRefNbr) {
        this.instRefNbr = instRefNbr;
    }

    public String getOrderRefNbr() {
        return orderRefNbr;
    }

    public void setOrderRefNbr(String orderRefNbr) {
        this.orderRefNbr = orderRefNbr;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getSecurityCcy() {
        return securityCcy;
    }

    public void setSecurityCcy(String securityCcy) {
        this.securityCcy = securityCcy;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOrderExtRefId1() {
        return orderExtRefId1;
    }

    public void setOrderExtRefId1(String orderExtRefId1) {
        this.orderExtRefId1 = orderExtRefId1;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getIsAcctNbr() {
        return isAcctNbr;
    }

    public void setIsAcctNbr(String isAcctNbr) {
        this.isAcctNbr = isAcctNbr;
    }

    public String getBndAcctNbr() {
        return bndAcctNbr;
    }

    public void setBndAcctNbr(String bndAcctNbr) {
        this.bndAcctNbr = bndAcctNbr;
    }

    public String getInstType() {
        return instType;
    }

    public void setInstType(String instType) {
        this.instType = instType;
    }

    public String getInstStatus() {
        return instStatus;
    }

    public void setInstStatus(String instStatus) {
        this.instStatus = instStatus;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(BigDecimal newQuantity) {
        this.newQuantity = newQuantity;
    }

    public BigDecimal getOrigIbPrice() {
        return origIbPrice;
    }

    public void setOrigIbPrice(BigDecimal origIbPrice) {
        this.origIbPrice = origIbPrice;
    }

    public BigDecimal getNewIbPrice() {
        return newIbPrice;
    }

    public void setNewIbPrice(BigDecimal newIbPrice) {
        this.newIbPrice = newIbPrice;
    }

    public BigDecimal getOrigClientPrice() {
        return origClientPrice;
    }

    public void setOrigClientPrice(BigDecimal origClientPrice) {
        this.origClientPrice = origClientPrice;
    }

    public BigDecimal getNewClientPrice() {
        return newClientPrice;
    }

    public void setNewClientPrice(BigDecimal newClientPrice) {
        this.newClientPrice = newClientPrice;
    }

    public BigDecimal getBankCommAmt() {
        return bankCommAmt;
    }

    public void setBankCommAmt(BigDecimal bankCommAmt) {
        this.bankCommAmt = bankCommAmt;
    }

    public BigDecimal getNewBankCommAmt() {
        return newBankCommAmt;
    }

    public void setNewBankCommAmt(BigDecimal newBankCommAmt) {
        this.newBankCommAmt = newBankCommAmt;
    }

    public BigDecimal getSpread() {
        return spread;
    }

    public void setSpread(BigDecimal spread) {
        this.spread = spread;
    }

    public BigDecimal getNewSpread() {
        return newSpread;
    }

    public void setNewSpread(BigDecimal newSpread) {
        this.newSpread = newSpread;
    }

    public String getSpreadOverride() {
        return spreadOverride;
    }

    public void setSpreadOverride(String spreadOverride) {
        this.spreadOverride = spreadOverride;
    }

    public String getNewSpreadOverride() {
        return newSpreadOverride;
    }

    public void setNewSpreadOverride(String newSpreadOverride) {
        this.newSpreadOverride = newSpreadOverride;
    }

    public BigDecimal getAccruedInt() {
        return accruedInt;
    }

    public void setAccruedInt(BigDecimal accruedInt) {
        this.accruedInt = accruedInt;
    }

    public BigDecimal getNewAccruedInt() {
        return newAccruedInt;
    }

    public void setNewAccruedInt(BigDecimal newAccruedInt) {
        this.newAccruedInt = newAccruedInt;
    }

    public BigDecimal getSettlementAmt() {
        return settlementAmt;
    }

    public void setSettlementAmt(BigDecimal settlementAmt) {
        this.settlementAmt = settlementAmt;
    }

    public BigDecimal getNewSettlementAmt() {
        return newSettlementAmt;
    }

    public void setNewSettlementAmt(BigDecimal newSettlementAmt) {
        this.newSettlementAmt = newSettlementAmt;
    }

    public String getSettAcctNbr() {
        return settAcctNbr;
    }

    public void setSettAcctNbr(String settAcctNbr) {
        this.settAcctNbr = settAcctNbr;
    }

    public String getNewSettAcctNbr() {
        return newSettAcctNbr;
    }

    public void setNewSettAcctNbr(String newSettAcctNbr) {
        this.newSettAcctNbr = newSettAcctNbr;
    }

    public String getSettCcy() {
        return settCcy;
    }

    public void setSettCcy(String settCcy) {
        this.settCcy = settCcy;
    }

    public String getSettCcyNew() {
        return settCcyNew;
    }

    public void setSettCcyNew(String settCcyNew) {
        this.settCcyNew = settCcyNew;
    }

    public Instant getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Instant tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Instant getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Instant settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getInstRemarks() {
        return instRemarks;
    }

    public void setInstRemarks(String instRemarks) {
        this.instRemarks = instRemarks;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(Instant createdTs) {
        this.createdTs = createdTs;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTs() {
        return updatedTs;
    }

    public void setUpdatedTs(Instant updatedTs) {
        this.updatedTs = updatedTs;
    }

    public ZonedDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderInstHkDTO)) {
            return false;
        }

        OrderInstHkDTO orderInstHkDTO = (OrderInstHkDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orderInstHkDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderInstHkDTO{" +
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
