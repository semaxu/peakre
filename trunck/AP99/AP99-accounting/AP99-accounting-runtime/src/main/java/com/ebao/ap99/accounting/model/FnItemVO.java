/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.model;

import java.math.BigDecimal;

/**
 * Date: Mar 27, 2016 5:18:56 PM
 * 
 * @author xiaoyu.yang
 */
public class FnItemVO {

    private String item;

    private BigDecimal cedentQ1;

    private BigDecimal cedentQ2;

    private BigDecimal cedentQ3;

    private BigDecimal cedentQ4;

    private BigDecimal cedentQ5;

    private BigDecimal cedentQ6;

    private BigDecimal cedentQ7;

    private BigDecimal cedentQ8;

    private BigDecimal total;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public BigDecimal getCedentQ1() {
        return cedentQ1;
    }

    public void setCedentQ1(BigDecimal cedentQ1) {
        this.cedentQ1 = cedentQ1;
    }

    public BigDecimal getCedentQ2() {
        return cedentQ2;
    }

    public void setCedentQ2(BigDecimal cedentQ2) {
        this.cedentQ2 = cedentQ2;
    }

    public BigDecimal getCedentQ3() {
        return cedentQ3;
    }

    public void setCedentQ3(BigDecimal cedentQ3) {
        this.cedentQ3 = cedentQ3;
    }

    public BigDecimal getCedentQ4() {
        return cedentQ4;
    }

    public void setCedentQ4(BigDecimal cedentQ4) {
        this.cedentQ4 = cedentQ4;
    }

    public BigDecimal getCedentQ5() {
        return cedentQ5;
    }

    public void setCedentQ5(BigDecimal cedentQ5) {
        this.cedentQ5 = cedentQ5;
    }

    public BigDecimal getCedentQ6() {
        return cedentQ6;
    }

    public void setCedentQ6(BigDecimal cedentQ6) {
        this.cedentQ6 = cedentQ6;
    }

    public BigDecimal getCedentQ7() {
        return cedentQ7;
    }

    public void setCedentQ7(BigDecimal cedentQ7) {
        this.cedentQ7 = cedentQ7;
    }

    public BigDecimal getCedentQ8() {
        return cedentQ8;
    }

    public void setCedentQ8(BigDecimal cedentQ8) {
        this.cedentQ8 = cedentQ8;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
