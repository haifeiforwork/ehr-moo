/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.service.impl;

import java.util.ArrayList;
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

import com.lgcns.ikep4.lightpack.board.dao.BoardDao;
import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.lightpack.board.model.BoardGroup;
import com.lgcns.ikep4.lightpack.board.service.BoardAdminService;
import com.lgcns.ikep4.lightpack.board.service.BoardItemService;
import com.lgcns.ikep4.support.security.acl.model.ACLGroupPermission;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.role.model.Role;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * BoardAdminService 구현체 클래스
 */
@Service
public class BoardAdminServiceImpl implements BoardAdminService {

	/** The log. */
	protected final Log log = LogFactory.getLog(this.getClass());


	/** The board dao. */
	@Autowired
	private BoardDao boardDao;

	/** The idgen service. */
	@Autowired
	private IdgenService idgenService;

	@SuppressWarnings("unused")
	@Autowired
	private BoardItemService boardItemService;

	@Autowired
	private ACLService aclService;

	/** 위지윅에디터에서 업로드 가능한 이미지 미디어타입 목록 */
	private final List<String> supportedImageMediaTypeList = Arrays.asList("image/gif", "image/jpeg", "image/png",
			"image/swf", "image/psd", "image/bmp", "image/tiff_ii", "image/tiff_mm", "image/jpc", "image/jp2",
			"image/jpx", "image/jb2", "image/swc", "image/iff", "image/wbmp", "image/xbm");

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardAdminService#
	 * checkSupportedImageMediaType(java.lang.String)
	 */
	public Boolean checkSupportedImageMediaType(String imageType) {

		for(String target : this.supportedImageMediaTypeList) {
			if(target.equals(imageType)) {
				return true;
			}
		}
		return false;
		//return Arrays.binarySearch(this.supportedImageMediaTypeList.toArray(new String[0]), imageType) > 0;
	}
	
	/**
	 * 게시판 목록
	 */
	public List<Board> listByBoardRootIdMap(Map<String, String> map) {
		List<Board> boardList = this.boardDao.listByBoardRootIdMap(map);
		return boardList;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.board.service.BoardAdminService#listByBoardRootId
	 * (java.lang.String)
	 */
	public List<Board> listByBoardRootIdPermission(String boardRootId, String portalId,String userId) {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("boardRootId", boardRootId);
		parameter.put("portalId", portalId);
		parameter.put("userId", userId);

		List<Board> boardList = this.boardDao.listByBoardRootIdPermission(parameter);

		return boardList;
	}
	
	
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.board.service.BoardAdminService#listByBoardRootId
	 * (java.lang.String)
	 */
	public List<Board> listByBoardRootId(String boardRootId, String portalId) {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("boardRootId", boardRootId);
		parameter.put("portalId", portalId);


		List<Board> boardList = this.boardDao.listByBoardRootId(parameter);

		return boardList;
	}
	
	public List<Board> listByBoardRootIdMobile(String boardRootId, String portalId) {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("boardRootId", boardRootId);
		parameter.put("portalId", portalId);


		List<Board> boardList = this.boardDao.listByBoardRootIdMobile(parameter);

		return boardList;
	}
	
	public List<Board> listByBoardRootIdPer(Map<String, Object> param) {
		List<Board> boardList = this.boardDao.listByBoardRootIdPer(param);

		return boardList;
	}
	
	public List<Board> listByBoardRootIdPerMobile(Map<String, Object> param) {
		List<Board> boardList = this.boardDao.listByBoardRootIdPerMobile(param);

		return boardList;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.board.service.BoardAdminService#readBoard(java
	 * .lang.String)
	 */
	public Board readBoard(String boardId) {
		Board board = this.boardDao.get(boardId);

		// 게시판의 조상목록을 조회한다.
		board.setParentList(this.boardDao.getParents(boardId));

		// 게시판의 손자목록을 조회한다.
		board.setChildList(this.boardDao.getChildren(boardId));

		//게시판 읽기 권한을 세팅
		this.setupBoardReadPermission(board);

		//게시판 쓰기 권한을 세팅
		this.setupBoardWritePermission(board);
		
		this.setupBoardAdminPermission(board);

		return board;
	}
	public Board readBoard2(String boardId) {
		Board board = this.boardDao.get(boardId);

		return board;
	}
	/**
	 * 게시판 읽기 권한을 세팅하는 메서드
	 *
	 * @param board 게시판 모델 클래스
	 */
	private void setupBoardReadPermission(Board board) {
		//읽기 권한 정보를 가져온다.
		ACLResourcePermission readPermission = this.aclService.getSystemPermission(Board.BOARD_ACL_CLASS_NAME, board.getBoardId(), "READ");
		if(readPermission != null) {
			readPermission = this.aclService.listDetailPermission(readPermission);
			List<User> readUserList = readPermission.getAssignUserDetailList();
			List<Group> readGroupList = readPermission.getGroupDetailList();
			List<Role> readRoleList = readPermission.getRoleDetailList();

			List<ACLGroupPermission> readPermissionList = readPermission.getGroupPermissionList();

			if(readGroupList != null) {
				List <BoardGroup> readBoardGroupList = new ArrayList<BoardGroup>();

				BoardGroup boardGroup = null;

				for(Group group : readGroupList) {

					boardGroup = new BoardGroup();
					boardGroup.setGroupId(group.getGroupId());
					boardGroup.setGroupName(group.getGroupName());
					//System.out.println("@@@@@@@@@@@@@@@@@@@@@:group.getGroupId():"+group.getGroupId()+":group.getGroupName():"+group.getGroupName());
					for(ACLGroupPermission aclGroupPermission : readPermissionList) {
						//System.out.println("@@@@@@@@@@@@@@@@@@@@@:aclGroupPermission.getGroupId():"+aclGroupPermission.getGroupId());
						//System.out.println("@@@@@@@@@@@@@@@@@@@@@:aclGroupPermission.getHierarchyPermission():"+aclGroupPermission.getHierarchyPermission());
						if(aclGroupPermission.getGroupId().equals(group.getGroupId())) {
							boardGroup.setHierarchied(aclGroupPermission.getHierarchyPermission());
						}
					}

					readBoardGroupList.add(boardGroup);
				}

				board.setReadGroupList(readBoardGroupList);
			}

			board.setReadUserList(readUserList);
			board.setReadRoleList(readRoleList);
			
			board.setReadPermission(readPermission.getOpen());

		}
	}

	/**
	 * 게시판 쓰기 권한을 세팅하는 메서드
	 *
	 * @param board 게시판 모델 클래스
	 */
	private void setupBoardWritePermission(Board board) {
		//쓰기 권한 정보를 가져온다.
		ACLResourcePermission writePermission = this.aclService.getSystemPermission(Board.BOARD_ACL_CLASS_NAME, board.getBoardId(), "WRITE");

		if(writePermission != null) {
			writePermission = this.aclService.listDetailPermission(writePermission);

			List<Group> writeGroupList = writePermission.getGroupDetailList();

			List<ACLGroupPermission> wirtePermissionList = writePermission.getGroupPermissionList();

			if(writeGroupList != null) {
				List<BoardGroup> writeBoardGroupList = new ArrayList<BoardGroup>();

				BoardGroup boardGroup = null;

				for(Group group : writeGroupList) {
					boardGroup = new BoardGroup();
					boardGroup.setGroupId(group.getGroupId());
					boardGroup.setGroupName(group.getGroupName());

					for(ACLGroupPermission aclGroupPermission : wirtePermissionList) {
						if(aclGroupPermission.getGroupId().equals(group.getGroupId())) {
							boardGroup.setHierarchied(aclGroupPermission.getHierarchyPermission());
							continue;
						}
					}

					writeBoardGroupList.add(boardGroup);
				}

				board.setWriteGroupList(writeBoardGroupList);
			}
			
			board.setWritePermission(writePermission.getOpen());

			List<User> writeUserList = writePermission.getAssignUserDetailList();
			board.setWriteUserList(writeUserList);
			List<Role> writeRoleList = writePermission.getRoleDetailList();
			board.setWriteRoleList(writeRoleList);
		}
	}
	
	private void setupBoardAdminPermission(Board board) {
		//쓰기 권한 정보를 가져온다.
		ACLResourcePermission adminPermission = this.aclService.getSystemPermission(Board.BOARD_ACL_CLASS_NAME, board.getBoardId(), "ADMIN");

		if(adminPermission != null) {
			adminPermission = this.aclService.listDetailPermission(adminPermission);

			List<Group> adminGroupList = adminPermission.getGroupDetailList();

			List<ACLGroupPermission> wirtePermissionList = adminPermission.getGroupPermissionList();

			if(adminGroupList != null) {
				List<BoardGroup> adminBoardGroupList = new ArrayList<BoardGroup>();

				BoardGroup boardGroup = null;

				for(Group group : adminGroupList) {
					boardGroup = new BoardGroup();
					boardGroup.setGroupId(group.getGroupId());
					boardGroup.setGroupName(group.getGroupName());

					for(ACLGroupPermission aclGroupPermission : wirtePermissionList) {
						if(aclGroupPermission.getGroupId().equals(group.getGroupId())) {
							boardGroup.setHierarchied(aclGroupPermission.getHierarchyPermission());
							continue;
						}
					}

					adminBoardGroupList.add(boardGroup);
				}

				board.setAdminGroupList(adminBoardGroupList);
			}
			
			board.setAdminPermission(adminPermission.getOpen());

			List<User> adminUserList = adminPermission.getAssignUserDetailList();
			board.setAdminUserList(adminUserList);
			List<Role> adminRoleList = adminPermission.getRoleDetailList();
			board.setAdminRoleList(adminRoleList);
		}
	}
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.board.service.BoardAdminService#createBoard
	 * (com.lgcns.ikep4.lightpack.board.model.Board)
	 */
	public String createBoard(Board board) {
		final String generatedId = this.idgenService.getNextId();

		board.setBoardId(generatedId);
		
		//1Level Board 생성시 Foreign key 제약조건 오류 방지
		if("".equals(board.getBoardParentId()))
		{
			board.setBoardParentId(null);
		}
		String boardId = this.boardDao.create(board);

		//게시판인경우 권한 정보를 저장한다.
		if("0".equals(board.getBoardType())) {
			this.createWritePermission(board);
			this.createReadPermission(board);
		}
		
		//게시판인경우 권한 정보를 저장한다.
		if("2".equals(board.getBoardType())) {
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
		ACLResourcePermission writePermission = this.aclService.getSystemPermission(Board.BOARD_ACL_CLASS_NAME, board.getBoardId(), "WRITE");
		writePermission.setAssignUserIdList(null);
		writePermission.setGroupPermissionList(null);
		writePermission.setOpen(board.getWritePermission());

		if(board.getWritePermission() == 0) {
			//쓰기 권한 사용자 정보를 넣는다.
			if(board.getWriteUserList() != null) {
				for(User user : board.getWriteUserList()) {
					writePermission.addAssignUserId(user.getUserId());

				}
			}

			//쓰기 권한 관리자 정보를 넣는다.
			if(board.getWriteGroupList() != null) {
				for(BoardGroup group : board.getWriteGroupList()) {
					writePermission.addGroupPermission(group.getGroupId(), group.getHierarchied());
				}
			}
			
			if(board.getWriteRoleList() != null) {
				//쓰기 권한 관리자 정보를 넣는다.
				
				for(Role role : board.getWriteRoleList()) {
					writePermission.addRoleId(role.getRoleId());
				}
			}
		}
		
		// portalId 읽어오기
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user",
				RequestAttributes.SCOPE_SESSION);
		
		this.aclService.updateSystemPermission(writePermission,user.getPortalId());
	}
	
	private void updateAdminPermission(Board board) {
		ACLResourcePermission adminPermission = this.aclService.getSystemPermission(Board.BOARD_ACL_CLASS_NAME, board.getBoardId(), "ADMIN");
		adminPermission.setAssignUserIdList(null);
		adminPermission.setGroupPermissionList(null);
		adminPermission.setOpen(board.getAdminPermission());

		if(board.getAdminPermission() == 0) {
			//쓰기 권한 사용자 정보를 넣는다.
			if(board.getAdminUserList() != null) {
				for(User user : board.getAdminUserList()) {
					adminPermission.addAssignUserId(user.getUserId());

				}
			}

			//쓰기 권한 관리자 정보를 넣는다.
			if(board.getAdminGroupList() != null) {
				for(BoardGroup group : board.getAdminGroupList()) {
					adminPermission.addGroupPermission(group.getGroupId(), group.getHierarchied());
				}
			}
			
			if(board.getAdminRoleList() != null) {
				//쓰기 권한 관리자 정보를 넣는다.
				
				for(Role role : board.getAdminRoleList()) {
					adminPermission.addRoleId(role.getRoleId());
				}
			}
		}
		
		// portalId 읽어오기
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user",
				RequestAttributes.SCOPE_SESSION);
		
		this.aclService.updateSystemPermission(adminPermission,user.getPortalId());
	}

	/**
	 * 읽기 권한을 수정한다.
	 *
	 * @param board 게시판 모델 객체
	 */
	private void updateReadPermission(Board board) {
		ACLResourcePermission readPermission  = this.aclService.getSystemPermission(Board.BOARD_ACL_CLASS_NAME, board.getBoardId(), "READ");
		readPermission.setAssignUserIdList(null);
		readPermission.setGroupPermissionList(null);
		readPermission.setRoleIdList(null);
		readPermission.setOpen(board.getReadPermission());
		
		if(board.getReadPermission() == 0) {
			//읽기 권한 사용자 정보를 넣는다.
			if(board.getReadUserList() != null) {
				for(User user : board.getReadUserList()) {
					readPermission.addAssignUserId(user.getUserId());
				}
			}

			//읽기 권한 관리자 정보를 넣는다.
			if(board.getReadGroupList() != null) {
				for(BoardGroup group : board.getReadGroupList()) {
					readPermission.addGroupPermission(group.getGroupId(), group.getHierarchied());
				}
			}
			
			if(board.getReadRoleList() != null) {
				//읽기 권한 관리자 정보를 넣는다.
				
				for(Role role : board.getReadRoleList()) {
					readPermission.addRoleId(role.getRoleId());
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
		ACLResourcePermission writePermission  = new ACLResourcePermission();

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
		writePermission.setOpen(board.getWritePermission());

		if(board.getWritePermission() == 0) {
			// 시스템 리소스의 오픈여부를 입력한다, 1=오픈, 0=비오픈

			if(board.getWriteUserList() != null) {
				//쓰기 권한 사용자 정보를 넣는다.
				for(User user : board.getWriteUserList()) {
					writePermission.addAssignUserId(user.getUserId());
				}
			}

			if(board.getWriteGroupList() != null) {
				//쓰기 권한 관리자 정보를 넣는다.
				for(BoardGroup group : board.getWriteGroupList()) {
					writePermission.addGroupPermission(group.getGroupId(), group.getHierarchied());
				}
			}
			
			if(board.getWriteRoleList() != null) {
				//쓰기 권한 관리자 정보를 넣는다.
				
				for(Role role : board.getWriteRoleList()) {
					writePermission.addRoleId(role.getRoleId());
				}
			}
			
		}
		this.aclService.createSystemPermission(writePermission);
	}
	
	private void createAdminPermission(Board board) {
		ACLResourcePermission adminPermission  = new ACLResourcePermission();

		// 시스템 리소스의 타입을 입력한다.
		adminPermission.setClassName(Board.BOARD_ACL_CLASS_NAME);
		// 시스템 리소스 이름을 입력한다.
		adminPermission.setResourceName(board.getBoardId());
		// 시스템 리소스에 대한 설명을 입력한다.
		adminPermission.setResourceDescription(board.getBoardName());

		// 사용자 아이디를 입력한다.
		adminPermission.setUserId(board.getUpdaterId());
		// 사용자 이름을 입력한다.
		adminPermission.setUserName(board.getUpdaterName());
		// 오퍼레이션 이름을 입력한다.
		adminPermission.setOperationName("ADMIN");
		adminPermission.setOpen(board.getAdminPermission());

		if(board.getAdminPermission() == 0) {
			// 시스템 리소스의 오픈여부를 입력한다, 1=오픈, 0=비오픈

			if(board.getAdminUserList() != null) {
				//쓰기 권한 사용자 정보를 넣는다.
				for(User user : board.getAdminUserList()) {
					adminPermission.addAssignUserId(user.getUserId());
				}
			}

			if(board.getAdminGroupList() != null) {
				//쓰기 권한 관리자 정보를 넣는다.
				for(BoardGroup group : board.getAdminGroupList()) {
					adminPermission.addGroupPermission(group.getGroupId(), group.getHierarchied());
				}
			}
			
			if(board.getAdminRoleList() != null) {
				//쓰기 권한 관리자 정보를 넣는다.
				
				for(Role role : board.getAdminRoleList()) {
					adminPermission.addRoleId(role.getRoleId());
				}
			}
			
		}
		this.aclService.createSystemPermission(adminPermission);
	}

	/**
	 * 읽기 권한을 생성한다.
	 *
	 * @param board 게시판 모델 객체
	 */
	private void createReadPermission(Board board) {
		ACLResourcePermission readPermission  = new ACLResourcePermission();
		// 시스템 리소스의 타입을 입력한다.
		readPermission.setClassName(Board.BOARD_ACL_CLASS_NAME);
		// 시스템 리소스 이름을 입력한다.
		readPermission.setResourceName(board.getBoardId());
		// 시스템 리소스에 대한 설명을 입력한다.
		readPermission.setResourceDescription(board.getBoardName());
		// 시스템 리소스의 오픈여부를 입력한다, 1=오픈, 0=비오픈
		readPermission.setOpen(board.getReadPermission());
		// 사용자 아이디를 입력한다.
		readPermission.setUserId(board.getUpdaterId());
		// 사용자 이름을 입력한다.
		readPermission.setUserName(board.getUpdaterName());
		// 오퍼레이션 이름을 입력한다.
		readPermission.setOperationName("READ");

		if(board.getReadPermission() == 0) {
			if( board.getReadUserList() != null) {
				//읽기 권한 사용자 정보를 넣는다.
				for(User user : board.getReadUserList()) {
					readPermission.addAssignUserId(user.getUserId());
				}
			}

			if( board.getReadGroupList() != null) {
				//읽기 권한 관리자 정보를 넣는다.
				for(BoardGroup group : board.getReadGroupList()) {
					readPermission.addGroupPermission(group.getGroupId(), group.getHierarchied());
				}
			}
			if(board.getReadRoleList() != null) {
				//읽기 권한 관리자 정보를 넣는다.
				
				for(Role role : board.getReadRoleList()) {
					readPermission.addRoleId(role.getRoleId());
				}
			}
		}

		this.aclService.createSystemPermission(readPermission);

	}
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.board.service.BoardAdminService#updateBoard
	 * (com.lgcns.ikep4.lightpack.board.model.Board)
	 */
	public void updateBoard(Board board) {
		
		//1Level Board 생성시 Foreign key 제약조건 오류 방지
		if("".equals(board.getBoardParentId()))
		{
			board.setBoardParentId(null);
		}
		
		this.boardDao.update(board);
		
		//게시판인 경우 권한 정보를 저장한다.
		if("0".equals(board.getBoardType())) {

			//권한이 생성되어 있는지 확인하고 생성되어 있으면 업데이트 아니면 생성을 한다.
			if(this.aclService.getSystemPermission(Board.BOARD_ACL_CLASS_NAME, board.getBoardId(), "READ") == null) {
				this.createReadPermission(board);
			} else {
				this.updateReadPermission(board);

			}
			if(this.aclService.getSystemPermission(Board.BOARD_ACL_CLASS_NAME, board.getBoardId(), "WRITE") == null) {
				this.createWritePermission(board);
			} else {
				this.updateWritePermission(board);
			}
			if(this.aclService.getSystemPermission(Board.BOARD_ACL_CLASS_NAME, board.getBoardId(), "ADMIN") == null) {
				this.createAdminPermission(board);
			} else {
				this.updateAdminPermission(board);
			}
		}
		
		//카테고리인 경우 읽기 권한 정보를 저장한다.
		if("2".equals(board.getBoardType())) {
			//권한이 생성되어 있는지 확인하고 생성되어 있으면 업데이트 아니면 생성을 한다.
			if(this.aclService.getSystemPermission(Board.BOARD_ACL_CLASS_NAME, board.getBoardId(), "READ") == null) {
				this.createReadPermission(board);
				
			} else {
				this.updateReadPermission(board);
				

			}
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.board.service.BoardAdminService#physicalDeleteBoard
	 * (java.lang.String)
	 */
	public void physicalDeleteBoard(String boardId) {
		/*
		//게시판 정보를 조회한다.
		Board board = this.boardDao.get(boardId);

		//권한을 삭제한다.
		this.aclService.deleteSystemPermission(Board.BOARD_ACL_CLASS_NAME, board.getBoardId());

		// 게시판에 게시글과 관련 정보를 모두 삭제한다.
		this.boardItemService.adminDeleteBoardItemInBoard(boardId);

		// 게시판을 삭제한다.
		this.boardDao.physicalDelete(boardId);
		 */


		this.updateBoardDeleteField(boardId, Board.BOARD_DELETE_WAIT);


		List<Board> boardList = this.boardDao.listChildrenBoard(boardId);

		for(Board board : boardList) {
			this.updateBoardDeleteField(board.getBoardId(), Board.BOARD_DELETE_WAIT);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.board.service.BoardAdminService#listParentBoard
	 * (java.lang.String)
	 */
	public List<Board> listParentBoard(String boardId) {
		return this.boardDao.getParents(boardId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.board.service.BoardAdminService#updateBoardMove
	 * (com.lgcns.ikep4.lightpack.board.model.Board)
	 */
	public void updateBoardMove(Board after) {

		Board before = this.boardDao.get(after.getBoardId());

		// 이동전의 위치에서는 자기 기준으로 정렬숫자가 높은 놈들은 -1 해준다.
		this.boardDao.updateSortOderDecrease(before);

		// 이동후의 위치에서는 자기 기준으로 정렬숙자가 높은 놈들은 +1 해준다.
		this.boardDao.updateSortOderIncrease(after);
		
		//1Level Board 생성시 Foreign key 제약조건 오류 방지
		if("".equals(after.getBoardParentId()))
		{
			after.setBoardParentId(null);
		}

		this.boardDao.updateMove(after);

	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardAdminService#
	 * readHasPermissionBoard(java.lang.String, java.lang.String)
	 */
	public Board readHasPermissionBoard(String userId, String boardRootId) {
		return this.boardDao.readHasPermissionBoard(userId, boardRootId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.board.service.BoardAdminService#listChildrenBoard
	 * (java.lang.String)
	 */
	public List<Board> listChildrenBoard(String boardId) {
		return this.boardDao.listChildrenBoard(boardId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.board.service.BoardAdminService#listChildrenBoard
	 * (java.lang.String)
	 */
	public List<Board> listChildrenBoard(String boardId, String portalId) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("boardId", boardId);
		parameter.put("portalId", portalId);
		
		return this.boardDao.listChildrenBoard(parameter);
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardAdminService#updateBoardDeleteField(java.lang.Integer)
	 */
	public void updateBoardDeleteField(String boardId, Integer status) {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("boardId", boardId);
		parameter.put("status", status);

		this.boardDao.updateBoardDeleteField(parameter);

	}
	
	public void updateBoardMenuType(boolean isTree) {
		boardDao.updateBoardMenuType(isTree ? "TREE" : "MENU");
	}
	
	public boolean isBoardMenuTree() {
		boolean isTree = false;
		String type = boardDao.selectBoardMenuType();
		
		if(type.equalsIgnoreCase("tree"))
			isTree =  true;
		
		return isTree;
	}

	public List<Board> getBoardMenuList() {
		// TODO Auto-generated method stub
		return this.boardDao.getBoardMenuList();
	}

}
