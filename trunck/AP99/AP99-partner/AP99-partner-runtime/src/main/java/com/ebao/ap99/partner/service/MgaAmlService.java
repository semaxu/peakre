package com.ebao.ap99.partner.service;


import com.ebao.ap99.partner.entity.TMgaAmlCheck;
import com.ebao.ap99.partner.model.Partner;



public interface MgaAmlService {
	
    public Long createAml(TMgaAmlCheck amlCheck) ;


    public TMgaAmlCheck loadMgaAmlCheckById(Long amlCheckId) ;


    boolean findAmlCheck(Partner partner);


    boolean findDeclinedAmlCheck(Partner partner);
    
    
}
