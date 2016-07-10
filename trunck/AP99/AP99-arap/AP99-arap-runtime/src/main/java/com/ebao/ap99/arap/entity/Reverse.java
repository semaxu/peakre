package com.ebao.ap99.arap.entity;

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
 * The persistent class for the T_RI_BCP_REVERSE database table.
 * 
 */
@Entity
@Table(name = "T_RI_BCP_REVERSE")
@NamedQuery(name = "Reverse.findAll", query = "SELECT r FROM Reverse r")
@NamedQueries({
	@NamedQuery(name = "Reverse.findByTransNo", query = " FROM Reverse c WHERE c.transNo = :transNo")
})
public class Reverse extends BaseEntityImpl {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name = "REVERSE_ID")
	private Long reverseId;
	
	@Column(name="REMARK")
	private String remark;

	@Column(name = "REQUEST_BY")
	private Long requestBy;

	@Column(name = "REVERSE_REASON")
	private Integer reverseReason;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "TRANS_NO")
	private String transNo;

	@Column(name = "TRANS_TYPE")
	private Integer transType;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "REVERSE_DATE")
	private Date reverseDate;
	
	public Reverse() {
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTransNo() {
		return this.transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public Long getReverseId() {
		return reverseId;
	}

	public void setReverseId(Long reverseId) {
		this.reverseId = reverseId;
	}

	public Long getRequestBy() {
		return requestBy;
	}

	public void setRequestBy(Long requestBy) {
		this.requestBy = requestBy;
	}

	public Integer getReverseReason() {
		return reverseReason;
	}

	public void setReverseReason(Integer reverseReason) {
		this.reverseReason = reverseReason;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTransType() {
		return transType;
	}

	public void setTransType(Integer transType) {
		this.transType = transType;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getReverseId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setReverseId(key);
	}

	public Date getReverseDate() {
		return reverseDate;
	}

	public void setReverseDate(Date reverseDate) {
		this.reverseDate = reverseDate;
	}
}