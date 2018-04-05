/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.note.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.note.dao.NoteDao;
import com.lgcns.ikep4.support.note.model.Note;
import com.lgcns.ikep4.support.note.search.NoteSearchCondition;
import com.lgcns.ikep4.support.note.service.NoteService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.tagfree.util.MimeUtil;
import com.lgcns.ikep4.support.search.service.BoardHandleService;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.abusereporting.service.ArAbuseHistoryService;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: NoteServiceImpl.java 17307 2012-02-06 04:42:08Z yruyo $
 */
@Service
@Transactional
public class NoteServiceImpl extends GenericServiceImpl<Note, String> implements NoteService {
	protected final Log log = LogFactory.getLog(getClass());
	
	
	private NoteDao noteDao;
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	public NoteServiceImpl(NoteDao dao) {
		super(dao);
		this.noteDao = dao;
	}

	@Autowired
	private TagService tagService;

	@Autowired
	private ActivityStreamService activityStreamService;
	
	@Autowired
	private FileService fileService; 

	@Autowired
	private ArAbuseHistoryService arAbuseHistoryService;

	@Autowired
	private BoardHandleService boardHandleService;
	
	@Override
	public Note read(String noteId) {
		Note note = new Note();
		note = this.dao.get(noteId);
		
		note.setFileDataList(this.fileService.getItemFile(noteId, "N"));
		return note;
	}
	
	public void folderDeleteStore(String folderId) {
		this.noteDao.folderDeleteStore(folderId);
	}

	/**
	 * 태그들을 저장한다.
	 * 태그는 화면에서 문자열 형태로 넘어온다. (ex: Tag-A, Tag-B, Tag-C)
	 *
	 * @param note the note
	 */
	private void saveTags(Note note) {
		//태그저장
		if(StringUtils.isEmpty(note.getTag())) {
			
			String tagNoteId = note.getNoteId();
			String tagNoteType = "NO";
			
			this.tagService.delete(tagNoteId, tagNoteType);;
						
		} else {
			Tag tag = new Tag();

			tag.setTagName(note.getTag());
			tag.setTagItemId(note.getNoteId());
			tag.setTagItemType("NO");
			tag.setTagItemName(note.getTitle());
			tag.setTagItemContents(note.getContents());
			tag.setTagItemUrl("/support/note/readNoteView.do?folderId=" + note.getFolderId()+ "&noteId="+note.getNoteId());
			tag.setRegisterId(note.getRegisterId());
			tag.setRegisterName(note.getRegisterName());
			tag.setPortalId(note.getPortalId());

			this.tagService.create(tag);
		}
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.note.service.NoteService#createNote(com.lgcns.ikep4.support.note.model.Note)
	 */
	@SuppressWarnings("unchecked")
	public String createNote(Note note, User user) {
		//신규 아이디를 받아온다.
		String id = this.idgenService.getNextId();

		//아이디를 설정한다.
		note.setNoteId(id);

		//ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		//ActiveX Editor 사용 여부 확인
		if("Y".equals(useActiveX)) {
			//사용자 브라우저가 IE인 경우
			if(note.getMsie()==null) {
				
			}
			else if(note.getMsie() == 1){
				try {	
					//현재 포탈 도메인 가져오기
					String portalHost = this.noteDao.getPortalHost(note.getPortalId());
					
					//현재 포탈 포트 가져오기
					String port = commonprop.getProperty("ikep4.activex.editor.port");
					//Tagfree ActiveX Editor Util => FileService, domain, port 생성자로 넘김 
					MimeUtil util = new MimeUtil(fileService, portalHost, port);
					util.setMimeValue(note.getContents());
					//Mime 데이타 decoding
					util.processDecoding();
					//editor 첨부된 이미지 확인
					if(util.getFileLinkList() != null && util.getFileLinkList().size()>0){
						note.setEditorFileLinkList(util.getFileLinkList());
					}
					//내용 가져오기
					String content = util.getDecodedHtml(false);		
					content = content.trim();
					//내용세팅
					note.setContents(content);
					
				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}

		//노트에 등록한 첨부파일의 링크 정보를 생성한다.
		if(note.getFileLinkList() != null) {
			//파일 링크 업데이트를 한다.
			this.fileService.saveFileLink(note.getFileLinkList(), note.getNoteId(), note.ITEM_TYPE, user);
		}

		//CKEDITOR내에 첨부된 이미지 파일의 링크 정보를 생성한다.
		if(note.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(note.getEditorFileLinkList(), note.getNoteId(), note.ITEM_TYPE, user);
		}

		//CKEDITOR내에 첨부된 첫번째 파일 아이디를 대표 썸네일 이미지로 한다.
		if(note.getEditorFileLinkList() != null && note.getEditorFileLinkList().size()>0) {
			if(note.getEditorFileLinkList().get(0) != null){
				note.setImageFileId(note.getEditorFileLinkList().get(0).getFileId());
			}
		}

		//노트를 생성한다.
		String noteId = this.noteDao.create(note);

		//태그저장
		//this.saveTags(note);

		//Activity Stream 게시글 생성
//		this.activityStreamService.create("NO", IKepConstant.ACTIVITY_CODE_DOC_POST, noteId, note.getTitle());
		
		// textContents update
		boardHandleService.updateTextContents("NO", noteId, note.getContents());
		
		// Abuse 체크모듈 호출
		/*
		ArAbuseHistory arAbuseHistory = new ArAbuseHistory();
		String textContents = StringUtil.extractTextFormHTML(note.getContents());
		arAbuseHistory.setModuleCode("NO"); //note
		arAbuseHistory.setItemId(noteId);
		arAbuseHistory.setReferenceId(note.getFolderId());
		arAbuseHistory.setTitle(note.getTitle());
		arAbuseHistory.setContents(textContents);
		arAbuseHistory.setPortalId(user.getPortalId());
		this.arAbuseHistoryService.checkAndSaveProhibitWord(arAbuseHistory, user);
		*/
		return noteId;
	}

	public Note readNote(String noteId) {
		//노트정보를 가져온다.
		Note note = this.noteDao.get(noteId);
		
		if (note != null){
			
			//첨부 파일 리스트를 가져와 노트정보에 넣는다
			List<FileData> fileDataList = this.fileService.getItemFile(noteId, Note.ATTECHED_FILE);
			note.setFileDataList(fileDataList);
			note.setAttachFileCount(fileDataList.size());
			
			//CKEDITOR내의 이미지 파일 리스트를 가져와 노트정보에 넣는다
			List<FileData> editorFileDataList = this.fileService.getItemFile(noteId, Note.EDITOR_FILE);
			note.setEditorFileDataList(editorFileDataList);
	
			//태그 목록을 조회하고 게시물에 넣는다
			//List<Tag> tagList = this.tagService.listTagByItemId(noteId, "NO");
			//note.setTagList(tagList);
	
			//태그 문자열을 만든다.
			/*
			if(tagList != null && tagList.size() > 0) {
				StringBuffer tagBuffer = new StringBuffer();
	
				for(Tag tag : tagList) {
					tagBuffer.append(tag.getTagName());
					tagBuffer.append(", ");
				}
	
				note.setTag(StringUtils.substringBeforeLast(tagBuffer.toString(), ","));
			}
			*/
		}

		return note;
	}

	public void updateNote(Note note, User user) {
		
		//ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		//ActiveX Editor 사용 여부 확인
		if("Y".equals(useActiveX)) {
			//사용자 브라우저가 IE인 경우
			if(note.getMsie() == 1){
				try {		
					//현재 포탈 도메인 가져오기
					String portalHost = this.noteDao.getPortalHost(note.getPortalId());					
					//현재 포탈 포트 가져오기
					String port = commonprop.getProperty("ikep4.activex.editor.port");
					//Tagfree ActiveX Editor Util => FileService, domain, port 생성자로 넘김 
					MimeUtil util = new MimeUtil(fileService, portalHost, port);
					util.setMimeValue(note.getContents());
					//Mime 데이타 decoding
					util.processDecoding();
					//editor 첨부된 이미지 확인
					if(util.getFileLinkList() != null && util.getFileLinkList().size()>0){
						List<FileLink> newFileLinkList = new ArrayList<FileLink>();
						//기존 등록된 파일 처리
						if(note.getEditorFileLinkList() != null){
							for (int i = 0; i < note.getEditorFileLinkList().size(); i++) {
								FileLink fLink = (FileLink) note.getEditorFileLinkList().get(i);
								newFileLinkList.add(fLink);
							}
						}
						//새로 등록된 파일 처리
						for (int i = 0; i < util.getFileLinkList().size(); i++) {
							FileLink fileLink = (FileLink)util.getFileLinkList().get(i);							
							newFileLinkList.add(fileLink);
						}
						
						note.setEditorFileLinkList(newFileLinkList);
					}
					//내용 가져오기
					String content = util.getDecodedHtml(false);		
					content = content.trim();
					//내용세팅
					note.setContents(content);
					
				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}
		
		//노트에 등록한 첨부파일의 링크 정보를 업데이트한다.
		if(note.getFileLinkList() != null) {												
			this.fileService.saveFileLink(note.getFileLinkList(), note.getNoteId(), note.ITEM_TYPE, user);
		}
	
		//CKEDITOR내에 첨부된 이미지 파일의 링크 정보를 업데이트한다.
		this.fileService.saveFileLinkForEditor(note.getEditorFileLinkList(), note.getNoteId(), note.ITEM_TYPE, user);

		//CKEDITOR내에 첨부된 첫번째 파일 아이디를 대표 썸네일 이미지로 한다.
		if(note.getEditorFileLinkList() != null && note.getEditorFileLinkList().size()>0) {
			if(note.getEditorFileLinkList().get(0) != null){
				note.setImageFileId(note.getEditorFileLinkList().get(0).getFileId());
			}
		} else {
			note.setImageFileId("");
		}

		//노트를 수정한다.
		this.noteDao.update(note);

		//태그저장
		//this.saveTags(note);

		//Activity Stream 게시글 수정
		this.activityStreamService.create("NO", IKepConstant.ACTIVITY_CODE_DOC_EDIT, note.getNoteId(), note.getTitle());
		
		// textContents update
		boardHandleService.updateTextContents("NO", note.getNoteId(), note.getContents());
		
		// Abuse 체크모듈 호출
		/*
		ArAbuseHistory arAbuseHistory = new ArAbuseHistory();
		String textContents = StringUtil.extractTextFormHTML(note.getContents());
		arAbuseHistory.setModuleCode("NO"); //board
		arAbuseHistory.setIsEdited(1); //수정글인 경우 1
		arAbuseHistory.setItemId(note.getNoteId());
		arAbuseHistory.setReferenceId(note.getFolderId());
		arAbuseHistory.setTitle(note.getTitle());
		arAbuseHistory.setContents(textContents);
		arAbuseHistory.setPortalId(user.getPortalId());
		this.arAbuseHistoryService.checkAndSaveProhibitWord(arAbuseHistory, user);
		*/
	}

	public Note readNoteMasterInfo(String noteId) {
		return this.noteDao.get(noteId);
	}

	public void logicalDeleteNote(Note note) {
		//논리적 삭제처리
		this.noteDao.logicalDelete(note);
		
		//Abuse 체크모듈 호출
		//this.arAbuseHistoryService.updateDeletedAbuseHistoryItem("NO", note.getNoteId());
	}

	public void restorationNote(Note note, User user) {
		//복원 처리
		this.noteDao.restorationNote(note);

		// Abuse 체크모듈 호출
		/*
		ArAbuseHistory arAbuseHistory = new ArAbuseHistory();
		String textContents = StringUtil.extractTextFormHTML(note.getContents());
		arAbuseHistory.setModuleCode("NO"); //note
		arAbuseHistory.setItemId(note.getNoteId());
		arAbuseHistory.setReferenceId(note.getFolderId());
		arAbuseHistory.setTitle(note.getTitle());
		arAbuseHistory.setContents(note.getTextContents());
		arAbuseHistory.setPortalId(user.getPortalId());
		this.arAbuseHistoryService.checkAndSaveProhibitWord(arAbuseHistory, user);
		*/
	}

	public void physicalDeleteNote(Note note) {		
		//전체 파일 삭제
		this.fileService.removeItemFile(note.getNoteId());

		//물리적 삭제처리
		this.noteDao.physicalDelete(note);
		
		//Abuse 체크모듈 호출
		//this.arAbuseHistoryService.updateDeletedAbuseHistoryItem("NO", note.getNoteId());
	}

	public void moveFolder(Note note) {
		// 노트 정보 업데이트
		this.noteDao.update(note);
	}

	public void checkPriority(Note note) {
		if (note.getPriority() == 1) {
			note.setPriority(0);
		} else {
			note.setPriority(1);			
		}

		// 노트 정보 업데이트
		this.noteDao.update(note);
	}

	public SearchResult<Note> listNoteBySearchCondition(NoteSearchCondition searchCondtion) {
		Integer count = this.noteDao.countBySearchCondition(searchCondtion);

		searchCondtion.terminateSearchCondition(count);

		SearchResult<Note> searchResult = null;
		if(searchCondtion.isEmptyRecord()) {
			searchResult = new SearchResult<Note>(searchCondtion);

		} else {

			List<Note> noteList = this.noteDao.listBySearchCondition(searchCondtion);

			if(noteList != null && noteList.size() > 0) {
				for(Note note : noteList) {
					//태그 리스트를 가져와 게시물에 넣는다
					//List<Tag> tagList = this.tagService.listTagByItemId(note.getNoteId(), "NO");
					//note.setTagList(tagList);

					//첨부 파일 리스트를 가져와 게시물에 넣는다
					List<FileData> fileDataList = this.fileService.getItemFile(note.getNoteId(), Note.ATTECHED_FILE);
					note.setAttachFileCount(fileDataList.size());
				}
			}

			searchResult = new SearchResult<Note>(noteList, searchCondtion);
		}

		return searchResult;
	}
	
	public SearchResult<Note> webDiaryListNoteBySearchCondition(NoteSearchCondition searchCondtion) {
		Integer count = this.noteDao.countBySearchCondition(searchCondtion);

		searchCondtion.terminateSearchCondition(count);

		SearchResult<Note> searchResult = null;
		if(searchCondtion.isEmptyRecord()) {
			searchResult = new SearchResult<Note>(searchCondtion);

		} else {

			List<Note> noteList = this.noteDao.listBySearchCondition(searchCondtion);

			/*
			if(noteList != null && noteList.size() > 0) {
				for(Note note : noteList) {
					//태그 리스트를 가져와 게시물에 넣는다
					//List<Tag> tagList = this.tagService.listTagByItemId(note.getNoteId(), "NO");
					//note.setTagList(tagList);

					//첨부 파일 리스트를 가져와 게시물에 넣는다
					List<FileData> fileDataList = this.fileService.getItemFile(note.getNoteId(), Note.ATTECHED_FILE);
					note.setAttachFileCount(fileDataList.size());
				}
			}
			*/
			searchResult = new SearchResult<Note>(noteList, searchCondtion);
		}

		return searchResult;
	}	
}
