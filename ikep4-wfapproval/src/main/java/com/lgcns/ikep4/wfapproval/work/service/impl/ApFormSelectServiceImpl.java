/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.work.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.wfapproval.admin.dao.ApFormDao;
import com.lgcns.ikep4.wfapproval.admin.dao.ApFormTplDao;
import com.lgcns.ikep4.wfapproval.admin.model.ApForm;
import com.lgcns.ikep4.wfapproval.admin.model.ApFormTpl;
import com.lgcns.ikep4.wfapproval.admin.search.ApFormSearchCondition;
import com.lgcns.ikep4.wfapproval.work.service.ApFormSelectService;


/**
 * 결재 양식관리 Service 구현
 * 
 * @author 박희진(neoheejin@naver.com)
 * @version $Id: ApFormSelectServiceImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Service("apFormSelectService")
public class ApFormSelectServiceImpl extends GenericServiceImpl<ApForm, String> implements ApFormSelectService {

	@Autowired
	private ApFormDao apFormDao;

	@Autowired
	private ApFormTplDao apFormTplDao;

	/**
	 * 양식 마스터 목록 조회
	 * 
	 * @see com.lgcns.ikep4.wfapproval.work.service.ApFormSelectService#listApFormSelect(com.lgcns.ikep4.wfapproval.admin.search.ApFormSearchCondition)
	 */
	@Transactional(readOnly = true)
	public SearchResult<ApForm> listApFormSelect(ApFormSearchCondition apFormSearchCondition) {
		
		SearchResult<ApForm> searchResult = null;
		
		Integer count = this.apFormDao.countApFormSelect(apFormSearchCondition);
		
		apFormSearchCondition.terminateSearchCondition(count);  
		
		//목록이 없다면...
		if(apFormSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ApForm>(apFormSearchCondition);
		} else {
			List<ApForm> apFormList = this.apFormDao.listApFormSelect(apFormSearchCondition);  
			
			searchResult = new SearchResult<ApForm>(apFormList, apFormSearchCondition); 
		}
		
		return searchResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#get(java.io.
	 * Serializable)
	 */
	public ApForm readApForm(String formId) {

		ApForm apForm = apFormDao.get(formId);

		// get Form Template.
		if (apFormTplDao.exists(formId)) {
			apForm.setApFormTpl(apFormTplDao.get(formId));
		} else {
			ApFormTpl apFormTpl = new ApFormTpl();

			apFormTpl.setFormId(apForm.getFormId());

			apForm.setApFormTpl(apFormTpl);
		}

		return apForm;
	}
}
