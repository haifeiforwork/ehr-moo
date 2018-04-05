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
import com.lgcns.ikep4.workflow.admin.dao.AdminSmsDao;
import com.lgcns.ikep4.workflow.admin.model.AdminSms;
import com.lgcns.ikep4.workflow.admin.model.AdminSmsSearchCondition;
import com.lgcns.ikep4.workflow.admin.service.SmsAdministrationService;

/**
 * 워크플로우 - 알림현황관리 - SMS관리
 *
 * @author 이재경
 * @version $Id: SmsAdministrationServiceImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Service("smsAdministrationService")
public class SmsAdministrationServiceImpl implements SmsAdministrationService {
	
	@Autowired
	private AdminSmsDao adminSmsDao;
	
	/*
	* SMS관리 리스트 조회
	*/
	public SearchResult<AdminSms> listSms(AdminSmsSearchCondition adminSmsSearchCondition){
		
		Integer count = this.adminSmsDao.countBySearchCondition(adminSmsSearchCondition);
		
		adminSmsSearchCondition.terminateSearchCondition(count);  
		
		SearchResult<AdminSms> searchResult = null;
		
		if(adminSmsSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<AdminSms>(adminSmsSearchCondition);
		}
		else {
			List<AdminSms> workItemList = (List<AdminSms>)this.adminSmsDao.listBySearchCondition(adminSmsSearchCondition);  
			searchResult = new SearchResult<AdminSms>(workItemList, adminSmsSearchCondition); 
		}  
		
		return searchResult;
	}
}
