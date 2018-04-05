/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.approval.admin.model.ApprDefLine;
import com.lgcns.ikep4.approval.admin.search.ApprDefLineSearchCondition;

/**
 * Default 결재선 관리 Service 정의
 *
 * @author 
 * @version $Id$
 */
@Transactional
public interface ApprDefLineService extends GenericService<ApprDefLine, String> {

	public SearchResult<ApprDefLine> listBySearchCondition(ApprDefLineSearchCondition searchCondition);
	
	/**
	 * Default Appr Line All 목록 조죄
	 * @return
	 */
	public List<ApprDefLine> listApprDefLine();
	
	/**
	 * 결재유형별 Default Appr Line 목록조회
	 * @param defLineType
	 * @return
	 */
	public List<ApprDefLine> listApprDefLineType(ApprDefLine apprDefLine);

	/**
	 * 결재선 삭제
	 * @param defLineIds
	 */
	public void delete(List<String> defLineIds);
}
