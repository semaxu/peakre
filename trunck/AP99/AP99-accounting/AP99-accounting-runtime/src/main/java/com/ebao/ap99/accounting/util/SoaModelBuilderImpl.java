package com.ebao.ap99.accounting.util;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.accounting.UI.model.Soa4UpdateVO;
import com.ebao.ap99.accounting.UI.model.SoaCurrency4UpdateVO;
import com.ebao.ap99.accounting.UI.model.SoaCurrencyVO;
import com.ebao.ap99.accounting.UI.model.SoaSearchVO;
import com.ebao.ap99.accounting.UI.model.SoaSection4UpdateVO;
import com.ebao.ap99.accounting.UI.model.SoaSectionItem4UpdateVO;
import com.ebao.ap99.accounting.UI.model.SoaSectionItemVO;
import com.ebao.ap99.accounting.UI.model.SoaSectionVO;
import com.ebao.ap99.accounting.UI.model.SoaVO;
import com.ebao.ap99.accounting.model.SoaCurrencyModel;
import com.ebao.ap99.accounting.model.SoaCurrencyNonPropVO;
import com.ebao.ap99.accounting.model.SoaModel;
import com.ebao.ap99.accounting.model.SoaSectionItemModel;
import com.ebao.ap99.accounting.model.SoaSectionItemNonPropVO;
import com.ebao.ap99.accounting.model.SoaSectionModel;
import com.ebao.ap99.accounting.model.SoaSectionNonPropVO;
import com.ebao.ap99.accounting.model.SoaNonPropVO;
import com.ebao.ap99.accounting.entity.TRiAcctQuarterClosing;
import com.ebao.ap99.accounting.entity.TRiSoa;
import com.ebao.ap99.accounting.entity.TRiSoaCurrency;
import com.ebao.ap99.accounting.entity.TRiSoaSection;
import com.ebao.ap99.accounting.entity.TRiSoaSectionItem;
import com.ebao.ap99.accounting.util.SoaConstant;
import com.ebao.ap99.arap.entity.EntryCode;
import com.ebao.ap99.arap.service.EntryCodeService;
import com.ebao.ap99.contract.entity.ContractModel;
import com.ebao.ap99.contract.service.ContractService;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.unicorn.platform.foundation.utils.BeanUtils;
public class SoaModelBuilderImpl {
	@Autowired
    public ContractService contractService;
	
    @Autowired
	EntryCodeService entryCodeService;
    
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	public SoaModelBuilderImpl() {
	}
	public SoaModel buildSoaModel(TRiSoa soa) throws Exception {
		SoaModel soaM = new SoaModel();
		BeanUtils.copyProperties(soaM, soa);
		soaM.setSoaId(String.valueOf(soa.getSoaId()));
		// build soa currency list
		buildSoaCurrencyModelList(soa,soaM);
		return soaM;
	}
	public List<SoaCurrencyModel> buildSoaCurrencyModelListMap(TRiSoa soa,SoaModel soaM) throws Exception {
		List<TRiSoaCurrency> soaCurrencyList = soa.getTRiSoaCurrencies();
		List<SoaCurrencyModel> soaCurrencyModelList = new ArrayList<SoaCurrencyModel> ();
		if(soaCurrencyList!= null){
		for (TRiSoaCurrency soaCurrency :  soaCurrencyList){
		 //   soaCurrency = (TRiSoaCurrency)soaCurrencyObj;
			SoaCurrencyModel soaCurrencyM = new SoaCurrencyModel();
			BeanUtils.copyProperties(soaCurrencyM, soaCurrency);
			soaCurrencyM.setSoaCurrencyId(String.valueOf(soaCurrency.getSoaCurrencyId()));
			soaCurrencyM.setCurrencyType(soaCurrency.getCurrencyCode());
			soaCurrencyModelList.add(soaCurrencyM);
			//build soa section list		
			buildSoaSectionModelList(soaCurrency,soaCurrencyM,soa.getContractId());
		}
		}
		soaM.setCurrencies(soaCurrencyModelList);
				return soaCurrencyModelList;		
	}
	public void buildSoaSectionModelList(TRiSoaCurrency soaCurrency,SoaCurrencyModel soaCurrencyM,long contractId) throws Exception {
		 List<TRiSoaSection> soaSectionList = soaCurrency.getTRiSoaSections();
		List<SoaSectionModel> soaSectionModelList = new ArrayList<SoaSectionModel>();
			for (TRiSoaSection soaSection :  soaSectionList){
				SoaSectionModel soaSectionM = new SoaSectionModel();
	            BeanUtils.copyProperties(soaSectionM, soaSection);
	            soaSectionM.setSoaSectionId(String.valueOf(soaSection.getSoaSectionId()));
	            soaSectionM.setContracCompId(soaSection.getContracCompId().toString());
	            ContractModel contactVO = contractService.getContractInfoByCompId(Long.valueOf(soaSection.getContracCompId()));
	            soaSectionM.setContractSection(contactVO.getFullName());
				soaSectionModelList.add(soaSectionM);
				//build soa section item list
				BigDecimal sectionCashBalanceTotal = BigDecimal.ZERO;
				BigDecimal sectionTotal = BigDecimal.ZERO;
				buildSoaSectionItemModelList(soaSection,soaSectionM,sectionCashBalanceTotal, sectionTotal,contractId);	
			}
		soaCurrencyM.setSections(soaSectionModelList);
	}
	/*
	 * Build entry items for currency section
	 * @see com.ebao.ap99.accounting.util.SoaModelBuilder#buildSoaSectionItemModelList(com.ebao.ap99.accounting.entity.TRiSoaSection, com.ebao.ap99.accounting.model.SoaSectionModel)
	 */
	public void buildSoaSectionItemModelList(TRiSoaSection soaSection, SoaSectionModel soaSectionM,BigDecimal sectionCashBalanceTotal,BigDecimal sectionTotal,long contractId) throws Exception {
		List<TRiSoaSectionItem> soaSectionItemList = soaSection.getTRiSoaSectionItems();
		List<SoaSectionItemModel> soaSectionItemModelList = new ArrayList<SoaSectionItemModel>();
			for (TRiSoaSectionItem entryItem : soaSectionItemList) {
				SoaSectionItemModel soaSectionItemM = new SoaSectionItemModel();
	            BeanUtils.copyProperties(soaSectionItemM, entryItem);
	            soaSectionItemM.setItemId(String.valueOf(entryItem.getItemId())); 
	            //calculate the Premium,IncurredLoss,Expense
	            calculateEntryItemValue(entryItem,soaSectionM);
	            //calculate the cashBalance
	            calculateCashBalance(sectionCashBalanceTotal,sectionTotal,entryItem,soaSectionM,contractId);
				soaSectionItemModelList.add(soaSectionItemM);
			}		
		soaSectionM.setEntryItems(soaSectionItemModelList);
	}
	public SoaModel buildSoaModelObj(TRiSoa soa) throws Exception {
		SoaModel soaM = new SoaModel();
		BeanUtils.copyProperties(soaM, soa);
		soaM.setSoaId(String.valueOf(soa.getSoaId()));
		soaM.setTaskCreator(soa.getTaskcreator());
		return soaM;
	}

	public SoaModel buildSoaModel4Seach(SoaSearchVO soaSearchVO) throws Exception {
		SoaModel soaM = new SoaModel();
		BeanUtils.copyProperties(soaM, soaSearchVO);
		return soaM;
	}
	public SoaModel buildSoaModelFromSoaVO(SoaVO soaVO) throws Exception {
		SoaModel soaM = new SoaModel();
		BeanUtils.copyProperties(soaM, soaVO);
		if (soaVO.getAccountLevel() != null && !"".equals(soaVO.getAccountLevel())) {
			soaM.setAccountLevel(soaVO.getAccountLevel());
		}
		if (soaVO.getBizType() != null && !"".equals(soaVO.getBizType())) {
			soaM.setBizType(soaVO.getBizType());
		}
		soaM.setBroke(soaVO.getBroke());
		soaM.setCedent(soaVO.getCedent());
		if (soaVO.getCedentQuarter() != null && !"".equals(soaVO.getCedentQuarter())) {
			soaM.setCedentQuarter(Integer.valueOf(soaVO.getCedentQuarter()));
		}
		if (soaVO.getCedentYear() != null && !"".equals(soaVO.getCedentYear())) {
			soaM.setCedentYear(Integer.valueOf(soaVO.getCedentYear()));
		}
		if (soaVO.getContractCode() != null && !"".equals(soaVO.getContractCode())) {
			soaM.setContractCode(soaVO.getContractCode());
		}
		if (soaVO.getDueDate() != null && !"".equals(soaVO.getDueDate())) {
			String[] dueDate = soaVO.getDueDate().split("T");
			soaM.setDueDate(formatter.parse(stringToDate(dueDate[0])));
		}
		if (soaVO.getReceivedDate() != null && !"".equals(soaVO.getReceivedDate())) {
			soaM.setReceivedDate(formatter.parse(stringToDate(soaVO.getReceivedDate())));
		}
		soaM.setRemarks(soaVO.getRemarks());
		soaM.setSoaText(soaVO.getSoaText());
		if (soaVO.getUwYear() != null && !"".equals(soaVO.getUwYear())) {
			soaM.setUwYear(Integer.valueOf(soaVO.getUwYear()));
		}
		if (soaVO.getFinancialYear() != null && !"".equals(soaVO.getFinancialYear())) {
			soaM.setFinancialYear(Integer.valueOf(soaVO.getFinancialYear()));
		}
			soaM.setSoaId(soaVO.getSoaIdRead().trim());
		// build soa currency list
		soaM = buildSoaCurrencyModelListFromSoaVO(soaVO,soaM);
		return soaM;
	}
	public SoaModel buildSoaCurrencyModelListFromSoaVO(SoaVO soaVO,SoaModel soaM) throws Exception {
		List<SoaCurrencyVO> soaCurrencyList = soaVO.getCurrencies();
		List<SoaCurrencyModel> soaCurrencyModelList = new ArrayList<SoaCurrencyModel>();
		SoaCurrencyVO soaCurrencyVO = null;
			for (Object soaCurrencyObj :  soaCurrencyList){
				soaCurrencyVO = (SoaCurrencyVO)soaCurrencyObj;
				if(soaCurrencyVO.getSections()!=null){
					SoaCurrencyModel soaCurrencyM = new SoaCurrencyModel();
					BeanUtils.copyProperties(soaCurrencyM, soaCurrencyVO);
					soaCurrencyModelList.add(soaCurrencyM);
					//build soa section list
					List<SoaSectionModel> soaSectionModelList = buildSoaSectionModelListFromSoaVO(soaCurrencyVO,soaCurrencyM);
					soaCurrencyM.setSections(soaSectionModelList);
				}
			}
		soaM.setCurrencies(soaCurrencyModelList);
				return soaM;		
	}
	public List<SoaSectionModel> buildSoaSectionModelListFromSoaVO(SoaCurrencyVO soaCurrencyVO,SoaCurrencyModel soaCurrencyM) throws Exception {
		List<SoaSectionVO> soaSectionList = soaCurrencyVO.getSections();
		List<SoaSectionModel> soaSectionModelList = new ArrayList<SoaSectionModel> ();
			for (SoaSectionVO soaSectionVO :  soaSectionList){
				SoaSectionModel soaSectionM = new SoaSectionModel();
				BeanUtils.copyProperties(soaSectionM, soaSectionVO);
				soaSectionModelList.add(soaSectionM);
				//build soa section item list
				List<SoaSectionItemModel> soaSectionItemModelList = buildSoaSectionItemModelListFromSoaVO(soaSectionVO,soaSectionM);
				soaSectionM.setEntryItems(soaSectionItemModelList);
			}
		soaCurrencyM.setSections(soaSectionModelList);
		return soaSectionModelList;
	}
	public List<SoaSectionItemModel> buildSoaSectionItemModelListFromSoaVO(SoaSectionVO soaSectionVO, SoaSectionModel soaSectionM) throws Exception {
		List<SoaSectionItemVO> soaSectionItemList = soaSectionVO.getEntryItems();
		List<SoaSectionItemModel> soaSectionItemModelList = new ArrayList<SoaSectionItemModel>();
			for (SoaSectionItemVO soaSectionItemVO : soaSectionItemList) {
				SoaSectionItemModel soaSectionItemM = new SoaSectionItemModel();
				BeanUtils.copyProperties(soaSectionItemM, soaSectionItemVO);
				soaSectionItemModelList.add(soaSectionItemM);
			}
		soaSectionM.setEntryItems(soaSectionItemModelList);;
		return soaSectionItemModelList;
	}
	public Object buildSoaModelFromSoa4UpdateVO(Soa4UpdateVO soaU) throws Exception {
        SoaModel soaM = new SoaModel();
		BeanUtils.copyProperties(soaM, soaU);
		if (soaU.getAccountLevel() != null && !"".equals(soaU.getAccountLevel())) {
			soaM.setAccountLevel(soaU.getAccountLevel());
		}
		if (soaU.getBizType() != null && !"".equals(soaU.getBizType())) {
			soaM.setBizType(soaU.getBizType());
		}
		soaM.setBroke(soaU.getBroke());
		soaM.setCedent(soaU.getCedent());
		if (soaU.getCedentQuarter() != null && !"".equals(soaU.getCedentQuarter())) {
			soaM.setCedentQuarter(Integer.valueOf(soaU.getCedentQuarter()));
		}
		if (soaU.getCedentYear() != null && !"".equals(soaU.getCedentYear())) {
			soaM.setCedentYear(Integer.valueOf(soaU.getCedentYear()));
		}
		if (soaU.getContractCode() != null && !"".equals(soaU.getContractCode())) {
			soaM.setContractCode(soaU.getContractCode());
		}
		if (soaU.getDueDate() != null && !"".equals(soaU.getDueDate())) {
			soaM.setDueDate(formatter.parse(stringToDate(soaU.getDueDate())));
		}
		if (soaU.getReceivedDate() != null && !"".equals(soaU.getReceivedDate())) {
			soaM.setReceivedDate(formatter.parse(stringToDate(soaU.getReceivedDate())));
		}
		soaM.setRemarks(soaU.getRemarks());
		soaM.setSoaText(soaU.getSoaText());
		if (soaU.getUwYear() != null && !"".equals(soaU.getUwYear())) {
			soaM.setUwYear(Integer.valueOf(soaU.getUwYear()));
		}
		if (soaU.getFinancialYear() != null && !"".equals(soaU.getFinancialYear())) {
			soaM.setFinancialYear(Integer.valueOf(soaU.getFinancialYear()));
		}
		//	soaM.setSoaId(soaU.getSoaId().trim());
		// build soa currency list
		soaM = buildSoaCurrencyModelListFromSoa4UpdateVO(soaU,soaM);
		return soaM;
	}
   public SoaModel buildSoaCurrencyModelListFromSoa4UpdateVO(Soa4UpdateVO soaU,SoaModel soaM) throws Exception {
	   List<SoaCurrency4UpdateVO> soaCurrencyList = soaU.getCurrencies();
		List<SoaCurrencyModel> soaCurrencyModelList = new ArrayList<SoaCurrencyModel>();
		SoaCurrency4UpdateVO soaCurrency4UpdateVO = null;
		if(soaCurrencyList != null){
			for (Object soaCurrencyObj :  soaCurrencyList){
				soaCurrency4UpdateVO = (SoaCurrency4UpdateVO)soaCurrencyObj;
				if(!"+".equals(soaCurrency4UpdateVO.getCurrencyType())){
					SoaCurrencyModel soaCurrencyM = new SoaCurrencyModel();
					soaCurrencyM.setSoaCurrencyId(soaCurrency4UpdateVO.getSoaCurrencyId());
					BeanUtils.copyProperties(soaCurrencyM, soaCurrency4UpdateVO);
					soaCurrencyModelList.add(soaCurrencyM);
					//build soa section list
					List<SoaSectionModel> soaSectionModelList = buildSoaSectionModelListFromSoa4UpdateVO(soaCurrency4UpdateVO,soaCurrencyM);
					soaCurrencyM.setSections(soaSectionModelList);
				}
			}
		}
		soaM.setCurrencies(soaCurrencyModelList);
				return soaM;		
	}
   public  List<SoaSectionModel> buildSoaSectionModelListFromSoa4UpdateVO(SoaCurrency4UpdateVO soaCurrency4UpdateVO,SoaCurrencyModel soaCurrencyM) throws Exception {
	   List<SoaSection4UpdateVO> soaSectionList = soaCurrency4UpdateVO.getSections();
	   List<SoaSectionModel> soaSectionModelList = new ArrayList<SoaSectionModel>();
			for (SoaSection4UpdateVO soaSection4UpdateVO :  soaSectionList){
				SoaSectionModel soaSectionM = new SoaSectionModel();
				BeanUtils.copyProperties(soaSectionM, soaSection4UpdateVO);
				soaSectionModelList.add(soaSectionM);
				//build soa section item list
				List<SoaSectionItemModel> soaSectionItemModelList = buildSoaSectionItemModelListFromSoa4UpdateVO(soaSection4UpdateVO,soaSectionM);
				soaSectionM.setEntryItems(soaSectionItemModelList);
			}
		soaCurrencyM.setSections(soaSectionModelList);
		return soaSectionModelList;
	}
   public List<SoaSectionItemModel> buildSoaSectionItemModelListFromSoa4UpdateVO(SoaSection4UpdateVO soaSection4UpdateVO, SoaSectionModel soaSectionM) throws Exception {
	    List<SoaSectionItem4UpdateVO> soaSectionItemList = soaSection4UpdateVO.getEntryItems();
		List<SoaSectionItemModel> soaSectionItemModelList = new ArrayList<SoaSectionItemModel> ();
			for (SoaSectionItem4UpdateVO soaSectionItem4UpdateVO : soaSectionItemList) {
				EntryCode entryCode = entryCodeService.getByEntryCode(soaSectionItem4UpdateVO.getEntryCode());
				if(entryCode!=null){
					SoaSectionItemModel soaSectionItemM = new SoaSectionItemModel();
					BeanUtils.copyProperties(soaSectionItemM, soaSectionItem4UpdateVO);
					soaSectionItemModelList.add(soaSectionItemM);
				}
			}
		soaSectionM.setEntryItems(soaSectionItemModelList);
		return soaSectionItemModelList;
	}
	public void buildSoaCurrencyModelList(TRiSoa soa,SoaModel soaM) throws Exception {
		List<TRiSoaCurrency> soaCurrencyList = soa.getTRiSoaCurrencies();
		List<SoaCurrencyModel> soaCurrencyModelList = new ArrayList<SoaCurrencyModel>();	
		for (TRiSoaCurrency soaCurrency :  soaCurrencyList){
			SoaCurrencyModel soaCurrencyM = new SoaCurrencyModel();
			BeanUtils.copyProperties(soaCurrencyM, soaCurrency);
			soaCurrencyM.setSoaCurrencyId(String.valueOf(soaCurrency.getSoaCurrencyId()));
			soaCurrencyM.setCurrencyType(soaCurrency.getCurrencyCode());
			soaCurrencyModelList.add(soaCurrencyM);
			//build soa section list
            buildSoaSectionModelList(soaCurrency,soaCurrencyM,soa.getContractId());
		}
		soaM.setCurrencies(soaCurrencyModelList);	
	}
	public String stringToDate(String pram){
		String returnValue = "";
	    if(pram.contains("T")){
			String[] pramR = pram.split("T");
			returnValue = pramR[0];
		}else{
			returnValue = pram;
		}
	    return returnValue;
	}
	
    
	private final String[] PREMIUM_ENTRY = {SoaConstant.PREMIUM_PROP,SoaConstant.XOL_PREMIUM,SoaConstant.XOL_PREMIUM_ADJUSTMENT,SoaConstant.REINSTATEMNET_PREMIUM};
	private final String[] EXPENSE_ENTRY = {SoaConstant.COMMISSION,SoaConstant.BROKERAGE,SoaConstant.TAX_OTHERS,SoaConstant.OVERRIDING_COMMISSION,SoaConstant.ACQUISITION_COST,SoaConstant.ADMINISTRACTIVE_EXPENSES,SoaConstant.FIRE_BRIGADE_TAX,SoaConstant.OTHER_EXPENSES,SoaConstant.INTERESTS_ON_ACCOUNTS};
	private final String[] INCURREDLOSS_ENTRY = {SoaConstant.LOSS_PAID,SoaConstant.HKAS_LOSS_RESERVE_CLOSING,SoaConstant.LAE_PAID,SoaConstant.CASH_CALL_PAYMENT,SoaConstant.HKAS_LAE_RESERVE};
	public void calculateEntryItemValue(TRiSoaSectionItem soaSectionItem,SoaSectionModel soaSectionM){
		if(ArrayUtils.contains(PREMIUM_ENTRY, soaSectionItem.getEntryCode()))
			soaSectionM.setPremium(soaSectionM.getPremium().add(soaSectionItem.getShareAmount()));
		if(ArrayUtils.contains(EXPENSE_ENTRY, soaSectionItem.getEntryCode()))
			soaSectionM.setExpense(soaSectionM.getExpense().add(soaSectionItem.getShareAmount()));
		if(ArrayUtils.contains(INCURREDLOSS_ENTRY, soaSectionItem.getEntryCode()))  	
		    soaSectionM.setIncurredLoss(soaSectionM.getIncurredLoss().add(soaSectionItem.getShareAmount()));	
		}
	
	public void calculateCashBalance(BigDecimal sectionCashBalanceTotal,BigDecimal sectionTotal, TRiSoaSectionItem soaSectionItem,
			SoaSectionModel soaSectionM,long contractId) throws Exception {
        ContractModel contractVO = contractService.getContractInfoByCompId(contractId);
        if(soaSectionItem.getEntryCode()!=null&&soaSectionItem.getShareAmount()!=null){
        	int reTroSign = 1;
        	if(contractVO!=null&&contractVO.getContractCategory()!=null&&"2".equals(contractVO.getContractCategory())){// Retro Contract
        		reTroSign = -1;
    		}
    		int sign = entryCodeService.getByEntryCode(soaSectionItem.getEntryCode()).getSign();
    	    if (entryCodeService.getByEntryCode(soaSectionItem.getEntryCode()).getCashBalance() == 1) {
    	        sectionCashBalanceTotal = soaSectionM.getCashBalance().add(soaSectionItem.getShareAmount().multiply(BigDecimal.valueOf(sign)).multiply(BigDecimal.valueOf(reTroSign)));
    	        soaSectionM.setCashBalance(sectionCashBalanceTotal);
    	    }
    	    sectionTotal = soaSectionM.getSectionTotal().add(soaSectionItem.getShareAmount().multiply(BigDecimal.valueOf(sign)).multiply(BigDecimal.valueOf(reTroSign)));
    	    soaSectionM.setSectionTotal(sectionTotal);
        }
		
	  
	}
	
	
	public Object buildSoaModelFromSoa4NonPropContVO(SoaNonPropVO soaU) throws Exception {
        SoaModel soaM = new SoaModel();
		BeanUtils.copyProperties(soaM, soaU);
		
		soaM.setBroke(soaU.getBroke());
		soaM.setCedent(soaU.getCedent());
		if (!"".equals(soaU.getCedentQuarter())&&soaU.getCedentQuarter() != 0 ) {
			soaM.setCedentQuarter(Integer.valueOf(soaU.getCedentQuarter()));
		}
		if ( !"".equals(soaU.getCedentYear())&&soaU.getCedentYear() != 0) {
			soaM.setCedentYear(Integer.valueOf(soaU.getCedentYear()));
		}
		if (soaU.getContractCode() != null && !"".equals(soaU.getContractCode())) {
			soaM.setContractCode(soaU.getContractCode());
		}
		if (soaU.getDueDate() != null && !"".equals(soaU.getDueDate())) {
			soaM.setDueDate(formatter.parse(stringToDate(soaU.getDueDate().toString())));
		}
		if (soaU.getReceivedDate() != null && !"".equals(soaU.getReceivedDate())) {
			soaM.setReceivedDate(formatter.parse(stringToDate(soaU.getReceivedDate().toString())));
		}
		soaM.setSoaText(soaU.getSoaText());
		if (soaU.getUwYear() != null && !"".equals(soaU.getUwYear())) {
			soaM.setUwYear(Integer.valueOf(soaU.getUwYear().toString()));
		}
		
		// build soa currency list
		soaM = buildSoaCurrencyModelListFromSoaSpecialVO(soaU,soaM);
		return soaM;
	}
	
	public SoaModel buildSoaCurrencyModelListFromSoaSpecialVO(SoaNonPropVO soaU,SoaModel soaM) throws Exception {
		   List<SoaCurrencyNonPropVO> soaCurrencyList = soaU.getCurrencies();
			List<SoaCurrencyModel> soaCurrencyModelList = new ArrayList<SoaCurrencyModel>();
				for (SoaCurrencyNonPropVO soaCurrencySpecialVO :  soaCurrencyList){
					if(!"+".equals(soaCurrencySpecialVO.getCurrencyType())){
						SoaCurrencyModel soaCurrencyM = new SoaCurrencyModel();
						BeanUtils.copyProperties(soaCurrencyM, soaCurrencySpecialVO);
						soaCurrencyModelList.add(soaCurrencyM);
						//build soa section list
						List<SoaSectionModel> soaSectionModelList = buildSoaSectionModelListFromSoaSpecialVO(soaCurrencySpecialVO,soaCurrencyM);
						soaCurrencyM.setSections(soaSectionModelList);
					}
				}
			soaM.setCurrencies(soaCurrencyModelList);
					return soaM;		
		}
	
	 public  List<SoaSectionModel> buildSoaSectionModelListFromSoaSpecialVO(SoaCurrencyNonPropVO soaCurrencySpecialVO,SoaCurrencyModel soaCurrencyM) throws Exception {
		   List<SoaSectionNonPropVO> soaSectionList = soaCurrencySpecialVO.getSections();
		   List<SoaSectionModel> soaSectionModelList = new ArrayList<SoaSectionModel>();
				for (SoaSectionNonPropVO soaSectionSpecialVO :  soaSectionList){
					SoaSectionModel soaSectionM = new SoaSectionModel();
					BeanUtils.copyProperties(soaSectionM, soaSectionSpecialVO);
					soaSectionModelList.add(soaSectionM);
					//build soa section item list
					List<SoaSectionItemModel> soaSectionItemModelList = buildSoaSectionItemModelListFromSoaSpecialVO(soaSectionSpecialVO,soaSectionM);
					soaSectionM.setEntryItems(soaSectionItemModelList);
				}
			soaCurrencyM.setSections(soaSectionModelList);
			return soaSectionModelList;
		}
	 public List<SoaSectionItemModel> buildSoaSectionItemModelListFromSoaSpecialVO(SoaSectionNonPropVO soaSectionSpecialVO, SoaSectionModel soaSectionM) throws Exception {
		    List<SoaSectionItemNonPropVO> soaSectionItemList = soaSectionSpecialVO.getEntryItems();
			List<SoaSectionItemModel> soaSectionItemModelList = new ArrayList<SoaSectionItemModel> ();
				for (SoaSectionItemNonPropVO soaSectionItemSpecialVO : soaSectionItemList) {			
					SoaSectionItemModel soaSectionItemM = new SoaSectionItemModel();
					BeanUtils.copyProperties(soaSectionItemM, soaSectionItemSpecialVO);
					soaSectionItemModelList.add(soaSectionItemM);
				}
			soaSectionM.setEntryItems(soaSectionItemModelList);;
			return soaSectionItemModelList;
		}
	
}
