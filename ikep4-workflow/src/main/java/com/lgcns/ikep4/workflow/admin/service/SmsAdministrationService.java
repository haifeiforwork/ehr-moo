/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.service;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.workflow.admin.model.AdminSms;
import com.lgcns.ikep4.workflow.admin.model.AdminSmsSearchCondition;


/**
 * 워크플로우 - 알림현황관리 - SMS관리
 *
 * @author 이재경
 * @version $Id: SmsAdministrationService.java 16245 2011-08-18 04:28:59Z giljae $
 */
public interface SmsAdministrationService{

	/*
	* SMS관리 리스트 조회
	*/
	public SearchResult<AdminSms> listSms(AdminSmsSearchCondition adminSmsSearchCondition);
}
