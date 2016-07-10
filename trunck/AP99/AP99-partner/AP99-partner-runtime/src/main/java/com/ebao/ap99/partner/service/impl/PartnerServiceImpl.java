package com.ebao.ap99.partner.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.ebao.ap99.mail.MailService;
import com.ebao.ap99.mail.MailVO;
import com.ebao.ap99.parent.Page;
import com.ebao.ap99.parent.PaginationHelper;
import com.ebao.ap99.parent.constant.NumberingType;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.ap99.partner.constant.Constants;
import com.ebao.ap99.partner.dao.AmlCheckDao;
import com.ebao.ap99.partner.dao.BankAccountDao;
import com.ebao.ap99.partner.dao.BrokerAmlCheckDao;
import com.ebao.ap99.partner.dao.ChangeHistoryDao;
import com.ebao.ap99.partner.dao.ContactDao;
import com.ebao.ap99.partner.dao.MgaAmlCheckDao;
import com.ebao.ap99.partner.dao.PartnerDao;
import com.ebao.ap99.partner.dao.PartnerLogDao;
import com.ebao.ap99.partner.dao.QuerySqlChangeHistory;
import com.ebao.ap99.partner.dao.QuerySqlTemplate;
import com.ebao.ap99.partner.dao.RelationshipDao;
import com.ebao.ap99.partner.dao.RetroAmlCheckDao;
import com.ebao.ap99.partner.entity.MessageVO;
import com.ebao.ap99.partner.entity.TAmlCheck;
import com.ebao.ap99.partner.entity.TBankAccount;
import com.ebao.ap99.partner.entity.TChangeHistory;
import com.ebao.ap99.partner.entity.TContact;
import com.ebao.ap99.partner.entity.TPartner;
import com.ebao.ap99.partner.entity.TPartnerLog;
import com.ebao.ap99.partner.entity.TRelationship;
import com.ebao.ap99.partner.model.ChangeHistoryQuery;
import com.ebao.ap99.partner.model.Partner;
import com.ebao.ap99.partner.service.CheckAmlService;
import com.ebao.ap99.partner.service.PartnerAPI;
import com.ebao.ap99.partner.service.PartnerService;
import com.ebao.unicorn.platform.foundation.cfg.Env;
import com.ebao.unicorn.platform.foundation.numbering.NumberingService;
import com.ebao.unicorn.platform.foundation.security.entity.User;
import com.ebao.unicorn.platform.foundation.utils.BeanUtils;
import com.ebao.unicorn.platform.urpmgmt.service.UserService;


public class PartnerServiceImpl  implements PartnerService,CheckAmlService,PartnerAPI{
    public static final  String  Status1 = "Valid";
    public static final  String  Status2 = "Invalid";
    
    
	@Autowired
	private PartnerDao partnerDao;
	@Autowired
	private ContactDao contactDao;
	@Autowired
	private BankAccountDao bankAccountDao;
	@Autowired
	private AmlCheckDao amlCheckDao;
	@Autowired
	private RetroAmlCheckDao retroAmlCheckDao;
	@Autowired
	private BrokerAmlCheckDao brokerAmlCheckDao;
	@Autowired
	private MgaAmlCheckDao mgaAmlCheckDao;
	@Autowired
    private RelationshipDao relationshipDao;
	@Autowired
	private PartnerLogDao partnerLogDao;
    @Autowired
    private ChangeHistoryDao changeHistoryDao;
    @Autowired
    private NumberingService numService;
    @Autowired
    private UserService userService;
	@Override
	public Long createPartner(TPartner partner) {
		partnerDao.insert(partner);
		return partner.getPartnerId();
	}


	@Override
	public TPartner loadPartnerById(Long partnerId) {
		return partnerDao.load(partnerId);
	}
	
	@Override
	public TPartner loadPartnerByBusinessPartnerId(String businessPartnerId) {
	        return partnerDao.loadByBusinessPartnerId(businessPartnerId);
	}
	@Override
	public List<TPartner> loadAllPartner() {
		return partnerDao.loadAll();
	}


	@Override
	public List<TPartner> loadAllPartnerByConditions(Map<String, String> equalConditions, Integer start, Integer limit) {
		return partnerDao.loadAllByConditions(equalConditions, start, limit);
	}
	
	@Override
	public List<TPartner> loadPartnerByConditions(TPartner partner) {
		return partnerDao.findListByConditions(partner);
	}


    @Override
    public Page<TPartner> findPageList(Page<TPartner> page) {
    	TPartner partner = page.getCondition();
        Long results = partnerDao.countForList(partner);
        //page.setResults(results);
        List<TPartner> list = partnerDao.findPageByConditions(partner, page.getStart(), page.getCountPerPage());
        page.setRows(list);
        int pageIndex = page.getStart() / page.getCountPerPage() + 1;
        page.setPageIndex(pageIndex);
        Long pageCount = results % page.getCountPerPage() == 0 ? results / page.getCountPerPage()
                : results / page.getCountPerPage() + 1;
        page.setPageCount(pageCount);
        if (results < page.getCountPerPage()) {
            page.setCountPerPage(results.intValue());
        }
        return page;
    }


    @Override
    public Long createOrUpdate(TPartner partner) {
        if(partner.getPartnerId() == 0L){
            partnerDao.insert(partner);
        }else {
          
            List <TContact> tContacts = partner.getTContacts();
            List <TBankAccount> tBankAccounts = partner.getTBankAccounts();
            List <TAmlCheck> tAmlChecks = partner.getTAmlChecks();
            List <TRelationship> tRelationships = partner.getTRelationships();
            
            List <TContact> tContactOlds = contactDao.findListByPartner(partner);
            List <TBankAccount> tBankAccountOlds = bankAccountDao.findListByPartner(partner);
            List <TAmlCheck> tAmlCheckOlds = amlCheckDao.findListByPartner(partner);
            List <TRelationship> tRelationshipOlds = relationshipDao.findListByPartner(partner);
            if(tContactOlds.size()>0){
                for(TContact tContactOld : tContactOlds){
                    boolean deleteFlag = true;
                    for(TContact tContact : tContacts){
                        if(tContactOld.getContactId()==tContact.getContactId()){
                            deleteFlag = false;
                        }
                    }
                    
                    if(deleteFlag){
                        contactDao.delete(tContactOld);
                    }
                }
    
            }
            if(tBankAccountOlds.size()>0){
                for(TBankAccount tBankAccountOld : tBankAccountOlds){
                    boolean deleteFlag = true;
                    for(TBankAccount tBankAccount : tBankAccounts){
                        if(tBankAccountOld.getBankAccountId()==tBankAccount.getBankAccountId()){
                            deleteFlag = false;
                        }
                    }
                    if(deleteFlag){
                        bankAccountDao.delete(tBankAccountOld);
                    }
                }    
            }
            
            if(tAmlCheckOlds.size()>0){
                for(TAmlCheck tAmlCheckOld : tAmlCheckOlds){
                    boolean deleteFlag = true;
                    for(TAmlCheck tAmlCheck : tAmlChecks){
                        if(tAmlCheckOld.getAmlCheckId()==tAmlCheck.getAmlCheckId()){
                            deleteFlag = false;
                        }
                    }
                    if(deleteFlag){
                        amlCheckDao.delete(tAmlCheckOld);
                    }
                }
            }
            if(tRelationshipOlds.size()>0){
                for(TRelationship tRelationshipOld : tRelationshipOlds){
                    boolean deleteFlag = true;
                    for(TRelationship tRelationship : tRelationships){
                        if(tRelationshipOld.getRelationshipId()==tRelationship.getRelationshipId()){
                            deleteFlag = false;
                        }
                    }
                    if(deleteFlag){
                        relationshipDao.delete(tRelationshipOld);
                    }
                }    
            }
            partnerDao.merge(partner);
            
        }
        return partner.getPartnerId();
    }
    
    @Override
    public Page<Partner> findPageListBySql(Partner partner) {
        
        // TODO Auto-generated method stub
        Page<Partner> page = new Page<Partner>();
        PaginationHelper<Partner> ph = new PaginationHelper<Partner>();
        QuerySqlTemplate sqltmp = new QuerySqlTemplate();
        
        String sql = sqltmp.getQueryPartnerSql(partner);

        BeanPropertyRowMapper<Partner> rowmapper = new BeanPropertyRowMapper<Partner>(Partner.class);
        
        page = ph.fetchPage(sql, new Object[]{}, partner.getPageIndex(), partner.getCountPerPage(),rowmapper);

        return page;
    }
    
    @Override
    public Page<ChangeHistoryQuery> queryChangeHistoryList(ChangeHistoryQuery changeHistory) {
        
        // TODO Auto-generated method stub
        Page<ChangeHistoryQuery> page = new Page<ChangeHistoryQuery>();
        PaginationHelper<ChangeHistoryQuery> ph = new PaginationHelper<ChangeHistoryQuery>();
        QuerySqlChangeHistory sqltmp = new QuerySqlChangeHistory();
        
        String sql = sqltmp.getQueryHistorySql(String.valueOf(changeHistory.getPartnerId()));

        BeanPropertyRowMapper<ChangeHistoryQuery> rowmapper = new BeanPropertyRowMapper<ChangeHistoryQuery>(ChangeHistoryQuery.class);
        
        page = ph.fetchPage(sql, new Object[]{}, changeHistory.getPageIndex(), changeHistory.getCountPerPage(),rowmapper);

        return page;
    }


    @Override
    public void createPartnerLog(TPartner Partner) {
        TPartnerLog partnerLog = new TPartnerLog();
        BeanUtils.copyProperties(partnerLog, Partner);
        partnerLog.setFullName(Partner.getFirstName()+" "+Partner.getLastName());
        String content = checkPartnerChange(Partner,partnerLog);

        partnerLogDao.insert(partnerLog); 
    }
    
    public void createChangeHistory(TPartner Partner,String content){
        TChangeHistory changeHistory = new TChangeHistory();
        changeHistory.setChangeContent(content);
        changeHistory.setPartnerId(Partner.getPartnerId());
        changeHistory.setTradingName(Partner.getTradingName());
        changeHistory.setUpdateDate(Partner.getUpdateTime());
        changeHistory.setRoleSelect(Partner.getRoleSelect());
        changeHistory.setOperator(String.valueOf(AppContext.getCurrentUser().getUserId()));
        changeHistory.setFirstName(Partner.getFirstName());
        changeHistory.setLastName(Partner.getLastName());
        changeHistory.setBusinessPartnerCategory(Partner.getBusinessPartnerCategory());
        changeHistoryDao.insert(changeHistory);
    }
    public String checkPartnerChange(TPartner Partner,TPartnerLog partnerLog){
        String content = "";
        List<TPartnerLog> partnerLogs = partnerLogDao.findListByPartner(partnerLog);
        if(partnerLogs.size()>0){
            TPartnerLog partnerLogComp = partnerLogs.get(0);
            if(partnerLogComp.getTradingName()!=null&&partnerLog.getTradingName()!=null){
                if(!partnerLogComp.getTradingName().equals(partnerLog.getTradingName())){
                    String content1 =  " Trading name changed from "+partnerLogComp.getTradingName()+" to "+partnerLog.getTradingName(); 
                    createChangeHistory(Partner,content1);
                }
            }
            if(partnerLogComp.getLastName()!=null&&partnerLog.getLastName()!=null){
                if(!partnerLogComp.getLastName().equals(partnerLog.getLastName())){
                    String content2 =  " Name changed from "+partnerLogComp.getFirstName()+" "+partnerLogComp.getLastName() + " to "+partnerLogComp.getFirstName()+" "+partnerLog.getLastName();
                    createChangeHistory(Partner,content2);
                }  
            }
            if(partnerLogComp.getFirstName()!=null&&partnerLog.getFirstName()!=null){
                if(!partnerLogComp.getFirstName().equals(partnerLog.getFirstName())){
                    String content3 =  " Name changed from "+partnerLogComp.getFirstName() + " "+partnerLogComp.getLastName() +" to "+partnerLog.getFirstName()+" "+partnerLogComp.getLastName();
                    createChangeHistory(Partner,content3);
                }
            }
            if(partnerLogComp.getRegistrationName()!=null&&partnerLog.getRegistrationName()!=null){
                if(!partnerLogComp.getRegistrationName().equals(partnerLog.getRegistrationName())){
                    String content4 =  " Registration name changed from "+ partnerLogComp.getRegistrationName() + " to "+ partnerLog.getRegistrationName();
                    createChangeHistory(Partner,content4);
                }
            }
            if(partnerLogComp.getShortName()!=null&&partnerLog.getShortName()!=null){
                if(!partnerLogComp.getShortName().equals(partnerLog.getShortName())){
                    String content5 = " Short name changed from "+ partnerLogComp.getShortName() + " to " + partnerLog.getShortName();
                    createChangeHistory(Partner,content5);
                }
            }
            if(partnerLogComp.getStatus()!=null&&partnerLog.getStatus()!=null){
                String compStatus = "";
                String status = "";
                if(partnerLogComp.getStatus().equals("1")){
                     compStatus = Constants.STATUS_1;
                }else{
                    compStatus = Constants.STATUS_2;
                }
                if(partnerLog.getStatus().equals("1")){
                    status = Constants.STATUS_1;
                }else{
                    status = Constants.STATUS_2;
                }
                if(!partnerLogComp.getStatus().equals(partnerLog.getStatus())){
                    String content6  = " Status changed from "+ compStatus + " to " + status;
                    createChangeHistory(Partner,content6);
                }
            }
        }
       
        return content;
    }
    
    public boolean findAmlCheck(Partner partner){
        boolean amlFlag = false;
        boolean brokerAmlFlag = false;
        boolean retroAmlFlag = false;
        boolean mgaAmlFlag = false;
        List roleSelect = partner.getRoleSelectIds();
        if(roleSelect!=null){
            for(int i=0;i<roleSelect.size();i++){
               if(roleSelect.get(i).equals("1")){
                     amlFlag = amlCheckDao.findPendingAmlCheck(partner);        
                }else if(roleSelect.get(i).equals("2")){
                     brokerAmlFlag = brokerAmlCheckDao.findPendingAmlCheck(partner);        
                }else if(roleSelect.get(i).equals("4")){
                    retroAmlFlag =  retroAmlCheckDao.findPendingAmlCheck(partner);        
                }else if(roleSelect.get(i).equals("5")){
                    mgaAmlFlag =  mgaAmlCheckDao.findPendingAmlCheck(partner);        
                }
            } 
        }
        return amlFlag||brokerAmlFlag||retroAmlFlag||mgaAmlFlag;
    }
   
    public MessageVO amlCheck(String businessPartnerId,String roleId){
        TPartner partner =  new TPartner();
        MessageVO vo = new MessageVO();  
        
        partner.setBusinessPartnerId(businessPartnerId);
        List<TPartner> list =  partnerDao.findListByConditions(partner);
        String roleName = "Partner";
        if(!StringUtil.isBlank(roleId)){
            if(roleId.equals("1")){
                roleName = "Cedent";
            }else if(roleId.equals("2")){
                roleName = "Broker";
            }else if(roleId.equals("3")){
                roleName = "Insured";
            }else if(roleId.equals("4")){
                roleName = "Retrocessionaire";
            }else if(roleId.equals("5")){
                roleName = "MGA(Managing General Agent)";
            }
        }
        if(list.size()>0){
             partner = list.get(0);
             boolean amlFlag = true;
             //individual 
             if(partner.getBusinessPartnerCategory().equals("1")){
                 // status active pass
                 if(partner.getStatus().equals("1")){
                     vo.setCheckFlag(true);
                 //status inactive block
                 }else{
                     vo.setCheckFlag(false);
                     //[Role] [short name] with ID ****** is inactive.
                     vo.setMsg(roleName+" "+partner.getFirstName()+" "+partner.getLastName()+" with ID "+businessPartnerId+" is inactive.");
                 }
              //organization
             }else{
                 if(partner.getStatus().equals("1")){
                    if(roleId.equals("1")){
                         amlFlag = amlCheckDao.findAmlCheck(partner);        
                    }else if(roleId.equals("2")){
                        amlFlag = brokerAmlCheckDao.findAmlCheck(partner);        
                    }else if(roleId.equals("4")){
                        amlFlag = amlCheckDao.findAmlCheck(partner);        
                    }else if(roleId.equals("5")){
                        amlFlag = amlCheckDao.findAmlCheck(partner);        
                    }
                     if(!amlFlag){
                         //[Role] [short name] with ID ****** lacks effective AML check.
                         vo.setMsg(roleName+" "+partner.getShortName()+" with ID "+businessPartnerId+" lacks effective AML check.");
                     }
                    vo.setCheckFlag(amlFlag);
                 }else{
                     vo.setCheckFlag(false);
                     vo.setMsg(roleName+" "+partner.getShortName()+" with ID "+businessPartnerId+" is inactive.");
                 }
             }
        // partner does not exit     
        }else{
            vo.setCheckFlag(false);
            vo.setMsg(roleName+" with ID "+businessPartnerId+" does not exist.");
        }
       return vo;
        
    }
    public boolean findDeclinedAmlCheck(Partner partner){
        boolean amlFlag = false;
        boolean brokerAmlFlag = false;
        boolean retroAmlFlag = false;
        boolean mgaAmlFlag = false;
        List roleSelect = partner.getRoleSelectIds();
        if(roleSelect!=null){
            for(int i=0;i<roleSelect.size();i++){
                if(roleSelect.get(i).equals("1")){
                     amlFlag = amlCheckDao.findDeclinedAmlCheck(partner);        
                }else if(roleSelect.get(i).equals("2")){
                     brokerAmlFlag = brokerAmlCheckDao.findDeclinedAmlCheck(partner);        
                }else if(roleSelect.get(i).equals("4")){
                    retroAmlFlag =  retroAmlCheckDao.findDeclinedAmlCheck(partner);        
                }else if(roleSelect.get(i).equals("5")){
                    mgaAmlFlag =  mgaAmlCheckDao.findDeclinedAmlCheck(partner);        
                }
            } 
        }
        return amlFlag||brokerAmlFlag||retroAmlFlag||mgaAmlFlag;
    }
    
    @Override
    public void createAmlCheckMail(Partner partner){
        if(findAmlCheck(partner)){
            String mail = Env.getParameter("EMAIL_ADDRESS");
            MailVO mailVO = new MailVO();
            mailVO.setMailTo(mail);
            mailVO.setMailTitle("amlCheck");
            //please keep same with template setting
            mailVO.setMailType("amlCheck");
            //set template parameter
            mailVO.putTemplateParam("businessPartnerId", partner.getBusinessPartnerId());
            mailVO.putTemplateParam("tradingName", partner.getTradingName());
            try {
                MailService.sendEmail(mailVO);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
    
    @Override
    public void createDeclineMail(Partner partner){
        if(findDeclinedAmlCheck(partner)){
            MailVO mailVO = new MailVO();
            User user = userService.load(partner.getCreator());
            if(user!=null){
                mailVO.setMailTo(user.getEmail());
                mailVO.setMailTitle("declined amlCheck");
                //please keep same with template setting
                mailVO.setMailType("declinedAmlCheck");
                
                //set template parameter
                mailVO.putTemplateParam("businessPartnerId", partner.getBusinessPartnerId());
                mailVO.putTemplateParam("tradingName", partner.getTradingName());
                
                String approveBy = findDeclinedBy(partner);
                if(approveBy != null){
                    mailVO.putTemplateParam("checkBy", approveBy);
                }
                try {
                    MailService.sendEmail(mailVO);
                }catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } 

        }
    }
    
    public String getBusinessPartnerId() {
        Map<String, String> factors = new HashMap<String, String>();
        String businessPartnerId = null;
        try {
            businessPartnerId = numService.generateNumber(NumberingType.RI_BUSINESS_PARTNER_CODE, factors);
        } catch (Exception e) {
            
        }
        return businessPartnerId;
    }
    
    public String loadPartnerNameByPartnerCode(String partnerCode){
    	TPartner partner = partnerDao.loadByBusinessPartnerId(partnerCode);
    	if(null != partner){
    		if("2".equals(partner.getBusinessPartnerCategory())){
    			return partner.getTradingName();
    		} else if("1".equals(partner.getBusinessPartnerCategory())){
    		    String firstName = partner.getFirstName()==null?"":partner.getFirstName();
    		    String lastName = partner.getLastName()==null?"":partner.getLastName();
    			return firstName+" "+lastName;	
    		}else{
    		    return "";
    		}
    	}else {
    	    return "";    
    	}
       
    	
    }
    
    public boolean checkBusinessPartnerId(String businessPartnerId){
        return partnerDao.checkPartnerId(businessPartnerId);
    }


    @Override
    public String getPartnerCategory(String busiPartnerId) {
        TPartner partner = partnerDao.loadByBusinessPartnerId(busiPartnerId);
        return partner.getBusinessPartnerCategory();
    }


	@Override
	public String checkPartner(String busiPartnerId) {
		TPartner partner = partnerDao.loadByBusinessPartnerId(busiPartnerId);
		if(partner==null||partner.getStatus()==null){
			return "-1";
		}
        return partner.getStatus();
	}


    @Override
    public String getPartnerCountry(String busiPartnerId) {
        TPartner partner = partnerDao.loadByBusinessPartnerId(busiPartnerId);
        return partner.getCountry();
    }

    public String findDeclinedBy(Partner partner){
        String declinedBy = null;
        List roleSelect = partner.getRoleSelectIds();
        if(roleSelect!=null){
            for(int i=0;i<roleSelect.size();i++){
                if(roleSelect.get(i).equals("1")){
                    declinedBy = amlCheckDao.findDeclinedBy(partner);        
                }else if(roleSelect.get(i).equals("2")){
                    declinedBy = brokerAmlCheckDao.findDeclinedBy(partner);        
                }else if(roleSelect.get(i).equals("4")){
                    declinedBy =  retroAmlCheckDao.findDeclinedBy(partner);        
                }else if(roleSelect.get(i).equals("5")){
                    declinedBy =  mgaAmlCheckDao.findDeclinedBy(partner);        
                }
            } 
        }
        return declinedBy;
    }
    

}
