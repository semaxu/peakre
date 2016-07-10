package com.ebao.ap99.arap.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.ebao.ap99.arap.constant.Constants;
import com.ebao.ap99.arap.entity.Bank;
import com.ebao.ap99.arap.entity.BankAccount;
import com.ebao.ap99.arap.vo.BankAccountCondition;
import com.ebao.ap99.arap.vo.BankDTO;
import com.ebao.ap99.parent.Page;
import com.ebao.ap99.parent.PaginationHelper;
import com.ebao.unicorn.platform.data.helper.DataHelper;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.Assert;

public class BankAccountDao extends BaseDao<BankAccount> {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private DataHelper dataHelper;
	
	@Override
	public Class<BankAccount> getEntityClass() {
		return BankAccount.class;
	}

	public List<BankAccount> queryByCondition(BankAccountCondition condition)
			throws Exception {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BankAccount> criteriaQuery = builder.createQuery(getEntityClass());
		Root<BankAccount> root = criteriaQuery.from(getEntityClass());
		criteriaQuery.select(root);
		
		Expression<Boolean> whereClause = buildWhereClause(builder, root, condition);
		
		if(whereClause != null){
			criteriaQuery.where(whereClause);
		}
		criteriaQuery.orderBy(builder.desc(root.get("accountId")));
		TypedQuery<BankAccount> query = entityManager.createQuery(criteriaQuery);
		
		int start = dataHelper.firstPageResult(condition.getPageNumber(), condition.getPageSize());
		
		query.setFirstResult(start);
		query.setMaxResults(condition.getPageSize());
		
		return query.getResultList();
	}
	
	public List<BankAccount> queryBankAccountByCurrencyCode(String currencyCode, Long accountType) throws Exception {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BankAccount> criteriaQuery = builder.createQuery(getEntityClass());
		Root<BankAccount> root = criteriaQuery.from(getEntityClass());
		criteriaQuery.select(root);
		
		Expression<Boolean> whereClause = builder.notEqual(root.get("status"), Constants.STATUS_INVALID);// not invalid
		whereClause = builder.and(whereClause, builder.equal(root.get("currencyCode"), currencyCode));
		whereClause = builder.and(whereClause, builder.equal(root.get("accountType"), accountType));
		
		criteriaQuery.where(whereClause);
		
		TypedQuery<BankAccount> query = entityManager.createQuery(criteriaQuery);
		
		return query.getResultList();
	}
	
	public List<BankAccount> queryAllBankAccount() throws Exception {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BankAccount> criteriaQuery = builder.createQuery(getEntityClass());
		Root<BankAccount> root = criteriaQuery.from(getEntityClass());
		criteriaQuery.select(root);
		
		Expression<Boolean> whereClause = builder.equal(root.get("status"), Constants.STATUS_VALID);// valid
		
		criteriaQuery.where(whereClause);
		
		TypedQuery<BankAccount> query = entityManager.createQuery(criteriaQuery);
		
		return query.getResultList();
	}
	
	public void deleteBankAccount(long accountId) {
		BankAccount bankAccount = this.findBankAccount(accountId);
		
		entityManager.remove(bankAccount);
	}
	
	public BankAccount findBankAccount(long accountId) {
		return entityManager.find(BankAccount.class, accountId);
	}
	
	@SuppressWarnings("unchecked")
	public boolean hasDuplicateBankAccount(BankAccount bankAccount) throws Exception{
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" from BankAccount a ")
				.append(" where 1 = 1 ")
				.append(" and a.accountName = :accountName ")
				.append(" and a.accountNo = :accountNo ")
				.append(" and a.bankId = :bankId ")
				.append(" and a.branchId = :branchId ")
				.append(" and a.accountType = :accountType ")
				.append(" and a.currencyCode = :currencyCode ");
		if(bankAccount.getAccountId() != null){
			sqlBuff.append(" and a.accountId != :accountId ");
		}
		
		Query query = entityManager.createQuery(sqlBuff.toString(), getEntityClass());
		query.setParameter("accountName", bankAccount.getAccountName());
		query.setParameter("accountNo", bankAccount.getAccountNo());
		query.setParameter("bankId", bankAccount.getBankId());
		query.setParameter("branchId", bankAccount.getBranchId());
		query.setParameter("accountType", bankAccount.getAccountType());
		query.setParameter("currencyCode", bankAccount.getCurrencyCode());
		if(bankAccount.getAccountId() != null){
			query.setParameter("accountId", bankAccount.getAccountId());
		}
		List<BankAccount> list = query.getResultList();
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}
	
	public Long countByPageCondition(BankAccountCondition condition) throws Exception{
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<BankAccount> root = criteriaQuery.from(getEntityClass());
		criteriaQuery.select(builder.count(root));
		
		Expression<Boolean> whereClause = buildWhereClause(builder, root, condition);
		
		if(whereClause != null){
			criteriaQuery.where(whereClause);
		}
		Query query = entityManager.createQuery(criteriaQuery);
		return (Long)query.getSingleResult();
	}

	public void save(BankAccount bankAccount) throws Exception {
		Assert.isNotNull(bankAccount);
		
		BankAccount newBankAccount = null;
		Long accountId = bankAccount.getAccountId();
		if (null != accountId) {
			newBankAccount = this.findBankAccount(accountId);
			
			newBankAccount.setAccountName(bankAccount.getAccountName());
			newBankAccount.setAccountNo(bankAccount.getAccountNo());
			newBankAccount.setCurrencyCode(bankAccount.getCurrencyCode());
			newBankAccount.setBranchId(bankAccount.getBranchId());
			//newBankAccount.setBankId(bankAccount.getBankId());
			newBankAccount.setAccountType(bankAccount.getAccountType());
			
			insertOrUpdate(newBankAccount);
		}
		else {
			insertOrUpdate(bankAccount);
		}
	}
	
	public void saveAll(List<BankAccount> accountList) throws Exception{
		Assert.isNotNull(accountList);
		
		for(BankAccount account : accountList){
			save(account);
		}
	}
	
	private Expression<Boolean> buildWhereClause(CriteriaBuilder builder, Root<BankAccount> root, BankAccountCondition condition) throws Exception{

		Expression<Boolean> whereClause = null;
		
		if(condition.getAccountNo() != null){
			Expression<Boolean> expression = builder.like(root.get("accountNo"),  "%"+condition.getAccountNo()+"%");
			whereClause = (whereClause == null)? expression : builder.and(whereClause, expression);
		}
		if(condition.getAccountName() != null){
			Expression<Boolean> expression = builder.like(root.get("accountName"),  "%"+condition.getAccountName()+"%");
			whereClause = (whereClause == null)? expression : builder.and(whereClause, expression);
		}
		if(condition.getBranchId() != null){
			Expression<Boolean> expression = builder.equal(root.get("branchId"),  condition.getBranchId());
			whereClause = (whereClause == null)? expression : builder.and(whereClause, expression);
		}
		if(condition.getCurrencyCode() != null){
			Expression<Boolean> expression = builder.equal(root.get("currencyCode"), condition.getCurrencyCode());
			whereClause = (whereClause == null)? expression : builder.and(whereClause, expression);
		}
		
		return whereClause;
	}
	
	public String queryBankByCode(String bankCode) {
		Query query = getEntityManager().createNamedQuery("Bank.findBankNameByCode", Bank.class);
		query.setParameter("bankCode", bankCode);
		Bank bank = new Bank();
		try {
			Object obj = query.getSingleResult();
			if (null != obj)
				bank = (Bank)  obj;
		} catch (NoResultException e) {
			
		}
		String bankName = "";
		if (null != bank){
			bankName = bank.getBankName();
		}
		return bankName;
	}
	
	public Page<Bank> queryBank(BankDTO bankDTO){
		Page<Bank> page = new Page<Bank>();
        PaginationHelper<Bank> ph = new PaginationHelper<Bank>();
        
        String sql = "select t.* from t_ri_bcp_cfg_bank t where 1=1 ";
        if (StringUtils.isNotBlank(bankDTO.getBankCode())) {
        	sql += "and t.bank_code like '%" + bankDTO.getBankCode().trim() + "%' ";
        }
        
        if (StringUtils.isNotBlank(bankDTO.getBankName())) {
        	sql += "and t.bank_name like '%" + bankDTO.getBankName().trim() + "%' ";
        } 

        BeanPropertyRowMapper<Bank> rowmapper = new BeanPropertyRowMapper<Bank>(Bank.class);
        
        page = ph.fetchPage(sql, new Object[]{}, bankDTO.getPageIndex(), bankDTO.getCountPerPage(),rowmapper);

        return page;
	}
	
	public String queryAccountNameByAccountNo(String accountNo){
		String accountName = "";
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BankAccount> criteriaQuery = builder.createQuery(getEntityClass());
		Root<BankAccount> root = criteriaQuery.from(getEntityClass());
		criteriaQuery.select(root);
		
		Expression<Boolean> whereClause = builder.equal(root.get("accountNo"), accountNo);
		
		criteriaQuery.where(whereClause);
		
		TypedQuery<BankAccount> query = entityManager.createQuery(criteriaQuery);
		
		List<BankAccount> list = query.getResultList();
		if (null != list && list.size() > 0) {
			BankAccount bankAccount = list.get(0);
			if (null != bankAccount){
				accountName = bankAccount.getAccountName();
			}
		}
		return accountName;
	}
}
