package com.lgcns.ikep4.socialpack.microblogging.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.socialpack.microblogging.model.Mbgroup;
import com.lgcns.ikep4.socialpack.microblogging.model.MbgroupMember;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 
 * MbgroupService 테스트 케이스
 *
 * @author 최성우
 * @version $Id: MbgroupServiceTest.java 16246 2011-08-18 04:48:28Z giljae $
 */
public class MbgroupServiceTest extends BaseServiceTestCase {

	MockHttpServletRequest request;

	MockHttpServletResponse response;

	MockHttpSession session;

	User user;
	
	@Autowired
	private MbgroupService mbgroupService;
	
	@Autowired
	private MbgroupMemberService mbgroupMemberService;
	
	private Mbgroup mbgroup;
	private MbgroupMember mbgroupMember;

	String pk = "";
	String ownerId = "";
	
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
		
		pk = "test_group_id_testunit";

		mbgroup = new Mbgroup();
		mbgroup.setMbgroupId(pk);
		mbgroup.setMbgroupName("test_group_name_testunit");
		mbgroup.setMbgroupIntroduction("for unit test");
		mbgroup.setRegisterId(user.getUserId());
		mbgroup.setRegisterName(user.getUserName());
		mbgroup.setUpdaterId(user.getUserId());
		mbgroup.setUpdaterName(user.getUserName());
		mbgroup.setIsDelete(0);

		mbgroupService.create(mbgroup);
		

		mbgroupMember = new MbgroupMember();
		mbgroupMember.setMbgroupId(pk);
		mbgroupMember.setMemberId(user.getUserId());
		mbgroupMember.setStatus("1");
		mbgroupMember.setRegisterId(user.getUserId());
		mbgroupMember.setRegisterName(user.getUserName());
		
		mbgroupMemberService.create(mbgroupMember);

		ownerId = "user206";
		mbgroupMember = new MbgroupMember();
		mbgroupMember.setMbgroupId(pk);
		mbgroupMember.setMemberId(ownerId);
		mbgroupMember.setStatus("1");
		mbgroupMember.setRegisterId(user.getUserId());
		mbgroupMember.setRegisterName(user.getUserName());
		
		mbgroupMemberService.create(mbgroupMember);
	}

	@Test
	public void create() {
		Mbgroup mbgroup = mbgroupService.read(this.pk);
		assertNotNull(mbgroup);	
	}

	@Test
	public void read() {
		Mbgroup mbgroup = mbgroupService.read(this.pk);
		assertNotNull(mbgroup);	
	}

	@Test
	public void delete() {
		mbgroupService.delete(this.pk);
		Mbgroup mbgroup = mbgroupService.read(this.pk);
		assertNotNull(mbgroup);
	}

	@Test
	public void update() {
		mbgroup.setMbgroupIntroduction("update intro");
		mbgroupService.update(mbgroup);
		Mbgroup mbgroup = mbgroupService.read(this.pk);
		assertEquals("update intro", mbgroup.getMbgroupIntroduction());	
	}

	@Test
	public void myGroupList() {
		List<Mbgroup> myGroupList = mbgroupService.myGroupList(user.getUserId());
		assertNotNull(myGroupList);
	}

	@Test
	public void bothGroup() {
		Map map = new HashMap();
		map.put("userId", user.getUserId());
		map.put("ownerId", ownerId);
		
		List<Mbgroup> bothGroup = mbgroupService.bothGroup(map);
		assertNotNull(bothGroup);
	}

}
