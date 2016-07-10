package com.ebao.ap99.arap.service.impl;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.ebao.ap99.arap.constant.ArapType;
import com.ebao.ap99.arap.constant.CurrencyConstants;
import com.ebao.ap99.arap.constant.FinanceConsistants;
import com.ebao.ap99.arap.constant.FinanceTransactionType;
import com.ebao.ap99.arap.constant.IndicatorType;
import com.ebao.ap99.arap.constant.PostStatus;
import com.ebao.ap99.arap.constant.SettlementExchangeSourceType;
import com.ebao.ap99.arap.dao.FeeDao;
import com.ebao.ap99.arap.dao.GLAccountMappingDao;
import com.ebao.ap99.arap.dao.GLGeneralLedgerDao;
import com.ebao.ap99.arap.dao.GLSubLedgerDao;
import com.ebao.ap99.arap.dao.SettlementDetailDao;
import com.ebao.ap99.arap.entity.EntryCode;
import com.ebao.ap99.arap.entity.Fee;
import com.ebao.ap99.arap.entity.GLAccountMappingDef;
import com.ebao.ap99.arap.entity.GLGeneralLedger;
import com.ebao.ap99.arap.entity.GLSubLedger;
import com.ebao.ap99.arap.entity.SettlementDetail;
import com.ebao.ap99.arap.service.CurrencyExchangeService;
import com.ebao.ap99.arap.service.EntryCodeService;
import com.ebao.ap99.arap.service.GLDataService;
import com.ebao.ap99.arap.service.SettlementService;
import com.ebao.ap99.arap.vo.DoubleEntriesVO;
import com.ebao.ap99.arap.vo.EntryItemInformationVO;
import com.ebao.ap99.arap.vo.FinancialItemVO;
import com.ebao.ap99.arap.vo.GLInterfaceVO;
import com.ebao.ap99.arap.vo.GeneralLedgerDTO;
import com.ebao.ap99.arap.vo.GeneralLedgerVO;
import com.ebao.ap99.arap.vo.SubLedgerDTO;
import com.ebao.ap99.contract.entity.ContractModel;
import com.ebao.ap99.contract.service.ContractService;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.ap99.partner.service.PartnerAPI;
import com.ebao.unicorn.platform.foundation.utils.Assert;

public class GLDataServiceImpl implements GLDataService {
	@Autowired
	private GLAccountMappingDao glAccountMappingDao;
	
	@Autowired
	private FeeDao feeDao;
	
	@Autowired
	private SettlementDetailDao settlementDetailDao;
	
	@Autowired
	private GLSubLedgerDao glSubLedgerDao;
	
	@Autowired
	private GLGeneralLedgerDao glGeneralLedgerDao;
	
	@Autowired
	private CurrencyExchangeService currencyExchangeService;
	
	@Autowired
	private ContractService contractService;
	
	@Autowired
	private EntryCodeService entryCodeService;
	
	@Autowired
	private PartnerAPI partnerService;
	
	@Autowired
	private SettlementService settlementService;

	
	@Override
	@Cacheable("IGLDataService.getGLAcctountMappingByEntrycode")
	public List<GLAccountMappingDef> getGLAcctountMapping(GLInterfaceVO glInterface) throws Exception {
		return glAccountMappingDao.findByEntryCodeAndCashBalanceType(glInterface);
	}
	
	@Override
	public List<GLInterfaceVO> getNoCashBalanceFee() throws Exception{
		return feeDao.getGlInterfaceFee(ArapType.OTHERS.getType());
	}
	
	@Override
	public List<GLInterfaceVO> getARAPFee() throws Exception{
		return feeDao.getGlInterfaceFee(ArapType.AR.getType(), ArapType.AP.getType());
	}
	
	@Override
	public List<GLInterfaceVO> getInterfaceFee(Long transId) throws Exception{
		return feeDao.getInterfaceFee(transId);
	}
	
	@Override
	public List<GLInterfaceVO> getCashDetailFee() throws Exception{
		List<GLInterfaceVO> result = new ArrayList<GLInterfaceVO>();
		
		result.addAll(getSettlementDetailFee(FinanceTransactionType.COLLECTION.getType()));
		result.addAll(getSettlementDetailFee(FinanceTransactionType.PAYMENT.getType()));
		result.addAll(getSettlementDetailFee(FinanceTransactionType.INTERNAL_OFFSET.getType()));
		
		return result;
	}
	
	private List<GLInterfaceVO> getSettlementDetailFee(Integer settleTransType) throws Exception{
		List<GLInterfaceVO> result = new ArrayList<GLInterfaceVO>();
		
		result.addAll(settlementDetailDao.getGlInterfaceCashFee(settleTransType, SettlementExchangeSourceType.SETTLE_DTL_AMOUNT.getType()));
		result.addAll(settlementDetailDao.getGlInterfaceCashFee(settleTransType, SettlementExchangeSourceType.SETTLE_DTL_GAINLOSS.getType()));
		result.addAll(settlementDetailDao.getGlInterfaceCashFee(settleTransType, SettlementExchangeSourceType.SETTLE_DTL_SETTLE_DIFF.getType()));
		result.addAll(settlementDetailDao.getGlInterfaceCashFee(settleTransType, SettlementExchangeSourceType.SETTLE_DTL_SUSPENSE.getType()));
		return result;
	}
	
	@Override
	public void updatePostStatus(Long feeId, Long settleDetailId, PostStatus postStatus) throws Exception{
		if(settleDetailId != null){
			SettlementDetail settleDetail = settlementDetailDao.load(settleDetailId);
			settleDetail.setPostStatus(postStatus.getType());
			settleDetail.setPostDate(AppContext.getSysDate());
		}else if(feeId != null){
			Fee fee = feeDao.load(feeId);
			fee.setPostStatus(postStatus.getType());
			fee.setPostDate(AppContext.getSysDate());
		}
	}
	
	@Override
	public List<GeneralLedgerVO> queryGeneralLedger(GeneralLedgerDTO condition) throws Exception{
		List<GeneralLedgerVO> resultList = new ArrayList<GeneralLedgerVO>();
		List<GLGeneralLedger> glList = glGeneralLedgerDao.queryGeneralLedger(condition, condition.getPageIndex(), condition.getCountPerPage());
		Map<String, BigDecimal> USDAmountMap = new HashMap<String, BigDecimal>();
		if(CollectionUtils.isNotEmpty(glList)){
			for(GLGeneralLedger gl : glList){
				if(CurrencyConstants.CURRENCY_USD.equals(gl.getCurrencyCode())){
					USDAmountMap.put(gl.getUniqueKey(), gl.getAmount());
				}
			}
			BigDecimal amountInUSD;
			for(GLGeneralLedger gl : glList){
				GeneralLedgerVO vo = new GeneralLedgerVO();
				vo.setGeneralLedgerId(gl.getGeneralLedgerId());
				vo.setAmount(gl.getAmount());
				amountInUSD = USDAmountMap.get(gl.getUniqueKey());
				if(amountInUSD == null){
					if(CurrencyConstants.CURRENCY_USD.equals(gl.getCurrencyCode())){
						amountInUSD = gl.getAmount();
					}else{
						amountInUSD = currencyExchangeService.exchangeAmount(gl.getAmount(), gl.getCurrencyCode(), CurrencyConstants.CURRENCY_USD, gl.getPostDate()==null?AppContext.getSysDate():gl.getPostDate());
					}
				}
				vo.setAmountInUSD(amountInUSD);
				vo.setCreditDebit(gl.getGlAccountType());
				vo.setCurrency(gl.getCurrencyCode());
				vo.setDocumentDate(gl.getDocDate());
				vo.setGlAccount(gl.getGlAccountNo());
				vo.setGlAccountText(glAccountMappingDao.getAccountNameByCode(gl.getGlAccountNo()));
				vo.setPostDate(gl.getPostDate());
				vo.setPostStatus(gl.getPostStatus());
				vo.setSubTransaction(gl.getSubTransaction());
				resultList.add(vo);
			}
		}
		
		return resultList;
	}
	
	@Override
	public Long countGeneralLedger(GeneralLedgerDTO condition) throws Exception{
		return glGeneralLedgerDao.countGeneralLedger(condition);
	}
	
	@Override
	public List<EntryItemInformationVO> querySubLedgerItems(SubLedgerDTO condition) throws Exception{
		Assert.isNotNull(condition);
		List<EntryItemInformationVO> resultList = new ArrayList<EntryItemInformationVO>();
		
		List<GLSubLedger> glList = glSubLedgerDao.querySubLedgerItems(condition, condition.getPageIndex(), condition.getCountPerPage());
		if(CollectionUtils.isNotEmpty(glList)){
			BigDecimal exchangeRate;
			BigDecimal amountInUSD;
			GLGeneralLedger generalLedger;
			Date postDate;
			SettlementDetail settleDetail;
			for(GLSubLedger gl : glList){
				EntryItemInformationVO vo = new EntryItemInformationVO();
				vo.setMappingEntryId(gl.getGlMappingEntryId());
				vo.setContractCode(gl.getContractCode());
				vo.setAmount(gl.getAmount());
				vo.setCurrency(gl.getCurrencyCode());
				generalLedger = gl.getGeneralLedgerId()==null ? null : glGeneralLedgerDao.load(gl.getGeneralLedgerId());
				if(generalLedger == null || generalLedger.getPostDate() == null ){
					postDate = AppContext.getSysDate();
				}else{
					postDate = generalLedger.getPostDate();
				}
				exchangeRate = currencyExchangeService.getExchangeRate(gl.getCurrencyCode(), CurrencyConstants.CURRENCY_USD, postDate);
				vo.setExchangeRate(exchangeRate);
				amountInUSD = currencyExchangeService.exchangeAmount(gl.getAmount(), gl.getCurrencyCode(), CurrencyConstants.CURRENCY_USD, postDate);
				vo.setAmountInUSD(amountInUSD);
				vo.setBusinessDescription(gl.getBizTransDesc());
				vo.setBusinessNumber(gl.getBizTransNo());
				if(gl.getBizTransType() != null){
					vo.setBusinessType(gl.getBizTransType().toString());
				}
				vo.setEntryCode(gl.getEntryCode());
				vo.setGenerationDate(gl.getGenerationDate());
				if(gl.getSettleDetailId() != null){
					settleDetail = settlementDetailDao.load(gl.getSettleDetailId());
					if(settleDetail != null && settleDetail.getSuspenseId() != null){
						vo.setEntryCode(FinanceConsistants.GL_ENTRY_CODE_BALANCE);
						vo.setGenerationDate(settleDetail.getInsertTime());
						if(settleDetail.getPaymentId() != null){
							vo.setAmount(vo.getAmount().negate());
							vo.setAmountInUSD(vo.getAmountInUSD().negate());
						}
					}
				}
				EntryCode entryCode = entryCodeService.getByEntryCode(vo.getEntryCode());
				if(entryCode != null){
					vo.setEntryItem(entryCode.getEntryCodeName());
				}
				if(gl.getBizOperatorId() != null){
					vo.setOperator(gl.getBizOperatorId().toString());
				}
				vo.setPostDate(gl.getPostDate());
				vo.setPostStatus(gl.getPostStatus());
				if(gl.isReversal()){
					vo.setIndicator(IndicatorType.REVERSAL.getValue().toString());
				}else{
					if(gl.isEstimation()){
						vo.setIndicator(IndicatorType.ESTIMATION.getValue().toString());
					}else{
						vo.setIndicator(IndicatorType.ACTUAL.getValue().toString());
					}
				}
				if(gl.getContractId() != null){
					ContractModel contract = contractService.getContractInfoByCompId(gl.getContractId());
					if(contract != null){
						if(contract.getUwYear() != null){
							vo.setuWYear(contract.getUwYear().toString());
							vo.setContractCode(contract.getContractCode());
						}
						if(StringUtils.isNotBlank(contract.getBroker())){
							vo.setBroker(partnerService.loadPartnerNameByPartnerCode(contract.getBroker()));
						}
						if(StringUtils.isNotBlank(contract.getCedent())){
							vo.setCedentRetrocessionaire(partnerService.loadPartnerNameByPartnerCode(contract.getCedent()));
						}
						vo.setuWArea(contract.getCedentCountry());
					}
				}
				if(gl.getSettleType() != null){
					vo.setOperator(settlementService.getSettlementOperator(gl.getSettleType(), gl.getSettleId()).toString());
				}
				resultList.add(vo);
			}
		}
		return resultList;
	}
	
	@Override
	public Long countSubLedgerItems(SubLedgerDTO condition) throws Exception{
		return glSubLedgerDao.countSubLedgerItems(condition);
	}
	
	@Override
	public List<DoubleEntriesVO> queryDoubleEntries(Long mappingEntryId) throws Exception{
		List<DoubleEntriesVO> resultList = new ArrayList<DoubleEntriesVO>();
		List<GLSubLedger> glList = glSubLedgerDao.queryDoubleEntries(mappingEntryId);
		if(CollectionUtils.isNotEmpty(glList)){
			GLGeneralLedger generalLedger;
			Date postDate;
			BigDecimal displaySign;
			for(GLSubLedger gl : glList){
				DoubleEntriesVO vo = new DoubleEntriesVO();
				resultList.add(vo);
				displaySign = (gl.getSettleType() == FinanceTransactionType.PAYMENT.getType()) ? BigDecimal.ONE.negate() : BigDecimal.ONE;
				vo.setAmount(gl.getAmount().multiply(displaySign));
				generalLedger = gl.getGeneralLedgerId()==null ? null : glGeneralLedgerDao.load(gl.getGeneralLedgerId());
				if(generalLedger == null || generalLedger.getPostDate() == null ){
					postDate = AppContext.getSysDate();
				}else{
					postDate = generalLedger.getPostDate();
				}
				BigDecimal amountInUSD = currencyExchangeService.exchangeAmount(gl.getAmount(), gl.getCurrencyCode(), CurrencyConstants.CURRENCY_USD, postDate);
				vo.setAmountInUSD(amountInUSD.multiply(displaySign));
				vo.setCreditDebit(gl.getGlAccountType());
				vo.setGlAccount(gl.getGlAccountNo());
				vo.setGlAccountText(glAccountMappingDao.getAccountNameByCode(gl.getGlAccountNo()));
			}
		}
		return resultList;
	}

    @Override
    public List<FinancialItemVO> getFnView(Long level, Long conCompId) throws Exception {
        List<FinancialItemVO> viewTable = new ArrayList<FinancialItemVO>();
        List<String> fnQuarters = glGeneralLedgerDao.getFnQuarters(level, conCompId);
        if (CollectionUtils.isNotEmpty(fnQuarters)) {
            viewTable = glGeneralLedgerDao.getFnView(level, conCompId, fnQuarters);
        }
        return convertFnView(viewTable, fnQuarters.size(), CurrencyConstants.FN_VIEW_CURRENCY_CODE);
    }

    @Override
    public List<String> getFnQuarters(long level, long conCompId) throws Exception {
        return glGeneralLedgerDao.getFnQuarters(level, conCompId);
    }
    
    private List<FinancialItemVO> convertFnView(List<FinancialItemVO> viewList, Integer quarterSize, String viewCurrencyCode) throws Exception{
    	List<FinancialItemVO> result = new ArrayList<FinancialItemVO>();
    	Map<String, FinancialItemVO> voMap = new HashMap<String, FinancialItemVO>();
    	
    	FinancialItemVO totalVO = new FinancialItemVO();
    	FinancialItemVO convertVO;
    	if(CollectionUtils.isNotEmpty(viewList)){
    		for(FinancialItemVO srcVO : viewList){
    			convertVO = voMap.get(srcVO.getItem());
    			if(convertVO == null){
    				convertVO = new FinancialItemVO();
    				convertVO.setItem(srcVO.getItem());
    				convertVO.setCurrencyCode(viewCurrencyCode);
    				voMap.put(srcVO.getItem(), convertVO);
    				result.add(convertVO);
    			}
    			accmulateQuarterAmount(convertVO, srcVO, totalVO, quarterSize, viewCurrencyCode);
    		}
    		totalVO.setItem("9999");
    		result.add(totalVO);
    	}
    	return result;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void accmulateQuarterAmount(FinancialItemVO targetlVO, FinancialItemVO srcVO, FinancialItemVO totalVO, Integer quarterSize, String targetcurencyCode) throws Exception{
    	if(quarterSize > 0){
    		Class voClass = FinancialItemVO.class;
    		Object result;
    		BigDecimal targetAmount;
    		BigDecimal srcAmount;
    		BigDecimal convertAmount;
    		BigDecimal totalAmount;
    		Method getMethod;
    		Method setMethod;
    		for(int quarter=1; quarter <= quarterSize; quarter++){
    			getMethod = voClass.getMethod("getCedentQ"+quarter);
    			setMethod = voClass.getMethod("setCedentQ"+quarter, BigDecimal.class);
    			result = getMethod.invoke(srcVO);
    			if(result != null){
    				srcAmount = result == null?BigDecimal.ZERO : (BigDecimal)result;
    				convertAmount = currencyExchangeService.exchangeAmount(srcAmount, srcVO.getCurrencyCode(), targetcurencyCode);
    				
    				result = getMethod.invoke(targetlVO);
    				targetAmount = (result == null?BigDecimal.ZERO : (BigDecimal)result).add(convertAmount);
    				
    				result = getMethod.invoke(totalVO);
    				totalAmount = (result == null?BigDecimal.ZERO : (BigDecimal)result).add(convertAmount);
    				
    				setMethod.invoke(targetlVO, targetAmount);
    				setMethod.invoke(totalVO, totalAmount);
    			}
    		}
    	}
    }
}
