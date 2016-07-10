package com.ebao.ap99.partner.service;




public interface PartnerAPI {
    public String loadPartnerNameByPartnerCode(String businessPartnerId);
    
    public String getPartnerCategory(String busiPartnerId);
    
    public String getPartnerCountry(String busiPartnerId);
    
    public String checkPartner(String busiPartnerId);
    
}
