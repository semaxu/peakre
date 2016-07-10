/**
 * 
 */
package com.ebao.ap99.claim.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.claim.constant.ClaimConstant;
import com.ebao.ap99.claim.convertor.ClaimSectionInfoConvertor;
import com.ebao.ap99.claim.convertor.EventInfoConvertor;
import com.ebao.ap99.claim.dao.RiClaimEventDao;
import com.ebao.ap99.claim.dao.RiClaimInfoDao;
import com.ebao.ap99.claim.dao.RiClaimSectionDao;
import com.ebao.ap99.claim.entity.TRiClaimEvent;
import com.ebao.ap99.claim.entity.TRiClaimSectionInfo;
import com.ebao.ap99.claim.model.ClaimInfo;
import com.ebao.ap99.claim.model.EventInfo;
import com.ebao.ap99.claim.model.ReserveInfo;
import com.ebao.ap99.claim.model.SettlementInfo;
import com.ebao.ap99.claim.model.SettlementItemInfo;
import com.ebao.ap99.claim.service.EventUploadingService;
import com.ebao.ap99.claim.service.RiClaimSectionInfoService;
import com.ebao.ap99.claim.service.RiEventService;
import com.ebao.ap99.contract.entity.ContractModel;
import com.ebao.ap99.contract.service.ContractService;
import com.ebao.ap99.file.model.ItemVO;
import com.ebao.ap99.file.model.MessageVO;
import com.ebao.ap99.file.service.BizService;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * @author yujie.zhang
 *
 */
public class EventUploadingServiceImpl implements EventUploadingService, BizService {

	private static Logger logger = LoggerFactory.getLogger();
	@Autowired
	public RiEventService eventService;
	@Autowired
	public EventInfoConvertor eventInfoConvertor;
	@Autowired
	private RiClaimInfoDao claimInfoDao;
	@Autowired
	private RiClaimEventDao eventDao;
	@Autowired
	public ContractService contractService;
	@Autowired
	public RiClaimSectionDao claimSectionDao;
	@Autowired
	public RiClaimSectionInfoService sectionService;
	@Autowired
	public ClaimSectionInfoConvertor sectionInfoConvertor;

	@SuppressWarnings("rawtypes")
	@Override
	public MessageVO bizProcess(List itemVOList, Long documentId, Long businessId) {
		if (itemVOList != null && itemVOList.size() > 0) {
			try {
				saveAll(itemVOList, documentId);
			} catch (Exception e) {
				logger.error("error message:" + e.getMessage() + ",error cause:" + e.getCause());
			}
		}
		return new MessageVO();// it may be unnecessary
	}

	@SuppressWarnings("rawtypes")
	public void saveAll(List itemVoList, Long documentId) throws Exception {
		if (itemVoList != null && itemVoList.size() > 0) {
			// checkList(contractBOList);
			int i = 0;
			for (Object obj : itemVoList) {
				i++;
				@SuppressWarnings("unchecked")
				ItemVO<ClaimInfo> itemVO = (ItemVO<ClaimInfo>) obj;
				itemVO.setRowNo(String.valueOf(i));
				save(itemVO, documentId);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public void save(ItemVO itemVo, Long documentId) throws Exception {
		EventInfo eventInfo = (EventInfo) itemVo.getBizVO();

		// claimService.calculateReportingCurrencyAmount(bizVO);//
		// reportingCurrency
		Map<String, List<Long>> result = resetEventInfo(eventInfo);
		// convert model to entity
		TRiClaimEvent eventEntity = eventInfoConvertor.modelToEntityCascade(eventInfo);
		
		List<String> eventCodeExistList = eventDao.getEventCode();
		for(String eventCode : eventCodeExistList){
			// validate eventCode
			if(!eventCode.equals(eventEntity.getEventCode())){
				
			}else{
				throw new Exception(" EventCode has existed in DB");
			}
		}

		// save event info
		TRiClaimEvent eventEntityreturn = eventService.updateEvent(eventEntity);

		if (CollectionUtils.isNotEmpty(result.get("retroSectionIdList"))) {
			// load and Insert SectionInfo
			List<Long> rSectionIdList = result.get("retroSectionIdList");
			// load and Insert SectionInfo
			for (int i = 0; i < rSectionIdList.size(); i++) {
				ContractModel contactModel = contractService.getContractInfoByCompId(rSectionIdList.get(i));
				if (null == contactModel) {
					logger.warn("NO contract section found! section ID=" + rSectionIdList.get(i));
					continue;
				}
				TRiClaimSectionInfo section = sectionInfoConvertor.modelToEntity(contactModel);
				section.setRefId(eventEntityreturn.getEventId());
				section.setRefType(ClaimConstant.REF_TYPE_EVENT);
				section.setBusinessDirection(ClaimConstant.CLAIM_BUSINESS_DIRECTION__RETROCESSION);
				sectionService.createClaimSectionInfo(section);
			}
		}
		itemVo.setResultFlag(true);
		itemVo.setMsg("success");

	}

	public Map<String, List<Long>> resetEventInfo(EventInfo info) throws Exception {

		// setTaskOwner
		Long userId = claimInfoDao.getUserInfo(info.getTaskOwner()).getUserId();
		info.setTaskOwner(userId.toString());

		// set sectionId, retroRefSectionId. load and Insert SectionInfo

		// 1.retrocession reserve
		List<Long> rSectionIdList = new ArrayList<Long>();
		if (CollectionUtils.isNotEmpty(info.getReserveListRetro())) {
			List<ReserveInfo> retroReserveInfoList = new ArrayList<ReserveInfo>();

			// set sectionId and retroRefSectionId
			for (ReserveInfo re : info.getReserveListRetro()) {
				if (null == re.getSectionId()) {
					if (StringUtils.isNotBlank(re.getRetroRefcontractCode()) && null != re.getRetroRefuwYear()
							&& null != re.getRetroRefreinsStarting() && StringUtils.isNotBlank(re.getRetroRefsectionName())
							&& StringUtils.isNotBlank(re.getRetroRefsectionType())) {
						
						Long sectionId =contractService.getSectionIdByContractCodeAndSectionInfo(re.getRetroRefcontractCode(),
								re.getRetroRefuwYear(), re.getRetroRefreinsStarting(), re.getRetroRefsectionName(), re.getRetroRefsectionType()
								);
						re.setSectionId(sectionId);

						// re.setSectionId(sectionId); //getsectionId from
						// contract
						// model

					} else {
						throw new Exception(" Can not find SectionId by contractCode: " + re.getContractCode()
								+ ",SectionName: " + re.getSectionName() + " and SectionType:" + re.getSectionType());
					}
				}

//				if (null == re.getRetroRefSectionId()) {
//					if (StringUtils.isNotBlank(re.getRetroRefcontractCode())
//							&& StringUtils.isNotBlank(re.getRetroRefsectionName())
//							&& StringUtils.isNotBlank(re.getRetroRefsectionType())) {
//
//						// re.setRetroRefsectionId(retroRefsectionId);;
//						// //getsectionId from
//						// contract
//						// model
//
//					} else {
//						throw new Exception(" Can not find RetroRefSectionId by RetroRefcontractCode: "
//								+ re.getRetroRefcontractCode() + ",SectionName: " + re.getRetroRefsectionName()
//								+ " and SectionType:" + re.getRetroRefsectionType());
//					}
//				}

				re.setStatus(ClaimConstant.CLAIM_RESERVE_STATUS__NEW);
				re.setRefType(ClaimConstant.REF_TYPE_EVENT);
				retroReserveInfoList.add(re);
			}
			info.setReserveListRetro(retroReserveInfoList);
			rSectionIdList = retroReserveInfoList.stream().filter(s -> s.getSectionId() != null)
					.map(s -> s.getSectionId()).distinct().collect(Collectors.toList());

		}

		// Retrocession Settlement
		if (CollectionUtils.isNotEmpty(info.getSettlementListRetro())) {
			List<SettlementInfo> retroSettlementInfo = new ArrayList<SettlementInfo>();
			for (SettlementInfo seRetro : info.getSettlementListRetro()) {
				// reset status
				seRetro.setStatus(ClaimConstant.CLAIM_SETTLEMENT_STATUS__PENDING_SUBMIT);
				seRetro.setRefType(ClaimConstant.REF_TYPE_CLAIM);

				if (CollectionUtils.isNotEmpty(seRetro.getSettlementItemList())) {
					List<SettlementItemInfo> retroSettlementItemInfo = new ArrayList<SettlementItemInfo>();
					for (SettlementItemInfo seItem : seRetro.getSettlementItemList()) {
						if (null == seItem.getSectionId()) {
							if (StringUtils.isNotBlank(seItem.getRetroRefcontractCode())
									&& null != seItem.getRetroRefuwYear()
									&& null != seItem.getRetroRefreinsStarting()
								    && StringUtils.isNotBlank(seItem.getRetroRefsectionName())
									&& StringUtils.isNotBlank(seItem.getRetroRefsectionType())) {
								
								Long sectionId =contractService.getSectionIdByContractCodeAndSectionInfo(seItem.getRetroRefcontractCode(),
										seItem.getRetroRefuwYear(), seItem.getRetroRefreinsStarting(), seItem.getRetroRefsectionName(), seItem.getRetroRefsectionType()
										);
								seItem.setSectionId(new BigDecimal(sectionId));
								// re.setSectionId(sectionId); //getsectionId
								// from
								// contract
								// model
							} else {
								throw new Exception(" Can not find SectionId by contractCode: "
										+ seItem.getContractCode() + ",SectionName: " + seItem.getSectionName()
										+ " and SectionType:" + seItem.getSectionType());
							}
						}

//						if (null == seItem.getRetroRefSectionId()) {
//							if (StringUtils.isNotBlank(seItem.getRetroRefcontractCode())
//									&& StringUtils.isNotBlank(seItem.getRetroRefsectionName())
//									&& StringUtils.isNotBlank(seItem.getRetroRefsectionType())) {
//
//								// re.setRetroRefsectionId(retroRefsectionId);;
//								// //getsectionId from
//								// contract
//								// model
//
//							} else {
//								throw new Exception(" Can not find RetroRefSectionId by RetroRefcontractCode: "
//										+ seItem.getRetroRefcontractCode() + ",SectionName: "
//										+ seItem.getRetroRefsectionName() + " and SectionType:"
//										+ seItem.getRetroRefsectionType());
//							}
//						}
						// seItem.set
						retroSettlementItemInfo.add(seItem);
					}
					seRetro.setSettlementItemList(retroSettlementItemInfo);
				}
				retroSettlementInfo.add(seRetro);
			}
			info.setSettlementListRetro(retroSettlementInfo);
		}
		Map<String, List<Long>> result = new HashMap<>();
		result.put("retroSectionIdList", rSectionIdList);

		return result;

	}
}
