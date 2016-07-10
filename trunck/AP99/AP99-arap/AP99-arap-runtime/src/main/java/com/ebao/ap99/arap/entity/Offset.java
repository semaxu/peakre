package com.ebao.ap99.arap.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ebao.unicorn.platform.data.domain.BaseEntityImpl;

/**
 * The persistent class for the T_RI_BCP_OFFSET database table.
 * 
 */
@Entity
@Table(name = "T_RI_BCP_OFFSET")
@NamedQuery(name = "Offset.findAll", query = "SELECT o FROM Offset o")
@NamedQueries({
	@NamedQuery(name = "Offset.findByOffsetNo", query = " FROM Offset c WHERE c.offsetNo = :offsetNo")
})
public class Offset extends BaseEntityImpl {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name = "OFFSET_ID")
	private Long offsetId;

	@Temporal(TemporalType.DATE)
	@Column(name = "OFFSET_DATE")
	private Date offsetDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "REGISTRATION_DATE")
	private Date registrationDate;

	@Column(name = "OFFSET_NO")
	private String offsetNo;

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "AMOUNT")
	private BigDecimal amount;

	@Column(name = "CURRENCY_CODE")
	private String currencyCode;

	public Offset() {
	}

	public Date getOffsetDate() {
		return this.offsetDate;
	}

	public void setOffsetDate(Date offsetDate) {
		this.offsetDate = offsetDate;
	}

	public Long getOffsetId() {
		return this.offsetId;
	}

	public void setOffsetId(Long offsetId) {
		this.offsetId = offsetId;
	}

	public String getOffsetNo() {
		return this.offsetNo;
	}

	public void setOffsetNo(String offsetNo) {
		this.offsetNo = offsetNo;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getOffsetId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setOffsetId(key);
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

}