/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.activitystream.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.activitystream.model.ActivityCode;


/**
 * ActivityStream DAO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ActivityCodeDao.java 16271 2011-08-18 07:06:14Z giljae $
 */
public interface ActivityCodeDao extends GenericDao<ActivityCode, String> {

	/**
	 * Activity Code 조회
	 * 
	 * @param userId
	 * @return
	 */
	public List<ActivityCode> select(String userId);

	/**
	 * Activity Config 등록
	 * 
	 * @param activityCode
	 * @return
	 */
	public String createConfig(ActivityCode activityCode);

	/**
	 * Activity Config 조회
	 * 
	 * @param activityCode
	 * @return
	 */
	public ActivityCode selectConfig(ActivityCode activityCode);

	/**
	 * Activity Config 삭제
	 * 
	 * @param activityCode
	 */
	public void removeConfig(ActivityCode activityCode);

	/**
	 * Activity Config 리스트 조회
	 * 
	 * @param activityCode
	 * @return
	 */
	public List<ActivityCode> selectConfigList(ActivityCode activityCode);

}
