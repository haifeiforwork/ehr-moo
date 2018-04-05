/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.engine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.workflow.engine.dao.WorkformDao;
import com.lgcns.ikep4.workflow.engine.model.WorkItemBean;
import com.lgcns.ikep4.workflow.engine.service.WorkformService;


/**
 * TODO Javadoc주석작성
 *
 * @author 박철순(sniper28@naver.com)
 * @version $Id: WorkListServiceImpl.java 오전 11:14:17 
 */
@Service("workformService")
public class WorkformServiceImpl extends GenericServiceImpl<WorkItemBean, String> implements WorkformService {
	
	@Autowired
	private WorkformDao workformDao;	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.engine.service.WorkformService#getWorkform(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public WorkItemBean getWorkform(String processId, String activityId, String processInsId, String insLogId) {
		// TODO Auto-generated method stub
		WorkItemBean workformBean		= new WorkItemBean();
		workformBean.setProcessId(processId);
		workformBean.setActivityId(activityId);
		workformBean.setInstanceId(processInsId);
		workformBean.setInsLogId(insLogId);
		
		return workformDao.getWorkform(workformBean);
	}
	
	
}
