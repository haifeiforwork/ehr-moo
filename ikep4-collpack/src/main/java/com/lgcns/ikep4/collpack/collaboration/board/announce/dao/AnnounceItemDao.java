package com.lgcns.ikep4.collpack.collaboration.board.announce.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.collpack.collaboration.board.announce.model.AnnounceItem;
import com.lgcns.ikep4.collpack.collaboration.board.announce.search.AnnounceItemSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

public interface AnnounceItemDao extends GenericDao<AnnounceItem, String> {
	/**
	 * 공지사항 목록
	 * @param announceItemSearchCondition
	 * @return
	 */
	List<AnnounceItem> listBySearchCondition(AnnounceItemSearchCondition announceItemSearchCondition);
	/**
	 * 공지사항 목록 갯수
	 * @param boardItemSearchCondition
	 * @return
	 */
	Integer countBySearchCondition(AnnounceItemSearchCondition boardItemSearchCondition);

	/**
	 * 공지사항 내용 조회 카운트 변경
	 * @param id
	 */
	void updateAnnounceHitCount(String id);

	/**
	 * 공지사항 등록
	 * @param announceItem
	 */
	void createLinkAnnounce(AnnounceItem announceItem);

	/**
	 * 공지사항 정보 조회
	 * @param itemId
	 * @param workspaceId
	 * @return
	 */
	AnnounceItem getAnnounce(String itemId, String workspaceId);

	/**
	 * 공지사항 링크 정보 삭제
	 * @param itemId
	 * @param workspaceId
	 */
	void removeAnnounceLink(String itemId,String workspaceId);

	/**
	 * 공지사항 정보 삭제
	 * @param itemId
	 */
	void removeAnnounceItem(String itemId);

	/**
	 * 하위 팀 Workspace 정보 Count
	 * @param searchCondition
	 * @return
	 */
	Integer countChildWorkspaceBySearchCondition(AnnounceItemSearchCondition searchCondition);
	/**
	 *  하위 팀 Workspace 정보  목록
	 * @param searchCondition
	 * @return
	 */
	List<AnnounceItem> listChildWorkspaceBySearchCondition(AnnounceItemSearchCondition searchCondition);

	/**
	 * 공지사항 공유 링크 정보 삭제
	 * @param announceItemId
	 */
	void removeAnnounceShareLink(String announceItemId);

	/**
	 * 공지사항 조회 Reference 등록
	 * @param userId
	 * @param itemId
	 */
	void createAnnounceItemReference(String userId,String itemId);

	/**
	 * 조회 Reference 카운트
	 * @param userId
	 * @param itemId
	 * @return
	 */
	int countAnnounceReference(String userId, String itemId);

	/**
	 * 조회 Reference 삭제
	 * @param itemId
	 */
	void removeAnnounceReference(String itemId); 
	
	/**
	 * Workspace 메인 포틀릿 
	 * @param map
	 * @return
	 */
	public List<AnnounceItem> listAnnounceItemByPortlet(Map<String,String> map);
	/**
	 * Workspace 삭제 배치시 공지사항 첨부 파일 삭제
	 * @param workspaceId
	 * @return
	 */
	public List<AnnounceItem> listDeleteAnnounceByWorkspace(String workspaceId);
	
}
