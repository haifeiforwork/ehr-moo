/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.service;

import com.lgcns.ikep4.collpack.workmanual.model.Approval;
import com.lgcns.ikep4.collpack.workmanual.search.ApprovalSearchCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ApprovalService.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface ApprovalService extends GenericService<Approval, String> {
	/**
	 *나의 결재함 조회
	 * @param approvalSearchCondition
	 * @return
	 */
	public SearchResult<Approval> listMyApproval(ApprovalSearchCondition approvalSearchCondition);
	/**
	 *메일 발송
	 * @param mode
	 * @param approvalId
	 * @param user
	 * @param url
	 */
	public void sendMail(String mode, String approvalId, User user, String url);
	/**
	 *상신중인 결재정보 
	 * @param versionId
	 * @return
	 */
	public Approval readSubmittingApproval(String versionId);
}