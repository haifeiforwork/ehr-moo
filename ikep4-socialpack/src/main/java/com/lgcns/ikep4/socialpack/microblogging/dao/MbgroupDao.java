/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.socialpack.microblogging.model.Mbgroup;


/**
 * 
 * Mbgroup 관련 처리 DAO
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: MbgroupDao.java 16246 2011-08-18 04:48:28Z giljae $
 */

public interface MbgroupDao extends GenericDao<Mbgroup, String> {

	/**
	 * 사용자가 속한 그룹 List 반환
	 * 
	 * @param memberId
	 * @return Mbgroup객체 List
	 */
	public List<Mbgroup> selectMyGroupList(String id);

	/**
	 * 블로그 주인과 사용자가 함께 속한 그룹 List 반환
	 * 
	 * @param userId, ownerId를 넣은 Map
	 * @return Mbgroup객체 List
	 */
	public List<Mbgroup> selectBothGroup(Map map);

}
