/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.approval.work.dao.ApprListDao;
import com.lgcns.ikep4.approval.work.model.ApprList;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * 기안함 DAO 구현
 * 
 * @author jeehye
 * @version $Id: ApprListDaoImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Repository("ApprListDao")
public class ApprListDaoImpl extends GenericDaoSqlmap<ApprList, String> implements ApprListDao {

	private static final String NAMESPACE = "com.lgcns.ikep4.approval.work.dao.ApprList.";

	public String create(ApprList arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public ApprList get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(ApprList arg0) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * 내가 기안한 문서 목록
	 */
	public List<ApprList> listByMyRequest(ApprListSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listByMyRequest", searchCondition);
	}

	/**
	 * 검색조건에 의한 Default 내가 기안한 문서 목록 갯수
	 */
	public Integer countByMyRequest(ApprListSearchCondition searchCondition) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countByMyRequest", searchCondition);
		return count;
	}
	
	/**
	 * 결재함 목록
	 */
	public List<ApprList> listBySearchCondition(ApprListSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);
	}

	/**
	 * 결재함 목록 갯수
	 */
	public Integer countBySearchCondition(ApprListSearchCondition searchCondition) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
		return count;
	}
	
	/**
	 * 참조문서
	 */
	public List<ApprList> listBySearchConditionRef(ApprListSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchConditionRef", searchCondition);
	}

	/**
	 * 참조문서갯수
	 */
	public Integer countBySearchConditionRef(ApprListSearchCondition searchCondition) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchConditionRef", searchCondition);
		return count;
	}
	
	/**
	 * 열람문서
	 */
	public List<ApprList> listBySearchConditionRead(ApprListSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchConditionRead", searchCondition);
	}

	/**
	 * 열람문서갯수
	 */
	public Integer countBySearchConditionRead(ApprListSearchCondition searchCondition) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchConditionRead", searchCondition);
		return count;
	}

	/**
	 * 통합결재함
	 */
	public List<ApprList> listBySearchConditionIntegrate(ApprListSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchConditionIntegrate", searchCondition);
	}

	/**
	 * 통합결재함갯수
	 */
	public Integer countBySearchConditionIntegrate(ApprListSearchCondition searchCondition) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchConditionIntegrate", searchCondition);
		return count;
	}
	
	/**
	 * 부서수신함
	 */
	public List<ApprList> listBySearchConditionDeptRec(ApprListSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchConditionDeptRec", searchCondition);
	}

	/**
	 * 부서수신함
	 */
	public Integer countBySearchConditionDeptRec(ApprListSearchCondition searchCondition) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchConditionDeptRec", searchCondition);
		return count;
	}
	
	/**
	 * 검토요청함
	 */
	public List<ApprList> listBySearchConditionExam(ApprListSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchConditionExam", searchCondition);
	}

	/**
	 * 검토요청함
	 */
	public Integer countBySearchConditionExam(ApprListSearchCondition searchCondition) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchConditionExam", searchCondition);
		return count;
	}
	
	/**
	 * 위임문서
	 */
	public List<ApprList> listBySearchConditionLineEntrust(ApprListSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchConditionLineEntrust", searchCondition);
	}

	/**
	 * 위임문서 count
	 */
	public Integer countBySearchConditionLineEntrust(ApprListSearchCondition searchCondition) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchConditionLineEntrust", searchCondition);
		return count;
	}
	
	/**
	 * 개인보관함
	 */
	public List<ApprList> listBySearchConditionUserDoc(ApprListSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchConditionUserDoc", searchCondition);
	}

	/**
	 * 개인보관함 count
	 */
	public Integer countBySearchConditionUserDoc(ApprListSearchCondition searchCondition) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchConditionUserDoc", searchCondition);
		return count;
	}
	
	/**
	 * 이름가져오기
	 */
	public String getUserName(String userId) {
		String userName = (String) this.sqlSelectForObject(NAMESPACE + "getUserName", userId);
		return userName;
	}
	
	/**
	 * 이름가져오기
	 */
	public void updateSetSecurity(Map<String,String> map) {
		
		sqlUpdate(NAMESPACE + "updateSetSecurity", map);
	}
	
}
