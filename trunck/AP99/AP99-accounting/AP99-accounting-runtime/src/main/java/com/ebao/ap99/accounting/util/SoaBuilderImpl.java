package com.ebao.ap99.accounting.util;

import java.util.ArrayList;
import java.util.List;

import com.ebao.ap99.accounting.UI.model.Soa4UpdateVO;
import com.ebao.ap99.accounting.UI.model.SoaCurrency4UpdateVO;
import com.ebao.ap99.accounting.UI.model.SoaCurrencyVO;
import com.ebao.ap99.accounting.UI.model.SoaLayerVO;
import com.ebao.ap99.accounting.UI.model.SoaSearchVO;
import com.ebao.ap99.accounting.UI.model.SoaSection4UpdateVO;
import com.ebao.ap99.accounting.UI.model.SoaSectionItem4UpdateVO;
import com.ebao.ap99.accounting.UI.model.SoaSectionItemVO;
import com.ebao.ap99.accounting.UI.model.SoaSectionVO;
import com.ebao.ap99.accounting.UI.model.SoaVO;
import com.ebao.ap99.accounting.model.SoaCurrencyModel;
import com.ebao.ap99.accounting.model.SoaModel;
import com.ebao.ap99.accounting.model.SoaSectionItemModel;
import com.ebao.ap99.accounting.model.SoaSectionModel;
import com.ebao.ap99.accounting.entity.TRiSoa;
import com.ebao.ap99.accounting.entity.TRiSoaCurrency;
import com.ebao.ap99.accounting.entity.TRiSoaSection;
import com.ebao.ap99.accounting.entity.TRiSoaSectionItem;
import com.ebao.ap99.accounting.util.SoaConstant;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.unicorn.platform.foundation.utils.BeanUtils;

public class SoaBuilderImpl{

	public SoaBuilderImpl() {
	}
	public TRiSoa buildSoaObjFromModel(SoaModel soaM) throws Exception {
		TRiSoa soa = new TRiSoa();
		BeanUtils.copyProperties(soa, soaM); 
		soa.setSoaId(Long.parseLong(soaM.getSoaId().trim()));
		soa.setSoaStatus(SoaConstant.DATAINPUT);	
		return soa;
	}
	
	public TRiSoaCurrency buildSoaCurrencyObjFromModel(SoaCurrencyModel soaCurrencyM) throws Exception {
		TRiSoaCurrency soaCurrency = new TRiSoaCurrency();
		soaCurrency.setCurrencyCode(soaCurrencyM.getCurrencyType());
		BeanUtils.copyProperties(soaCurrency, soaCurrencyM);  
	//	soaCurrency.setSoaId(Long.parseLong(soaCurrencyM.getSoaId()));
				return soaCurrency;		
	}
	
	
	
	
	public TRiSoaSection buildSoaSectionObjFromModel(SoaSectionModel soaSectionM) throws Exception {
       TRiSoaSection soaSection = new TRiSoaSection();
		BeanUtils.copyProperties(soaSection, soaSectionM);   
		soaSection.setContracCompId(Long.parseLong(soaSectionM.getContracCompId()));
				return soaSection;	
	}
	
	public TRiSoaSectionItem buildSoaSectionItemObjFromModel(SoaSectionItemModel soaSectionItemM) throws Exception {
		 TRiSoaSectionItem  soaSectionItem  = new TRiSoaSectionItem ();
			BeanUtils.copyProperties(soaSectionItem , soaSectionItemM);   
			
					return soaSectionItem ;	
	}
	
	public TRiSoa buildSoaObjFromVO(SoaLayerVO soaV) throws Exception {
		TRiSoa soa = new TRiSoa();
		BeanUtils.copyProperties(soa, soaV); 
		//soaStatus  init value is 1(Pending)
		soa.setSoaStatus(SoaConstant.DATAINPUT);
		soa.setReviewedFlag("2");
       //task create
		soa.setTaskcreator(String.valueOf(AppContext.getCurrentUser().getUserId()));
		soa.setTaskcreatorName(String.valueOf(AppContext.getCurrentUser().getUserName()));
		return soa;
	}
	
	public TRiSoa buildSoaObjFromSpecialVO(SoaModel soaM) throws Exception {
		TRiSoa soa = new TRiSoa();
		BeanUtils.copyProperties(soa, soaM); 
		//soaStatus  init value is 1(Pending)
		soa.setSoaStatus(SoaConstant.DATAINPUT);
		soa.setReviewedFlag("2");
       //task create
		soa.setTaskcreator(String.valueOf(AppContext.getCurrentUser().getUserId()));
		soa.setTaskcreatorName(String.valueOf(AppContext.getCurrentUser().getUserName()));
		return soa;
	}

	public List<SoaCurrencyModel> buildSoaCurrencyList(TRiSoa soa,SoaModel soaM) throws Exception {
		List<TRiSoaCurrency> soaCurrencyList = soa.getTRiSoaCurrencies();
		List<SoaCurrencyModel> soaCurrencyModelList = new ArrayList<SoaCurrencyModel>();
		TRiSoaCurrency soaCurrency = null;
		for (Object soaCurrencyObj :  soaCurrencyList){
		    soaCurrency = (TRiSoaCurrency)soaCurrencyObj;
			SoaCurrencyModel soaCurrencyM = new SoaCurrencyModel();
			soaCurrencyModelList.add(soaCurrencyM);
			//build soa section list
			List<SoaSectionModel> soaSectionModelList = buildSoaSectionList(soaCurrency,soaCurrencyM);
			soaCurrencyM.setSections(soaSectionModelList);
		}
		soaM.setCurrencies(soaCurrencyModelList);
				return soaCurrencyModelList;		
	}
	
	public List<SoaSectionModel> buildSoaSectionList(TRiSoaCurrency soaCurrency,SoaCurrencyModel soaCurrencyM) throws Exception {
		List<TRiSoaSection> soaSectionList = soaCurrency.getTRiSoaSections();
		List<SoaSectionModel> soaSectionModelList = new ArrayList<SoaSectionModel>();
		TRiSoaSection soaSection = null;
		for (Object soaSectionObj :  soaSectionList){
		    soaSection = (TRiSoaSection)soaSectionObj;
			SoaSectionModel soaSectionM = new SoaSectionModel();
			soaSectionM.setCob(soaSection.getCob());
			soaSectionModelList.add(soaSectionM);
			//build soa section item list
			List<SoaSectionItemModel> soaSectionItemModelList = buildSoaSectionItemList(soaSection,soaSectionM);
			soaSectionM.setEntryItems(soaSectionItemModelList);
		}
		soaCurrencyM.setSections(soaSectionModelList);
		return soaSectionModelList;
	}
	
	public List<SoaSectionItemModel> buildSoaSectionItemList(TRiSoaSection soaSection, SoaSectionModel soaSectionM) throws Exception {
		List<TRiSoaSectionItem> soaSectionItemList = soaSection.getTRiSoaSectionItems();
		List<SoaSectionItemModel> soaSectionItemModelList = new ArrayList<SoaSectionItemModel>();
		TRiSoaSectionItem soaSectionItem = null;
		for (Object soaSectionItemObj : soaSectionItemList) {
			soaSectionItem = (TRiSoaSectionItem) soaSectionItemObj;
			SoaSectionItemModel soaSectionItemM = new SoaSectionItemModel();
			soaSectionItemM.setEntryCode(soaSectionItem.getEntryCode());
			soaSectionItemModelList.add(soaSectionItemM);
		}
		soaSectionM.setEntryItems(soaSectionItemModelList);;
		return soaSectionItemModelList;
	}
	
	public SoaModel buildSoaObj(TRiSoa soa) throws Exception {
		SoaModel soaM = new SoaModel();
	//	soaM.setBizType(new BigDecimal(1));
		soaM.setContractCode(soa.getContractCode());
		soaM.setBroke(soa.getBroke());
		return soaM;
	}
	
	public SoaModel buildSoa4Seach(SoaSearchVO soaSearchVO) throws Exception {
		SoaModel soaM = new SoaModel();
		soaM.setSoaId(soaSearchVO.getSoaId());		
		soaM.setContractCode(soaSearchVO.getContractCode());		
		soaM.setCedent(soaSearchVO.getCedent());			
		soaM.setReceivedDate(soaSearchVO.getReceivedDate());	
		soaM.setBizType(soaSearchVO.getBizType());		
		soaM.setSoaStatus(soaSearchVO.getSoaStatus());		
		soaM.setBroke(soaSearchVO.getBroke());	
		soaM.setCedentQuarter(soaSearchVO.getCedentQuarter());
		soaM.setCedentYear(soaSearchVO.getCedentYear());		
		if(soaSearchVO.isMainSoaFlag()==true){
			soaM.setMainSoaFlag("1");	
		}else{
			soaM.setMainSoaFlag("0");	
		}
		soaM.setUwYear(soaSearchVO.getUwYear());		
		soaM.setFinancialQuarter(soaSearchVO.getFinancialQuarter());
		soaM.setFinancialYear(soaSearchVO.getFinancialYear());		
		soaM.setTaskReleaser(soaSearchVO.getTaskReleaser());		
		soaM.setTaskCreator(soaSearchVO.getTaskCreator());
		return soaM;
	}

	public SoaModel buildSoaFromSoaVO(SoaVO soaVO) throws Exception {
		SoaModel soaM = new SoaModel();
		BeanUtils.copyProperties(soaM, soaVO);
		// build soa currency list
		soaM = buildSoaCurrencyListFromSoaVO(soaVO,soaM);
		return soaM;
	}
	
	public SoaModel buildSoaCurrencyListFromSoaVO(SoaVO soaVO,SoaModel soaM) throws Exception {
		List<SoaCurrencyVO> soaCurrencyList = soaVO.getCurrencies();
		List<SoaCurrencyModel> soaCurrencyModelList = new ArrayList<SoaCurrencyModel>();
		SoaCurrencyVO soaCurrencyVO = null;
		for (Object soaCurrencyObj :  soaCurrencyList){
			soaCurrencyVO = (SoaCurrencyVO)soaCurrencyObj;
			SoaCurrencyModel soaCurrencyM = new SoaCurrencyModel();
			BeanUtils.copyProperties(soaCurrencyM, soaCurrencyVO);
			soaCurrencyModelList.add(soaCurrencyM);
			//build soa section list
			List<SoaSectionModel> soaSectionModelList = buildSoaSectionListFromSoaVO(soaCurrencyVO,soaCurrencyM);
			soaCurrencyM.setSections(soaSectionModelList);
		}
		soaM.setCurrencies(soaCurrencyModelList);
				return soaM;		
	}
	

	public List<SoaSectionModel> buildSoaSectionListFromSoaVO(SoaCurrencyVO soaCurrencyVO,SoaCurrencyModel soaCurrencyM) throws Exception {
		List<SoaSectionVO> soaSectionList = soaCurrencyVO.getSections();
		List<SoaSectionModel> soaSectionModelList = new ArrayList<SoaSectionModel>();
		SoaSectionVO soaSectionVO = null;
		for (Object soaSectionObj :  soaSectionList){
			soaSectionVO = (SoaSectionVO)soaSectionObj;
			SoaSectionModel soaSectionM = new SoaSectionModel();
			BeanUtils.copyProperties(soaSectionM, soaSectionVO);
			soaSectionModelList.add(soaSectionM);
			//build soa section item list
			List<SoaSectionItemModel> soaSectionItemModelList = buildSoaSectionItemListFromSoaVO(soaSectionVO,soaSectionM);
			soaSectionM.setEntryItems(soaSectionItemModelList);
		}
		soaCurrencyM.setSections(soaSectionModelList);
		return soaSectionModelList;
	}
	
	
	public List<SoaSectionItemModel> buildSoaSectionItemListFromSoaVO(SoaSectionVO soaSectionVO, SoaSectionModel soaSectionM) throws Exception {
		List<SoaSectionItemVO> soaSectionItemList = soaSectionVO.getEntryItems();
		List<SoaSectionItemModel> soaSectionItemModelList = new ArrayList<SoaSectionItemModel>();
		SoaSectionItemVO soaSectionItemVO = null;
		for (Object soaSectionItemObj : soaSectionItemList) {
			soaSectionItemVO = (SoaSectionItemVO) soaSectionItemObj;
			SoaSectionItemModel soaSectionItemM = new SoaSectionItemModel();
			BeanUtils.copyProperties(soaSectionItemM, soaSectionItemVO);
			soaSectionItemModelList.add(soaSectionItemM);
		}
		soaSectionM.setEntryItems(soaSectionItemModelList);;
		return soaSectionItemModelList;
	}
	
	public TRiSoa buildSoaObjFromModelForUploading(SoaModel soaM) throws Exception {
		TRiSoa soa = new TRiSoa();
		BeanUtils.copyProperties(soa, soaM); 
		soa.setSoaStatus(SoaConstant.DATAINPUT);	
		return soa;
	}
	
	public TRiSoaCurrency buildSoaCurrencyObjFromModelForUploading(SoaCurrencyModel soaCurrencyM) throws Exception {
		TRiSoaCurrency soaCurrency = new TRiSoaCurrency();
		soaCurrency.setCurrencyCode(soaCurrencyM.getCurrencyType());
		BeanUtils.copyProperties(soaCurrency, soaCurrencyM);  
		return soaCurrency;		
	}
	
	public TRiSoaSection buildSoaSectionObjFromModelForUploading(SoaSectionModel soaSectionM) throws Exception {
	       TRiSoaSection soaSection = new TRiSoaSection();
			BeanUtils.copyProperties(soaSection, soaSectionM);   
		//	soaSection.setContracCompId(Long.parseLong(soaSectionM.getContracCompId()));
					return soaSection;	
		}
	
	public TRiSoaSectionItem buildSoaSectionItemObjFromModelForUploading(SoaSectionItemModel soaSectionItemM) throws Exception {
		 TRiSoaSectionItem  soaSectionItem  = new TRiSoaSectionItem ();
			BeanUtils.copyProperties(soaSectionItem , soaSectionItemM);   
					return soaSectionItem ;	
	}
	
	public Soa4UpdateVO buildSoaVO(SoaModel soaM) throws Exception {
		Soa4UpdateVO soaVO = new Soa4UpdateVO();
		BeanUtils.copyProperties(soaVO, soaM);
		// build soa currency list
		buildSoaCurrencyVO(soaVO,soaM);
		return soaVO;
	}
	
	public void buildSoaCurrencyVO(Soa4UpdateVO soaVO,SoaModel soaM) throws Exception {
		List<SoaCurrencyModel> soaCurrencyList = soaM.getCurrencies();
		List<SoaCurrency4UpdateVO> soaCurrencyVOList = new ArrayList<SoaCurrency4UpdateVO>();
		SoaCurrencyModel soaCurrencyModel = null;
		for (Object soaCurrencyObj :  soaCurrencyList){
			soaCurrencyModel = (SoaCurrencyModel)soaCurrencyObj;
			SoaCurrency4UpdateVO soaCurrencyVO = new SoaCurrency4UpdateVO();
			BeanUtils.copyProperties(soaCurrencyVO, soaCurrencyModel);
			soaCurrencyVOList.add(soaCurrencyVO);
			//build soa section list
			buildSoaSectionVO(soaCurrencyVO,soaCurrencyModel);
		}
		soaVO.setCurrencies(soaCurrencyVOList);
	}
	

	public void buildSoaSectionVO(SoaCurrency4UpdateVO soaCurrencyVO,SoaCurrencyModel soaCurrencyM) throws Exception {
		List<SoaSectionModel> soaSectionList = soaCurrencyM.getSections();
		List<SoaSection4UpdateVO> soaSectionVOList = new ArrayList<SoaSection4UpdateVO>();
		SoaSectionModel soaSectionModel = null;
		for (Object soaSectionObj :  soaSectionList){
			soaSectionModel = (SoaSectionModel)soaSectionObj;
			SoaSection4UpdateVO soaSectionVO = new SoaSection4UpdateVO();
			BeanUtils.copyProperties(soaSectionVO, soaSectionModel);
			soaSectionVOList.add(soaSectionVO);
			//build soa section item list
			buildSoaSectionItemVO(soaSectionVO,soaSectionModel);
		}
		soaCurrencyVO.setSections(soaSectionVOList);
	}
	
	
	public void buildSoaSectionItemVO(SoaSection4UpdateVO soaSectionVO, SoaSectionModel soaSectionM) throws Exception {
		List<SoaSectionItemModel> soaSectionItemList = soaSectionM.getEntryItems();
		List<SoaSectionItem4UpdateVO> soaSectionItemVOList = new ArrayList<SoaSectionItem4UpdateVO>();
		SoaSectionItemModel soaSectionItemModel = null;
		for (Object soaSectionItemObj : soaSectionItemList) {
			soaSectionItemModel = (SoaSectionItemModel) soaSectionItemObj;
			SoaSectionItem4UpdateVO soaSectionItemVO = new SoaSectionItem4UpdateVO();
			BeanUtils.copyProperties(soaSectionItemVO, soaSectionItemModel);
			soaSectionItemVOList.add(soaSectionItemVO);
		}
		soaSectionVO.setEntryItems(soaSectionItemVOList);;
		
	}
}
