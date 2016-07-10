/**
 * 
 */
package com.ebao.ap99.claim.dao;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.claim.entity.TSampleUser;
import com.ebao.unicorn.platform.foundation.utils.json.JSONUtils;
import com.ebao.unicorn.platform.test.AbstractTest;

/**
 * @author gang.wang
 * 
 */
public class SampleUserDaoTest extends AbstractTest {
	@Autowired
	private SampleUserDao sampleUserDao;

	@Test
	public void testFindUserByType() {

		List<TSampleUser> userList = sampleUserDao.findUserByType("Staff");
		assertNotNull(userList);

	}

	public class A {
		String number;

		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			this.number = number;
		}

	};

	public static void main(String[] args) {
		String s = "AAA1235346535";
		Map map = new HashMap();
		map.put("key", s);
		String[] ss = new String[] { s, s };
		A a = new SampleUserDaoTest().new A();
		a.setNumber(s);

		System.out.println(JSONUtils.toJSON(s));
		System.out.println(JSONUtils.toJSON(map));
		System.out.println(JSONUtils.toJSON(ss));
		System.out.println(JSONUtils.toJSON(a));
	}
}
