/**
 * 
 */
package com.ebao.ap99.accounting;

import java.util.List;

import com.ebao.ap99.accounting.model.ClaimEntryVO;

/**
 * @author xiaoyu.yang
 */
public interface AccountingService {

    /**
     * Recalculate forecast of open quarters<br/>
     * when treaty submit、EPI adjust or SOA submit revision
     * 
     * @param contractPremiumModel
     * @param changeReason
     * @throws Exception
     */
    void calcForecastByPattern(ContractPremiumModel contractPremiumModel) throws Exception;

    void adjustForecastOfOpenQuarter(ContractPremiumModel premModel) throws Exception;

    void adjustForecastForUPR$DACAdjust(ContractPremiumModel premModel) throws Exception;

    /**
     * query current financial quarter
     * 
     * @return
     * @throws Exception
     */
    YearQuarter currentFinancialQuarter() throws Exception;

    /**
     * Check if system date is in closing period or not
     * 
     * @return
     * @throws Exception
     */
    boolean inClosingPeriod() throws Exception;

    /**
     * After FN quarter status changed, call this method to refresh application
     * cache.
     * 
     * @throws Exception
     */
    void refreshClosingInfo() throws Exception;

    /**
     * After contract inforce
     * 
     * @param sectionIds
     * @param specialSubmit
     * @throws Exception
     */
    void afterTreatySubmit(List<Long> sectionIds, boolean specialSubmit) throws Exception;

    /**
     * After pricing estimate
     * 
     * @param sectionId
     * @throws Exception
     */
    void afterPricingEstimation(Long sectionId) throws Exception;

    /**
     * After claim submit
     * 
     * @param claimEntryList
     * @throws Exception
     */
    void afterClaimSubmit(List<ClaimEntryVO> claimEntryList) throws Exception;

}
