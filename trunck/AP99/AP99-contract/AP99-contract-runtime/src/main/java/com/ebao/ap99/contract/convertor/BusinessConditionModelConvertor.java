package com.ebao.ap99.contract.convertor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.ebao.ap99.contract.entity.TRiContDeductionsAttach;
import com.ebao.ap99.contract.entity.TRiContDeductionsCarried;
import com.ebao.ap99.contract.entity.TRiContDeductionsItem;
import com.ebao.ap99.contract.entity.TRiContLimitEvent;
import com.ebao.ap99.contract.entity.TRiContLimitItem;
import com.ebao.ap99.contract.entity.TRiContPremiumItem;
import com.ebao.ap99.contract.entity.TRiContReinstateItem;
import com.ebao.ap99.contract.model.BusinessConditionVO;
import com.ebao.ap99.contract.model.CurrencyVO;
import com.ebao.ap99.contract.model.DeductionsItemVO;
import com.ebao.ap99.contract.model.LimitEventVO;
import com.ebao.ap99.contract.model.LimitItemVO;
import com.ebao.ap99.contract.model.PremiumItemVO;
import com.ebao.ap99.contract.model.ReinstateItemVO;
import com.ebao.ap99.contract.model.BusinessModel.BusinessConditionBO;
import com.ebao.ap99.contract.model.BusinessModel.ClausesBO;
import com.ebao.ap99.contract.model.BusinessModel.CurrencyBO;
import com.ebao.ap99.contract.model.BusinessModel.DeductionsBO;
import com.ebao.ap99.contract.model.BusinessModel.DeductionsCommonBO;
import com.ebao.ap99.contract.model.BusinessModel.DeductionsPcBO;
import com.ebao.ap99.contract.model.BusinessModel.LimitBO;
import com.ebao.ap99.contract.model.BusinessModel.LossBO;
import com.ebao.ap99.contract.model.BusinessModel.PremiumBO;
import com.ebao.ap99.contract.model.BusinessModel.ReinstatementBO;
import com.ebao.ap99.contract.model.BusinessModel.ReserveRebateBO;
import com.ebao.ap99.contract.model.BusinessModel.ShareBO;
import com.ebao.ap99.parent.BeanUtils;

public class BusinessConditionModelConvertor {
	/**
	 * 
	 * @param bo
	 * @param vo
	 */
	public static void covertBuinessBOToVO(BusinessConditionBO bo, BusinessConditionVO vo) throws Exception {
		convertBusinessBOAndVO(bo, vo, true);
	}

	public static void convertBusinessVOToBO(BusinessConditionBO bo, BusinessConditionVO vo) throws Exception {
		convertBusinessBOAndVO(bo, vo, false);
	}

	public static void convertBusinessVOToBOForDeductionsDetail(BusinessConditionBO bo, BusinessConditionVO vo)
			throws Exception {
		convertBusinessVOAndBOForDeductionsDetail(bo, vo, false);
	}

	/**
	 * if isOppsite==true, then copy bo to vo, else copy vo to bo
	 * 
	 * @param bo
	 * @param vo
	 * @param isOppsite
	 */
	private static void convertBusinessBOAndVO(BusinessConditionBO bo, BusinessConditionVO vo, boolean isOpposite)
			throws Exception {
		// Basic Info Copy
		BeanUtils.copyPropertiesByDirection(bo, vo, isOpposite);
		// initialize bo
		if (!isOpposite && !vo.isBCEmpty()) {
			initializeBusinessBO(bo);
		}
		// Share Info Copy
		if (isOpposite && bo.getShareBO() != null) {
			BeanUtils.copyPropertiesByDirection(bo.getShareBO(), vo, isOpposite);
		} else if (!isOpposite) {
			if (bo.getShareBO() != null)
				BeanUtils.copyPropertiesByDirection(bo.getShareBO(), vo, isOpposite);
		}
		// Currency Info Copy
		if (isOpposite) {
			if (CollectionUtils.isNotEmpty(bo.getCurrencyBO())) {
				List<CurrencyVO> currencyList = new ArrayList<CurrencyVO>();
				for (CurrencyBO item : bo.getCurrencyBO()) {
					if (!item.getIsActive().equals("N")) {
						CurrencyVO currencyVO = new CurrencyVO();
						BeanUtils.copyProperties(item, currencyVO);
						currencyList.add(currencyVO);
					}
				}
				vo.setCurrencyList(currencyList);
			}
		} else {
			bo.setCurrencyBO(BeanUtils.convertList(vo.getCurrencyList(), CurrencyBO.class));
			List<CurrencyBO> deleteCurrencyBOList = BeanUtils.convertList(vo.getDeleteCurrencyList(), CurrencyBO.class);
			for (CurrencyBO item : deleteCurrencyBOList) {
				item.setIsActive("N");
			}
			bo.getCurrencyBO().addAll(deleteCurrencyBOList);
		}
		// Premium Info Copy
		if (isOpposite && bo.getPremiumBO() != null) {
			covertPremiumBOAndVO(bo.getPremiumBO(), vo, isOpposite);
		} else if (!isOpposite) {
			if (bo.getPremiumBO() != null)
				covertPremiumBOAndVO(bo.getPremiumBO(), vo, isOpposite);
		}
		// Limit Info Copy
		if (isOpposite && bo.getLimitBO() != null) {
			covertLimitBOAndVO(bo.getLimitBO(), vo, isOpposite);
		} else if (!isOpposite) {
			if (bo.getLimitBO() != null)
				covertLimitBOAndVO(bo.getLimitBO(), vo, isOpposite);
		}
		// Reinstatement Info Copy
		if (isOpposite && bo.getReinBO() != null) {
			covertReinstatementBOAndVO(bo.getReinBO(), vo, isOpposite);
		} else if (!isOpposite) {
			if (bo.getReinBO() != null)
				covertReinstatementBOAndVO(bo.getReinBO(), vo, isOpposite);
		}
		// Deductions Info Copy
		if (isOpposite && bo.getDeductionBO() != null) {
			converDeductionsBOAndVO(bo.getDeductionBO(), vo, isOpposite);
		} else if (!isOpposite) {
			if (bo.getDeductionBO() != null)
				converDeductionsBOAndVO(bo.getDeductionBO(), vo, isOpposite);
		}
		// Loss Info Copy
		if (isOpposite && bo.getLossBO() != null) {
			BeanUtils.copyPropertiesByDirection(bo.getLossBO(), vo, isOpposite);
		} else if (!isOpposite) {
			if (bo.getLossBO() != null)
				BeanUtils.copyPropertiesByDirection(bo.getLossBO(), vo, isOpposite);
		}
		// Clauses Info Copy
		if (isOpposite && bo.getClausesBO() != null) {
			BeanUtils.copyPropertiesByDirection(bo.getClausesBO(), vo, isOpposite);
		} else if (!isOpposite) {
			if (bo.getClausesBO() != null)
				BeanUtils.copyPropertiesByDirection(bo.getClausesBO(), vo, isOpposite);
		}
		// Reserve&Reserve Info Copy
		if (isOpposite && bo.getReserveRebateBO() != null) {
			BeanUtils.copyPropertiesByDirection(bo.getReserveRebateBO(), vo, isOpposite);
		} else if (!isOpposite) {
			if (bo.getReserveRebateBO() != null)
				BeanUtils.copyPropertiesByDirection(bo.getReserveRebateBO(), vo, isOpposite);
		}
	}

	private static void initializeBusinessBO(BusinessConditionBO bo) throws Exception {
		if (bo.getShareBO() == null) {
			bo.setShareBO(new ShareBO());
		}
		if (bo.getPremiumBO() == null) {
			bo.setPremiumBO(new PremiumBO());
		}
		if (bo.getLimitBO() == null) {
			bo.setLimitBO(new LimitBO());
		}
		if (bo.getReinBO() == null) {
			bo.setReinBO(new ReinstatementBO());
		}
		if (bo.getDeductionBO() == null) {
			bo.setDeductionBO(new DeductionsBO());
		}
		if (bo.getLossBO() == null) {
			bo.setLossBO(new LossBO());
		}
		if (bo.getClausesBO() == null) {
			bo.setClausesBO(new ClausesBO());
		}
		if (bo.getReserveRebateBO() == null) {
			bo.setReserveRebateBO(new ReserveRebateBO());
		}
	}

	private static void converDeductionsBOAndVO(DeductionsBO bo, BusinessConditionVO vo, boolean isOpposite)
			throws Exception {
		// Deductions Info Copy
		// For pc sliding detail & comm sliding detail, doesn't need copy to
		// page
		BeanUtils.copyPropertiesByDirection(bo, vo, isOpposite);
		if (isOpposite) {
			if (CollectionUtils.isNotEmpty(bo.getTRiContDeductionsItems())) {
				List<DeductionsItemVO> deductionsItemList = new ArrayList<DeductionsItemVO>();
				for (TRiContDeductionsItem item : bo.getTRiContDeductionsItems()) {
					if (!item.getIsActive().equals("N")) {
						DeductionsItemVO deductionsItemVO = new DeductionsItemVO();
						BeanUtils.copyProperties(item, deductionsItemVO);
						deductionsItemList.add(deductionsItemVO);
					}
				}
				vo.setDeductionsList(deductionsItemList);
			}
		} else {
			bo.setTRiContDeductionsItems(BeanUtils.convertList(vo.getDeductionsList(), TRiContDeductionsItem.class));
			List<TRiContDeductionsItem> deleteDeductionsList = BeanUtils.convertList(vo.getDeleteDeductionsList(),
					TRiContDeductionsItem.class);
			for (TRiContDeductionsItem item : deleteDeductionsList) {
				item.setIsActive("N");
			}
			bo.getTRiContDeductionsItems().addAll(deleteDeductionsList);
		}
	}

	private static void convertBusinessVOAndBOForDeductionsDetail(BusinessConditionBO bo, BusinessConditionVO vo,
			boolean isOpposite) throws Exception {
		if (null != bo.getDeductionBO()) {
			// Deductions Info Copy
			BeanUtils.copyPropertiesByDirection(bo.getDeductionBO(), vo, isOpposite);
			// Deductions Comm Sliding detail
			if (null != vo.getCommSlidingDetailId()) {
				if (null == bo.getDeductionBO().getDeductionComm()) {
					bo.getDeductionBO().setDeductionComm(new DeductionsCommonBO());
				}
				BeanUtils.copyPropertiesByDirection(bo.getDeductionBO().getDeductionComm(), vo, isOpposite);
			}
			// Deductions PC Sliding detail
			if (null != vo.getPcSlidingId()) {
				if (null == bo.getDeductionBO().getDeductionPc()) {
					bo.getDeductionBO().setDeductionPc(new DeductionsPcBO());
				}
				BeanUtils.copyPropertiesByDirection(bo.getDeductionBO().getDeductionPc(), vo, isOpposite);
			}
			if (isOpposite) {
				// TODO
			} else {
				if (null != bo.getDeductionBO().getDeductionPc()) {
					bo.getDeductionBO().getDeductionPc().setAttachList(
							BeanUtils.convertList(vo.getAttachTablePcList(), TRiContDeductionsAttach.class));
					List<TRiContDeductionsAttach> deleteAttachTablePcList = BeanUtils
							.convertList(vo.getDeleteAttachTablePcList(), TRiContDeductionsAttach.class);
					for (TRiContDeductionsAttach item : deleteAttachTablePcList) {
						item.setIsActive("N");
					}
					bo.getDeductionBO().getDeductionPc().getAttachList().addAll(deleteAttachTablePcList);
				}

				if (null != bo.getDeductionBO().getDeductionComm()) {
					bo.getDeductionBO().getDeductionComm().setAttachments(
							BeanUtils.convertList(vo.getAttachTableCommList(), TRiContDeductionsAttach.class));
					List<TRiContDeductionsAttach> deleteAttachTableCommList = BeanUtils
							.convertList(vo.getDeleteAttachTableCommList(), TRiContDeductionsAttach.class);
					for (TRiContDeductionsAttach item : deleteAttachTableCommList) {
						item.setIsActive("N");
					}
					bo.getDeductionBO().getDeductionComm().getAttachments().addAll(deleteAttachTableCommList);

					bo.getDeductionBO().getDeductionComm().setCarrieds(
							BeanUtils.convertList(vo.getDeductionsCarriedList(), TRiContDeductionsCarried.class));
				}
			}
		}
	}

	private static void covertReinstatementBOAndVO(ReinstatementBO bo, BusinessConditionVO vo, boolean isOpposite)
			throws Exception {
		BeanUtils.copyPropertiesByDirection(bo, vo, isOpposite);
		if (isOpposite) {
			if (CollectionUtils.isNotEmpty(bo.getTRiContReinstateItems())) {
				List<ReinstateItemVO> reinstateItemList = new ArrayList<ReinstateItemVO>();
				for (TRiContReinstateItem item : bo.getTRiContReinstateItems()) {
					if (!item.getIsActive().equals("N")) {
						ReinstateItemVO reinstateItemVO = new ReinstateItemVO();
						BeanUtils.copyProperties(item, reinstateItemVO);
						reinstateItemList.add(reinstateItemVO);
					}
				}
				vo.setReinstateList(reinstateItemList);
			}
		} else {
			bo.setTRiContReinstateItems(BeanUtils.convertList(vo.getReinstateList(), TRiContReinstateItem.class));
			List<TRiContReinstateItem> deleteReinstateItemList = BeanUtils.convertList(vo.getDeleteReinstateItemList(),
					TRiContReinstateItem.class);
			for (TRiContReinstateItem item : deleteReinstateItemList) {
				item.setIsActive("N");
			}
			bo.getTRiContReinstateItems().addAll(deleteReinstateItemList);
		}
	}

	private static void covertPremiumBOAndVO(PremiumBO bo, BusinessConditionVO vo, boolean isOpposite)
			throws Exception {
		BeanUtils.copyPropertiesByDirection(bo, vo, isOpposite);
		if (isOpposite) {
			if (CollectionUtils.isNotEmpty(bo.getTRiContPremiumItems())) {
				List<PremiumItemVO> premiumItemList = new ArrayList<PremiumItemVO>();
				for (TRiContPremiumItem item : bo.getTRiContPremiumItems()) {
					if (!item.getIsActive().equals("N")) {
						PremiumItemVO premiumItemVO = new PremiumItemVO();
						BeanUtils.copyProperties(item, premiumItemVO);
						premiumItemList.add(premiumItemVO);
					}
				}
				vo.setPremiumList(premiumItemList);
			}
		} else {
			bo.setTRiContPremiumItems(BeanUtils.convertList(vo.getPremiumList(), TRiContPremiumItem.class));
			List<TRiContPremiumItem> deletePremiumItemList = BeanUtils.convertList(vo.getDeletePremiumList(),
					TRiContPremiumItem.class);
			for (TRiContPremiumItem item : deletePremiumItemList) {
				item.setIsActive("N");
			}
			bo.getTRiContPremiumItems().addAll(deletePremiumItemList);
		}
	}

	private static void covertLimitBOAndVO(LimitBO bo, BusinessConditionVO vo, boolean isOpposite) throws Exception {
		BeanUtils.copyPropertiesByDirection(bo, vo, isOpposite);
		if (isOpposite) {
			List<LimitItemVO> limitItemList = BeanUtils.convertList(bo.getTRiContLimitItems(), LimitItemVO.class);
			vo.setLimitItemList(limitItemList);
			if (CollectionUtils.isNotEmpty(bo.getTRiContLimitEvents())) {
				List<LimitEventVO> eventVOList = new ArrayList<LimitEventVO>();
				for (TRiContLimitEvent item : bo.getTRiContLimitEvents()) {
					if (!item.getIsActive().equals("N")) {
						LimitEventVO limitEventVO = new LimitEventVO();
						BeanUtils.copyProperties(item, limitEventVO);
						eventVOList.add(limitEventVO);
					}
				}
				vo.setLimitEventList(eventVOList);
			}
		} else {
			bo.setTRiContLimitItems(BeanUtils.convertList(vo.getLimitItemList(), TRiContLimitItem.class));
			bo.setTRiContLimitEvents(BeanUtils.convertList(vo.getLimitEventList(), TRiContLimitEvent.class));

			List<TRiContLimitItem> deleteLimitItemList = BeanUtils.convertList(vo.getDeleteLimitItemList(),
					TRiContLimitItem.class);
			for (TRiContLimitItem item : deleteLimitItemList) {
				item.setIsActive("N");
			}
			bo.getTRiContLimitItems().addAll(deleteLimitItemList);
			List<TRiContLimitEvent> deleteLimitEventList = BeanUtils.convertList(vo.getDeleteLimitEventList(),
					TRiContLimitEvent.class);
			for (TRiContLimitEvent item : deleteLimitEventList) {
				item.setIsActive("N");
			}
			bo.getTRiContLimitEvents().addAll(deleteLimitEventList);
		}
	}
}
