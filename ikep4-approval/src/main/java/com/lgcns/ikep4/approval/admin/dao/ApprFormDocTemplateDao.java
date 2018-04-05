/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.approval.admin.model.ApprFormDocTemplate;
import com.lgcns.ikep4.approval.admin.search.ApprFormSearchCondition;



/**
 * TODO Javadoc주석작성
 *
 * @author wonchu
 * @version $Id: FormListDao.java 16245 2011-08-18 04:28:59Z giljae $
 */
public interface ApprFormDocTemplateDao extends GenericDao<ApprFormDocTemplate, String> {
	
	/**
	 * apprForm Doc Template 목록
	 * @param 	ApprFormSearchCondition
	 * @return 	SearchResult
	 */
	public List<ApprFormDocTemplate> listBySearchCondition(ApprFormSearchCondition searchCondition);
	
	/**
	 * apprForm Doc Template count
	 * @param 	ApprFormSearchCondition
	 * @return 	Integer
	 */
	public Integer countBySearchCondition(ApprFormSearchCondition searchCondition);
	
	/**
	 * apprForm Doc Template  삭제
	 * @param 	ApprFormDocTemplate
	 * @return 	void
	 */
	public void delete(ApprFormDocTemplate apprFormDocTemplate);
}