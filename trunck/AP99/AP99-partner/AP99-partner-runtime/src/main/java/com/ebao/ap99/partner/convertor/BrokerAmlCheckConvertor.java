package com.ebao.ap99.partner.convertor;

import java.util.ArrayList;
import java.util.List;

import com.ebao.ap99.partner.entity.TBrokerAmlCheck;
import com.ebao.ap99.partner.entity.TPartner;
import com.ebao.ap99.partner.model.BrokerAmlCheck;
import com.ebao.unicorn.platform.foundation.utils.BeanUtils;

public class BrokerAmlCheckConvertor {
    public TBrokerAmlCheck modelToEntity(BrokerAmlCheck AMLCheck) {
        TBrokerAmlCheck entity = new TBrokerAmlCheck();
        BeanUtils.copyProperties(entity, AMLCheck);
        return entity;
    }

    public BrokerAmlCheck entityToModel(TBrokerAmlCheck entity) {
        BrokerAmlCheck AMLCheck = new BrokerAmlCheck();
        BeanUtils.copyProperties(AMLCheck, entity);
        return AMLCheck;
    }

    public List<TBrokerAmlCheck> modelListToEntityList(List<BrokerAmlCheck> AMLCheckList) {
        List<TBrokerAmlCheck> entityList = new ArrayList<TBrokerAmlCheck>();
        for (BrokerAmlCheck AMLCheck : AMLCheckList) {
            TBrokerAmlCheck entity = modelToEntity(AMLCheck);
            entityList.add(entity);
        }
        return entityList;
    }

    public void convertEntityList(List<BrokerAmlCheck> AMLCheckList, TPartner entity) {
        for (BrokerAmlCheck AMLCheck : AMLCheckList) {
            TBrokerAmlCheck AMLCheckEntity = modelToEntity(AMLCheck);
            entity.addTBrokerAmlCheck(AMLCheckEntity);
        }
    }

    public List<BrokerAmlCheck> entitylListToModelList(List<TBrokerAmlCheck> entityList) {
        List<BrokerAmlCheck> AMLCheckList = new ArrayList<BrokerAmlCheck>();
        for (TBrokerAmlCheck entity : entityList) {
            BrokerAmlCheck AMLCheck = entityToModel(entity);
            AMLCheckList.add(AMLCheck);
        }
        return AMLCheckList;
    }
}
