/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.socialpack.microblogging.model.InvitingInfo;
import com.lgcns.ikep4.socialpack.microblogging.model.MbgroupMember;
import com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 마이크로블로깅 그룹 회원 관련 서비스
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: MbgroupMemberService.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Transactional
public interface MbgroupMemberService extends GenericService<MbgroupMember, MbgroupMember> {

	/**
	 * 그룹에 속한 Group Member  List 반환
	 * 
	 * @param mbgroupId와 그룹상태정보 넣은 MbgroupMember 객체
	 * @return 그룹에 속한 Group Member  List
	 */
	public List<MbgroupMember> 	mbgroupMemberList		(MbgroupMember mbgroupMember);

	/**
	 * 그룹에 속한 Group Member User객체 List 반환
	 * 
	 * @param mbgroupId와 조회조건 필드
	 * @return User List
	 */
	public List<User> 			mbgroupMemberUserList	(MblogSearchCondition mblogSearchCondition);

	/**
	 * 그룹 초대 정보 List 반환
	 * 
	 * @param mbgroup Id와 member Id를 담은 조회조건 필드
	 * @return 초대정보 List
	 */
	public List<InvitingInfo> 	invitingInfoList		(MblogSearchCondition mblogSearchCondition);

	/**
	 * 그룹 마지막 멤버여부 반환
	 * 
	 * @param mbgroup Id와 member Id를 담은 조회조건 필드
	 * @return 그룹 마지막 멤버여부
	 */
	public String			 	checkLastGroupMemberYN	(MbgroupMember mbgroupMember);
}
