package com.ebao.ap99.accounting.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.ebao.ap99.accounting.YearQuarter;
import com.ebao.ap99.accounting.dao.RevaluateDao;
import com.ebao.ap99.accounting.dao.TRiAcctFeeDao;
import com.ebao.ap99.accounting.dao.TRiAcctFeeDetailDao;
import com.ebao.ap99.accounting.entity.TRiACCTRevaluate;
import com.ebao.ap99.accounting.entity.TRiAcctFee;
import com.ebao.ap99.accounting.entity.TRiAcctFeeDetail;
import com.ebao.ap99.accounting.model.RevaluateInfoModel;
import com.ebao.ap99.accounting.model.RevaluationDetailModel;
import com.ebao.ap99.accounting.model.RevaluationValidModel;
import com.ebao.ap99.accounting.model.SoaTreeVO;
import com.ebao.ap99.accounting.util.GenerateExcel;
import com.ebao.ap99.accounting.util.SoaConstant;
import com.ebao.ap99.arap.constant.BizTransactionType;
import com.ebao.ap99.arap.constant.CurrencyConstants;
import com.ebao.ap99.arap.constant.FinanceConsistants;
import com.ebao.ap99.arap.service.FinanceService;
import com.ebao.ap99.arap.vo.BusinessFee;
import com.ebao.ap99.arap.vo.BusinessFeeModel;
import com.ebao.ap99.contract.entity.ContractModel;
import com.ebao.ap99.file.service.CalService;
import com.ebao.ap99.parent.Page;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.unicorn.platform.foundation.cfg.Env;
import com.ebao.unicorn.platform.foundation.context.SpringContextUtils;
import com.ebao.unicorn.platform.foundation.utils.BeanUtils;

public class RevaluationReportServiceImpl implements CalService {
	
	@Autowired
	private TRiAcctFeeDao riAcctFeeDao;

	@Autowired
	private RevaluateDao revaluateDao;

	@Autowired
	private TRiAcctFeeDetailDao riAcctFeeDetailDao;

	@Autowired
	FinanceService financeService;
	
	 @Autowired
	 private AccountingServiceImpl accountingServiceImpl;

	@PersistenceContext
	private EntityManager em;

	public EntityManager getEntityManager() {
		return this.em;
	}

	public void executeRevaluation() throws Exception {
		// collect revaluate source items
		long revaluationId = generateRevaluateInfo();
		// collect ARAP
		collectARAP(revaluationId);
		// collect Reserve for Soa
		collectReserveForAccounting(revaluationId);
		collectReserveForClaim(revaluationId);
		// collect Suspense
		 collectSuspense(revaluationId);
		// collect Estimate
		 collectEstimate(revaluationId);
		// create summary for each currency and month
		createSummaryByMonthAndCurrency(revaluationId);
		// calculate exchange diff for each item group
		generateAcctFeeForRevaluate(revaluationId);
		// set the finishing time
		setFinishingTime(revaluationId);
		//save the detail of revaluation
		collectRevaluationDetailRecord(revaluationId);

	}
	
	public RevaluationValidModel validRevaluationMonthEndExchangeRate(RevaluationValidModel revaluationValidModel) throws Exception {
		// collect revaluate source items
		long revaluationId = generateRevaluateInfo();
		// collect ARAP
		collectARAP(revaluationId);
		// collect Reserve
		collectReserveForAccounting(revaluationId);
		collectReserveForClaim(revaluationId);
		// collect Suspense
		 collectSuspense(revaluationId);
		// collect Estimate
		 collectEstimate(revaluationId);
		//validMonthEndExchangeRate
		 RevaluationValidModel revalModel = validMonthEndExhangeRate(revaluationValidModel,revaluationId);
	    // delete general data
		 deleteReversalStatus4Valid(revaluationId);
		 return revalModel;
	}
	
	public Page<RevaluateInfoModel> revaluationView() {
       List<RevaluateInfoModel> revaluateList = new ArrayList<RevaluateInfoModel>();
       revaluateList.add(getRevaluationValueByFN());
		 Page<RevaluateInfoModel> page = new Page<RevaluateInfoModel>();
		    long results = 1;
	        int start = 1;
	        page.setStart(start);
	        page.setCountPerPage(10);	        
	        page.setRows(revaluateList);
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
	
	public RevaluationDetailModel revaluationDetailSeach() throws Exception{
		
		return getRevaluationExcelPath();
	}

	private final String VALID = "1";

	public long generateRevaluateInfo() {
		TRiACCTRevaluate revaluate = new TRiACCTRevaluate();
		YearQuarter yearQuarter = new YearQuarter(AppContext.getSysDate());
		revaluate.setFnQuarter(yearQuarter.toString().trim());
		revaluate.setStatus(VALID);
		revaluate.setPostData(AppContext.getSysDate());
		revaluate.setStartingData(AppContext.getSysDate());
		revaluate.setOperatorID(String.valueOf(AppContext.getCurrentUser().getUserId()));
		revaluate.setOperator(String.valueOf(AppContext.getCurrentUser().getUserName()));
		revaluate.setExecuteStatus("0");
		revaluateDao.insert(revaluate);
		em.flush();
		return revaluate.getRevaluationId();
	}
	
	public void setFinishingTime(long revaluationId) {
		TRiACCTRevaluate revaluate =revaluateDao.load(revaluationId);
		revaluate.setExecuteStatus("1");
		revaluate.setFinishingData(AppContext.getSysDate());
		revaluateDao.merge(revaluate);
		em.flush();
	}


	public void collectARAP(long revaluationId) {
		JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
		StringBuilder sqlBuild = new StringBuilder(" ");
	        sqlBuild.append("    insert into T_RI_ACCT_REVALUATE_DETAIL                                    ");
			sqlBuild.append("      (REVALUATION_DETAIL_ID,                                                 ");
			sqlBuild.append("       REVALUATION_ID,                                                        ");
			sqlBuild.append("       ITEM_TYPE,                                                             ");
			sqlBuild.append("       BIZ_ID,                                                                ");
			sqlBuild.append("       CURRENCY_CODE,                                                         ");
			sqlBuild.append("       BALANCE,                                                               ");
			sqlBuild.append("       SIGN,                                                                  ");
			sqlBuild.append("       TRANS_DATE,                                                            ");
			sqlBuild.append("       entry_code,                                                            ");
			sqlBuild.append("       entry_item,                                                            ");
			sqlBuild.append("       contract_code)                                                           ");
			sqlBuild.append("      select S_RI_ACCT_REVALUATE_DETAIL_ID.NEXTVAL,                           ");
			sqlBuild.append("                 " + revaluationId + ",                                       ");
			sqlBuild.append("             'ARAP',                                                          ");
			sqlBuild.append("             BF.FEE_ID,                                                       ");
			sqlBuild.append("             BF.currency_code,                                                ");
			sqlBuild.append("             BF.balance,                                                      ");
			sqlBuild.append("             BF.SIGN,                                                         ");
			sqlBuild.append("             BF.Post_Date,                                                   ");
			sqlBuild.append("             BF.entry_code,                                                   ");
			sqlBuild.append("             EC.Entry_Code_Name,                                              "); 
			sqlBuild.append("             contract_code                                                      ");
			sqlBuild.append("        from T_RI_BCP_FEE BF,T_RI_ENTRY_CODE EC                               ");
			sqlBuild.append("       where BF.STATUS = 0                                                    ");// 0:outstanding,1:fullySettle
			sqlBuild.append("         and BF.Arap_Type in (1, 2)                                           ");// 1:AR,2:AP
			sqlBuild.append("     and BF.Entry_Code not in (" + SoaConstant.REVALUATE_RESERVE_ENTRY_CODE + ")        "); 
			sqlBuild.append("         and BF.Entry_Code = EC.Entry_Code                                  "); 
			sqlBuild.append("         and BF.Currency_Code not in ('" + CurrencyConstants.EXCHANGE_BASE_CURRENCY + "') ");   
		jt.execute(sqlBuild.toString());
	}

	public void collectReserveForAccounting(long revaluationId) {
		JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
		StringBuilder sqlBuild = new StringBuilder(" ");
		
	        sqlBuild.append("  insert into T_RI_ACCT_REVALUATE_DETAIL                                                 ");
			sqlBuild.append("                (REVALUATION_DETAIL_ID,                                                  ");
			sqlBuild.append("                 REVALUATION_ID,                                                         ");
			sqlBuild.append("                 ITEM_TYPE,                                                              ");
			sqlBuild.append("                 BIZ_ID,                                                                 ");
			sqlBuild.append("                 CURRENCY_CODE,                                                          ");
			sqlBuild.append("                 BALANCE,                                                                ");
			sqlBuild.append("                 SIGN,                                                                   ");
			sqlBuild.append("                 TRANS_DATE,                                                             ");
			sqlBuild.append("                 entry_code,                                                             ");
			sqlBuild.append("                 entry_item,                                                             ");
			sqlBuild.append("                 contract_code)                                                            ");
			sqlBuild.append("                select S_RI_ACCT_REVALUATE_DETAIL_ID.NEXTVAL,                            ");
			sqlBuild.append("                 " + revaluationId + ",                                                  ");
			sqlBuild.append("                       'Reserve',                                                        ");
			sqlBuild.append("                       FEE_ID,                                                           ");
			sqlBuild.append("                       currency_code,                                                    ");
			sqlBuild.append("                       balance,                                                          ");
			sqlBuild.append("                       SIGN,                                                             ");
			sqlBuild.append("                       POST_DATE,                                                        ");
			sqlBuild.append("                       entry_code,                                                       ");
			sqlBuild.append("                       Entry_Code_Name,                                                  ");
			sqlBuild.append("                       contract_code                                                       ");
			sqlBuild.append("                  from (select F.*,EC.Entry_Code_Name                                    ");
			sqlBuild.append("                         from T_RI_BCP_FEE F,T_RI_ENTRY_CODE EC                          ");
//			sqlBuild.append("                           ,   (select BF.Entry_Code,                                     ");
//			sqlBuild.append("                                      max(BF.Insert_Time) as Insert_Time                 ");
//			sqlBuild.append("                                 from T_RI_BCP_FEE BF                                    ");
//			sqlBuild.append("          where  BF.Entry_Code  in (" + SoaConstant.RESERVE_ENTRY_CODE + ")                   ");
//			sqlBuild.append("          and BF.Currency_Code not in ('" + CurrencyConstants.EXCHANGE_BASE_CURRENCY + "')");
//			sqlBuild.append("                                  and BF.Post_Date is not null                           ");
//			sqlBuild.append("                                group by BF.Entry_Code) E                                ");
//          sqlBuild.append("                        where F.insert_time = E.Insert_Time                              ");
			sqlBuild.append("                          where F.Biz_Trans_Type in (3)                                       ");
			sqlBuild.append("                          and F.Entry_Code  in (" + SoaConstant.REVALUATE_RESERVE_ENTRY_CODE + ")     ");
			sqlBuild.append("          and F.Currency_Code not in ('" + CurrencyConstants.EXCHANGE_BASE_CURRENCY + "')");
			sqlBuild.append("                           and F.Balance ! = 0                                        ");
			sqlBuild.append("                          and F.Entry_Code = EC.Entry_Code ) D                         ");
		
		jt.execute(sqlBuild.toString());
	}
	
	public void collectReserveForClaim(long revaluationId) {
		JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
		StringBuilder sqlBuild = new StringBuilder(" ");
		
	        sqlBuild.append("  insert into T_RI_ACCT_REVALUATE_DETAIL                                                 ");
			sqlBuild.append("                (REVALUATION_DETAIL_ID,                                                  ");
			sqlBuild.append("                 REVALUATION_ID,                                                         ");
			sqlBuild.append("                 ITEM_TYPE,                                                              ");
			sqlBuild.append("                 BIZ_ID,                                                                 ");
			sqlBuild.append("                 CURRENCY_CODE,                                                          ");
			sqlBuild.append("                 BALANCE,                                                                ");
			sqlBuild.append("                 SIGN,                                                                   ");
			sqlBuild.append("                 TRANS_DATE,                                                             ");
			sqlBuild.append("                 entry_code,                                                             ");
			sqlBuild.append("                 entry_item,                                                             ");
			sqlBuild.append("                 contract_code)                                                            ");
			sqlBuild.append("                select S_RI_ACCT_REVALUATE_DETAIL_ID.NEXTVAL,                            ");
			sqlBuild.append("                 " + revaluationId + ",                                                  ");
			sqlBuild.append("                       'Reserve',                                                        ");
			sqlBuild.append("                       FEE_ID,                                                           ");
			sqlBuild.append("                       currency_code,                                                    ");
			sqlBuild.append("                       balance,                                                          ");
			sqlBuild.append("                       SIGN,                                                             ");
			sqlBuild.append("                       POST_DATE,                                                        ");
			sqlBuild.append("                       entry_code,                                                       ");
			sqlBuild.append("                       Entry_Code_Name,                                                  ");
			sqlBuild.append("                       contract_code                                                       ");
			sqlBuild.append("                  from (select F.*,EC.Entry_Code_Name                                    ");
			sqlBuild.append("                         from T_RI_BCP_FEE F,T_RI_ENTRY_CODE EC                         ");
//			sqlBuild.append("                             , (select BF.Entry_Code,                                     ");
//			sqlBuild.append("                                      max(BF.Insert_Time) as Insert_Time                 ");
//			sqlBuild.append("                                 from T_RI_BCP_FEE BF                                    ");
//			sqlBuild.append("          where  BF.Entry_Code  in (" + SoaConstant.RESERVE_ENTRY_CODE + ")                   ");
//			sqlBuild.append("          and BF.Currency_Code not in ('" + CurrencyConstants.EXCHANGE_BASE_CURRENCY + "')");
//			sqlBuild.append("                                  and BF.Post_Date is not null                           ");
//			sqlBuild.append("                                group by BF.Entry_Code) E                                ");
//			sqlBuild.append("                        where F.insert_time = E.Insert_Time                              ");
			sqlBuild.append("                      where F.Biz_Trans_Type in (1,2)                                       ");
			sqlBuild.append("                          and F.Entry_Code  in (" + SoaConstant.REVALUATE_RESERVE_ENTRY_CODE + ")    ");
			sqlBuild.append("          and F.Currency_Code not in ('" + CurrencyConstants.EXCHANGE_BASE_CURRENCY + "')");
			sqlBuild.append("                           and F.Balance ! = 0                                        ");
			sqlBuild.append("                          and F.Entry_Code = EC.Entry_Code ) D                            ");
		
		jt.execute(sqlBuild.toString());
	}

	public void collectSuspense(long revaluationId) {
		JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
		StringBuilder sqlBuild = new StringBuilder(" ");

		sqlBuild.append("   insert into T_RI_ACCT_REVALUATE_DETAIL                                   ");
		sqlBuild.append("         (REVALUATION_DETAIL_ID,                                            ");
		sqlBuild.append("          REVALUATION_ID,                                                   ");
		sqlBuild.append("          ITEM_TYPE,                                                        ");
		sqlBuild.append("          BIZ_ID,                                                           ");
		sqlBuild.append("          CURRENCY_CODE,                                                    ");
		sqlBuild.append("          BALANCE,                                                          ");
		sqlBuild.append("          SIGN,                                                             ");
		sqlBuild.append("          TRANS_DATE,                                                       ");
		sqlBuild.append("       entry_code,                                                          ");
		sqlBuild.append("       entry_item,                                                          ");
		sqlBuild.append("       contract_code)                                                       ");
		sqlBuild.append("          select S_RI_ACCT_REVALUATE_DETAIL_ID.NEXTVAL,                     ");
		sqlBuild.append("                 " + revaluationId + ",                                     ");
		sqlBuild.append("          'SUSPENSE',                                                       ");
		sqlBuild.append("          SUSPENSE_ID,                                                      ");
		sqlBuild.append("          currency_code,                                                    ");
		sqlBuild.append("          balance,                                                          ");
		sqlBuild.append("          1,                                                                ");
		sqlBuild.append("          post_date,                                                        ");
		sqlBuild.append("          '"+FinanceConsistants.GL_ENTRY_CODE_BALANCE+"',                   ");
		sqlBuild.append("          '"+FinanceConsistants.GL_ENTRY_CODE_BALANCE+"',                   ");
		sqlBuild.append("           contract_code                                                    ");	
		sqlBuild.append("     from (select b.suspense_id, a.post_date, b.balance, b.currency_code,    ");
		sqlBuild.append("             decode(b.contract_id,'','',(select i.contract_code from t_ri_contract_info i where i.cont_comp_id = b.contract_id) ) as contract_code                             ");
		sqlBuild.append("             from T_RI_BCP_SETTLEMENT_DETAIL a,                             ");
		sqlBuild.append("                  t_ri_bcp_suspense          b,                             ");
		sqlBuild.append("                  t_ri_bcp_collection        c                              ");
		sqlBuild.append("            where a.suspense_id = b.suspense_id                             ");
		sqlBuild.append("              and a.collection_id is not null                               ");
		sqlBuild.append("              and b.Currency_Code not in ('" + CurrencyConstants.EXCHANGE_BASE_CURRENCY + "')  ");
		sqlBuild.append("              and a.collection_id = c.collection_id                         ");
		sqlBuild.append("              and c.status != -1                                            ");
		sqlBuild.append("              and b.balance != 0) e                                         ");

		jt.execute(sqlBuild.toString());
	}
	
	public void collectEstimate(long revaluationId) {
		JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
		
	       String[] arr = SoaConstant.ESTIMATE_ENTRYCODE.split(":");
	       String entryCode = "";
	        List<String> entryCodelist = (List<String>) Arrays.asList(arr);
	        for (int i = 0; i < entryCodelist.size(); i++) {
	        	entryCode = entryCodelist.get(i);
	        	StringBuilder sqlBuild = new StringBuilder(" ");
	    		sqlBuild.append("    insert into T_RI_ACCT_REVALUATE_DETAIL                       ");
	    		sqlBuild.append("      (REVALUATION_DETAIL_ID,                                    ");
	    		sqlBuild.append("       REVALUATION_ID,                                           ");
	    		sqlBuild.append("       ITEM_TYPE,                                                ");
	    		sqlBuild.append("       BIZ_ID,                                                   ");
	    		sqlBuild.append("       CURRENCY_CODE,                                            ");
	    		sqlBuild.append("       BALANCE,                                                  ");
	    		sqlBuild.append("       SIGN,                                                     ");
	    		sqlBuild.append("       TRANS_DATE,                                               ");
	    		sqlBuild.append("       entry_code,                                               ");
	    		sqlBuild.append("       entry_item,                                               ");
	    		sqlBuild.append("       contract_code)                                            ");
	    		sqlBuild.append("   select S_RI_ACCT_REVALUATE_DETAIL_ID.NEXTVAL,                  ");
	    		sqlBuild.append("          " + revaluationId + ",                                  ");
	    		sqlBuild.append("          'Estimate',                                             ");
	    		sqlBuild.append("          bf.FEE_ID,                                              ");
	    		sqlBuild.append("          bf.currency_code,                                       ");
	    		sqlBuild.append("          bf.balance,                                             ");
	    		sqlBuild.append("          bf.SIGN,                                                ");
	    		sqlBuild.append("          bf.Post_Date,                                           ");
	    		sqlBuild.append("          bf.entry_code,                                          ");
	    		sqlBuild.append("          ec.Entry_Code_Name,                                     ");
	    		sqlBuild.append("          bf.contract_code                                        ");
	    		sqlBuild.append("     from (select f.biz_trans_id                                  ");
	    		sqlBuild.append("             from t_Ri_Bcp_Fee f, t_Ri_Bcp_Fee f1                 ");
	    		sqlBuild.append("            where f.is_estimation = 1                             ");
	    		sqlBuild.append("              and f.entry_code in " + entryCode+ " ");
	    		sqlBuild.append("              and f.biz_trans_id is not null                      ");
	    		sqlBuild.append("            group by f.contract_code, f.biz_trans_id              ");
	    		sqlBuild.append("           having sum(f.balance) != 0) fs,                        ");
	    		sqlBuild.append("          t_ri_bcp_fee bf,                                        ");
	    		sqlBuild.append("          t_ri_entry_code ec                                      ");
	    		sqlBuild.append("    where fs.biz_trans_id = bf.biz_trans_id                       ");
	    		sqlBuild.append("      and bf.is_estimation = 1                                    ");
	    		sqlBuild.append("      and bf.entry_code in " + entryCode+ " ");
	    		sqlBuild.append("      and bf.entry_code = ec.entry_code                           ");
	    		sqlBuild.append("      and bf.Currency_Code not in ('" + CurrencyConstants.EXCHANGE_BASE_CURRENCY + "')  ");
	    		jt.execute(sqlBuild.toString());
	        }	
		
	}

	public void createSummaryByMonthAndCurrency(long revaluationId) {
		JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
		StringBuilder sqlBuild = new StringBuilder(" ");
		sqlBuild.append(" insert into T_RI_ACCT_REVALUATE_RESULT                                                 ");
		sqlBuild.append("   (REVALUATION_RESULT_ID,                                                         ");
		sqlBuild.append("    REVALUATION_ID,                                                                ");
		sqlBuild.append("    FN_MONTH,                                                                      ");
		sqlBuild.append("    CURRENCY_CODE,                                                                 ");
		sqlBuild.append("    AMOUNT,                                                                        ");
		sqlBuild.append("    original_rate,                                                                 ");
		sqlBuild.append("    current_rate,                                                                  ");
		sqlBuild.append("    EX_RATE_DIFF,                                                                  ");
		sqlBuild.append("    EX_DIFF)                                                                       ");
		sqlBuild.append("    select S_RI_ACCT_REVALUATE_RESULT_ID.Nextval, " + revaluationId + ",REVALUATE_RESULT.* from (  ");
		sqlBuild.append("   select to_char(RD.TRANS_DATE, 'yyyymm') as FN_MONTH,                            ");
		sqlBuild.append("          RD.currency_code,                                                        ");
		sqlBuild.append("          sum(RD.BALANCE * RD.sign) as amount,                                     ");
		sqlBuild.append("          ER1.Rate as original_rate,                                               ");
		sqlBuild.append("          ER2.Rate as current_rate,                                                ");
		sqlBuild.append("          ER2.Rate - ER1.Rate as EX_RATE_DIFF,                                ");
		sqlBuild.append("          sum(round(((round(1/ER2.Rate,9) - round(1/ER1.Rate,9)) * RD.BALANCE * RD.sign),4)) as EX_DIFF             ");
		sqlBuild.append("     from T_RI_ACCT_REVALUATE_DETAIL      RD,                                           ");
		sqlBuild.append("          T_RI_BCP_CFG_EXCHANGE_RATE ER1,                                          ");
		sqlBuild.append("          T_RI_BCP_CFG_EXCHANGE_RATE ER2                                           ");
		sqlBuild.append("    where RD.Currency_Code = ER1.base_currency_code                                  ");
		sqlBuild.append("      and ER1.Rate_Type = 1                                                        ");
		sqlBuild.append("      and RD.TRANS_DATE >= ER1.Valid_Date                                          ");
		sqlBuild.append("      and RD.TRANS_DATE <= ER1.Expiry_Date                                         ");
		sqlBuild.append("      and RD.Currency_Code = ER2.base_currency_code                                  ");
		sqlBuild.append("      and ER2.Valid_Date <= to_date(to_char(sysdate, 'yyyy-mm-dd' ),'yyyy-mm-dd')                                                ");
		sqlBuild.append("      and ER2.Expiry_Date >= to_date(to_char(sysdate, 'yyyy-mm-dd' ),'yyyy-mm-dd')                                               ");
		sqlBuild.append("      and ER2.Rate_Type = 2                                                        ");
		sqlBuild.append("      and RD.REVALUATION_ID = " + revaluationId + "                                    ");
		sqlBuild.append("    group by to_char(RD.TRANS_DATE, 'yyyymm'),                                     ");
		sqlBuild.append("             RD.currency_code,                                                     ");
		sqlBuild.append("             ER1.Rate,                                                             ");
		sqlBuild.append("             ER2.Rate                                                              ");
		sqlBuild.append("    order by to_char(RD.TRANS_DATE, 'yyyymm'), RD.currency_code) REVALUATE_RESULT  ");
		jt.execute(sqlBuild.toString());

	}
	
	
	public RevaluationValidModel validMonthEndExhangeRate(RevaluationValidModel revaluationValidModel,long revaluationId) {
		JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
		StringBuilder sqlBuild = new StringBuilder(" ");
	        sqlBuild.append("  select RD.Currency_Code                         "); 
			sqlBuild.append("    from T_RI_ACCT_REVALUATE_DETAIL RD            "); 
			sqlBuild.append("   where RD.CURRENCY_CODE not in                  "); 
			sqlBuild.append("         (select ER.Base_Currency_Code            "); 
			sqlBuild.append("            from T_RI_BCP_CFG_EXCHANGE_RATE ER    "); 
			sqlBuild.append("           where ER.Rate_TYPE = 2                 "); 
			sqlBuild.append("             and ER.Valid_Date <= to_date(to_char(sysdate, 'yyyy-mm-dd' ),'yyyy-mm-dd')         "); 
			sqlBuild.append("             and ER.Expiry_Date >= to_date(to_char(sysdate, 'yyyy-mm-dd' ),'yyyy-mm-dd'))       "); 
			sqlBuild.append("      and RD.REVALUATION_ID = " + revaluationId + " ");
			sqlBuild.append("      and RD.Currency_Code not in ('" + CurrencyConstants.EXCHANGE_BASE_CURRENCY + "') ");   
			sqlBuild.append("   group by RD.currency_code                      "); 

			List<Map<String, Object>> result = jt.queryForList(sqlBuild.toString());
			StringBuilder currencyCodes  = new StringBuilder("");
			for (Map<String, Object> revaluateResult : result) {
				if (revaluateResult.get("Currency_Code") != null) {
					currencyCodes.append(revaluateResult.get("Currency_Code").toString());
					currencyCodes.append(",");
				}else{
					revaluationValidModel.setCurrencyCodes("true");
					return revaluationValidModel;
				}
			}
			if(result == null||result.size() == 0){
				revaluationValidModel.setCurrencyCodes("true");
				return revaluationValidModel;
			}
			revaluationValidModel.setCurrencyCodes(currencyCodes.toString());
			return revaluationValidModel;
	}
	
	
	public void collectRevaluationDetailRecord(long revaluationID) {
		JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
		StringBuilder sqlBuild = new StringBuilder(" ");
		    sqlBuild.append("  insert into T_RI_ACCT_EXCEL_REVALUATE   ");
		    sqlBuild.append("    (revaluation_excel_id,                       ");
		    sqlBuild.append("    REVALUATION_ID,                       ");
		    sqlBuild.append("     Contract_Code,                          ");
		    sqlBuild.append("     Entry_Code,                           ");
		    sqlBuild.append("     Entry_Item,                           ");
		    sqlBuild.append("     currency_OC,                          ");
		    sqlBuild.append("     amount_OC,                            ");
		    sqlBuild.append("     original_rate,                        ");
	    	sqlBuild.append("     amount_USD,                           ");
		    sqlBuild.append("     current_rate,                         ");
	     	sqlBuild.append("     currentAmount_USD,                    ");
		    sqlBuild.append("     revaluationAmount_USD,                ");
		    sqlBuild.append("     trans_date)                ");
		    sqlBuild.append("    select S_RI_ACCT_REVALUATE_EXCEL_ID.Nextval,REVALUATE_RESULT.* from (  ");
	        sqlBuild.append("  select                                                                                    ");
	        sqlBuild.append("                     RD.REVALUATION_ID,                                                        ");
			sqlBuild.append("                     RD.Contract_Code,                                                        ");
			sqlBuild.append("                     RD.Entry_Code,                                                         ");
			sqlBuild.append("                     RD.Entry_Item,                                                         ");
			sqlBuild.append("                     RD.Currency_Code as currency_OC,                                       ");
			sqlBuild.append("                     RD.BALANCE * RD.sign as amount_OC,                                     ");
			sqlBuild.append("                     ER1.Rate as original_rate,                                             ");
			sqlBuild.append("                     round( RD.BALANCE * RD.sign/ER1.Rate ,4) as amount_USD,                ");
			sqlBuild.append("                     ER2.Rate as current_rate,                                              ");
			sqlBuild.append("                     round(RD.BALANCE * RD.sign/ER2.Rate,4) as currentAmount_USD,            ");
			sqlBuild.append("                    round(((round(1/ER2.Rate,9) - round(1/ER1.Rate,9)) * RD.BALANCE * RD.sign),4) as revaluationAmount_USD,  ");
			sqlBuild.append("                     RD.trans_date                                                          ");
			sqlBuild.append("                from T_RI_ACCT_REVALUATE_DETAIL RD,                                         ");
			sqlBuild.append("                     T_RI_ACCT_REVALUATE R,                                                 ");
			sqlBuild.append("                     T_RI_BCP_CFG_EXCHANGE_RATE ER1,                                        ");
			sqlBuild.append("                     T_RI_BCP_CFG_EXCHANGE_RATE ER2                                         ");
			sqlBuild.append("               where RD.Currency_Code = ER1.base_currency_code                                ");
			sqlBuild.append("                 and R.REVALUATION_ID = RD.Revaluation_Id                                   ");
			sqlBuild.append("                 and R.Status = 1                                                           ");
			sqlBuild.append("                 and ER1.Rate_Type = 1                                                      ");
			sqlBuild.append("                 and RD.TRANS_DATE >= ER1.Valid_Date                                        ");
			sqlBuild.append("                 and RD.TRANS_DATE <= ER1.Expiry_Date                                       ");
			sqlBuild.append("                 and RD.Currency_Code = ER2.base_currency_code                                ");
			sqlBuild.append("                 and ER2.Valid_Date <= to_date(to_char(sysdate, 'yyyy-mm-dd' ),'yyyy-mm-dd')                                              ");
			sqlBuild.append("                 and ER2.Expiry_Date >= to_date(to_char(sysdate, 'yyyy-mm-dd' ),'yyyy-mm-dd')                                             ");
			sqlBuild.append("                 and ER2.Rate_Type = 2                                                      ");
			sqlBuild.append("                 and R.REVALUATION_ID = " + revaluationID + "                               ");
			sqlBuild.append("               order by to_char(RD.TRANS_DATE, 'yyyymm'), RD.currency_code  ) REVALUATE_RESULT  ");
		jt.execute(sqlBuild.toString());

	}
	
	public List<RevaluationDetailModel> getRevaluationExcelRecord(long revaluationID) {
		JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
		StringBuilder sqlBuild = new StringBuilder(" ");
	        sqlBuild.append("  select * from T_RI_ACCT_EXCEL_REVALUATE R                                                  ");
			sqlBuild.append("               where  R.REVALUATION_ID = " + revaluationID + "                               ");
			sqlBuild.append("               order by to_char(R.TRANS_DATE, 'yyyymm'), R.currency_OC                  ");
		jt.execute(sqlBuild.toString());
		List<Map<String, Object>> result = jt.queryForList(sqlBuild.toString());
		List<RevaluationDetailModel> revalResultList = new ArrayList<RevaluationDetailModel>();
		for (Map<String, Object> revaluateResult : result) {
			if (revaluateResult.get("REVALUATION_ID") != null) {
				RevaluationDetailModel  revaluateDetail = new RevaluationDetailModel();
				revaluateDetail.setRevaluationAmountUSD(new BigDecimal(revaluateResult.get("revaluationAmount_USD").toString()));
				revaluateDetail.setCurrentAmountUSD(new BigDecimal(revaluateResult.get("currentAmount_USD").toString()));
				revaluateDetail.setCurrentRate(new BigDecimal(revaluateResult.get("current_rate").toString()));
				revaluateDetail.setAmountUSD(new BigDecimal(revaluateResult.get("amount_USD").toString()));
				revaluateDetail.setOriginalRate(new BigDecimal(revaluateResult.get("original_rate").toString()));
				revaluateDetail.setAmountOC(new BigDecimal(revaluateResult.get("amount_OC").toString()));
				revaluateDetail.setCurrencyOC(revaluateResult.get("currency_OC").toString());
				revaluateDetail.setEntryItem(revaluateResult.get("Entry_Item").toString());
				revaluateDetail.setEntryCode(revaluateResult.get("Entry_Code").toString());
				revaluateDetail.setContractID((String) revaluateResult.get("Contract_Code"));
				revalResultList.add(revaluateDetail);
			}
		}
		return revalResultList;

	}

	public void generateAcctFeeForRevaluate(long revaluationId) throws Exception {

		// reversal the previous revaluate transaction
		TRiAcctFee riAcctFee = new TRiAcctFee();
		riAcctFee.setBusiType(1);
		reversalPreRevaluation(riAcctFee, revaluationId);
		generateRevaluateFee(revaluationId, getTotalRevaluationValue(revaluationId), riAcctFee);
		riAcctFeeDao.insert(riAcctFee);
		em.flush();
		postFN(riAcctFee);
	}

	public void reversalPreRevaluation(TRiAcctFee riAcctFee, long revaluationId) {
		TRiAcctFeeDetail condition = new TRiAcctFeeDetail();
		Long busiID = findPrevaluationList4Reversal(revaluationId);
		if (busiID !=0) {
			condition.setBusiId(busiID);
			List<TRiAcctFeeDetail> riAcctFeeDetailList = riAcctFeeDetailDao.findList(condition);
			if (riAcctFeeDetailList.size() != 0) {
				TRiAcctFeeDetail riAcctFeeDetailResult = riAcctFeeDetailList.get(0);
				TRiAcctFeeDetail riAcctFeeDetail = new TRiAcctFeeDetail(riAcctFee);
				BeanUtils.copyProperties(riAcctFeeDetail, riAcctFeeDetailResult);
				if(riAcctFeeDetailResult.getAmount()!=null){
					riAcctFeeDetail.setAmount(riAcctFeeDetailResult.getAmount().negate());
				}				
				riAcctFeeDetail.setDetailId(null);
			}
		}
		setReversalStatus4PreRevaluation(revaluationId);
	}

	public Long findPrevaluationList4Reversal(long revaluationId) {
		long buisId = 0l;
		JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
		StringBuilder sqlBuild = new StringBuilder(" ");
		sqlBuild.append(" select max(fd.busi_id) as busi_id  from t_ri_acct_fee_detail fd where fd.entry_code = '1234'  "); // 1:valid,2:reversal
		List<Map<String, Object>> result = jt.queryForList(sqlBuild.toString());
		for (Map<String, Object> revaluateResult : result) {
			if (revaluateResult.get("busi_id") != null) {
				buisId = Long.parseLong(revaluateResult.get("busi_id").toString());
				return buisId;
			}
		}
		return buisId;
	}

	public void setReversalStatus4PreRevaluation(long revaluationId) {
		YearQuarter yearQuarter = new YearQuarter(AppContext.getSysDate());
		
		JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
		StringBuilder sqlBuild = new StringBuilder(" ");		
		sqlBuild.append(" delete t_Ri_Acct_Revaluate_Result RT         "); 
		sqlBuild.append("  where RT.Revaluation_Id in                  "); 
		sqlBuild.append("        (select RR.Revaluation_Id             "); 
		sqlBuild.append("           from T_RI_ACCT_REVALUATE RR        "); 
		sqlBuild.append("          where 1=1               "); 
		sqlBuild.append("            and RR.Fn_Quarter = '" + yearQuarter.toString() + "' ");
		sqlBuild.append("            and RR.Revaluation_Id <>" + revaluationId + " )");	
		
		StringBuilder sqlBuild1 = new StringBuilder(" ");		
		sqlBuild1.append(" delete t_Ri_Acct_Revaluate_Detail RD         "); 
		sqlBuild1.append("  where RD.Revaluation_Id in                  "); 
		sqlBuild1.append("        (select RR.Revaluation_Id             "); 
		sqlBuild1.append("           from T_RI_ACCT_REVALUATE RR        "); 
		sqlBuild1.append("          where 1=1               "); 
		sqlBuild1.append("            and RR.Fn_Quarter = '" + yearQuarter.toString() + "' ");
		sqlBuild1.append("            and RR.Revaluation_Id <>" + revaluationId + " )");	
		
		StringBuilder sqlBuild2 = new StringBuilder(" ");		
		sqlBuild2.append("  delete T_RI_ACCT_REVALUATE R         "); 
		sqlBuild2.append("  where R.Revaluation_Id in                  "); 
		sqlBuild2.append("        (select RR.Revaluation_Id             "); 
		sqlBuild2.append("           from T_RI_ACCT_REVALUATE RR        "); 
		sqlBuild2.append("          where 1=1               "); 
		sqlBuild2.append("            and RR.Fn_Quarter = '" + yearQuarter.toString() + "' ");
		sqlBuild2.append("            and RR.Revaluation_Id <>" + revaluationId + ") ");	
		
		jt.execute(sqlBuild.toString());
		jt.execute(sqlBuild1.toString());
		jt.execute(sqlBuild2.toString());

	}
	
	public void deleteReversalStatus4Valid(long revaluationId) {
		
		JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
		
		StringBuilder sqlBuild1 = new StringBuilder(" ");		
		sqlBuild1.append(" delete t_Ri_Acct_Revaluate_Detail RD         "); 
		sqlBuild1.append("  where 1=1                  "); 
		sqlBuild1.append("  and RD.Revaluation_Id =" + revaluationId + " ");	
		
		StringBuilder sqlBuild2 = new StringBuilder(" ");		
		sqlBuild2.append("  delete T_RI_ACCT_REVALUATE R         "); 
		sqlBuild2.append("  where 1=1                            "); 
		sqlBuild2.append("  and R.Revaluation_Id =" + revaluationId + " ");	
		
		
		jt.execute(sqlBuild1.toString());
		jt.execute(sqlBuild2.toString());

	}

	private final String REVALUATION_VALUE = "AMOUNT";
	private final String FN_MONTH = "FN_MONTH";

	public BigDecimal getTotalRevaluationValue(long revaluationId) {
		JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
		StringBuilder sqlBuild = new StringBuilder(" ");
		sqlBuild.append(" select sum(RR.EX_DIFF) as AMOUNT     ");
		sqlBuild.append("    from T_RI_ACCT_REVALUATE_RESULT RR      ");
		sqlBuild.append("      where RR.REVALUATION_ID = " + revaluationId + "  ");
		List<Map<String, Object>> result = jt.queryForList(sqlBuild.toString());
		BigDecimal totalRevaluateValue = BigDecimal.ZERO;
		BigDecimal revaluateValue = (BigDecimal) result.get(0).get(REVALUATION_VALUE);
		if(revaluateValue!=null){
			totalRevaluateValue = revaluateValue;
		}
		return totalRevaluateValue;
	}
	
	public RevaluateInfoModel getRevaluationValueByFN() {
		JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
		StringBuilder sqlBuild = new StringBuilder(" ");
		sqlBuild.append(" select sum(RR.EX_DIFF) as AMOUNT, to_date(RR.FN_MONTH,'yyyymm') as FN_MONTH     ");
		sqlBuild.append("    from T_RI_ACCT_REVALUATE_RESULT RR,T_RI_ACCT_REVALUATE R      ");
		sqlBuild.append("      where RR.REVALUATION_ID = R.Revaluation_Id  ");
		sqlBuild.append("      and R.Status = 1  ");
		sqlBuild.append("    group by RR.FN_MONTH      ");
		List<Map<String, Object>> result = jt.queryForList(sqlBuild.toString());
		BigDecimal totalRevaluateValue = BigDecimal.ZERO;
		totalRevaluateValue = (BigDecimal) result.get(0).get(REVALUATION_VALUE);
		RevaluateInfoModel revaluateInfoModel = new RevaluateInfoModel();
		Date fnYQ = (Date)result.get(0).get(FN_MONTH);
		YearQuarter yq = new YearQuarter(fnYQ);
		revaluateInfoModel.setFNQuarter(yq.toString());
		 String totalRevaluateValueStr = NumberFormat.getNumberInstance().format(totalRevaluateValue);
		revaluateInfoModel.setRevaluateValue(totalRevaluateValueStr);
		revaluateInfoModel.setCurrencyCode(CurrencyConstants.EXCHANGE_BASE_CURRENCY);
		return revaluateInfoModel;
	}

	public void generateRevaluateFee(long revaluationId, BigDecimal totalRevaluateValue, TRiAcctFee riAcctFee) {
		TRiAcctFeeDetail riAcctFeeDetail = new TRiAcctFeeDetail(riAcctFee);
		riAcctFeeDetail.setEntryCode("1234");// TODO revaluate code
		riAcctFeeDetail.setCurrency(CurrencyConstants.EXCHANGE_BASE_CURRENCY);
		riAcctFeeDetail.setAmount(totalRevaluateValue);
		riAcctFeeDetail.setBusiId(revaluationId);
	}

	public void postFN(TRiAcctFee riAcctFee) throws Exception {
		List<BusinessFeeModel> modelList = new ArrayList<BusinessFeeModel>();
		BusinessFeeModel feeModel = new BusinessFeeModel();
		feeModel.setBizTransType(BizTransactionType.RE_VALUATION.getType());
		feeModel.setPartnerCode("");
		List<BusinessFee> bizFeeList = new ArrayList<BusinessFee>();
		for (TRiAcctFeeDetail acctFeeDetail : riAcctFee.getTRiAcctFeeDetails()) {
			feeModel.setBizTransNo(acctFeeDetail.getBusiId().toString());
			//feeModel.setContractId(Long.valueOf(acctFeeDetail.getBusiId().toString()));
			// build the fee info
			// fee.setEntryCode(acctFeeDetail.getEntryCode());
			feeModel.setBizTransId(Long.valueOf(acctFeeDetail.getBusiId().toString()));
			BusinessFee fee = new BusinessFee();
			fee.setEntryCode(FinanceConsistants.GL_ENTRY_CODE_REVALUATION);
			fee.setCurrencyCode(acctFeeDetail.getCurrency());
			fee.setTotalPeriod(1);
			fee.setCurrentPeriod(1);
			fee.setAmount(acctFeeDetail.getAmount());
			fee.setDueDate(null);
			fee.setEstimation(false);
			fee.setNeedPost(true);
			fee.setBizTransDesc("revaluation");
			feeModel.setFeeList(bizFeeList);
			bizFeeList.add(fee);
		}
		modelList.add(feeModel);
		try {
			// Write data to finace
			financeService.writeToFinance(modelList);
		} catch (Exception e) {
			//e.printStackTrace();
			throw e;
		}
	}



	
	public RevaluationDetailModel generateExcel(long revaluationID) throws Exception {
	//  	SimpleDateFormat fymd = new SimpleDateFormat ("yyyyMMddhhmm");
	//  	SimpleDateFormat fymd1 = new SimpleDateFormat ("yyyyMMdd");
	//  	String sDate = fymd.format(AppContext.getSysDate());
	//  	String sDate1 = fymd1.format(AppContext.getSysDate());
	  	 String envPath = Env.getParameter("FILE_PATH");
         String envSeparator = Env.getParameter("SEPARATOR");
         StringBuffer filePath = new StringBuffer(envPath);
    //     filePath.append("REVALUATION").append(envSeparator).append(sDate1);
         filePath.append("REVALUATION");
		String fileName = "REVALUATION_"+revaluationID + ".xls";
		File files = new File(filePath.toString());
		String path = files.getAbsolutePath();
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
		OutputStream file = new FileOutputStream(fil);
		try {
			List<RevaluationDetailModel> revalResultList = getRevaluationExcelRecord(revaluationID);
			GenerateExcel generateExcel = new GenerateExcel();
			ByteArrayOutputStream outByteStream = generateExcel.revaluateExcelGenerate(revalResultList);
			byte[] outArray = outByteStream.toByteArray();
			file.write(outArray);	
		} finally {
			file.close();		
		}
		RevaluationDetailModel revalDetailModel = new RevaluationDetailModel();
		return revalDetailModel;
	}
	

	
	public RevaluationDetailModel getRevaluationExcelPath() {
		JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
		StringBuilder sqlBuild = new StringBuilder(" ");
		sqlBuild.append(" select  Revaluation_Id    ");
		sqlBuild.append("    from T_RI_ACCT_REVALUATE R      ");
		sqlBuild.append("      where 1=1  ");
		sqlBuild.append("      and R.Status = 1  ");
		List<Map<String, Object>> result = jt.queryForList(sqlBuild.toString());
		BigDecimal revaluationID = (BigDecimal) result.get(0).get("Revaluation_Id");	
		StringBuffer exportPath = new StringBuffer("fileUpload/REVALUATION/");
		String fileName = "REVALUATION_"+revaluationID + ".xls";
		exportPath.append(fileName);
		
		RevaluationDetailModel revalDetailModel = new RevaluationDetailModel();
		revalDetailModel.setFilePath(exportPath.toString());
		return revalDetailModel;
	}
	
	public Page<RevaluateInfoModel> revaluationSearch(String FNYear, String FNQuarter,String status) throws ParseException {
		 Page<RevaluateInfoModel> page = new Page<RevaluateInfoModel>();
		 List<RevaluateInfoModel> revaluateInfoList =  getRevaluationByFN(FNYear,FNQuarter,status);
	        int start = 1;
	        page.setStart(start);
	        page.setCountPerPage(10);	        
	        page.setRows(revaluateInfoList);
	        long results = revaluateInfoList.size();
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
	public List<RevaluateInfoModel> getRevaluationByFN(String FNYear, String FNQuarter,String status) throws ParseException {
		List<RevaluateInfoModel> revaluateInfoList = new ArrayList<RevaluateInfoModel>();
		String FN = "";
		String executeStatus = "0";
		if("undefined".equals(FNYear)||("0".equals(FNYear)&&"0".equals(FNQuarter))){
			FN = "%%";
		}else if(!"0".equals(FNYear)&&!"0".equals(FNQuarter)){
			FN = FNYear+"Q"+FNQuarter+"%";
		}else if("0".equals(FNYear)){
			FN = "%Q"+FNQuarter+"%";
		}else if("0".equals(FNQuarter)){
			FN = FNYear+"%";
		}
		
		if("1".equals(status)&&"1".equals(status)){
			executeStatus = "0";
		}else if("2".equals(status)&&"2".equals(status)){
			executeStatus = "1";
		}
		JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
		StringBuilder sqlBuild = new StringBuilder(" ");
		sqlBuild.append(" select *  ");
		sqlBuild.append("    from T_RI_ACCT_REVALUATE R      ");
		sqlBuild.append("      where 1=1  ");
	    sqlBuild.append("  and  R.Fn_Quarter Like '"+FN+"'  ");
	    if(!("0".equals(status)&&"0".equals(status))){
	    	  sqlBuild.append("  and  R.execute_status = '"+executeStatus+"'  ");
		}
		sqlBuild.append("   order by R.Insert_Time desc    ");
		List<Map<String, Object>> result = jt.queryForList(sqlBuild.toString());
		 for (Map<String, Object> revaluationResult : result) {
		       	if(revaluationResult.get("revaluation_id")!=null){
		       		RevaluateInfoModel revaluateInfoModel = new RevaluateInfoModel();
		       		String Reval = revaluationResult.get("revaluation_id").toString();
		       		BigDecimal totalReval = getTotalRevaluationValue(Long.parseLong(Reval));
		       		revaluateInfoModel.setRevaluateValue(totalReval.toString());
		       		revaluateInfoModel.setRevaluationId(Long.parseLong(Reval));
		       		String FNYearAndQuarter = (String) revaluationResult.get("fn_quarter");
		       		revaluateInfoModel.setFNYear(FNYearAndQuarter.substring(0, 4));
		       		revaluateInfoModel.setFNQuarter("Q"+FNYearAndQuarter.substring(5, 7));
		       		revaluateInfoModel.setExecuteDate((Date) revaluationResult.get("insert_time"));
		       		revaluateInfoModel.setStartingData((Date) revaluationResult.get("starting_time"));
		       		revaluateInfoModel.setFinishingData((Date) revaluationResult.get("finishing_time"));
		       		revaluateInfoModel.setOperator((String) revaluationResult.get("operator"));
		       		revaluateInfoModel.setExecuteStatus((String) revaluationResult.get("execute_status"));
		       		revaluateInfoList.add(revaluateInfoModel);
		       	}
		 }
		
		return revaluateInfoList;
	}
	
	public boolean getExecutedFlag() {
		boolean executedFlag = false;
		YearQuarter yq = new YearQuarter(AppContext.getSysDate());
		String FN = yq.toString();
		JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
		StringBuilder sqlBuild = new StringBuilder(" ");
		sqlBuild.append(" select *  ");
		sqlBuild.append("    from T_RI_ACCT_REVALUATE R      ");
		sqlBuild.append("      where 1=1  ");
	    sqlBuild.append("  and  R.Fn_Quarter = '"+FN+"'  ");
		List<Map<String, Object>> result = jt.queryForList(sqlBuild.toString());
		 for (Map<String, Object> revaluationResult : result) {
		       	if(revaluationResult.get("revaluation_id")!=null){
		       		executedFlag = true;
		       	}
		 }
		return executedFlag;
	}

	@Override
	public <T> List bizProcess(Map model) {
		String revaluationID =  (String) model.get("refId");
		List<RevaluationDetailModel> revalResultList = getRevaluationExcelRecord(Long.parseLong(revaluationID));
		return revalResultList;
	}
	
	public RevaluationValidModel validBefore() throws Exception {
		 //valid the quarter closing date
		RevaluationValidModel revaluationValidModel = new RevaluationValidModel();
        if (accountingServiceImpl.inClosingPeriod()) {
        	revaluationValidModel.setInClosingPeriod("true");
        } else {
        	revaluationValidModel.setInClosingPeriod("false");
        }
        
        if(getExecutedFlag()){
        	revaluationValidModel.setExecutedFlag("true");
        }else{
        	revaluationValidModel.setExecutedFlag("false");
        }
        
        validRevaluationMonthEndExchangeRate(revaluationValidModel);
        
		return revaluationValidModel;
	}
}
