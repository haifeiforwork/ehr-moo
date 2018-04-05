/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.timezone.dao;

import java.util.Date;

import com.lgcns.ikep4.framework.core.dao.GenericDao;


/**
 * TimeZoneSupportDao 인터페이스
 * 
 * @author 최현식
 * @author modified by 주길재
 * @version $Id: TimeZoneSupportDao.java 16258 2011-08-18 05:37:22Z giljae $
 */
public interface TimeZoneSupportDao extends GenericDao<Date, String> {

	/**
	 * 데이터베이스의 시스템 시간을 읽어온다.
	 * 
	 * @return
	 */
	Date getSystemDate();

}
