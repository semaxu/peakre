package com.ebao.ap99.partner.convertor;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.partner.entity.TPartner;
import com.ebao.ap99.partner.model.AmlCheck;
import com.ebao.ap99.partner.model.BankAccount;
import com.ebao.ap99.partner.model.BrokerAmlCheck;
import com.ebao.ap99.partner.model.Contact;
import com.ebao.ap99.partner.model.MgaAmlCheck;
import com.ebao.ap99.partner.model.Partner;
import com.ebao.ap99.partner.model.Relationship;
import com.ebao.ap99.partner.model.RetroAmlCheck;
import com.ebao.unicorn.platform.foundation.utils.BeanUtils;

public class PartnerConvertor {
    private FastDateFormat   DATETIME_FORMAT = FastDateFormat.getInstance("yyy/MM/dd hh:mm:ss");
    private SimpleDateFormat formatter       = new SimpleDateFormat("yyy/MM/dd hh:mm:ss");

    @Autowired
    private ContactConvertor     contactConvertor;
    @Autowired
    private BankAccountConvertor bankAccountConvertor;
    @Autowired
    private AmlCheckConvertor    amlCheckConvertor;
    @Autowired
    private BrokerAmlCheckConvertor    brokerAmlCheckConvertor;
    @Autowired
    private RetroAmlCheckConvertor    retroAmlCheckConvertor;
    @Autowired
    private MgaAmlCheckConvertor    mgaAmlCheckConvertor;
    @Autowired
    private RelationshipConvertor  relationshipConvertor;

    
    

    public TPartner modelToEntity(Partner partner) {

        TPartner tPartner = new TPartner();
        BeanUtils.copyProperties(tPartner, partner);
       /* 	try {
        	if(StringUtils.isNotBlank(partner.getCreateDate()))    
        	tPartner.setCreateDate(formatter.parse(partner.getCreateDate()));
        	if(StringUtils.isNotBlank(partner.getDateOfBirth()))    
        	tPartner.setDateOfBirth(formatter.parse(partner.getDateOfBirth()));
        	if(StringUtils.isNotBlank(partner.getDateOfRegistration()))    
        	tPartner.setDateOfRegistration(formatter.parse(partner.getDateOfRegistration()));
        	if(StringUtils.isNotBlank(partner.getDuedateOfRegistration()))    
         	tPartner.setDuedateOfRegistration(formatter.parse(partner.getDuedateOfRegistration()));
        	if(StringUtils.isNotBlank(partner.getDuedateOfIdCertification()))    
        	tPartner.setDuedateOfIdCertification(formatter.parse(partner.getDuedateOfIdCertification()));
        
         } catch (ParseException e) { // TODO Auto-generated catch block
          e.printStackTrace(); }*/
         

        return tPartner;
    }

    public Partner entityToModel(TPartner tPartner) {
        Partner partner = new Partner();

        BeanUtils.copyProperties(partner, tPartner);
       /* if(tPartner.getCreateDate()!=null)
        partner.setCreateDate(DateFormatUtils.format(tPartner.getCreateDate(), DATETIME_FORMAT.getPattern()));
        if(tPartner.getDateOfBirth()!=null)
        partner.setDateOfBirth(DateFormatUtils.format(tPartner.getDateOfBirth(), DATETIME_FORMAT.getPattern()));
        if(tPartner.getDateOfRegistration()!=null)
        partner.setDateOfRegistration(DateFormatUtils.format(tPartner.getDateOfRegistration(),DATETIME_FORMAT.getPattern())); 
        if(tPartner.getDuedateOfRegistration()!=null)
        partner.setDuedateOfRegistration(DateFormatUtils.format(tPartner.getDuedateOfRegistration(), DATETIME_FORMAT.getPattern())); 
        if(tPartner.getDuedateOfIdCertification()!=null)
        partner.setDuedateOfIdCertification(DateFormatUtils.format(tPartner.getDuedateOfIdCertification(),DATETIME_FORMAT.getPattern()));*/
     
        return partner;
    }

    public TPartner modelToEntityCascade(Partner partner) {
        TPartner partnerEntity = modelToEntity(partner);
        if(partner.getContactTable()!=null)
        contactConvertor.convertEntityList(partner.getContactTable(), partnerEntity);
        if(partner.getBankAccountTable()!=null)
        bankAccountConvertor.convertEntityList(partner.getBankAccountTable(), partnerEntity);
        if(partner.getAmlCheckTable()!=null)
        amlCheckConvertor.convertEntityList(partner.getAmlCheckTable(), partnerEntity);
        if(partner.getBrokerAmlCheckTable()!=null)
        brokerAmlCheckConvertor.convertEntityList(partner.getBrokerAmlCheckTable(), partnerEntity);
        if(partner.getRetroAmlCheckTable()!=null)
        retroAmlCheckConvertor.convertEntityList(partner.getRetroAmlCheckTable(), partnerEntity);
        if(partner.getMgaAmlCheckTable()!=null)
        mgaAmlCheckConvertor.convertEntityList(partner.getMgaAmlCheckTable(), partnerEntity);
        if(partner.getRelationshipTable()!=null)
        relationshipConvertor.convertEntityList(partner.getRelationshipTable(), partnerEntity);
       
        
        return partnerEntity;
    }

    public Partner entityToModelCascade(TPartner partnerEntity) {
        Partner partner = entityToModel(partnerEntity);
        if(partnerEntity.getTContacts()!=null){
            List<Contact> contactList = contactConvertor.entitylListToModelList(partnerEntity.getTContacts());    
            partner.setContactTable(contactList);
        }
        if(partnerEntity.getTBankAccounts()!=null){
            List<BankAccount> bankAccountList = bankAccountConvertor.entitylListToModelList(partnerEntity.getTBankAccounts());
            partner.setBankAccountTable(bankAccountList);
        }
        if(partnerEntity.getTAmlChecks()!=null){
            List<AmlCheck> amlCheckList = amlCheckConvertor.entitylListToModelList(partnerEntity.getTAmlChecks());
            partner.setAmlCheckTable(amlCheckList);
        }
        if(partnerEntity.getTBrokerAmlChecks()!=null){
            List<BrokerAmlCheck> brokerAmlCheckList = brokerAmlCheckConvertor.entitylListToModelList(partnerEntity.getTBrokerAmlChecks());
            partner.setBrokerAmlCheckTable(brokerAmlCheckList);
        }
        if(partnerEntity.getTRetroAmlChecks()!=null){
            List<RetroAmlCheck> amlCheckList = retroAmlCheckConvertor.entitylListToModelList(partnerEntity.getTRetroAmlChecks());
            partner.setRetroAmlCheckTable(amlCheckList);
        }
        if(partnerEntity.getTMgaAmlChecks()!=null){
            List<MgaAmlCheck> mgaAmlCheckList = mgaAmlCheckConvertor.entitylListToModelList(partnerEntity.getTMgaAmlChecks());
            partner.setMgaAmlCheckTable(mgaAmlCheckList);
        }
        if(partnerEntity.getTRelationships()!=null){
            List<Relationship> relationshipList = relationshipConvertor.entitylListToModelList(partnerEntity.getTRelationships());
            partner.setRelationshipTable(relationshipList);
        }
        return partner;
    }

}
