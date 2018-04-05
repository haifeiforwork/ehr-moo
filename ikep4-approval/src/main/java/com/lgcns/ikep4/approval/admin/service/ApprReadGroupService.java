/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.approval.admin.model.ApprReadGroup;
import com.lgcns.ikep4.approval.admin.search.ApprReadGroupSearchCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;

/**
 * 부서 결재함 열람권한  Service 정의
 *
 * @author 
 * @version $Id$
 */
@Transactional
public interface ApprReadGroupService extends GenericService<ApprReadGroup, String> {

	public SearchResult<ApprReadGroup> listBySearchCondition(ApprReadGroupSearchCondition searchCondition);
	
	public void createApprReadGroup(ApprReadGroup object);
	
	public List<ApprReadGroup> getGroupList(String userId,String portalId);
	
	public ApprReadGroup getBasicInfo(String userId,String portalId);
	
	public void updateApprReadGroup(ApprReadGroup object);
	
	public void deleteReadGroup(List<String> userIds,String portalId);
}
