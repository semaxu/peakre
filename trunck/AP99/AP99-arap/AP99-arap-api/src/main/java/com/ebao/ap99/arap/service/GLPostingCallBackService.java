package com.ebao.ap99.arap.service;

import java.util.Date;
import java.util.List;

public interface GLPostingCallBackService {
	/**
	 * Call back business module to notice post information
	 * @param postDate
	 * @param bizTransNoList
	 * @throws Exception
	 */
	public void callBack(Date postDate, List<String> bizTransNoList) throws Exception;
	
}
