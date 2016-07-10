/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.constant;

/**
 * Date: Feb 25, 2016 2:30:30 PM
 * 
 * @author xiaoyu.yang
 */
public enum EstimateEntryEnum {

    GWP("1000"),
    Commission("2000"),
    Brokerage("2090"),
    TaxOthers("2071"),
    LossPaid("3000"),
    LAEPaid("3030"),
    CashCallPayment("3040"),
    UPR_Opening("4111"),
    UPR_Closing("4112"),
    DAC_Opening("4115"),
    DAC_Closing("4116"),
    EP_Opening("EP01"),
    EP_Closing("EP02"),
    LossReserve_Opening("4121"),
    LossReserve_Closing("4122"),
    LAEReserve_Opening("4141"),
    LAEReserve_Closing("4142");

    private String entryCode;

    public static EstimateEntryEnum getEstimateEntryEnumByEntryCode(String entryCode) {
        for (EstimateEntryEnum temp : EstimateEntryEnum.values()) {
            if (temp.getEntryCode().equals(entryCode)) {
                return temp;
            }
        }
        return null;
    }

    private EstimateEntryEnum(String entryCode) {
        this.entryCode = entryCode;
    }

    public String getEntryCode() {
        return entryCode;
    }

    public boolean needGenerateE() {
        switch (this) {
            case GWP:
                return true;
            case Commission:
                return true;
            case Brokerage:
                return true;
            case TaxOthers:
                return true;
            case UPR_Opening:
                return true;
            case UPR_Closing:
                return true;
            case DAC_Opening:
                return true;
            case DAC_Closing:
                return true;
            case EP_Opening:
                return false;
            case EP_Closing:
                return false;
            case LossReserve_Opening:
                return true;
            case LossReserve_Closing:
                return true;
            default:
                return false;
        }
    }

    public boolean needReverseE() {
        switch (this) {
            case GWP:
                return true;
            case Commission:
                return true;
            case Brokerage:
                return true;
            case TaxOthers:
                return true;
            case UPR_Opening:
                return false;
            case UPR_Closing:
                return false;
            case DAC_Opening:
                return false;
            case DAC_Closing:
                return false;
            case EP_Opening:
                return false;
            case EP_Closing:
                return false;
            case LossReserve_Opening:
                return true;
            case LossReserve_Closing:
                return true;
            default:
                return false;
        }
    }
}
