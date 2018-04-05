/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.batch;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lgcns.ikep4.support.quartz.service.QuartzJobService;

/**
 * Batch Pause And Resume
 *
 * @author 주길재
 * @version $Id: BatchPauseAndResume.java 17057 2011-12-09 01:25:49Z giljae $
 */
@ContextConfiguration({ "classpath:/context-batch-dao.xml",
	"classpath:/context-batch-service.xml", "classpath:/context-batch-test.xml" })
public class BatchPauseAndResume extends AbstractTransactionalJUnit4SpringContextTests  {
	@Autowired
	private QuartzJobService quartzJobService;
	
	/**
	 * 전체 배치 작업 중지
	 *
	 */
	@Ignore
	@Test
	@Rollback(false)
	public void pauseAllJob() {
		quartzJobService.pauseAllJob();
	}
	
	/**
	 * 전체 배치 작업 재개
	 *
	 */
	@Ignore
	@Test
	@Rollback(false)
	public void resumeAllJob() {
		quartzJobService.resumeAllJob();
	}
	
	/**
	 * 부분 배치 작업 중지
	 *
	 */
	@Ignore
	@Test
	@Rollback(false)
	public void pauseJob() {
		quartzJobService.pauseJob("잡이름 입력");
	}
	
	/**
	 * 부분 배치 작업 재개
	 *
	 */
	@Ignore
	@Test
	@Rollback(false)
	public void resumeJob() {
		quartzJobService.resumeJob("잡이름 입력");
	}
}
