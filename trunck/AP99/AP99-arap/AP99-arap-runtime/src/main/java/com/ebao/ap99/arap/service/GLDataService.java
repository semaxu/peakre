package com.ebao.ap99.arap.service;

import java.util.List;

import com.ebao.ap99.arap.constant.PostStatus;
import com.ebao.ap99.arap.entity.GLAccountMappingDef;
import com.ebao.ap99.arap.vo.DoubleEntriesVO;
import com.ebao.ap99.arap.vo.EntryItemInformationVO;
import com.ebao.ap99.arap.vo.FinancialItemVO;
import com.ebao.ap99.arap.vo.GLInterfaceVO;
import com.ebao.ap99.arap.vo.GeneralLedgerDTO;
import com.ebao.ap99.arap.vo.GeneralLedgerVO;
import com.ebao.ap99.arap.vo.SubLedgerDTO;

public interface GLDataService {
	
	/**
	 * Get GL account mapping rules related to specific interface data
	 * @param glInterface
	 * @return
	 * @throws Exception
	 */
    public List<GLAccountMappingDef> getGLAcctountMapping(GLInterfaceVO glInterface) throws Exception;

    /**
     * Get no cash balance fee (other fee / no ARAP fee) TO DO check if need to
     * consider date
     * 
     * @return
     * @throws Exception
     */
    public List<GLInterfaceVO> getNoCashBalanceFee() throws Exception;

    /**
     * Get ARAP fee TO DO check if need to consider date
     * 
     * @return
     * @throws Exception
     */
    public List<GLInterfaceVO> getARAPFee() throws Exception;

    /**
     * Get cash fee detail TO DO check if need to consider date
     * 
     * @return
     * @throws Exception
     */
    public List<GLInterfaceVO> getCashDetailFee() throws Exception;

    /**
     * Get Interface fee after write to finance by business module
     * 
     * @param transId
     * @return
     * @throws Exception
     */
    public List<GLInterfaceVO> getInterfaceFee(Long transId) throws Exception;

    /**
     * Update post status to corresponding fee
     * 
     * @param feeId
     * @param settleDetailId cash fee detail id
     * @param postStatus
     * @throws Exception
     */
    public void updatePostStatus(Long feeId, Long settleDetailId, PostStatus postStatus) throws Exception;

    /**
     * Get financial view
     * 
     * @param level
     * @param conCompId
     * @return
     * @throws Exception
     */
    public List<FinancialItemVO> getFnView(Long level, Long conCompId) throws Exception;

    /**
     * Get financial quarterStr list
     * 
     * @param level
     * @param conCompId
     * @return
     * @throws Exception
     */
    public List<String> getFnQuarters(long level, long conCompId) throws Exception;

    /**
     * Query general ledger data
     * 
     * @param condition
     * @return
     * @throws Exception
     */
    public List<GeneralLedgerVO> queryGeneralLedger(GeneralLedgerDTO condition) throws Exception;
    
    /**
     * Get total size of result
     * @param condition
     * @return
     * @throws Exception
     */
    public Long countGeneralLedger(GeneralLedgerDTO condition) throws Exception;

    /**
     * Query entry items of sub ledger that's related to grouped general ledger
     * 
     * @param condition
     * @return
     * @throws Exception
     */
    public List<EntryItemInformationVO> querySubLedgerItems(SubLedgerDTO condition) throws Exception;
    
    /**
     * Get total size of result
     * @param condition
     * @return
     * @throws Exception
     */
    public Long countSubLedgerItems(SubLedgerDTO condition) throws Exception;

    /**
     * Query sub ledger entry items related to specified BCP fee
     * 
     * @param mappingEntryId
     * @return
     * @throws Exception
     */
    public List<DoubleEntriesVO> queryDoubleEntries(Long mappingEntryId) throws Exception;
}
