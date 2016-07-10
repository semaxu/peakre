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
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.claim.convertor.ClaimSectionInfoConvertor;
import com.ebao.ap99.claim.dao.RiClaimSectionDao;
import com.ebao.ap99.claim.entity.TRiClaimSectionInfo;
import com.ebao.ap99.claim.service.RiClaimSectionInfoService;
import com.ebao.ap99.contract.entity.ContractModel;
import com.ebao.ap99.contract.service.ContractService;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * @author yujie.zhang
 *
 */
@Module(com.ebao.ap99.parent.constant.Module.CLAIM)
@Path("/claimSection")
public class ClaimSectionInfoRestfulService {
	private static Logger logger = LoggerFactory.getLogger();
	@Autowired
	public RiClaimSectionInfoService sectionService;
	@Autowired
	public ClaimSectionInfoConvertor sectionInfoConvertor;
	@Autowired
	public ContractService contractService;
	@Autowired
	public RiClaimSectionDao claimSectionDao;
	@GET
	@Path("/check")
	public Long[] getCheckSection(@QueryParam("RefId") long refId,
			@QueryParam("BusinessDirection") String businessDirection) {

		logger.info("sectionInfo.RefId======" + refId);
		
		//getCheckedSectionIdList
		List<Long> sectionList = claimSectionDao.getCheckedSectionIdList(refId, businessDirection);
		Long[] sections =(Long[])sectionList.toArray(new Long[sectionList.size()]);
		return sections;

	}

	@POST
	@Path(value = "/{TreeArray}/{RefType}/{RefId}/{BusinessDirection}")
	// @Consumes(MediaType.APPLICATION_JSON)
	public Object postSections(@PathParam(value = "TreeArray") String treeArray,
			@PathParam(value = "RefType") String refType, @PathParam(value = "RefId") long refId,
			@PathParam(value = "BusinessDirection") String businessDirection) throws Exception {

		logger.info("sectionInfo.RefId=====" + refId);

		//TODO: 1. cannot clear all selections. 
		//      2.need forbid to untick sections which have reserve/settlement
		
		List<Long> sectionListold = claimSectionDao.getCheckedSectionIdList(refId, businessDirection);
		List<Long> newSectionList = new  ArrayList<Long>();
		if (treeArray != null && !treeArray.equals("null")) {
			List<String> SectionList = (List<String>) Arrays.asList(treeArray.split(","));

			for(String sl:SectionList){
				newSectionList.add(Long.parseLong(sl)); 
			}
		}
			
			//delete checkOut Sections
		    sectionListold.removeAll(newSectionList);
			sectionService.deleteSection(refId, businessDirection, sectionListold);
			//Reload old sections
			sectionListold = claimSectionDao.getCheckedSectionIdList(refId, businessDirection);
			
			//insert new section
			newSectionList.removeAll(sectionListold);
			
			
			for (int i = 0; i < newSectionList.size(); i++) {
				ContractModel contactModel = contractService.getContractInfoByCompId(newSectionList.get(i));
				if (null == contactModel) {
					logger.warn("NO contract section found! section ID=" + newSectionList.get(i));
					continue;
				}

				TRiClaimSectionInfo section = sectionInfoConvertor.modelToEntity(contactModel);
				// TRiClaimInfo claimInfo = new TRiClaimInfo();
				// claimInfo.setClaimId(refId);
				section.setRefId(refId);
				section.setRefType(refType);
				section.setBusinessDirection(businessDirection);
				sectionService.createClaimSectionInfo(section);
			}
//		}else{
//			//delete checkOut Sections
//			sectionService.deleteSection(refId, businessDirection, sectionListold,null);
//		}

		//reload claim sections
		List<TRiClaimSectionInfo> sectionList = sectionService.getCheckedsectionList(refId, businessDirection);
		return sectionInfoConvertor.getSectionList(sectionList);

	}

	@GET
	@Path(value = "{RefId}/{BusinessDirection}")
	public Object getSectionCodeTable( @PathParam(value = "RefId") long refId,
			@PathParam(value = "BusinessDirection") String businessDirection) {
		//initiation  claimInfo/EventInfo  load claimSections
		List<TRiClaimSectionInfo> sectionList = sectionService.getCheckedsectionList(refId, businessDirection);
		//List<TreeModel> treeModel = 
		return sectionInfoConvertor.getSectionList(sectionList);
	}
}
