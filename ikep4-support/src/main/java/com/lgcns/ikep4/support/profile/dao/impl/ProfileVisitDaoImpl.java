/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.profile.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.profile.dao.ProfileVisitDao;
import com.lgcns.ikep4.support.profile.model.ProfileVisit;

/**
 * 프로파일 방문자 기록용 DAO impl
 *
 * @author 이형운
 * @version $Id: ProfileVisitDaoImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Repository("profileVisitDao")
public class ProfileVisitDaoImpl extends GenericDaoSqlmap<ProfileVisit, String> implements ProfileVisitDao {

	/**
	 * PROFILE_VISIT_NAME_SPACE
	 */
	private static final String PROFILE_VISIT_NAME_SPACE = "support.profile.dao.ProfileVisit.";
	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.profile.dao.ProfileVisitDao#selectAllByOwnerId(com.lgcns.ikep4.support.profile.model.ProfileVisit)
	 */
	public List<ProfileVisit> selectAllByOwnerId(ProfileVisit profileVisit) {
		return sqlSelectForList(PROFILE_VISIT_NAME_SPACE + "selectAllByOwnerId", profileVisit);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.profile.dao.ProfileVisitDao#selectAllCountByOwnerId(com.lgcns.ikep4.support.profile.model.ProfileVisit)
	 */
	public Integer selectAllCountByOwnerId(ProfileVisit profileVisit) {
		return (Integer) sqlSelectForObject(PROFILE_VISIT_NAME_SPACE + "selectAllCountByOwnerId", profileVisit);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(com.lgcns.ikep4.support.profile.model.ProfileVisit)
	 */
	public String create(ProfileVisit profileVisit) {
		return (String) sqlInsert(PROFILE_VISIT_NAME_SPACE + "create", profileVisit);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.profile.dao.ProfileVisitDao#physicalDelete(com.lgcns.ikep4.support.profile.model.ProfileVisit)
	 */
	public void physicalDelete(ProfileVisit profileVisit) {
		sqlDelete(PROFILE_VISIT_NAME_SPACE + "physicalDelete", profileVisit);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.profile.dao.ProfileVisitDao#physicalDeleteThreadByOwnerId(com.lgcns.ikep4.support.profile.model.ProfileVisit)
	 */
	public void physicalDeleteThreadByOwnerId(ProfileVisit profileVisit) {
		sqlDelete(PROFILE_VISIT_NAME_SPACE + "physicalDeleteThreadByOwnerId", profileVisit);
	}

	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(com.lgcns.ikep4.support.profile.model.ProfileVisit)
	 */
	public ProfileVisit get(ProfileVisit profileVisit) {
		return (ProfileVisit) sqlSelectForObject(PROFILE_VISIT_NAME_SPACE + "get", profileVisit);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	@Deprecated
	public void update(ProfileVisit profileVisit) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	@Deprecated
	public void remove(String id) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public ProfileVisit get(String id) {
		return null;
	}

}
