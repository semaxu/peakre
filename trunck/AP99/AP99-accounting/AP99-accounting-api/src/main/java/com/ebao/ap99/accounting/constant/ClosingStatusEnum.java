/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.constant;

/**
 * Date: Feb 25, 2016 10:22:54 AM
 * 
 * @author xiaoyu.yang
 */
public enum ClosingStatusEnum {

    Open(1),
    Closing(2),
    Closed(3);

    private int value;

    public static ClosingStatusEnum getClosingStatusEnumByValue(int value) {
        for (ClosingStatusEnum temp : ClosingStatusEnum.values()) {
            if (temp.getValue() == value) {
                return temp;
            }
        }
        return null;
    }

    private ClosingStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
