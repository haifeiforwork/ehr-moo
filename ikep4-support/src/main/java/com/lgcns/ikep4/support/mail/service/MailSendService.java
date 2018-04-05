package com.lgcns.ikep4.support.mail.service;

import java.util.Map;

import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 메일보내기 처리 클래스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: MailSendService.java 17315 2012-02-08 04:56:13Z yruyo $
 */
public interface MailSendService {

	/**
	 * 메일 보내기 SMTP 프로토콜을 이용하여 메일을 보냄
	 * @param dataMap
	 * @param user TODO
	 * @param mailVO
	 * 
	 * @return @
	 */
	@SuppressWarnings("rawtypes")
	public String sendMailOld(Mail mail, Map dataMap, User user);
	
	
	/**
	 * 메일 보내기 SMTP 프로토콜을 이용하여 메일을 보냄
	 * @param dataMap
	 * @param user TODO
	 * @param mailVO
	 * 
	 * @return @
	 */
	@SuppressWarnings("rawtypes")
	public String sendMail(Mail mail, Map dataMap, User user);
	
	@SuppressWarnings("rawtypes")
	public String sendMailNotice(Mail mail, Map dataMap, User user);

}
