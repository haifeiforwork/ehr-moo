/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.service;

import java.util.List;

import com.lgcns.ikep4.collpack.workmanual.model.ApprovalLine;
import com.lgcns.ikep4.collpack.workmanual.model.ApprovalLinePk;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ApprovalLineService.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface ApprovalLineService extends GenericService<ApprovalLine, ApprovalLinePk> {
	/**
	 *결재자 정보 - ManualId
	 * @param manualId
	 * @param portalId
	 * @return
	 */
	public List<ApprovalLine> listApprovalLineByManualId(String manualId, String portalId);
	/**
	 *결재자 정보 - VersionId
	 * @param versionId
	 * @param portalId
	 * @return
	 */
	public List<ApprovalLine> listApprovalLineByVersionId(String versionId, String portalId);
	/**
	 *결재자 정보
	 * @param approvalId
	 * @return
	 */
	public List<ApprovalLine> listApprovalLine(String approvalId);
	/**
	 *결재
	 * @param approvalLine
	 * @param user
	 */
	public void updateApprovalLine(ApprovalLine approvalLine, User user);
	/**
	 *결재자 추가
	 * @param approvalLine
	 */
	public void createApprovalUser(ApprovalLine approvalLine);
	/**
	 *결재자 제거
	 * @param approvalLinePk
	 */
	public void deleteApprovalUser(ApprovalLinePk approvalLinePk);
}
