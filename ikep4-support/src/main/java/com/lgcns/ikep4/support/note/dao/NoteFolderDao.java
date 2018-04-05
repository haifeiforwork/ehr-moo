package com.lgcns.ikep4.support.note.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.note.model.NoteFolder;
import com.lgcns.ikep4.support.note.search.NoteSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;

public interface NoteFolderDao extends GenericDao<NoteFolder, String> {

	/**
	 * 루트 폴더  ID에 종속되어 있는 모든 폴더 목록을 조회한다.
	 *
	 * @param boardRootId 부모 게시판 아이디
	 * @return 루트 폴더 ID에 종속되어 있는 모든 폴더 목록
	 */
	public List<NoteFolder> listByFolderRootId(Map<String, Object> parameter);

	/**
	 * 사용자가 생성한 모든 폴더 목록을 조회한다.
	 *
	 * @param portalId
	 * @param userId
	 * @return 사용자가 생성한 모든 폴더 목록
	 */
	public List<NoteFolder> listByUserFolder(Map<String, Object> parameter);

	/**
	 * 사용자에게 공유된 모든 폴더 목록을 조회한다.
	 *
	 * @param portalId
	 * @param userId
	 * @return 사용자에게 공유된 모든 폴더 목록
	 */
	public List<NoteFolder> listBySharedFolder(Map<String, Object> parameter);

	/**
	 * 사용자가 이동 가능한 모든 폴더 목록을 조회한다.
	 *
	 * @param portalId
	 * @param userId
	 * @param folderId
	 * @return 사용자가 생성한 모든 폴더 중 현재 선택된 노트가 속한 폴더를 제외한 폴더 목록
	 */
	public List<NoteFolder> listByMoveUserFolder(Map<String, Object> parameter);

	/**
	 * 자식 게시판을 조회한다.
	 * 
	 * @param boardId
	 * @return
	 */
	public List<NoteFolder> listChildrenFolder(Map<String, Object> parameter);

	public List<NoteFolder> selectAll(NoteSearchCondition noteSearchCondition);
	
	public Integer selectAllTotalCount(NoteSearchCondition noteSearchCondition);

	/**
	 * 폴더명 존재하는 체크
	 */
	public boolean existFolderName(Map<String, String> param);
	
	/**
	 * 폴더저장
	 * @param NoteFolder
	 */
	String createFolder(NoteFolder noteFolder);
	
	/**
	 * 기존 기본 폴더 Clear
	 * @param NoteFolder
	 */
	public void clearDefaultFolder(NoteFolder noteFolder);
	
	/**
	 * 폴더수정
	 * @param NoteFolder
	 */
	public void updateFolder(NoteFolder noteFolder);

	/**
	 * 폴더의 위치을 변경한다.
	 *
	 * @param NoteFolder
	 */
	void updateMove(NoteFolder noteFolder);

	/**
	 * 파라미터로 들어온 폴더보다 정렬값이 큰 폴더를 대상으로 현재 정렬값을 1씩 증가시킨다.
	 *
	 * @param NoteFolder 정렬 대상 게시판 모델 객체
	 */
	void updateSortOderIncrease(NoteFolder noteFolder);

	/**
	 * 파라미터로 들어온 폴더보다 정렬값이 큰 폴더를 대상으로 현재 정렬값을 1씩 감소시킨다.
	 *
	 * @param NoteFolder 정렬 대상 게시판 모델 객체
	 */
	void updateSortOderDecrease(NoteFolder noteFolder);

	/**
	 * 폴더에 삭제
	 * @param 	folderId
	 * @return 	void
	 */
	public void deleteFolder(String folderId);

	/**
	 * 사용자의 기본 폴더 정보를 조회한다.
	 *
	 * @param userId 사용자 아이디
	 * @return 사용자의 기본 폴더 정보
	 */
	public NoteFolder getDefaultFolder(String userId);

}