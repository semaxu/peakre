package com.ebao.ap99.accounting.estimation;

import java.math.BigDecimal;

import com.ebao.ap99.accounting.constant.EstimateStatusEnum;

public class EstimateItem {
    private Long               itemId;
    private Long               estimateId;
    private String             entryCode;
    private BigDecimal         amount;
    private EstimateStatusEnum status;
    private String             calcFormula;
    private String             changeReason;

    public EstimateItem() {
    }

    public EstimateItem(String entryCode) {
        this.entryCode = entryCode;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getEstimateId() {
        return estimateId;
    }

    public void setEstimateId(Long estimateId) {
        this.estimateId = estimateId;
    }

    public String getEntryCode() {
        return entryCode;
    }

    public void setEntryCode(String entryCode) {
        this.entryCode = entryCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public EstimateStatusEnum getStatus() {
        return status;
    }

    public void setStatus(EstimateStatusEnum status) {
        this.status = status;
    }

    public String getCalcFormula() {
        return calcFormula;
    }

    public void setCalcFormula(String calcFormula) {
        this.calcFormula = calcFormula;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

}
