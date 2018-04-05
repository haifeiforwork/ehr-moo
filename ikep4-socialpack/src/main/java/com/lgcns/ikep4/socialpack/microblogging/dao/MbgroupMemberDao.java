/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.socialpack.microblogging.model.InvitingInfo;
import com.lgcns.ikep4.socialpack.microblogging.model.MbgroupMember;
import com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 
 * MbgroupMember 관련 처리 DAO
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: MbgroupMemberDao.java 16246 2011-08-18 04:48:28Z giljae $
 */

public interface MbgroupMemberDao extends GenericDao<MbgroupMember, MbgroupMember> {

	/**
	 * 그룹에 속한 Group Member  List 반환
	 * 
	 * @param mbgroupId와 그룹상태정보 넣은 MbgroupMember 객체
	 * @return 그룹에 속한 Group Member  List
	 */
	public List<MbgroupMember> selectMbgroupMemberList(MbgroupMember mbgroupMember);

	/**
	 * 그룹에 속한 Group Member User객체 List 반환
	 * 
	 * @param mbgroupId와 조회조건 필드
	 * @return User List
	 */
	public List<User> selectMbgroupMemberUserList(MblogSearchCondition mblogSearchCondition);

	/**
	 * 그룹 초대 정보 List 반환
	 * 
	 * @param mbgroup Id와 member Id를 담은 조회조건 필드
	 * @return 초대정보 List
	 */
	public List<InvitingInfo> selectInvitingInfoList(MblogSearchCondition mblogSearchCondition);

}
