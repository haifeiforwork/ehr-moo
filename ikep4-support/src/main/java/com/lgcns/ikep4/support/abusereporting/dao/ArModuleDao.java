/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.abusereporting.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.abusereporting.model.ArModule;


/**
 * 
 * ArModule 관련 처리 DAO
 *
 * @author 최성우
 * @version $Id: ArModuleDao.java 16271 2011-08-18 07:06:14Z giljae $
 */
public interface ArModuleDao extends GenericDao<ArModule, String> {

	/**
	 * 모든 Module 반환
	 * 
	 * @return ArModule 리스트
	 */
	public List<ArModule> list();
	
}
