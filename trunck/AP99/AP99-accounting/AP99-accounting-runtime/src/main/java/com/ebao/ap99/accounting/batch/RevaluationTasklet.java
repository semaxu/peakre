package com.ebao.ap99.accounting.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.parent.util.UserUtil;



public class RevaluationTasklet implements Tasklet {
	private Logger logger=LoggerFactory.getLogger(RevaluationTasklet.class);
	  @Autowired
	  RevaluationJob revaluationJob;
	
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		logger.info("Revaluation mapping tasklet start!");
		
		UserUtil.setDummyUser();
		revaluationJob.executeRevaluation();;
		logger.info("Revaluation mapping tasklet finish!");
		return RepeatStatus.FINISHED;
	}

}
