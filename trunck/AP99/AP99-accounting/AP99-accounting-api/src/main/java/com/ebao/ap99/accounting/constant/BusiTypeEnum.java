/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.constant;

/**
 * Date: Feb 25, 2016 10:12:32 AM
 * 
 * @author xiaoyu.yang
 */
public enum BusiTypeEnum {

    ClaimReserve(1),
    ClaimSettlement(2),
    Statement(3),
    IBNRUpload(4),
    Estimation(5),
    Revaluation(6),
    Collection(7),
    Payment(8),
    InternalOffset(9),
    TransactionReversal(10);

    private int value;

    public static BusiTypeEnum getBusiTypeEnumByValue(int value) {
        for (BusiTypeEnum temp : BusiTypeEnum.values()) {
            if (temp.getValue() == value) {
                return temp;
            }
        }
        return null;
    }

    private BusiTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
