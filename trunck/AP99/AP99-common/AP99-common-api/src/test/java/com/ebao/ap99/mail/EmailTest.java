package com.ebao.ap99.mail;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.unicorn.integration.client.Client;
import com.ebao.unicorn.platform.test.AbstractTest;

public class EmailTest extends AbstractTest {

	@Autowired
	private Client mqClient;
	
	@Test
	public void testCreateUser() throws Exception
	{
		MailVO mailVO = new MailVO();
		
		mailVO.setMailTo("gordon.cao@ebaotech.com");
		mailVO.setMailTitle("test 77777");
		//please keep same with template setting
		mailVO.setMailType("createUser");
		
		//set template parameter
		mailVO.putTemplateParam("username", "test: Do not reply");
		mailVO.putTemplateParam("password", "password");
		mailVO.putTemplateParam("url", "http://www.baidu.com");
		
		//Relative file path can be fetched from Document Management.
	//	mailVO.addAttachment("fileUpload/T_ROLE.sql");
	//	mailVO.addAttachment("fileUpload/在保.sql");
		
		MailService.sendEmail(mailVO);
		
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		
//		map.put("emailTo", "gordon.cao@ebaotech.com");
//		map.put("emailTitle", "create user");
//		map.put("emailType", "createUser");
//		map.put("emailFrom", "test@test.com");
//		HashMap emailContent = new HashMap();
//		emailContent.put("username", "test");
//		emailContent.put("password", "password");
//		emailContent.put("url", "http://www.baidu.com");
//		
//		List<String> attachmentList = new ArrayList<String>();
//		attachmentList.add("fileUpload/T_ROLE.sql");
//		attachmentList.add("fileUpload/在保.sql");
//		map.put("attachmentList", attachmentList);
//		map.put("emailContent", emailContent);
//		mqClient.send("emailSend", map);
		assertTrue(true);
	}
}
