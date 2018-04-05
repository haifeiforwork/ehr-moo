/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.workflow.admin.dao.AdminEmailDao;
import com.lgcns.ikep4.workflow.admin.model.AdminEmailLog;
import com.lgcns.ikep4.workflow.admin.model.AdminEmailLogSearchCondition;
import com.lgcns.ikep4.workflow.admin.service.AdminEmailService;

/**
 * 결재 메일 Service 구현
 *
 * @author 장규진
 * @version $Id: AdminEmailServiceImpl.java 18226 2012-04-24 02:25:31Z jaesyang $
 */
@Service("adminEmailService")
public class AdminEmailServiceImpl extends GenericServiceImpl<AdminEmailLog, String> implements AdminEmailService {

	@Autowired
	private AdminEmailDao adminEmailDao;

	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private MailSendService mailSendService;

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#get(java.io.Serializable)
	 */
	public SearchResult<AdminEmailLog> readAdminEmailSearchList(AdminEmailLogSearchCondition adminEmailLogSearchCondition) {
		SearchResult<AdminEmailLog> searchResult = null;
		
		Integer count = this.adminEmailDao.getCountAdminEmailLogList(adminEmailLogSearchCondition);
		
		adminEmailLogSearchCondition.terminateSearchCondition(count);  
		
		//목록이 없다면...
		if(adminEmailLogSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<AdminEmailLog>(adminEmailLogSearchCondition);
		} else {
			List<AdminEmailLog> adminEmailLogList = this.adminEmailDao.getAdminEmailLogSearchList(adminEmailLogSearchCondition);  
			
			searchResult = new SearchResult<AdminEmailLog>(adminEmailLogList, adminEmailLogSearchCondition); 
		}  
		
		return searchResult;		
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.wfapproval.admin.service.ApEmailService#createApEmailLog(com.lgcns.ikep4.wfapproval.admin.model.ApEmailLog)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String createAdminEmailLog(AdminEmailLog adminEmailLog) {
		
		AdminEmailLog createAdminEmailLog = new AdminEmailLog();
		String rMail = adminEmailLog.getReceiverEmail();
		int insCnt = 0;
		
		if( rMail != null && !"".equals(rMail) ) {
			createAdminEmailLog.setSenderId(adminEmailLog.getSenderId());
			createAdminEmailLog.setSenderEmail(adminEmailLog.getSenderEmail());
			createAdminEmailLog.setEmailTitle(adminEmailLog.getEmailTitle());
			createAdminEmailLog.setEmailContent(adminEmailLog.getEmailContent());
			createAdminEmailLog.setIsSuccess(adminEmailLog.getIsSuccess());
			Date date = new Date();
			SimpleDateFormat ff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			createAdminEmailLog.setSendDate(ff.format(date));
			/* 수신자 로그 등록 */
			String rMailId = adminEmailLog.getReceiver();
			String[] rList 	 = rMail.split(",");
			String[] rIdList = rMailId.split(",");
			for( int i = 0; i < rList.length; i++) {
				String sLogId = this.idgenService.getNextId();
				createAdminEmailLog.setLogId(Long.parseLong(sLogId));
				createAdminEmailLog.setReceiverEmail(rList[i]);
				createAdminEmailLog.setReceiveType("TO");
				if(i < rIdList.length ) createAdminEmailLog.setReceiver(rIdList[i]);
				
				this.adminEmailDao.create(createAdminEmailLog);
				insCnt++;
			}
			
			/* 참조자 로그 등록 */
			String ccMail = adminEmailLog.getCcEmail();
			if( ccMail != null && !"".equals(ccMail) ) {
				String[] ccList	 = ccMail.split(",");
				for( int i = 0; i < ccList.length; i++) {
					String sLogId = this.idgenService.getNextId();
					createAdminEmailLog.setLogId(Long.parseLong(sLogId));
					createAdminEmailLog.setReceiverEmail(ccList[i]);
					createAdminEmailLog.setReceiveType("CC");
					
					this.adminEmailDao.create(createAdminEmailLog);
					insCnt++;
				}
			}

			/* 비밀 참조자 로그 등록 */
			String bccMail = adminEmailLog.getBccEmail();
			if( bccMail != null && !"".equals(bccMail) ) {
				String[] bccList	 = bccMail.split(",");
				for( int i = 0; i < bccList.length; i++) {
					String sLogId = this.idgenService.getNextId();
					createAdminEmailLog.setLogId(Long.parseLong(sLogId));
					createAdminEmailLog.setReceiverEmail(bccList[i]);
					createAdminEmailLog.setReceiveType("BCC");
					
					this.adminEmailDao.create(createAdminEmailLog);
					insCnt++;
				}
			}

		}
		
		return Integer.toString(insCnt);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.wfapproval.admin.service.ApEmailService#sendApEmail(com.lgcns.ikep4.wfapproval.admin.model.ApEmailLog)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String sendAdminEmail(AdminEmailLog adminEmailLog, User user) {
		
		/* TODO : 메일발송 처리 */
		Mail mail = new Mail();
		String rMail = adminEmailLog.getReceiverEmail();
		adminEmailLog.setIsSuccess("N");
		String reStr = "";
		
		if(rMail != null && !"".equals(rMail)) {
			/* 수신자 설정 */
			String[] rList = rMail.split(",");
			List<HashMap> toEmailList = new ArrayList<HashMap>();
			for( int i = 0; i < rList.length; i++) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("email", rList[i]);
				map.put("name", "");
				toEmailList.add(map);
			}
			mail.setToEmailList(toEmailList);
			
			/* 참조자 설정 */
			String ccMail = adminEmailLog.getCcEmail();
			if(ccMail != null && !"".equals(ccMail)) {
				String[] ccList = ccMail.split(",");
				List<HashMap> ccEmailList = new ArrayList<HashMap>();
				for( int i = 0; i < ccList.length; i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("email", ccList[i]);
					map.put("name", "");
					ccEmailList.add(map);
				}
				mail.setCcEmailList(ccEmailList);
			}
			
			/* 비밀 참조자 설정 */
			String bccMail = adminEmailLog.getBccEmail();
			if(bccMail != null && !"".equals(bccMail)) {
				String[] bccList = bccMail.split(",");
				List<HashMap> bccEmailList = new ArrayList<HashMap>();
				for( int i = 0; i < bccList.length; i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("email", bccList[i]);
					map.put("name", "");
					bccEmailList.add(map);
				}
				mail.setBccEmailList(bccEmailList);
			}
			
			//mail.setToEmail(adminEmailLog.getReceiverEmail());
			mail.setFromEmail(adminEmailLog.getSenderEmail());
			mail.setTitle(adminEmailLog.getEmailTitle());
			mail.setContent(adminEmailLog.getEmailContent());
			mail.setMailType(MailConstant.MAIL_TYPE_TXT);// 메일 형식(Text, Html, Template)
			Map dataMap = new HashMap();
			// 템플릿 메일인 경우 아래와 같이 코딩함
			/*
			 * dataMap.put("mail", mail); dataMap.put("userName", "김연아");
			 * mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
			 * mail.setMailTemplatePath("/base/mail/testMail.vm");
			 */
			// 메일 보내기
			reStr = mailSendService.sendMail(mail, dataMap, user);
	
			// 로그에 기록
			if( reStr != null && !"".equals(reStr)) {
				adminEmailLog.setIsSuccess("Y");
			}
		}
		
		String logsCnt = this.createAdminEmailLog(adminEmailLog);
		
		return reStr;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.wfapproval.admin.service.ApEmailService#deleteApEmailLog(com.lgcns.ikep4.wfapproval.admin.model.ApEmailLog)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void deleteAdminEmailLog(String logId) {
		if(this.adminEmailDao.exists(logId)){
			this.adminEmailDao.remove(logId);
		}
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.wfapproval.admin.service.ApEmailService#deleteApEmailLog(com.lgcns.ikep4.wfapproval.admin.model.ApEmailLog)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void deleteMultiAdminEmailLog(List<String> logIds) {
		this.adminEmailDao.removeMulti(logIds);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.wfapproval.admin.service.ApEmailService#readApEmail(java.lang.String)
	 */
	@Transactional(readOnly = true)
	public AdminEmailLog readAdminEmail(String logId) {
		return this.adminEmailDao.get(logId);
	}
	
}
