/**
 * 
 */
package com.ebao.ap99.claim.convertor;

import com.ebao.ap99.claim.constant.ClaimConstant;
import com.ebao.ap99.claim.constant.EntryCodeConstant;

/**
 * @author yujie.zhang
 *
 */
public class EntryCodeConvertor {
	/**
	 * 
	 * @param LossType
	 *            1.Loss(Indemnity),2.Expense,3.Additional case
	 *            reserve,4.Reinstatement provision,5.Tax,6.Others
	 * @param BusiType
	 *            1.reserve 2.settlement
	 * @param EntryType
	 *            1.opening 2.closing
	 * @return
	 */
	public  String conertorEntryCode(String LossType, String BusiType, String EntryType) {
		String entryCode = "";

		if (BusiType.equals(ClaimConstant.BUSI_TYPE_RESERVE)) {
			if (EntryType.equals(ClaimConstant.ENTRY_TYPE_CLOSING)) {
				if (LossType.equals(ClaimConstant.LOSS))
					entryCode = EntryCodeConstant.LOSS_RESERVE_CLOSING;
				if (LossType.equals(ClaimConstant.EXPENSE))
					entryCode = EntryCodeConstant.EXPENSE_CLOSING;
				if (LossType.equals(ClaimConstant.ADDITIONAL))
					entryCode = EntryCodeConstant.ADDITIONAL_LOSS_CLOSING;
				if (LossType.equals(ClaimConstant.RIP))
					entryCode = EntryCodeConstant.RIP_PROVISION_CLOSING;
				if (LossType.equals(ClaimConstant.TAX))
					entryCode = EntryCodeConstant.TAX_CLOSING;
				if (LossType.equals(ClaimConstant.OTHER))
					entryCode = EntryCodeConstant.OTHERS_CLOSING;
			} else {
				if (LossType.equals(ClaimConstant.LOSS))
					entryCode = EntryCodeConstant.LOSS_RESERVE_OPPENING;
				if (LossType.equals(ClaimConstant.EXPENSE))
					entryCode = EntryCodeConstant.EXPENSE_OPPENING;
				if (LossType.equals(ClaimConstant.ADDITIONAL))
					entryCode = EntryCodeConstant.ADDITIONAL_LOSS_OPPENING;
				if (LossType.equals(ClaimConstant.RIP))
					entryCode = EntryCodeConstant.RIP_PROVISION_OPENING;
				if (LossType.equals(ClaimConstant.TAX))
					entryCode = EntryCodeConstant.TAX_OPENING;
				if (LossType.equals(ClaimConstant.OTHER))
					entryCode = EntryCodeConstant.OTHERS_OPENING;
			}
		} else {
			if (LossType.equals(ClaimConstant.LOSS))
				entryCode = EntryCodeConstant.SETTLEMENT_LOSS;
			if (LossType.equals(ClaimConstant.EXPENSE))
				entryCode = EntryCodeConstant.SETTLEMENT_EXPENSE;
			if (LossType.equals(ClaimConstant.RIP))
				entryCode = EntryCodeConstant.SETTLEMENT_RIP;
			if (LossType.equals(ClaimConstant.TAX))
				entryCode = EntryCodeConstant.SETTLEMENT_TAX;
			if (LossType.equals(ClaimConstant.OTHER))
				entryCode = EntryCodeConstant.SETTLEMENT_OTHER;

		}
		return entryCode;
	}

}
