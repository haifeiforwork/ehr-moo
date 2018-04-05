package com.lgcns.ikep4.servicepack.seamless.batch;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.servicepack.seamless.dao.SeamlessmessageAdminDao;


/**
 * TODO 통합메세지 BATCH 파일 만료 기한이 지난메일 삭제
 * 
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: SeamlessMessageBatch.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class SeamlessMessageBatch extends QuartzJobBean {

	private SeamlessmessageAdminDao seamlessmessageAdminDao;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			// Job Data Map에서ref-bean을 등록하지 못하므로 아래처럼 코드를 처리
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");

			this.seamlessmessageAdminDao = (SeamlessmessageAdminDao) appContext.getBean("seamlessmessageAdminDaoImpl");

			this.seamlessmessageAdminDao.removeBatch();
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

}
