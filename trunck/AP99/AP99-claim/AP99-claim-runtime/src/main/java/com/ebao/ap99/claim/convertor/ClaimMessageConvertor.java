/**
 * 
 */
package com.ebao.ap99.claim.convertor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.claim.constant.ClaimConstant;
import com.ebao.ap99.claim.dao.RiClaimMessageDao;
import com.ebao.ap99.claim.entity.TRiClaimMessage;
import com.ebao.ap99.parent.model.TreeModel;

/**
 * @author yujie.zhang
 *
 */
public class ClaimMessageConvertor {

	@Autowired
	public RiClaimMessageDao messageDao;

	public void convertorClaimMessage() {

	}

	public List<TreeModel> convertorMessageList(Long refId) {

		List<TRiClaimMessage> messageLists = messageDao.getClaimMessageByRefId(refId);
		List<TreeModel> returnList = new ArrayList<TreeModel>();
		for (TRiClaimMessage m : messageLists) {
			TreeModel treeModel = new TreeModel();
			treeModel.setId(m.getMessageId());
			treeModel.setText(m.getMessageDescription());
			returnList.add(treeModel);
		}
		return returnList;
	}

	public List<Long> getMessageIdList(Long refId) {

		List<TRiClaimMessage> messageLists = messageDao.getClaimMessageByRefId(refId);
		List<Long> returnMessageIdList = new ArrayList<Long>();
		for (TRiClaimMessage m : messageLists) {

			returnMessageIdList.add(m.getMessageId());

		}
		return returnMessageIdList;
	}

	public List<TRiClaimMessage> getAmontMessage(List<TRiClaimMessage> message) {
		
		List<TRiClaimMessage> messageReturn = new ArrayList<TRiClaimMessage>();
		for (TRiClaimMessage t : message) {

			if (t.getMessageType().equals(ClaimConstant.CLAIM_AMOUNT_MESSAGE)
					|| t.getMessageType().equals(ClaimConstant.EVENT_AMOUNT_MESSAGE)) {
				messageReturn.add(t);
			}
		}
		
		return messageReturn;
	}

	public List<TRiClaimMessage> getUnCheckedMessage(List<TRiClaimMessage> message){
		List<TRiClaimMessage> messageReturn = new ArrayList<TRiClaimMessage>();
		for (TRiClaimMessage t : message) {

			if (t.getFlag().equals(ClaimConstant.MESSAGE_UNCHECKED)) {
				messageReturn.add(t);
			}
		}
		
		return messageReturn;
	}
}
