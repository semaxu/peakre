/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.arap.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Date: Apr 10, 2016 1:32:53 PM
 * 
 * @author xiaoyu.yang
 */
public class FnViewVO implements Serializable {

    private static final long serialVersionUID = -5292664893851885451L;

    private List<TableColumn>       tableColumns;
    private List<FinancialItemVO>   contractTable;
    private List<SectionTableModel> sectionTables;

    public List<TableColumn> getTableColumns() {
        return tableColumns;
    }

    public void setTableColumns(List<TableColumn> tableColumns) {
        this.tableColumns = tableColumns;
    }

    public List<FinancialItemVO> getContractTable() {
        return contractTable;
    }

    public void setContractTable(List<FinancialItemVO> contractTable) {
        this.contractTable = contractTable;
    }

    public List<SectionTableModel> getSectionTables() {
        return sectionTables;
    }

    public void setSectionTables(List<SectionTableModel> sectionTables) {
        this.sectionTables = sectionTables;
    }
}
