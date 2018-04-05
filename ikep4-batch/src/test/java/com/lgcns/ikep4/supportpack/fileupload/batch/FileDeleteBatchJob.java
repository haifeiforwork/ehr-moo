/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.supportpack.fileupload.batch;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lgcns.ikep4.support.quartz.model.BatchCronJobDetail;
import com.lgcns.ikep4.support.quartz.service.QuartzJobService;


/**
 * FileDeleteBatch 등록,수정,삭제
 * 
 * @author 주길재
 * @version $Id: FileDeleteBatchJob.java 17097 2011-12-14 02:35:36Z giljae $
 */
@ContextConfiguration({ "classpath:/context-batch-dao.xml",
	"classpath:/context-batch-service.xml", "classpath:/context-batch-test.xml" })
public class FileDeleteBatchJob extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private QuartzJobService quartzJobService;

	/**
	 * FileDeleteBatch Job을 생성한다.
	 */
	@Ignore
	@Test
	@Rollback(false)
	public void create() {
		BatchCronJobDetail cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_fileDeleteTrigger");
		cronJobDetail.setJobName("BASIC_fileDeleteBatch");
		cronJobDetail.setJobClass("com.lgcns.ikep4.support.fileupload.batch.FileDeleteBatch");
		cronJobDetail.setCronExpression("0 11 18 * * ?");
		cronJobDetail.setDescription("파일삭제 배치 프로그램");

		quartzJobService.createJobCronTrigger(cronJobDetail);
	}

	/**
	 * FileDeleteBatch Job을 삭제한다.
	 */
	@Ignore
	@Test
	@Rollback(false)
	public void delete() {
		quartzJobService.deleteJob("BASIC_fileDeleteBatch");
	}
}
