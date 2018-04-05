/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.engine.demon;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.workflow.engine.dao.InterfaceDao;
import com.lgcns.ikep4.workflow.engine.dao.impl.InterfaceDaoImpl;
import com.lgcns.ikep4.workflow.engine.model.InterfaceBean;
import com.lgcns.ikep4.workflow.engine.service.DeployService;
import com.lgcns.ikep4.workflow.engine.service.InstanceService;
import com.lgcns.ikep4.workflow.engine.service.InterfaceService;
import com.lgcns.ikep4.workflow.engine.service.impl.InterfaceServiceImpl;
import com.lgcns.ikep4.workflow.engine.web.InterfaceController;

/**
 * TODO Javadoc주석작성
 *
 * @author 박철순 (sniper28@naver.com)
 * @version $Id: InterfaceDemon.java 16245 2011-08-18 04:28:59Z giljae $ InterfaceDemon.java 오후 2:11:21
 */
@Controller
public class InterfaceDemon extends TimerTask  {
	
	Log log = LogFactory.getLog(InterfaceDemon.class);

	@Autowired
	private InterfaceDaoImpl interfaceDao;	
	
	@Autowired
	private InstanceService instanceService;		
	
	
	public InterfaceDemon() {
		
	}

	/**
	 * 쓰레드를 처리한다. 
	 */
	public void run(){
		executeInterface();
	}
	

	public void executeInterface() {
		log.info("##### START : InstanceService ");
		//InterfaceDaoImpl interpaceDaoImpl = new InterfaceDaoImpl();
		
		List<InterfaceBean> list		= interfaceDao.readInterface();
		for(int i=0;i<list.size();i++) {
			InterfaceBean interfaceBean	= list.get(i);
			String interfaceType		= interfaceBean.getInterfaceType();
			String processId			= interfaceBean.getProcessId();
			String activityId			= interfaceBean.getActivityId();
			String title				= interfaceBean.getTitle();
			String userId				= interfaceBean.getUserId();
			Date executeDate			= interfaceBean.getCreateDate();
			
			Map<String, Object> appKeyMap	= new HashMap<String, Object>();
			if(interfaceBean.getAppKey01() != null && interfaceBean.getAppKey01().equals("")) {
				appKeyMap.put("appKey01", interfaceBean.getAppKey01());
			} else if(interfaceBean.getAppKey02() != null && !interfaceBean.getAppKey02().equals("")) {
				appKeyMap.put("appKey02", interfaceBean.getAppKey02());
			} else if(interfaceBean.getAppKey03() != null && !interfaceBean.getAppKey03().equals("")) {
				appKeyMap.put("appKey03", interfaceBean.getAppKey03());
			} else if(interfaceBean.getAppKey04() != null && !interfaceBean.getAppKey04().equals("")) {
				appKeyMap.put("appKey04", interfaceBean.getAppKey04());
			} else if(interfaceBean.getAppKey05() != null && !interfaceBean.getAppKey05().equals("")) {
				appKeyMap.put("appKey05", interfaceBean.getAppKey05());
			} else if(interfaceBean.getAppKey06() != null && !interfaceBean.getAppKey06().equals("")) {
				appKeyMap.put("appKey06", interfaceBean.getAppKey06());
			} else if(interfaceBean.getAppKey07() != null && !interfaceBean.getAppKey07().equals("")) {
				appKeyMap.put("appKey07", interfaceBean.getAppKey07());
			} else if(interfaceBean.getAppKey08() != null && !interfaceBean.getAppKey08().equals("")) {
				appKeyMap.put("appKey08", interfaceBean.getAppKey08());
			} else if(interfaceBean.getAppKey09() != null && !interfaceBean.getAppKey09().equals("")) {
				appKeyMap.put("appKey09", interfaceBean.getAppKey09());
			} else if(interfaceBean.getAppKey10() != null && !interfaceBean.getAppKey10().equals("")) {
				appKeyMap.put("appKey10", interfaceBean.getAppKey10());
			}
			Map<String, Object> paramHsh	= new HashMap<String, Object>();
			
			
			
			if(interfaceType.equalsIgnoreCase("StartProcess")) {
				instanceService.startProcess(processId, title, userId, null, executeDate);
			} else if(interfaceType.equalsIgnoreCase("CompleteWorkItem")) {
				instanceService.completeWorkItem(processId, activityId, userId, appKeyMap, paramHsh, executeDate);
			}
		}
	}	
	
}