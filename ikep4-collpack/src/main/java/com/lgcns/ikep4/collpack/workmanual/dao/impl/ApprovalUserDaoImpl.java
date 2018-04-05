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

import com.lgcns.ikep4.collpack.workmanual.dao.ApprovalUserDao;
import com.lgcns.ikep4.collpack.workmanual.model.ApprovalUser;
import com.lgcns.ikep4.collpack.workmanual.model.ApprovalUserPk;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * DAO 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ApprovalUserDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository
public class ApprovalUserDaoImpl extends GenericDaoSqlmap<ApprovalUser, ApprovalUserPk> implements ApprovalUserDao {
	private static final String NAMESPACE = "collpack.workmanual.dao.approvalUser."; 
	
	public ApprovalUserPk create(ApprovalUser approvalUser) {
		sqlInsert(NAMESPACE + "create", approvalUser);
		return (ApprovalUser) approvalUser;
	}

	public boolean exists(ApprovalUserPk approvalUserPk) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "count", approvalUserPk);
		if (count > 0) {
			return true;
		}
		return false;
	}

	public ApprovalUser get(ApprovalUserPk approvalUserPk) {
		return (ApprovalUser) sqlSelectForObject(NAMESPACE + "get", approvalUserPk);
	}

	public void remove(ApprovalUserPk approvalUserPk) {
		sqlDelete(NAMESPACE + "remove", approvalUserPk);
	}

	public void update(ApprovalUser approvalUser) {
		sqlUpdate(NAMESPACE + "update", approvalUser);
	}
	////////////////////////////////////

	//문서결재자 정보
	public List<ApprovalUser> listApprovalUser(String categoryId) {
		return sqlSelectForList(NAMESPACE + "listApprovalUser", categoryId);
	}
	//문서 결재자 여부
	public String approvalUserYn(String approvalUserId) {
		return (String) sqlSelectForObject(NAMESPACE + "approvalUserYn", approvalUserId);
	}
	//카테고리 아이디로 삭제
	public void removeByCategoryId(String categoryId) {
		sqlDelete(NAMESPACE + "removeByCategoryId", categoryId);
	}
}
