package com.ebao.ap99.arap.vo;

import java.util.List;

import com.ebao.ap99.accounting.YearQuarter;

public class GLPostingCallBack {
	private YearQuarter yearQuarter;
	private List<GLPostingVO> postDetailList;
	public YearQuarter getYearQuarter() {
		return yearQuarter;
	}
	public void setYearQuarter(YearQuarter yearQuarter) {
		this.yearQuarter = yearQuarter;
	}
	public List<GLPostingVO> getPostDetailList() {
		return postDetailList;
	}
	public void setPostDetailList(List<GLPostingVO> postDetailList) {
		this.postDetailList = postDetailList;
	}
	
}
