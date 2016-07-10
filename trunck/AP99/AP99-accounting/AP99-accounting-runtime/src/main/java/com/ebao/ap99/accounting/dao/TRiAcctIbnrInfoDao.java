/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.ebao.ap99.accounting.entity.TRiAcctIbnrInfo;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

/**
 * Date: Mar 17, 2016 2:48:10 PM
 * 
 * @author xiaoyu.yang
 */
public class TRiAcctIbnrInfoDao extends BaseDao<TRiAcctIbnrInfo> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Class<TRiAcctIbnrInfo> getEntityClass() {
        return TRiAcctIbnrInfo.class;
    }

    public Long getMaxDocumentIdBySegmentId(Long segmentId){
    	Long uploadId =  (long) 0;
    	final Query query = getEntityManager().createNamedQuery("TRiAcctIbnrInfo.findMaxDocumentIdBySegmentId", Long.class);
		query.setParameter("segmentId", segmentId);
		@SuppressWarnings("unchecked")
		//List<TRiAcctIbnrInfo> ibnrInfoList = (List<TRiAcctIbnrInfo>) query.getResultList();
		//TRiAcctIbnrInfo ibnrInfo = new TRiAcctIbnrInfo();
		
		List<Long> uploadIdList =  query.getResultList();
		if(CollectionUtils.isNotEmpty(uploadIdList)){
			uploadId  = uploadIdList.get(0);
					//ibnrInfoList.get(0).getUploadId().longValue();
		}
    	
		return uploadId;
    }
    
    public double getAmountBySegmentIdAndUploadId(Long segmentId,Long uploadId){
		StringBuilder sql = new StringBuilder();

		sql.append("  select nvl(sum(ta.amount),0) as amount from t_ri_acct_ibnr_info ta      ");
		sql.append("   where   ta.segment_id = ? and ta.upload_id = ?                         ");

		double amount = 0;
		TRiAcctIbnrInfo ibnrInfo = new TRiAcctIbnrInfo();
		this.getJdbcTemplate().query(sql.toString(), new Object[] { segmentId,uploadId}, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				ibnrInfo.setAmount(rs.getBigDecimal("amount"));
				
			}
		});
		amount = ibnrInfo.getAmount().doubleValue();
    	return amount;
    }
    
    public List<TRiAcctIbnrInfo> loadOldIbnrByUploadIdAndSegmentId(Long uploadId,Long segmentId){
    	final Query query = getEntityManager().createNamedQuery("TRiAcctIbnrInfo.findIBNRBySegmentIdAndUploadId", TRiAcctIbnrInfo.class);
		query.setParameter("segmentId", segmentId);
		query.setParameter("uploadId", uploadId);

		@SuppressWarnings("unchecked")
		//List<TRiAcctIbnrInfo> ibnrInfoList = (List<TRiAcctIbnrInfo>) query.getResultList();
		//TRiAcctIbnrInfo ibnrInfo = new TRiAcctIbnrInfo();
		
		List<TRiAcctIbnrInfo> ibnrList =  query.getResultList();
		
		return ibnrList;
    }
    
    public Long getMaxDocumentIdBySegmentIdAndQuarter(Long segmentId,String fnQuarter){
    	Long uploadId =  (long) 0;
    	final Query query = getEntityManager().createNamedQuery("TRiAcctIbnrInfo.findMaxDocumentIdBySegmentIdAndQuarter", Long.class);
		query.setParameter("segmentId", segmentId);
		query.setParameter("fnQuarter", fnQuarter);
		@SuppressWarnings("unchecked")
		//List<TRiAcctIbnrInfo> ibnrInfoList = (List<TRiAcctIbnrInfo>) query.getResultList();
		//TRiAcctIbnrInfo ibnrInfo = new TRiAcctIbnrInfo();
		
		List<Long> uploadIdList =  query.getResultList();
		if(CollectionUtils.isNotEmpty(uploadIdList)){
			uploadId  = uploadIdList.get(0);
					//ibnrInfoList.get(0).getUploadId().longValue();
		}
    	
		return uploadId;
    }
    
    
  
}
