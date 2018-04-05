package com.lgcns.ikep4.collpack.collaboration.board.announce.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.collpack.collaboration.board.announce.model.AnnounceItem;
import com.lgcns.ikep4.collpack.collaboration.board.announce.search.AnnounceItemSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.support.user.member.model.User;


@Transactional
public interface AnnounceItemService {
	/**
	 * 공지사항 목록
	 * 
	 * @param announceItemSearchCondition
	 * @return
	 */
	SearchResult<AnnounceItem> listAnnounceItemBySearchCondition(AnnounceItemSearchCondition announceItemSearchCondition);

	/**
	 * 공지사항 조회
	 * 
	 * @param userId
	 * @param itemId
	 * @param workspaceId
	 * @return
	 */
	AnnounceItem readAnnounceItem(String userId, String itemId, String workspaceId);

	/**
	 * 공지사항 등록
	 * 
	 * @param announceItem
	 * @param user
	 * @return
	 */
	String createAnnounceItem(AnnounceItem announceItem, User user);

	/**
	 * 공지사항 정보 수정
	 * 
	 * @param announceItem
	 * @param user
	 */
	void updateAnnounceItem(AnnounceItem announceItem, User user);

	/**
	 * 공지 사항 삭제
	 * 
	 * @param announceItem
	 */
	void adminDeleteAnnounceItem(AnnounceItem announceItem);

	/**
	 * 공지사항 다중 삭제
	 * 
	 * @param itemIds
	 * @param workspaceId
	 * @param portalId
	 */
	void adminMultiDeleteAnnounceItem(List<String> itemIds, String workspaceId, String portalId);

	/**
	 * Workspace 중 개설된 Workspace 의 하위 팀 목록
	 * 
	 * @param searchCondition
	 * @return
	 */
	SearchResult<AnnounceItem> listChildWorkspaceInfoBySearchCondition(AnnounceItemSearchCondition searchCondition);

	/**
	 * 공지사항 링크 정보 등록
	 * 
	 * @param announceItemId
	 * @param workspaceIds
	 */
	void createAnnounceLinkItem(String announceItemId, List<String> workspaceIds);

	/**
	 * Workspace 정보 조회
	 * 
	 * @param portalId
	 * @param workspaceId
	 * @return
	 */
	Workspace getWorkspaceInfo(String portalId, String workspaceId);

	/**
	 * Workspace 메인 포틀릿
	 * 
	 * @param map
	 * @return
	 */
	public List<AnnounceItem> listAnnounceItemByPortlet(Map<String, String> map);

	/**
	 * 공지사항중 게시물에 첨부가 있는 게시물 목록 조회 - Workspace 삭제 배치시 첨부 파일 삭제처리
	 * 
	 * @param workspaceId
	 * @return
	 */
	public List<AnnounceItem> listDeleteAnnounceByWorkspace(String workspaceId);
}
