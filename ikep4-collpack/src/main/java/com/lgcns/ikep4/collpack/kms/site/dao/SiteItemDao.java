package com.lgcns.ikep4.collpack.kms.site.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.collpack.kms.site.model.SiteItem;
import com.lgcns.ikep4.collpack.kms.site.search.SiteItemSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

public interface SiteItemDao extends GenericDao<SiteItem, String> {
	/**
	 * 공지사항 목록
	 * @param siteItemSearchCondition
	 * @return
	 */
	List<SiteItem> listBySearchCondition(SiteItemSearchCondition siteItemSearchCondition);
	/**
	 * 공지사항 목록 갯수
	 * @param boardItemSearchCondition
	 * @return
	 */
	Integer countBySearchCondition(SiteItemSearchCondition boardItemSearchCondition);

	/**
	 * 공지사항 내용 조회 카운트 변경
	 * @param id
	 */
	void updateSiteHitCount(String id);

//	/**
//	 * 공지사항 등록
//	 * @param siteItem
//	 */
//	void createLinkSite(SiteItem siteItem);

	/**
	 * 공지사항 정보 조회
	 * @param itemId
	 * @param workspaceId
	 * @return
	 */
//	SiteItem getSite(String itemId, String workspaceId);
	SiteItem getSite(String itemId);

	/**
	 * 공지사항 링크 정보 삭제
	 * @param itemId
	 * @param workspaceId
	 */
//	void removeSiteLink(String itemId,String workspaceId);
	void removeSiteLink(String itemId);

	/**
	 * 공지사항 정보 삭제
	 * @param itemId
	 */
	void removeSiteItem(String itemId);

	/**
	 * 하위 팀 Workspace 정보 Count
	 * @param searchCondition
	 * @return
	 */
	Integer countChildWorkspaceBySearchCondition(SiteItemSearchCondition searchCondition);
	/**
	 *  하위 팀 Workspace 정보  목록
	 * @param searchCondition
	 * @return
	 */
	List<SiteItem> listChildWorkspaceBySearchCondition(SiteItemSearchCondition searchCondition);

	/**
	 * 공지사항 공유 링크 정보 삭제
	 * @param siteItemId
	 */
	void removeSiteShareLink(String siteItemId);

	/**
	 * 공지사항 조회 Reference 등록
	 * @param userId
	 * @param itemId
	 */
	void createSiteItemReference(String userId,String itemId);

	/**
	 * 조회 Reference 카운트
	 * @param userId
	 * @param itemId
	 * @return
	 */
	int countSiteReference(String userId, String itemId);

	/**
	 * 조회 Reference 삭제
	 * @param itemId
	 */
	void removeSiteReference(String itemId); 
	
	/**
	 * Workspace 메인 포틀릿 
	 * @param map
	 * @return
	 */
	public List<SiteItem> listSiteItemByPortlet(Map<String,String> map);
	/**
	 * Workspace 삭제 배치시 공지사항 첨부 파일 삭제
	 * @param workspaceId
	 * @return
	 */
//	public List<SiteItem> listDeleteSiteByWorkspace(String workspaceId);
	public List<SiteItem> listDeleteSiteByWorkspace();
}
