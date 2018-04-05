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

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.wfapproval.work.dao.ApListDao;
import com.lgcns.ikep4.wfapproval.work.model.ApList;
import com.lgcns.ikep4.wfapproval.work.search.ApListSearchCondition;
import com.lgcns.ikep4.wfapproval.work.service.ApListTempService;


/**
 * 임시저장 목록 Service 구현
 * 
 * @author 장규진(mistpoet@paran.com)
 * @version $Id: ApListTempServiceImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Service("apListTempService")
public class ApListTempServiceImpl extends GenericServiceImpl<ApList, String> implements ApListTempService {

	@Autowired
	private ApListDao apListDao;

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#get(java.io.
	 * Serializable)
	 */
	public SearchResult<ApList> readApSearchList(ApListSearchCondition apListSearchCondition) {
		SearchResult<ApList> searchResult = null;
		
		Integer count = this.apListDao.getCountApList(apListSearchCondition);
		
		apListSearchCondition.terminateSearchCondition(count);  
		
		//목록이 없다면...
		if(apListSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ApList>(apListSearchCondition);
		} else {
			List<ApList> apList = this.apListDao.getApSearchList(apListSearchCondition);  
			
			searchResult = new SearchResult<ApList>(apList, apListSearchCondition); 
		}  
		
		return searchResult;		
	}
	
}
