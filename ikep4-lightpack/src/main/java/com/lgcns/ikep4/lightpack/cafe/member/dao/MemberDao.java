/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.member.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.cafe.member.model.Member;
import com.lgcns.ikep4.lightpack.cafe.member.search.MemberSearchCondition;


/**
 * Cafe Member DAO
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: MemberDao.java 16240 2011-08-18 04:08:15Z giljae $
 */
public interface MemberDao extends GenericDao<Member, String> {

	/**
	 * cafe 별 회원목록
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<Member> listBySearchCondition(MemberSearchCondition searchCondition);

	/**
	 * cafe 별 회원수
	 * 
	 * @param searchCondition
	 * @return
	 */
	public Integer countBySearchCondition(MemberSearchCondition searchCondition);

	/**
	 * 시샵을 제외한 멤버
	 * 
	 * @param cafeId
	 * @return
	 */
	public List<Member> listCafeMember(Map<String, String> map);

	/**
	 * 회원정보 조회
	 * 
	 * @param member
	 * @return
	 */
	public Member get(Member object);

	/**
	 * Cafe 목록의 시샵 정보
	 * 
	 * @param cafeIds
	 * @return
	 */
	public List<Member> getSysopList(Map<String, List<String>> map);

	/**
	 * 해당 Cafe 시샵 정보
	 * 
	 * @param cafeId
	 * @return
	 */
	public Member getSysop(String cafeId);

	/**
	 * 해당 Cafe 회원 목록
	 * 
	 * @param map
	 * @return
	 */
	public List<Member> getMemberList(Map<String, Object> map);

	/**
	 * WS 모든 회원(준회원이상) 메일 발송 대상 목록
	 * 
	 * @param cafeId
	 * @return
	 */
	public List<Member> getMemberMailList(String cafeId);

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
	 * Cafe 의 회원 영구 삭제
	 * 
	 * @param wokspaceId
	 */
	public void physicalDeleteMemberByCafe(String cafeId);

}
