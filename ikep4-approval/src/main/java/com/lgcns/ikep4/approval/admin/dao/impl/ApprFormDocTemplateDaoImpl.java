/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.approval.admin.dao.ApprFormDocTemplateDao;
import com.lgcns.ikep4.approval.admin.model.ApprFormDocTemplate;
import com.lgcns.ikep4.approval.admin.search.ApprFormSearchCondition;


/**
 * TODO Javadoc주석작성
 *
 * @author wonchu
 * @version $Id: FormListDaoImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Repository
public class ApprFormDocTemplateDaoImpl extends GenericDaoSqlmap<ApprFormDocTemplate, String> implements ApprFormDocTemplateDao{
	
	private static final String NAMESPACE = "approval.admin.dao.ApprFormDocTemplate.";
	
	/**
	 * apprForm Doc Template 목록
	 * @param 	ApprFormSearchCondition
	 * @return 	SearchResult
	 */
	public List<ApprFormDocTemplate> listBySearchCondition(ApprFormSearchCondition searchCondition) {
		return sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);
	}
	
	/**
	 * apprForm Doc Template count
	 * @param 	ApprFormSearchCondition
	 * @return 	Integer
	 */
	public Integer countBySearchCondition(ApprFormSearchCondition searchCondition) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);

		return count;
	}
	
	/**
	 * apprForm Doc Template  생성
	 * @param 	ApprFormDocTemplate
	 * @return 	String
	 */
	public String create(ApprFormDocTemplate apprFormDocTemplate){
		sqlInsert(NAMESPACE + "create", apprFormDocTemplate);
		return apprFormDocTemplate.getTemplateId();
	}
	
	/**
	 * apprForm Doc Template  수정
	 * @param 	ApprFormDocTemplate
	 * @return 	void
	 */
	public void update(ApprFormDocTemplate apprFormDocTemplate){
		sqlInsert(NAMESPACE + "update", apprFormDocTemplate);
	}
	
	/**
	 * apprForm Doc Template  삭제
	 * @param 	ApprFormDocTemplate
	 * @return 	void
	 */
	public void delete(ApprFormDocTemplate apprFormDocTemplate){
		sqlDelete(NAMESPACE + "delete", apprFormDocTemplate);
	}
	
	public ApprFormDocTemplate get(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}
}