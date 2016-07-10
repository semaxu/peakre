/**
 * 
 */
package com.ebao.ap99.arap.restful;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.arap.constant.BankAccountStatus;
import com.ebao.ap99.arap.convertor.BankAccountConvertor;
import com.ebao.ap99.arap.entity.BankAccount;
import com.ebao.ap99.arap.service.BankAccountService;
import com.ebao.ap99.arap.vo.BankAccountCondition;
import com.ebao.ap99.arap.vo.BankAccountDTO;
import com.ebao.ap99.arap.vo.BankAccountVO;
import com.ebao.ap99.arap.vo.BankDTO;
import com.ebao.ap99.arap.vo.OperationResult;
import com.ebao.ap99.parent.Page;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.Assert;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * @author meiliang.zou
 *
 */
@Path("/bankAccount")
@Module(com.ebao.ap99.parent.constant.Module.ARAP)
public class BankAccountRestfulService {
    private Logger logger = LoggerFactory.getLogger();

    @Autowired
    public BankAccountService bankAccountService;
    
    @Autowired
    public BankAccountConvertor bankAccountConvertor;

    @POST
    @Path("/queryBankAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Page<BankAccount> queryBankAccount(BankAccountDTO bankAccount) throws Exception {
        logger.debug("BankAccountName=>" + bankAccount.getBankAccountName());
        logger.debug("BankAccountNumber=>" + bankAccount.getBankAccountNumber());
        logger.debug("Currency=>" + bankAccount.getCurrency());
        logger.debug("Branch=>" + bankAccount.getBranch());
        BankAccountCondition condition = bankAccountConvertor.modelToQueryConditionEntity(bankAccount);
        
        List<BankAccount> list = null;
        long total = 0;
        try
        {
        	list = bankAccountService.findByCondition(condition);
        	
        	total = bankAccountService.countByPageCondition(condition);
        }
        catch (Exception e) {
        	logger.error("Search Bank Account Exception.\n", e);
			throw e;
        }
        
        Page<BankAccount> page = new Page<BankAccount>();
        page.setRows(list);
        page.setPageIndex(bankAccount.getPageIndex());
        
        long countPerPage = bankAccount.getCountPerPage();
        long pageCount = total % countPerPage == 0 ? (total / countPerPage) : (total / countPerPage + 1);
        
        logger.info("pages num:" + pageCount);
        page.setPageCount(pageCount);
        page.setCountPerPage(bankAccount.getCountPerPage());
        
        return page;
    }
    
    @POST
    @Path("/saveBankAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public OperationResult saveBankAccount(BankAccountDTO bankAccountDTO) throws Exception {
    	logger.debug("AccountId=>" + bankAccountDTO.getAccountId());
        logger.debug("Branch=>" + bankAccountDTO.getBranch());
        logger.debug("BankAccountNumber=>" + bankAccountDTO.getBankAccountNumber());
    	logger.debug("BankAccountName=>" + bankAccountDTO.getBankAccountName());
        logger.debug("Currency=>" + bankAccountDTO.getCurrency());
        logger.debug("Bank=>" + bankAccountDTO.getBank());
        logger.debug("AccountType=>" + bankAccountDTO.getAccountType());
        logger.debug("Status=>" + bankAccountDTO.getStatus());
    	
    	BankAccount bankAccount = bankAccountConvertor.modelToEntity(bankAccountDTO);
    	
    	OperationResult operationResult = null;
    	
    	try
        {
    		if (StringUtils.isBlank(bankAccountDTO.getStatus())) {
    			bankAccount.setStatus(BankAccountStatus.VALID.getValue());
    		}
    		operationResult = bankAccountService.saveBankAccount(bankAccount);
        }
        catch (Exception e) {
        	logger.error("Save Bank Account Exception.\n", e);
			throw e;
        }
    	
    	return operationResult;
    }
    
    @POST
    @Path("/deleteBankAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public OperationResult delBankAccount(BankAccountDTO bankAccountDTO) throws Exception {
    	Long accountId = bankAccountDTO.getAccountId();
    	logger.debug("AccountId=>" + bankAccountDTO.getAccountId());
    	
    	OperationResult operationResult = null;
    	try{
    		operationResult = bankAccountService.deleteBankAccount(accountId);
    	}catch(Exception e){
    		logger.error("Delete bank account exception.\n", e);
			throw e;
    	}
    	
    	return operationResult;
    }
    
    @POST
    @Path("/queryAllBankAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map<String, Object>> queryAllBankAccount() throws Exception {
    	List<Map<String, Object>> resultList = null;
    	
    	try {
    		resultList = bankAccountService.queryAllBankAccount();
		} catch (Exception e) {
			logger.error("Query All Bank Account Exception.\n", e);
			throw e;
		}
    	
        return resultList;
    }
    
    @GET
    @Path("/queryBankAccountByCurrency/{currency}/{accountType}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<BankAccountVO> queryBankAccountByCurrency(@PathParam("currency") String currencyCode, 
    		@PathParam("accountType") Long accountType) throws Exception{
    	logger.debug("--Currency---" + currencyCode);
    	logger.debug("--accountType---" + accountType);
    	Assert.isNotNull(currencyCode);
    	Assert.isNotNull(accountType);
    	
    	List<BankAccountVO> list = null;
		try {
			list = bankAccountService.queryBankAccountByCurrencyCode(currencyCode, accountType);
		} catch (Exception e) {
			logger.error("Query Bank Account by Currency Code Exception.\n", e);
			throw e;
		}
    	
    	return list;
    }
    
    @POST
    @Path("/queryBank")
    public Object queryBank(BankDTO bankDTO) {
    	Assert.isNotNull(bankDTO);
        return bankAccountService.queryBank(bankDTO);
    }
    
    @POST
    @Path("/queryBankByCode")
    public Map<String, String> queryBankByCode(BankDTO bankDTO) {
    	Assert.isNotNull(bankDTO);
        Map<String, String> map = new HashMap<String, String>();
        String bankCode = "";
        if (StringUtils.isNotBlank(bankDTO.getBusinessPartnerId())){
        	bankCode = bankDTO.getBusinessPartnerId();
        }
        if (StringUtils.isNotBlank(bankDTO.getBankCode())){
        	bankCode = bankDTO.getBankCode();
        }
        String bankName =  bankAccountService.queryBankByCode(bankCode);
        map.put("name",bankName);
        return map;
    }
}
