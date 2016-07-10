/**
 * 
 */
package com.ebao.ap99.claim.service;

import java.util.List;

import com.ebao.ap99.claim.entity.TRiClaimSectionInfo;

/**
 * @author yujie.zhang
 *
 */
public interface RiClaimSectionInfoService {
 void createClaimSectionInfo(TRiClaimSectionInfo tRiclaimSection);
 TRiClaimSectionInfo updateClaimSectionInfo(TRiClaimSectionInfo tRiclaimSection);
 TRiClaimSectionInfo getClaimSection(long claimSectionId);
 void deleteClaimSection(TRiClaimSectionInfo tRiclaimSection);
 List<TRiClaimSectionInfo> getAllClaimSection();
 /**
  * TODO return List<Long> : section Id
  * @param claimId
  * @param businessDirection
  * @return
  */
 List<TRiClaimSectionInfo> getCheckedsectionList(long claimId,String businessDirection);
 void deleteSection(long claimId,String businessDirection,List<Long> oldSectionList);
}
