package com.lgcns.ikep4.support.fileupload.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.fileupload.dao.FileCheckDao;
import com.lgcns.ikep4.support.fileupload.model.FileCheck;
import com.lgcns.ikep4.support.fileupload.service.FileCheckService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;


/**
 * 파일권한체크 처리 서비스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: FileCheckServiceImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Service
public class FileCheckServiceImpl extends GenericServiceImpl<FileCheck, String> implements FileCheckService {

	/**
	 * 파일권한 체크 Dao
	 */
	@Autowired
	private FileCheckDao fileCheckDao;

	/**
	 * 권한체크 서비스
	 */
	@Autowired
	ACLService aclService;

	/**
	 * 파일권한 체크 (Board)
	 * 
	 * @param fileCheck
	 * @return
	 */
	private boolean checkBoard(FileCheck fileCheck) {
		final String boardId = this.fileCheckDao.getBoardId(fileCheck.getItemId());
		//게시물의 작성자인 경우 사용할수 있다.

		String author = this.fileCheckDao.getItemAuthor(fileCheck.getItemId());

		Boolean hasAdminPermission = this.aclService.hasSystemPermission("BBS", "MANAGE", "BBS", fileCheck.getUserId());
		Boolean hasBoardPermission = this.aclService.hasSystemPermission("BBS-Board", new String[]{"READ", "WRITE", "MANAGE"}, boardId, fileCheck.getUserId());

		return hasAdminPermission || hasBoardPermission || author.equals(fileCheck.getUserId());
	}

	/**
	 * 파일권한 체크 (Planner)
	 * 
	 * @param fileCheck
	 * @return
	 */
	private boolean checkPlanner(FileCheck fileCheck) {

		boolean check = true;

		FileCheck data = this.fileCheckDao.checkPlanner(fileCheck);

		if (data == null) {
			check = false;
		}

		return check;
	}

	/**
	 * 파일권한 체크 (SocialBlog)
	 * 
	 * @param fileCheck
	 * @return
	 */
	private boolean checkSocialBlog(FileCheck fileCheck) {

		boolean check = true;

		// ItemId 만으로 조회 해당 글에 대한 RegisterId를 받아 온다.
		FileCheck preData = this.fileCheckDao.checkSocialBlog(fileCheck);

		String isAuth = "N";
		// 해당 아이템에 대한 데이타가 존재 하며
		// 해당 아이템을 작성한 registerId가 UserID와 같으면 비공개 까지 조회 아니면 공개만 조회
		if (!(preData== null) && preData.getUserId().equals(fileCheck.getUserId())) {
			isAuth = "Y";
		}
		fileCheck.setIsAuth(isAuth);
		FileCheck data = this.fileCheckDao.checkSocialBlog(fileCheck);

		if (data == null) {
			check = false;
		}

		return check;
	}

	/**
	 * 파일권한 체크 (MicroBlog)
	 * 
	 * @param fileCheck
	 * @return
	 */
	private boolean checkMicroBlog(FileCheck fileCheck) {

		return this.fileCheckDao.checkMicroBlog(fileCheck);

	}

	/**
	 * 파일권한 체크 (QA)
	 * 
	 * @param fileCheck
	 * @return
	 */
	private boolean checkQA(FileCheck fileCheck) {

		boolean check = true;

		FileCheck data = this.fileCheckDao.checkQA(fileCheck);

		if (data == null) {
			check = false;
		}

		return check;
	}

	/**
	 * 파일권한 체크 (Forum)
	 * 
	 * @param fileCheck
	 * @return
	 */
	private boolean checkForum(FileCheck fileCheck) {

		boolean check = true;

		FileCheck data = this.fileCheckDao.checkForum(fileCheck);

		if (data == null) {
			check = false;
		}

		return check;
	}

	/**
	 * 파일권한 체크 (Todo)
	 * 
	 * @param fileCheck
	 * @return
	 */
	private boolean checkTodo(FileCheck fileCheck) {
		return this.fileCheckDao.checkTodo(fileCheck);
	}

	/**
	 * 파일권한 체크 (Message)
	 * 
	 * @param fileCheck
	 * @return
	 */
	private boolean checkMessage(FileCheck fileCheck) {
		return this.fileCheckDao.checkMessage(fileCheck);
	}

	/**
	 * 파일권한 체크 (WorkManual)
	 * 
	 * @param fileCheck
	 * @return
	 */
	private boolean checkWorkManual(FileCheck fileCheck) {

		boolean check = true;

		FileCheck data = this.fileCheckDao.checkWorkManual(fileCheck);

		if (data == null) {
			check = false;
		}

		return check;
	}

	/**
	 * 파일권한 체크 (WhosWho)
	 * 
	 * @param fileCheck
	 * @return
	 */
	private boolean checkWhosWho(FileCheck fileCheck) {

		boolean check = true;

		FileCheck data = this.fileCheckDao.checkWhosWho(fileCheck);

		if (data == null) {
			check = false;
		}

		return check;
	}

	/**
	 * 파일권한 체크 (CorporateVocabulary)
	 * 
	 * @param fileCheck
	 * @return
	 */
	private boolean checkCorporateVocabulary(FileCheck fileCheck) {

		boolean check = true;

		FileCheck data = this.fileCheckDao.checkCorporateVocabulary(fileCheck);

		if (data == null) {
			check = false;
		}

		return check;
	}

	/**
	 * 파일권한 체크 (Survey)
	 * 
	 * @param fileCheck
	 * @return
	 */
	private boolean checkSurvey(FileCheck fileCheck) {

		boolean check = true;

		FileCheck data = this.fileCheckDao.checkSurvey(fileCheck);

		if (data == null) {
			check = false;
		}

		return check;
	}

	/**
	 * 파일권한 체크 (Ideation)
	 * 
	 * @param fileCheck
	 * @return
	 */
	private boolean checkIdeation(FileCheck fileCheck) {

		boolean check = true;

		FileCheck data = this.fileCheckDao.checkIdeation(fileCheck);

		if (data == null) {
			check = false;
		}

		return check;
	}

	/**
	 * 파일권한 체크 (Workspace)
	 * 
	 * @param fileCheck
	 * @return
	 */
	private boolean checkWorkspace(FileCheck fileCheck) {

		boolean check = true;

		FileCheck data = this.fileCheckDao.checkWorkspace(fileCheck);

		if (data == null) {
			check = false;
		}

		return check;
	}

	/**
	 * 파일권한 체크 (Workspace board)
	 * WS 게시판 권한 체크
	 * - 게시물 권한 조회 및 버전여부 조회
	 * - 게시판 권한 조회
	 * - 운영진 이상 초회
	 * - 
	 * @param fileCheck
	 * @return
	 */
	private boolean checkWorkspaceBoard(FileCheck fileCheck) {
		boolean check = false;

		// 회원 +게시판+게시물 권한 조회 (게시판 권한따름+버전관리 포함 게시물 권한 조회)
		check = this.fileCheckDao.checkWorkspaceBoard(fileCheck);
		if(check) {
			return check;
		}
		
		// 개별설정
		check = this.aclService.hasContentPermission("WS_BD", "READ", fileCheck.getItemId(),  fileCheck.getUserId());

		return check;
	}

	/**
	 * 파일권한 체크 (Workspace weekly)
	 * 주간보고 첨부파일 조회 권한 체크
	 * - WS 멤버여부 확인 및  취합본의 경우 상위 WS 멤버 여부 확인-동시처리
	 * @param fileCheck
	 * @return
	 */
	private boolean checkWorkspaceWeekly(FileCheck fileCheck) {
		return this.fileCheckDao.checkWorkspaceWeekly(fileCheck);
	}

	/**
	 * 파일권한 체크 (Workspace announce)
	 * Workspace 공지사항 첨부 권한 체크
	 * - WS 준회원 이상 모두 조회 가능
	 * - 하위 WS 공유시 해당 WS 준회원 이상 모두 조회 가능
	 * @param fileCheck
	 * @return
	 */
	private boolean checkWorkspaceAnnounce(FileCheck fileCheck) {
		return this.fileCheckDao.checkWorkspaceAnnounce(fileCheck);
	}
	
	/**
	 * 파일권한 체크 (KMS)
	 * 
	 * @param fileCheck
	 * @return
	 */
	private boolean checkKms(FileCheck fileCheck) {

		boolean check = true;

		FileCheck data = this.fileCheckDao.checkKms(fileCheck);

		if (data == null) {
			check = false;
		}

		return check;
	}

	/**
	 * 파일권한체크 처리 (메인함수)
	 */
	public boolean checkFile(String fileId, String itemId, String itemTypeCode, String userId) {

		boolean check = true;

		FileCheck fileCheck = new FileCheck();
		fileCheck.setFileId(fileId);
		fileCheck.setItemId(itemId);
		fileCheck.setItemTypeCode(itemTypeCode);
		fileCheck.setUserId(userId);

		try {
			boolean isSystemAdmin = this.aclService.isSystemAdmin(itemTypeCode, userId);

			// System Admin 권한 체크
			if (!isSystemAdmin) {

				if (itemTypeCode.equals(IKepConstant.ITEM_TYPE_CODE_BBS)) {
					check = this.checkBoard(fileCheck);
				} else if (itemTypeCode.equals(IKepConstant.ITEM_TYPE_CODE_PLANNER)) {
					check = this.checkPlanner(fileCheck);
				} else if (itemTypeCode.equals(IKepConstant.ITEM_TYPE_CODE_SOCIAL_BLOG)) {
					check = this.checkSocialBlog(fileCheck);
				} else if (itemTypeCode.equals(IKepConstant.ITEM_TYPE_CODE_MICROBLOG)) {
					check = this.checkMicroBlog(fileCheck);
				} else if (itemTypeCode.equals(IKepConstant.ITEM_TYPE_CODE_QA)) {
					check = this.checkQA(fileCheck);
				} else if (itemTypeCode.equals(IKepConstant.ITEM_TYPE_CODE_FORUM)) {
					check = this.checkForum(fileCheck);
				} else if (itemTypeCode.equals(IKepConstant.ITEM_TYPE_CODE_TODO)) {
					check = this.checkTodo(fileCheck);
				} else if (itemTypeCode.equals(IKepConstant.ITEM_TYPE_CODE_MESSAGING)) {
					check = this.checkMessage(fileCheck);
				} else if (itemTypeCode.equals(IKepConstant.ITEM_TYPE_CODE_WORK_MANUAL)) {
					check = this.checkWorkManual(fileCheck);
				} else if (itemTypeCode.equals(IKepConstant.ITEM_TYPE_CODE_WHOSWHO)) {
					check = this.checkWhosWho(fileCheck);
				} else if (itemTypeCode.equals(IKepConstant.ITEM_TYPE_CODE_VOCABULARY)) {
					check = this.checkCorporateVocabulary(fileCheck);
				} else if (itemTypeCode.equals(IKepConstant.ITEM_TYPE_CODE_SURVEY)) {
					check = this.checkSurvey(fileCheck);
				} else if (itemTypeCode.equals(IKepConstant.ITEM_TYPE_CODE_IDEATION)) {
					check = this.checkIdeation(fileCheck);
				} else if (itemTypeCode.equals(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE)) {
					check = this.checkWorkspace(fileCheck);
				} else if (itemTypeCode.equals(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE_BOARD)) {
					check = this.checkWorkspaceBoard(fileCheck);
				} else if (itemTypeCode.equals(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE_WEEKLY)) {
					check = this.checkWorkspaceWeekly(fileCheck);
				} else if (itemTypeCode.equals(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE_ANNOUNCE)) {
					check = this.checkWorkspaceAnnounce(fileCheck);
				} else if (itemTypeCode.equals("KMS_BD")) {
					check = this.checkKms(fileCheck);
				}
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return check;
	}

}
