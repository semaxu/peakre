/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Date: Jan 4, 2016 4:28:38 PM
 * 
 * @author xiaoyu.yang
 */
public class IBNRrecord implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6190922382319961357L;

    private String fnQuarter;

    private BigDecimal uy2013;

    private BigDecimal uy2014;

    private BigDecimal uy2015;

    private BigDecimal uy2016;

    private BigDecimal uy2017;

    private BigDecimal uy2018;

    private BigDecimal uy2019;

    private BigDecimal uy2020;

    public String getFnQuarter() {
        return fnQuarter;
    }

    public void setFnQuarter(String fnQuarter) {
        this.fnQuarter = fnQuarter;
    }

    public BigDecimal getUy2013() {
        return uy2013;
    }

    public void setUy2013(BigDecimal uy2013) {
        this.uy2013 = uy2013;
    }

    public BigDecimal getUy2014() {
        return uy2014;
    }

    public void setUy2014(BigDecimal uy2014) {
        this.uy2014 = uy2014;
    }

    public BigDecimal getUy2015() {
        return uy2015;
    }

    public void setUy2015(BigDecimal uy2015) {
        this.uy2015 = uy2015;
    }

    public BigDecimal getUy2016() {
        return uy2016;
    }

    public void setUy2016(BigDecimal uy2016) {
        this.uy2016 = uy2016;
    }

    public BigDecimal getUy2017() {
        return uy2017;
    }

    public void setUy2017(BigDecimal uy2017) {
        this.uy2017 = uy2017;
    }

    public BigDecimal getUy2018() {
        return uy2018;
    }

    public void setUy2018(BigDecimal uy2018) {
        this.uy2018 = uy2018;
    }

    public BigDecimal getUy2019() {
        return uy2019;
    }

    public void setUy2019(BigDecimal uy2019) {
        this.uy2019 = uy2019;
    }

    public BigDecimal getUy2020() {
        return uy2020;
    }

    public void setUy2020(BigDecimal uy2020) {
        this.uy2020 = uy2020;
    }

}
