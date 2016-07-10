package com.ebao.ap99.mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MailVO {	
	private String mailTitle;
	private String mailTo;
	private String mailFrom;
	//mail type to read template
	private String mailType;
	//Relative file path according to FILE_ROOT_PATH
	private List<String> attachmentList = new ArrayList<String>();
	//parameter start with $ in template, such as: $url, $username
	private Map<String, String> templateParam = new HashMap<String, String>();
	
	public void addAttachment(String relativeFilePath) {
		attachmentList.add(relativeFilePath);
	}
	
	public void putTemplateParam(String param, String value) {
		if (param.startsWith("$")) 
			param = param.substring(1);
		
		templateParam.put(param, value);
	}
	
	public String getMailTitle() {
		return mailTitle;
	}
	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}
	public String getMailTo() {
		return mailTo;
	}
	public void setMailTo(String mailTo) {
		//TODO validate email address
		this.mailTo = mailTo;
	}
	public String getMailType() {
		return mailType;
	}
	public void setMailType(String mailType) {
		this.mailType = mailType;
	}

	public List<String> getAttachmentList() {
		return attachmentList;
	}

	public Map<String, String> getTemplateParam() {
		return templateParam;
	}

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }
	

}
