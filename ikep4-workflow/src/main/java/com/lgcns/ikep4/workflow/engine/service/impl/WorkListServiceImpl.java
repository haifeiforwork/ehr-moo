/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.engine.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.workflow.engine.dao.WorkListDao;
import com.lgcns.ikep4.workflow.engine.model.WorkItemBean;
import com.lgcns.ikep4.workflow.engine.search.WorkItemSearchCondition;
import com.lgcns.ikep4.workflow.engine.service.WorkListService;


/**
 * TODO Javadoc주석작성
 *
 * @author 박철순(sniper28@naver.com)
 * @version $Id: WorkListServiceImpl.java 오전 11:14:17 
 */
@Service("workListService")
public class WorkListServiceImpl extends GenericServiceImpl<WorkItemBean, String> implements WorkListService {
	
	@Autowired
	private WorkListDao workListDao;	
	
	public SearchResult<WorkItemBean> getTodoList(WorkItemSearchCondition workItemSearchCondition) {
		SearchResult<WorkItemBean> searchResult = null;
		List<WorkItemBean> workItemList 		= workListDao.todoList(workItemSearchCondition.getUserId());  
		searchResult = new SearchResult<WorkItemBean>(workItemList, workItemSearchCondition);
		return searchResult;
	}	
	


	public List<WorkItemBean> getTodoList(String userId) {
		return workListDao.todoList(userId); 
	}
	
	public List<WorkItemBean> getRunningList(String userId) {
		return workListDao.runningList(userId);
	}

	public List<WorkItemBean> getCompleteList(String userId) {
		return null;
	}
	
	public List<WorkItemBean> startProcessList() {
		// TODO Auto-generated method stub
		return null;
	}
}
