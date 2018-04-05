/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.board.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.collpack.collaboration.alliance.dao.AllianceDao;
import com.lgcns.ikep4.collpack.collaboration.alliance.model.Alliance;
import com.lgcns.ikep4.collpack.kms.board.dao.BoardDao;
import com.lgcns.ikep4.collpack.kms.board.dao.BoardMigrationDao;
import com.lgcns.ikep4.collpack.kms.board.model.Board;
import com.lgcns.ikep4.collpack.kms.board.model.BoardGroup;
import com.lgcns.ikep4.collpack.kms.board.model.BoardItem;
import com.lgcns.ikep4.collpack.kms.board.model.BoardMigration;
import com.lgcns.ikep4.collpack.kms.board.service.BoardAdminService;
import com.lgcns.ikep4.collpack.kms.board.service.BoardItemService;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspaceConstants;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.support.security.acl.model.ACLGroupPermission;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.group.dao.GroupDao;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * BoardAdminService 구현체 클래스
 */
@Service("kmsBoardAdminServiceImpl")
public class BoardAdminServiceImpl implements BoardAdminService {

	/** The log. */
	protected final Log log = LogFactory.getLog(this.getClass());

	/** The board dao. */
	@Autowired
	private BoardDao boardDao;

	/** The idgen service. */
	@Autowired
	private IdgenService idgenService;

	@Autowired
	private BoardItemService boardItemService;

	@Autowired
	private ACLService aclService;

	@Autowired
	FileService fileService;

	/** 위지윅에디터에서 업로드 가능한 이미지 미디어타입 목록 */
	private final List<String> supportedImageMediaTypeList = Arrays.asList("image/gif", "image/jpeg", "image/png",
			"image/swf", "image/psd", "image/bmp", "image/tiff_ii", "image/tiff_mm", "image/jpc", "image/jp2",
			"image/jpx", "image/jb2", "image/swc", "image/iff", "image/wbmp", "image/xbm");

	private static final String CLASS_NAME = "Coll-BD";

	@Autowired
	private AllianceDao allianceDao;

	@Autowired
	private BoardMigrationDao boardMigrationDao;

	@Autowired
	private TagService tagService;

	@Autowired
	private GroupDao groupDao;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.collpack.kms.board.service.BoardAdminService#
	 * checkSupportedImageMediaType(java.lang.String)
	 */
	public Boolean checkSupportedImageMediaType(String imageType) {
		return Arrays.binarySearch(this.supportedImageMediaTypeList.toArray(new String[0]), imageType) > 0;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.collpack.kms.board.service.BoardAdminService
	 * #listByBoardRootId (java.lang.String)
	 */
	/**
	 * public List<Board> listByBoardRootId(String boardRootId) { List<Board>
	 * boardList = this.boardDao.listByBoardRootId(boardRootId); return
	 * boardList; }
	 **/

	/**
	 * 게시판 조회
	 */
	public Board readBoard(String boardId) {
		Board board = this.boardDao.get(boardId);

		Map<String, String> map = new HashMap<String, String>();
		map.put("isKnowhow", board.getIsKnowhow());
		map.put("boardId", boardId);

		// 게시판의 조상목록을 조회한다.
		board.setParentList(boardDao.getParents(map));
		// 게시판의 손자목록을 조회한다.
		board.setChildList(boardDao.getChildren(map));

		List<Tag> tagList = tagService.listTagByItemId(board.getBoardId(), TagConstants.ITEM_TYPE_KMS);
		board.setTagList(tagList);

		// 게시판의 조상목록을 조회한다.
		// board.setParentList(this.boardDao.getParents(boardId));

		// 게시판의 손자목록을 조회한다.
		// board.setChildList(this.boardDao.getChildren(boardId));

		if (board.getWritePermission().equals("4")) {
			// 쓰기 권한 정보를 가져온다.
			ACLResourcePermission writePermission = this.aclService.getSystemPermission(Board.BOARD_ACL_CLASS_NAME,
					board.getBoardId(), "WRITE");

			if (writePermission != null) {
				writePermission = this.aclService.listDetailPermission(writePermission);

				List<Group> writeGroupList = writePermission.getGroupDetailList();

				List<ACLGroupPermission> wirtePermissionList = writePermission.getGroupPermissionList();

				if (writeGroupList != null) {
					List<BoardGroup> writeBoardGroupList = new ArrayList<BoardGroup>();

					BoardGroup boardGroup = null;

					for (Group group : writeGroupList) {
						boardGroup = new BoardGroup();
						boardGroup.setGroupId(group.getGroupId());
						boardGroup.setGroupName(group.getGroupName());

						for (ACLGroupPermission aclGroupPermission : wirtePermissionList) {
							if (aclGroupPermission.getGroupId().equals(group.getGroupId())) {
								boardGroup.setHierarchied(aclGroupPermission.getHierarchyPermission());
								continue;
							}
						}

						writeBoardGroupList.add(boardGroup);
					}

					board.setWriteGroupList(writeBoardGroupList);
				}

				List<User> writeUserList = writePermission.getAssignUserDetailList();
				board.setWriteUserList(writeUserList);
			}
		}
		if (board.getReadPermission().equals("4")) {

			// 읽기 권한 정보를 가져온다.
			ACLResourcePermission readPermission = this.aclService.getSystemPermission(Board.BOARD_ACL_CLASS_NAME,
					board.getBoardId(), "READ");

			if (readPermission != null) {
				readPermission = this.aclService.listDetailPermission(readPermission);

				List<User> readUserList = readPermission.getAssignUserDetailList();
				List<Group> readGroupList = readPermission.getGroupDetailList();
				List<ACLGroupPermission> readPermissionList = readPermission.getGroupPermissionList();

				if (readGroupList != null) {
					List<BoardGroup> readBoardGroupList = new ArrayList<BoardGroup>();

					BoardGroup boardGroup = null;

					for (Group group : readGroupList) {

						boardGroup = new BoardGroup();
						boardGroup.setGroupId(group.getGroupId());
						boardGroup.setGroupName(group.getGroupName());

						for (ACLGroupPermission aclGroupPermission : readPermissionList) {
							if (aclGroupPermission.getGroupId().equals(group.getGroupId())) {
								boardGroup.setHierarchied(aclGroupPermission.getHierarchyPermission());
							}
						}
						readBoardGroupList.add(boardGroup);
					}
					board.setReadGroupList(readBoardGroupList);
				}
				board.setReadUserList(readUserList);
			}
		}
		return board;
	}
	public List<Board> boardList(String isKnowhow) {
		 return this.boardDao.boardList(isKnowhow);
	}
	

	/**
	 * 게시판 생성
	 */
	public String createBoard(Board board) {
		final String generatedId = this.idgenService.getNextId();

		board.setBoardId(generatedId);
		// 1Level Board 생성시 Foreign key 제약조건 오류 방지
		if ("".equals(board.getBoardParentId())) {
			board.setBoardParentId(null);
		}
		String boardId = this.boardDao.create(board);

		// 게시판인경우 권한 정보를 저장한다.
		if ("0".equals(board.getBoardType())) {
			this.createWritePermission(board);
			this.createReadPermission(board);
		}

		return boardId;

	}

	/**
	 * 쓰기 권한을 수정한다.
	 * 
	 * @param board 게시판 모델 객체
	 */
	private void updateWritePermission(Board board) {
		ACLResourcePermission writePermission = this.aclService.getSystemPermission(Board.BOARD_ACL_CLASS_NAME,
				board.getBoardId(), "WRITE");
		writePermission.setAssignUserIdList(null);
		writePermission.setGroupPermissionList(null);
		writePermission.setOpen(Integer.parseInt(board.getWritePermission()));

		if (board.getWritePermission().equals("0")) {
			// 쓰기 권한 사용자 정보를 넣는다.
			if (board.getWriteUserList() != null) {
				for (User user : board.getWriteUserList()) {
					writePermission.addAssignUserId(user.getUserId());

				}
			}

			// 쓰기 권한 관리자 정보를 넣는다.
			if (board.getWriteGroupList() != null) {
				for (BoardGroup group : board.getWriteGroupList()) {
					writePermission.addGroupPermission(group.getGroupId(), group.getHierarchied());
				}
			}
		}

		// portalId 읽어오기
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user",
				RequestAttributes.SCOPE_SESSION);

		this.aclService.updateSystemPermission(writePermission, user.getPortalId());
	}

	/**
	 * 읽기 권한을 수정한다.
	 * 
	 * @param board 게시판 모델 객체
	 */
	private void updateReadPermission(Board board) {
		ACLResourcePermission readPermission = this.aclService.getSystemPermission(Board.BOARD_ACL_CLASS_NAME,
				board.getBoardId(), "READ");
		readPermission.setAssignUserIdList(null);
		readPermission.setGroupPermissionList(null);
		readPermission.setOpen(Integer.parseInt(board.getReadPermission()));

		if (board.getReadPermission().equals("0")) {
			// 읽기 권한 사용자 정보를 넣는다.
			if (board.getReadUserList() != null) {
				for (User user : board.getReadUserList()) {
					readPermission.addAssignUserId(user.getUserId());
				}
			}

			// 읽기 권한 관리자 정보를 넣는다.
			if (board.getReadGroupList() != null) {
				for (BoardGroup group : board.getReadGroupList()) {
					readPermission.addGroupPermission(group.getGroupId(), group.getHierarchied());
				}
			}
		}

		// portalId 읽어오기
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user",
				RequestAttributes.SCOPE_SESSION);

		this.aclService.updateSystemPermission(readPermission, user.getPortalId());
	}

	/**
	 * 쓰기권한을 생성한다.
	 * 
	 * @param board 게시판 모델 객체
	 */
	private void createWritePermission(Board board) {
		ACLResourcePermission writePermission = new ACLResourcePermission();

		// 시스템 리소스의 타입을 입력한다.
		writePermission.setClassName(Board.BOARD_ACL_CLASS_NAME);
		// 시스템 리소스 이름을 입력한다.
		writePermission.setResourceName(board.getBoardId());
		// 시스템 리소스에 대한 설명을 입력한다.
		writePermission.setResourceDescription(board.getBoardName());

		// 사용자 아이디를 입력한다.
		writePermission.setUserId(board.getUpdaterId());
		// 사용자 이름을 입력한다.
		writePermission.setUserName(board.getUpdaterName());
		// 오퍼레이션 이름을 입력한다.
		writePermission.setOperationName("WRITE");
		writePermission.setOpen(Integer.parseInt(board.getWritePermission()));

		if (board.getWritePermission().equals("0")) {
			// 시스템 리소스의 오픈여부를 입력한다, 1=오픈, 0=비오픈

			if (board.getWriteUserList() != null) {
				// 쓰기 권한 사용자 정보를 넣는다.
				for (User user : board.getWriteUserList()) {
					writePermission.addAssignUserId(user.getUserId());
				}
			}

			if (board.getWriteGroupList() != null) {
				// 쓰기 권한 관리자 정보를 넣는다.
				for (BoardGroup group : board.getWriteGroupList()) {
					writePermission.addGroupPermission(group.getGroupId(), group.getHierarchied());
				}
			}
		}
		this.aclService.createSystemPermission(writePermission);
	}

	/**
	 * 읽기 권한을 생성한다.
	 * 
	 * @param board 게시판 모델 객체
	 */
	private void createReadPermission(Board board) {
		ACLResourcePermission readPermission = new ACLResourcePermission();
		// 시스템 리소스의 타입을 입력한다.
		readPermission.setClassName(Board.BOARD_ACL_CLASS_NAME);
		// 시스템 리소스 이름을 입력한다.
		readPermission.setResourceName(board.getBoardId());
		// 시스템 리소스에 대한 설명을 입력한다.
		readPermission.setResourceDescription(board.getBoardName());
		// 시스템 리소스의 오픈여부를 입력한다, 1=오픈, 0=비오픈
		readPermission.setOpen(Integer.parseInt(board.getReadPermission()));
		// 사용자 아이디를 입력한다.
		readPermission.setUserId(board.getUpdaterId());
		// 사용자 이름을 입력한다.
		readPermission.setUserName(board.getUpdaterName());
		// 오퍼레이션 이름을 입력한다.
		readPermission.setOperationName("READ");

		if (board.getReadPermission().equals("0")) {
			if (board.getReadUserList() != null) {
				// 읽기 권한 사용자 정보를 넣는다.
				for (User user : board.getReadUserList()) {
					readPermission.addAssignUserId(user.getUserId());
				}
			}

			if (board.getReadGroupList() != null) {
				// 읽기 권한 관리자 정보를 넣는다.
				for (BoardGroup group : board.getReadGroupList()) {
					readPermission.addGroupPermission(group.getGroupId(), group.getHierarchied());
				}
			}
		}

		this.aclService.createSystemPermission(readPermission);

	}

	/**
	 * 게시판 조회 권한 정보 등록/수정
	 * 
	 * @param board
	 */
	private void saveReadPermission(Board board) {
		ACLResourcePermission readPermission = new ACLResourcePermission();
		// 시스템 리소스의 타입을 입력한다.
		readPermission.setClassName(Board.BOARD_ACL_CLASS_NAME);
		// 시스템 리소스 이름을 입력한다.
		readPermission.setResourceName(board.getBoardId());
		// 시스템 리소스에 대한 설명을 입력한다.
		readPermission.setResourceDescription(board.getBoardName());
		// 사용자 아이디를 입력한다.
		readPermission.setUserId(board.getUpdaterId());
		// 사용자 이름을 입력한다.
		readPermission.setUserName(board.getUpdaterName());
		// 오퍼레이션 이름을 입력한다.
		readPermission.setOperationName("READ");

		// 시스템 리소스의 오픈여부를 입력한다, 1=오픈, 0=비오픈
		if (board.getReadPermission().equals("1")) {
			readPermission.setOpen(1);
		} else {
			readPermission.setOpen(0);

			List<Group> groupList = new ArrayList<Group>();

			Group group = new Group();
			group.setGroupId(board.getWorkspaceId());
			group.setGroupTypeId(WorkspaceConstants.GROUP_TYPE_ID);

			if (board.getReadPermission().equals("2") || board.getReadPermission().equals("3")) {
				groupList = groupDao.selectOrgGroupByGroupTypeId(group);
			}
			if (board.getReadPermission().equals("2")) {
				for (Group group1 : groupList) {
					if (group1.getGroupId().equals(group1.getParentGroupId())) {
						continue;
					} else {
						readPermission.addGroupPermission(group1.getGroupId(), 0);
					}
				}
			} else if (board.getReadPermission().equals("3")) {
				for (Group group1 : groupList) {
					if (group1.getGroupId().equals(group1.getParentGroupId())) {
						continue;
					} else if (group1.getGroupName().equals(WorkspaceConstants.WS_PERMISSION_GROUP_ASSOCIATE)) {
						continue;
					} else {
						readPermission.addGroupPermission(group1.getGroupId(), 0);
					}
				}
			} else if (board.getReadPermission().equals("4")) {
				if (board.getReadUserList() != null) {
					// 읽기 권한 사용자 정보를 넣는다.
					for (User user : board.getReadUserList()) {
						readPermission.addAssignUserId(user.getUserId());
					}
				}
				if (board.getReadGroupList() != null) {
					// 읽기 권한 관리자 정보를 넣는다.
					for (BoardGroup boardGroup : board.getReadGroupList()) {
						readPermission.addGroupPermission(boardGroup.getGroupId(), boardGroup.getHierarchied());
					}
				}
			}
			// 동맹 그룹 권한 ( 정회원 이상 ) Read 권한 전사가 아닌경우
			if (!board.getReadPermission().equals("1") && board.getAllianceIds() != null) {

				for (String allianceId : board.getAllianceIds()) {
					Alliance alliance = allianceDao.get(allianceId);

					Group allianceGroup = new Group();

					if (!alliance.getWorkspaceId().equals(board.getWorkspaceId())) {
						allianceGroup.setGroupId(alliance.getWorkspaceId());
					} else {
						allianceGroup.setGroupId(alliance.getToWorkspaceId());
					}
					allianceGroup.setGroupTypeId(WorkspaceConstants.GROUP_TYPE_ID);
					List<Group> allianceGroupList = groupDao.selectOrgGroupByGroupTypeId(allianceGroup);

					for (Group group1 : allianceGroupList) {
						// Root 권한 그룹 제외
						if (group1.getGroupId().equals(group1.getParentGroupId())) {
							continue;
						}// 준회원 제외
						else if (group1.getGroupName().equals(WorkspaceConstants.WS_PERMISSION_GROUP_ASSOCIATE)) {
							continue;
						} else {
							readPermission.addGroupPermission(group1.getGroupId(), 0);
						}
					}
					// 동맹 그룹 정보 & 게시판 정보 등록
					Map<String, String> allianceBoardMap = new HashMap<String, String>();
					allianceBoardMap.put("allianceId", allianceId);
					allianceBoardMap.put("boardId", board.getBoardId());

					boardDao.createBoardAlliance(allianceBoardMap);
				}

			}
		}
		// 리소스에 대한 권한 정보를 읽어 온다.
		ACLResourcePermission hasAclResourcePermission = aclService.getSystemPermission(readPermission.getClassName(),
				readPermission.getResourceName(), readPermission.getOperationName());

		if (hasAclResourcePermission != null) {
			// portalId 읽어오기
			User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user",
					RequestAttributes.SCOPE_SESSION);

			aclService.updateSystemPermission(readPermission, user.getPortalId());
		} else {
			aclService.createSystemPermission(readPermission);
		}
	}

	/**
	 * 게시판 쓰기 권한 등록/수정
	 * 
	 * @param board
	 */
	private void saveWritePermission(Board board) {
		ACLResourcePermission writePermission = new ACLResourcePermission();

		// 시스템 리소스의 타입을 입력한다.
		writePermission.setClassName(Board.BOARD_ACL_CLASS_NAME);
		// 시스템 리소스 이름을 입력한다.
		writePermission.setResourceName(board.getBoardId());
		// 시스템 리소스에 대한 설명을 입력한다.
		writePermission.setResourceDescription(board.getBoardName());

		// 사용자 아이디를 입력한다.
		writePermission.setUserId(board.getUpdaterId());
		// 사용자 이름을 입력한다.
		writePermission.setUserName(board.getUpdaterName());
		// 오퍼레이션 이름을 입력한다.
		writePermission.setOperationName("WRITE");
		// 시스템 리소스의 오픈여부를 입력한다, 1=오픈, 0=비오픈

		if (board.getWritePermission().equals("1")) {
			writePermission.setOpen(1);
		} else {
			writePermission.setOpen(0);

			List<Group> groupList = new ArrayList<Group>();

			Group group = new Group();
			group.setGroupId(board.getWorkspaceId());
			group.setGroupTypeId(WorkspaceConstants.GROUP_TYPE_ID);

			if (board.getWritePermission().equals("2") || board.getWritePermission().equals("3")) {
				groupList = groupDao.selectOrgGroupByGroupTypeId(group);
			}
			if (board.getWritePermission().equals("2")) {

				for (Group group1 : groupList) {
					if (group1.getGroupId().equals(group1.getParentGroupId())) {
						continue;
					} else {
						writePermission.addGroupPermission(group1.getGroupId(), 0);
					}
				}
			} else if (board.getWritePermission().equals("3")) {

				for (Group group1 : groupList) {
					if (group1.getGroupId().equals(group1.getParentGroupId())) {
						continue;
					} else if (group1.getGroupName().equals(WorkspaceConstants.WS_PERMISSION_GROUP_ASSOCIATE)) {
						continue;
					} else {
						writePermission.addGroupPermission(group1.getGroupId(), 0);
					}
				}
			} else if (board.getWritePermission().equals("4")) {

				if (board.getWriteUserList() != null) {
					// 쓰기 권한 사용자 정보를 넣는다.
					for (User user : board.getWriteUserList()) {
						writePermission.addAssignUserId(user.getUserId());
					}
				}
				if (board.getWriteGroupList() != null) {
					// 쓰기 권한 관리자 정보를 넣는다.
					for (BoardGroup boardGroup : board.getWriteGroupList()) {
						writePermission.addGroupPermission(boardGroup.getGroupId(), boardGroup.getHierarchied());
					}
				}
			}
		}
		// 리소스에 대한 권한 정보를 읽어 온다.
		ACLResourcePermission hasAclResourcePermission = aclService.getSystemPermission(writePermission.getClassName(),
				writePermission.getResourceName(), writePermission.getOperationName());

		if (hasAclResourcePermission != null) {
			// portalId 읽어오기
			User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user",
					RequestAttributes.SCOPE_SESSION);

			aclService.updateSystemPermission(writePermission, user.getPortalId());
		} else {
			aclService.createSystemPermission(writePermission);
		}
	}

	/**
	 * 게시판 정보 수정
	 */
	public void updateBoard(Board board) {
		// 1Level Board 생성시 Foreign key 제약조건 오류 방지
		if ("".equals(board.getBoardParentId())) {
			board.setBoardParentId(null);
		}

		this.boardDao.update(board);

		// 게시판인 경우 권한 정보를 저장한다.
		if ("0".equals(board.getBoardType())) {

			// 권한이 생성되어 있는지 확인하고 생성되어 있으면 업데이트 아니면 생성을 한다.
			if (this.aclService.getSystemPermission(Board.BOARD_ACL_CLASS_NAME, board.getBoardId(), "READ") == null) {
				this.createReadPermission(board);
			} else {
				this.updateReadPermission(board);

			}
			if (this.aclService.getSystemPermission(Board.BOARD_ACL_CLASS_NAME, board.getBoardId(), "WRITE") == null) {
				this.createWritePermission(board);
			} else {
				this.updateWritePermission(board);
			}
		}
	}

	/**
	 * 게시판 삭제
	 */
	public void physicalDeleteBoard(String boardId) {
		// 게시판 정보를 조회한다.
		Board board = this.boardDao.get(boardId);

		// 권한을 삭제한다.
		this.aclService.deleteSystemPermission(Board.BOARD_ACL_CLASS_NAME, board.getBoardId());

		// 게시판에 게시글과 관련 정보를 모두 삭제한다.
		this.boardItemService.adminDeleteBoardItemInBoard(boardId);

		// 게시판을 삭제한다.
		this.boardDao.physicalDelete(boardId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.collpack.kms.board.service.BoardAdminService
	 * #listParentBoard (java.lang.String)
	 */
	// public List<Board> listParentBoard(String boardId) {
	// return this.boardDao.getParents(boardId);
	// }

	/**
	 * 게시판 이동
	 */
	public void updateBoardMove(Board after) {

		Board before = this.boardDao.get(after.getBoardId());

		// 이동전의 위치에서는 자기 기준으로 정렬숫자가 높은 놈들은 -1 해준다.
		this.boardDao.updateSortOderDecrease(before);

		// 이동후의 위치에서는 자기 기준으로 정렬숙자가 높은 놈들은 +1 해준다.
		this.boardDao.updateSortOderIncrease(after);
		// 1Level Board 생성시 Foreign key 제약조건 오류 방지
		if ("".equals(after.getBoardParentId())) {
			after.setBoardParentId(null);
		}
		this.boardDao.updateMove(after);

	}

	/**
	 * 게시판 읽기권한
	 */
	public Board readHasPermissionBoard(String userId, String boardRootId) {
		return this.boardDao.readHasPermissionBoard(userId, boardRootId);
	}

	/**
	 * 하위 게시판 목록
	 * 
	 * @param boardId
	 * @return
	 */
	public List<Board> listChildrenBoard(String boardId) {
		return this.boardDao.listChildrenBoard(boardId);
	}

	/* WS 추가 5/17 */
	/**
	 * 게시판 생성
	 */
	public String createBoard(Board board, User user) {

		String boardId = null;

		String generatedId = idgenService.getNextId();

		board.setBoardId(generatedId);
		// 1Level Board 생성시 Foreign key 제약조건 오류 방지
		if ("".equals(board.getBoardParentId())) {
			board.setBoardParentId(null);
		}
		// Read 권한과 동일 처리
		if (board.getSameReadPermission() != null && board.getSameReadPermission().equals("1")) {
			board.setWritePermission(board.getReadPermission());
			board.setWriteUserList(board.getReadUserList());
			board.setWriteGroupList(board.getReadGroupList());
		}

		boardId = boardDao.create(board);

		// 게시판 태그 등록
		if (board.getTag() != null && !board.getTag().equals("")) {
			createTag(board, user);
		}
		saveReadPermission(board);

		saveWritePermission(board);

		return boardId;
	}

	/**
	 * 게시판 이동
	 */
	public void moveWorkspaceBoard() {
		boardDao.moveWorkspaceBoard();

	}

	/**
	 * 삭제된 WS 내의 게시판 목록
	 */
	public List<Board> listDeleteBoardByWorkspace(String workspaceId) {
		return this.boardDao.listDeleteBoardByWorkspace(workspaceId);
	}

	/**
	 * 배치 Job 으로 삭제된 게시판 삭제 영구 삭제
	 */
	public void deleteWorkspaceBoard(String boardId) {
		boardDao.deleteWorkspaceBoard(boardId);

	}

	/**
	 * 삭제된 게시판 목록 조회
	 */
	public List<Board> listDeleteBoardBatch() {
		return this.boardDao.listDeleteBoardBatch();
	}

	public void batchDeleteBoard() {

		this.log.debug("\r\n ===================================================================================================");
		this.log.debug("\r\n ====================== Workspace 에서 삭제된 게시판 영구 삭제  배치 프로세스 시작 =============================");

		// StringBuffer buffer = new StringBuffer();

		this.log.debug("\r\n ----------------------------------------------------------------------------------");
		this.log.debug("\r\n[1. 삭제된  게시판 정보 조회]");

		List<Board> deleteList = this.boardDao.listDeleteBoardBatch();

		this.log.debug("\r\n[1. 삭제된  게시판 갯수 : " + deleteList.size());
		this.log.debug("\r\n ");
		for (Board board : deleteList) {

			this.log.debug("\r\n[boardId : " + board.getBoardId() + "]");
			this.log.debug("\r\n[boardName : " + board.getBoardName() + "]");
			this.log.debug("\r\n ");
			// this.log.debug(buffer.toString());
			// buffer.delete(0, buffer.length());

			this.log.debug("\r\n[	1. 해당 게시판의 게시물중  첨부파일이 있는 게시물 조회]");
			this.log.debug("\r\n ");
			// this.log.debug(buffer.toString());
			// buffer.delete(0, buffer.length());

			List<BoardItem> attachBoardItemList = boardItemService.listDeleteBoardItem(board.getBoardId());
			this.log.debug("\r\n[1. 첨부가 있는 게시물 갯수 : " + attachBoardItemList.size());
			this.log.debug("\r\n ");
			for (BoardItem boardItem : attachBoardItemList) {
				this.log.debug("\r\n ");
				this.log.debug("\r\n[itemId : " + boardItem.getItemId() + "]");
				this.log.debug("\r\n[title : " + boardItem.getTitle() + "]");
				this.log.debug("\r\n[attachCnt : " + boardItem.getAttachFileCount() + "]");
				this.log.debug("\r\n ");

				// 첨부 파일 삭제 부분
				this.fileService.removeItemFile(boardItem.getItemId());

			}
			this.log.debug("\r\n ");
			this.log.debug("\r\n[4. 삭제된  게시판 정보 삭제 프로시져 실행]");
			this.log.debug("\r\n ");
			// this.log.debug(buffer.toString());
			// buffer.delete(0, buffer.length());
			/** 권한/게시물/댓글/...배치 Job 실행 **/
			boardDao.deleteWorkspaceBoard(board.getBoardId());
			this.log.debug("\r\n[4. 해당 게시판 정보 삭제 프로시져 종료]");

		}

		this.log.debug("\r\n ====================== Workspace 에서 삭제된 게시판 영구 삭제  배치 프로세스 종료 =============================");
		this.log.debug("\r\n ===================================================================================================");

	}

	public void moveBoard() {

	}

	/**
	 * 게시판 이관
	 */
	public void moveBoardByWorkspace(String orgWorkspaceId, String orgBoardId, String targetWorkspaceId,
			String targetBoardId, User user) {

		try {

			List<Board> boardList = this.boardDao.listChildBoardAll(orgBoardId);

			String tempParentId = null;

			int i = 0;
			for (Board board : boardList) {

				// 실제 게시판 이관은 최상위의 Board의 parent만 변경하기
				// 해당 Board 의 하위 게시판은 workspaceId 만 변경처리
				if (i == 0) {

					tempParentId = board.getBoardParentId();

					/**
					 * 게시판 최상위 이관
					 */
					Map<String, String> boardMap = new HashMap<String, String>();

					boardMap.put("orgWorkspaceId", orgWorkspaceId);
					boardMap.put("orgBoardId", orgBoardId); // 게시판아이디
					boardMap.put("targetWorkspaceId", targetWorkspaceId);

					boardMap.put("targetBoardId", targetBoardId); // 부모 게시판 아이디
					boardMap.put("updaterId", user.getUserId());
					boardMap.put("updaterName", user.getUserName());

					this.boardDao.updateMoveBoardByWorkspace(boardMap);

					// 해당 Board 의 하위 게시판은 workspaceId 만 변경처리
					Map<String, String> map = new HashMap<String, String>();
					map.put("workspaceId", targetWorkspaceId);
					map.put("boardId", orgBoardId);
					this.boardDao.updateChildBoardAllWorkspace(map);

					i++;

				} else {
					tempParentId = board.getBoardParentId();
				}

				/**
				 * 게시판 Mirgration 테이블 등록 (권한 정보 변경을 위한)
				 */
				BoardMigration boardMigration = new BoardMigration();
				String migrationId = idgenService.getNextId();

				boardMigration.setMigrationId(migrationId);
				boardMigration.setBoardId(board.getBoardId());
				boardMigration.setTargetWorkspaceId(targetWorkspaceId);
				boardMigration.setTargetParentBoardId(tempParentId);
				boardMigration.setRegisterId(user.getUserId());
				boardMigration.setRegisterName(user.getUserName());

				boardMigrationDao.create(boardMigration);

				/**
				 * 기존 동맹정보 삭제
				 */
				boardDao.removeBoardAlliance(board.getBoardId());

				tempParentId = board.getBoardId();

			}

		} catch (Exception exception) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("\r\nWorkspace 게시판 이관 오류...... ");

			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			this.log.error(exception.getStackTrace());
		}
	}

	/**
	 * 태그 등록
	 * 
	 * @param
	 */
	private void createTag(Board board, User user) {

		// 태그 등록 - 태그 있을때 등록
		if (!StringUtil.isEmpty(board.getTag())) {
			Tag tag = new Tag();

			tag.setTagName(board.getTag()); // 사용자가 작성한 tag
			tag.setTagItemId(board.getBoardId()); // BOARD ID
			tag.setTagItemType(WorkspaceConstants.ITEM_TYPE_NAME); // 모듈 타입 정의되어
																	// 있음.
			//tag.setTagItemSubType(tagSubType); //모듈 서브 타입 - 있을때만 넣기
			tag.setTagItemSubType(board.getIsKnowhow()); // 모듈 서브 타입 - 있을때만 넣기
			tag.setTagItemName(board.getBoardName()); // 게시판 이름
			tag.setTagItemContents(board.getDescription()); // 게시판 소개글
			tag.setTagItemUrl("/collpack/collaboration/board/boardItem/listBoardItemView.do?docPopup=true&isKnowhow="
					+ board.getIsKnowhow() + "&boardId=" + board.getBoardId()); // 게시판
																					// 팝업창
																					// url
			tag.setRegisterId(user.getUserId());
			tag.setRegisterName(user.getUserName());
			tag.setPortalId(user.getPortalId());

			tagService.create(tag);
		}
	}

	/**
	 * 게시판 목록
	 */
	public List<Board> listByBoardRootId(Map<String, String> map) {
		List<Board> boardList = this.boardDao.listByBoardRootId(map);

		return boardList;
	}

	/**
	 * 게시판 조회
	 */
	public Board readBoard(Map<String, String> map) {

		Board board = boardDao.get(map);

		board.setParentList(boardDao.getParents(map));

		board.setChildList(boardDao.getChildren(map));

		List<Tag> tagList = tagService.listTagByItemId(board.getBoardId(), WorkspaceConstants.ITEM_TYPE_NAME);
		board.setTagList(tagList);

		// Read 하위 그룹 체크여부
		int readSearchSubFlag = 0;
		// Write 하위 그룹 체크여부
		int writeSearchSubFlag = 0;
		if (board.getReadPermission().equals("4")) {
			ACLResourcePermission aclResourcePermission = aclService.getSystemPermission(CLASS_NAME,
					board.getBoardId(), "READ");
			if (aclResourcePermission != null) {
				aclResourcePermission = aclService.listDetailPermission(aclResourcePermission);
				if (aclResourcePermission.getGroupPermissionList() != null) {
					for (ACLGroupPermission aclGroupPerm : aclResourcePermission.getGroupPermissionList()) {
						readSearchSubFlag = aclGroupPerm.getHierarchyPermission();
						if (readSearchSubFlag > 0) {
							board.setReadSearchSubFlag(readSearchSubFlag);
							break;
						}
					}
				}
			}
			board.setAclReadPermissionList(aclResourcePermission);
		}
		if (board.getWritePermission().equals("4")) {
			ACLResourcePermission aclResourcePermission = aclService.getSystemPermission(CLASS_NAME,
					board.getBoardId(), "WRITE");
			if (aclResourcePermission != null) {
				aclResourcePermission = aclService.listDetailPermission(aclResourcePermission);

				if (aclResourcePermission.getGroupPermissionList() != null) {
					for (ACLGroupPermission aclGroupPerm : aclResourcePermission.getGroupPermissionList()) {
						writeSearchSubFlag = aclGroupPerm.getHierarchyPermission();
						if (writeSearchSubFlag > 0) {
							board.setWriteSearchSubFlag(writeSearchSubFlag);
							break;
						}
					}
				}
			}
			board.setAclWritePermissionList(aclResourcePermission);

		}
		return board;
	}

	/**
	 * 게시판 이름 변경
	 */
	public void updateBoardName(Board board, User user) {

		try {
			boardDao.update(board);
		} catch (Exception exception) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("\r\nWorkspace 게시판 이름변경 오류...... ");

			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			this.log.error(exception.getStackTrace());
		}
	}

	/**
	 * 게시판 수정
	 */
	public void updateBoard(Board board, User user) {
		// 1Level Board 생성시 Foreign key 제약조건 오류 방지
		if ("".equals(board.getBoardParentId())) {
			board.setBoardParentId(null);
		}
		// Read 권한과 동일 처리
		if (board.getSameReadPermission() != null && board.getSameReadPermission().equals("1")) {
			board.setWritePermission(board.getReadPermission());
			board.setWriteUserList(board.getReadUserList());
			board.setWriteGroupList(board.getReadGroupList());
		}

		boardDao.update(board);

		// 게시판 태그 등록
		if (board.getTag() != null && !board.getTag().equals("")) {
			createTag(board, user);
		}
		// 동맹 그룹 권한 ( 정회원 이상 ) Read 권한 전사가 아닌경우
		if (!board.getReadPermission().equals("1")) {
			// 기존 동맹 게시판 정보 삭제
			boardDao.removeBoardAlliance(board.getBoardId());
		}
		saveReadPermission(board);
		saveWritePermission(board);
	}

	/**
	 * 게시판 삭제
	 */
	public void physicalDeleteBoard(Map<String, String> map) {

		// 게시판의 게시물 조회후 해당 게시물 삭제
		// 게시물 첨부 삭제
		// 게시물 댓글 삭제
		// 게시물에 대한 권한 삭제

		// 게시판 삭제
		// 게시판 권한 삭제

		boardDao.physicalDelete(map);

		// 권한 삭제
		aclService.deleteSystemPermission(CLASS_NAME, map.get("boardId"));

	}

	/**
	 * 게시판 임시삭제
	 */
	public void logicalDeleteBoard(String boardId) {
		boardDao.logicalDelete(boardId);
	}

	/**
	 * 게시판 상위 정보
	 */
	public List<Board> listParentBoard(Map<String, String> map) {
		return boardDao.getParents(map);
	}
	
	/**
	 * 게시판 상위 정보이름
	 */
	public String getKmsMapName(String boardId) {
		
		List<Board> parentList = boardDao.getKmsMapName(boardId);
		
		String kmsMapName = "";
		
		if(parentList.size() != 0){
			String isKnowhow = parentList.get(0).getIsKnowhow();
			if(isKnowhow.equals("0")){
				kmsMapName = "업무노하우";
			}else if(isKnowhow.equals("1")){
				kmsMapName = "일반정보";
			}else{
				kmsMapName = "원문";
			}

			for (Iterator iterator = parentList.iterator(); iterator.hasNext();) {
				Board board = (Board) iterator.next();
				kmsMapName = kmsMapName + " > " + board.getBoardName();
			}
		}
		
		return kmsMapName;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.collpack.kms.board.service.BoardAdminService
	 * #listChildrenBoard(java.util.Map)
	 */
	// public List<Board> listChildrenBoard(Map<String, String> map) {
	// return boardDao.getChildren(map);
	// }
	/**
	 * 게시판 하위 정보
	 */
	public List<Board> listChildrenBoard(Map<String, String> map) {
		return this.boardDao.listChildrenBoard(map);
	}

	/**
	 * 게시판 조회 권한
	 */
	public Board readHasPermissionBoard(Map<String, String> map) {
		return this.boardDao.readHasPermissionBoard(map);
	}

	/**
	 * 삭제된 Workspace 내의 모든 게시판 삭제 처리 (게시물 등 관련 데이터)
	 * 
	 * @param workspaceId
	 */
	public void physicalDeleteBoardBatchByWorkspaceId(String workspaceId) {

		List<Board> boardList = boardDao.listBoardByWorkspaceId(workspaceId);

		for (Board board : boardList) {

			// 게시판 권한 삭제
			aclService.deleteSystemPermission(CLASS_NAME, board.getBoardId());

			// 게시판 삭제
			boardDao.physicalDeleteBatch(board.getBoardId());
		}

	}

	/**
	 * 삭제된 게시판 영구 삭제
	 */
	public void physicalDeleteBoard() {

		List<Board> boardList = boardDao.listDeleteBoardBatch();

		for (Board board : boardList) {

			// 권한 삭제
			aclService.deleteSystemPermission(CLASS_NAME, board.getBoardId());

			// 게시판 삭제
			boardDao.physicalDeleteBatch(board.getBoardId());
		}

	}

}
