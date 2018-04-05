package com.lgcns.ikep4.util.mail.service.impl;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.MimeUtility;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.mail.MailConstant;
import com.lgcns.ikep4.util.mail.base.MailReadBase;
import com.lgcns.ikep4.util.mail.model.Mail;
import com.lgcns.ikep4.util.mail.model.MailAttach;
import com.lgcns.ikep4.util.mail.service.SendService;
import com.lgcns.ikep4.util.user.member.model.User;


/**
 * 메일보내기 처리 클래스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: MailSendServiceImpl.java 17315 2012-02-08 04:56:13Z yruyo $
 */
@Service("sendService")
public class SendServiceImpl extends MailReadBase implements SendService {

	/**
	 * 템플릿 메일 변환용 벨로시티 엔진 서비스
	 */
	@Autowired
	VelocityEngine velocityEngine;

	/**
	 * 메일 발송
	 */
	@SuppressWarnings("rawtypes")
	public String sendMail(Mail mail, Map dataMap, User user) {

		String reStr = "";
		String mailContent = "";

		try {
			
            Properties prop = PropertyLoader.loadProperties("/configuration/mail-conf.properties");
			
			// 수신자가 있은 경우에 메일 발송
			if (mail.getToEmailList() != null && mail.getToEmailList().size() > 0) {
				
				String server = prop.getProperty("ikep4.support.mail.externalserver");	
				URL url = new URL(server + "/mail/mailPortlet.do");
		        
				URLConnection urlConnection = url.openConnection();
				HttpURLConnection httpConnection = (HttpURLConnection)urlConnection;
				httpConnection.setDoOutput(true);
				httpConnection.setRequestProperty("Accept-Language",  "ko-kr,ko;q=0.8,en-us;q=0.5,en;q=0.3");

				StringBuffer parameter = new StringBuffer();
				parameter.append("mode=send");
				parameter.append("&vtype=json");
				
				// 발신자 설정				
				parameter.append("&sender=");		
				
				if(StringUtil.isEmpty(mail.getFromEmail())){
					parameter.append(user.getMail());
				}else{
					parameter.append(mail.getFromEmail());
				}
				
				//수신자 설정
				StringBuffer to = new StringBuffer();		
				StringBuffer cc = new StringBuffer();		
				StringBuffer bcc = new StringBuffer();		
				

				// 수신자 목록 설정
				for (HashMap<String, String> toEmail : mail.getToEmailList()) {										
					to.append((String) toEmail.get("email"));
					//to.append("admin@eptest.co.kr");
					to.append(",");
				}

				// 참조자 목록 설정
				if (mail.getCcEmailList() != null) {
					for (HashMap<String, String> ccEmail : mail.getCcEmailList()) {
						cc.append((String) ccEmail.get("email"));
						cc.append(",");						
					}
				}

				// 비밀참조자 목록 설정
				if (mail.getBccEmailList() != null) {
					for (HashMap<String, String> bccEmail : mail.getBccEmailList()) {
						bcc.append((String) bccEmail.get("email"));
						bcc.append(",");
					}
				}
				
				if(to.length() > 0)
				{
					parameter.append("&to=");
					parameter.append(to);
				}
				
				if(cc.length() > 0)
				{
					parameter.append("&cc=");
					parameter.append(cc);
				}
				
				if(bcc.length() > 0)
				{
					parameter.append("&bcc=");
					parameter.append(bcc);
				}
				
				// 제목 설정
				parameter.append("&subject=");
				parameter.append(URLEncoder.encode(mail.getTitle(), "UTF-8"));
				
				//본문 설정
				parameter.append("&contents=");
				
				// Templage 메일인 경우
				// 시스템 자동메일인 경우는 HTML양식을 읽어와서 메일을 보냄
				if (mail.getMailType().equals(MailConstant.MAIL_TYPE_TEPLATE)) {
					mailContent = getMailTemplateForVelocity(mail, dataMap);
					
				}
				// Html 메일인 경우
				else if (mail.getMailType().equals(MailConstant.MAIL_TYPE_HTML)) {
					mailContent = mail.getContent();
					//email.setHtmlMsg(mailContent);
				}
				// Text 메일인 경우
				else {
					mailContent = mail.getContent();
					//email.setTextMsg(mailContent);
				}
				
				parameter.append(URLEncoder.encode(mailContent, "UTF-8"));

				OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
				wr.write(parameter.toString());
				wr.flush();
				
				int responseCode = httpConnection.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					
					reStr = "success";
				}
				else
				{
					reStr = "fail";
				}				
			}

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return reStr;

	}
	
	@SuppressWarnings("rawtypes")
	public String sendMailSimple(Mail mail, Map dataMap, User user) {

		String reStr = "";
		String mailContent = "";

		try {
			
            Properties prop = PropertyLoader.loadProperties("/configuration/mail-conf.properties");
			
			// 수신자가 있은 경우에 메일 발송
			if (mail.getToEmail() != null && mail.getToEmail() != "") {
				
				String server = prop.getProperty("ikep4.support.mail.externalserver");	
				URL url = new URL(server + "/mail/mailPortlet.do");
		        
				URLConnection urlConnection = url.openConnection();
				HttpURLConnection httpConnection = (HttpURLConnection)urlConnection;
				httpConnection.setDoOutput(true);
				httpConnection.setRequestProperty("Accept-Language",  "ko-kr,ko;q=0.8,en-us;q=0.5,en;q=0.3");

				StringBuffer parameter = new StringBuffer();
				parameter.append("mode=send");
				parameter.append("&vtype=json");
				
				// 발신자 설정				
				parameter.append("&sender=");		
				
				if(StringUtil.isEmpty(mail.getFromEmail())){
					parameter.append(user.getMail());
				}else{
					parameter.append(mail.getFromEmail());
				}
				
				//수신자 설정
				StringBuffer to = new StringBuffer();		
				

				// 수신자 목록 설정
					to.append(mail.getToEmail());
					to.append(",");

				if(to.length() > 0)
				{
					parameter.append("&to=");
					parameter.append(to);
				}
				
				
				// 제목 설정
				parameter.append("&subject=");
				parameter.append(URLEncoder.encode(mail.getTitle(), "UTF-8"));
				
				//본문 설정
				parameter.append("&contents=");
				
				// Templage 메일인 경우
				// 시스템 자동메일인 경우는 HTML양식을 읽어와서 메일을 보냄
				if (mail.getMailType().equals(MailConstant.MAIL_TYPE_TEPLATE)) {
					mailContent = getMailTemplateForVelocity(mail, dataMap);
					
				}
				// Html 메일인 경우
				else if (mail.getMailType().equals(MailConstant.MAIL_TYPE_HTML)) {
					mailContent = mail.getContent();
					//email.setHtmlMsg(mailContent);
				}
				// Text 메일인 경우
				else {
					mailContent = mail.getContent();
					//email.setTextMsg(mailContent);
				}
				
				parameter.append(URLEncoder.encode(mailContent, "UTF-8"));

				OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
				wr.write(parameter.toString());
				wr.flush();
				
				int responseCode = httpConnection.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					
					reStr = "success";
				}
				else
				{
					reStr = "fail";
				}				
			}

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return reStr;

	}

	/**
	 * 독서자 메일 발송
	 */
	@SuppressWarnings("rawtypes")
	public String sendMailNotice(Mail mail, Map dataMap, User user) {

		String reStr = "";
		String mailContent = "";

		try {
			
            Properties prop = PropertyLoader.loadProperties("/configuration/mail-conf.properties");
			
			// 수신자가 있은 경우에 메일 발송
			if (mail.getToEmailList() != null && mail.getToEmailList().size() > 0) {
				
				String server = prop.getProperty("ikep4.support.mail.externalserver");	
				URL url = new URL(server + "/mail/mailPortlet.do");
		        
				URLConnection urlConnection = url.openConnection();
				HttpURLConnection httpConnection = (HttpURLConnection)urlConnection;
				httpConnection.setDoOutput(true);
				httpConnection.setRequestProperty("Accept-Language",  "ko-kr,ko;q=0.8,en-us;q=0.5,en;q=0.3");

				StringBuffer parameter = new StringBuffer();
				parameter.append("mode=send");
				parameter.append("&vtype=json");
				
				// 발신자 설정				
				parameter.append("&sender=");		
				
				if(StringUtil.isEmpty(mail.getFromEmail())){
					parameter.append(user.getMail());
				}else{
					parameter.append(mail.getFromEmail());
				}
				
				//수신자 설정
				StringBuffer to = new StringBuffer();		
				StringBuffer cc = new StringBuffer();		
				StringBuffer bcc = new StringBuffer();		
				

				// 수신자 목록 설정
				for (HashMap<String, String> toEmail : mail.getToEmailList()) {										
					to.append((String) toEmail.get("email"));
					//to.append("admin@eptest.co.kr");
					to.append(",");
				}

				// 참조자 목록 설정
				if (mail.getCcEmailList() != null) {
					for (HashMap<String, String> ccEmail : mail.getCcEmailList()) {
						cc.append((String) ccEmail.get("email"));
						cc.append(",");						
					}
				}

				// 비밀참조자 목록 설정
				if (mail.getBccEmailList() != null) {
					for (HashMap<String, String> bccEmail : mail.getBccEmailList()) {
						bcc.append((String) bccEmail.get("email"));
						bcc.append(",");
					}
				}
				
				if(to.length() > 0)
				{
					parameter.append("&to=");
					parameter.append(to);
				}
				
				if(cc.length() > 0)
				{
					parameter.append("&cc=");
					parameter.append(cc);
				}
				
				if(bcc.length() > 0)
				{
					parameter.append("&bcc=");
					parameter.append(bcc);
				}
				
				// 제목 설정
				parameter.append("&subject=");
				parameter.append(URLEncoder.encode(mail.getTitle(), "UTF-8"));
				
				//본문 설정
				parameter.append("&contents=");
				
				// Templage 메일인 경우
				// 시스템 자동메일인 경우는 HTML양식을 읽어와서 메일을 보냄
				if (mail.getMailType().equals(MailConstant.MAIL_TYPE_TEPLATE)) {
					mailContent = getMailTemplateForVelocity(mail, dataMap);
					
				}
				// Html 메일인 경우
				else if (mail.getMailType().equals(MailConstant.MAIL_TYPE_HTML)) {
					mailContent = mail.getContent();
					//email.setHtmlMsg(mailContent);
				}
				// Text 메일인 경우
				else {
					mailContent = mail.getContent();
					//email.setTextMsg(mailContent);
				}
				
				parameter.append(URLEncoder.encode(mailContent, "UTF-8"));

				parameter.append("&readernotice=1");
				
				OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
				wr.write(parameter.toString());
				wr.flush();
				
				int responseCode = httpConnection.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					
					reStr = "success";
				}
				else
				{
					reStr = "fail";
				}				
			}

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return reStr;

	}
	
	/**
	 * 메일 발송
	 */
	@SuppressWarnings("rawtypes")
	public String sendMailOld(Mail mail, Map dataMap, User user) {

		String reStr = "";
		String mailContent = "";

		try {
			HtmlEmail email = new HtmlEmail();

			// 메일 초기 정보 셋팅(보내는사람 계정, 메일 서버 주소 등등)
			setMailCommon(user, mail, "smtp");

			email.setHostName(mail.getServer());
			email.setAuthentication(mail.getUserId(), mail.getUserPwd());
			email.setCharset("UTF-8");

			// SSL을 사용할 경우
			if (mail.isSsl()) {
				email.setSslSmtpPort(String.valueOf(mail.getPort()));
				email.setSSL(true);
			}
			// SSL을 사용하지 않을 경우
			else {
				email.setSmtpPort(mail.getPort());
			}

			// 발신자 설정
			email.setFrom(mail.getFromEmail(), mail.getFromName());

			// 수신자가 있은 경우에 메일 발송
			if (mail.getToEmailList() != null && mail.getToEmailList().size() > 0) {

				// 수신자 목록 설정
				for (HashMap<String, String> toEmail : mail.getToEmailList()) {
					email.addTo((String) toEmail.get("email"), (String) toEmail.get("name"));
				}

				// 참조자 목록 설정
				if (mail.getCcEmailList() != null) {
					for (HashMap<String, String> ccEmail : mail.getCcEmailList()) {
						email.addCc((String) ccEmail.get("email"), (String) ccEmail.get("name"));
					}
				}

				// 비밀참조자 목록 설정
				if (mail.getBccEmailList() != null) {
					for (HashMap<String, String> bccEmail : mail.getBccEmailList()) {
						email.addBcc((String) bccEmail.get("email"), (String) bccEmail.get("name"));
					}
				}

				// 제목 설정
				email.setSubject(mail.getTitle());

				// Templage 메일인 경우
				// 시스템 자동메일인 경우는 HTML양식을 읽어와서 메일을 보냄
				if (mail.getMailType().equals(MailConstant.MAIL_TYPE_TEPLATE)) {
					mailContent = getMailTemplateForVelocity(mail, dataMap);
					email.setHtmlMsg(mailContent);
				}
				// Html 메일인 경우
				else if (mail.getMailType().equals(MailConstant.MAIL_TYPE_HTML)) {
					mailContent = mail.getContent();
					email.setHtmlMsg(mailContent);
				}
				// Text 메일인 경우
				else {
					mailContent = mail.getContent();
					email.setTextMsg(mailContent);
				}

				// 첨부파일 목록 설정
				if (mail.getAttachList() != null) {
					for (MailAttach mailAttach : mail.getAttachList()) {

						EmailAttachment attachment = new EmailAttachment();
						attachment.setName(MimeUtility.encodeText(mailAttach.getName()));
						attachment.setDescription(mailAttach.getDescription());
						attachment.setPath(mailAttach.getPath());
						attachment.setDisposition(EmailAttachment.ATTACHMENT);

						email.attach(attachment);
					}
				}

				// 메일 발송
				email.send();

				reStr = "success";
			}

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return reStr;

	}

	/**
	 * Template 가져오기 시스템 자동메일인 경우는 Velocity 템플릿을 이용하여 HTML을 만들어서 보냄
	 * 
	 * @param mail
	 * @param dataMap
	 * @return @
	 */
	@SuppressWarnings("rawtypes")
	private String getMailTemplateForVelocity(Mail mail, Map dataMap) {

		String htmlContent = "";

		try {

			// 메일 Template 설정
			String filePath = mail.getMailTemplatePath();

			htmlContent = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, filePath, "UTF-8", dataMap);
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
		return htmlContent;
	}

}
