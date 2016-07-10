/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * Date: Apr 26, 2016 9:57:19 AM
 * 
 * @author xiaoyu.yang
 */
@Entity
@Table(name = "T_RI_ACCT_ESTIMATE_TRANS")
@NamedQueries({ @NamedQuery(name = "TRiAcctEstimateTrans.findAll", query = "SELECT t FROM TRiAcctEstimateTrans t"),
        @NamedQuery(name = "TRiAcctEstimateTrans.findListByConditions", query = "SELECT t FROM TRiAcctEstimateTrans t WHERE t.sectionId = :sectionId AND t.cedentYear = :cedentYear AND t.cedentQuarter = :cedentQuarter AND t.entryCode IN (:entryCode)") })
public class TRiAcctEstimateTrans extends BaseEntityImpl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
    @Column(name = "TRANS_ID")
    private Long transId;

    @Column(name = "TRANS_TYPE")
    private Integer transType;

    @Column(name = "CEDENT_YEAR")
    private Integer cedentYear;

    @Column(name = "CEDENT_QUARTER")
    private Integer cedentQuarter;

    @Column(name = "CONT_ID")
    private Long contId;

    @Column(name = "SECTION_ID")
    private Long sectionId;

    @Column(name = "ESTIMATE_ID")
    private Long estimateId;

    @Column(name = "ENTRY_CODE")
    private String entryCode;

    private String currency;

    @Column(name = "MAIN_CURRENCY")
    private String mainCurrency;

    @Column(name = "TO_MAIN_CURR_RATE")
    private BigDecimal toMainCurrencyRate;

    private BigDecimal amount;

    private String text;

    @Column(name = "SOA_ID")
    private Long soaId;

    @Column(name = "CLAIM_NO")
    private String claimNo;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_OF_LOSS")
    private Date dateOfLoss;

    @Column(name = "BIZ_DIRECTION")
    private Integer bizDirection;

    @Column(name = "BIZ_TRANS_TYPE")
    private Integer bizTransType;

    @Column(name = "CALC_FLAG")
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean calcFlag;

    public TRiAcctEstimateTrans() {
    }

    public Long getTransId() {
        return transId;
    }

    public void setTransId(Long transId) {
        this.transId = transId;
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
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

    public Long getContId() {
        return contId;
    }

    public void setContId(Long contId) {
        this.contId = contId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getSoaId() {
        return soaId;
    }

    public void setSoaId(Long soaId) {
        this.soaId = soaId;
    }

    public String getClaimNo() {
        return claimNo;
    }

    public void setClaimNo(String claimNo) {
        this.claimNo = claimNo;
    }

    public Date getDateOfLoss() {
        return dateOfLoss;
    }

    public void setDateOfLoss(Date dateOfLoss) {
        this.dateOfLoss = dateOfLoss;
    }

    public Integer getBizDirection() {
        return bizDirection;
    }

    public void setBizDirection(Integer bizDirection) {
        this.bizDirection = bizDirection;
    }

    public Integer getBizTransType() {
        return bizTransType;
    }

    public void setBizTransType(Integer bizTransType) {
        this.bizTransType = bizTransType;
    }

    public Boolean getCalcFlag() {
        return calcFlag;
    }

    public void setCalcFlag(Boolean calcFlag) {
        this.calcFlag = calcFlag;
    }

    @Override
    public Long getPrimaryKey() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setPrimaryKey(Long paramLong) {
        // TODO Auto-generated method stub

    }
}
