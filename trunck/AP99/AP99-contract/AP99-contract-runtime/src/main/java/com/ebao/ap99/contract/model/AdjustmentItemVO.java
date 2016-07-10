/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.model;

import java.util.ArrayList;
import java.util.List;

import com.ebao.ap99.contract.entity.TRiContAdjustmentItem;
import com.ebao.ap99.contract.entity.TRiContAdjustmentItemLog;

/**
 * @author weiping.wang
 *
 */
public class AdjustmentItemVO {
	private String panelTitle;
	private String adjustedSUPIDefined;
	private List<TRiContAdjustmentItem> adjustedSUPIList = new ArrayList<TRiContAdjustmentItem>();
	private List<TRiContAdjustmentItem> deleteAdjustedSUPIList = new ArrayList<TRiContAdjustmentItem>();
	private List<TRiContAdjustmentItemLog> hisList = new ArrayList<TRiContAdjustmentItemLog>();

	public String getPanelTitle() {
		return panelTitle;
	}

	public void setPanelTitle(String panelTitle) {
		this.panelTitle = panelTitle;
	}

	public String getAdjustedSUPIDefined() {
		return adjustedSUPIDefined;
	}

	public void setAdjustedSUPIDefined(String adjustedSUPIDefined) {
		this.adjustedSUPIDefined = adjustedSUPIDefined;
	}

	public List<TRiContAdjustmentItem> getAdjustedSUPIList() {
		return adjustedSUPIList;
	}

	public void setAdjustedSUPIList(List<TRiContAdjustmentItem> adjustedSUPIList) {
		this.adjustedSUPIList = adjustedSUPIList;
	}

	public List<TRiContAdjustmentItem> getDeleteAdjustedSUPIList() {
		return deleteAdjustedSUPIList;
	}

	public void setDeleteAdjustedSUPIList(List<TRiContAdjustmentItem> deleteAdjustedSUPIList) {
		this.deleteAdjustedSUPIList = deleteAdjustedSUPIList;
	}

	public List<TRiContAdjustmentItemLog> getHisList() {
		return hisList;
	}

	public void setHisList(List<TRiContAdjustmentItemLog> hisList) {
		this.hisList = hisList;
	}

}
