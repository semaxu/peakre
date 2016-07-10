package com.ebao.ap99.partner.convertor;



import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.parent.service.CodeTableDS;
import com.ebao.ap99.parent.service.UserDS;
import com.ebao.ap99.partner.entity.TRetroAmlCheck;
import com.ebao.ap99.partner.model.RetroAmlCheckPDFVO;
import com.ebao.unicorn.platform.foundation.utils.BeanUtils;

public class RetroAmlCheckDS {

    @Autowired
    private CodeTableDS codeTableDS;
    @Autowired
    private UserDS userDS;
    public  RetroAmlCheckPDFVO entityToModel(TRetroAmlCheck entity) throws Exception {
        RetroAmlCheckPDFVO AMLCheck = new RetroAmlCheckPDFVO();
        BeanUtils.copyProperties(AMLCheck, entity);
        if(entity.getCountryIncorporation()!=null){
            String country = codeTableDS.getCodeTableDesc(800019L, entity.getCountryIncorporation(), com.ebao.unicorn.platform.foundation.context.AppContext.getCurrentUser().getLangId() );
            AMLCheck.setCountryIncorporation(country);
        }
        if(entity.getCheckBy()!=0L){
            AMLCheck.setCheckBy(userDS.getUserNameByUserId(entity.getCheckBy()));
        }
        if(entity.getSanctionedCountry()!=null){
            AMLCheck.setSanctionedCountry(entity.getSanctionedCountry().equals("0")?"Yes":"No");
        }
        if(entity.getLicensed()!=null){
            AMLCheck.setLicensed(entity.getLicensed().equals("0")?"Yes":"No");
        }
        return AMLCheck;
    }



}
