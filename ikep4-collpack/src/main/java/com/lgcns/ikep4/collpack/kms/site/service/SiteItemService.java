package com.lgcns.ikep4.collpack.kms.site.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.collpack.kms.site.model.SiteItem;
import com.lgcns.ikep4.collpack.kms.site.search.SiteItemSearchCondition;
//import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.support.user.member.model.User;


@Transactional
public interface SiteItemService {
	/**
	 * 공지사항 목록
	 * 
	 * @param siteItemSearchCondition
	 * @return
	 */
	SearchResult<SiteItem> listSiteItemBySearchCondition(SiteItemSearchCondition siteItemSearchCondition);

	/**
	 * 공지사항 조회
	 * 
	 * @param userId
	 * @param itemId
	 * @param workspaceId
	 * @return
	 */
//	SiteItem readSiteItem(String userId, String itemId, String workspaceId);
	SiteItem readSiteItem(String userId, String itemId);

	/**
	 * 공지사항 등록
	 * 
	 * @param siteItem
	 * @param user
	 * @return
	 */
	String createSiteItem(SiteItem siteItem, User user);

	/**
	 * 공지사항 정보 수정
	 * 
	 * @param siteItem
	 * @param user
	 */
	void updateSiteItem(SiteItem siteItem, User user);

	/**
	 * 공지 사항 삭제
	 * 
	 * @param siteItem
	 */
	void adminDeleteSiteItem(SiteItem siteItem);

	/**
	 * 공지사항 다중 삭제
	 * 
	 * @param itemIds
	 * @param workspaceId
	 * @param portalId
	 */
//	void adminMultiDeleteSiteItem(List<String> itemIds, String workspaceId, String portalId);
	void adminMultiDeleteSiteItem(List<String> itemIds, String portalId);

//	/**
//	 * Workspace 중 개설된 Workspace 의 하위 팀 목록
//	 * 
//	 * @param searchCondition
//	 * @return
//	 */
//	SearchResult<SiteItem> listChildWorkspaceInfoBySearchCondition(SiteItemSearchCondition searchCondition);

	/**
	 * 공지사항 링크 정보 등록
	 * 
	 * @param siteItemId
	 * @param workspaceIds
	 */
//	void createSiteLinkItem(String siteItemId, List<String> workspaceIds);
//	void createSiteLinkItem(String siteItemId);

//	/**
//	 * Workspace 정보 조회
//	 * 
//	 * @param portalId
//	 * @param workspaceId
//	 * @return
//	 */
//	Workspace getWorkspaceInfo(String portalId, String workspaceId);

	/**
	 * Workspace 메인 포틀릿
	 * 
	 * @param map
	 * @return
	 */
	public List<SiteItem> listSiteItemByPortlet(Map<String, String> map);

	/**
	 * 공지사항중 게시물에 첨부가 있는 게시물 목록 조회 - Workspace 삭제 배치시 첨부 파일 삭제처리
	 * 
	 * @param workspaceId
	 * @return
	 */
//	public List<SiteItem> listDeleteSiteByWorkspace(String workspaceId);
	public List<SiteItem> listDeleteSiteByWorkspace();
}
