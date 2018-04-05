/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.survey.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.servicepack.survey.model.Survey;
import com.lgcns.ikep4.servicepack.survey.model.TargetKey;
import com.lgcns.ikep4.servicepack.survey.search.SurveySearchCondition;

/**
 * 설문조사 dao
 *
 * @author ihko11
 * @version $Id: SurveyService.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Transactional
public interface SurveyService extends GenericService<Survey, String> {
	/**
	 * 설문 신청 리스트
	 * @param surveySearchCondition
	 * @return
	 */
	public SearchResult<Survey> listBySearchCondition(SurveySearchCondition surveySearchCondition) ;
	/**
	 * 설문 승인 리스트
	 * @param surveySearchCondition
	 * @return
	 */
	public SearchResult<Survey> approveListBySearchCondition(SurveySearchCondition surveySearchCondition) ;
	/**
	 * 설문 진행 리스트
	 * @param surveySearchCondition
	 * @return
	 */
	public SearchResult<Survey> ingListBySearchCondition(SurveySearchCondition surveySearchCondition) ;
	/**
	 * 설문 종료 리스트
	 * @param surveySearchCondition
	 * @return
	 */
	public SearchResult<Survey> endListBySearchCondition(SurveySearchCondition surveySearchCondition) ;
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
	 * 복사
	 * @param survey
	 */
	public void copy(Survey survey);
	/**
	 * 마감일자 일별 스케쥴려 업데이터
	 *
	 */
	public void exipireDateUpdate();
	/**
	 * 참여자 목록 리스트에 존재여부
	 * @param targetKey
	 * @return
	 */
	public boolean existByUserId(TargetKey targetKey);
	/**
	 * 개별 리스트 총카운트
	 * @param surveyId
	 * @return
	 */
	public Integer getTotalCountBySurveyId(String surveyId);
	/**
	 * 전사 리스트 총카운트
	 * @return
	 */
	public Integer getTotalCountByMember(String portalId);
	/**
	 * 설문 그룹 대상 가져오기
	 * @return
	 */
	public List<HashMap<String, String>> getListTargetGroup(String targetId);	
	/**
	 * 설문 대상 정보 가져오기
	 * @return
	 */
	public List<HashMap<String, String>> getListTarget(String targetId);
	
	
	/**
	 * 오픈한 설문 설문종료일 변경하기
	 * @param data
	 */
    public void updateEndDate( HashMap<String, String> data );	
    
    public String readComment(String id);
}
