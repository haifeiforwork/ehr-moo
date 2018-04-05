/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.approval.work.model.ApprList;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;


/**
 * 기안함
 * 
 * @author 서지혜
 * @version $Id$
 */

public interface ApprListDao extends GenericDao<ApprList, String> {

	/**
	 * 내가 기안한 문서 목록
	 * 
	 * @param ApprListSearchCondition
	 * @return
	 */
	List<ApprList> listByMyRequest(ApprListSearchCondition searchCondition);

	/**
	 * 내가 기안한 문서 Count
	 * 
	 * @param ApprListSearchCondition
	 * @return
	 */
	Integer countByMyRequest(ApprListSearchCondition searchCondition);
	
	/**
	 * 결재함 목록
	 * 
	 * @param ApprListSearchCondition
	 * @return
	 */
	List<ApprList> listBySearchCondition(ApprListSearchCondition searchCondition);
	
	/**
	 * 결재함 Count
	 * 
	 * @param ApprListSearchCondition
	 * @return
	 */
	Integer countBySearchCondition(ApprListSearchCondition searchCondition);
	
	/**
	 * 참조문서
	 * 
	 * @param ApprListSearchCondition
	 * @return
	 */
	List<ApprList> listBySearchConditionRef(ApprListSearchCondition searchCondition);
	
	/**
	 * 참조문서 Count
	 * 
	 * @param ApprListSearchCondition
	 * @return
	 */
	Integer countBySearchConditionRef(ApprListSearchCondition searchCondition);
	
	/**
	 * 열람문서
	 * 
	 * @param ApprListSearchCondition
	 * @return
	 */
	List<ApprList> listBySearchConditionRead(ApprListSearchCondition searchCondition);
	
	/**
	 * 열람문서 Count
	 * 
	 * @param ApprListSearchCondition
	 * @return
	 */
	Integer countBySearchConditionRead(ApprListSearchCondition searchCondition);
	
	/**
	 * 통합결재함
	 * 
	 * @param ApprListSearchCondition
	 * @return
	 */
	List<ApprList> listBySearchConditionIntegrate(ApprListSearchCondition searchCondition);
	
	/**
	 * 통합결재함 Count
	 * 
	 * @param ApprListSearchCondition
	 * @return
	 */
	Integer countBySearchConditionIntegrate(ApprListSearchCondition searchCondition);
	
	/**
	 * 부서수신함
	 * 
	 * @param ApprListSearchCondition
	 * @return
	 */
	List<ApprList> listBySearchConditionDeptRec(ApprListSearchCondition searchCondition);
	
	/**
	 * 부서수신함 Count
	 * 
	 * @param ApprListSearchCondition
	 * @return
	 */
	Integer countBySearchConditionDeptRec(ApprListSearchCondition searchCondition);
	
	/**
	 * 검토요청함
	 * 
	 * @param ApprListSearchCondition
	 * @return
	 */
	List<ApprList> listBySearchConditionExam(ApprListSearchCondition searchCondition);
	
	/**
	 * 검토요청함 Count
	 * 
	 * @param ApprListSearchCondition
	 * @return
	 */
	Integer countBySearchConditionExam(ApprListSearchCondition searchCondition);
	
	/**
	 * 위임문서
	 * 
	 * @param ApprListSearchCondition
	 * @return
	 */
	List<ApprList> listBySearchConditionLineEntrust(ApprListSearchCondition searchCondition);
	
	/**
	 * 위임문서 Count
	 * 
	 * @param ApprListSearchCondition
	 * @return
	 */
	Integer countBySearchConditionLineEntrust(ApprListSearchCondition searchCondition);
	
	/**
	 * 개인보관함
	 * 
	 * @param ApprListSearchCondition
	 * @return
	 */
	List<ApprList> listBySearchConditionUserDoc(ApprListSearchCondition searchCondition);
	
	/**
	 * 개인보관함 Count
	 * 
	 * @param ApprListSearchCondition
	 * @return
	 */
	Integer countBySearchConditionUserDoc(ApprListSearchCondition searchCondition);
	
	/**
	 * 이름가져오기
	 * @param userId
	 * @return  String
	 */
	String getUserName(String userId);
	
	/**
	 * 이름가져오기
	 * @param userId
	 * @return  String
	 */
	void updateSetSecurity(Map<String,String> map);
	
	
}
