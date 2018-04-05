/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.test.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.lightpack.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.board.model.BoardRecommend;
import com.lgcns.ikep4.lightpack.board.model.BoardReference;
import com.lgcns.ikep4.lightpack.board.search.BoardItemSearchCondition;
import com.lgcns.ikep4.lightpack.board.service.BoardItemService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * BoardItemService Test 클래스
 *
 * @author 최현식
 * @version $Id: BoardItemServiceTest.java 16302 2011-08-19 08:43:50Z giljae $
 */
@Ignore
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"})
public class BoardItemServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	private static final String TEST_USER = "user1";

	private static final String TEST_PORTAL = "portal1";

	private static final Integer THREAD_COUNT = 10;

	private static final long[] TAG_ARRAY = {new Date().getTime() + 100, new Date().getTime() + 200, new Date().getTime() + 300};

	@Autowired
	private BoardItemService boardItemService;

	@Autowired
	private BoardItemDao boardItemDao;

	@Autowired
	private BoardDao boardDao;

	private BoardItem boardItem;

	private BoardItem replyItem;

	private Board board;

	private String pk;

	private String replyPk;

	private User user;

	@Autowired
	private IdgenService idgenService;

	private BoardItemSearchCondition searchCondition;

	private MockHttpServletRequest request;

	@Before
	public void setUp() {

		this.request = new MockHttpServletRequest(); // HttpServletRequest Mock객체를 생성합니다.
		MockHttpSession session = new MockHttpSession(); // HttpSession Mock객체를 생성합니다.

		this.user = new User();
		this.user.setUserId("admin");
		this.user.setUserName("admin");
		session.setAttribute("ikep.user", this.user); // User 모델을 ikep.user라는 키로 세션에 저장합니다.
		this.request.setSession(session);

		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(this.request));


		this.board = BoardFixture.fixtureBoard(this.idgenService.getNextId());

		this.boardDao.create(this.board);

		this.boardItem = BoardFixture.fixtureBoardItem(this.board.getBoardId(), this.idgenService.getNextId());

		this.boardItem.setReplyCount(1);

		this.pk = this.boardItemDao.create(this.boardItem);

		this.replyItem = BoardFixture.fixtureBoardItem(this.board.getBoardId(), this.idgenService.getNextId());

		this.replyItem.setItemParentId(this.boardItem.getItemId());
		this.replyItem.setItemGroupId(this.boardItem.getItemId());

		this.replyPk = this.boardItemDao.create(this.replyItem);

	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardItemServiceImpl#listBoardItemBySearchCondition(com.lgcns.ikep4.lightpack.board.search.BoardItemSearchCondition)}.
	 */
	@Test
	public void testListBoardItemBySearchCondition() {
		this.searchCondition = new BoardItemSearchCondition();
		this.searchCondition.setBoardId(this.boardItem.getBoardId());
		this.searchCondition.setPageIndex(1);
		this.searchCondition.setAdmin(true);

		SearchResult<BoardItem> result = this.boardItemService.listBoardItemBySearchCondition(this.searchCondition);

		Assert.assertTrue(result.getEntity().size() > 0);
	}



	/**
	 * Test list recent board item.
	 */
	@Test
	public void testListRecentBoardItem() {
		
		//User user;
		
		//List<BoardItem> boardItemList = this.boardItemService.listRecentBoardItem(this.boardItem.getBoardId(),"admin", 5);
		List<BoardItem> boardItemList = this.boardItemService.listRecentBoardItem(this.searchCondition);
		
		Assert.assertTrue(boardItemList.size() > 0);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardItemServiceImpl#readBoardItem(java.lang.String)}.
	 */
	@Test
	public void testReadBoardItem() {
		BoardItem boardItem = this.boardItemService.readBoardItem(this.boardItem.getItemId());

		Assert.assertNotNull(boardItem);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardItemServiceImpl#createBoardItem(com.lgcns.ikep4.lightpack.board.model.BoardItem, com.lgcns.ikep4.support.user.member.model.User)}.
	 */
	@Test
	public void testCreateBoardItem() {
		String newBoardId = this.boardItemService.createBoardItem(this.boardItem, this.user);

		BoardItem boardItem = this.boardItemDao.get(newBoardId);

		Assert.assertNotNull(boardItem);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardItemServiceImpl#createReplyBoardItem(com.lgcns.ikep4.lightpack.board.model.BoardItem, com.lgcns.ikep4.support.user.member.model.User)}.
	 */
	@Test
	public void testCreateReplyBoardItem() {
		String newReplyId = this.boardItemService.createBoardItem(this.replyItem, this.user);

		BoardItem boardItem = this.boardItemDao.get(newReplyId);

		Assert.assertNotNull(boardItem);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardItemServiceImpl#updateBoardItem(com.lgcns.ikep4.lightpack.board.model.BoardItem, com.lgcns.ikep4.support.user.member.model.User)}.
	 */
	@Test
	public void testUpdateBoardItem() {
		this.boardItem.setContents("UPDATE-CONTENT");

		this.boardItemService.updateBoardItem(this.boardItem, this.user);

		BoardItem result = this.boardItemDao.get(this.boardItem.getItemId());

		Assert.assertEquals("UPDATE-CONTENT", result.getContents());
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardItemServiceImpl#adminDeleteBoardItem(com.lgcns.ikep4.lightpack.board.model.BoardItem)}.
	 */
	@Test
	public void testAdminDeleteBoardItem() {
		this.boardItemService.adminDeleteBoardItem(this.boardItem);

		BoardItem result = this.boardItemDao.get(this.boardItem.getItemId());

		Assert.assertTrue(result.getItemDelete().equals(BoardItem.STATUS_DELETE_WAITING));
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardItemServiceImpl#userDeleteBoardItem(com.lgcns.ikep4.lightpack.board.model.BoardItem)}.
	 */
	@Test
	public void testUserDeleteBoardItem() {
		this.boardItemService.userDeleteBoardItem(this.boardItem);

		BoardItem result = this.boardItemDao.get(this.boardItem.getItemId());

		Assert.assertTrue(result.getItemDelete().equals(BoardItem.STATUS_USER_DELETED));

		this.boardItemService.userDeleteBoardItem(this.replyItem);

		result = this.boardItemDao.get(this.replyItem.getItemId());

		Assert.assertTrue(result.getItemDelete().equals(BoardItem.STATUS_DELETE_WAITING));

		this.boardItemService.userDeleteBoardItem(this.boardItem);

		result = this.boardItemDao.get(this.boardItem.getItemId());

		Assert.assertTrue(result.getItemDelete().equals(BoardItem.STATUS_DELETE_WAITING));
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardItemServiceImpl#updateRecommendCount(com.lgcns.ikep4.lightpack.board.model.BoardRecommend)}.
	 */
	@Test
	public void testUpdateRecommendCount() {
		BoardRecommend boardRecommend = new BoardRecommend();

		boardRecommend.setItemId(this.boardItem.getItemId());
		boardRecommend.setRegisterId(this.user.getUserId());

		this.boardItemService.updateRecommendCount(boardRecommend);

		BoardItem result = this.boardItemDao.get(this.boardItem.getItemId());

		Assert.assertTrue(result.getRecommendCount() == 1);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardItemServiceImpl#updateHitCount(com.lgcns.ikep4.lightpack.board.model.BoardReference)}.
	 */
	@Test
	public void testUpdateHitCount() {
		BoardReference boardReference = new BoardReference();

		boardReference.setItemId(this.boardItem.getItemId());
		boardReference.setRegisterId(this.user.getUserId());

		this.boardItemService.updateHitCount(boardReference);

		BoardItem result = this.boardItemDao.get(this.boardItem.getItemId());

		Assert.assertTrue(result.getHitCount() == 1);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardItemServiceImpl#adminMultiDeleteBoardItem(java.lang.String[])}.
	 */
	@Test
	public void testAdminMultiDeleteBoardItem() {
		this.boardItemService.adminMultiDeleteBoardItem(new String[]{this.boardItem.getItemId(), this.replyItem.getItemId()});

		BoardItem result = this.boardItemDao.get(this.boardItem.getItemId());

		Assert.assertTrue(result.getItemDelete().equals(BoardItem.STATUS_DELETE_WAITING));

		result = this.boardItemDao.get(this.replyItem.getItemId());

		Assert.assertTrue(result.getItemDelete().equals(BoardItem.STATUS_DELETE_WAITING));

	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardItemServiceImpl#exsitRecommend(com.lgcns.ikep4.lightpack.board.model.BoardRecommend)}.
	 */
	@Test
	public void testExsitRecommend() {
		BoardRecommend boardRecommend = new BoardRecommend();

		boardRecommend.setItemId(this.boardItem.getItemId());
		boardRecommend.setRegisterId(this.user.getUserId());

		Assert.assertFalse(this.boardItemService.exsitRecommend(boardRecommend));

		this.boardItemService.updateRecommendCount(boardRecommend);

		Assert.assertTrue(this.boardItemService.exsitRecommend(boardRecommend));
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardItemServiceImpl#readBoardItemMasterInfo(java.lang.String)}.
	 */
	@Test
	public void testReadBoardItemMasterInfo() {
		BoardItem result = this.boardItemService.readBoardItemMasterInfo(this.boardItem.getItemId());

		Assert.assertNotNull(result);
	}


	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardItemServiceImpl#updateItemDeleteField(java.lang.String, java.lang.Integer)}.
	 */
	@Test
	public void testUpdateItemDeleteField() {
		this.boardItemService.updateItemDeleteField(this.boardItem.getItemId(), BoardItem.STATUS_DELETE_WAITING);

		BoardItem result = this.boardItemDao.get(this.boardItem.getItemId());

		Assert.assertTrue(result.getItemDelete().equals(BoardItem.STATUS_DELETE_WAITING));
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardItemServiceImpl#updateItemListDeleteField(java.util.List, java.lang.Integer)}.
	 */
	@Test
	public void testUpdateItemListDeleteField() {
		this.boardItemService.updateItemListDeleteField(Arrays.asList(this.boardItem.getItemId(), this.replyItem.getItemId()), BoardItem.STATUS_DELETE_WAITING);

		BoardItem result = this.boardItemDao.get(this.boardItem.getItemId());

		Assert.assertTrue(result.getItemDelete().equals(BoardItem.STATUS_DELETE_WAITING));

		result = this.boardItemDao.get(this.replyItem.getItemId());

		Assert.assertTrue(result.getItemDelete().equals(BoardItem.STATUS_DELETE_WAITING));
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardItemServiceImpl#updateItemThreadDeleteField(java.lang.String, java.lang.Integer)}.
	 */
	@Test
	public void testUpdateItemThreadDeleteField() {
		this.boardItemService.updateItemThreadDeleteField(this.boardItem.getItemId(), BoardItem.STATUS_DELETE_WAITING);

		BoardItem result = this.boardItemDao.get(this.boardItem.getItemId());

		Assert.assertTrue(result.getItemDelete().equals(BoardItem.STATUS_DELETE_WAITING));

		result = this.boardItemDao.get(this.replyItem.getItemId());

		Assert.assertTrue(result.getItemDelete().equals(BoardItem.STATUS_DELETE_WAITING));
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardItemServiceImpl#deleteBoardItemThread(com.lgcns.ikep4.lightpack.board.model.BoardItem)}.
	 */
	@Test
	public void testDeleteBoardItemThread() {
		this.boardItemService.deleteBoardItemThread(this.boardItem);

		BoardItem result = this.boardItemDao.get(this.boardItem.getItemId());

		Assert.assertNull(result);

		result = this.boardItemDao.get(this.replyItem.getItemId());

		Assert.assertNull(result);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardItemServiceImpl#listReplayItemThreadByItemId(java.lang.String)}.
	 */
	@Test
	public void testListReplayItemThreadByItemId() {
		List<BoardItem> result = this.boardItemService.listReplayItemThreadByItemId(this.boardItem.getItemGroupId());

		Assert.assertTrue(result.size() == 2);
	}

}
