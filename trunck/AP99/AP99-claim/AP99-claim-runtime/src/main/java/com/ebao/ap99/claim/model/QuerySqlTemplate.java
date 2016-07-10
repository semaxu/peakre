/**
 * 
 */
package com.ebao.ap99.claim.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author yujie.zhang
 *
 */
public class QuerySqlTemplate {

//	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD");
	 private SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");

	public String getQueryClaimSql(ClaimQuery claimquery) {

		StringBuilder sql = new StringBuilder();

		sql.append(" select t.claim_no,t.Event_code as eventId,t.Date_Of_Loss_From,t.Date_Of_Loss_to,              "
				 + " t.Cause_Of_Loss,t.Status,t.Loss_Description,t.claim_id,t.task_owner,t.OPERATING_BY "
				 + " from t_ri_claim_info t where 1=1 ");
		//sql.append("select * from t_ri_claim_info t where 1=1 ");

		sql = getClaimQuerySqlTmp(sql, claimquery);
		sql.append(" order by t.claim_id  desc");

		return sql.toString();

	}

	public String getQueryClaimCount(ClaimQuery claimquery) {

		StringBuilder sql = new StringBuilder();
		sql.append("select count(1) from t_ri_claim_info t where 1=1 ");
		sql = getClaimQuerySqlTmp(sql, claimquery);
		return sql.toString();

	}

	public StringBuilder getClaimQuerySqlTmp(StringBuilder sql, ClaimQuery claimquery) {

		if (StringUtils.isNotBlank(claimquery.getClaimNo())) {
			sql.append(" and t.claim_No like ? ");
			// '").append(claimquery.getClaimNo()).append("
		}
		if (StringUtils.isNotBlank(claimquery.getEventId())) {
			sql.append(" and t.event_Code=? ");
		}
		if (StringUtils.isNotBlank(claimquery.getClaimBranch())) {
			sql.append(" and t.claim_Branch=? ");
		}
		if (StringUtils.isNotBlank(claimquery.getContractID())) {
			sql.append(" and t.claim_id in ( select ref_id from T_Ri_Claim_Section_Info where contract_Code=? ) ");
		}
		if (StringUtils.isNotBlank(claimquery.getUnderwritingYear())) {
			sql.append(" and t.claim_id in ( select ref_id from T_RI_CLAIM_SECTION_INFO where UW_YEAR=? ) ");
		}
		if (StringUtils.isNotBlank(claimquery.getCedantName())) {
			sql.append(" and t.claim_id in ( select ref_id from T_RI_CLAIM_SECTION_INFO where cedent_Name like ? ) ");
		}
		
		if (StringUtils.isNotBlank(claimquery.getCedantCountry())) {
			sql.append(" and t.claim_id in ( select ref_id from T_RI_CLAIM_SECTION_INFO where cedent_Country=? ) ");
		}
		// lossfrom
		if (StringUtils.isNotBlank(claimquery.getDateOfLossFrom())) {
			// sql.append(" and t.date_of_loss_from>=to_date(?,'YYYY-MM-DD') ");
			sql.append(" and t.date_of_loss_from >= ? ");
		}
		// lossto
		if (StringUtils.isNotBlank(claimquery.getDateOfLossTo())) {
			sql.append(" and t.date_of_loss_from <= ? ");
		}

		if (StringUtils.isNotBlank(claimquery.getCauseOfLoss())) {
			sql.append(" and t.cause_Of_Loss like ? ");
		}

		if (StringUtils.isNotBlank(claimquery.getStatus())) {
			sql.append(" and t.status=? ");
		}
		if (StringUtils.isNotBlank(claimquery.getTaskOwner())) {
			sql.append(" and t.task_Owner=? ");
		}
		if (StringUtils.isNotBlank(claimquery.getLossDescription())) {
			sql.append(" and t.loss_Description like ? ");
		}
		return sql;
	}

	public Object getClaimParam(ClaimQuery claimquery) throws Exception {

		List<Object> param = new ArrayList<Object>();

		if (StringUtils.isNotBlank(claimquery.getClaimNo())) {
			// '").append(claimquery.getClaimNo()).append("
			param.add("%"+claimquery.getClaimNo()+"%");
		}
		if (StringUtils.isNotBlank(claimquery.getEventId())) {
			param.add(claimquery.getEventId());
		}
		if (StringUtils.isNotBlank(claimquery.getClaimBranch())) {
			param.add(claimquery.getClaimBranch());
		}
		if (StringUtils.isNotBlank(claimquery.getContractID())) {
			param.add(claimquery.getContractID());

		}
		if (StringUtils.isNotBlank(claimquery.getUnderwritingYear())) {
			param.add(claimquery.getUnderwritingYear());
		}
		if (StringUtils.isNotBlank(claimquery.getCedantName())) {
			param.add("%"+claimquery.getCedantName()+"%");
		}

		if (StringUtils.isNotBlank(claimquery.getCedantCountry())) {
			param.add(claimquery.getCedantCountry());

		}
		// lossfrom
		if (StringUtils.isNotBlank(claimquery.getDateOfLossFrom()) ) {
			
			param.add((Date)formatter.parse(claimquery.getDateOfLossFrom().replace("T", " ")));
		}
		// lossto
		if (StringUtils.isNotBlank(claimquery.getDateOfLossTo())) {

			param.add((Date)formatter.parse(claimquery.getDateOfLossTo().replace("T", " ")));
		}

		if (StringUtils.isNotBlank(claimquery.getCauseOfLoss())) {
			param.add("%"+claimquery.getCauseOfLoss()+"%");

		}

		if (StringUtils.isNotBlank(claimquery.getStatus())) {
			param.add(claimquery.getStatus());

		}
		if (StringUtils.isNotBlank(claimquery.getTaskOwner())) {
			param.add(claimquery.getTaskOwner());
		}

		if (StringUtils.isNotBlank(claimquery.getLossDescription())) {
			param.add("%"+claimquery.getLossDescription()+"%");

		}

		Object[] params = new Object[param.size()];
		param.toArray(params);
		return params;
	}

	public String getQueryEventSql(EventInfo eventInfo) {

		StringBuilder sql = new StringBuilder();

		sql.append("select * from t_ri_claim_event t where 1=1 ");
		sql = getEventQuerySqlTmp(sql, eventInfo);
		sql.append(" order by t.event_id  desc");

		return sql.toString();

	}

	public String getQueryEventCount(EventInfo eventInfo) {

		StringBuilder sql = new StringBuilder();
		sql.append("select count(1) from t_ri_claim_event t where 1=1 ");
		sql = getEventQuerySqlTmp(sql, eventInfo);
		return sql.toString();

	}

	public StringBuilder getEventQuerySqlTmp(StringBuilder sql, EventInfo eventInfo) {

		if (StringUtils.isNotBlank(eventInfo.getClaimNo())) {
			sql.append(" and t.event_id in (select event_code from t_ri_claim_info where claim_no like ? ) ");
			// '").append(claimquery.getClaimNo()).append("
		}
		if (StringUtils.isNotBlank(eventInfo.getEventCode())) {
			sql.append(" and t.event_Code like ? ");
		}
		if (StringUtils.isNotBlank(eventInfo.getContractCode())) {
			sql.append(" and t.event_id in ( select ref_id from T_Ri_Claim_Section_Info where contract_Code=? ) ");
		}
		if (StringUtils.isNotBlank(eventInfo.getCedentName())) {
			sql.append(" and t.event_id in ( select ref_id from T_RI_CLAIM_SECTION_INFO where cedent_Name like ? ) ");
		}

		// lossfrom
		//StringUtils.is
		if (null!= eventInfo.getDateOfLossFrom() ) {
			// sql.append(" and t.date_of_loss_from>=to_date(?,'YYYY-MM-DD') ");
			sql.append(" and t.date_of_loss_from >= ? ");
		}
		// lossto
		if (null!=eventInfo.getDateOfLossTo()) {
			sql.append(" and t.date_of_loss_from <= ? ");
		}

		if (StringUtils.isNotBlank(eventInfo.getCauseOfLoss())) {
			sql.append(" and t.cause_Of_Loss like ? ");
		}

		if (StringUtils.isNotBlank(eventInfo.getTaskOwner())) {
			sql.append(" and t.task_Owner = ? ");
		}
		if (StringUtils.isNotBlank(eventInfo.getLossDesc())) {
			sql.append(" and t.loss_Desc like ? ");
		}
		return sql;
	}

	public Object getEventParam(EventInfo eventInfo) throws Exception {

		List<Object> param = new ArrayList<Object>();

		if (StringUtils.isNotBlank(eventInfo.getClaimNo())) {
			// '").append(claimquery.getClaimNo()).append("
			param.add("%"+eventInfo.getClaimNo()+"%");
		}
		if (StringUtils.isNotBlank(eventInfo.getEventCode())) {
			param.add("%"+eventInfo.getEventCode()+"%");
		}

		if (StringUtils.isNotBlank(eventInfo.getContractCode())) {
			param.add(eventInfo.getContractCode());

		}
		if (StringUtils.isNotBlank(eventInfo.getCedentName())) {
			param.add("%"+eventInfo.getCedentName()+"%");

		}
		// lossfrom
		if (null!=eventInfo.getDateOfLossFrom()) {
			// String formattedDate 
			param.add(eventInfo.getDateOfLossFrom());
		}
		// lossto
		if (null!=eventInfo.getDateOfLossTo()) {

			param.add(eventInfo.getDateOfLossTo());
		}

		if (StringUtils.isNotBlank(eventInfo.getCauseOfLoss())) {
			param.add("%"+eventInfo.getCauseOfLoss()+"%");

		}

		if (StringUtils.isNotBlank(eventInfo.getTaskOwner())) {
			param.add(eventInfo.getTaskOwner());
		}

		if (StringUtils.isNotBlank(eventInfo.getLossDesc())) {
			param.add("%"+eventInfo.getLossDesc()+"%");

		}

		Object[] params = new Object[param.size()];
		param.toArray(params);
		return params;
	}

}
