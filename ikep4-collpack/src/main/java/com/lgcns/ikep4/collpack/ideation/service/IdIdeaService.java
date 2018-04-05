/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.ideation.model.IdIdea;
import com.lgcns.ikep4.collpack.ideation.model.IdSearch;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdIdeaService.java 10759 2011-05-12 10:49:04Z loverfairy $
 */
@Transactional
public interface IdIdeaService extends GenericService<IdIdea, String>  {
	
	/**
	 * 상세 조회
	 * @param itemId
	 * @param registerId
	 * @return
	 */
	public IdIdea get(String itemId, String registerId);
	
	/**
	 * 개수들
	 * @param idSearch
	 * @return
	 */
	public IdIdea getCountes(IdSearch idSearch);
	
	/**
	 * 토론글 일반 리스트
	 * @param idSearch
	 * @return
	 */
	public List<IdIdea> list(IdSearch idSearch);
	
	/**
	 * 토론글 일반 목록 개수
	 * @param idSearch
	 * @return
	 */
	public int getCountList(IdSearch idSearch);
	
	
	/**
	 * 즐겨찾기 개수 변경
	 * @param itemId
	 * @param favoriteCount
	 */
	public void updateFavoriteCount(String itemId, int favoriteCount, String userId);
	
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
	
	/**
	 * 비지니스 아이디어로 변경
	 *
	 */
	public void updateBusinessItem(String itemId, String businessItem);
	
	
	/**
	 * 선정 내용 저장
	 *
	 */
	public void updateExamination(String itemId, String examinationComment);
	
	
	/**
	 * 삭제
	 */
	public void remove(String itemId);
	
	/**
	 * best 아이디어 선정
	 *
	 */
	public void updateBest();
	
	/**
	 * 사용자 즐겨찾기 개수
	 * @param userId
	 * @param itemTypeCode
	 * @return
	 */
	public int getFavorite(String userId, String itemTypeCode);
	
	public String  createReplyItem(IdIdea idIdea);

}
