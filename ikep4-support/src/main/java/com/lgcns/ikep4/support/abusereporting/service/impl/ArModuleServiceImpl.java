/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.abusereporting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.abusereporting.dao.ArModuleDao;
import com.lgcns.ikep4.support.abusereporting.model.ArModule;
import com.lgcns.ikep4.support.abusereporting.service.ArModuleService;


/**
 * 
 * ArModuleService 구현 클래스
 *
 * @author 최성우
 * @version $Id: ArModuleServiceImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Service
public class ArModuleServiceImpl extends GenericServiceImpl<ArModule, String> implements ArModuleService {

	@Autowired
	private ArModuleDao arModuleDao;

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create(java.lang.Object)
	 */
	public String create(ArModule arModule) {
		
		arModuleDao.create(arModule);
		
		return "ok";
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#read(java.io.Serializable)
	 */
	public ArModule read(String id) {
		ArModule arModule = arModuleDao.get(id);
		
		return arModule;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#delete(java.io.Serializable)
	 */
	public void delete(String id) {
		arModuleDao.remove(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.abusereporting.service.ArModuleService#list()
	 */
	public List<ArModule> list() {
		return arModuleDao.list();
	}

}
