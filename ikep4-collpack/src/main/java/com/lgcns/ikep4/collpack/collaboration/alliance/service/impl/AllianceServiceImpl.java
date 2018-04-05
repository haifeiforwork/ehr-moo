/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.alliance.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.collpack.collaboration.alliance.dao.AllianceDao;
import com.lgcns.ikep4.collpack.collaboration.alliance.model.Alliance;
import com.lgcns.ikep4.collpack.collaboration.alliance.search.AllianceSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.alliance.service.AllianceService;
import com.lgcns.ikep4.collpack.collaboration.board.board.dao.BoardDao;
import com.lgcns.ikep4.collpack.collaboration.workspace.dao.WorkspaceDao;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * Alliance Service 구현
 * 
 * @author 김종철
 * @version $Id: AllianceServiceImpl.java 13925 2011-05-31 02:20:55Z happyi1018
 *          $
 */
@Service("allianceService")
public class AllianceServiceImpl extends GenericServiceImpl<Alliance, String> implements AllianceService {

	@Autowired
	private AllianceDao allianceDao;

	@Autowired
	private BoardDao boardDao;

	@Autowired
	private WorkspaceDao workspaceDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private IdgenService idgenService;

	@Autowired
	private MailSendService mailSendService;

	@Autowired
	ACLService aclService;

	@Autowired
	protected MessageSource messageSource = null;

	private static final String MESSAGE_MAIL_CREATE = "message.collpack.collaboration.alliance.mail.create";

	private static final String MESSAGE_MAIL_CLOSE = "message.collpack.collaboration.alliance.mail.close";

	private static final String MESSAGE_MAIL_CANCEL = "message.collpack.collaboration.alliance.mail.cancel";

	private static final String MESSAGE_MAIL_APPROVED = "message.collpack.collaboration.alliance.mail.approved";

	private static final String MESSAGE_MAIL_HOLD = "message.collpack.collaboration.alliance.mail.hold";

	private static Map<String, String> messageCodes = new HashMap<String, String>();
	static {
		messageCodes.put("create", MESSAGE_MAIL_CREATE);
		messageCodes.put("close", MESSAGE_MAIL_CLOSE);
		messageCodes.put("cancel", MESSAGE_MAIL_CANCEL);
	}

	private static Map<String, String> statusCodes = new HashMap<String, String>();
	static {
		statusCodes.put("approved", MESSAGE_MAIL_APPROVED);
		statusCodes.put("hold", MESSAGE_MAIL_HOLD);

	}

	/**
	 * 동맹 목록
	 */
	public SearchResult<Alliance> listBySearchCondition(AllianceSearchCondition searchCondition) {
		Integer count = this.allianceDao.countBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<Alliance> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Alliance>(searchCondition);

		} else {

			List<Alliance> list = this.allianceDao.listBySearchCondition(searchCondition);

			for (int i = 0; i < list.size(); i++) {
				Alliance alliance = list.get(i);
				alliance.setGiveAllianceList(allianceDao.giveAllianceBoardByWorkspace(searchCondition.getWorkspaceId(),
						alliance.getWorkspaceId()));

				alliance.setReceiveAllianceList(allianceDao.receiveAllianceBoardByWorkspace(
						searchCondition.getWorkspaceId(), alliance.getWorkspaceId()));
				list.set(i, alliance);
			}
			searchResult = new SearchResult<Alliance>(list, searchCondition);
		}

		return searchResult;
	}

	/**
	 * 동맹에게 공유한 게시판 전체 목록
	 */
	public List<Alliance> giveAllianceBoardListByWorkspace(String workspaceId) {
		return this.allianceDao.giveAllianceBoardListByWorkspace(workspaceId);
	}

	/**
	 * 동맹에게 공유받은 게시판 전체 목록
	 */
	public List<Alliance> receiveAllianceBoardListByWorkspace(String workspaceId) {
		return this.allianceDao.receiveAllianceBoardListByWorkspace(workspaceId);
	}

	/**
	 * 동맹 목록
	 */
	public List<Alliance> listAllianceByWorkspace(String workspaceId) {
		return this.allianceDao.listAllianceByWorkspace(workspaceId);
	}

	/**
	 * 게시판을 공유한 동맹 목록
	 */
	public List<Alliance> listAllianceByBoard(String workspaceId, String boardId) {
		return this.allianceDao.listAllianceByBoard(workspaceId, boardId);
	}

	/**
	 * 동맹 신청
	 */
	public String create(Alliance object) {
		object.setAllianceId(idgenService.getNextId());

		return this.allianceDao.create(object);
	}

	/**
	 * 동맹 신청
	 */
	public String create(Alliance object, User user) {
		object.setAllianceId(idgenService.getNextId());

		sendMail("create", "", object, user, "0");

		return this.allianceDao.create(object);
	}

	/**
	 * 동맹 조회
	 */
	public Alliance read(Alliance object) {
		return allianceDao.get(object);
	}

	/**
	 * 조직타입의 동맹 조회
	 */
	public Alliance getOrgAlliance(Alliance object) {
		return allianceDao.getOrgAlliance(object);
	}

	@Deprecated
	public Alliance read(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 동맹 수정
	 */
	public void update(Alliance object) {
		allianceDao.update(object);

	}

	/**
	 * 동맹 상태변경(승인,반려,신청취소)
	 */
	public void updateStatus(AllianceSearchCondition searchCondition, List<String> allianceIds, User user) {
		// allianceDao.updateStatus(object);
		for (int i = 0; i < allianceIds.size(); i++) {
			Alliance alliance = new Alliance();

			String allianceId = allianceIds.get(i);

			alliance.setAllianceId(allianceId);
			alliance.setStatus(searchCondition.getStatus());
			alliance.setUpdaterId(user.getUserId());
			alliance.setUpdaterName(user.getUserName());

			Alliance alliance1 = allianceDao.get(allianceId);

			if (searchCondition.getStatus().equals("1") && alliance1.getStatus().equals("0")) { // 동맹요청
																								// 승인
				sendMail("create", "approved", alliance1, user, searchCondition.getStatus());
			}
			if (searchCondition.getStatus().equals("2")) { // 동맹요청 보류
				sendMail("create", "hold", alliance1, user, searchCondition.getStatus());
			} else if (searchCondition.getStatus().equals("3")) { // 동맹종료
				sendMail("close", "", alliance1, user, searchCondition.getStatus());
			}

			allianceDao.updateStatus(alliance);

		}

	}

	@Deprecated
	public void delete(String id) {
		// TODO Auto-generated method stub

	}

	/**
	 * 동맹삭제 + 동맹게시판 삭제
	 */
	public void physicalDelete(List<String> allianceIds) {
		for (int i = 0; i < allianceIds.size(); i++) {
			String allianceId = allianceIds.get(i);
			boardDao.removeBoardByAllianceId(allianceId);
			allianceDao.physicalDelete(allianceId);
		}
	}

	/**
	 * 동맹삭제 + 동맹게시판 삭제
	 */
	public void physicalDelete(List<String> allianceIds, User user) {
		for (int i = 0; i < allianceIds.size(); i++) {
			String allianceId = allianceIds.get(i);
			boardDao.removeBoardByAllianceId(allianceId);
			Alliance alliance1 = allianceDao.get(allianceId);
			sendMail("close", "", alliance1, user, "4");

			allianceDao.physicalDelete(allianceId);

		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sendMail(String action, String status, Alliance alliance, User user, String allianceStatus) {

		Mail mail = new Mail();
		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		if (user.getLocaleCode().toUpperCase().equals("KO")) {
			mail.setMailTemplatePath("workspaceAllianceTemplate.vm");
		} else {
			mail.setMailTemplatePath("workspaceAllianceEnglishTemplate.vm");
		}
		// mail.setMailTemplatePath("workspaceAllianceTemplate.vm");

		// 발신자
		User sender = new User();
		// 발신자 -> 개설 신청자,폐쇄신청자,승인,폐쇄처리자
		sender = user;
		// 수신자
		List recipients = new ArrayList();
		HashMap<String, String> recip;

		String actionMessage = null;
		actionMessage = messageSource.getMessage(messageCodes.get(action), null, new Locale("ko"));
		String statusMessage = null;
		if (status != null && !status.equals("")) {
			statusMessage = messageSource.getMessage(statusCodes.get(status), null, new Locale("ko"));
		}
		if(StringUtil.isEmpty(statusMessage)){
			statusMessage="";
		}
		if(StringUtil.isEmpty(actionMessage)){
			actionMessage="";
		}
		Workspace toWorkspace = new Workspace();
		Workspace fromWorkspace = new Workspace();
		if (allianceStatus.equals("0") || allianceStatus.equals("4")) {// 동맹신청,동맹신청취소

			// 수신자 --> 동맹요청받은 시샵
			// 요청받은 WS
			toWorkspace = workspaceDao.getWorkspace(alliance.getToWorkspaceId());
			// 요청한 WS
			fromWorkspace = workspaceDao.getWorkspace(alliance.getWorkspaceId());
			User receiverUser = userDao.get(toWorkspace.getSysopId());

			if (receiverUser != null) {
				recip = new HashMap<String, String>();
				recip.put("email", receiverUser.getMail());
				recip.put("name", receiverUser.getUserName());
				recipients.add(recip);
				mail.setToEmailList(recipients);
				// 메일 제목
				if (user.getLocaleCode().toUpperCase().equals("KO")) {
					mail.setTitle("[워크스페이스] " + actionMessage + " 알림 - " + fromWorkspace.getWorkspaceName());
				} else {
					mail.setTitle("[Workspace] " + actionMessage + " 알림 - " + fromWorkspace.getWorkspaceName());
				}
			}
		} else if (allianceStatus.equals("1") || allianceStatus.equals("2") || allianceStatus.equals("3")) {// 동앵승인,동맹보류,동맹종료
			// 수신자 --> 동맹요청한 시샵
			// 요청받은 WS
			toWorkspace = workspaceDao.getWorkspace(alliance.getToWorkspaceId());
			// 요청한 WS
			fromWorkspace = workspaceDao.getWorkspace(alliance.getWorkspaceId());
			// 동맹 신청자에게 메일 발송
			User receiverUser = userDao.get(alliance.getRegisterId());

			if (receiverUser != null) {
				recip = new HashMap<String, String>();
				recip.put("email", receiverUser.getMail());
				recip.put("name", receiverUser.getUserName());
				recipients.add(recip);
				mail.setToEmailList(recipients);
				// 메일 제목
				if (user.getLocaleCode().toUpperCase().equals("KO")) {
					mail.setTitle("[워크스페이스] 동맹요청 " + statusMessage + " 알림 - " + toWorkspace.getWorkspaceName());
				} else {
					mail.setTitle("[Workspace] 동맹요청 " + statusMessage + " 알림 - " + toWorkspace.getWorkspaceName());
				}
			}
		}
		Map dataMap = new HashMap();

		dataMap.put("action", actionMessage);
		dataMap.put("status", statusMessage);
		dataMap.put("alliance", alliance);
		dataMap.put("allianceStatus", allianceStatus);
		dataMap.put("fromWorkspace", fromWorkspace);
		dataMap.put("toWorkspace", toWorkspace);
		dataMap.put("registDate", DateUtil.getToday(""));
		dataMap.put("sender", sender);
		/** 메일발송 임시 미사용, 메일 발송 오류로인한 **/
		mailSendService.sendMail(mail, dataMap, sender);

	}

}
