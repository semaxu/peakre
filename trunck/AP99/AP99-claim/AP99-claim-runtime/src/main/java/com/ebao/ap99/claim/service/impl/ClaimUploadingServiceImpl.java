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
import com.ebao.ap99.claim.convertor.ClaimInfoConvertor;
import com.ebao.ap99.claim.convertor.ClaimSectionInfoConvertor;
import com.ebao.ap99.claim.dao.RiClaimEventDao;
import com.ebao.ap99.claim.dao.RiClaimInfoDao;
import com.ebao.ap99.claim.dao.RiClaimSectionDao;
import com.ebao.ap99.claim.entity.TRiClaimInfo;
import com.ebao.ap99.claim.entity.TRiClaimSectionInfo;
import com.ebao.ap99.claim.model.ClaimInfo;
import com.ebao.ap99.claim.model.ReserveInfo;
import com.ebao.ap99.claim.model.SettlementInfo;
import com.ebao.ap99.claim.model.SettlementItemInfo;
import com.ebao.ap99.claim.service.ClaimUploadingService;
import com.ebao.ap99.claim.service.RiClaimInfoService;
import com.ebao.ap99.claim.service.RiClaimSectionInfoService;
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
public class ClaimUploadingServiceImpl implements ClaimUploadingService, BizService {

	private static Logger logger = LoggerFactory.getLogger();
	@Autowired
	public RiClaimInfoService claimService;

	@Autowired
	public ClaimInfoConvertor claimInfoConvertor;

	@Autowired
	private RiClaimEventDao eventInfoDao;
	@Autowired
	public RiClaimSectionInfoService sectionService;
	@Autowired
	public ClaimSectionInfoConvertor sectionInfoConvertor;
	@Autowired
	public ContractService contractService;
	@Autowired
	public RiClaimSectionDao claimSectionDao;
	@Autowired
	private RiClaimInfoDao claimInfoDao;

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
		ClaimInfo claiminfo = (ClaimInfo) itemVo.getBizVO();

		// claimService.calculateReportingCurrencyAmount(bizVO);//
		// reportingCurrency
		Map<String, List<Long>> result = resetClaimInfo(claiminfo);
		// convert model to entity
		TRiClaimInfo claimEntity = claimInfoConvertor.modelToEntityCascade(claiminfo);

		// save claim info
		TRiClaimInfo claimEntityReturn =  claimService.updateClaimInfo(claimEntity);

		if (CollectionUtils.isNotEmpty(result.get("financialSectionIdList"))) {
			// load and Insert SectionInfo
			List<Long> fSectionIdList = result.get("financialSectionIdList");
			for (int i = 0; i < fSectionIdList.size(); i++) {
				ContractModel contactModel = contractService.getContractInfoByCompId(fSectionIdList.get(i));
				if (null == contactModel) {
					logger.warn("NO contract section found! section ID=" + fSectionIdList.get(i));
					continue;
				}
				TRiClaimSectionInfo section = sectionInfoConvertor.modelToEntity(contactModel);
				section.setRefId(claimEntityReturn.getClaimId());
				section.setRefType(ClaimConstant.REF_TYPE_CLAIM);
				section.setBusinessDirection(ClaimConstant.CLAIM_BUSINESS_DIRECTION__FINANCIAL);
				sectionService.createClaimSectionInfo(section);
			}
		}
		if (CollectionUtils.isNotEmpty(result.get("retroSectionIdList"))) {
			// load and Insert SectionInfo
			List<Long> rSectionIdList = result.get("retroSectionIdList");
			for (int i = 0; i < rSectionIdList.size(); i++) {
				ContractModel contactModel = contractService.getContractInfoByCompId(rSectionIdList.get(i));
				if (null == contactModel) {
					logger.warn("NO contract section found! section ID=" + rSectionIdList.get(i));
					continue;
				}
				TRiClaimSectionInfo section = sectionInfoConvertor.modelToEntity(contactModel);
				section.setRefId(claimEntityReturn.getClaimId());
				section.setRefType(ClaimConstant.REF_TYPE_CLAIM);
				section.setBusinessDirection(ClaimConstant.CLAIM_BUSINESS_DIRECTION__RETROCESSION);
				sectionService.createClaimSectionInfo(section);
			}
		}

		itemVo.setResultFlag(true);
		itemVo.setMsg("success");

	}

	public Map<String, List<Long>> resetClaimInfo(ClaimInfo info) throws Exception {

		// set EventId
		if (StringUtils.isNotBlank(info.getEventCode())) {
			if (eventInfoDao.getEventIdByEventCode(info.getEventCode()) > 0) {
				info.setEventId(eventInfoDao.getEventIdByEventCode(info.getEventCode()));
			}
		}
		// setTaskOwner
		Long userId = claimInfoDao.getUserInfo(info.getTaskOwner()).getUserId();
		info.setTaskOwner(userId.toString());

		// set claim Status
		info.setStatus(ClaimConstant.CLAIM_STATUS__OPEN);

		// set sectionId, retroRefSectionId. load and Insert SectionInfo
		
		// 1.Financial reserve
		List<Long> fSectionIdList = new ArrayList<Long>();
		List<Long> rSectionIdList = new ArrayList<Long>();
		if (CollectionUtils.isNotEmpty(info.getReserveList())) {
			List<ReserveInfo> financialReserveInfoList = new ArrayList<ReserveInfo>();

			for (ReserveInfo re : info.getReserveList()) {
				if (null == re.getSectionId()) {
					if (StringUtils.isNotBlank(re.getContractCode()) && null != re.getUwYear()
							&& null != re.getReinsStarting() && StringUtils.isNotBlank(re.getSectionName())
									&& StringUtils.isNotBlank(re.getSectionType())) {
						
						Long sectionId =contractService.getSectionIdByContractCodeAndSectionInfo(re.getContractCode(),
								re.getUwYear(), re.getReinsStarting(), re.getSectionName(), re.getSectionType()
								);
						re.setSectionId(sectionId);
						// set sectionId
						// re.setSectionId(sectionId); //getsectionId from
						// contract
						// model

					} else {
						throw new Exception(" Can not find SectionId by contractCode: " + re.getContractCode()
								+ ",SectionName: " + re.getSectionName() + " and SectionType:" + re.getSectionType());
					}
				}
				re.setStatus(ClaimConstant.CLAIM_RESERVE_STATUS__NEW);
				re.setRefType(ClaimConstant.REF_TYPE_CLAIM);
				financialReserveInfoList.add(re);
			}
			info.setReserveList(financialReserveInfoList);
			fSectionIdList = financialReserveInfoList.stream().filter(s -> s.getSectionId() != null)
					.map(s -> s.getSectionId()).distinct().collect(Collectors.toList());

		}
		// 2.retrocession reserve
		if (CollectionUtils.isNotEmpty(info.getReserveListRetro())) {
			List<ReserveInfo> retroReserveInfoList = new ArrayList<ReserveInfo>();

			// set sectionId and retroRefSectionId
			for (ReserveInfo re : info.getReserveListRetro()) {
				if (null == re.getRetroRefSectionId()) {
					if (StringUtils.isNotBlank(re.getContractCode()) && (null != re.getUwYear())
							&& null != re.getReinsStarting() && 
							StringUtils.isNotBlank(re.getSectionName()) && 
							StringUtils.isNotBlank(re.getSectionType())) {
						
						Long sectionId =contractService.getSectionIdByContractCodeAndSectionInfo(re.getContractCode(),
								re.getUwYear(), re.getReinsStarting(), re.getSectionName(), re.getSectionType()
								);
						re.setRetroRefSectionId(sectionId);

						// re.setSectionId(sectionId); //getsectionId from
						// contract
						// model

					} else {
						throw new Exception(" Can not find SectionId by contractCode: " + re.getContractCode()
								+ ",SectionName: " + re.getSectionName() + " and SectionType:" + re.getSectionType());
					}
				}

				if (null == re.getSectionId()) {
					if (StringUtils.isNotBlank(re.getRetroRefcontractCode())
							&& null != re.getRetroRefuwYear()
							&& null != re.getRetroRefreinsStarting()
							&& StringUtils.isNotBlank(re.getRetroRefsectionName())
							&& StringUtils.isNotBlank(re.getRetroRefsectionType())) {
						
						Long retroRefsectionId =contractService.getSectionIdByContractCodeAndSectionInfo(re.getRetroRefcontractCode(),
								re.getRetroRefuwYear(), re.getRetroRefreinsStarting(), re.getRetroRefsectionName(), re.getRetroRefsectionType()
								);
						re.setSectionId(retroRefsectionId);
						// re.setRetroRefsectionId(retroRefsectionId);;
						// //getsectionId from
						// contract
						// model

					} else {
						throw new Exception(" Can not find RetroRefSectionId by RetroRefcontractCode: "
								+ re.getRetroRefcontractCode() + ",SectionName: " + re.getRetroRefsectionName()
								+ " and SectionType:" + re.getRetroRefsectionType());
					}
				}

				re.setStatus(ClaimConstant.CLAIM_RESERVE_STATUS__NEW);
				re.setRefType(ClaimConstant.REF_TYPE_CLAIM);
				retroReserveInfoList.add(re);
			}
			info.setReserveListRetro(retroReserveInfoList);
			rSectionIdList = retroReserveInfoList.stream().filter(s -> s.getSectionId() != null)
					.map(s -> s.getSectionId()).distinct().collect(Collectors.toList());
		}

		// financial Settlement
		if (CollectionUtils.isNotEmpty(info.getSettlementList())) {
			List<SettlementInfo> financialSettlementInfo = new ArrayList<SettlementInfo>();
			for (SettlementInfo se : info.getSettlementList()) {
				// reset status
				se.setStatus(ClaimConstant.CLAIM_SETTLEMENT_STATUS__PENDING_SUBMIT);
				se.setRefType(ClaimConstant.REF_TYPE_CLAIM);

				if (CollectionUtils.isNotEmpty(se.getSettlementItemList())) {
					List<SettlementItemInfo> financialSettlementItemInfo = new ArrayList<SettlementItemInfo>();
					for (SettlementItemInfo seItem : se.getSettlementItemList()) {
						if (null == seItem.getSectionId()) {
							if (StringUtils.isNotBlank(seItem.getContractCode()) && null != seItem.getUwYear()
									&& null != seItem.getReinsStarting()
									&& StringUtils.isNotBlank(seItem.getSectionName())
									&& StringUtils.isNotBlank(seItem.getSectionType())) {

								Long sectionId =contractService.getSectionIdByContractCodeAndSectionInfo(seItem.getContractCode(),
										seItem.getUwYear(), seItem.getReinsStarting(), seItem.getSectionName(), seItem.getSectionType()
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
						// seItem.set
						financialSettlementItemInfo.add(seItem);
					}
					se.setSettlementItemList(financialSettlementItemInfo);
				}
				financialSettlementInfo.add(se);
			}
			info.setSettlementList(financialSettlementInfo);
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
						if (null == seItem.getRetroRefSectionId()) {
							if (StringUtils.isNotBlank(seItem.getContractCode())
									&& null != seItem.getUwYear()
									&& null != seItem.getReinsStarting()
									&& StringUtils.isNotBlank(seItem.getSectionName())
									&& StringUtils.isNotBlank(seItem.getSectionType())){
								
								Long sectionId =contractService.getSectionIdByContractCodeAndSectionInfo(seItem.getContractCode(),
										seItem.getUwYear(), seItem.getReinsStarting(), seItem.getSectionName(), seItem.getSectionType()
										);
								seItem.setRetroRefSectionId(new BigDecimal(sectionId));

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

						if (null == seItem.getSectionId()) {
							if (StringUtils.isNotBlank(seItem.getRetroRefcontractCode())
									&& null != seItem.getRetroRefuwYear()
									&& null != seItem.getRetroRefreinsStarting()
									&& StringUtils.isNotBlank(seItem.getRetroRefsectionName())
									&& StringUtils.isNotBlank(seItem.getRetroRefsectionType())) {
								
								Long retroRefsectionId =contractService.getSectionIdByContractCodeAndSectionInfo(seItem.getRetroRefcontractCode(),
										seItem.getRetroRefuwYear(), seItem.getRetroRefreinsStarting(), seItem.getRetroRefsectionName(), seItem.getRetroRefsectionType()
										);
								seItem.setSectionId(new BigDecimal(retroRefsectionId));

								// re.setRetroRefsectionId(retroRefsectionId);;
								// //getsectionId from
								// contract
								// model

							} else {
								throw new Exception(" Can not find RetroRefSectionId by RetroRefcontractCode: "
										+ seItem.getRetroRefcontractCode() + ",SectionName: "
										+ seItem.getRetroRefsectionName() + " and SectionType:"
										+ seItem.getRetroRefsectionType());
							}
						}
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
		result.put("financialSectionIdList", fSectionIdList);
		result.put("retroSectionIdList", rSectionIdList);

		return result;

	}
}
