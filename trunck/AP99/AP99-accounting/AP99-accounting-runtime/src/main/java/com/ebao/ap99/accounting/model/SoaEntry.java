package com.ebao.ap99.accounting.model;

import java.math.BigDecimal;

public class SoaEntry {

    private Long soaId;

    private Integer cedentYear;

    private Integer cedentQuarter;

    private String contractCode;

    private Long sectionId;

    private String entryCode;

    private String currency;

    private BigDecimal amount;

    private String soaText;

    public Long getSoaId() {
        return soaId;
    }

    public void setSoaId(Long soaId) {
        this.soaId = soaId;
    }

    public Integer getCedentYear() {
        return cedentYear;
    }

    public void setCedentYear(Integer cedentYear) {
        this.cedentYear = cedentYear;
    }

    public Integer getCedentQuarter() {
        return cedentQuarter;
    }

    public void setCedentQuarter(Integer cedentQuarter) {
        this.cedentQuarter = cedentQuarter;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public String getEntryCode() {
        return entryCode;
    }

    public void setEntryCode(String entryCode) {
        this.entryCode = entryCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSoaText() {
        return soaText;
    }

    public void setSoaText(String soaText) {
        this.soaText = soaText;
    }

}
