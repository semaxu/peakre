/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ebao.ap99.parent.model.TableColumn;

/**
 * Date: Mar 3, 2016 9:59:23 AM
 * 
 * @author xiaoyu.yang
 */
public class UwViewVO implements Serializable {

    private static final long serialVersionUID = 5424367988072223605L;

    private String            mainCurrency;
    private String            currentFNQuarter;
    private Date              date;
    private boolean           cleanCut;
    private List<TableColumn> tableColumns;
    private List<EntryItemVO> forecastTable;
    private List<EntryItemVO> estimationTable;
    private List<EntryItemVO> reversalTable;
    private List<EntryItemVO> actualTable;

    public String getMainCurrency() {
        return mainCurrency;
    }

    public void setMainCurrency(String mainCurrency) {
        this.mainCurrency = mainCurrency;
    }

    public String getCurrentFNQuarter() {
        return currentFNQuarter;
    }

    public void setCurrentFNQuarter(String currentFNQuarter) {
        this.currentFNQuarter = currentFNQuarter;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isCleanCut() {
        return cleanCut;
    }

    public void setCleanCut(boolean cleanCut) {
        this.cleanCut = cleanCut;
    }

    public List<TableColumn> getTableColumns() {
        return tableColumns;
    }

    public void setTableColumns(List<TableColumn> tableColumns) {
        this.tableColumns = tableColumns;
    }

    public List<EntryItemVO> getForecastTable() {
        return forecastTable;
    }

    public void setForecastTable(List<EntryItemVO> forecastTable) {
        this.forecastTable = forecastTable;
    }

    public List<EntryItemVO> getEstimationTable() {
        return estimationTable;
    }

    public void setEstimationTable(List<EntryItemVO> estimationTable) {
        this.estimationTable = estimationTable;
    }

    public List<EntryItemVO> getReversalTable() {
        return reversalTable;
    }

    public void setReversalTable(List<EntryItemVO> reversalTable) {
        this.reversalTable = reversalTable;
    }

    public List<EntryItemVO> getActualTable() {
        return actualTable;
    }

    public void setActualTable(List<EntryItemVO> actualTable) {
        this.actualTable = actualTable;
    }

}
