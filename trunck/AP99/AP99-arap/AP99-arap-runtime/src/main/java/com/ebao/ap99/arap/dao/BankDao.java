package com.ebao.ap99.arap.dao;

import com.ebao.ap99.arap.entity.Bank;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class BankDao extends BaseDao<Bank> {

	@Override
	public Class<Bank> getEntityClass() {
		// TODO Auto-generated method stub
		return Bank.class;
	}

}
