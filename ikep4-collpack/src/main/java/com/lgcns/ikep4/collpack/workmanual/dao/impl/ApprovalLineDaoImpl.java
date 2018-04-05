/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.workmanual.dao.ApprovalLineDao;
import com.lgcns.ikep4.collpack.workmanual.model.ApprovalLine;
import com.lgcns.ikep4.collpack.workmanual.model.ApprovalLinePk;
import com.lgcns.ikep4.collpack.workmanual.model.ManualPk;
import com.lgcns.ikep4.collpack.workmanual.model.ManualVersionPk;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * DAO 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ApprovalLineDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository
public class ApprovalLineDaoImpl extends GenericDaoSqlmap<ApprovalLine, ApprovalLinePk> implements ApprovalLineDao {
	private static final String NAMESPACE = "collpack.workmanual.dao.approvalLine."; 
	
	public ApprovalLinePk create(ApprovalLine approvalLine) {
		sqlInsert(NAMESPACE + "create", approvalLine);
		return (ApprovalLinePk) approvalLine;
	}

	public boolean exists(ApprovalLinePk approvalLinePk) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "count", approvalLinePk);
		if (count > 0) {
			return true;
		}
		return false;
	}

	public ApprovalLine get(ApprovalLinePk approvalLinePk) {
		return (ApprovalLine) sqlSelectForObject(NAMESPACE + "get", approvalLinePk);
	}

	public void remove(ApprovalLinePk approvalLinePk) {
		sqlDelete(NAMESPACE + "remove", approvalLinePk);
	}

	public void update(ApprovalLine approvalLine) {
		sqlUpdate(NAMESPACE + "update", approvalLine);
	}
	////////////////////////////////////

	//결재자 정보 - ManualId
	public List<ApprovalLine> listApprovalLineByManualId(ManualPk manualPk) {
		return sqlSelectForList(NAMESPACE + "listApprovalLineByManualId", manualPk); 
	}
	//결재자 정보 - VersionId
	public List<ApprovalLine> listApprovalLineByVersionId(ManualVersionPk manualVersionPk) {
		return sqlSelectForList(NAMESPACE + "listApprovalLineByVersionId", manualVersionPk); 
	}
	//결재정보 삭제
	public void removeAll(String approvalId) {
		sqlDelete(NAMESPACE + "removeAll", approvalId);
	}
	//결재자 정보
	public List<ApprovalLine> listApprovalLine(String approvalId) {
		return sqlSelectForList(NAMESPACE + "listApprovalLine", approvalId); 
	}
	//다음 결재자 미결재 상태로 변경
	public void updateNextApproval(ApprovalLinePk approvalLinePk) {
		sqlUpdate(NAMESPACE + "updateNextApproval", approvalLinePk);
	}
	//결재 순번 증가
	public void updateApprovalLineUp(ApprovalLinePk approvalLinePk) {
		sqlUpdate(NAMESPACE + "updateApprovalLineUp", approvalLinePk);
	}
	//결재 순번 감소
	public void updateApprovalLineDown(ApprovalLinePk approvalLinePk) {
		sqlUpdate(NAMESPACE + "updateApprovalLineDown", approvalLinePk);
	}
}
