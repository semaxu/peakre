package com.ebao.ap99.partner.service;

import java.util.List;
import java.util.Map;

import com.ebao.ap99.parent.Page;
import com.ebao.ap99.partner.entity.TPartner;
import com.ebao.ap99.partner.model.ChangeHistoryQuery;
import com.ebao.ap99.partner.model.Partner;
import com.ebao.ap99.partner.model.PartnerQuery;


public interface PartnerService {
	
	public Long createPartner(TPartner partner);
	
	public Long createOrUpdate(TPartner partner);
	
    public TPartner loadPartnerById(Long partnerId);
    
    public TPartner loadPartnerByBusinessPartnerId(String businessPartnerId);
    
	public List<TPartner> loadAllPartner() ;
	
	public List<TPartner> loadAllPartnerByConditions(Map<String, String> equalConditions, Integer start, Integer limit);
	
	public List<TPartner> loadPartnerByConditions(TPartner partner);
      
	public Page<TPartner> findPageList(Page<TPartner> page);
	
	public Page<Partner> findPageListBySql(Partner partner);
	
	public Page<ChangeHistoryQuery> queryChangeHistoryList(ChangeHistoryQuery changeHistory) ;

	public void createPartnerLog(TPartner Partner);
	
	public boolean findAmlCheck(Partner partner);
	
	public String getBusinessPartnerId() ;
	
	public boolean checkBusinessPartnerId(String businessPartnerId);
	
	public void createAmlCheckMail(Partner Partner);
	
	public void createDeclineMail(Partner Partner);
	
	public String loadPartnerNameByPartnerCode(String partnerCode);
}
