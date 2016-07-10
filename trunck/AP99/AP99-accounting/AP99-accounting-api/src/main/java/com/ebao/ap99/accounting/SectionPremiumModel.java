package com.ebao.ap99.accounting;

import java.math.BigDecimal;
import java.util.Map;

public class SectionPremiumModel {
    private Long                    contractId;
    private Long                    sectionId;
    private BigDecimal              lossRatio;
    private BigDecimal              totalExpenseRate; // = (sum of expense) / EPI
    private String                  currency;
    private Map<String, BigDecimal> flowItems;        //<Entry Code, Amount>

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public BigDecimal getLossRatio() {
        return lossRatio;
    }

    public void setLossRatio(BigDecimal lossRatio) {
        this.lossRatio = lossRatio;
    }

    public BigDecimal getTotalExpenseRate() {
        return totalExpenseRate;
    }

    public void setTotalExpenseRate(BigDecimal totalExpenseRate) {
        this.totalExpenseRate = totalExpenseRate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Map<String, BigDecimal> getFlowItems() {
        return flowItems;
    }

    public void setFlowItems(Map<String, BigDecimal> flowItems) {
        this.flowItems = flowItems;
    }

}
