/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.qna.model.Qna;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface QnaDao extends GenericDao<Qna, String>  {
	
	/**
	 * 모든 게시물 반환
	 * 
	 * @return
	 */
	List<Qna> listAll(Qna qnaSearch);
	
	/**
	 * 관련 게시물
	 * @param qnaSearch
	 * @return
	 */
	List<Qna> listRelation(Qna qnaSearch);
	
	
	/**
	 * 자신과 자식 아이디 가져오기
	 * @param qnaId
	 * @return
	 */
	List<String> listChildId(String qnaId);
	
	/**
	 * 게시물 개수
	 * 
	 * @return
	 */
	int selectCount(Qna qnaSearch);
	
	/**
	 * 관련 QNA 개수 가져오기
	 * @param qnaSearch
	 * @return
	 */
	int selectCountRelation(Qna qnaSearch);
	
	
	/**
	 * hit 카운트 올리기
	 * 
	 * @return
	 */
	int selectGroupAdoptStatus(String qnaI);
	
	/**
	 * 답변이 있는지 채택된 답변이 있는지 체크
	 * 
	 * @return
	 */
	List<Qna> selectByGroup(String qnaGroupId);
	
	/**
	 * 답변이 있는지 채택된 답변이 있는지 체크 - line 수만큼만 가져올때
	 * 
	 * @return
	 */
	List<Qna> selectByGroup(String qnaGroupId, String line);
	
	
	/**
	 * 답변 시간 전체 더한값 가져오기
	 * @param portalId
	 * @return
	 */
	double getSumAnswerNecessaryTime(String portalId);
	
	/**
	 * 답변이 있는 전체 개수 가져오기
	 * @param portalId
	 * @return
	 */
	int getCountAnswerNecessaryTime(String portalId);
	
	/**
	 * 질문 전체 개수 가져오기
	 * @param portalId
	 * @return
	 */
	int getCountQna(String portalId);
	
	/**
	 * 답변이 있는 전체 개수 가져오기
	 * @param portalId
	 * @return
	 */
	int getCountQnaHasAnswer(String portalId);
	
	
	/**
	 * hit 카운트 올리기
	 * 
	 * @return
	 */
	void updateHit(String id);
	
	
	/**
	 * 추천수 변경
	 * 
	 * @return
	 */
	void updateRecommendCount(String qnaId);
	
	/**
	 * 답글수 변경
	 * 
	 * @return
	 */
	void updateReplyCount(String qnaId);
	
	/**
	 * 한줄 리플 수 변경
	 * 
	 * @return
	 */
	void updateLinereplyCount(String qnaId);
	
	/**
	 * 첨부파일수 올리기
	 * 
	 * @return
	 */
	void updateAttachFileCount(String qnaId, int attachFileCount);
	
	/**
	 * 답변 채택
	 * 
	 * @return
	 */
	void updateAnswerAdopt(String qnaId, int answerAdopt);
	
	/**
	 * 첨부파일수 올리기
	 * 
	 * @return
	 */
	void updateUrgent(String qnaId, int urgent);
	
	/**
	 * 삭제 변수 수정
	 * 
	 * @return
	 */
	void updateItemDelete(String qnaId, int itemDelete);
	
	/**
	 * 게시판 상태 수정
	 * 
	 * @return
	 */
	void updateStatus(String qnaGroupId, String status);
	
	/**
	 * 즐겨찾기 수 변경
	 * 
	 * @return
	 */
	void updateFavoriteCount(String qnaId, int favoriteCount);
	
	
	/**
	 * 자식 추천수 모두 더해 질문 추천수에 업데이트 함
	 * @param qnaId
	 */
	void updateRecommendCountSum(String qnaId);
	
	/**
	 * 즐겨찾기 수 변경
	 * 
	 * @return
	 */
	void updateAnswerNecessaryTime(String qnaId, double answerNecessaryTime);
	
	
	/**
	 * hit수 그룹전체 업데이트
	 * 
	 * @return
	 */
	void updateGroupHitCount(String qnaGroupId);
	
	/**
	 * 즐겨찾기 수 변경 즐겨찾기값을 더할수도 뺄수도 있음.
	 * @param favoriteCount TODO
	 * 
	 * @return
	 */
	void updateGroupFavoriteCount(String qnaGroupId, int favoriteCount);
	
	/**
	 * 카테고리 ID 변경
	 * @param beforeCategoryId
	 * @param afterCategoryId
	 */
	void updateCategoryId(String beforeCategoryId, String afterCategoryId);
	
	
	/**
	 * 메일수 업데이트
	 * @param qnaId
	 */
	void updateMailCount(String qnaId);
	
	
	/**
	 * 블로그수 업데이트
	 * @param qnaId
	 */
	void updateMblogCount(String qnaId);
	
	/**
	 * 스코어 점수 업데이트
	 * @param qna
	 */
	void updateScore(String qnaId, int score);
	
	/**
	 * 베스트 flag 업데이트
	 * @param qna
	 */
	void updateBestFlag(int qnaType, int score, String portalId);
	
	/**
	 * 베스트 qnaId 조회
	 * @param qnaType
	 * @param portalId
	 * @param newDate
	 * @return
	 */
	List<String> selectBestQnaId(int qnaType, String portalId, String newDate, int endRowIndex);
	
	/**
	 * 스코어, 베스트 flag 초기화
	 *
	 */
	void updateScoreInit() ;
}
