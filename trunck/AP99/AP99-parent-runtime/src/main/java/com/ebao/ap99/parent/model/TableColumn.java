/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.parent.model;

import java.io.Serializable;

/**
 * To display dynamic Table with uncertain columns<br/>
 * Date: Mar 3, 2016 11:24:29 AM
 * 
 * @author xiaoyu.yang
 */
public class TableColumn implements Serializable {

    private static final long serialVersionUID = -4954868848637660248L;

    public static final String VALUE_KEY = "cedentQ";

    private String text;

    private String value;

    private int status;

    public TableColumn() {

    }

    public TableColumn(String text, String value, int status) {
        this.text = text;
        this.value = value;
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
