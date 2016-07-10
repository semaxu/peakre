package com.ebao.ap99.claim.constant;

public class ClaimConstant {
	// public static final String CLAIM_BUSINESS_TYPE__FINANCIAL = "1";
	// public static final String CLAIM_BUSINESS_TYPE__RETROCESSION = "2";

	public static final String CLAIM_STATUS__OPEN = "0";
	public static final String CLAIM_STATUS__CLOSE = "1";

	public static final String CLAIM_RESERVE_STATUS__NEW = "0";
	public static final String CLAIM_RESERVE_STATUS__INFORCE = "1";

	public static final String CLAIM_SETTLEMENT_STATUS__PENDING_SUBMIT = "1";
	public static final String CLAIM_SETTLEMENT_STATUS__PENDING_APPROVAL = "2";
	public static final String CLAIM_SETTLEMENT_STATUS__APPROVED = "3";
	public static final String CLAIM_SETTLEMENT_STATUS__REJECTED = "4";

	public static final String CLAIM_POSTING_FLAG_YES = "1";
	public static final String CLAIM_POSTING_FLAG_NO = "0";

	public static final String CLAIM_BUSINESS_DIRECTION__FINANCIAL = "1";
	public static final String CLAIM_BUSINESS_DIRECTION__RETROCESSION = "2";
	
	public static final String CLAIM_SETTLEMENT__SUBMIT = "1";
	public static final String CLAIM_SETTLEMENT__CHANGEPOSTING = "2";
	
	// BusiType 1.reserve 2.settlement
	public static final String BUSI_TYPE_RESERVE = "1";
	public static final String BUSI_TYPE_SETTLEMENT = "2";
	// EntryType 1.opening 2.closing 3.settlement
	public static final String ENTRY_TYPE_OPENING = "1";
	public static final String ENTRY_TYPE_CLOSING = "2";
	
	//1.Loss(Indemnity),2.Expense,3.Additional case reserve,4.Reinstatement provision,5.Tax,6.Others
	public static final String LOSS = "1";
	public static final String EXPENSE ="2";
	public static final String ADDITIONAL ="3";
	public static final String RIP = "4";
	public static final String TAX = "5";
	public static final String OTHER = "6";
	//bizTransType 1.CLAIM_RESERVE 2.CLAIM_SETTLEMENT
	
	public static final int BIZ_CLAIM_RESERVE = 1;
	public static final int BIZ_CLAIM_SETTLEMENT = 2;
	
	// contractCategory 1.Assumed 2.retro
	public static final int CONTRACTCATEGORY_ASSUMED = 1;
	public static final int CONTRACTCATEGORY_RETRO = 2;
	
	//reserve postingType
	/**
	 * 1.new reserve submit opening:0 closing:amountOc
	 * 2.update reserve posting change No to yes
	 * 3.update reserve posting change Yes to No
	 * 4.update reserve amount change only
	 */
	public static final String NEW_RESERVE_SUBMIT = "1";
	public static final String UPDATE_RESERVE_POSTING_NO_TO_YES = "2";
	public static final String UPDATE_RESERVE_POSTING_YES_TO_NO = "3";
	public static final String UPDATE_RESERVE_AMOUNT_CHANGE_ONLY ="4";
	
	//reporting currency
	public static final String REPORTING_CURRENCY = "USD";
	
	//summary type
	public static final String FINANCIAL =  "Financial";
	public static final String RETROCESSION = "Retrocession";
	public static final String TOTALNET = "Total Net";
	public static final String GROSSCLAIM = "Gross Claim";
	public static final String GROSSBEFOREEVENT = "Gross Before Event Retrocession";
	public static final String EVENTRETROCESSION = "Retrocession at Event Level";
	
	//message type
	//1.PortfolioTransfer 2.claim amount message 3.event amount message
	public static final String PORTFOLIOTRANSFER = "1";
	public static final String CLAIM_AMOUNT_MESSAGE = "2";
	public static final String EVENT_AMOUNT_MESSAGE = "3";
	
	//amount level one 100,000  level two 1,000,000
	public static final double AMOUNT_LEVEL_ONE = 100000;
	public static final double AMOUNT_LEVEL_TWO = 1000000;
	
	//claim message
	public static final String AMOUNT_LEVEL_ONE_CLAIM_DESC = "The total loss of this claim has hit USD 100,000.";
	public static final String AMOUNT_LEVEL_TWO_CLAIM_DESC = "The total loss of this claim has hit USD 1,000,000.";
	//event message
	public static final String AMOUNT_LEVEL_ONE_EVENT_DESC = "The total loss of this event has hit USD 100,000.";
	public static final String AMOUNT_LEVEL_TWO_EVENT_DESC = "The total loss of this event has hit USD 1,000,000.";
	//PortfolioTransfer message 
	public static final String PORTFOLIO_TRANSFER_MESSAGE = " is under portfolio withdrawal.";
	
	//ref_type  1.claim 2.event
	
	public static final String REF_TYPE_CLAIM = "1";
	public static final String REF_TYPE_EVENT = "2";
	
	//message isChecked
	public static final String MESSAGE_UNCHECKED = "0";
	public static final String MESSAGE_ISCHECKED = "1";
	
	//PortfolioTrans withdrawal
	public static final String IS_WITHDRAWAL = "1";
	
}
