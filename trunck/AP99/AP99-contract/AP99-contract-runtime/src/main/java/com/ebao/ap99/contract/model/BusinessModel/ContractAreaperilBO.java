package com.ebao.ap99.contract.model.BusinessModel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import com.ebao.ap99.contract.entity.TRiContractAreaperil;
import com.ebao.ap99.parent.DataTypeConvertor;

@XmlAccessorType(XmlAccessType.FIELD)
public class ContractAreaperilBO extends TRiContractAreaperil {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @XmlTransient
    private List<String>      coveredList      = new ArrayList<String>();
    @XmlTransient
    private List<String>      perilList        = new ArrayList<String>();

    public List<String> getCoveredList() {
        return DataTypeConvertor.transStringToSelectTree(getCovered());
    }

    public void setCoveredList(List<String> coveredList) {
        this.setCovered(DataTypeConvertor.parseSelectTreeToString(coveredList));
        this.coveredList = coveredList;
    }

    public List<String> getPerilList() {
        return DataTypeConvertor.transStringToSelectTree(getPeril());
    }

    public void setPerilList(List<String> perilList) {
        this.setPeril(DataTypeConvertor.parseSelectTreeToString(perilList));
        this.perilList = perilList;
    }

}
