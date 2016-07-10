/**
 * 
 */
package com.ebao.ap99.parent.model;

/**
 * @author yujie.zhang
 *
 */
public class LockModel {

	
	
	public Long opeartId;
	public String opeartName;
	public Long lockUserId;
	public String  lockUserName;
	
	//public Long lockId;
	public Long resourceId;
	public Long resourceType;//1.contract; 2.claim/event; 3.SOA ......
	public String resourceNo;//contractCode; claimNo/eventCode; SOA_NO ....
	public Long ownerId;
	public String  ownerName;

	//public BusinessResource businessResource;
	
	public Integer messageType;//0.success 1.fail
	public String message;

	
	
	public Integer getMessageType() {
		return messageType;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOpeartName() {
		return opeartName;
	}

	public void setOpeartName(String opeartName) {
		this.opeartName = opeartName;
	}

	public Long getLockUserId() {
		return lockUserId;
	}

	public void setLockUserId(Long lockUserId) {
		this.lockUserId = lockUserId;
	}

	public String getLockUserName() {
		return lockUserName;
	}

	public void setLockUserName(String lockUserName) {
		this.lockUserName = lockUserName;
	}

	

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Long getResourceType() {
		return resourceType;
	}

	public void setResourceType(Long resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceNo() {
		return resourceNo;
	}

	public void setResourceNo(String resourceNo) {
		this.resourceNo = resourceNo;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Long getOpeartId() {
		return opeartId;
	}

	public void setOpeartId(Long opeartId) {
		this.opeartId = opeartId;
	}

//	public Long getLockId() {
//		return lockId;
//	}
//
//	public void setLockId(Long lockId) {
//		this.lockId = lockId;
//	}
//	public BusinessResource getBusinessResource() {
//		return businessResource;
//	}
//
//	public void setBusinessResource(BusinessResource businessResource) {
//		this.businessResource = businessResource;
//	}
	
	
}
