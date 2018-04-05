/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.approval.admin.model.ApprDefLine;
import com.lgcns.ikep4.approval.admin.search.ApprDefLineSearchCondition;

/**
 * Default 결재선 Dao 정의
 *
 * @author 
 * @version $Id$
 */
public interface ApprDefLineDao extends GenericDao<ApprDefLine, String> {


	/**
	 * Default 결재선명 정보 목록
	 * 
	 * @param IntDefLineSearchCondition
	 * @return
	 */
	List<ApprDefLine> listBySearchCondition(ApprDefLineSearchCondition searchCondition);
	
	/**
	 * Default 결재선명 정보 Count
	 * 
	 * @param workspaceSearchCondition
	 * @return
	 */
	Integer countBySearchCondition(ApprDefLineSearchCondition searchCondition);

	/**
	 * 결재선 전체 목록
	 * @return
	 */
	List<ApprDefLine> listApprDefLine();	
	
	/**
	 * 결재선의 시스템,결재 유형(내부/협조)별 목록
	 * @param apprDefLine
	 * @return
	 */
	public List<ApprDefLine> listApprDefLineType(ApprDefLine apprDefLine);
	

}
