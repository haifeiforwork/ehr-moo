/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.test.dao;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lgcns.ikep4.lightpack.board.dao.BoardDao;
import com.lgcns.ikep4.lightpack.board.dao.BoardItemDao;
import com.lgcns.ikep4.lightpack.board.dao.BoardReferenceDao;
import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.lightpack.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.board.model.BoardReference;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * BoardReferenceDao Test 클래스
 *
 * @author 최현식
 * @version $Id: BoardReferenceDaoTest.java 16302 2011-08-19 08:43:50Z giljae $
 */
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml" })
public class BoardReferenceDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private BoardReferenceDao boardReferenceDao;
	@Autowired
	private IdgenService idgenService;

	@Autowired
	private BoardDao boardDao;

	@Autowired
	private BoardItemDao boardItemDao;

	private BoardItem boardItem;

	private Board board;

	private BoardReference boardReference;

	@Before
	public void setUp() {
		this.board = BoardFixture.fixtureBoard(this.idgenService.getNextId());

		this.boardDao.create(this.board);

		this.boardItem = BoardFixture.fixtureBoardItem(this.board.getBoardId(), this.idgenService.getNextId());

		this.boardItemDao.create(this.boardItem);

		this.boardReference = new BoardReference();

		this.boardReference.setItemId(this.boardItem.getItemId());
		this.boardReference.setRegisterId("TEST");

		this.boardReferenceDao.create(this.boardReference);
	}

	@Test
	public void testGet() {
		Assert.assertNotNull(this.boardReferenceDao.get(this.boardReference));
	}

	@Test
	public void testExists() {
		Assert.assertTrue(this.boardReferenceDao.exists(this.boardReference));
	}

	@Test
	public void testCreate() {
		Assert.assertTrue(this.boardReferenceDao.exists(this.boardReference));
	}

	@Test
	public void testUpdate() {
		//구현하지 않았음
	}

	@Test
	public void testRemove() {
		//구현하지 않았음
	}

	@Test
	public void testRemoveByItemId() {
		this.boardReferenceDao.removeByItemId(this.boardReference.getItemId());
		Assert.assertFalse( this.boardReferenceDao.exists(this.boardReference));
	}

}