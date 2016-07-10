package com.ebao.ap99.partner.service;



import com.ebao.ap99.partner.entity.TAmlCheck;
import com.ebao.ap99.partner.model.Partner;
import com.ebao.ap99.partner.model.PartnerQuery;



public interface AmlService {
	
    public Long createAml(TAmlCheck amlCheck) ;


    public TAmlCheck loadAmlCheckById(Long amlCheckId) ;


    boolean findAmlCheck(Partner partner);


    boolean findDeclinedAmlCheck(Partner partner);
    
    
}
