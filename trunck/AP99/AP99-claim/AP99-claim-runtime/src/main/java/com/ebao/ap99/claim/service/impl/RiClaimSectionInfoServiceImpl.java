/**
 * 
 */
package com.ebao.ap99.claim.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.claim.dao.RiClaimSectionDao;
import com.ebao.ap99.claim.entity.TRiClaimSectionInfo;
import com.ebao.ap99.claim.service.RiClaimSectionInfoService;

/**
 * @author yujie.zhang
 *
 */
public class RiClaimSectionInfoServiceImpl implements RiClaimSectionInfoService {

	/* (non-Javadoc)
	 * @see com.ebao.ap99.claim.service.RiClaimSectionInfoService#createClaimSectionInfo(com.ebao.ap99.claim.entity.TRiClaimSectionInfo)
	 */
	@Autowired
	private RiClaimSectionDao  claimSectionDao;
	@Override
	public void createClaimSectionInfo(TRiClaimSectionInfo tRiclaimSection) {		
		claimSectionDao.insert(tRiclaimSection);
	}

	/* (non-Javadoc)
	 * @see com.ebao.ap99.claim.service.RiClaimSectionInfoService#updateClaimSectionInfo(com.ebao.ap99.claim.entity.TRiClaimSectionInfo)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public TRiClaimSectionInfo updateClaimSectionInfo(TRiClaimSectionInfo tRiclaimSection) {
		return claimSectionDao.merge(tRiclaimSection);
	}

	/* (non-Javadoc)
	 * @see com.ebao.ap99.claim.service.RiClaimSectionInfoService#getClaimSection(long)
	 */
	@Override
	public TRiClaimSectionInfo getClaimSection(long claimSectionId) {
		return claimSectionDao.load(claimSectionId);
	}

	/* (non-Javadoc)
	 * @see com.ebao.ap99.claim.service.RiClaimSectionInfoService#deleteClaimSection(com.ebao.ap99.claim.entity.TRiClaimSectionInfo)
	 */
	@Override
	public void deleteClaimSection(TRiClaimSectionInfo tRiclaimSection) {
		claimSectionDao.delete(tRiclaimSection);
	}

	/* (non-Javadoc)
	 * @see com.ebao.ap99.claim.service.RiClaimSectionInfoService#getAllClaimSection()
	 */
	@Override
	public List<TRiClaimSectionInfo> getAllClaimSection() {
		return claimSectionDao.loadAll();
	}

	@Override
	public List<TRiClaimSectionInfo> getCheckedsectionList(long claimId,String businessDirection) {
		List<TRiClaimSectionInfo> sectionList=claimSectionDao.getCheckedSectionList(claimId,businessDirection);

		return sectionList;
	}

	@Override
	public void deleteSection(long claimId, String businessDirection, List<Long> oldSectionList) {
		//oldSectionList.removeAll(newSectionList);
		
		for(Long d:oldSectionList){
			TRiClaimSectionInfo sectionInfo = claimSectionDao.loadSectionbySectionId(claimId, businessDirection, d);
			deleteClaimSection(sectionInfo);
		}
		
	}

}
