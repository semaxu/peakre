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
public class ClaimBoList implements XMLParseInterface{

	@XmlElementWrapper
	@XmlElement(name = "claimInfo")
	private List<ClaimInfo> BOList = new ArrayList<ClaimInfo>();

	public void addNewObject(ClaimInfo o) {
		BOList.add(o);
	}

	@Override
	public List getBoList() {
		return BOList;
	}
}
