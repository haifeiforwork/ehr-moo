/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.service;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.workflow.admin.model.AdminParticipantSearchCondition;
import com.lgcns.ikep4.workflow.admin.model.AdminParticipants;

/**
 * 참여자 정보 조회
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminParticipantsService.java 16245 2011-08-18 04:28:59Z giljae $
 */
public interface AdminParticipantsService extends GenericService<AdminParticipants, String> {
	public SearchResult<AdminParticipants> listRole(AdminParticipantSearchCondition adminParticipantSearchCondition);
}

