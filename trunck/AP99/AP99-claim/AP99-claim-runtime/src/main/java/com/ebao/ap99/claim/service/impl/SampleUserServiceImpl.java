/**
 * 
 */
package com.ebao.ap99.claim.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.claim.dao.SampleUserDao;
import com.ebao.ap99.claim.entity.TSampleUser;
import com.ebao.ap99.claim.service.SampleUserService;

/**
 * @author gang.wang
 *
 */
public class SampleUserServiceImpl implements SampleUserService {
	
	@Autowired
	private SampleUserDao userDao;

	@Override
	public void createUser(TSampleUser user) {
		 userDao.insert(user);
	}

	@SuppressWarnings("deprecation")
	@Override
	public TSampleUser updateUser(TSampleUser uesr) {
		return userDao.merge(uesr);
	}

	@Override
	public TSampleUser getUser(Long userId) {
		return userDao.load(userId);
	}

	@Override
	public void deleteUser(TSampleUser uesr) {
		userDao.delete(uesr);
	}

	@Override
	public List<TSampleUser> getAllUsers() {
		return userDao.loadAll();
	}

}
