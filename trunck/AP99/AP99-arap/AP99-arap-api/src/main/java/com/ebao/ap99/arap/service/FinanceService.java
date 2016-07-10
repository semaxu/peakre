package com.ebao.ap99.arap.service;

import java.util.List;

import com.ebao.ap99.arap.vo.BusinessFeeModel;
import com.ebao.ap99.arap.vo.FnViewVO;
import com.ebao.ap99.arap.vo.SettlementHistory;

public interface FinanceService {

    /**
     * Send business fee to finance model
     * 
     * @param modelList
     * @return {@value=FinanceConsistants#WRITER_TO_FIN_XXX}
     * @throws Exception
     */
    public Integer writeToFinance(List<BusinessFeeModel> modelList) throws Exception;

    /**
     * Get settlement history
     * 
     * @param bizTransType
     * @param bizTransId
     * @return
     * @throws Exception
     */
    public List<SettlementHistory> getSettlementHistory(Integer bizTransType, Long bizTransId) throws Exception;


    /**
     * Get financial view
     * 
     * @param level
     * @param conCompId
     * @return
     * @throws Exception
     */
    public FnViewVO getFnView(Long contractId) throws Exception;
}
