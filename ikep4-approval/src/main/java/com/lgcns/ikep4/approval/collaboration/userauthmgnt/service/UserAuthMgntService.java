package com.lgcns.ikep4.approval.collaboration.userauthmgnt.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.approval.collaboration.userauthmgnt.model.UserAuthMgnt;
import com.lgcns.ikep4.approval.collaboration.userauthmgnt.search.UserAuthMgntSearchCondition;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 사용자 권한 관리 SERVICE
 * 
 * @author pjh
 * @version $Id$
 */
@Transactional
public interface UserAuthMgntService {
	
	/**
	 * 사용자 권한 관리 목록 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public SearchResult<UserAuthMgnt> getUserAuthMgntList( UserAuthMgntSearchCondition userAuthMgntSearchCondition, User user) throws Exception;
	
	/**
	 * 사용자 권한 관리 목록
	 * @param userAuthMgntSearchCondition
	 * @return
	 * @throws Exception
	 */
	public List<UserAuthMgnt> getUserAuthMgntList( UserAuthMgntSearchCondition userAuthMgntSearchCondition) throws Exception;
		
	/**
	 * 사용자 권한 관리 등록
	 * @param userAuthMgnt
	 */
	public void createUserAuthMgnt( UserAuthMgnt userAuthMgnt, User user) throws Exception;
	
	/**
	 * 사용자 권한 관리 조회
	 * @param userAuthMgnt
	 * @return
	 * @throws Exception
	 */
	public UserAuthMgnt getUserAuthMgnt( UserAuthMgntSearchCondition userAuthMgntSearchCondition, User user) throws Exception;
	
	/**
	 * 사용자 권한 관리 수정
	 * @param userAuthMgnt
	 * @throws Exception
	 */
	public int updateUserAuthMgnt( UserAuthMgnt userAuthMgnt, User user) throws Exception;
	
	/**
	 * 사용자 권한 관리 삭제
	 * @param userAuthMgnt
	 * @return
	 * @throws Exception
	 */
	public int deleteUserAuthMgnt( String delUserAuthMgntList, User user) throws Exception;
	
	/**
	 * 권한 확인
	 * @param user
	 * @param searchCondition
	 * @return
	 * @throws Exception
	 */
	public boolean checkPermission( User user, UserAuthMgntSearchCondition searchCondition) throws Exception;
	
	/**
	 * 시스템 Admin확인
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean isSystemAdmin( User user) throws Exception;
	
	/**
	 * 시스템 그룹(부서) Admin 확인
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean isSystemGroup( User user)throws Exception;
}
