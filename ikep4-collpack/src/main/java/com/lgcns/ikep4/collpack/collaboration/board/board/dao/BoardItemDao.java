/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.board.board.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.collpack.collaboration.board.board.model.BoardItem;
import com.lgcns.ikep4.collpack.collaboration.board.board.model.Counsel;
import com.lgcns.ikep4.collpack.collaboration.board.board.search.BoardItemSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * 게시글 DAO
 * 
 * 게시글 IKEP4_BD_ITEM 테이블 CRUD와  목록조회등 DB와의 연동을 담당하는 클래스이다.
 *
 * @author ${user}
 * @version $$Id: BoardItemDao.java 16236 2011-08-18 02:48:22Z giljae $$
 */
public interface BoardItemDao extends GenericDao<BoardItem, String> {

	/**
	 * 게시글의 아이디 배열을 키로 해서 게시글을 조회한다.
	 *
	 * @param boardItemSearchCondition 게시글 검색조건
	 * @return 아이디 배열에 해당되는 게시글 목록
	 */
	List<BoardItem> listByItemIdArray(List<String> idList);

	/**
	 * 게시글 본인 포함 하위 쓰레드를 조회한다.
	 *
	 * @param id 게시글 아이디
	 * @return 본인 포함 하위 쓰레드 게시글
	 */
	List<BoardItem> listLowerItemThread(String id);

	/**
	 * 게시글 검색조건을 이용해서 게시글 목록을 조회한다.
	 *
	 * @param boardItemSearchCondition 게시글 검색조건
	 * @return 게시글 목록
	 */
	List<BoardItem> listBySearchConditionMain(BoardItemSearchCondition boardItemSearchCondition);

	/**
	 * 게시글 검색조건을 이용해서 게시글 목록을 조회한다.
	 *
	 * @param boardItemSearchCondition 게시글 검색조건
	 * @return 게시글 목록
	 */
	List<BoardItem> listBySearchCondition(BoardItemSearchCondition boardItemSearchCondition);

	/**
	 * 게시글 검색조건을 이용해서 게시글 목록(게시글 쓰레드가 무시된)을 조회한다.
	 *
	 * @param boardItemSearchCondition 게시글 검색조건
	 * @return 게시글 목록(게시글 쓰레드가 무시된)
	 */
	List<BoardItem> listNoThreadBySearchCondition(BoardItemSearchCondition boardItemSearchCondition);

	/**
	 * 게시글의 상위, 하위 전체 쓰레드를 조회한다.
	 *
	 * @param id 게시글 ID
	 * @return 쓰레드 게시글 목록
	 */
	List<BoardItem> listReplayItemThreadByItemId(String id);

	/**
	 * 게시글 검색조건을 충족하는 게시글의 총 갯수를 구한다.
	 *
	 * @param boardItemSearchCondition 게시글 검색조건
	 * @return 게시글 갯수
	 */
	Integer countBySearchCondition(BoardItemSearchCondition boardItemSearchCondition);

	/**
	 * 게시글 검색조건을 충족하는 게시글(게시글 쓰레드가 무시된)의 총 갯수를 구한다.
	 *
	 * @param boardItemSearchCondition 게시글 검색조건
	 * @return 게시글 갯수
	 */
	Integer countNoThreadBySearchCondition(BoardItemSearchCondition boardItemSearchCondition);

	/**
	 * 게시글의 하위 답변 게시글의 갯수를 조회한다.
	 *
	 * @param id 조회 대상 게시글 ID
	 * @return 자식게시글(답변) 갯수
	 */
	Integer countChildren(String id);

	/**
	 * 게시글을 논리적으로 삭제한다.
	 *
	 * @param boardItem 삭제 대상 게시글
	 */
	void logicalDelete(BoardItem boardItem);

	/**
	 * 게시글을 삭제한다.
	 *
	 * @param id 삭제  대상 게시글 ID
	 */
	void physicalDelete(String id);

	/**
	 * 게시글 쓰레드를 삭제한다.
	 *
	 * @param id 삭제 대상 게시글 ID
	 */
	void physicalDeleteThread(String id);

	/**
	 * 게시글의 추천 수를 증가시킨다.
	 * 
	 * @param id 업데이트 대상 게시글 ID
	 */
	void updateRecommendCount(String id);

	/**
	 * 게시글의 조회 수를 증가시킨다.
	 *
	 * @param id 업데이트 대상 게시글 ID
	 */
	void updateHitCount(String id);

	/**
	 * 게시글의 댓글 수를 증가 시킨다.
	 *
	 * @param id 업데이트 대상 게시글 ID
	 */
	void updateLinereplyCount(String id);

	/**
	 * 게시글 답글 수를 조회한다
	 *
	 * @param id 조회 대상 게시글 ID
	 */
	void updateReplyCount(String id);
	
	void consultationRequest(Counsel counsel);
	
	public Integer selectConsultationRequestListCount(Map<String, String> map);
	
	public List<Map<String, Object>> selectConsultationRequestList(Map<String, String> map);

	/**
	 * 스탭을 업데이트 한다.
	 *
	 * @param boardItem 업데이트 대상 게시글
	 */
	void updateStep(BoardItem boardItem);

	/**
	 * 게시판의 모든 게시글을 조회한다.
	 * 
	 * @param boardId
	 */
	List<BoardItem> listByBoardIdForBoardDelete(String boardId);

	/**
	 * 삭제 목록을 파라미터 갯수만큼 가져온다.
	 * 
	 * @param getCount 가져와야 하는 삭제 목록
	 * 
	 * @return 삭제대상 게시글  목록
	 */
	List<BoardItem> listBatchDeleteBoardItem(Integer getCount);

	/**
	 * 특정 게시판에 최신에 등록된 게시글을 가져온다.
	 * 
	 * @param parameter  boardId 게시판 ID, count 글갯수
	 * @return
	 */
	List<BoardItem> listRecentBoardItem(Map<String, Object> parameter);


	/**
	 * 최신에 등록된 게시물을 가져온다.
	 * 
	 * @param parameterMap
	 */
	List<BoardItem> listRecentAllBoardItem(Map<String, Object> parameter);
	
	
	
	
	
	
	/* 5/17 WS에서 추가 부분 **/
	
	/**
	 * Collaboration 메인 화면에서 나의 Workspace 중의 최근 게시물 조회
	 */
	public List<BoardItem> listMyCollBySearchCondition(BoardItemSearchCondition boardItemSearchCondition);

	/**
	 * 개별 WOrkspace 게시판 최근 목록
	 * @param map
	 * @return
	 */
	public List<BoardItem> listBoardItemByPortlet(Map<String,String> map);
	
	/**
	 * 게시물 이동
	 * @param map
	 */
	void updateBoardItemMove(Map<String, String> map); 
	
	/**
	 *  해당 게시판의 게시물중  첨부파일이 있는 게시물 조회 (삭제 배치중 )
	 * @param boardId
	 * @return
	 */
	public List<BoardItem> listDeleteBoardItem(String boardId);
	
	
	
	
	
	/**
	 * 나의 Workspace 내의 최근 게시물 목록 갯수
	 * @param userId
	 * @return
	 */
	public Integer countMyCollBySearchCondition(String userId);
		
	//public String createBoardItemRecommend(BoardItemRecommend object);
	
	//public String createBoardItemReference(BoardItemReference object);
	
	//public boolean existsBoardItemRecommend(BoardItemRecommend object);
	
	//public boolean existsBoardItemReference(BoardItemReference object);
	

    /**
     * 게시물 추천정보 삭제
     */
    public void physicalDeleteRecommend(String id);
    /**
     * 게시물 조회 정보 삭제
     * @param id
     */
    public void physicalDeleteReference(String id);
    
    /**
     * 게시물 정보 삭제
     * @param boardItem
     */
	void updateItem(BoardItem boardItem); 
	
	/**
	 * 메일수 수정
	 * @param itemId
	 */
	public void updateMailCount(String itemId);
	
	/**
	 * 블로그수 수정
	 * @param itemId
	 */
	public void updateMblogCount(String itemId);

	
}
