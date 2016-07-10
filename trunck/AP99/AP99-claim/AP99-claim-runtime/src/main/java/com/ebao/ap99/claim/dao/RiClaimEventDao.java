/**
 * 
 */
package com.ebao.ap99.claim.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.ebao.ap99.claim.entity.TRiClaimEvent;
import com.ebao.ap99.claim.entity.TRiClaimInfo;
import com.ebao.ap99.claim.model.ClaimRecords;
import com.ebao.ap99.claim.model.EventInfo;
import com.ebao.ap99.parent.model.TreeModel;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * @author yujie.zhang
 *
 */
public class RiClaimEventDao extends BaseDao<TRiClaimEvent>{
	
	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiClaimEvent> getEntityClass() {
		return TRiClaimEvent.class;
	}
	
	public List<TreeModel> getEventCodeList(){
		List<TreeModel> eventCodes = new ArrayList<>();
		
		List<TRiClaimEvent>  eventList = this.loadAll();
		
		for(TRiClaimEvent e:eventList){
			TreeModel codeModel = new TreeModel();
			codeModel.setId(e.getEventId());
			codeModel.setText(e.getEventCode());
			eventCodes.add(codeModel);
		}
		
		return eventCodes;
	}
	
	public List<String> getEventCode() {
		
       List<String> eventCodes = new ArrayList<>();
		
		List<TRiClaimEvent>  eventList = this.loadAll();
		
		for(TRiClaimEvent e:eventList){
			eventCodes.add(e.getEventCode());
		}
		
		return eventCodes;
	}

	
	public List<ClaimRecords> getClaimRecords(EventInfo eventInfo){

		List<Object> param = new ArrayList<Object>();
		param.add(eventInfo.getEventId());
		//param.add(settlementQuery.getBusinessDirection());

		StringBuilder sql = new StringBuilder();

		sql.append(" select s.section_name as sectionId,tmp.claim_id as claimId,                                           ");   
		sql.append(" tmp.claim_no as claimNo ,tmp.update_time as dateOfLostUpdate,tmp.status ,                             ");   
		sql.append(" (select nvl(sum(a.org_amount_usd), 0)                                                                 ");   
		sql.append("   from t_Ri_Claim_Reserve a                                                                           ");   
		sql.append("   where a.section_id = tmp.section_id                                                                 ");   
		sql.append("    and a.ref_id = tmp.claim_id                                                                        ");   
		sql.append("     and a.business_direction = 1                                                                      ");   
		sql.append("     and a.reserve_type in ('1', '2', '3')) as lossReverse ,                                           ");   
		sql.append(" (select nvl(sum(c.amount_usd), 0)                                                                     ");   
		sql.append("    from t_Ri_Claim_Settlement b, t_Ri_Claim_Settlement_Item c                                         ");   
		sql.append("   where b.settlement_id = c.settlement_id                                                             ");   
		sql.append("     and b.ref_id = tmp.claim_id                                                                       ");   
		sql.append("     and b.status = 3                                                                                  ");   
		sql.append("     and c.section_id = tmp.section_id                                                                 ");   
		sql.append("     and c.business_direction = 1                                                                      ");   
		sql.append("     and c.settlement_type in ('1', '2')) as lossPaid,                                                 ");   
		sql.append("     (select nvl(sum(a.org_amount_usd), 0)                                                             ");   
		sql.append("    from t_Ri_Claim_Reserve a                                                                          ");   
		sql.append("   where a.retro_ref_section_id = tmp.section_id                                                       ");   
		sql.append("     and a.ref_id = tmp.claim_id                                                                       ");   
		sql.append("     and a.business_direction =2                                                                       ");   
		sql.append("     and a.reserve_type in ('1', '2', '3')) as retroOS,                                                ");   
		sql.append(" (select nvl(sum(c.amount_usd), 0)                                                                     ");   
		sql.append("    from t_Ri_Claim_Settlement b, t_Ri_Claim_Settlement_Item c                                         ");   
		sql.append("   where b.settlement_id = c.settlement_id                                                             ");   
		sql.append("     and b.ref_id = tmp.claim_id                                                                       ");   
		sql.append("     and b.status = 3                                                                                  ");   
		sql.append("     and c.retro_ref_section_id = tmp.section_id                                                       ");   
		sql.append("     and c.business_direction = 2                                                                      ");   
		sql.append("     and c.settlement_type in ('1', '2')) as retroPaid,s.broker_name as broker,                        ");   
		sql.append("     s.cedent_name as cedent ,s.contract_code as contractId                                            ");   
		sql.append("  from (select distinct tr.section_id, tc.claim_id,                                                    ");   
		sql.append("        tc.claim_no,tc.update_time,tc.status                                                           ");   
		sql.append("         from t_ri_claim_info tc left join t_Ri_Claim_Reserve tr                                       ");   
		sql.append("         on tc.claim_id = tr.ref_id and tr.business_direction = 1                                      ");   
		sql.append("        where tc.event_code = ? ) tmp left join t_Ri_Claim_Section_Info s                                ");   
		sql.append("         on tmp.claim_id = s.ref_id  and tmp.section_id = s.section_id and s.business_direction = 1    ");
		sql.append("        where 1=1                                                                                      ");
		
		if (StringUtils.isNotBlank(eventInfo.getContractCode())) {
			sql.append("       and s.contract_code like ?                                                                  ");   
			param.add("%"+eventInfo.getContractCode()+"%");
		}
		
		if (StringUtils.isNotBlank(eventInfo.getBrokerName())) {
			sql.append("       and s.broker_name like ?                                                                       ");   
			param.add("%"+eventInfo.getBrokerName()+"%");
		}
		
		if (StringUtils.isNotBlank(eventInfo.getCedentName())) {
			sql.append("       and s.cedent_name like ?                                                                       ");   
			param.add("%"+eventInfo.getCedentName()+"%");
		}
		
		if (!"".equals(eventInfo.getUwYear()) && eventInfo.getUwYear() != null ) {
			sql.append("       and s.uw_year = ?                                                                              ");   
			param.add(eventInfo.getUwYear());
		}
		
		if (StringUtils.isNotBlank(eventInfo.getContractType())) {
			sql.append("       and s.contract_id in  (select contract_id from t_ri_contract_info where contract_type =? )      ");   
			param.add(eventInfo.getContractType());
		}


		
		logger.info(sql.toString());

		Object[] params = new Object[param.size()];
		param.toArray(params);

		List<ClaimRecords> claimRecords = this.getJdbcTemplate().query(sql.toString(), params,
				new BeanPropertyRowMapper<ClaimRecords>(ClaimRecords.class));

		// claimRecords
		return claimRecords;
	}

	public List<TreeModel> getClaimNoList(EventInfo eventInfo){
		
		List<Object> param = new ArrayList<Object>();
		param.add(eventInfo.getEventId());
		//param.add(settlementQuery.getBusinessDirection());

		StringBuilder sql = new StringBuilder();
		
		
		sql.append(" select distinct claim_id as id , claim_no as text  ");
		sql.append(" from t_ri_claim_info c,t_ri_claim_section_info s   ");
		sql.append(" where c.claim_id = s.ref_id                        ");
        sql.append(" and c.event_code=?                           ");
        if (StringUtils.isNotBlank(eventInfo.getContractCode())) {
			sql.append("       and s.contract_code like ?                                                                  ");   
			param.add("%"+eventInfo.getContractCode()+"%");
		}
		
		if (StringUtils.isNotBlank(eventInfo.getBrokerName())) {
			sql.append("       and s.broker_name like ?                                                                       ");   
			param.add("%"+eventInfo.getBrokerName()+"%");
		}
		
		if (StringUtils.isNotBlank(eventInfo.getCedentName())) {
			sql.append("       and s.cedent_name like ?                                                                       ");   
			param.add("%"+eventInfo.getCedentName()+"%");
		}
		
		if (!"".equals(eventInfo.getUwYear()) && eventInfo.getUwYear() != null ) {
			sql.append("       and s.uw_year = ?                                                                              ");   
			param.add(eventInfo.getUwYear());
		}
		
		if (StringUtils.isNotBlank(eventInfo.getContractType())) {
			sql.append("       and s.contract_id in  (select contract_id from t_ri_contract_info where contract_type =? )      ");   
			param.add(eventInfo.getContractType());
		}
		
		
		//List<TreeModel> eventCodes = new ArrayList<>();
		
		logger.info(sql.toString());

		Object[] params = new Object[param.size()];
		param.toArray(params);

		List<TreeModel> claimNos = this.getJdbcTemplate().query(sql.toString(), params,
				new BeanPropertyRowMapper<TreeModel>(TreeModel.class));

		
		return claimNos;
	}
	
	public Long getEventIdByEventCode(String eventCode){
		final Query query = getEntityManager().createNamedQuery("TRiClaimEvent.findEventIdByEventCode", TRiClaimEvent.class);
		query.setParameter("eventCode", eventCode);
		@SuppressWarnings("unchecked")
		
		Long eventId = (long) (0);
		@SuppressWarnings("unchecked")
		List<TRiClaimEvent> list = query.getResultList();
		if(CollectionUtils.isNotEmpty(list)){
			eventId = list.get(0).getEventId();
		}
		
		return eventId;
	}
}
