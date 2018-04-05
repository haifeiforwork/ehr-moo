package com.lgcns.ikep4.approval.collaboration.userauthmgnt.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.approval.collaboration.userauthmgnt.dao.UserAuthMgntDao;
import com.lgcns.ikep4.approval.collaboration.userauthmgnt.model.UserAuthMgnt;
import com.lgcns.ikep4.approval.collaboration.userauthmgnt.search.UserAuthMgntSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * 사용자 권한 관리 UserAuthMgntDao 구현체 클래스
 * 
 * @author pjh
 * @version $Id$
 */
@Repository
public class UserAuthMgntDaoImpl extends GenericDaoSqlmap<UserAuthMgnt, String> implements UserAuthMgntDao{
	
	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "com.lgcns.ikep4.approval.collaboration.userauthmgnt.dao.UserAuthMgnt.";
	
	public String create( UserAuthMgnt userAuthMgnt) {
		
		return (String) sqlInsert( NAMESPACE + "createUserAuthMgnt" , userAuthMgnt);
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public UserAuthMgnt get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(UserAuthMgnt arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 사용자 권한 관리 목록 Count
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int getUserAuthMgntCount( UserAuthMgntSearchCondition userAuthMgntSearchCondition) throws Exception {
		
		return (Integer) sqlSelectForObject( NAMESPACE + "getUserAuthMgntCount", userAuthMgntSearchCondition);
	}
	
	/**
	 * 사용자 권한 관리 목록 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<UserAuthMgnt> getUserAuthMgntList( UserAuthMgntSearchCondition userAuthMgntSearchCondition) throws Exception {
		
		return sqlSelectForList( NAMESPACE + "getUserAuthMgntList" , userAuthMgntSearchCondition);
	}
	
	/**
	 * 사용자 권한 관리 중복 확인
	 * @param userAuthMgnt
	 * @return
	 * @throws Exception
	 */
	public int checkDupUserAuthMgnt( UserAuthMgnt userAuthMgnt) throws Exception {
		
		return (Integer) sqlSelectForObject( NAMESPACE + "checkDupUserAuthMgnt" , userAuthMgnt);
	}
	
	/**
	 * 사용자 권한 관리 조회
	 * @param userAuthMgnt
	 * @return
	 * @throws Exception
	 */
	public UserAuthMgnt getUserAuthMgnt( UserAuthMgntSearchCondition userAuthMgntSearchCondition) throws Exception {
		
		return (UserAuthMgnt) sqlSelectForObject( NAMESPACE + "getUserAuthMgnt" , userAuthMgntSearchCondition);
	}
	
	/**
	 * 사용자 권한 관리 수정
	 * @param userAuthMgnt
	 * @throws Exception
	 */
	public int updateUserAuthMgnt( UserAuthMgnt userAuthMgnt) throws Exception {
		
		return sqlUpdate( NAMESPACE + "updateUserAuthMgnt", userAuthMgnt);
	}
	
	/**
	 * 사용자 권한 관리 삭제
	 * @param userAuthMgnt
	 * @return
	 * @throws Exception
	 */
	public int deleteUserAuthMgnt( UserAuthMgnt userAuthMgnt) throws Exception {
		
		return sqlUpdate( NAMESPACE + "deleteUserAuthMgnt", userAuthMgnt);
	 }
}
