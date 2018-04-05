/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.approval.admin.model.ApprFormDocTemplate;
import com.lgcns.ikep4.approval.admin.search.ApprFormSearchCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;

/**
 * WorkPlace List 서비스
 *
 * @author wonchu
 * @version $Id: ApprFormDocTemplateListService.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Transactional
public interface ApprFormDocTemplateService extends GenericService<ApprFormDocTemplate, String> {

	/**
	 * apprForm Doc Template 목록
	 * @param 	searchCondition
	 * @return 	SearchResult
	 */
	public SearchResult<ApprFormDocTemplate> listBySearchCondition(ApprFormSearchCondition apprFormSearchCondition);
	
	/**
	 * apprForm Doc Template  생성
	 * @param 	ApprFormDocTemplate
	 * @return 	String
	 */
	public String createApprFormDocTemplate(ApprFormDocTemplate apprFormDocTemplate);
	
	/**
	 * apprForm Doc Template  수정
	 * @param 	ApprFormDocTemplate
	 * @return 	void
	 */
	public void updateApprFormDocTemplate(ApprFormDocTemplate apprFormDocTemplate);
	
	/**
	 * apprForm Doc Template  삭제
	 * @param 	ApprFormDocTemplate
	 * @return 	void
	 */
	public void deleteApprFormDocTemplate(ApprFormDocTemplate apprFormDocTemplate);
}