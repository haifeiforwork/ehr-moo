/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.workmanual.dao.ApprovalUserDao;
import com.lgcns.ikep4.collpack.workmanual.model.ApprovalUser;
import com.lgcns.ikep4.collpack.workmanual.model.ApprovalUserPk;
import com.lgcns.ikep4.collpack.workmanual.service.ApprovalUserService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;


/**
 * Service 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ApprovalUserServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service 
@Transactional
public class ApprovalUserServiceImpl extends GenericServiceImpl<ApprovalUser, ApprovalUserPk> implements ApprovalUserService {

	@Autowired
	private ApprovalUserDao approvalUserDao;
	
	public ApprovalUserPk create(ApprovalUser approvalUser) {
		return approvalUserDao.create(approvalUser);
	}

	public boolean exists(ApprovalUserPk approvalUserPk) {
		return approvalUserDao.exists(approvalUserPk);
	}

	public ApprovalUser read(ApprovalUserPk approvalUserPk) {
		return approvalUserDao.get(approvalUserPk);
	}

	public void delete(ApprovalUserPk approvalUserPk) {
		approvalUserDao.remove(approvalUserPk);
	}

	public void update(ApprovalUser approvalUser) {
		approvalUserDao.update(approvalUser);
	}
	////////////////////////////////////
	
	//문서결재자 정보
	public List<ApprovalUser> listApprovalUser(String categoryId) {
		return approvalUserDao.listApprovalUser(categoryId);
	}
	//문서 결재자 여부
	public String approvalUserYn(String approvalUserId) {
		return approvalUserDao.approvalUserYn(approvalUserId);
	}
}
