package com.lgcns.ikep4.servicepack.survey.service;

import org.quartz.SchedulerException;

import com.lgcns.ikep4.servicepack.survey.model.SurveyJobDetailBean;
import com.lgcns.ikep4.servicepack.survey.util.TriggerException;
/**
 * 
 * 스케줄러 자동생성 
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: JobService.java 16244 2011-08-18 04:11:42Z giljae $
 */
public interface JobService {
	/**
	 * 스케줄잡등록
	 * @param job
	 * @throws Exception
	 */
	public void saveJob(SurveyJobDetailBean job)throws TriggerException,SchedulerException;
	
	/**
	 * 스케쥴잡제거
	 * @param job
	 * @throws Exception
	 */
	public void removeJob(SurveyJobDetailBean job)throws SchedulerException;
}
