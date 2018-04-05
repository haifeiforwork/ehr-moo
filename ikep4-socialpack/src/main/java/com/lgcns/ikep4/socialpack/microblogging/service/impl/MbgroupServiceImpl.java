/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.socialpack.microblogging.dao.MbgroupDao;
import com.lgcns.ikep4.socialpack.microblogging.model.Mbgroup;
import com.lgcns.ikep4.socialpack.microblogging.service.MbgroupService;


/**
 * 
 * MbgroupService 구현 클래스
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: MbgroupServiceImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Service
public class MbgroupServiceImpl extends GenericServiceImpl<Mbgroup, String> implements MbgroupService {

	@Autowired
	private MbgroupDao mbgroupDao;

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create(java.lang.Object)
	 */
	public String create(Mbgroup object) {
		return mbgroupDao.create(object);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {
		return mbgroupDao.exists(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#read(java.io.Serializable)
	 */
	public Mbgroup read(String id) {
		Mbgroup mbgroup = mbgroupDao.get(id);
		
		return mbgroup;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#delete(java.io.Serializable)
	 */
	public void delete(String id) {
		mbgroupDao.remove(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#update(java.lang.Object)
	 */
	public void update(Mbgroup object) {
		mbgroupDao.update(object);
	}
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MbgroupService#myGroupList(java.lang.String)
	 */
	public List<Mbgroup> myGroupList(String id) {
		return mbgroupDao.selectMyGroupList(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MbgroupService#bothGroup(java.util.Map)
	 */
	public List<Mbgroup> bothGroup(Map map) {
		return mbgroupDao.selectBothGroup(map);
	}
}
