package com.ebao.ap99.accounting.UI.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoaLineViewVO {
	
	private String lineTitle;
	
	private String total = "0";
	
	//<position, value>
	private Map<Integer, String> colValueMap = new HashMap<Integer, String>();
	
	public void setColumnValue(Integer index, String value) 
	{
		colValueMap.put(index, value);
	}
	
	public Map<Integer, String> getColValueMap() 
	{
		return colValueMap;
	}
	
	private String column1;
	
	private String column2;
		
	private String column3;
	
	private String column4;
	
	private String column5;
		
	private String column6;
	
	private String column7;
	
	private String column8;
	
	private String column9;
	
	private String column10;
	
	private String column11;
	
	private String column12;
	
	private String column13;
	
	private String column14;
	
	private String column15;
	
	private String column16;
	
	private String column17;
	
	private String column18;
	
	private String column19;
	
	private String column20;
	
	
	
	private int orderOfSoaViews;
	
	public SoaLineViewVO(String title) {
		this.lineTitle = title;
	}

	public String getLineTitle() {
		return lineTitle;
	}

	public void setLineTitle(String lineTitle) {
		this.lineTitle = lineTitle;
	}
	
	

	public String getColumn1() {
		return colValueMap.get(1);
	}

	public String getColumn2() {
		return colValueMap.get(2);
	}

	public String getColumn3() {
		return colValueMap.get(3);
	}

	public String getColumn4() {
		return colValueMap.get(4);
	}

	public String getColumn5() {
		return colValueMap.get(5);
	}

	public String getColumn6() {
		return colValueMap.get(6);
	}

	public String getColumn7() {
		return colValueMap.get(7);
	}

	public String getColumn8() {
		return colValueMap.get(8);
	}


	public String getColumn9() {
		return colValueMap.get(9);
	}


	public String getColumn10() {
		return colValueMap.get(10);
	}

	public String getColumn11() {
		return colValueMap.get(11);
	}


	public String getColumn12() {
		return colValueMap.get(12);
	}


	public String getColumn13() {
		return colValueMap.get(13);
	}

	public String getColumn14() {
		return colValueMap.get(14);
	}


	public String getColumn15() {
		return colValueMap.get(15);
	}

	public String getColumn16() {
		return colValueMap.get(16);
	}


	public String getColumn17() {
		return colValueMap.get(17);
	}


	public String getColumn18() {
		return colValueMap.get(18);
	}


	public String getColumn19() {
		return colValueMap.get(19);
	}



	public String getColumn20() {
		return colValueMap.get(20);
	}


	public int getOrderOfSoaViews() {
		return orderOfSoaViews;
	}

	public void setOrderOfSoaViews(int orderOfSoaViews) {
		this.orderOfSoaViews = orderOfSoaViews;
	}

	public String getTotal() {
		return this.total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
	
	

	
	

}
