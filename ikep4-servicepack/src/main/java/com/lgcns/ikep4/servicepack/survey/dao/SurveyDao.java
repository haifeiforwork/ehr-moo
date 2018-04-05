/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.survey.dao;

import java.util.HashMap;
import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.servicepack.survey.model.Survey;
import com.lgcns.ikep4.servicepack.survey.search.SurveySearchCondition;

/**
 * 설문조사 dao
 *
 * @author ihko11
 * @version $Id: SurveyDao.java 16244 2011-08-18 04:11:42Z giljae $
 */
public interface SurveyDao extends GenericDao<Survey,String> {
	/**
	 * 설문 신청리스트
	 * @param surveySearchCondition
	 * @return
	 */
	List<Survey> listBySearchCondition(SurveySearchCondition surveySearchCondition);
	/**
	 * 설문 신청 리스트 카운트
	 * @param surveySearchCondition
	 * @return
	 */
	Integer countBySearchCondition(SurveySearchCondition surveySearchCondition); 
	
	/**
	 * 설문 승인리스트 
	 * @param surveySearchCondition
	 * @return
	 */
	List<Survey> approveListBySearchCondition(SurveySearchCondition surveySearchCondition);
	/**
	 * 설문 승인리스트 카운트
	 * @param surveySearchCondition
	 * @return
	 */
	Integer approveCountBySearchCondition(SurveySearchCondition surveySearchCondition); 
	
	/**
	 * 설문 진행리스트
	 * @param surveySearchCondition
	 * @return
	 */
	List<Survey> ingListBySearchCondition(SurveySearchCondition surveySearchCondition);
	/**
	 * 설문 진행중리스트 카운트
	 * @param surveySearchCondition
	 * @return
	 */
	Integer ingCountBySearchCondition(SurveySearchCondition surveySearchCondition);
	
	/**
	 * 설문 결과 리스트
	 * @param surveySearchCondition
	 * @return
	 */
	List<Survey> endListBySearchCondition(SurveySearchCondition surveySearchCondition);
	/**
	 * 설문 결과리스트 카운트
	 * @param surveySearchCondition
	 * @return
	 */
	Integer endCountBySearchCondition(SurveySearchCondition surveySearchCondition);
	
	/**
	 * 승인
	 * @param survey
	 */
	public void approve(Survey survey);
	/**
	 * 거부
	 * @param survey
	 */
	public void reject(Survey survey);
	/**
	 * 설문 마감일자 스케줄링 업데이터
	 *
	 */
	public void exipireDateUpdate();
	/**
	 * 설문 그룹 대상 가져오기
	 *
	 */
	public List getListTargetGroup(String targetId);	
	/**
	 * 설문 대상 정보 가져오기
	 *
	 */
	public List getListTarget(String targetId);
	/**
	 * 오픈한 설문 설문종료일 변경하기
	 * @param data
	 */
    void updateEndDate( HashMap<String, String> data );	
    
    public String getComment(String id);
	
}
