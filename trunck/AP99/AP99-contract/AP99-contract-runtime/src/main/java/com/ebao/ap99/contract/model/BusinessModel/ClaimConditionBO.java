package com.ebao.ap99.contract.model.BusinessModel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import com.ebao.ap99.contract.entity.TRiContClaim;
import com.ebao.ap99.parent.DataTypeConvertor;

@XmlAccessorType(XmlAccessType.FIELD)
public class ClaimConditionBO extends TRiContClaim {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @XmlTransient
    private List<String>      pricesIndexList  = new ArrayList<String>();
    @XmlTransient
    private List<String>      wetherIndexList  = new ArrayList<String>();

    public List<String> getPricesIndexList() {
        return DataTypeConvertor.transStringToSelectTree(getPricesIndex());
    }

    public void setPricesIndexList(List<String> pricesIndexList) {
        this.setPricesIndex(DataTypeConvertor.parseSelectTreeToString(pricesIndexList));
        this.pricesIndexList = pricesIndexList;
    }

    public List<String> getWetherIndexList() {
        return DataTypeConvertor.transStringToSelectTree(getWetherIndex());
    }

    public void setWetherIndexList(List<String> wetherIndexList) {
        this.setWetherIndex(DataTypeConvertor.parseSelectTreeToString(wetherIndexList));
        this.wetherIndexList = wetherIndexList;
    }

}
