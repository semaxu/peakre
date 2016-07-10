/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.UI.model;

import java.util.Date;

import com.ebao.ap99.accounting.entity.TRiAcctEstimateItemHis;

/**
 * Date: Mar 21, 2016 10:22:42 AM
 * 
 * @author xiaoyu.yang
 */
public class TRiAcctEstimateItemHisVO extends TRiAcctEstimateItemHis {

    private static final long serialVersionUID = -815486280977327745L;

    private Long insertBy;

    private Date insertTime;

    public Long getInsertBy() {
        return insertBy;
    }

    public void setInsertBy(Long insertBy) {
        this.insertBy = insertBy;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

}
