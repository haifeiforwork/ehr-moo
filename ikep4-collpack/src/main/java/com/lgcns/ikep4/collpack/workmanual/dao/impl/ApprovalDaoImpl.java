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

import com.lgcns.ikep4.collpack.workmanual.dao.ApprovalDao;
import com.lgcns.ikep4.collpack.workmanual.model.Approval;
import com.lgcns.ikep4.collpack.workmanual.search.ApprovalSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * DAO 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ApprovalDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository
public class ApprovalDaoImpl extends GenericDaoSqlmap<Approval, String> implements ApprovalDao {
	private static final String NAMESPACE = "collpack.workmanual.dao.approval."; 
	
	public String create(Approval approval) {
		return (String) sqlInsert(NAMESPACE + "create", approval);
	}

	public boolean exists(String approvalId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "count", approvalId);
		if (count > 0) {
			return true;
		}
		return false;
	}

	public Approval get(String approvalId) {
		return (Approval) sqlSelectForObject(NAMESPACE + "get", approvalId);
	}

	public void remove(String approvalId) {
		sqlDelete(NAMESPACE + "remove", approvalId);
	}

	public void update(Approval approval) {
		sqlUpdate(NAMESPACE + "update", approval);
	}
	////////////////////////////////////

	//나의 결재함 조회 개수
	public Integer countMyApproval(ApprovalSearchCondition approvalSearchCondition) {
		return (Integer) sqlSelectForObject(NAMESPACE + "countMyApproval", approvalSearchCondition);
	}
	//나의 결재함 조회
	public List<Approval> listMyApproval(ApprovalSearchCondition approvalSearchCondition) {
		return sqlSelectForList(NAMESPACE + "listMyApproval", approvalSearchCondition);
	}
	//상신 중인 결재정보
	public Approval getSubmittingApproval(String versionId) {
		return (Approval) sqlSelectForObject(NAMESPACE + "getSubmittingApproval", versionId);
	}
}
