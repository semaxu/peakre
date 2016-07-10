/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.util;

/**
 * Date: Jan 18, 2016 6:08:10 PM
 * 
 * @author xiaoyu.yang
 */
public class AccountingCst {

    public static final String RESTFUL_RESULT         = "result";
    public static final String RESTFUL_RESULT_FAIL    = "fail";
    public static final String RESTFUL_RESULT_SUCCESS = "success";

    public static final String SECTION_MAIN_CURRENCY = "DEFAULT";
    public static final String SECTION_CURRENCY_USD  = "USD";

    public static final String CHANGE_REASON_01 = "Contract In-force";
    public static final String CHANGE_REASON_02 = "FY2015Q1 cut-off batch job";
    public static final String CHANGE_REASON_03 = "Estimation reversal of UY2015Q1";
    public static final String CHANGE_REASON_04 = "Post estimation of UY2015Q1";
    public static final String CHANGE_REASON_05 = "Estimation adjustment of UY2015Q1";
    public static final String CHANGE_REASON_06 = "Statement text";
    public static final String CHANGE_REASON_07 = "Withdrawal of 'statement text'";
    public static final String CHANGE_REASON_08 = "Pricing estimate adjustment at 'Date'";

    public static final String REINSTATEMENT_PREMIUM           = "1020";
    public static final String TAX_OTHERS                      = "2071";
    public static final String OTHER_EXPENSES                  = "2072";
    public static final String EXPENSE                         = "3030";
    public static final String CASH_CALL_PAYMENT               = "3040";
    public static final String LOSS_RESERVE_OPENING            = "4121";
    public static final String LOSS_RESERVE_CLOSING            = "4122";
    public static final String ADDITIONAL_LOSS_RESERVE_OPENING = "4125";
    public static final String ADDITIONAL_LOSS_RESERVE_CLOSING = "4126";
    public static final String LAE_RESERVE_OPENING             = "4141";
    public static final String LAE_RESERVE_CLOSING             = "4142";
}
