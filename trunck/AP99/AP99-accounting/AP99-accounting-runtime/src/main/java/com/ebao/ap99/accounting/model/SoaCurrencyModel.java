package com.ebao.ap99.accounting.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;



@XmlAccessorType(XmlAccessType.FIELD)
public class SoaCurrencyModel {

	private String soaCurrencyId;

	private BigDecimal amount;

	private String soaId;
	@XmlElementWrapper
	@XmlElement(name = "SectionInfo")
	private List<SoaSectionModel> sections = new ArrayList<SoaSectionModel>();
	
	private String currencyType;

	public SoaCurrencyModel() {
		// TODO Auto-generated constructor stub
	}

	public String getSoaId() {
		return soaId;
	}

	public void setSoaId(String soaId) {
		this.soaId = soaId;
	}

	public String getSoaCurrencyId() {
		return soaCurrencyId;
	}

	public void setSoaCurrencyId(String soaCurrencyId) {
		this.soaCurrencyId = soaCurrencyId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public List<SoaSectionModel> getSections() {
		return this.sections;
	}

	public void setSections(List<SoaSectionModel> sections) {
		this.sections = sections;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

}
