/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.batch;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lgcns.ikep4.support.quartz.model.BatchSimpleJobDetail;
import com.lgcns.ikep4.support.quartz.service.QuartzJobService;


/**
 * NotiSchedule Job
 * 
 * @author 주길재
 * @version $Id: NotiScheduleJob.java 17097 2011-12-14 02:35:36Z giljae $
 */
@ContextConfiguration({ "classpath:/context-batch-dao.xml",
	"classpath:/context-batch-service.xml", "classpath:/context-batch-test.xml" })
public class NotiScheduleJob extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private QuartzJobService quartzJobService;

	/**
	 * NotiSchedule Job을 생성한다.
	 */
	@Ignore
	@Test
	@Rollback(false)
	public void create() {
		BatchSimpleJobDetail simpleJobDetail = new BatchSimpleJobDetail();
		simpleJobDetail.setTriggerName("BASIC_alarmScheduleTrigger");
		simpleJobDetail.setJobName("BASIC_alarmScheduleJob");
		simpleJobDetail.setJobClass("com.lgcns.ikep4.lightpack.planner.batch.NotiSchedule");
		simpleJobDetail.setRepeatInterval(300000);
		simpleJobDetail.setRepeatCount(-1);
		simpleJobDetail.setDescription("일정 알람 서비스(메일,쪽지,SMS)");
		
		quartzJobService.createJobSimpleTrigger(simpleJobDetail);
	}

	/**
	 * NotiSchedule Job을 삭제한다.
	 */
	@Ignore
	@Test
	@Rollback(false)
	public void delete() {
		quartzJobService.deleteJob("BASIC_alarmScheduleJob");
	}
}
