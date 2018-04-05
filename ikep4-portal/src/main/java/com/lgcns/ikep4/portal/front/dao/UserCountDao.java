/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.front.dao;

/**
 * Login User details Dao
 *
 * @author 조경식
 * @version $Id: LoginUserDetailsDao.java 16243 2011-08-18 04:10:43Z giljae $
 */
public interface UserCountDao {
	/**
	 * 실제 사용자명수를 조회한다.
	 *
	 * @param none
	 * @return 사용자명수
	 */
	public String userCount();
}
