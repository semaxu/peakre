package com.ebao.ap99.partner.convertor;



import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.parent.service.CodeTableDS;
import com.ebao.ap99.parent.service.UserDS;
import com.ebao.ap99.partner.entity.TAmlCheck;
import com.ebao.ap99.partner.model.AmlCheckPDFVO;
import com.ebao.unicorn.platform.foundation.utils.BeanUtils;


public class AmlCheckDS {

    @Autowired
    private CodeTableDS codeTableDS;
    @Autowired
    private UserDS userDS;
    
    public  AmlCheckPDFVO entityToModel(TAmlCheck entity) throws Exception {
        AmlCheckPDFVO AMLCheck = new AmlCheckPDFVO();
        BeanUtils.copyProperties(AMLCheck, entity);
        if(entity.getCountryIncorporation()!=null){
            String country = codeTableDS.getCodeTableDesc(800019L, entity.getCountryIncorporation(), com.ebao.unicorn.platform.foundation.context.AppContext.getCurrentUser().getLangId() );
            AMLCheck.setCountryIncorporation(country);
        }
        if(entity.getCheckBy()!=0L){
            AMLCheck.setCheckBy(userDS.getUserNameByUserId(entity.getCheckBy()));
        }
        if(entity.getSanctionedCountry()!=null){
            AMLCheck.setSanctionedCountry(entity.getSanctionedCountry().equals("1")?"Yes":"No");
        }
        if(entity.getLicensed()!=null){
            AMLCheck.setLicensed(entity.getLicensed().equals("1")?"Yes":"No");
        }
        return AMLCheck;
    }



}
