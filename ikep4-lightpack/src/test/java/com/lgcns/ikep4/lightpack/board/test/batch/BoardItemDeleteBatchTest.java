/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.test.batch;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

//import com.lgcns.ikep4.lightpack.board.batch.BoardItemDeleteBatch;
import com.lgcns.ikep4.lightpack.board.service.BoardBatchService;
import com.lgcns.ikep4.lightpack.board.service.BoardItemService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;

/**
 * BoardItemDeleteBatch Test 클래스
 *
 * @author 최현식
 * @version $Id: BoardItemDeleteBatchTest.java 16302 2011-08-19 08:43:50Z giljae $
 */

@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"})
public class BoardItemDeleteBatchTest extends AbstractTransactionalJUnit4SpringContextTests {

//	private BoardItemDeleteBatch boardItemDeleteBatch;
//
//	@Autowired
//	private BoardBatchService boardBatchService;
//
//	@Autowired
//	private BoardItemService boardItemService;
//	
//	@Autowired
//	private ACLService aclService;
//
//
//	private JobExecutionContext context;
//
//
//	@Before
//	public void setUp() {
//		this.boardItemDeleteBatch = new BoardItemDeleteBatch();
//		this.boardItemDeleteBatch.setBoardBatchService(this.boardBatchService);
//		this.boardItemDeleteBatch.setBoardItemService(this.boardItemService);
//		this.boardItemDeleteBatch.setACLService(this.aclService);
//
//
//	}
//
//	@Test
//	public void test() throws ParseException, SchedulerException {
//
//		this.boardItemDeleteBatch.testExcute();
//		
//		// 삭제대상게시판
//		Assert.assertNull(this.boardBatchService.nextDeletedBoard());
//		
//		// 지난게시물이 있는지 여부
//		Assert.assertTrue(this.boardBatchService.listBatchDeletePassedBoardItem(1).size()==0);
//		
//		// 삭제대상 게시물이 있는지 여부(관리자 삭제 : item_delte=2)
//		Assert.assertTrue(this.boardBatchService.listBatchDeleteStatusBoardItem(1).size()==0);
//		
//		
//		
//
//	}
}
