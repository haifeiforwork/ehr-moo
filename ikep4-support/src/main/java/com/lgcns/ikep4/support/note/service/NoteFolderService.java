package com.lgcns.ikep4.support.note.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.note.model.NoteFolder;
import com.lgcns.ikep4.support.note.search.NoteSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;

@Transactional
public interface NoteFolderService extends GenericService<NoteFolder, String> {

	/**
	 * 루트 폴더  ID에 종속되어 있는 모든 폴더 목록을 조회한다.
	 *
	 * @param portalId
	 * @param userId
	 * @return 루트 폴더 ID에 종속되어 있는 모든 폴더 목록
	 */
	public List<NoteFolder> listByFolderRootId(String portalId, String userId);

	/**
	 * 사용자가 생성한 모든 폴더 목록을 조회한다.
	 *
	 * @param portalId
	 * @param userId
	 * @return 사용자가 생성한 모든 폴더 목록
	 */
	public List<NoteFolder> listByUserFolder(String portalId, String userId);

	/**
	 * 사용자에게 공유된 모든 폴더 목록을 조회한다.
	 *
	 * @param portalId
	 * @param userId
	 * @return 사용자에게 공유된 모든 폴더 목록
	 */
	public List<NoteFolder> listBySharedFolder(User user, String portalId);

	/**
	 * 사용자가 이동 가능한 모든 폴더 목록을 조회한다.
	 *
	 * @param portalId
	 * @param userId
	 * @param folderId
	 * @return 사용자가 생성한 모든 폴더 중 현재 선택된 노트가 속한 폴더를 제외한 폴더 목록
	 */
	public List<NoteFolder> listByMoveUserFolder(String portalId, String userId, String folderId);

	/**
	 * 폴더의 자식 폴더를 가져온다.
	 * 
	 * @param folderId
	 * @param portalId
	 * @return
	 */
	public List<NoteFolder> listChildrenFolder(String folderId, String portalId, String userId);

	/**
	 * 그룹별 폴터  목록
	 * @param param Map(portalId:포탈 아이디, 사용자 아이디)
	 * @return List<ApprUserDocFolder> 포탈 시스템 별 관리 트리 목록
	 */
	public SearchResult<NoteFolder> listByGroupType(NoteSearchCondition noteSearchCondition);

	/**
	 * 폴더를 조회한다.
	 *
	 * @param folderId 폴더 ID
	 * @return 폴더 모델 객체
	 */
	NoteFolder readFolder(NoteSearchCondition noteSearchCondition);

	/**
	 * 기본 폴더를 조회한다.
	 *
	 * @param userId 사용자 ID
	 * @return 폴더 모델 객체
	 */
	NoteFolder readDefaultFolder(NoteSearchCondition noteSearchCondition);
	
	/**
	 * 폴더명 존재하는 체크
	 * @param 	Map<String, String> 
	 * @return 	boolean
	 */
	public boolean existFolderName(Map<String, String> param);
	
	/**
	 * 폴더  생성
	 * @param NoteFolder 모델
	 */
	String createFolder(NoteFolder noteFolder);
	
	/**
	 * 폴더 수정
	 * @param NoteFolder 모델
	 */
	public void updateFolder(NoteFolder noteFolder);

	/**
	 * 폴더의 위치을 변경한다.
	 *
	 * @param NoteFolder 이동 대상 폴더 모델 객체
	 */
	void updateFolderMove(NoteFolder noteFolder);

	/**
	 * 폴더 삭제
	 * @param 	folderId
	 * @return 	void
	 */
	public void physicalDeleteFolder(String folderId);
	
}
