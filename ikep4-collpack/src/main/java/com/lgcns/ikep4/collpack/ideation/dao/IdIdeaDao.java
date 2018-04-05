/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.collpack.ideation.model.IdIdea;
import com.lgcns.ikep4.collpack.ideation.model.IdSearch;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdIdeaDao.java 12098 2011-05-19 09:46:17Z loverfairy $
 */
public interface IdIdeaDao extends GenericDao<IdIdea, String>  {
	
	/**
	 * 사용자가 등록한 토론글, 그 글에 해당하는 즐겨찾기 합, 찬성합, 반대합
	 * @param registerId
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
	 * 일반 목록 개수
	 * @param idSearch
	 * @return
	 */
	public int getCountList(IdSearch idSearch);
	
	
	
	/**
	 * 베스트 토론글 수정
	 * @param itemId
	 */
	public void updateBestItem(String itemId);
	
	/**
	 * 토스트 토론글 초기화
	 *
	 */
	public void updateBestItemInit();
	
	
	/**
	 * 추천아이디어로 변경
	 *
	 */
	public void updateRecommendItem(String itemId, int recommendItem);
	
	/**
	 * 채택 아이디어로 변경
	 *
	 */
	public void updateAdoptItem(String itemId, int adoptItem);
	
	
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
	 * 조회수 수정
	 * @param itemId
	 */
	public void updateHitCount(String itemId);
	
	
	/**
	 * 추천수 수정
	 * @param itemId
	 */
	public void updateRecommendCount(String itemId);
	
	
	/**
	 * 즐겨찾기수 수정
	 * @param itemId
	 */
	public void updateFavoriteCount(String itemId, int favoriteCount);
	
	/**
	 * 채택수 수정
	 * @param itemId
	 */
	public void updateAdoptCount(String itemId);
	
	/**
	 * 토론의견수 수정
	 * @param itemId
	 */
	public void updateLinereplyCount(String itemId);
	
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
	 * 사용자 즐겨찾기 개수
	 * @param userId
	 * @param itemTypeCode
	 * @return
	 */
	public int getFavorite(String userId, String itemTypeCode);

	public void updateCategoryId(String beforeCategoryId, String afterCategoryId);	
}
