package com.ebao.ap99.arap.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.ebao.ap99.arap.dao.EntryCodeDao;
import com.ebao.ap99.arap.entity.EntryCode;
import com.ebao.ap99.arap.service.EntryCodeService;

public class EntryCodeServiceImpl implements EntryCodeService {
	
	@Autowired
	EntryCodeDao entryCodeDao;
	
	@Autowired
	CacheManager cacheManager;
	
	@Cacheable("IEntryCodeService.getAllEntryCode")
	public List<EntryCode> getAllEntryCode() throws Exception {
		return entryCodeDao.loadAll();
	}

	@Override
	public EntryCode getByEntryCode(String entryCode) throws Exception {
		return getAllEntryCodeMap().get(entryCode);
	}

	@CacheEvict(cacheNames= {"IEntryCodeService.getAllEntryCode"}, allEntries = true)
	private Map<String, EntryCode> getAllEntryCodeMap() throws Exception{
		Map<String, EntryCode> entryCodeMap = new HashMap<String, EntryCode>();
		List<EntryCode> entryCodeList = this.getAllEntryCode();
		for(EntryCode entryCode : entryCodeList){
			entryCodeMap.put(entryCode.getEntryCode(), entryCode);
		}
		return entryCodeMap;
	}
	
	public void clearCache(){
		Cache cache = cacheManager.getCache("IEntryCodeService.getAllEntryCode");
		if (cache != null){
			cache.clear();
		}
	}
}
