/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.parent.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

/**
 * Date: Jan 13, 2016 11:33:23 AM
 * 
 * @author xiaoyu.yang
 */
public class FieldsCollector {

    public static void concatSql(Object object, StringBuilder sqlBuf) {
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.getName().equals("serialVersionUID") && invokeMethod(object, field.getName()) != null
                        && StringUtils.isNotBlank(invokeMethod(object, field.getName()).toString())) {
                    sqlBuf.append(" AND t." + field.getName() + " =:" + field.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * For this function, it used to Business Model Search Before you used this
     * function, must have another condition, if you don't have other condition,
     * please concat with 'where 1=1'
     * 
     * @param src BusinessModel
     * @param dbEntityClass DBEntity
     * @param srcTableName
     * @param sqlBuf
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <D> void concatSql(Object businessModel, Class<D> dbEntityClass, String srcTableName,
                                     StringBuilder sqlBuf) throws InstantiationException, IllegalAccessException,
                                             IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
                                             SecurityException {
        D dbEntity = (D) dbEntityClass.getConstructor().newInstance();
        BeanUtils.copyProperties(businessModel, dbEntity);
        try {
            Field[] fields = dbEntity.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.getName().equals("serialVersionUID") && invokeMethod(dbEntity, field.getName()) != null
                        && StringUtils.isNotBlank(invokeMethod(dbEntity, field.getName()).toString())) {
                    sqlBuf.append(" AND " + srcTableName + "." + field.getName() + " =:" + field.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setParams(Object object, Query query) {
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.getName().equals("serialVersionUID") && invokeMethod(object, field.getName()) != null
                        && StringUtils.isNotBlank(invokeMethod(object, field.getName()).toString())) {
                    query.setParameter(field.getName(), invokeMethod(object, field.getName()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param src
     * @param dbEntityClass
     * @param query
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <D> void setParams(Object businessModel, Class<D> dbEntityClass, Query query)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException {
        D dbEntity = (D) dbEntityClass.getConstructor().newInstance();
        BeanUtils.copyProperties(businessModel, dbEntity);
        try {
            Field[] fields = dbEntity.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.getName().equals("serialVersionUID") && invokeMethod(dbEntity, field.getName()) != null
                        && StringUtils.isNotBlank(invokeMethod(dbEntity, field.getName()).toString())) {
                    query.setParameter(field.getName(), invokeMethod(dbEntity, field.getName()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object invokeMethod(Object owner, String fieldname) throws SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Object result = null;
        Method method = owner.getClass().getMethod(GetterUtil.toGetter(fieldname));
        result = method.invoke(owner);
        return result;
    }
}
