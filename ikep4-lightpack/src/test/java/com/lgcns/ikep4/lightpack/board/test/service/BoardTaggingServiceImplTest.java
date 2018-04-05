/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.test.service;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.lightpack.board.test.dao.BoardFixture;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.lightpack.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.board.search.RelatedBoardItemSearchCondition;
import com.lgcns.ikep4.lightpack.board.service.BoardAdminService;
import com.lgcns.ikep4.lightpack.board.service.BoardItemService;
import com.lgcns.ikep4.lightpack.board.service.BoardTaggingService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * BoardTaggingServiceImpl Test 클래스
 *
 * @author 최현식
 * @version $Id: BoardTaggingServiceImplTest.java 16302 2011-08-19 08:43:50Z giljae $
 */
@Ignore
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"})
public class BoardTaggingServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private BoardTaggingService boardTaggingService;

	@Autowired
	private BoardItemService boardItemService;

	@Autowired
	private BoardAdminService boardAdminService;

	private MockHttpServletRequest request;

	private RelatedBoardItemSearchCondition searchCondition;

	private BoardItem boardItem;

	private Board board;

	@Before
	public void setUp() {

		this.request = new MockHttpServletRequest(); // HttpServletRequest Mock객체를 생성합니다.
		MockHttpSession session = new MockHttpSession(); // HttpSession Mock객체를 생성합니다.

		User user = new User();
		session.setAttribute("ikep.user", user); // User 모델을 ikep.user라는 키로 세션에 저장합니다.
		this.request.setSession(session);

		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(this.request));

		this.board = BoardFixture.fixtureBoard(null);

		this.boardAdminService.createBoard(this.board);

		this.boardItem = BoardFixture.fixtureBoardItem(this.board.getBoardId(), null);

		this.boardItemService.createBoardItem(this.boardItem, user);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardTaggingServiceImpl#listRelatedBoardItemBySearchCondition(com.lgcns.ikep4.lightpack.board.search.RelatedBoardItemSearchCondition)}.
	 */
	@Test
	public void testListRelatedBoardItemBySearchCondition() {
		this.searchCondition = new RelatedBoardItemSearchCondition();
		this.searchCondition.setPageIndex(1);

		SearchResult<BoardItem> result = this.boardTaggingService.listRelatedBoardItemBySearchCondition(this.searchCondition);

		Assert.assertTrue(true);

	}

}
