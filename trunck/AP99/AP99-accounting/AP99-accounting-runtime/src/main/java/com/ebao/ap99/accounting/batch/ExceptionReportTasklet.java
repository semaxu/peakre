package com.ebao.ap99.accounting.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.accounting.batch.ExceptionReportJob;
import com.ebao.ap99.parent.util.UserUtil;



public class ExceptionReportTasklet implements Tasklet {
	private Logger logger=LoggerFactory.getLogger(ExceptionReportTasklet.class);
	  @Autowired
	  ExceptionReportJob exceptionReportJob;
	
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		logger.info("Exception Report mapping tasklet start!");
		
		UserUtil.setDummyUser();
		 exceptionReportJob.executeExceptionReport();
		
		logger.info("Exception Report mapping tasklet finish!");
		return RepeatStatus.FINISHED;
	}

}
