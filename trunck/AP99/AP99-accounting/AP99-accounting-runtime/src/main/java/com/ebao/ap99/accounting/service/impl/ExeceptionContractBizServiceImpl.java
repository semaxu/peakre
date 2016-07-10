package com.ebao.ap99.accounting.service.impl;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ebao.ap99.accounting.YearQuarter;
import com.ebao.ap99.accounting.UI.model.ExceptionContractVO;
import com.ebao.ap99.accounting.dao.ExceptionContractDao;
import com.ebao.ap99.accounting.entity.TRiAcctExcepCont;
import com.ebao.ap99.accounting.entity.TRiAcctExcepContDetail;
import com.ebao.ap99.accounting.model.ExceptionContractRecord;
import com.ebao.ap99.accounting.service.ExeceptionContractBizService;
import com.ebao.ap99.accounting.util.GenerateExcel;
import com.ebao.ap99.contract.service.ContractService;
import com.ebao.ap99.mail.MailService;
import com.ebao.ap99.mail.MailVO;
import com.ebao.ap99.parent.Page;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.unicorn.integration.client.Client;
import com.ebao.unicorn.platform.foundation.context.SpringContextUtils;

import io.netty.util.internal.StringUtil;

public class ExeceptionContractBizServiceImpl implements ExeceptionContractBizService  {
	

	@Autowired
    public ContractService contractService;
	
	@Autowired
    public ExceptionContractDao exceptionContractDao;
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private Client mqClient;
	
	 @Autowired
	private AccountingServiceImpl accountingServiceImpl;

	
	public EntityManager getEntityManager() {
		return this.em;
	}

	@Override
	public void exceptionJobExecute() throws Exception {
		//generate exception contract data
		long exceptionID = generateExceptionContract();
		setReversalStatus4PreException(exceptionID);
		collectExceptionContract(exceptionID);
		generateExcel(exceptionID);
		sendEmailToClient(exceptionID);
		
	}


	@Override
	public Page<ExceptionContractRecord> reviewExceptionContract(ExceptionContractVO exceptionContractVO) throws Exception {
        for(ExceptionContractRecord excepCont :exceptionContractVO.getExceptionContractList()){     	
        	updateExceptionContract(excepCont);
        }
        return loadExceptionContract(exceptionContractVO.getExceptionContractList().get(0).getFnyear(),exceptionContractVO.getExceptionContractList().get(0).getFnquarter());
	}

	@Override
	public Page<ExceptionContractRecord> loadExceptionContract(Long FNYear, String FNQuarter) {
		final Query query = getEntityManager().createNamedQuery("TRiAcctExcepContDetail.findByFNYearAndFNQuarter",TRiAcctExcepContDetail.class);
		query.setParameter("fnYear", FNYear);
		query.setParameter("fnQuarter", FNQuarter);
		@SuppressWarnings("unchecked")
		List<TRiAcctExcepContDetail> infoList = query.getResultList(); 
       List<ExceptionContractRecord> excepContList = new ArrayList<ExceptionContractRecord>();
       for (TRiAcctExcepContDetail excepContResult : infoList) {
       	if(excepContResult.getContractCode()!=null){
       		ExceptionContractRecord excepCont = new ExceptionContractRecord();
       		excepCont.setFnquarter(excepContResult.getFnQuarter());
       		excepCont.setFnyear(excepContResult.getFnYear());
       		excepCont.setContractID(excepContResult.getContractCode());
       		excepCont.setContracCompID(String.valueOf(excepContResult.getContCompId()));
       		String ReviewedFlag = excepContResult.getReviewedFlag(); 		
       	 if ("2".equals(ReviewedFlag) ){
       		excepCont.setReviewedFlag(true);
         } else {
        	 excepCont.setReviewedFlag(false);
         }
       		excepCont.setFnyearAndQuarter(excepCont.toString());
       		excepContList.add(excepCont);
       	}
       }
		 
		 Page<ExceptionContractRecord> page = new Page<ExceptionContractRecord>();
		    long results = infoList.size();
	        int start = 1;
	        page.setStart(start);
	        page.setCountPerPage(10);	        
	        page.setRows(excepContList);
	        int pageIndex = page.getStart() / page.getCountPerPage() + 1;
	        page.setPageIndex(pageIndex);
	        Long pageCount = results % page.getCountPerPage() == 0 ? results / page.getCountPerPage()
	                : results / page.getCountPerPage() + 1;
	        page.setPageCount(pageCount);
	        if (results < page.getCountPerPage()) {
	            page.setCountPerPage(Integer.parseInt(String.valueOf(results)));
	        }
	        return page;
	}
	@Override
	public Page<ExceptionContractRecord> searchExceptionContract(Long FNYear, String FNQuarter) {
		JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
        StringBuilder sqlBuild = new StringBuilder(" ");
       sqlBuild.append(" select ec.fn_year,ec.fn_quarter from T_RI_ACCT_EXCEP_CONT_DETAIL ec        ");
       sqlBuild.append(" where 1=1                           ");
       if(FNYear!=null){
    	   sqlBuild.append("  and  ec.fn_year = '"+FNYear+"'                           ");
       }
       if(!StringUtil.isNullOrEmpty(FNQuarter)){
    	   sqlBuild.append("  and  ec.fn_quarter = '"+FNQuarter+"'                           ");
       }    
       sqlBuild.append("  group by ec.fn_year,ec.fn_quarter                             ");
       sqlBuild.append("  order by ec.fn_year,ec.fn_quarter                       ");
       
       List<Map<String, Object>> result = jt.queryForList(sqlBuild.toString());  
       List<ExceptionContractRecord> excepContList = new ArrayList<ExceptionContractRecord>();
       for (Map<String, Object> excepContResult : result) {
       	if(excepContResult.get("fn_year")!=null){
       		ExceptionContractRecord excepCont = new ExceptionContractRecord();
       		excepCont.setFnquarter(excepContResult.get("fn_quarter").toString());
       		excepCont.setFnyear(Long.parseLong(excepContResult.get("fn_year").toString()));
       		excepCont.setFnyearAndQuarter(excepCont.toString());
       		excepContList.add(excepCont);
       	}
       }	
		 
		 Page<ExceptionContractRecord> page = new Page<ExceptionContractRecord>();
		    long results = result.size();
	        int start = 1;
	        page.setStart(start);
	        page.setCountPerPage(10);	        
	        page.setRows(excepContList);
	        int pageIndex = page.getStart() / page.getCountPerPage() + 1;
	        page.setPageIndex(pageIndex);
	        Long pageCount = results % page.getCountPerPage() == 0 ? results / page.getCountPerPage()
	                : results / page.getCountPerPage() + 1;
	        page.setPageCount(pageCount);
	        if (results < page.getCountPerPage()) {
	            page.setCountPerPage(Integer.parseInt(String.valueOf(results)));
	        }
	        return page;
	}
	
	public void updateExceptionContract(ExceptionContractRecord excepCont) {
		String reviewFlag = "1";
     	if (excepCont.getReviewedFlag()) {
     		reviewFlag="2";
        } else {
        	reviewFlag="1";
        }     	
		JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
        StringBuilder sqlBuild = new StringBuilder(" ");
       sqlBuild.append(" update T_RI_ACCT_EXCEP_CONT_DETAIL set reviewed_flag = '"+reviewFlag+"'  where cont_comp_id =  "+excepCont.getContracCompID()+"   "); 
       jt.execute(sqlBuild.toString());  
	}
	
	public void collectExceptionContract(long exceptionID) throws Exception {
		YearQuarter currentFNQuarter = accountingServiceImpl.currentFinancialQuarter();
		
		JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
        StringBuilder sql = new StringBuilder(" ");
        sql.append("   insert into T_RI_ACCT_EXCEP_CONT_DETAIL                  ");
		sql.append("     (exception_detail_id,                                  ");
		sql.append("      exception_id,                                         ");
		sql.append("      cont_comp_id,                                         ");
		sql.append("      contract_id,                                          ");
		sql.append("      fn_year,                                              ");
		sql.append("      fn_quarter,                                           ");
		sql.append("      reviewed_flag,                                        ");
		sql.append("      STATUS)                                               ");
		sql.append("     select S_RI_EXCEP_CON_DETAIL_ID.NEXTVAL,               ");
		sql.append("            17003363,                                       ");
		sql.append("            t.cont_comp_id,                                 ");
		sql.append("            t.contract_code,                                ");
		sql.append("            "+currentFNQuarter.getYear()+",                 ");
		sql.append("            "+currentFNQuarter.getQuarter()+",              ");
		sql.append("            '1',                                            ");
		sql.append("            '1'                                             ");
		sql.append("       from (select t2.cont_comp_id, t2.contract_code       ");
		sql.append("               from t_ri_contract_structure   cont,         ");
		sql.append("                    t_ri_contract_structure   sec,          ");
		sql.append("                    t_ri_contract_structure   subsec,       ");
		sql.append("                    t_ri_contract_info        t2,           ");
		sql.append("                    t_ri_cont_section_info    t3,           ");
		sql.append("                    t_ri_cont_subsection_info t4,           ");
		sql.append("                    t_ri_cont_pricing         t5,           ");
		sql.append("                    t_ri_cont_pricing_item    t6            ");
		sql.append("              where cont.cont_Comp_Id = t2.cont_Comp_Id     ");
		sql.append("                and sec.cont_Comp_Id = t3.cont_Comp_Id      ");
		sql.append("                and subsec.cont_Comp_Id = t4.cont_Comp_Id   ");
		sql.append("                and cont.cont_Comp_Id = sec.parent_Id       ");
		sql.append("                and sec.cont_Comp_Id = subsec.parent_Id     ");
		sql.append("                and t2.latest_status = '4'                  ");
		sql.append("                and t5.cont_Comp_Id = sec.cont_Comp_Id      ");
		sql.append("                and t6.pricing_id = t5.pricing_id           ");
		sql.append("                and t6.loss_ratio <= 0.07) t                ");
        jt.execute(sql.toString());
		
	}
	
	public long generateExceptionContract() {
		TRiAcctExcepCont exceptionContract = new TRiAcctExcepCont();
		exceptionContract.setJobId(123l);
		exceptionContract.setStatus("1");//valid
		exceptionContractDao.insert(exceptionContract);
		em.flush();
		return exceptionContract.getExceptionId();
	}
	
	public void sendEmailToClient(long exceptionID) throws Exception
	{
		List<ExceptionContractRecord> excepContList = collectExceptionContract2Client(exceptionID);
		HashMap<String, Object> map = new HashMap<String, Object>();
		
//		map.put("emailTo", "sema.xu@ebaotech.com");
//		map.put("emailTitle", "create user");
//		map.put("emailType", "createUser");
//		map.put("emailFrom", "test@test.com");
//		HashMap<String,String> emailContent = new HashMap<String,String>();
//		emailContent.put("username", "test");
//		emailContent.put("password", "password");
//		emailContent.put("url", "http://www.baidu.com");
//		
//		List<String> attachmentList = new ArrayList<String>();
//	//	attachmentList.add("fileUpload/T_ROLE.sql");
//	//	map.put("attachmentList", attachmentList);
//		map.put("emailContent", emailContent);
//		mqClient.send("emailSend", map);
//		
		
		YearQuarter yq = new YearQuarter(AppContext.getSysDate());
		
		MailVO mailVO = new MailVO();
        
        mailVO.setMailTo("sema.xu@ebaotech.com");
        mailVO.setMailTitle("create user");
        //please keep same with template setting
        mailVO.setMailType("revaluationReport");
        
        //set template parameter
        mailVO.putTemplateParam("username", "test");
        mailVO.putTemplateParam("password", "password");
        mailVO.putTemplateParam("FNYearAndQuarter", yq.toString());
        mailVO.putTemplateParam("url", "http://www.baidu.com");
        
        //Relative file path can be fetched from Document Management.
   //     mailVO.addAttachment("fileUpload/T_ROLE.sql");
   //     mailVO.addAttachment("fileUpload/在保.sql");
        
        MailService.sendEmail(mailVO);
		assertTrue(true);
	}

	public void generateExcel(long exceptionID) throws Exception {
		String path = "D:/Exception";
	  	SimpleDateFormat fymd = new SimpleDateFormat ("yyyy/MM/dd");
	  	String sDate = fymd.format(AppContext.getSysDate());
		String fileName = "EXCEPTION_RECORD"+sDate + ".xls";
		File files = new File(path);
		path = files.getAbsolutePath();
		File fil = new File(path + "/" + fileName);
		File _parentDir = new File(fil.getParent());
		if (!_parentDir.isDirectory()) {
			_parentDir.mkdirs();
		}
		boolean newFileFlag = fil.createNewFile();
		if (!newFileFlag) {
			// exist repeat file
			// throw new Exception("exist repeat file");
			fil.delete();
			fil.createNewFile();
		}
		List<ExceptionContractRecord> excepContList = collectExceptionContract2Client(exceptionID);
		GenerateExcel generateExcel = new GenerateExcel();
		ByteArrayOutputStream outByteStream = generateExcel.excelGenerate(excepContList);
		byte[] outArray = outByteStream.toByteArray();
		OutputStream file = new FileOutputStream(fil);
		file.write(outArray);
		file.close();
	}
	
	public List<ExceptionContractRecord> collectExceptionContract2Client(long exceptionID) {
//		TRiAcctExcepCont excepCont = exceptionContractDao.load(exceptionID);
//       List<ExceptionContractRecord> excepContList = new ArrayList<ExceptionContractRecord>();
//       for (TRiAcctExcepContDetail excepContResult: excepCont.getTRiAcctExcepContDetails()) {
//       	if(excepContResult.getContractCode()!=null){
//       		ExceptionContractRecord excepContRecord = new ExceptionContractRecord();
//       		excepContRecord.setFnquarter(excepContResult.getFnQuarter());
//       		excepContRecord.setFnyear(excepContResult.getFnYear());
//       		excepContRecord.setContractID(excepContResult.getContractCode());
//       		excepContRecord.setContracCompID(String.valueOf(excepContResult.getContCompId()));
//       		String ReviewedFlag = excepContResult.getReviewedFlag(); 		
//       	 if ("2".equals(ReviewedFlag) ){
//       		excepContRecord.setReviewedFlag(true);
//         } else {
//        	 excepContRecord.setReviewedFlag(false);
//         }
//         	excepContRecord.setFnyearAndQuarter(excepContRecord.toString());
//       		excepContList.add(excepContRecord);
//       	}
//       }	
				
		JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
        StringBuilder sqlBuild = new StringBuilder(" ");
       sqlBuild.append(" select ec.contract_id,ec.cont_comp_id, ec.reviewed_flag,ec.fn_year,ec.fn_quarter        ");
       sqlBuild.append(" from T_RI_ACCT_EXCEP_CONT_DETAIL ec                           ");
       sqlBuild.append("  where ec.exception_id = "+exceptionID+"                            ");
       List<Map<String, Object>> result = jt.queryForList(sqlBuild.toString());  
       List<ExceptionContractRecord> excepContList = new ArrayList<ExceptionContractRecord>();
       for (Map<String, Object> excepContResult : result) {
       	if(excepContResult.get("contract_id")!=null){
       		ExceptionContractRecord excepCont = new ExceptionContractRecord();
       		excepCont.setFnquarter(excepContResult.get("fn_quarter").toString());
     //  		excepCont.setFnyear(excepContResult.get("fn_year").toString());
       		excepCont.setContractID(excepContResult.get("contract_id").toString());
       		excepCont.setContracCompID(excepContResult.get("cont_comp_id").toString());
       		String ReviewedFlag = excepContResult.get("reviewed_flag").toString(); 		
       	 if ("2".equals(ReviewedFlag) ){
       		excepCont.setReviewedFlag(true);
         } else {
        	 excepCont.setReviewedFlag(false);
         }
       		excepCont.setFnyearAndQuarter(excepCont.toString());
       		excepContList.add(excepCont);
       	}
       }	
	        return excepContList;
	}
	
	public void setReversalStatus4PreException(long exceptionID) {
		JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
		StringBuilder sqlBuild = new StringBuilder(" ");
		sqlBuild.append(" update T_RI_ACCT_EXCEP_CONT RR set RR.Status = '2' where RR.Status = '1'  "); // 1:valid, 2:reversal
		sqlBuild.append(" and RR.exception_id <> " + exceptionID + " ");
		jt.execute(sqlBuild.toString());

	}


}