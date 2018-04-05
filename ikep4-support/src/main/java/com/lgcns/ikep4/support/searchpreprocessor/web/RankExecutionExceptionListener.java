package com.lgcns.ikep4.support.searchpreprocessor.web;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.support.searchpreprocessor.model.BatchLog;
import com.lgcns.ikep4.support.searchpreprocessor.service.BatchLogService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * 
 * 배치콜 job분석로그
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: RankExecutionExceptionListener.java 16276 2011-08-18 07:09:07Z giljae $
 */
public class RankExecutionExceptionListener implements JobListener {
	@Autowired
	private BatchLogService batchLogService;
	
	@Autowired
	private IdgenService idgenService;
	
	
	public String getName() {
		// TODO Auto-generated method stub
		return "RankExecutionExceptionListener";
	}

	public void jobExecutionVetoed(JobExecutionContext context) {
		// TODO Auto-generated method stub
		//System.out.println("jobExecutionVetoed"+context.getFireTime() + ":" + context.getJobDetail().getFullName() + ":" + context.getFireTime());
		/*BatchLog batchLog = new BatchLog();
        batchLog.setId( idgenService.getNextId() );
        batchLog.setName( context.getJobDetail().getFullName() );
        batchLog.setStartDate( context.getFireTime() );
        batchLog.setEndDate( new Date() );
        batchLog.setResult( "Vetoed" );
        batchLog.setDescription("");
        batchLogService.create(batchLog);*/
	}

	public void jobToBeExecuted(JobExecutionContext context) {
		// TODO Auto-generated method stub
		//System.out.println("jobToBeExecuted"+context.getFireTime() + ":" + context.getJobDetail().getFullName() + ":" + context.getFireTime());
		/*BatchLog batchLog = new BatchLog();
        batchLog.setId( idgenService.getNextId() );
        batchLog.setName( context.getJobDetail().getFullName() );
        batchLog.setStartDate( context.getFireTime() );
        batchLog.setEndDate( new Date() );
        batchLog.setResult("ToBe");
        batchLog.setDescription("");
        batchLogService.create(batchLog);*/
	}

	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		// TODO Auto-generated method stub
		//System.out.println("Was"+context.getFireTime() + ":" + context.getJobDetail().getFullName() + ":" + context.getFireTime());
		//System.out.println(jobException);
		
		 BatchLog batchLog = new BatchLog();
         batchLog.setId( idgenService.getNextId() );
         batchLog.setName( context.getJobDetail().getName() );
         batchLog.setStartDate( context.getFireTime() );
         batchLog.setEndDate( new Date() );
         if( jobException != null ){
        	 batchLog.setResult( "F" );
         }else{
        	 batchLog.setResult( "C" );
         }
         
         if( jobException != null ){
        	 batchLog.setDescription( jobException.getLocalizedMessage() );
         }
         
         batchLogService.create(batchLog);
	}

//	public void setBatchLogService(BatchLogService batchLogService) {
//		this.batchLogService = batchLogService;
//	}
//
//	public void setIdgenService(IdgenService idgenService) {
//		this.idgenService = idgenService;
//	}
}
