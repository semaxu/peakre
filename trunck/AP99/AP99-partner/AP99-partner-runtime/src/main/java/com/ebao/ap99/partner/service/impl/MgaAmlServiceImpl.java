package com.ebao.ap99.partner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.file.model.ItemVO;
import com.ebao.ap99.file.service.PrintBizService;
import com.ebao.ap99.partner.convertor.MgaAmlCheckDS;
import com.ebao.ap99.partner.convertor.RetroAmlCheckDS;
import com.ebao.ap99.partner.dao.MgaAmlCheckDao;
import com.ebao.ap99.partner.entity.TMgaAmlCheck;
import com.ebao.ap99.partner.model.AmlCheckPDFVOList;
import com.ebao.ap99.partner.model.MgaAmlCheckPDFVO;
import com.ebao.ap99.partner.model.MgaAmlCheckPDFVOList;
import com.ebao.ap99.partner.model.Partner;
import com.ebao.ap99.partner.service.MgaAmlService;


public class MgaAmlServiceImpl implements MgaAmlService,PrintBizService{

	@Autowired
	private MgaAmlCheckDao amlCheckDao;
    @Autowired
    public MgaAmlCheckDS mgaAmlCheckDS;

	@Override
	public Long createAml(TMgaAmlCheck amlCheck) {
	    if(amlCheck.getAmlCheckId() == 0){
	        amlCheckDao.insert(amlCheck);     
	    }else {
	        amlCheckDao.merge(amlCheck);
	    }
	   
		return amlCheck.getAmlCheckId();
	}


	@Override
	public TMgaAmlCheck loadMgaAmlCheckById(Long amlCheckId) {
		return amlCheckDao.load(amlCheckId);
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
    @Override
    public ItemVO loadBusinessVO(ItemVO itemVO) throws Exception {
        Long amlCheckId = 0L;
        if (itemVO.getParam() != null) {
            amlCheckId = Long.parseLong((String) itemVO.getParam().get("AmlCheckId"));
        } else {
            return itemVO;
        }

        TMgaAmlCheck amlCheck = amlCheckDao.load(amlCheckId);
        MgaAmlCheckPDFVO amlCheckPDFVO = mgaAmlCheckDS.entityToModel(amlCheck);
        MgaAmlCheckPDFVOList amlCheckPDFVOList = new MgaAmlCheckPDFVOList();
        amlCheckPDFVOList.addNewObject(amlCheckPDFVO);
        itemVO.setBizVO(amlCheckPDFVOList);
        return itemVO;
    }
}
