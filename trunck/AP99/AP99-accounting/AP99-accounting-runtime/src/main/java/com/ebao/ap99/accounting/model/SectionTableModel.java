/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.model;

import java.util.List;

/**
 * Date: Mar 27, 2016 8:47:15 PM
 * 
 * @author xiaoyu.yang
 */
public class SectionTableModel {

    private String sectionName;

    private Long sectionId;

    private List<FnItemVO> sectionTable;

    public SectionTableModel() {
        super();
    }

    public SectionTableModel(String sectionName, Long sectionId, List<FnItemVO> sectionTable) {
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

    public List<FnItemVO> getSectionTable() {
        return sectionTable;
    }

    public void setSectionTable(List<FnItemVO> sectionTable) {
        this.sectionTable = sectionTable;
    }

}
