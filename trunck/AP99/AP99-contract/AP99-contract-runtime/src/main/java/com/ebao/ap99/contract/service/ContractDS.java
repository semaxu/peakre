package com.ebao.ap99.contract.service;

import java.math.BigDecimal;
import java.util.List;

import com.ebao.ap99.contract.entity.TRiContractStructure;
import com.ebao.ap99.contract.model.LinkContractVO;
import com.ebao.ap99.contract.model.BusinessModel.ContractBO;
import com.ebao.ap99.contract.model.BusinessModel.CurrencyBO;
//import com.ebao.ap99.contract.model.BusinessModel.ContractStructureBO;
import com.ebao.ap99.contract.model.BusinessModel.SectionBO;
import com.ebao.ap99.contract.model.BusinessModel.SubsectionBO;
import com.ebao.ap99.contract.model.PDFModel.ContractPDFVO;

public interface ContractDS {

	/**
	 * For one link, it only belongs to a cedent, A cedent can involved many
	 * link
	 * 
	 * @param cedentId
	 * @return
	 */
	public List<String> getLinkedListByCedent(String cedentId);

	/**
	 * get Link Contract List for Link Contract Page
	 * 
	 * @param linkName
	 * @param cedantId
	 * @return
	 */
	public List<LinkContractVO> getLinkContractList(String linkName, String contractCode, Long uwYear);

	public void saveOrUpdate(TRiContractStructure contractStructure);

	public void deleteSectionInfo(Long contCompId);

	public String getContractCode() throws Exception;

	/**
	 * load contract info for UI
	 * 
	 * @param contCompId
	 * @return
	 * @throws Exception
	 */
	public ContractBO loadContractBOForView(Long contCompId) throws Exception;

	/**
	 * load section info for UI
	 * 
	 * @param sectionId
	 * @return
	 * @throws Exception
	 */
	public SectionBO loadSectionBOForView(Long sectionId) throws Exception;

	/**
	 * load subsection info for UI
	 * 
	 * @param subsectionId
	 * @return
	 * @throws Exception
	 */
	public SubsectionBO loadSubSectionBOForView(Long subsectionId) throws Exception;

	public ContractBO loadContractBO(Long contCompId, boolean includeDetail) throws Exception;

	public SectionBO loadSectionBO(Long sectionId, boolean includeDetail) throws Exception;

	public SubsectionBO loadSubSectionBO(Long subsectionId, boolean includeDetail) throws Exception;

	public ContractBO generateStructureForBO(ContractBO bo);

	/**
	 * 
	 * @param bo
	 * @param includeDetail
	 * @throws Exception
	 */
	public void saveContractBO(ContractBO bo, boolean includeDetail) throws Exception;

	public void saveSectionInfo(SectionBO bo, boolean includeDetail) throws Exception;

	public void saveSubsectionInfo(SubsectionBO bo, boolean includeDetail) throws Exception;

	public ContractBO loadContractBOForLog(Long contCompId, Long operateId, String status, boolean isRevert)
			throws Exception;

	public ContractPDFVO convertContractBOToPDFVO(ContractBO bo) throws Exception;

	/**
	 * if the current contract/section/subsection has been inforce then return
	 * true, else return false
	 * 
	 * @param contCompId
	 * @return
	 */
	public boolean checkTheEntityHasBennInforce(Long contCompId);

	public SectionBO loadSectionBOForLog(Long sectionId, Long operateId, String contractNature, boolean isRevert)
			throws Exception;

	public SubsectionBO loadSubsectionBOForLog(Long subsectionId, Long operateId, String contractNature,
			boolean isRevert) throws Exception;

	public BigDecimal getExchangeRate(List<CurrencyBO> currencyBOList, String currency, String mainCurrency)
			throws Exception;
}
