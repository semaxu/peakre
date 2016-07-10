/**
 * 
 */
package com.ebao.ap99.claim.convertor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.accounting.model.ClaimEntryVO;
import com.ebao.ap99.arap.vo.BusinessFee;
import com.ebao.ap99.arap.vo.BusinessFeeModel;
import com.ebao.ap99.claim.constant.ClaimConstant;
import com.ebao.ap99.claim.dao.RiClaimEventDao;
import com.ebao.ap99.claim.dao.RiClaimInfoDao;
import com.ebao.ap99.claim.dao.RiClaimSectionDao;
import com.ebao.ap99.claim.entity.TRiClaimEvent;
import com.ebao.ap99.claim.entity.TRiClaimInfo;
import com.ebao.ap99.claim.entity.TRiClaimReserve;
import com.ebao.ap99.claim.model.ReserveInfo;
import com.ebao.ap99.contract.service.ContractService;
import com.ebao.ap99.parent.constant.ReInsuranceCst;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.unicorn.platform.foundation.utils.BeanUtils;

/**
 * @author gang.wang
 *
 */
public class ReserveInfoConvertor {
	@Autowired
	private RiClaimSectionDao claimSectionDao;
	@Autowired
	private EntryCodeConvertor entryCodeConvertor;

	@Autowired
	private RiClaimInfoDao claimInfoDao;

	@Autowired
	private RiClaimEventDao eventDao;
	@Autowired
	public ContractService contractService;

	public TRiClaimReserve modelToEntity(ReserveInfo reserveInfo) {
		TRiClaimReserve entity = new TRiClaimReserve();
		BeanUtils.copyProperties(entity, reserveInfo);
		return entity;
	}

	public ReserveInfo entityToModel(TRiClaimReserve entity) {
		ReserveInfo reserveInfo = new ReserveInfo();
		BeanUtils.copyProperties(reserveInfo, entity);
		return reserveInfo;
	}

	public void convertToEntityList(List<ReserveInfo> reserveInfoList, TRiClaimInfo claimEntity,
			String businessDirection) {
		for (ReserveInfo reserveInfo : reserveInfoList) {
			TRiClaimReserve reserveEntity = modelToEntity(reserveInfo);
			reserveEntity.setBusinessDirection(businessDirection);
			claimEntity.addTRiClaimReserve(reserveEntity);
		}
	}

	public List<TRiClaimReserve> convertToEntityList(List<ReserveInfo> reserveInfoList, String businessDirection) {
		List<TRiClaimReserve> reserveEntitys = new ArrayList<>();
		for (ReserveInfo reserveInfo : reserveInfoList) {
			TRiClaimReserve reserveEntity = modelToEntity(reserveInfo);
			reserveEntity.setBusinessDirection(businessDirection);
			reserveEntitys.add(reserveEntity);
		}
		return reserveEntitys;
	}

	public Map<String, List<ReserveInfo>> entityListToModelList(List<TRiClaimReserve> entityList) {
		List<ReserveInfo> financialReserveInfoList = new ArrayList<ReserveInfo>();
		List<ReserveInfo> retroReserveInfoList = new ArrayList<ReserveInfo>();
		entityList.stream()
				.filter(reserve -> reserve.getBusinessDirection().equals(ReInsuranceCst.CONTRACT_CATEGORY_ASSUME))
				// .collect(Collectors.toList())
				.forEach((TRiClaimReserve entity) -> {
					financialReserveInfoList.add(entityToModel(entity));
				});
		entityList.stream()
				.filter(reserve -> reserve.getBusinessDirection().equals(ReInsuranceCst.CONTRACT_CATEGORY_RETRO))
				// .collect(Collectors.toList())
				.forEach((TRiClaimReserve entity) -> {
					retroReserveInfoList.add(entityToModel(entity));
				});

		Map<String, List<ReserveInfo>> result = new HashMap<>();
		result.put("financial", financialReserveInfoList);
		result.put("retro", retroReserveInfoList);
		return result;
	}

	public TRiClaimReserve changeReserve(TRiClaimReserve reserve) {
		reserve.setOrgAmountOc(reserve.getAmountOc());
		reserve.setOrgPostingFlag(reserve.getPostingFlag());
		reserve.setStatus(ClaimConstant.CLAIM_RESERVE_STATUS__INFORCE);
		reserve.setOrgAmountUsd(reserve.getAmountUsd());
		return reserve;
	}

	/**
	 * @param postingType
	 *            : 1.new reserve submit opening:0 closing:amountOc 2.update
	 *            reserve posting change No to yes 3.update reserve posting
	 *            change Yes to No 4.update reserve amount change only
	 * @return
	 */
	public List<ClaimEntryVO> reservePostAccounting(TRiClaimReserve reserve, String postingType) {
		List<ClaimEntryVO> claimEntryVOList = new ArrayList<>();
		String refNo;
		Date dateOfLoss;
		Long contractId = claimSectionDao.getContractIdBySectionId(reserve.getSectionId().longValue());
		if (reserve.getRefType().equals(ClaimConstant.REF_TYPE_CLAIM)) {
			TRiClaimInfo claimEntity = claimInfoDao.load(reserve.getRefId());
			refNo = claimEntity.getClaimNo();
			dateOfLoss = claimEntity.getDateOfLossFrom();
		} else {
			TRiClaimEvent eventEntity = eventDao.load(reserve.getRefId());
			refNo = eventEntity.getEventCode();
			dateOfLoss = eventEntity.getDateOfLossFrom();
		}

		if (reserve.getReserveType().equals(ClaimConstant.LOSS)
				|| reserve.getReserveType().equals(ClaimConstant.EXPENSE)
				|| reserve.getReserveType().equals(ClaimConstant.ADDITIONAL)) {
			ClaimEntryVO claimEntryOp = getClaimEntryVOList(reserve, postingType, ClaimConstant.ENTRY_TYPE_OPENING,
					contractId, refNo,dateOfLoss);
			claimEntryVOList.add(claimEntryOp);
			ClaimEntryVO claimEntryCl = getClaimEntryVOList(reserve, postingType, ClaimConstant.ENTRY_TYPE_CLOSING,
					contractId,  refNo,dateOfLoss);
			claimEntryVOList.add(claimEntryCl);
		} else {
			List<ClaimEntryVO> otherClaimEntry = getOtherClaimEntryVOList(reserve, postingType, contractId,
					 refNo,dateOfLoss);
			claimEntryVOList.addAll(otherClaimEntry);
		}
		return claimEntryVOList;
	}

	/**
	 * 
	 * @param reserve
	 * @param postingType
	 * @param entryType
	 * @param contractId
	 * @param claimEntity
	 * @return
	 */
	public ClaimEntryVO getClaimEntryVOList(TRiClaimReserve reserve, String postingType, String entryType,
			Long contractId,String refNo ,Date dateOfLoss) {
		ClaimEntryVO claimEntry = new ClaimEntryVO();

		// BusinessFee businessFee = new BusinessFee();

		//Long sectionLevelId = contractService.getLevelContCompIdList(reserve.getSectionId(), "2").get(0);
		claimEntry.setEntryCode(entryCodeConvertor.conertorEntryCode(reserve.getReserveType(),
				ClaimConstant.BUSI_TYPE_RESERVE, entryType));
		claimEntry.setCurrency(reserve.getOriginalCurrency());
		claimEntry.setClaimNo(refNo);
		claimEntry.setContractId(contractId);
		claimEntry.setSectionId(contractService.getLevelContCompIdList(reserve.getSectionId(), "2").get(0));
		claimEntry.setDateOfLoss(dateOfLoss);
		claimEntry.setBusinessDirection(reserve.getBusinessDirection());
		claimEntry.setBizTransType(ClaimConstant.BIZ_CLAIM_RESERVE);
		claimEntry.setRefType(reserve.getRefType());
		
		if (postingType.equals(ClaimConstant.NEW_RESERVE_SUBMIT)
				|| postingType.equals(ClaimConstant.UPDATE_RESERVE_POSTING_NO_TO_YES)) {
			// 1. new reserve submit: opening:0 closing:amountOc
			// 2. update reserve : posting change No to yes
			// opening:0 closing:amountOc
			if (entryType.equals(ClaimConstant.ENTRY_TYPE_OPENING)) {
				claimEntry.setAmount(new BigDecimal(0));
			} else {
				claimEntry.setAmount(reserve.getAmountOc());
			}
		} else if (postingType.equals(ClaimConstant.UPDATE_RESERVE_POSTING_YES_TO_NO)) {
			// 3. update reserve : posting change Yes to No
			// opening:-orgAmountOc closing:0
			if (entryType.equals(ClaimConstant.ENTRY_TYPE_OPENING)) {
				claimEntry.setAmount(reserve.getOrgAmountOc());
			} else {
				claimEntry.setAmount(new BigDecimal(0));
			}
		} else if (postingType.equals(ClaimConstant.UPDATE_RESERVE_AMOUNT_CHANGE_ONLY)) {
			// 4. update reserve : amountOc change
			// opening:-orgAmountOc closing:amountOc
			if (entryType.equals(ClaimConstant.ENTRY_TYPE_OPENING)) {
				claimEntry.setAmount(reserve.getOrgAmountOc());
			} else {
				claimEntry.setAmount(reserve.getAmountOc());
			}
		}

		return claimEntry;
	}

	/**
	 * 
	 * @param reserve
	 * @param postingType
	 * @return
	 */
	public List<ClaimEntryVO> getOtherClaimEntryVOList(TRiClaimReserve reserve, String postingType, Long contractId,
			String refNo ,Date dateOfLoss) {
		List<ClaimEntryVO> claimEntryList = new ArrayList<>();
		String entryType = ClaimConstant.ENTRY_TYPE_OPENING;

		if (postingType.equals(ClaimConstant.NEW_RESERVE_SUBMIT)
				|| postingType.equals(ClaimConstant.UPDATE_RESERVE_POSTING_NO_TO_YES)) {
			// 1. new reserve submit
			// 2. update reserve : posting change No to yes
			// amountOc

			ClaimEntryVO claimEntry = getOtherClaimEntryVOPost(reserve, entryType, contractId,  refNo,dateOfLoss);
			claimEntryList.add(claimEntry);

		} else if (postingType.equals(ClaimConstant.UPDATE_RESERVE_POSTING_YES_TO_NO)) {
			// 3. update reserve : posting change Yes to No
			// offSet:-orgAmountOc
			ClaimEntryVO offSetClaimEntry = getOtherOffsetClaimEntryVOPost(reserve, entryType, contractId,  refNo,dateOfLoss);
			claimEntryList.add(offSetClaimEntry);

		} else if (postingType.equals(ClaimConstant.UPDATE_RESERVE_AMOUNT_CHANGE_ONLY)) {
			// 4. update reserve : amountOc change
			// opening:-orgAmountOc closing:amountOc

			ClaimEntryVO offSetClaimEntry = getOtherOffsetClaimEntryVOPost(reserve, entryType, contractId,  refNo,dateOfLoss);
			offSetClaimEntry.setAmount(reserve.getAmountOc().subtract(reserve.getOrgAmountOc()));
			claimEntryList.add(offSetClaimEntry);

		}
		return claimEntryList;
	}

	public ClaimEntryVO getOtherClaimEntryVOPost(TRiClaimReserve reserve, String entryType, Long contractId,
			String refNo ,Date dateOfLoss) {
		ClaimEntryVO claimEntry = new ClaimEntryVO();

		claimEntry.setEntryCode(entryCodeConvertor.conertorEntryCode(reserve.getReserveType(),
				ClaimConstant.BUSI_TYPE_RESERVE, entryType));
		claimEntry.setCurrency(reserve.getOriginalCurrency());
		claimEntry.setClaimNo(refNo);
		claimEntry.setContractId(contractId);
		claimEntry.setSectionId(contractService.getLevelContCompIdList(reserve.getSectionId(), "2").get(0));
		claimEntry.setDateOfLoss(dateOfLoss);
		claimEntry.setAmount(reserve.getAmountOc());
		claimEntry.setBusinessDirection(reserve.getBusinessDirection());
		claimEntry.setBizTransType(ClaimConstant.BIZ_CLAIM_RESERVE);
		claimEntry.setRefType(reserve.getRefType());


		return claimEntry;
	}

	public ClaimEntryVO getOtherOffsetClaimEntryVOPost(TRiClaimReserve reserve, String entryType, Long contractId,
			String refNo ,Date dateOfLoss) {
		ClaimEntryVO claimEntry = new ClaimEntryVO();

		claimEntry.setEntryCode(entryCodeConvertor.conertorEntryCode(reserve.getReserveType(),
				ClaimConstant.BUSI_TYPE_RESERVE, entryType));
		claimEntry.setCurrency(reserve.getOriginalCurrency());
		claimEntry.setClaimNo(refNo);
		claimEntry.setContractId(contractId);
		claimEntry.setSectionId(contractService.getLevelContCompIdList(reserve.getSectionId(), "2").get(0));
		claimEntry.setDateOfLoss(dateOfLoss);
		claimEntry.setAmount(reserve.getOrgAmountOc().multiply(new BigDecimal(-1)));
		claimEntry.setBusinessDirection(reserve.getBusinessDirection());
		claimEntry.setBizTransType(ClaimConstant.BIZ_CLAIM_RESERVE);
		claimEntry.setRefType(reserve.getRefType());

		return claimEntry;
	}

	/**
	 * reserve Converter posting
	 * 
	 * @param reserve
	 * @param postingType
	 *            : 1.new reserve submit opening:0 closing:amountOc 2.update
	 *            reserve posting change No to yes 3.update reserve posting
	 *            change Yes to No 4.update reserve amount change only
	 * @return
	 */
	public BusinessFeeModel reserveConvertorPosting(TRiClaimReserve reserve, String postingType) {

		Long contractId = claimSectionDao.getContractIdBySectionId(reserve.getSectionId().longValue());

		BusinessFeeModel businessFeeModel = new BusinessFeeModel();
		if (reserve.getBusinessDirection().equals(ClaimConstant.CLAIM_BUSINESS_DIRECTION__FINANCIAL)) {
			businessFeeModel.setContractCategory(ClaimConstant.CONTRACTCATEGORY_ASSUMED);
		} else {
			businessFeeModel.setContractCategory(ClaimConstant.CONTRACTCATEGORY_RETRO);
		}
		businessFeeModel.setBizTransType(ClaimConstant.BIZ_CLAIM_RESERVE);
		businessFeeModel.setBizTransNo(reserve.getReserveId().toString());
		businessFeeModel.setBizTransId(reserve.getReserveId());
		businessFeeModel.setContractId(contractId);
		businessFeeModel.setBizOperatorId(AppContext.getAppUser().getUserId());
		// businessFeeModel.setPartnerCode(null);

		List<BusinessFee> businessFeeList = new ArrayList<>();
		if (reserve.getReserveType().equals(ClaimConstant.LOSS)
				|| reserve.getReserveType().equals(ClaimConstant.EXPENSE)
				|| reserve.getReserveType().equals(ClaimConstant.ADDITIONAL)) {
			BusinessFee businessFeeOpen = getBusinessFeeList(reserve, postingType, ClaimConstant.ENTRY_TYPE_OPENING);
			businessFeeList.add(businessFeeOpen);

			BusinessFee businessFeeClose = getBusinessFeeList(reserve, postingType, ClaimConstant.ENTRY_TYPE_CLOSING);
			businessFeeList.add(businessFeeClose);
		} else {
			List<BusinessFee> otherFeeList = getOtherFeeList(reserve, postingType);
			businessFeeList.addAll(otherFeeList);
		}
		businessFeeModel.setFeeList(businessFeeList);

		return businessFeeModel;
	}

	/**
	 * LOSS_RESERVE,EXPENSE,ADDITIONAL_LOSS
	 * 
	 * @param reserve
	 *            reserveList
	 * @param postingType
	 * @param entryCode
	 * @return
	 */

	public BusinessFee getBusinessFeeList(TRiClaimReserve reserve, String postingType, String entryType) {
		BusinessFee businessFee = new BusinessFee();

		businessFee.setEntryCode(entryCodeConvertor.conertorEntryCode(reserve.getReserveType(),
				ClaimConstant.BUSI_TYPE_RESERVE, entryType));
		businessFee.setCurrencyCode(reserve.getOriginalCurrency());
		businessFee.setDueDate(reserve.getUpdateTime());

		// businessFee.setCurrentPeriod(0);
		// businessFee.setTotalPeriod(0);
		// businessFee.setEstimation(false);
		businessFee.setNeedPost(true);
		businessFee.setBizRefId(reserve.getReserveId().longValue());
		businessFee.setSectionId(reserve.getSectionId());

		if (postingType.equals(ClaimConstant.NEW_RESERVE_SUBMIT)
				|| postingType.equals(ClaimConstant.UPDATE_RESERVE_POSTING_NO_TO_YES)) {
			// 1. new reserve submit: opening:0 closing:amountOc
			// 2. update reserve : posting change No to yes
			// opening:0 closing:amountOc
			if (entryType.equals(ClaimConstant.ENTRY_TYPE_OPENING)) {
				businessFee.setAmount(new BigDecimal(0));
			} else {
				businessFee.setAmount(reserve.getAmountOc());
			}
		} else if (postingType.equals(ClaimConstant.UPDATE_RESERVE_POSTING_YES_TO_NO)) {
			// 3. update reserve : posting change Yes to No
			// opening:-orgAmountOc closing:0
			if (entryType.equals(ClaimConstant.ENTRY_TYPE_OPENING)) {
				businessFee.setAmount(reserve.getOrgAmountOc());
			} else {
				businessFee.setAmount(new BigDecimal(0));
			}
		} else if (postingType.equals(ClaimConstant.UPDATE_RESERVE_AMOUNT_CHANGE_ONLY)) {
			// 4. update reserve : amountOc change
			// opening:-orgAmountOc closing:amountOc
			if (entryType.equals(ClaimConstant.ENTRY_TYPE_OPENING)) {
				businessFee.setAmount(reserve.getOrgAmountOc());
			} else {
				businessFee.setAmount(reserve.getAmountOc());
			}
		}

		return businessFee;
	}

	/**
	 * RIP,TAX,OTHERS
	 * 
	 * @param reserve
	 * @param postingType
	 * @param entryCode
	 * @return
	 */

	public List<BusinessFee> getOtherFeeList(TRiClaimReserve reserve, String postingType) {
		List<BusinessFee> businessFeeList = new ArrayList<>();
		String entryType = ClaimConstant.ENTRY_TYPE_OPENING;

		if (postingType.equals(ClaimConstant.NEW_RESERVE_SUBMIT)
				|| postingType.equals(ClaimConstant.UPDATE_RESERVE_POSTING_NO_TO_YES)) {
			// 1. new reserve submit
			// 2. update reserve : posting change No to yes
			// amountOc

			BusinessFee businessFee = getOtherBusiFeePost(reserve, entryType);
			businessFeeList.add(businessFee);

		} else if (postingType.equals(ClaimConstant.UPDATE_RESERVE_POSTING_YES_TO_NO)) {
			// 3. update reserve : posting change Yes to No
			// offSet:-orgAmountOc
			BusinessFee offSetBusinessFee = getOtherOffsetBusiFeePost(reserve, entryType);
			businessFeeList.add(offSetBusinessFee);

		} else if (postingType.equals(ClaimConstant.UPDATE_RESERVE_AMOUNT_CHANGE_ONLY)) {
			// 4. update reserve : amountOc change
			// opening:-orgAmountOc closing:amountOc
			// BusinessFee businessFee = getOtherBusiFeePost(reserve,
			// entryType);
			// businessFeeList.add(businessFee);

			BusinessFee offSetBusinessFee = getOtherOffsetBusiFeePost(reserve, entryType);
			// reserve.getAmountOc().subtract(reserve.getOrgAmountOc())
			offSetBusinessFee.setAmount(reserve.getAmountOc().subtract(reserve.getOrgAmountOc()));
			businessFeeList.add(offSetBusinessFee);

		}
		return businessFeeList;
	}

	public BusinessFee getOtherBusiFeePost(TRiClaimReserve reserve, String entryType) {
		BusinessFee businessFee = new BusinessFee();

		businessFee.setEntryCode(entryCodeConvertor.conertorEntryCode(reserve.getReserveType(),
				ClaimConstant.BUSI_TYPE_RESERVE, entryType));
		businessFee.setCurrencyCode(reserve.getOriginalCurrency());
		businessFee.setDueDate(reserve.getUpdateTime());

		// businessFee.setCurrentPeriod(0);
		// businessFee.setTotalPeriod(0);
		businessFee.setEstimation(true);
		businessFee.setNeedPost(true);
		businessFee.setBizRefId(reserve.getReserveId().longValue());
		businessFee.setSectionId(reserve.getSectionId());
		businessFee.setAmount(reserve.getAmountOc());

		return businessFee;
	}

	public BusinessFee getOtherOffsetBusiFeePost(TRiClaimReserve reserve, String entryType) {
		BusinessFee offSetBusinessFee = new BusinessFee();
		offSetBusinessFee.setEntryCode(entryCodeConvertor.conertorEntryCode(reserve.getReserveType(),
				ClaimConstant.BUSI_TYPE_RESERVE, entryType));
		offSetBusinessFee.setCurrencyCode(reserve.getOriginalCurrency());
		offSetBusinessFee.setDueDate(reserve.getUpdateTime());

		// businessFee.setCurrentPeriod(0);
		// businessFee.setTotalPeriod(0);
		offSetBusinessFee.setEstimation(true);
		offSetBusinessFee.setNeedPost(true);
		offSetBusinessFee.setBizRefId(reserve.getReserveId().longValue());
		offSetBusinessFee.setSectionId(reserve.getSectionId());
		offSetBusinessFee.setAmount(reserve.getOrgAmountOc().multiply(new BigDecimal(-1)));

		return offSetBusinessFee;
	}
}
