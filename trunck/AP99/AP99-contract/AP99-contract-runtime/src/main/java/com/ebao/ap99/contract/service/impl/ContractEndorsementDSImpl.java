/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.contract.dao.TRiContractEndoDao;
import com.ebao.ap99.contract.entity.TRiContractEndo;
import com.ebao.ap99.contract.model.ContractVO;
import com.ebao.ap99.contract.model.EndorsementVO;
import com.ebao.ap99.contract.service.ContractEndorsementDS;
import com.ebao.ap99.contract.service.ContractHomeDS;
import com.ebao.ap99.parent.BeanUtils;

/**
 * Date: Feb 3, 2016 4:30:40 PM
 * 
 * @author Weiping.Wang
 *
 */
public class ContractEndorsementDSImpl implements ContractEndorsementDS {
	@Autowired
	private TRiContractEndoDao endoDao;

	@Autowired
	private ContractHomeDS contractHomeDS;

	@Override
	public List<EndorsementVO> loadEndorsementList(Long contId) throws Exception {
		List<EndorsementVO> endorsementList = BeanUtils.convertList(endoDao.getEndorsementList(contId),
				EndorsementVO.class);
		return endorsementList;
	}

	@Override
	public EndorsementVO getLatestEndorsement(Long contId) throws Exception {
		EndorsementVO vo = null;
		List<EndorsementVO> endorsementList = this.loadEndorsementList(contId);
		if (CollectionUtils.isNotEmpty(endorsementList)) {
			vo = endorsementList.get(endorsementList.size() - 1);
		}
		return vo;
	}

	@Override
	public EndorsementVO loadEndorsement(Long endoId) throws Exception {
		EndorsementVO endorsement = new EndorsementVO();
		if (endoId != null) {
			TRiContractEndo entity = endoDao.getEndorsement(endoId);
			BeanUtils.copyPropertiesIngoreNull(endorsement, entity);
		}
		return endorsement;
	}

	@Override
	public EndorsementVO saveEndorsement(EndorsementVO vo) throws Exception {
		TRiContractEndo entity = new TRiContractEndo();
		BeanUtils.copyPropertiesIngoreNull(entity, vo);
		endoDao.save(entity);

		ContractVO model = contractHomeDS.loadContractInfo(vo.getContCompId());
		// model.setEndoId(entity.getEndoId());
		if (vo.getEndoId() == null) {
			model = contractHomeDS.contractEndoSave(model);
		}

		BeanUtils.copyPropertiesIngoreNull(vo, entity);
		vo.setLatestStatus(model.getLatestStatus());
		return vo;
	}

	@Override
	public void deleteEndorsement(EndorsementVO vo) throws Exception {
		TRiContractEndo endorsement = new TRiContractEndo();
		TRiContractEndo entity = endoDao.getEndorsement(vo.getEndoId());
		BeanUtils.copyPropertiesIngoreNull(endorsement, entity);

		endorsement.setIsActive("N");
		endoDao.save(endorsement);
	}
}
