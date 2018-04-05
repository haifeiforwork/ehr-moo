/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.test.dao;

import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lgcns.ikep4.lightpack.board.dao.BoardDao;
import com.lgcns.ikep4.lightpack.board.dao.BoardItemDao;
import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.lightpack.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.board.search.BoardItemSearchCondition;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * BoardItemDao Test 클래스
 *
 * @author 최현식
 * @version $Id: BoardItemDaoTest.java 16302 2011-08-19 08:43:50Z giljae $
 */
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"})
public class BoardItemDaoTest extends AbstractTransactionalJUnit4SpringContextTests  {
	@Autowired
	private BoardItemDao boardItemDao;

	@Autowired
	private BoardDao boardDao;

	private BoardItem boardItem;

	private BoardItem replyItem;

	private Board board;

	private String pk;

	private String replyPk;

	@Autowired
	private IdgenService idgenService;


	private BoardItemSearchCondition searchCondition;

	@Before
	public void setUp() {
		this.board = BoardFixture.fixtureBoard(this.idgenService.getNextId());

		this.boardDao.create(this.board);

		this.boardItem = BoardFixture.fixtureBoardItem(this.board.getBoardId(), this.idgenService.getNextId());


		this.pk = this.boardItemDao.create(this.boardItem);

		this.replyItem = BoardFixture.fixtureBoardItem(this.board.getBoardId(), this.idgenService.getNextId());

		this.replyItem.setItemParentId(this.boardItem.getItemId());
		this.replyItem.setItemGroupId(this.boardItem.getItemId());

		this.replyPk = this.boardItemDao.create(this.replyItem);

	}
	@Test
	public void testGet() {
		BoardItem boardItem = this.boardItemDao.get(this.pk);
		Assert.assertNotNull(boardItem);
	}

	@Test
	public void testExists() {
		boolean exists = this.boardItemDao.exists(this.pk);

		Assert.assertTrue(exists);


	}
	@Test
	public void testCreate() {
		BoardItem result = this.boardItemDao.get(this.pk);

		Assert.assertNotNull(result);

	}
	@Test
	public void testUpdate() {
		this.boardItem.setContents("--------------------------");

		this.boardItemDao.update(this.boardItem);

		BoardItem result = this.boardItemDao.get(this.pk);

		Assert.assertEquals(this.boardItem.getContents(), result.getContents());
	}

	@Test
	@Ignore
	public void testPhysicalDelete() {
		this.boardItemDao.physicalDelete(this.pk);

		BoardItem result = this.boardItemDao.get(this.pk);

		assertNull(result);
	}

	@Test
	public void testLogicalDelete() {
		this.boardItemDao.logicalDelete(this.boardItem);

		BoardItem result = this.boardItemDao.get(this.boardItem.getItemId());

		Assert.assertTrue(result.getItemDelete() == 1);

	}
	@Test
	public void testRemove() {
		//구현하지 않았음
	}
	@Test
	public void testListByItemIdArray() {
		List<BoardItem> boardItemList = this.boardItemDao.listByItemIdArray(Arrays.asList(this.boardItem.getItemId()));

		Assert.assertTrue(boardItemList.size() > 0);
	}
	@Test
	public void testListLowerItemThread() {
		List<BoardItem> boardItemList = this.boardItemDao.listLowerItemThread(this.boardItem.getItemId());
		Assert.assertTrue(boardItemList.size() > 0);
	}

	@Test
	public void testListBySearchCondition() {
		this.searchCondition = new BoardItemSearchCondition();
		this.searchCondition.setBoardId(this.boardItem.getBoardId());
		this.searchCondition.setPageIndex(1);
		this.searchCondition.terminateSearchCondition(this.boardItemDao.countBySearchCondition(this.searchCondition));
		this.searchCondition.setAdmin(true);

		List<BoardItem> result = this.boardItemDao.listBySearchCondition(this.searchCondition);

		Assert.assertTrue(result.size() > 0);
	}


	@Test
	public void testListReplayItemThreadByItemId() {
		this.searchCondition = new BoardItemSearchCondition();
		this.searchCondition.setBoardId(this.boardItem.getBoardId());
		this.searchCondition.setPageIndex(1);
		this.searchCondition.setAdmin(true);
		this.searchCondition.terminateSearchCondition(this.boardItemDao.countBySearchCondition(this.searchCondition));

		List<BoardItem> result = this.boardItemDao.listReplayItemThreadByItemId(this.boardItem.getItemId());

		Assert.assertTrue(result.size() == 2);
	}

	@Test
	public void testCountBySearchCondition() {
		this.searchCondition = new BoardItemSearchCondition();
		this.searchCondition.setBoardId(this.boardItem.getBoardId());
		this.searchCondition.setPageIndex(1);
		this.searchCondition.setAdmin(true);

		Assert.assertTrue(this.boardItemDao.countBySearchCondition(this.searchCondition) == 2);
	}


	@Test
	public void testCountChildren() {
		Assert.assertTrue(this.boardItemDao.countChildren(this.boardItem.getItemId()) == 1);
	}


	@Test
	public void testPhysicalDeleteThread() {
		this.boardItemDao.physicalDeleteThread(this.boardItem.getItemId());

		List<BoardItem> result = this.boardItemDao.listReplayItemThreadByItemId(this.boardItem.getItemId());

		Assert.assertTrue(result.size() == 0);
	}

	@Test
	public void testUpdateRecommendCount() {
		this.boardItemDao.updateRecommendCount(this.boardItem.getItemId());

		BoardItem result = this.boardItemDao.get(this.boardItem.getItemId());

		Assert.assertTrue(result.getRecommendCount() == 1);

	}
	@Test
	public void testUpdateHitCount() {
		this.boardItemDao.updateHitCount(this.boardItem.getItemId());

		BoardItem result = this.boardItemDao.get(this.boardItem.getItemId());

		Assert.assertTrue(result.getHitCount() == 1);

	}

	@Test
	public void testUpdateLinereplyCount() {

		//게시글의 댓글 갯수를 업데이트한다.
		this.boardItemDao.updateLinereplyCount( this.boardItem.getItemId());

		BoardItem result = this.boardItemDao.get(this.boardItem.getItemId());

		Assert.assertTrue(result.getLinereplyCount() == 1);
	}

	@Test
	public void testUpdateReplyCount() {
		this.boardItemDao.updateReplyCount(this.boardItem.getItemId());

		BoardItem result = this.boardItemDao.get(this.boardItem.getItemId());

		Assert.assertTrue(result.getReplyCount() == 1);
	}

	@Test
	public void testUpdateStep() {
		this.boardItemDao.updateStep(this.boardItem);
	}

	@Test
	public void testListBatchDeletePassedBoardItem() {
		this.boardItem.setEndDate(DateUtils.addYears(new Date(), -1));
		this.boardItemDao.update(this.boardItem);

		List<BoardItem> result = this.boardItemDao.listBatchDeletePassedBoardItem(5);

		Assert.assertTrue(result.size() > 2);

	}
	@Test
	public void testListBatchDeleteStatusBoardItem() {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("itemId", this.boardItem.getItemId());
		parameter.put("status", BoardItem.STATUS_DELETE_WAITING);

		this.boardItemDao.updateItemDeleteField(parameter);

		List<BoardItem> result = this.boardItemDao.listBatchDeletePassedBoardItem(5);

		Assert.assertTrue(result.size() > 0);

	}
	@Test
	public void testListRecentBoardItem() {
		//Map<String, Object> parameter = new HashMap<String, Object>();

		//parameter.put("boardId", this.boardItem.getBoardId());
		//parameter.put("count", 5);

		List<BoardItem> result = this.boardItemDao.listRecentBoardItem(this.searchCondition);

		Assert.assertTrue(result.size() > 0);
	}

	@Test
	public void testListRecentAllBoardItem() {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("userId", "admin");
		parameter.put("count", 5);

		List<BoardItem> result = this.boardItemDao.listRecentAllBoardItem(parameter);

		Assert.assertTrue(result.size() > 0);
	}

	@Test
	public void testUpdateItemDeleteField() {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("itemId", this.boardItem.getItemId());
		parameter.put("status", BoardItem.STATUS_DELETE_WAITING);

		this.boardItemDao.updateItemDeleteField(parameter);

		BoardItem result = this.boardItemDao.get(this.boardItem.getItemId());

		Assert.assertTrue(result.getItemDelete() == 2);
	}

	@Test
	public void testListByBoardItemOfDeletedBoard() {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("boardId", this.boardItem.getBoardId());
		parameter.put("getCount", 5);

		List<BoardItem> result = this.boardItemDao.listByBoardItemOfDeletedBoard(parameter);

		Assert.assertTrue(result.size() > 0);
	}
}
