/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.userconfig.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.support.userconfig.dao.UserConfigDao;
import com.lgcns.ikep4.support.userconfig.model.UserConfig;
import com.lgcns.ikep4.support.userconfig.service.UserConfigService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * 개인화 설정 서비스 구현 클래스
 *
 * @author 최현식
 * @version $Id: UserConfigServiceImpl.java 19225 2012-06-12 09:13:40Z yu_hs $
 */
@Service
public class UserConfigServiceImpl implements UserConfigService {
	@Autowired
	private UserConfigDao userConfigDao;

	@Autowired
	private IdgenService idgenService;

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.userconfig.service.UserConfigService#listUserConfigByUserId(java.lang.String)
	 */
	public List<UserConfig> listUserConfigByUserId(String userId) {

		return this.userConfigDao.listByUserId(userId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.userconfig.service.UserConfigService#readUserConfigByUserIdAndModuleId(java.lang.String, java.lang.String)
	 */
	public UserConfig readUserConfigByUserIdAndModuleId(String userId, String moduleId, String portalId) {

		UserConfig parameter = new UserConfig();

		parameter.setUserId(userId);
		parameter.setModuleId(moduleId);
		parameter.setPortalId(portalId);


		return this.userConfigDao.getByUserIdAndModuleId(parameter);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.userconfig.service.UserConfigService#readUserConfigById(java.lang.String)
	 */
	public UserConfig readUserConfigById(String id) {
		return this.userConfigDao.get(id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.userconfig.service.UserConfigService#createUserConfig(com.lgcns.ikep4.support.userconfig.model.UserConfig)
	 */
	public String createUserConfig(UserConfig userConfig) {
		String generatedId = this.idgenService.getNextId();

		userConfig.setId(generatedId);

		UserConfig updated = this.userConfigDao.getByUserIdAndModuleId(userConfig);

		if(updated == null) {
			String createdId = this.userConfigDao.create(userConfig);
			return createdId;
		} else {
			userConfig.setId(updated.getId());
			this.updateUserConfig(userConfig);

			return updated.getId();
		}
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.userconfig.service.UserConfigService#updateUserConfig(com.lgcns.ikep4.support.userconfig.model.UserConfig)
	 */
	public void updateUserConfig(UserConfig userConfig) {
		this.userConfigDao.update(userConfig);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.userconfig.service.UserConfigService#deleteUserConfig(java.lang.String)
	 */
	public void deleteUserConfig(String id) {
		this.userConfigDao.remove(id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.userconfig.service.UserConfigService#deleteUserConfigByUserIdAndModuleId(java.lang.String, java.lang.String)
	 */
	public void deleteUserConfigByUserIdAndModuleId(String userId, String moduleId, String portalId) {
		UserConfig parameter = new UserConfig();

		parameter.setUserId(userId);
		parameter.setModuleId(moduleId);
		parameter.setPortalId(portalId);

		this.userConfigDao.removeUserConfigByUserIdAndModuleId(parameter);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.userconfig.service.UserConfigService#existsUserConfigByUserIdAndModuleId(java.lang.String, java.lang.String)
	 */
	public Boolean existsUserConfigByUserIdAndModuleId(String userId, String moduleId, String portalId) {
		UserConfig parameter = new UserConfig();

		parameter.setUserId(userId);
		parameter.setModuleId(moduleId);
		parameter.setPortalId(portalId);

		return this.userConfigDao.existsByUserIdAndModuleId(parameter);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.userconfig.service.UserConfigService#existsUserConfigById(java.lang.String)
	 */
	public Boolean existsUserConfigById(String id) {
		return this.userConfigDao.exists(id);
	}



}
