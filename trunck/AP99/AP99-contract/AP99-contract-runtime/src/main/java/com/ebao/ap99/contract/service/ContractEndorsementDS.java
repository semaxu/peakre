/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.service;

import java.util.List;

import com.ebao.ap99.contract.model.EndorsementVO;

/**
 * Date: Feb 3, 2016 4:29:59 PM
 * 
 * @author Weiping.Wang
 *
 */
public interface ContractEndorsementDS {

	/**
	 * Load endorsement list by contract id.
	 * 
	 * @param contId
	 * @return
	 * @throws Exception
	 */
	public List<EndorsementVO> loadEndorsementList(Long contId) throws Exception;

	/**
	 * Load endorsement by endo id.
	 * 
	 * @param endoId
	 * @return
	 * @throws Exception
	 */
	public EndorsementVO loadEndorsement(Long endoId) throws Exception;

	/**
	 * Save endorsement info. Back up contract info, then set contract status to
	 * "Data Update".
	 * 
	 * @param vo
	 * @throws Exception
	 */
	public EndorsementVO saveEndorsement(EndorsementVO vo) throws Exception;

	/**
	 * Delete endorsement info. Then revert the contract to the last saved
	 * "In Force" status.
	 * 
	 * @param vo
	 * @throws Exception
	 */
	public void deleteEndorsement(EndorsementVO vo) throws Exception;

	/**
	 * Load latest endorsement info by contract id.
	 * 
	 * @param contId
	 * @return
	 * @throws Exception
	 */
	public EndorsementVO getLatestEndorsement(Long contId) throws Exception;

}
