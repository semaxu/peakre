/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.constant;

/**
 * Date: Mar 9, 2016 2:38:43 PM
 * 
 * @author xiaoyu.yang
 */
public enum FeeBusiTypeEnum {

    EstimateGenerate(1),
    EstimateReverse(2);

    private int value;

    public static FeeBusiTypeEnum getFeeBusiTypeEnumByValue(int value) {
        for (FeeBusiTypeEnum temp : FeeBusiTypeEnum.values()) {
            if (temp.getValue() == value) {
                return temp;
            }
        }
        return null;
    }

    private FeeBusiTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
