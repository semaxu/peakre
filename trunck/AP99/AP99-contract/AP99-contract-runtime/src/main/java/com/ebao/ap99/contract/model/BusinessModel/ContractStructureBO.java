package com.ebao.ap99.contract.model.BusinessModel;

import java.util.ArrayList;
import java.util.List;

public class ContractStructureBO {
	private Long contCompId;
	private String type;
	private String Name;
	private String fullName;
	private Long parentId;
	private List<ContractStructureBO> structureBO = new ArrayList<ContractStructureBO>();
	
	public Long getContCompId() {
		return contCompId;
	}
	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public List<ContractStructureBO> getStructureBO() {
		return structureBO;
	}
	public void setStructureBO(List<ContractStructureBO> structureBO) {
		this.structureBO = structureBO;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
