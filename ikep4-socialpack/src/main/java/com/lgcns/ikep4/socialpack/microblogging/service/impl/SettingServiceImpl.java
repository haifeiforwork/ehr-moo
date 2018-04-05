/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.socialpack.microblogging.dao.SettingDao;
import com.lgcns.ikep4.socialpack.microblogging.model.Setting;
import com.lgcns.ikep4.socialpack.microblogging.service.SettingService;


/**
 * 
 * SettingService 구현 클래스
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: SettingServiceImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Service
public class SettingServiceImpl extends GenericServiceImpl<Setting, String> implements SettingService {

	@Autowired
	private SettingDao settingDao;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#create(java.lang
	 * .Object)
	 */
	public String create(Setting object) {
		return settingDao.create(object);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#exists(java.io.
	 * Serializable)
	 */
	public boolean exists(String id) {
		return settingDao.exists(id);
	}

	/*
	 * (non-Javadoc)
	 * @seecom.lgcns.ikep4.framework.core.service.GenericService#get(java.io.
	 * Serializable)
	 */
	public Setting read(String id) {
		Setting setting = settingDao.get(id);
		
		return setting;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#remove(java.io.
	 * Serializable)
	 */
	public void delete(String id) {
		settingDao.remove(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#update(java.lang.Object)
	 */
	public void update(Setting object) {
		settingDao.update(object);
	}
}
