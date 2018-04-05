/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.approval.admin.model.ApprAdminConfig;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * 알림 관리 서비스
 *
 * @author jeehye
 * @version $Id: ApprNoticeService.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Transactional
public interface ApprAdminConfigService extends GenericService<ApprAdminConfig, String> {
	
	/**
	 * 결재 기능 사용 저장
	 * @param apprNotice 모델
	 * @return  ID
	 */
	public void adminConfigCreate(ApprAdminConfig apprAdminConfig);
	
	/**
	 * 결재 기능 사용  수정
	 * @param apprNotice 모델
	 * @return  ID
	 */
	public void adminConfigUpdate(ApprAdminConfig apprAdminConfig);
	
	/**
	 * 결재 기능 사용  조회
	 * @param portalId
	 * @return  ApprAdminConfig
	 */
	public ApprAdminConfig adminConfigDetail(String portalId);
	
	/**
	 * 메뉴 display 여부
	 * @param userId
	 * @return
	 */
	public boolean existReadAllAuth(String userId);
	
}
