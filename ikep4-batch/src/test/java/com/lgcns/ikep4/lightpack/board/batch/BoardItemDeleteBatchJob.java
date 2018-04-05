/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.batch;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lgcns.ikep4.support.quartz.model.BatchCronJobDetail;
import com.lgcns.ikep4.support.quartz.service.QuartzJobService;


/**
 * BoardItemDeleteBatch Job
 * 
 * @author 주길재
 * @version $Id: BoardItemDeleteBatchJob.java 17097 2011-12-14 02:35:36Z giljae $
 */
@ContextConfiguration({ "classpath:/context-batch-dao.xml",
	"classpath:/context-batch-service.xml", "classpath:/context-batch-test.xml" })
public class BoardItemDeleteBatchJob extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private QuartzJobService quartzJobService;

	/**
	 * BoardItemDeleteBatch Job을 삭제한다.
	 */
	@Ignore
	@Test
	@Rollback(false)
	public void delete() {
		quartzJobService.deleteJob("BASIC_boardItemDeleteBatch");
	}
	
	/**
	 * BoardItemDeleteBatch Job을 생성한다.
	 */
	@Ignore
	@Test
	@Rollback(false)
	public void create() {
		BatchCronJobDetail cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_boardItemDeleteBatchTrigger");
		cronJobDetail.setJobName("BASIC_boardItemDeleteBatch");
		cronJobDetail.setJobClass("com.lgcns.ikep4.lightpack.board.batch.BoardItemDeleteBatch");
		cronJobDetail.setCronExpression("0 0 2 * * ?");
		cronJobDetail.setDescription("삭제 대기 상태인 게시판과 게시판에 속해 있는 게시물, 게시기간이 지난 게시물, 댓글 삭제");
		
		quartzJobService.createJobCronTrigger(cronJobDetail);
	}
	
	/**
	 * BoardItemDeleteBatch Job을 중지한다.
	 *
	 */
	@Ignore
	@Test
	@Rollback(false)
	public void pauseJob() {
		quartzJobService.pauseJob("BASIC_boardItemDeleteBatch");
	}
	
	/**
	 * BoardItemDeleteBatch Job을 재개한다.
	 *
	 */
	@Ignore
	@Test
	@Rollback(false)
	public void resumeJob() {
		quartzJobService.resumeJob("BASIC_boardItemDeleteBatch");
	}
	
	@Ignore
	@Test
	@Rollback(false)
	public void pauseAll() {
		quartzJobService.pauseAllJob();
	}
}
