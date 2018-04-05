package com.lgcns.ikep4.socialpack.microblogging.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.socialpack.microblogging.model.Search;
import com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * 
 * SearchService 테스트 케이스
 *
 * @author 최성우
 * @version $Id: SearchServiceTest.java 16246 2011-08-18 04:48:28Z giljae $
 */
public class SearchServiceTest extends BaseServiceTestCase {

	MockHttpServletRequest request;

	MockHttpServletResponse response;

	MockHttpSession session;

	User user;

	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private SearchService searchService;
	
	private Search search;

	String pk = "";
	
	String searchWord = "";
	

	@Before
	public void setUp() {

		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();

		user = new User();
		user.setUserId("user100");
		user.setUserName("사용자100");
		user.setPortalId("P000001");
		session.setAttribute("ikep.user", user);
		
		request.setSession(session);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		searchWord = "unittest";
		
		search = new Search();

		pk = idgenService.getNextId();
		
		search.setSearchId(pk);
		search.setSearchWord(searchWord);
		search.setSearchUserId(user.getUserId());
		search.setIsSave("1");

		searchService.create(search);
	}

	@Test
	public void create() {
		Search search = searchService.read(this.pk);
		assertNotNull(search);	
	}

	@Test
	public void read() {
		Search search = searchService.read(this.pk);
		assertNotNull(search);	
		
	}

	@Test
	public void update() {
		search.setIsSave("0");
		searchService.update(search);
		Search returnSearch = searchService.read(this.pk);
		assertEquals("0", returnSearch.getIsSave());	
	}
	
	@Test
	public void delete() {
		searchService.delete(this.pk);
		Search search = this.searchService.read(this.pk);
		assertNull(search);
	}

	@Test
	public void count() {
		Search search = new Search();
		search.setSearchWord(searchWord);
		search.setSearchUserId(user.getUserId());
		
		int count = searchService.count(search);
		assertTrue(0 < count);	
	}

	@Test
	public void listBySearchUserId() {
		Search search = new Search();
		search.setSearchWord(searchWord);
		search.setSearchUserId(user.getUserId());
		
		List<Search> list = searchService.listBySearchUserId(search);
		assertNotNull(list);
	}

	@Test
	public void trendList() {
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setPortalId(user.getPortalId());
		
		List<String> list = searchService.trendList(mblogSearchCondition);
		assertNotNull(list);
	}

}
