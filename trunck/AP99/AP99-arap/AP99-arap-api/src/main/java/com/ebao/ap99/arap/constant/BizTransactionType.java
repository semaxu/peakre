package com.ebao.ap99.arap.constant;

public enum BizTransactionType {
	CLAIM_RESERVE(1),
	CLAIM_SETTLEMENT(2), 
	STATEMENT(3), 
	IBNR_UPLOAD(4), 
	ESTIMATION(5),
	ARAP_COLLECTION(6),
	ARAP_PAYMENT(7),
	ARAP_INTERNAL_OFFSET(8),
	ARAP_TRANS_REVERSAL(9),
	RE_VALUATION(10);
	
	public static final Integer CATE_CLAIM = 1;
	public static final Integer CATE_STATEMENT = 2;
	public static final Integer CATE_ARAP = 3;
	
	private Integer type;
	
	private BizTransactionType(Integer type){
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	public static Integer getCategory(Integer bizTransType){
		if(bizTransType != null){
			switch(bizTransType){
			case 1: return 1;
			case 2: return 1;
			case 3: return 2;
			case 4: return 2;
			case 5: return 2;
			case 6: return 3;
			case 7: return 3;
			case 8: return 3;
			case 9: return 3;
			case 10: return 2;
			}
		}
		return null;
	}
	public static String getName(Integer bizTransType){
		if(bizTransType != null){
			switch(bizTransType){
			case 1: return "Claim Reserve";
			case 2: return "Claim Settlement";
			case 3: return "Statement";
			case 4: return "IBNR Upload";
			case 5: return "Estimation";
			case 6: return "Collection";
			case 7: return "Payment";
			case 8: return "Internal Offset";
			case 9: return "Transaction Reversal";
			case 10: return "Revaluation";
			}
		}
		return null;
	}
}
