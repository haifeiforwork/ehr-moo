/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.timezone.dao.impl;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.timezone.dao.TimeZoneSupportDao;


/**
 * TimeZoneSupportDao 구현체
 * 
 * @author 최현식
 * @author modified by 주길재
 * @version $Id: TimeZoneSupportDaoImpl.java 6293 2011-04-14 08:35:32Z
 *          designtker $
 */
@Repository
public class TimeZoneSupportDaoImpl extends GenericDaoSqlmap<Date, String> implements TimeZoneSupportDao {

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public Date get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	@Deprecated
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	@Deprecated
	public String create(Date object) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	@Deprecated
	public void update(Date object) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	@Deprecated
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	/**
	 * 데이터 베이스의 시스템 시간을 읽어온다.
	 */
	public Date getSystemDate() {
		return (Date) this.sqlSelectForObject("support.timezone.dao.TimeZoneSupport.getSystemDate");
	}

}
