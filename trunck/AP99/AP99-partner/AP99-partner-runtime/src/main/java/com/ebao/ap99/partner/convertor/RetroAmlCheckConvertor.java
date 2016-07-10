package com.ebao.ap99.partner.convertor;

import java.util.ArrayList;
import java.util.List;

import com.ebao.ap99.partner.entity.TAmlCheck;
import com.ebao.ap99.partner.entity.TPartner;
import com.ebao.ap99.partner.entity.TRetroAmlCheck;
import com.ebao.ap99.partner.model.AmlCheck;
import com.ebao.ap99.partner.model.RetroAmlCheck;
import com.ebao.unicorn.platform.foundation.utils.BeanUtils;

public class RetroAmlCheckConvertor {
    public TRetroAmlCheck modelToEntity(RetroAmlCheck RetroAMLCheck) {
        TRetroAmlCheck entity = new TRetroAmlCheck();
        BeanUtils.copyProperties(entity, RetroAMLCheck);
        return entity;
    }

    public RetroAmlCheck entityToModel(TRetroAmlCheck entity) {
        RetroAmlCheck RetroAMLCheck = new RetroAmlCheck();
        BeanUtils.copyProperties(RetroAMLCheck, entity);
        return RetroAMLCheck;
    }

    public List<TRetroAmlCheck> modelListToEntityList(List<RetroAmlCheck> RetroAMLCheckList) {
        List<TRetroAmlCheck> entityList = new ArrayList<TRetroAmlCheck>();
        for (RetroAmlCheck RetroAMLCheck : RetroAMLCheckList) {
            TRetroAmlCheck entity = modelToEntity(RetroAMLCheck);
            entityList.add(entity);
        }
        return entityList;
    }

    public void convertEntityList(List<RetroAmlCheck> RetroAMLCheckList, TPartner entity) {
        for (RetroAmlCheck RetroAMLCheck : RetroAMLCheckList) {
            TRetroAmlCheck RetroAMLCheckEntity = modelToEntity(RetroAMLCheck);
            entity.addTRetroAmlCheck(RetroAMLCheckEntity);
        }
    }

    public List<RetroAmlCheck> entitylListToModelList(List<TRetroAmlCheck> entityList) {
        List<RetroAmlCheck> RetroAMLCheckList = new ArrayList<RetroAmlCheck>();
        for (TRetroAmlCheck entity : entityList) {
            RetroAmlCheck RetroAMLCheck = entityToModel(entity);
            RetroAMLCheckList.add(RetroAMLCheck);
        }
        return RetroAMLCheckList;
    }
}
