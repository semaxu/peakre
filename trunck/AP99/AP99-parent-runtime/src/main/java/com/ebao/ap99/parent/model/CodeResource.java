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
 * Date: Jan 13, 2016 7:26:52 PM
 * 
 * @author xiaoyu.yang
 */
public class CodeResource implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7563757637601638493L;

    private String code;

    private String name;

    public CodeResource() {
    }

    public CodeResource(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public CodeResource(Object code, String name) {
        this.code = code.toString();
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
