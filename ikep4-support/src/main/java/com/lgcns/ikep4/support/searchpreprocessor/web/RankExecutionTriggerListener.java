package com.lgcns.ikep4.support.searchpreprocessor.web;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgcns.ikep4.support.searchpreprocessor.model.BatchLog;
import com.lgcns.ikep4.support.searchpreprocessor.service.BatchLogService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * 
 * 배치 trigger 분석로그
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: RankExecutionTriggerListener.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Component
public class RankExecutionTriggerListener implements TriggerListener{

	@Autowired
	BatchLogService batchLogService;
	
	@Autowired
	private IdgenService idgenService;
	
	public String getName() {
		return "RankExecutionTriggerListener";
	}

	public void triggerFired(Trigger trigger, JobExecutionContext ctx) {
        //System.out.println("Scheduled " + trigger.getJobName() + " Fired!!");
    }

    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext ctx) {
       // System.out.println("Scheduled " + trigger.getJobName() + " Executed!!");
        return false;
    }

    public void triggerComplete(Trigger trigger, JobExecutionContext ctx, int arg) {
        //System.out.println("Scheduled " + trigger.getJobName() + " Completed!!");

        BatchLog batchLog = new BatchLog();
        batchLog.setId( idgenService.getNextId() );
        batchLog.setName( trigger.getJobName() );
        batchLog.setStartDate( trigger.getStartTime() );
        batchLog.setStartDate( trigger.getEndTime() );
        batchLog.setResult( ctx.getResult().toString() );
        batchLog.setDescription( ctx.getResult().toString() );
        batchLogService.create(batchLog);
        
    }

    public void triggerMisfired(Trigger trigger) {
    	//System.out.println("Scheduled " + trigger.getJobName() + " Misfired!!");
    }
}
