/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.member.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.collaboration.member.dao.MemberDao;
import com.lgcns.ikep4.collpack.collaboration.member.model.Member;
import com.lgcns.ikep4.collpack.collaboration.member.search.MemberSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * Workspace Member Dao 구현 클래스
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: MemberDaoImpl.java 16809 2011-10-14 05:37:27Z giljae $
 */
@Repository("memberDao")
public class MemberDaoImpl extends GenericDaoSqlmap<Member, String> implements MemberDao {

	private static final String NAMESPACE = "collpack.collaboration.member.dao.Member.";

	/**
	 * 회원 목록 조회
	 */
	public List<Member> listBySearchCondition(MemberSearchCondition searchCondition) {
		return sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);
	}
	
	public List<Member> memberListBySearchCondition(MemberSearchCondition searchCondition) {
		return sqlSelectForList(NAMESPACE + "memberListBySearchCondition", searchCondition);
	}

	/**
	 * 회원 수 조회
	 */
	public Integer countBySearchCondition(MemberSearchCondition searchCondition) {
		return (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
	}

	/**
	 * 나의 신청 내역
	 */
	public List<Member> listWorkspaceMember(Map<String, String> map) {
		return sqlSelectForList(NAMESPACE + "listWorkspaceMember", map);
	}

	/**
	 * 회원 신청
	 */
	public String create(Member object) {
		return (String) sqlInsert(NAMESPACE + "create", object);
	}

	@Deprecated
	public Member get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 회원정보 조회
	 */
	public Member get(Member object) {
		return (Member) sqlSelectForObject(NAMESPACE + "get", object);
	}

	/**
	 * 회원정보 조회(동맹)
	 */
	public Member getForAlliance(Member object) {
		return (Member) sqlSelectForObject(NAMESPACE + "getForAlliance", object);
	}

	/**
	 * 권한 그룹 정보
	 */
	public String getEvGroup(Map<String, String> map) {
		return (String) sqlSelectForObject(NAMESPACE + "getEvGroup", map);
	}

	/**
	 * 시샵 목록
	 */
	public List<Member> getSysopList(Map<String, List<String>> map) {
		return sqlSelectForList(NAMESPACE + "getSysopList", map);
	}

	/**
	 * 해당 워크스페이스의 시샵 정보조회
	 */
	public Member getSysop(String workspaceId) {
		return (Member) sqlSelectForObject(NAMESPACE + "getSysop", workspaceId);
	}

	/**
	 * 멤버 목록
	 */
	public List<Member> getMemberList(Map<String, Object> map) {
		return sqlSelectForList(NAMESPACE + "getMemberList", map);
	}

	/**
	 * 메일 대상 목록
	 */
	public List<Member> getMemberMailList(String workspaceId) {
		return sqlSelectForList(NAMESPACE + "getMemberMailList", workspaceId);
	}

	/**
	 * 권한 그룹 Root 정보
	 */
	public String getRootEvGroup(String groupTypeId) {
		return (String) sqlSelectForObject(NAMESPACE + "getRootEvGroup", groupTypeId);
	}

	@Deprecated
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 회원 존재유무 확인
	 */
	public boolean exists(Member object) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "exists", object);
		return count > 0;
	}

	/**
	 * 회원정보 수정
	 */
	public void update(Member object) {
		sqlUpdate(NAMESPACE + "update", object);
	}

	@Deprecated
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	/**
	 * 회원 정보 삭제
	 */
	public void physicalDelete(Member object) {
		sqlDelete(NAMESPACE + "physicalDelete", object);
	}

	/**
	 * 해당 WS의 회원 정보 전체 삭제
	 */
	public void physicalDeleteMemberByWorkspace(String workspaceId) {
		sqlDelete(NAMESPACE + "physicalDeleteMemberByWorkspace", workspaceId);
	}

	/**
	 * 회원의 권한 그룹 매핑정보 삭제
	 */
	public void deleteUserGroup(Map<String, String> map) {
		sqlDelete(NAMESPACE + "deleteUserGroup", map);
	}

	/**
	 * 권한 그룹 삭제
	 */
	public void deleteEvGroup(String workspaceId) {
		sqlDelete(NAMESPACE + "deleteEvGroup", workspaceId);
	}
}
