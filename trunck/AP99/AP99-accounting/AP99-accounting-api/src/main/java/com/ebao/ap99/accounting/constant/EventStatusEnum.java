/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.constant;

/**
 * Date: Mar 11, 2016 3:24:01 PM
 * 
 * @author xiaoyu.yang
 */
public enum EventStatusEnum {

    Pending(1),
    Done(2);

    private int value;

    public static EventStatusEnum getEventStatusEnumByValue(int value) {
        for (EventStatusEnum temp : EventStatusEnum.values()) {
            if (temp.getValue() == value) {
                return temp;
            }
        }
        return null;
    }

    private EventStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
