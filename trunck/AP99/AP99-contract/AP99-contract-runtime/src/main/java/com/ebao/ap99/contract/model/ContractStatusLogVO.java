package com.ebao.ap99.contract.model;

import java.util.Date;

import com.ebao.ap99.contract.entity.TRiContOperation;

public class ContractStatusLogVO extends TRiContOperation {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date updateDate;
	private Long updateUserId;

	public Date getUpdateDate() {
		return this.updateDate == null ? this.getUpdateTime() : this.updateDate;
	}

	public void setUpdateDate(Date updateData) {
		this.updateDate = updateData;
	}

	public Long getUpdateUserId() {
		return this.updateUserId == null ? this.getUpdateBy() : this.updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}
}
