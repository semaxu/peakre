package com.ebao.ap99.arap.constant;

public class FinanceConsistants {
    public static final String GL_ENTRY_CODE_SETTLE_DIFF = "0101"; 
    public static final String GL_ENTRY_CODE_BALANCE = "0102"; 
    public static final String GL_ENTRY_CODE_GAIN_LOSS = "0103"; 
    public static final String GL_ENTRY_CODE_GAIN_LOSS_DIFF = "0105"; 
    public static final String GL_ENTRY_CODE_REVALUATION = "0104"; 
    
    //Write to finance module successfully
    public static final Integer WRITER_TO_FIN_SUCCESS = 1;
    //No any data was posted to finance module
    public static final Integer WRITER_TO_FIN_NO_DATA = 2;
    /**
     * The data was rejected by finance module considering corresponding contract was unsettled,
     * it means only the contract with special status (in force / cancelled) can be accepted
     */
    public static final Integer WRITER_TO_FIN_UNSETTLED_CONTRACT = 3;
}
