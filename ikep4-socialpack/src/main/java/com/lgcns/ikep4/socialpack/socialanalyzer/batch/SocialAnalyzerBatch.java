package com.lgcns.ikep4.socialpack.socialanalyzer.batch;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.socialpack.socialanalyzer.service.RelationService;
import com.lgcns.ikep4.socialpack.socialanalyzer.service.SocialityService;


/**
 * TODO Javadoc주석작성
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: SocialAnalyzerBatch.java 16246 2011-08-18 04:48:28Z giljae $
 */
public class SocialAnalyzerBatch extends QuartzJobBean {

	private SocialityService socialityService;

	private RelationService relationService;

	//
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			// Job Data Map에서ref-bean을 등록하지 못하므로 아래처럼 코드를 처리
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");

			this.socialityService = (SocialityService) appContext.getBean("socialityServiceImpl");

			this.relationService = (RelationService) appContext.getBean("relationServiceImpl");

			// 배치 SP_SOCIALITY
			socialityService.batchSociality();
			// 배치 SP_RELATION
			relationService.batchRelation();
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

}
