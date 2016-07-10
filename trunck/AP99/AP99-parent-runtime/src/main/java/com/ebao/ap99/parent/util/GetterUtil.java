/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.parent.util;

/**
 * Date: Jan 13, 2016 11:29:00 AM
 * 
 * @author xiaoyu.yang
 */
public class GetterUtil {

    /**
     * Get getter method name by field name
     * 
     * @param fieldname
     * @return
     */
    public static String toGetter(String fieldname) {

        if (fieldname == null || fieldname.length() == 0) {
            return null;
        }

        /*
         * If the second char is upper, make 'get' + field name as getter name.
         * For example, eBlog -> geteBlog
         */
        if (fieldname.length() > 2) {
            String second = fieldname.substring(1, 2);
            if (second.equals(second.toUpperCase())) {
                return new StringBuffer("get").append(fieldname).toString();
            }
        }

        /* Common situation */
        fieldname = new StringBuffer("get").append(fieldname.substring(0, 1).toUpperCase())
                .append(fieldname.substring(1)).toString();

        return fieldname;
    }
}
