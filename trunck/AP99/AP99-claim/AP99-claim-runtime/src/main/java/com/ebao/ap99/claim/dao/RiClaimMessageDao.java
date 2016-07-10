/**
 * 
 */
package com.ebao.ap99.claim.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;

import com.ebao.ap99.claim.entity.TRiClaimMessage;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

/**
 * @author yujie.zhang
 *
 */
public class RiClaimMessageDao extends BaseDao<TRiClaimMessage>{

	@Override
	public Class<TRiClaimMessage> getEntityClass() {
		return TRiClaimMessage.class;
	}
	
	public TRiClaimMessage getClaimMessage(Long refId,String messageType) {
		final Query query = getEntityManager().createNamedQuery("TRiClaimMessage.findByRefIdAndMessageType", TRiClaimMessage.class);
		query.setParameter("refId", refId);
		query.setParameter("messageType", messageType);
		@SuppressWarnings("unchecked")
		List<TRiClaimMessage> list = query.getResultList();
		TRiClaimMessage claimMessage = new TRiClaimMessage();
		if(CollectionUtils.isNotEmpty(list)){
			claimMessage  = list.get(0);
		}
		return claimMessage;
	}

	public TRiClaimMessage getClaimMessageBySectionId(Long refId,String messageType,Long sectionId) {
		final Query query = getEntityManager().createNamedQuery("TRiClaimMessage.findByRefIdMessageTypeSectionId", TRiClaimMessage.class);
		query.setParameter("refId", refId);
		query.setParameter("messageType", messageType);
		query.setParameter("sectionId", sectionId);

		@SuppressWarnings("unchecked")
		List<TRiClaimMessage> list = query.getResultList();
		TRiClaimMessage claimMessage = new TRiClaimMessage();
		if(CollectionUtils.isNotEmpty(list)){
			claimMessage  = list.get(0);
		}
		return claimMessage;
	}
	
	public List<TRiClaimMessage> getClaimMessageByRefId(Long refId) {
		final Query query = getEntityManager().createNamedQuery("TRiClaimMessage.findByRefId", TRiClaimMessage.class);
		query.setParameter("refId", refId);

		@SuppressWarnings("unchecked")
		List<TRiClaimMessage> list = query.getResultList();
		
		return list;
	}
	//TRiClaimMessage.findMessageIdByRefId
	public List<Long> getMessageIdByRefId(Long refId){
		
		final Query query = getEntityManager().createNamedQuery("TRiClaimMessage.findMessageIdByRefId", Long.class);
		query.setParameter("refId", refId);

		@SuppressWarnings("unchecked")
		List<Long> list = query.getResultList();

		return list;
	}
}
