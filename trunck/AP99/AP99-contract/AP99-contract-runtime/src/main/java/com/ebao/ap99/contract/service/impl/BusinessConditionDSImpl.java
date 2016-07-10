package com.ebao.ap99.contract.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.arap.constant.CurrencyConstants;
import com.ebao.ap99.arap.service.CurrencyExchangeService;
import com.ebao.ap99.contract.convertor.BusinessConditionModelConvertor;
import com.ebao.ap99.contract.dao.TRiContClausesDao;
import com.ebao.ap99.contract.dao.TRiContClausesLogDao;
import com.ebao.ap99.contract.dao.TRiContCurrencyDao;
import com.ebao.ap99.contract.dao.TRiContCurrencyLogDao;
import com.ebao.ap99.contract.dao.TRiContDeductionsAttLogDao;
import com.ebao.ap99.contract.dao.TRiContDeductionsAttachDao;
import com.ebao.ap99.contract.dao.TRiContDeductionsCarriedDao;
import com.ebao.ap99.contract.dao.TRiContDeductionsCarriedLogDao;
import com.ebao.ap99.contract.dao.TRiContDeductionsCommDao;
import com.ebao.ap99.contract.dao.TRiContDeductionsCommLogDao;
import com.ebao.ap99.contract.dao.TRiContDeductionsDao;
import com.ebao.ap99.contract.dao.TRiContDeductionsItemLogDao;
import com.ebao.ap99.contract.dao.TRiContDeductionsLogDao;
import com.ebao.ap99.contract.dao.TRiContDeductionsPcDao;
import com.ebao.ap99.contract.dao.TRiContDeductionsPcLogDao;
import com.ebao.ap99.contract.dao.TRiContLimitDao;
import com.ebao.ap99.contract.dao.TRiContLimitEventLogDao;
import com.ebao.ap99.contract.dao.TRiContLimitItemLogDao;
import com.ebao.ap99.contract.dao.TRiContLimitLogDao;
import com.ebao.ap99.contract.dao.TRiContLossDao;
import com.ebao.ap99.contract.dao.TRiContLossLogDao;
import com.ebao.ap99.contract.dao.TRiContPremiumDao;
import com.ebao.ap99.contract.dao.TRiContPremiumItemLogDao;
import com.ebao.ap99.contract.dao.TRiContPremiumLogDao;
import com.ebao.ap99.contract.dao.TRiContReinstateDao;
import com.ebao.ap99.contract.dao.TRiContReinstateItemLogDao;
import com.ebao.ap99.contract.dao.TRiContReinstateLogDao;
import com.ebao.ap99.contract.dao.TRiContReserveRebateDao;
import com.ebao.ap99.contract.dao.TRiContReserveRebateLogDao;
import com.ebao.ap99.contract.dao.TRiContShareDao;
import com.ebao.ap99.contract.dao.TRiContShareLogDao;
import com.ebao.ap99.contract.dao.TRiContractStructureDao;
import com.ebao.ap99.contract.entity.TRiContClauses;
import com.ebao.ap99.contract.entity.TRiContClausesLog;
import com.ebao.ap99.contract.entity.TRiContCurrency;
import com.ebao.ap99.contract.entity.TRiContCurrencyLog;
import com.ebao.ap99.contract.entity.TRiContDeductions;
import com.ebao.ap99.contract.entity.TRiContDeductionsAttLog;
import com.ebao.ap99.contract.entity.TRiContDeductionsAttach;
import com.ebao.ap99.contract.entity.TRiContDeductionsCarrLog;
import com.ebao.ap99.contract.entity.TRiContDeductionsCarried;
import com.ebao.ap99.contract.entity.TRiContDeductionsComm;
import com.ebao.ap99.contract.entity.TRiContDeductionsCommLog;
import com.ebao.ap99.contract.entity.TRiContDeductionsItem;
import com.ebao.ap99.contract.entity.TRiContDeductionsItemLog;
import com.ebao.ap99.contract.entity.TRiContDeductionsLog;
import com.ebao.ap99.contract.entity.TRiContDeductionsPc;
import com.ebao.ap99.contract.entity.TRiContDeductionsPcLog;
import com.ebao.ap99.contract.entity.TRiContLimit;
import com.ebao.ap99.contract.entity.TRiContLimitEvent;
import com.ebao.ap99.contract.entity.TRiContLimitEventLog;
import com.ebao.ap99.contract.entity.TRiContLimitItem;
import com.ebao.ap99.contract.entity.TRiContLimitItemLog;
import com.ebao.ap99.contract.entity.TRiContLimitLog;
import com.ebao.ap99.contract.entity.TRiContLoss;
import com.ebao.ap99.contract.entity.TRiContLossLog;
import com.ebao.ap99.contract.entity.TRiContPremium;
import com.ebao.ap99.contract.entity.TRiContPremiumItem;
import com.ebao.ap99.contract.entity.TRiContPremiumItemLog;
import com.ebao.ap99.contract.entity.TRiContPremiumLog;
import com.ebao.ap99.contract.entity.TRiContReinstate;
import com.ebao.ap99.contract.entity.TRiContReinstateItem;
import com.ebao.ap99.contract.entity.TRiContReinstateItemLog;
import com.ebao.ap99.contract.entity.TRiContReinstateLog;
import com.ebao.ap99.contract.entity.TRiContReserveRebate;
import com.ebao.ap99.contract.entity.TRiContReserveRebateLog;
import com.ebao.ap99.contract.entity.TRiContShare;
import com.ebao.ap99.contract.entity.TRiContShareLog;
import com.ebao.ap99.contract.model.BusinessConditionVO;
import com.ebao.ap99.contract.model.CurrencyVO;
import com.ebao.ap99.contract.model.DeductionsAttachVO;
import com.ebao.ap99.contract.model.DeductionsCarriedVO;
import com.ebao.ap99.contract.model.DeductionsCommVO;
import com.ebao.ap99.contract.model.DeductionsItemVO;
import com.ebao.ap99.contract.model.DeductionsPcVO;
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
import com.ebao.ap99.contract.service.BusinessConditionDS;
import com.ebao.ap99.contract.service.ContractDS;
import com.ebao.ap99.contract.util.ContractCst;
import com.ebao.ap99.parent.BeanUtils;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class BusinessConditionDSImpl implements BusinessConditionDS {
	@Autowired
	private TRiContPremiumDao premiumDao;
	@Autowired
	private TRiContReserveRebateDao reserveRebateDao;
	@Autowired
	private TRiContReserveRebateLogDao reserveRebateLogDao;
	@Autowired
	private TRiContShareDao shareDao;
	@Autowired
	private TRiContShareLogDao shareLogDao;
	@Autowired
	private TRiContLossDao lossDao;
	@Autowired
	private TRiContLossLogDao lossLogDao;
	@Autowired
	private TRiContDeductionsDao deductionsDao;
	@Autowired
	private TRiContDeductionsPcDao pcSlidingDetailDao;
	@Autowired
	private TRiContDeductionsAttachDao attachmentTablePcDao;
	@Autowired
	private TRiContDeductionsAttachDao attachmentTableSsDao;
	@Autowired
	private TRiContDeductionsCommDao commSlidingDetailDao;
	@Autowired
	private TRiContDeductionsCarriedDao deductionsCarriedDao;
	@Autowired
	private TRiContDeductionsCarriedLogDao deductionsCarriedLogDao;
	@Autowired
	private TRiContDeductionsLogDao deductionsLogDao;
	@Autowired
	private TRiContDeductionsItemLogDao deductionsItemLogDao;
	@Autowired
	private TRiContDeductionsPcLogDao deductionsPcLogDao;
	@Autowired
	private TRiContDeductionsPcDao deductionsPcDao;
	@Autowired
	private TRiContDeductionsCommDao deductionsCommDao;
	@Autowired
	private TRiContDeductionsCommLogDao deductionsCommLogDao;
	@Autowired
	private TRiContDeductionsAttachDao deductionsAttachDao;
	@Autowired
	private TRiContDeductionsAttLogDao deductionsAttLogDao;
	@Autowired
	private TRiContLimitDao limitDao;
	@Autowired
	private TRiContLimitLogDao limitLogDao;
	@Autowired
	private TRiContLimitEventLogDao limitEventLogDao;
	@Autowired
	private TRiContLimitItemLogDao limitItemLogDao;
	@Autowired
	private TRiContClausesDao clausesDao;
	@Autowired
	private TRiContClausesLogDao clausesLogDao;
	@Autowired
	private TRiContCurrencyDao currencyDao;
	@Autowired
	private TRiContCurrencyLogDao currencyLogDao;
	@Autowired
	private TRiContReinstateDao reinstateDao;
	@Autowired
	TRiContReinstateLogDao reinstateLogDao;
	@Autowired
	private TRiContReinstateItemLogDao reinstateItemLogDao;
	@Autowired
	private TRiContPremiumLogDao premiumLogDao;
	@Autowired
	private TRiContPremiumItemLogDao premiumItemLogDao;
	@Autowired
	private TRiContractStructureDao structureDao;
	@Autowired
	private CurrencyExchangeService currencyExchangeService;
	@Autowired
	private ContractDS contractDS;

	private static Logger logger = LoggerFactory.getLogger();

	public void saveBusinessBO(BusinessConditionBO bo) throws IllegalAccessException, InvocationTargetException {
		bindingContCompIdConForBusinessBO(bo);
		if (bo.getPremiumBO() != null) {
			this.savePremium(bo.getPremiumBO());
		}
		if (bo.getReserveRebateBO() != null) {
			this.saveReserveRebate(bo.getReserveRebateBO());
		}
		if (bo.getShareBO() != null) {
			this.saveShare(bo.getShareBO());
		}
		if (bo.getLossBO() != null) {
			this.saveLoss(bo.getLossBO());
		}
		if (bo.getDeductionBO() != null) {
			this.saveDeductions(bo.getDeductionBO());
		}
		if (bo.getClausesBO() != null) {
			this.saveClauses(bo.getClausesBO());
		}
		if (bo.getCurrencyBO() != null) {
			this.saveCurrency(bo);
		}
		if (bo.getReinBO() != null) {
			this.saveReinstate(bo.getReinBO());
		}
		if (bo.getLimitBO() != null) {
			this.saveLimit(bo.getLimitBO());
		}
	}

	public void bindingContCompIdConForBusinessBO(BusinessConditionBO bo)
			throws IllegalAccessException, InvocationTargetException {
		if (bo.getPremiumBO() != null) {
			bo.getPremiumBO().setContCompId(bo.getContCompId());
		}
		if (bo.getReserveRebateBO() != null) {
			bo.getReserveRebateBO().setContCompId(bo.getContCompId());
		}
		if (bo.getShareBO() != null) {
			bo.getShareBO().setContCompId(bo.getContCompId());
		}
		if (bo.getLossBO() != null) {
			bo.getLossBO().setContCompId(bo.getContCompId());
		}
		if (bo.getReserveRebateBO() != null) {
			bo.getReserveRebateBO().setContCompId(bo.getContCompId());
		}
		if (bo.getDeductionBO() != null) {
			bo.getDeductionBO().setContCompId(bo.getContCompId());
		}
		if (bo.getClausesBO() != null) {
			bo.getClausesBO().setContCompId(bo.getContCompId());
		}
		if (bo.getReinBO() != null) {
			bo.getReinBO().setContCompId(bo.getContCompId());
		}
		if (bo.getLimitBO() != null) {
			bo.getLimitBO().setContCompId(bo.getContCompId());
		}
	}

	public void saveClauses(ClausesBO vo) throws IllegalAccessException, InvocationTargetException {
		TRiContClauses clausesVO = new TRiContClauses();
		BeanUtils.copyPropertiesIngoreNull(clausesVO, vo);
		clausesVO = clausesDao.save(clausesVO);
		vo.setClausesId(clausesVO.getClausesId());
	}

	public void saveReserveRebate(ReserveRebateBO vo) throws IllegalAccessException, InvocationTargetException {
		TRiContReserveRebate reserveRebateVO = new TRiContReserveRebate();
		BeanUtils.copyPropertiesIngoreNull(reserveRebateVO, vo);
		reserveRebateVO = reserveRebateDao.save(reserveRebateVO);
		vo.setReserveId(reserveRebateVO.getReserveId());
	}

	public void saveShare(ShareBO vo) throws IllegalAccessException, InvocationTargetException {
		TRiContShare shareVO = new TRiContShare();
		BeanUtils.copyPropertiesIngoreNull(shareVO, vo);
		shareVO = shareDao.save(shareVO);
		vo.setShareId(shareVO.getShareId());
	}

	public void saveLoss(LossBO vo) throws IllegalAccessException, InvocationTargetException {
		TRiContLoss lossVO = new TRiContLoss();
		BeanUtils.copyPropertiesIngoreNull(lossVO, vo);
		lossVO = lossDao.save(lossVO);
		vo.setLossId(lossVO.getLossId());
	}

	public void savePremium(PremiumBO vo) throws IllegalAccessException, InvocationTargetException {
		TRiContPremium premiumVO = new TRiContPremium();
		BeanUtils.copyPropertiesIngoreNull(premiumVO, vo);
		for (TRiContPremiumItem item : premiumVO.getTRiContPremiumItems()) {
			item.setTRiContPremium(premiumVO);
		}
		premiumVO = premiumDao.save(premiumVO);
		BeanUtils.copyPropertiesIngoreNull(vo, premiumVO);
		vo.setPremiumId(premiumVO.getPremiumId());
	}

	public void saveDeductions(DeductionsBO bo) throws IllegalAccessException, InvocationTargetException {
		TRiContDeductions deductionsVO = new TRiContDeductions();
		BeanUtils.copyPropertiesIngoreNull(deductionsVO, bo);
		for (TRiContDeductionsItem item : deductionsVO.getTRiContDeductionsItems()) {
			item.setTRiContDeductions(deductionsVO);
		}
		deductionsVO = deductionsDao.save(deductionsVO);
		BeanUtils.copyPropertiesIngoreNull(bo, deductionsVO);
		bo.setDeductionsId(deductionsVO.getDeductionsId());
		if (bo.getDeductionPc() != null) {
			bo.getDeductionPc().setDeductionsId(bo.getDeductionsId());
			savePcSlidingDetail(bo.getDeductionPc());
		}
		if (bo.getDeductionComm() != null) {
			bo.getDeductionComm().setDeductionsId(bo.getDeductionsId());
			saveCommSlidingDetail(bo.getDeductionComm());
		}

	}

	public void savePcSlidingDetail(DeductionsPcVO vo) throws Exception {
		TRiContDeductionsPc pcSlidingDetailVO = new TRiContDeductionsPc();
		BeanUtils.copyPropertiesIngoreNull(pcSlidingDetailVO, vo);
		pcSlidingDetailVO = pcSlidingDetailDao.save(pcSlidingDetailVO);
		vo.setDeductionsId(pcSlidingDetailVO.getDeductionsId());
		vo.setPcSlidingId(pcSlidingDetailVO.getPcSlidingId());
		List<DeductionsAttachVO> attachmentTablePcList = vo.getDeductionsAttach();
		List<DeductionsAttachVO> deleteDeductionsAttachs = BeanUtils.convertList(vo.getDeleteDeductionsAttach(),
				DeductionsAttachVO.class);
		for (DeductionsAttachVO item : deleteDeductionsAttachs) {
			item.setIsActive("N");
		}
		attachmentTablePcList.addAll(deleteDeductionsAttachs);
		for (DeductionsAttachVO attachmentTablePcVO : attachmentTablePcList) {
			attachmentTablePcVO.setSlidingId(pcSlidingDetailVO.getPcSlidingId());
			attachmentTablePcVO.setType(ContractCst.CONTRACT_DEDUCTION_DETAIL_TYPE_PC);
			TRiContDeductionsAttach attachTablePc = new TRiContDeductionsAttach();
			BeanUtils.copyPropertiesIngoreNull(attachTablePc, attachmentTablePcVO);
			attachTablePc = attachmentTablePcDao.save(attachTablePc);
			attachmentTablePcVO.setAttachmentId(attachTablePc.getAttachmentId());
		}
		// remove the deleted record
		List<DeductionsAttachVO> attachList = new ArrayList<DeductionsAttachVO>();
		for (DeductionsAttachVO item : vo.getDeductionsAttach()) {
			if (null == item.getIsActive() || !item.getIsActive().equals("N")) {
				DeductionsAttachVO attachVO = new DeductionsAttachVO();
				BeanUtils.copyProperties(item, attachVO);
				attachList.add(attachVO);
			}
		}
		vo.setDeductionsAttach(attachList);
	}

	public void savePcSlidingDetail(DeductionsPcBO bo) throws IllegalAccessException, InvocationTargetException {
		if (bo != null) {
			TRiContDeductionsPc pcSlidingDetailVO = new TRiContDeductionsPc();
			BeanUtils.copyPropertiesIngoreNull(pcSlidingDetailVO, bo);
			pcSlidingDetailVO = pcSlidingDetailDao.save(pcSlidingDetailVO);
			bo.setDeductionsId(pcSlidingDetailVO.getDeductionsId());
			bo.setPcSlidingId(pcSlidingDetailVO.getPcSlidingId());
			if (CollectionUtils.isNotEmpty(bo.getAttachList())) {
				List<TRiContDeductionsAttach> attachmentTablePcList = bo.getAttachList();
				for (TRiContDeductionsAttach attachmentTablePc : attachmentTablePcList) {
					attachmentTablePc.setSlidingId(pcSlidingDetailVO.getPcSlidingId());
					attachmentTablePc.setType(ContractCst.CONTRACT_DEDUCTION_DETAIL_TYPE_PC);
					attachmentTablePc = attachmentTablePcDao.save(attachmentTablePc);
				}
			}
		}
	}

	@Override
	public void saveCommSlidingDetail(DeductionsCommVO vo) throws Exception {
		// new one object
		TRiContDeductionsComm commSlidingDetailVO = new TRiContDeductionsComm();
		// copy vo to commSlidingDetailVO ,only commSlidingDetialVO's all
		BeanUtils.copyPropertiesIngoreNull(commSlidingDetailVO, vo);
		commSlidingDetailVO.setTRiContDeductionsCarrieds(
				BeanUtils.convertList(vo.getDeductionsCarried(), TRiContDeductionsCarried.class));
		for (TRiContDeductionsCarried carried : commSlidingDetailVO.getTRiContDeductionsCarrieds()) {
			if (null == carried.getYn() || carried.getYn().equals("0")) {
				carried.setExtinction(null);
				carried.setYears(null);
			}
			carried.setTRiContDeductionsComm(commSlidingDetailVO);
		}
		// make the commSlidingDetailVO complete and insert in to plsql
		commSlidingDetailVO = commSlidingDetailDao.save(commSlidingDetailVO);
		// set id from complete to vo
		vo.setDeductionsId(commSlidingDetailVO.getDeductionsId());
		vo.setCommSlidingDetailId(commSlidingDetailVO.getCommSlidingDetailId());
		// get itemList
		List<DeductionsAttachVO> attachmentTableSsList = vo.getDeductionsAttach();
		List<DeductionsAttachVO> deleteDeductionsAttachs = BeanUtils.convertList(vo.getDeleteDeductionsAttach(),
				DeductionsAttachVO.class);
		for (DeductionsAttachVO item : deleteDeductionsAttachs) {
			item.setIsActive("N");
		}
		attachmentTableSsList.addAll(deleteDeductionsAttachs);
		// circular the itemList
		for (DeductionsAttachVO attachmentTableSsVO : attachmentTableSsList) {
			// set id and type
			attachmentTableSsVO.setSlidingId(commSlidingDetailVO.getCommSlidingDetailId());
			attachmentTableSsVO.setType(ContractCst.CONTRACT_DEDUCTION_DETAIL_TYPE_SS);
			// do as the above
			TRiContDeductionsAttach DeductionsAttach = new TRiContDeductionsAttach();
			BeanUtils.copyPropertiesIngoreNull(DeductionsAttach, attachmentTableSsVO);
			DeductionsAttach = attachmentTableSsDao.save(DeductionsAttach);
			attachmentTableSsVO.setAttachmentId(DeductionsAttach.getAttachmentId());
		}
		// remove the deleted record
		List<DeductionsAttachVO> attachList = new ArrayList<DeductionsAttachVO>();
		for (DeductionsAttachVO item : vo.getDeductionsAttach()) {
			if (null == item.getIsActive() || !item.getIsActive().equals("N")) {
				DeductionsAttachVO attachVO = new DeductionsAttachVO();
				BeanUtils.copyProperties(item, attachVO);
				attachList.add(attachVO);
			}
		}
		vo.setDeductionsAttach(attachList);
	}

	public void saveCommSlidingDetail(DeductionsCommonBO bo) throws IllegalAccessException, InvocationTargetException {

		if (bo != null) {
			TRiContDeductionsComm commSlidingDetailVO = new TRiContDeductionsComm();
			// copy vo to commSlidingDetailVO ,only commSlidingDetialVO's all
			BeanUtils.copyPropertiesIngoreNull(commSlidingDetailVO, bo);
			commSlidingDetailVO.setTRiContDeductionsCarrieds(bo.getCarrieds());
			for (TRiContDeductionsCarried carried : commSlidingDetailVO.getTRiContDeductionsCarrieds()) {
				if (carried.getYn().equals("0")) {
					carried.setExtinction(null);
					carried.setYears(null);
				}
				carried.setTRiContDeductionsComm(commSlidingDetailVO);
			}
			// make the commSlidingDetailVO complete and insert in to plsql
			commSlidingDetailVO = commSlidingDetailDao.save(commSlidingDetailVO);
			// set id from complete to vo
			bo.setDeductionsId(commSlidingDetailVO.getDeductionsId());
			bo.setCommSlidingDetailId(commSlidingDetailVO.getCommSlidingDetailId());
			// get itemList
			if (CollectionUtils.isNotEmpty(bo.getAttachments())) {
				List<TRiContDeductionsAttach> attachmentTableSsList = bo.getAttachments();
				// circular the itemList
				for (TRiContDeductionsAttach attachmentTableSs : attachmentTableSsList) {
					// set id and type
					attachmentTableSs.setSlidingId(commSlidingDetailVO.getCommSlidingDetailId());
					attachmentTableSs.setType(ContractCst.CONTRACT_DEDUCTION_DETAIL_TYPE_SS);
					attachmentTableSs = attachmentTableSsDao.save(attachmentTableSs);
				}
			}
		}
	}

	@Override
	public BusinessConditionVO loadBusinessConditionInfo(Long contCompId, String contractNature) {
		BusinessConditionVO vo = new BusinessConditionVO();
		vo.setContCompId(contCompId);
		vo.setContractNature(contractNature);
		// copy share info to Business Condition
		TRiContShare shareVO = shareDao.loadByContCompId(contCompId);
		BeanUtils.copyPropertiesIngoreNull(vo, shareVO);
		// copy loss info to Business Condition
		TRiContLoss lossVO = lossDao.loadByContCompId(contCompId);
		BeanUtils.copyPropertiesIngoreNull(vo, lossVO);
		// copy clause info to Business Condition
		TRiContClauses clauseVO = clausesDao.loadByContCompId(contCompId);
		BeanUtils.copyPropertiesIngoreNull(vo, clauseVO);
		// copy Premium Info to Business Condition
		vo = loadAndMergePremiumInfo(contCompId, vo);
		// copy Deduction Info to Business Condition
		vo = loadAndMergeDeductionsInfo(contCompId, vo);
		// copy Currency Info to Business Condition
		vo = loadAndMergeCurrencyInfo(contCompId, vo);
		// copy Limit Info to Business Condition
		vo = loadAndMergeLimitInfo(contCompId, vo);
		// copy Reinstate Info to Business Condition
		vo = loadAndMergeReinstateInfo(contCompId, vo);
		// copy reserve rebate info to Business Condition
		TRiContReserveRebate reserveRebateVO = reserveRebateDao.loadByContCompId(contCompId);
		BeanUtils.copyPropertiesIngoreNull(vo, reserveRebateVO);
		return vo;
	}

	public BusinessConditionVO loadParentBusinessCondition(Long contCompId) throws Exception {
		Long parentCompId = structureDao.load(contCompId).getParentId();
		if (parentCompId != null && parentCompId != 0L) {
			BusinessConditionVO vo = new BusinessConditionVO();
			BusinessConditionBO businessBO = loadBusinessConditionBO(parentCompId);
			BusinessConditionModelConvertor.covertBuinessBOToVO(businessBO, vo);
			return vo;
		}
		return null;
	}

	public BusinessConditionVO loadAndMergeDeductionsInfo(Long contCompId, BusinessConditionVO vo) {
		TRiContDeductions deductionsVO = deductionsDao.loadByContCompId(contCompId);
		if (null != deductionsVO && null != deductionsVO.getDeductionsId()) {
			BeanUtils.copyPropertiesIngoreNull(vo, deductionsVO);
			if (null != deductionsVO.getTRiContDeductionsItems()) {
				List<DeductionsItemVO> itemVOList = new ArrayList<DeductionsItemVO>();
				for (TRiContDeductionsItem tRiContDeductionsItem : deductionsVO.getTRiContDeductionsItems()) {
					DeductionsItemVO itemVO = new DeductionsItemVO();
					BeanUtils.copyPropertiesIngoreNull(itemVO, tRiContDeductionsItem);
					itemVO.setDeductionsId(deductionsVO.getDeductionsId());
					itemVOList.add(itemVO);
				}
				vo.setDeductionsList(itemVOList);
			}
		}

		return vo;
	}

	@Override
	public DeductionsPcVO loadDeductionsPcInfo(Long deductionsId) {
		DeductionsPcVO pcVO = new DeductionsPcVO();
		TRiContDeductionsPc deductionsPcVO = pcSlidingDetailDao.loadByContCompId(deductionsId);
		if (null != deductionsPcVO) {
			BeanUtils.copyPropertiesIngoreNull(pcVO, deductionsPcVO);
			List<TRiContDeductionsAttach> deductionsAttachList = deductionsAttachDao
					.loadBySlidingId(deductionsPcVO.getPcSlidingId());
			if (null != deductionsAttachList && deductionsAttachList.size() > 0) {
				List<DeductionsAttachVO> attactList = new ArrayList<DeductionsAttachVO>();
				for (TRiContDeductionsAttach deductionsAttachVO : deductionsAttachList) {
					DeductionsAttachVO itemVO = new DeductionsAttachVO();
					BeanUtils.copyPropertiesIngoreNull(itemVO, deductionsAttachVO);
					attactList.add(itemVO);
				}
				pcVO.setDeductionsAttach(attactList);
			}
		}
		return pcVO;
	}

	@Override
	public DeductionsCommVO loadDeductionsCommInfo(Long deductionsId) throws Exception {
		DeductionsCommVO commVO = new DeductionsCommVO();
		TRiContDeductionsComm deductionsCommVO = commSlidingDetailDao.loadByContCompId(deductionsId);
		if (null != deductionsCommVO && null != deductionsCommVO.getCommSlidingDetailId()) {
			BeanUtils.copyPropertiesIngoreNull(commVO, deductionsCommVO);
			List<TRiContDeductionsCarried> carriedList = deductionsCommVO.getTRiContDeductionsCarrieds().stream()
					.collect(Collectors.toList());
			commVO.setDeductionsCarried(BeanUtils.convertList(carriedList, DeductionsCarriedVO.class));
			List<TRiContDeductionsAttach> deductionsAttachList = deductionsAttachDao
					.loadBySlidingId(deductionsCommVO.getCommSlidingDetailId());
			if (null != deductionsAttachList && deductionsAttachList.size() > 0) {
				List<DeductionsAttachVO> attactList = new ArrayList<DeductionsAttachVO>();
				for (TRiContDeductionsAttach deductionsAttachVO : deductionsAttachList) {
					DeductionsAttachVO itemVO = new DeductionsAttachVO();
					BeanUtils.copyPropertiesIngoreNull(itemVO, deductionsAttachVO);
					attactList.add(itemVO);
				}
				commVO.setDeductionsAttach(attactList);
			}
		}

		return commVO;
	}

	public BusinessConditionVO loadAndMergePremiumInfo(Long contCompId, BusinessConditionVO vo) {
		TRiContPremium premiumVO = premiumDao.loadByContCompId(contCompId);
		if (null != premiumVO && null != premiumVO.getPremiumId()) {
			BeanUtils.copyPropertiesIngoreNull(vo, premiumVO);
			if (null != premiumVO.getTRiContPremiumItems()) {
				List<PremiumItemVO> itemVOList = new ArrayList<PremiumItemVO>();
				PremiumItemVO itemVO = null;
				Long count = (long) 1;
				Long countpay = (long) 1;
				for (TRiContPremiumItem tRiContPremiumItem : premiumVO.getTRiContPremiumItems()) {
					itemVO = new PremiumItemVO();
					if (tRiContPremiumItem.getItemType().equals(ContractCst.CONTRACT_PREMIUM_DEPOSIT_SHEDULE)) {
						itemVO.setInstalNo(count);
						count++;
					}

					// if
					// (tRiContPremiumItem.getItemType().equals(ContractCst.CONTRACT_PREMIUM_PAYMENT_SHEDULE))
					// {
					// itemVO.setInstalPayNo(countpay);
					// countpay++;
					// }
					BeanUtils.copyPropertiesIngoreNull(itemVO, tRiContPremiumItem);
					itemVO.setPremiumId(premiumVO.getPremiumId());
					itemVOList.add(itemVO);
				}
				vo.setPremiumList(itemVOList);
			}
		}
		return vo;
	}

	public BusinessConditionVO loadAndMergeLimitInfo(Long contCompId, BusinessConditionVO vo) {
		TRiContLimit limitVO = limitDao.loadByContCompId(contCompId);
		if (null != limitVO && null != limitVO.getLimitId()) {
			BeanUtils.copyPropertiesIngoreNull(vo, limitVO);
			if (null != limitVO.getTRiContLimitItems()) {
				List<LimitItemVO> itemVOList = new ArrayList<LimitItemVO>();
				for (TRiContLimitItem tRiContLimitItem : limitVO.getTRiContLimitItems()) {
					LimitItemVO itemVO = new LimitItemVO();
					BeanUtils.copyPropertiesIngoreNull(itemVO, tRiContLimitItem);
					tRiContLimitItem.setTRiContLimit(limitVO);
					itemVOList.add(itemVO);
				}
				vo.setLimitItemList(itemVOList);
			}

			if (null != limitVO.getTRiContLimitEvents()) {
				List<LimitEventVO> eventVOList = new ArrayList<LimitEventVO>();
				for (TRiContLimitEvent tRiContLimitEvent : limitVO.getTRiContLimitEvents()) {
					LimitEventVO itemVO = new LimitEventVO();
					BeanUtils.copyPropertiesIngoreNull(itemVO, tRiContLimitEvent);
					tRiContLimitEvent.setTRiContLimit(limitVO);
					eventVOList.add(itemVO);
				}
				vo.setLimitEventList(eventVOList);
			}
		}
		return vo;
	}

	public BusinessConditionVO loadAndMergeReinstateInfo(Long contCompId, BusinessConditionVO vo) {
		TRiContReinstate reinstateVO = reinstateDao.loadByContCompId(contCompId);
		if (null != reinstateVO && null != reinstateVO.getReinId()) {
			BeanUtils.copyPropertiesIngoreNull(vo, reinstateVO);
			if (null != reinstateVO.getTRiContReinstateItems()) {
				List<ReinstateItemVO> itemVOList = new ArrayList<ReinstateItemVO>();
				for (TRiContReinstateItem tRiContReinstateItem : reinstateVO.getTRiContReinstateItems()) {
					ReinstateItemVO itemVO = new ReinstateItemVO();
					BeanUtils.copyPropertiesIngoreNull(itemVO, tRiContReinstateItem);
					itemVOList.add(itemVO);
				}
				vo.setReinstateList(itemVOList);
			}
		}
		return vo;
	}

	public BusinessConditionVO loadAndMergeCurrencyInfo(Long contCompId, BusinessConditionVO vo) {
		List<TRiContCurrency> currencyList = currencyDao.loadByContCompId(contCompId);
		if (null != currencyList && currencyList.size() > 0) {
			List<CurrencyVO> itemList = new ArrayList<CurrencyVO>();
			for (TRiContCurrency tRiContCurrency : currencyList) {
				CurrencyVO currencyVO = new CurrencyVO();
				BeanUtils.copyPropertiesIngoreNull(currencyVO, tRiContCurrency);
				itemList.add(currencyVO);
			}

			vo.setCurrencyList(itemList);

		}
		return vo;
	}

	public void saveReinstate(ReinstatementBO vo) throws IllegalAccessException, InvocationTargetException {

		if (vo != null) {
			TRiContReinstate reinstateVO = new TRiContReinstate();
			BeanUtils.copyPropertiesIngoreNull(reinstateVO, vo);
			if (CollectionUtils.isNotEmpty(reinstateVO.getTRiContReinstateItems())) {
				for (TRiContReinstateItem item : reinstateVO.getTRiContReinstateItems()) {
					item.setTRiContReinstate(reinstateVO);
				}
			}
			reinstateVO = reinstateDao.save(reinstateVO);
			BeanUtils.copyPropertiesIngoreNull(vo, reinstateVO);
		}

	}

	public void saveLimit(LimitBO vo) throws IllegalAccessException, InvocationTargetException {
		TRiContLimit limitVO = new TRiContLimit();
		BeanUtils.copyPropertiesIngoreNull(limitVO, vo);
		for (TRiContLimitEvent item : limitVO.getTRiContLimitEvents()) {
			item.setTRiContLimit(limitVO);
		}
		for (TRiContLimitItem item : limitVO.getTRiContLimitItems()) {
			item.setTRiContLimit(limitVO);
		}
		limitVO = limitDao.save(limitVO);
		BeanUtils.copyPropertiesIngoreNull(vo, limitVO);
	}

	public void saveCurrency(BusinessConditionBO vo) throws IllegalAccessException, InvocationTargetException {
		List<CurrencyBO> currencyList = vo.getCurrencyBO();
		for (CurrencyBO currencyVO : currencyList) {
			TRiContCurrency contcurrencyVO = new TRiContCurrency();
			BeanUtils.copyPropertiesIngoreNull(contcurrencyVO, vo);
			BeanUtils.copyPropertiesIngoreNull(contcurrencyVO, currencyVO);
			if (null == currencyVO.getMainCurrency()) {
				contcurrencyVO.setMainCurrency("false");
			}
			contcurrencyVO = currencyDao.save(contcurrencyVO);
			currencyVO.setCurrencyId(contcurrencyVO.getCurrencyId());
		}
	}

	@Override
	public void backupBusinessConditionInfo(Long contCompId, Long operateId) {
		backUpShareInfo(contCompId, operateId);
		backUpCurrencyInfo(contCompId, operateId);
		backUpPremiumInfo(contCompId, operateId);
		backUpLimitInfo(contCompId, operateId);
		backUpReinstatementInfo(contCompId, operateId);
		backUpReserveRebateInfo(contCompId, operateId);
		backUpLossConditionInfo(contCompId, operateId);
		backUpClauseInfo(contCompId, operateId);
		backUpDecutionsInfo(contCompId, operateId);
	}

	/**
	 * backup share info
	 * 
	 * @param contCompId
	 * @param operateId
	 */
	public void backUpShareInfo(Long contCompId, Long operateId) {
		logger.debug("begin backup Share info, contCompId=" + contCompId + ",operateId=" + operateId);
		TRiContShare shareVO = shareDao.loadByContCompId(contCompId);
		if (null == shareVO || shareVO.getShareId() == null) {
			logger.debug(
					"the currenct component doesn't have Share info, don't need back up, contCompId=" + contCompId);
			return;
		}

		// backup premium main info
		TRiContShareLog logVO = new TRiContShareLog();
		BeanUtils.copyPropertiesIngoreNull(logVO, shareVO);
		logVO.clearPrimaryKeyCascade();
		logVO.setOperateId(operateId);
		shareLogDao.insert(logVO);
		logger.debug("backup Share info success, contCompId=" + contCompId + ",operateId=" + operateId);
	}

	/**
	 * backup deductionsInfo
	 * 
	 * @param contCompId
	 * @param operateId
	 */
	public void backUpDecutionsInfo(Long contCompId, Long operateId) {
		logger.debug("begin backup deductions info, contCompId=" + contCompId + ",operateId=" + operateId);
		TRiContDeductions deductionsVO = deductionsDao.loadByContCompId(contCompId);
		if (null == deductionsVO || deductionsVO.getDeductionsId() == null) {
			logger.debug("the currenct component doesn't have deductions info, don't need back up, contCompId="
					+ contCompId);
			return;
		}

		// backup deductions main info
		TRiContDeductionsLog deductionsLogVO = new TRiContDeductionsLog();
		BeanUtils.copyPropertiesIngoreNull(deductionsLogVO, deductionsVO);
		deductionsLogVO.clearPrimaryKeyCascade();
		deductionsLogVO.setOperateId(operateId);
		deductionsLogDao.insert(deductionsLogVO);

		// backup deductions item info
		List<TRiContDeductionsItem> deductionsItemList = deductionsVO.getTRiContDeductionsItems();
		if (null != deductionsItemList && deductionsItemList.size() > 0) {
			for (TRiContDeductionsItem deductionsItemVO : deductionsItemList) {
				TRiContDeductionsItemLog deductionsItemVOLog = new TRiContDeductionsItemLog();
				BeanUtils.copyPropertiesIngoreNull(deductionsItemVOLog, deductionsItemVO);
				deductionsItemVOLog.setOperateId(operateId);
				deductionsItemVOLog.setDeductionsId(deductionsVO.getDeductionsId());
				deductionsItemVOLog.clearPrimaryKeyCascade();
				deductionsItemLogDao.insert(deductionsItemVOLog);
			}
		}
		List<TRiContDeductionsAttach> deductionsAttachList = new ArrayList<TRiContDeductionsAttach>();
		// backup deductions pc info
		TRiContDeductionsPc deductionsPc = deductionsPcDao.loadByContCompId(deductionsVO.getDeductionsId());
		if (deductionsPc != null && deductionsPc.getPcSlidingId() != null) {
			TRiContDeductionsPcLog deductionsPcLogVO = new TRiContDeductionsPcLog();
			BeanUtils.copyPropertiesIngoreNull(deductionsPcLogVO, deductionsPc);
			deductionsPcLogVO.clearPrimaryKeyCascade();
			deductionsPcLogVO.setOperateId(operateId);
			deductionsPcLogDao.insert(deductionsPcLogVO);
			deductionsAttachList.addAll(deductionsAttachDao.loadBySlidingId(deductionsPc.getPcSlidingId()));
		}
		// backup deductions comm info
		TRiContDeductionsComm deductionsComm = deductionsCommDao.loadByContCompId(deductionsVO.getDeductionsId());
		if (deductionsComm != null && deductionsComm.getCommSlidingDetailId() != null) {
			TRiContDeductionsCommLog deductionsCommLogVO = new TRiContDeductionsCommLog();
			BeanUtils.copyPropertiesIngoreNull(deductionsCommLogVO, deductionsComm);
			deductionsCommLogVO.clearPrimaryKeyCascade();
			deductionsCommLogVO.setOperateId(operateId);
			deductionsCommLogDao.insert(deductionsCommLogVO);
			deductionsAttachList.addAll(deductionsAttachDao.loadBySlidingId(deductionsComm.getCommSlidingDetailId()));

			// backup deductions carried forward info
			List<TRiContDeductionsCarried> carriedInfo = deductionsCarriedDao
					.loadBySlidingId(deductionsComm.getCommSlidingDetailId());
			if (CollectionUtils.isNotEmpty(carriedInfo)) {
				for (TRiContDeductionsCarried carriedItem : carriedInfo) {
					TRiContDeductionsCarrLog carrLogVO = new TRiContDeductionsCarrLog();
					BeanUtils.copyPropertiesIngoreNull(carrLogVO, carriedItem);
					carrLogVO.clearPrimaryKeyCascade();
					carrLogVO.setOperateId(operateId);
					deductionsCarriedLogDao.insert(carrLogVO);
				}
			}
		}
		// backup deductions attach info
		if (CollectionUtils.isNotEmpty(deductionsAttachList)) {
			for (TRiContDeductionsAttach deductionsAttachVO : deductionsAttachList) {
				TRiContDeductionsAttLog deductionsAttVOLog = new TRiContDeductionsAttLog();
				BeanUtils.copyPropertiesIngoreNull(deductionsAttVOLog, deductionsAttachVO);
				deductionsAttVOLog.clearPrimaryKeyCascade();
				deductionsAttVOLog.setOperateId(operateId);
				deductionsAttLogDao.insert(deductionsAttVOLog);
			}
		}

		logger.debug("backup Deductions info success, contCompId=" + contCompId + ",operateId=" + operateId);

	}

	/**
	 * Need back up premium info and premium item info
	 * 
	 * @param contCompId
	 * @param operateId
	 */
	public void backUpPremiumInfo(Long contCompId, Long operateId) {
		logger.debug("begin backup premium info, contCompId=" + contCompId + ",operateId=" + operateId);
		TRiContPremium premiumVO = premiumDao.loadByContCompId(contCompId);
		if (null == premiumVO || premiumVO.getPremiumId() == null) {
			logger.debug(
					"the currenct component doesn't have premium info, don't need back up, contCompId=" + contCompId);
			return;
		}

		// backup premium main info
		TRiContPremiumLog logVO = new TRiContPremiumLog();
		BeanUtils.copyPropertiesIngoreNull(logVO, premiumVO);
		logVO.clearPrimaryKeyCascade();
		logVO.setOperateId(operateId);
		premiumLogDao.insert(logVO);

		// backup premium item info
		List<TRiContPremiumItem> itemList = premiumVO.getTRiContPremiumItems();
		if (null != itemList && itemList.size() > 0) {
			for (TRiContPremiumItem itemVO : itemList) {
				TRiContPremiumItemLog itemVOLog = new TRiContPremiumItemLog();
				BeanUtils.copyPropertiesIngoreNull(itemVOLog, itemVO);
				itemVOLog.clearPrimaryKeyCascade();
				itemVOLog.setOperateId(operateId);
				itemVOLog.setPremiumId(premiumVO.getPremiumId());
				premiumItemLogDao.insert(itemVOLog);
			}
		}
		logger.debug("backup premium info success, contCompId=" + contCompId + ",operateId=" + operateId);
	}

	/**
	 * backup the currency info
	 * 
	 * @param contCompId
	 * @param operateId
	 */
	public void backUpCurrencyInfo(Long contCompId, Long operateId) {
		logger.debug("begin backup currency info, contCompId=" + contCompId + ",operateId=" + operateId);
		List<TRiContCurrency> list = currencyDao.loadByContCompId(contCompId);
		if (null == list || list.size() == 0) {
			logger.debug(
					"the currenct component doesn't have premium info, don't need back up, contCompId=" + contCompId);
			return;
		}
		for (TRiContCurrency currencyVO : list) {
			// backup premium main info
			TRiContCurrencyLog logVO = new TRiContCurrencyLog();
			BeanUtils.copyPropertiesIngoreNull(logVO, currencyVO);
			logVO.clearPrimaryKeyCascade();
			logVO.setOperateId(operateId);
			currencyLogDao.insert(logVO);
		}

	}

	/**
	 * backup limit info
	 * 
	 * @param contCompId
	 * @param operateId
	 */
	public void backUpLimitInfo(Long contCompId, Long operateId) {
		logger.debug("begin backup limit info, contCompId=" + contCompId + ",operateId=" + operateId);
		TRiContLimit limitVO = limitDao.loadByContCompId(contCompId);
		if (limitVO == null || limitVO.getLimitId() == null) {
			logger.debug(
					"the currenct component doesn't have limit info, don't need back up, contCompId=" + contCompId);
			return;
		}

		// backup limit main info
		TRiContLimitLog logVO = new TRiContLimitLog();
		BeanUtils.copyPropertiesIngoreNull(logVO, limitVO);
		logVO.clearPrimaryKeyCascade();
		logVO.setOperateId(operateId);
		limitLogDao.insert(logVO);

		// backup limit item info
		List<TRiContLimitItem> itemList = limitVO.getTRiContLimitItems();
		if (null != itemList && itemList.size() > 0) {
			for (TRiContLimitItem itemVO : itemList) {
				TRiContLimitItemLog itemVOLog = new TRiContLimitItemLog();
				BeanUtils.copyPropertiesIngoreNull(itemVOLog, itemVO);
				itemVOLog.clearPrimaryKeyCascade();
				itemVOLog.setOperateId(operateId);
				itemVOLog.setLimitId(limitVO.getLimitId());
				limitItemLogDao.insert(itemVOLog);
			}
		}

		// backup limit event info
		List<TRiContLimitEvent> eventList = limitVO.getTRiContLimitEvents();
		if (null != eventList && eventList.size() > 0) {
			for (TRiContLimitEvent itemVO : eventList) {
				TRiContLimitEventLog itemVOLog = new TRiContLimitEventLog();
				BeanUtils.copyPropertiesIngoreNull(itemVOLog, itemVO);
				itemVOLog.clearPrimaryKeyCascade();
				itemVOLog.setOperateId(operateId);
				itemVOLog.setLimitId(limitVO.getLimitId());
				limitEventLogDao.insert(itemVOLog);
			}
		}
		logger.debug("backup limit info success, contCompId=" + contCompId + ",operateId=" + operateId);
	}

	/**
	 * backup Reinstatement info
	 * 
	 * @param contCompId
	 * @param operateId
	 */
	public void backUpReinstatementInfo(Long contCompId, Long operateId) {
		logger.debug("begin backup Reinstatement info, contCompId=" + contCompId + ",operateId=" + operateId);
		TRiContReinstate reinVO = reinstateDao.loadByContCompId(contCompId);
		if (reinVO == null || reinVO.getReinId() == null) {
			logger.debug("the currenct component doesn't have Reinstatement info, don't need back up, contCompId="
					+ contCompId);
			return;
		}

		// backup Reinstatement main info
		TRiContReinstateLog logVO = new TRiContReinstateLog();
		BeanUtils.copyPropertiesIngoreNull(logVO, reinVO);
		logVO.clearPrimaryKeyCascade();
		logVO.setOperateId(operateId);
		reinstateLogDao.insert(logVO);

		// backup Reinstatement item info
		List<TRiContReinstateItem> itemList = reinVO.getTRiContReinstateItems();
		if (null != itemList && itemList.size() > 0) {
			for (TRiContReinstateItem itemVO : itemList) {
				TRiContReinstateItemLog itemVOLog = new TRiContReinstateItemLog();
				BeanUtils.copyPropertiesIngoreNull(itemVOLog, itemVO);
				itemVOLog.clearPrimaryKeyCascade();
				itemVOLog.setOperateId(operateId);
				itemVOLog.setReinId(reinVO.getReinId());
				reinstateItemLogDao.insert(itemVOLog);
			}
		}

		logger.debug("backup Reinstatement info success, contCompId=" + contCompId + ",operateId=" + operateId);
	}

	/**
	 * backup reserve&rebate info
	 * 
	 * @param contCompId
	 * @param operateId
	 */
	public void backUpReserveRebateInfo(Long contCompId, Long operateId) {
		logger.debug("begin backup reserve&rebate info, contCompId=" + contCompId + ",operateId=" + operateId);
		TRiContReserveRebate resVO = reserveRebateDao.loadByContCompId(contCompId);
		if (null == resVO || resVO.getReserveId() == null) {
			logger.debug("the currenct component doesn't have reserve&rebate info, don't need back up, contCompId="
					+ contCompId);
			return;
		}

		TRiContReserveRebateLog logVO = new TRiContReserveRebateLog();
		BeanUtils.copyPropertiesIngoreNull(logVO, resVO);
		logVO.clearPrimaryKeyCascade();
		logVO.setOperateId(operateId);
		reserveRebateLogDao.insert(logVO);
		logger.debug("backup reserve&rebate info success, contCompId=" + contCompId + ",operateId=" + operateId);
	}

	/**
	 * backup Loss Corridor/Loss Participation info
	 * 
	 * @param contCompId
	 * @param operateId
	 */
	public void backUpLossConditionInfo(Long contCompId, Long operateId) {
		logger.debug("begin backup Loss Corridor/Loss Participation info, contCompId=" + contCompId + ",operateId="
				+ operateId);
		TRiContLoss lossVO = lossDao.loadByContCompId(contCompId);
		if (null == lossVO || lossVO.getLossId() == null) {
			logger.debug(
					"the currenct component doesn't have Loss Corridor/Loss Participation info, don't need back up, contCompId="
							+ contCompId);
			return;
		}

		TRiContLossLog logVO = new TRiContLossLog();
		BeanUtils.copyPropertiesIngoreNull(logVO, lossVO);
		logVO.clearPrimaryKeyCascade();
		logVO.setOperateId(operateId);
		lossLogDao.insert(logVO);
		logger.debug("backup Loss Corridor/Loss Participation info success, contCompId=" + contCompId + ",operateId="
				+ operateId);
	}

	/**
	 * backup Clause info
	 * 
	 * @param contCompId
	 * @param operateId
	 */
	public void backUpClauseInfo(Long contCompId, Long operateId) {
		logger.debug("begin backup Clause info, contCompId=" + contCompId + ",operateId=" + operateId);
		TRiContClauses clausesVO = clausesDao.loadByContCompId(contCompId);
		if (null == clausesVO || clausesVO.getClausesId() == null) {
			logger.debug(
					"the currenct component doesn't have Clause info, don't need back up, contCompId=" + contCompId);
			return;
		}

		TRiContClausesLog logVO = new TRiContClausesLog();
		BeanUtils.copyPropertiesIngoreNull(logVO, clausesVO);
		logVO.clearPrimaryKeyCascade();
		logVO.setOperateId(operateId);
		clausesLogDao.insert(logVO);
		logger.debug("backup Clause info success, contCompId=" + contCompId + ",operateId=" + operateId);
	}

	@Override
	public void deleteBusinessInfo(Long contCompId) {
		// TODO Auto-generated method stub

	}

	/**
	 * load business condition business model
	 * 
	 * @param contCompId
	 * @return
	 */
	public BusinessConditionBO loadBusinessConditionBO(Long contCompId) {
		BusinessConditionBO bo = new BusinessConditionBO();
		bo.setContCompId(contCompId);
		// Share info
		TRiContShare shareInfo = shareDao.loadByContCompId(contCompId);
		if (shareInfo != null) {
			bo.setShareBO(new ShareBO());
			BeanUtils.copyProperties(shareInfo, bo.getShareBO());
		}
		// Currency info
		List<TRiContCurrency> currencyList = currencyDao.loadByContCompId(contCompId);
		if (CollectionUtils.isNotEmpty(currencyList)) {
			bo.setCurrencyBO(new ArrayList<CurrencyBO>());
			for (TRiContCurrency currency : currencyList) {
				CurrencyBO currencyBO = new CurrencyBO();
				BeanUtils.copyProperties(currency, currencyBO);
				bo.getCurrencyBO().add(currencyBO);
			}
		}
		// Premium info
		TRiContPremium premiumInfo = premiumDao.loadByContCompId(contCompId);
		if (premiumInfo != null) {
			bo.setPremiumBO(new PremiumBO());
			BeanUtils.copyProperties(premiumInfo, bo.getPremiumBO());
		}
		// Limit info
		TRiContLimit limitInfo = limitDao.loadByContCompId(contCompId);
		if (limitInfo != null) {
			bo.setLimitBO(new LimitBO());
			BeanUtils.copyProperties(limitInfo, bo.getLimitBO());
		}
		// Deductions Info
		bo.setDeductionBO(loadDeductionsBO(contCompId));
		// Reinstatement Info
		TRiContReinstate reinInfo = reinstateDao.loadByContCompId(contCompId);
		if (reinInfo != null) {
			bo.setReinBO(new ReinstatementBO());
			BeanUtils.copyProperties(reinInfo, bo.getReinBO());
		}
		// Loss Info
		TRiContLoss lossInfo = lossDao.loadByContCompId(contCompId);
		if (lossInfo != null) {
			bo.setLossBO(new LossBO());
			BeanUtils.copyProperties(lossInfo, bo.getLossBO());
		}
		// Reserve&Rebate Info
		TRiContReserveRebate reserveInfo = reserveRebateDao.loadByContCompId(contCompId);
		if (reserveInfo != null) {
			bo.setReserveRebateBO(new ReserveRebateBO());
			BeanUtils.copyProperties(reserveInfo, bo.getReserveRebateBO());
		}
		// Clauses Info
		TRiContClauses clausesInfo = clausesDao.loadByContCompId(contCompId);
		if (clausesInfo != null) {
			bo.setClausesBO(new ClausesBO());
			BeanUtils.copyProperties(clausesInfo, bo.getClausesBO());
		}
		return bo;
	}

	public DeductionsBO loadDeductionsBO(Long contCompId) {
		DeductionsBO deductionsBO = new DeductionsBO();
		TRiContDeductions deductionInfo = deductionsDao.loadByContCompId(contCompId);
		if (null != deductionInfo && deductionInfo.getDeductionsId() != null) {
			BeanUtils.copyProperties(deductionInfo, deductionsBO);
			// PC Sliding
			TRiContDeductionsPc pcInfo = deductionsPcDao.loadByContCompId(deductionInfo.getDeductionsId());
			if (null != pcInfo && null != pcInfo.getPcSlidingId()) {
				DeductionsPcBO pcBO = new DeductionsPcBO();
				BeanUtils.copyProperties(pcInfo, pcBO);
				List<TRiContDeductionsAttach> pcAttachInfoList = attachmentTableSsDao
						.loadBySlidingId(pcInfo.getPcSlidingId());
				pcBO.setAttachList(pcAttachInfoList);
				deductionsBO.setDeductionPc(pcBO);
			}
			// Common Sliding
			TRiContDeductionsComm commInfo = deductionsCommDao.loadByContCompId(deductionInfo.getDeductionsId());
			if (null != commInfo && null != commInfo.getCommSlidingDetailId()) {
				DeductionsCommonBO commBO = new DeductionsCommonBO();
				BeanUtils.copyProperties(commInfo, commBO);
				List<TRiContDeductionsAttach> pcAttachInfoList = attachmentTableSsDao
						.loadBySlidingId(commInfo.getCommSlidingDetailId());
				commBO.setAttachments(pcAttachInfoList);
				List<TRiContDeductionsCarried> carriedInfo = deductionsCarriedDao
						.loadBySlidingId(commInfo.getCommSlidingDetailId());
				commBO.setCarrieds(carriedInfo);
				deductionsBO.setDeductionComm(commBO);
			}
		}
		return deductionsBO;
	}

	@Override
	public BigDecimal getOurShare(ShareBO shareBO) throws Exception {
		BigDecimal ourShare = null;
		if (null != shareBO) {
			ourShare = shareBO.getSignedShares2() == null
					? (shareBO.getWrittenShare2() == null ? (shareBO.getSignedShares1() == null
							? (shareBO.getWrittenShare1() == null ? null : shareBO.getWrittenShare1())
							: shareBO.getSignedShares1()) : shareBO.getWrittenShare2())
					: shareBO.getSignedShares2();
		}
		return ourShare;
	}

	@Override
	public Map<String, BigDecimal> getExchangeRateMap(List<CurrencyBO> currencyBOList) throws Exception {
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
		if (CollectionUtils.isNotEmpty(currencyBOList)) {
			// get main currency
			String mainCurrency = ContractCst.CONTRACT_DEFAULT_CURRENCY;
			for (CurrencyBO item : currencyBOList) {
				if (item.getMainCurrency().equals("true")) {
					mainCurrency = item.getCurrencyType();
				}
			}
			// mapping currency and exchangeRate
			for (CurrencyBO item : currencyBOList) {
				BigDecimal exchangeRate = currencyExchangeService.getExchangeRate(item.getCurrencyType(), mainCurrency);
				if (null != item.getExchangeType()) {
					if (item.getExchangeType().equals("1") && null != item.getCurrencyRate()) {
						exchangeRate = item.getCurrencyRate();
					} else if (item.getExchangeType().equals("2") && null != item.getCurrencyDate()) {
						exchangeRate = currencyExchangeService.getExchangeRate(item.getCurrencyType(), mainCurrency,
								item.getCurrencyDate());
					}
				}
				map.put(item.getCurrencyType(), exchangeRate);
			}
		}
		return map;
	}

	@Override
	public BigDecimal getPremium(List<CurrencyBO> currencyBOList, PremiumBO premiumBO) throws Exception {
		BigDecimal premium = null;
		// get map of currency and exchangeRate
		Map<String, BigDecimal> map = this.getExchangeRateMap(currencyBOList);
		// get main currency
		String mainCurrency = ContractCst.CONTRACT_DEFAULT_CURRENCY;
		for (CurrencyBO item : currencyBOList) {
			if (item.getMainCurrency().equals("true")) {
				mainCurrency = item.getCurrencyType();
			}
		}
		if (null != premiumBO) {
			if (CollectionUtils.isNotEmpty(premiumBO.getTRiContPremiumItems())) {
				premium = BigDecimal.ZERO;
				Boolean isSignedShare = true;
				List<TRiContPremiumItem> premiumForSecList = new ArrayList<TRiContPremiumItem>();
				for (TRiContPremiumItem item : premiumBO.getTRiContPremiumItems()) {
					// proportional
					if (premiumBO.getPremiumType() == null) {
						if (null != item.getItemType() && ContractCst.CONTRACT_PREMIUM_EPI.equals(item.getItemType())) {
							premiumForSecList.add(item);
							if (null != item.getCurrency() && item.getCurrency().equals(mainCurrency)) {
								if (item.getOurSignedShare() == null) {
									isSignedShare = false;
								}
							}
						}
					} else {
						// non-proportional
						if (premiumBO.getPremiumType().equals("1") && null != item.getItemType()
								&& ContractCst.CONTRACT_PREMIUM_FLAT.equals(item.getItemType())) {
							premiumForSecList.add(item);
							if (null != item.getCurrency() && item.getCurrency().equals(mainCurrency)) {
								if (item.getOurSignedShare() == null) {
									isSignedShare = false;
								}
							}
						} else if (premiumBO.getPremiumType().equals("2") && null != item.getItemType()
								&& ContractCst.CONTRACT_PREMIUM_FIXED.equals(item.getItemType())) {
							premiumForSecList.add(item);
							if (null != item.getCurrency() && item.getCurrency().equals(mainCurrency)) {
								if (item.getOurSignedShare() == null) {
									isSignedShare = false;
								}
							}
						} else if (premiumBO.getPremiumType().equals("3") && null != item.getItemType()
								&& ContractCst.CONTRACT_PREMIUM_SWING.equals(item.getItemType())) {
							premiumForSecList.add(item);
							if (null != item.getCurrency() && item.getCurrency().equals(mainCurrency)) {
								if (item.getOurSignedShare() == null) {
									isSignedShare = false;
								}
							}
						}
					}
				}

				boolean isBlank = true;
				for (TRiContPremiumItem item : premiumForSecList) {
					if (isSignedShare) {
						if (null != item.getOurSignedShare()) {
							isBlank = false;
							break;
						}
					} else {
						if (null != item.getOurWrittenShare()) {
							isBlank = false;
							break;
						}
					}
				}
				if (isBlank) {
					return null;
				}

				// premium calculate
				for (TRiContPremiumItem item : premiumForSecList) {
					BigDecimal exchangeRate = null;
					if (!map.isEmpty()) {
						Iterator<Entry<String, BigDecimal>> iter = map.entrySet().iterator();
						boolean isExist = false;
						while (iter.hasNext()) {
							Map.Entry<String, BigDecimal> entry = (Entry<String, BigDecimal>) iter.next();
							if (null != item.getCurrency() && entry.getKey().equals(item.getCurrency())) {
								exchangeRate = entry.getValue();
								isExist = true;
							}
						}
						if (!isExist) {
							exchangeRate = currencyExchangeService.getExchangeRate(item.getCurrency(), mainCurrency);
						}
					} else {
						exchangeRate = currencyExchangeService.getExchangeRate(item.getCurrency(), mainCurrency);
					}
					if (isSignedShare) {
						premium = premium
								.add((item.getOurSignedShare() == null ? BigDecimal.ZERO : item.getOurSignedShare())
										.divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE,
												BigDecimal.ROUND_HALF_UP));
					} else {
						premium = premium
								.add((item.getOurWrittenShare() == null ? BigDecimal.ZERO : item.getOurWrittenShare())
										.divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE,
												BigDecimal.ROUND_HALF_UP));
					}
				}
			}
		}
		return premium;
	}

	@Override
	public BigDecimal getDeductions(DeductionsBO deductionsBO, BigDecimal premium) throws Exception {
		BigDecimal deductions = null;

		if (null != deductionsBO) {
			if (null == deductionsBO.getRiPercentage() && null == deductionsBO.getPercentOfPremium()
					&& null == deductionsBO.getFixedAmountHunredPercent() && null == deductionsBO.getBrokerage()
					&& null == deductionsBO.getBrokerage2()) {
				if (CollectionUtils.isNotEmpty(deductionsBO.getTRiContDeductionsItems())) {
					boolean isBlank = true;
					for (TRiContDeductionsItem item : deductionsBO.getTRiContDeductionsItems()) {
						if (null != item.getPercentage() || null != item.getAmountPercent()) {
							isBlank = false;
							break;
						}
					}
					if (isBlank) {
						return null;
					}
				} else {
					return null;
				}
			}
			// premium is null and all percentage is null, return null
			if (null == deductionsBO.getRiPercentage() && null == deductionsBO.getPercentOfPremium()
					&& null == deductionsBO.getBrokerage() && null == premium) {
				if (CollectionUtils.isNotEmpty(deductionsBO.getTRiContDeductionsItems())) {
					boolean isNull = true;
					for (TRiContDeductionsItem item : deductionsBO.getTRiContDeductionsItems()) {
						if (null != item.getPercentage()) {
							isNull = false;
							break;
						}
					}
					if (isNull) {
						return null;
					}
				} else {
					return null;
				}
			}
			deductions = BigDecimal.ZERO;
			deductions = deductions
					.add(deductionsBO.getRiPercentage() == null ? BigDecimal.ZERO : deductionsBO.getRiPercentage());
			/**
			 * remove Profit Commission info
			 */
			// deductions = deductions.add(
			// deductionsBO.getProfitPercentage() == null ? BigDecimal.ZERO :
			// deductionsBO.getProfitPercentage());
			// deductions = deductions.add(deductionsBO.getExpensesPercentage()
			// == null ? BigDecimal.ZERO
			// : deductionsBO.getExpensesPercentage());
			deductions = deductions.add(deductionsBO.getPercentOfPremium() == null
					? (deductionsBO.getFixedAmountHunredPercent() == null ? BigDecimal.ZERO
							: transAmountToPercentage(deductionsBO.getFixedAmountHunredPercent(), premium))
					: deductionsBO.getPercentOfPremium());
			deductions = deductions.add(deductionsBO.getBrokerage() == null
					? (deductionsBO.getBrokerage2() == null ? BigDecimal.ZERO
							: transAmountToPercentage(deductionsBO.getBrokerage2(), premium))
					: deductionsBO.getBrokerage());
			if (CollectionUtils.isNotEmpty(deductionsBO.getTRiContDeductionsItems())) {
				for (TRiContDeductionsItem item : deductionsBO.getTRiContDeductionsItems()) {
					deductions = deductions.add(item.getPercentage() == null
							? (item.getAmountPercent() == null ? BigDecimal.ZERO
									: transAmountToPercentage(item.getAmountPercent(), premium))
							: item.getPercentage());
				}
			}
		}
		return deductions;
	}

	@Override
	public BigDecimal transAmountToPercentage(BigDecimal amount, BigDecimal premium) throws Exception {
		BigDecimal pre = null;
		if (null != amount && premium != BigDecimal.ZERO) {
			pre = amount.divide(premium, 4, BigDecimal.ROUND_HALF_UP);
		}
		return pre;
	}

	@Override
	public DeductionsPcVO loadDeductionsPcInfoForLog(Long deductionsId, Long operateId) throws Exception {
		DeductionsPcVO pcVO = new DeductionsPcVO();
		TRiContDeductionsPcLog deductionsPcLogVO = deductionsPcLogDao.loadByDecutionsIdAndOperateId(deductionsId,
				operateId);
		if (null != deductionsPcLogVO) {
			BeanUtils.copyPropertiesIngoreNull(pcVO, deductionsPcLogVO);
			List<TRiContDeductionsAttLog> deductionsAttLogList = deductionsAttLogDao
					.loadBySlidingIdAndOperateId(deductionsPcLogVO.getPcSlidingId(), operateId);
			if (CollectionUtils.isNotEmpty(deductionsAttLogList)) {
				List<DeductionsAttachVO> attactList = new ArrayList<DeductionsAttachVO>();
				for (TRiContDeductionsAttLog deductionsAttLogVO : deductionsAttLogList) {
					DeductionsAttachVO itemVO = new DeductionsAttachVO();
					BeanUtils.copyPropertiesIngoreNull(itemVO, deductionsAttLogVO);
					attactList.add(itemVO);
				}
				pcVO.setDeductionsAttach(attactList);
			}
		}
		return pcVO;
	}

	@Override
	public DeductionsCommVO loadDeductionsCommInfoForLog(Long deductionsId, Long operateId) throws Exception {
		DeductionsCommVO commVO = new DeductionsCommVO();
		TRiContDeductionsCommLog deductionsCommLogVO = deductionsCommLogDao.loadByDecutionsIdAndOperateId(deductionsId,
				operateId);
		if (null != deductionsCommLogVO && null != deductionsCommLogVO.getCommSlidingDetailId()) {
			BeanUtils.copyPropertiesIngoreNull(commVO, deductionsCommLogVO);
			List<TRiContDeductionsCarrLog> carrLogList = deductionsCarriedLogDao
					.loadBySlidingIdAndOperateId(deductionsCommLogVO.getCommSlidingDetailId(), operateId);
			commVO.setDeductionsCarried(BeanUtils.convertList(carrLogList, DeductionsCarriedVO.class));
			List<TRiContDeductionsAttLog> deductionsAttLogList = deductionsAttLogDao
					.loadBySlidingIdAndOperateId(deductionsCommLogVO.getCommSlidingDetailId(), operateId);
			if (CollectionUtils.isNotEmpty(deductionsAttLogList)) {
				List<DeductionsAttachVO> attactList = new ArrayList<DeductionsAttachVO>();
				for (TRiContDeductionsAttLog deductionsAttLogVO : deductionsAttLogList) {
					DeductionsAttachVO itemVO = new DeductionsAttachVO();
					BeanUtils.copyPropertiesIngoreNull(itemVO, deductionsAttLogVO);
					attactList.add(itemVO);
				}
				commVO.setDeductionsAttach(attactList);
			}
		}

		return commVO;
	}

	@Override
	public BusinessConditionBO convertBusinessConditionBOWithMainCurrency(BusinessConditionBO bo) throws Exception {
		BusinessConditionBO bcBO = new BusinessConditionBO();
		// get map of currency and exchangeRate
		Map<String, BigDecimal> map = this.getExchangeRateMap(bo.getCurrencyBO());
		BusinessConditionVO bcVO = new BusinessConditionVO();
		BusinessConditionModelConvertor.covertBuinessBOToVO(bo, bcVO);
		BusinessConditionModelConvertor.convertBusinessVOToBO(bcBO, bcVO);
		if (null != bcBO.getDeductionBO() && null != bo.getDeductionBO()) {
			bcBO.getDeductionBO().setDeductionComm(bo.getDeductionBO().getDeductionComm());
			bcBO.getDeductionBO().setDeductionPc(bo.getDeductionBO().getDeductionPc());
		}
		// get main currency
		String mainCurrency = ContractCst.CONTRACT_DEFAULT_CURRENCY;
		for (CurrencyBO item : bcBO.getCurrencyBO()) {
			if (item.getMainCurrency().equals("true")) {
				mainCurrency = item.getCurrencyType();
			}
		}
		if (null != bcBO.getPremiumBO()) {
			for (TRiContPremiumItem item : bcBO.getPremiumBO().getTRiContPremiumItems()) {
				BigDecimal exchangeRate = contractDS.getExchangeRate(bcBO.getCurrencyBO(), item.getCurrency(),
						mainCurrency);
				item.setAmount(item.getAmount() == null ? null
						: item.getAmount().divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE,
								BigDecimal.ROUND_HALF_UP));
				item.setOurWrittenShare(item.getOurWrittenShare() == null ? null
						: item.getOurWrittenShare().divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE,
								BigDecimal.ROUND_HALF_UP));
				item.setOurSignedShare(item.getOurSignedShare() == null ? null
						: item.getOurSignedShare().divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE,
								BigDecimal.ROUND_HALF_UP));
			}
		}
		if (null != bcBO.getLimitBO()) {
			for (TRiContLimitItem item : bcBO.getLimitBO().getTRiContLimitItems()) {
				BigDecimal exchangeRate = contractDS.getExchangeRate(bcBO.getCurrencyBO(), item.getCurrency(),
						mainCurrency);
				item.setLimitLayer(item.getLimitLayer() == null ? null
						: item.getLimitLayer().divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE,
								BigDecimal.ROUND_HALF_UP));
				item.setDeductible(item.getDeductible() == null ? null
						: item.getDeductible().divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE,
								BigDecimal.ROUND_HALF_UP));
				item.setLimitMin(item.getLimitMin() == null ? null
						: item.getLimitMin().divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE,
								BigDecimal.ROUND_HALF_UP));
				item.setLimitMax(item.getLimitMax() == null ? null
						: item.getLimitMax().divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE,
								BigDecimal.ROUND_HALF_UP));
				item.setAad(item.getAad() == null ? null
						: item.getAad().divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE,
								BigDecimal.ROUND_HALF_UP));
				item.setAal(item.getAal() == null ? null
						: item.getAal().divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE,
								BigDecimal.ROUND_HALF_UP));
				item.setLayerWrittenShare(item.getLayerWrittenShare() == null ? null
						: item.getLayerWrittenShare().divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE,
								BigDecimal.ROUND_HALF_UP));
				item.setLayerShare(item.getLayerShare() == null ? null
						: item.getLayerShare().divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE,
								BigDecimal.ROUND_HALF_UP));
				item.setAalWrittenShare(item.getAalWrittenShare() == null ? null
						: item.getAalWrittenShare().divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE,
								BigDecimal.ROUND_HALF_UP));
				item.setAalShare(item.getAalShare() == null ? null
						: item.getAalShare().divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE,
								BigDecimal.ROUND_HALF_UP));
				item.setSumInsured(item.getSumInsured() == null ? null
						: item.getSumInsured().divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE,
								BigDecimal.ROUND_HALF_UP));
				item.setLiability(item.getLiability() == null ? null
						: item.getLiability().divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE,
								BigDecimal.ROUND_HALF_UP));
				item.setLiabilityWrittenShare(item.getLiabilityWrittenShare() == null ? null
						: item.getLiabilityWrittenShare().divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE,
								BigDecimal.ROUND_HALF_UP));
				item.setLiabilitySignedShare(item.getLiabilitySignedShare() == null ? null
						: item.getLiabilitySignedShare().divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE,
								BigDecimal.ROUND_HALF_UP));
				item.setRetente(item.getRetente() == null ? null
						: item.getRetente().divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE,
								BigDecimal.ROUND_HALF_UP));
			}
			for (TRiContLimitEvent event : bcBO.getLimitBO().getTRiContLimitEvents()) {
				BigDecimal exchangeRate = contractDS.getExchangeRate(bcBO.getCurrencyBO(), event.getCurrency(),
						mainCurrency);
				event.setLiability(event.getLiability() == null ? null
						: event.getLiability().divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE,
								BigDecimal.ROUND_HALF_UP));
				event.setLiabilityWrittenShare(event.getLiabilityWrittenShare() == null ? null
						: event.getLiabilityWrittenShare().divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE,
								BigDecimal.ROUND_HALF_UP));
				event.setLiabilitySignedShare(event.getLiabilitySignedShare() == null ? null
						: event.getLiabilitySignedShare().divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE,
								BigDecimal.ROUND_HALF_UP));
				event.setLimitHundred(event.getLimitHundred() == null ? null
						: event.getLimitHundred().divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE,
								BigDecimal.ROUND_HALF_UP));
				event.setWrittenShare(event.getWrittenShare() == null ? null
						: event.getWrittenShare().divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE,
								BigDecimal.ROUND_HALF_UP));
				event.setLimitShare(event.getLimitShare() == null ? null
						: event.getLimitShare().divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE,
								BigDecimal.ROUND_HALF_UP));
			}
		}
		return bcBO;
	}
}
