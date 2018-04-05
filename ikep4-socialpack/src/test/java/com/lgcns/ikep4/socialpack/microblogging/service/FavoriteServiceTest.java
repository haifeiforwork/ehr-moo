package com.lgcns.ikep4.socialpack.microblogging.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.socialpack.microblogging.model.Favorite;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 
 * FavoriteService 테스트 케이스
 *
 * @author 최성우
 * @version $Id: FavoriteServiceTest.java 16246 2011-08-18 04:48:28Z giljae $
 */
public class FavoriteServiceTest extends BaseServiceTestCase {

	MockHttpServletRequest request;

	MockHttpServletResponse response;

	MockHttpSession session;

	User user;
	
	@Autowired
	private FavoriteService favoriteService;
	
	private Favorite favorite;

	@Before
	public void setUp() {

		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();

		user = new User();
		user.setUserId("user100");
		user.setUserName("사용자100");
		
		request.setSession(session);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		favorite = new Favorite();

		favorite.setUserId(user.getUserId());
		favorite.setMblogId("100000772080");

		Favorite returnFavorite = favoriteService.read(this.favorite);
		if(null == returnFavorite){
			favoriteService.create(favorite);
		}
	}

	@Test
	public void create() {
		Favorite favorite = favoriteService.read(this.favorite);
		assertNotNull(favorite);	
	}

	@Test
	public void read() {
		Favorite favorite = favoriteService.read(this.favorite);
		assertNotNull(favorite);	
		
	}

	@Test
	public void countByUserId() {
		int count= this.favoriteService.countByUserId(user.getUserId());
		assertTrue(0 < count);
	}
	
	@Test
	public void delete() {
		favoriteService.delete(this.favorite);
		Favorite favorite = this.favoriteService.read(this.favorite);
		assertNull(favorite);
	}

}
