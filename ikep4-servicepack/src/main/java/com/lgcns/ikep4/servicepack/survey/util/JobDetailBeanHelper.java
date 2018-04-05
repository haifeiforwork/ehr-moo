package com.lgcns.ikep4.servicepack.survey.util;

import org.quartz.Scheduler;
import org.springframework.scheduling.quartz.JobDetailBean;
import org.springframework.scheduling.quartz.SimpleTriggerBean;

import com.lgcns.ikep4.servicepack.survey.model.SurveyJobDetailBean;
/**
 * 
 * job생성
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: JobDetailBeanHelper.java 16244 2011-08-18 04:11:42Z giljae $
 */
public final class JobDetailBeanHelper{
	private JobDetailBeanHelper() {
	}
	
	/**
	 * 설문 메일 발송시 simpejob 생성
	 * @param jobDetailBean
	 * @return
	 * @throws TriggerException
	 */
	public static SimpleTriggerBean createJob(SurveyJobDetailBean jobDetailBean) throws TriggerException{
		try
		{
			JobDetailBean jobDetail = new JobDetailBean();
			jobDetail.setJobClass( ReflectionUtils.newInstance(jobDetailBean.getJobClassName()).getClass() );
			jobDetail.setBeanName( jobDetailBean.getJobName() );
			jobDetail.setName( jobDetailBean.getJobName() );
			jobDetail.setDescription( jobDetailBean.getDescription() );
			jobDetail.setGroup(  Scheduler.DEFAULT_GROUP );
			jobDetail.setJobDataAsMap( jobDetailBean.getJobDataAsMap() );
			jobDetail.afterPropertiesSet();

			SimpleTriggerBean trigger = new SimpleTriggerBean();
			trigger.setBeanName(jobDetailBean.getTriggerName());
			trigger.setJobDetail(jobDetail);
			trigger.setGroup( Scheduler.DEFAULT_GROUP );
			trigger.setStartDelay(1);
			trigger.setRepeatInterval(jobDetailBean.getRepeatInterval());
			trigger.setStartTime(jobDetailBean.getStartTime());
			
			//한번만 돌도록 셋팅은 -1
			trigger.setPriority( jobDetailBean.getPriority() );
			//한번만 수행
			trigger.setRepeatCount(jobDetailBean.getRepeatCount() );
			
			trigger.afterPropertiesSet();
			
			return trigger;
		}
		catch(Exception e)
		{
			throw new TriggerException(e);
		}
	}
}
