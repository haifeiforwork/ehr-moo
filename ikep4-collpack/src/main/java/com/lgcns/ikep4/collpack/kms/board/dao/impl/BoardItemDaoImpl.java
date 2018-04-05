/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.board.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao;
import com.lgcns.ikep4.collpack.kms.board.model.BoardItem;
import com.lgcns.ikep4.collpack.kms.board.search.BoardItemReaderSearchCondition;
import com.lgcns.ikep4.collpack.kms.board.search.BoardItemSearchCondition;
import com.lgcns.ikep4.collpack.kms.board.search.RelatedBoardItemSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * BoardItemDao 구현체 클래스
 */
@Repository("kmsBoardItemDaoImpl")
public class BoardItemDaoImpl extends GenericDaoSqlmap<BoardItem, String> implements BoardItemDao {

	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "collpack.kms.board.dao.boardItem.";

	/**
	 * 게시물 조회
	 */
	public BoardItem get(String id) {
		return (BoardItem) this.sqlSelectForObject(NAMESPACE + "get", id);
	}

	/**
	 * 게시물 존재유무 확인
	 */
//	public boolean exists(String id) {
//		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "exists", id);
//
//		return count > 0;
//	}

	/**
	 * 게시물 등록
	 */
	public String create(BoardItem object) {
		this.sqlInsert(NAMESPACE + "create", object);

		return object.getItemId();
	}

	/**
	 * 게시물 정보 수정
	 */
	public void update(BoardItem object) {
		this.sqlInsert(NAMESPACE + "update", object);
	}
	
	public void updateTempItem(BoardItem object) {
		this.sqlInsert(NAMESPACE + "updateTempItem", object);
	}

	/**
	 * 게시물 임시 삭제
	 */
	public void logicalDelete(BoardItem boardItem) {
		this.sqlUpdate(NAMESPACE + "logicalDelete", boardItem);
	}

	/**
	 * 게시물 영구 삭제
	 */
	public void physicalDelete(String id) {
		this.sqlDelete(NAMESPACE + "physicalDelete", id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	@Deprecated
	public void remove(String id) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 게시물 목록
	 */
	public List<BoardItem> listBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchCondition", boardItemSearchCondition);
	}

	/**
	 * 게시물 갯수
	 */
	public Integer countBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", boardItemSearchCondition);
	}

	/**
	 * 게시물 하위 삭제처리
	 */
	public void physicalDeleteThread(String id) {
		this.sqlDelete(NAMESPACE + "physicalDeleteThread", id);

	}

	/**
	 * 게시물 하위 갯수
	 */
	public Integer countChildren(String id) {
		return (Integer) this.sqlSelectForObject(NAMESPACE + "countChildren", id);
	}

	/**
	 * 게시물 추천 수 수정
	 */
	public void updateRecommendCount(String id) {
		this.sqlUpdate(NAMESPACE + "updateRecommendCount", id);
	}

	/**
	 * 게시물 조회수 수정
	 */
	public void updateHitCount(String id) {
		this.sqlUpdate(NAMESPACE + "updateHitCount", id);
	}
	
	public void updateMark(String id) {
		this.sqlUpdate(NAMESPACE + "updateMark", id);
	}

	/**
	 * 게시물 답변 정보 Step 수정
	 */
	public void updateStep(BoardItem boardItem) {
		this.sqlUpdate(NAMESPACE + "updateStep", boardItem);
	}

	/**
	 * 게시물 댓글 수 수정
	 */
	public void updateLinereplyCount(String id) {
		this.sqlUpdate(NAMESPACE + "updateLinereplyCount", id);
	}

	/**
	 * 게시물 답변수 수정
	 */
	public void updateReplyCount(String id) {
		this.sqlUpdate(NAMESPACE + "updateReplyCount", id);
	}

	/**
	 * 게시물 답변 목록
	 */
//	public List<BoardItem> listReplayItemThreadByItemId(String id) {
//		return this.sqlSelectForList(NAMESPACE + "listReplayItemThreadByItemId", id);
//	}

	/**
	 * 게시물 목록
	 */
	public List<BoardItem> listNoThreadBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listNoThreadBySearchCondition", boardItemSearchCondition);
	}
	
	public List<BoardItem> getRecommendDetailList(BoardItemSearchCondition searchCondition){
		return this.sqlSelectForList(NAMESPACE + "getRecommendDetailList", searchCondition);
	}
	
	public List<BoardItem> getRecommendReceiveDetailList(BoardItemSearchCondition searchCondition){
		return this.sqlSelectForList(NAMESPACE + "getRecommendReceiveDetailList", searchCondition);
	}
	
	public List<BoardItem> getReplyDetailList(BoardItemSearchCondition searchCondition){
		return this.sqlSelectForList(NAMESPACE + "getReplyDetailList", searchCondition);
	}
	
	public List<BoardItem> getReplyReceiveDetailList(BoardItemSearchCondition searchCondition){
		return this.sqlSelectForList(NAMESPACE + "getReplyReceiveDetailList", searchCondition);
	}

	/**
	 * 게시물 갯수
	 */
	public Integer countNoThreadBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return (Integer) this
				.sqlSelectForObject(NAMESPACE + "countNoThreadBySearchCondition", boardItemSearchCondition);
	}

	/**
	 * 게시물 하위 목록
	 */
	public List<BoardItem> listLowerItemThread(String id) {
		return this.sqlSelectForList(NAMESPACE + "listLowerItemThread", id);
	}

	/**
	 * 게시물 ID 목록
	 */
//	public List<BoardItem> listByItemIdArray(List<String> idList) {
//		return this.sqlSelectForList(NAMESPACE + "listByItemIdArray", idList);
//	}

	/**
	 * 
	 */
	public List<BoardItem> listByBoardIdForBoardDelete(String boardId) {
		return this.sqlSelectForList(NAMESPACE + "listByBoardIdForBoardDelete", boardId);
	}

	/**
	 * 게시물 영구 삭제
	 */
	public List<BoardItem> listBatchDeleteBoardItem(Integer getCount) {
		return this.sqlSelectForList(NAMESPACE + "listBatchDeleteBoardItem", getCount);
	}

	/**
	 * 게시물 최근 목록 - 게시판 ID
	 */
//	public List<BoardItem> listRecentBoardItem(Map<String, Object> parameter) {
//		return this.sqlSelectForList(NAMESPACE + "listRecentBoardItem", parameter);
//	}

	/**
	 * 게시물 최근 목록 전체
	 */
//	public List<BoardItem> listRecentAllBoardItem(Map<String, Object> parameter) {
//		return this.sqlSelectForList(NAMESPACE + "listRecentAllBoardItem", parameter);
//
//	}

	/* 5/17 WS에서 추가 부분 * */

	/**
	 * Collaboration 메인 화면에서 나의 Workspace 중의 최근 게시물 조회
	 */
	public List<BoardItem> listMyCollBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return (List<BoardItem>) this.sqlSelectForList(NAMESPACE + "listMyCollBySearchCondition",
				boardItemSearchCondition);
	}

	/**
	 * 개별 WOrkspace 게시판 최근 목록
	 */
	public List<BoardItem> listBoardItemByPortlet(Map<String, String> map) {
		return (List<BoardItem>) this.sqlSelectForList(NAMESPACE + "listBoardItemByPortlet", map);
	}

	/**
	 * 해당 게시판의 게시물중 첨부파일이 있는 게시물 조회 (삭제 배치중 )
	 */
	public List<BoardItem> listDeleteBoardItem(String boardId) {
		return (List<BoardItem>) this.sqlSelectForList(NAMESPACE + "listDeleteBoardItem", boardId);

	}

	/**
	 * 게시물 이동
	 */
	public void updateBoardItemMove(Map<String, String> map) {
		this.sqlInsert(NAMESPACE + "updateBoardItemMove", map);
	}

	/*
	 * public boolean existsBoardItemRecommend(BoardItemRecommend object) {
	 * Integer count = (Integer)this.sqlSelectForObject(NAMESPACE +
	 * "existsRecommend", object); return count > 0; } public boolean
	 * existsBoardItemReference(BoardItemReference object) { Integer count =
	 * (Integer)this.sqlSelectForObject(NAMESPACE + "existsReference", object);
	 * return count > 0; } public String
	 * createBoardItemRecommend(BoardItemRecommend object) {
	 * this.sqlInsert(NAMESPACE + "createRecommend", object); return
	 * object.getItemId(); } public String
	 * createBoardItemReference(BoardItemReference object) {
	 * this.sqlInsert(NAMESPACE + "createReference", object); return
	 * object.getItemId(); }
	 */
	/**
	 * 게시물 수정
	 */
	public void updateItem(BoardItem object) {
		this.sqlInsert(NAMESPACE + "updateItem", object);
	}

	/**
	 * 게시물 조회 정보 삭제
	 */
	public void physicalDeleteReference(String id) {
		this.sqlDelete(NAMESPACE + "physicalDeleteReference", id);
	}

	/**
	 * 게시물 추천정보 삭제
	 */
	public void physicalDeleteRecommend(String id) {
		this.sqlDelete(NAMESPACE + "physicalDeleteRecommend", id);
	}

	/**
	 * Workspace 내의 게시물 갯수 조회
	 */
	public Integer countMyCollBySearchCondition(String userId) {
		return (Integer) this.sqlSelectForObject(NAMESPACE + "countMyCollBySearchCondition", userId);
	}

	public void updateMailCount(String itemId) {
		sqlUpdate(NAMESPACE + "updateMailCount", itemId);
	}

	public void updateMblogCount(String itemId) {
		sqlUpdate(NAMESPACE + "updateMblogCount", itemId);
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/*
	 * 최신정보 리스트 카운트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#latestCountBySearchCondition(com.lgcns.ikep4.collpack.kms.board.search.BoardItemSearchCondition)
	 */
	public Integer latestCountBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "latestCountBySearchCondition", boardItemSearchCondition);
	}
	
	public Integer listReaderCountBySearchCondition(BoardItemReaderSearchCondition boardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "listReaderCountBySearchCondition", boardItemSearchCondition);
	}
	
	public Integer hotissueCountBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "hotissueCountBySearchCondition", boardItemSearchCondition);
	}
	
	/*
	 * 최신정보 게시물 리스트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#latestListBySearchCondition(com.lgcns.ikep4.collpack.kms.board.search.BoardItemSearchCondition)
	 */
	public List<BoardItem> latestListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) { 
		return (List<BoardItem>)this.sqlSelectForList(NAMESPACE + "latestListBySearchCondition", boardItemSearchCondition);
	}
	
	public List listReaderBySearchCondition(BoardItemReaderSearchCondition boardItemSearchCondition) { 
		return this.sqlSelectForList(NAMESPACE + "listReaderBySearchCondition", boardItemSearchCondition);
	}
	
	public List<BoardItem> hotissueListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) { 
		return (List<BoardItem>)this.sqlSelectForList(NAMESPACE + "hotissueListBySearchCondition", boardItemSearchCondition);
	}

	/*
	 * 우수정보 리스트 카운트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#latestCountBySearchCondition(com.lgcns.ikep4.collpack.kms.board.search.BoardItemSearchCondition)
	 */
	public Integer excellenceCountBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "excellenceCountBySearchCondition", boardItemSearchCondition);
	}
	
	/*
	 * 우수정보  게시물 리스트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#latestListBySearchCondition(com.lgcns.ikep4.collpack.kms.board.search.BoardItemSearchCondition)
	 */
	public List<BoardItem> excellenceListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) { 
		return (List<BoardItem>)this.sqlSelectForList(NAMESPACE + "excellenceListBySearchCondition", boardItemSearchCondition);
	}
	
	/*
	 * 게시글 읽기정보 카운트
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#countBoardItemReference(java.lang.String, java.lang.String)
	 */
	public int countBoardReference(String userId, String itemId) {
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("userId", userId);
		param.put("itemId", itemId);
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBoardReference", param);

	}
	
	/*
	 *  게시물 조회수 UPDATE
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#updateBoardHitCount(java.lang.String)
	 */
	public void updateBoardHitCount(String itemId) {
		this.sqlUpdate(NAMESPACE + "updateBoardHitCount", itemId);  
	}
	
	
	/*
	 * 조회이력정보 insert
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#createBoardItemReference(java.lang.String)
	 */
	public void createBoardItemReference(String userId,String itemId) {
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("userId", userId);
		param.put("itemId", itemId);
		this.sqlInsert(NAMESPACE + "createBoardItemReference", param);
	}
	
	/*
	 * 관련글 리스트 카운트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#latestCountBySearchCondition(com.lgcns.ikep4.collpack.kms.board.search.BoardItemSearchCondition)
	 */
	public Integer relatedCountBySearchCondition(RelatedBoardItemSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "relatedCountBySearchCondition", searchCondition);
	}
	
	/*
	 * 관련글 게시물 리스트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#latestListBySearchCondition(com.lgcns.ikep4.collpack.kms.board.search.BoardItemSearchCondition)
	 */
	public List<BoardItem> listRelatedListBySearchCondition(RelatedBoardItemSearchCondition searchCondition) { 
		return (List<BoardItem>)this.sqlSelectForList(NAMESPACE + "listRelatedListBySearchCondition", searchCondition);
	}
	
	/*
	 * 게시글 읽기누적정보 카운트
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#countBoardRefCumulative(Map<String, String> map)
	 */
	public int countBoardRefCumulative(Map<String, String> map) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBoardRefCumulative", map);

	}
	
	/*
	 * 게시글 읽기누적 insert
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#createBoardItemRefCumulative(Map<String, String> map)
	 */
	public void createBoardItemRefCumulative(Map<String, String> map) {
		this.sqlInsert(NAMESPACE + "createBoardItemRefCumulative", map);
	}
	
	/*
	 * 게시글 읽기누적 update
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#updateBoardItemRefCumulative(Map<String, String> map)
	 */
	public void updateBoardItemRefCumulative(Map<String, String> map) {
		this.sqlUpdate(NAMESPACE + "updateBoardItemRefCumulative", map);
	}
	
	/*
	 * 활용정보 중복검사
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#countCreateRefInfo(Map<String, String> map)
	 */
	public int countCreateRefInfo(Map<String, String> map) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countCreateRefInfo", map);

	}
	
	/*
	 * 활용정보 insert
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#createRefInfo(Map<String, String> map)
	 */
	public void createRefInfo(Map<String, String> map) {
		this.sqlInsert(NAMESPACE + "createRefInfo", map);
	}
	
	/*
	 * 활용정보 delete
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#deleteRefInfo(String id)
	 */
	public void deleteRefInfo(String id) {
		this.sqlDelete(NAMESPACE + "deleteRefInfo", id);
	}
	
	/*
	 * 활용정보 list
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#createRefInfo(Map<String, String> map)
	 */
	public List<BoardItem> listRelatedListBySearchCondition(Map<String, String> map) {
		// TODO Auto-generated method stub
		return (List<BoardItem>)this.sqlSelectForList(NAMESPACE + "listRefInfoMap", map);
	}
	
	/*
	 * Ref 리스트 카운트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#refCountBySearchCondition(com.lgcns.ikep4.collpack.kms.board.search.BoardItemSearchCondition)
	 */
	public Integer refCountBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "refCountBySearchCondition", boardItemSearchCondition);
	}
	
	/*
	 * Ref 게시물 리스트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#refListBySearchCondition(com.lgcns.ikep4.collpack.kms.board.search.BoardItemSearchCondition)
	 */
	public List<BoardItem> refListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) { 
		return (List<BoardItem>)this.sqlSelectForList(NAMESPACE + "refListBySearchCondition", boardItemSearchCondition);
	}
	
	/*
	 * 임시저장 리스트 카운트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#temporaryCountBySearchCondition(com.lgcns.ikep4.collpack.kms.board.search.BoardItemSearchCondition)
	 */
	public Integer temporaryCountBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "temporaryCountBySearchCondition", boardItemSearchCondition);
	}
	
	public Integer targetCountBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "targetCountBySearchCondition", boardItemSearchCondition);
	}
	
	public Integer keyInfoCountBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "keyInfoCountBySearchCondition", boardItemSearchCondition);
	}
	
	/*
	 * 임시저장  게시물 리스트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#temporaryListBySearchCondition(com.lgcns.ikep4.collpack.kms.board.search.BoardItemSearchCondition)
	 */
	public List<BoardItem> temporaryListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) { 
		return (List<BoardItem>)this.sqlSelectForList(NAMESPACE + "temporaryListBySearchCondition", boardItemSearchCondition);
	}
	
	public List<BoardItem> targetListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) { 
		return (List<BoardItem>)this.sqlSelectForList(NAMESPACE + "targetListBySearchCondition", boardItemSearchCondition);
	}
	
	public List<BoardItem> keyInfoListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) { 
		return (List<BoardItem>)this.sqlSelectForList(NAMESPACE + "keyInfoListBySearchCondition", boardItemSearchCondition);
	}
	
	/*
	 * 전문가 리스트 카운트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#assessCountBySearchCondition(com.lgcns.ikep4.collpack.kms.board.search.BoardItemSearchCondition)
	 */
	public Integer assessCountBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "assessCountBySearchCondition", boardItemSearchCondition);
	}
	
	/*
	 * 전문가  게시물 리스트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#assessListBySearchCondition(com.lgcns.ikep4.collpack.kms.board.search.BoardItemSearchCondition)
	 */
	public List<BoardItem> assessListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) { 
		return (List<BoardItem>)this.sqlSelectForList(NAMESPACE + "assessListBySearchCondition", boardItemSearchCondition);
	}
	
	/*
	 * 전문가 설정 
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#updateBoardItemRefCumulative(Map<String, String> map)
	 */
	public void assessMove(Map<String, String> map) {
		this.sqlUpdate(NAMESPACE + "assessMove", map);
	}

	/*
	 * 지식조회 리스트 카운트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#latestCountBySearchCondition(com.lgcns.ikep4.collpack.kms.board.search.BoardItemSearchCondition)
	 */
	public Integer searchCountBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "searchCountBySearchCondition", boardItemSearchCondition);
	}
	
	/*
	 * 지식조회  게시물 리스트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#latestListBySearchCondition(com.lgcns.ikep4.collpack.kms.board.search.BoardItemSearchCondition)
	 */
	public List<BoardItem> searchListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) { 
		return (List<BoardItem>)this.sqlSelectForList(NAMESPACE + "searchListBySearchCondition", boardItemSearchCondition);
	}
	
	/**
	 * 게시물 조회 PRINT
	 */
	public BoardItem getBoardItemPrint(String id) {
		return (BoardItem) this.sqlSelectForObject(NAMESPACE + "getBoardItemPrint", id);
	}
	
	public BoardItem getCaution() {
		return (BoardItem) this.sqlSelectForObject(NAMESPACE + "getCaution");
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/*
	 * E등급지식 리스트 카운트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#temporaryCountBySearchCondition(com.lgcns.ikep4.collpack.kms.board.search.BoardItemSearchCondition)
	 */
	public Integer einfogradeCountBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "einfogradeCountBySearchCondition", boardItemSearchCondition);
	}
	
	/*
	 * E등급지식  게시물 리스트 ##
	 * @see com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao#temporaryListBySearchCondition(com.lgcns.ikep4.collpack.kms.board.search.BoardItemSearchCondition)
	 */
	public List<BoardItem> einfogradeListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition) { 
		return (List<BoardItem>)this.sqlSelectForList(NAMESPACE + "einfogradeListBySearchCondition", boardItemSearchCondition);
	}
	
	public void updateBoardItemReference(String userId, String itemId) {
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("registerId", userId);
		param.put("itemId", itemId);
		
		this.sqlUpdate(NAMESPACE + "updateBoardItemReference", param);
		
	}
	
	public List<BoardItem> expertList() {
		return (List<BoardItem>) this.sqlSelectForList(NAMESPACE + "expertList", "");
	}
	
	public void insertTargetGroup(BoardItem boardItem){
		this.sqlInsert(NAMESPACE + "insertTargetGroup", boardItem);
	}
	
	public List<BoardItem> selectTargetGroup(String itemId) {
		return sqlSelectForList(NAMESPACE + "selectTargetGroup", itemId);
	}
	
	public List<BoardItem> selectTargetUser(String itemId) {
		return sqlSelectForList(NAMESPACE + "selectTargetUser", itemId);
	}
	
	public void deleteTargetGroup(String id) {
		this.sqlDelete(NAMESPACE + "deleteTargetGroup", id);
	}
	
	public List<BoardItem> selectTargetUserList(String id){
		return (List<BoardItem>) this.sqlSelectForList(NAMESPACE + "selectTargetUserList", id);
	}
	
	public List<BoardItem> selectKeyInfoUserList(){
		return (List<BoardItem>) this.sqlSelectForList(NAMESPACE + "selectKeyInfoUserList");
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getUserInfoList(String[] userIds) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "getUserInfoList", userIds);
	}
}
