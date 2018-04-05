package com.lgcns.ikep4.support.message.batch;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.support.message.dao.MessageAdminDao;


/**
 * TODO 쪽지 BATCH 파일 만료 기한이 지난 쪽지 삭제
 * 
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageBatch.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class MessageBatch extends QuartzJobBean {

	private MessageAdminDao messageAdminDao;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			// Job Data Map에서ref-bean을 등록하지 못하므로 아래처럼 코드를 처리
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");

			this.messageAdminDao = (MessageAdminDao) appContext.getBean("messageAdminDaoImpl");

			this.messageAdminDao.removeBatch();
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

	}
}
