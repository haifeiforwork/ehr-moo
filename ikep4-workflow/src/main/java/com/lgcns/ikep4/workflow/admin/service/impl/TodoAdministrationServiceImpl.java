/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.workflow.admin.dao.AdminTodoDao;
import com.lgcns.ikep4.workflow.admin.model.AdminTodo;
import com.lgcns.ikep4.workflow.admin.model.AdminTodoSearchCondition;
import com.lgcns.ikep4.workflow.admin.service.TodoAdministrationService;

/**
 * 워크플로우 - 현황관리 - 업무관리
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: TodoAdministrationServiceImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Service("todoAdministrationService")
public class TodoAdministrationServiceImpl implements TodoAdministrationService {
	
	@Autowired
	private AdminTodoDao adminTodoDao;
	
	/*
	* 업무관리 리스트 조회
	*/
	public SearchResult<AdminTodo> listTodo(AdminTodoSearchCondition adminTodoSearchCondition){
		Integer count = adminTodoDao.listTodoCount(adminTodoSearchCondition);

		adminTodoSearchCondition.terminateSearchCondition(count);  
		
		SearchResult<AdminTodo> todoSearchResult = null;
		
		if(adminTodoSearchCondition.isEmptyRecord()) {
			todoSearchResult = new SearchResult<AdminTodo>(adminTodoSearchCondition);
		}else{
			List<AdminTodo> listTodo = adminTodoDao.listTodo(adminTodoSearchCondition);
			todoSearchResult = new SearchResult<AdminTodo>(listTodo, adminTodoSearchCondition); 
		}  
		return todoSearchResult;
	}
}
