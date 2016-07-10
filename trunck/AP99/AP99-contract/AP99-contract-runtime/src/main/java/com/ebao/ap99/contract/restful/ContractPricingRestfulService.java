package com.ebao.ap99.contract.restful;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.contract.convertor.PricingEstimateModelConvertor;
import com.ebao.ap99.contract.dao.TRiContPricingItemLogDao;
import com.ebao.ap99.contract.dao.TRiContractInfoDao;
import com.ebao.ap99.contract.dao.TRiContractStructureDao;
import com.ebao.ap99.contract.entity.TRiContPricingItemLog;
import com.ebao.ap99.contract.entity.TRiContractInfo;
import com.ebao.ap99.contract.entity.TRiContractStructure;
import com.ebao.ap99.contract.model.PricingEstimateItemVO;
import com.ebao.ap99.contract.model.PricingEstimateVO;
import com.ebao.ap99.contract.model.BusinessModel.PricingEstimateBO;
import com.ebao.ap99.contract.service.ContractPricingDS;
import com.ebao.ap99.contract.util.ContractCst;
import com.ebao.ap99.parent.BeanUtils;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * 
 * @author elvira.du
 *
 */
@Path("/pricingEstimate")
@Module(com.ebao.ap99.parent.constant.Module.CONTRACT)
public class ContractPricingRestfulService {
	private static Logger logger = LoggerFactory.getLogger();

	@Autowired
	private ContractPricingDS contractPricingDS;
	@Autowired
	private TRiContPricingItemLogDao itemHisDao;
	@Autowired
	private TRiContractStructureDao structureDao;
	@Autowired
	private TRiContractInfoDao contractInfoDao;

	@GET
	@Path("/loadPricingEstimate/{ContCompId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PricingEstimateVO loadPricingEstimate(@PathParam("ContCompId") Long contCompId) throws Exception {
		PricingEstimateBO pricingEstimateBO = contractPricingDS.loadPricingEstimateBO(contCompId);
		PricingEstimateVO estimateVO = new PricingEstimateVO();
		if (pricingEstimateBO == null || null == pricingEstimateBO.getPricingId()) {
			estimateVO = contractPricingDS.loadPricingVOFromSection(contCompId);
		} else {
			PricingEstimateModelConvertor.convertBOToVO(pricingEstimateBO, estimateVO);
			List<TRiContPricingItemLog> logList = itemHisDao.getItemLogByPricingId(estimateVO.getPricingId());
			// initial underwriter
			if (CollectionUtils.isNotEmpty(estimateVO.getPricingItemList())) {
				for (PricingEstimateItemVO item : estimateVO.getPricingItemList()) {
					item.setUnderwriter(AppContext.getCurrentUser().getUserId());
				}
			}
			estimateVO.setHisList(BeanUtils.convertList(logList, PricingEstimateItemVO.class));
		}

		// get contract level's information
		TRiContractStructure structureEntity = structureDao.load(contCompId);
		TRiContractInfo parentEntity = contractInfoDao.load(structureEntity.getParentId());
		estimateVO.setContractNature(parentEntity.getContractNature());

		return estimateVO;
	}

	@POST
	@Path("/savePricingEstimate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PricingEstimateVO savePricingEstimate(PricingEstimateVO vo) throws Exception {
		if (ContractCst.CONTRACT_OPERATE_PRICING_ESTIMATE.equals(vo.getOperateType())) {
			vo = contractPricingDS.savePricingEstimateVOWithCalculate(vo);
		} else {
			vo = contractPricingDS.savePricingEstimateVO(vo);

		}
		logger.debug("The operateType = " + vo.getOperateType() + ",savePricingEstimate the sectionId="
				+ vo.getContCompId() + ",pricingId=" + vo.getPricingId());
		return vo;
	}

	@GET
	@Path("/loadPricingEstimateForLog/{ContCompId}/{OperateId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PricingEstimateVO loadPricingEstimateForLog(@PathParam("ContCompId") Long contCompId,
			@PathParam("OperateId") Long operateId) throws Exception {
		PricingEstimateBO pricingEstimateBO = contractPricingDS.loadPricingEstimateBOForLog(contCompId, operateId);
		PricingEstimateVO estimateVO = new PricingEstimateVO();
		if (null != pricingEstimateBO) {
			BeanUtils.copyProperties(pricingEstimateBO, estimateVO);
			if (CollectionUtils.isNotEmpty(pricingEstimateBO.getTRiContPricingItems())) {
				List<PricingEstimateItemVO> pricingItemList = BeanUtils
						.convertList(pricingEstimateBO.getTRiContPricingItems(), PricingEstimateItemVO.class);
				estimateVO.getPricingItemList().add(pricingItemList.get(0));
				estimateVO.setHisList(
						BeanUtils.convertList(pricingEstimateBO.getTRiContPricingItems(), PricingEstimateItemVO.class));
			}
		}
		return estimateVO;
	}
}
