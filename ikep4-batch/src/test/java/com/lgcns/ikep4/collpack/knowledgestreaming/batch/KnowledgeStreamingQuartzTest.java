/**
 * 
 */
package com.lgcns.ikep4.collpack.knowledgestreaming.batch;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lgcns.ikep4.support.quartz.model.BatchCronJobDetail;
import com.lgcns.ikep4.support.quartz.service.QuartzJobService;

/**
 * @author 주길재
 *
 */
@ContextConfiguration({ "classpath:/context-batch-dao.xml",
	"classpath:/context-batch-service.xml", "classpath:/context-batch-test.xml" })
public class KnowledgeStreamingQuartzTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private QuartzJobService quartzJobService;

	/**
	 * ScoreQuartz Job을 생성한다.
	 */
	@Ignore
	@Test
	@Rollback(false)
	public void create() {
		BatchCronJobDetail cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_KnowledgeStreamingTrigger");
		cronJobDetail.setJobName("BASIC_KnowledgeStreamingQuartz");
		cronJobDetail.setJobClass("com.lgcns.ikep4.collpack.knowledgestreaming.batch.KnowledgeStreamingQuartz");
		cronJobDetail.setCronExpression("0 43 14 * * ?");
		cronJobDetail.setDescription("지식 활용 수준 집계 데이터");

		quartzJobService.createJobCronTrigger(cronJobDetail);
	}

	/**
	 * ScoreQuartz Job을 삭제한다.
	 */
	@Ignore
	@Test
	@Rollback(false)
	public void delete() {
		quartzJobService.deleteJob("BASIC_qnaScoreBatch");
	}
}
