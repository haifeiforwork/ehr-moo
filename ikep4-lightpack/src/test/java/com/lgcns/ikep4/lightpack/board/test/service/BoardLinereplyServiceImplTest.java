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
import com.lgcns.ikep4.lightpack.board.dao.BoardDao;
import com.lgcns.ikep4.lightpack.board.dao.BoardItemDao;
import com.lgcns.ikep4.lightpack.board.dao.BoardLinereplyDao;
import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.lightpack.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.board.model.BoardLinereply;
import com.lgcns.ikep4.lightpack.board.search.BoardLinereplySearchCondition;
import com.lgcns.ikep4.lightpack.board.service.BoardLinereplyService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * BoardLinereplyServiceImpl Test 클래스
 *
 * @author 최현식
 * @version $Id: BoardLinereplyServiceImplTest.java 16302 2011-08-19 08:43:50Z giljae $
 */
@Ignore
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"})
public class BoardLinereplyServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private IdgenService idgenService;

	@Autowired
	private BoardLinereplyService boardLinereplyService;

	@Autowired
	private BoardDao boardDao;

	@Autowired
	private BoardItemDao boardItemDao;

	@Autowired
	private BoardLinereplyDao boardLinereplyDao;

	private Board board;

	private BoardItem boardItem;

	private BoardLinereply boardLinereply;

	private BoardLinereply child;

	private String pk;

	private BoardLinereplySearchCondition searchCondition;

	private MockHttpServletRequest request;

	@Before
	public void setUp() {
		this.request = new MockHttpServletRequest(); // HttpServletRequest Mock객체를 생성합니다.
		MockHttpSession session = new MockHttpSession(); // HttpSession Mock객체를 생성합니다.

		User user = new User();
		user.setUserId("admin");
		user.setUserName("admin");
		session.setAttribute("ikep.user", user); // User 모델을 ikep.user라는 키로 세션에 저장합니다.
		this.request.setSession(session);

		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(this.request));

		this.board = BoardFixture.fixtureBoard(this.idgenService.getNextId());

		this.boardDao.create(this.board);

		this.boardItem = BoardFixture.fixtureBoardItem(this.board.getBoardId(), this.idgenService.getNextId());

		this.boardItemDao.create(this.boardItem);

		this.boardLinereply = BoardFixture.fixtureBoardLinereply(this.boardItem.getItemId(), this.idgenService.getNextId());

		this.pk = this.boardLinereplyDao.create(this.boardLinereply);

		this.child  = BoardFixture.fixtureBoardLinereply(this.boardItem.getItemId(), this.idgenService.getNextId());

		this.child.setLinereplyId(this.idgenService.getNextId());
		this.child.setLinereplyParentId(this.pk);

		this.boardLinereplyDao.create(this.child);

		Assert.assertNotNull("테스트 픽스쳐 초기화", this.pk);
	}


	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardLinereplyServiceImpl#listBoardLinereplyBySearchCondition(com.lgcns.ikep4.lightpack.board.search.BoardLinereplySearchCondition)}.
	 */
	@Test
	public void testListBoardLinereplyBySearchCondition() {
		this.searchCondition = new BoardLinereplySearchCondition();
		this.searchCondition.setItemId(this.boardLinereply.getItemId());
		this.searchCondition.setPageIndex(1);

		SearchResult<BoardLinereply> result = this.boardLinereplyService.listBoardLinereplyBySearchCondition(this.searchCondition);


		Assert.assertTrue(result.getEntity().size() == 2);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardLinereplyServiceImpl#readBoardLinereply(java.lang.String)}.
	 */
	@Test
	public void testReadBoardLinereply() {
		BoardLinereply result = this.boardLinereplyService.readBoardLinereply(this.boardLinereply.getLinereplyId());
		Assert.assertNotNull(result);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardLinereplyServiceImpl#createBoardLinereply(com.lgcns.ikep4.lightpack.board.model.BoardLinereply)}.
	 */
	@Test
	public void testCreateBoardLinereply() {
		BoardLinereply create = BoardFixture.fixtureBoardLinereply(this.boardItem.getItemId(), this.idgenService.getNextId());

		this.boardLinereplyService.createBoardLinereply(create);

		BoardLinereply result = this.boardLinereplyService.readBoardLinereply(create.getLinereplyId());

		Assert.assertNotNull(result);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardLinereplyServiceImpl#createReplyBoardLinereply(com.lgcns.ikep4.lightpack.board.model.BoardLinereply)}.
	 */
	@Test
	public void testCreateReplyBoardLinereply() {
		BoardLinereply create = BoardFixture.fixtureBoardLinereply(this.boardItem.getItemId(), this.idgenService.getNextId());

		create.setLinereplyParentId(this.boardLinereply.getLinereplyId());
		create.setLinereplyGroupId(this.boardLinereply.getLinereplyId());

		this.boardLinereplyService.createBoardLinereply(create);

		BoardLinereply result = this.boardLinereplyService.readBoardLinereply(create.getLinereplyId());

		Assert.assertNotNull(result);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardLinereplyServiceImpl#updateBoardLinereply(com.lgcns.ikep4.lightpack.board.model.BoardLinereply)}.
	 */
	@Test
	public void testUpdateBoardLinereply() {
		this.boardLinereply.setContents("UPDATE-CONTENT");

		this.boardLinereplyService.updateBoardLinereply(this.boardLinereply);

		BoardLinereply result = this.boardLinereplyService.readBoardLinereply(this.boardLinereply.getLinereplyId());

		Assert.assertEquals("UPDATE-CONTENT", result.getContents());

	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardLinereplyServiceImpl#adminDeleteBoardLinereply(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAdminDeleteBoardLinereply() {
		this.boardLinereplyService.adminDeleteBoardLinereply(this.boardLinereply.getItemId(), this.boardLinereply.getLinereplyId());
		BoardLinereply result = this.boardLinereplyService.readBoardLinereply(this.boardLinereply.getLinereplyId());
		Assert.assertNull(result);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardLinereplyServiceImpl#userDeleteBoardLinereply(com.lgcns.ikep4.lightpack.board.model.BoardLinereply)}.
	 */
	@Test
	public void testUserDeleteBoardLinereply() {
		//자식있는 놈을 삭제하면 삭제플레그만 변경됨
		this.boardLinereplyService.userDeleteBoardLinereply(this.boardLinereply);

		BoardLinereply result = this.boardLinereplyService.readBoardLinereply(this.boardLinereply.getLinereplyId());

		Assert.assertTrue(result.getLinereplyDelete() == 1);

		//자식이 없는 놈을 삭제하는 실제로 삭제됨
		this.boardLinereplyService.userDeleteBoardLinereply(this.child);

		result = this.boardLinereplyService.readBoardLinereply(this.child.getLinereplyId());

		Assert.assertNull(result);

		//자식을 없앴으니 이제 실제로 삭제가 되어야 한다.
		this.boardLinereplyService.userDeleteBoardLinereply(this.boardLinereply);

		result = this.boardLinereplyService.readBoardLinereply(this.boardLinereply.getLinereplyId());

		Assert.assertNull(result);

	}

}
