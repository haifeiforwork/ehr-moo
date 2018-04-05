/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.service;

import java.util.List;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.workflow.admin.model.AdminEmailLog;
import com.lgcns.ikep4.workflow.admin.model.AdminEmailLogSearchCondition;

/**
 * 결재 관련 메일 서비스
 *
 * @author 장규진
 * @version $Id: AdminEmailService.java 16245 2011-08-18 04:28:59Z giljae $
 */
public interface AdminEmailService {
	
	public String createAdminEmailLog(AdminEmailLog object);
	
	public String sendAdminEmail(AdminEmailLog object, User user);

	public void deleteAdminEmailLog(String logId);
	
	public void deleteMultiAdminEmailLog(List<String> logIds);
	
	public AdminEmailLog readAdminEmail(String logId);
	
	public SearchResult<AdminEmailLog> readAdminEmailSearchList(AdminEmailLogSearchCondition adminEmailLogSearchCondition);
	
}
