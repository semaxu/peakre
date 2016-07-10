package com.ebao.ap99.partner.entity;

public class MessageVO {
    private String    businessId;
    private boolean checkFlag;
    private String  errorType;
    private String  msg;
    public  String getBusinessId() {
        return businessId;
    }
    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
    public boolean isCheckFlag() {
        return checkFlag;
    }
    public void setCheckFlag(boolean checkFlag) {
        this.checkFlag = checkFlag;
    }
    public String getErrorType() {
        return errorType;
    }
    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    
    
}
