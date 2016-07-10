/**
 * 
 */
package com.ebao.ap99.claim.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.ebao.ap99.claim.entity.TSampleUser;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

/**
 * @author gang.wang
 *
 */
public class SampleUserDao extends BaseDao<TSampleUser> {

	@Override
	public Class<TSampleUser> getEntityClass() {
		return TSampleUser.class;
	}

//	@SuppressWarnings("unchecked")
	public List<TSampleUser> findUserByType(String type) {

		StringBuilder sql = new StringBuilder();
		sql.append("select user_id, user_name, user_type from t_sample_user t1, t_sample_user_type t2 ");
		sql.append("where t1.user_type = t2.type_id and t2.type_desc = ? ");

		List<TSampleUser> userList = this.getJdbcTemplate().query(sql.toString(), new Object[] { type },
				new BeanPropertyRowMapper<TSampleUser>(TSampleUser.class));

		return userList;
	}

}
