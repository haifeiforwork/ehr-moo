/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.role.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.role.model.Role;
import com.lgcns.ikep4.support.user.role.model.RoleType;


/**
 * TODO Javadoc주석작성
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: RoleService.java 16276 2011-08-18 07:09:07Z giljae $
 */
@Transactional
public interface RoleService extends GenericService<Role, String> {

	/**
	 * 역할 코드 조회
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public SearchResult<Role> list(AdminSearchCondition searchCondition);
	
	/**
	 * 역할 정보 가져오기
	 * 
	 * @param roldId 정보를 가져올 역할 ID
	 * @return
	 */
	public Role read(String roleId);
	
	/**
	 * 역할타입 ID를 조회
	 * 
	 * @param localeCode 로케일코드
	 * @return
	 */
	public List<RoleType> selectTypeId(String localeCode);
	
	/**
	 * Role-Group 매핑 정보의 그룹목록을 불러옴
	 * 
	 * @param roleId 검색하고자 하는 역할 ID
	 * @return
	 */
	public List<Role> selectRoleGroupList(String roleId);
	
	public List<Role> selectByNameRoleGroupList(String roleName);
	
	/**
	 * Role-User 매핑 정보의 사용자 목록을 불러옴
	 * 
	 * @param roleId 검색하고자 하는 역할 ID
	 * @return
	 */
	public List<Map<String,String>> selectRoleUserList(String roleId);
	
	public List<Map<String,String>> selectByNameRoleUserList(String roleName);
	
	/**
	 * 역할 삭제
	 * 
	 * @param role 삭제할 역할 정보
	 */
	public void remove(Role role);
	
}