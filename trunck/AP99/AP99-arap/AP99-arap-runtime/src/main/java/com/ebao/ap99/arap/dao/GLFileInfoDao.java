package com.ebao.ap99.arap.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;

import com.ebao.ap99.arap.constant.PostStatus;
import com.ebao.ap99.arap.entity.GLFileInfo;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class GLFileInfoDao extends BaseDao<GLFileInfo> {

	@Override
	public Class<GLFileInfo> getEntityClass() {
		return GLFileInfo.class;
	}

	@SuppressWarnings("unchecked")
	public List<Date> getPostPendingDate() throws Exception {
		List<Date> dateList = new ArrayList<Date>();
		String sql = "select distinct a.post_date from T_RI_GL_GENERAL_LEDGER a where a.post_date is not null and a.post_status != :postStatus";
		Query query = this.getEntityManager().createNativeQuery(sql);
		query.setParameter("postStatus", PostStatus.POSTED.getType());
		List<Timestamp> result = query.getResultList();
		if(CollectionUtils.isNotEmpty(result)){
			for(Timestamp postDate : result){
				dateList.add(postDate);
			}
		}
		return dateList;
	}
	
	@SuppressWarnings("unchecked")
	public List<GLFileInfo> getByFileDate(Date fileDate) throws Exception{
		Query query = this.getEntityManager().createNamedQuery("GLFileInfo.findByPostDate", this.getEntityClass());
		query.setParameter("fileDate", fileDate);
		return query.getResultList();
	}
}
