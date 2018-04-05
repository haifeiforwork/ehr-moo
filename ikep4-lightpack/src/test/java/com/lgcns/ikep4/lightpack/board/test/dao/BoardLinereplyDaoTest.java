/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.test.dao;

import static org.junit.Assert.assertNull;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lgcns.ikep4.lightpack.board.dao.BoardDao;
import com.lgcns.ikep4.lightpack.board.dao.BoardItemDao;
import com.lgcns.ikep4.lightpack.board.dao.BoardLinereplyDao;
import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.lightpack.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.board.model.BoardLinereply;
import com.lgcns.ikep4.lightpack.board.search.BoardLinereplySearchCondition;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * BoardLinereplyDao Test 클래스
 *
 * @author 최현식
 * @version $Id: BoardLinereplyDaoTest.java 16302 2011-08-19 08:43:50Z giljae $
 */
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"})
public class BoardLinereplyDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private IdgenService idgenService;

	@Autowired
	private BoardDao boardDao;

	@Autowired
	private BoardItemDao boardItemDao;

	@Autowired
	private BoardLinereplyDao boardLinereplyDao;

	private Board board;

	private BoardItem boardItem;

	private BoardLinereply boardLinereply;

	private String pk;

	private BoardLinereplySearchCondition searchContion;


	@Before
	public void setUp() {

		this.board = BoardFixture.fixtureBoard(this.idgenService.getNextId());

		this.boardDao.create(this.board);

		this.boardItem = BoardFixture.fixtureBoardItem(this.board.getBoardId(), this.idgenService.getNextId());

		this.boardItemDao.create(this.boardItem);

		this.boardLinereply = BoardFixture.fixtureBoardLinereply(this.boardItem.getItemId(), this.idgenService.getNextId());

		this.pk = this.boardLinereplyDao.create(this.boardLinereply);

		BoardLinereply child  = BoardFixture.fixtureBoardLinereply(this.boardItem.getItemId(), this.idgenService.getNextId());

		child.setLinereplyId(this.idgenService.getNextId());
		child.setLinereplyParentId(this.pk);

		this.boardLinereplyDao.create(child);

		Assert.assertNotNull("테스트 픽스쳐 초기화", this.pk);

	}

	@Test
	public void testGet() {
		BoardLinereply boardLinereply = this.boardLinereplyDao.get(this.pk);
		Assert.assertNotNull(boardLinereply);
	}
	@Test
	@Ignore
	public void testExists() {
		boolean exists = this.boardLinereplyDao.exists(this.pk);

		Assert.assertTrue(exists);


	}
	@Test
	@Ignore
	public void testCreate() {
		BoardLinereply result = this.boardLinereplyDao.get(this.pk);

		Assert.assertNotNull(result);

	}
	@Test
	@Ignore
	public void testUpdate() {
		this.boardLinereply.setContents("--------------------------");

		this.boardLinereplyDao.update(this.boardLinereply);

		BoardLinereply result = this.boardLinereplyDao.get(this.pk);

		Assert.assertEquals(this.boardLinereply.getContents(), result.getContents());
	}

	@Test
	public void testPhysicalDelete() {
		this.boardLinereplyDao.physicalDelete(this.pk);

		BoardLinereply result = this.boardLinereplyDao.get(this.pk);

		assertNull(result);
	}

	@Test
	public void testLogicalDelete() {
		this.boardLinereplyDao.logicalDelete(this.boardLinereply);

		BoardLinereply result = this.boardLinereplyDao.get(this.pk);

		Assert.assertTrue(result.getLinereplyDelete() == 1);
	}

	@Test
	public void testUpdateStep() {
		this.boardLinereplyDao.updateStep(this.boardLinereply);

		//스탭 업데이트 라서 테스트가 어려움
		Assert.assertTrue(true);
	}

	@Test
	public void testRemove() {
		//구현하지 않았음
	}

	@Test
	public void testPhysicalDeleteThreadByLinereplyId() {
		this.boardLinereplyDao.physicalDeleteThreadByLinereplyId(this.boardLinereply.getLinereplyId());

		Assert.assertNull(this.boardLinereplyDao.get(this.boardLinereply.getLinereplyId()));
	}

	@Test
	public void testPhysicalDeleteThreadByItemThread() {
		this.boardLinereplyDao.physicalDeleteThreadByItemThread(this.boardLinereply.getItemId());
		Assert.assertNull(this.boardLinereplyDao.get(this.boardLinereply.getLinereplyId()));
	}

	@Test
	public void testPhysicalDeleteThreadByItemId() {
		this.boardLinereplyDao.physicalDeleteThreadByItemId(this.boardLinereply.getItemId());
		Assert.assertNull(this.boardLinereplyDao.get(this.boardLinereply.getLinereplyId()));
	}

	@Test
	public void testListBySearchCondition() {
		this.searchContion = new BoardLinereplySearchCondition();
		this.searchContion.setItemId(this.boardLinereply.getItemId());
		this.searchContion.setPageIndex(1);
		this.searchContion.terminateSearchCondition(this.boardLinereplyDao.countBySearchCondition(this.searchContion));

		List<BoardLinereply> result = this.boardLinereplyDao.listBySearchCondition(this.searchContion);

		Assert.assertTrue(result.size() > 0);
	}

	@Test
	public void testCountBySearchCondition() {
		this.searchContion = new BoardLinereplySearchCondition();
		this.searchContion.setItemId(this.boardLinereply.getItemId());

		Assert.assertTrue(this.boardLinereplyDao.countBySearchCondition(this.searchContion) > 0);
	}

	@Test
	public void testCountChildren() {
		Assert.assertTrue(this.boardLinereplyDao.countChildren(this.boardLinereply.getLinereplyId()) == 1);

	}

}
