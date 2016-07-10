/**
 * 
 */
package com.ebao.ap99.claim.restful;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.claim.convertor.ClaimMessageConvertor;
import com.ebao.ap99.claim.convertor.EventInfoConvertor;
import com.ebao.ap99.claim.convertor.ReserveInfoConvertor;
import com.ebao.ap99.claim.dao.RiClaimEventDao;
import com.ebao.ap99.claim.dao.RiClaimInfoDao;
import com.ebao.ap99.claim.dao.RiClaimSectionDao;
import com.ebao.ap99.claim.entity.TRiClaimEvent;
import com.ebao.ap99.claim.entity.TRiClaimInfo;
import com.ebao.ap99.claim.entity.TRiClaimSectionInfo;
import com.ebao.ap99.claim.entity.TRiClaimMessage;
import com.ebao.ap99.claim.model.ClaimBoList;
import com.ebao.ap99.claim.model.ClaimInfo;
import com.ebao.ap99.claim.model.EventBoList;
import com.ebao.ap99.claim.model.EventInfo;
import com.ebao.ap99.claim.model.ReserveInfo;
import com.ebao.ap99.claim.service.RiClaimInfoService;
import com.ebao.ap99.claim.service.RiClaimReserveService;
import com.ebao.ap99.claim.service.RiClaimSettlementItemService;
import com.ebao.ap99.claim.service.RiClaimSettlementService;
import com.ebao.ap99.claim.service.RiEventService;
import com.ebao.ap99.file.util.JaxbXMLParser;
import com.ebao.ap99.parent.model.TreeModel;
import com.ebao.ap99.partner.entity.MessageVO;
import com.ebao.ap99.partner.service.CheckAmlService;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * @author yujie.zhang
 *
 */
@Module(Module.CLAIM)
@Path("/event")
public class EventInfoRestfulService {
	private static Logger logger = LoggerFactory.getLogger();
	private SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd HH:mm");

	@Autowired
	public RiEventService eventService;
	@Autowired
	public EventInfoConvertor eventInfoConvertor;
	@Autowired
	public RiClaimReserveService reserveService;
	@Autowired
	public RiClaimSettlementService settlementService;
	@Autowired
	public ReserveInfoConvertor reserveInfoConvertor;
	@Autowired
	public RiClaimSettlementItemService settlementItemService;
	@Autowired
	private RiClaimEventDao eventInfoDao;
	@Autowired
	private RiClaimInfoDao claimInfoDao;
	@Autowired
	public RiClaimSectionDao claimSectionDao;
	@Autowired
	public RiClaimInfoService claimService;
	@Autowired
	public ClaimMessageConvertor messageConvertor;
	@Autowired
	public CheckAmlService checkAmlService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TRiClaimEvent createEvent(EventInfo eventInfo) throws Exception {
		logger.info("EventInfoRestfulService.createEvent");

		TRiClaimEvent tClaimEvent = eventInfoConvertor.modelToEntity(eventInfo);
		eventService.createEvent(tClaimEvent);
		return tClaimEvent;
	}

	@GET
	@Path("/eventInfo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EventInfo getEventInfo(@QueryParam("EventId") long EventID) {
		// EventInfoConvertor evCon = new EventInfoConvertor();

		TRiClaimEvent tClaimEvent = eventService.getEvent(EventID);
		EventInfo eventInfoReturn = eventInfoConvertor.entityToModelCascade(tClaimEvent);

		// eventInfoReturn.setClaimRecordsList(eventInfoDao.getClaimRecords(eventInfoReturn));
		eventInfoConvertor.getClaimRecords(eventInfoReturn);

		eventService.calculateSummary(eventInfoReturn);
		eventService.calculateEventSummary(eventInfoReturn);

		return eventInfoReturn;

	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object saveEventInfo(EventInfo eventInfo) throws Exception {

		// delete TRiClaimReservelist
		if (eventInfo.getDeleteReserveList() != null) {
			reserveService.deleteReserveList(eventInfo.getDeleteReserveList());
		}
		// delete SettlementItemList
		if (eventInfo.getDeleteSettlementItemList() != null) {
			settlementItemService.deleteSettlementItemList(eventInfo.getDeleteSettlementItemList());
		}

		// calculate USD Amount on reserve level and settlement item level
		eventService.calculateReportingCurrencyAmount(eventInfo);// reportingCurrency

		// convert model to entity
		TRiClaimEvent eventEntity = eventInfoConvertor.modelToEntityCascade(eventInfo);

		// save claim info
		TRiClaimEvent eventEntityReturn = eventService.updateEvent(eventEntity);
		// convert entity to model
		EventInfo eventInfoReturn = eventInfoConvertor.entityToModelCascade(eventEntityReturn);
		// load claim data, for settlement level data,
		// only load those with new status.
		// claimInfoReturn =
		// claimService.getClaimInfoWithPendingSettlement(claimInfoReturn);
		// calculate summary info on reserve and settlement level
		eventService.calculateSummary(eventInfoReturn);
		eventService.calculateEventSummary(eventInfoReturn);

		return eventInfoReturn;
	}

	@POST
	@Path("/submit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void submitEventInfo(EventInfo eventInfo) throws Exception {

		// delete TRiClaimReservelist
		if (eventInfo.getDeleteReserveList() != null) {
			reserveService.deleteReserveList(eventInfo.getDeleteReserveList());
		}
		// delete SettlementItemList
		if (eventInfo.getDeleteSettlementItemList() != null) {
			settlementItemService.deleteSettlementItemList(eventInfo.getDeleteSettlementItemList());
		}

		// calculate USD Amount on reserve level and settlement item level
		eventService.calculateReportingCurrencyAmount(eventInfo);// reportingCurrency

		// convert model to entity
		TRiClaimEvent eventEntity = eventInfoConvertor.modelToEntityCascade(eventInfo);

		// save claim info
		eventService.updateEvent(eventEntity);

		// deal reserve
		reserveService.dealReserveSubmit(eventInfo.getEventId(), null, eventInfo.getReserveUpdateRemarkRetro());

		// deal settlement 暂无approve流程，submit即approve通过，取出未approve的所有settlement
		settlementService.dealSettlementSubmit(eventInfo.getEventId());

		
	}

	@POST
	@Path("/queryClaimSumary")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object queryClaimSumaryList(EventInfo eventInfo) {

		return eventInfoConvertor.getClaimRecords(eventInfo);
	}

	@GET
	@Path("/eventDate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EventInfo getEventDate(@QueryParam("EventId") long EventID) {
		// EventInfoConvertor evCon = new EventInfoConvertor();

		TRiClaimEvent tClaimEvent = eventInfoDao.load(EventID);
		EventInfo eventInfoReturn = new EventInfo();
		eventInfoReturn.setDateOfLossFrom(tClaimEvent.getDateOfLossFrom());// 
		eventInfoReturn.setDateOfLossTo(tClaimEvent.getDateOfLossTo());// 
		// eventInfoConvertor.entityToModelCascade(tClaimEvent);

		return eventInfoReturn;

	}

	@GET
	@Path("/eventCode")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<TreeModel> getEventCode() {
		// EventInfoConvertor evCon = new EventInfoConvertor();
		List<TreeModel> eventCodes = eventInfoDao.getEventCodeList();
		return eventCodes;
	}

	@GET
	@Path("/checkCode")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getEventcode() {
		// EventInfoConvertor evCon = new EventInfoConvertor();
		List<String> eventCodes = eventInfoDao.getEventCode();
		return eventCodes;
	}
	
	

	@POST
	@Path("/remove")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean getClaimNo(List<Long> relatedClaim) {
		// EventInfoConvertor evCon = new EventInfoConvertor();
		for(Long related : relatedClaim){
			claimService.removeRelatedClaim(related);
		}
		return true;
	}

	@GET
	@Path("/relateClaim")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<TreeModel> getRelateClaimList(@QueryParam("EventId") long eventId) {
		// EventInfoConvertor evCon = new EventInfoConvertor();

		List<TreeModel> claimLists = claimInfoDao.getClaimNoList(eventId);

		return claimLists;

	}
	
	@GET
	@Path("/message/{eventId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object getMessage(@PathParam("eventId") long eventId) {
		
		List<TRiClaimMessage> claimMessages = eventService.createClaimMessage(eventId);
		List<TRiClaimMessage> claimMessagesReturn = messageConvertor.getUnCheckedMessage(claimMessages);

		return claimMessagesReturn;
		
	}
	@GET
	@Path("/amountMessage/{eventId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object getAmountMessage(@PathParam("eventId") long eventId) {
		

		List<TRiClaimMessage> claimMessages = eventService.createClaimMessage(eventId);
		List<TRiClaimMessage> claimMessagesReturn = messageConvertor.getAmontMessage(claimMessages);
		
		return claimMessagesReturn;
		
	}
	
	@POST
	@Path("/AMLCHECK")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object AMLCheck(EventInfo eventInfo) {

		
		List<ReserveInfo> reservelist = eventInfoConvertor.getAMLReserveList(eventInfo);
		
//		boolean flag = true;
//		//List<TRiClaimSectionInfo> sectionLists = claimSectionDao.getSectionInfoByRefId(refId);
//		for (ReserveInfo re : reservelist) {
//			TRiClaimSectionInfo sectionInfo = claimSectionDao.getClaimSectionInfoBySection(re.getSectionId());
//			
//			if (StringUtils.isNotBlank(sectionInfo.getCedentName())) {
//				flag = checkAmlService.amlCheck(sectionInfo.getCedentName(), "1");
//			}
//			if (StringUtils.isNotBlank(sectionInfo.getBrokerName())) {
//				flag = checkAmlService.amlCheck(sectionInfo.getBrokerName(), "2");
//			}
//		}
//		return flag;
		
	    List<TRiClaimSectionInfo> sectionLists = new ArrayList<TRiClaimSectionInfo>();

		for (ReserveInfo re : reservelist) {
			TRiClaimSectionInfo sectionInfo = claimSectionDao.getClaimSectionInfoBySection(re.getSectionId());
			sectionLists.add(sectionInfo);
		}
		List<MessageVO> messageList = new ArrayList<>();
		List<String> cedentList = sectionLists.stream().filter(s -> s.getCedentName() != null)
				.map(s -> s.getCedentName()).distinct().collect(Collectors.toList());
		for(String cedent:cedentList){
			MessageVO message = checkAmlService.amlCheck(cedent, "1");
			if(!message.isCheckFlag()){
				messageList.add(message);
			}
		}
		List<String> brokerList = sectionLists.stream().filter(s -> s.getBrokerName() != null)
				.map(s -> s.getBrokerName()).distinct().collect(Collectors.toList());
		for(String broker:brokerList){
			MessageVO message = checkAmlService.amlCheck(broker, "2");
			if(!message.isCheckFlag()){
				messageList.add(message);
			}
		}
		
		return messageList;
	}
	
	
	
	@GET
	@Path("/loadEventModel/{eventId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String loadClaimModel(@PathParam("eventId") Long eventId) throws Exception {

		//ContractBO bo = contractDS.loadContractBO(contCompId, true);
		
		TRiClaimEvent tClaimEvent = eventService.getEvent(eventId);
		EventInfo eventInfoReturn = eventInfoConvertor.entityToModelCascade(tClaimEvent);
		
		JaxbXMLParser jaxbXMLParser = new JaxbXMLParser();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		EventBoList eventBoList = new EventBoList();
		eventBoList.addNewObject(eventInfoReturn);
		@SuppressWarnings("static-access")
		String xmlString = jaxbXMLParser.marshallXml(out, eventBoList, EventBoList.class);
		if (logger.isDebugEnabled()) {
			logger.debug("eventId=",
					eventId + ",xml:" + xmlString);
		}

		return xmlString;
	}

}
