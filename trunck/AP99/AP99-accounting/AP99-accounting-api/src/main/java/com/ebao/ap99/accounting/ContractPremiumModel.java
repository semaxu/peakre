package com.ebao.ap99.accounting;

import java.util.Date;
import java.util.List;

public class ContractPremiumModel {
    private Long                      contractId;
    private Integer                   nature;         //Proportional / Non-Proportional
    private Integer                   type;           //assumed/retrocession
    private Date                      periodStart;
    private Date                      periodEnd;
    private List<SectionPremiumModel> sectionPremList;

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Integer getNature() {
        return nature;
    }

    public void setNature(Integer nature) {
        this.nature = nature;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(Date periodStart) {
        this.periodStart = periodStart;
    }

    public Date getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(Date periodEnd) {
        this.periodEnd = periodEnd;
    }

    public List<SectionPremiumModel> getSectionPremList() {
        return sectionPremList;
    }

    public void setSectionPremList(List<SectionPremiumModel> sectionPremList) {
        this.sectionPremList = sectionPremList;
    }

}
