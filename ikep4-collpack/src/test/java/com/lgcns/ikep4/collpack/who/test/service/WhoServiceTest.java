/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.who.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.collpack.who.constants.WhoConstants;
import com.lgcns.ikep4.collpack.who.model.Pro;
import com.lgcns.ikep4.collpack.who.model.Who;
import com.lgcns.ikep4.collpack.who.search.WhoSearchCondition;
import com.lgcns.ikep4.collpack.who.service.WhoService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * TODO Javadoc주석작성
 *
 * @author 서혜숙(shs0420@nate.com)
 * @version $Id: WhoServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class WhoServiceTest extends BaseServiceTestCase {

	@Autowired
	private WhoService whoService;

	private MockHttpServletRequest req;
	
	private Who who;
	
	User user;

	private String pk;

	@Before
	public void setUp() {
		req = new MockHttpServletRequest(); // HttpServletRequest Mock객체를 생성
		MockHttpSession session = new MockHttpSession(); // HttpSession Mock객체를 생성
		
		user = new User();
		user.setUserId("user96");
		user.setUserName("사용자96");	
		
		session.setAttribute("ikep.user", user); // User 모델을 ikep.user라는 키로 세션에 저장
		req.setSession(session);

		// RequestContextHolder에 위에서 작업한 request 객체를 저장
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(req));
		
		who = new Who();
		
		who.setName("홍길동");
		who.setCompanyName("길동주식회사");
		who.setTeamName("팀");
		who.setJobRankName("직급");
		who.setOfficePhoneno("01098761234");
		who.setMobile("01098761234");
		who.setMail("test@abc.com");
		who.setCompanyAddress("서울 개포구 개포동 11");
		who.setMemo("메모테스트");
		who.setHitCount(0);
		who.setVersion(1.0);
		who.setUpdateReason("주소변경");
		who.setPortalId("p1");
		who.setRegisterId("rgt");
		who.setRegisterName("rgt");
		who.setUpdaterId("upd");
		who.setUpdaterName("upd");	
		
		pk = whoService.create(who,user);
	}

	@Test
	public void testSelectProList() {
		Tag searchTag = new Tag();
		searchTag.setTagId("");
		searchTag.setTagItemId(user.getUserId());
		searchTag.setTagItemType(TagConstants.ITEM_TYPE_PROFILE_PRO);
		searchTag.setAsTagItemType("");
		searchTag.setPageIndex(1);
		searchTag.setPagePer(WhoConstants.WHO_TAG_PAGE_PER_RECORD);		
		List<Pro> proList = whoService.selectProList(searchTag);

		assertNotNull(proList);
	}	

	@Test
	public void testSelectWhoProList() {
		Tag searchTag = new Tag();
		searchTag.setTagId("");
		searchTag.setTagItemType(TagConstants.ITEM_TYPE_WHO);
		searchTag.setAsTagItemType("");
		searchTag.setTagItemId(user.getUserId());
		searchTag.setGroupType(who.getProfileGroupId());
		searchTag.setPageIndex(1);
		searchTag.setPagePer(WhoConstants.WHO_TAG_PAGE_PER_RECORD);			
		List<Pro> proList = whoService.selectWhoProList(searchTag);

		assertNotNull(proList);
	}
	
	@Test
	public void testCreate() {
		Who result = whoService.read(pk);		
		assertNotNull(result);
	}
	 
	@Test
	public void testUpdate() {
		who = whoService.read(pk);
		
		who.setCompanyName("Update길동주식회사");
		who.setTeamName("팀");
		who.setJobRankName("직급");
		who.setOfficePhoneno("01098761234");
		who.setMobile("01098761234");
		who.setMail("test@abc.com");
		who.setCompanyAddress("서울 개포구 개포동 11");
		who.setMemo("메모테스트");
		who.setUpdaterId("바꿈ID");
		who.setUpdaterName("바꿈이름");
		whoService.update(who);
		
		Who result = whoService.read(pk);
		
		assertEquals(this.who.getCompanyName(), result.getCompanyName());
	}	
	
	@Test
	public void testSelectProfileHistoryList() {
		List<Who> profileHistoryList = whoService.selectProfileHistoryList(who.getProfileGroupId());
		assertNotNull(profileHistoryList);
	}	
		
	
	@Test
	public void testCheckAlreadyMailExists() {	
		int wordCount = whoService.checkAlreadyMailExists(who);
		assertNotNull(wordCount);
	}	
	
	@Test
	public void testDelete() {
		whoService.delete(who.getProfileId());
		Who result = whoService.read(who.getProfileId());
		assertNull(result);
	}
	
	@Test
	public void testReadDetail() {
		who.setProfileId(pk);

		Who result = whoService.readDetail(who);

		assertEquals(1, result.getHitCount());
		

	}	
	
	@Test
	public void testListWhoBySearchCondition() {		
		WhoSearchCondition whoSearchCondition = new WhoSearchCondition();
		
		SearchResult<Who> result = whoService.listWhoBySearchCondition(whoSearchCondition);
		assertNotNull(result);
	}	
}
