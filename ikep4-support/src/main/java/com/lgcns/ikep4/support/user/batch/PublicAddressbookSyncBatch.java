package com.lgcns.ikep4.support.user.batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.support.user.member.service.UserService;

/**
 * SAP 사용자 정보 입력 배치 처리 클래스
 * 
 * @author 최재영(FrancisChoi@lgcns.com)
 * @version $Id: UserInfoSyncBatch 16273 2012-08-23 07:07:47Z  $
 */
public class PublicAddressbookSyncBatch extends QuartzJobBean {
	
	private UserService userService;

	/**
	 * 사용자 정보 배치 처리
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Log log = LogFactory.getLog(this.getClass());
		
		
	    try {
	    	   
	    		SchedulerContext schedulerContext = context.getScheduler().getContext();
	    		ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");
	    		this.userService = (UserService) appContext.getBean("userService");
	  		    userService.updatePublicAddressbook();
	  		 
	  		    //log.debug("=== UserInfoSyncBatch AclUpate result : " + result);	
		      		      
	        } catch (Exception e) {
	        	e.printStackTrace();
			 //  throw new IKEP4ApplicationException("", e);
		    }
		    
	        //log.debug("=== UserInfoSyncBatch end ===");
		
	}
	

}
