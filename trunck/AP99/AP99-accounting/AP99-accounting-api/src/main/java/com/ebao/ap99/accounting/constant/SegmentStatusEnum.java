/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.constant;

/**
 * Date: Feb 25, 2016 10:35:46 AM
 * 
 * @author xiaoyu.yang
 */
public enum SegmentStatusEnum {

    Invalid(0),
    Valid(1);

    private int value;

    public static SegmentStatusEnum getSegmentStatusEnumByValue(int value) {
        for (SegmentStatusEnum temp : SegmentStatusEnum.values()) {
            if (temp.getValue() == value) {
                return temp;
            }
        }
        return null;
    }

    private SegmentStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
