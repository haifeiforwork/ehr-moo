/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.member.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.collpack.collaboration.member.model.Member;
import com.lgcns.ikep4.collpack.collaboration.member.search.MemberSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;


/**
 * Workspace Member DAO
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: MemberDao.java 16809 2011-10-14 05:37:27Z giljae $
 */
public interface MemberDao extends GenericDao<Member, String> {

	/**
	 * workspace 별 회원목록
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<Member> listBySearchCondition(MemberSearchCondition searchCondition);
	
	public List<Member> memberListBySearchCondition(MemberSearchCondition searchCondition);

	/**
	 * workspace 별 회원수
	 * 
	 * @param searchCondition
	 * @return
	 */
	public Integer countBySearchCondition(MemberSearchCondition searchCondition);

	/**
	 * 시샵을 제외한 멤버
	 * 
	 * @param workspaceId
	 * @return
	 */
	public List<Member> listWorkspaceMember(Map<String, String> map);

	/**
	 * 회원정보 조회
	 * 
	 * @param member
	 * @return
	 */
	public Member get(Member object);

	/**
	 * 회원정보 조회
	 * 
	 * @param member
	 * @return
	 */
	public Member getForAlliance(Member object);

	/**
	 * Workspace 목록의 시샵 정보
	 * 
	 * @param workspaceIds
	 * @return
	 */
	public List<Member> getSysopList(Map<String, List<String>> map);

	/**
	 * 해당 Workspace 시샵 정보
	 * 
	 * @param workspaceId
	 * @return
	 */
	public Member getSysop(String workspaceId);

	/**
	 * 해당 Workspace 회원 목록
	 * 
	 * @param map
	 * @return
	 */
	public List<Member> getMemberList(Map<String, Object> map);

	/**
	 * WS 모든 회원(준회원이상) 메일 발송 대상 목록
	 * 
	 * @param workspaceId
	 * @return
	 */
	public List<Member> getMemberMailList(String workspaceId);

	/**
	 * WS별 권한 그룹
	 * 
	 * @param map
	 * @return
	 */
	public String getEvGroup(Map<String, String> map);

	/**
	 * 권한그룹 Root 정보
	 * 
	 * @param groupTypeId
	 * @return
	 */
	public String getRootEvGroup(String groupTypeId);

	/**
	 * 회원존재유무
	 * 
	 * @param member
	 * @return
	 */
	public boolean exists(Member object);

	/**
	 * 회원 영구 삭제
	 * 
	 * @param object
	 */
	public void physicalDelete(Member object);

	/**
	 * Workspace 의 회원 영구 삭제
	 * 
	 * @param wokspaceId
	 */
	public void physicalDeleteMemberByWorkspace(String wokspaceId);

	/**
	 * Workspace 권한 사용자 그룹 매핑 삭제
	 * 
	 * @param map
	 */
	public void deleteUserGroup(Map<String, String> map);

	/**
	 * Workspace 의 권한 그룹 정보 삭제
	 * 
	 * @param workspaceId
	 */
	public void deleteEvGroup(String workspaceId);

}
