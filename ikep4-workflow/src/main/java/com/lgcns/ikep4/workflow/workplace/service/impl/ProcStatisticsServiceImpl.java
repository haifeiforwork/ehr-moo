/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.workplace.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.workflow.engine.model.ProcessBean;
import com.lgcns.ikep4.workflow.engine.model.WorkItemBean;
import com.lgcns.ikep4.workflow.workplace.dao.ProcStatisticsDao;
import com.lgcns.ikep4.workflow.workplace.dao.WorkplaceListDao;
import com.lgcns.ikep4.workflow.workplace.search.WorkplaceSearchCondition;
import com.lgcns.ikep4.workflow.workplace.service.ProcStatisticsService;


/**
 * WorkPlace List Impl 
 * 
 * @author 이재경
 * @version $Id: ProcStatisticsServiceImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Service("procStatisticsService")
public class ProcStatisticsServiceImpl extends GenericServiceImpl<WorkItemBean, String> implements ProcStatisticsService{
	
	@Autowired
	private ProcStatisticsDao procStatisticsDao;	
	
	@Autowired
	private WorkplaceListDao workplaceListDao;	
		
	@Transactional(readOnly = true)
	public ProcessBean procStatisticsDetail(WorkplaceSearchCondition workplaceSearchCondition) { 
		return (ProcessBean)this.procStatisticsDao.procStatisticsDetail(workplaceSearchCondition);
	}
	
	@Transactional(readOnly = true)
	public int procStatisticsRunningCount(WorkplaceSearchCondition workplaceSearchCondition) { 
		if( workplaceSearchCondition.getProcessId() == null){
			return 0;
		}
		else{
			workplaceSearchCondition.setQueryId("ProcStatisticsRunning");
			return this.workplaceListDao.countBySearchCondition(workplaceSearchCondition);
		}
	}
	
	@Transactional(readOnly = true)
	public int procStatisticsCompleteCount(WorkplaceSearchCondition workplaceSearchCondition) { 
		if( workplaceSearchCondition.getProcessId() == null){
			return 0;
		}
		else{
			workplaceSearchCondition.setQueryId("ProcStatisticsComplete");
			return this.workplaceListDao.countBySearchCondition(workplaceSearchCondition);
		}
	}
}
