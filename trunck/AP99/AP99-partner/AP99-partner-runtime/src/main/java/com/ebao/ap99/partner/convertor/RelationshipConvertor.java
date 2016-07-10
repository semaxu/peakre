package com.ebao.ap99.partner.convertor;

import java.util.ArrayList;
import java.util.List;

import com.ebao.ap99.partner.entity.TContact;
import com.ebao.ap99.partner.entity.TPartner;
import com.ebao.ap99.partner.entity.TRelationship;
import com.ebao.ap99.partner.model.Contact;
import com.ebao.ap99.partner.model.Relationship;
import com.ebao.unicorn.platform.foundation.utils.BeanUtils;

public class RelationshipConvertor {
    public TRelationship modelToEntity(Relationship relationship) {
        TRelationship entity = new TRelationship();
        BeanUtils.copyProperties(entity, relationship);
        return entity;
    }

    public Relationship entityToModel(TRelationship entity) {
        Relationship relationship = new Relationship();
        BeanUtils.copyProperties(relationship, entity);
        return relationship;
    }

    public List<TRelationship> modelListToEntityList(List<Relationship> relationshipList) {
        List<TRelationship> entityList = new ArrayList<TRelationship>();
        for (Relationship relationship : relationshipList) {
            TRelationship entity = modelToEntity(relationship);
            entityList.add(entity);
        }
        return entityList;
    }

    public void convertEntityList(List<Relationship> relationshipList, TPartner entity) {
        for (Relationship relationship : relationshipList) {
            TRelationship relationshipEntity = modelToEntity(relationship);
            entity.addTRelationship(relationshipEntity);
        }
    }

    public List<Relationship> entitylListToModelList(List<TRelationship> entityList) {
        List<Relationship> relationshipList = new ArrayList<Relationship>();
        for (TRelationship entity : entityList) {
            Relationship relationship = entityToModel(entity);
            relationshipList.add(relationship);
        }
        return relationshipList;
    }
}
