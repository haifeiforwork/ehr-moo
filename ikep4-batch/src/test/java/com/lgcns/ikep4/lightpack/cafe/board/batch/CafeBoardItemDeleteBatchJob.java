/**
 * 
 */
package com.lgcns.ikep4.lightpack.cafe.board.batch;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lgcns.ikep4.support.quartz.model.BatchCronJobDetail;
import com.lgcns.ikep4.support.quartz.service.QuartzJobService;

/**
 * CafeBoardItemDeleteBatchJob
 * @author 조경식
 *
 */
@ContextConfiguration({ "classpath:/context-batch-dao.xml",
	"classpath:/context-batch-service.xml", "classpath:/context-batch-test.xml" })
public class CafeBoardItemDeleteBatchJob extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private QuartzJobService quartzJobService;
	
	/**
	 * CafeBoardItemDeleteBatchJob Job을 삭제한다.
	 */
	@Ignore
	@Test
	@Rollback(false)
	public void delete() {
		quartzJobService.deleteJob("BASIC_cafeBoardItemDeleteBatch");
	}

	/**
	 * CafeBoardItemDeleteBatchJob Job을 생성한다.
	 */
	@Ignore
	@Test
	@Rollback(false)
	public void create() {
		BatchCronJobDetail cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_cafeBoardItemDeleteBatch");
		cronJobDetail.setJobName("BASIC_cafeBoardItemDeleteBatch");
		cronJobDetail.setJobClass("com.lgcns.ikep4.lightpack.cafe.board.batch.CafeBoardItemDeleteBatch");
		cronJobDetail.setCronExpression("0 3 19 * * ?");
		cronJobDetail.setDescription("영구 삭제된 카페 게시판, 삭제된 게시판 및 게시글, 댓글 삭제");
		
		quartzJobService.createJobCronTrigger(cronJobDetail);
	}
}
