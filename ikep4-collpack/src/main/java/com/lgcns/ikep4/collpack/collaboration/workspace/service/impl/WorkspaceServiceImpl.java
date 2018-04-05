/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.workspace.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.collpack.collaboration.board.announce.model.AnnounceItem;
import com.lgcns.ikep4.collpack.collaboration.board.announce.service.AnnounceItemService;
import com.lgcns.ikep4.collpack.collaboration.board.board.model.Board;
import com.lgcns.ikep4.collpack.collaboration.board.board.model.BoardItem;
import com.lgcns.ikep4.collpack.collaboration.board.board.service.BoardAdminService;
import com.lgcns.ikep4.collpack.collaboration.board.board.service.BoardItemService;
import com.lgcns.ikep4.collpack.collaboration.board.weekly.model.WeeklyItem;
import com.lgcns.ikep4.collpack.collaboration.board.weekly.service.WeeklyItemService;
import com.lgcns.ikep4.collpack.collaboration.member.dao.MemberDao;
import com.lgcns.ikep4.collpack.collaboration.member.model.Member;
import com.lgcns.ikep4.collpack.collaboration.member.service.MemberService;
import com.lgcns.ikep4.collpack.collaboration.workspace.dao.WorkspaceDao;
import com.lgcns.ikep4.collpack.collaboration.workspace.dao.WorkspacePortletDao;
import com.lgcns.ikep4.collpack.collaboration.workspace.dao.WorkspacePortletLayoutDao;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspaceConstants;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspacePortlet;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspacePortletLayout;
import com.lgcns.ikep4.collpack.collaboration.workspace.search.WorkspaceSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspaceService;
import com.lgcns.ikep4.collpack.collaboration.workspaceMap.dao.WorkspaceMapAdminDao;
import com.lgcns.ikep4.collpack.collaboration.workspaceMap.model.WorkspaceMap;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.group.dao.GroupDao;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.tagfree.util.MimeUtil;

/**
 * Workspace Service 구현 클래스
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: WorkspaceServiceImpl.java 16487 2011-09-06 01:34:40Z giljae $
 */
@Service("workspaceService")
public class WorkspaceServiceImpl extends GenericServiceImpl<Workspace, String> implements WorkspaceService {

	@Autowired
	private WorkspaceDao workspaceDao;

	@Autowired
	private MemberService memberService;

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private IdgenService idgenService;

	@Autowired
	private TagService tagService;

	@Autowired
	private FileService fileService;

	@Autowired
	private WorkspaceMapAdminDao workspaceMapAdminDao;

	@Autowired
	public WorkspacePortletLayoutDao workspacePortletLayoutDao;

	@Autowired
	private BoardAdminService boardAdminService;

	@Autowired
	private MailSendService mailSendService;

	@Autowired
	ACLService aclService;

	@Autowired
	private BoardItemService boardItemService;

	@Autowired
	WeeklyItemService weeklyItemService;

	@Autowired
	private AnnounceItemService announceItemService;
	@Autowired
	public WorkspacePortletDao workspacePortletDao;
	
	private static final String DEFAULT_BOARD_ROOT_ID = "0";

	@Autowired
	protected MessageSource messageSource = null;

	private static final String MESSAGE_MAIL_CREATE = "message.collpack.collaboration.workspace.mail.create";

	private static final String MESSAGE_MAIL_CLOSE = "message.collpack.collaboration.workspace.mail.close";

	private static final String MESSAGE_MAIL_APPROVED = "message.collpack.collaboration.workspace.mail.approved";

	private static final String MESSAGE_MAIL_REQUEST = "message.collpack.collaboration.workspace.mail.request";

	private static final String MESSAGE_MAIL_REQUEST_REJECT = "message.collpack.collaboration.workspace.mail.requestReject";

	private static final String MESSAGE_MAIL_CLOSE_REJECT = "message.collpack.collaboration.workspace.mail.closeReject";

	private static Map<String, String> messageCodes = new HashMap<String, String>();
	static {
		messageCodes.put("create", MESSAGE_MAIL_CREATE);
		messageCodes.put("close", MESSAGE_MAIL_CLOSE);
	}

	private static Map<String, String> statusCodes = new HashMap<String, String>();
	static {
		statusCodes.put("approved", MESSAGE_MAIL_APPROVED);
		statusCodes.put("request", MESSAGE_MAIL_REQUEST);
		statusCodes.put("requestReject", MESSAGE_MAIL_REQUEST_REJECT);
		statusCodes.put("closeReject", MESSAGE_MAIL_CLOSE_REJECT);
	}

	/**
	 * workspace 목록
	 */
	public SearchResult<Workspace> listBySearchCondition(WorkspaceSearchCondition searchCondition) {

		Integer count = this.workspaceDao.countBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<Workspace> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Workspace>(searchCondition);

		} else {

			List<Workspace> list = this.workspaceDao.listBySearchCondition(searchCondition);

			// System.out.println(list.size());

			for (int i = 0; i < list.size(); i++) {

				Workspace workspace = list.get(i);

				List<Tag> tagList = tagService.listTagByItemId(workspace.getWorkspaceId(),
						WorkspaceConstants.ITEM_TYPE_NAME);
				if (tagList != null && tagList.size() > 0) {
					workspace.setTagList(tagList);

					list.set(i, workspace);
				}
			}
			searchResult = new SearchResult<Workspace>(list, searchCondition);
		}

		return searchResult;
	}

	/**
	 * 나의 WS 목록
	 */
	public SearchResult<Workspace> listBySearchConditionPersonal(WorkspaceSearchCondition searchCondition) {

		Integer count = this.workspaceDao.countBySearchConditionPersonal(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<Workspace> searchResult = null;

		// MyColl 목록인 경우 페이징 처리 없애기
		if (searchCondition.getListType() == null || searchCondition.getListType().equals("")) {
			searchCondition.setStartRowIndex(0);
			searchCondition.setEndRowIndex(count);
		}
		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Workspace>(searchCondition);

		} else {

			List<Workspace> list = this.workspaceDao.listBySearchConditionPersonal(searchCondition);

			// System.out.println(list.size());

			for (int i = 0; i < list.size(); i++) {

				Workspace workspace = list.get(i);

				List<Tag> tagList = tagService.listTagByItemId(workspace.getWorkspaceId(),
						WorkspaceConstants.ITEM_TYPE_NAME);
				if (tagList != null && tagList.size() > 0) {
					workspace.setTagList(tagList);

					list.set(i, workspace);
				}
			}
			searchResult = new SearchResult<Workspace>(list, searchCondition);
		}

		return searchResult;
	}

	/**
	 * 랜덤 WS 목록
	 */
	public List<Workspace> randomWorkspace(String portalId) {

		Integer count = this.workspaceDao.countRandomWorkspace(portalId);

		List<Workspace> workspaceList = new ArrayList<Workspace>();
		int randomCnt = 0;
		if (count > 4) {
			randomCnt = 4;
		} else {
			randomCnt = count;
		}
		for (int i = 0; i < randomCnt; i++) {
			int num = (int) Math.round(Math.random() * (count - 1)) + 1;

			Map<String, String> map = new HashMap<String, String>();
			map.put("portalId", portalId);
			map.put("rownum", num + "");

			Workspace workspace = this.workspaceDao.randomWorkspace(map);

			if (workspace != null) {
				List<FileData> fileDataList = fileService.getItemFile(workspace.getWorkspaceId(), "N");
				workspace.setFileDataList(fileDataList);
				workspaceList.add(workspace);
			}

		}
		return workspaceList;

	}

	/**
	 * 동맹 요청 목록
	 */
	public List<Workspace> listWorkspaceByType(Map<String, String> map) {
		return workspaceDao.listWorkspaceByType(map);
	}

	/**
	 * 카테고리별 WS 목록
	 */
	public List<Workspace> listWorkspaceByCategory(Map<String, String> map) {
		return workspaceDao.listWorkspaceByCategory(map);
	}

	/**
	 * 검색 WS 목록
	 */
	public List<Workspace> listWorkspaceBySearch(Map<String, String> map) {
		return workspaceDao.listWorkspaceBySearch(map);
	}
	/**
	 * 나의 Workspace 목록
	 */
	public List<Workspace> listMyCollaboration(Map<String, String> map) {
		return workspaceDao.listMyCollaboration(map);
	}
	
	public List<Workspace> listMyScheduleCollaboration(Map<String, String> map) {
		//return workspaceDao.listMyCollaboration(map);
		return workspaceDao.listMyScheduleCollaboration(map);
	}
	
	/**
	 * 나의 Workspace 목록 for Mobile
	 */
	public List<Workspace> listMyCollaborationForMobile(Map<String, String> map) {
		if("1".equals(map.get("isPlanner"))) {
			
			List<Workspace> list = workspaceDao.listMyScheduleCollaboration(map);
			List<Workspace> teamList = this.workspaceDao.getTeamPlannerMenuList(map.get("memberId"));
			//부서 변경시 신규 부서로 팀 설정
			for(Workspace teamspace1 : list) {
				for(Workspace teamspace2 : teamList) {
					if(teamspace2.getWorkspaceId().length() > 2){
						if(!teamspace1.getWorkspaceId().equals(teamspace2.getWorkspaceId())) {
							map.put("oldItemId", teamspace2.getWorkspaceId());
							map.put("itemId",teamspace1.getWorkspaceId());
							//지우고,
							this.workspaceDao.deleteMovePlannerMenuList(map);
							//등록
							this.workspaceDao.insertMovePlannerMenuList(map);
						}
					}
				}
			}
			
			return workspaceDao.listPlannerTeamForMobile(map);
		} else {
			return workspaceDao.listMyCollaborationForMobile(map);
		}
		
	}

	/**
	 * 신규 Workspace 목록
	 */
	public List<Workspace> listNewCollaboration(Map<String, String> map) {
		List<Workspace> workspaceList = workspaceDao.listNewCollaboration(map);
		for (int i = 0; i < workspaceList.size(); i++) {
			if (i == 0) {
				Workspace tmpWorkspace = new Workspace();
				tmpWorkspace = workspaceList.get(i);

				List<FileData> fileDataList = fileService.getItemFile(tmpWorkspace.getWorkspaceId(), "N");
				tmpWorkspace.setFileDataList(fileDataList);

				workspaceList.set(i, tmpWorkspace);
				break;
			}
		}
		return workspaceList;
	}

	/**
	 * 운영자 이상의 Workapce 목록
	 */
	public List<Workspace> listWorkspaceManager(Map<String, String> map) {
		return workspaceDao.listWorkspaceManager(map);
	}

	/**
	 * 조직타입의 gkdnl Workapce 목록
	 */
	public SearchResult<Workspace> listBySearchConditionGroupHierarchy(WorkspaceSearchCondition searchCondition) {
		Integer count = this.workspaceDao.countBySearchConditionGroupHierarchy(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<Workspace> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Workspace>(searchCondition);
		} else {

			List<Workspace> list = this.workspaceDao.listBySearchConditionGroupHierarchy(searchCondition);

			searchResult = new SearchResult<Workspace>(list, searchCondition);
		}

		return searchResult;
	}

	/**
	 * workspace 미개설 조직정보리스트
	 */
	public SearchResult<Workspace> listBySearchConditionGroup(WorkspaceSearchCondition searchCondition) {
		Integer count = this.workspaceDao.countBySearchConditionGroup(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<Workspace> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Workspace>(searchCondition);
		} else {

			List<Workspace> list = this.workspaceDao.listBySearchConditionGroup(searchCondition);

			searchResult = new SearchResult<Workspace>(list, searchCondition);
		}

		return searchResult;
	}

	/**
	 * 그룹 조직원 정보
	 */
	public List<Workspace> listGroupMembers(String groupId) {
		return workspaceDao.listGroupMembers(groupId);
	}

	/**
	 * workspace 폐쇄대기
	 */
	public SearchResult<Workspace> listBySearchConditionCloseGroup(WorkspaceSearchCondition searchCondition) {

		Integer count = this.workspaceDao.countBySearchConditionCloseGroup(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<Workspace> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Workspace>(searchCondition);
		} else {

			List<Workspace> list = this.workspaceDao.listBySearchConditionCloseGroup(searchCondition);

			searchResult = new SearchResult<Workspace>(list, searchCondition);
		}
		return searchResult;
	}

	/**
	 * 타입별 Workspace 갯수
	 */
	public List<Workspace> countWorkspaceType(Map<String, String> map) {
		return workspaceDao.countWorkspaceType(map);
	}

	/**
	 * 타입별(팀) Workspace 갯수
	 */
	public List<Workspace> countWorkspaceTypeOrg(Map<String, String> map) {
		return workspaceDao.countWorkspaceTypeOrg(map);
	}

	/**
	 * Workspace 방문 기록 등록
	 */
	public void createWorkspaceVisit(Map<String, String> map) {

		boolean hasVisit = this.workspaceDao.existsWorkspaceVisit(map);

		if (!hasVisit) {
			this.workspaceDao.createWorkspaceVisit(map);
		}
	}

	/**
	 * 조직 WS 생성시 호출 workspace 생성
	 */
	public String createWorkspace(Workspace workspace, User user) {

		workspace.setWorkspaceId(idgenService.getNextId());
		this.workspaceDao.create(workspace);

		// WS Main Body Portlet 생성
		WorkspacePortlet workspacePortlet = new WorkspacePortlet();
		workspacePortlet.setIsDefault(1);
		List<WorkspacePortlet> list = workspacePortletDao.listAllPortlet(workspacePortlet);
		
		WorkspacePortletLayout workspacePortletLayout = new WorkspacePortletLayout();
		
		for(WorkspacePortlet defaultPortlet : list) {
			workspacePortletLayout.setPortletLayoutId(idgenService.getNextId());
			
			if( defaultPortlet.getSortOrder()<=4)
			{
				workspacePortletLayout.setColIndex(1);
				workspacePortletLayout.setRowIndex(defaultPortlet.getSortOrder()); // 1,2,3				
			} else {
				workspacePortletLayout.setColIndex(2);
				workspacePortletLayout.setRowIndex(defaultPortlet.getSortOrder() - 4); // 1,2,3			
			}
			workspacePortletLayout.setPortletId(defaultPortlet.getPortletId());
			workspacePortletLayout.setWorkspaceId(workspace.getWorkspaceId());

			workspacePortletLayout.setIsBoardPortlet(0);
			workspacePortletLayout.setBoardId("");
			workspacePortletLayoutDao.create(workspacePortletLayout);			
		}
		/*for (int i = 1; i <= 6; i++) {
			workspacePortletLayout.setPortletLayoutId(idgenService.getNextId());
				
			if (i <= 3) {
				workspacePortletLayout.setColIndex(1);
				workspacePortletLayout.setRowIndex(i); // 1,2,3
			} else {
				workspacePortletLayout.setColIndex(2);
				workspacePortletLayout.setRowIndex(i - 3); // 1,2
			}
			workspacePortletLayout.setPortletId("WS_PORTLET_0" + i);
			workspacePortletLayout.setWorkspaceId(object.getWorkspaceId());

			workspacePortletLayout.setIsBoardPortlet(0);
			workspacePortletLayout.setBoardId("");
			workspacePortletLayoutDao.create(workspacePortletLayout);
		}*/

		// Ws Map Root 생성
		WorkspaceMap workspaceMap = new WorkspaceMap();
		workspaceMap.setMapId(idgenService.getNextId());
		workspaceMap.setWorkspaceId(workspace.getWorkspaceId());
		workspaceMap.setMapParentId(workspaceMap.getMapId());
		workspaceMap.setMapName(workspace.getWorkspaceName() + "Map");
		workspaceMap.setMapDescription(workspaceMap.getMapName());
		workspaceMap.setRegisterId(workspace.getRegisterId());
		workspaceMap.setRegisterName(workspace.getRegisterName());
		workspaceMap.setUpdaterId(workspace.getUpdaterId());
		workspaceMap.setUpdaterName(workspace.getUpdaterName());
		workspaceMapAdminDao.createMapRoot(workspaceMap);

		return workspace.getWorkspaceId();
	}

	/**
	 * 팀 Workspace 배치 생성시 게시판 생성
	 * 
	 * @param workspaceId
	 * @param user
	 * @return
	 */
	public String createBoard(String workspaceId, User user) {
		// 게시판 생성
		Board board = new Board();

		board.setWorkspaceId(workspaceId);
		board.setBoardParentId(null);
		board.setBoardName("나도한마디");
		board.setDescription("나도한마디");

		board.setBoardType("0"); // 게시글
		board.setListType("0");
		board.setUrlPopup(0);
		board.setAnonymous(0);
		board.setRss(0);

		board.setFileSize(10 * 1024 * 1024L);// 10M
		board.setImageFileSize(1000 * 1024L); // 1000K
		board.setImageWidth(300);
		board.setPageNum(10);
		board.setDocPopup(0);

		board.setReply(1);
		board.setRestrictionType("exe^jsp^com");
		board.setPortalId(user.getPortalId());
		board.setSortOrder(0);
		board.setBoardRootId(DEFAULT_BOARD_ROOT_ID);

		board.setLinereply(1);
		board.setVersionManage(0);
		board.setWiki(0);
		board.setMove(1);

		board.setReadPermission("2");
		board.setWritePermission("2");
		board.setItemPermissionUpdate("1");

		board.setIsDelete(0);
		board.setRegisterId(user.getUserId());
		board.setRegisterName(user.getUserName());
		board.setUpdaterId(user.getUserId());
		board.setUpdaterName(user.getUserName());
		board.setAllowType("all");
		
		// board.setParentList(this.boardAdminService.listParentBoard(boardMap));
		this.boardAdminService.createBoard(board, user);
		
		board.setListType("3");
		board.setBoardName("사진앨범");
		board.setDescription("사진앨범");		
		this.boardAdminService.createBoard(board, user);
		
		board.setListType("0");
		board.setBoardName("최근소식");
		board.setDescription("최근소식");		
		return this.boardAdminService.createBoard(board, user);

	}

	/**
	 * Workspace 생성
	 */
	public String createWorkspace(String portalId, User user, Workspace workspace) {
		/**
		 * 1. workspace 등록 2. 시샵 등록 3. 회원 등록 4. 권한 그룹(운영진,정회원,준회원) 5. 운영진,정회원
		 * 그룹에 멤버 등록 5. 기본 게시판 등록
		 */

		try {

			workspace.setWorkspaceId(idgenService.getNextId());
			workspace.setRegisterId(user.getUserId());
			workspace.setRegisterName(user.getUserName());
			workspace.setUpdaterId(user.getUserId());
			workspace.setUpdaterName(user.getUserName());
			workspace.setPortalId(portalId);
			workspace.setWorkspaceStatus(WorkspaceConstants.WORKSPACE_STATUS_WAITING_OPEN);
			workspace.setOpenType(WorkspaceConstants.WORKSPACE_OPEN_TYPE_USER);

			this.workspaceDao.create(workspace);

			// 개설 신청 승인 요청 메일 발송
			sendMail("create", "", workspace, user, "WO");
			
			// WS Main Body Portlet 생성
			WorkspacePortlet workspacePortlet = new WorkspacePortlet();
			workspacePortlet.setIsDefault(1);
			List<WorkspacePortlet> list = workspacePortletDao.listAllPortlet(workspacePortlet);
			
			WorkspacePortletLayout workspacePortletLayout = new WorkspacePortletLayout();			
			for(WorkspacePortlet defaultPortlet : list) {
				workspacePortletLayout.setPortletLayoutId(idgenService.getNextId());
				
				if( defaultPortlet.getSortOrder()<=4)
				{
					workspacePortletLayout.setColIndex(1);
					workspacePortletLayout.setRowIndex(defaultPortlet.getSortOrder()); // 1,2,3				
				} else {
					workspacePortletLayout.setColIndex(2);
					workspacePortletLayout.setRowIndex(defaultPortlet.getSortOrder() - 4); // 1,2		
				}
				workspacePortletLayout.setPortletId(defaultPortlet.getPortletId());
				workspacePortletLayout.setWorkspaceId(workspace.getWorkspaceId());

				workspacePortletLayout.setIsBoardPortlet(0);
				workspacePortletLayout.setBoardId("");
				workspacePortletLayoutDao.create(workspacePortletLayout);			
			}
			/**WorkspacePortletLayout workspacePortletLayout = new WorkspacePortletLayout();
			for (int i = 1; i <= 6; i++) {
				workspacePortletLayout.setPortletLayoutId(idgenService.getNextId());
				if (i <= 3) {
					workspacePortletLayout.setColIndex(1);
					workspacePortletLayout.setRowIndex(i); // 1,2,3
				} else {
					workspacePortletLayout.setColIndex(2);
					workspacePortletLayout.setRowIndex(i - 3); // 1,2
				}
				workspacePortletLayout.setPortletId("WS_PORTLET_0" + i);
				workspacePortletLayout.setWorkspaceId(workspace.getWorkspaceId());

				workspacePortletLayout.setIsBoardPortlet(0);
				workspacePortletLayout.setBoardId("");
				workspacePortletLayoutDao.create(workspacePortletLayout);
			}**/
			// Ws Map Root 생성
			WorkspaceMap workspaceMap = new WorkspaceMap();
			workspaceMap.setMapId(idgenService.getNextId());
			workspaceMap.setWorkspaceId(workspace.getWorkspaceId());
			workspaceMap.setMapParentId(workspaceMap.getMapId());
			workspaceMap.setMapName(workspace.getWorkspaceName() + "Map");
			workspaceMap.setMapDescription(workspaceMap.getMapName());
			workspaceMap.setRegisterId(workspace.getRegisterId());
			workspaceMap.setRegisterName(workspace.getRegisterName());
			workspaceMap.setUpdaterId(workspace.getUpdaterId());
			workspaceMap.setUpdaterName(workspace.getUpdaterName());
			workspaceMapAdminDao.createMapRoot(workspaceMap);

			// 기본 게시판 생성

			// 시샵 멤버 등록
			createSysopMember(workspace, user);

			// WS 메인 그룹 생성
			createWsMainGroup(portalId, user, workspace);

			// ACL 운영진 그룹 + 시샵
			createAclGroup(portalId, user, workspace, "운영진", user.getUserId());

			// ACL 정회원 그룹 + 정회원멤버
			createAclGroup(portalId, user, workspace, "정회원", workspace.getMemberIds());
			// ACL 준회원 그룹 + 준회원 멤버
			createAclGroup(portalId, user, workspace, "준회원", workspace.getAssociateIds());

			// 개설시 추가회원 등록
			if (workspace.getMemberIds() != null) {
				createMember(workspace.getWorkspaceId(), user, workspace.getMemberIds());
			}
			// 이미지 등록
			if (workspace.getFileId() != null && !workspace.getFileId().equals("")) {
				fileService.createFileLink(workspace.getFileId(), workspace.getWorkspaceId(),
						WorkspaceConstants.ITEM_TYPE_NAME, user);
			}
			// Tag 등록
			if (workspace.getTag() != null && !workspace.getTag().equals("")) {
				createTag(workspace, user);
			}
			createBoard(workspace.getWorkspaceId(), user);

		} catch (Exception exception) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("\r\nWorkspace 생성실패...... ");

			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			this.log.error(exception.getStackTrace());
		}

		return workspace.getWorkspaceId();
	}

	/**
	 * 시샵 등록
	 * 
	 * @param workspace
	 * @param user
	 */
	public void createSysopMember(Workspace workspace, User user) {

		// 개설자 시샵 등록처리
		Member member = new Member();

		member.setWorkspaceId(workspace.getWorkspaceId());
		member.setMemberId(user.getUserId());
		member.setMemberIntroduction(" ");
		member.setMemberLevel(WorkspaceConstants.MEMBER_STATUS_SYSOP);
		member.setJoinType(WorkspaceConstants.MEMBER_JOIN_TYPE_ADM);
		member.setRegisterId(user.getUserId());
		member.setRegisterName(user.getUserName());
		member.setUpdaterId(user.getUserId());
		member.setUpdaterName(user.getUserName());
		memberDao.create(member);

	}

	/**
	 * 멤버 등록
	 * 
	 * @param workspaceId
	 * @param user
	 * @param memberIds
	 */
	public void createMember(String workspaceId, User user, List<String> memberIds) {

		// Map<String, String> map = new HashMap<String, String>();
		// map.put("workspaceId", workspaceId);
		// map.put("groupName", "정회원");
		// 정회원 그룹 가져오기
		// String groupId=memberDao.getEvGroup(map);

		Member sysopMember = memberDao.getSysop(workspaceId);

		for (String memberId : memberIds) {
			// 시샵인 경우 등록 제외
			if (sysopMember != null && memberId.equals(sysopMember.getMemberId())) {
				continue;
			}
			Member member = new Member();

			member.setWorkspaceId(workspaceId);
			member.setMemberId(memberId);

			member.setMemberIntroduction(" ");
			member.setMemberLevel(WorkspaceConstants.MEMBER_STATUS_MEMBER);
			member.setJoinType(WorkspaceConstants.MEMBER_JOIN_TYPE_ADM);

			member.setRegisterId(user.getUserId());
			member.setRegisterName(user.getUserName());
			member.setUpdaterId(user.getUserId());
			member.setUpdaterName(user.getUserName());

			memberDao.create(member);

		}
	}

	/**
	 * 메인 권한 그룹 등록
	 * 
	 * @param portalId
	 * @param user
	 * @param workspace
	 */
	public void createWsMainGroup(String portalId, User user, Workspace workspace) {

		// 최초 WS 생성시 WS ROOT 그룹(WS 동일)
		try {

			String parentGroupId = memberDao.getRootEvGroup(WorkspaceConstants.GROUP_TYPE_ID);

			Group group = new Group();

			group.setGroupId(workspace.getWorkspaceId());
			group.setPortalId(portalId);
			group.setGroupName(workspace.getWorkspaceName());
			group.setParentGroupId(parentGroupId);
			group.setGroupTypeId(WorkspaceConstants.GROUP_TYPE_ID);
			group.setChildGroupCount("0");
			group.setSortOrder("0");
			group.setRegisterId(user.getUserId());
			group.setRegisterName(user.getUserName());
			group.setUpdaterId(user.getUserId());
			group.setUpdaterName(user.getUserName());
			group.setViewOption("0");
			group.setFullPath(workspace.getWorkspaceName());

			groupDao.create(group);

		} catch (Exception exception) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("\r\nWorkspace 권한 그룹 메인 생성 실패...... ");

			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			this.log.error(exception.getStackTrace());
		}
	}

	/**
	 * 권한 그룹 등록
	 * 
	 * @param portalId
	 * @param user
	 * @param workspace
	 * @param groupName
	 * @param userId
	 */
	public void createAclGroup(String portalId, User user, Workspace workspace, String groupName, String userId) {

		// 최초 WS 생성시 회원을 정회원 그룹으로 권한 설정
		// 워크스페이스 권한 그룹
		try {

			Group group = new Group();

			group.setGroupId(idgenService.getNextId());
			group.setPortalId(portalId);
			group.setGroupName(groupName);
			group.setParentGroupId(workspace.getWorkspaceId());
			group.setGroupTypeId(WorkspaceConstants.GROUP_TYPE_ID);
			group.setChildGroupCount("0");
			group.setSortOrder("0");
			group.setRegisterId(user.getUserId());
			group.setRegisterName(user.getUserName());
			group.setUpdaterId(user.getUserId());
			group.setUpdaterName(user.getUserName());
			group.setViewOption("0");
			group.setFullPath("|" + workspace.getWorkspaceName() + "|" + groupName);

			groupDao.create(group);
			groupDao.updateChildGroupCount(workspace.getWorkspaceId(), "plus");
			
			// 운영진 매핑 등록 ( 팀 Coll 관리자가 생성시 관리자는 운영진등록 제외)
			/**
			if (userId == null || userId.equals("")) {
				user.setGroupId(group.getGroupId());
				userDao.addUserToGroup(user);
			} else {
				
			}
			**/
			if(userId !=null && !userId.equals("")) {
				User memberUser = new User();

				memberUser.setUserId(userId);
				memberUser.setGroupId(group.getGroupId());
				memberUser.setRegisterId(user.getUserId());
				memberUser.setRegisterName(user.getUserName());
				memberUser.setUpdaterId(user.getUserId());
				memberUser.setUpdaterName(user.getUserName());
				userDao.addUserToGroup(memberUser);				
			}

		} catch (Exception exception) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("\r\nWorkspace 권한 그룹 생성 실패...... ");

			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			this.log.error(exception.getStackTrace());
		}
	}

	/**
	 * 권한 그룹 등록
	 * 
	 * @param portalId
	 * @param user
	 * @param workspace
	 * @param groupName
	 * @param memberIds
	 */
	public void createAclGroup(String portalId, User user, Workspace workspace, String groupName, List<String> memberIds) {

		// 최초 WS 생성시 회원을 정회원 그룹으로 권한 설정
		// 워크스페이스 권한 그룹
		try {

			Group group = new Group();

			group.setGroupId(idgenService.getNextId());
			group.setPortalId(portalId);
			group.setGroupName(groupName);
			group.setParentGroupId(workspace.getWorkspaceId());
			group.setGroupTypeId(WorkspaceConstants.GROUP_TYPE_ID);
			group.setChildGroupCount("0");
			group.setSortOrder("0");
			group.setRegisterId(user.getUserId());
			group.setRegisterName(user.getUserName());
			group.setUpdaterId(user.getUserId());
			group.setUpdaterName(user.getUserName());
			group.setViewOption("0");
			group.setFullPath("|" + workspace.getWorkspaceName() + "|" + groupName);

			groupDao.create(group);

			groupDao.updateChildGroupCount(workspace.getWorkspaceId(), "plus");

			// 그룹 권한별 사용자 등록 (IKEP4_EV_USER_GROUP)
			if (memberIds != null) {
				for (String memberId : workspace.getMemberIds()) {
					User memberUser = new User();

					memberUser.setUserId(memberId);
					memberUser.setGroupId(group.getGroupId());
					memberUser.setRegisterId(user.getUserId());
					memberUser.setRegisterName(user.getUserName());
					memberUser.setUpdaterId(user.getUserId());
					memberUser.setUpdaterName(user.getUserName());
					userDao.addUserToGroup(memberUser);
				}
			}

		} catch (Exception exception) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("\r\nWorkspace 권한 그룹 및 권한 사용자 생성 실패...... ");

			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			this.log.error(exception.getStackTrace());
		}
	}

	/**
	 * 그룹 타입의 Workspace 생성
	 */
	public void createOrgWorkspace(User user, List<String> groupIds) {
		// TODO Auto-generated method stub

		String portalId = user.getPortalId();

		User sysopUser = new User();

		/* 워크스페이스 생성 */
		for (int i = 0; i < groupIds.size(); i++) {
			// 해당 그룹아이디의 Workspace가 존재하는지 체크
			// 존재한다면 Continue

			// 존재하지 않는다면..Insert
			Workspace workspace = new Workspace();

			// 그룹 리더 정보
			Group group = groupDao.get(groupIds.get(i));

			if (group.getLeaderId() != null && !group.getLeaderId().equals("")) {
				sysopUser = userDao.get(group.getLeaderId());
				// 시샵이 있는경우 시샵이 생성한것으로 처리
				if (sysopUser != null && sysopUser.getUserId() != null) {
					user = sysopUser;
				}
			}
			String description = group.getGroupName() + "의 Workspace 입니다.";

			workspace.setTypeId("0");
			workspace.setPortalId(portalId);
			workspace.setWorkspaceName(group.getGroupName());
			workspace.setTeamId(groupIds.get(i));
			workspace.setDescription(description);
			workspace.setRegisterId(user.getUserId());
			workspace.setRegisterName(user.getUserName());
			workspace.setUpdaterId(user.getUserId());
			workspace.setUpdaterName(user.getUserName());
			
			workspace.setWorkspaceStatus(WorkspaceConstants.WORKSPACE_STATUS_OPEN);
			workspace.setOpenType(WorkspaceConstants.WORKSPACE_OPEN_TYPE_ADM);

			String workspaceId = createWorkspace(workspace, user); // insertWorkspace

			workspace.setWorkspaceId(workspaceId);

			/* 부서조직원 정보 set */
			List<Workspace> listGroupMembers = listGroupMembers(groupIds.get(i));

			/** 맴버정보 **/
			// User sysopUser = null; //new User();

			// 멤버중 팀장 확인 및 시샵으로 설정
			List<String> memberIds = new ArrayList<String>();

			// 시샵은 멤버에서 제외처리
			if (listGroupMembers.size() > 0) {
				for (int idx = 0; idx < listGroupMembers.size(); idx++) {
					Workspace tmpWorkspace = new Workspace();
					tmpWorkspace = listGroupMembers.get(idx);

					if (sysopUser != null && sysopUser.getUserId() != null && sysopUser.getUserId().equals(tmpWorkspace.getMemberId())) {
						continue;
					}
					memberIds.add(tmpWorkspace.getMemberId());
				}
			}

			workspace.setMemberIds(memberIds);

			if (sysopUser != null && sysopUser.getUserId() != null) {
				// 시샵 지정
				createSysopMember(workspace, sysopUser);
			}

			// WS 메인 그룹 생성
			createWsMainGroup(portalId, user, workspace);

			// ACL 운영진 그룹 + 시샵
			createAclGroup(portalId, user, workspace, "운영진", sysopUser.getUserId());

			// ACL 정회원 그룹 + 정회원멤버
			createAclGroup(portalId, user, workspace, "정회원", workspace.getMemberIds());

			// ACL 준회원 그룹 + 준회원 멤버
			createAclGroup(portalId, user, workspace, "준회원", workspace.getAssociateIds());

			// 개설시 추가회원 등록
			if (workspace.getMemberIds() != null && workspace.getMemberIds().size() > 0) {
				createMember(workspace.getWorkspaceId(), user, workspace.getMemberIds());
			}
			createBoard(workspace.getWorkspaceId(), user);

		}
	}

	/**
	 * workspace 조회
	 */
	public Workspace read(Workspace object) {
		return workspaceDao.get(object);
	}

	/**
	 * Workspace 조회
	 */
	public Workspace getWorkspace(String workspaceId) {
		return workspaceDao.getWorkspace(workspaceId);
	}

	/**
	 * 디폴트 Workspace 조회
	 */
	public Workspace getDefaultWorkspace(Map<String, String> map) {
		return workspaceDao.getDefaultWorkspace(map);
	}

	/**
	 * Workspace 이름 중복 체크
	 */
	public boolean checkWorkspaceName(Map<String, String> map) {
		return workspaceDao.checkWorkspaceName(map);
	}

	/**
	 * 팀 이름 중복 체크
	 */
	public String checkWorkspaceTeam(String teamId) {
		return workspaceDao.checkWorkspaceTeam(teamId);
	}

	/**
	 * workspace 조직도 정보 호출
	 */
	public List<Workspace> getOrgGroup(Map<String, String> map) {
		return workspaceDao.getOrgGroup(map);
	}

	/**
	 * workspace 존재유무
	 */
	public boolean exists(Workspace object) {
		return workspaceDao.exists(object);
	}

	/**
	 * workspace 정보 수정
	 */
	public void update(Workspace object) {
		workspaceDao.update(object);
	}

	/**
	 * Workapce 수정
	 */
	public void updateWorkspace(Workspace workspace, User user) {

		workspace.setUpdaterId(user.getUserId());
		workspace.setUpdaterName(user.getUserName());

		workspaceDao.update(workspace);

		Map<String, String> map = new HashMap<String, String>();
		map.put("workspaceId", workspace.getWorkspaceId());
		map.put("memberType", "member");

		List<Member> memberList = memberDao.listWorkspaceMember(map);

		if (memberList != null) {
			for (int i = 0; i < memberList.size(); i++) {
				Member member = memberList.get(i);

				memberService.physicalDelete(member);
			}
		}

		Map<String, String> userGroupMap = new HashMap<String, String>();
		userGroupMap.put("workspaceId", workspace.getWorkspaceId());
		userGroupMap.put("groupName", "정회원");

		// 정회원 그룹 가져오기
		String memberGroupId = memberDao.getEvGroup(userGroupMap);

		Member sysopMember = memberDao.getSysop(workspace.getWorkspaceId());

		if (workspace.getMemberIds() != null && workspace.getMemberIds().size() > 0) {
			for (int i = 0; i < workspace.getMemberIds().size(); i++) {
				Member member = new Member();

				String memberId = workspace.getMemberIds().get(i);

				if (memberId.equals(sysopMember.getMemberId())) {
					continue;
				}
				member.setWorkspaceId(workspace.getWorkspaceId());
				member.setMemberId(memberId);

				member.setMemberIntroduction("개설시 회원가입");
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

			}
		}

		// 이미지 등록
		if (workspace.getFileId() != null && !workspace.getFileId().equals("")) {
			fileService.removeItemFile(workspace.getWorkspaceId());
			fileService.createFileLink(workspace.getFileId(), workspace.getWorkspaceId(),
					WorkspaceConstants.ITEM_TYPE_NAME, user);
		}
		// Tag 등록
		if (workspace.getTag() != null) {
			createTag(workspace, user);
		}
	}

	/**
	 * Workspace 정보 수정
	 */
	public void updateWorkspaceInfo(Workspace workspace, User user) {

		workspace.setUpdaterId(user.getUserId());
		workspace.setUpdaterName(user.getUserName());

		workspaceDao.update(workspace);

		// 이미지 등록
		if (workspace.getFileId() != null && !workspace.getFileId().equals("")) {
			fileService.removeItemFile(workspace.getWorkspaceId());
			fileService.createFileLink(workspace.getFileId(), workspace.getWorkspaceId(),
					WorkspaceConstants.ITEM_TYPE_NAME, user);
		}
		// Tag 등록
		if (workspace.getTag() != null) {
			createTag(workspace, user);
		}
	}

	/**
	 * Workspace 소개 정보 수정
	 */
	public void updateWorkspaceIntro(Workspace workspace, User user) {
		//ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		//ActiveX Editor 사용 여부 확인
		if("Y".equals(useActiveX)) {
			//사용자 브라우저가 IE인 경우
			if(workspace.getMsie() == 1){
				try {		
					//현재 포탈 도메인 가져오기
					Portal portal = (Portal)RequestContextHolder.currentRequestAttributes().getAttribute("ikep.portal", RequestAttributes.SCOPE_SESSION);
					//현재 포탈 포트 가져오기
					String port = commonprop.getProperty("ikep4.activex.editor.port");
					//Tagfree ActiveX Editor Util => FileService, domain, port 생성자로 넘김 
					MimeUtil util = new MimeUtil(fileService, portal.getPortalHost(), port);
					util.setMimeValue(workspace.getIntroduction());
					//Mime 데이타 decoding
					util.processDecoding();
					//editor 첨부된 이미지 확인
					if(util.getFileLinkList() != null && util.getFileLinkList().size()>0){
						List<FileLink> newFileLinkList = new ArrayList<FileLink>();
						//기존 등록된 파일 처리
						if(workspace.getEditorFileLinkList() != null){
							for (int i = 0; i < workspace.getEditorFileLinkList().size(); i++) {
								FileLink fLink = (FileLink) workspace.getEditorFileLinkList().get(i);
								newFileLinkList.add(fLink);
							}
						}
						//새로 등록된 파일 처리
						for (int i = 0; i < util.getFileLinkList().size(); i++) {
							FileLink fileLink = (FileLink)util.getFileLinkList().get(i);							
							newFileLinkList.add(fileLink);
						}
						
						workspace.setEditorFileLinkList(newFileLinkList);
					}
					//내용 가져오기
					String content = util.getDecodedHtml(false);		
					content = content.trim();
					//내용세팅
					workspace.setIntroduction(content);
					
				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}			
		workspace.setUpdaterId(user.getUserId());
		workspace.setUpdaterName(user.getUserName());
		workspaceDao.updateIntro(workspace);
	}

	/**
	 * Workspace 상태변경 ( 승인, 폐쇄, 폐쇄신청, 폐쇄, 신청취소)
	 */
	public void updateWorkspaceStatus(List<String> workspaceIds, String workspaceStatus, User user) {

		for (int i = 0; i < workspaceIds.size(); i++) {
			Workspace workspace = new Workspace();
			String workspaceId = workspaceIds.get(i);
			workspace.setWorkspaceId(workspaceId);
			workspace.setWorkspaceStatus(workspaceStatus);
			workspace.setUpdaterId(user.getUserId());
			workspace.setUpdaterName(user.getUserName());
			workspace.setPortalId(user.getPortalId());

			// 업데이트 처리전 메일 발송함
			Workspace workspace1 = workspaceDao.get(workspace);

			if (workspaceStatus.equals("O") && workspace1.getWorkspaceStatus().equals("WC")) { // 폐쇄
																								// 반려
				sendMail("close", "closeReject", workspace1, user, workspaceStatus);
			}
			if (workspaceStatus.equals("O") && workspace1.getWorkspaceStatus().equals("WO")) { // 개설
																								// 승인
				sendMail("create", "approved", workspace1, user, workspaceStatus);
			} else if (workspaceStatus.equals("WC")) { // 폐쇄 신청
				sendMail("close", "request", workspace1, user, workspaceStatus);
			} else if (workspaceStatus.equals("C")) { // 폐쇄승인
				sendMail("close", "approved", workspace1, user, workspaceStatus);
			} else if (workspaceStatus.equals("WR") && workspace1.getWorkspaceStatus().equals("WO")) { // 개설
																										// 반려
				sendMail("create", "requestReject", workspace1, user, workspaceStatus);
			}
			workspaceDao.updateStatus(workspace);
		}
	}

	/**
	 * Workspace 상태변경 ( 승인, 폐쇄, 폐쇄신청, 폐쇄, 신청취소) - 다중
	 */
	public void updateWorkspaceStatus(Portal portal, User user, List<String> workspaceIds, String workspaceStatus) {

		for (int i = 0; i < workspaceIds.size(); i++) {

			Workspace workspace = new Workspace();

			String workspaceId = workspaceIds.get(i);

			workspace.setPortalId(portal.getPortalId());
			workspace.setWorkspaceId(workspaceId);
			workspace.setWorkspaceStatus(workspaceStatus);

			workspace.setUpdaterId(user.getUserId());
			workspace.setUpdaterName(user.getUserName());

			Workspace workspace1 = workspaceDao.get(workspace);

			// 업데이트 처리전 메일 발송함
			if (workspaceStatus.equals("O") && workspace1.getWorkspaceStatus().equals("WC")) { // 폐쇄
																								// 반려
				sendMail("close", "closeReject", workspace1, user, workspaceStatus);
			}
			if (workspaceStatus.equals("O") && workspace1.getWorkspaceStatus().equals("WO")) { // 개설
																								// 승인
				sendMail("create", "approved", workspace1, user, workspaceStatus);
			} else if (workspaceStatus.equals("WC")) { // 폐쇄 신청
				sendMail("close", "request", workspace1, user, workspaceStatus);
			} else if (workspaceStatus.equals("C")) { // 폐쇄승인
				sendMail("close", "approved", workspace1, user, workspaceStatus);
			} else if (workspaceStatus.equals("WR") && workspace1.getWorkspaceStatus().equals("WO")) { // 개설
																										// 반려
				sendMail("create", "requestReject", workspace1, user, workspaceStatus);
			}

			if (workspace1.getWorkspaceStatus().equals("WC") && workspaceStatus.equals("O")) {
				workspace.setWorkspaceStatus("Reject");// 폐쇄 반려처리
				workspaceDao.updateStatus(workspace); // 폐쇄 반려처리
			} else {
				workspaceDao.updateStatus(workspace);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sendMail(String action, String status, Workspace workspace, User user, String workspaceStatus) {

		ACLResourcePermission aclResourcePermission = new ACLResourcePermission();

		// Coll 관리자 목록
		// 리소스에 대한 권한 정보를 읽어 온다.
		aclResourcePermission = aclService.getSystemPermission(WorkspaceConstants.ACL_CLASS_NAME,
				WorkspaceConstants.ACL_RESOURCE_NAME, WorkspaceConstants.ACL_OPERATION_NAME);

		// 권한에 대한 상세정보를 조회 한다.
		// 관리자가 없는경우는 메일 발송안함
		if (aclResourcePermission != null) {
			aclResourcePermission = aclService.listDetailPermission(aclResourcePermission);

			Mail mail = new Mail();
			mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
			if(user.getLocaleCode().toUpperCase().equals("KO")) {
				mail.setMailTemplatePath("workspaceTemplate.vm");
			} else {
				mail.setMailTemplatePath("workspaceEnglishTemplate.vm");
			}
			// 발신자
			User sender = new User();
			// 발신자 -> 개설 신청자,폐쇄신청자,승인,폐쇄처리자
			sender = user;

			// 수신자
			List recipients = new ArrayList();
			HashMap<String, String> recip;
			String actionMessage = null;
			actionMessage = messageSource.getMessage(messageCodes.get(action), null, new Locale("ko"));
			String statusMessage = "";
			
			if (status != null && !status.equals("")) {
				statusMessage = messageSource.getMessage(statusCodes.get(status), null, new Locale("ko"));
			}
			if (workspaceStatus.equals(WorkspaceConstants.WORKSPACE_STATUS_WAITING_OPEN)
					|| workspaceStatus.equals(WorkspaceConstants.WORKSPACE_STATUS_WAITING_CLOSE)) {// 개설신청,폐쇄신청
				// 수신자 --> Coll 관리자
				for (User receiverUser : aclResourcePermission.getAssignUserDetailList()) {
					recip = new HashMap<String, String>();
					recip.put("email", receiverUser.getMail());
					recip.put("name", receiverUser.getUserName());
					recipients.add(recip);
				}
				mail.setToEmailList(recipients);
				// 메일 제목
				if(user.getLocaleCode().toUpperCase().equals("KO")) {
					mail.setTitle("[워크스페이스] " + actionMessage + " 신청 " + statusMessage + " 알림 - "
						+ (String) workspace.getWorkspaceName());
				} else {
					mail.setTitle("[Workspace] " + actionMessage + " 신청 " + statusMessage + " 알림 - "
							+ (String) workspace.getWorkspaceName());					
				}
			} else if (workspaceStatus.equals(WorkspaceConstants.WORKSPACE_STATUS_OPEN)
					|| workspaceStatus.equals(WorkspaceConstants.WORKSPACE_STATUS_CLOSE)
					|| workspaceStatus.equals(WorkspaceConstants.WORKSPACE_STATUS_WAITING_REJECT)) {// 개설승인,폐쇄승인,개설반려

				// 수신자 --> Workspace 등록자
				User receiverUser = userDao.get(workspace.getRegisterId());

				recip = new HashMap<String, String>();

				recip.put("email", receiverUser.getMail());
				recip.put("name", receiverUser.getUserName());

				recipients.add(recip);
				mail.setToEmailList(recipients);
				// 메일 제목
				if(user.getLocaleCode().toUpperCase().equals("KO")) {
					mail.setTitle("[워크스페이스] " + actionMessage + " 신청 " + statusMessage + " 알림 - "
						+ (String) workspace.getWorkspaceName());
				} else {
					mail.setTitle("[Workspace] " + actionMessage + " 신청 " + statusMessage + " 알림 - "
							+ (String) workspace.getWorkspaceName());					
				}
			}

			Map dataMap = new HashMap();
			dataMap.put("action", actionMessage);
			dataMap.put("status", statusMessage);
			dataMap.put("workspaceStatus",workspaceStatus);
			dataMap.put("workspace", workspace);
			dataMap.put("registDate", DateUtil.getToday(""));
			dataMap.put("sender", sender);

			/** 메일발송 임시 미사용, 메일 발송 오류로인한 **/
			mailSendService.sendMail(mail, dataMap, sender);
		}
	}

	/**
	 * workspace 상태 변경(개설,폐쇄신청,폐쇄,개설반려)
	 */
	public void updateStatus(Workspace object) {
		workspaceDao.updateStatus(object);
	}

	/**
	 * workspace 물리적 삭제
	 */
	public void physicalDelete(String workspaceId) {

		/** 워크스페이스 멤버 삭제 **/
		memberService.physicalDeleteMemberByWorkspace(workspaceId);
		/** 권한 그룹 삭제 **/
		memberDao.deleteEvGroup(workspaceId);
		workspaceDao.physicalDelete(workspaceId);

	}

	/**
	 * Workspace 삭제
	 */
	public void physicalDelete(List<String> workspaceIds) {

		for (int i = 0; i < workspaceIds.size(); i++) {

			String workspaceId = workspaceIds.get(i);

			/** 워크스페이스 멤버 삭제 + 권한 그룹 삭제 **/
			memberService.physicalDeleteMemberByWorkspace(workspaceId);

			// memberDao.deleteUserGroup(map)
			/** 권한 그룹 삭제 **/
			memberDao.deleteEvGroup(workspaceId);

			workspaceDao.physicalDelete(workspaceId);

		}

	}

	/**
	 * 태그 등록
	 * 
	 * @param
	 */
	private void createTag(Workspace workspace, User user) {

		// 태그 등록 - 태그 있을때 등록
		if (!StringUtil.isEmpty(workspace.getTag())) {
			Tag tag = new Tag();
			//String tagSubType = "";

			// tagSubType = "A";

			tag.setTagName(workspace.getTag()); // 사용자가 작성한 tag
			tag.setTagItemId(workspace.getWorkspaceId()); // WS ID
			tag.setTagItemType(WorkspaceConstants.ITEM_TYPE_NAME); // 모듈 타입 정의되어
																	// 있음.
			//tag.setTagItemSubType(tagSubType); // 모듈 서브 타입 - 있을때만 넣기
			tag.setTagItemName(workspace.getWorkspaceName()); // WS 이름
			tag.setTagItemContents(workspace.getDescription()); // WS 소개글
			tag.setTagItemUrl("/collpack/collaboration/main/Workspace.do?docPopup=true&workspaceId="
					+ workspace.getWorkspaceId()); // WS 팝업창 url
			tag.setRegisterId(user.getUserId());
			tag.setRegisterName(user.getUserName());
			tag.setPortalId(user.getPortalId());

			tagService.create(tag);
		}
	}

	/**
	 * Workspace 삭제 목록
	 */
	public List<Workspace> listAllWorkspaceDelete() {
		// Workspace 삭제 목록 정보
		return workspaceDao.listAllWorkspaceDelete();
	}

	/**
	 * 미개설된 팀 Workspace 생성 - 배치
	 */
	public void syncTeamWorkspace(String userId) {
		workspaceDao.syncTeamWorkspace(userId);
	}

	/**
	 * 신규 사용자 팀 Workspace 자동 회원 등록 - 배치
	 */
	public void syncUserWorkspace(String userId) {
		workspaceDao.syncUserWorkspace(userId);
	}

	/**
	 * Workspace 삭제처리
	 */
	public void deleteWorkspaceBatch(String workspaceId) {
		workspaceDao.deleteWorkspaceBatch(workspaceId);
	}

	/**
	 * 팀 Workapce 하위 목록
	 */
	public List<Workspace> listGroupHierarchy(String groupId) {
		return workspaceDao.listGroupHierarchy(groupId);
	}

	/**
	 * 배치 Job으로 삭제된 Workspace 영구 삭제
	 */
	public void batchDeleteWorkspace() {

		StringBuffer buffer = new StringBuffer();

		List<Workspace> deleteWorkspaceList = workspaceDao.listAllWorkspaceDelete();
		buffer.append("\r\n ----------------------------------------------------------------------------------");
		buffer.append("\r\n[1. 삭제된  Workspace 정보 조회]");

		buffer.append("\r\n[1. 삭제된  Workspace 갯수 : " + deleteWorkspaceList.size());
		buffer.append("\r\n ");

		for (int i = 0; i < deleteWorkspaceList.size(); i++) {

			Workspace workspace = deleteWorkspaceList.get(i);

			buffer.append("\r\n[삭제대상 Workspace]");
			buffer.append("\r\n[workspaceId : " + workspace.getWorkspaceId() + "]");
			buffer.append("\r\n[workspaceName : " + workspace.getWorkspaceName() + "]");
			buffer.append("\r\n[typeId : " + workspace.getTypeId() + "]");
			buffer.append("\r\n[typeName : " + workspace.getTypeName() + "]");
			buffer.append("\r\n ");

			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			buffer.append("\r\n[1. Workspace의  게시판 정보 조회]");
			buffer.append("\r\n ");

			List<Board> deleteList = boardAdminService.listDeleteBoardByWorkspace(workspace.getWorkspaceId());
			buffer.append("\r\n ---------------------------------------------------------------------------------");

			for (Board board : deleteList) {

				buffer.append("\r\n ");
				buffer.append("\r\n[boardId : " + board.getBoardId() + "]");
				buffer.append("\r\n[boardName : " + board.getBoardName() + "]");
				buffer.append("\r\n ");
				this.log.debug(buffer.toString());
				buffer.delete(0, buffer.length());

				buffer.append("\r\n ");
				buffer.append("\r\n[	1. 해당 게시판의 게시물중  첨부파일이 있는 게시물 조회]");
				buffer.append("\r\n ");
				this.log.debug(buffer.toString());
				buffer.delete(0, buffer.length());

				List<BoardItem> attachBoardItemList = boardItemService.listDeleteBoardItem(board.getBoardId());
				for (BoardItem boardItem : attachBoardItemList) {

					buffer.append("\r\n[itemId : " + boardItem.getItemId() + "]");
					buffer.append("\r\n[title : " + boardItem.getTitle() + "]");
					buffer.append("\r\n[attachCnt : " + boardItem.getAttachFileCount() + "]");

					this.fileService.removeItemFile(boardItem.getItemId());

				}

				buffer.append("\r\n[4. 해당 게시판 정보 삭제 프로시져 실행]");
				buffer.append("\r\n ");
				this.log.debug(buffer.toString());
				buffer.delete(0, buffer.length());
				boardAdminService.deleteWorkspaceBoard(board.getBoardId());

			}

			buffer.append("\r\n ");
			buffer.append("\r\n[2. 해당 공지사항 정보 조회 및 첨부파일 삭제]");
			buffer.append("\r\n ");
			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			List<AnnounceItem> attachAnnounceItemList = announceItemService.listDeleteAnnounceByWorkspace(workspace
					.getWorkspaceId());
			for (AnnounceItem announceItem : attachAnnounceItemList) {
				buffer.append("\r\n ");
				buffer.append("\r\n[itemId : " + announceItem.getItemId() + "]");
				buffer.append("\r\n[title : " + announceItem.getTitle() + "]");
				buffer.append("\r\n[attachCnt : " + announceItem.getAttachFileCount() + "]");
				buffer.append("\r\n ");

				this.fileService.removeItemFile(announceItem.getItemId());

			}
			buffer.append("\r\n ");
			buffer.append("\r\n[3. 주간보고 정보 조회 및 첨부파일 삭제]");
			buffer.append("\r\n ");
			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			List<WeeklyItem> attachWeeklyItemList = weeklyItemService.listDeleteWeeklyItemByWorkspace(workspace
					.getWorkspaceId());
			for (WeeklyItem weeklyItem : attachWeeklyItemList) {
				buffer.append("\r\n ");
				buffer.append("\r\n[itemId : " + weeklyItem.getItemId() + "]");
				buffer.append("\r\n[title : " + weeklyItem.getTitle() + "]");
				buffer.append("\r\n[attachCnt : " + weeklyItem.getAttachFileCount() + "]");
				buffer.append("\r\n ");

				this.fileService.removeItemFile(weeklyItem.getItemId());

			}
			buffer.append("\r\n ");
			buffer.append("\r\n[5. 해당 Workspace 삭제 프로시져 실행]");
			buffer.append("\r\n ");
			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			workspaceDao.deleteWorkspaceBatch(workspace.getWorkspaceId());

		}
		this.log.debug("\r\n ====================== Workspace 삭제 배치 프로세스 종료 =============================");
		this.log.debug("\r\n =================================================================================");
	}

	public SearchResult<Workspace> getWorkspaceImageFile(WorkspaceSearchCondition searchCondition) {

		SearchResult<Workspace> searchResult = null;

		searchCondition.setStartRowIndex(searchCondition.getCurrentCount());
		searchCondition.setEndRowIndex(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());

		List<Workspace> list = workspaceDao.getWorkspaceImageFile(searchCondition);

		if (list == null) {
			searchCondition.setCurrentCount(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());
			searchCondition.setRecordCount(0);
		} else {
			searchCondition.setCurrentCount(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());
			searchCondition.setRecordCount(list.size());

			searchResult = new SearchResult<Workspace>(list, searchCondition);
		}

		return searchResult;

	}

	public SearchResult<Workspace> listBySearchConditionMobile(WorkspaceSearchCondition searchCondition) {
		Integer count = this.workspaceDao.countBySearchConditionPersonal(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<Workspace> searchResult = null;

		// MyColl 목록인 경우 페이징 처리 없애기
		if (searchCondition.getListType() == null || searchCondition.getListType().equals("")) {
			searchCondition.setStartRowIndex(0);
			searchCondition.setEndRowIndex(count);
		}
		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Workspace>(searchCondition);

		} else {

			List<Workspace> list = this.workspaceDao.listBySearchConditionPersonal(searchCondition);
			List<Workspace> presentList = this.workspaceDao.getPresentCollMenuList(searchCondition.getMemberId());

			// System.out.println(list.size());

			for (int i = 0; i < list.size(); i++) {

				Workspace workspace = list.get(i);

				List<Tag> tagList = tagService.listTagByItemId(workspace.getWorkspaceId(),
						WorkspaceConstants.ITEM_TYPE_NAME);
				if (tagList != null && tagList.size() > 0) {
					workspace.setTagList(tagList);

					list.set(i, workspace);
				}
			}
			
			for(Workspace workspace1 : list) {
				for(Workspace workspace2 : presentList) {
					if(workspace1.getWorkspaceId().equals(workspace2.getWorkspaceId())) {
						workspace1.setPresentWorkspaceId("1");
					}
				}
			}
			
			searchResult = new SearchResult<Workspace>(list, searchCondition);
		}

		return searchResult;
	}
	
	public void updateCollMenuList(Map<String, Object> param) {
		
		//지우고,
		this.workspaceDao.deleteCollMenuList((String)param.get("userId"));
		
		//집어넣는다.
		for(int i=0; i<((List<String>)param.get("itemIds")).size(); i++){
			Map<String,Object> param1 = new HashMap<String, Object>();
			param1.put("userId", param.get("userId"));
			param1.put("itemId", ((List<String>)param.get("itemIds")).get(i));
			this.workspaceDao.insertCollMenuList(param1);
		}
	}

	public SearchResult<Workspace> listBySearchConditionPlannerForMobile(WorkspaceSearchCondition searchCondition) {
		Integer count = this.workspaceDao.countBySearchConditionPersonal(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<Workspace> searchResult = null;

		// MyColl 목록인 경우 페이징 처리 없애기
		if (searchCondition.getListType() == null || searchCondition.getListType().equals("")) {
			searchCondition.setStartRowIndex(0);
			searchCondition.setEndRowIndex(count);
		}
		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Workspace>(searchCondition);

		} else {

//			List<Workspace> list = this.workspaceDao.listBySearchConditionPersonal(searchCondition);
			Map<String, String> map = new HashMap<String, String>();
			map.put("portalId", searchCondition.getPortalId());
			map.put("memberId",searchCondition.getMemberId());
			List<Workspace> list = workspaceDao.listMyScheduleCollaboration(map);
			List<Workspace> presentList = this.workspaceDao.getPresentPlannerMenuList(searchCondition.getMemberId());

			//전사 일정 메뉴 등록
			String[] temp1 = {"전체","본사","진주","울산","대구"};
			
			for(int i=0; i<temp1.length; i++) {
				Workspace tempWorkspace = new Workspace();
				tempWorkspace.setWorkspaceId(temp1[i]);
				tempWorkspace.setWorkspaceName("전사일정 : "+temp1[i]);
				list.add(tempWorkspace);
			}
			
			for (int i = 0; i < list.size(); i++) {

				Workspace workspace = list.get(i);

				List<Tag> tagList = tagService.listTagByItemId(workspace.getWorkspaceId(),
						WorkspaceConstants.ITEM_TYPE_NAME);
				if (tagList != null && tagList.size() > 0) {
					workspace.setTagList(tagList);

					list.set(i, workspace);
				}
			}
			
			for(Workspace workspace1 : list) {
				for(Workspace workspace2 : presentList) {
					if(workspace1.getWorkspaceId().equals(workspace2.getWorkspaceId())) {
						workspace1.setPresentWorkspaceId("1");
					}
				}
			}
			
			searchResult = new SearchResult<Workspace>(list, searchCondition);
		}

		return searchResult;
	}

	public void updatePlannerMenuList(Map<String, Object> param) {
		//지우고,
		this.workspaceDao.deletePlannerMenuList((String)param.get("userId"));
		
		//집어넣는다.
		for(int i=0; i<((List<String>)param.get("itemIds")).size(); i++){
			Map<String,Object> param1 = new HashMap<String, Object>();
			param1.put("userId", param.get("userId"));
			param1.put("itemId", ((List<String>)param.get("itemIds")).get(i));
			this.workspaceDao.insertPlannerMenuList(param1);
		}
	}

	public  List<Workspace> getPresentPlannerMenuList(String userId) {
		// TODO Auto-generated method stub
		return this.workspaceDao.getPresentPlannerMenuList(userId);
	}
	
	public String myTeamWorkspace(String userId) {
		return workspaceDao.myTeamWorkspace(userId);
	}
}
