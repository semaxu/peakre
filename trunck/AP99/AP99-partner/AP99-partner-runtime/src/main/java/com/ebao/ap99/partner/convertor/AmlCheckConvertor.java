package com.ebao.ap99.partner.convertor;

import java.util.ArrayList;
import java.util.List;

import com.ebao.ap99.partner.entity.TAmlCheck;
import com.ebao.ap99.partner.entity.TPartner;
import com.ebao.ap99.partner.model.AmlCheck;
import com.ebao.unicorn.platform.foundation.utils.BeanUtils;

public class AmlCheckConvertor {
    public TAmlCheck modelToEntity(AmlCheck AMLCheck) {
        TAmlCheck entity = new TAmlCheck();
        BeanUtils.copyProperties(entity, AMLCheck);
        return entity;
    }

    public AmlCheck entityToModel(TAmlCheck entity) {
        AmlCheck AMLCheck = new AmlCheck();
        BeanUtils.copyProperties(AMLCheck, entity);
        return AMLCheck;
    }

    public List<TAmlCheck> modelListToEntityList(List<AmlCheck> AMLCheckList) {
        List<TAmlCheck> entityList = new ArrayList<TAmlCheck>();
        for (AmlCheck AMLCheck : AMLCheckList) {
            TAmlCheck entity = modelToEntity(AMLCheck);
            entityList.add(entity);
        }
        return entityList;
    }

    public void convertEntityList(List<AmlCheck> AMLCheckList, TPartner entity) {
        for (AmlCheck AMLCheck : AMLCheckList) {
            TAmlCheck AMLCheckEntity = modelToEntity(AMLCheck);
            entity.addTAmlCheck(AMLCheckEntity);
        }
    }

    public List<AmlCheck> entitylListToModelList(List<TAmlCheck> entityList) {
        List<AmlCheck> AMLCheckList = new ArrayList<AmlCheck>();
        for (TAmlCheck entity : entityList) {
            AmlCheck AMLCheck = entityToModel(entity);
            AMLCheckList.add(AMLCheck);
        }
        return AMLCheckList;
    }
}
