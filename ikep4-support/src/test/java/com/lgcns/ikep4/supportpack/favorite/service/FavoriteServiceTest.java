package com.lgcns.ikep4.supportpack.favorite.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.favorite.model.PortalFavorite;
import com.lgcns.ikep4.support.favorite.model.PortalFavoriteSearchCondition;
import com.lgcns.ikep4.support.favorite.service.PortalFavoriteService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
		"classpath:/configuration/spring/context-service.xml" })
public class FavoriteServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private PortalFavoriteService favoriteService;

	@Autowired
	private IdgenService idgenService;

	private PortalFavorite favorite;

	private String favoriteId;

	User user;

	@Before
	public void setUp() {

		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpSession session = new MockHttpSession();

		user = new User();
		user.setUserId("user1");
		user.setUserName("사용자1");
		user.setLocaleCode("ko");
		
		session.setAttribute("ikep.user", user);
		request.setSession(session);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));


		favorite = new PortalFavorite();

		favorite.setType("CONTENTS");
		favorite.setItemTypeCode(IKepConstant.ITEM_TYPE_CODE_BBS);
		favorite.setTargetId("11");
		favorite.setTargetTitle("test");
		favorite.setRegisterId(user.getUserId());
		favorite.setRegisterName(user.getUserName());
		favorite.setUpdaterId(user.getUserId());
		favorite.setUpdaterName(user.getUserName());

		favoriteId = idgenService.getNextId();
		favorite.setFavoriteId(favoriteId);

		favoriteService.create(favorite);

	}

	@Test
	public void read() {

		PortalFavorite result = favoriteService.read(favoriteId);
	}

	@Test
	public void delete() {

		favoriteService.delete(favorite);
	}

	@Test
	public void getAllForDocument() {

		PortalFavoriteSearchCondition searchCondition = new PortalFavoriteSearchCondition();

		SearchResult<PortalFavorite> result = favoriteService.getAllForDocument(searchCondition);
	}

	@Test
	public void getAllForPeople() {

		PortalFavoriteSearchCondition searchCondition = new PortalFavoriteSearchCondition();

		SearchResult<PortalFavorite> result = favoriteService.getAllForPeople(searchCondition);
	}

	@Test
	public void getSummaryForDocument() {

		PortalFavoriteSearchCondition searchCondition = new PortalFavoriteSearchCondition();

		SearchResult<PortalFavorite> result = favoriteService.getSummaryForDocument(searchCondition);
	}

	@Test
	public void getSummaryForPeople() {

		PortalFavoriteSearchCondition searchCondition = new PortalFavoriteSearchCondition();

		SearchResult<PortalFavorite> result = favoriteService.getSummaryForPeople(searchCondition);
	}

}
