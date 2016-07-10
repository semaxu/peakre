package com.ebao.ap99.partner.service;

import com.ebao.ap99.partner.entity.MessageVO;

public interface CheckAmlService {
	
    public MessageVO amlCheck(String businessPartnerId,String roleId);
    
   
}
