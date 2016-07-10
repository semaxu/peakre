/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.model;

import java.math.BigDecimal;

/**
 * Date: Mar 28, 2016 7:41:46 PM
 * 
 * @author xiaoyu.yang
 */
public class TechnicalResultVO {

    private String insertTime;

    private String contractCode;

    private String accountText;

    private String section;

    private String entryCode;

    private String entryCodeName;

    private String indicater;

    private String cedentQuarter;

    private String currency;

    private BigDecimal cashBalance;

    private BigDecimal technicalResult;

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getAccountText() {
        return accountText;
    }

    public void setAccountText(String accountText) {
        this.accountText = accountText;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getEntryCode() {
        return entryCode;
    }

    public void setEntryCode(String entryCode) {
        this.entryCode = entryCode;
    }

    public String getEntryCodeName() {
        return entryCodeName;
    }

    public void setEntryCodeName(String entryCodeName) {
        this.entryCodeName = entryCodeName;
    }

    public String getIndicater() {
        return indicater;
    }

    public void setIndicater(String indicater) {
        this.indicater = indicater;
    }

    public String getCedentQuarter() {
        return cedentQuarter;
    }

    public void setCedentQuarter(String cedentQuarter) {
        this.cedentQuarter = cedentQuarter;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(BigDecimal cashBalance) {
        this.cashBalance = cashBalance;
    }

    public BigDecimal getTechnicalResult() {
        return technicalResult;
    }

    public void setTechnicalResult(BigDecimal technicalResult) {
        this.technicalResult = technicalResult;
    }

}
