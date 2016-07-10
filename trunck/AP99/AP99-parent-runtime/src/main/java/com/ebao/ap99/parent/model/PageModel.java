/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.parent.model;

import java.io.Serializable;
import java.util.List;

/**
 * Date: Feb 17, 2016 3:01:07 PM
 * 
 * @author xiaoyu.yang
 */
public class PageModel<T> implements Serializable {

    private static final long serialVersionUID = -6605015320250193500L;

    private int     pageIndex    = 1; // current pageIndex
    public int      totalPages   = 0; // total page num
    private int     pageSize;
    private int     totalRows    = 0; // num of total records
    private int     pageStartRow = 0; // start row of each page
    private int     pageEndRow   = 0; // end row of each page
    private List<T> list;

    /**
     * @param list
     * @param pageRecorders
     */
    public PageModel(List<T> list, int pageSize, int pageIndex) {
        init(list, pageSize, pageIndex);
    }

    /**
     * init page
     * 
     * @param list
     * @param pageRecorders
     */
    public void init(List<T> list, int pageSize, int pageIndex) {
        this.pageSize = pageSize;

        totalRows = list.size();

        if ((totalRows % pageSize) == 0) {
            totalPages = totalRows / pageSize;
        } else {
            totalPages = totalRows / pageSize + 1;
        }

        this.pageIndex = pageIndex;

        if (pageIndex * pageSize < totalRows) {//whether the page is last one
            pageEndRow = pageIndex * pageSize;
            pageStartRow = pageEndRow - pageSize;
        } else {
            pageEndRow = totalRows;
            pageStartRow = pageSize * (totalPages - 1);
        }

        List<T> objects = null;
        if (!list.isEmpty()) {
            objects = list.subList(pageStartRow, pageEndRow);
        }
        this.list = objects;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getPageStartRow() {
        return pageStartRow;
    }

    public void setPageStartRow(int pageStartRow) {
        this.pageStartRow = pageStartRow;
    }

    public int getPageEndRow() {
        return pageEndRow;
    }

    public void setPageEndRow(int pageEndRow) {
        this.pageEndRow = pageEndRow;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
