/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.member.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.collpack.collaboration.member.dao.MemberDao;
import com.lgcns.ikep4.collpack.collaboration.member.model.Member;
import com.lgcns.ikep4.collpack.collaboration.member.search.MemberSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.member.service.MemberService;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspaceConstants;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspaceService;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.DateUtil;


/**
 * Workspace 멤버 Service 구현 클래스
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: MemberServiceImpl.java 16809 2011-10-14 05:37:27Z giljae $
 */
@Service("memberService")
public class MemberServiceImpl extends GenericServiceImpl<Member, String> implements MemberService {

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private ActivityStreamService activityStreamService;

	@Autowired
	private MailSendService mailSendService;

	/**
	 * 회원 목록
	 */
	public SearchResult<Member> listBySearchCondition(MemberSearchCondition searchCondition) {

		Integer count = this.memberDao.countBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<Member> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Member>(searchCondition);

		} else {

			List<Member> list = this.memberDao.listBySearchCondition(searchCondition);

			searchResult = new SearchResult<Member>(list, searchCondition);
		}

		return searchResult;
	}
	
	public SearchResult<Member> memberListBySearchCondition(MemberSearchCondition searchCondition) {

		SearchResult<Member> searchResult = null;


		List<Member> list = this.memberDao.memberListBySearchCondition(searchCondition);

		searchResult = new SearchResult<Member>(list, searchCondition);

		return searchResult;
	}

	/**
	 * 회원이 신청한 가입신청한 WS 목록
	 */
	public List<Member> listWorkspaceMember(Map<String, String> map) {

		User sessionuser = (User) this.getRequestAttribute("ikep.user");

		List<Member> memberList = memberDao.listWorkspaceMember(map);

		if (memberList != null) {
			if (!sessionuser.getLocaleCode().equals("ko")) {
				for (Member member : memberList) {
					member.setMemberName(member.getMemberEnglishName());
					member.setTeamName(member.getTeamEnglishName());
					member.setJobTitleName(member.getJobTitleEnglishName());
				}
			}
		}
		return memberList;
	}

	/**
	 * 시샵 목록 조회
	 */
	public List<Member> getSysopList(Map<String, List<String>> map) {
		return memberDao.getSysopList(map);
	}

	/**
	 * 해당 WS의 시샵정보 조회
	 */
	public Member getSysop(String workspaceId) {
		return memberDao.getSysop(workspaceId);
	}

	/**
	 * 회원 목록
	 */
	public List<Member> getMemberList(Map<String, Object> map) {
		return memberDao.getMemberList(map);
	}

	/**
	 * 멤버 메일 발송 대상자 목록
	 */
	public List<Member> getMemberMailList(String workspaceId) {
		return memberDao.getMemberMailList(workspaceId);
	}

	/**
	 * 회원 가입신청
	 */
	public String create(Member object) {
		return memberDao.create(object);
	}

	/*
	 * ACL 권한 그룹의 회원삭제후 신규 등록
	 */
	public void createAclUserGroup(String portalId, User user, String groupId, List<String> memberIds) {

		// 권한 그룹별 사용자 등록
		try {
			// 해당 권한 그룹 조회

			// 해당 권한 그룹 사용자 삭제 (IKEP4_EV_USER_GROUP)
			userDao.removeUserFromGroup(groupId);

			// 해당 권한 그룹 사용자 등록 (IKEP4_EV_USER_GROUP)
			if (memberIds != null) {
				for (String memberId : memberIds) {
					User memberUser = new User();

					memberUser.setUserId(memberId);
					memberUser.setGroupId(groupId);
					memberUser.setRegisterId(user.getUserId());
					memberUser.setRegisterName(user.getUserName());
					memberUser.setUpdaterId(user.getUserId());
					memberUser.setUpdaterName(user.getUserName());
					userDao.addUserToGroup(memberUser);
				}
			}

		} catch (Exception exception) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("\r\nWorkspace 권한 그룹 사용자 등록 생성 실패...... ");

			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			this.log.error(exception.getStackTrace());
		}
	}

	/**
	 * 관리자의 신규 회원 등록
	 * 
	 * @param workspaceId
	 * @param memberIds
	 * @param user
	 */
	public void addMember(String workspaceId, List<String> memberIds, User user) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("workspaceId", workspaceId);
		map.put("groupName", "정회원");

		// 정회원 그룹 가져오기
		String memberGroupId = memberDao.getEvGroup(map);

		String activityUserId = null;
		// int totalCnt =0;
		for (String memberId : memberIds) {
			if (activityUserId == null) {
				activityUserId = memberId;
			}
			// totalCnt++;
			Member isMember = new Member();
			isMember.setWorkspaceId(workspaceId);
			isMember.setMemberId(memberId);
			isMember = memberDao.get(isMember);
			Member member = new Member();

			if (isMember == null) {
				/**
				 * 기존 회원이 없으면 신규 등록
				 */
				member.setWorkspaceId(workspaceId);
				member.setMemberId(memberId);
				member.setMemberIntroduction("정회원가입");
				member.setMemberLevel(WorkspaceConstants.MEMBER_STATUS_MEMBER);
				member.setJoinType(WorkspaceConstants.MEMBER_JOIN_TYPE_ADM);
				member.setRegisterId(user.getUserId());
				member.setRegisterName(user.getUserName());
				member.setUpdaterId(user.getUserId());
				member.setUpdaterName(user.getUserName());
				memberDao.create(member);

				/**
				 * 권한등록(신규)
				 */
				User memberUser = new User();
				memberUser.setUserId(memberId);
				memberUser.setGroupId(memberGroupId);
				memberUser.setRegisterId(user.getUserId());
				memberUser.setRegisterName(user.getUserName());
				memberUser.setUpdaterId(user.getUserId());
				memberUser.setUpdaterName(user.getUserName());
				userDao.addUserToGroup(memberUser);
			} else {
				/**
				 * 기존회원이 있으며 준/가입대기 회원을 정회원으로 등급조정
				 */
				if (isMember.getMemberLevel().equals(WorkspaceConstants.MEMBER_STATUS_ASSOCIATE)
						|| isMember.getMemberLevel().equals(WorkspaceConstants.MEMBER_STATUS_WAIT)) {
					member.setWorkspaceId(workspaceId);
					member.setMemberId(memberId);
					member.setMemberLevel(WorkspaceConstants.MEMBER_STATUS_MEMBER);
					member.setUpdateType("level");// 등급변경
					member.setUpdaterId(user.getUserId());
					member.setUpdaterName(user.getUserName());
					memberDao.update(member);

					/**
					 * 권한등록(변경)
					 */
					Map<String, String> userGroupMap = new HashMap<String, String>();
					userGroupMap.put("workspaceId", member.getWorkspaceId());
					userGroupMap.put("memberId", memberId);

					/** 기존 멤버를 해당 워크스페이스의 멤버를 ACL 그룹에서 삭제(운영진,정회원,준회원) **/
					memberDao.deleteUserGroup(userGroupMap);

					User memberUser = new User();
					memberUser.setUserId(memberId);
					memberUser.setGroupId(memberGroupId);
					memberUser.setRegisterId(user.getUserId());
					memberUser.setRegisterName(user.getUserName());
					memberUser.setUpdaterId(user.getUserId());
					memberUser.setUpdaterName(user.getUserName());
					userDao.addUserToGroup(memberUser);
				}
			}
		}

	}

	/**
	 * 관리자의 신규 준회원 등록
	 * 
	 * @param workspaceId
	 * @param associateIds
	 * @param user
	 */
	public void addAssociate(String workspaceId, List<String> associateIds, User user) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("workspaceId", workspaceId);
		map.put("groupName", "준회원");

		// 준회원 그룹 가져오기
		String associateGroupId = memberDao.getEvGroup(map);

		String activityUserId = null;
		// int totalCnt =0;
		for (String associateId : associateIds) {
			if (activityUserId == null) {
				activityUserId = associateId;
			}
			// totalCnt++;
			Member isMember = new Member();
			isMember.setWorkspaceId(workspaceId);
			isMember.setMemberId(associateId);
			isMember = memberDao.get(isMember);

			Member member = new Member();
			if (isMember == null) {
				/**
				 * 기존 회원이 없으면 신규 준회원 등록
				 */
				member.setWorkspaceId(workspaceId);
				member.setMemberId(associateId);
				member.setMemberIntroduction("준회원가입");
				member.setMemberLevel(WorkspaceConstants.MEMBER_STATUS_ASSOCIATE);
				member.setJoinType(WorkspaceConstants.MEMBER_JOIN_TYPE_ADM);
				member.setRegisterId(user.getUserId());
				member.setRegisterName(user.getUserName());
				member.setUpdaterId(user.getUserId());
				member.setUpdaterName(user.getUserName());
				memberDao.create(member);

				/**
				 * 권한등록(신규 준회원)
				 */
				User memberUser = new User();
				memberUser.setUserId(associateId);
				memberUser.setGroupId(associateGroupId);
				memberUser.setRegisterId(user.getUserId());
				memberUser.setRegisterName(user.getUserName());
				memberUser.setUpdaterId(user.getUserId());
				memberUser.setUpdaterName(user.getUserName());
				userDao.addUserToGroup(memberUser);
			} else {
				/**
				 * 기존회원이 있으며 가입대기 회원을 준회원으로 등급조정
				 */
				if (isMember.getMemberLevel().equals(WorkspaceConstants.MEMBER_STATUS_WAIT)) {
					member.setWorkspaceId(workspaceId);
					member.setMemberId(associateId);
					member.setMemberLevel(WorkspaceConstants.MEMBER_STATUS_ASSOCIATE);
					member.setUpdateType("level");// 등급변경
					member.setUpdaterId(user.getUserId());
					member.setUpdaterName(user.getUserName());
					memberDao.update(member);

					/**
					 * 권한등록(변경 준회원)
					 */
					Map<String, String> userGroupMap = new HashMap<String, String>();
					userGroupMap.put("workspaceId", member.getWorkspaceId());
					userGroupMap.put("memberId", associateId);

					/** 기존 멤버를 해당 워크스페이스의 멤버를 ACL 그룹에서 삭제(운영진,정회원,준회원) **/
					memberDao.deleteUserGroup(userGroupMap);

					User memberUser = new User();
					memberUser.setUserId(associateId);
					memberUser.setGroupId(associateGroupId);
					memberUser.setRegisterId(user.getUserId());
					memberUser.setRegisterName(user.getUserName());
					memberUser.setUpdaterId(user.getUserId());
					memberUser.setUpdaterName(user.getUserName());
					userDao.addUserToGroup(memberUser);
				}
			}
		}

	}

	/**
	 * 신규 회원 등록 ( 관리자 )
	 */
	public void addNewMember(MemberSearchCondition searchCondition, User user, List<String> memberIds,
			List<String> associateIds) {

		String activityUserId = null;
		int totalCnt = 0;

		Workspace workspace = new Workspace();
		workspace.setPortalId(searchCondition.getPortalId());
		workspace.setWorkspaceId(searchCondition.getWorkspaceId());
		workspace = workspaceService.read(workspace);

		/**
		 * 정회원 등록
		 */
		if (memberIds != null) {
			addMember(searchCondition.getWorkspaceId(), memberIds, user);
			for (String memberId : memberIds) {
				if (activityUserId == null) {
					activityUserId = memberId;
				}
				totalCnt++;

				// activity Member Join
				activityStreamService.createForMember(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
						IKepConstant.ACTIVITY_CODE_JOIN, workspace.getWorkspaceId(), workspace.getWorkspaceName(),
						activityUserId);

			}
		}
		/**
		 * 준회원 등록
		 */
		if (associateIds != null) {
			addAssociate(searchCondition.getWorkspaceId(), associateIds, user);
			for (String associateId : associateIds) {
				if (activityUserId == null) {
					activityUserId = associateId;
				}
				totalCnt++;

				// activity Member Join
				activityStreamService.createForMember(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
						IKepConstant.ACTIVITY_CODE_JOIN, workspace.getWorkspaceId(), workspace.getWorkspaceName(),
						activityUserId);

			}

		}

	}

	/**
	 * 회원 정보조회
	 */
	public Member read(Member object) {
		return memberDao.get(object);
	}
	
	/**
	 * 회원 정보조회(동맹)
	 */
	public Member readForAlliance(Member object) {
		return memberDao.getForAlliance(object);
	}

	/**
	 * 회원 존재유무 확인
	 */
	public boolean exists(Member object) {
		return memberDao.exists(object);
	}

	/**
	 * 회원 정보 수정
	 */
	public void update(Member object) {
		memberDao.update(object);
	}

	/**
	 * 해당 사용자의 디폴트 Workspace 변경 처리
	 */
	public void updateWorkspaceDefaultSet(String portalId, User user, String workspaceId) {

		Member member = new Member();
		member.setPortalId(portalId);
		member.setUpdaterId(user.getUserId());
		member.setUpdaterName(user.getUserName());
		member.setMemberId(user.getUserId());
		member.setUpdateType("defaultInit");
		memberDao.update(member);

		member = new Member();

		member.setWorkspaceId(workspaceId);
		member.setMemberId(user.getUserId());
		member.setIsDefault(1);
		member.setUpdateType("defaultSet");
		member.setUpdaterId(user.getUserId());
		member.setUpdaterName(user.getUserName());
		memberDao.update(member);

	}

	/**
	 * 회원 등급 정보 수정
	 */
	public void updateMemberLevel(MemberSearchCondition searchCondition, List<String> memberIds, User user) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("workspaceId", searchCondition.getWorkspaceId());
		if (searchCondition.getMemberLevel().equals("1") || searchCondition.getMemberLevel().equals("2")) {
			map.put("groupName", "운영진");
		} else if (searchCondition.getMemberLevel().equals("3")) {
			map.put("groupName", "정회원");
		} else {
			map.put("groupName", "준회원");
		}
		// 해당 그룹 가져오기
		String groupId = memberDao.getEvGroup(map);

		try {
			if (memberIds != null) {
				for (int i = 0; i < memberIds.size(); i++) {
					Member member = new Member();

					String memberId = memberIds.get(i);

					member.setWorkspaceId(searchCondition.getWorkspaceId());
					member.setMemberId(memberId);
					member.setMemberLevel(searchCondition.getMemberLevel());
					member.setUpdaterId(user.getUserId());
					member.setUpdaterName(user.getUserName());
					member.setUpdateType(searchCondition.getUpdateType());
					memberDao.update(member);

					// 회원/준회원 승인 , 가입 반려시
					if (searchCondition.getListType() != null && searchCondition.getListType().equals("Join")) {
						if (searchCondition.getMemberLevel().equals("3")
								|| searchCondition.getMemberLevel().equals("4")
								|| searchCondition.getMemberLevel().equals("6")) {
							sendMail(member, user, searchCondition.getMemberLevel());
						}
					}

					/**
					 * 권한등록(변경)
					 */
					Map<String, String> userGroupMap = new HashMap<String, String>();
					userGroupMap.put("workspaceId", member.getWorkspaceId());
					userGroupMap.put("memberId", memberId);

					/** 기존 멤버를 해당 워크스페이스의 멤버를 ACL 그룹에서 삭제(운영진,정회원,준회원) **/

					memberDao.deleteUserGroup(userGroupMap);

					User memberUser = new User();

					memberUser.setUserId(memberId);
					memberUser.setGroupId(groupId);
					memberUser.setRegisterId(user.getUserId());
					memberUser.setRegisterName(user.getUserName());
					memberUser.setUpdaterId(user.getUserId());
					memberUser.setUpdaterName(user.getUserName());
					userDao.addUserToGroup(memberUser);

				}
				// createAclUserGroup(searchCondition.getPortalId(),user,"100000143629",
				// memberIds);
			}

		} catch (Exception exception) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("\r\nWorkspace 회원 등급 변경, 사용자 권한 삭제, 사용자 권한  등록 실패...... ");

			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			this.log.error(exception.getStackTrace());
		}
	}

	/**
	 * 시샵 정보 변경
	 */
	public Member updateSysop(MemberSearchCondition searchCondition, List<String> memberIds, User user) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("workspaceId", searchCondition.getWorkspaceId());
		map.put("groupName", "운영진");

		// 해당 그룹 가져오기
		String groupId = memberDao.getEvGroup(map);

		Member member = new Member();
		/**
		 * 기존 시샵은 운영진으로 변경
		 */
		member.setWorkspaceId(searchCondition.getWorkspaceId());
		member.setMemberLevel(WorkspaceConstants.MEMBER_STATUS_ADMIN);
		member.setUpdaterId(user.getUserId());
		member.setUpdaterName(user.getUserName());
		member.setUpdateType("manage");
		memberDao.update(member);

		/**
		 * 신규 시샵 지정
		 */
		for (int i = 0; i < memberIds.size(); i++) {
			member = new Member();

			String memberId = memberIds.get(i);

			member.setWorkspaceId(searchCondition.getWorkspaceId());
			member.setMemberId(memberId);
			member.setMemberLevel(searchCondition.getMemberLevel());
			member.setUpdaterId(user.getUserId());
			member.setUpdaterName(user.getUserName());
			member.setUpdateType("level");
			memberDao.update(member);

			/**
			 * 권한등록(변경)
			 */
			Map<String, String> userGroupMap = new HashMap<String, String>();
			userGroupMap.put("workspaceId", member.getWorkspaceId());
			userGroupMap.put("memberId", memberId);

			/** 기존 멤버를 해당 워크스페이스의 멤버를 ACL 그룹에서 삭제(운영진,정회원,준회원) **/

			memberDao.deleteUserGroup(userGroupMap);

			User memberUser = new User();

			memberUser.setUserId(memberId);
			memberUser.setGroupId(groupId);
			memberUser.setRegisterId(user.getUserId());
			memberUser.setRegisterName(user.getUserName());
			memberUser.setUpdaterId(user.getUserId());
			memberUser.setUpdaterName(user.getUserName());
			userDao.addUserToGroup(memberUser);

		}
		/**
		 * 현재 나의 멤버역할 확인 시샵인 경우 관리메뉴 멤버 목록 시샵이 아닌경우 Workspace 메인으로 분기
		 */
		member = new Member();
		member.setWorkspaceId(member.getWorkspaceId());
		member.setMemberId(user.getUserId());
		member = memberDao.get(member);
		return member;
	}

	/**
	 * 회원 영구 삭제
	 */
	public void physicalDelete(Member object) {

		memberDao.physicalDelete(object);

		Map<String, String> map = new HashMap<String, String>();
		map.put("workspaceId", object.getWorkspaceId());
		map.put("memberId", object.getMemberId());

		/** 멤버 삭제시 해당 워크스페이스의 멤버를 ACL 그룹에서 삭제(운영진,정회원,준회원) **/

		memberDao.deleteUserGroup(map);
	}

	/**
	 * 멤버의 워크스페이스 삭제
	 */
	public void physicalDelete(List<String> workspaceIds, User user) {

		Workspace workspace = new Workspace();
		workspace.setPortalId(user.getPortalId());

		String activityUserId = null;
		int totalCnt = 0;
		for (int i = 0; i < workspaceIds.size(); i++) {
			totalCnt++;

			Member member = new Member();
			String workspaceId = workspaceIds.get(i);
			member.setWorkspaceId(workspaceId);
			member.setMemberId(user.getUserId());

			workspace.setWorkspaceId(workspaceId);

			workspace = workspaceService.read(workspace);

			member = memberDao.get(member);

			if (activityUserId == null) {
				activityUserId = member.getMemberId();
			}
			if (member.getMemberLevel().equals("1") && workspace.getWorkspaceStatus().equals("O") && workspace != null
					&& (workspace.getWorkspaceStatus().equals("O") || workspace.getWorkspaceStatus().equals("WC"))) { // 시샵은
																														// 제외
				continue;
			}
			memberDao.physicalDelete(member);

			Map<String, String> map = new HashMap<String, String>();
			map.put("workspaceId", workspaceId);
			map.put("memberId", user.getUserId());

			/** 멤버 삭제시 해당 워크스페이스의 멤버를 ACL 그룹에서 삭제(운영진,정회원,준회원) **/

			memberDao.deleteUserGroup(map);

			// activity Member Quit
			activityStreamService.createForMember(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
					IKepConstant.ACTIVITY_CODE_QUIT, workspace.getWorkspaceId(), workspace.getWorkspaceName(),
					activityUserId);

		}

	}

	/**
	 * 워크스페이스의 멤버삭제
	 */
	public void physicalDelete(String workspaceId, List<String> memberIds) {

		String activityUserId = null;

		Workspace workspace = new Workspace();

		workspace = workspaceService.getWorkspace(workspaceId);

		for (int i = 0; i < memberIds.size(); i++) {

			Member member = new Member();
			String memberId = memberIds.get(i);
			member.setWorkspaceId(workspaceId);
			member.setMemberId(memberId);

			if (activityUserId == null) {
				activityUserId = memberId;
			}
			memberDao.physicalDelete(member);

			Map<String, String> map = new HashMap<String, String>();
			map.put("workspaceId", workspaceId);
			map.put("memberId", memberId);

			/** 멤버 삭제시 해당 워크스페이스의 멤버를 ACL 그룹에서 삭제(운영진,정회원,준회원) **/

			memberDao.deleteUserGroup(map);

			// activity Member Quit
			activityStreamService.createForMember(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
					IKepConstant.ACTIVITY_CODE_QUIT, workspace.getWorkspaceId(), workspace.getWorkspaceName(),
					activityUserId);

		}

	}

	/**
	 * Ws 삭제
	 */
	public void physicalDeleteMemberByWorkspace(String wokspaceId) {

		memberDao.physicalDeleteMemberByWorkspace(wokspaceId);

		Map<String, String> map = new HashMap<String, String>();
		map.put("workspaceId", wokspaceId);

		/** 멤버 삭제시 해당 워크스페이스의 멤버를 ACL 그룹에서 삭제(운영진,정회원,준회원) **/

		memberDao.deleteUserGroup(map);

	}

	/**
	 * 세션 정보 얻기
	 * 
	 * @param name
	 * @return
	 */
	private Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}

	/**
	 * 회원 가입/회원승인/가입반려 메일발송
	 * 
	 * @param member
	 * @param user
	 * @param memberLevel
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sendMail(Member member, User user, String memberLevel) {

		Mail mail = new Mail();
		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		if (user.getLocaleCode().toUpperCase().equals("KO")) {
			mail.setMailTemplatePath("workspaceMemberTemplate.vm");
		} else {
			mail.setMailTemplatePath("workspaceMemberEnglishTemplate.vm");
		}

		// 발신자
		User sender = new User();
		// 발신자 -> 개설 신청자,폐쇄신청자,승인,폐쇄처리자
		sender = user;

		// 수신자
		List recipients = new ArrayList();
		HashMap<String, String> recip;

		member = memberDao.get(member);

		if (memberLevel.equals("5")) { // 회원 가입신청
			// 수신자 --> 시샵
			Member sysopMember = memberDao.getSysop(member.getWorkspaceId());

			recip = new HashMap<String, String>();
			recip.put("email", sysopMember.getMailId());
			if (user.getLocaleCode().toUpperCase().equals("KO")) {
				recip.put("name", sysopMember.getMemberName());
				// 메일 제목
				mail.setTitle(member.getWorkspaceName() + " 워크스페이스 회원가입 승인 요청 ");
			} else {
				recip.put("name", sysopMember.getMemberEnglishName());
				// 메일 제목
				mail.setTitle(member.getWorkspaceName() + " Register approved the request workspace");
			}
			recipients.add(recip);
			mail.setToEmailList(recipients);

		} else if (memberLevel.equals("6")) { // 회원 가입 반려
			// 수신자 --> 회원가입 신청자

			recip = new HashMap<String, String>();
			recip.put("email", member.getMailId());
			if (user.getLocaleCode().toUpperCase().equals("KO")) {
				recip.put("name", member.getMemberName());
				// 메일 제목
				mail.setTitle(member.getWorkspaceName() + " 워크스페이스 회원가입이 반려되었습니다.");
			} else {
				recip.put("name", member.getMemberEnglishName());
				// 메일 제목
				mail.setTitle(member.getWorkspaceName() + " Workspace has been companion registration. ");
			}
			recipients.add(recip);
			mail.setToEmailList(recipients);

		} else if (memberLevel.equals("3") // 정회원/준회원 승인
				|| memberLevel.equals("4")) {

			// 수신자 --> 회원가입 신청자
			recip = new HashMap<String, String>();

			recip.put("email", member.getMailId());
			if (user.getLocaleCode().toUpperCase().equals("KO")) {
				recip.put("name", member.getMemberName());
				// 메일 제목
				mail.setTitle(member.getWorkspaceName() + " 워크스페이스 회원가입이 승인되었습니다.");
			} else {
				recip.put("name", member.getMemberEnglishName());
				// 메일 제목
				mail.setTitle(member.getWorkspaceName() + " Register your workspace has been approved. ");
			}
			recipients.add(recip);
			mail.setToEmailList(recipients);
		}

		Map dataMap = new HashMap();
		dataMap.put("member", member);
		dataMap.put("user", user);
		dataMap.put("memberLevel", memberLevel);
		dataMap.put("registDate", DateUtil.getToday(""));
		dataMap.put("sender", sender);

		/** 메일발송 임시 미사용, 메일 발송 오류로인한 **/
		mailSendService.sendMail(mail, dataMap, sender);

	}
}
