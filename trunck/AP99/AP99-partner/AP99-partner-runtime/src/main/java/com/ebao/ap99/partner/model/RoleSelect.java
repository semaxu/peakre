package com.ebao.ap99.partner.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


public class RoleSelect implements Serializable {
	private static final long serialVersionUID = 1L;

	private long roleSelectId;
    private String roleType;
    private String roleId;
    

    
    public long getRoleSelectId() {
        return roleSelectId;
    }


    public void setRoleSelectId(long roleSelectId) {
        this.roleSelectId = roleSelectId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }


    private Long insertBy;
    private Date insertTime;
	
	public RoleSelect() {
	}

    public String getRoleType() {
        return roleType;
    }


    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }


    public Long getInsertBy() {
        return insertBy;
    }


    public void setInsertBy(Long insertBy) {
        this.insertBy = insertBy;
    }


    public Date getInsertTime() {
        return insertTime;
    }


    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
	
	

}