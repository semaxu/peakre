/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.constant;

/**
 * Date: Mar 10, 2016 5:34:37 PM
 * 
 * @author xiaoyu.yang
 */
public enum EventTypeEnum {

    EPI(1),
    SOA(2),
    WITHDRAW(3);

    private Integer value;

    public static EventTypeEnum getEventTypeEnumByValue(Integer value) {
        for (EventTypeEnum temp : EventTypeEnum.values()) {
            if (temp.getValue().equals(value)) {
                return temp;
            }
        }
        return null;
    }

    private EventTypeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
