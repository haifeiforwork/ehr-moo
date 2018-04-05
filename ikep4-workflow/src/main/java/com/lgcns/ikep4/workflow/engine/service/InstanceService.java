/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.engine.service;

import java.util.Date;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.workflow.engine.model.InstanceBean;
import com.lgcns.ikep4.workflow.engine.model.WorkItemBean;

/**
 * TODO Javadoc주석작성
 *
 * @author 박철순 (sniper28@naver.com)
 * @version $Id: InstanceService.java 16245 2011-08-18 04:28:59Z giljae $ InstanceService.java 오후 7:03:12
 */
@Transactional
public interface InstanceService extends GenericService<WorkItemBean, String> {
	
	public InstanceBean startProcess(String processId, String title, String userId, Map<String, Object> paramHsh);
	
	public InstanceBean startProcess(String processId, String title, String userId, Map<String, Object> paramHsh, Date openDate);
	
	public InstanceBean startProcess(String processId, String title, String userId, Map<String, Object> paramHsh, Date executeDate, Date openDate);
	
	public InstanceBean startProcess(String processId, String title, String userId, Map<String, Object> appKeyHsh, Map<String, Object> paramHsh, Date executeDate);
	
	public InstanceBean startProcess(String processId, String processVer, String title, String userId, Map<String, Object> paramHsh, Date executeDate);
	

	
	
	public void selectWorkItem(String processInsId, String activityId, String insLogId, String userId, Map<String, Object> paramHsh);
	
	
	
	public void completeWorkItem(String processInsId, String activityId, String insLogId, String userId, Map<String, Object> paramHsh); 
	
	public void completeWorkItem(String processInsId, String activityId, String insLogId, String userId, Map<String, Object> paramHsh, Date executeDate);
	
	public void completeWorkItem(String processId, String activityId, String userId, Map<String, Object> appKeyMap, Map<String, Object> paramHsh, Date executeDate);
	
	public void deleteInstance(String processInsId);
	
	public boolean setDelegate(String instanceLogId, String userId, String delegatorId, String message);
	
	public InstanceBean select(InstanceBean instanceBean);
	
}
