package com.ebao.ap99.contract.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContractModel {
    private Long               contCompId;
    private String             contractCode;
    private String             contractNature;
    private String             contractCategory;
    private Long               contractId;
    private String             contractName;
    private String             mainCurrency;
    private String             name;
    private String             fullName;
    private Long               uwYear;
    private String             cedent;
    private String             cedentCountry;
    private String             broker;
    private String             brokerCountry;
    private String             cedentRef;
    private String             brokerRef;
    private String             mainclass;
    private String             fronting;
    private String             posting;
    private String             retrocession;
    private String             latestStatus;
    private Date               reinsStarting;
    private Date               reinsEnding;
    private BigDecimal         settlementDays;
    private String             currency;
    private List<SectionModel> sectionModel = new ArrayList<SectionModel>();

    public Long getContCompId() {
        return contCompId;
    }

    public void setContCompId(Long contCompId) {
        this.contCompId = contCompId;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getContractNature() {
        return contractNature;
    }

    public void setContractNature(String contractNature) {
        this.contractNature = contractNature;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getUwYear() {
        return uwYear;
    }

    public void setUwYear(Long uwYear) {
        this.uwYear = uwYear;
    }

    public String getCedent() {
        return cedent;
    }

    public void setCedent(String cedent) {
        this.cedent = cedent;
    }

    public String getCedentCountry() {
        return cedentCountry;
    }

    public void setCedentCountry(String cedentCountry) {
        this.cedentCountry = cedentCountry;
    }

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public String getBrokerCountry() {
        return brokerCountry;
    }

    public void setBrokerCountry(String brokerCountry) {
        this.brokerCountry = brokerCountry;
    }

    public String getCedentRef() {
        return cedentRef;
    }

    public void setCedentRef(String cedentRef) {
        this.cedentRef = cedentRef;
    }

    public String getBrokerRef() {
        return brokerRef;
    }

    public void setBrokerRef(String brokerRef) {
        this.brokerRef = brokerRef;
    }

    public String getMainclass() {
        return mainclass;
    }

    public void setMainclass(String mainclass) {
        this.mainclass = mainclass;
    }

    public String getFronting() {
        return fronting;
    }

    public void setFronting(String fronting) {
        this.fronting = fronting;
    }

    public String getPosting() {
        return posting;
    }

    public void setPosting(String posting) {
        this.posting = posting;
    }

    public String getRetrocession() {
        return retrocession;
    }

    public void setRetrocession(String retrocession) {
        this.retrocession = retrocession;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<SectionModel> getSectionModel() {
        return sectionModel;
    }

    public void setSectionModel(List<SectionModel> sectionModel) {
        this.sectionModel = sectionModel;
    }

    public String getContractCategory() {
        return contractCategory;
    }

    public void setContractCategory(String contractCategory) {
        this.contractCategory = contractCategory;
    }

    public String getMainCurrency() {
        return mainCurrency;
    }

    public void setMainCurrency(String mainCurrency) {
        this.mainCurrency = mainCurrency;
    }

    public String getLatestStatus() {
        return latestStatus;
    }

    public void setLatestStatus(String latestStatus) {
        this.latestStatus = latestStatus;
    }

    public Date getReinsStarting() {
        return reinsStarting;
    }

    public void setReinsStarting(Date reinsStarting) {
        this.reinsStarting = reinsStarting;
    }

    public Date getReinsEnding() {
        return reinsEnding;
    }

    public void setReinsEnding(Date reinsEnding) {
        this.reinsEnding = reinsEnding;
    }

    public BigDecimal getSettlementDays() {
        return settlementDays;
    }

    public void setSettlementDays(BigDecimal settlementDays) {
        this.settlementDays = settlementDays;
    }

}
