/**
 * 
 */
package com.ebao.ap99.claim.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.ebao.ap99.file.service.XMLParseInterface;

/**
 * @author yujie.zhang
 *
 */
@XmlRootElement(name = "Root")
public class EventBoList implements XMLParseInterface{

	@XmlElementWrapper
	@XmlElement(name = "eventInfo")
	private List<EventInfo> BOList = new ArrayList<EventInfo>();

	public void addNewObject(EventInfo o) {
		BOList.add(o);
	}

	@Override
	public List getBoList() {
		return BOList;
	}
}
