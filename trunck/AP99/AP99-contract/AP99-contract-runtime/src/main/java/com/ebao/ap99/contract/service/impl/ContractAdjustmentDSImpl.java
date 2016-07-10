/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.contract.convertor.ContractModelConvertor;
import com.ebao.ap99.contract.convertor.ContractSectionModelConvertor;
import com.ebao.ap99.contract.dao.TRiContAdjustmentDao;
import com.ebao.ap99.contract.dao.TRiContAdjustmentItemDao;
import com.ebao.ap99.contract.dao.TRiContAdjustmentItemLogDao;
import com.ebao.ap99.contract.dao.TRiContOperationDao;
import com.ebao.ap99.contract.entity.TRiContAdjustment;
import com.ebao.ap99.contract.entity.TRiContAdjustmentItem;
import com.ebao.ap99.contract.entity.TRiContAdjustmentItemLog;
import com.ebao.ap99.contract.entity.TRiContOperation;
import com.ebao.ap99.contract.entity.TRiContPremiumItem;
import com.ebao.ap99.contract.model.AdjustmentItemVO;
import com.ebao.ap99.contract.model.AdjustmentVO;
import com.ebao.ap99.contract.model.ContractSectionVO;
import com.ebao.ap99.contract.model.ContractVO;
import com.ebao.ap99.contract.model.BusinessModel.BusinessConditionBO;
import com.ebao.ap99.contract.model.BusinessModel.ContractBO;
import com.ebao.ap99.contract.model.BusinessModel.SectionBO;
import com.ebao.ap99.contract.service.ContractAdjustmentDS;
import com.ebao.ap99.contract.service.ContractDS;
import com.ebao.ap99.contract.util.ContractCst;
import com.ebao.ap99.parent.BeanUtils;
import com.ebao.ap99.parent.context.AppContext;

/**
 * Date: Mar 24, 2016 11:45:46 PM
 * 
 * @author weiping.wang
 *
 */
public class ContractAdjustmentDSImpl implements ContractAdjustmentDS {

	@Autowired
	private TRiContAdjustmentDao adjustmentDao;

	@Autowired
	private TRiContAdjustmentItemDao adjustmentItemDao;

	@Autowired
	private TRiContAdjustmentItemLogDao adjustmentItemLogDao;

	@Autowired
	private ContractDS contractDS;

	@Autowired
	private TRiContOperationDao operationDao;

	@Override
	public AdjustmentVO getAdjustInfo(Long contractId) throws Exception {
		AdjustmentVO vo = new AdjustmentVO();
		Long adjustmentId = null;
		List<TRiContAdjustmentItem> adjustmentItemList = new ArrayList<TRiContAdjustmentItem>();

		// Level 1
		// Get contract info
		ContractVO contractvo = new ContractVO();
		ContractBO contractBO = contractDS.loadContractBO(contractId, false);
		ContractModelConvertor.convertBOToVO(contractBO, contractvo);
		BeanUtils.copyPropertiesIngoreNull(vo, contractvo);
		vo.setRemark(null);
		// Get SUPI Adjustment info, if any
		TRiContAdjustment entity = adjustmentDao.loadByContCompId(contractId);
		if (entity != null) {
			BeanUtils.copyPropertiesIngoreNull(vo, entity);
			adjustmentId = entity.getAdjustmentId();
			adjustmentItemList = entity.getTRiContAdjustmentItems();
		}

		// Level 2
		List<SectionBO> sectionList = contractBO.getSectionList();
		if (CollectionUtils.isNotEmpty(sectionList)) {
			for (SectionBO item : sectionList) {
				vo.getSupiSecList().add(this.getAdjustmentItemVO(item, adjustmentItemList, adjustmentId));
			}
		}

		return vo;
	}

	private AdjustmentItemVO getAdjustmentItemVO(SectionBO bo, List<TRiContAdjustmentItem> itemList, Long adjustmentId)
			throws Exception {
		AdjustmentItemVO vo = new AdjustmentItemVO();
		// Get section info
		ContractSectionVO sectionVO = new ContractSectionVO();
		ContractSectionModelConvertor.convertBOToVO(bo, sectionVO);
		vo.setPanelTitle(sectionVO.getContCompId() + " " + sectionVO.getSectionName());
		// Get business condition's premium info
		BusinessConditionBO bcBO = bo.getBusinessBO();
		if (bcBO.getPremiumBO() != null) {
			vo.setAdjustedSUPIDefined(bcBO.getPremiumBO().getDefinedIn());
		}
		// Get adjustment records, if records do not exist, then get the
		// contract's section info
		if (CollectionUtils.isNotEmpty(itemList)) {
			for (TRiContAdjustmentItem item : itemList) {
				if (item.getContCompId().equals(bo.getContCompId())) {
					TRiContAdjustmentItem adjustmentItem = new TRiContAdjustmentItem();
					BeanUtils.copyProperties(item, adjustmentItem);
					adjustmentItem.setTRiContAdjustment(null);
					// initial UserBy
					adjustmentItem.setUserBy(AppContext.getCurrentUser().getUserId());
					vo.getAdjustedSUPIList().add(adjustmentItem);
				}
			}
		} else {
			List<TRiContPremiumItem> TRiContPremiumItems = bcBO.getPremiumBO().getTRiContPremiumItems();
			if (CollectionUtils.isNotEmpty(TRiContPremiumItems)) {
				for (TRiContPremiumItem item : TRiContPremiumItems) {
					if (item.getItemType().equals(ContractCst.CONTRACT_PREMIUM_SUPI)) {
						TRiContAdjustmentItem adjustmentItem = new TRiContAdjustmentItem();
						adjustmentItem.setCurrency(item.getCurrency());
						adjustmentItem.setAmount(item.getAmount());
						adjustmentItem.setContCompId(bo.getContCompId());
						// initial UserBy
						adjustmentItem.setUserBy(AppContext.getCurrentUser().getUserId());
						vo.getAdjustedSUPIList().add(adjustmentItem);
					}
				}
			}

			// initial hitory
			vo.setHisList(BeanUtils.convertList(vo.getAdjustedSUPIList(), TRiContAdjustmentItemLog.class));
		}
		// Get history List
		if (adjustmentId != null) {
			List<TRiContAdjustmentItemLog> itemLogList = adjustmentItemLogDao
					.loadByContCompIdAndAdjustmentId(adjustmentId, bo.getContCompId());
			vo.setHisList(itemLogList);
		}
		return vo;
	}

	@Override
	public void saveAdjustment(AdjustmentVO vo) throws Exception {
		TRiContAdjustment entity = new TRiContAdjustment();
		BeanUtils.copyProperties(vo, entity);
		boolean needSavehis = vo.getAdjustmentId() == null;
		List<TRiContAdjustmentItem> notBackupList = new ArrayList<TRiContAdjustmentItem>();
		if (CollectionUtils.isNotEmpty(vo.getSupiSecList())) {
			for (AdjustmentItemVO item : vo.getSupiSecList()) {
				if (CollectionUtils.isNotEmpty(item.getAdjustedSUPIList())) {
					if (this.isAmoutChanged(item) || item.getAdjustedSUPIList().get(0).getItemId() == null) {
						for (TRiContAdjustmentItem adjustMentItem : item.getAdjustedSUPIList()) {
							TRiContAdjustmentItem itemEntity = new TRiContAdjustmentItem();
							BeanUtils.copyProperties(adjustMentItem, itemEntity);
							if (!this.isAmoutChanged(item) && item.getAdjustedSUPIList().get(0).getItemId() == null) {
								notBackupList.add(itemEntity);
							}
							itemEntity.setTRiContAdjustment(entity);
							entity.getTRiContAdjustmentItems().add(itemEntity);
						}
					}
				}
			}
		}
		adjustmentDao.save(entity);
		TRiContAdjustment entityBackup = new TRiContAdjustment();
		BeanUtils.copyProperties(entity, entityBackup);
		List<TRiContAdjustmentItemLog> needSaveHisList = new ArrayList<TRiContAdjustmentItemLog>();
		if (CollectionUtils.isNotEmpty(vo.getSupiSecList())) {
			if (needSavehis) {
				for (AdjustmentItemVO item : vo.getSupiSecList()) {
					needSaveHisList.addAll(item.getHisList());
				}

			}
		}
		// back up records
		TRiContOperation operation = new TRiContOperation(vo.getContCompId(), ContractCst.CONTRACT_OPERATE_ADJUSTMENT,
				null, null);
		operationDao.insert(operation);
		adjustmentDao.saveLogForSupiAdjustmentInfo(entityBackup, operation.getOperateId(), needSaveHisList,
				notBackupList);
	}

	private boolean isAmoutChanged(AdjustmentItemVO vo) throws Exception {
		boolean ret = false;
		List<TRiContAdjustmentItem> adjustedSUPIList = vo.getAdjustedSUPIList();
		Long sectionId = null;
		if (CollectionUtils.isNotEmpty(adjustedSUPIList)) {
			sectionId = adjustedSUPIList.get(0).getContCompId();
			// adjustment records is not exist
			if (null == adjustedSUPIList.get(0).getItemId()) {
				SectionBO secBO = contractDS.loadSectionBO(sectionId, false);
				if (null != secBO && null != secBO.getBusinessBO() && null != secBO.getBusinessBO().getPremiumBO()) {
					List<TRiContPremiumItem> TRiContPremiumItems = secBO.getBusinessBO().getPremiumBO()
							.getTRiContPremiumItems();
					if (CollectionUtils.isNotEmpty(TRiContPremiumItems)) {
						for (TRiContPremiumItem entity : TRiContPremiumItems) {
							if (entity.getItemType().equals(ContractCst.CONTRACT_PREMIUM_SUPI)) {
								for (TRiContAdjustmentItem model : adjustedSUPIList) {
									if (entity.getCurrency().equals(model.getCurrency())
											&& ((entity.getAmount() == null && model.getAmount() != null)
													|| (entity.getAmount() != null && model.getAmount() == null)
													|| entity.getAmount().compareTo(model.getAmount()) != 0)) {
										ret = true;
										break;
									}
								}
							}
						}
					}
				}
			} else {
				// adjustment records is exist
				List<TRiContAdjustmentItem> entityList = adjustmentItemDao.loadByContCompId(sectionId);
				if (CollectionUtils.isNotEmpty(entityList)) {
					for (TRiContAdjustmentItem entity : entityList) {
						for (TRiContAdjustmentItem model : adjustedSUPIList) {
							if (entity.getItemId().equals(model.getItemId())
									&& entity.getAmount().compareTo(model.getAmount()) != 0) {
								ret = true;
								break;
							}
						}
					}
				}
			}
		}

		return ret;
	}

}
