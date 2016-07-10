package com.ebao.ap99.partner.convertor;

import java.util.ArrayList;
import java.util.List;

import com.ebao.ap99.partner.entity.TContact;
import com.ebao.ap99.partner.entity.TPartner;
import com.ebao.ap99.partner.model.Contact;
import com.ebao.unicorn.platform.foundation.utils.BeanUtils;

public class ContactConvertor {
    public TContact modelToEntity(Contact contact) {
        TContact entity = new TContact();
        BeanUtils.copyProperties(entity, contact);
        return entity;
    }

    public Contact entityToModel(TContact entity) {
        Contact contact = new Contact();
        BeanUtils.copyProperties(contact, entity);
        return contact;
    }

    public List<TContact> modelListToEntityList(List<Contact> contactList) {
        List<TContact> entityList = new ArrayList<TContact>();
        for (Contact contact : contactList) {
            TContact entity = modelToEntity(contact);
            entityList.add(entity);
        }
        return entityList;
    }

    public void convertEntityList(List<Contact> contactList, TPartner entity) {
        for (Contact contact : contactList) {
            TContact contactEntity = modelToEntity(contact);
            entity.addTContact(contactEntity);
        }
    }

    public List<Contact> entitylListToModelList(List<TContact> entityList) {
        List<Contact> contactList = new ArrayList<Contact>();
        for (TContact entity : entityList) {
            Contact contact = entityToModel(entity);
            contactList.add(contact);
        }
        return contactList;
    }
}
