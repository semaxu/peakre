/**
 * 
 */
package com.ebao.ap99.claim.model;

import java.util.List;

import com.ebao.ap99.parent.model.TreeModel;

/**
 * @author yujie.zhang
 *
 */
public class ClaimMessage {

	private Long[] messages;
	
	private List<TreeModel> messageTree;

	public Long[] getMessages() {
		return messages;
	}

	public void setMessages(Long[] messages) {
		this.messages = messages;
	}

	public List<TreeModel> getMessageTree() {
		return messageTree;
	}

	public void setMessageTree(List<TreeModel> messageTree) {
		this.messageTree = messageTree;
	}
	
	
}
