package com.ebao.ap99.parent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class BeanUtils extends org.springframework.beans.BeanUtils {
	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	// then use Spring BeanUtils to copy and ignore null
	public static void copyPropertiesIngoreNull(Object target, Object src) {
		copyProperties(src, target, getNullPropertyNames(src));
	}
	/**
	 * if isOpposite, then left--> right, else right-->left
	 * @param obj1: if(leftToRight){src = left} else {target = right}
	 * @param obj2: if(leftToRight){target = right} else {src = left}
	 * @param isOpposite
	 */
	public static void copyPropertiesByDirection(Object left, Object right, boolean leftToRight) {
		if(leftToRight){
			copyProperties(left, right);
		}else{
			copyProperties(right, left);
		}
	}
	
	/**
	 * copy srcList to List<D>
	 * @param srcList
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public static <S, D> List<D> convertList(List<S> srcList, Class<D> c) throws Exception {
        List<D> list = new ArrayList<D>();
        if(srcList != null && !srcList.isEmpty()){
            for (S s : srcList) {
                D temp = (D) c.getConstructor().newInstance();
                BeanUtils.copyProperties(s, temp);
                list.add(temp);
            }
        }
        return list;
    }

	/**
	 * 
	 * @param source
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static boolean checkFieldValueNull(Object source, List<String> unCheckFields) throws IllegalArgumentException, IllegalAccessException {
		final BeanWrapper src = new BeanWrapperImpl(source);
		// java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
		boolean allNull = true;
		for (Field f : source.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			if(f.getName().equals("insertBy") || f.getName().equals("insertTime") || f.getName().equals("updateBy") || f.getName().equals("updateTime") 
					|| f.getName().equals("serialVersionUID")){
				continue;
			}
			if(null != unCheckFields && unCheckFields.size() > 0){
				if(unCheckFields.contains(f.getName())){
					continue;
				}
			}
			Object object = f.get(source);
			if(null == object){
				continue;
			}
			String objectType = object.getClass().getName();
			if (objectType.equalsIgnoreCase("java.lang.Long")) {
				if ((Long) f.get(source) != 0L) {
					allNull = false;
					break;
				}
			} else if (objectType.equalsIgnoreCase("java.math.BigDecimal")) {
				if (((BigDecimal) f.get(source)).compareTo(BigDecimal.ZERO) != 0) {
					allNull = false;
					break;
				}
			} else if (objectType.equalsIgnoreCase("java.lang.Integer")) {
				if ((Integer) f.get(source) != 0) {
					allNull = false;
					break;
				}
			}else if (f.get(source) != null) {
				allNull = false;
				break;
			}
		}
		return allNull;
	}
}
