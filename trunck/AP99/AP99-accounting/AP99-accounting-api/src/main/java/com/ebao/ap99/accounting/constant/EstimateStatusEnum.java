/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.constant;

/**
 * Date: Jan 14, 2016 4:48:00 PM
 * 
 * @author xiaoyu.yang
 */
public enum EstimateStatusEnum {

    BaseForecast(0),
    Forecast(1),
    Estimating(2),
    Estimated(3),
    Reversed(4),
    Actual(5),
    Upr(6);

    private int value;

    public static EstimateStatusEnum getEstimateStatusEnumByValue(int value) {
        for (EstimateStatusEnum temp : EstimateStatusEnum.values()) {
            if (temp.getValue() == value) {
                return temp;
            }
        }
        return null;
    }

    private EstimateStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
