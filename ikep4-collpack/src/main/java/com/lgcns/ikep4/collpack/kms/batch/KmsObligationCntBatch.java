package com.lgcns.ikep4.collpack.kms.batch;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.collpack.kms.admin.service.AdminPermissionService;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;

public class KmsObligationCntBatch extends QuartzJobBean {
	
	private AdminPermissionService adminPermissionService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try{
			
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			ApplicationContext appContext = (ApplicationContext)schedulerContext.get("applicationContext");
			adminPermissionService = (AdminPermissionService)appContext.getBean("AdminPermissionService");
			adminPermissionService.registObligationCnt();			
			
			
		}catch(Exception e){
			e.printStackTrace();
			throw new IKEP4ApplicationException("", e);
		}
		
	}

}
