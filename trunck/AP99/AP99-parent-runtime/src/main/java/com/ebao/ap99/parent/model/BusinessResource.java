/**
 * 
 */
package com.ebao.ap99.parent.model;

/**
 * @author yujie.zhang
 *
 */
public class BusinessResource {

	public Long businessId;
	public Long businessType;//1.contract; 2.claim/event; 3.SOA ......
	public String businessNo;//contractCode; claimNo/eventCode; SOA_NO ....
	public Long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	public Long ownerId;
	
	public Long getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	public Long getBusinessType() {
		return businessType;
	}
	public void setBusinessType(Long businessType) {
		this.businessType = businessType;
	}
	public String getBusinessNo() {
		return businessNo;
	}
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	
}
