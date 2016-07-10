package com.ebao.ap99.arap.restful;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.arap.convertor.BalanceSearchConvertor;
import com.ebao.ap99.arap.service.SuspenseService;
import com.ebao.ap99.arap.vo.Balance;
import com.ebao.ap99.arap.vo.BalanceSearchDTO;
import com.ebao.ap99.arap.vo.SuspenseQueryCondition;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

@Path("/balanceSearch")
@Module(Module.CLAIM)
public class BalanceSearchRestfulService {
	private Logger logger = LoggerFactory.getLogger();
	
	@Autowired
	public BalanceSearchConvertor balanceSearchConvertor;
	@Autowired
	public SuspenseService suspenseService;

	@POST
	@Path("/queryBalanceSearch")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Balance> queryBalanceSearch(BalanceSearchDTO balanceSearchDTO) throws Exception {
		logger.debug("ContractId=>" + balanceSearchDTO.getContractId());

		
		SuspenseQueryCondition condition = balanceSearchConvertor.modelToSuspenseQueryConditionEntity(balanceSearchDTO);
		
		List<Balance> list = new ArrayList<Balance>();
		
		try {
			list = suspenseService.getSuspenseByCodition(condition);
		} catch (Exception e) {
			logger.error("queryBalanceSearch exception.",e);
			throw e;
		}
		
		return list;
	}
}
