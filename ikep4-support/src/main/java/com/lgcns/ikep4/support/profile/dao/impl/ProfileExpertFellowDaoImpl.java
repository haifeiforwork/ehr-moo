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
import com.lgcns.ikep4.support.profile.dao.ProfileExpertFellowDao;
import com.lgcns.ikep4.support.profile.model.ProfileExpertFellow;

/**
 * 프로파일에서 Expert 전문가 목록을 가져 오는 class Impl
 *
 * @author 이형운
 * @version $Id: ProfileExpertFellowDaoImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Repository("profileExpertFellowDao")
public class ProfileExpertFellowDaoImpl extends GenericDaoSqlmap<ProfileExpertFellow, String> implements
		ProfileExpertFellowDao {

	
	/**
	 * PROFILE_EXPERT_FELLOW_NAME_SPACE
	 */
	private static final String PROFILE_EXPERT_FELLOW_NAME_SPACE = "support.profile.dao.ProfileExpertFellow.";
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.profile.dao.ProfileExpertFellowDao#listProfileFellowExpert(java.lang.String)
	 */
	public List<ProfileExpertFellow> listProfileFellowExpert(String userId) {
		return sqlSelectForList(PROFILE_EXPERT_FELLOW_NAME_SPACE + "listProfileFellowExpert",userId);
	}
	
	@Deprecated
	public ProfileExpertFellow get(String id) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public String create(ProfileExpertFellow object) {
		return null;
	}

	@Deprecated
	public void update(ProfileExpertFellow object) {

	}

	@Deprecated
	public void remove(String id) {
	}


}
