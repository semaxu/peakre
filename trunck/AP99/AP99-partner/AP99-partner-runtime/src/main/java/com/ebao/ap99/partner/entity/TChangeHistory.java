package com.ebao.ap99.partner.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.ebao.ap99.parent.BaseEntityImpl;

import java.util.Date;


/**
 * The persistent class for the T_CHANGE_HISTORY database table.
 * 
 */
@Entity
@Table(name="T_RI_BP_CHANGE_HISTORY")
@NamedQuery(name="TChangeHistory.findAll", query="SELECT t FROM TChangeHistory t")
public class TChangeHistory  extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="S_UID")
	@Column(name="CHANGE_HISTORY_ID")
	private long changeHistoryId;

	@Column(name="ROLE_SELECT")
	private String roleSelect;

	@Column(name="CHANGE_CONTENT")
	private String changeContent;
    
	@Column(name="OPERATOR")
	private String operator;

	@Column(name="PARTNER_ID")
	private long partnerId;

	@Column(name="TRADING_NAME")
	private String tradingName;
	
    @Column(name="FIRST_NAME")
	private String firstName;
	
    @Column(name="LAST_NAME")
	private String lastName;
   
    @Column(name="BUSINESS_PARTNER_CATEGORY")
    private String businessPartnerCategory;
	

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

	
    
	public TChangeHistory() {
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

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    

    public String getBusinessPartnerCategory() {
        return businessPartnerCategory;
    }

    public void setBusinessPartnerCategory(String businessPartnerCategory) {
        this.businessPartnerCategory = businessPartnerCategory;
    }

    @Override
    public Long getPrimaryKey() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setPrimaryKey(Long paramLong) {
        // TODO Auto-generated method stub
        
    }

}