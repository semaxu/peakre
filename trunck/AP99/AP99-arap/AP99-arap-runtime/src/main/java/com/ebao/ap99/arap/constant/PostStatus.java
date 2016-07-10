package com.ebao.ap99.arap.constant;

public enum PostStatus {
	WAIT_FOR_POST(0), 
	SUB_LEDGER_MAPPING(1),
	POSTED(2), 
	POST_FAIL(-1);

	private Integer type;

	private PostStatus(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
