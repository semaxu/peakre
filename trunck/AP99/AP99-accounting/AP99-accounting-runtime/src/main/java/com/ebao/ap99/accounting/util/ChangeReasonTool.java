/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.util;

/**
 * Date: May 25, 2016 7:54:44 PM
 * 
 * @author xiaoyu.yang
 */
public class ChangeReasonTool {

    private ChangeReasonTool() {

    }

    private static final ThreadLocal<String> changeReason = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return "Forecast Recalculation";
        }
    };

    public static ThreadLocal<String> getChangeReason() {
        return changeReason;
    }

}
