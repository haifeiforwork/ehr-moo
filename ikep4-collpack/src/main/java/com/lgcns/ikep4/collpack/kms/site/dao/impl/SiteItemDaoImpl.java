package com.lgcns.ikep4.collpack.kms.site.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.kms.site.dao.SiteItemDao;
import com.lgcns.ikep4.collpack.kms.site.model.SiteItem;
import com.lgcns.ikep4.collpack.kms.site.search.SiteItemSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

@Repository("kmsSiteItemDaoImpl")
public class SiteItemDaoImpl extends GenericDaoSqlmap<SiteItem, String> implements SiteItemDao {
	private static final String NAMESPACE = "collpack.kms.site.dao.siteItem."; 

	/*
	 * 공지사항 게시물 생성 ##
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(SiteItem object) {  
		this.sqlInsert(NAMESPACE + "create", object);
		return object.getItemId();
	}
	/*
	 * 공지사항 게시물 수정##
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(SiteItem object) { 
		this.sqlInsert(NAMESPACE + "update", object); 
	}
	
	/*
	 * 공지사항 게시물 리스트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.site.dao.SiteItemDao#listBySearchCondition(com.lgcns.ikep4.collpack.kms.board.site.search.SiteItemSearchCondition)
	 */
	public List<SiteItem> listBySearchCondition(SiteItemSearchCondition siteItemSearchCondition) { 
		return (List<SiteItem>)this.sqlSelectForList(NAMESPACE + "listBySearchCondition", siteItemSearchCondition);
	}
	
	/*
	 * 공지사항 게시물 리스트 카운트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.site.dao.SiteItemDao#countBySearchCondition(com.lgcns.ikep4.collpack.kms.board.site.search.SiteItemSearchCondition)
	 */
	public Integer countBySearchCondition(SiteItemSearchCondition siteItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", siteItemSearchCondition);
	}

	/*
	 * 공지사항 게시물 조회수 UPDATE X
	 * @see com.lgcns.ikep4.collpack.kms.board.site.dao.SiteItemDao#updateHitCount(java.lang.String)
	 */
	public void updateSiteHitCount(String itemId) {
		this.sqlUpdate(NAMESPACE + "updateSiteHitCount", itemId);  
	}

//	/*
//	 * 공지사항 게시물 공유링크 INSERT ##
//	 * @see com.lgcns.ikep4.collpack.kms.board.site.dao.SiteItemDao#createLinkSite(com.lgcns.ikep4.collpack.kms.board.site.model.SiteItem)
//	 */
//	public void createLinkSite(SiteItem siteItem) {
//		sqlInsert(NAMESPACE + "createLinkSite", siteItem);
//	}
	
	/*
	 * 공지사항 게시물 읽기 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.site.dao.SiteItemDao#getSite(java.lang.String, java.lang.String)
	 */
//	public SiteItem getSite(String itemId, String workspaceId) {
	public SiteItem getSite(String itemId) {
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("itemId", itemId);
//		param.put("workspaceId", workspaceId);
		return (SiteItem)this.sqlSelectForObject(NAMESPACE + "getSite", param);
	}
	
	/*
	 * 공지사항 공유 게시물 전체 링크삭제(원본게시물링크 제외) ##
	 * @see com.lgcns.ikep4.collpack.kms.board.site.dao.SiteItemDao#removeSiteShareLink(java.lang.String)
	 */
	public void removeSiteShareLink(String siteItemId) {
		this.sqlDelete(NAMESPACE + "removeSiteShareLink", siteItemId);
	}
	
	/*
	 * 공지사항 링크삭제 #해당아이템 링크  DELETE#
	 * @see com.lgcns.ikep4.collpack.kms.board.site.dao.SiteItemDao#physicalDeleteSiteLink(java.lang.String)
	 */
//	public void removeSiteLink(String itemId,String workspaceId) {
	public void removeSiteLink(String itemId) {
		Map<String,String> param = new HashMap<String,String>();
		param.put("itemId", itemId);
//		param.put("workspaceId", workspaceId);
		this.sqlDelete(NAMESPACE + "removeSiteLink", param); 
	}
	
	/*
	 * 공지사항 게시물 삭제 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.site.dao.SiteItemDao#physicalDeleteSiteItem(java.lang.String)
	 */
	public void removeSiteItem(String itemId) {
		this.sqlDelete(NAMESPACE + "removeSiteItem", itemId);
	}

	/*
	 * 해당 게시글 조회참조 정보 일괄삭제
	 * @see com.lgcns.ikep4.collpack.kms.board.site.dao.SiteItemDao#removeSiteReference(java.lang.String)
	 */
	public void removeSiteReference(String itemId) {
		this.sqlDelete(NAMESPACE + "removeSiteReference", itemId);
	}
	/*
	 * 하위부서중 개설 Workspace 리스트 카운트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.site.dao.SiteItemDao#countChildWorkspaceBySearchCondition(com.lgcns.ikep4.collpack.kms.board.site.search.SiteItemSearchCondition)
	 */
	public Integer countChildWorkspaceBySearchCondition(SiteItemSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countChildrenWorkspace", searchCondition);
	}
	
	/*
	 * 하위부서중 개설 Workspace 리스트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.site.dao.SiteItemDao#listChildWorkspaceBySearchCondition(com.lgcns.ikep4.collpack.kms.board.site.search.SiteItemSearchCondition)
	 */
	public List<SiteItem> listChildWorkspaceBySearchCondition(SiteItemSearchCondition searchCondition) {
		return (List<SiteItem>)this.sqlSelectForList(NAMESPACE + "listChildWorkspaceBySearchCondition", searchCondition);
	}
	
	
	public SiteItem get(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * 참조테이블 insert
	 * @see com.lgcns.ikep4.collpack.kms.board.site.dao.SiteItemDao#createSiteItemReference(java.lang.String)
	 */
	public void createSiteItemReference(String userId,String itemId) {
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("userId", userId);
		param.put("itemId", itemId);
		this.sqlInsert(NAMESPACE + "createSiteReference", param);
	}
	/*
	 * 참조테이블 정보 카운트
	 * @see com.lgcns.ikep4.collpack.kms.board.site.dao.SiteItemDao#getSiteReference(java.lang.String, java.lang.String)
	 */
	public int countSiteReference(String userId, String itemId) {
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("userId", userId);
		param.put("itemId", itemId);
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countSiteReference", param);

	}
	
	/*
	 * (non-Javadoc) Workspace 메인 포틀릿 정보
	 * @see com.lgcns.ikep4.collpack.kms.board.site.dao.SiteItemDao#listSiteItemByPortlet(java.util.Map)
	 */
	public List<SiteItem> listSiteItemByPortlet(Map<String,String> map) {
		return (List<SiteItem>)this.sqlSelectForList(NAMESPACE + "listSiteItemByPortlet", map);
	}
	/**
	 * 공지사항중 게시물에 첨부가 있는 게시물 목록 조회 - Workspace 삭제 배치시 첨부 파일 삭제처리
	 */
//	public List<SiteItem> listDeleteSiteByWorkspace(String workspaceId) { 
	public List<SiteItem> listDeleteSiteByWorkspace() {		
//		return (List<SiteItem>)this.sqlSelectForList(NAMESPACE + "listDeleteSiteByWorkspace", workspaceId);
		return (List<SiteItem>)this.sqlSelectForList(NAMESPACE + "listDeleteSiteByWorkspace");

	}
}
