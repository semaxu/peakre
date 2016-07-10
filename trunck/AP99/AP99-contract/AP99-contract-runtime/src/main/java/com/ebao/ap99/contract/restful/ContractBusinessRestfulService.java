package com.ebao.ap99.contract.restful;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.contract.convertor.BusinessConditionModelConvertor;
import com.ebao.ap99.contract.model.BusinessConditionVO;
import com.ebao.ap99.contract.model.DeductionsCommVO;
import com.ebao.ap99.contract.model.DeductionsPcVO;
import com.ebao.ap99.contract.model.BusinessModel.BusinessConditionBO;
import com.ebao.ap99.contract.service.BusinessConditionDS;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

@Module(com.ebao.ap99.parent.constant.Module.CONTRACT)
@Path("/businessCondition")
// @Module(Module.CLAIM)
public class ContractBusinessRestfulService {
	private static Logger logger = LoggerFactory.getLogger();

	@Autowired
	private BusinessConditionDS businessConditionDS;

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BusinessConditionVO saveBusinessVO(BusinessConditionVO vo) throws Exception {
		BusinessConditionBO bo = new BusinessConditionBO();
		BusinessConditionModelConvertor.convertBusinessVOToBO(bo, vo);
		businessConditionDS.saveBusinessBO(bo);
		BusinessConditionModelConvertor.covertBuinessBOToVO(bo, vo);
		return vo;
	}

	@POST
	@Path("/savePcSliding")
	public DeductionsPcVO savePcSlidingDetailVO(DeductionsPcVO vo) throws Exception {
		businessConditionDS.savePcSlidingDetail(vo);
		return vo;
	}

	@POST
	@Path("/saveCommSliding")
	public DeductionsCommVO saveCommSlidingDetailVO(DeductionsCommVO vo) throws Exception {
		businessConditionDS.saveCommSlidingDetail(vo);
		return vo;
	}

	@GET
	@Path("/load/{contCompId}/{contractNature}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BusinessConditionVO loadBusinessVO(@PathParam("contCompId") Long contCompId,
			@PathParam("contractNature") String contractNature) throws Exception {
		BusinessConditionVO vo = new BusinessConditionVO();
		BusinessConditionBO businessBO = businessConditionDS.loadBusinessConditionBO(contCompId);
		BusinessConditionModelConvertor.covertBuinessBOToVO(businessBO, vo);
		// if can't find business condition, default load parent level business
		// condition
		if (vo.isBCEmpty()) {
			vo = businessConditionDS.loadParentBusinessCondition(contCompId);
			if (vo != null) {
				vo.resetIds();
				vo.setContCompId(contCompId);
			}
		}
		return vo;
	}

	@GET
	@Path("/loadPcSliding/{slidingId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DeductionsPcVO loadDeductionPcVO(@PathParam("slidingId") Long slidingId) throws Exception {
		DeductionsPcVO vo = businessConditionDS.loadDeductionsPcInfo(slidingId);
		return vo;
	}

	@GET
	@Path("/loadCommSliding/{slidingId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DeductionsCommVO loadDeductionCommVO(@PathParam("slidingId") Long slidingId) throws Exception {
		DeductionsCommVO vo = businessConditionDS.loadDeductionsCommInfo(slidingId);
		return vo;
	}

	@GET
	@Path("/loadPcSlidingForLog/{deductionsId}/{operateId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DeductionsPcVO loadDeductionPcVOForLog(@PathParam("deductionsId") Long deductionsId,
			@PathParam("operateId") Long operateId) throws Exception {
		DeductionsPcVO vo = businessConditionDS.loadDeductionsPcInfoForLog(deductionsId, operateId);
		return vo;
	}

	@GET
	@Path("/loadCommSlidingForLog/{deductionsId}/{operateId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DeductionsCommVO loadDeductionCommVOForLog(@PathParam("deductionsId") Long deductionsId,
			@PathParam("operateId") Long operateId) throws Exception {
		DeductionsCommVO vo = businessConditionDS.loadDeductionsCommInfoForLog(deductionsId, operateId);
		return vo;
	}

}
