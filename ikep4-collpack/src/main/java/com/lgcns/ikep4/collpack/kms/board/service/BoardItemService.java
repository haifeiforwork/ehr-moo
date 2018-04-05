/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.kms.board.model.BoardAssessItem;
import com.lgcns.ikep4.collpack.kms.board.model.BoardItem;
import com.lgcns.ikep4.collpack.kms.board.model.BoardItemReader;
import com.lgcns.ikep4.collpack.kms.board.model.BoardRecommend;
import com.lgcns.ikep4.collpack.kms.board.model.BoardReference;
import com.lgcns.ikep4.collpack.kms.board.search.BoardItemReaderSearchCondition;
import com.lgcns.ikep4.collpack.kms.board.search.BoardItemSearchCondition;
import com.lgcns.ikep4.framework.web.SearchResult;
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

	/**
	 * 게시글 검색조건을 이용해서 게시글 목록(게시글 쓰레드가 무시된)을 조회한다.
	 * 
	 * @param boardItemSearchCondition 게시글 검색조건
	 * @return 게시글 목록(게시글 쓰레드가 무시된)
	 */
	SearchResult<BoardItem> listBoardItemNoThreadBySearchCondition(BoardItemSearchCondition boardItemSearchCondition);
	
	public List<BoardItem> getRecommendDetailList(BoardItemSearchCondition searchCondition);
	
	public List<BoardItem> getRecommendReceiveDetailList(BoardItemSearchCondition searchCondition);
	
	public List<BoardItem> getReplyDetailList(BoardItemSearchCondition searchCondition);
	
	public List<BoardItem> getReplyReceiveDetailList(BoardItemSearchCondition searchCondition);

	/**
	 * 게시글을 조회한다. 게시글 조회시 첨부파일, 위지윅에디터 파일, 게시글쓰레드, 관련글, 태그 목록을 함께 가져온다.
	 * 
	 * @param itemId 게시글 ID
	 * @return 조회된 게시글
	 */
	BoardItem readBoardItem(String itemId);

	/**
	 * 게시글을 생성한다.
	 * 
	 * @param boardItem 생성해야 하는 게시글 모델 객체
	 * @return 생성된 게시글 ID
	 */
	String createBoardItem(BoardItem boardItem, User user);

	/**
	 * 답변을 생성한다.
	 * 
	 * @param boardItem 생성해야 하는 게시글 모델 객체
	 * @return 생성된 게시글 ID
	 */
	String createReplyBoardItem(BoardItem boardItem, User user);

	/**
	 * 게시글을 수정한다.
	 * 
	 * @param boardItem 수정해야 하는 게시글 모델 객체
	 */
	void updateBoardItem(BoardItem boardItem, User user);
	
	void updateTempBoardItem(BoardItem boardItem, User user);
	
	void updateBoardItem(BoardItem boardItem, User user, boolean isAssess);

	/**
	 * 관리자 모드로 게시글을 삭제한다. 관리자 혹은 작성자가 답변글이 없는 게시글을 삭제할 경우 게시글, 댓글, 첨부파일, 위지윅에디터
	 * 파일을 모두 삭제한다.
	 * 
	 * @param id 삭제 대상 게시글 ID
	 * @param batch 배치용 삭제여부
	 */
	void adminDeleteBoardItem(BoardItem boardItem, Boolean batch);

	/**
	 * 작성자 모드로 게시글을 논리적으로 삭제한다. 삭제여부만 업데이트 함
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
	 * 게시글의 추천 수를 증가시킨다. 사용자는 2번 이상의 추천은 하지 못한다.
	 * 
	 * @param boardRecommend 게시판 추천 모델 객체
	 */
	void updateRecommendCount(BoardRecommend boardRecommend);
	
	void updateScore(BoardRecommend boardRecommend);

	/**
	 * 게시글의 조회 수를 증가시킨다. 사용자는 게시글의 조회 수를 2번 이상 증가시키지 못한다.
	 * 
	 * @param id 업데이트 대상 게시글 ID
	 */
	void updateHitCount(BoardReference boardReference);

	/**
	 * 게시글ID 목록에 해당되는 모든 게시글 쓰레드를 삭제한다. 게시글ID에 해당되는 쓰레드와 댓글, 첨부파일, 위지윅에디터 파일을 모두
	 * 삭제한다.
	 * 
	 * @param itemIds 삭제되어야 하는 게시글 ID
	 */
	void adminMultiDeleteBoardItem(List<String> itemIds);

	/**
	 * 게시판에 속해있는 모든 게시글을 삭제한다.
	 * 
	 * @param boardId
	 */
	void adminDeleteBoardItemInBoard(String boardId);

	/**
	 * 게시글 기본 정보만 조회한다.
	 * 
	 * @param itemId 게시글 ID
	 * @return 게시글 기본 정보
	 */
	BoardItem readBoardItemMasterInfo(String itemId);
	
	BoardItem readCaution();

	/**
	 * TODO Javadoc주석작성
	 * 
	 * @param boardId
	 * @param itemId
	 * @param count
	 * @return
	 */
//	List<BoardItem> listRecentBoardItem(String boardId, Integer count);

	/* WS 추가된 내역 05/17 */

	/**
	 * Collaboration 메인 화면에서 나의 Workspace 중의 최근 게시물 조회
	 */
	public SearchResult<BoardItem> listMyCollBoardItemBySearchCondition(BoardItemSearchCondition searchCondition,
			User user);

	/**
	 * 개별 WOrkspace 게시판 최근 목록
	 * 
	 * @param map
	 * @return
	 */
	public List<BoardItem> listBoardItemByPortlet(Map<String, String> map);

	/**
	 * 삭제된 게시판의 첨부파일이 있는 게시물 조회
	 * 
	 * @param boardId
	 * @return
	 */
	public List<BoardItem> listDeleteBoardItem(String boardId);

	/**
	 * 게시물 이동
	 * 
	 * @param orgBoardId
	 * @param targetBoardId
	 * @param itemIds
	 * @param user
	 */
	void moveBoardItem(String orgBoardId, String targetBoardId, String[] itemIds, User user);

	/*
	 * public void createBoardItemReference(BoardItemReference
	 * boardItemReference); public void
	 * createBoardItemRecommend(BoardItemRecommend boardItemRecommend); public
	 * boolean existsBoardItemReference(BoardItemReference boardItemReference);
	 * public boolean existsBoardItemRecommend(BoardItemRecommend
	 * boardItemRecommend);
	 */
	/**
	 * 게시물 삭제 - 관리자
	 */
	void adminDeleteBoardItem(BoardItem boardItem);

	// void updateRecommendCount(String itemId);

	// void updateHitCount(String itemId);

	/**
	 * 메일수 수정
	 * 
	 * @param itemId
	 */
	public void updateMailCount(String itemId);

	/**
	 * 블로그수 수정
	 * 
	 * @param itemId
	 */
	public void updateMblogCount(String itemId);

	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	/**
	 * 최신 Board 게시물 리스트
	 * 
	 * @param announceItemSearchCondition
	 * @return
	 */
	SearchResult<BoardItem> latestListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition);
	
	SearchResult<BoardItemReader> listReaderBySearchCondition(BoardItemReaderSearchCondition boardItemSearchCondition);
	
	SearchResult<BoardItem> hotissueListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition);

	/**
	 * 우수정보 Board 게시물 리스트
	 * 
	 * @param announceItemSearchCondition
	 * @return
	 */
	SearchResult<BoardItem> excellenceListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition);

	/**
	 * 게시글을 조회한다. 게시글 조회시 첨부파일, 위지윅에디터 파일, 게시글쓰레드, 관련글, 태그 목록을 함께 가져온다.
	 * 
	 * @param itemId 게시글 ID
	 * @return 조회된 게시글
	 */
	BoardItem readBoardItem1(String itemId);
	
	/**
	 * 게시글의 조회 수를 증가시킨다. 사용자는 게시글의 조회 수를 2번 이상 증가시키지 못한다.
	 * 
	 * @param id 업데이트 대상 게시글 ID
	 */
	void updateHitCount(String userId, String itemId);
	
	/**
	 * Ref Board 게시물 리스트
	 * 
	 * @param boardItemSearchCondition
	 * @return
	 */
	SearchResult<BoardItem> refListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition);
	
	/**
	 * 임시저장 Board 게시물 리스트
	 * 
	 * @param boardItemSearchCondition
	 * @return
	 */
	SearchResult<BoardItem> temporaryListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition);
	
	SearchResult<BoardItem> targetListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition);
	
	SearchResult<BoardItem> keyInfoListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition);
	
	/**
	 * 전문가 Board 게시물 리스트
	 * 
	 * @param boardItemSearchCondition
	 * @return
	 */
	SearchResult<BoardItem> assessListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition);
	
	/**
	 * 전문가 설정
	 * 
	 * @param map
	 * @return
	 */
	public void assessMove(Map<String, String> map);
	
	public List<BoardAssessItem> listAssessItem(String boardId);
	
	/**
	 * 전문가평가 사용자인지 체크
	 * 
	 * @param sysName
	 * @param userId
	 * @return
	 */
	public boolean isAssessor(String userId);
	
	public boolean isKeyInfoAssessor(String userId);
	
	/**
	 * 지식조회 Board 게시물 리스트
	 * 
	 * @param announceItemSearchCondition
	 * @return
	 */
	SearchResult<BoardItem> searchListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition);
	
	/**
	 * 게시글을 조회한다. 게시글 조회시 첨부파일, 위지윅에디터 파일, 게시글쓰레드, 관련글, 태그 목록을 함께 가져온다.
	 * 
	 * @param itemId 게시글 ID
	 * @return 조회된 게시글
	 */
	BoardItem readBoardItemPrint(String itemId);
	
	/**
	 * E등급지식 Board 게시물 리스트
	 * 
	 * @param boardItemSearchCondition
	 * @return
	 */
	SearchResult<BoardItem> einfogradeListBySearchCondition(BoardItemSearchCondition boardItemSearchCondition);
	
	public List<BoardItem> expertList();
	
}
