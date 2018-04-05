package com.lgcns.ikep4.collpack.collaboration.board.announce.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.collaboration.board.announce.dao.AnnounceItemDao;
import com.lgcns.ikep4.collpack.collaboration.board.announce.model.AnnounceItem;
import com.lgcns.ikep4.collpack.collaboration.board.announce.search.AnnounceItemSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

@Repository
public class AnnounceItemDaoImpl extends GenericDaoSqlmap<AnnounceItem, String> implements AnnounceItemDao {
	private static final String NAMESPACE = "collpack.collaboration.board.announce.dao.announceItem."; 

	/*
	 * 공지사항 게시물 생성 ##
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(AnnounceItem object) {  
		this.sqlInsert(NAMESPACE + "create", object);
		return object.getItemId();
	}
	/*
	 * 공지사항 게시물 수정##
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(AnnounceItem object) { 
		this.sqlInsert(NAMESPACE + "update", object); 
	}
	
	/*
	 * 공지사항 게시물 리스트 ##
	 * @see com.lgcns.ikep4.collpack.collaboration.board.announce.dao.AnnounceItemDao#listBySearchCondition(com.lgcns.ikep4.collpack.collaboration.board.announce.search.AnnounceItemSearchCondition)
	 */
	public List<AnnounceItem> listBySearchCondition(AnnounceItemSearchCondition announceItemSearchCondition) { 
		return (List<AnnounceItem>)this.sqlSelectForList(NAMESPACE + "listBySearchCondition", announceItemSearchCondition);
	}
	
	/*
	 * 공지사항 게시물 리스트 카운트 ##
	 * @see com.lgcns.ikep4.collpack.collaboration.board.announce.dao.AnnounceItemDao#countBySearchCondition(com.lgcns.ikep4.collpack.collaboration.board.announce.search.AnnounceItemSearchCondition)
	 */
	public Integer countBySearchCondition(AnnounceItemSearchCondition announceItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", announceItemSearchCondition);
	}

	/*
	 * 공지사항 게시물 조회수 UPDATE X
	 * @see com.lgcns.ikep4.collpack.collaboration.board.announce.dao.AnnounceItemDao#updateHitCount(java.lang.String)
	 */
	public void updateAnnounceHitCount(String itemId) {
		this.sqlUpdate(NAMESPACE + "updateAnnounceHitCount", itemId);  
	}

	/*
	 * 공지사항 게시물 공유링크 INSERT ##
	 * @see com.lgcns.ikep4.collpack.collaboration.board.announce.dao.AnnounceItemDao#createLinkAnnounce(com.lgcns.ikep4.collpack.collaboration.board.announce.model.AnnounceItem)
	 */
	public void createLinkAnnounce(AnnounceItem announceItem) {
		sqlInsert(NAMESPACE + "createLinkAnnounce", announceItem);
	}
	
	/*
	 * 공지사항 게시물 읽기 ##
	 * @see com.lgcns.ikep4.collpack.collaboration.board.announce.dao.AnnounceItemDao#getAnnounce(java.lang.String, java.lang.String)
	 */
	public AnnounceItem getAnnounce(String itemId, String workspaceId) {
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("itemId", itemId);
		param.put("workspaceId", workspaceId);
		return (AnnounceItem)this.sqlSelectForObject(NAMESPACE + "getAnnounce", param);
	}
	
	/*
	 * 공지사항 공유 게시물 전체 링크삭제(원본게시물링크 제외) ##
	 * @see com.lgcns.ikep4.collpack.collaboration.board.announce.dao.AnnounceItemDao#removeAnnounceShareLink(java.lang.String)
	 */
	public void removeAnnounceShareLink(String announceItemId) {
		this.sqlDelete(NAMESPACE + "removeAnnounceShareLink", announceItemId);
	}
	
	/*
	 * 공지사항 링크삭제 #해당아이템 링크  DELETE#
	 * @see com.lgcns.ikep4.collpack.collaboration.board.announce.dao.AnnounceItemDao#physicalDeleteAnnounceLink(java.lang.String)
	 */
	public void removeAnnounceLink(String itemId,String workspaceId) {
		Map<String,String> param = new HashMap<String,String>();
		param.put("itemId", itemId);
		param.put("workspaceId", workspaceId);
		this.sqlDelete(NAMESPACE + "removeAnnounceLink", param); 
	}
	
	/*
	 * 공지사항 게시물 삭제 ##
	 * @see com.lgcns.ikep4.collpack.collaboration.board.announce.dao.AnnounceItemDao#physicalDeleteAnnounceItem(java.lang.String)
	 */
	public void removeAnnounceItem(String itemId) {
		this.sqlDelete(NAMESPACE + "removeAnnounceItem", itemId);
	}

	/*
	 * 해당 게시글 조회참조 정보 일괄삭제
	 * @see com.lgcns.ikep4.collpack.collaboration.board.announce.dao.AnnounceItemDao#removeAnnounceReference(java.lang.String)
	 */
	public void removeAnnounceReference(String itemId) {
		this.sqlDelete(NAMESPACE + "removeAnnounceReference", itemId);
	}
	/*
	 * 하위부서중 개설 Workspace 리스트 카운트 ##
	 * @see com.lgcns.ikep4.collpack.collaboration.board.announce.dao.AnnounceItemDao#countChildWorkspaceBySearchCondition(com.lgcns.ikep4.collpack.collaboration.board.announce.search.AnnounceItemSearchCondition)
	 */
	public Integer countChildWorkspaceBySearchCondition(AnnounceItemSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countChildrenWorkspace", searchCondition);
	}
	
	/*
	 * 하위부서중 개설 Workspace 리스트 ##
	 * @see com.lgcns.ikep4.collpack.collaboration.board.announce.dao.AnnounceItemDao#listChildWorkspaceBySearchCondition(com.lgcns.ikep4.collpack.collaboration.board.announce.search.AnnounceItemSearchCondition)
	 */
	public List<AnnounceItem> listChildWorkspaceBySearchCondition(AnnounceItemSearchCondition searchCondition) {
		return (List<AnnounceItem>)this.sqlSelectForList(NAMESPACE + "listChildWorkspaceBySearchCondition", searchCondition);
	}
	
	
	public AnnounceItem get(String id) {
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
	 * @see com.lgcns.ikep4.collpack.collaboration.board.announce.dao.AnnounceItemDao#createAnnounceItemReference(java.lang.String)
	 */
	public void createAnnounceItemReference(String userId,String itemId) {
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("userId", userId);
		param.put("itemId", itemId);
		this.sqlInsert(NAMESPACE + "createAnnounceReference", param);
	}
	/*
	 * 참조테이블 정보 카운트
	 * @see com.lgcns.ikep4.collpack.collaboration.board.announce.dao.AnnounceItemDao#getAnnounceReference(java.lang.String, java.lang.String)
	 */
	public int countAnnounceReference(String userId, String itemId) {
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("userId", userId);
		param.put("itemId", itemId);
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countAnnounceReference", param);

	}
	
	/*
	 * (non-Javadoc) Workspace 메인 포틀릿 정보
	 * @see com.lgcns.ikep4.collpack.collaboration.board.announce.dao.AnnounceItemDao#listAnnounceItemByPortlet(java.util.Map)
	 */
	public List<AnnounceItem> listAnnounceItemByPortlet(Map<String,String> map) {
		return (List<AnnounceItem>)this.sqlSelectForList(NAMESPACE + "listAnnounceItemByPortlet", map);
	}
	/**
	 * 공지사항중 게시물에 첨부가 있는 게시물 목록 조회 - Workspace 삭제 배치시 첨부 파일 삭제처리
	 */
	public List<AnnounceItem> listDeleteAnnounceByWorkspace(String workspaceId) { 
		return (List<AnnounceItem>)this.sqlSelectForList(NAMESPACE + "listDeleteAnnounceByWorkspace", workspaceId);

	}
}
