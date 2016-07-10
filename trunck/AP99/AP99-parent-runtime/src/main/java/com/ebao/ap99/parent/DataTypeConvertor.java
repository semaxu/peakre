/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.parent;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author weiping.wang
 */
public class DataTypeConvertor {

    public static String parseSelectTreeToString(List treeList) {
        String str = "";
        try {
            if (treeList != null && treeList.size() > 0) {
                for (int i = 0; i < treeList.size(); i++) {
                    if (i == 0) {
                        str += treeList.get(i).toString();
                    } else if (i > 0) {
                        str = str + "," + treeList.get(i).toString();
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        return str;
    }

    public static List<String> transStringToSelectTree(String str) {
        List<String> list = new ArrayList<String>();
        String[] arrayStr = new String[] {};
        if (str != null) {
            try {
                List<String> sList = new ArrayList<String>();
                arrayStr = str.split(",");
                sList = (List<String>) Arrays.asList(arrayStr);
                list = sList.stream().collect(Collectors.toList());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * split a string into an list of strings
     * 
     * @param str
     * @return
     */
    public static List<String> splitStringToListBySlash(String str) {
        List<String> list = new ArrayList<String>();
        String[] arrayStr = new String[] {};
        if (str != null) {
            try {
                List<String> sList = new ArrayList<String>();
                arrayStr = str.split("/");
                sList = (List<String>) Arrays.asList(arrayStr);
                list = sList.stream().collect(Collectors.toList());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return list;
    }

    public static String transAmoutToString(BigDecimal amount) {

        //    	DecimalFormat dataFormat =new DecimalFormat("#,##0.00##");
        DecimalFormat dataFormat = new DecimalFormat("#,##0.00");

        return dataFormat.format(amount);
    }
}
