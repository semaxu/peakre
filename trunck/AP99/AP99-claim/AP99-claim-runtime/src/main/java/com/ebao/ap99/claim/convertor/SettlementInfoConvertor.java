/**
 * 
 */
package com.ebao.ap99.claim.convertor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.claim.entity.TRiClaimInfo;
import com.ebao.ap99.claim.entity.TRiClaimSettlement;
import com.ebao.ap99.claim.model.SettlementInfo;
import com.ebao.ap99.claim.model.SettlementItemInfo;
import com.ebao.ap99.parent.constant.ReInsuranceCst;
import com.ebao.ap99.parent.model.TreeModel;
import com.ebao.unicorn.platform.foundation.utils.BeanUtils;

/**
 * @author gang.wang
 *
 */
public class SettlementInfoConvertor {

	@Autowired
	private SettlementItemInfoConvertor settleDetailInfoConvertor;

	public TRiClaimSettlement modelToEntity(SettlementInfo settleInfo) {
		TRiClaimSettlement entity = new TRiClaimSettlement();

		BeanUtils.copyProperties(entity, settleInfo);

		return entity;
	}

	public SettlementInfo entityToModel(TRiClaimSettlement entity) {
		SettlementInfo settleInfo = new SettlementInfo();
		BeanUtils.copyProperties(settleInfo, entity);

		return settleInfo;
	}

	public void convertToEntityList(List<SettlementInfo> settleInfoList, TRiClaimInfo claimEntity) {
		for (SettlementInfo settleInfo : settleInfoList) {
			TRiClaimSettlement entity = modelToEntity(settleInfo);
			claimEntity.addTRiClaimSettlement(entity);
		}
	}

	// public List<SettlementInfo>
	// entityListToModelList(List<TRiClaimSettlement> entityList) {
	// List<SettlementInfo> settleInfoList = new ArrayList<SettlementInfo>();
	// for (TRiClaimSettlement entity : entityList) {
	// SettlementInfo SettlementInfo = entityToModel(entity);
	// settleInfoList.add(SettlementInfo);
	// }
	// return settleInfoList;
	// }

	public TRiClaimSettlement modelToEntityCascade(SettlementInfo settleInfo, String businessDirection) {
		TRiClaimSettlement settleEntity = modelToEntity(settleInfo);
		settleEntity.setBusinessDirection(businessDirection);
		if (settleInfo.getSettlementItemList() != null && settleInfo.getSettlementItemList().size() != 0)
			settleDetailInfoConvertor.convertToEntityList(settleInfo.getSettlementItemList(), settleEntity,
					businessDirection);
		return settleEntity;
	}

	public SettlementInfo entityToModelCascade(TRiClaimSettlement settleEntity) {
		SettlementInfo settleInfo = entityToModel(settleEntity);
		if (settleEntity.getTRiClaimSettlementItems() != null
				&& settleEntity.getTRiClaimSettlementItems().size() != 0) {
			List<SettlementItemInfo> settleDetailInfoList = settleDetailInfoConvertor
					.entityListToModelList(settleEntity.getTRiClaimSettlementItems());
			settleInfo.setSettlementItemList(settleDetailInfoList);
		}
		return settleInfo;
	}

	public void convertToEntityListCascade(List<SettlementInfo> settleInfoList, TRiClaimInfo claimEntity,
			String businessDirection) {
		for (SettlementInfo settleInfo : settleInfoList) {
			TRiClaimSettlement entity = modelToEntityCascade(settleInfo, businessDirection);
			claimEntity.addTRiClaimSettlement(entity);
		}
	}

	public List<TRiClaimSettlement> convertToEntityListCascade(List<SettlementInfo> settleInfoList,
			String businessDirection) {
		List<TRiClaimSettlement> entitys = new ArrayList<>();
		for (SettlementInfo settleInfo : settleInfoList) {
			TRiClaimSettlement entity = modelToEntityCascade(settleInfo, businessDirection);
			entitys.add(entity);
		}
		return entitys;
	}

	public Map<String, List<SettlementInfo>> entityListToModelListCascade(List<TRiClaimSettlement> entityList) {
		List<SettlementInfo> financialSettleInfoList = new ArrayList<SettlementInfo>();
		List<SettlementInfo> retroSettleInfoList = new ArrayList<SettlementInfo>();
		entityList.stream()
				.filter(settle -> settle.getBusinessDirection().equals(ReInsuranceCst.CONTRACT_CATEGORY_ASSUME))
				.forEach((TRiClaimSettlement entity) -> {
					financialSettleInfoList.add(entityToModelCascade(entity));
				});

		entityList.stream()
				.filter(settle -> settle.getBusinessDirection().equals(ReInsuranceCst.CONTRACT_CATEGORY_RETRO))
				.forEach((TRiClaimSettlement entity) -> {
					retroSettleInfoList.add(entityToModelCascade(entity));
				});

		Map<String, List<SettlementInfo>> result = new HashMap<>();
		result.put("financial", financialSettleInfoList);
		result.put("retro", retroSettleInfoList);

		return result;
	}

	public List<TreeModel> getSettlementList(List<TRiClaimSettlement> list, String businessDirection) {

		List<TreeModel> settlements = new ArrayList<TreeModel>();

		for (TRiClaimSettlement p : list) {

			if (businessDirection.equals(p.getBusinessDirection())) {
				TreeModel treeModel = new TreeModel();
				treeModel.setId(p.getSettlementId());
				treeModel.setText(p.getSettlementName());
				settlements.add(treeModel);
			}
		}
		return settlements;

	}
}
