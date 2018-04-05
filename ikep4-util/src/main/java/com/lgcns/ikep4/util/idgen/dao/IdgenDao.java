/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.util.idgen.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;


/**
 * ID 생성 DAO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: IdgenDao.java 16247 2011-08-18 04:54:29Z giljae $
 */
public interface IdgenDao extends GenericDao<String, String> {

	/**
	 * ID값 얻어오기
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getNextId();

}
