/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.arap.vo;

import java.io.Serializable;

/**
 * Date: Apr 10, 2016 1:54:41 PM
 * 
 * @author xiaoyu.yang
 */
public class TableColumn implements Serializable {

    private static final long serialVersionUID = -4954868848637660248L;

    public static final String VALUE_KEY = "cedentQ";

    private String text;

    private String value;

    public TableColumn() {

    }

    public TableColumn(String text, String value) {
        this.text = text;
        this.value = value;
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

}
