package com.ebao.ap99.accounting.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.ebao.ap99.file.service.XMLParseInterface;

@XmlRootElement(name = "Root")
public class SoaBOList implements XMLParseInterface {

	@XmlElementWrapper
	@XmlElement(name = "SoaInfo")
	private List<SoaModel> BOList = new ArrayList<SoaModel>();

	public void addNewObject(SoaModel o) {
		BOList.add(o);
	}

	@Override
	public List getBoList() {
		return BOList;
	}
}

