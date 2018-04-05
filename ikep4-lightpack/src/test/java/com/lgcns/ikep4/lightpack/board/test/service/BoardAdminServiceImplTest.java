/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.test.service;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lgcns.ikep4.lightpack.board.test.dao.BoardFixture;
import com.lgcns.ikep4.lightpack.board.dao.BoardDao;
import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.lightpack.board.service.BoardAdminService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * BoardAdminServiceImpl Test 클래스
 *
 * @author 최현식
 * @version $Id: BoardAdminServiceImplTest.java 16302 2011-08-19 08:43:50Z giljae $
 */
@Ignore
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"})
public class BoardAdminServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private BoardAdminService boardAdminService;

	@Autowired
	private BoardDao boardDao;

	@Autowired
	private IdgenService idgenService;

	private Board board;

	private Board child;

	private String boardId;

	@Before
	public void setUp() throws FileNotFoundException {
		this.board = BoardFixture.fixtureBoard(this.idgenService.getNextId());

		this.boardId = this.boardDao.create(this.board);

		this.child = BoardFixture.fixtureBoard(this.idgenService.getNextId());

		this.child.setBoardParentId(this.board.getBoardId());

		this.boardDao.create(this.child);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardAdminServiceImpl#checkSupportedImageMediaType(java.lang.String)}.
	 */
	@Test
	public void testCheckSupportedImageMediaType() {
		Assert.assertTrue(this.boardAdminService.checkSupportedImageMediaType("image/bmp"));
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardAdminServiceImpl#listByBoardRootId(java.lang.String)}.
	 */
	@Test
	public void testListByBoardRootId() {
		List<Board> boardList = this.boardAdminService.listByBoardRootId("0", "P000001");

		Assert.assertTrue(boardList.size() > 0);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardAdminServiceImpl#readBoard(java.lang.String)}.
	 */
	@Test
	public void testReadBoard() {
		Board result = this.boardAdminService.readBoard(this.board.getBoardId());
		Assert.assertNotNull(result);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardAdminServiceImpl#createBoard(com.lgcns.ikep4.lightpack.board.model.Board)}.
	 */
	@Test
	public void testCreateBoard() {
		Board create = BoardFixture.fixtureBoard(null);

		this.boardAdminService.createBoard(this.board);

		Board result = this.boardAdminService.readBoard(this.board.getBoardId());

		Assert.assertNotNull(result);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardAdminServiceImpl#updateBoard(com.lgcns.ikep4.lightpack.board.model.Board)}.
	 */
	@Test
	public void testUpdateBoard() {
		this.board.setBoardName("UPDATE-NAME");

		this.boardAdminService.updateBoard(this.board);

		Board result = this.boardAdminService.readBoard(this.board.getBoardId());

		Assert.assertEquals("UPDATE-NAME", this.board.getBoardName());
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardAdminServiceImpl#physicalDeleteBoard(java.lang.String)}.
	 */
	@Test
	public void testPhysicalDeleteBoard() {
		this.boardAdminService.physicalDeleteBoard(this.board.getBoardId());

		Board result = this.boardAdminService.readBoard(this.board.getBoardId());

		Assert.assertTrue(result.getBoardDelete() == 1);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardAdminServiceImpl#listParentBoard(java.lang.String)}.
	 */
	@Test
	public void testListParentBoard() {
		List<Board> boardList = this.boardAdminService.listParentBoard(this.child.getBoardId());

		Assert.assertTrue(boardList.size() == 2);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardAdminServiceImpl#updateBoardMove(com.lgcns.ikep4.lightpack.board.model.Board)}.
	 */
	@Test
	public void testUpdateBoardMove() {
		this.boardAdminService.updateBoardMove(this.board);

		Assert.assertTrue(true);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardAdminServiceImpl#readHasPermissionBoard(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testReadHasPermissionBoard() {
		Assert.assertTrue(true);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardAdminServiceImpl#listChildrenBoard(java.lang.String)}.
	 */
	@Test
	public void testListChildrenBoard() {
		List<Board> boardList = this.boardAdminService.listChildrenBoard(this.board.getBoardId());

		Assert.assertTrue(boardList.size() == 1);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.lightpack.board.service.impl.BoardAdminServiceImpl#updateBoardDeleteField(java.lang.String, java.lang.Integer)}.
	 */
	@Test
	public void testUpdateBoardDeleteField() {
		this.boardAdminService.updateBoardDeleteField(this.board.getBoardId(), Board.BOARD_DELETE_WAIT);

		Board result = this.boardAdminService.readBoard(this.board.getBoardId());

		Assert.assertTrue(result.getBoardDelete() == 1);
	}

}
