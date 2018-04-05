/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.gallery.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.lightpack.gallery.model.BoardItem;
import com.lgcns.ikep4.lightpack.gallery.search.BoardItemSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * 게시글 DAO
 * 
 * 게시글 IKEP4_BD_ITEM 테이블 CRUD와  목록조회등 DB와의 연동을 담당하는 클래스이다.
 *
 * @author ${user}
 * @version $$Id: BoardItemDao.java 13883 2011-05-30 12:46:05Z designtker $$
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
	List<BoardItem> listBySearchCondition(BoardItemSearchCondition boardItemSearchCondition);

	/**
	 * 게시글 검색조건을 충족하는 게시글의 총 갯수를 구한다.
	 *
	 * @param boardItemSearchCondition 게시글 검색조건
	 * @return 게시글 갯수
	 */
	Integer countBySearchCondition(BoardItemSearchCondition boardItemSearchCondition);
	/**
	 * 게시글 검색조건을 이용해서 게시글 목록을 조회한다.
	 *
	 * @param boardItemSearchCondition 게시글 검색조건
	 * @return 게시글 목록
	 */
	List<BoardItem> listBySearchConditionView(BoardItemSearchCondition boardItemSearchCondition);

	/**
	 * 게시글 검색조건을 충족하는 게시글의 총 갯수를 구한다.
	 *
	 * @param boardItemSearchCondition 게시글 검색조건
	 * @return 게시글 갯수
	 */
	Integer countBySearchConditionView(BoardItemSearchCondition boardItemSearchCondition);

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
	void updateLinereplyCount(String itemId);


	/**
	 * 전시 기간을 벗어난 삭제 목록을 파라미터 갯수만큼 가져온다.
	 * 
	 * @param getCount 가져와야 하는 삭제 목록
	 * 
	 * @return 삭제대상 게시글  목록
	 */
	List<BoardItem> listBatchDeletePassedBoardItem(Integer getCount);

	/**
	 * 게시물 상태가 2인 삭제 목록을 파라미터 갯수만큼 가져온다.
	 * 
	 * @param getCount 가져와야 하는 삭제 목록
	 * 
	 * @return 삭제대상 게시글  목록
	 */
	List<BoardItem> listBatchDeleteStatusBoardItem(Integer getCount);

	/**
	 * 특정 게시판에 최신에 등록된 게시글을 가져온다.
	 * 
	 * @param parameter  boardId 게시판 ID, count 글갯수
	 * @return
	 */
	List<BoardItem> listRecentBoardItem(Map<String, Object> parameter);


	List<BoardItem> listRecentBoardItemPortlet(Map<String, Object> parameter);
	
	/**
	 * 최신에 등록된 게시물을 가져온다.
	 * 
	 * @param parameterMap
	 */
	List<BoardItem> listRecentAllBoardItem(Map<String, Object> parameter);

	/**
	 * 게시글 삭제 상태를 변경한다.
	 * 
	 * @param parameter itemId 게시글 ID, status 게시글 상태
	 */
	void updateItemDeleteField(Map<String, Object> parameter);


	/**
	 * 삭제 대상 게시 글을 가져온다
	 * 
	 * @param boardId 게시판ID
	 * @param getCount 가져와야 하는 갯수
	 * @return
	 */
	List<BoardItem> listByBoardItemOfDeletedBoard(Map<String, Object> parameter);

}