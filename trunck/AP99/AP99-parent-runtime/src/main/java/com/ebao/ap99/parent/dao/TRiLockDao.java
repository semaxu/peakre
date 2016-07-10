/**
 * 
 */
package com.ebao.ap99.parent.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.Query;

import org.springframework.jdbc.core.RowCallbackHandler;

import com.ebao.ap99.parent.entity.TRiLock;
import com.ebao.ap99.parent.model.LockModel;
import com.ebao.unicorn.platform.foundation.context.AppUser;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

/**
 * @author yujie.zhang
 *
 */
public class TRiLockDao extends BaseDao<TRiLock>{

	@Override
	public Class<TRiLock> getEntityClass() {
		//  Auto-generated method stub
		return TRiLock.class;
	}

	/**
	 * 
	 * @param businessResource
	 * @return TRiLockList
	 */
	public List<TRiLock> getLockInfoByResourceTypeAndId(LockModel lockModel){
		
		final Query query = getEntityManager().createNamedQuery("TRiLock.findByResourceIdAndResourceType", TRiLock.class);
		query.setParameter("resourceId", lockModel.getResourceId());
		query.setParameter("resourceType", lockModel.getResourceType());
		@SuppressWarnings("unchecked")
		List<TRiLock> list = query.getResultList();

		return list;
	}
	
	public AppUser getUserInfo(long userId) {

		StringBuilder sql = new StringBuilder();

		sql.append("  select user_id,user_name  from t_pub_user  where User_id=?  ");
		

		AppUser user = new AppUser();

		this.getJdbcTemplate().query(sql.toString(), new Object[] { userId }, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				user.setUserId(rs.getLong("user_id"));
				user.setUserName(rs.getString("user_name"));
				
			}
		});
		return user;

	}

	public AppUser getUserInfo(String userName) {

		StringBuilder sql = new StringBuilder();

		sql.append("  select user_id,user_name  from t_pub_user  where user_name=?  ");
		

		AppUser user = new AppUser();

		this.getJdbcTemplate().query(sql.toString(), new Object[] { userName }, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				user.setUserId(rs.getLong("user_id"));
				user.setUserName(rs.getString("user_name"));
				
			}
		});
		return user;

	}
}
