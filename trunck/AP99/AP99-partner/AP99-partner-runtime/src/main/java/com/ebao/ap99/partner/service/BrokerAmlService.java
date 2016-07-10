package com.ebao.ap99.partner.service;


import com.ebao.ap99.partner.entity.TBrokerAmlCheck;
import com.ebao.ap99.partner.model.Partner;



public interface BrokerAmlService {
	
    public Long createAml(TBrokerAmlCheck amlCheck) ;


    public TBrokerAmlCheck loadBrokerAmlCheckById(Long amlCheckId) ;


    boolean findAmlCheck(Partner partner);


    boolean findDeclinedAmlCheck(Partner partner);
    
    
}
