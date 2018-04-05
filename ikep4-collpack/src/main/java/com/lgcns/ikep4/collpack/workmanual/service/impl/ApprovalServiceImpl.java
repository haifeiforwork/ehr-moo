/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.workmanual.dao.ApprovalDao;
import com.lgcns.ikep4.collpack.workmanual.dao.ApprovalLineDao;
import com.lgcns.ikep4.collpack.workmanual.dao.ManualVersionDao;
import com.lgcns.ikep4.collpack.workmanual.model.Approval;
import com.lgcns.ikep4.collpack.workmanual.model.ApprovalLine;
import com.lgcns.ikep4.collpack.workmanual.model.ManualVersion;
import com.lgcns.ikep4.collpack.workmanual.model.ManualVersionPk;
import com.lgcns.ikep4.collpack.workmanual.search.ApprovalSearchCondition;
import com.lgcns.ikep4.collpack.workmanual.service.ApprovalService;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;


/**
 * Service 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ApprovalServiceImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Service 
@Transactional
public class ApprovalServiceImpl extends GenericServiceImpl<Approval, String> implements ApprovalService {
	@Autowired
	private ApprovalDao approvalDao;
	@Autowired
	private ManualVersionDao manualVersionDao;
	@Autowired
	private ApprovalLineDao approvalLineDao;

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private MailSendService mailSendService;
	@Autowired
	private UserService userService;     
	@Autowired
    private TimeZoneSupportService timeZoneSupportService;

	@Deprecated
	public String create(Approval approval) {
		return approvalDao.create(approval);
	}

	@Deprecated
	public boolean exists(String approvalId) {
		return approvalDao.exists(approvalId);
	}

	public Approval read(String approvalId) {
		return approvalDao.get(approvalId);
	}

	@Deprecated
	public void delete(String approvalId) {
		approvalDao.remove(approvalId);
	}

	@Deprecated
	public void update(Approval approval) {
		approvalDao.update(approval);
	}
	////////////////////////////////////
	
	//나의 결재함 조회
	public SearchResult<Approval> listMyApproval(ApprovalSearchCondition approvalSearchCondition) {
		Integer count = approvalDao.countMyApproval(approvalSearchCondition);
		approvalSearchCondition.terminateSearchCondition(count);  
		
		SearchResult<Approval> searchResult = null; 
		if(approvalSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Approval>(approvalSearchCondition);
		} else {
			List<Approval> manualVersionList = approvalDao.listMyApproval(approvalSearchCondition); 
			searchResult = new SearchResult<Approval>(manualVersionList, approvalSearchCondition);  			
		}  
		
		return searchResult;
	}
	//상신중인 결재정보 
	public Approval readSubmittingApproval(String versionId) {
		return approvalDao.getSubmittingApproval(versionId);
	}
	//메일 발송  
	public void sendMail(String mode, String approvalId, User user, String url) {
		if(mode.equals("A")) {//상신
			mailSubmit(approvalId, user, url);
		} else if(mode.equals("B")) {//상신취소
			mailSubmitCancel(approvalId, user, url);
		} else if(mode.equals("C")) {//승인
			mailAccept(approvalId, user, url);
		} else if(mode.equals("D")) {//반려
			mailReject(approvalId, user, url);
		}
	}
	//메일-상신
	private void mailSubmit(String approvalId, User user, String url) {
		Approval approval = approvalDao.get(approvalId);

		ManualVersionPk manualVersionPk = new ManualVersionPk();
		manualVersionPk.setVersionId(approval.getVersionId());
		manualVersionPk.setPortalId(user.getPortalId());
		ManualVersion manualVersion = manualVersionDao.getManualVersion(manualVersionPk);
		
		List<ApprovalLine> approvalLineList = approvalLineDao.listApprovalLine(approval.getApprovalId());
		
		User registUser = userService.read(approval.getRegisterId());

		String pattern = "yyyy.MM.dd";
		String contents1 = "<html><body><b>";
		String contents2 = messageSource.getMessage("ui.collpack.workmanual.message.mail.name", null, new Locale(user.getLocaleCode())) + " : <a target='_new' href='" + url + "/collpack/workmanual/readManualVersionView.do?pathStep=C&versionId=" + manualVersion.getVersionId() + "'>" + manualVersion.getVersionTitle() + "</a><br>";
		String contents3 = messageSource.getMessage("ui.collpack.workmanual.message.mail.contents", null, new Locale(user.getLocaleCode())) + " : " + approval.getRequestContents() + "<br>";
		String contents4 = messageSource.getMessage("ui.collpack.workmanual.message.mail.registerName", null, new Locale(user.getLocaleCode())) + " : " + registUser.getUserName() + " " +  registUser.getTeamName() + " " + registUser.getJobTitleName() + "<br>";
		String contents5 = messageSource.getMessage("ui.collpack.workmanual.message.mail.registDate", null, new Locale(user.getLocaleCode())) + " : " + timeZoneSupportService.convertTimeZoneToString(approval.getRegistDate(), pattern) + "<br>";
		String contents7 = "</b></body></html>";
		
		Mail mail = new Mail();
		String title = "";
		List<HashMap> list = new ArrayList<HashMap>();
		
		//수신자 : 미결자
		for(int i=0; i<approvalLineList.size(); i++) {
			if(approvalLineList.get(i).getApprovalResult().equals("B")) { //A: 대기, B : 미결재, C : 승인, D: 반려
				User approvalUser = userService.read(approvalLineList.get(i).getApprovalUserId());
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("email", approvalUser.getMail());
				map.put("name", approvalUser.getUserName());
				list.add(map);
			}
		}

		//제목
		if(approval.getManualType().equals("A")) { //( A:제정, B:개정, C:폐기)	
			title = messageSource.getMessage("ui.collpack.workmanual.button.submit", null, new Locale(user.getLocaleCode()));
		} else if(approval.getManualType().equals("B")) {
			title = messageSource.getMessage("ui.collpack.workmanual.button.submit.revision", null, new Locale(user.getLocaleCode()));
		} else if(approval.getManualType().equals("C")) {
			title = messageSource.getMessage("ui.collpack.workmanual.button.disuse", null, new Locale(user.getLocaleCode()));
		}
		
		//내용
		
		mail.setTitle(title + " - " + manualVersion.getVersionTitle());
		mail.setToEmailList(list);
		mail.setContent(contents1 + contents2 + contents3 + contents4 + contents5 + contents7);
		mail.setMailType(MailConstant.MAIL_TYPE_HTML);
		mailSendService.sendMail(mail, null, user);
	}
	//메일-상신취소
	private void mailSubmitCancel(String approvalId, User user, String url) {
		Approval approval = approvalDao.get(approvalId);

		ManualVersionPk manualVersionPk = new ManualVersionPk();
		manualVersionPk.setVersionId(approval.getVersionId());
		manualVersionPk.setPortalId(user.getPortalId());
		ManualVersion manualVersion = manualVersionDao.getManualVersion(manualVersionPk);
		
		List<ApprovalLine> approvalLineList = approvalLineDao.listApprovalLine(approval.getApprovalId());
		
		User registUser = userService.read(approval.getRegisterId());

		String pattern = "yyyy.MM.dd";
		String contents1 = "<html><body><b>";
		String contents2 = messageSource.getMessage("ui.collpack.workmanual.message.mail.name", null, new Locale(user.getLocaleCode())) + " : <a target='_new' href='" + url + "/collpack/workmanual/readManualVersionView.do?pathStep=C&versionId=" + manualVersion.getVersionId() + "'>" + manualVersion.getVersionTitle() + "</a><br>";
		String contents3 = messageSource.getMessage("ui.collpack.workmanual.message.mail.contents", null, new Locale(user.getLocaleCode())) + " : " + approval.getRequestContents() + "<br>";
		String contents4 = messageSource.getMessage("ui.collpack.workmanual.message.mail.registerName", null, new Locale(user.getLocaleCode())) + " : " + registUser.getUserName() + " " +  registUser.getTeamName() + " " + registUser.getJobTitleName() + "<br>";
		String contents5 = messageSource.getMessage("ui.collpack.workmanual.message.mail.registDate", null, new Locale(user.getLocaleCode())) + " : " + timeZoneSupportService.convertTimeZoneToString(approval.getRegistDate(), pattern) + "<br>";
		String contents6 = messageSource.getMessage("ui.collpack.workmanual.message.mail.approvalComment", null, new Locale(user.getLocaleCode())) + " : ";
		String contents7 = "</b></body></html>";
		
		Mail mail = new Mail();
		String title = "";
		List<HashMap> list = new ArrayList<HashMap>();
		
		//수신자 : 승인자 + 미결재자
		for(int i=0; i<approvalLineList.size(); i++) {
			if(approvalLineList.get(i).getApprovalResult().equals("B") || approvalLineList.get(i).getApprovalResult().equals("C")) { //A: 대기, B : 미결재, C : 승인, D: 반려
				User approvalUser = userService.read(approvalLineList.get(i).getApprovalUserId());
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("email", approvalUser.getMail());
				map.put("name", approvalUser.getUserName());
				list.add(map);
			}
		}

		//제목
		if(approval.getManualType().equals("A")) { //( A:제정, B:개정, C:폐기)	
			title = messageSource.getMessage("ui.collpack.workmanual.button.submit.cancel", null, new Locale(user.getLocaleCode()));
		} else if(approval.getManualType().equals("B")) {
			title = messageSource.getMessage("ui.collpack.workmanual.button.submit.revision.cancel", null, new Locale(user.getLocaleCode()));
		} else if(approval.getManualType().equals("C")) {
			title = messageSource.getMessage("ui.collpack.workmanual.button.disuse.cancel", null, new Locale(user.getLocaleCode()));
		}
		
		//내용
		int j = 0;
		for(int i=0; i<approvalLineList.size(); i++) {
			if(approvalLineList.get(i).getApprovalResult().equals("C")) { //A: 대기, B : 미결재, C : 승인, D: 반려
				contents6 += "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + Integer.toString(++j) + ". " + 
					timeZoneSupportService.convertTimeZoneToString(approvalLineList.get(i).getApprovalDate(), pattern) + " " +
					approvalLineList.get(i).getApprovalUserName() + " " +
					approvalLineList.get(i).getApprovalUserTeamName() + " " +
					approvalLineList.get(i).getApprovalUserJobTitleName() + " " +
					approvalLineList.get(i).getApprovalComment();	
			}
		}	
		
		mail.setTitle(title + " - " + manualVersion.getVersionTitle());
		mail.setToEmailList(list);
		mail.setContent(contents1 + contents2 + contents3 + contents4 + contents5 + contents6 + contents7);
		mail.setMailType(MailConstant.MAIL_TYPE_HTML);
		mailSendService.sendMail(mail, null, user);		
	}
	//메일-승인
	private void mailAccept(String approvalId, User user, String url) {
		Approval approval = approvalDao.get(approvalId);

		ManualVersionPk manualVersionPk = new ManualVersionPk();
		manualVersionPk.setVersionId(approval.getVersionId());
		manualVersionPk.setPortalId(user.getPortalId());
		ManualVersion manualVersion = manualVersionDao.getManualVersion(manualVersionPk);
		
		List<ApprovalLine> approvalLineList = approvalLineDao.listApprovalLine(approval.getApprovalId());
		
		User registUser = userService.read(approval.getRegisterId());

		String pattern = "yyyy.MM.dd";
		String contents1 = "<html><body><b>";
		String contents2 = messageSource.getMessage("ui.collpack.workmanual.message.mail.name", null, new Locale(user.getLocaleCode())) + " : <a target='_new' href='" + url + "/collpack/workmanual/readManualVersionView.do?pathStep=C&versionId=" + manualVersion.getVersionId() + "'>" + manualVersion.getVersionTitle() + "</a><br>";
		String contents3 = messageSource.getMessage("ui.collpack.workmanual.message.mail.contents", null, new Locale(user.getLocaleCode())) + " : " + approval.getRequestContents() + "<br>";
		String contents4 = messageSource.getMessage("ui.collpack.workmanual.message.mail.registerName", null, new Locale(user.getLocaleCode())) + " : " + registUser.getUserName() + " " +  registUser.getTeamName() + " " + registUser.getJobTitleName() + "<br>";
		String contents5 = messageSource.getMessage("ui.collpack.workmanual.message.mail.registDate", null, new Locale(user.getLocaleCode())) + " : " + timeZoneSupportService.convertTimeZoneToString(approval.getRegistDate(), pattern) + "<br>";
		String contents6 = messageSource.getMessage("ui.collpack.workmanual.message.mail.approvalComment", null, new Locale(user.getLocaleCode())) + " : ";
		String contents7 = "</b></body></html>";
		
		Mail mail = new Mail();
		String title = "";
		List<HashMap> list = new ArrayList<HashMap>();
		
		//수신자 : 미결자 or 상신자
		boolean isEnd = true;
		for(int i=0; i<approvalLineList.size(); i++) {
			if(approvalLineList.get(i).getApprovalResult().equals("B")) { //A: 대기, B : 미결재, C : 승인, D: 반려
				isEnd = false;
				
				User approvalUser = userService.read(approvalLineList.get(i).getApprovalUserId());
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("email", approvalUser.getMail());
				map.put("name", approvalUser.getUserName());
				list.add(map);
			}
		}
		if(isEnd) {//완료이므로 상신자에게 메일 발송
			User approvalUser = userService.read(approval.getRegisterId());
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("email", approvalUser.getMail());
			map.put("name", approvalUser.getUserName());
			list.add(map);
		}

		//제목
		if(approval.getManualType().equals("A")) { //( A:제정, B:개정, C:폐기)
			title = messageSource.getMessage("ui.collpack.workmanual.button.submit", null, new Locale(user.getLocaleCode()));
		} else if(approval.getManualType().equals("B")) {
			title = messageSource.getMessage("ui.collpack.workmanual.button.submit.revision", null, new Locale(user.getLocaleCode()));
		} else if(approval.getManualType().equals("C")) {
			title = messageSource.getMessage("ui.collpack.workmanual.button.disuse", null, new Locale(user.getLocaleCode()));
		}
		if(isEnd) {
			title += " " + messageSource.getMessage("ui.collpack.workmanual.message.mail.complete.ok", null, new Locale(user.getLocaleCode()));
		}
		
		//내용
		int j = 0;
		for(int i=0; i<approvalLineList.size(); i++) {
			if(approvalLineList.get(i).getApprovalResult().equals("C")) { //A: 대기, B : 미결재, C : 승인, D: 반려
				contents6 += "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + Integer.toString(++j) + ". " + 
					timeZoneSupportService.convertTimeZoneToString(approvalLineList.get(i).getApprovalDate(), pattern) + " " +
					approvalLineList.get(i).getApprovalUserName() + " " +
					approvalLineList.get(i).getApprovalUserTeamName() + " " +
					approvalLineList.get(i).getApprovalUserJobTitleName() + " " +
					approvalLineList.get(i).getApprovalComment();	
			}
		}
		
		mail.setTitle(title + " - " + manualVersion.getVersionTitle());
		mail.setToEmailList(list);
		mail.setContent(contents1 + contents2 + contents3 + contents4 + contents5 + contents6 + contents7);
		mail.setMailType(MailConstant.MAIL_TYPE_HTML);
		mailSendService.sendMail(mail, null, user);
	}
	//메일-반려
	private void mailReject(String approvalId, User user, String url) {
		Approval approval = approvalDao.get(approvalId);

		ManualVersionPk manualVersionPk = new ManualVersionPk();
		manualVersionPk.setVersionId(approval.getVersionId());
		manualVersionPk.setPortalId(user.getPortalId());
		ManualVersion manualVersion = manualVersionDao.getManualVersion(manualVersionPk);
		
		List<ApprovalLine> approvalLineList = approvalLineDao.listApprovalLine(approval.getApprovalId());
		
		User registUser = userService.read(approval.getRegisterId());

		String pattern = "yyyy.MM.dd";
		String contents1 = "<html><body><b>";
		String contents2 = messageSource.getMessage("ui.collpack.workmanual.message.mail.name", null, new Locale(user.getLocaleCode())) + " : <a target='_new' href='" + url + "/collpack/workmanual/readManualVersionView.do?pathStep=C&versionId=" + manualVersion.getVersionId() + "'>" + manualVersion.getVersionTitle() + "</a><br>";
		String contents3 = messageSource.getMessage("ui.collpack.workmanual.message.mail.contents", null, new Locale(user.getLocaleCode())) + " : " + approval.getRequestContents() + "<br>";
		String contents4 = messageSource.getMessage("ui.collpack.workmanual.message.mail.registerName", null, new Locale(user.getLocaleCode())) + " : " + registUser.getUserName() + " " +  registUser.getTeamName() + " " + registUser.getJobTitleName() + "<br>";
		String contents5 = messageSource.getMessage("ui.collpack.workmanual.message.mail.registDate", null, new Locale(user.getLocaleCode())) + " : " + timeZoneSupportService.convertTimeZoneToString(approval.getRegistDate(), pattern) + "<br>";
		String contents6 = messageSource.getMessage("ui.collpack.workmanual.message.mail.approvalComment", null, new Locale(user.getLocaleCode())) + " : ";
		String contents7 = "</b></body></html>";
		
		Mail mail = new Mail();
		String title = "";
		List<HashMap> list = new ArrayList<HashMap>();
		
		//수신자 : 상신자
		User approvalUser = userService.read(approval.getRegisterId());
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", approvalUser.getMail());
		map.put("name", approvalUser.getUserName());
		list.add(map);

		//제목
		if(approval.getManualType().equals("A")) { //( A:제정, B:개정, C:폐기)
			title = messageSource.getMessage("ui.collpack.workmanual.button.submit", null, new Locale(user.getLocaleCode()));
		} else if(approval.getManualType().equals("B")) {
			title = messageSource.getMessage("ui.collpack.workmanual.button.submit.revision", null, new Locale(user.getLocaleCode()));
		} else if(approval.getManualType().equals("C")) {
			title = messageSource.getMessage("ui.collpack.workmanual.button.disuse", null, new Locale(user.getLocaleCode()));
		}
		title += " " + messageSource.getMessage("ui.collpack.workmanual.message.mail.complete.reject", null, new Locale(user.getLocaleCode()));

		//내용
		int j = 0;
		for(int i=0; i<approvalLineList.size(); i++) {
			if(approvalLineList.get(i).getApprovalResult().equals("C") || approvalLineList.get(i).getApprovalResult().equals("D")) { //A: 대기, B : 미결재, C : 승인, D: 반려
				contents6 += "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + Integer.toString(++j) + ". " + 
					timeZoneSupportService.convertTimeZoneToString(approvalLineList.get(i).getApprovalDate(), pattern) + " " +
					approvalLineList.get(i).getApprovalUserName() + " " +
					approvalLineList.get(i).getApprovalUserTeamName() + " " +
					approvalLineList.get(i).getApprovalUserJobTitleName() + " " +
					approvalLineList.get(i).getApprovalComment();	
			}
		}	
		
		mail.setTitle(title + " - " + manualVersion.getVersionTitle());
		mail.setToEmailList(list);
		mail.setContent(contents1 + contents2 + contents3 + contents4 + contents5 + contents6 + contents7);
		mail.setMailType(MailConstant.MAIL_TYPE_HTML);
		mailSendService.sendMail(mail, null, user);	
	}
}
