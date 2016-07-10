package com.ebao.ap99.arap.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.unicorn.platform.foundation.numbering.dao.NumberingSeqDAO;

public class NumberingDao {
	
	@Autowired
	private NumberingSeqDAO seqDao;
	
	public synchronized Long getDailySequence(String numberingType) throws Exception{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMDD");
		String dateStr = simpleDateFormat.format(new Date());
		
		Long nextSeq = 1L;
		Long currentSeq = seqDao.querySequence(numberingType, dateStr);
		if(currentSeq == 0){
			seqDao.createSequence(numberingType, dateStr, nextSeq);
		}else{
			nextSeq = currentSeq + 1;
			seqDao.updateSequence(numberingType, dateStr, nextSeq, currentSeq);
		}
		return nextSeq;
	}
}
