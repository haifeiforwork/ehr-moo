/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.qna.model.Qna;
import com.lgcns.ikep4.collpack.qna.model.QnaExpert;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaService.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Transactional
public interface QnaService extends GenericService<Qna, String> {
	
	/**
	 * 등록
	 * @param qna
	 * @param user
	 * @return
	 */
	public String create(Qna qna, User user);
	
	/**
	 * 수정
	 * @param qna
	 * @param user
	 */
	public void update(Qna qna, User user);
	

	/**
	 * 게시물 가져오기
	 * @param qnaSearch
	 * @return
	 */
	public List<Qna> list(Qna qnaSearch);
	
	/**
	 * 관련 게시물
	 * @param qnaSearch
	 * @return
	 */
	public List<Qna> listRelation(Qna qnaSearch);
	
	/**
	 * 게시물 전체 수
	 * @param qnaSearch
	 * @return
	 */
	public int listCount(Qna qnaSearch);
	
	/**
	 * 관련 QNA 개수 가져오기
	 * @param qnaSearch
	 * @return
	 */
	public int readCountRelation(Qna qnaSearch);
	
	/**
	 * 그룹 리스트 가져오기
	 * @param String
	 * @return
	 */
	public List<Qna> readGroup(String groupId, String registerId);
	
	
	/**
	 * 베스트 답변 qnaId 그룹 질정해진 개수만 가져오기
	 * @param String
	 * @return
	 */
	public String readBestQnaId(int type, String portalId, String newDate, int line );
	
	/**
	 * 메인에서 사용하는 전문가 리스트중 답변리스트 가져오기
	 * @param partalId
	 * @return
	 */
	public List<QnaExpert> listMainExpert(String partalId);
	
	/**
	 * 게시물 임시 삭제 하기
	 * @param qnaId
	 */
	public void approveDeleteItem(String qnaId);
	
	/**
	 * 게시물 임시 삭제 취소하기
	 * @param qnaId
	 */
	public void cancleDeleteItem(String qnaId);
	
	/**
	 * 답변 채택 하기
	 * @param qnaId
	 */
	public void approveAdopt(String qnaId);
	
	/**
	 * 답변채택 취소
	 * @param qnaId
	 */
	public void cancelAdopt(String qnaId);
	
	
	/**
	 * 즐겨찾기 추가
	 * @param qnaId
	 */
	public void updateFavorite(String qnaGroupId, int favoriteCount);
	
	/**
	 * 메일수 업데이트
	 * @param qnaId
	 */
	public void updateMailCount(String qnaId);
	
	
	/**
	 * 블로그수 업데이트
	 * @param qnaId
	 */
	public void updateMblogCount(String qnaId);
	
	
	
}
