/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.restlet.engine.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.contract.dao.TRiContLimitDao;
import com.ebao.ap99.contract.dao.TRiContRetroDao;
import com.ebao.ap99.contract.dao.TRiContRetroLogDao;
import com.ebao.ap99.contract.dao.TRiContSectionInfoDao;
import com.ebao.ap99.contract.dao.TRiContSectionInfoLogDao;
import com.ebao.ap99.contract.dao.TRiContSubsectionInfoDao;
import com.ebao.ap99.contract.dao.TRiContSubsectionInfoLogDao;
import com.ebao.ap99.contract.dao.TRiContractInfoDao;
import com.ebao.ap99.contract.dao.TRiContractStructureDao;
import com.ebao.ap99.contract.dao.TRiContractStructureLogDao;
import com.ebao.ap99.contract.entity.TRiContLimitEvent;
import com.ebao.ap99.contract.entity.TRiContLimitItem;
import com.ebao.ap99.contract.entity.TRiContRetro;
import com.ebao.ap99.contract.entity.TRiContRetroLog;
import com.ebao.ap99.contract.entity.TRiContSectionInfo;
import com.ebao.ap99.contract.entity.TRiContSubsectionInfo;
import com.ebao.ap99.contract.entity.TRiContractInfo;
import com.ebao.ap99.contract.entity.TRiContractStructure;
import com.ebao.ap99.contract.model.ContractSectionVO;
import com.ebao.ap99.contract.model.ContractSubsectionVO;
import com.ebao.ap99.contract.model.LimitEventVO;
import com.ebao.ap99.contract.model.RetrocessionItemVO;
import com.ebao.ap99.contract.model.RetrocessionVO;
import com.ebao.ap99.contract.model.BusinessModel.BusinessConditionBO;
import com.ebao.ap99.contract.model.BusinessModel.LimitBO;
import com.ebao.ap99.contract.service.BusinessConditionDS;
import com.ebao.ap99.contract.service.ContractRetrocessionDS;
import com.ebao.ap99.contract.service.ContractService;
import com.ebao.ap99.contract.util.ContractCst;
import com.ebao.ap99.parent.BeanUtils;
import com.ebao.ap99.parent.model.TreeModel;
import com.ebao.ap99.partner.service.PartnerAPI;
import com.ebao.unicorn.platform.data.code.CodeService;

/**
 * Date: Jan 19, 2016 4:08:48 PM
 * 
 * @author weiping.wang
 *
 */
public class ContractRetrocessionDSImpl implements ContractRetrocessionDS {
	@Autowired
	private TRiContRetroDao retrocessionDao;

	@Autowired
	private TRiContractStructureDao contractStructureDao;

	@Autowired
	private TRiContractStructureLogDao contractStructureLogDao;

	@Autowired
	private TRiContSectionInfoDao contSectionInfoDao;

	@Autowired
	private TRiContSectionInfoLogDao contSectionInfoLogDao;

	@Autowired
	private TRiContSubsectionInfoDao contSubsectionInfoDao;

	@Autowired
	private TRiContSubsectionInfoLogDao contSubsectionInfoLogDao;

	@Autowired
	private TRiContractInfoDao contractInfoDao;

	@Autowired
	private TRiContLimitDao contLimitDao;

	@Autowired
	private TRiContRetroLogDao retroLogDao;

	@Autowired
	private ContractService contractService;

	@Autowired
	private BusinessConditionDS businessConditionDS;

	@Autowired
	private CodeService codeTableService;

	@Autowired
	private PartnerAPI partnerAPI;

	@Override
	public void saveRetrocession(RetrocessionVO vo) throws Exception {
		List<RetrocessionItemVO> retrocessionList = vo.getRetrocessionList();
		List<RetrocessionItemVO> deleteRetrocessionList = vo.getDeleteRetrocessionList();
		if (CollectionUtils.isNotEmpty(deleteRetrocessionList)) {
			for (RetrocessionItemVO item : deleteRetrocessionList) {
				item.setIsActive("N");
			}
		}
		retrocessionList.addAll(deleteRetrocessionList);

		Long compId = vo.getContCompId();
		if (CollectionUtils.isNotEmpty(retrocessionList)) {
			for (RetrocessionItemVO item : retrocessionList) {
				TRiContRetro tRiContRetro = new TRiContRetro();
				BeanUtils.copyProperties(item, tRiContRetro);
				tRiContRetro.setCompId(compId);
				retrocessionDao.merge(tRiContRetro);
			}
		}
	}

	@Override
	public List<RetrocessionItemVO> loadRetrocessionList(Long compId) throws Exception {
		return BeanUtils.convertList(retrocessionDao.getRetrocessionList(compId), RetrocessionItemVO.class);
	}

	@Override
	public List<RetrocessionItemVO> loadCoveredSectionList(Long contId) throws Exception {
		List<RetrocessionItemVO> retrocessionList = new ArrayList<RetrocessionItemVO>();

		// Get StructureList for this Contract's all sections
		List<TRiContractStructure> assumedSections = contractStructureDao.getChildList(contId);
		for (TRiContractStructure assumedSection : assumedSections) {
			// Get this section info
			TRiContSectionInfo sectionInfo = contSectionInfoDao.load(assumedSection.getContCompId());
			// Get this section's retrocessionList
			List<TRiContRetro> retrocessionListSection = retrocessionDao
					.getCoveredSectionList(assumedSection.getContCompId());
			if (CollectionUtils.isNotEmpty(retrocessionListSection)) {
				for (TRiContRetro retro : retrocessionListSection) {
					RetrocessionItemVO itemVO = new RetrocessionItemVO();
					BeanUtils.copyPropertiesIngoreNull(itemVO, retro);
					BeanUtils.copyPropertiesIngoreNull(itemVO, getRetrocessionItemVO(retro.getCompId()));
					itemVO.setSection(sectionInfo == null ? null : sectionInfo.getSectionName());
					itemVO.setCedent(partnerAPI.loadPartnerNameByPartnerCode(itemVO.getCedent()));
					itemVO.setBroker(partnerAPI.loadPartnerNameByPartnerCode(itemVO.getBroker()));
					retrocessionList.add(itemVO);
				}
			}
			// Get StructureList for this section's all subsections
			List<TRiContractStructure> structureListForSubsections = contractStructureDao
					.getChildList(assumedSection.getContCompId());
			for (TRiContractStructure structureForSubsection : structureListForSubsections) {
				// Get this subsection's retrocessionList
				List<TRiContRetro> retrocessionListSubsection = retrocessionDao
						.getCoveredSectionList(structureForSubsection.getContCompId());
				if (CollectionUtils.isNotEmpty(retrocessionListSubsection)) {
					for (TRiContRetro retro : retrocessionListSubsection) {
						RetrocessionItemVO itemVO = new RetrocessionItemVO();
						BeanUtils.copyPropertiesIngoreNull(itemVO, retro);
						BeanUtils.copyPropertiesIngoreNull(itemVO, getRetrocessionItemVO(retro.getCompId()));
						itemVO.setSection(sectionInfo == null ? null : sectionInfo.getSectionName());
						itemVO.setSubSection(contSubsectionInfoDao.load(retro.getRetroCompId()).getSubsectionName());
						itemVO.setCedent(partnerAPI.loadPartnerNameByPartnerCode(itemVO.getCedent()));
						itemVO.setBroker(partnerAPI.loadPartnerNameByPartnerCode(itemVO.getBroker()));
						retrocessionList.add(itemVO);
					}
				}
			}

		}
		return retrocessionList;
	}

	/**
	 * Get retrocession for underlying treaties by section id or subsection id
	 * 
	 * @param compId
	 * @return
	 */
	private RetrocessionItemVO getRetrocessionItemVO(Long compId) {
		RetrocessionItemVO vo = new RetrocessionItemVO();
		TRiContractStructure structure = contractStructureDao.load(compId);
		String underlyingSectionName = null;
		TRiContractInfo contractInfo = null;
		// if compId is section id
		if (ContractCst.SECTION_LEVEL.equals(structure.getType())) {
			TRiContSectionInfo sectionInfo = contSectionInfoDao.load(compId);
			underlyingSectionName = sectionInfo.getSectionName();
			contractInfo = contractInfoDao.load(structure.getParentId());
		} else if (ContractCst.SUB_SECTION_LEVEL.equals(structure.getType())) {
			// if compId is subsection id
			TRiContSubsectionInfo subsectionInfo = contSubsectionInfoDao.load(compId);
			underlyingSectionName = subsectionInfo.getSubsectionName();
			contractInfo = contractInfoDao.load(contractStructureDao.load(structure.getParentId()).getParentId());
		}

		vo.setUnderlyingContract(contractInfo.getContractCode());
		vo.setUnderlyingSec(underlyingSectionName);
		vo.setCedent(contractInfo.getCedent());
		vo.setBroker(contractInfo.getBroker());
		vo.setUWYear(contractInfo.getUwYear());
		vo.setMainLoB(contractInfo.getMainclass());
		return vo;
	}

	@Override
	public RetrocessionItemVO generateRetrocession(Long retroCompId) throws Exception {
		RetrocessionItemVO vo = new RetrocessionItemVO();
		TRiContractStructure contractStructure = contractStructureDao.load(retroCompId);
		String retroSectionName = null;
		TRiContractInfo tRiContractInfo = null;
		TRiContSectionInfo tRiContSectionInfo;
		TRiContSubsectionInfo tRiContSubsectionInfo;
		TRiContLimitItem tRiContLimitItem = new TRiContLimitItem();
		if (ContractCst.SECTION_LEVEL.equals(contractStructure.getType())) {
			tRiContractInfo = contractInfoDao.load(contractStructure.getParentId());
			tRiContSectionInfo = contSectionInfoDao.load(retroCompId);
			retroSectionName = tRiContSectionInfo == null ? null : tRiContSectionInfo.getSectionName();
		} else if (ContractCst.SUB_SECTION_LEVEL.equals(contractStructure.getType())) {
			tRiContractInfo = contractInfoDao
					.load(contractStructureDao.load(contractStructure.getParentId()).getParentId());
			tRiContSubsectionInfo = contSubsectionInfoDao.load(retroCompId);
			retroSectionName = tRiContSubsectionInfo == null ? null : tRiContSubsectionInfo.getSubsectionName();
		}
		vo.setRetroCompId(retroCompId);
		vo.setRetroSectionName(retroSectionName);
		vo.setRetroContractCode(tRiContractInfo.getContractCode());
		vo.setContractNature(tRiContractInfo.getContractNature());
		BusinessConditionBO bo = businessConditionDS.loadBusinessConditionBO(retroCompId);

		this.generateLimitInfo(bo, tRiContractInfo.getMainCurrency(), vo, tRiContractInfo.getContractNature());

		return vo;
	}

	private void generateLimitInfo(BusinessConditionBO bo, String mainCurrency, RetrocessionItemVO vo,
			String contractNature) throws Exception {
		String briefText = null;
		if (null != bo && null != bo.getLimitBO()) {
			// convert businessConditionBO
			BusinessConditionBO bcBO = businessConditionDS.convertBusinessConditionBOWithMainCurrency(bo);
			LimitBO limitBO = bcBO.getLimitBO();

			vo.setLimitType(limitBO.getLimitType());

			if (CollectionUtils.isNotEmpty(limitBO.getTRiContLimitItems())) {
				String perRisk = null == limitBO.getPerRisk() ? null
						: this.getContractCodeDescription(ContractCst.CODE_TABLE_CONT_LIMIT_RISKEVENT_TYPE,
								limitBO.getPerRisk());
				BigDecimal qs = null;
				BigDecimal noOfLines = null;

				InnerObject innerObjForLimitItem = new InnerObject();
				for (TRiContLimitItem item : limitBO.getTRiContLimitItems()) {
					BeanUtils.copyPropertiesIngoreNull(innerObjForLimitItem, item);
					if (item.getCurrency().equals(mainCurrency)) {
						qs = item.getQs();
						noOfLines = item.getNoOfLines();
					}
				}
				innerObjForLimitItem.setQs(qs);
				innerObjForLimitItem.setNoOfLines(noOfLines);

				DecimalFormat df = new DecimalFormat("0.00%");
				Format nf = new DecimalFormat(",##0.00");
				if (null != limitBO.getLimitType()
						&& (ContractCst.CONTRACT_PROPORTIONAL_LIMIT_TYPE_QS.equals(limitBO.getLimitType())
								|| ContractCst.CONTRACT_LIMIT_COMB.equals(limitBO.getLimitType()))) {
					briefText = "Currency:" + mainCurrency;
					briefText += innerObjForLimitItem.getSumInsured() == null ? ""
							: ("\nSum Insured:" + nf.format(innerObjForLimitItem.getSumInsured()));
					briefText += innerObjForLimitItem.getQs() == null ? ""
							: (";\tQS %:" + df.format(innerObjForLimitItem.getQs()));
					if (ContractCst.CONTRACT_LIMIT_COMB.equals(limitBO.getLimitType())) {
						briefText += innerObjForLimitItem.getNoOfLines() == null ? ""
								: (";\tNumber of Lines:" + innerObjForLimitItem.getNoOfLines());
					}
					briefText += innerObjForLimitItem.getLiability() == null ? ""
							: (";\tLiability 100%:" + nf.format(innerObjForLimitItem.getLiability()));
					briefText += innerObjForLimitItem.getLiabilitySignedShare() == null
							? (innerObjForLimitItem.getLiabilityWrittenShare() == null ? ""
									: (";\tLiability Retrocession Share:"
											+ nf.format(innerObjForLimitItem.getLiabilityWrittenShare())))
							: (";\tLiability Retrocession Share:"
									+ nf.format(innerObjForLimitItem.getLiabilitySignedShare()));
				} else if (null != limitBO.getLimitType()
						&& ContractCst.CONTRACT_PROPORTIONAL_LIMIT_TYPE_SURPLUS.equals(limitBO.getLimitType())) {
					briefText = "Currency:" + mainCurrency;
					briefText += innerObjForLimitItem.getRetente() == null ? ""
							: ("\nRetention:" + nf.format(innerObjForLimitItem.getRetente()));
					briefText += innerObjForLimitItem.getNoOfLines() == null ? ""
							: (";\tNumber of Lines:" + innerObjForLimitItem.getNoOfLines());
					briefText += innerObjForLimitItem.getLiability() == null ? ""
							: (";\tLiability 100%:" + nf.format(innerObjForLimitItem.getLiability()));
					briefText += innerObjForLimitItem.getLiabilitySignedShare() == null
							? (innerObjForLimitItem.getLiabilityWrittenShare() == null ? ""
									: (";\tLiability Retrocession Share:"
											+ nf.format(innerObjForLimitItem.getLiabilityWrittenShare())))
							: (";\tLiability Retrocession Share:"
									+ nf.format(innerObjForLimitItem.getLiabilitySignedShare()));
				}

				if (ContractCst.CONTRACT_NATURE_NON_PROPORTIONAL.equals(contractNature)) {
					List<LimitEventVO> limitEventVOList = new ArrayList<LimitEventVO>();
					for (TRiContLimitEvent item : limitBO.getTRiContLimitEvents()) {
						LimitEventVO eventVO = new LimitEventVO();
						eventVO.setEvent(item.getEvent());
						eventVO.setLimitHundred(item.getLimitHundred());
						eventVO.setLimitShare(item.getLimitShare());
						eventVO.setWrittenShare(item.getWrittenShare());
						limitEventVOList.add(eventVO);
					}
					// merge limitEventVOList
					HashMap<String, LimitEventVO> mapLimitEvent = new HashMap<String, LimitEventVO>();
					for (LimitEventVO eventVO : limitEventVOList) {
						if (mapLimitEvent.containsKey(eventVO.getEvent())) {
							if (eventVO.getLimitHundred() != null) {
								eventVO.setLimitHundred(mapLimitEvent.get(eventVO.getEvent()).getLimitHundred()
										.add(eventVO.getLimitHundred()));
							}
							if (eventVO.getLimitShare() != null) {
								eventVO.setLimitShare(mapLimitEvent.get(eventVO.getEvent()).getLimitShare()
										.add(eventVO.getLimitShare()));
							}
							if (eventVO.getWrittenShare() != null) {
								eventVO.setWrittenShare(mapLimitEvent.get(eventVO.getEvent()).getWrittenShare()
										.add(eventVO.getWrittenShare()));
							}
						}
						mapLimitEvent.put(eventVO.getEvent(), eventVO);
					}
					limitEventVOList.clear();
					limitEventVOList.addAll(mapLimitEvent.values());

					String eventBriefText = "";
					if (CollectionUtils.isNotEmpty(limitEventVOList)) {
						for (LimitEventVO item : limitEventVOList) {
							eventBriefText += "\nEvent:" + (item.getEvent() == null ? " "
									: this.getContractCodeDescription(ContractCst.CODE_TABLE_CONTRACT_PERIL_TYPE,
											item.getEvent()));
							eventBriefText += item.getLimitHundred() == null ? ""
									: (";\tLimit in 100%:" + nf.format(item.getLimitHundred()));
							eventBriefText += item.getWrittenShare() == null ? ""
									: (";\tLimit Our Written Share:" + nf.format(item.getWrittenShare()));
							eventBriefText += item.getLimitShare() == null ? ""
									: (";\tLimit Our Signed Share:" + nf.format(item.getLimitShare()));
						}
					}

					if (null != limitBO.getLimitType()
							&& ContractCst.CONTRACT_NONPROPORTIONAL_LIMIT_TYPE_XOL.equals(limitBO.getLimitType())) {
						briefText = perRisk == null ? "Currency:" + mainCurrency
								: "Per Risk/Event:" + perRisk + ",\tCurrency:" + mainCurrency;
						briefText += innerObjForLimitItem.getLimitLayer() == null ? ""
								: ("\nLimit 100%:" + nf.format(innerObjForLimitItem.getLimitLayer()));
						briefText += innerObjForLimitItem.getDeductible() == null ? ""
								: (";\tDeductible:" + nf.format(innerObjForLimitItem.getDeductible()));
						briefText += innerObjForLimitItem.getAad() == null ? ""
								: (";\tAAD:" + nf.format(innerObjForLimitItem.getAad()));
						briefText += innerObjForLimitItem.getAal() == null ? ""
								: (";\tAAL 100%:" + nf.format(innerObjForLimitItem.getAal()));
						briefText += innerObjForLimitItem.getLayerWrittenShare() == null ? ""
								: (";\tLimit Our Written Share:"
										+ nf.format(innerObjForLimitItem.getLayerWrittenShare()));
						briefText += innerObjForLimitItem.getLayerShare() == null ? ""
								: (";\tLimit Our Signed Share:" + nf.format(innerObjForLimitItem.getLayerShare()));
						briefText += innerObjForLimitItem.getAalWrittenShare() == null ? ""
								: (";\tAAL Our Written Share:" + nf.format(innerObjForLimitItem.getAalWrittenShare()));
						briefText += innerObjForLimitItem.getAalShare() == null ? ""
								: (";\tAAL Our Signed Share:" + nf.format(innerObjForLimitItem.getAalShare()));
						if (null == limitBO.getPerRisk() || !limitBO.getPerRisk().equals("1")) {
							briefText += eventBriefText;
						}
					} else
						if (null != limitBO.getLimitType() && ContractCst.CONTRACT_NONPROPORTIONAL_LIMIT_TYPE_STOPLOSS
								.equals(limitBO.getLimitType())) {
						briefText = "Currency:" + mainCurrency;
						briefText += innerObjForLimitItem.getLimitLayer() == null
								? (innerObjForLimitItem.getLimitLayerPer() == null ? ""
										: ("\nLimit:" + df.format(innerObjForLimitItem.getLimitLayerPer())))
								: ("\nLimit:" + nf.format(innerObjForLimitItem.getLimitLayer()));
						briefText += innerObjForLimitItem.getDeductible() == null
								? (innerObjForLimitItem.getDeductiblePer() == null ? ""
										: (";\tDeductible:" + df.format(innerObjForLimitItem.getDeductiblePer())))
								: (";\tDeductible:" + nf.format(innerObjForLimitItem.getDeductible()));
						briefText += innerObjForLimitItem.getLimitMin() == null ? ""
								: (";\tMin:" + nf.format(innerObjForLimitItem.getLimitMin()));
						briefText += innerObjForLimitItem.getLimitMax() == null ? ""
								: (";\tMax:" + nf.format(innerObjForLimitItem.getLimitMax()));
						briefText += eventBriefText;
					}
				}
			}
		}
		vo.setBriefText(briefText);
	}

	class InnerObject {
		BigDecimal sumInsured = null;
		BigDecimal qs = null;
		BigDecimal liability = null;
		BigDecimal liabilitySignedShare = null;
		BigDecimal liabilityWrittenShare = null;
		BigDecimal retente = null;
		BigDecimal noOfLines = null;
		BigDecimal limitLayer = null;
		BigDecimal deductible = null;
		BigDecimal limitLayerPer = null;
		BigDecimal deductiblePer = null;
		BigDecimal limitMin = null;
		BigDecimal limitMax = null;
		BigDecimal aad = null;
		BigDecimal aal = null;
		BigDecimal layerShare = null;
		BigDecimal layerWrittenShare = null;
		BigDecimal aalShare = null;
		BigDecimal aalWrittenShare = null;

		public BigDecimal getSumInsured() {
			return sumInsured;
		}

		public void setSumInsured(BigDecimal sumInsured) {
			this.sumInsured = sumInsured == null ? this.sumInsured
					: (this.sumInsured == null ? BigDecimal.ZERO : this.sumInsured).add(sumInsured);
		}

		public BigDecimal getQs() {
			return qs;
		}

		public void setQs(BigDecimal qs) {
			this.qs = qs;
		}

		public BigDecimal getLiability() {
			return liability;
		}

		public void setLiability(BigDecimal liability) {
			this.liability = liability == null ? this.liability
					: (this.liability == null ? BigDecimal.ZERO : this.liability).add(liability);
		}

		public BigDecimal getLiabilitySignedShare() {
			return liabilitySignedShare;
		}

		public void setLiabilitySignedShare(BigDecimal liabilitySignedShare) {
			this.liabilitySignedShare = liabilitySignedShare == null ? this.liabilitySignedShare
					: (this.liabilitySignedShare == null ? BigDecimal.ZERO : this.liabilitySignedShare)
							.add(liabilitySignedShare);
		}

		public BigDecimal getLiabilityWrittenShare() {
			return liabilityWrittenShare;
		}

		public void setLiabilityWrittenShare(BigDecimal liabilityWrittenShare) {
			this.liabilityWrittenShare = liabilityWrittenShare == null ? this.liabilityWrittenShare
					: (this.liabilityWrittenShare == null ? BigDecimal.ZERO : this.liabilityWrittenShare)
							.add(liabilityWrittenShare);
		}

		public BigDecimal getRetente() {
			return retente;
		}

		public void setRetente(BigDecimal retente) {
			this.retente = retente == null ? this.retente
					: (this.retente == null ? BigDecimal.ZERO : this.retente).add(retente);
		}

		public BigDecimal getNoOfLines() {
			return noOfLines;
		}

		public void setNoOfLines(BigDecimal noOfLines) {
			this.noOfLines = noOfLines;
		}

		public BigDecimal getLimitLayer() {
			return limitLayer;
		}

		public void setLimitLayer(BigDecimal limitLayer) {
			this.limitLayer = limitLayer == null ? this.limitLayer
					: (this.limitLayer == null ? BigDecimal.ZERO : this.limitLayer).add(limitLayer);
		}

		public BigDecimal getDeductible() {
			return deductible;
		}

		public void setDeductible(BigDecimal deductible) {
			this.deductible = deductible == null ? this.deductible
					: (this.deductible == null ? BigDecimal.ZERO : this.deductible).add(deductible);
		}

		public BigDecimal getLimitLayerPer() {
			return limitLayerPer;
		}

		public void setLimitLayerPer(BigDecimal limitLayerPer) {
			this.limitLayerPer = limitLayerPer == null ? this.limitLayerPer
					: (this.limitLayerPer == null ? BigDecimal.ZERO : this.limitLayerPer).add(limitLayerPer);
		}

		public BigDecimal getDeductiblePer() {
			return deductiblePer;
		}

		public void setDeductiblePer(BigDecimal deductiblePer) {
			this.deductiblePer = deductiblePer == null ? this.deductiblePer
					: (this.deductiblePer == null ? BigDecimal.ZERO : this.deductiblePer).add(deductiblePer);
		}

		public BigDecimal getLimitMin() {
			return limitMin;
		}

		public void setLimitMin(BigDecimal limitMin) {
			this.limitMin = limitMin == null ? this.limitMin
					: (this.limitMin == null ? BigDecimal.ZERO : this.limitMin).add(limitMin);
		}

		public BigDecimal getLimitMax() {
			return limitMax;
		}

		public void setLimitMax(BigDecimal limitMax) {
			this.limitMax = limitMax == null ? this.limitMax
					: (this.limitMax == null ? BigDecimal.ZERO : this.limitMax).add(limitMax);
		}

		public BigDecimal getAad() {
			return aad;
		}

		public void setAad(BigDecimal aad) {
			this.aad = aad == null ? this.aad : (this.aad == null ? BigDecimal.ZERO : this.aad).add(aad);
		}

		public BigDecimal getAal() {
			return aal;
		}

		public void setAal(BigDecimal aal) {
			this.aal = aal == null ? this.aal : (this.aal == null ? BigDecimal.ZERO : this.aal).add(aal);
		}

		public BigDecimal getLayerShare() {
			return layerShare;
		}

		public void setLayerShare(BigDecimal layerShare) {
			this.layerShare = layerShare == null ? this.layerShare
					: (this.layerShare == null ? BigDecimal.ZERO : this.layerShare).add(layerShare);
		}

		public BigDecimal getLayerWrittenShare() {
			return layerWrittenShare;
		}

		public void setLayerWrittenShare(BigDecimal layerWrittenShare) {
			this.layerWrittenShare = layerWrittenShare == null ? this.layerWrittenShare
					: (this.layerWrittenShare == null ? BigDecimal.ZERO : this.layerWrittenShare)
							.add(layerWrittenShare);
		}

		public BigDecimal getAalShare() {
			return aalShare;
		}

		public void setAalShare(BigDecimal aalShare) {
			this.aalShare = aalShare == null ? this.aalShare
					: (this.aalShare == null ? BigDecimal.ZERO : this.aalShare).add(aalShare);
		}

		public BigDecimal getAalWrittenShare() {
			return aalWrittenShare;
		}

		public void setAalWrittenShare(BigDecimal aalWrittenShare) {
			this.aalWrittenShare = aalWrittenShare == null ? this.aalWrittenShare
					: (this.aalWrittenShare == null ? BigDecimal.ZERO : this.aalWrittenShare).add(aalWrittenShare);
		}
	}

	@Override
	public void backupRetrocessionInfo(Long contCompId, Long operateId) throws Exception {
		backUpRetrocession(contCompId, operateId);
		backUpCoveredSection(contCompId, operateId);
	}

	private void backUpRetrocession(Long compId, Long operateId) throws Exception {
		List<RetrocessionItemVO> retroList = loadRetrocessionList(compId);
		if (null != retroList && retroList.size() != 0) {
			for (RetrocessionItemVO item : retroList) {
				TRiContRetroLog logVO = new TRiContRetroLog();
				BeanUtils.copyPropertiesIngoreNull(logVO, item);
				logVO.clearPrimaryKeyCascade();
				logVO.setOperateId(operateId);
				retroLogDao.insert(logVO);
			}
		}
	}

	private void backUpCoveredSection(Long retroCompId, Long operateId) throws Exception {
		List<RetrocessionItemVO> coveredList = loadCoveredSectionList(retroCompId);
		if (null != coveredList && coveredList.size() != 0) {
			for (RetrocessionItemVO item : coveredList) {
				TRiContRetroLog logVO = new TRiContRetroLog();
				BeanUtils.copyPropertiesIngoreNull(logVO, item);
				logVO.clearPrimaryKeyCascade();
				logVO.setOperateId(operateId);
				retroLogDao.insert(logVO);
			}
		}
	}

	@Override
	public List<TreeModel> getRetroContractStructure(String contractCode, Long uwYear) throws Exception {
		List<TRiContractInfo> contractInfos = new ArrayList<TRiContractInfo>();
		contractInfos = contractInfoDao.getRetroEntityByCodeUWYear(contractCode, uwYear);

		if (null == contractInfos || contractInfos.size() == 0) {
			return null;
		} else {
			List<TreeModel> contractStructureList = new ArrayList<TreeModel>();
			for (TRiContractInfo contractInfo : contractInfos) {
				Long contractId = contractInfo.getContCompId();
				TreeModel contractStructure = contractService.getChildrenItemsByParentId(contractId);
				contractStructureList.add(contractStructure);
			}
			return contractStructureList;
		}
	}

	@Override
	public List<RetrocessionItemVO> loadRetrocessionListForLog(Long compId, Long operateId) throws Exception {
		return BeanUtils.convertList(retroLogDao.getRetrocessionListForLog(compId, operateId),
				RetrocessionItemVO.class);
	}

	@Override
	public List<RetrocessionItemVO> loadCoveredSectionListForLog(Long contId, Long operateId) throws Exception {
		List<RetrocessionItemVO> retrocessionList = new ArrayList<RetrocessionItemVO>();

		// Get sectionLogList for this Contract
		List<Long> assumedSectionsLog = contractStructureLogDao.getChildrenIdList(contId, operateId);
		for (Long assumedSectionLog : assumedSectionsLog) {
			// Get this section info history
			ContractSectionVO sectionVOLog = contSectionInfoLogDao.getContractSectionVOForLog(assumedSectionLog,
					operateId);
			// Get this section's retrocessionList
			List<TRiContRetroLog> retroLogListSection = retroLogDao.loadCoveredSectionListForLog(assumedSectionLog,
					operateId);
			if (CollectionUtils.isNotEmpty(retroLogListSection)) {
				for (TRiContRetroLog retroLog : retroLogListSection) {
					RetrocessionItemVO itemVO = new RetrocessionItemVO();
					BeanUtils.copyPropertiesIngoreNull(itemVO, retroLog);
					BeanUtils.copyPropertiesIngoreNull(itemVO, getRetrocessionItemVO(retroLog.getCompId()));
					itemVO.setSection(sectionVOLog == null ? null : sectionVOLog.getSectionName());
					retrocessionList.add(itemVO);
				}
			}
			// Get StructureList for this section's all subsections
			List<Long> assumedSubsectionsLog = contractStructureLogDao.getChildrenIdList(assumedSectionLog, operateId);
			for (Long assumedSubsectionLog : assumedSubsectionsLog) {
				// Get this subsection info history
				ContractSubsectionVO subsectionVOLog = contSubsectionInfoLogDao
						.getContractSubsectionVOForLog(assumedSubsectionLog, operateId);
				// Get this subsection's retrocessionList
				List<TRiContRetroLog> retroLogListSubsection = retroLogDao
						.loadCoveredSectionListForLog(assumedSubsectionLog, operateId);
				if (CollectionUtils.isNotEmpty(retroLogListSubsection)) {
					for (TRiContRetroLog retroLog : retroLogListSubsection) {
						RetrocessionItemVO itemVO = new RetrocessionItemVO();
						BeanUtils.copyPropertiesIngoreNull(itemVO, retroLog);
						BeanUtils.copyPropertiesIngoreNull(itemVO, getRetrocessionItemVO(retroLog.getCompId()));
						itemVO.setSection(sectionVOLog == null ? null : sectionVOLog.getSectionName());
						itemVO.setSubSection(subsectionVOLog == null ? null : subsectionVOLog.getSubsectionName());
						retrocessionList.add(itemVO);
					}
				}
			}

		}
		return retrocessionList;
	}

	private String getContractCodeDescription(long codeTable, String codeId) {
		if (!StringUtils.isNullOrEmpty(codeId)) {
			try {
				return codeTableService.findCodeTableValueByCode$CodeTableId(codeId, Long.valueOf(codeTable), null)
						.getDescription();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
