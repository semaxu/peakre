package com.ebao.ap99.accounting.model;

public class ExceptionContractRecord {
	    private String contractID;

	    private String fnquarter;

	    private Long fnyear;

	    private String contracCompID;

	    private Boolean reviewedFlag;
	    
	    private String fnyearAndQuarter;
	    
	    public String toString() {
	        return this.fnyear + "Q" + this.fnquarter;
	    }

		public String getContractID() {
			return contractID;
		}

		public void setContractID(String contractID) {
			this.contractID = contractID;
		}

		public String getFnquarter() {
			return fnquarter;
		}

		public void setFnquarter(String fnquarter) {
			this.fnquarter = fnquarter;
		}



		public String getFnyearAndQuarter() {
			return fnyearAndQuarter;
		}

		public void setFnyearAndQuarter(String fnyearAndQuarter) {
			this.fnyearAndQuarter = fnyearAndQuarter;
		}

		public String getContracCompID() {
			return contracCompID;
		}

		public void setContracCompID(String contracCompID) {
			this.contracCompID = contracCompID;
		}

		public Boolean getReviewedFlag() {
			return reviewedFlag;
		}

		public void setReviewedFlag(Boolean reviewedFlag) {
			this.reviewedFlag = reviewedFlag;
		}

		public Long getFnyear() {
			return fnyear;
		}

		public void setFnyear(Long fnyear) {
			this.fnyear = fnyear;
		}

		
	    


	
	    
	    
	    


}
