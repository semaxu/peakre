package com.ebao.ap99.partner.service;



import com.ebao.ap99.partner.entity.TRetroAmlCheck;
import com.ebao.ap99.partner.model.Partner;



public interface RetroAmlService {
	
    public Long createAml(TRetroAmlCheck amlCheck) ;


    public TRetroAmlCheck loadAmlCheckById(Long amlCheckId) ;


    boolean findAmlCheck(Partner partner);


    boolean findDeclinedAmlCheck(Partner partner);
    
    
}
