/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.service;

import java.util.List;

import com.ebao.ap99.contract.model.RetrocessionItemVO;
import com.ebao.ap99.contract.model.RetrocessionVO;
import com.ebao.ap99.parent.model.TreeModel;

/**
 * Date: Jan 19, 2016 4:07:45 PM
 * 
 * @author weiping.wang
 *
 */
public interface ContractRetrocessionDS {

	public void saveRetrocession(RetrocessionVO vo) throws Exception;

	public List<RetrocessionItemVO> loadRetrocessionList(Long compId) throws Exception;

	public List<RetrocessionItemVO> loadCoveredSectionList(Long contId) throws Exception;

	/**
	 * Get retrocession info by retro section id.
	 * 
	 * @param retroCompId
	 * @return
	 * @throws Exception
	 */
	public RetrocessionItemVO generateRetrocession(Long retroCompId) throws Exception;

	public void backupRetrocessionInfo(Long contCompId, Long operateId) throws Exception;

	/**
	 * Get retro contract structure by contractCode and uwYear.
	 * 
	 * @param contractCode
	 * @param uwYear
	 * @return
	 * @throws Exception
	 */
	public List<TreeModel> getRetroContractStructure(String contractCode, Long uwYear) throws Exception;

	public List<RetrocessionItemVO> loadRetrocessionListForLog(Long compId, Long operateId) throws Exception;

	public List<RetrocessionItemVO> loadCoveredSectionListForLog(Long contId, Long operateId) throws Exception;

}
