package com.ebao.ap99.partner.convertor;



import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.parent.service.CodeTableDS;
import com.ebao.ap99.parent.service.UserDS;
import com.ebao.ap99.partner.entity.TBrokerAmlCheck;
import com.ebao.ap99.partner.model.BrokerAmlCheckPDFVO;
import com.ebao.unicorn.platform.foundation.utils.BeanUtils;

public class BrokerAmlCheckDS {
    @Autowired
    private CodeTableDS codeTableDS;
    @Autowired
    private UserDS userDS;
    public  BrokerAmlCheckPDFVO entityToModel(TBrokerAmlCheck entity) throws Exception {
        BrokerAmlCheckPDFVO AMLCheck = new BrokerAmlCheckPDFVO();
        BeanUtils.copyProperties(AMLCheck, entity);
        if(entity.getCountryIncorporation()!=null){
            String country = codeTableDS.getCodeTableDesc(800019L, entity.getCountryIncorporation(), com.ebao.unicorn.platform.foundation.context.AppContext.getCurrentUser().getLangId() );
            AMLCheck.setCountryIncorporation(country);
        }
        if(entity.getCheckBy()!=0L){
            AMLCheck.setCheckBy(userDS.getUserNameByUserId(entity.getCheckBy()));
        }
        if(entity!=null){
            AMLCheck.setSanctionedCountry(entity.getSanctionedCountry().equals("1")?"Yes":"No");
        }
        if(entity.getInsuranceBody()!=null){
            AMLCheck.setInsuranceBody(entity.getInsuranceBody().equals("1")?"Yes":"No");
        }
        if(entity.getIsManagementTerrorist()!=null){
            AMLCheck.setIsManagementTerrorist(entity.getIsManagementTerrorist().equals("1")?"Yes":"No");
        }
        if(entity.getIsDirectorTerrorist()!=null){
            AMLCheck.setIsDirectorTerrorist(entity.getIsDirectorTerrorist().equals("1")?"Yes":"No");
        }
        if(entity.getIsOwnerTerrorist()!=null){
            AMLCheck.setIsOwnerTerrorist(entity.getIsOwnerTerrorist().equals("1")?"Yes":"No");
        }
        if(entity.getIsBrokerSanctioned()!=null){
            AMLCheck.setIsBrokerSanctioned(entity.getIsBrokerSanctioned().equals("1")?"Yes":"No");
        }
        if(entity.getBrokerAssociation()!=null){
            AMLCheck.setBrokerAssociation(entity.getLloydBroker().equals("1")?"Yes":"No");
        }
        if(entity.getLloydBroker()!=null){
            AMLCheck.setLloydBroker(entity.getLloydBroker().equals("1")?"Yes":"No");
        }
        return AMLCheck;
    }



}
