/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.security.acl.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.user.code.model.JobClass;
import com.lgcns.ikep4.support.user.code.model.JobDuty;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.role.model.Role;


/**
 * 권한별 상세정보 인터페이스
 * 
 * @author 주길재
 * @version $Id: ACLCommonDao.java 16276 2011-08-18 07:09:07Z giljae $
 */
public interface ACLCommonDao extends GenericDao<Object, String> {

	/**
	 * 유저권한 상세정보 가져오기
	 * 
	 * @param assignUserIdList
	 * @return
	 */
	public List<User> listUserDetailPermission(List<String> assignUserIdList, String portalId);

	/**
	 * 그룹권한 상세정보 가져오기
	 * 
	 * @param assignUserIdList
	 * @return
	 */
	public List<Group> listGroupDetailPermission(List<String> groupIdPermissionList, String portalId);

	/**
	 * 역할권한 상세정보 가져오기
	 * 
	 * @param assignUserIdList
	 * @return
	 */
	public List<Role> listRoleDetailPermission(List<String> roleIdList, String portalId);

	/**
	 * 고용형태 권한 상세정보 가져오기
	 * 
	 * @param assignUserIdList
	 * @return
	 */
	public List<JobClass> listJobClassDetailPermission(List<String> jobClassCodeList, String portalId);

	/**
	 * 직책 권한 상세정보 가져오기
	 * 
	 * @param assignUserIdList
	 * @return
	 */
	public List<JobDuty> listJobDutyDetailPermission(List<String> jobDutyCodeList, String portalId);
}
