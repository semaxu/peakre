package com.ebao.ap99.contract.entity;

public class ContractSearchModel extends ContractModel {
    private int pageIndex;

    private int countPerPage;

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getCountPerPage() {
		return countPerPage;
	}

	public void setCountPerPage(int countPerPage) {
		this.countPerPage = countPerPage;
	}
}
