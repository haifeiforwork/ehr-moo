package com.lgcns.ikep4.socialpack.microblogging.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.socialpack.microblogging.model.InvitingInfo;
import com.lgcns.ikep4.socialpack.microblogging.model.Mbgroup;
import com.lgcns.ikep4.socialpack.microblogging.model.MbgroupMember;
import com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 
 * MbgroupMemberService 테스트 케이스
 *
 * @author 최성우
 * @version $Id: MbgroupMemberServiceTest.java 16246 2011-08-18 04:48:28Z giljae $
 */
public class MbgroupMemberServiceTest extends BaseServiceTestCase {

	MockHttpServletRequest request;

	MockHttpServletResponse response;

	MockHttpSession session;

	User user;

	@Autowired
	private MbgroupService mbgroupService;
	
	@Autowired
	private MbgroupMemberService mbgroupMemberService;
	
	private MbgroupMember mbgroupMember;
	
	private MblogSearchCondition mblogSearchCondition;

	@Before
	public void setUp() {

		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();

		user = new User();
		user.setUserId("user100");
		user.setUserName("사용자100");
		session.setAttribute("ikep.user", user);
		
		request.setSession(session);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		String mbgroupId = "test_group_id";
		
		Mbgroup mbgroup = new Mbgroup();
		mbgroup.setMbgroupId(mbgroupId);
		mbgroup.setMbgroupName("test_group_name_testunit");
		mbgroup.setMbgroupIntroduction("for unit test");
		mbgroup.setRegisterId(user.getUserId());
		mbgroup.setRegisterName(user.getUserName());
		mbgroup.setUpdaterId(user.getUserId());
		mbgroup.setUpdaterName(user.getUserName());
		mbgroup.setIsDelete(0);

		mbgroupService.create(mbgroup);
		
		mbgroupMember = new MbgroupMember();
		
		mbgroupMember.setMbgroupId(mbgroupId);
		mbgroupMember.setMemberId("user206");
		mbgroupMember.setStatus("1");
		mbgroupMember.setRegisterId(user.getUserId());
		mbgroupMember.setRegisterName(user.getUserName());

		if(!mbgroupMemberService.equals(mbgroupMember)){
			mbgroupMemberService.create(mbgroupMember);
		}

		mblogSearchCondition = new MblogSearchCondition();
		
		mblogSearchCondition.setMbgroupId(mbgroupId);
		
	}
	
	@Test
	public void create() {
		MbgroupMember mbgroupMember = mbgroupMemberService.read(this.mbgroupMember);
		assertNotNull(mbgroupMember);	
	}

	@Test
	public void update() {
		mbgroupMember.setStatus("0");
		mbgroupMemberService.update(this.mbgroupMember);
		
		MbgroupMember retrunMbgroupMember = mbgroupMemberService.read(this.mbgroupMember);
		assertEquals("0", retrunMbgroupMember.getStatus());	
	}

	@Test
	public void read() {
		MbgroupMember mbgroupMember = mbgroupMemberService.read(this.mbgroupMember);
		assertNotNull(mbgroupMember);	
	}

	@Test
	public void exists() {
		mbgroupMember.setStatus("1");
		boolean exist =  mbgroupMemberService.exists(this.mbgroupMember);
		assertTrue(exist);	
	}

	@Test
	public void mbgroupMemberList() {
		List<MbgroupMember> mbgroupMemberList =  mbgroupMemberService.mbgroupMemberList(this.mbgroupMember);
		assertNotNull(mbgroupMemberList);	
	}

	@Test
	public void mbgroupMemberUserList() {
		
		List<User> mbgroupMemberUserList =  mbgroupMemberService.mbgroupMemberUserList(this.mblogSearchCondition);
		assertNotNull(mbgroupMemberUserList);	
	}

	@Test
	public void invitingInfoList() {
		
		List<InvitingInfo> invitingInfoList =  mbgroupMemberService.invitingInfoList(this.mblogSearchCondition);
		assertNotNull(invitingInfoList);	
	}

	
	@Test
	public void delete() {
		mbgroupMemberService.delete(this.mbgroupMember);
		MbgroupMember mbgroupMember = this.mbgroupMemberService.read(this.mbgroupMember);
		assertNull(mbgroupMember);
	}

	@Test
	public void checkLastGroupMemberYN() {
		mbgroupMemberService.checkLastGroupMemberYN(this.mbgroupMember);
	}
}
