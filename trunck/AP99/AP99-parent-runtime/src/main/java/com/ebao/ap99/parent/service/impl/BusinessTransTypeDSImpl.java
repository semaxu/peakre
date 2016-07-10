package com.ebao.ap99.parent.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;

import com.ebao.ap99.parent.dao.BusinessTransTypeDao;
import com.ebao.ap99.parent.entity.BusinessTransType;
import com.ebao.ap99.parent.service.BusinessTransTypeDS;
import com.ebao.unicorn.platform.foundation.utils.Assert;

public class BusinessTransTypeDSImpl implements BusinessTransTypeDS {
	
	@Autowired
	BusinessTransTypeDao transTypeDao;
	
	@Autowired
	CacheManager cacheManager;
	
	@Override
	@Cacheable("BusinessTransTypeDS.getByTransType")
	public BusinessTransType getByTransType(Integer transType) throws Exception {
		Assert.isNotNull(transType);
		List<BusinessTransType> transTypeList = transTypeDao.loadAll();
		for(BusinessTransType bizTransType : transTypeList){
			if(bizTransType.getTransTypeId() == transType){
				return bizTransType;
			}
		}
		throw new Exception("Invalid business transaction type : " + transType);
	}

}
