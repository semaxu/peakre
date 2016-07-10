package com.ebao.ap99.contract.model.BusinessModel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import com.ebao.ap99.contract.entity.TRiContractMoreInfo;
import com.ebao.ap99.parent.DataTypeConvertor;

@XmlAccessorType(XmlAccessType.FIELD)
public class ContractMoreInfoBO extends TRiContractMoreInfo {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @XmlTransient
    private List<String>      coBList          = new ArrayList<String>();
    @XmlTransient
    private List<String>      loBList          = new ArrayList<String>();
    @XmlTransient
    private List<String>      currencyList     = new ArrayList<String>();
    @XmlTransient
    private List<String>      uwAreaList       = new ArrayList<String>();
    @XmlTransient
    private List<String>      coveredAreaList  = new ArrayList<String>();

    public List<String> getCoBList() {
        return DataTypeConvertor.transStringToSelectTree(this.getCoB());
    }

    public void setCoBList(List<String> coBList) {
        this.setCoB(DataTypeConvertor.parseSelectTreeToString(coBList));
        this.coBList = coBList;
    }

    public List<String> getLoBList() {
        return DataTypeConvertor.transStringToSelectTree(this.getLoB());
    }

    public void setLoBList(List<String> loBList) {
        this.setLoB(DataTypeConvertor.parseSelectTreeToString(loBList));
        this.loBList = loBList;
    }

    public List<String> getCurrencyList() {
        return DataTypeConvertor.transStringToSelectTree(this.getCurrency());
    }

    public void setCurrencyList(List<String> currencyList) {
        this.setCurrency(DataTypeConvertor.parseSelectTreeToString(currencyList));
        this.currencyList = currencyList;
    }

    public List<String> getUwAreaList() {
        return DataTypeConvertor.transStringToSelectTree(this.getUwArea());
    }

    public void setUwAreaList(List<String> uwAreaList) {
        this.setUwArea(DataTypeConvertor.parseSelectTreeToString(uwAreaList));
        this.uwAreaList = uwAreaList;
    }

    public List<String> getCoveredAreaList() {
        return DataTypeConvertor.transStringToSelectTree(this.getCoveredArea());
    }

    public void setCoveredAreaList(List<String> coveredAreaList) {
        this.setCoveredArea(DataTypeConvertor.parseSelectTreeToString(coveredAreaList));
        this.coveredAreaList = coveredAreaList;
    }
}
