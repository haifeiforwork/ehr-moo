package com.lgcns.ikep4.servicepack.survey.service.impl;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerBean;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.servicepack.survey.model.SurveyJobDetailBean;
import com.lgcns.ikep4.servicepack.survey.service.JobService;
import com.lgcns.ikep4.servicepack.survey.util.JobDetailBeanHelper;
import com.lgcns.ikep4.servicepack.survey.util.TriggerException;
/**
 * 
 * 스케쥴러 자동생성
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: JobServiceImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Service
public class JobServiceImpl implements JobService {

	@Autowired
	@Qualifier(value="surveyScheduler")
	private SchedulerFactoryBean  surveyScheduler;
	
	/**
	 * 스케쥴러등록
	 * @throws TriggerException 
	 * @throws SchedulerException 
	 */
	public void saveJob(SurveyJobDetailBean job) throws TriggerException, SchedulerException  {
			Scheduler sched= surveyScheduler.getScheduler();
		    SimpleTriggerBean triger = (SimpleTriggerBean)JobDetailBeanHelper.createJob(job);
			sched.addJob(triger.getJobDetail(), true);
			sched.scheduleJob(triger);
	}

	/**
	 * 스케쥴러 삭제
	 * @throws SchedulerException 
	 */
	public void removeJob(SurveyJobDetailBean job) throws SchedulerException  {
		Scheduler sched= surveyScheduler.getScheduler();
		sched.deleteJob(job.getJobName(), Scheduler.DEFAULT_GROUP);
	}
}
