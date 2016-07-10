/**
 * 
 */
package com.ebao.ap99.claim.restful;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.claim.constant.ClaimConstant;
import com.ebao.ap99.claim.convertor.ClaimInfoConvertor;
import com.ebao.ap99.claim.convertor.ClaimMessageConvertor;
import com.ebao.ap99.claim.convertor.ReserveInfoConvertor;
import com.ebao.ap99.claim.dao.RiClaimSectionDao;
import com.ebao.ap99.claim.dao.RiClaimSettlementDao;
import com.ebao.ap99.claim.entity.TRiClaimInfo;
import com.ebao.ap99.claim.entity.TRiClaimMessage;
import com.ebao.ap99.claim.entity.TRiClaimReserve;
import com.ebao.ap99.claim.entity.TRiClaimSectionInfo;
import com.ebao.ap99.claim.entity.TRiClaimSettlement;
import com.ebao.ap99.claim.model.ClaimBoList;
//import com.ebao.ap99.claim.model.CheckClaim;
import com.ebao.ap99.claim.model.ClaimInfo;
import com.ebao.ap99.claim.model.ReserveInfo;
import com.ebao.ap99.claim.service.RiClaimInfoService;
import com.ebao.ap99.claim.service.RiClaimReserveService;
import com.ebao.ap99.claim.service.RiClaimSettlementItemService;
import com.ebao.ap99.claim.service.RiClaimSettlementService;
import com.ebao.ap99.contract.entity.ContractModel;
import com.ebao.ap99.contract.service.ContractService;
import com.ebao.ap99.file.util.JaxbXMLParser;
import com.ebao.ap99.parent.caller.service.IServiceCaller;
import com.ebao.ap99.partner.entity.MessageVO;
import com.ebao.ap99.partner.service.CheckAmlService;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * @author gang.wang
 *
 */
@Path("/claimInfo")
@Module(com.ebao.ap99.parent.constant.Module.CLAIM)
public class ClaimInfoRestfulService {

	private static Logger logger = LoggerFactory.getLogger();
	@Autowired
	IServiceCaller serviceCaller;
	@Autowired
	public RiClaimInfoService claimService;
	@Autowired
	public ContractService contractService;
	@Autowired
	public ClaimInfoConvertor claimInfoConvertor;
	@Autowired
	public RiClaimReserveService reserveService;
	@Autowired
	public RiClaimSettlementService settlementService;
	@Autowired
	public ReserveInfoConvertor reserveInfoConvertor;
	@Autowired
	public RiClaimSettlementItemService settlementItemService;
	// @Autowired
	// private RiClaimReserveDao reserveDao;
	@Autowired
	private RiClaimSettlementDao settlementDao;
	@Autowired
	public RiClaimSectionDao claimSectionDao;

	@Autowired
	public CheckAmlService checkAmlService;
	// @Autowired
	// private RiClaimSettlementItemDao settlementItemDao;

	@Autowired
	public ClaimMessageConvertor messageConvertor;

	@POST
	// @Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TRiClaimInfo createClaim(ClaimInfo claimInfo) throws Exception {
		TRiClaimInfo claimInfoDb = claimInfoConvertor.modelToEntity(claimInfo);
		claimService.createClaimInfo(claimInfoDb);
		return claimInfoDb;
	}

	@PUT
	// @Path("/claimInfo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ClaimInfo saveClaimInfo(ClaimInfo claimInfo) throws Exception {
		logger.info("ClaimInfoRestfulService.saveClaimInfo");

		// Object result = null;
		// try {
		// result = serviceCaller.doAction("claimInfo_save", claimInfo);
		// } catch (Exception e) {
		// logger.error("exception occured in claimInfo service call.", e);
		// throw new Exception("exception occured in claimInfo service call.");
		// }

		// delete TRiClaimReservelist
		if (claimInfo.getDeleteReserveList() != null) {
			reserveService.deleteReserveList(claimInfo.getDeleteReserveList());
		}
		// delete SettlementItemList
		if (claimInfo.getDeleteSettlementItemList() != null) {
			settlementItemService.deleteSettlementItemList(claimInfo.getDeleteSettlementItemList());
		}

		// calculate USD Amount on reserve level and settlement item level
		claimService.calculateReportingCurrencyAmount(claimInfo);// reportingCurrency

		// convert model to entity
		TRiClaimInfo claimEntity = claimInfoConvertor.modelToEntityCascade(claimInfo);

		// save claim info
		// TRiClaimInfo claimEntityReturn =
		claimService.updateClaimInfo(claimEntity);

		// claimService.copyFinaReserve(claimEntityReturn);

		// claimService.copyFinaSettle(claimEntityReturn);

		// convert entity to model
		// ClaimInfo claimInfoReturn =
		// claimInfoConvertor.entityToModelCascade(claimEntityReturn);
		// load claim data, for settlement level data,
		// only load those with new status.
		// claimInfoReturn =
		// claimService.getClaimInfoWithPendingSettlement(claimInfoReturn);
		// calculate summary info on claim, reserve and settlement level
		claimEntity = claimService.getClaim(claimEntity.getClaimId());
		// convert entity to model
		ClaimInfo claimInfoReturn = claimInfoConvertor.entityToModelCascade(claimEntity);
		claimService.calculateSummary(claimInfoReturn);

		return claimInfoReturn;
	}

	@GET
	@Path("/{claimId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ClaimInfo loadClaimInfo(@PathParam("claimId") long claimId) {

		// load claim info
		TRiClaimInfo claimEntity = claimService.getClaim(claimId);
		// convert entity to model
		ClaimInfo claimInfoReturn = claimInfoConvertor.entityToModelCascade(claimEntity);
		// load claim data, for settlement level data,
		// only load those with new status.
		// claimInfoReturn =
		// claimService.getClaimInfoWithPendingSettlement(claimInfoReturn);
		// calculate summary info on claim, reserve and settlement level
		claimService.calculateSummary(claimInfoReturn);

		return claimInfoReturn;

	}

	@POST
	@Path("/submit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void submitClaimInfo(ClaimInfo claimInfo) throws Exception {
		logger.info("ClaimInfoRestfulService.submitClaimInfo");
		long claimId = claimInfo.getClaimId();
		logger.info("claimId=====" + claimId);
		// delete TRiClaimReservelist
		if (claimInfo.getDeleteReserveList() != null) {
			reserveService.deleteReserveList(claimInfo.getDeleteReserveList());
		}
		// delete SettlementItemList
		if (claimInfo.getDeleteSettlementItemList() != null) {
			settlementItemService.deleteSettlementItemList(claimInfo.getDeleteSettlementItemList());
		}

		// calculate USD Amount on reserve level and settlement item level
		claimService.calculateReportingCurrencyAmount(claimInfo);
		// convert model to entity
		TRiClaimInfo claimEntity = claimInfoConvertor.modelToEntityCascade(claimInfo);
		//

		// save claim info
		TRiClaimInfo claimEntityReturn = claimService.updateClaimInfo(claimEntity);

		if (null != claimEntityReturn.getTRiClaimReserves() && claimEntityReturn.getTRiClaimReserves().size() > 0) {
			claimService.copyFinaReserve(claimEntityReturn);
		}

		if (null != claimEntityReturn.getTRiClaimSettlements()
				&& claimEntityReturn.getTRiClaimSettlements().size() > 0) {
			claimService.copyFinaSettle(claimEntityReturn);

		}

		// List<TRiClaimSettlement> settlementList =
		// settlementDao.getNewSettleByRefId(claimId);
		// List<TRiClaimReserve> reserveList =
		// reserveDao.getFinaReserveList(claimId);
		// if (reserveList.size() > 0) {
		// claimService.insertClaimReserve(claimEntity.getClaimId(),
		// reserveList);
		// }
		// if (settlementList.size() > 0) {
		// claimService.insertClaimSettle(claimId, settlementList);
		// }
		//
		// if (null != claimEntity.getTRiClaimReserves() &&
		// claimEntity.getTRiClaimReserves().size() > 0) {
		// claimService.updateClaimReserve(claimEntity);
		// }

		// deal reserve
		reserveService.dealReserveSubmit(claimInfo.getClaimId(), claimInfo.getReserveUpdateRemark(),
				claimInfo.getReserveUpdateRemarkRetro());

		// deal settlement 暂无approve流程，submit即approve通过，取出未approve的所有settlement
		settlementService.dealSettlementSubmit(claimInfo.getClaimId());

	}

	@POST
	@Path("/checkClose")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public int checkCloseClaim(ClaimInfo claimInfo) {
		int check = 0;
		long claimId = claimInfo.getClaimId();

		// CheckClaim checkClaim = new CheckClaim();

		// cheak settlement
		List<TRiClaimSettlement> settlementList = settlementService.getOutstandingSettleByRefId(claimId);
		// exist not approved settlement
		if (settlementList.size() > 0) {
			check = 2;
			// checkClaim.setSettlementCheck(1);
		}

		// check reserve
		List<TRiClaimReserve> reserveList = reserveService.getReserveList(claimId);
		for (TRiClaimReserve re : reserveList) {
			// exist reserveAmount can't change to 0
			if (re.getAmountOc().longValue() > 0) {
				check = 1;
				// checkClaim.setReserveCheck(1);
				break;
			}
			// exist reserve can't be submitted
			if (re.getStatus().equals(ClaimConstant.CLAIM_RESERVE_STATUS__NEW)) {
				check = 1;
				// checkClaim.setReserveCheck(1);
				break;
			}
		}
		return check;
	}

	@POST
	@Path("/close")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void closeClaim(ClaimInfo claimInfo) throws Exception {

		claimService.calculateReportingCurrencyAmount(claimInfo);

		TRiClaimInfo claimEntity = claimInfoConvertor.modelToEntityCascade(claimInfo);
		claimEntity.setStatus(ClaimConstant.CLAIM_STATUS__CLOSE);
		List<TRiClaimReserve> claimReserve = claimEntity.getTRiClaimReserves();
		if (null != claimReserve) {
			for (TRiClaimReserve cr : claimReserve) {
				if (cr.getAmountOc().longValue() != 0) {
					cr.setAmountOc(new BigDecimal(0));
					cr.setAmountUsd(new BigDecimal(0));
				}
			}
		}

		claimEntity.setTRiClaimReserves(claimReserve);

		claimService.updateClaimInfo(claimEntity);

		// deal reserve
		reserveService.dealReserveSubmit(claimInfo.getClaimId(), claimInfo.getReserveUpdateRemark(),
				claimInfo.getReserveUpdateRemarkRetro());

		// deal settlement 暂无approve流程，submit即approve通过，取出未approve的所有settlement
		settlementService.dealSettlementSubmit(claimInfo.getClaimId());
	}

	@POST
	@Path("/open")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void openClaim(ClaimInfo claimInfo) throws Exception {
		TRiClaimInfo claimEntity = claimInfoConvertor.modelToEntityCascade(claimInfo);
		claimEntity.setStatus(ClaimConstant.CLAIM_STATUS__OPEN);
		claimService.updateClaimInfo(claimEntity);
	}
	//
	// @GET
	// @Path("/reserve/{claimId}")
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	// public List<TRiClaimReserve> getReserveList(@PathParam("claimId") long
	// claimId) {
	// // EventInfoConvertor evCon = new EventInfoConvertor();
	// List<TRiClaimReserve> reserveList = reserveDao.getCurrentList(claimId);
	// if (reserveList != null && reserveList.size() > 0) {
	// reserveList.forEach(r -> {
	// r.setRefId(claimId);
	// r.setBusinessDirection("1");
	// @SuppressWarnings("deprecation")
	// TRiClaimReserve reserveRetrun = reserveDao.merge(r);
	// });
	// }
	// return reserveList;
	// }

	@GET
	@Path("/posting/{sectionId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean getPostingFlag(@PathParam("sectionId") long sectionId) {
		// EventInfoConvertor evCon = new EventInfoConvertor();
		// boolean flag = false;
		boolean flag = contractService.needPostingGL(sectionId);
		return flag;
	}

	@GET
	@Path("/message/{claimId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object getMessage(@PathParam("claimId") long claimId) {

		List<TRiClaimMessage> claimMessages = claimService.createClaimMessage(claimId);
		// claimService.createClaimMessage(claimId);
		List<TRiClaimMessage> claimMessagesReturn = messageConvertor.getUnCheckedMessage(claimMessages);

		return claimMessagesReturn;

	}

	@GET
	@Path("/amountMessage/{claimId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object getAmountMessage(@PathParam("claimId") long claimId) {

		// List<TRiClaimMessage> claimMessages = claimInfoConvertor.
		// claimService.createClaimMessage(claimId);
		List<TRiClaimMessage> message = claimService.createClaimMessage(claimId);

		List<TRiClaimMessage> claimMessagesReturn = messageConvertor.getAmontMessage(message);

		return claimMessagesReturn;

	}
	
	
	@GET
	@Path("/contractDate/{contCompId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Date getContractDate(@PathParam("contCompId") long contCompId) {

		// List<TRiClaimMessage> claimMessages = claimInfoConvertor.
		// claimService.createClaimMessage(claimId);
		ContractModel contractVo = null;
		try {
			contractVo = contractService.getContractInfoByCompId(contCompId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return contractVo.getReinsStarting();

	}
	
	
	@GET
	@Path("/contractStatus")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean getContractStatus(List<Long> sectionId) {

		// List<TRiClaimMessage> claimMessages = claimInfoConvertor.
		// claimService.createClaimMessage(claimId);
		int num =0;
		for(Long contCompId : sectionId){
			ContractModel contractVo = null;
			try {
				contractVo = contractService.getContractInfoByCompId(contCompId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!"4".equals(contractVo.getLatestStatus())){
				num = num +1;
			}
		}
		if(num >0){
			return false;
		}else{
			return true;
		}
		

	}


	@POST
	@Path("/AMLCHECK")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object AMLCheck(ClaimInfo claimInfo) {

		List<ReserveInfo> reservelist = claimInfoConvertor.getAMLReserveList(claimInfo);

		// boolean flag = true;
		List<TRiClaimSectionInfo> sectionLists = new ArrayList<TRiClaimSectionInfo>();
		// claimSectionDao.getSectionInfoByRefId(refId);
		for (ReserveInfo re : reservelist) {
			TRiClaimSectionInfo sectionInfo = claimSectionDao.getClaimSectionInfoBySection(re.getSectionId());
			sectionLists.add(sectionInfo);
			// if (StringUtils.isNotBlank(sectionInfo.getCedentName())) {
			// //flag = checkAmlService.amlCheck(sectionInfo.getCedentName(),
			// "1");
			// }
			// if (StringUtils.isNotBlank(sectionInfo.getBrokerName())) {
			// //flag = checkAmlService.amlCheck(sectionInfo.getBrokerName(),
			// "2");
			// }
		}
		List<MessageVO> messageList = new ArrayList<>();
		List<String> cedentList = sectionLists.stream().filter(s -> s.getCedentName() != null)
				.map(s -> s.getCedentName()).distinct().collect(Collectors.toList());
		for (String cedent : cedentList) {
			MessageVO message = checkAmlService.amlCheck(cedent, "1");
			if (!message.isCheckFlag()) {
				messageList.add(message);
			}
		}
		List<String> brokerList = sectionLists.stream().filter(s -> s.getBrokerName() != null)
				.map(s -> s.getBrokerName()).distinct().collect(Collectors.toList());
		for (String broker : brokerList) {
			MessageVO message = checkAmlService.amlCheck(broker, "2");
			if (!message.isCheckFlag()) {
				messageList.add(message);
			}
		}

		return messageList;

	}

	/**
	 * 
	 * @param claimId
	 * @return
	 * @throws Exception
	 */
//	@GET
//	@Path("/loadClaimModel")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_XML)
	
	@GET
	@Path("loadClaimModel/{claimId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String loadClaimModel(@PathParam("claimId") Long claimId) throws Exception {

		// ContractBO bo = contractDS.loadContractBO(contCompId, true);

		TRiClaimInfo claimEntity = claimService.getClaim(claimId);
		// convert entity to model
		ClaimInfo claimInfoReturn = claimInfoConvertor.entityToModelCascade(claimEntity);

		JaxbXMLParser jaxbXMLParser = new JaxbXMLParser();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ClaimBoList claimBoList = new ClaimBoList();
		claimBoList.addNewObject(claimInfoReturn);
		@SuppressWarnings("static-access")
		String xmlString = jaxbXMLParser.marshallXml(out, claimBoList, ClaimBoList.class);
		if (logger.isDebugEnabled()) {
			logger.debug("claimid=", claimId + ",xml:" + xmlString);
		}

		return xmlString;
	}

}
