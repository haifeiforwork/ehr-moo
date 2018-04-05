package com.lgcns.ikep4.collpack.kms.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.collpack.collaboration.board.board.model.BoardGroup;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspaceConstants;
import com.lgcns.ikep4.collpack.kms.admin.dao.AdminBoardManageDao;
import com.lgcns.ikep4.collpack.kms.admin.model.Board;
import com.lgcns.ikep4.collpack.kms.admin.service.AdminBoardManageService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.security.acl.model.ACLGroupPermission;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.user.group.dao.GroupDao;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

@Service("AdminBoardManageService")
public class AdminBoardManageServiceImpl extends GenericServiceImpl<Object, String> implements AdminBoardManageService {

	@Autowired
	private AdminBoardManageDao adminBoardManageDao;
	
	@Autowired
	private ACLService aclService;
	
	/** The idgen service. */
	@Autowired
	private IdgenService idgenService;
	
	
	public List listByBoardRootId(Map<String, String> paramMap) {
		return adminBoardManageDao.listByBoardRootId(paramMap);
	}

	public List listChildrenBoard(Map<String, String> boardMap) {
		return adminBoardManageDao.listChildrenBoard(boardMap);
	}

	/**
	 * 게시판 조회
	 */
	public Board readBoard(Map<String, String> paramMap) {
		
		Board board = adminBoardManageDao.get(paramMap);
		// 게시판의 조상목록을 조회한다.
		board.setParentList(adminBoardManageDao.getParents(paramMap));		

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
		
		return board;
	}

	public void updateBoard(Board board, User user) {
		// 1Level Board 생성시 Foreign key 제약조건 오류 방지
		if ("".equals(board.getBoardParentId())) {
			board.setBoardParentId(null);
		}
		
		adminBoardManageDao.update(board);		
		saveWritePermission(board);
		
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
			if (board.getWritePermission().equals("4")) {

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

	public void updateBoardMove(Board after, Map<String, String> paramMap) {
		
		Board before = (Board)adminBoardManageDao.get(paramMap);

		// 이동전의 위치에서는 자기 기준으로 정렬숫자가 높은 놈들은 -1 해준다.
		adminBoardManageDao.updateSortOderDecrease(before);

		// 이동후의 위치에서는 자기 기준으로 정렬숙자가 높은 놈들은 +1 해준다.
		adminBoardManageDao.updateSortOderIncrease(after);
		// 1Level Board 생성시 Foreign key 제약조건 오류 방지
		if ("".equals(after.getBoardParentId())) {
			after.setBoardParentId(null);
		}
		adminBoardManageDao.updateMove(after);
		
	}

	public List<Board> listParentBoard(Map<String, String> paramMap) {
		return adminBoardManageDao.getParents(paramMap);
	}

	public String createBoard(Board board, User user) {
		String boardId = null;

		String generatedId = idgenService.getNextId();

		board.setBoardId(generatedId);
		// 1Level Board 생성시 Foreign key 제약조건 오류 방지
		if ("".equals(board.getBoardParentId())) {
			board.setBoardParentId(null);
		}		

		
		
		board.setWritePermission(board.getWritePermission());
		board.setWriteUserList(board.getWriteUserList());
		board.setWriteGroupList(board.getWriteGroupList());
		
		
		boardId = adminBoardManageDao.create(board);

		saveWritePermission(board);

		return boardId;
	}

	public void logicalDeleteBoard(Map<String, String> paramMap) {
		adminBoardManageDao.logicalDelete(paramMap);
		
	}

}
