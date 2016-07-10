package com.ebao.ap99.file.model;

import java.util.Map;

public class ItemVO<T> {

    private boolean resultFlag;
    private String  rowNo;
    private String  msg;
    private T       bizVO;
    private Long    businessId;
    private Map     param;

    public Map getParam() {
        return param;
    }

    public void setParam(Map param) {
        this.param = param;
    }

    public boolean isResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(boolean resultFlag) {
        this.resultFlag = resultFlag;
    }

    public String getRowNo() {
        return rowNo;
    }

    public void setRowNo(String rowNo) {
        this.rowNo = rowNo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getBizVO() {
        return bizVO;
    }

    public void setBizVO(T bizVO) {
        this.bizVO = bizVO;
    }

}
