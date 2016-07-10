package com.ebao.ap99.partner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.file.model.ItemVO;
import com.ebao.ap99.file.service.PrintBizService;
import com.ebao.ap99.partner.convertor.RetroAmlCheckDS;
import com.ebao.ap99.partner.dao.RetroAmlCheckDao;
import com.ebao.ap99.partner.entity.TRetroAmlCheck;
import com.ebao.ap99.partner.model.Partner;
import com.ebao.ap99.partner.model.RetroAmlCheckPDFVO;
import com.ebao.ap99.partner.model.RetroAmlCheckPDFVOList;
import com.ebao.ap99.partner.service.RetroAmlService;


public class RetroAmlServiceImpl implements RetroAmlService,PrintBizService{

	@Autowired
	private RetroAmlCheckDao amlCheckDao;
    @Autowired
    public RetroAmlCheckDS retroAmlCheckDS;
	@Override
	public Long createAml(TRetroAmlCheck amlCheck) {
	    if(amlCheck.getAmlCheckId() == 0){
	        amlCheckDao.insert(amlCheck);     
	    }else {
	        amlCheckDao.merge(amlCheck);
	    }
	   
		return amlCheck.getAmlCheckId();
	}


	@Override
	public TRetroAmlCheck loadAmlCheckById(Long amlCheckId) {
		return amlCheckDao.load(amlCheckId);
	}
	
    @Override
    public boolean findAmlCheck(Partner partner){
        boolean checkFlag = true;
      //  checkFlag = amlCheckDao.findAmlCheck(partner);
        return checkFlag;
    }
    
    
    @Override
    public boolean findDeclinedAmlCheck(Partner partner){
        boolean checkFlag = true;
        checkFlag = amlCheckDao.findDeclinedAmlCheck(partner);
        return checkFlag;
    }
    
    @Override
    public ItemVO loadBusinessVO(ItemVO itemVO) throws Exception {
        Long amlCheckId = 0L;
        if (itemVO.getParam() != null) {
            amlCheckId = Long.parseLong((String) itemVO.getParam().get("AmlCheckId"));
        } else {
            return itemVO;
        }

        TRetroAmlCheck amlCheck = amlCheckDao.load(amlCheckId);
        RetroAmlCheckPDFVO amlCheckPDFVO = retroAmlCheckDS.entityToModel(amlCheck);
        RetroAmlCheckPDFVOList amlCheckPDFVOList = new RetroAmlCheckPDFVOList();
        amlCheckPDFVOList.addNewObject(amlCheckPDFVO);
        itemVO.setBizVO(amlCheckPDFVOList);
        return itemVO;
    }
}
