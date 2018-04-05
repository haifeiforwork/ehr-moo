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
import com.lgcns.ikep4.support.activitystream.model.ActivityStream;
import com.lgcns.ikep4.support.activitystream.model.ActivityStreamSearchCondition;


/**
 * ActivityStream DAO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ActivityStreamDao.java 16271 2011-08-18 07:06:14Z giljae $
 */
public interface ActivityStreamDao extends GenericDao<ActivityStream, String> {

	/**
	 * 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<ActivityStream> listBySearchCondition(ActivityStreamSearchCondition searchCondition);

	/**
	 * TODO 리스트 검색 (Workspace)
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<ActivityStream> listBySearchConditionWorkspace(ActivityStreamSearchCondition searchCondition);

	/**
	 * 리스트 갯수
	 * 
	 * @param searchCondition
	 * @return
	 */
	Integer countBySearchCondition(ActivityStreamSearchCondition searchCondition);

}
