/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.service;

import com.lgcns.ikep4.approval.work.model.ApprNotice;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * 알림 관리 서비스
 *
 * @author jeehye
 * @version $Id: ApprNoticeService.java 16234 2011-08-18 02:44:36Z giljae $
 */
public interface ApprNoticeService extends GenericService<ApprNotice, String> {
	
	/**
	 * 알림정보 저장
	 * @param apprNotice 모델
	 * @return  ID
	 */
	public void noticeCreate(ApprNotice apprNotice);
	
	/**
	 * 알림정보 수정
	 * @param apprNotice 모델
	 * @return  ID
	 */
	public void noticeUpdate(ApprNotice apprNotice);
	
	/**
	 * 알림정보 수정
	 * @param apprNotice 모델
	 * @return  ID
	 */
	public void noticeUpdateUsage(ApprNotice apprNotice);
	
	/**
	 * 알림정보 조회
	 * @param apprNotice 모델
	 * @return  ID
	 */
	public ApprNotice noticeDetail(String userId, String portalId);
	
}
