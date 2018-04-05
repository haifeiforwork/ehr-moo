package com.lgcns.ikep4.support.note.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.note.dao.NoteFolderDao;
import com.lgcns.ikep4.support.note.model.NoteFolder;
import com.lgcns.ikep4.support.note.search.NoteSearchCondition;
import com.lgcns.ikep4.support.note.service.NoteFolderService;
import com.lgcns.ikep4.support.note.service.NoteService;
import com.lgcns.ikep4.support.note.service.NoteSharedUserService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;

@Service
public class NoteFolderServiceImpl extends GenericServiceImpl<NoteFolder, String> implements NoteFolderService {

	@Autowired
	private NoteFolderDao noteFolderDao;
	
	@Autowired
	private IdgenService idgenService;


	@Autowired
	private NoteService noteService;


	@Autowired
	private NoteSharedUserService noteSharedUserService;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.note.service.NoteFolderService#listByBoardRootId
	 * (java.lang.String)
	 */
	public List<NoteFolder> listByFolderRootId(String portalId, String userId) {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("portalId", portalId);
		parameter.put("userId", userId);

		List<NoteFolder> noteFolderList = this.noteFolderDao.listByFolderRootId(parameter);

		return noteFolderList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.note.service.NoteFolderService#listByUserFolder
	 * (java.lang.String)
	 */
	public List<NoteFolder> listByUserFolder(String portalId, String userId) {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("portalId", portalId);
		parameter.put("userId", userId);

		List<NoteFolder> noteFolderList = this.noteFolderDao.listByUserFolder(parameter);

		return noteFolderList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.note.service.NoteFolderService#listBySharedFolder
	 * (java.lang.String)
	 */
	public List<NoteFolder> listBySharedFolder(User user, String portalId) {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("portalId", portalId);
		parameter.put("userId", user.getUserId());

		List<NoteFolder> noteFolderList = this.noteFolderDao.listBySharedFolder(parameter);

		List<NoteFolder> returnList = new ArrayList<NoteFolder>();
		for (NoteFolder folder : noteFolderList) {
			returnList.add(folder);
		}
		
		return returnList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.note.service.NoteFolderService#listByMoveUserFolder
	 * (java.lang.String)
	 */
	public List<NoteFolder> listByMoveUserFolder(String portalId, String userId, String folderId) {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("portalId", portalId);
		parameter.put("userId", userId);
		parameter.put("folderId", folderId);

		List<NoteFolder> noteFolderList = this.noteFolderDao.listByMoveUserFolder(parameter);

		return noteFolderList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.note.service.NoteFolderService#listChildrenFolder
	 * (java.lang.String)
	 */
	public List<NoteFolder> listChildrenFolder(String folderId, String portalId, String userId) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("folderId", folderId);
		parameter.put("portalId", portalId);
		parameter.put("userId", userId);
		
		return this.noteFolderDao.listChildrenFolder(parameter);
	}

	/*
	 * (non-Javadoc)
	 * @see 
	 * com.lgcns.ikep4.support.note.service.NoteFolderService#listByGroupType
	 */
	public SearchResult<NoteFolder> listByGroupType(NoteSearchCondition noteSearchCondition) {
		
		Integer count = noteFolderDao.selectAllTotalCount(noteSearchCondition);
		noteSearchCondition.terminateSearchCondition(count);
		
		SearchResult<NoteFolder> searchResult = null;
		
		if (noteSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<NoteFolder>(noteSearchCondition);

		} else {

			List<NoteFolder> addrgroupList = noteFolderDao.selectAll(noteSearchCondition);
			searchResult = new SearchResult<NoteFolder>(addrgroupList, noteSearchCondition);
		}
		
		return searchResult;

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.note.service.NoteFolderService#readFolder(java
	 * .lang.String)
	 */
	public NoteFolder readFolder(NoteSearchCondition searchCondition) {

		NoteFolder noteFolder = new NoteFolder();
		if (searchCondition.getFolderId() == null || StringUtil.isEmpty(searchCondition.getFolderId())) {
			// 설정정보 없을 경우 '모든 폴더'로 세팅
			searchCondition.setFolderId("A");
		}
		
		if (searchCondition.getFolderId().equals("A") || searchCondition.getFolderId().equals("I") || searchCondition.getFolderId().equals("D") || searchCondition.getFolderId().equals("F")) {
			noteFolder.setFolderId(searchCondition.getFolderId());
		} else {
			// 폴더 관리 정보를 가져온다.
			noteFolder = this.noteFolderDao.get(searchCondition.getFolderId());

			//폴더 공유 정보를 세팅
			this.setupFolderSharedPermission(searchCondition, noteFolder);
		}		

		return noteFolder;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.note.service.NoteFolderService#readDefaultFolder(java
	 * .lang.String)
	 */
	public NoteFolder readDefaultFolder(NoteSearchCondition searchCondition) {

		NoteFolder noteFolder = new NoteFolder();

		// 폴더 관리 정보를 가져온다.
		noteFolder = this.noteFolderDao.getDefaultFolder(searchCondition.getUserId());

		return noteFolder;
	}

	/**
	 * 폴더 공유 정보 세팅하는 메서드
	 *
	 * @param noteFolder 폴더 모델 클래스
	 */
	private void setupFolderSharedPermission(NoteSearchCondition noteSearchCondition, NoteFolder noteFolder) {
		//공유 정보를 가져온다.
		List<User> sharedUserList = this.noteSharedUserService.listBySharedUser(noteSearchCondition);
		if(sharedUserList != null) {
			noteFolder.setSharedUserList(sharedUserList);		
		}
	}

	/**
	 * 폴더명 존재하는 체크
	 * @param 	Map<String, String> 
	 * @return 	boolean
	 */
	public boolean existFolderName(Map<String, String> param) {
		return noteFolderDao.existFolderName(param);
	}
	
	/**
	 * 폴더  생성
	 * @param NoteFolder 모델
	 */
	public String createFolder(NoteFolder noteFolder) {

		// 폴더 ID 생성
		noteFolder.setFolderId(idgenService.getNextId());

		// 기본 폴더일 경우 기존 기본 폴더 Clear
		if(noteFolder.getDefaultFlag() == 1){
			noteFolderDao.clearDefaultFolder(noteFolder);
		}
		
		// 폴더 생성
		String folderId = this.noteFolderDao.createFolder(noteFolder);

		// 공유 정보 생성 (폴더 유형이 '폴더' 이고, 공유 설정이 '공유' 일 경우만 생성)
		if(noteFolder.getFolderType() == 0 && noteFolder.getShared() == 1){
			noteSharedUserService.createSharedUser(noteFolder);
		}
		return folderId;
	}

	/**
	 * 폴더  수정
	 * @param NoteFolder 모델
	 */
	public void updateFolder(NoteFolder noteFolder) { 

		// 기본 폴더일 경우 기존 기본 폴더 Clear
		if(noteFolder.getDefaultFlag() == 1){
			noteFolderDao.clearDefaultFolder(noteFolder);
		}		
		
		// 폴더 수정
		noteFolderDao.updateFolder(noteFolder);
		
		// 공유 정보 삭제
		noteSharedUserService.physicalDeleteSharedUser(noteFolder.getFolderId());
		
		// 공유 정보 생성 (폴더 유형이 '폴더' 이고, 공유 설정이 '공유' 일 경우만 재생성)
		if(noteFolder.getFolderType() == 0 && noteFolder.getShared() == 1){
			noteSharedUserService.createSharedUser(noteFolder);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.note.service.NoteFolderService#updateFolderMove
	 * (com.lgcns.ikep4.support.note.model.NoteFolder)
	 */
	public void updateFolderMove(NoteFolder after) {

		NoteFolder before = this.noteFolderDao.get(after.getFolderId());

		// 이동전의 위치에서는 자기 기준으로 정렬숫자가 높은 놈들은 -1 해준다.
		this.noteFolderDao.updateSortOderDecrease(before);

		// 이동후의 위치에서는 자기 기준으로 정렬숙자가 높은 놈들은 +1 해준다.
		this.noteFolderDao.updateSortOderIncrease(after);
		
		//1Level Board 생성시 Foreign key 제약조건 오류 방지
		if("".equals(after.getFolderParentId()))
		{
			after.setFolderParentId(null);
		}

		this.noteFolderDao.updateMove(after);
	}
	
	/**
	 * 폴더 삭제
	 * @param 	folderId
	 * @return 	void
	 */
	public void physicalDeleteFolder(String folderId) {

		NoteFolder noteFolder = this.noteFolderDao.get(folderId);

		// 이동전의 위치에서는 자기 기준으로 정렬숫자가 높은 놈들은 -1 해준다.
		this.noteFolderDao.updateSortOderDecrease(noteFolder);
		// 폴더 삭제
		noteFolderDao.deleteFolder(folderId);
		// 폴더 하위 노트 정보 삭제
		noteService.folderDeleteStore(folderId);
		// 공유 정보 삭제
		noteSharedUserService.physicalDeleteSharedUser(folderId);
	}

}