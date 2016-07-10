package com.ebao.ap99.contract.service;

import java.util.Date;
import java.util.List;

import com.ebao.ap99.contract.entity.ContractModel;
import com.ebao.ap99.contract.entity.SectionModel;
import com.ebao.ap99.parent.model.TreeModel;

/**
 * @since 12/20/2015
 * @author elvira.du
 */
public interface ContractService {
    /**
     * get inforce Contract Structure By contractCode & uwYear
     * 
     * @param contractCode
     * @param uwYear
     * @return
     * @throws Exception
     */
    public List<TreeModel> getContractStructureByCode(String contractCode, Long uwYear) throws Exception;

    /**
     * get the Contract Structure By contractCode & uwYear & inForce Flag the
     * inForce flag should be true/false
     * 
     * @param contractCode
     * @param uwYear
     * @param isInforce
     * @return
     * @throws Exception
     */
    public List<TreeModel> getContractStructureByCode(String contractCode, Long uwYear, boolean isInforce)
            throws Exception;

    /**
     * load the contract brief information by contCompId
     * 
     * @param compContId
     * @return
     * @throws Exception
     */
    public ContractModel getContractInfoByCompId(Long compContId) throws Exception;

    /**
     * load the contract relative underwriting year by contract code(contract
     * Id)
     * 
     * @param contractCode
     * @return
     */
    public List<Long> getContractUWYearByContractCode(String contractCode);

    /**
     * load the contract structure children item by parentId the current
     * contract model as: contract->section->subSection
     * 
     * @param contractCode
     * @return
     */
    public TreeModel getChildrenItemsByParentId(Long parentCompId);

    /**
     * load section basic info by sectionId
     * 
     * @param sectionId
     * @return
     */
    public SectionModel getSectionModel(Long sectionId);

    //    /**
    //     * get contractModel by contractCode and uwYear.
    //     * 
    //     * @param contractCode
    //     * @param uwYear
    //     * @return
    //     * @throws Exception
    //     */
    //    public ContractModel getcontractIdByCode(String contractCode, Long uwYear) throws Exception;

    /**
     * load contract premium model by section id, return ContractPremiumModel
     * For contract inforce, there need calculate Forcast
     * 
     * @param sectionId
     * @return
     */
    public Object getPremiumModelBySectionId(Long sectionId) throws Exception;

    /**
     * get the posting flag by contractId/sectionId/subSectionId
     * 
     * @param contCompId
     * @return
     */
    public boolean needPostingGL(Long contCompId);

    /**
     * get retrocession's section Id List by Assume Section/SubSection Id
     * 
     * @param contCompId
     * @return
     */
    public List<Long> getRetrocessionSectionIdList(Long contCompId);

    /**
     * get contract structure by contractModel
     * 
     * @param contractModel
     * @return
     */
    public List<TreeModel> getContractStructure(ContractModel contractModel) throws Exception;

    /**
     * get the Contract Structure By contractCode & uwYear, and the contract is
     * "In Force" or "Cancelled".
     * 
     * @param contractCode
     * @param uwYear
     * @return
     * @throws Exception
     */
    public List<TreeModel> getInforceAndCancelledContractStructureByCode(String contractCode, Long uwYear)
            throws Exception;

    /**
     * get contractModel which is "In Force" or "Cancelled" by contractCode and
     * uwYear.
     * 
     * @param contractCode
     * @param uwYear
     * @return
     * @throws Exception
     */
    public ContractModel getInforceAndCancelledContractIdByCode(String contractCode, Long uwYear) throws Exception;

    /**
     * reset clean-cut flag with sectionId & isCleanCut
     * 
     * @param contCompId
     * @param isCleanCut
     */
    public void resetTheCleanCutFlag(Long contCompId, boolean isCleanCut);

    /**
     * get clean-cut flag by contCompId
     * 
     * @param contCompId
     * @return
     */
    public boolean isCleanCutContractSection(Long contCompId);

    /**
     * Get contract code by Id
     * 
     * @param contCompId
     * @return
     * @throws Exception
     */
    public String getContractCodeById(Long contCompId) throws Exception;

    /**
     * Actualize contract
     * 
     * @param contractId
     * @throws Exception
     */
    public void actualizeByContractId(Long contractId) throws Exception;

    /**
     * get the correct level contcompId
     * 
     * @param contCompId
     * @param level
     * @return
     */
    public List<Long> getLevelContCompIdList(Long contCompId, String level);

    /**
     * if the contract is "In Force" or "Cancelled" status
     * 
     * @param contCompId
     * @return
     */
    public boolean isInforceOrCancelledContract(Long contCompId) throws Exception;

    /**
     * get sectionId by contratCode, uwYear, reinsStarting, sectionName,
     * ShareType
     * 
     * @param contractCode
     * @param uwYear
     * @param reinsStarting
     * @param sectionName
     * @param ShareType
     * @return
     * @throws Exception
     */
    public Long getSectionIdByContractCodeAndSectionInfo(String contractCode, Long uwYear, Date reinsStarting,
                                                         String sectionName, String ShareType) throws Exception;
}
