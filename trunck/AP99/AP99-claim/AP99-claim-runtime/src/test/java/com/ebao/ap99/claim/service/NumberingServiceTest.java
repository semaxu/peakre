/**
 * 
 */
package com.ebao.ap99.claim.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.parent.constant.NumberingFactor;
import com.ebao.ap99.parent.constant.NumberingType;
import com.ebao.unicorn.platform.foundation.numbering.NumberingService;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;
import com.ebao.unicorn.platform.test.AbstractTest;

/**
 * @author gang.wang
 *
 */
public class NumberingServiceTest extends AbstractTest {

	private static Logger logger = LoggerFactory.getLogger();

	@Autowired
	private NumberingService numService;
	
	@Test
	public void testGenerateNumber() {
		// rule : CL{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}6{SEQUENCE}
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMDD");
		String today = simpleDateFormat.format(date);
		
		Map<String, String> factors = new HashMap<String, String>();
		factors.put(NumberingFactor.TRANS_YEAR, today.substring(0, 4));
		factors.put(NumberingFactor.TRANS_MONTH, today.substring(4, 6));
		factors.put(NumberingFactor.TRANS_DAY, today.substring(6, 8));

		String claimNumber = null;
		try {
			claimNumber = numService.generateNumber(NumberingType.RI_CLAIM_NUMBER, factors);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("claimNumber========" + claimNumber);
	}

}
