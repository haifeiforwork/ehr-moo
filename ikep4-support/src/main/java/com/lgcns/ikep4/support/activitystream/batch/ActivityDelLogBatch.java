/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.activitystream.batch;

import java.util.List;
import java.util.Properties;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.support.activitystream.model.ActivityCode;
import com.lgcns.ikep4.support.activitystream.model.ActivityDelLog;
import com.lgcns.ikep4.support.activitystream.service.ActivityCodeService;
import com.lgcns.ikep4.support.activitystream.service.ActivityDelLogService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 파일 삭제처리 배치 처리 클래스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ActivityDelLogBatch.java 16316 2011-08-22 00:26:53Z giljae $
 */
public class ActivityDelLogBatch extends QuartzJobBean {
	/**
	 * Activity delete log 서비스
	 */
	private ActivityDelLogService activityDelLogService;

	/**
	 * Activity Code 서비스
	 */
	private ActivityCodeService activityCodeService;

	/**
	 * Activity Stream 삭제 배치
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		try {
			// Job Data Map에서ref-bean을 등록하지 못하므로 아래처럼 코드를 처리
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");

			this.activityDelLogService = (ActivityDelLogService) appContext.getBean("activityDelLogServiceImpl");

			this.activityCodeService = (ActivityCodeService) appContext.getBean("activityCodeServiceImpl");

			int deleteCount = 0;

			Properties prop = PropertyLoader.loadProperties("/configuration/activitystream-conf.properties");
			String defaultMaxSaveValue = prop.getProperty("ikep4.support.activitystream.maxSaveValue");

			ActivityCode activityCode = new ActivityCode();
			List<ActivityCode> activityCodeList = activityCodeService.selectConfigList(activityCode);

			for (ActivityCode object : activityCodeList) {

				ActivityDelLog activityDelLog = new ActivityDelLog();
				activityDelLog.setUserId(object.getUserId());
				if (StringUtil.isEmpty(object.getMaxSaveValue())) {
					activityDelLog.setConfigValue(Integer.parseInt(defaultMaxSaveValue));
				} else {
					activityDelLog.setConfigValue(Integer.parseInt(object.getMaxSaveValue()));
				}

				try {
					deleteCount = activityDelLogService.deleteBatch(activityDelLog);
					activityDelLog.setSuccess(1);
					activityDelLog.setDeleteCount(deleteCount);

				} catch (Exception e) {
					activityDelLog.setSuccess(0);
					activityDelLog.setDeleteCount(0);
					// e.printStackTrace();
				}

				activityDelLogService.create(activityDelLog);
			}

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

}
