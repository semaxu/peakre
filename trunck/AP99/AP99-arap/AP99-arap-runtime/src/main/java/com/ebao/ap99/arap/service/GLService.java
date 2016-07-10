package com.ebao.ap99.arap.service;

import com.ebao.ap99.accounting.YearQuarter;

public interface GLService {
    public static final Integer GL_LOG_BATCH = 1;
    public static final Integer GL_LOG_TRANS = 2;
    public static final Integer GL_EX_SOURCE_SUB_LEDGER = 1;
    public static final Integer GL_EX_SOURCE_GENERAL_LEDGER = 2;
    
	/**
	 * Sub ledger data generation with below steps
	 *  Extract original GL data
	 * 	GL account mapping definition
	 * @throws Exception
	 */
	public void subLedgerGeneration() throws Exception;
	
	/**
	 * Get Interface fee after write to finance by business module
	 * @param transId
	 * @throws Exception
	 */
	public void subLedgerWriteDirectly(Long transId) throws Exception;

	/**
	 * General Ledger data generation with below steps
	 *  Sub ledger grouping
	 *  Calculate GL data in multiple currency
	 *  Generate GL file
	 * @throws Exception
	 */
	public void generalLedgerGeneration() throws Exception;
		
	/**
	 * Get current opening finance quarter
	 * @return
	 * @throws Exception
	 */
	public YearQuarter getOpeningFinQuarter() throws Exception;
}
