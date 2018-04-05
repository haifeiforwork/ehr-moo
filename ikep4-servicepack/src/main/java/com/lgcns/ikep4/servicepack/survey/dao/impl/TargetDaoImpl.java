/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.survey.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.servicepack.survey.dao.TargetDao;
import com.lgcns.ikep4.servicepack.survey.model.Target;
import com.lgcns.ikep4.servicepack.survey.model.TargetKey;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 설문조사 참여자 목록 impl
 *
 * @author ihko11
 * @version $Id: TargetDaoImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Repository
public class TargetDaoImpl extends GenericDaoSqlmap<Target,TargetKey> implements TargetDao {
	
	private static final String NAMESPACE = "com.lgcns.ikep4.servicepack.survey.svTarget.";
	
	/**
	 * 설문 읽기
	 */
	public Target get(TargetKey id) {
		return (Target) sqlSelectForObject(NAMESPACE+"get", id);
	}
	
/**
 * 참여자 존재여부
 */
	public boolean exists(TargetKey id) {
		Integer count = (Integer)sqlSelectForObject(NAMESPACE+"exists", id);
		return count > 0;
	}
	
	/**
	 * 참여대상자 설문별 총 카운트
	 */
	public Integer getTotalCountBySurveyId(String id) {
		return (Integer)sqlSelectForObject(NAMESPACE+"getTotalCountBySurveyId", id);
	}
	
	/**
	 * 전사일경우 사용자 총 카운트
	 */
	public Integer getTotalCountByMember(String portalId) {
		return (Integer)sqlSelectForObject(NAMESPACE+"getTotalCountByMember",portalId);
	}
	
	
	/**
	 * 참여대상자 사용자별 존재여부
	 */
	public boolean existByUserId(TargetKey id) {
		Integer count = (Integer)sqlSelectForObject(NAMESPACE+"existByUserId", id);
		return count > 0;
	}
	
/**
 * 참여대상자 입력
 */
	public TargetKey create(Target target) {
		sqlInsert(NAMESPACE+"create", target);
		
		return target.getTargetKey();
	}

	/**
	 * 설문 대상자 수정
	 */
	public void update(Target object) {
	}
	
	/**
	 * 참여대상자 삭제
	 */
	public void remove(TargetKey id) {
		sqlDelete(NAMESPACE+"remove", id);
	}
	
	/**
	 * 참여 대상자 설문별 전참여 대상자 삭제
	 */
	public void removeAllBySurveyId(String id) {
		sqlDelete(NAMESPACE+"removeAllBySurveyId", id);
	}
	

	/**
	 * target of surveyId All List
	 */
	public List<Target> getAllBySurveyId(String surveyId) {
		return  sqlSelectForList(NAMESPACE+"getAllBySurveyId", surveyId);	
	}
	/**
	 * 설문 그룹 대상 가져오기
	 */
	public List<Target> getSubGroupList(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "getSubGroupList", map);
	}		
}
