package com.lgcns.ikep4.portal.portlet.batch;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.portal.portlet.service.WeatherInfoService;


/**
 * 날씨 포틀릿 배치
 * 
 * @author 임종상
 * @version $Id: PortletWeatherBatch.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortletWeatherBatch extends QuartzJobBean { // QuartzJobBean 을 상속
															// 받아야 한다.

	protected final Log log = LogFactory.getLog(this.getClass());

	private WeatherInfoService weatherInfoService;

	// 스케쥴러에 의해 실행될 부분
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		// 반복할 작업 구현

		this.log.debug("[" + new Date() + "]");
		this.log.debug("\r\n 날씨 정보 배치 시작 ----------------------------------------------------");

		try {
			// Job Data Map에서ref-bean을 등록하지 못하므로 아래처럼 코드를 처리
			SchedulerContext schedulerContext = jobExecutionContext.getScheduler().getContext();
			ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");

			this.weatherInfoService = (WeatherInfoService) appContext.getBean("weatherInfoService");

			this.weatherInfoService.createWeatherInfo();
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}
}
