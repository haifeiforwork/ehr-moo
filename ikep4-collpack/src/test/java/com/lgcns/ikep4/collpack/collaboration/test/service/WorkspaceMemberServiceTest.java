/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.test.service;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;


import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.collaboration.member.model.Member;
import com.lgcns.ikep4.collpack.collaboration.member.search.MemberSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.member.service.MemberService;
import com.lgcns.ikep4.framework.web.SearchResult;



/**
 * BoardService 테스트 케이스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: WorkspaceMemberServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class WorkspaceMemberServiceTest extends BaseServiceTestCase {

	@Autowired
	private MemberService memberService;

	private Member member;
	private Member memberSet;
	private String pk;

	
	@Before
	public void setUp() {
		member = new Member();
		
		Date today = new Date();

		member.setWorkspaceId(workspaceId);
		member.setMemberId(registerId);
		member.setMemberIntroduction("test Memberfor DAO");
		member.setMemberLevel("5");
		member.setJoinApplyDate(today);

		member.setRegisterId(registerId);
		member.setRegisterName(registerName);

		member.setUpdaterId(updaterId);
		member.setUpdaterName(updaterName);

		member.setJoinType("0");
		
		
		pk = memberService.create(member);
		

		this.memberSet = member;
	}

	@Test
	public void testCreate() {
		Member result = memberService.read(memberSet);
		assertNotNull(result);
	}
	@Test
	public void testRead() {
		Member result = memberService.read(memberSet);
		assertNotNull(result);
	}
	@Test
	public void testUpdate() {
		this.member = memberService.read(memberSet);
		this.member.setMemberIntroduction("modified content");
		this.member.setUpdateType("modify");
		memberService.update(this.member);
		Member result = memberService.read(memberSet);
		
		assertEquals(this.member.getMemberIntroduction(), result.getMemberIntroduction());

	}
	@Test
	public void testUpdateMemberJoin() {
		this.member = memberService.read(memberSet);
		this.member.setMemberLevel("4");
		this.member.setUpdateType("join");
		memberService.update(this.member);
		
		Member result = memberService.read(memberSet);
		
		assertEquals(this.member.getMemberLevel(), result.getMemberLevel());
	}
	
	@Test
	public void testUpdateMemberLevel() {
		this.member = memberService.read(memberSet);
		this.member.setMemberLevel("3");
		this.member.setUpdateType("level");
		memberService.update(this.member);
		
		Member result = memberService.read(memberSet);
		
		assertEquals(this.member.getMemberLevel(), result.getMemberLevel());
	}
	@Test
	public void testPhysicalDelete() {
		this.member = memberService.read(memberSet);
		this.memberService.physicalDelete(this.member);
		
		Member result = this.memberService.read(memberSet);
		assertNull(result);
	}

	@Test
	public void testExist() {
		boolean result = memberService.exists(memberSet);
		assertTrue(result);
	}

	@Test
	public void testListBySearchCondition() {
		MemberSearchCondition searchCondition = new MemberSearchCondition();
		
		
		searchCondition.setPortalId(portalId);
		searchCondition.setWorkspaceId(workspaceId);
		searchCondition.setSearchColumn("MEMBER_ID");
		
		SearchResult<Member> result = memberService.listBySearchCondition(searchCondition);
		assertNotNull(result);
	}
}
