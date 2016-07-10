/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Date: Mar 23, 2016 3:58:41 PM
 * 
 * @author xiaoyu.yang
 */
public class ActualItem {

    private String entryCode;

    private String text;

    private Date insertTime;

    private Long insertBy;

    private String currency;

    private String mainCurrency;

    private BigDecimal toMainCurrencyRate;

    private BigDecimal amount;

    private BigDecimal switchedAmount;

    public String getEntryCode() {
        return entryCode;
    }

    public void setEntryCode(String entryCode) {
        this.entryCode = entryCode;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Long getInsertBy() {
        return insertBy;
    }

    public void setInsertBy(Long insertBy) {
        this.insertBy = insertBy;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMainCurrency() {
        return mainCurrency;
    }

    public void setMainCurrency(String mainCurrency) {
        this.mainCurrency = mainCurrency;
    }

    public BigDecimal getToMainCurrencyRate() {
        return toMainCurrencyRate;
    }

    public void setToMainCurrencyRate(BigDecimal toMainCurrencyRate) {
        this.toMainCurrencyRate = toMainCurrencyRate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getSwitchedAmount() {
        return amount.divide(toMainCurrencyRate, 2, BigDecimal.ROUND_HALF_UP);
    }

    public void setSwitchedAmount(BigDecimal switchedAmount) {
        this.switchedAmount = switchedAmount;
    }

}
