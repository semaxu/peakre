/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.model;

import com.ebao.ap99.parent.model.QueryVO;

/**
 * Date: Mar 30, 2016 10:09:43 AM
 * 
 * @author xiaoyu.yang
 */
public class ActualizationInfoVO extends QueryVO {

    private Long contCompId;

    private String contractCode;

    private String contractName;

    private String brokerRef;

    private String cedentRef;

    private String subclass;

    private Integer uwYear;

    private Integer actualized;

    private String contractIds;

    private String broker;

    private String cedent;

    private boolean exceeding;

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

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getBrokerRef() {
        return brokerRef;
    }

    public void setBrokerRef(String brokerRef) {
        this.brokerRef = brokerRef;
    }

    public String getCedentRef() {
        return cedentRef;
    }

    public void setCedentRef(String cedentRef) {
        this.cedentRef = cedentRef;
    }

    public String getSubclass() {
        return subclass;
    }

    public void setSubclass(String subclass) {
        this.subclass = subclass;
    }

    public Integer getUwYear() {
        return uwYear;
    }

    public void setUwYear(Integer uwYear) {
        this.uwYear = uwYear;
    }

    public Integer getActualized() {
        return actualized;
    }

    public void setActualized(Integer actualized) {
        this.actualized = actualized;
    }

    public String getContractIds() {
        return contractIds;
    }

    public void setContractIds(String contractIds) {
        this.contractIds = contractIds;
    }

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public String getCedent() {
        return cedent;
    }

    public void setCedent(String cedent) {
        this.cedent = cedent;
    }

    public boolean isExceeding() {
        return exceeding;
    }

    public void setExceeding(boolean exceeding) {
        this.exceeding = exceeding;
    }

}
