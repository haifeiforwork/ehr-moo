/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.survey.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.servicepack.survey.model.RequestLog;
import com.lgcns.ikep4.servicepack.survey.model.SendLog;
import com.lgcns.ikep4.servicepack.survey.model.Survey;
import com.lgcns.ikep4.servicepack.survey.service.RequestLogService;
import com.lgcns.ikep4.servicepack.survey.service.SendLogService;
import com.lgcns.ikep4.servicepack.survey.service.SurveyService;



/**
 * mail 배치
 * 
 * @author 고인호(ihko11@daum.net)
 * @version $Id: SurveyScheduled.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Component
public class SurveyScheduled extends QuartzJobBean {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private SurveyService  surveyService;
	
	@Autowired
	private MailSendService mailSendService;
	
	@Autowired
	private SendLogService sendLogService;

	@Autowired
	private RequestLogService requestLogService;
	

	/**
	 * 실행 메소드
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try
		{
			Map<String,Object> dataMap = (Map<String,Object>)context.getJobDetail().getJobDataMap();
			
			String surveyId = (String)dataMap.get("surveyId");
			User user = (User)dataMap.get("user");
			Integer sendSeq = (Integer)dataMap.get("sendSeq");
			String url = (String)dataMap.get("url");
			String sendMailOption = (String)dataMap.get("sendMailOption");
			
			int sendSuccessCount=0;
			
			Survey dbInfo = surveyService.read( surveyId ); 
			
			String comment = surveyService.readComment( surveyId ); 
			
			dbInfo.setSendComment(comment);
			
			List<SendLog> sendLogList = new ArrayList<SendLog>();
			
			SendLog searchSendLog = new SendLog();
			searchSendLog.setSurveyId(surveyId);
			searchSendLog.setPortalId(user.getPortalId());
			
			//미참여자 메일
			if( sendMailOption.equals("NOT") )
			{
				if( dbInfo.getSurveyTarget().intValue() == 0 ){ //전사
					sendLogList = sendLogService.getSendNotMailListAllByUser(searchSendLog);
				}else{	//선택
					sendLogList = sendLogService.getSendNotMail( surveyId );
			
				}
			}	
			else //open시 메일
			{
				if( dbInfo.getSurveyTarget().intValue() == 0 ){ //전사
					sendLogList = sendLogService.getSendMailListAllByUser(searchSendLog);
				}else{	//선택
					sendLogList = sendLogService.getSendMailListAllBySurveyId( surveyId );
				}	
			}

			for (int j = 0; j < sendLogList.size(); j++) {
				SendLog sendLog = (SendLog)sendLogList.get(j);
				/*sendLog.setSendSeq( j );
				sendLog.setSurveyId( surveyId );
				
				SendLogKey sendLogKey = new SendLogKey( sendLog.getSurveyId(),sendLog.getReceiverId() );
				
				if( sendLogService.exists( sendLogKey ) )
					sendLogService.update(sendLog);
				else
					sendLogService.create(sendLog);*/
				
				try
				{
					sendTamplet(sendLog,dbInfo,user,url);
					sendSuccessCount++;
				}
				catch(Exception e)
				{
					log.error(e.getMessage());
				}
			}
			
			RequestLog  requestLog = new RequestLog();
			requestLog.setSurveyId(surveyId);
			requestLog.setSendSeq(sendSeq);
			requestLog.setSendSuccessCount(sendSuccessCount);
			requestLog.setSendEndDate(new Date());
			
			requestLogService.update(requestLog);
		}
		catch(Exception e){
			log.error(e.getMessage());
		}
		
	}
	
	/**
	 * 실질적인 메일 보내는 로직
	 * @param sendLogList
	 * @param survey
	 * @param request
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void sendTamplet(SendLog sendLog,Survey survey,User user,String url)throws JobExecutionException{
		HashMap<String, String> map = new HashMap<String, String>();
		List<HashMap> list = new ArrayList<HashMap>();
		map.put("email", sendLog.getReceiverMail() );
		map.put("name", sendLog.getReceiverName() );
		list.add(map);
		String contentsTemp = survey.getContents().replace( "\r\n", "<br/>" );
		survey.setContents( contentsTemp );
	
		Mail mail = new Mail();
		//mail.setUserId("ikepuser1");
		//mail.setUserPwd("1q2w3e4r!@");
		mail.setToEmailList(list);
		
		
		//mail.setTitle( survey.getTitle() );
		//mail.setTitle(survey.getTitle());
		//MessageBundle 사용 필요...
		mail.setTitle("[설문 참여] " + survey.getTitle());
		//System.out.println("===============>>>>>>>" + survey.getTitle());
		//System.out.println("===============<<<<<<<" + mail.getTitle());
		
		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		if(user.getLocaleCode().toUpperCase().equals("KO")) {
			mail.setMailTemplatePath("surveyMail.vm");
		} else {
			mail.setMailTemplatePath("surveyEnglishMail.vm");			
		}
		Map dataMap = new HashMap();
		dataMap.put("sendLog",sendLog );
		dataMap.put("survey", survey);
		dataMap.put("url", url );
		dataMap.put("user", user );
		
		String reStr = mailSendService.sendMail(mail, dataMap,user);
		
		log.debug("-----------------------mail----------------" + reStr);
	}
	
	

	public void setSurveyService(SurveyService surveyService) {
		this.surveyService = surveyService;
	}



	public void setMailSendService(MailSendService mailSendService) {
		this.mailSendService = mailSendService;
	}



	public void setSendLogService(SendLogService sendLogService) {
		this.sendLogService = sendLogService;
	}


	public void setRequestLogService(RequestLogService requestLogService) {
		this.requestLogService = requestLogService;
	}
	
}
