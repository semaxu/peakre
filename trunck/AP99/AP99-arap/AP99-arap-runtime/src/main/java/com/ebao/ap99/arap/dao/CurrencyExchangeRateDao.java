package com.ebao.ap99.arap.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.arap.constant.Constants;
import com.ebao.ap99.arap.constant.ExchangeRateStatus;
import com.ebao.ap99.arap.entity.CurrencyExchangeRate;
import com.ebao.ap99.arap.vo.CurrencyExchangeRateCondition;
import com.ebao.unicorn.platform.data.helper.DataHelper;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.Assert;

import reactor.util.CollectionUtils;

public class CurrencyExchangeRateDao extends BaseDao<CurrencyExchangeRate> {
	//private DateFormat df = new SimpleDateFormat("MM/yyyy");
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private DataHelper dataHelper;
	
	@Override
	public Class<CurrencyExchangeRate> getEntityClass() {
		return CurrencyExchangeRate.class;
	}
	
	public void save(CurrencyExchangeRate rate) throws Exception{
		Assert.isNotNull(rate);
		insertOrUpdate(rate);
	}
	
	public void saveAll(List<CurrencyExchangeRate> rateList) throws Exception{
		Assert.isNotNull(rateList);
		for(CurrencyExchangeRate rate : rateList){
			save(rate);
		}
	}
	
	public void deleteExchangeRate(long rateId){
		CurrencyExchangeRate currencyExchangeRate = this.findCurrencyExchangeRateByRateId(rateId);
		
		em.remove(currencyExchangeRate);
	}
	
	@SuppressWarnings("unchecked")
	public List<CurrencyExchangeRate> loadValidData(String baseCurrencyCode, String exchangeCurrencyCode, Integer rateType) throws Exception{
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" from CurrencyExchangeRate a ")
				.append(" where a.baseCurrencyCode = :baseCurrencyCode ")
				.append(" and a.exCurrencyCode = :exCurrencyCode ")
				.append(" and a.rateType = :rateType ")
				.append(" and a.status = :status ");
		
		Query query = em.createQuery(sqlBuff.toString(), getEntityClass());
		query.setParameter("baseCurrencyCode", baseCurrencyCode);
		query.setParameter("exCurrencyCode", exchangeCurrencyCode);
		query.setParameter("rateType", rateType);
		query.setParameter("status", ExchangeRateStatus.VALID.getValue());
		
		return query.getResultList();
	}
	
	public BigDecimal getExchangeRate(String baseCurrencyCode, String exchangeCurrencyCode, Integer rateType, Date date) throws Exception{
		Assert.isNotNull(baseCurrencyCode);
		Assert.isNotNull(exchangeCurrencyCode);
		Assert.isNotNull(date);
		
		if(baseCurrencyCode.equals(exchangeCurrencyCode)){
			return new BigDecimal(1);
		}
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" from CurrencyExchangeRate a ")
				.append(" where a.baseCurrencyCode = :baseCurrencyCode ")
				.append(" and a.exCurrencyCode = :exCurrencyCode ")
				.append(" and a.validDate <= :validDate ")
				.append(" and a.expiryDate >= :expiryDate ")
				.append(" and a.rateType = :rateType ")
				.append(" and a.status = :status ");
		Query query = em.createQuery(sqlBuff.toString(), getEntityClass());
		query.setParameter("baseCurrencyCode", baseCurrencyCode);
		query.setParameter("exCurrencyCode", exchangeCurrencyCode);
		query.setParameter("validDate", date);
		query.setParameter("expiryDate", date);
		query.setParameter("rateType", rateType);
		query.setParameter("status", ExchangeRateStatus.VALID.getValue());
		@SuppressWarnings("unchecked")
		List<CurrencyExchangeRate> result = query.getResultList();
		if(CollectionUtils.isEmpty(result)){
			return null;
		}
		return result.get(0).getRate();
	}
	
	@SuppressWarnings("unchecked")
	public List<CurrencyExchangeRate> findByPageCondition(CurrencyExchangeRateCondition condition) throws Exception{
		Query query = this.createQueryWithCondition(condition, Constants.OPTION_TYPE_QUERY);
		
		int start = dataHelper.firstPageResult(condition.getPageNumber(), condition.getPageSize());
		query.setFirstResult(start);
		query.setMaxResults(condition.getPageSize());
		
		return query.getResultList();
	}
	
	public CurrencyExchangeRate findCurrencyExchangeRateByRateId(long rateId) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<CurrencyExchangeRate> criteriaQuery = builder.createQuery(getEntityClass());
		Root<CurrencyExchangeRate> root = criteriaQuery.from(getEntityClass());
		criteriaQuery.select(root);
		
		Expression<Boolean> expression = builder.equal(root.get("rateId"),  rateId);
		
		criteriaQuery.where(expression);
		
		TypedQuery<CurrencyExchangeRate> query = em.createQuery(criteriaQuery);
		
		return query.getSingleResult();
	}
	
	public Long countByPageCondition(CurrencyExchangeRateCondition condition) throws Exception{
		Query query = this.createQueryWithCondition(condition, Constants.OPTION_TYPE_COUNT);
		Object totalNum = query.getSingleResult();
		return Long.valueOf(String.valueOf(totalNum));
	}
	
	@SuppressWarnings("unused")
	private Expression<Boolean> buildWhereClause(CriteriaBuilder builder, Root<CurrencyExchangeRate> root, CurrencyExchangeRateCondition condition) throws Exception{
		Expression<Boolean> whereClause = null;
		
		if(condition.getBaseCurrencyCode() != null){
			Expression<Boolean> expression = builder.equal(root.get("baseCurrencyCode"),  condition.getBaseCurrencyCode());
			whereClause = (whereClause == null)? expression : builder.and(whereClause, expression);
		}
		if(condition.getExCurrencyCode() != null){
			Expression<Boolean> expression = builder.equal(root.get("exCurrencyCode"),  condition.getExCurrencyCode());
			whereClause = (whereClause == null)? expression : builder.and(whereClause, expression);
		}
		if(condition.getRateType() != null){
			Expression<Boolean> expression = builder.equal(root.get("rateType"),  condition.getRateType());
			whereClause = (whereClause == null)? expression : builder.and(whereClause, expression);
		}
		if(condition.getValidDateFrom() != null){
			Expression<Boolean> expression = builder.greaterThanOrEqualTo(root.get("validDate"), condition.getValidDateFrom());
			whereClause = (whereClause == null)? expression : builder.and(whereClause, expression);
		}
		if(condition.getValidDateTo() != null){
			Expression<Boolean> expression = builder.lessThanOrEqualTo(root.get("validDate"), condition.getValidDateTo());
			whereClause = (whereClause == null)? expression : builder.and(whereClause, expression);
		}
		if(condition.getExpiryDateFrom() != null){
			Expression<Boolean> expression = builder.greaterThanOrEqualTo(root.get("expiryDate"), condition.getExpiryDateFrom());
			whereClause = (whereClause == null)? expression : builder.and(whereClause, expression);
		}
		if(condition.getExpiryDateTo() != null){
			Expression<Boolean> expression = builder.lessThanOrEqualTo(root.get("expiryDate"), condition.getExpiryDateTo());
			whereClause = (whereClause == null)? expression : builder.and(whereClause, expression);
		}
		return whereClause;
	}
	
	private Query createQueryWithCondition(CurrencyExchangeRateCondition condition, String type) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sqlBuff = new StringBuffer("");
		if (Constants.OPTION_TYPE_COUNT.equals(type)) {
			sqlBuff.append("select count(1) ");
		}
		sqlBuff.append(" from CurrencyExchangeRate a where 1=1 ");
		
		if (StringUtils.isNotBlank(condition.getBaseCurrencyCode())) {
			sqlBuff.append(" and a.baseCurrencyCode = :baseCurrencyCode ");
			params.put("baseCurrencyCode", condition.getBaseCurrencyCode());
		}
		
		if (StringUtils.isNotBlank(condition.getExCurrencyCode())) {
			sqlBuff.append(" and a.exCurrencyCode = :exCurrencyCode ");
			params.put("exCurrencyCode", condition.getExCurrencyCode());
		}
		
		if (null != condition.getValidDateFrom()) {
			sqlBuff.append(" and a.validDate >= :validDateFrom1 ");
			params.put("validDateFrom1", condition.getValidDateFrom());
		}
		
		if (null != condition.getValidDateTo()) {
			sqlBuff.append(" and a.validDate <= :validDateTo2 ");
			params.put("validDateTo2", condition.getValidDateTo());
		}
		
		if (null != condition.getExpiryDateFrom()) {
			sqlBuff.append(" and a.expiryDate >= :expiryDateFrom3 ");
			params.put("expiryDateFrom3", condition.getExpiryDateFrom());
		}
		
		if (null != condition.getExpiryDateTo()) {
			sqlBuff.append(" and a.expiryDate <= :expiryDateTo4 ");
			params.put("expiryDateTo4", condition.getExpiryDateTo());
		}
		
		if (null != condition.getRateType()) {
			sqlBuff.append(" and a.rateType = :rateType ");
			params.put("rateType", condition.getRateType());
		}
		sqlBuff.append(" and a.status = :status ");
		params.put("status", ExchangeRateStatus.VALID.getValue());
		sqlBuff.append(" order by a.rateId desc ");
		
		Query query = null;
		if (Constants.OPTION_TYPE_COUNT.equals(type)) {
			query = em.createQuery(sqlBuff.toString());
		}
		else if (Constants.OPTION_TYPE_QUERY.equals(type)){
			query = em.createQuery(sqlBuff.toString(), getEntityClass());
		}
		String key;
		for(Iterator<String> ite = params.keySet().iterator();ite.hasNext();){
			key = ite.next();
			query.setParameter(key, params.get(key));
		}
		return query;
	}
}
