package com.ebao.ap99.arap.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.accounting.AccountingService;
import com.ebao.ap99.accounting.YearQuarter;
import com.ebao.ap99.arap.constant.BizTransactionType;
import com.ebao.ap99.arap.constant.CurrencyConstants;
import com.ebao.ap99.arap.constant.GLBatchType;
import com.ebao.ap99.arap.constant.PostStatus;
import com.ebao.ap99.arap.constant.YesNoType;
import com.ebao.ap99.arap.dao.BCPTransDao;
import com.ebao.ap99.arap.dao.GLBatchDao;
import com.ebao.ap99.arap.dao.GLExDetailDao;
import com.ebao.ap99.arap.dao.GLGeneralLedgerDao;
import com.ebao.ap99.arap.dao.GLLogDao;
import com.ebao.ap99.arap.dao.GLMappingEntryDao;
import com.ebao.ap99.arap.dao.GLSubLedgerDao;
import com.ebao.ap99.arap.dao.SetllementExchangeDetailDao;
import com.ebao.ap99.arap.entity.GLAccountMappingDef;
import com.ebao.ap99.arap.entity.GLBatch;
import com.ebao.ap99.arap.entity.GLExDetail;
import com.ebao.ap99.arap.entity.GLGeneralLedger;
import com.ebao.ap99.arap.entity.GLMappingEntry;
import com.ebao.ap99.arap.entity.GLSubLedger;
import com.ebao.ap99.arap.entity.SetllementExchangeDetail;
import com.ebao.ap99.arap.service.CurrencyExchangeService;
import com.ebao.ap99.arap.service.GLDataService;
import com.ebao.ap99.arap.service.GLFileService;
import com.ebao.ap99.arap.service.GLPostingCallBackService;
import com.ebao.ap99.arap.service.GLService;
import com.ebao.ap99.arap.vo.GLInterfaceVO;
import com.ebao.ap99.parent.BeanUtils;
import com.ebao.ap99.parent.context.AppContext;

public class GLServiceImpl implements GLService {
	private Logger logger=LoggerFactory.getLogger(GLService.class);
	
	@Autowired
	private GLBatchDao glBatchDao;
	
	@Autowired
	private GLSubLedgerDao glSubLedgerDao;
	
	@Autowired
	private GLGeneralLedgerDao glGeneralLedgerDao;
	
	@Autowired
	private BCPTransDao bcpTransDao;
	
	@Autowired
	private GLMappingEntryDao glMappingEntryDao;
	
	@Autowired
	private GLLogDao glLogDao;
	
	@Autowired
	private GLExDetailDao glExDetailDao;
	
	@Autowired
	private SetllementExchangeDetailDao setllementExchangeDetailDao;
	
	@Autowired
	private GLDataService glDataService;
	
	@Autowired
	private GLFileService glFileService;
	
	@Autowired
	private AccountingService accoutingService;
	
	@Autowired
	private GLPostingCallBackService SoaBizServiceImpl;
	
	@Autowired
	private CurrencyExchangeService currencyExchangeService;
	
	@Override
	public void subLedgerGeneration() throws Exception {
		logger.info("==========subLedgerGeneration============");
		GLBatch batch = createGlBatch(GLBatchType.SUB_LEDGER_GENERATION.getValue());
		
		List<GLInterfaceVO> glInterfaceList = new ArrayList<GLInterfaceVO>();
		glInterfaceList.addAll(glDataService.getNoCashBalanceFee());
		glInterfaceList.addAll(glDataService.getARAPFee());
		glInterfaceList.addAll(glDataService.getCashDetailFee());
		
		YearQuarter financeQuarter = getOpeningFinQuarter();
		List<GLInterfaceVO> mappingVOList = subLedgerMapping(glInterfaceList, batch, financeQuarter);
		
		batch.setStatus(YesNoType.YES.getType());
		glBatchDao.insertOrUpdate(batch);
		
		callBackBizModule(mappingVOList);
	}
	
	@Override
	public void subLedgerWriteDirectly(Long transId) throws Exception {
		List<GLInterfaceVO> glInterfaceList = glDataService.getInterfaceFee(transId);
		YearQuarter financeQuarter = getOpeningFinQuarter();
		List<GLInterfaceVO> mappingVOList = subLedgerMapping(glInterfaceList, null, financeQuarter);
		callBackBizModule(mappingVOList);
	}
	
	@Override
	public void generalLedgerGeneration() throws Exception {
		logger.info("==========generalLedgerGeneration============");
		GLBatch batch = null;
		boolean isGLOpen = isGLOpening();
		if(isGLOpen){
			batch = createGlBatch(GLBatchType.GENERAL_LEDGER_GENERATION.getValue());
			
			boolean isOnlySpecialSubmit = accoutingService.inClosingPeriod()? true:false;
			YearQuarter financeQuarter = getOpeningFinQuarter();
			List<GLSubLedger> subLedgerList = glSubLedgerDao.getSubLedgers(isOnlySpecialSubmit);
			List<GLExDetail> slExList = generateExInGLCurrency(subLedgerList);
			saveGLExList(slExList);
			List<GLGeneralLedger> generalLedgerList = groupSubLedger(subLedgerList);

			postGeneralLedger(generalLedgerList, financeQuarter, batch);
			
			batch.setStatus(YesNoType.YES.getType());
			glBatchDao.insertOrUpdate(batch);
		}
		try{
			glFileService.generateGLFile();
		}catch(Exception e){
			glLogDao.log("Generate GL file faile : "+e.getMessage(), glLogDao.LOG_TYPE_BATCH, batch==null?null:batch.getGlBatchId());
		}
	}
	
	/**
	 * Calculate amount in GL currency
	 * @param subLedgerList
	 * @throws Exception
	 */
	private List<GLExDetail> generateExInGLCurrency(List<GLSubLedger> subLedgerList) throws Exception{
		List<GLExDetail> result = new ArrayList<GLExDetail>();
		if(CollectionUtils.isNotEmpty(subLedgerList)){
			List<GLExDetail> exDetailList;
			for(GLSubLedger subLedger : subLedgerList){
				//existing sub ledger in GL currency
				exDetailList = glExDetailDao.findBySourceTypeAndSourceId(GL_EX_SOURCE_SUB_LEDGER, subLedger.getSubLedgerId());
				// cash fee in GL currency
				if(CollectionUtils.isEmpty(exDetailList) && subLedger.getSettleDetailId() != null){
					List<SetllementExchangeDetail> settleExList = setllementExchangeDetailDao.getBySourceTypeAndSourceId(subLedger.getSettleSourceType(), subLedger.getSettleDetailId());
					if(CollectionUtils.isNotEmpty(settleExList)){
						exDetailList = covertSettleExDetailToGL(settleExList, GL_EX_SOURCE_SUB_LEDGER, subLedger.getSubLedgerId());
					}
				}
				//calculate amount in GL currency
				if(CollectionUtils.isEmpty(exDetailList) && subLedger.getAmount() != null){
					exDetailList = calcGLExDetail(GL_EX_SOURCE_SUB_LEDGER, subLedger.getSubLedgerId(),subLedger.getCurrencyCode(), subLedger.getAmount());
				}
				if(CollectionUtils.isNotEmpty(exDetailList)){
					subLedger.setExDetailList(exDetailList);
					result.addAll(exDetailList);
				}
			}
		}
		return result;
	}
	
	private void saveGLExList(List<GLExDetail> exDetailList) throws Exception{
		if(CollectionUtils.isNotEmpty(exDetailList)){
			for(GLExDetail glEx : exDetailList){
				if(glEx.getDetailId() == null){
					glExDetailDao.insert(glEx);
				}
			}
		}
	}
	
	private List<GLExDetail> covertSettleExDetailToGL(List<SetllementExchangeDetail> settleExList, Integer sourceType, Long sourceId) throws Exception{
		List<GLExDetail> glExList = new ArrayList<GLExDetail>();
		for(SetllementExchangeDetail settleEx : settleExList){
			GLExDetail glEx = new GLExDetail();
			glEx.setAmount(settleEx.getConvertedAmount());
			glEx.setCurrencyCode(settleEx.getConvertCurrencyCode());
			glEx.setExchangeRate(settleEx.getExchangeRate());
			glEx.setSourceId(sourceId);
			glEx.setSourceType(sourceType);
		}
		return glExList;
	}
	
	private List<GLExDetail> calcGLExDetail(Integer sourceType, Long sourceId, String orgCurrencyCode, BigDecimal amount) throws Exception{
		List<GLExDetail> glExList = new ArrayList<GLExDetail>();
		Date transDate = AppContext.getSysDate();
		String GLCurrencyCode;
		BigDecimal exchangeRate;
		BigDecimal exAmount = amount;
		for(int idx = 0; idx < CurrencyConstants.GL_CURRENCY_CODE_ARRAY.length; idx++){
			GLCurrencyCode = CurrencyConstants.GL_CURRENCY_CODE_ARRAY[idx];
			exchangeRate = BigDecimal.ONE;
			GLExDetail glEx = new GLExDetail();
			if(!GLCurrencyCode.equals(orgCurrencyCode)){
				exchangeRate = currencyExchangeService.getExchangeRate(orgCurrencyCode, GLCurrencyCode, transDate);
				exAmount = currencyExchangeService.exchangeAmount(amount, orgCurrencyCode, GLCurrencyCode, transDate);
			}
			glEx.setAmount(exAmount);
			glEx.setCurrencyCode(GLCurrencyCode);
			glEx.setExchangeRate(exchangeRate);
			glEx.setSourceId(sourceId);
			glEx.setSourceType(sourceType);
			glExList.add(glEx);
		}
		return glExList;
	}
	
	private void postGeneralLedger(List<GLGeneralLedger> generalLedgerList, YearQuarter financeQuarter, GLBatch glBatch) throws Exception{
		if(CollectionUtils.isNotEmpty(generalLedgerList)){
			List<GLSubLedger> subLedgerList;
			GLSubLedger subLedgerItem = null;
			
			for(GLGeneralLedger generalLedger : generalLedgerList){
				generalLedger.setFinQuarter(financeQuarter.toString());
				generalLedger.setGlBatchId(glBatch.getGlBatchId());
				generalLedger.setPostDate(AppContext.getSysDate());
				glGeneralLedgerDao.insertOrUpdate(generalLedger);
				
				postGeneralLedgerEx(generalLedger);
				
				subLedgerList = generalLedger.getSubLedgerList();
				if(CollectionUtils.isNotEmpty(subLedgerList)){
					for(GLSubLedger subLedger : subLedgerList){
						subLedgerItem = glSubLedgerDao.load(subLedger.getSubLedgerId());
						subLedgerItem.setGeneralLedgerId(generalLedger.getGeneralLedgerId());
						glSubLedgerDao.insertOrUpdate(subLedgerItem);
						glDataService.updatePostStatus(subLedgerItem.getFeeId(), subLedgerItem.getSettleDetailId(), PostStatus.POSTED);
					}
				}
			}
		}
	}
	
	private void postGeneralLedgerEx(GLGeneralLedger generalLedger) throws Exception{
		List<GLExDetail> glExList = generalLedger.getExDetailList();
		if(CollectionUtils.isNotEmpty(glExList)){
			for(GLExDetail glEx : glExList){
				glEx.setSourceType(GL_EX_SOURCE_GENERAL_LEDGER);
				glEx.setSourceId(generalLedger.getGeneralLedgerId());
			}
			saveGLExList(glExList);
		}
	}
	
	/**
     * Generate general ledger through grouping sub ledgers
     * 
     * @param subLedgerList
     * @return
     * @throws Exception
     */
	private List<GLGeneralLedger> groupSubLedger(List<GLSubLedger> subLedgerList) throws Exception{
		Map<String, GLGeneralLedger> generalLedgerMap = new HashMap<String, GLGeneralLedger>();
		String groupKey = "";
		if(CollectionUtils.isNotEmpty(subLedgerList)){
			for(GLSubLedger subLedger : subLedgerList){
				if(subLedger.getAmount() != null){
					subLedger.setDocDate(AppContext.getSysDate());
					groupKey = subLedger.getGLAccountGroupingKey();
					GLGeneralLedger generalLedger = generalLedgerMap.get(groupKey);
					if(generalLedger == null){
						generalLedger = new GLGeneralLedger();
						generalLedger.setSubLedgerList(new ArrayList<GLSubLedger>());
						generalLedger.setGlAccountNo(subLedger.getGlAccountNo());
						generalLedger.setGlAccountType(subLedger.getGlAccountType());
						generalLedger.setMainTransaction(subLedger.getMainTransaction());
						generalLedger.setSubTransaction(subLedger.getSubTransaction());
						generalLedger.setDocDate(subLedger.getDocDate());
						generalLedger.setCurrencyCode(subLedger.getCurrencyCode());
						generalLedger.setAmount(BigDecimal.ZERO);
						generalLedgerMap.put(groupKey, generalLedger);
					}
					generalLedger.setAmount(generalLedger.getAmount().add(subLedger.getAmount()));
					generalLedger.getSubLedgerList().add(subLedger);
					
					groupSubLedgerEx(generalLedger, subLedger);
				}
			}
		}
		return new ArrayList<GLGeneralLedger>(generalLedgerMap.values());
	}
	
	private void groupSubLedgerEx(GLGeneralLedger gl, GLSubLedger sl) throws Exception{
		List<GLExDetail> slExList = sl.getExDetailList();
		if(CollectionUtils.isNotEmpty(slExList)){
			
			List<GLExDetail> glExList = gl.getExDetailList();
			if(CollectionUtils.isEmpty(glExList)){
				glExList = new ArrayList<GLExDetail>();
				gl.setExDetailList(glExList);
			}
			
			GLExDetail glCurrencyEx;
			for(GLExDetail slEx : slExList){//sub ledger exchange detail
				glCurrencyEx = null;
				for(GLExDetail glEx : glExList){//general ledger exchange detail
					if(slEx.getCurrencyCode().equalsIgnoreCase(glEx.getCurrencyCode())){//group by the same currency
						glCurrencyEx = glEx;
					}
				}
				if(glCurrencyEx == null){
					glCurrencyEx = new GLExDetail();
					glCurrencyEx.setCurrencyCode(slEx.getCurrencyCode());
					
					glExList.add(glCurrencyEx);
				}
				glCurrencyEx.setAmount(glCurrencyEx.getAmount().add(slEx.getAmount()));
			}
		}
	}
	
	private boolean isGLOpening() throws Exception{
		//TO DO
		return true;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void callBackBizModule(List<GLInterfaceVO> glInterfaceList) throws Exception{
		if(CollectionUtils.isNotEmpty(glInterfaceList)){
			Map<Integer, List<String>> postDataMap = new HashMap<Integer, List<String>>();
			for(GLInterfaceVO vo : glInterfaceList){
				//Settlement detail is not necessary to be callback to business module
				if(vo.getBizTransType() != null && vo.getSettleDetailId() == null){
					List<String> transTypeDataList = postDataMap.get(vo.getBizTransType());
					if(CollectionUtils.isEmpty(transTypeDataList)){
						transTypeDataList = new ArrayList<String>();
						postDataMap.put(vo.getBizTransType(), transTypeDataList);
					}
					transTypeDataList.add(vo.getBizTransNo());
				}
			}
			YearQuarter financeQuarter = accoutingService.currentFinancialQuarter();
			Date postDate = financeQuarter.getClosingDate();
			Set transTypeSet = postDataMap.keySet();
			if(transTypeSet != null){
				for(Iterator<Integer> ite = transTypeSet.iterator();ite.hasNext();){
					Integer transType = ite.next();
					List<String> transNoList = postDataMap.get(transType);
					Integer transCategory = BizTransactionType.getCategory(transType);
					switch(transCategory){
					case 1:
						break;
					case 2:
						SoaBizServiceImpl.callBack(postDate, transNoList);
						break;
					}
				}
			}
		}
	}
	
	private List<GLInterfaceVO> subLedgerMapping(List<GLInterfaceVO> interfaceDataList, GLBatch glBatch, YearQuarter financeQuarter) throws Exception{
		List<GLInterfaceVO> mappingVOList = new ArrayList<GLInterfaceVO>();
		if(CollectionUtils.isNotEmpty(interfaceDataList)){
			List<GLAccountMappingDef> accountMappingDefList = null;
			for(GLInterfaceVO interfaceData : interfaceDataList){
				accountMappingDefList = glDataService.getGLAcctountMapping(interfaceData);
				if(glAccountMapping(interfaceData, accountMappingDefList, glBatch, financeQuarter)){
					mappingVOList.add(interfaceData);
				}else{
					logger.error(interfaceData.getNoMappingDefMessage());
				}
			}
		}
		return mappingVOList;
	}
	
	private boolean glAccountMapping(GLInterfaceVO interfaceData, List<GLAccountMappingDef> accountMappingDefList, GLBatch glBatch, YearQuarter financeQuarter) throws Exception{
		if(CollectionUtils.isEmpty(accountMappingDefList)){
			return false;
		}
		boolean isMatch = false;
		if(CollectionUtils.isNotEmpty(accountMappingDefList)){
			GLMappingEntry glMappingEntry = newGLMappingEntry();
			for(GLAccountMappingDef accountMappingDef : accountMappingDefList){
				GLSubLedger subLedger = glAccountMappingMatch(interfaceData, accountMappingDef, glMappingEntry.getPrimaryKey());
				if(subLedger != null){
					subLedger.setPredictedQuarter(financeQuarter.toString());
					if(glBatch != null){
						subLedger.setGlBatchId(glBatch.getGlBatchId());
					}
					glSubLedgerDao.insert(subLedger);
					isMatch = true;
				}
			}
			if(isMatch){
				glDataService.updatePostStatus(interfaceData.getFeeId(), interfaceData.getSettleDetailId(), PostStatus.SUB_LEDGER_MAPPING);
			}
		}
		return isMatch;
	}
	
	private GLMappingEntry newGLMappingEntry() throws Exception{
		GLMappingEntry glMappingEntry = new GLMappingEntry();
		glMappingEntryDao.insert(glMappingEntry);
		return glMappingEntry;
	}
	
	private GLSubLedger glAccountMappingMatch(GLInterfaceVO data, GLAccountMappingDef def, Long glMappingEntryId) throws Exception{
		boolean isMatch = true;
		if(!def.getEntryCode().equals(data.getEntryCode())){
			return null;
		}
		if(isMatch && def.getCurrencyCode() != null && !def.getCurrencyCode().equals(data.getCurrencyCode())){
			isMatch = false;
		}
		if(isMatch && def.getContractCate() != null && !def.getContractCate().equals(data.getContractCate())){
			isMatch = false;
		}
		if(isMatch && def.getCashBalanceType() != null && !def.getCashBalanceType().equals(data.getCashBalanceType())){
			isMatch = false;
		}
		if(isMatch && !glAccountMappingMatchOnOtherFactors(data.getOtherFactorMap(), def.getOtherFactorMap())){
			isMatch = false;
		}
		if(isMatch){
			GLSubLedger subLedger = new GLSubLedger();
			BeanUtils.copyPropertiesIngoreNull(subLedger, data);
			BeanUtils.copyPropertiesIngoreNull(subLedger, def);
			subLedger.setGlMappingId(def.getMappingId());
			subLedger.setGlMappingEntryId(glMappingEntryId);
			subLedger.setAmount(subLedger.getAmount());
			
			return subLedger;
		}
		
		return null;
	}
	
	private boolean glAccountMappingMatchOnOtherFactors(Map<String, String> data, Map<String, String> def){
		if(def != null && !def.isEmpty()){
			if(data == null || data.isEmpty()){
				return false;
			}
			Iterator<String> keys = def.keySet().iterator();
			String factor;
			while(keys.hasNext()){
				factor = keys.next();
				if(!def.get(factor).equalsIgnoreCase(data.get(factor))){
					return false;
				}
			}
		}
		return true;
	}
	
	private GLBatch createGlBatch(Integer glBatchType) throws Exception{
		GLBatch batch = new GLBatch();
		batch.setBatchType(glBatchType);
		batch.setStartTime(AppContext.getSysDate());
		glBatchDao.insertOrUpdate(batch);
		return batch;
	}
	
	@Override
	public YearQuarter getOpeningFinQuarter() throws Exception{
		boolean isClosingPeriod = accoutingService.inClosingPeriod();
		YearQuarter financeQuarter = accoutingService.currentFinancialQuarter();
		if(isClosingPeriod){
			financeQuarter.nextQuarter();
		}
		return financeQuarter;
	}
}
