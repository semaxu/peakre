/**
 * 
 */
package com.ebao.ap99.claim.convertor;

import java.util.ArrayList;
//import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.camel.Header;
//import org.restlet.engine.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.claim.entity.TRiClaimInfo;
import com.ebao.ap99.claim.model.ClaimInfo;
import com.ebao.ap99.claim.model.ReserveInfo;
import com.ebao.ap99.claim.model.SettlementInfo;
import com.ebao.ap99.claim.service.RiClaimSectionInfoService;
import com.ebao.ap99.parent.constant.ReInsuranceCst;
import com.ebao.unicorn.platform.foundation.utils.BeanUtils;

/**
 * @author yujie.zhang
 *
 */
public class ClaimInfoConvertor {

	@Autowired
	private ReserveInfoConvertor reserveInfoConvertor;
	@Autowired
	private SettlementInfoConvertor settleInfoConvertor;
	@Autowired
	public ClaimSectionInfoConvertor sectionInfoConvertor;
	@Autowired
	public RiClaimSectionInfoService sectionService;

	// private FastDateFormat DATETIME_FORMAT =
	// FastDateFormat.getInstance("yyy/MM/dd hh:mm:ss");
	 //private SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd HH:mm");

	public TRiClaimInfo modelToEntity(ClaimInfo claimInfo) throws Exception {

		TRiClaimInfo claimEntity = new TRiClaimInfo();

		
		BeanUtils.copyProperties(claimEntity, claimInfo);
//		if (!StringUtils.isNullOrEmpty(claimInfo.getDateOfLossFrom())) {
//			//String a = claimInfo.getDateOfLossFrom();
//			claimEntity.setDateOfLossFrom((Date)formatter.parse(claimInfo.getDateOfLossFrom().replace("T", " ")));
//		}
//		if (!StringUtils.isNullOrEmpty(claimInfo.getDateOfLossTo())) {
//			//String a = claimInfo.getDateOfLossTo();
//			claimEntity.setDateOfLossTo((Date)formatter.parse(claimInfo.getDateOfLossTo().replace("T", " ")));
//		}
//
//		if (!StringUtils.isNullOrEmpty(claimInfo.getDateOfCreation())) {
//			//String a = claimInfo.getDateOfLossFrom();
//			claimEntity.setDateOfCreation((Date)formatter.parse(claimInfo.getDateOfCreation().replace("T", " ")));
//		}
//		if (!StringUtils.isNullOrEmpty(claimInfo.getDateOfReport())) {
//			//String a = claimInfo.getDateOfLossTo();
//			claimEntity.setDateOfReport((Date)formatter.parse(claimInfo.getDateOfReport().replace("T", " ")));
//		}

		return claimEntity;
	}

	public ClaimInfo entityToModel(TRiClaimInfo claimEntity) {
		ClaimInfo claimInfo = new ClaimInfo();

		BeanUtils.copyProperties(claimInfo, claimEntity);
		
//		String DateOfLossFrom = formatter.format(claimEntity.getDateOfLossFrom());
//		claimInfo.setDateOfLossFrom(DateOfLossFrom.replace(" ", "T"));
//		
//		String DateOfLossTo = formatter.format(claimEntity.getDateOfLossTo());
//		claimInfo.setDateOfLossTo(DateOfLossTo.replace(" ", "T"));
//		
//		//String dateOfCreation = formatter.format(claimEntity.getDateOfCreation()).replace(" ", "T");
//		claimInfo.setDateOfCreation(formatter.format(claimEntity.getDateOfCreation()).replace(" ", "T"));
//		claimInfo.setDateOfReport(formatter.format(claimEntity.getDateOfReport()).replace(" ", "T"));
//		
		return claimInfo;
	}

	public TRiClaimInfo modelToEntityCascade(@Header("ClaimInfo") ClaimInfo claimInfo) throws Exception {
		TRiClaimInfo claimEntity = modelToEntity(claimInfo);
		if (claimInfo.getReserveList() != null && claimInfo.getReserveList().size() != 0)
			reserveInfoConvertor.convertToEntityList(claimInfo.getReserveList(), claimEntity,
					ReInsuranceCst.CONTRACT_CATEGORY_ASSUME);
		if (claimInfo.getSettlementList() != null && claimInfo.getSettlementList().size() != 0)
			settleInfoConvertor.convertToEntityListCascade(claimInfo.getSettlementList(), claimEntity,
					ReInsuranceCst.CONTRACT_CATEGORY_ASSUME);
		if (claimInfo.getReserveListRetro() != null && claimInfo.getReserveListRetro().size() != 0)
			reserveInfoConvertor.convertToEntityList(claimInfo.getReserveListRetro(), claimEntity,
					ReInsuranceCst.CONTRACT_CATEGORY_RETRO);
		if (claimInfo.getSettlementListRetro() != null && claimInfo.getSettlementListRetro().size() != 0)
			settleInfoConvertor.convertToEntityListCascade(claimInfo.getSettlementListRetro(), claimEntity,
					ReInsuranceCst.CONTRACT_CATEGORY_RETRO);
		return claimEntity;
	}

	public ClaimInfo entityToModelCascade(TRiClaimInfo claimEntity) {
		ClaimInfo claimInfo = entityToModel(claimEntity);
		if (claimEntity.getTRiClaimReserves() != null && claimEntity.getTRiClaimReserves().size() != 0) {
			Map<String, List<ReserveInfo>> result = reserveInfoConvertor
					.entityListToModelList(claimEntity.getTRiClaimReserves());
			claimInfo.setReserveList(result.get("financial"));
			claimInfo.setReserveListRetro(result.get("retro"));
		}
		if (claimEntity.getTRiClaimSettlements() != null && claimEntity.getTRiClaimSettlements().size() != 0) {
			Map<String, List<SettlementInfo>> result = settleInfoConvertor
					.entityListToModelListCascade(claimEntity.getTRiClaimSettlements());
			claimInfo.setSettlementList(result.get("financial"));
			claimInfo.setSettlementListRetro(result.get("retro"));
		}
		return claimInfo;
	}
	
	public List<ReserveInfo> getAMLReserveList(ClaimInfo claimInfo){
		List<ReserveInfo> reserveList = new ArrayList<>();
		if (claimInfo.getReserveList() != null && claimInfo.getReserveList().size() != 0)
			reserveList.addAll(claimInfo.getReserveList());
	
		if (claimInfo.getReserveListRetro() != null && claimInfo.getReserveListRetro().size() != 0)
			reserveList.addAll(claimInfo.getReserveListRetro());

		return reserveList;
	}
}
