/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.member.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.collaboration.member.model.Member;
import com.lgcns.ikep4.collpack.collaboration.member.search.MemberSearchCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * Workspace Member Service
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: MemberService.java 16809 2011-10-14 05:37:27Z giljae $
 */
@Transactional
public interface MemberService extends GenericService<Member, String> {

	/**
	 * workspace 별 회원목록
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<Member> listBySearchCondition(MemberSearchCondition searchCondition);
	
	public SearchResult<Member> memberListBySearchCondition(MemberSearchCondition searchCondition);

	/**
	 * 시샵을 제외한 멤버
	 * 
	 * @param map
	 * @return
	 */
	public List<Member> listWorkspaceMember(Map<String, String> map);

	/**
	 * 선택된 workspace 의 시샵 목록
	 * 
	 * @param map
	 * @return
	 */
	public List<Member> getSysopList(Map<String, List<String>> map);

	/**
	 * 해당 Workspace 회원 목록
	 * 
	 * @param map
	 * @return
	 */
	public List<Member> getMemberList(Map<String, Object> map);

	/**
	 * 해당 Workspace의 시샵 정보 조회
	 * 
	 * @param workspaceId
	 * @return
	 */
	public Member getSysop(String workspaceId);

	/**
	 * WS 모든 회원(준회원이상) 메일 발송 대상 목록
	 * 
	 * @param workspaceId
	 * @return
	 */
	public List<Member> getMemberMailList(String workspaceId);

	/**
	 * 회원정보 조회
	 * 
	 * @param object
	 * @return
	 */
	public Member read(Member object);
	
	/**
	 * 회원정보 조회(동맹)
	 * 
	 * @param object
	 * @return
	 */
	public Member readForAlliance(Member object);

	/**
	 * 신규 회원 추가 (관리자) - 정,준회원
	 * 
	 * @param searchCondition
	 * @param user
	 * @param memberIds
	 * @param associateIds
	 */
	public void addNewMember(MemberSearchCondition searchCondition, User user, List<String> memberIds,
			List<String> associateIds);

	/**
	 * 회원존재유무
	 * 
	 * @param object
	 * @return
	 */
	public boolean exists(Member object);

	/**
	 * 해당 사용자의 디폴트 Workspace 변경 처리
	 * 
	 * @param portalId
	 * @param user
	 * @param workspaceId
	 */
	public void updateWorkspaceDefaultSet(String portalId, User user, String workspaceId);

	/**
	 * 회원 등급 수정
	 * 
	 * @param searchCondition
	 * @param memberIds
	 * @param user
	 */
	public void updateMemberLevel(MemberSearchCondition searchCondition, List<String> memberIds, User user);

	/**
	 * 시샵 변경
	 * 
	 * @param searchCondition
	 * @param memberIds
	 * @param user
	 * @return
	 */
	public Member updateSysop(MemberSearchCondition searchCondition, List<String> memberIds, User user);

	/**
	 * 회원 영구 삭제
	 * 
	 * @param object
	 */
	public void physicalDelete(Member object);

	/**
	 * 멤버의 Workspace 가입 정보 삭제
	 * 
	 * @param workspaceIds
	 * @param userId
	 */
	public void physicalDelete(List<String> workspaceIds, User user);

	/**
	 * Workspace의 선택한 멤버 삭제
	 * 
	 * @param workspaceId
	 * @param memberIds
	 */
	public void physicalDelete(String workspaceId, List<String> memberIds);

	/**
	 * 해당 Workspace의 모든 회원 삭제
	 * 
	 * @param wokspaceId
	 */
	public void physicalDeleteMemberByWorkspace(String wokspaceId);

	public void sendMail(Member member, User user, String memberLevel);
}
