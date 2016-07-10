/**
 * 
 */
package com.ebao.ap99.claim.restful;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.claim.convertor.ClaimMessageConvertor;
import com.ebao.ap99.claim.dao.RiClaimMessageDao;
import com.ebao.ap99.claim.model.ClaimMessage;
import com.ebao.ap99.claim.service.RiClaimMessageService;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;

/**
 * @author yujie.zhang
 *
 */
@Module(com.ebao.ap99.parent.constant.Module.CLAIM)
@Path("/message")
public class ClaimMessageRestful {

	@Autowired
	public RiClaimMessageService messageService;
	@Autowired
	public RiClaimMessageDao messageDao;
	@Autowired
	public ClaimMessageConvertor messageConvertor;
	@GET
	@Path(value = "/{RefId}")
	public Object getSectionCodeTable(@PathParam(value = "RefId") Long refId) {
		ClaimMessage claimMessage = new ClaimMessage();
		List<Long> checkMessage = messageDao.getMessageIdByRefId(refId);
		claimMessage.setMessages((Long[])checkMessage.toArray(new Long[checkMessage.size()]));
		claimMessage.setMessageTree(messageConvertor.convertorMessageList(refId));
		return claimMessage;
	}
	
	@POST
	@Path(value = "/{TreeArray}/{RefId}")
	public void submitNotRemindMessage(@PathParam(value = "TreeArray") String treeArray,
			@PathParam(value = "RefId") Long refId){
		List<Long> newSectionList = new  ArrayList<Long>();

		if (treeArray != null && !treeArray.equals("null")) {
			List<String> SectionList = (List<String>) Arrays.asList(treeArray.split(","));

			for(String sl:SectionList){
				newSectionList.add(Long.parseLong(sl)); 
			}
		}
		
		List<Long> checkMessage = messageDao.getMessageIdByRefId(refId);
		checkMessage.removeAll(newSectionList);
		
		messageService.updateUncheckMessage(checkMessage);
		messageService.updateIscheckMessage(newSectionList);
		
		
	}
	
	
}
