/**
 * 
 */
package com.ebao.ap99.claim.convertor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.restlet.engine.util.StringUtils;

import com.ebao.ap99.claim.entity.TRiClaimSectionInfo;
import com.ebao.ap99.contract.entity.ContractModel;
import com.ebao.ap99.parent.model.TreeModel;

/**
 * @author yujie.zhang
 *
 */
public class ClaimSectionInfoConvertor {

//	public TRiClaimSectionInfo modelToEntity(ClaimSectionInfo claimSectionInfo) {
//
//		TRiClaimSectionInfo RiclaimSectioninfo = new TRiClaimSectionInfo();
//
//		RiclaimSectioninfo.setBrokerName(claimSectionInfo.getBrokerName());
//		RiclaimSectioninfo.setCedentCountry(claimSectionInfo.getCedentCountry());
//		RiclaimSectioninfo.setCedentName(claimSectionInfo.getCedentName());
//		RiclaimSectioninfo.setContractCode(claimSectionInfo.getContractCode());
//		if (claimSectionInfo.getContractId() != null && claimSectionInfo.getContractId().length() != 0) {
//
//			RiclaimSectioninfo.setContractId(new BigDecimal(claimSectionInfo.getContractId()));
//		}
//		RiclaimSectioninfo.setContractNature(claimSectionInfo.getContractNature());
//		RiclaimSectioninfo.setFrontingFlag(claimSectionInfo.getFrontingFlag());
//		RiclaimSectioninfo.setRetrocessionFlag(claimSectionInfo.getRetrocessionFlag());
//		RiclaimSectioninfo.setSectionCode(claimSectionInfo.getSectionCode());
//		if (claimSectionInfo.getSectionId() != null && claimSectionInfo.getSectionId() != 0) {
//
//			RiclaimSectioninfo.setSectionId(claimSectionInfo.getSectionId());
//		}
//		RiclaimSectioninfo.setSectionName(claimSectionInfo.getSectionName());
//		if (claimSectionInfo.getUwYear() != null && claimSectionInfo.getUwYear().length() != 0) {
//
//			RiclaimSectioninfo.setUwYear(new BigDecimal(claimSectionInfo.getUwYear()));
//		}
//
//		return RiclaimSectioninfo;
//
//	}
//	
	public TRiClaimSectionInfo modelToEntity(ContractModel contractVO) {

		TRiClaimSectionInfo RiclaimSectioninfo = new TRiClaimSectionInfo();

		if (!StringUtils.isNullOrEmpty(contractVO.getBroker())) {

		RiclaimSectioninfo.setBrokerName(contractVO.getBroker().toString());
		}
		RiclaimSectioninfo.setCedentCountry(contractVO.getCedentCountry());
		if (!StringUtils.isNullOrEmpty(contractVO.getCedent())) {

		RiclaimSectioninfo.setCedentName(contractVO.getCedent().toString());
		}
		
		RiclaimSectioninfo.setContractCode(contractVO.getContractCode());
		if (contractVO.getContractId() != null && contractVO.getContractId() != 0) {

			RiclaimSectioninfo.setContractId(new BigDecimal(contractVO.getContractId()));
		}
		RiclaimSectioninfo.setContractNature(contractVO.getContractNature());
		RiclaimSectioninfo.setFrontingFlag(contractVO.getFronting());
		RiclaimSectioninfo.setRetrocessionFlag(contractVO.getRetrocession());
//		RiclaimSectioninfo.setSectionCode(contractVO.getSectionCode);

		if (contractVO.getContCompId()!= null && contractVO.getContCompId() != 0) {
			
			RiclaimSectioninfo.setSectionId(contractVO.getContCompId());
		}
		RiclaimSectioninfo.setSectionName(contractVO.getName());

		if (contractVO.getUwYear() != null && contractVO.getUwYear() != 0) {

			RiclaimSectioninfo.setUwYear(new BigDecimal(contractVO.getUwYear()));
		}
		RiclaimSectioninfo.setFullName(contractVO.getFullName());
		
		return RiclaimSectioninfo;

	}
	public String[] sectionListToArray(List<TRiClaimSectionInfo> list){
		List<String> ls= new ArrayList<String>();
		for(TRiClaimSectionInfo p:list){
			ls.add(p.getSectionId().toString());

		}
		final int size =  ls.size();
		String[] arr = new String[size];
		for (int i=0 ; i<ls.size() ; i++){
			arr[i]=ls.get(i);
		}
		
		return arr;
		
	}
	
	public List<TreeModel> getSectionList(List<TRiClaimSectionInfo> list){
		List<TreeModel> sections =  new ArrayList<TreeModel>();
		for(TRiClaimSectionInfo p:list){
			TreeModel treeModel = new TreeModel();
			treeModel.setId(p.getSectionId().longValue());
			treeModel.setText(p.getFullName());
			sections.add(treeModel);
		}
		return sections;

	}
}
