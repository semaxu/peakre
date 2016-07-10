package com.ebao.ap99.accounting.restful;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.io.ByteArrayOutputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.ebao.ap99.accounting.UI.model.Soa4UpdateVO;
import com.ebao.ap99.accounting.UI.model.SoaLayerVO;
import com.ebao.ap99.accounting.UI.model.SoaSearchVO;
import com.ebao.ap99.accounting.model.SoaBOList;
import com.ebao.ap99.accounting.model.SoaModel;
import com.ebao.ap99.accounting.model.SoaValidateModel;
import com.ebao.ap99.accounting.service.ChooseSectionBizService;
import com.ebao.ap99.accounting.service.SoaBizService;
import com.ebao.ap99.accounting.service.impl.AccountingServiceImpl;
import com.ebao.ap99.arap.service.EntryCodeService;
import com.ebao.ap99.file.util.JaxbXMLParser;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;

@Module(com.ebao.ap99.parent.constant.Module.ACCOUNTING)
@Path("/statementOfAccounting")
public class SoaInfoRestfulService {
    @Autowired
    private SoaBizService soaBizService;

    @Autowired
    private ChooseSectionBizService chooseSectionBizService;

    @Autowired
    private AccountingServiceImpl accountingServiceImpl;
    
    @Autowired
    EntryCodeService entryCodeService;

    @POST
    @Path("/search")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object searchSoaInfo(SoaSearchVO soaSearchV) throws Exception {
        return soaBizService.getSoaInfo(soaSearchV);
    }

    @POST
    @Path("/createSoaLayer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object createSoaAndCurrencyLayerInfo(SoaLayerVO soaLayerV) throws Exception {
        return soaBizService.createSoaAndCurrencyLayerInfo(soaLayerV);
    }

    @GET
    @Path("/loadSoa/{SoaIdRead}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object loadSoaInfo(@PathParam("SoaIdRead") Long SoaIdRead) throws Exception {
        return soaBizService.loadSoaInfo(SoaIdRead);
    }

    @POST
    @Path("/saveAndUpdateSoa")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object saveAndUpdate(Soa4UpdateVO soaU) throws Exception {
    	return soaBizService.saveAndUpdateSoaInfo(soaU);
    }

    @GET
    @Path("/cancelSoa/{SoaIdRead}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void cancelSoaInfo(@PathParam("SoaIdRead") Long SoaIdRead) throws Exception {
        soaBizService.cancelSoaInfo(SoaIdRead);
    }

    @POST
    @Path(value = "/contractStructure/{TreeArray}/{ContractID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object loadSections(@PathParam(value = "TreeArray") String TreeArray,
                               @PathParam(value = "ContractID") long ContractID) throws Exception {
        return chooseSectionBizService.loadSectionsInfo(TreeArray);
    }

    @GET
    @Path("/contractStructure/{contractCode}/{uwYear}/{inforceFlag}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object loadContractStrue(@PathParam("contractCode") String contractCode, @PathParam("uwYear") Long uwYear,
                                    @PathParam("inforceFlag") boolean inforceFlag) throws Exception {
        return chooseSectionBizService.getContractStructureByCode(contractCode, uwYear, inforceFlag);
    }

    @GET
    @Path("/loadSoaView/{SoaIdRead}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object loadSoaViewInfo(@QueryParam("SoaIdRead") Long SoaIdRead) throws Exception {
        return soaBizService.loadSoaViewInfo(SoaIdRead);
    }

    @GET
    @Path("/loadSoaSummary/{SoaIdRead}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object loadSoaSummaryInfo(@PathParam("SoaIdRead") Long SoaIdRead) throws Exception {
        return soaBizService.loadSoaViewInfo(SoaIdRead);
    }
    

    @POST
    @Path("/submitSoa")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void submintSoa(Soa4UpdateVO soaU) throws Exception {
        soaBizService.submitSoaInfo(soaU);
    }
    
    @POST
    @Path("/PTFSubmitSoa")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void PTFSubmitSoa(Soa4UpdateVO soaU) throws Exception {
        soaBizService.PTFSubmitSoaInfo(soaU);
    }

    @POST
    @Path("/ignoringSubmit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void ignoringSubmintSoa(Soa4UpdateVO soaU) throws Exception {
        soaBizService.ignoringSubmintSoa(soaU);
    }

    @GET
    @Path("/withdrawIgnoringCutOffDateSoa/{SoaIdRead}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void withdrawIgnoringCutOffDateSoa(@PathParam("SoaIdRead") Long SoaIdRead) throws Exception {
        soaBizService.withdrawIgnoringCutOffDateSoaInfo(SoaIdRead);
    }

    @GET
    @Path("/withdrawSoa/{SoaIdRead}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void withdrawSoa(@PathParam("SoaIdRead") Long SoaIdRead) throws Exception {
        soaBizService.withdrawSoaInfo(SoaIdRead);
    }

    @POST
    @Path("/submitAndReverseSoa")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void submitAndReverseSoaInfo(Soa4UpdateVO soaU) throws Exception {
        soaBizService.submitAndReverseInfo(soaU);
    }

    @POST
    @Path("/ignoringSaveAndReverse")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void ignoringSaveAndReverseSoaInfo(Soa4UpdateVO soaU) throws Exception {
        soaBizService.ignoringSubmitAndReverseInfo(soaU);
    }

    @POST
    @Path("/validateSoaReversal")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object validateSoaReversalFlag(Soa4UpdateVO soaU) throws Exception {
        return soaBizService.validateSoaReversal(soaU);
    }
    
    @POST
    @Path("/validCendentYearAndQuarter/{ContractCode}/{UWYear}/{CendantYear}/{CendantQuarter}/{Cedent}/{Broker}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object validCendentYearAndQuarter(@PathParam("ContractCode") String ContractCode, @PathParam("UWYear") Integer UWYear,
            @PathParam("CendantYear") Integer CendantYear,@PathParam("CendantQuarter") Integer CendantQuarter,@PathParam("Cedent") String Cedent,
            @PathParam("Broker") String Broker) throws Exception {
        return soaBizService.validateCendentQuarter(ContractCode,UWYear,CendantYear,CendantQuarter,Cedent,Broker);
    }
    

    @GET
    @Path("/SaveSoaView/{SoaIdRead}/{ReviewedFlag}/{SoaText}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void SaveSoaViewInfo(@PathParam("SoaIdRead") Long SoaIdRead, @PathParam("ReviewedFlag") String ReviewedFlag,
                                @PathParam("SoaText") String SoaText) throws Exception {
        soaBizService.saveSoaViewInfo(SoaIdRead, ReviewedFlag, SoaText);
    }


    @GET
    @Path("/validateSoa/{ContractCode}/{UWYear}/{CendantYear}/{CendantQuarter}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object validateSoa(@PathParam("ContractCode") String ContractCode, @PathParam("UWYear") Integer UWYear,
                              @PathParam("CendantYear") Integer CendantYear,
                              @PathParam("CendantQuarter") Integer CendantQuarter) throws Exception {
        return soaBizService.validateSoa(ContractCode, UWYear, CendantYear, CendantQuarter);
    }
    
    @GET
    @Path("/validatePTFSoa/{ContractCode}/{UWYear}/{CendantYear}/{CendantQuarter}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object validatePTFSoa(@PathParam("ContractCode") String ContractCode, @PathParam("UWYear") Integer UWYear,
                              @PathParam("CendantYear") Integer CendantYear,
                              @PathParam("CendantQuarter") Integer CendantQuarter) throws Exception {
        return soaBizService.validatePTFSoa(ContractCode, UWYear, CendantYear, CendantQuarter);
    }

    @GET
    @Path("/validQuarterClosing")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object validQuarterClosing() throws Exception {
        SoaValidateModel soaValidateModel = new SoaValidateModel();
        if (accountingServiceImpl.inClosingPeriod()) {
        	 soaValidateModel.setIsOverCutOffDate("overDate");
        } else {
        	   soaValidateModel.setIsOverCutOffDate("true");
        }
        return soaValidateModel;
    }
    //add by ammon.zhou

    @POST
	@Path("/viewStatement/{contractCode}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<SoaModel> viewSoaInfo(@PathParam("contractCode") String contractCode) throws Exception{
		SoaSearchVO soaSearchVO = new SoaSearchVO();
		soaSearchVO.setContractCode(contractCode);
		return soaBizService.findSoaList(soaSearchVO);
	}
    
    
    @GET
    @Path("/queryEntryItem/{entryCode}/{statementType}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Object queryEntryItem(@PathParam("entryCode") String entryCode,@PathParam("statementType") String statementType) throws Exception {
    	return soaBizService.ValidEntryItemForStatementType(entryCode,statementType);
	}
    
    @POST
    @Path("/AMLCheck/{Cedent}/{Broker}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object AMLCheck(@PathParam("Cedent") String cedent,
            @PathParam("Broker") String broker) throws Exception {
        return soaBizService.AMLCheck(cedent,broker);
    }
    
    @POST
    @Path("/IsSoaWithdrawOrder/{SoaID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object IsSoaWithdrawOrder(@PathParam("SoaID") String soaID,
            @PathParam("Broker") String broker) throws Exception {
        return soaBizService.IsSoaWithdrawOrder(soaID);
    }
    
    @GET
	@Path("loadSoaModel/{SoaID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String loadClaimModel(@PathParam("SoaID") Long SoaID) throws Exception {
    	SoaModel soaModel = soaBizService.loadSoaInfo(SoaID);
    	JaxbXMLParser jaxbXMLParser = new JaxbXMLParser();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		SoaBOList soaBOListList = new SoaBOList();
		soaBOListList.addNewObject(soaModel);
		@SuppressWarnings("static-access")
		String xmlString = jaxbXMLParser.marshallXml(out, soaBOListList, SoaBOList.class);
		return xmlString;
	}
}
