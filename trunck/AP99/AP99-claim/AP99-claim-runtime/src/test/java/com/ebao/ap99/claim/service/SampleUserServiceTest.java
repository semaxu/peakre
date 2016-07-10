/**
 * 
 */
package com.ebao.ap99.claim.service;

import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.claim.entity.TSampleUser;
import com.ebao.unicorn.platform.test.AbstractTest;

/**
 * @author gang.wang
 *
 */
public class SampleUserServiceTest extends AbstractTest {
	@Autowired
	private SampleUserService userService;
	
	@Test
	public void createUser() {
		TSampleUser user = new TSampleUser();
		user.setUserType(BigDecimal.valueOf(1));
		user.setUserName("USER");
		userService.createUser(user);
		System.out.println("*****************begin test sampleUser************1:");
		List<TSampleUser> userList = userService.getAllUsers();
		System.out.println("*****************begin test sampleUser************2:"+userList.size());
		
		assertEquals(userList.size(), 1);
		System.out.println("*****************begin test sampleUser************3:"+userList.size());
	}
}
