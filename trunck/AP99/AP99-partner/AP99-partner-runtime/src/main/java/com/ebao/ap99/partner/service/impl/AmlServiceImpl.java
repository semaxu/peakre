package com.ebao.ap99.partner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.file.model.ItemVO;
import com.ebao.ap99.file.service.PrintBizService;
import com.ebao.ap99.partner.convertor.AmlCheckDS;
import com.ebao.ap99.partner.dao.AmlCheckDao;
import com.ebao.ap99.partner.entity.TAmlCheck;
import com.ebao.ap99.partner.model.AmlCheckPDFVO;
import com.ebao.ap99.partner.model.AmlCheckPDFVOList;
import com.ebao.ap99.partner.model.Partner;
import com.ebao.ap99.partner.model.PartnerQuery;
import com.ebao.ap99.partner.service.AmlService;


public class AmlServiceImpl implements AmlService ,PrintBizService{

	@Autowired
	private AmlCheckDao amlCheckDao;
    @Autowired
    public AmlCheckDS amlCheckDS;

	@Override
	public Long createAml(TAmlCheck amlCheck) {
	    if(amlCheck.getAmlCheckId() == 0){
	        amlCheckDao.insert(amlCheck);     
	    }else {
	        amlCheckDao.merge(amlCheck);
	    }
	   
		return amlCheck.getAmlCheckId();
	}


	@Override
	public TAmlCheck loadAmlCheckById(Long amlCheckId) {
		return amlCheckDao.load(amlCheckId);
	}


    @Override
    public ItemVO loadBusinessVO(ItemVO itemVO) throws Exception {
        Long amlCheckId = 0L;
        if (itemVO.getParam() != null) {
            amlCheckId = Long.parseLong((String) itemVO.getParam().get("AmlCheckId"));
        } else {
            return itemVO;
        }

        TAmlCheck amlCheck = amlCheckDao.load(amlCheckId);
        AmlCheckPDFVO amlCheckPDFVO = amlCheckDS.entityToModel(amlCheck);
        AmlCheckPDFVOList amlCheckPDFVOList = new AmlCheckPDFVOList();
        amlCheckPDFVOList.addNewObject(amlCheckPDFVO);
        itemVO.setBizVO(amlCheckPDFVOList);
        return itemVO;
    }
    
    @Override
    public boolean findAmlCheck(Partner partner){
        boolean checkFlag = true;
     //   checkFlag = amlCheckDao.findAmlCheck(partner);
        return checkFlag;
    }
    
    
    @Override
    public boolean findDeclinedAmlCheck(Partner partner){
        boolean checkFlag = true;
        checkFlag = amlCheckDao.findDeclinedAmlCheck(partner);
        return checkFlag;
    }
    
    }
	
	
	

