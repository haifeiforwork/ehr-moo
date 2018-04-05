/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.workplace.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.workflow.engine.model.ProcessBean;
import com.lgcns.ikep4.workflow.engine.model.WorkItemBean;
import com.lgcns.ikep4.workflow.workplace.dao.WorkplaceListDao;
import com.lgcns.ikep4.workflow.workplace.search.WorkplaceSearchCondition;
import com.lgcns.ikep4.workflow.workplace.service.WorkplaceListService;


/**
 * WorkPlace List Impl 
 * 
 * @author 이재경
 * @version $Id: WorkplaceListServiceImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Service("workplaceListService")
@SuppressWarnings("unchecked")
public class WorkplaceListServiceImpl extends GenericServiceImpl<WorkItemBean, String> implements WorkplaceListService{
	
	@Autowired
	private WorkplaceListDao workplaceListDao;	
	
	@Transactional(readOnly = true)
	public SearchResult<ProcessBean> workplaceProcessList(WorkplaceSearchCondition workplaceSearchCondition) { 
		
		Integer count = this.workplaceListDao.countBySearchCondition(workplaceSearchCondition);
		
		workplaceSearchCondition.terminateSearchCondition(count);  
		
		SearchResult<ProcessBean> searchResult = null;
		
		if(workplaceSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ProcessBean>(workplaceSearchCondition);
		}
		else {
			List<ProcessBean> workItemList = (List<ProcessBean>)this.workplaceListDao.listBySearchCondition(workplaceSearchCondition);  
			searchResult = new SearchResult<ProcessBean>(workItemList, workplaceSearchCondition); 
		}  
		
		return searchResult;
	}

	@Transactional(readOnly = true)
	public SearchResult<WorkItemBean> workplaceWorkList(WorkplaceSearchCondition workplaceSearchCondition) { 
		
		Integer count = this.workplaceListDao.countBySearchCondition(workplaceSearchCondition);
		
		workplaceSearchCondition.terminateSearchCondition(count);  
		
		SearchResult<WorkItemBean> searchResult = null;
		
		if(workplaceSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<WorkItemBean>(workplaceSearchCondition);
		}
		else {
			List<WorkItemBean> workItemList = (List<WorkItemBean>)this.workplaceListDao.listBySearchCondition(workplaceSearchCondition);  
			searchResult = new SearchResult<WorkItemBean>(workItemList, workplaceSearchCondition); 
		}  
		
		return searchResult;
	}
	
	@Transactional(readOnly = true)
	public ProcessBean readProcInfo(ProcessBean processBean){
		return this.workplaceListDao.readProcInfo(processBean);
	}
	
	@Transactional(readOnly = true)
	public List<HashMap<?, ?>> selectListHashMap(WorkplaceSearchCondition workplaceSearchCondition){
		return (List<HashMap<?, ?>>)this.workplaceListDao.listBySearchCondition(workplaceSearchCondition);
	}
	
	@Transactional(readOnly = true)
	public int listCount(WorkplaceSearchCondition workplaceSearchCondition) { 
		return this.workplaceListDao.countBySearchCondition(workplaceSearchCondition);
	}
}
