package com.lgcns.ikep4.socialpack.microblogging.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

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

import com.lgcns.ikep4.socialpack.microblogging.model.Follow;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 
 * FollowService 테스트 케이스
 *
 * @author 최성우
 * @version $Id: FollowServiceTest.java 16246 2011-08-18 04:48:28Z giljae $
 */
public class FollowServiceTest extends BaseServiceTestCase {

	MockHttpServletRequest request;

	MockHttpServletResponse response;

	MockHttpSession session;

	User user;
	
	@Autowired
	private FollowService followService;
	
	private Follow follow;
	
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
		
		ownerId = "user206";
		
		String otherId = "user207";
		
		follow = new Follow();

		follow.setUserId(user.getUserId());
		follow.setFollowingUserId(otherId);

		if(!followService.exists(follow)){
			followService.create(follow);
		}

		follow.setUserId(otherId);
		follow.setFollowingUserId(user.getUserId());

		if(!followService.exists(follow)){
			followService.create(follow);
		}

		follow.setUserId(ownerId);
		follow.setFollowingUserId(otherId);

		if(!followService.exists(follow)){
			followService.create(follow);
		}

		follow.setUserId(otherId);
		follow.setFollowingUserId(ownerId);

		if(!followService.exists(follow)){
			followService.create(follow);
		}
	}

	@Test
	public void create() {
		boolean exist = followService.exists(this.follow);
		assertTrue(exist);	
	}

	@Test
	public void exists() {
		boolean exist = followService.exists(this.follow);
		assertTrue(exist);			
	}

	@Test
	public void getFollower() {
		List<Follow> list = this.followService.getFollower(this.follow);
		assertNotNull(list);	
	}

	@Test
	public void getFollowing() {
		List<Follow> list = this.followService.getFollowing(this.follow);
		assertNotNull(list);	
	}

	@Test
	public void getBothFollowing() {
		Map map = new HashMap();
		map.put("ownerId", ownerId);
		map.put("userId", user.getUserId());
		
		List<Follow> list = this.followService.getBothFollowing(map);
		assertNotNull(list);	
	}

	@Test
	public void getBothFollower() {
		Map map = new HashMap();
		map.put("ownerId", ownerId);
		map.put("userId", user.getUserId());
		
		List<Follow> list = this.followService.getBothFollower(map);
		assertNotNull(list);	
	}

	@Test
	public void myFollowingCount() {
		int count = this.followService.myFollowingCount(user.getUserId());
		assertTrue(0 < count);	
	}

	@Test
	public void myFollowerCount() {
		int count = this.followService.myFollowerCount(user.getUserId());
		assertTrue(0 < count);	
	}

	@Test
	public void delete() {
		followService.delete(this.follow);
		
		boolean exist = followService.exists(this.follow);
		assertFalse(exist);	
	}

}
