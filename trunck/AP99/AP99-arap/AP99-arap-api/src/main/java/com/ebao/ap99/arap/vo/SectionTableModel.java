/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.arap.vo;

import java.util.List;

/**
 * Date: Apr 10, 2016 1:34:21 PM
 * 
 * @author xiaoyu.yang
 */
public class SectionTableModel {

    private String sectionName;

    private Long sectionId;

    private List<FinancialItemVO> sectionTable;

    public SectionTableModel() {
        super();
    }

    public SectionTableModel(String sectionName, Long sectionId, List<FinancialItemVO> sectionTable) {
        super();
        this.sectionName = sectionName;
        this.sectionId = sectionId;
        this.sectionTable = sectionTable;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public List<FinancialItemVO> getSectionTable() {
        return sectionTable;
    }

    public void setSectionTable(List<FinancialItemVO> sectionTable) {
        this.sectionTable = sectionTable;
    }
}
