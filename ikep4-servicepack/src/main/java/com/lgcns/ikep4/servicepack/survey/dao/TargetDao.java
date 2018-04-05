/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.survey.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.servicepack.survey.model.Target;
import com.lgcns.ikep4.servicepack.survey.model.TargetKey;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 설문조사 타겟dao
 *
 * @author ihko11
 * @version $Id: TargetDao.java 16244 2011-08-18 04:11:42Z giljae $
 */
public interface TargetDao extends GenericDao<Target,TargetKey> {
	/**
	 * 설문 타겟 리스트
	 * @param surveyId
	 * @return
	 */
	public List<Target> getAllBySurveyId(String surveyId);
	/**
	 * 설문 타겟 리스트 삭제
	 * @param surveyId
	 */
	public void removeAllBySurveyId(String surveyId);
	/**
	 * 설문 개별설정 타겟 리스트
	 * @param surveyId
	 * @return
	 */
	public Integer getTotalCountBySurveyId(String surveyId);
	/**
	 * 설문 전사 타겟 리스트
	 * @return
	 */
	public Integer getTotalCountByMember(String portalId);
	/**
	 * 설문 타겟 존재여부
	 * @param targetKey
	 * @return
	 */
	public boolean existByUserId(TargetKey targetKey);
	
	public List<Target> getSubGroupList(Map<String, String> map);
	
}
