/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.approval.admin.dao.ApprFormDocTemplateDao;
import com.lgcns.ikep4.approval.admin.model.ApprFormDocTemplate;
import com.lgcns.ikep4.approval.admin.search.ApprFormSearchCondition;
import com.lgcns.ikep4.approval.admin.service.ApprFormDocTemplateService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * WorkPlace List Impl 
 * 
 * @author wonchu
 * @version $Id: ApprFormDocTemplateServiceImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Service("apprFormDocTemplateServiceImpl")
@SuppressWarnings("unchecked")
public class ApprFormDocTemplateServiceImpl extends GenericServiceImpl<ApprFormDocTemplate, String> implements ApprFormDocTemplateService{
	
	@Autowired
	private ApprFormDocTemplateDao apprFormDocTemplateDao;
	
	@Autowired
	private IdgenService idgenService;
	
	/**
	 * apprForm Doc Template 목록
	 * @param 	ApprFormSearchCondition
	 * @return 	SearchResult
	 */
	public SearchResult<ApprFormDocTemplate> listBySearchCondition(ApprFormSearchCondition searchCondition) {
		
		Integer count = apprFormDocTemplateDao.countBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);
		
		SearchResult<ApprFormDocTemplate> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ApprFormDocTemplate>(searchCondition);
		} else {
			List<ApprFormDocTemplate> formList = apprFormDocTemplateDao.listBySearchCondition(searchCondition);
			searchResult = new SearchResult<ApprFormDocTemplate>(formList, searchCondition);
		}

		return searchResult;
		
	}

	/**
	 * apprForm Doc Template  생성
	 * @param 	ApprFormDocTemplate
	 * @return 	String
	 */
	public String createApprFormDocTemplate(ApprFormDocTemplate apprFormDocTemplate) {
		apprFormDocTemplate.setTemplateId(idgenService.getNextId());
		return apprFormDocTemplateDao.create(apprFormDocTemplate);
	}
	
	/**
	 * apprForm Doc Template  수정
	 * @param 	ApprFormDocTemplate
	 * @return 	void
	 */
	public void updateApprFormDocTemplate(ApprFormDocTemplate apprFormDocTemplate) {
		apprFormDocTemplateDao.update(apprFormDocTemplate);
	}
	
	/**
	 * apprForm Doc Template  삭제
	 * @param 	ApprFormDocTemplate
	 * @return 	void
	 */
	public void deleteApprFormDocTemplate(ApprFormDocTemplate apprFormDocTemplate) {
		apprFormDocTemplate.setTemplates(StringUtils.split(apprFormDocTemplate.getTemplateId(), ","));
		
		apprFormDocTemplateDao.delete(apprFormDocTemplate);
	}
	
}
