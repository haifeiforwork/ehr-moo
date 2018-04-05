/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.userauthmgnt.dao;

import java.util.List;

import com.lgcns.ikep4.approval.collaboration.userauthmgnt.model.UserAuthMgnt;
import com.lgcns.ikep4.approval.collaboration.userauthmgnt.search.UserAuthMgntSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * 사용자 권한 관리 DAO
 * 
 * @author pjh
 * @version $Id$
 */
public interface UserAuthMgntDao extends GenericDao<UserAuthMgnt, String> {
	
	/**
	 * 사용자 권한 관리 목록 Count
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int getUserAuthMgntCount( UserAuthMgntSearchCondition userAuthMgntSearchCondition) throws Exception;
	
	/**
	 * 사용자 권한 관리 목록 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<UserAuthMgnt> getUserAuthMgntList( UserAuthMgntSearchCondition userAuthMgntSearchCondition) throws Exception;
	
	
	/**
	 * 사용자 권한 관리 중복 조회
	 * @param userAuthMgnt
	 * @return
	 * @throws Exception
	 */
	public int checkDupUserAuthMgnt( UserAuthMgnt userAuthMgnt) throws Exception;
	
	/**
	 * 사용자 권한 관리 조회
	 * @param userAuthMgnt
	 * @return
	 * @throws Exception
	 */
	public UserAuthMgnt getUserAuthMgnt( UserAuthMgntSearchCondition userAuthMgntSearchCondition) throws Exception;
	
	/**
	 * 사용자 권한 관리 수정
	 * @param userAuthMgnt
	 * @throws Exception
	 */
	public int updateUserAuthMgnt( UserAuthMgnt userAuthMgnt) throws Exception;
	
	/**
	 * 사용자 권한 관리 삭제
	 * @param userAuthMgnt
	 * @return
	 * @throws Exception
	 */
	public int deleteUserAuthMgnt( UserAuthMgnt userAuthMgnt) throws Exception;
}
