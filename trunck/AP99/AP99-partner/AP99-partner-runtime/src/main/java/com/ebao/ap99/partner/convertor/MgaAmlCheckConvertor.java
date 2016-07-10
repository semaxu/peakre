package com.ebao.ap99.partner.convertor;

import java.util.ArrayList;
import java.util.List;

import com.ebao.ap99.partner.entity.TBrokerAmlCheck;
import com.ebao.ap99.partner.entity.TMgaAmlCheck;
import com.ebao.ap99.partner.entity.TPartner;
import com.ebao.ap99.partner.model.BrokerAmlCheck;
import com.ebao.ap99.partner.model.MgaAmlCheck;
import com.ebao.unicorn.platform.foundation.utils.BeanUtils;

public class MgaAmlCheckConvertor {
    public TMgaAmlCheck modelToEntity(MgaAmlCheck AMLCheck) {
        TMgaAmlCheck entity = new TMgaAmlCheck();
        BeanUtils.copyProperties(entity, AMLCheck);
        return entity;
    }

    public MgaAmlCheck entityToModel(TMgaAmlCheck entity) {
        MgaAmlCheck AMLCheck = new MgaAmlCheck();
        BeanUtils.copyProperties(AMLCheck, entity);
        return AMLCheck;
    }

    public List<TMgaAmlCheck> modelListToEntityList(List<MgaAmlCheck> AMLCheckList) {
        List<TMgaAmlCheck> entityList = new ArrayList<TMgaAmlCheck>();
        for (MgaAmlCheck AMLCheck : AMLCheckList) {
            TMgaAmlCheck entity = modelToEntity(AMLCheck);
            entityList.add(entity);
        }
        return entityList;
    }

    public void convertEntityList(List<MgaAmlCheck> AMLCheckList, TPartner entity) {
        for (MgaAmlCheck AMLCheck : AMLCheckList) {
            TMgaAmlCheck AMLCheckEntity = modelToEntity(AMLCheck);
            entity.addTMgaAmlCheck(AMLCheckEntity);
        }
    }

    public List<MgaAmlCheck> entitylListToModelList(List<TMgaAmlCheck> entityList) {
        List<MgaAmlCheck> AMLCheckList = new ArrayList<MgaAmlCheck>();
        for (TMgaAmlCheck entity : entityList) {
            MgaAmlCheck AMLCheck = entityToModel(entity);
            AMLCheckList.add(AMLCheck);
        }
        return AMLCheckList;
    }
}
