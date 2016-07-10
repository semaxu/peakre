package com.ebao.ap99.arap.restful;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.arap.service.GLDataService;
import com.ebao.ap99.arap.vo.DoubleEntriesVO;
import com.ebao.ap99.arap.vo.EntryItemInformationVO;
import com.ebao.ap99.arap.vo.GeneralLedgerDTO;
import com.ebao.ap99.arap.vo.GeneralLedgerVO;
import com.ebao.ap99.arap.vo.SubLedgerDTO;
import com.ebao.ap99.parent.Page;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.Assert;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

@Path("/GLQuery")
@Module(com.ebao.ap99.parent.constant.Module.ARAP)
public class GLQueryRestfulService {
	@Autowired
	private GLDataService glDataService;
	
	private Logger logger=LoggerFactory.getLogger();
	
	@POST
	@Path("/queryGeneralLedger")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Page<GeneralLedgerVO> queryGeneralLedger(GeneralLedgerDTO generalLedgerCondition) throws Exception {
		List<GeneralLedgerVO> list = null;
		Long total = 0L;
    	try{
    		list = glDataService.queryGeneralLedger(generalLedgerCondition);
    		total = glDataService.countGeneralLedger(generalLedgerCondition);
    	}catch(Exception e){
    		logger.error("queryGeneralLedger fail",e);
			throw e;
    	}
    	
    	Page<GeneralLedgerVO> page = new Page<GeneralLedgerVO>();
    	page.setRows(list);
    	page.setPageIndex(generalLedgerCondition.getPageIndex());
        
    	Long pageCount = total % page.getCountPerPage() == 0 ? total / page.getCountPerPage()
				: total / page.getCountPerPage() + 1;
		page.setPageCount(pageCount);
		if (total < page.getCountPerPage()) {
			page.setCountPerPage(total.intValue());
		}
		
    	return page;
	}
	
	@GET
    @Path("/queryEntryItemInformation/{generalLedgerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<EntryItemInformationVO> queryEntryItemInformation(@PathParam("generalLedgerId") String generalLedgerId) throws Exception{
    	logger.debug("--generalLedgerId---" + generalLedgerId);
    	Assert.isNotNull(generalLedgerId);

    	List<EntryItemInformationVO> list = null;
    	try{ 
    		SubLedgerDTO condition = new SubLedgerDTO();
    		condition.setGeneralLedgerId(Long.valueOf(generalLedgerId));
    		list = glDataService.querySubLedgerItems(condition);
    	}catch(Exception e){
    		logger.error("queryEntryItemInformation fail",e);
			throw e;
    	}
    	
    	return list;
    }

	@GET
    @Path("/queryDoubleEntries/{mappingEntryId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<DoubleEntriesVO> queryDoubleEntries(@PathParam("mappingEntryId") String mappingEntryId) throws Exception{
    	logger.debug("--mappingEntryId---" + mappingEntryId);
    	Assert.isNotNull(mappingEntryId);

    	List<DoubleEntriesVO> list = null;
    	try{ 
    		list = glDataService.queryDoubleEntries(Long.valueOf(mappingEntryId));
    	}catch(Exception e){
    		logger.error("queryDoubleEntries fail",e);
			throw e;
    	}
    	
    	return list;
    }
	
	@POST
	@Path("/querySubLedger")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Page<EntryItemInformationVO> querySubLedger(SubLedgerDTO subLedgerCondition) throws Exception {
		List<EntryItemInformationVO> list = null;
		Long total = 0L;
    	try{
    		list = glDataService.querySubLedgerItems(subLedgerCondition);
    		total = glDataService.countSubLedgerItems(subLedgerCondition);
    	}catch(Exception e){
    		logger.error("querySubLedger fail",e);
			throw e;
    	}
    	
    	Page<EntryItemInformationVO> page = new Page<EntryItemInformationVO>();
    	page.setRows(list);
    	page.setPageIndex(subLedgerCondition.getPageIndex());
        
    	Long pageCount = total % page.getCountPerPage() == 0 ? total / page.getCountPerPage()
				: total / page.getCountPerPage() + 1;
		page.setPageCount(pageCount);
		if (total < page.getCountPerPage()) {
			page.setCountPerPage(total.intValue());
		}
		
    	return page;
	}
	
	
}
