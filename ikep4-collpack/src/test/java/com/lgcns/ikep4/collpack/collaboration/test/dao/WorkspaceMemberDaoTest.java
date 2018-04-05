/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.collaboration.member.dao.MemberDao;
import com.lgcns.ikep4.collpack.collaboration.member.model.Member;
import com.lgcns.ikep4.collpack.collaboration.member.search.MemberSearchCondition;



/**
 * BoardDao 테스트 케이스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: WorkspaceMemberDaoTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class WorkspaceMemberDaoTest extends BaseDaoTestCase {

	@Autowired
	private MemberDao memberDao;

	//@Autowired
	//private IdgenService idgenService;
	
	private Member member;
	private Member memberSet;
	private String pk;
	//private String pk1;

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
		
		pk = memberDao.create(member);
		
		//this.pk=member.getWorkspaceId();
		//this.pk1=member.getMemberId();
		this.memberSet = member;
	}

	@Test
	public void testCreate() {
		Member result = memberDao.get(memberSet);
		assertNotNull(result);
	}
	
	@Test
	public void testGet() {
		Member result = memberDao.get(memberSet);
		assertNotNull(result);
	}
	
	@Test
	public void testExist() {
		boolean result = memberDao.exists(memberSet);
		assertTrue(result);
	}
	
	@Test
	public void testUpdate() {
		this.member = memberDao.get(memberSet);
		this.member.setMemberIntroduction("modified Member Info");
		this.member.setUpdateType("modify");
		memberDao.update(this.member);
		
		Member result = memberDao.get(memberSet);
		
		assertEquals(this.member.getMemberIntroduction(), result.getMemberIntroduction());
	}
	
	@Test
	public void testUpdateMemberJoin() {
		this.member = memberDao.get(memberSet);
		this.member.setMemberLevel("4");
		this.member.setUpdateType("join");
		memberDao.update(this.member);
		
		Member result = memberDao.get(memberSet);
		
		assertEquals(this.member.getMemberLevel(), result.getMemberLevel());
	}
	
	@Test
	public void testUpdateMemberLevel() {
		this.member = memberDao.get(memberSet);
		this.member.setMemberLevel("3");
		this.member.setUpdateType("level");
		memberDao.update(this.member);
		
		Member result = memberDao.get(memberSet);
		
		assertEquals(this.member.getMemberLevel(), result.getMemberLevel());
	}	
		
	@Test
	public void testPhysicalDelete() {
		
		this.member = memberDao.get(memberSet);
		
		memberDao.physicalDelete(this.member);
		
		Member result = memberDao.get(memberSet);
		assertNull(result);
	}



	@Test
	public void testListBySearchCondition() {
		MemberSearchCondition searchCondition = new MemberSearchCondition();
		
		searchCondition.setPortalId(portalId);
		searchCondition.setWorkspaceId(workspaceId);
		searchCondition.setSearchColumn("MEMBER_ID");
		
		
		List<Member> result = memberDao.listBySearchCondition(searchCondition);
		assertNotNull(result);
	}
	@Test
	public void testCountBySearchCondition() {
		
		MemberSearchCondition searchCondition = new MemberSearchCondition();
		
		searchCondition.setPortalId(portalId);
		searchCondition.setWorkspaceId(workspaceId);
		//searchCondition.setSearchColumn("MEMBER_ID");
		
		
		int count = memberDao.countBySearchCondition(searchCondition);
		assertNotNull(count);
	}
	
}
