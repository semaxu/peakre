package com.ebao.ap99.arap.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ebao.unicorn.platform.data.domain.BaseEntityImpl;


/**
 * The persistent class for the T_RI_GL_MAPPING_ENTRY database table.
 * 
 */
@Entity
@Table(name="T_RI_GL_MAPPING_ENTRY")
@NamedQuery(name="GLMappingEntry.findAll", query="SELECT g FROM GLMappingEntry g")
public class GLMappingEntry extends BaseEntityImpl {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="GL_MAPPING_ENTRY_ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	private long glMappingEntryId;

	public GLMappingEntry() {
	}

	public long getGlMappingEntryId() {
		return this.glMappingEntryId;
	}

	public void setGlMappingEntryId(long glMappingEntryId) {
		this.glMappingEntryId = glMappingEntryId;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getGlMappingEntryId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setGlMappingEntryId(glMappingEntryId);
	}

}