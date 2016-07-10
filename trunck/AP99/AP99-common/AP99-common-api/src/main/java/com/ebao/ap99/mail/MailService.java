package com.ebao.ap99.mail;


import java.util.HashMap;

import com.ebao.ap99.file.util.BeanUtil;
import com.ebao.unicorn.integration.client.Client;
import com.ebao.unicorn.platform.test.AbstractTest;

public class MailService extends AbstractTest {
   
    private static Client mqClient = null;
    public static void sendEmail(MailVO mailVO) throws Exception
    {
        if (mqClient == null)
            mqClient = (Client) BeanUtil.getStringBean("mqClient");
        
    	HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("emailTo", mailVO.getMailTo());
		map.put("emailFrom", mailVO.getMailFrom());
		map.put("emailTitle", mailVO.getMailTitle());
		map.put("emailType", mailVO.getMailType());
		//map.put("emailFrom", "test@test.com");
		map.put("attachmentList", mailVO.getAttachmentList());
		map.put("emailContent", mailVO.getTemplateParam());
        mqClient.send("emailSend", map);
    }
   
}
