package com.lgcns.ikep4.lightpack.planner.batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.lightpack.planner.service.ScheduleService;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;
import com.lgcns.ikep4.util.jco.EpGroupDataRcvRFC;
import com.lgcns.ikep4.util.jco.EpPersonDataRcvRFC;
import com.lgcns.ikep4.util.jco.ScheduleSyncDataRcvRFC;


/**
 * 일정 알람 배치 작업
 * 
 * @author 신용환(combinet@gmail.com)
 * @version $Id: NotiSchedule.java 17315 2012-02-08 04:56:13Z yruyo $
 */
public class ScheduleSyncBatch extends QuartzJobBean {

	private ScheduleSyncDataRcvRFC scheduleSync;
	
	private ScheduleService scheduleService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Log log = LogFactory.getLog(this.getClass());
		
	    try {
	    	SchedulerContext schedulerContext = context.getScheduler().getContext();
			 ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");
			 this.scheduleService = (ScheduleService) appContext.getBean("scheduleService");
			 this.scheduleSync = (ScheduleSyncDataRcvRFC) appContext.getBean("scheduleSync");
				scheduleService.updateScheduleSyncData(scheduleSync.ScheduleSyncDataRcvRFC(null));
				scheduleService.insertScheduleSyncData();
	        } catch (Exception e) {
	        	e.printStackTrace();
			 //  throw new IKEP4ApplicationException("", e);
		    }
		    
	        log.debug("=== UserInfoSyncBatch end ===");
		
	}


}
