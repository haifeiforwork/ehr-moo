package com.lgcns.ikep4.support.user.batch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;
import com.lgcns.ikep4.util.jco.EpGroupDataRcvRFC;
import com.lgcns.ikep4.util.jco.EpPersonDataRcvNewRFC;
import com.lgcns.ikep4.util.jco.EpPersonDataRcvRFC;

/**
 * SAP 사용자 정보 입력 배치 처리 클래스
 * 
 * @author 최재영(FrancisChoi@lgcns.com)
 * @version $Id: UserInfoSyncBatch 16273 2012-08-23 07:07:47Z  $
 */
public class UserInfoSyncNewBatch extends QuartzJobBean {
	
	private UserService userService;
	private GroupService groupService;
	
	
	private EpPersonDataRcvNewRFC epPersonRcvNew;
	private EpGroupDataRcvRFC epGrpRcv;

	/**
	 * 사용자 정보 배치 처리
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Log log = LogFactory.getLog(this.getClass());
		
	    try {
	    	 log.debug("=== UserInfoSyncBatch start ===");
	    	 
			 SchedulerContext schedulerContext = context.getScheduler().getContext();
			 ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");
			 this.groupService = (GroupService) appContext.getBean("groupService");
			 this.userService = (UserService) appContext.getBean("userService");
			 this.epPersonRcvNew = (EpPersonDataRcvNewRFC) appContext.getBean("epPersonRcvNew");
			 this.epGrpRcv = (EpGroupDataRcvRFC) appContext.getBean("epGrpRcv");
	         
			 String jobCondition = userService.readJobCondition("BASIC_UserInfoSyncTrigger");
	    	 
		      // SAP 에서 데이터를 가져온다
	    	  log.debug("=== UserInfoSyncBatch Receive Data from SAP ==="); 
	    	  
	  		  groupService.updateSapGroup(epGrpRcv.EpGroupDataRFC(null,jobCondition));
	  		
	  		   String result = groupService.updateEpTableFromTmpGroupTable();	  			
  		
	  		   userService.updateSapNewUser(epPersonRcvNew.EpPersonDataRFC(null,jobCondition));  
	  		   
	  		   String strDefaultPasswd = EncryptUtil.encryptText("a1234567");
	  		
	  		   result = userService.updateEpUserTabeFromTmpUserTable();
	  		 
	  		   log.debug("=== UserInfoSyncBatch result : " + result);
	  		   
	  		   result = userService.updateUserMenuAcl();
	  		 
	  		   log.debug("=== UserInfoSyncBatch AclUpate result : " + result);	
	  		   
	  		   //result = userService.updatePublicAddressbook();
	  		 
	  		   //log.debug("=== UserInfoSyncBatch AclUpate result : " + result);	
		      		      
	        } catch (Exception e) {
	        	e.printStackTrace();
			 //  throw new IKEP4ApplicationException("", e);
		    }
		    
	        log.debug("=== UserInfoSyncBatch end ===");
		
	}
	

}
