package com.ebao.ap99.arap.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.arap.service.impl.GLServiceImpl;
import com.ebao.ap99.parent.util.UserUtil;

public class GLGeneralLedgerTasklet implements Tasklet {
	private Logger logger=LoggerFactory.getLogger(GLGeneralLedgerTasklet.class);
	
	@Autowired
	GLServiceImpl glService;
	
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		logger.info("GL general ledger generation tasklet start!");
		
		UserUtil.setDummyUser();
		glService.generalLedgerGeneration();
		
		logger.info("GL general ledger generation tasklet start!");
		return RepeatStatus.FINISHED;
	}

}
