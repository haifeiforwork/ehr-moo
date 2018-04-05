/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.member.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.cafe.member.dao.MemberDao;
import com.lgcns.ikep4.lightpack.cafe.member.model.Member;
import com.lgcns.ikep4.lightpack.cafe.member.search.MemberSearchCondition;


/**
 * Cafe Member Dao 구현 클래스
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: MemberDaoImpl.java 16297 2011-08-19 07:52:43Z giljae $
 */
@Repository("cfMemberDao")
public class MemberDaoImpl extends GenericDaoSqlmap<Member, String> implements MemberDao {

	private static final String NAMESPACE = "lightpack.cafe.member.dao.Member.";

	public List<Member> listBySearchCondition(MemberSearchCondition searchCondition) {
		return sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);
	}

	public Integer countBySearchCondition(MemberSearchCondition searchCondition) {
		return (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
	}

	public List<Member> listCafeMember(Map<String, String> map) {
		return sqlSelectForList(NAMESPACE + "listCafeMember", map);
	}

	public String create(Member object) {
		return (String) sqlInsert(NAMESPACE + "create", object);
	}

	@Deprecated
	public Member get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Member get(Member object) {
		return (Member) sqlSelectForObject(NAMESPACE + "get", object);
	}

	public List<Member> getSysopList(Map<String, List<String>> map) {
		return sqlSelectForList(NAMESPACE + "getSysopList", map);
	}

	public Member getSysop(String cafeId) {
		return (Member) sqlSelectForObject(NAMESPACE + "getSysop", cafeId);
	}

	public List<Member> getMemberList(Map<String, Object> map) {
		return sqlSelectForList(NAMESPACE + "getMemberList", map);
	}

	public List<Member> getMemberMailList(String cafeId) {
		return sqlSelectForList(NAMESPACE + "getMemberMailList", cafeId);
	}

	@Deprecated
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean exists(Member object) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "exists", object);
		return count > 0;
	}

	public void update(Member object) {
		sqlUpdate(NAMESPACE + "update", object);
	}

	@Deprecated
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	public void physicalDelete(Member object) {
		sqlDelete(NAMESPACE + "physicalDelete", object);
	}

	public void physicalDeleteMemberByCafe(String cafeId) {
		sqlDelete(NAMESPACE + "physicalDeleteMemberByCafe", cafeId);
	}

}
