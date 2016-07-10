package com.ebao.ap99.claim.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the T_RI_CLAIM_MESSAGE database table.
 * 
 */
@Entity
@Table(name="T_RI_CLAIM_MESSAGE")
@NamedQuery(name="TRiClaimMessage.findAll", query="SELECT t FROM TRiClaimMessage t")
@NamedQueries({
	@NamedQuery(name = "TRiClaimMessage.findByRefIdAndMessageType", query = "  FROM TRiClaimMessage s WHERE s.refId = :refId and s.messageType = :messageType "),
	@NamedQuery(name = "TRiClaimMessage.findByRefIdMessageTypeSectionId", query = "  FROM TRiClaimMessage s WHERE s.refId = :refId and s.messageType = :messageType and s.sectionId = :sectionId"),
	@NamedQuery(name = "TRiClaimMessage.findByRefId", query = "  FROM TRiClaimMessage s WHERE s.refId = :refId "),
	@NamedQuery(name = "TRiClaimMessage.findMessageIdByRefId", query = " select s.messageId FROM TRiClaimMessage s WHERE s.refId = :refId and s.flag ='1' "),

})
public class TRiClaimMessage extends com.ebao.unicorn.platform.data.domain.BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@SequenceGenerator(name="T_RI_CLAIM_MESSAGE_MESSAGEID_GENERATOR", sequenceName="U_SID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name="MESSAGE_ID", unique=true, nullable=false, precision=10)
	private Long messageId;

//	@Column(name="INSERT_BY", precision=10)
//	private BigDecimal insertBy;
//
//	@Temporal(TemporalType.DATE)
//	@Column(name="INSERT_TIME")
//	private Date insertTime;

	@Temporal(TemporalType.DATE)
	@Column(name="MESSAGE_DATE")
	private Date messageDate;

	@Column(name="MESSAGE_DESCRIPTION", length=500)
	private String messageDescription;

	@Column(name="MESSAGE_NAME", length=100)
	private String messageName;

	@Column(name="MESSAGE_TYPE", length=1)
	private String messageType;

	@Column(name="REF_ID", precision=10)
	private Long refId;

	@Column(name="REF_TYPE", length=1)
	private String refType;

	@Column(name="SECTION_ID", precision=10)
	private Long sectionId;
	
	private String flag;

//	@Column(name="UPDATE_BY", precision=10)
//	private BigDecimal updateBy;
//
//	@Temporal(TemporalType.DATE)
//	@Column(name="UPDATE_TIME")
//	private Date updateTime;

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public TRiClaimMessage() {
	}

	public long getMessageId() {
		return this.messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

//	public BigDecimal getInsertBy() {
//		return this.insertBy;
//	}
//
//	public void setInsertBy(BigDecimal insertBy) {
//		this.insertBy = insertBy;
//	}
//
//	public Date getInsertTime() {
//		return this.insertTime;
//	}
//
//	public void setInsertTime(Date insertTime) {
//		this.insertTime = insertTime;
//	}

	public Date getMessageDate() {
		return this.messageDate;
	}

	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}

	public String getMessageDescription() {
		return this.messageDescription;
	}

	public void setMessageDescription(String messageDescription) {
		this.messageDescription = messageDescription;
	}

	public String getMessageName() {
		return this.messageName;
	}

	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	public String getMessageType() {
		return this.messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public Long getRefId() {
		return this.refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public String getRefType() {
		return this.refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

//	public BigDecimal getUpdateBy() {
//		return this.updateBy;
//	}
//
//	public void setUpdateBy(BigDecimal updateBy) {
//		this.updateBy = updateBy;
//	}
//
//	public Date getUpdateTime() {
//		return this.updateTime;
//	}
//
//	public void setUpdateTime(Date updateTime) {
//		this.updateTime = updateTime;
//	}

	@Override
	public Long getPrimaryKey() {
		return null;
	}

	@Override
	public void setPrimaryKey(Long arg0) {
		
	}

}