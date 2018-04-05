/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.todo.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.lightpack.todo.dao.UserSettingDao;
import com.lgcns.ikep4.lightpack.todo.model.UserSetting;
import com.lgcns.ikep4.lightpack.todo.service.UserSettingService;

/**
 * Service 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: UserSettingServiceImpl.java 16240 2011-08-18 04:08:15Z giljae $
 */
@Service 
@Transactional
public class UserSettingServiceImpl extends GenericServiceImpl<UserSetting, String> implements UserSettingService {
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	private UserSettingDao userSettingDao;

	public String create(UserSetting userSetting) {
		return userSettingDao.create(userSetting);
	}

	public boolean exists(String userId) {
		return userSettingDao.exists(userId);
	}

	public UserSetting read(String userId) {
		UserSetting userSetting = userSettingDao.get(userId);
		if(userSetting == null) {
			userSetting = new UserSetting();
			userSetting.setUserId(userId);
		}
		
		return userSetting;
	}

	public void delete(String userId) {
		userSettingDao.remove(userId);
	}

	public void update(UserSetting userSetting) {
		userSettingDao.update(userSetting);
	}
}