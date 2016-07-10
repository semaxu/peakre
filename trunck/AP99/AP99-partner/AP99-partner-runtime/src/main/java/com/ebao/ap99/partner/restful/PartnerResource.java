package com.ebao.ap99.partner.restful;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.file.util.JaxbXMLParser;
import com.ebao.ap99.parent.Page;
import com.ebao.ap99.partner.convertor.AmlCheckConvertor;
import com.ebao.ap99.partner.convertor.AmlCheckDS;
import com.ebao.ap99.partner.convertor.BrokerAmlCheckConvertor;
import com.ebao.ap99.partner.convertor.BrokerAmlCheckDS;
import com.ebao.ap99.partner.convertor.MgaAmlCheckConvertor;
import com.ebao.ap99.partner.convertor.MgaAmlCheckDS;
import com.ebao.ap99.partner.convertor.PartnerConvertor;
import com.ebao.ap99.partner.convertor.RetroAmlCheckConvertor;
import com.ebao.ap99.partner.convertor.RetroAmlCheckDS;
import com.ebao.ap99.partner.entity.TAmlCheck;
import com.ebao.ap99.partner.entity.TBrokerAmlCheck;
import com.ebao.ap99.partner.entity.TMgaAmlCheck;
import com.ebao.ap99.partner.entity.TPartner;
import com.ebao.ap99.partner.entity.TRetroAmlCheck;
import com.ebao.ap99.partner.model.AmlCheck;
import com.ebao.ap99.partner.model.AmlCheckPDFVO;
import com.ebao.ap99.partner.model.AmlCheckPDFVOList;
import com.ebao.ap99.partner.model.BrokerAmlCheck;
import com.ebao.ap99.partner.model.BrokerAmlCheckPDFVO;
import com.ebao.ap99.partner.model.BrokerAmlCheckPDFVOList;
import com.ebao.ap99.partner.model.ChangeHistoryQuery;
import com.ebao.ap99.partner.model.MgaAmlCheck;
import com.ebao.ap99.partner.model.MgaAmlCheckPDFVO;
import com.ebao.ap99.partner.model.MgaAmlCheckPDFVOList;
import com.ebao.ap99.partner.model.Partner;
import com.ebao.ap99.partner.model.RetroAmlCheck;
import com.ebao.ap99.partner.model.RetroAmlCheckPDFVO;
import com.ebao.ap99.partner.model.RetroAmlCheckPDFVOList;
import com.ebao.ap99.partner.service.AmlService;
import com.ebao.ap99.partner.service.BrokerAmlService;
import com.ebao.ap99.partner.service.MgaAmlService;
import com.ebao.ap99.partner.service.PartnerService;
import com.ebao.ap99.partner.service.RetroAmlService;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.DateFormatUtils;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

@Module(Module.PUBLIC)
@Path("/partner")
public class PartnerResource {
    private FastDateFormat   DATETIME_FORMAT = FastDateFormat.getInstance("yyyy");
    private SimpleDateFormat formatter       = new SimpleDateFormat("yyyy");
    private static Logger    logger          = LoggerFactory.getLogger();

    public PartnerResource() {
    }

    @Autowired
    private PartnerService         partnerService;
    @Autowired
    private AmlService             amlService;
    @Autowired
    private RetroAmlService        retroAmlService;
    @Autowired
    private BrokerAmlService       brokerAmlService;
    @Autowired
    private MgaAmlService          mgaAmlService;
    @Autowired
    public PartnerConvertor        partnerConvertor;
    @Autowired
    public AmlCheckConvertor       amlCheckConvertor;
    @Autowired
    public RetroAmlCheckConvertor  retroAmlCheckConvertor;
    @Autowired
    public BrokerAmlCheckConvertor brokerAmlCheckConvertor;
    @Autowired
    public MgaAmlCheckConvertor    mgaAmlCheckConvertor;
    @Autowired
    public AmlCheckDS amlCheckDS;
    @Autowired
    public BrokerAmlCheckDS brokerAmlCheckDS;
    @Autowired
    public RetroAmlCheckDS retroAmlCheckDS;
    @Autowired
    public MgaAmlCheckDS mgaAmlCheckDS;
    @GET
    @Path("/listAll")
    public List<TPartner> loadAllPartner() {
        return partnerService.loadAllPartner();
    }

    @GET
    @Path("/load")
    public Partner loadPartyByPartyId(@QueryParam("partnerId") Long partnerId) {
        TPartner tPartner = partnerService.loadPartnerById(partnerId);
        Partner partner = partnerConvertor.entityToModelCascade(tPartner);
        String roleSelect = partner.getRoleSelect();
        if (roleSelect != null) {
            String[] roleIds = roleSelect.split(",");
            List<String> roleSelectIds = new ArrayList<String>();
            for (int i = 0; i < roleIds.length; i++) {
                roleSelectIds.add(roleIds[i]);
            }
            partner.setRoleSelectIds(roleSelectIds);
        }
        partner.setBusinessPartnerId(String.format("%06d", Integer.valueOf(partner.getBusinessPartnerId())));
        return partner;
    }

    @POST
    @Path("/create")
    public Partner createPartner(Partner partner) {
        logger.info("partnerRestfulService.savePartner");
        List<String> roleIds = partner.getRoleSelectIds();
        if (roleIds != null) {
            String roleSelect = "";
            for (int i = 0; i < roleIds.size(); i++) {
                if (i == roleIds.size() - 1) {
                    roleSelect = roleSelect + roleIds.get(i);
                } else {
                    roleSelect = roleSelect + roleIds.get(i) + ",";
                }
            }
            partner.setRoleSelect(roleSelect);
        }
        TPartner partnerEntity = partnerConvertor.modelToEntityCascade(partner);

        partnerService.createOrUpdate(partnerEntity);
        Partner partnerReturn = partnerConvertor.entityToModelCascade(partnerEntity);
        return partnerReturn;
    }

    @POST
    @Path("/submit")
    public Partner submitPartner(Partner partner) {
        logger.info("partnerRestfulService.savePartner");
        List<String> roleIds = partner.getRoleSelectIds();
        if (roleIds != null) {
            String roleSelect = "";
            for (int i = 0; i < roleIds.size(); i++) {
                if (i == roleIds.size() - 1) {
                    roleSelect = roleSelect + roleIds.get(i);
                } else {
                    roleSelect = roleSelect + roleIds.get(i) + ",";
                }
            }
            partner.setRoleSelect(roleSelect);
        }
        TPartner partnerEntity = partnerConvertor.modelToEntityCascade(partner);
        partnerService.createOrUpdate(partnerEntity);
        partnerService.createPartnerLog(partnerEntity);
        partnerService.createAmlCheckMail(partner);
        partnerService.createDeclineMail(partner);
        Partner partnerReturn = partnerConvertor.entityToModelCascade(partnerEntity);
        return partnerReturn;
    }

    @POST
    @Path("/query")
    public Object getPageList(Partner partnerQuery) {
        Page<Partner> page = new Page<Partner>();
        logger.info("partnerQuery.queryByConditions");
        page = partnerService.findPageListBySql(partnerQuery);
        List<Partner> list = page.getRows();
        for (int i = 0; i < list.size(); i++) {
            String roleSelect = list.get(i).getRoleSelect();
            if (roleSelect != null) {
                String[] roleIds = roleSelect.split(",");
                List<String> roleSelectIds = new ArrayList<String>();
                for (int j = 0; j < roleIds.length; j++) {
                    roleSelectIds.add(roleIds[j]);

                }
                list.get(i).setRoleSelectIds(roleSelectIds);
            }
            list.get(i)
                    .setBusinessPartnerId(String.format("%06d", Integer.valueOf(list.get(i).getBusinessPartnerId())));
        }

        return page;
    }

    @POST
    @Path("/createCompliance")
    public AmlCheck createCompliance(AmlCheck amlCheck) {
        TAmlCheck amlEntity = amlCheckConvertor.modelToEntity(amlCheck);
        amlService.createAml(amlEntity);
        TPartner tPartner = partnerService.loadPartnerById(Long.valueOf(amlCheck.getPartnerId()));
        amlEntity.setTPartner(tPartner);
        if (amlEntity.getCheckDate() != null) {
            amlEntity.setCheckYear(DateFormatUtils.format(amlEntity.getCheckDate(), DATETIME_FORMAT.getPattern()));
        }
        amlService.createAml(amlEntity);
        AmlCheck amlReturn = amlCheckConvertor.entityToModel(amlEntity);
        amlReturn.setPartnerId(amlCheck.getPartnerId());
        return amlReturn;
    }

    @GET
    @Path("/loadCompliance")
    public AmlCheck loadAmlByAmlId(@QueryParam("amlCheckId") Long amlCheckId) {
        TAmlCheck tAmlCheck = amlService.loadAmlCheckById(amlCheckId);
        AmlCheck amlCheck = amlCheckConvertor.entityToModel(tAmlCheck);
        amlCheck.setPartnerId(String.valueOf(tAmlCheck.getTPartner().getPartnerId()));
        return amlCheck;
    }

    @POST
    @Path("/createRetroCompliance")
    public RetroAmlCheck createRetroCompliance(RetroAmlCheck amlCheck) {
        TRetroAmlCheck amlEntity = retroAmlCheckConvertor.modelToEntity(amlCheck);
        retroAmlService.createAml(amlEntity);
        TPartner tPartner = partnerService.loadPartnerById(Long.valueOf(amlCheck.getPartnerId()));
        amlEntity.setTPartner(tPartner);
        if (amlEntity.getCheckDate() != null) {
            amlEntity.setCheckYear(DateFormatUtils.format(amlEntity.getCheckDate(), DATETIME_FORMAT.getPattern()));
        }
        retroAmlService.createAml(amlEntity);
        RetroAmlCheck amlReturn = retroAmlCheckConvertor.entityToModel(amlEntity);
        amlReturn.setPartnerId(amlCheck.getPartnerId());
        return amlReturn;
    }

    @GET
    @Path("/loadRetroCompliance")
    public RetroAmlCheck loadRetroAmlByAmlId(@QueryParam("amlCheckId") Long amlCheckId) {
        TRetroAmlCheck tAmlCheck = retroAmlService.loadAmlCheckById(amlCheckId);
        RetroAmlCheck amlCheck = retroAmlCheckConvertor.entityToModel(tAmlCheck);
        amlCheck.setPartnerId(String.valueOf(tAmlCheck.getTPartner().getPartnerId()));
        return amlCheck;
    }

    @POST
    @Path("/createBrokerCompliance")
    public BrokerAmlCheck createBrokerCompliance(BrokerAmlCheck amlCheck) {
        TBrokerAmlCheck amlEntity = brokerAmlCheckConvertor.modelToEntity(amlCheck);
        TPartner tPartner = partnerService.loadPartnerById(Long.valueOf(amlCheck.getPartnerId()));
        amlEntity.setTPartner(tPartner);
        if (amlEntity.getCheckDate() != null) {
            amlEntity.setCheckYear(DateFormatUtils.format(amlEntity.getCheckDate(), DATETIME_FORMAT.getPattern()));
        }
        brokerAmlService.createAml(amlEntity);
        BrokerAmlCheck amlReturn = brokerAmlCheckConvertor.entityToModel(amlEntity);
        return amlReturn;
    }

    @GET
    @Path("/loadBrokerCompliance")
    public BrokerAmlCheck loadBrokerAmlByAmlId(@QueryParam("amlCheckId") Long amlCheckId) {
        TBrokerAmlCheck tAmlCheck = brokerAmlService.loadBrokerAmlCheckById(amlCheckId);
        BrokerAmlCheck amlCheck = brokerAmlCheckConvertor.entityToModel(tAmlCheck);
        amlCheck.setPartnerId(String.valueOf(tAmlCheck.getTPartner().getPartnerId()));
        return amlCheck;
    }

    @POST
    @Path("/createMgaCompliance")
    public MgaAmlCheck createMgaCompliance(MgaAmlCheck amlCheck) {
        TMgaAmlCheck amlEntity = mgaAmlCheckConvertor.modelToEntity(amlCheck);
        TPartner tPartner = partnerService.loadPartnerById(Long.valueOf(amlCheck.getPartnerId()));
        amlEntity.setTPartner(tPartner);
        if (amlEntity.getCheckDate() != null) {
            amlEntity.setCheckYear(DateFormatUtils.format(amlEntity.getCheckDate(), DATETIME_FORMAT.getPattern()));
        }
        mgaAmlService.createAml(amlEntity);
        MgaAmlCheck amlReturn = mgaAmlCheckConvertor.entityToModel(amlEntity);
        return amlReturn;
    }

    @GET
    @Path("/loadMgaCompliance")
    public MgaAmlCheck loadMgaAmlByAmlId(@QueryParam("amlCheckId") Long amlCheckId) {
        TMgaAmlCheck tAmlCheck = mgaAmlService.loadMgaAmlCheckById(amlCheckId);
        MgaAmlCheck amlCheck = mgaAmlCheckConvertor.entityToModel(tAmlCheck);
        amlCheck.setPartnerId(String.valueOf(tAmlCheck.getTPartner().getPartnerId()));
        return amlCheck;
    }

    @POST
    @Path("/queryHistory")
    public Object queryHistoryList(ChangeHistoryQuery changeHistory) {
        Page<ChangeHistoryQuery> page = new Page<ChangeHistoryQuery>();
        logger.info("partnerQuery.queryByConditions");
        page = partnerService.queryChangeHistoryList(changeHistory);
        List<ChangeHistoryQuery> list = page.getRows();
        for (int i = 0; i < list.size(); i++) {
            String roleSelect = list.get(i).getRoleSelect();
            if (roleSelect != null) {
                String[] roleIds = roleSelect.split(",");
                List<String> roleSelectIds = new ArrayList<String>();
                for (int j = 0; j < roleIds.length; j++) {
                    roleSelectIds.add(roleIds[j]);

                }
                list.get(i).setRoleSelectIds(roleSelectIds);
            }
        }
        return page;
    }

    @GET
    @Path("/getPartnerCode")
    public Map<String, String> getPartnerCode() {
        Map<String, String> map = new HashMap<String, String>();
        String businessPartnerId = partnerService.getBusinessPartnerId();
        map.put("partnerCode", businessPartnerId);
        return map;
    }

    @GET
    @Path("/checkPartnerCode/{businessPartnerId}")
    public Map<String, Object> checkPartnerCode(@PathParam("businessPartnerId") String businessPartnerId) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean checkResult = partnerService.checkBusinessPartnerId(businessPartnerId);
        map.put("checkResult", checkResult);
        return map;
    }

    @GET
    @Path("/loadAMlPDFVO")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_XML)
    public String loadAmlPDFVO(@QueryParam("AmlCheckId") Long amlCheckId) throws Exception {

        TAmlCheck tAmlCheck = amlService.loadAmlCheckById(amlCheckId);
        AmlCheckPDFVO amlCheckPDFVO = amlCheckDS.entityToModel(tAmlCheck);
        AmlCheckPDFVOList amlCheckPDFVOList = new AmlCheckPDFVOList();
        amlCheckPDFVOList.addNewObject(amlCheckPDFVO);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JaxbXMLParser jaxbXMLParser = new JaxbXMLParser();
        String xmlString = jaxbXMLParser.marshallXml(out, amlCheckPDFVOList, AmlCheckPDFVOList.class);
        return xmlString;
    }
    @GET
    @Path("/loadRetroAMlPDFVO")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_XML)
    public String loadRetroAmlPDFVO(@QueryParam("AmlCheckId") Long amlCheckId) throws Exception {

        TRetroAmlCheck tAmlCheck = retroAmlService.loadAmlCheckById(amlCheckId);
        RetroAmlCheckPDFVO amlCheckPDFVO = retroAmlCheckDS.entityToModel(tAmlCheck);
        RetroAmlCheckPDFVOList amlCheckPDFVOList = new RetroAmlCheckPDFVOList();
        amlCheckPDFVOList.addNewObject(amlCheckPDFVO);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JaxbXMLParser jaxbXMLParser = new JaxbXMLParser();
        String xmlString = jaxbXMLParser.marshallXml(out, amlCheckPDFVOList, AmlCheckPDFVOList.class);
        return xmlString;
    }
    @GET
    @Path("/loadBrokerAMlPDFVO")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_XML)
    public String loadBrokerAmlPDFVO(@QueryParam("AmlCheckId") Long amlCheckId) throws Exception {

        TBrokerAmlCheck tAmlCheck = brokerAmlService.loadBrokerAmlCheckById(amlCheckId);
        BrokerAmlCheckPDFVO amlCheckPDFVO = brokerAmlCheckDS.entityToModel(tAmlCheck);
        BrokerAmlCheckPDFVOList amlCheckPDFVOList = new BrokerAmlCheckPDFVOList();
        amlCheckPDFVOList.addNewObject(amlCheckPDFVO);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JaxbXMLParser jaxbXMLParser = new JaxbXMLParser();
        String xmlString = jaxbXMLParser.marshallXml(out, amlCheckPDFVOList, BrokerAmlCheckPDFVOList.class);
        return xmlString;
    }
    @GET
    @Path("/loadMgaAMlPDFVO")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_XML)
    public String loadMgaAmlPDFVO(@QueryParam("AmlCheckId") Long amlCheckId) throws Exception {

        TMgaAmlCheck tAmlCheck = mgaAmlService.loadMgaAmlCheckById(amlCheckId);
        MgaAmlCheckPDFVO amlCheckPDFVO = mgaAmlCheckDS.entityToModel(tAmlCheck);
        MgaAmlCheckPDFVOList amlCheckPDFVOList = new MgaAmlCheckPDFVOList();
        amlCheckPDFVOList.addNewObject(amlCheckPDFVO);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JaxbXMLParser jaxbXMLParser = new JaxbXMLParser();
        String xmlString = jaxbXMLParser.marshallXml(out, amlCheckPDFVOList, BrokerAmlCheckPDFVOList.class);
        return xmlString;
    }
    @POST
    @Path("/queryByPartnerCode")
    public Map<String, String> getNameByPartnerCode(Partner partner) {
        Map<String, String> map = new HashMap<String, String>();
        String businessPartnerId = partnerService.loadPartnerNameByPartnerCode(partner.getBusinessPartnerId());
        map.put("name", businessPartnerId);
        return map;
    }

    @GET
    @Path("/loadByBusinessPartnerId")
    public Partner loadPartyByBusinessPartyId(@QueryParam("businessPartnerId") String businessPartnerId) {
        TPartner tPartner = partnerService.loadPartnerByBusinessPartnerId(businessPartnerId);
        Partner partner = partnerConvertor.entityToModelCascade(tPartner);
        String roleSelect = partner.getRoleSelect();
        if (roleSelect != null) {
            String[] roleIds = roleSelect.split(",");
            List<String> roleSelectIds = new ArrayList<String>();
            for (int i = 0; i < roleIds.length; i++) {
                roleSelectIds.add(roleIds[i]);
            }
            partner.setRoleSelectIds(roleSelectIds);
        }
        partner.setBusinessPartnerId(businessPartnerId);
        return partner;
    }

    @GET
    @Path("/getPartnerInfo/{partnerId}")
    public Map<String, Object> getPartnerInfo(@PathParam("partnerId") Long partnerId) {
        Map<String, Object> map = new HashMap<String, Object>();
        TPartner partner = partnerService.loadPartnerById(partnerId);
        map.put("tradingName", partner.getTradingName());
        map.put("country", partner.getCountry());
        return map;
    }

}
