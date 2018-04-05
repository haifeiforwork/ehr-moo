/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.profile.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.profile.dao.ProfileVisitDao;
import com.lgcns.ikep4.support.profile.model.ProfileVisit;
import com.lgcns.ikep4.support.profile.service.ProfileVisitService;

/**
 * 프로파일 방문자 기록용 Service Impl
 *
 * @author 이형운
 * @version $Id: ProfileVisitServiceImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Service("profileVisitService")
public class ProfileVisitServiceImpl extends GenericServiceImpl<ProfileVisit, String> implements ProfileVisitService {

	/**
	 * 프로파일 방문자 정보 컨트롤용 Dao
	 */
	@Autowired
	private ProfileVisitDao profileVisitDao;
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#recordProfileVisitByDay(com.lgcns.ikep4.support.profile.model.ProfileVisit)
	 */
	public void recordProfileVisitByDay(ProfileVisit profileVisit) {
		
		ProfileVisit todayProfileVisit = profileVisitDao.get(profileVisit);
		// Null 인 경우에만 입력
		if(todayProfileVisit == null){
			profileVisitDao.create(profileVisit);
		}
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#create(com.lgcns.ikep4.support.profile.model.ProfileVisit)
	 */
	public String create(ProfileVisit profileVisit) {
		return profileVisitDao.create(profileVisit);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.profile.service.ProfileVisitService#selectAllByOwnerId(com.lgcns.ikep4.support.profile.model.ProfileVisit)
	 */
	public List<ProfileVisit> selectAllByOwnerId(ProfileVisit profileVisit) {
		return profileVisitDao.selectAllByOwnerId(profileVisit);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.profile.service.ProfileVisitService#selectAllCountByOwnerId(com.lgcns.ikep4.support.profile.model.ProfileVisit)
	 */
	public Integer selectAllCountByOwnerId(ProfileVisit profileVisit) {
		return profileVisitDao.selectAllCountByOwnerId(profileVisit);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.profile.service.ProfileVisitService#get(com.lgcns.ikep4.support.profile.model.ProfileVisit)
	 */
	public ProfileVisit get(ProfileVisit profileVisit) {
		return profileVisitDao.get(profileVisit);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.profile.service.ProfileVisitService#physicalDelete(com.lgcns.ikep4.support.profile.model.ProfileVisit)
	 */
	public void physicalDelete(ProfileVisit profileVisit) {
		profileVisitDao.physicalDelete(profileVisit);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.profile.service.ProfileVisitService#physicalDeleteThreadByOwnerId(com.lgcns.ikep4.support.profile.model.ProfileVisit)
	 */
	public void physicalDeleteThreadByOwnerId(ProfileVisit profileVisit) {
		profileVisitDao.physicalDeleteThreadByOwnerId(profileVisit);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#read(java.io.Serializable)
	 */
	@Deprecated
	public ProfileVisit read(String id) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(String id) {
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#update(java.lang.Object)
	 */
	@Deprecated
	public void update(ProfileVisit object) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#delete(java.io.Serializable)
	 */
	@Deprecated
	public void delete(String id) {
	}

}
