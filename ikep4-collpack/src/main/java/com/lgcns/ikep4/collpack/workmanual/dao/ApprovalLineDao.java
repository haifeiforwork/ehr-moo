/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.workmanual.model.ApprovalLine;
import com.lgcns.ikep4.collpack.workmanual.model.ApprovalLinePk;
import com.lgcns.ikep4.collpack.workmanual.model.ManualPk;
import com.lgcns.ikep4.collpack.workmanual.model.ManualVersionPk;
import com.lgcns.ikep4.framework.core.dao.GenericDao;


/**
 * DAO 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ApprovalLineDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface ApprovalLineDao extends GenericDao<ApprovalLine, ApprovalLinePk> {
	/**
	 *결재자 정보 - ManualId
	 * @param manualPk
	 * @return
	 */
	public List<ApprovalLine> listApprovalLineByManualId(ManualPk manualPk);
	/**
	 *결재자 정보 - VersionId
	 * @param manualVersionPk
	 * @return
	 */
	public List<ApprovalLine> listApprovalLineByVersionId(ManualVersionPk manualVersionPk);
	/**
	 *결재정보 삭제
	 * @param approvalId
	 */
	public void removeAll(String approvalId);
	/**
	 *결재자 정보
	 * @param approvalId
	 * @return
	 */
	public List<ApprovalLine> listApprovalLine(String approvalId);
	/**
	 *다음 결재자 미결재 상태로 변경
	 * @param approvalLinePk
	 */
	public void updateNextApproval(ApprovalLinePk approvalLinePk);
	/**
	 *결재 순번 증가
	 * @param approvalLinePk
	 */
	public void updateApprovalLineUp(ApprovalLinePk approvalLinePk);
	/**
	 *결재 순번 감소
	 * @param approvalLinePk
	 */
	public void updateApprovalLineDown(ApprovalLinePk approvalLinePk);
}
