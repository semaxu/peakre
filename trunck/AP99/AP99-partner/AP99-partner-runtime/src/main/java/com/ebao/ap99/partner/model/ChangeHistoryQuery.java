package com.ebao.ap99.partner.model;

import java.io.Serializable;
import javax.persistence.*;

import com.ebao.ap99.parent.BaseEntityImpl;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the T_CHANGE_HISTORY database table.
 * 
 */

public class ChangeHistoryQuery  implements Serializable {
	private static final long serialVersionUID = 1L;


	private long changeHistoryId;

	private String roleSelect;

	private String changeContent;

	private String operator;

	private long partnerId;

	private String tradingName;
	
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name ;

	private Date updateTime;
	
	private List <String> roleSelectIds;

    private int pageIndex;

    private int countPerPage;
    
	public ChangeHistoryQuery() {
	}

	public long getChangeHistoryId() {
		return this.changeHistoryId;
	}

	public void setChangeHistoryId(long changeHistoryId) {
		this.changeHistoryId = changeHistoryId;
	}

	public String getRoleSelect() {
		return this.roleSelect;
	}

	public void setRoleSelect(String roleSelect) {
		this.roleSelect = roleSelect;
	}

	public String getChangeContent() {
		return this.changeContent;
	}

	public void setChangeContent(String changeContent) {
		this.changeContent = changeContent;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public long getPartnerId() {
		return this.partnerId;
	}

	public void setPartnerId(long partnerId) {
		this.partnerId = partnerId;
	}

	public String getTradingName() {
		return this.tradingName;
	}

	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


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

    public List <String> getRoleSelectIds() {
        return roleSelectIds;
    }

    public void setRoleSelectIds(List <String> roleSelectIds) {
        this.roleSelectIds = roleSelectIds;
    }

}