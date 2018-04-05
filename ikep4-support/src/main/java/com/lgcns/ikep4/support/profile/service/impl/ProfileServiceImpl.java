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
import com.lgcns.ikep4.support.profile.dao.ProfileExpertFellowDao;
import com.lgcns.ikep4.support.profile.model.ProfileExpertFellow;
import com.lgcns.ikep4.support.profile.service.ProfileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;


/**
 * 프로파일에서 사용자 정보를 업데이트 하기 위해  User Service 를 호출 하는 Profile Service Implement Class
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: ProfileServiceImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Service("profileService")
public class ProfileServiceImpl extends GenericServiceImpl<User, String> implements ProfileService {

	/**
	 * 프로파일 user 정보 컨트롤 용 Service
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * 프로파일 대상자의 Expert Fellow 정보 컨트롤 조회용 Dao
	 */
	@Autowired
	private ProfileExpertFellowDao profileExpertFellowDao;
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.profile.service.ProfileService#updateProfile(com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateProfile(User profile) {
		userService.updateProfile(profile);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.profile.service.ProfileService#updateProfileStaus(com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateProfileStaus(User profile) {
		userService.updateProfileStaus(profile);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.profile.service.ProfileService#updatePictureId(com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updatePictureId(User profile) {
		userService.updatePictureId(profile);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.profile.service.ProfileService#updateProfilePictureId(com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateProfilePictureId(User profile) {
		userService.updateProfilePictureId(profile);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.profile.service.ProfileService#updateTwitterInfo(com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateTwitterInfo(User profile) {
		userService.updateTwitterInfo(profile);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.profile.service.ProfileService#updateFacebookInfo(com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateFacebookInfo(User profile) {
		userService.updateFacebookInfo(profile);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.profile.service.ProfileService#updateExportField(com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateExportField(User profile) {
		userService.updateExportField(profile);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.profile.service.ProfileService#listProfileFellowExpert(java.lang.String)
	 */
	public List<ProfileExpertFellow> listProfileFellowExpert(String userId) {
		return profileExpertFellowDao.listProfileFellowExpert(userId);
	}
	
}
