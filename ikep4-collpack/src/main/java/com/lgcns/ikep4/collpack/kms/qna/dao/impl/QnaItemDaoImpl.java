package com.lgcns.ikep4.collpack.kms.qna.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.kms.qna.dao.QnaItemDao;
import com.lgcns.ikep4.collpack.kms.qna.model.QnaConfig;
import com.lgcns.ikep4.collpack.kms.qna.model.QnaItem;
import com.lgcns.ikep4.collpack.kms.qna.model.QnaRecommendPK;
import com.lgcns.ikep4.collpack.kms.qna.search.QnaItemSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

@Repository("kmsQnaItemDaoImpl")
public class QnaItemDaoImpl extends GenericDaoSqlmap<QnaItem, String> implements QnaItemDao {
	private static final String NAMESPACE = "collpack.kms.qna.dao.qnaItem."; 

	/*
	 * 공지사항 게시물 생성 ##
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(QnaItem object) {  
		this.sqlInsert(NAMESPACE + "create", object);
		return object.getItemId();
	}
	/*
	 * 공지사항 게시물 수정##
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(QnaItem object) { 
		this.sqlInsert(NAMESPACE + "update", object); 
	}
	
	/*
	 * 공지사항 게시물 리스트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.qna.dao.QnaItemDao#listBySearchCondition(com.lgcns.ikep4.collpack.kms.board.qna.search.QnaItemSearchCondition)
	 */
	public List<QnaItem> listBySearchCondition(QnaItemSearchCondition qnaItemSearchCondition) { 
		return (List<QnaItem>)this.sqlSelectForList(NAMESPACE + "listBySearchCondition", qnaItemSearchCondition);
	}
	
	public List<QnaItem> listQnaSubItem(QnaItemSearchCondition qnaItemSearchCondition) { 
		return (List<QnaItem>)this.sqlSelectForList(NAMESPACE + "listQnaSubItem", qnaItemSearchCondition);
	}
	
	/*
	 * 공지사항 게시물 리스트 카운트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.qna.dao.QnaItemDao#countBySearchCondition(com.lgcns.ikep4.collpack.kms.board.qna.search.QnaItemSearchCondition)
	 */
	public Integer countBySearchCondition(QnaItemSearchCondition qnaItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", qnaItemSearchCondition);
	}
	
	public Integer countQnaSubItem(QnaItemSearchCondition qnaItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countQnaSubItem", qnaItemSearchCondition);
	}
	
	public boolean existsRecommend(QnaRecommendPK object) {
		Integer count = (Integer)this.sqlSelectForObject(NAMESPACE + "existsRecommend", object);
		return count > 0;
	}
	
	public QnaRecommendPK createRecommend(QnaRecommendPK object) {
		this.sqlInsert(NAMESPACE + "createRecommend", object);

		return object;
	}
	
	public void updateRecommendCount(String id) {
		this.sqlUpdate(NAMESPACE + "updateRecommendCount", id);
	}
	
	public void updateStep(QnaItem boardItem) {
		this.sqlUpdate(NAMESPACE + "updateStep", boardItem);
	}
	
	public void updateReplyCount(String id) {
		this.sqlUpdate(NAMESPACE + "updateReplyCount", id);
	}
	
	public void insertTargetGroup(QnaItem boardItem){
		this.sqlInsert(NAMESPACE + "insertTargetGroup", boardItem);
	}
	
	public List<QnaItem> selectTargetUserList(String id){
		return (List<QnaItem>) this.sqlSelectForList(NAMESPACE + "selectTargetUserList", id);
	}

	/*
	 * 공지사항 게시물 조회수 UPDATE X
	 * @see com.lgcns.ikep4.collpack.kms.board.qna.dao.QnaItemDao#updateHitCount(java.lang.String)
	 */
	public void updateQnaHitCount(String itemId) {
		this.sqlUpdate(NAMESPACE + "updateQnaHitCount", itemId);  
	}

//	/*
//	 * 공지사항 게시물 공유링크 INSERT ##
//	 * @see com.lgcns.ikep4.collpack.kms.board.qna.dao.QnaItemDao#createLinkQna(com.lgcns.ikep4.collpack.kms.board.qna.model.QnaItem)
//	 */
//	public void createLinkQna(QnaItem qnaItem) {
//		sqlInsert(NAMESPACE + "createLinkQna", qnaItem);
//	}
	
	/*
	 * 공지사항 게시물 읽기 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.qna.dao.QnaItemDao#getQna(java.lang.String, java.lang.String)
	 */
//	public QnaItem getQna(String itemId, String workspaceId) {
	public QnaItem getQna(String itemId) {
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("itemId", itemId);
//		param.put("workspaceId", workspaceId);
		return (QnaItem)this.sqlSelectForObject(NAMESPACE + "getQna", param);
	}
	
	/*
	 * 공지사항 공유 게시물 전체 링크삭제(원본게시물링크 제외) ##
	 * @see com.lgcns.ikep4.collpack.kms.board.qna.dao.QnaItemDao#removeQnaShareLink(java.lang.String)
	 */
	public void removeQnaShareLink(String qnaItemId) {
		this.sqlDelete(NAMESPACE + "removeQnaShareLink", qnaItemId);
	}
	
	/*
	 * 공지사항 링크삭제 #해당아이템 링크  DELETE#
	 * @see com.lgcns.ikep4.collpack.kms.board.qna.dao.QnaItemDao#physicalDeleteQnaLink(java.lang.String)
	 */
//	public void removeQnaLink(String itemId,String workspaceId) {
	public void removeQnaLink(String itemId) {
		Map<String,String> param = new HashMap<String,String>();
		param.put("itemId", itemId);
//		param.put("workspaceId", workspaceId);
		this.sqlDelete(NAMESPACE + "removeQnaLink", param); 
	}
	
	public QnaConfig readQnaconfig(){
		return (QnaConfig)this.sqlSelectForObject(NAMESPACE + "readQnaconfig");
	}
	
	public void createQnaConfig(QnaConfig qnaConfig) {
		sqlInsert(NAMESPACE + "createQnaConfig", qnaConfig);
	}
	
	public void updateQnaConfig(QnaConfig qnaConfig) {
		sqlUpdate(NAMESPACE + "updateQnaConfig", qnaConfig);
	}
	
	/*
	 * 공지사항 게시물 삭제 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.qna.dao.QnaItemDao#physicalDeleteQnaItem(java.lang.String)
	 */
	public void removeQnaItem(String itemId) {
		this.sqlDelete(NAMESPACE + "removeQnaItem", itemId);
	}

	/*
	 * 해당 게시글 조회참조 정보 일괄삭제
	 * @see com.lgcns.ikep4.collpack.kms.board.qna.dao.QnaItemDao#removeQnaReference(java.lang.String)
	 */
	public void removeQnaReference(String itemId) {
		this.sqlDelete(NAMESPACE + "removeQnaReference", itemId);
	}
	/*
	 * 하위부서중 개설 Workspace 리스트 카운트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.qna.dao.QnaItemDao#countChildWorkspaceBySearchCondition(com.lgcns.ikep4.collpack.kms.board.qna.search.QnaItemSearchCondition)
	 */
	public Integer countChildWorkspaceBySearchCondition(QnaItemSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countChildrenWorkspace", searchCondition);
	}
	
	/*
	 * 하위부서중 개설 Workspace 리스트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.qna.dao.QnaItemDao#listChildWorkspaceBySearchCondition(com.lgcns.ikep4.collpack.kms.board.qna.search.QnaItemSearchCondition)
	 */
	public List<QnaItem> listChildWorkspaceBySearchCondition(QnaItemSearchCondition searchCondition) {
		return (List<QnaItem>)this.sqlSelectForList(NAMESPACE + "listChildWorkspaceBySearchCondition", searchCondition);
	}
	
	
	public QnaItem get(String id) {
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
	 * @see com.lgcns.ikep4.collpack.kms.board.qna.dao.QnaItemDao#createQnaItemReference(java.lang.String)
	 */
	public void createQnaItemReference(String userId,String itemId) {
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("userId", userId);
		param.put("itemId", itemId);
		this.sqlInsert(NAMESPACE + "createQnaReference", param);
	}
	/*
	 * 참조테이블 정보 카운트
	 * @see com.lgcns.ikep4.collpack.kms.board.qna.dao.QnaItemDao#getQnaReference(java.lang.String, java.lang.String)
	 */
	public int countQnaReference(String userId, String itemId) {
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("userId", userId);
		param.put("itemId", itemId);
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countQnaReference", param);

	}
	
	/*
	 * (non-Javadoc) Workspace 메인 포틀릿 정보
	 * @see com.lgcns.ikep4.collpack.kms.board.qna.dao.QnaItemDao#listQnaItemByPortlet(java.util.Map)
	 */
	public List<QnaItem> listQnaItemByPortlet(Map<String,String> map) {
		return (List<QnaItem>)this.sqlSelectForList(NAMESPACE + "listQnaItemByPortlet", map);
	}
	/**
	 * 공지사항중 게시물에 첨부가 있는 게시물 목록 조회 - Workspace 삭제 배치시 첨부 파일 삭제처리
	 */
//	public List<QnaItem> listDeleteQnaByWorkspace(String workspaceId) { 
	public List<QnaItem> listDeleteQnaByWorkspace() {		
//		return (List<QnaItem>)this.sqlSelectForList(NAMESPACE + "listDeleteQnaByWorkspace", workspaceId);
		return (List<QnaItem>)this.sqlSelectForList(NAMESPACE + "listDeleteQnaByWorkspace");

	}
	public QnaItem getTopQnaItem() {
		return (QnaItem)this.sqlSelectForObject(NAMESPACE + "getTopQnaItem");
	}
}
