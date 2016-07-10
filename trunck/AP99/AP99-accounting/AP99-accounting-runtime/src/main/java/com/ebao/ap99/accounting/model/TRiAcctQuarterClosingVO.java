/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.model;

/**
 * Date: Jan 12, 2016 10:13:37 AM
 * 
 * @author xiaoyu.yang
 */
public class TRiAcctQuarterClosingVO {

    private Long quarterId;

    private String quarter;

    private Integer closingStatus;

    private String closingDate;

    private String startDate;

    private String closedDate;

    //    private Date closingDate;
    //
    //    private Date startDate;
    //
    //    private Date closedDate;

    public Long getQuarterId() {
        return quarterId;
    }

    public void setQuarterId(Long quarterId) {
        this.quarterId = quarterId;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public Integer getClosingStatus() {
        return closingStatus;
    }

    public void setClosingStatus(Integer closingStatus) {
        this.closingStatus = closingStatus;
    }

    //    public Date getClosingDate() {
    //        return closingDate;
    //    }
    //
    //    public void setClosingDate(Date closingDate) {
    //        this.closingDate = closingDate;
    //    }
    //
    //    public Date getStartDate() {
    //        return startDate;
    //    }
    //
    //    public void setStartDate(Date startDate) {
    //        this.startDate = startDate;
    //    }
    //
    //    public Date getClosedDate() {
    //        return closedDate;
    //    }
    //
    //    public void setClosedDate(Date closedDate) {
    //        this.closedDate = closedDate;
    //    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(String closedDate) {
        this.closedDate = closedDate;
    }

}
