package com.lgcns.ikep4.support.note.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.abusereporting.model.ArAbuseHistory;
import com.lgcns.ikep4.support.abusereporting.service.ArAbuseHistoryService;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.note.dao.NoteShareDao;
import com.lgcns.ikep4.support.note.model.Note;
import com.lgcns.ikep4.support.note.model.NoteShare;
import com.lgcns.ikep4.support.note.model.BoardItemVersion;
import com.lgcns.ikep4.support.note.service.NoteShareService;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;

@Service
public class NoteShareServiceImpl extends GenericServiceImpl<NoteShare, String> implements NoteShareService {

	@Autowired
	private NoteShareDao noteShareDao;
	
	@Autowired
	private IdgenService idgenService;

	@Autowired
	private FileService fileService;

	@Autowired
	private TagService tagService;

	@Autowired
	private ActivityStreamService activityStreamService;

	@Autowired
	private ArAbuseHistoryService arAbuseHistoryService;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.note.service.NoteShareService#boardListByBBS
	 * (java.lang.String)
	 */
	public List<NoteShare> boardListByBBS(String boardRootId, String portalId) {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("boardRootId", boardRootId);
		parameter.put("portalId", portalId);

		List<NoteShare> boardList = this.noteShareDao.boardListByBBS(parameter);

		List<NoteShare> returnList = new ArrayList<NoteShare>();
		for (NoteShare board : boardList) {
			returnList.add(board);
		}
				
		return returnList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.note.service.NoteShareService#boardListByBBSPer
	 * (java.lang.String)
	 */
	public List<NoteShare> boardListByBBSPer(Map<String, Object> param) {
		List<NoteShare> boardList = this.noteShareDao.boardListByBBSPer(param);

		List<NoteShare> returnList = new ArrayList<NoteShare>();
		for (NoteShare board : boardList) {
			returnList.add(board);
		}
				
		return returnList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.note.service.NoteShareService#myCollaborationListByWorkspace
	 * (java.lang.String)
	 */
	public List<NoteShare> myCollaborationListByWorkspace(Map<String, String> param){
		List<NoteShare> workspaceList = this.noteShareDao.myCollaborationListByWorkspace(param);

		return workspaceList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.note.service.NoteShareService#boardListByWorkspae
	 * (java.lang.String)
	 */
	public List<NoteShare> boardListByWorkspace(String boardRootId, String portalId, String workspaceId) {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("boardRootId", boardRootId);
		parameter.put("portalId", portalId);
		parameter.put("workspaceId", workspaceId);

		List<NoteShare> boardList = this.noteShareDao.boardListByWorkspace(parameter);

		List<NoteShare> returnList = new ArrayList<NoteShare>();
		for (NoteShare board : boardList) {
			returnList.add(board);
		}
				
		return returnList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.note.service.NoteShareService#boardListByWorkspaePer
	 * (java.lang.String)
	 */
	public List<NoteShare> boardListByWorkspacePer(Map<String, Object> parameter) {
		List<NoteShare> boardList = this.noteShareDao.boardListByWorkspacePer(parameter);

		List<NoteShare> returnList = new ArrayList<NoteShare>();
		for (NoteShare board : boardList) {
			returnList.add(board);
		}
				
		return returnList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.note.service.NoteShareService#boardListByKnowledge
	 * (java.lang.String)
	 */
	public List<NoteShare> boardListByKnowledge(String boardRootId, String portalId) {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("boardRootId", boardRootId);
		parameter.put("portalId", portalId);

		List<NoteShare> boardList = this.noteShareDao.boardListByKnowledge(parameter);

		List<NoteShare> returnList = new ArrayList<NoteShare>();
		for (NoteShare board : boardList) {
			returnList.add(board);
		}
				
		return returnList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.note.service.NoteShareService#boardListByKnowledgePer
	 * (java.lang.String)
	 */
	public List<NoteShare> boardListByKnowledgePer(Map<String, Object> parameter) {
		List<NoteShare> boardList = this.noteShareDao.boardListByKnowledgePer(parameter);

		List<NoteShare> returnList = new ArrayList<NoteShare>();
		for (NoteShare board : boardList) {
			returnList.add(board);
		}
				
		return returnList;
	}

	/**
	 * 태그들을 저장한다.
	 * 태그는 화면에서 문자열 형태로 넘어온다. (ex: Tag-A, Tag-B, Tag-C)
	 *
	 * @param noteShare
	 */
	private void saveTags(NoteShare noteShare, String type) {

		String tagItemType = TagConstants.ITEM_TYPE_BBS;
		String tagUrl = "/lightpack/board/boardItem/readBoardItemView.do?popupYn=true&boardId=" + noteShare.getBoardId()+ "&itemId="+noteShare.getItemId();
		if (type.equals("W")) {
			 tagItemType = TagConstants.ITEM_TYPE_WORKSPACE;
			 tagUrl = "/collpack/collaboration/board/boardItem/readBoardItemView.do?&popupYn=true&boardId=" + noteShare.getBoardId() + "&itemId=" + noteShare.getItemId();
		} else if (type.equals("K")) {
			tagItemType = TagConstants.ITEM_TYPE_KNOWLEDGE;
			tagUrl = "/collpack/knowledge/board/boardItem/readBoardItemView.do?popupYn=true&categoryId=" + noteShare.getBoardId()+ "&itemId="+noteShare.getItemId();
		}
		//태그저장
		if(!StringUtils.isEmpty(noteShare.getTag())) {
			Tag tag = new Tag();

			tag.setTagName(noteShare.getTag());
			tag.setTagItemId(noteShare.getItemId());
			tag.setTagItemType(tagItemType);
			tag.setTagItemSubType(noteShare.getWorkspaceId());
			tag.setTagItemName(noteShare.getTitle());
			tag.setTagItemContents(noteShare.getContents());
			tag.setTagItemUrl(tagUrl);
			tag.setRegisterId(noteShare.getRegisterId());
			tag.setRegisterName(noteShare.getRegisterName());
			tag.setPortalId(noteShare.getPortalId());

			this.tagService.create(tag);
		}
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.note.service.NoteShareService#createBoardItemForBBS(com.lgcns.ikep4.support.note.model.NoteShare)
	 */
	@SuppressWarnings("unchecked")
	public String createBoardItemForBBS(Note note, NoteShare noteShare, User user) {
		noteShare.setStep(0);
		noteShare.setIndentation(0);
		noteShare.setItemDisplay(0);
		noteShare.setTitle(note.getTitle());
		noteShare.setContents(note.getContents());
		noteShare.setTextContents(note.getTextContents());
		noteShare.setHitCount(0);
		noteShare.setRecommendCount(0);
		noteShare.setReplyCount(0);
		noteShare.setLinereplyCount(0);
		noteShare.setAttachFileCount(0);
		noteShare.setItemDelete(0);

		//신규 아이디를 받아온다.
		String id = this.idgenService.getNextId();

		//아이디를 설정한다. 최상의 글의 경우 ItemGroupId는 ItemId와 동일하다.
		noteShare.setItemId(id);
		noteShare.setItemGroupId(id);
		
		//노트의 첨부파일 갯수를 게시물의 첨부파일 갯수로 업데이트 한다.
		if(note.getFileDataList() != null) {
			noteShare.setAttachFileCount(note.getFileDataList().size());
		}

		//노트의 썸네일 이미지를 게시물의 대표 썸네일 이미지로 한다.
		if(note.getEditorFileDataList() != null && note.getEditorFileDataList().size()>0) {
			noteShare.setImageFileId(note.getImageFileId());
		}

		//게시물을 생성한다.
		String boardItemId = this.noteShareDao.createBBS(noteShare);

		//게시물에 등록한 첨부파일의 링크 정보를 생성한다.
		if(note.getFileDataList() != null && note.getFileDataList().size() > 0) {
			List<String> fileIdList = new ArrayList<String>();
			for (FileData fileData : note.getFileDataList()) {
				fileIdList.add(fileData.getFileId());
			}
			//파일 링크 업데이트를 한다.
			this.fileService.copyForTransfer(fileIdList, boardItemId, NoteShare.ITEM_TYPE_BBS, user);
		}

		//CKEDITOR내에 첨부된 이미지 파일의 링크 정보를 생성한다.
		if(note.getEditorFileDataList() != null && note.getEditorFileDataList().size() > 0) {
			String thumnailImg = note.getEditorFileDataList().get(0).getFileId();
			for (FileData fileData : note.getEditorFileDataList()) {
				String newFileLinkId = this.fileService.copyByFileLinkForEditor(fileData.getFileId(), boardItemId, NoteShare.ITEM_TYPE_BBS, user);
				//String oldFileLink = "/support/fileupload/downloadFile.do?fileId=" + fileData.getFileId();
				//String newFileLink = "/support/fileupload/downloadFile.do?fileId=" + newFileLinkId;
				String oldFileLink = fileData.getFileId();
				String newFileLink = newFileLinkId;
				noteShare.setContents(noteShare.getContents().replace(oldFileLink, newFileLink));
				noteShare.setTextContents(noteShare.getTextContents().replace(oldFileLink, newFileLink));
				if(fileData.getFileId().equals(thumnailImg)){
					noteShare.setImageFileId(newFileLinkId);
				}
			}
			
			// 에디터 내 이미지 파일이 존재할 경우 내용 및 썸네일 이미지 정보 업데이트 처리
			this.noteShareDao.updateBBS(noteShare);
		}
				
		//태그저장
		this.saveTags(noteShare, "B");

		//Activity Stream 게시글 생성
		this.activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_BBS, IKepConstant.ACTIVITY_CODE_DOC_POST, boardItemId, noteShare.getTitle());
		
		// Abuse 체크모듈 호출
		ArAbuseHistory arAbuseHistory = new ArAbuseHistory();
		String textContents = StringUtil.extractTextFormHTML(noteShare.getContents());
		arAbuseHistory.setModuleCode("BD"); //board
		arAbuseHistory.setItemId(boardItemId);
		arAbuseHistory.setReferenceId(noteShare.getBoardId());
		arAbuseHistory.setTitle(noteShare.getTitle());
		arAbuseHistory.setContents(textContents);
		arAbuseHistory.setPortalId(user.getPortalId());
		this.arAbuseHistoryService.checkAndSaveProhibitWord(arAbuseHistory, user);
		
		return boardItemId;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.note.service.NoteShareService#createBoardItemForWS(com.lgcns.ikep4.support.note.model.NoteShare)
	 */
	@SuppressWarnings("unchecked")
	public String createBoardItemForWS(Note note, NoteShare noteShare, User user) {
		noteShare.setStep(0);
		noteShare.setIndentation(0);
		noteShare.setItemDisplay(0);
		noteShare.setTitle(note.getTitle());
		noteShare.setContents(note.getContents());
		noteShare.setTextContents(note.getTextContents());
		noteShare.setHitCount(0);
		noteShare.setRecommendCount(0);
		noteShare.setReplyCount(0);
		noteShare.setLinereplyCount(0);
		noteShare.setAttachFileCount(0);
		noteShare.setItemDelete(0);

		//신규 아이디를 받아온다.
		String id = this.idgenService.getNextId();

		//아이디를 설정한다. 최상의 글의 경우 ItemGroupId는 ItemId와 동일하다.
		noteShare.setItemId(id);
		noteShare.setItemGroupId(id);
		
		//노트의 첨부파일 갯수를 게시물의 첨부파일 갯수로 업데이트 한다.
		if(note.getFileDataList() != null) {
			noteShare.setAttachFileCount(note.getFileDataList().size());
		}

		//노트의 썸네일 이미지를 게시물의 대표 썸네일 이미지로 한다.
		if(note.getEditorFileDataList() != null && note.getEditorFileDataList().size()>0) {
			noteShare.setImageFileId(note.getImageFileId());
		}

		//게시물을 생성한다.
		String boardItemId = this.noteShareDao.createWS(noteShare);

		//게시물에 등록한 첨부파일의 링크 정보를 생성한다.
		if(note.getFileDataList() != null && note.getFileDataList().size() > 0) {
			List<String> fileIdList = new ArrayList<String>();
			for (FileData fileData : note.getFileDataList()) {
				fileIdList.add(fileData.getFileId());
			}
			//파일 링크 업데이트를 한다.
			this.fileService.copyForTransfer(fileIdList, boardItemId, NoteShare.ITEM_TYPE_WS, user);
		}

		//CKEDITOR내에 첨부된 이미지 파일의 링크 정보를 생성한다.
		if(note.getEditorFileDataList() != null && note.getEditorFileDataList().size() > 0) {
			String thumnailImg = note.getEditorFileDataList().get(0).getFileId();
			for (FileData fileData : note.getEditorFileDataList()) {
				String newFileLinkId = this.fileService.copyByFileLinkForEditor(fileData.getFileId(), boardItemId, NoteShare.ITEM_TYPE_WS, user);
				//String oldFileLink = "/support/fileupload/downloadFile.do?fileId=" + fileData.getFileId();
				//String newFileLink = "/support/fileupload/downloadFile.do?fileId=" + newFileLinkId;
				String oldFileLink = fileData.getFileId();
				String newFileLink = newFileLinkId;
				noteShare.setContents(noteShare.getContents().replace(oldFileLink, newFileLink));
				noteShare.setTextContents(noteShare.getTextContents().replace(oldFileLink, newFileLink));
				if(fileData.getFileId().equals(thumnailImg)){
					noteShare.setImageFileId(newFileLinkId);
				}
			}
			
			// 에디터 내 이미지 파일이 존재할 경우 내용 및 썸네일 이미지 정보 업데이트 처리
			this.noteShareDao.updateWS(noteShare);
		}
		
		//태그저장
		this.saveTags(noteShare, "W");

		//Activity Stream 게시글 생성
		this.activityStreamService.createForCollavoration(
				IKepConstant.ITEM_TYPE_CODE_WORK_SPACE,
				IKepConstant.ACTIVITY_CODE_COLL_DOC_POST,
				noteShare.getItemId(), noteShare.getTitle(), "BBS",
				noteShare.getWorkspaceId(), noteShare.getWorkspaceName());
		
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("portalId", noteShare.getPortalId());
		parameter.put("workspaceId", noteShare.getWorkspaceId());
		parameter.put("boardId", noteShare.getBoardId());

		NoteShare board = this.noteShareDao.boardByWorkspace(parameter);
		
		// 사용
		if (board.getVersionManage() == 1) {
			// 현재수정본 버전정보에 등록

			BoardItemVersion itemVersion = new BoardItemVersion();
			itemVersion.setVersionId(noteShare.getItemId());
			// itemVersion.setVersion(1.0);
			itemVersion.setItemId(noteShare.getItemId());
			itemVersion.setItemType(noteShare.getItemType());
			itemVersion.setItemDisplay(noteShare.getItemDisplay());
			itemVersion.setTitle(noteShare.getTitle());
			itemVersion.setContents(noteShare.getContents());
			itemVersion.setAttachFileCount(noteShare.getAttachFileCount());
			itemVersion.setStartDate(noteShare.getStartDate());
			itemVersion.setEndDate(noteShare.getEndDate());
			itemVersion.setRegisterId(user.getUserId());
			itemVersion.setRegisterName(user.getUserName());

			this.noteShareDao.createItemVersion(itemVersion);
		}
		
		// Abuse 체크모듈 호출
		ArAbuseHistory arAbuseHistory = new ArAbuseHistory();
		String textContents = StringUtil.extractTextFormHTML(noteShare.getContents());
		arAbuseHistory.setModuleCode("WS"); //board
		arAbuseHistory.setItemId(boardItemId);
		arAbuseHistory.setReferenceId(noteShare.getBoardId());
		arAbuseHistory.setTitle(noteShare.getTitle());
		arAbuseHistory.setContents(textContents);
		arAbuseHistory.setPortalId(user.getPortalId());
		this.arAbuseHistoryService.checkAndSaveProhibitWord(arAbuseHistory, user);
		
		return boardItemId;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.note.service.NoteShareService#createBoardItemForKW(com.lgcns.ikep4.support.note.model.NoteShare)
	 */
	@SuppressWarnings("unchecked")
	public String createBoardItemForCK(Note note, NoteShare noteShare, User user) {
		noteShare.setStep(0);
		noteShare.setIndentation(0);
		noteShare.setTitle(note.getTitle());
		noteShare.setContents(note.getContents());
		noteShare.setTextContents(note.getTextContents());
		noteShare.setHitCount(0);
		noteShare.setRecommendCount(0);
		noteShare.setReplyCount(0);
		noteShare.setLinereplyCount(0);
		noteShare.setAttachFileCount(0);
		noteShare.setItemDelete(0);

		//신규 아이디를 받아온다.
		String id = this.idgenService.getNextId();

		//아이디를 설정한다. 최상의 글의 경우 ItemGroupId는 ItemId와 동일하다.
		noteShare.setItemId(id);
		noteShare.setItemGroupId(id);
		
		//노트의 첨부파일 갯수를 게시물의 첨부파일 갯수로 업데이트 한다.
		if(note.getFileDataList() != null) {
			noteShare.setAttachFileCount(note.getFileDataList().size());
		}

		//노트의 썸네일 이미지를 게시물의 대표 썸네일 이미지로 한다.
		if(note.getEditorFileDataList() != null && note.getEditorFileDataList().size()>0) {
			noteShare.setImageFileId(note.getImageFileId());
		}

		//게시물을 생성한다.
		String boardItemId = this.noteShareDao.createCK(noteShare);

		//게시물에 등록한 첨부파일의 링크 정보를 생성한다.
		if(note.getFileDataList() != null && note.getFileDataList().size() > 0) {
			List<String> fileIdList = new ArrayList<String>();
			for (FileData fileData : note.getFileDataList()) {
				fileIdList.add(fileData.getFileId());
			}
			//파일 링크 업데이트를 한다.
			this.fileService.copyForTransfer(fileIdList, boardItemId, NoteShare.ITEM_TYPE_CK, user);
		}

		//CKEDITOR내에 첨부된 이미지 파일의 링크 정보를 생성한다.
		if(note.getEditorFileDataList() != null && note.getEditorFileDataList().size() > 0) {
			String thumnailImg = note.getEditorFileDataList().get(0).getFileId();
			for (FileData fileData : note.getEditorFileDataList()) {
				String newFileLinkId = this.fileService.copyByFileLinkForEditor(fileData.getFileId(), boardItemId, NoteShare.ITEM_TYPE_CK, user);
				//String oldFileLink = "/support/fileupload/downloadFile.do?fileId=" + fileData.getFileId();
				//String newFileLink = "/support/fileupload/downloadFile.do?fileId=" + newFileLinkId;
				String oldFileLink = fileData.getFileId();
				String newFileLink = newFileLinkId;
				noteShare.setContents(noteShare.getContents().replace(oldFileLink, newFileLink));
				noteShare.setTextContents(noteShare.getTextContents().replace(oldFileLink, newFileLink));
				if(fileData.getFileId().equals(thumnailImg)){
					noteShare.setImageFileId(newFileLinkId);
				}
			}
			
			// 에디터 내 이미지 파일이 존재할 경우 내용 및 썸네일 이미지 정보 업데이트 처리
			this.noteShareDao.updateCK(noteShare);
		}
		
		//태그저장
		this.saveTags(noteShare, "K");

		//Activity Stream 게시글 생성
		this.activityStreamService.create("CK", IKepConstant.ACTIVITY_CODE_DOC_POST, boardItemId, noteShare.getTitle());

		// Abuse 체크모듈 호출
		ArAbuseHistory arAbuseHistory = new ArAbuseHistory();
		String textContents = StringUtil.extractTextFormHTML(noteShare.getContents());
		arAbuseHistory.setModuleCode("CK"); //board
		arAbuseHistory.setItemId(boardItemId);
		arAbuseHistory.setReferenceId(noteShare.getBoardId());
		arAbuseHistory.setTitle(noteShare.getTitle());
		arAbuseHistory.setContents(textContents);
		arAbuseHistory.setPortalId(user.getPortalId());
		this.arAbuseHistoryService.checkAndSaveProhibitWord(arAbuseHistory, user);
				
		return boardItemId;
	}

	/**
	 * 세션 정보 얻기
	 * 
	 * @param name
	 * @return
	 */
	public Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}

}