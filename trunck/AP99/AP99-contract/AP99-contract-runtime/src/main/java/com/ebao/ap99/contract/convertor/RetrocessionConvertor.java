/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.convertor;

import java.util.ArrayList;
import java.util.List;

import com.ebao.ap99.contract.entity.TRiContRetro;

/**
 * Date: Jan 19, 2016 4:24:47 PM
 * 
 * @author weiping.wang
 *
 */
public class RetrocessionConvertor {
	public String[] getCheckedSection(List<TRiContRetro> list) {
		List<String> ls = new ArrayList<String>();
		for (TRiContRetro p : list) {
			ls.add(p.getRetroCompId().toString());
		}
		final int size = ls.size();
		String[] arr = new String[size];
		for (int i = 0; i < ls.size(); i++) {
			arr[i] = ls.get(i);
		}

		return arr;
	}
}
