package com.ebao.ap99.contract.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.restlet.engine.util.StringUtils;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONTRACT_STRUCTURE database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONTRACT_STRUCTURE")
@NamedQueries({
		@NamedQuery(name = "TRiContractStructure.findChildrenIdByParentId", query = "SELECT contCompId FROM TRiContractStructure s WHERE s.isActive != 'N' and s.parentId = :contCompId ORDER BY contCompId ASC"),
		@NamedQuery(name = "TRiContractStructure.getChildrenCount", query = "SELECT COUNT(1) FROM TRiContractStructure s WHERE s.isActive != 'N' and  s.parentId = :contCompId"),
		@NamedQuery(name = "TRiContractStructure.findByParentId", query = "FROM TRiContractStructure s WHERE s.isActive != 'N' and s.parentId = :parentId") })
public class TRiContractStructure extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "CONT_COMP_ID")
	private Long contCompId;

	@Column(name = "FULL_NAME")
	private String fullName;

	@Column(name = "NAME")
	private String name;

	@Column(name = "PARENT_ID")
	private Long parentId;

	@Column(name = "\"TYPE\"")
	private String type;

	@Column(name = "IS_ACTIVE")
	private String isActive;

	@Column(name = "IS_CLEANCUT")
	@org.hibernate.annotations.Type(type = "yes_no")
	private Boolean isCleanCut;

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		if (StringUtils.isNullOrEmpty(isActive)) {
			this.isActive = "Y";
		} else {
			this.isActive = isActive;
		}
	}

	public TRiContractStructure() {
		// default Y
		this.isActive = "Y";
	}

	public TRiContractStructure(Long contCompId, String name, String type, Long parentId) {
		super();
		this.contCompId = contCompId;
		this.name = name;
		this.parentId = parentId;
		this.type = type;
	}

	public Long getContCompId() {
		return this.contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getContCompId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setContCompId(key);
	}

	public Boolean getIsCleanCut() {
		return isCleanCut;
	}

	public void setIsCleanCut(Boolean isCleanCut) {
		this.isCleanCut = isCleanCut;
	}

}
