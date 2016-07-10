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
 * Date: Mar 3, 2016 11:47:35 AM
 * 
 * @author xiaoyu.yang
 */
public class EntryItemVO implements Comparable<EntryItemVO> {

    private String item;

    private BigDecimal cedentQ1;

    private BigDecimal cedentQ2;

    private BigDecimal cedentQ3;

    private BigDecimal cedentQ4;

    private BigDecimal cedentQ5;

    private BigDecimal cedentQ6;

    private BigDecimal cedentQ7;

    private BigDecimal cedentQ8;

    private BigDecimal cedentQ9;

    private BigDecimal cedentQ10;

    private BigDecimal cedentQ11;

    private BigDecimal cedentQ12;

    private BigDecimal cedentQ13;

    private BigDecimal cedentQ14;

    private BigDecimal cedentQ15;

    private BigDecimal cedentQ16;

    private BigDecimal cedentQ17;

    private BigDecimal cedentQ18;

    private BigDecimal cedentQ19;

    private BigDecimal cedentQ20;

    private BigDecimal exchangeRate = new BigDecimal("1");

    private BigDecimal total = BigDecimal.ZERO;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public BigDecimal getCedentQ1() {
        if (cedentQ1 != null) {
            return cedentQ1.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP);
        } else {
            return null;
        }
    }

    public void setCedentQ1(BigDecimal cedentQ1) {
        this.cedentQ1 = cedentQ1;
    }

    public BigDecimal getCedentQ2() {
        if (cedentQ2 != null) {
            return cedentQ2.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP);
        } else {
            return null;
        }
    }

    public void setCedentQ2(BigDecimal cedentQ2) {
        this.cedentQ2 = cedentQ2;
    }

    public BigDecimal getCedentQ3() {
        if (cedentQ3 != null) {
            return cedentQ3.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP);
        } else {
            return null;
        }
    }

    public void setCedentQ3(BigDecimal cedentQ3) {
        this.cedentQ3 = cedentQ3;
    }

    public BigDecimal getCedentQ4() {
        if (cedentQ4 != null) {
            return cedentQ4.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP);
        } else {
            return null;
        }
    }

    public void setCedentQ4(BigDecimal cedentQ4) {
        this.cedentQ4 = cedentQ4;
    }

    public BigDecimal getCedentQ5() {
        if (cedentQ5 != null) {
            return cedentQ5.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP);
        } else {
            return null;
        }
    }

    public void setCedentQ5(BigDecimal cedentQ5) {
        this.cedentQ5 = cedentQ5;
    }

    public BigDecimal getCedentQ6() {
        if (cedentQ6 != null) {
            return cedentQ6.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP);
        } else {
            return null;
        }
    }

    public void setCedentQ6(BigDecimal cedentQ6) {
        this.cedentQ6 = cedentQ6;
    }

    public BigDecimal getCedentQ7() {
        if (cedentQ7 != null) {
            return cedentQ7.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP);
        } else {
            return null;
        }
    }

    public void setCedentQ7(BigDecimal cedentQ7) {
        this.cedentQ7 = cedentQ7;
    }

    public BigDecimal getCedentQ8() {
        if (cedentQ8 != null) {
            return cedentQ8.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP);
        } else {
            return null;
        }
    }

    public void setCedentQ8(BigDecimal cedentQ8) {
        this.cedentQ8 = cedentQ8;
    }

    public BigDecimal getCedentQ9() {
        if (cedentQ9 != null) {
            return cedentQ9.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP);
        } else {
            return null;
        }
    }

    public void setCedentQ9(BigDecimal cedentQ9) {
        this.cedentQ9 = cedentQ9;
    }

    public BigDecimal getCedentQ10() {
        if (cedentQ10 != null) {
            return cedentQ10.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP);
        } else {
            return null;
        }
    }

    public void setCedentQ10(BigDecimal cedentQ10) {
        this.cedentQ10 = cedentQ10;
    }

    public BigDecimal getCedentQ11() {
        if (cedentQ11 != null) {
            return cedentQ11.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP);
        } else {
            return null;
        }
    }

    public void setCedentQ11(BigDecimal cedentQ11) {
        this.cedentQ11 = cedentQ11;
    }

    public BigDecimal getCedentQ12() {
        if (cedentQ12 != null) {
            return cedentQ12.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP);
        } else {
            return null;
        }
    }

    public void setCedentQ12(BigDecimal cedentQ12) {
        this.cedentQ12 = cedentQ12;
    }

    public BigDecimal getCedentQ13() {
        if (cedentQ13 != null) {
            return cedentQ13.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP);
        } else {
            return null;
        }
    }

    public void setCedentQ13(BigDecimal cedentQ13) {
        this.cedentQ13 = cedentQ13;
    }

    public BigDecimal getCedentQ14() {
        if (cedentQ14 != null) {
            return cedentQ14.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP);
        } else {
            return null;
        }
    }

    public void setCedentQ14(BigDecimal cedentQ14) {
        this.cedentQ14 = cedentQ14;
    }

    public BigDecimal getCedentQ15() {
        if (cedentQ15 != null) {
            return cedentQ15.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP);
        } else {
            return null;
        }
    }

    public void setCedentQ15(BigDecimal cedentQ15) {
        this.cedentQ15 = cedentQ15;
    }

    public BigDecimal getCedentQ16() {
        if (cedentQ16 != null) {
            return cedentQ16.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP);
        } else {
            return null;
        }
    }

    public void setCedentQ16(BigDecimal cedentQ16) {
        this.cedentQ16 = cedentQ16;
    }

    public BigDecimal getCedentQ17() {
        if (cedentQ17 != null) {
            return cedentQ17.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP);
        } else {
            return null;
        }
    }

    public void setCedentQ17(BigDecimal cedentQ17) {
        this.cedentQ17 = cedentQ17;
    }

    public BigDecimal getCedentQ18() {
        if (cedentQ18 != null) {
            return cedentQ18.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP);
        } else {
            return null;
        }
    }

    public void setCedentQ18(BigDecimal cedentQ18) {
        this.cedentQ18 = cedentQ18;
    }

    public BigDecimal getCedentQ19() {
        if (cedentQ19 != null) {
            return cedentQ19.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP);
        } else {
            return null;
        }
    }

    public void setCedentQ19(BigDecimal cedentQ19) {
        this.cedentQ19 = cedentQ19;
    }

    public BigDecimal getCedentQ20() {
        if (cedentQ20 != null) {
            return cedentQ20.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP);
        } else {
            return null;
        }
    }

    public void setCedentQ20(BigDecimal cedentQ20) {
        this.cedentQ20 = cedentQ20;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        if (cedentQ1 != null) {
            total = total.add(cedentQ1);
        }
        if (cedentQ2 != null) {
            total = total.add(cedentQ2);
        }
        if (cedentQ3 != null) {
            total = total.add(cedentQ3);
        }
        if (cedentQ4 != null) {
            total = total.add(cedentQ4);
        }
        if (cedentQ5 != null) {
            total = total.add(cedentQ5);
        }
        if (cedentQ6 != null) {
            total = total.add(cedentQ6);
        }
        if (cedentQ7 != null) {
            total = total.add(cedentQ7);
        }
        if (cedentQ8 != null) {
            total = total.add(cedentQ8);
        }
        if (cedentQ9 != null) {
            total = total.add(cedentQ9);
        }
        if (cedentQ10 != null) {
            total = total.add(cedentQ10);
        }
        if (cedentQ11 != null) {
            total = total.add(cedentQ11);
        }
        if (cedentQ12 != null) {
            total = total.add(cedentQ12);
        }
        if (cedentQ13 != null) {
            total = total.add(cedentQ13);
        }
        if (cedentQ14 != null) {
            total = total.add(cedentQ14);
        }
        if (cedentQ15 != null) {
            total = total.add(cedentQ15);
        }
        if (cedentQ16 != null) {
            total = total.add(cedentQ16);
        }
        if (cedentQ17 != null) {
            total = total.add(cedentQ17);
        }
        if (cedentQ18 != null) {
            total = total.add(cedentQ18);
        }
        if (cedentQ19 != null) {
            total = total.add(cedentQ19);
        }
        if (cedentQ20 != null) {
            total = total.add(cedentQ20);
        }
        total = total.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP);
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(String itemOrder) {
        this.itemOrder = itemOrder;
    }

    private String itemOrder = "1000,2000,2090,2071,EP02,4122,4112,4116,3000";

    @Override
    public int compareTo(EntryItemVO o) {
        return itemOrder.indexOf(this.getItem()) - itemOrder.indexOf(o.getItem());
    }

}
