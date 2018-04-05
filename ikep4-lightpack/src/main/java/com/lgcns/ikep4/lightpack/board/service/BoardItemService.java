/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.lightpack.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.board.model.BoardRecommend;
import com.lgcns.ikep4.lightpack.board.model.BoardReference;
import com.lgcns.ikep4.lightpack.board.search.BoardItemSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 게시글 서비스 클래스
 */
@Transactional
public interface BoardItemService {

	/**
	 * 게시글 검색조건을 이용해서 게시글 목록을 조회한다.
	 *
	 * @param boardItemSearchCondition 게시글 검색조건
	 * @return 게시글 목록
	 */
	SearchResult<BoardItem> listBoardItemBySearchCondition(BoardItemSearchCondition boardItemSearchCondition);
	SearchResult<BoardItem> listBoardItemBySearchCondition2(BoardItemSearchCondition boardItemSearchCondition);
	SearchResult<BoardItem> listBoardItemBySearchCondition3(BoardItemSearchCondition boardItemSearchCondition);
	
	SearchResult<BoardItem> deleteListBoardItemBySearchCondition(BoardItemSearchCondition boardItemSearchCondition);
	/**
	 * 게시글을 조회한다.
	 * 게시글 조회시 첨부파일, 위지윅에디터 파일, 게시글쓰레드, 관련글, 태그 목록을 함께 가져온다.
	 * @param itemId 게시글 ID
	 * @return 조회된 게시글
	 */
	BoardItem readBoardItem(String itemId);

	List<String> workplaceList(String itemId);
	/**
	 * 게시글을 생성한다.
	 *
	 * @param boardItem 생성해야 하는 게시글 모델 객체
	 * @return 생성된 게시글 ID
	 */
	String createBoardItem(BoardItem boardItem, User user);
	
	void saveBoardItemWorkplace(BoardItem boardItem);
	
	/**
	 * 답변을 생성한다.
	 *
	 * @param boardItem 생성해야 하는 게시글 모델 객체
	 * @return 생성된 게시글 ID
	 */
	String createReplyBoardItem(BoardItem boardItem, User user);
	
	int getMailWaitTime();
	
	int getMailWaitCount();
	
	int getMailWaitYn(String itemId);
	
	int getBoardItemCount(BoardItem boardItem);
	
	int getHappyBoardItemCount(BoardItem boardItem);
	
	void updateMailWaitYn(String itemId);

	/**
	 * 게시글을 수정한다.
	 *
	 * @param boardItem 수정해야 하는 게시글 모델 객체
	 */
	void updateBoardItem(BoardItem boardItem, User user);

	/**
	 * 관리자 모드로 게시글을 삭제한다.
	 * 관리자 혹은 작성자가 답변글이 없는 게시글을 삭제할 경우 게시글, 댓글, 첨부파일, 위지윅에디터 파일을 모두 삭제한다.
	 *
	 * @param id 삭제  대상 게시글 ID
	 * @param batch 배치용 삭제여부
	 */
	void adminDeleteBoardItem(BoardItem boardItem);

	/**
	 * 작성자 모드로 게시글을 논리적으로 삭제한다.
	 * 삭제여부만 업데이트 함
	 *
	 * @param boardItem 삭제 대상 게시글
	 */
	void userDeleteBoardItem(BoardItem boardItem);

	/**
	 * 게시글이 추천되었는지 확인
	 *
	 * @param boardRecommend 게시판 추천 모델 객체
	 */
	Boolean exsitRecommend(BoardRecommend boardRecommend);

	/**
	 * 게시글의 추천 수를 증가시킨다.
	 * 사용자는 2번 이상의 추천은 하지 못한다.
	 *
	 * @param boardRecommend 게시판 추천 모델 객체
	 */
	void updateRecommendCount(BoardRecommend boardRecommend);

	/**
	 * 게시글의 조회 수를 증가시킨다.
	 * 사용자는 게시글의 조회 수를 2번 이상 증가시키지 못한다.
	 *
	 * @param id 업데이트 대상 게시글 ID
	 */
	void updateHitCount(BoardReference boardReference);

	/**
	 * 게시글ID 목록에 해당되는 모든 게시글 쓰레드를 삭제 상태로 만든다.
	 *
	 * @param itemIds 삭제되어야 하는 게시글 ID
	 */
	void adminMultiDeleteBoardItem(String[] itemIds);


	/**
	 * 게시글 기본 정보만 조회한다.
	 * 
	 * @param itemId 게시글 ID
	 * 
	 * @return 게시글 기본 정보
	 * 
	 */
	BoardItem readBoardItemMasterInfo(String itemId);

	/**
	 * 최근 게시글을 가져온다.
	 * 
	 * @param boardId 게시판ID
	 * @param count 갯수
	 * @return
	 */
	List<BoardItem> listRecentBoardItem(BoardItemSearchCondition searchConditiont);

	/**
	 * 게시글 삭제 상태를 변경한다.
	 * 
	 * @param itemId 게시글 ID
	 * @param status 게시글 상태
	 */
	void updateItemDeleteField(String itemId, Integer status);

	/**
	 * 여러개의 게시글 상태를 변경한다.
	 * 
	 * @param asList 게시글 ID 목록
	 * @param status 게시글 상태
	 */
	void updateItemListDeleteField(List<String> asList, Integer status);

	/**
	 * 게시글 쓰레드의 상태를 변경한다.
	 * 
	 * @param itemId
	 * @param statusDeleteWaiting
	 */
	void updateItemThreadDeleteField(String itemId, Integer status);

	/**
	 * 게시물 쓰레드를  삭제한다.
	 * 게시글ID에 해당되는 쓰레드와 댓글, 첨부파일, 위지윅에디터 파일을 모두 삭제한다.
	 * 
	 * @param boardItem
	 */
	void deleteBoardItemThread(BoardItem boardItem);

	/**
	 * 게시물 쓰레드를 가져온다.
	 * 
	 * @param itemGroupId 게시물 그룹 Id
	 * @return
	 */
	List<BoardItem> listReplayItemThreadByItemId(String itemGroupId);
	
	/**
	 * 메일수 수정
	 * @param itemId
	 */
	public void updateMailCount(String itemId);
	
	
	public void doBoardItemNotiJobSchedule(Date jobTime, int interval, String fileUrl) throws ParseException;
	
	public void doBoardItemNotiInstant(String itemId) throws ParseException;
	
	/**
	 * 블로그수 수정
	 * @param itemId
	 */
	public void updateMblogCount(String itemId);

	/**
	 * 게시물 이동
	 * 
	 * @param orgBoardId
	 * @param targetBoardId
	 * @param itemIds
	 * @param user
	 */
	void moveBoardItem(String orgBoardId, String targetBoardId, String[] itemIds, User user);
	
	/**
	 * 포틀릿에서 답변글을 제외한 게시글을 가져온다.
	 * */
	List<BoardItem> listRecentBoardItemPortlet(String boardId, Integer count);
	
	/**
	 * 임시 저장 게시물 리스트
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<BoardItem> listTempSaveItemBySearchCondition(BoardItemSearchCondition searchCondition);
	
	/**
	 * 이전 게시글을 조회한다.
	 * 게시글 조회시 첨부파일, 위지윅에디터 파일, 게시글쓰레드, 관련글, 태그 목록을 함께 가져온다.
	 * @param boardId 게시판 ID
	 * @param itemId 게시글 ID
	 * @return 조회된 게시글
	 */
	BoardItem readPrevBoardItem(String boardId, String itemId);

	/**
	 * 다음 게시글을 조회한다.
	 * 게시글 조회시 첨부파일, 위지윅에디터 파일, 게시글쓰레드, 관련글, 태그 목록을 함께 가져온다.
	 * @param boardId 게시판 ID
	 * @param itemId 게시글 ID
	 * @return 조회된 게시글
	 */
	BoardItem readNextBoardItem(String boardId, String itemId);
	
	public SearchResult<BoardItem> getBoardMenuList(BoardItemSearchCondition searchCondition);
	
	public void updateBoardMenuList(Map<String, Object> param);
}