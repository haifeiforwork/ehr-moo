/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.test.dao;

import static org.junit.Assert.assertNull;

import java.io.FileNotFoundException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.util.Log4jConfigurer;

import com.lgcns.ikep4.lightpack.board.dao.BoardDao;
import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * BoardDao Test 클래스
 *
 * @author 최현식
 * @version $Id: BoardDaoTest.java 16302 2011-08-19 08:43:50Z giljae $
 */
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"})
public class BoardDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private BoardDao boardDao;
	@Autowired
	private IdgenService idgenService;

	private Board board;

	private String boardId;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Log4jConfigurer.initLogging("classpath:configuration/log4j.xml");
	}

	@Before
	public void setUp() throws FileNotFoundException {
		final String generatedId = this.idgenService.getNextId();

		this.board = BoardFixture.fixtureBoard(generatedId);

		this.boardId = this.boardDao.create(this.board);

	}
	@Test
	public void testGet() {
		Board board = this.boardDao.get(this.boardId);
		Assert.assertNotNull(board);
	}
	@Test
	public void testExists() {
		boolean exists = this.boardDao.exists(this.boardId);

		Assert.assertTrue(exists);


	}
	@Test
	public void testCreate() {
		Board result = this.boardDao.get(this.boardId);

		Assert.assertNotNull(result);

	}
	@Test
	public void testUpdate() {
		this.board.setBoardName("updated-name");

		this.boardDao.update(this.board);

		Board result = this.boardDao.get(this.boardId);

		Assert.assertEquals(this.board.getBoardName(), result.getBoardName());
	}

	@Test
	public void testPhysicalDelete() {
		this.boardDao.physicalDelete(this.boardId);

		Board result = this.boardDao.get(this.boardId);

		assertNull(result);
	}

}
