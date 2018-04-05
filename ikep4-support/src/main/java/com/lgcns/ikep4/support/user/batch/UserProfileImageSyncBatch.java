package com.lgcns.ikep4.support.user.batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;


import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.service.UserProfileImageService;
import com.lgcns.ikep4.util.jco.EpPersonDataRcvRFC;

/**
 * SAP 사용자 이미지 입력 배치 처리 클래스
 * 
 * @author 최재영(FrancisChoi@lgcns.com)
 * @version $Id: UserProfileImageSyncBatch 16273 2012-08-23 07:07:47Z  $
 */
public class UserProfileImageSyncBatch extends QuartzJobBean {

	private EpPersonDataRcvRFC epPersonRcv;
	private UserProfileImageService userProfileImageService;
	private TimeZoneSupportService timeZoneSupportService;
	
	
	/**
	 * 사용자 이미지 배치 처리
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Log log = LogFactory.getLog(this.getClass());
		
	    try {
	    	 log.debug("=== UserProfileImageSyncBatch start ===");
	    	 
			 SchedulerContext schedulerContext = context.getScheduler().getContext();
			 ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");
			 
			 this.epPersonRcv = (EpPersonDataRcvRFC) appContext.getBean("epPersonRcv");
			 this.userProfileImageService = (UserProfileImageService) appContext.getBean("userProfileImageService");

		      // SAP 에서 데이터를 가져온다
	    	  log.debug("=== UserProfileImageSyncBatch Receive Data from SAP ==="); 
	    	  
	    	  //String pattern = "yyyyMMdd";
	  		  
	    	  //this.timeZoneSupportService = (TimeZoneSupportService) appContext.getBean("timeZoneSupportService");
	    	  	  		
	          //String strImageName = timeZoneSupportService.convertTimeZoneToString(pattern);
	 	 
	 	      userProfileImageService.userProfileImagesSync(epPersonRcv.EpPersonDataRcvRFC(null));
		      		      
	        } catch (Exception e) {
	        	e.printStackTrace();			
		    }
		    
	        log.debug("=== UserProfileImageSyncBatch end ===");
		
	}	

}
