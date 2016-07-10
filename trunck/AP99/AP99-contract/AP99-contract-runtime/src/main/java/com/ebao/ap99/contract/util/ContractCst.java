package com.ebao.ap99.contract.util;

public class ContractCst {
	public static String CONTRACT_LEVEL = "1";
	public static String SECTION_LEVEL = "2";
	public static String SUB_SECTION_LEVEL = "3";

	// Premium type
	public static String PREMIUM_TYPE_FLAT = "1";
	public static String PREMIUM_TYPE_FIXED = "2";
	public static String PREMIUM_TYPE_SWING = "3";
	// Premium item Type
	public static String CONTRACT_PREMIUM_EPI = "1";
	public static String CONTRACT_PREMIUM_TERRORISM = "2";
	public static String CONTRACT_PREMIUM_SUPI = "3";
	public static String CONTRACT_PREMIUM_FLAT = "4";
	public static String CONTRACT_PREMIUM_FIXED = "5";
	public static String CONTRACT_PREMIUM_SWING = "6";
	public static String CONTRACT_PREMIUM_MININUM_PREMIUM = "7";
	// public static String CONTRACT_PREMIUM_MINI_SWING = "11";
	public static String CONTRACT_PREMIUM_DEPOSIT_PREMIUM = "8";
	// public static String CONTRACT_PREMIUM_DEPOSIT_PREMIUM_SWING = "12";
	public static String CONTRACT_PREMIUM_DEPOSIT_SHEDULE = "9";
	// public static String CONTRACT_PREMIUM_DEPOSIT_SHEDULE_SWING = "13";
	// public static String CONTRACT_PREMIUM_PAYMENT_SHEDULE = "10";
	// STRUCTURE Separator
	public static String STRUCTURE_SEPARATOR = ">";

	// Deduction
	public static String CONTRACT_DEDUCTION_DETAIL_TYPE_PC = "1";
	public static String CONTRACT_DEDUCTION_DETAIL_TYPE_SS = "2";

	// Limit_proportional
	public static String CONTRACT_PROPORTIONAL_LIMIT_TYPE_QS = "1";
	public static String CONTRACT_PROPORTIONAL_LIMIT_TYPE_SURPLUS = "2";
	public static String CONTRACT_NONPROPORTIONAL_LIMIT_TYPE_XOL = "3";
	public static String CONTRACT_NONPROPORTIONAL_LIMIT_TYPE_STOPLOSS = "4";
	public static String CONTRACT_NONPROPORTIONAL_AMOUNT_TYPE_AMOUNT = "1";
	public static String CONTRACT_NONPROPORTIONAL_AMOUNT_TYPE_PERCENTAGE = "2";
	// Limit item Type
	public static String CONTRACT_LIMIT_QS = "1";
	public static String CONTRACT_LIMIT_SURPLUS = "2";
	public static String CONTRACT_LIMIT_XOL = "3";
	public static String CONTRACT_LIMIT_STOPLOSS = "4";
	public static String CONTRACT_LIMIT_STOPLOSS_AMOUNT = "5";
	public static String CONTRACT_LIMIT_STOPLOSS_PERCENTAGE = "6";
	public static String CONTRACT_LIMIT_COMB = "7";

	// CONTRACT NATURE
	public static String CONTRACT_NATURE_PROPORTIONAL = "1";
	public static String CONTRACT_NATURE_NON_PROPORTIONAL = "2";

	// contract status
	public static String CONTRACT_STATUS_DATA_INPUT = "1";
	public static String CONTRACT_STATUS_UW_REVIEW = "2";
	public static String CONTRACT_STATUS_DATA_UPDATE = "3";
	public static String CONTRACT_STATUS_INFORCE = "4";
	public static String CONTRACT_STATUS_CANCELLED = "5";

	// review result
	public static String CONTRACT_REVIEW_REJECT = "0";
	public static String CONTRACT_REVIEW_APPROVE = "1";

	// Contract operate type
	public static String CONTRACT_OPERATE_DATA_INPUT = "1";
	public static String CONTRACT_OPERATE_UW_REVIEW = "2";
	public static String CONTRACT_OPERATE_ENDORSEMENT = "3";
	public static String CONTRACT_OPERATE_ADJUSTMENT = "4";
	public static String CONTRACT_OPERATE_PRICING_ESTIMATE = "5";
	public static String CONTRACT_OPERATE_TERMINATION = "6";

	// default Main Currency
	public static String CONTRACT_DEFAULT_CURRENCY = "USD";

	// Contract Fee Entry Code
	public static String ENTRY_PROPORTIONAL_PREMIUM_FEE = "1000";
	public static String ENTRY_XOL_PREMIUM_FEE = "1010";
	public static String ENTRY_XOL_PREMIUM_FEE_ADJUSTMENT = "1011";
	public static String ENTRY_COMMISSION_FEE = "2000";
	public static String ENTRY_PROVISONAL_COMMISSION_FEE = "2010";
	public static String ENTRY_SLIDING_COMMISSION_FEE = "2020";
	public static String ENTRY_PROFIT_COMMISSION_FEE = "2030";
	public static String ENTRY_OVERRIDING_COMMISSION_FEE = "2040";
	public static String ENTRY_TAX_EXPENSE_FEE = "2071";
	public static String ENTRY_OTHER_EXPENSE_FEE = "2071";
	public static String ENTRY_BROKERAGE_FEE = "2090";

	// Deductions item type
	public static String DEDUCTION_OVERRIDING_COMMISSION = "1";
	public static String DEDUCTION_FRONTING_FEE = "2";
	public static String DEDUCTION_MANAGEMENT_FEE = "3";
	public static String DEDUCTION_TAXES = "4";
	public static String DEDUCTION_LEADER_FEE = "5";
	public static String DEDUCTION_OTHERS = "6";

	// Contract code type
	public static long CODE_TABLE_CONT_PROTECTION_TYPE = 810015;
	public static long CODE_TABLE_CONT_CLAIM_BASIS_TYPE = 810016;
	public static long CODE_TABLE_CONT_ACCOUNT_BASIS_TYPE = 810017;
	public static long CODE_TABLE_CONT_ACCOUNT_EARNING_TYPE = 810018;
	public static long CODE_TABLE_CONT_ACCOUNT_WRITTEN_TYPE = 810019;
	public static long CODE_TABLE_CONT_ACCOUNT_FRE_TYPE = 810020;
	public static long CODE_TABLE_CONT_SHARE_DEFINED_TYPE = 810021;
	public static long CODE_TABLE_CONT_PREMIUM_CALC_TYPE = 810022;
	public static long CODE_TABLE_CONT_LIMIT_RISKEVENT_TYPE = 810023;
	public static long CODE_TABLE_CONT_COMMISSION_TYPE = 810024;
	public static long CODE_TABLE_CONT_ENDORSEMENT_TYPE = 810025;
	public static long CODE_TABLE_CONTRACT_PERIL_TYPE = 810026;
	public static long CODE_TABLE_CONT_UW_COMPANY_TYPE = 810027;
	public static long CODE_TABLE_CONTRACT_COB_TYPE = 810028;
	public static long CODE_TABLE_CONTRACT_SUBCOB_TYPE = 810029;
	public static long CODE_TABLE_CONTRACT_STATUS = 810030;
	public static long CODE_TABLE_CONTRACT_NATURE_TYPE = 810001;
	public static long CODE_TABLE_CONTRACT_CATEGORY_TYPE = 810002;
	public static long CODE_TABLE_CONTRACT_TYPE = 810003;
	public static long CODE_TABLE_CONT_SHARE_CATE_TYPE = 810004;
	public static long CODE_TABLE_CONT_SHARE_TYPE = 810005;
	public static long CODE_TABLE_CONT_CLAIM_CATE_TYPE = 810006;
	public static long CODE_TABLE_CONT_CLAIM_WEATHER_TYPE = 810007;
	public static long CODE_TABLE_CONT_CLAIM_PRICING_TYPE = 810008;
	public static long CODE_TABLE_CONT_TERMINATION_TYPE = 810009;
	public static long CODE_TABLE_CONT_EXCHANGE_TYPE = 810010;
	public static long CODE_TABLE_CONT_EPI_TYPE = 810011;
	public static long CODE_TABLE_CONT_PREMIUM_DEFINED_TYPE = 810012;
	public static long CODE_TABLE_CONT_DEDUCTION_ITEM_TYPE = 810013;
	public static long CODE_TABLE_CONT_LIMIT_EVENT_TYPE = 810014;
	public static long CODE_TABLE_CLAUSE_REQUIRED_TYPE = 810031;
	public static long CODE_TABLE_CLAUSE_RECOMMEND_TYPE = 810032;
	public static long CODE_TABLE_CONTRACT_COUNTRYE = 800019;

}
