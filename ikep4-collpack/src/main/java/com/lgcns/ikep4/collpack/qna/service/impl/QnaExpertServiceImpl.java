/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.collpack.qna.constants.QnaConstants;
import com.lgcns.ikep4.collpack.qna.dao.QnaExpertDao;
import com.lgcns.ikep4.collpack.qna.model.Qna;
import com.lgcns.ikep4.collpack.qna.model.QnaExpert;
import com.lgcns.ikep4.collpack.qna.service.QnaExpertService;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.message.service.MessageOutsideService;
import com.lgcns.ikep4.support.sms.model.Sms;
import com.lgcns.ikep4.support.sms.service.SmsService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: QnaExpertServiceImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Service("qnaExpertService")
public class QnaExpertServiceImpl extends GenericServiceImpl<QnaExpert, String> implements QnaExpertService {


	@Autowired
	private QnaExpertDao qnaExpertDao;
	
	@Autowired
	private SmsService smsService;
	
	@Autowired
	private MessageOutsideService messageOutsideService;
	
	@Autowired
	private MailSendService mailSendService;
	
	@Autowired
	protected MessageSource messageSource = null;
	

	public void createByQna(Qna qna, User user) {
		
		//전문가 의뢰 있으면 전문가 등록 - 질문일때
		if(qna.getQnaType() == 0 && qna.getExpertIds() != null && qna.getExpertIds().length > 0){
			
			qnaExpertDao.removeByQnaId(qna.getQnaId());
			
			//메세지 보내기 내용
			String urgentMsg = "";
			if(qna.getUrgent() != null && qna.getUrgent() == 1){
				urgentMsg = getMessage("message.collpack.qna.expert.mail.urgent");
			}
			
			String contentsTitle = qna.getRegisterName()+getMessage("message.collpack.qna.expert.mail.contents");
			String contentsBody = urgentMsg+" "+qna.getTitle();
			
			StringBuffer massage = new StringBuffer();
			
			massage.append(contentsTitle);
			massage.append(contentsBody);
			
			StringBuffer mailMassage = new StringBuffer();
			
			mailMassage.append(contentsTitle);
			mailMassage.append("<br/><br/>질문을 확인하시고 답변등록 요청드립니다.<br/><br/><a href=\""+qna.getQnaPathUrl()+"/getQna.do?qnaId="+qna.getQnaId()+"\" target='_blank'>"+contentsBody+"</a>");
			
			String msgTitle = getMessage("message.collpack.qna.expert.mail.title");
			
			char[] channelCheck = qna.getExpertChannel().toCharArray();
			char mailChannel = channelCheck[0];
			//char smsChannel = channelCheck[1];
			//char messageChannel = channelCheck[2];
			//char mblogChannel = channelCheck[3];
			
			List mailList = new ArrayList();	//메일 맵을 list로 보내야 한다.
			String receiverIdes = "";
			
			for(String expertInfo : qna.getExpertIds()){
				
				String[] expertModel = expertInfo.split("[/]");
				
				String expertId = "";
				String expertName = "";
				String expertTel = "";
				String expertMail = "";
				
				try{expertId = expertModel[QnaConstants.EXPERT_INFO_ID].trim();} catch(Exception e){}
				try{expertName = expertModel[QnaConstants.EXPERT_INFO_NAME].trim();} catch(Exception e){}
				try{expertTel = expertModel[QnaConstants.EXPERT_INFO_TEL].trim();} catch(Exception e){}
				try{expertMail = expertModel[QnaConstants.EXPERT_INFO_MAI].trim();} catch(Exception e){}
				
				QnaExpert qnaExpert = new QnaExpert();
				qnaExpert.setExpertId(expertId);
				qnaExpert.setQnaId(qna.getQnaId());
				qnaExpert.setRequestChannel(qna.getExpertChannel());
				qnaExpert.setRegisterId(qna.getUpdaterId());
				qnaExpert.setRegisterName(qna.getRegisterName());
				
				qnaExpertDao.create(qnaExpert);
				
				/*//SMS보내기
				if (smsChannel=='1' && !StringUtil.isEmpty(expertTel)) {
					
					Sms sms = new Sms();

					sms.setReceiverId(expertId);
					sms.setReceiverPhoneno(expertTel);
					sms.setContents(massage.toString());
					sms.setRegisterId(qna.getRegisterId());
					sms.setRegisterName(qna.getRegisterName());	

					smsService.createLMS(sms);
				}*/
				
				if (mailChannel == '1' && !StringUtil.isEmpty(expertMail)) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("email", expertMail);
					map.put("name", expertName);
					
				   
					mailList.add(map);
					
					Mail mail = new Mail();
					
					Properties prop = PropertyLoader.loadProperties("/configuration/mail-conf.properties");
					String mailDomain = prop.getProperty("ikep4.support.mail.domain");
					
                    mail.setFromEmail("admin@"+mailDomain);
                    
                    mail.setToEmailList(mailList);

                    mail.setMailType(MailConstant.MAIL_TYPE_HTML);

                    mail.setTitle(msgTitle);
                    mail.setContent(mailMassage.toString());

                    Map dataMap = new HashMap();
                    
                    mailSendService.sendMail(mail, dataMap, user);
					
				}
				
				receiverIdes += expertId +",";
				
			}
			
			
			//메일보내기
		/*	if(mailList.size() > 0){
				sendMail(mailChannel, mailList, msgTitle, mailMassage, user );
			}*/
			
	/*		//쪽지보내기
			if (messageChannel=='1') {
				
				Message message = new Message();
				message.setSenderId(qna.getRegisterId());
				message.setSenderName(qna.getRegisterName());
				message.setContents(mailMassage.toString());
				
				message.setReceiverList(receiverIdes.substring(0, receiverIdes.length()-1));
				
				messageOutsideService.sendMessageOutside(message, user);
			}*/
			
			
		}
	}
	
	/**
	 * 메일 보내기 - 상단 로직 complexity 때문에 분리
	 * @param mailChannel
	 * @param mailList
	 * @param msgTitle
	 * @param mailMassage
	 * @param user
	 */
	private void sendMail(char mailChannel, List mailList, String msgTitle, StringBuffer mailMassage, User user ){
		if (mailChannel == '1') {
			
			Mail mail = new Mail();
			
			mail.setToEmailList(mailList);

			mail.setMailType(MailConstant.MAIL_TYPE_HTML);

			mail.setTitle(msgTitle);
			mail.setContent(mailMassage.toString());

			mailSendService.sendMail(mail, new HashMap(), user);

		}
	}
	
	
	public List<QnaExpert> list(String qnaId) {
		return qnaExpertDao.list(qnaId);
	}

	
	public QnaExpert read(String qnaId, String expertId) {
		return qnaExpertDao.getExpert(qnaId, expertId);
	}


	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#update(java.lang
	 * .Object)
	 */
	public void update(QnaExpert qnaExpert) {
		qnaExpertDao.update(qnaExpert);
	}


	public void delete(String qnaId, String expertId) {
		qnaExpertDao.removeExpert(qnaId, expertId);
		
	}


	public void removeQna(String qnaId) {
		qnaExpertDao.removeByQnaId(qnaId);
	}
	
	
	/**
	 * 리소스번들에서 메시지 값 조회
	 * 
	 * @param messageCode 리소스번들의 코드값
	 * @param messageParameters array of arguments that will be filled in for
	 *            params within the message (params look like "{0}", "{1,date}",
	 *            "{2,time}" within a message)
	 * @param defaultMessage 리소스번들 조회 실패시 기본 제공 메시지
	 * @return 코드값에 해당하는 리소스 값
	 */
	public String getMessage(String messageCode, Object[] messageParameters, String defaultMessage) {
		Locale locale = Locale.getDefault();
		return messageSource.getMessage(messageCode, messageParameters, defaultMessage, locale);
	}
	
	public String getMessage(String messageCode) {
		return getMessage(messageCode, null, null);
	}
	
}
