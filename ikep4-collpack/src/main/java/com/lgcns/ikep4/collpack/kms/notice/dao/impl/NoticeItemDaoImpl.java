package com.lgcns.ikep4.collpack.kms.notice.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.kms.notice.dao.NoticeItemDao;
import com.lgcns.ikep4.collpack.kms.notice.model.NoticeConfig;
import com.lgcns.ikep4.collpack.kms.notice.model.NoticeItem;
import com.lgcns.ikep4.collpack.kms.notice.search.NoticeItemSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.portlet.model.PortletManagerMessage;

@Repository("kmsNoticeItemDaoImpl")
public class NoticeItemDaoImpl extends GenericDaoSqlmap<NoticeItem, String> implements NoticeItemDao {
	private static final String NAMESPACE = "collpack.kms.notice.dao.noticeItem."; 

	/*
	 * 공지사항 게시물 생성 ##
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(NoticeItem object) {  
		this.sqlInsert(NAMESPACE + "create", object);
		return object.getItemId();
	}
	/*
	 * 공지사항 게시물 수정##
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(NoticeItem object) { 
		this.sqlInsert(NAMESPACE + "update", object); 
	}
	
	/*
	 * 공지사항 게시물 리스트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.notice.dao.NoticeItemDao#listBySearchCondition(com.lgcns.ikep4.collpack.kms.board.notice.search.NoticeItemSearchCondition)
	 */
	public List<NoticeItem> listBySearchCondition(NoticeItemSearchCondition noticeItemSearchCondition) { 
		return (List<NoticeItem>)this.sqlSelectForList(NAMESPACE + "listBySearchCondition", noticeItemSearchCondition);
	}
	
	/*
	 * 공지사항 게시물 리스트 카운트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.notice.dao.NoticeItemDao#countBySearchCondition(com.lgcns.ikep4.collpack.kms.board.notice.search.NoticeItemSearchCondition)
	 */
	public Integer countBySearchCondition(NoticeItemSearchCondition noticeItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", noticeItemSearchCondition);
	}

	/*
	 * 공지사항 게시물 조회수 UPDATE X
	 * @see com.lgcns.ikep4.collpack.kms.board.notice.dao.NoticeItemDao#updateHitCount(java.lang.String)
	 */
	public void updateNoticeHitCount(String itemId) {
		this.sqlUpdate(NAMESPACE + "updateNoticeHitCount", itemId);  
	}

//	/*
//	 * 공지사항 게시물 공유링크 INSERT ##
//	 * @see com.lgcns.ikep4.collpack.kms.board.notice.dao.NoticeItemDao#createLinkNotice(com.lgcns.ikep4.collpack.kms.board.notice.model.NoticeItem)
//	 */
//	public void createLinkNotice(NoticeItem noticeItem) {
//		sqlInsert(NAMESPACE + "createLinkNotice", noticeItem);
//	}
	
	/*
	 * 공지사항 게시물 읽기 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.notice.dao.NoticeItemDao#getNotice(java.lang.String, java.lang.String)
	 */
//	public NoticeItem getNotice(String itemId, String workspaceId) {
	public NoticeItem getNotice(String itemId) {
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("itemId", itemId);
//		param.put("workspaceId", workspaceId);
		return (NoticeItem)this.sqlSelectForObject(NAMESPACE + "getNotice", param);
	}
	
	/*
	 * 공지사항 공유 게시물 전체 링크삭제(원본게시물링크 제외) ##
	 * @see com.lgcns.ikep4.collpack.kms.board.notice.dao.NoticeItemDao#removeNoticeShareLink(java.lang.String)
	 */
	public void removeNoticeShareLink(String noticeItemId) {
		this.sqlDelete(NAMESPACE + "removeNoticeShareLink", noticeItemId);
	}
	
	/*
	 * 공지사항 링크삭제 #해당아이템 링크  DELETE#
	 * @see com.lgcns.ikep4.collpack.kms.board.notice.dao.NoticeItemDao#physicalDeleteNoticeLink(java.lang.String)
	 */
//	public void removeNoticeLink(String itemId,String workspaceId) {
	public void removeNoticeLink(String itemId) {
		Map<String,String> param = new HashMap<String,String>();
		param.put("itemId", itemId);
//		param.put("workspaceId", workspaceId);
		this.sqlDelete(NAMESPACE + "removeNoticeLink", param); 
	}
	
	public NoticeConfig readNoticeconfig(){
		return (NoticeConfig)this.sqlSelectForObject(NAMESPACE + "readNoticeconfig");
	}
	
	public void createNoticeConfig(NoticeConfig noticeConfig) {
		sqlInsert(NAMESPACE + "createNoticeConfig", noticeConfig);
	}
	
	public void updateNoticeConfig(NoticeConfig noticeConfig) {
		sqlUpdate(NAMESPACE + "updateNoticeConfig", noticeConfig);
	}
	
	/*
	 * 공지사항 게시물 삭제 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.notice.dao.NoticeItemDao#physicalDeleteNoticeItem(java.lang.String)
	 */
	public void removeNoticeItem(String itemId) {
		this.sqlDelete(NAMESPACE + "removeNoticeItem", itemId);
	}

	/*
	 * 해당 게시글 조회참조 정보 일괄삭제
	 * @see com.lgcns.ikep4.collpack.kms.board.notice.dao.NoticeItemDao#removeNoticeReference(java.lang.String)
	 */
	public void removeNoticeReference(String itemId) {
		this.sqlDelete(NAMESPACE + "removeNoticeReference", itemId);
	}
	/*
	 * 하위부서중 개설 Workspace 리스트 카운트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.notice.dao.NoticeItemDao#countChildWorkspaceBySearchCondition(com.lgcns.ikep4.collpack.kms.board.notice.search.NoticeItemSearchCondition)
	 */
	public Integer countChildWorkspaceBySearchCondition(NoticeItemSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countChildrenWorkspace", searchCondition);
	}
	
	/*
	 * 하위부서중 개설 Workspace 리스트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.notice.dao.NoticeItemDao#listChildWorkspaceBySearchCondition(com.lgcns.ikep4.collpack.kms.board.notice.search.NoticeItemSearchCondition)
	 */
	public List<NoticeItem> listChildWorkspaceBySearchCondition(NoticeItemSearchCondition searchCondition) {
		return (List<NoticeItem>)this.sqlSelectForList(NAMESPACE + "listChildWorkspaceBySearchCondition", searchCondition);
	}
	
	
	public NoticeItem get(String id) {
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
	 * @see com.lgcns.ikep4.collpack.kms.board.notice.dao.NoticeItemDao#createNoticeItemReference(java.lang.String)
	 */
	public void createNoticeItemReference(String userId,String itemId) {
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("userId", userId);
		param.put("itemId", itemId);
		this.sqlInsert(NAMESPACE + "createNoticeReference", param);
	}
	/*
	 * 참조테이블 정보 카운트
	 * @see com.lgcns.ikep4.collpack.kms.board.notice.dao.NoticeItemDao#getNoticeReference(java.lang.String, java.lang.String)
	 */
	public int countNoticeReference(String userId, String itemId) {
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("userId", userId);
		param.put("itemId", itemId);
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countNoticeReference", param);

	}
	
	/*
	 * (non-Javadoc) Workspace 메인 포틀릿 정보
	 * @see com.lgcns.ikep4.collpack.kms.board.notice.dao.NoticeItemDao#listNoticeItemByPortlet(java.util.Map)
	 */
	public List<NoticeItem> listNoticeItemByPortlet(Map<String,String> map) {
		return (List<NoticeItem>)this.sqlSelectForList(NAMESPACE + "listNoticeItemByPortlet", map);
	}
	/**
	 * 공지사항중 게시물에 첨부가 있는 게시물 목록 조회 - Workspace 삭제 배치시 첨부 파일 삭제처리
	 */
//	public List<NoticeItem> listDeleteNoticeByWorkspace(String workspaceId) { 
	public List<NoticeItem> listDeleteNoticeByWorkspace() {		
//		return (List<NoticeItem>)this.sqlSelectForList(NAMESPACE + "listDeleteNoticeByWorkspace", workspaceId);
		return (List<NoticeItem>)this.sqlSelectForList(NAMESPACE + "listDeleteNoticeByWorkspace");

	}
	public NoticeItem getTopNoticeItem() {
		return (NoticeItem)this.sqlSelectForObject(NAMESPACE + "getTopNoticeItem");
	}
}
