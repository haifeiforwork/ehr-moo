/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.batch;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lgcns.ikep4.support.quartz.model.BatchCronJobDetail;
import com.lgcns.ikep4.support.quartz.service.QuartzJobService;


/**
 * ScoreQuartz Job
 * 
 * @author 주길재
 * @version $Id: ScoreQuartzJob.java 17097 2011-12-14 02:35:36Z giljae $
 */
@ContextConfiguration({ "classpath:/context-batch-dao.xml",
	"classpath:/context-batch-service.xml", "classpath:/context-batch-test.xml" })
public class ScoreQuartzJob extends AbstractTransactionalJUnit4SpringContextTests {
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
		cronJobDetail.setTriggerName("BASIC_qnaScoreCronTrigger");
		cronJobDetail.setJobName("BASIC_qnaScoreBatch");
		cronJobDetail.setJobClass("com.lgcns.ikep4.collpack.qna.batch.ScoreQuartz");
		cronJobDetail.setCronExpression("0 0 1 * * ?");
		cronJobDetail.setDescription("Q&A score저장, best 질문, 답변 베스트 선정");

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