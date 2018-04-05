/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.dao;

import java.util.List;

import com.lgcns.ikep4.workflow.admin.model.AdminEmailLog;
import com.lgcns.ikep4.workflow.admin.model.AdminEmailLogSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * 결재 코드관리 DAO 정의
 *
 * @author 장규진
 * @version $Id: AdminEmailDao.java 16245 2011-08-18 04:28:59Z giljae $
 */
public interface AdminEmailDao extends GenericDao<AdminEmailLog, String> {

	/**
	 * 메일 로그 검색 리스트
	 * 
	 * @return
	 */
	List<AdminEmailLog> getAdminEmailLogSearchList(AdminEmailLogSearchCondition adminEmailLogSearchCondition);


	/**
	 * 메일 로그 검색 카운트
	 * 
	 * @return
	 */
	Integer getCountAdminEmailLogList(AdminEmailLogSearchCondition adminEmailLogSearchCondition);

	/**
	 * Email 상세 내용
	 * 
	 * @return
	 */
	public AdminEmailLog getEmailLogView(String logId);

	/**
	 * Email로그 다중 삭제
	 * 
	 * @return
	 */
	public void removeMulti(List<String> logIds);
	
}
