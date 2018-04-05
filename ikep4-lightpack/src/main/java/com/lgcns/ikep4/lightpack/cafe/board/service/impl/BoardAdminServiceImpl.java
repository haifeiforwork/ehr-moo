/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.board.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.lightpack.cafe.board.dao.BoardDao;
import com.lgcns.ikep4.lightpack.cafe.board.model.Board;
import com.lgcns.ikep4.lightpack.cafe.board.model.BoardGroup;
import com.lgcns.ikep4.lightpack.cafe.board.service.BoardAdminService;
import com.lgcns.ikep4.lightpack.cafe.board.service.BoardItemService;
import com.lgcns.ikep4.support.security.acl.model.ACLGroupPermission;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * BoardAdminService 구현체 클래스
 */
@Service("cfBoardAdminServiceImpl")
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

	/** 위지윅에디터에서 업로드 가능한 이미지 미디어타입 목록 */
	private final List<String> supportedImageMediaTypeList = Arrays.asList("image/gif", "image/jpeg", "image/png",
			"image/swf", "image/psd", "image/bmp", "image/tiff_ii", "image/tiff_mm", "image/jpc", "image/jp2",
			"image/jpx", "image/jb2", "image/swc", "image/iff", "image/wbmp", "image/xbm");

	private static final String CLASS_NAME = "Cafe-BD";

	@Autowired
	private TagService tagService;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.board.service.BoardAdminService#
	 * checkSupportedImageMediaType(java.lang.String)
	 */
	public Boolean checkSupportedImageMediaType(String imageType) {
		return Arrays.binarySearch(this.supportedImageMediaTypeList.toArray(new String[0]), imageType) > 0;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.board.service.BoardAdminService
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
		map.put("cafeId", board.getCafeId());
		map.put("boardId", boardId);

		// 게시판의 조상목록을 조회한다.
		board.setParentList(boardDao.getParents(map));
		// 게시판의 손자목록을 조회한다.
		board.setChildList(boardDao.getChildren(map));

		List<Tag> tagList = tagService.listTagByItemId(board.getBoardId(), IKepConstant.ITEM_TYPE_CODE_CAFE);
		board.setTagList(tagList);

		// 게시판의 조상목록을 조회한다.
		// board.setParentList(this.boardDao.getParents(boardId));

		// 게시판의 손자목록을 조회한다.
		// board.setChildList(this.boardDao.getChildren(boardId));
		/*
		 * if (board.getWritePermission().equals("4")) { //쓰기 권한 정보를 가져온다.
		 * ACLResourcePermission writePermission =
		 * this.aclService.getSystemPermission(Board.BOARD_ACL_CLASS_NAME,
		 * board.getBoardId(), "WRITE"); if(writePermission != null) {
		 * writePermission =
		 * this.aclService.listDetailPermission(writePermission); List<Group>
		 * writeGroupList = writePermission.getGroupDetailList();
		 * List<ACLGroupPermission> wirtePermissionList =
		 * writePermission.getGroupPermissionList(); if(writeGroupList != null)
		 * { List<BoardGroup> writeBoardGroupList = new ArrayList<BoardGroup>();
		 * BoardGroup boardGroup = null; for(Group group : writeGroupList) {
		 * boardGroup = new BoardGroup();
		 * boardGroup.setGroupId(group.getGroupId());
		 * boardGroup.setGroupName(group.getGroupName()); for(ACLGroupPermission
		 * aclGroupPermission : wirtePermissionList) {
		 * if(aclGroupPermission.getGroupId().equals(group.getGroupId())) {
		 * boardGroup
		 * .setHierarchied(aclGroupPermission.getHierarchyPermission());
		 * continue; } } writeBoardGroupList.add(boardGroup); }
		 * board.setWriteGroupList(writeBoardGroupList); } List<User>
		 * writeUserList = writePermission.getAssignUserDetailList();
		 * board.setWriteUserList(writeUserList); } } if
		 * (board.getReadPermission().equals("4")) { //읽기 권한 정보를 가져온다.
		 * ACLResourcePermission readPermission =
		 * this.aclService.getSystemPermission(Board.BOARD_ACL_CLASS_NAME,
		 * board.getBoardId(), "READ"); if(readPermission != null) {
		 * readPermission =
		 * this.aclService.listDetailPermission(readPermission); List<User>
		 * readUserList = readPermission.getAssignUserDetailList(); List<Group>
		 * readGroupList = readPermission.getGroupDetailList();
		 * List<ACLGroupPermission> readPermissionList =
		 * readPermission.getGroupPermissionList(); if(readGroupList != null) {
		 * List <BoardGroup> readBoardGroupList = new ArrayList<BoardGroup>();
		 * BoardGroup boardGroup = null; for(Group group : readGroupList) {
		 * boardGroup = new BoardGroup();
		 * boardGroup.setGroupId(group.getGroupId());
		 * boardGroup.setGroupName(group.getGroupName()); for(ACLGroupPermission
		 * aclGroupPermission : readPermissionList) {
		 * if(aclGroupPermission.getGroupId().equals(group.getGroupId())) {
		 * boardGroup
		 * .setHierarchied(aclGroupPermission.getHierarchyPermission()); } }
		 * readBoardGroupList.add(boardGroup); }
		 * board.setReadGroupList(readBoardGroupList); }
		 * board.setReadUserList(readUserList); } }
		 */
		return board;
	}

	/**
	 * 게시판 생성
	 */
	public String createBoard(Board board) {
		final String generatedId = this.idgenService.getNextId();

		board.setBoardId(generatedId);

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
		writePermission.setOpen((board.getWritePermission()));

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
		readPermission.setOpen((board.getReadPermission()));

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
		/*
		 * ACLResourcePermission writePermission = new ACLResourcePermission();
		 * // 시스템 리소스의 타입을 입력한다.
		 * writePermission.setClassName(Board.BOARD_ACL_CLASS_NAME); // 시스템 리소스
		 * 이름을 입력한다. writePermission.setResourceName(board.getBoardId()); // 시스템
		 * 리소스에 대한 설명을 입력한다.
		 * writePermission.setResourceDescription(board.getBoardName()); // 사용자
		 * 아이디를 입력한다. writePermission.setUserId(board.getUpdaterId()); // 사용자
		 * 이름을 입력한다. writePermission.setUserName(board.getUpdaterName()); //
		 * 오퍼레이션 이름을 입력한다. writePermission.setOperationName("WRITE");
		 * writePermission
		 * .setOpen(Integer.parseInt(board.getWritePermission()));
		 * if(board.getWritePermission().equals("0")) { // 시스템 리소스의 오픈여부를 입력한다,
		 * 1=오픈, 0=비오픈 if(board.getWriteUserList() != null) { //쓰기 권한 사용자 정보를
		 * 넣는다. for(User user : board.getWriteUserList()) {
		 * writePermission.addAssignUserId(user.getUserId()); } }
		 * if(board.getWriteGroupList() != null) { //쓰기 권한 관리자 정보를 넣는다.
		 * for(BoardGroup group : board.getWriteGroupList()) {
		 * writePermission.addGroupPermission(group.getGroupId(),
		 * group.getHierarchied()); } } }
		 * this.aclService.createSystemPermission(writePermission);
		 */
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
		readPermission.setOpen((board.getReadPermission()));
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
		/*
		 * ACLResourcePermission readPermission = new ACLResourcePermission();
		 * // 시스템 리소스의 타입을 입력한다.
		 * readPermission.setClassName(Board.BOARD_ACL_CLASS_NAME); // 시스템 리소스
		 * 이름을 입력한다. readPermission.setResourceName(board.getBoardId()); // 시스템
		 * 리소스에 대한 설명을 입력한다.
		 * readPermission.setResourceDescription(board.getBoardName()); // 사용자
		 * 아이디를 입력한다. readPermission.setUserId(board.getUpdaterId()); // 사용자 이름을
		 * 입력한다. readPermission.setUserName(board.getUpdaterName()); // 오퍼레이션
		 * 이름을 입력한다. readPermission.setOperationName("READ"); // 시스템 리소스의 오픈여부를
		 * 입력한다, 1=오픈, 0=비오픈 if (board.getReadPermission().equals("1")) {
		 * readPermission.setOpen(1); } else { readPermission.setOpen(0);
		 * List<Group> groupList = new ArrayList<Group>(); Group group = new
		 * Group(); group.setGroupId(board.getCafeId());
		 * //--group.setGroupTypeId(CafeConstants.GROUP_TYPE_ID); if
		 * (board.getReadPermission().equals("2") ||
		 * board.getReadPermission().equals("3")) { groupList =
		 * groupDao.selectOrgGroupByGroupTypeId(group); } if
		 * (board.getReadPermission().equals("2")) { for (Group group1 :
		 * groupList) { if
		 * (group1.getGroupId().equals(group1.getParentGroupId())) { continue; }
		 * else { readPermission.addGroupPermission(group1.getGroupId(), 0); } }
		 * } else if (board.getReadPermission().equals("3")) { for (Group group1
		 * : groupList) { if
		 * (group1.getGroupId().equals(group1.getParentGroupId())){ continue;
		 * }else if
		 * (group1.getGroupName().equals(CafeConstants.WS_PERMISSION_GROUP_ASSOCIATE
		 * )){ continue; }else {
		 * readPermission.addGroupPermission(group1.getGroupId(), 0); } } } else
		 * if(board.getReadPermission().equals("4")) { if(
		 * board.getReadUserList() != null) { //읽기 권한 사용자 정보를 넣는다. for(User user
		 * : board.getReadUserList()) {
		 * readPermission.addAssignUserId(user.getUserId()); } } if(
		 * board.getReadGroupList() != null) { //읽기 권한 관리자 정보를 넣는다.
		 * for(BoardGroup boardGroup : board.getReadGroupList()) {
		 * readPermission.addGroupPermission(boardGroup.getGroupId(),
		 * boardGroup.getHierarchied()); } } } } // 리소스에 대한 권한 정보를 읽어 온다.
		 * ACLResourcePermission hasAclResourcePermission =
		 * aclService.getSystemPermission(readPermission.getClassName(),
		 * readPermission.getResourceName(), readPermission.getOperationName());
		 * if (hasAclResourcePermission != null) {
		 * aclService.updateSystemPermission(readPermission); } else {
		 * aclService.createSystemPermission(readPermission); }
		 */
	}

	/**
	 * 게시판 쓰기 권한 등록/수정
	 * 
	 * @param board
	 */
	private void saveWritePermission(Board board) {
		/*
		 * ACLResourcePermission writePermission = new ACLResourcePermission();
		 * // 시스템 리소스의 타입을 입력한다.
		 * writePermission.setClassName(Board.BOARD_ACL_CLASS_NAME); // 시스템 리소스
		 * 이름을 입력한다. writePermission.setResourceName(board.getBoardId()); // 시스템
		 * 리소스에 대한 설명을 입력한다.
		 * writePermission.setResourceDescription(board.getBoardName()); // 사용자
		 * 아이디를 입력한다. writePermission.setUserId(board.getUpdaterId()); // 사용자
		 * 이름을 입력한다. writePermission.setUserName(board.getUpdaterName()); //
		 * 오퍼레이션 이름을 입력한다. writePermission.setOperationName("WRITE"); // 시스템
		 * 리소스의 오픈여부를 입력한다, 1=오픈, 0=비오픈 if
		 * (board.getWritePermission().equals("1")) {
		 * writePermission.setOpen(1); } else { writePermission.setOpen(0);
		 * List<Group> groupList = new ArrayList<Group>(); Group group = new
		 * Group(); group.setGroupId(board.getCafeId());
		 * //--group.setGroupTypeId(CafeConstants.GROUP_TYPE_ID); if
		 * (board.getWritePermission().equals("2") ||
		 * board.getWritePermission().equals("3")) { groupList =
		 * groupDao.selectOrgGroupByGroupTypeId(group); } if
		 * (board.getWritePermission().equals("2")) { for (Group group1 :
		 * groupList) { if
		 * (group1.getGroupId().equals(group1.getParentGroupId())) { continue; }
		 * else { writePermission.addGroupPermission(group1.getGroupId(), 0); }
		 * } } else if (board.getWritePermission().equals("3")) { for (Group
		 * group1 : groupList) { if
		 * (group1.getGroupId().equals(group1.getParentGroupId())) { continue; }
		 * else if
		 * (group1.getGroupName().equals(CafeConstants.WS_PERMISSION_GROUP_ASSOCIATE
		 * )) { continue; } else {
		 * writePermission.addGroupPermission(group1.getGroupId(), 0); } } }
		 * else if(board.getWritePermission().equals("4")) {
		 * if(board.getWriteUserList() != null) { //쓰기 권한 사용자 정보를 넣는다. for(User
		 * user : board.getWriteUserList()) {
		 * writePermission.addAssignUserId(user.getUserId()); } }
		 * if(board.getWriteGroupList() != null) { //쓰기 권한 관리자 정보를 넣는다.
		 * for(BoardGroup boardGroup : board.getWriteGroupList()) {
		 * writePermission.addGroupPermission(boardGroup.getGroupId(),
		 * boardGroup.getHierarchied()); } } } } // 리소스에 대한 권한 정보를 읽어 온다.
		 * ACLResourcePermission hasAclResourcePermission =
		 * aclService.getSystemPermission(writePermission.getClassName(),
		 * writePermission.getResourceName(),
		 * writePermission.getOperationName()); if (hasAclResourcePermission !=
		 * null) { aclService.updateSystemPermission(writePermission); } else {
		 * aclService.createSystemPermission(writePermission); }
		 */
	}

	/**
	 * 게시판 정보 수정
	 */
	public void updateBoard(Board board) {

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
		// 권한을 삭제한다.
		this.aclService.deleteSystemPermission(Board.BOARD_ACL_CLASS_NAME, boardId);

		// 게시판에 게시글과 관련 정보를 모두 삭제한다.
		this.boardItemService.adminDeleteBoardItemInBoard(boardId);

		// 게시판을 삭제한다.
		this.boardDao.physicalDelete(boardId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.board.service.BoardAdminService
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

		// Read 권한과 동일 처리
		/*
		 * if(board.getSameReadPermission() != null &&
		 * board.getSameReadPermission().equals("1")) {
		 * board.setWritePermission(board.getReadPermission());
		 * board.setWriteUserList(board.getReadUserList());
		 * board.setWriteGroupList(board.getReadGroupList()); }
		 */

		boardId = boardDao.create(board);

		// 게시판 태그 등록
		/*
		 * if(board.getTag()!=null && !board.getTag().equals("")) {
		 * createTag(board,user); }
		 */
		/*
		 * saveReadPermission(board); saveWritePermission(board);
		 */

		return boardId;
	}

	/**
	 * 게시판 이동
	 */
	public void moveCafeBoard() {
		boardDao.moveCafeBoard();

	}

	/**
	 * 삭제된 WS 내의 게시판 목록
	 */
	public List<Board> listDeleteBoardByCafe(String cafeId) {
		return this.boardDao.listDeleteBoardByCafe(cafeId);
	}

	/**
	 * 게시판 삭제
	 */
	public void deleteCafeBoard(String boardId) {
		boardDao.deleteCafeBoard(boardId);

	}

	/**
	 * 삭제된 게시판 목록 조회
	 */
	public List<Board> listDeleteBoardBatch() {
		return this.boardDao.listDeleteBoardBatch();
	}

	public void moveBoard() {

	}

	/**
	 * 게시판 이관
	 */
	@SuppressWarnings("unused")
	public void moveBoardByCafe(String orgCafeId, String orgBoardId, String targetCafeId, String targetBoardId,
			User user) {

		try {

			List<Board> boardList = this.boardDao.listChildBoardAll(orgBoardId);

			String tempParentId = null;

			int i = 0;
			for (Board board : boardList) {

				// 실제 게시판 이관은 최상위의 Board의 parent만 변경하기
				// 해당 Board 의 하위 게시판은 cafeId 만 변경처리
				if (i == 0) {

					tempParentId = board.getBoardParentId();

					/**
					 * 게시판 최상위 이관
					 */
					Map<String, String> boardMap = new HashMap<String, String>();

					boardMap.put("orgCafeId", orgCafeId);
					boardMap.put("orgBoardId", orgBoardId); // 게시판아이디
					boardMap.put("targetCafeId", targetCafeId);

					boardMap.put("targetBoardId", targetBoardId); // 부모 게시판 아이디
					boardMap.put("updaterId", user.getUserId());
					boardMap.put("updaterName", user.getUserName());

					this.boardDao.updateMoveBoardByCafe(boardMap);

					// 해당 Board 의 하위 게시판은 cafeId 만 변경처리
					Map<String, String> map = new HashMap<String, String>();
					map.put("cafeId", targetCafeId);
					map.put("boardId", orgBoardId);
					this.boardDao.updateChildBoardAllCafe(map);

					i++;

				} else {
					tempParentId = board.getBoardParentId();
				}

				tempParentId = board.getBoardId();

			}

		} catch (Exception exception) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("\r\nCafe 게시판 이관 오류...... ");

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
	@SuppressWarnings("unused")
	private void createTag(Board board, User user) {

		// 태그 등록 - 태그 있을때 등록
		if (!StringUtil.isEmpty(board.getTag())) {
			Tag tag = new Tag();

			tag.setTagName(board.getTag()); // 사용자가 작성한 tag
			tag.setTagItemId(board.getBoardId()); // BOARD ID
			tag.setTagItemType(IKepConstant.ITEM_TYPE_CODE_CAFE); // 모듈 타입 정의되어
																	// 있음.
			// tag.setTagItemSubType(tagSubType); //모듈 서브 타입 - 있을때만 넣기
			tag.setTagItemSubType(board.getCafeId()); // 모듈 서브 타입 - 있을때만 넣기
			tag.setTagItemName(board.getBoardName()); // 게시판 이름
			tag.setTagItemContents(board.getDescription()); // 게시판 소개글
			tag.setTagItemUrl("/lightpack/cafe/board/boardItem/listBoardItemView.do?docPopup=true&cafeId="
					+ board.getCafeId() + "&boardId=" + board.getBoardId()); // 게시판
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

		List<Tag> tagList = tagService.listTagByItemId(board.getBoardId(), IKepConstant.ITEM_TYPE_CODE_CAFE);
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
			buffer.append("\r\nCafe 게시판 이름변경 오류...... ");

			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			this.log.error(exception.getStackTrace());
		}
	}

	/**
	 * 게시판 수정
	 */
	public void updateBoard(Board board, User user) {

		// Read 권한과 동일 처리
		if (board.getSameReadPermission() != null && board.getSameReadPermission().equals("1")) {
			board.setWritePermission(board.getReadPermission());
			board.setWriteUserList(board.getReadUserList());
			board.setWriteGroupList(board.getReadGroupList());
		}

		boardDao.update(board);

		// 게시판 태그 등록
		/*
		 * if(board.getTag()!=null && !board.getTag().equals("")) {
		 * createTag(board,user); }
		 */
		// 동맹 그룹 권한 ( 정회원 이상 ) Read 권한 전사가 아닌경우
		/*
		 * if (!board.getReadPermission().equals("1")) { // 기존 동맹 게시판 정보 삭제
		 * boardDao.removeBoardAlliance(board.getBoardId()); }
		 */
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

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.collpack.collaboration.board.board.service.BoardAdminService
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
	 * 삭제된 Cafe 내의 모든 게시판 삭제 처리 (게시물 등 관련 데이터)
	 * 
	 * @param cafeId
	 */
	public void physicalDeleteBoardBatchByCafeId(String cafeId) {

		List<Board> boardList = boardDao.listBoardByCafeId(cafeId);

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

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.cafe.board.service.BoardAdminService#
	 * listByMenuBoardRootId(java.util.Map)
	 */
	public List<Board> listByMenuBoardRootId(Map<String, String> map) {
		List<Board> boardList = this.boardDao.listByMenuBoardRootId(map);

		return boardList;
	}

	/**
	 * 카페 글쓰기 가능한 게시판 리스트
	 */
	public List<Board> getCafeWriteBoard(String cafeId) {
		return this.boardDao.getCafeWriteBoard(cafeId);
	}
	
	/**
	 * 카페에 소속된 전체 게시판 아이디 목록
	 * @param cafeId
	 * @return
	 */
	public List<String> listBoardIdForCafe(String cafeId) {
		List<String> boardIdList = this.boardDao.listBoardIdForCafe(cafeId);
		
		return boardIdList;
	}

}
