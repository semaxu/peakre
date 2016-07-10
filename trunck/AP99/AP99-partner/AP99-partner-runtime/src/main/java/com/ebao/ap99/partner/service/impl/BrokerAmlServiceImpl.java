package com.ebao.ap99.partner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.file.model.ItemVO;
import com.ebao.ap99.file.service.PrintBizService;
import com.ebao.ap99.partner.convertor.BrokerAmlCheckConvertor;
import com.ebao.ap99.partner.convertor.BrokerAmlCheckDS;
import com.ebao.ap99.partner.dao.BrokerAmlCheckDao;
import com.ebao.ap99.partner.entity.TBrokerAmlCheck;
import com.ebao.ap99.partner.model.BrokerAmlCheckPDFVO;
import com.ebao.ap99.partner.model.BrokerAmlCheckPDFVOList;
import com.ebao.ap99.partner.model.Partner;
import com.ebao.ap99.partner.service.BrokerAmlService;


public class BrokerAmlServiceImpl implements BrokerAmlService,PrintBizService{

	@Autowired
	private BrokerAmlCheckDao amlCheckDao;
    @Autowired
    public BrokerAmlCheckDS brokerAmlCheckDS;

	@Override
	public Long createAml(TBrokerAmlCheck amlCheck) {
	    if(amlCheck.getAmlCheckId() == 0){
	        amlCheckDao.insert(amlCheck);     
	    }else {
	        amlCheckDao.merge(amlCheck);
	    }
	   
		return amlCheck.getAmlCheckId();
	}


	@Override
	public TBrokerAmlCheck loadBrokerAmlCheckById(Long amlCheckId) {
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

        TBrokerAmlCheck amlCheck = amlCheckDao.load(amlCheckId);
        BrokerAmlCheckPDFVO amlCheckPDFVO = brokerAmlCheckDS.entityToModel(amlCheck);
        BrokerAmlCheckPDFVOList amlCheckPDFVOList = new BrokerAmlCheckPDFVOList();
        amlCheckPDFVOList.addNewObject(amlCheckPDFVO);
        itemVO.setBizVO(amlCheckPDFVOList);
        return itemVO;
    }
    
    @Override
    public boolean findAmlCheck(Partner partner){
        boolean checkFlag = true;
   //     checkFlag = amlCheckDao.findAmlCheck(partner);
        return checkFlag;
    }
    
    
    @Override
    public boolean findDeclinedAmlCheck(Partner partner){
        boolean checkFlag = true;
        checkFlag = amlCheckDao.findDeclinedAmlCheck(partner);
        return checkFlag;
    }
	
}
