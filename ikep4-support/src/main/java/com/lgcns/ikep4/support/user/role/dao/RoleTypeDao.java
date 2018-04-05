/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.role.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.role.model.RoleType;

/**
 * 역할타입 DAO
 *
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: RoleTypeDao.java 19023 2012-05-31 06:36:51Z malboru80 $
 */
public interface RoleTypeDao extends GenericDao<RoleType, String> {
	
	/**
	 * 역할타입 코드 조회
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public List<RoleType> selectAll(AdminSearchCondition searchCondition);
	
	/**
	 * 역할타입 코드 조회 카운트
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	Integer selectCount(AdminSearchCondition searchCondition);
	
	/**
	 * 포탈 생성시 역할타입 등록 
	 * 
	 * @param portalId 포탈 ID
	 * @return
	 */
	public List<RoleType> listRoleTypePortal(String portalId);
}
