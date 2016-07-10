/**
 * 
 */
package com.ebao.ap99.claim.service;

import java.util.List;

import com.ebao.ap99.claim.entity.TSampleUser;

/**
 * @author gang.wang
 *
 */
public interface SampleUserService {
	void createUser(TSampleUser user);
	TSampleUser updateUser(TSampleUser uesr);
	TSampleUser getUser(Long userId);
	void deleteUser(TSampleUser uesr);
	List<TSampleUser> getAllUsers();
}
