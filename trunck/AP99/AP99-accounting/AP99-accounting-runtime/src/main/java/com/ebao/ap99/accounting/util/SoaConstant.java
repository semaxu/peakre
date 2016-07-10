package com.ebao.ap99.accounting.util;

import java.util.HashMap;
import java.util.Map;

public class SoaConstant {

    // SoaStatus

    public static String DATAINPUT = "1";

    public static String RELEASED = "2";

    public static String WITHDRAWAL = "3";

    public static String CANCEL = "4";

    public static String PARKING = "5";

    public static String PREMIUM_PROP = "1000";

    public static String COMMISSION = "2000";

    public static String TAX_OTHERS = "2071";

    public static String BROKERAGE = "2090";

    public static String LOSS_PAID = "3000";
    
    public static String LAE_PAID = "3030";
    
    public static String CASH_CALL_PAYMENT = "3040";
    
    public static String HKAS_LAE_RESERVE = "4142";
    
    public static String OVERRIDING_COMMISSION = "2040";
    
    public static String ACQUISITION_COST = "2050";
    
    public static String ADMINISTRACTIVE_EXPENSES = "2060";
    
    public static String FIRE_BRIGADE_TAX = "2070";
    
    public static String OTHER_EXPENSES = "2070";
    
    public static String INTERESTS_ON_ACCOUNTS = "2075";

    public static String LOSS_RESERVE_OPENING = "4021";

    public static String HKAS_LOSS_RESERVE_CLOSING = "4122";

    public static String XOL_PREMIUM = "1010";

    public static String XOL_PREMIUM_ADJUSTMENT = "1011";

    public static String REINSTATEMNET_PREMIUM = "1020";
    
    public static String ESTIMATE_ENTRYCODE = "('1000'):('2000'):('2071'):('2090'):('4121','4122'):('4111','4112'):('4115','4116')";

    public static int HKAS_Loss_Reserve_Opening            = 4121;
    public static int HKAS_Loss_Reserve_Closing            = 4122;
    public static int HKAS_Additional_Loss_Reserve_Opening = 4125;
    public static int HKAS_Additional_Loss_Reserve_Closing = 4126;
    public static int HKAS_LAE_Reserve_Opening             = 4141;
    public static int HKAS_LAE_Reserve_Closing             = 4142;
    public static int Loss_Reserve_Ptf_In                  = 5040;
    public static int Loss_Reserve_Ptf_Out                 = 5041;
    public static int Premium_Reserve_Withheld             = 6111;
    public static int Premium_Reserve_Release              = 6112;

    public static String HKAS_Loss_Reserve_Opening_Code            = "4121";
    public static String HKAS_Loss_Reserve_Closing_Code            = "4122";
    public static String HKAS_Additional_Loss_Reserve_Opening_Code = "4125";
    public static String HKAS_Additional_Loss_Reserve_Closing_Code = "4126";
    public static String HKAS_LAE_Reserve_Opening_Code             = "4141";
    public static String HKAS_LAE_Reserve_Closing_Code             = "4142";

    public static String CoB_1 = "Fire, FLEXA";
    public static String CoB_2 = "Property All Risks";
    public static String CoB_3 = "Business Interruption";
    public static String CoB_4 = "Natural Catastrophe";
    public static String CoB_5 = "Contractors'/Erection All Risks";
    public static String CoB_6 = "General Third Party Liability";
    public static String CoB_7 = "Products Liability";
    public static String CoB_8 = "Workmen's Compensation/Employers' Liability";

    public static String CEDENT_COUNTRY_1 = "China";
    public static String CEDENT_COUNTRY_2 = "United States";
    public static String CEDENT_COUNTRY_3 = "United Kingdom";

    public static String SHARE_TYPE_1 = "QS";
    public static String SHARE_TYPE_2 = "Surplus";
    public static String SHARE_TYPE_3 = "XOL";
    
    public static long CODE_TABLE_CONTRACT_COUNTRYE = 800019;
    public static long CODE_TABLE_CONT_SHARE_TYPE = 810005;
    public static long CODE_TABLE_CONTRACT_SUBCOB_TYPE = 810029;

    public static String RESERVE_ENTRY_CODE = HKAS_Loss_Reserve_Closing + "," + HKAS_Additional_Loss_Reserve_Closing
            + "," + HKAS_LAE_Reserve_Closing + "," + Loss_Reserve_Ptf_In + "," + Loss_Reserve_Ptf_Out + ","
            + Premium_Reserve_Withheld + "," + Premium_Reserve_Release;
    
    public static String RESERVE_CLOSING_ENTRY_CODE = HKAS_Loss_Reserve_Closing + "," + HKAS_Additional_Loss_Reserve_Closing
            + "," + HKAS_LAE_Reserve_Closing;
    
    public static String RESERVE_OPENING_ENTRY_CODE = HKAS_Loss_Reserve_Opening + "," + HKAS_Additional_Loss_Reserve_Opening
            + "," + HKAS_LAE_Reserve_Opening;
    
    public static String RESERVE_ALL_ENTRY_CODE = HKAS_Loss_Reserve_Closing + "," + HKAS_Additional_Loss_Reserve_Closing
            + "," + HKAS_LAE_Reserve_Closing+","+HKAS_Loss_Reserve_Opening + "," + HKAS_Additional_Loss_Reserve_Opening
            + "," + HKAS_LAE_Reserve_Opening;
    
    
    
    public static String REVALUATE_RESERVE_ENTRY_CODE = HKAS_Loss_Reserve_Closing +","+HKAS_Loss_Reserve_Opening+"," + HKAS_Additional_Loss_Reserve_Closing+"," + HKAS_Additional_Loss_Reserve_Opening
            + "," + HKAS_LAE_Reserve_Closing + "," + HKAS_LAE_Reserve_Opening + "," + Loss_Reserve_Ptf_In + "," + Loss_Reserve_Ptf_Out + ","
            + Premium_Reserve_Withheld + "," + Premium_Reserve_Release;

    public static String CLOSING_ENTRY_CODE = HKAS_Loss_Reserve_Closing + "," + HKAS_Additional_Loss_Reserve_Closing
            + "," + HKAS_LAE_Reserve_Closing;

    public static Map<String, String> getInitOpeningEntryCodeMatchMap() throws Exception {
        Map<String, String> openingEntryCodeMap = new HashMap<String, String>();
        openingEntryCodeMap.put(HKAS_Loss_Reserve_Closing_Code, HKAS_Loss_Reserve_Opening_Code);
        openingEntryCodeMap.put(HKAS_Additional_Loss_Reserve_Closing_Code, HKAS_Additional_Loss_Reserve_Opening_Code);
        openingEntryCodeMap.put(HKAS_LAE_Reserve_Closing_Code, HKAS_LAE_Reserve_Opening_Code);
        return openingEntryCodeMap;
    }

    public static Map<String, String> getInitCoBMatchMap() throws Exception {
        Map<String, String> CoBMap = new HashMap<String, String>();
        CoBMap.put("1", CoB_1);
        CoBMap.put("2", CoB_2);
        CoBMap.put("3", CoB_3);
        CoBMap.put("4", CoB_4);
        CoBMap.put("5", CoB_5);
        CoBMap.put("6", CoB_6);
        CoBMap.put("7", CoB_7);
        CoBMap.put("8", CoB_8);
        return CoBMap;
    }

    public static Map<String, String> getInitShareTypeMatchMap() throws Exception {
        Map<String, String> shareTypeMap = new HashMap<String, String>();
        shareTypeMap.put("1", SHARE_TYPE_1);
        shareTypeMap.put("2", SHARE_TYPE_2);
        shareTypeMap.put("3", SHARE_TYPE_3);
        return shareTypeMap;
    }

    public static Map<String, String> getInitCedentCountryMatchMap() throws Exception {
        Map<String, String> cedentCountryMap = new HashMap<String, String>();
        cedentCountryMap.put("1", CEDENT_COUNTRY_1);
        cedentCountryMap.put("2", CEDENT_COUNTRY_2);
        cedentCountryMap.put("3", CEDENT_COUNTRY_3);
        return cedentCountryMap;
    }

}
