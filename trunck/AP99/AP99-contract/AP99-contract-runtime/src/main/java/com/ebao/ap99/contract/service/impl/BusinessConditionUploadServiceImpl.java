/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.contract.dao.TRiContDeductionsAttachDao;
import com.ebao.ap99.contract.entity.TRiContDeductionsAttach;
import com.ebao.ap99.contract.model.DeductionsAttachVO;
import com.ebao.ap99.contract.service.BusinessConditionUploadService;
import com.ebao.ap99.file.model.ItemVO;
import com.ebao.ap99.file.service.BizService;
import com.ebao.ap99.parent.BeanUtils;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * Date: Mar 17, 2016 11:20:38 AM
 * 
 * @author Weiping.Wang
 *
 */
public class BusinessConditionUploadServiceImpl implements BizService, BusinessConditionUploadService {
	private static Logger logger = LoggerFactory.getLogger();

	@Autowired
	private TRiContDeductionsAttachDao deductionsAttachDao;

	public void save(ItemVO itemVo, Long documentId, Long slidingId) throws Exception {
		TRiContDeductionsAttach bizVO = (TRiContDeductionsAttach) itemVo.getBizVO();
		try {
			bizVO.setSlidingId(slidingId);
			TRiContDeductionsAttach deductionsAttach = new TRiContDeductionsAttach();
			BeanUtils.copyPropertiesIngoreNull(deductionsAttach, bizVO);

			BigDecimal divisor = new BigDecimal(100);
			BigDecimal comm = bizVO.getCommission() == null ? null : bizVO.getCommission().divide(divisor);
			BigDecimal lrFrom = bizVO.getLrFrom() == null ? null : bizVO.getLrFrom().divide(divisor);
			BigDecimal lrTo = bizVO.getLrTo() == null ? null : bizVO.getLrTo().divide(divisor);
			deductionsAttach.setCommission(comm);
			deductionsAttach.setLrFrom(lrFrom);
			deductionsAttach.setLrTo(lrTo);

			deductionsAttach = deductionsAttachDao.save(deductionsAttach);

			itemVo.setResultFlag(true);
		} catch (Exception e) {
			itemVo.setMsg("SlidingId:" + slidingId + ",has an error message:" + e.getMessage() + ",error cause:"
					+ e.getCause());
			itemVo.setResultFlag(false);
			logger.error("SlidingId:" + slidingId + ",has an error message:" + e.getMessage() + ",error cause:"
					+ e.getCause());
		}
	}

	public void saveAll(List itemVoList, Long documentId, Long slidingId) throws Exception {
		for (Object obj : itemVoList) {
			save((ItemVO<DeductionsAttachVO>) obj, documentId, slidingId);
		}
	}

	@Override
	public List bizProcess(List itemVOList, Long documentId, Long businessId) {
		if (CollectionUtils.isNotEmpty(itemVOList)) {
			try {
				saveAll(itemVOList, documentId, businessId);
			} catch (Exception e) {
				logger.error("error message:" + e.getMessage() + ",error cause:" + e.getCause());
			}
		}
		return null;
	}

}
