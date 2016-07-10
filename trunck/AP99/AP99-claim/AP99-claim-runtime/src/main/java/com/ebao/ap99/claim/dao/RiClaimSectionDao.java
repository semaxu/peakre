package com.ebao.ap99.claim.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.ebao.ap99.claim.entity.TRiClaimSectionInfo;
import com.ebao.ap99.parent.model.TreeModel;
//import com.ebao.ap99.contract.entity.TRiContractStructure;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
//import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class RiClaimSectionDao  extends BaseDao<TRiClaimSectionInfo>{
	//private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiClaimSectionInfo> getEntityClass() {
		return TRiClaimSectionInfo.class;
	}
	/*
	 * return TRiClaimSectionInfo list
	 */
	public List<TRiClaimSectionInfo> getCheckedSectionList(Long refId,String businessDirection) {
		final Query query = getEntityManager().createNamedQuery("TRiClaimSectionInfo.findByRefId", TRiClaimSectionInfo.class);
		query.setParameter("refId", refId);
		query.setParameter("businessDirection", businessDirection);
		@SuppressWarnings("unchecked")
		List<TRiClaimSectionInfo> list = query.getResultList();
		return list;
	}
	/*
	 * return SectionId list
	 */
	public List<Long> getCheckedSectionIdList(Long refId,String businessDirection) {
		final Query query = getEntityManager().createNamedQuery("TRiClaimSectionInfo.findSectionIdByRefId", Long.class);
		query.setParameter("refId", refId);
		query.setParameter("businessDirection", businessDirection);
		@SuppressWarnings("unchecked")
		List<Long> list = query.getResultList();
		return list;
	}
	
	/*
	 * return SectionId list
	 */
	public List<Long> getExistSectionIdList(Long refId,String businessDirection) {
		final Query query = getEntityManager().createNamedQuery("TRiClaimSectionInfo.findSectionIdByRefIdBusinDirec", Long.class);
		query.setParameter("refId", refId);
		query.setParameter("businessDirection", businessDirection);
		@SuppressWarnings("unchecked")
		List<Long> list = query.getResultList();
		return list;
	}
	
	
	public List<Long> getrefIdBysectionId(Long sectionId) {
		final Query query = getEntityManager().createNamedQuery("TRiClaimSectionInfo.findrefIdbySectionId", Long.class);
		query.setParameter("sectionId", sectionId);
		@SuppressWarnings("unchecked")
		List<Long> list = query.getResultList();
		return list;
	}
	
	
	/*
	 * return SectionModel
	 */
	public TRiClaimSectionInfo loadSectionbySectionId(Long claimId,String businessDirection,Long sectionId) {
		final Query query = getEntityManager().createNamedQuery("TRiClaimSectionInfo.findByRefIdAndSectionId", TRiClaimSectionInfo.class);
		query.setParameter("refId", claimId);
		query.setParameter("sectionId", sectionId);
		query.setParameter("businessDirection", businessDirection);
		TRiClaimSectionInfo sectionInfo = (TRiClaimSectionInfo) query.getSingleResult();
		return sectionInfo;
	}
	/**
	 * 
	 * @param sectionId
	 * @return ContractId
	 */
	public Long getContractIdBySectionId(Long sectionId){
		final Query query = getEntityManager().createNamedQuery("TRiClaimSectionInfo.findByRefSectionId", TRiClaimSectionInfo.class);
		query.setParameter("sectionId", sectionId);
		@SuppressWarnings("unchecked")
		List<TRiClaimSectionInfo> sectionInfoList = (List<TRiClaimSectionInfo>) query.getResultList();
		TRiClaimSectionInfo sectionInfo = new TRiClaimSectionInfo();
		if(CollectionUtils.isNotEmpty(sectionInfoList)){
			sectionInfo  = sectionInfoList.get(0);
		}
		return sectionInfo.getContractId().longValue();
	}
	
	public TRiClaimSectionInfo getClaimSectionInfoBySection(Long sectionId){
		
		final Query query = getEntityManager().createNamedQuery("TRiClaimSectionInfo.findByRefSectionId", TRiClaimSectionInfo.class);
		query.setParameter("sectionId", sectionId);
		@SuppressWarnings("unchecked")
		List<TRiClaimSectionInfo> sectionInfoList = (List<TRiClaimSectionInfo>) query.getResultList();
		
		TRiClaimSectionInfo sectionInfo = new TRiClaimSectionInfo();
		
		if(CollectionUtils.isNotEmpty(sectionInfoList)){
			sectionInfo  = sectionInfoList.get(0);
		}
		
		return sectionInfo;
	}
	
	/*
	 * return ContractNatrue
	 */
	public String getContractNatrueBySectionId(Long sectionId) {
		final Query query = getEntityManager().createNamedQuery("TRiClaimSectionInfo.findByRefSectionId", TRiClaimSectionInfo.class);
		query.setParameter("sectionId", sectionId);
		@SuppressWarnings("unchecked")
		List<TRiClaimSectionInfo> sectionInfoList = (List<TRiClaimSectionInfo>) query.getResultList();
		
		String contractNatrue = "2";
		if(CollectionUtils.isNotEmpty(sectionInfoList)){
			contractNatrue = sectionInfoList.get(0).getContractNature();
		}
		return contractNatrue;
	}
	
	public List<TRiClaimSectionInfo> getSectionInfoByRefId(Long refid){
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select b.* from t_ri_claim_reserve a ,t_ri_claim_section_info b ");
		sql.append(" where a.ref_id=b.ref_id  ");
		sql.append(" and a.section_id= b.section_id ");
		sql.append(" and a.business_direction=b.business_direction ");
		sql.append(" and a.ref_id = ? ");
		List<TRiClaimSectionInfo> sectionLists = this.getJdbcTemplate().query(sql.toString(),
				new Object[] { refid }, new BeanPropertyRowMapper<TRiClaimSectionInfo>(TRiClaimSectionInfo.class));
		
		return sectionLists;

	}
	
}
