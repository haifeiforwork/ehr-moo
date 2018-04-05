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

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.workflow.admin.dao.AdminParticipantsDao;
import com.lgcns.ikep4.workflow.admin.model.AdminParticipantSearchCondition;
import com.lgcns.ikep4.workflow.admin.model.AdminParticipants;
import com.lgcns.ikep4.workflow.admin.service.AdminParticipantsService;

/**
 * 참여자 정보 조회
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminParticipantsServiceImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Service("adminParticipantsService")
public class AdminParticipantsServiceImpl extends GenericServiceImpl<AdminParticipants, String> implements AdminParticipantsService {
	
	@Autowired
	private AdminParticipantsDao adminParticipantsDao;
	
	/*
	* 롤  조회
	*/
	public SearchResult<AdminParticipants> listRole(AdminParticipantSearchCondition adminParticipantSearchCondition){
		Integer count = adminParticipantsDao.listRoleCount(adminParticipantSearchCondition);

		adminParticipantSearchCondition.terminateSearchCondition(count);  
		
		SearchResult<AdminParticipants> roleSearchResult = null;
		
		if(adminParticipantSearchCondition.isEmptyRecord()) {
			roleSearchResult = new SearchResult<AdminParticipants>(adminParticipantSearchCondition);
		}else{
			List<AdminParticipants> listRole = adminParticipantsDao.listRole(adminParticipantSearchCondition);
			roleSearchResult = new SearchResult<AdminParticipants>(listRole, adminParticipantSearchCondition); 
		}  
		return roleSearchResult;
	}
}
