/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.forum.model.FrItem;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrItemDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface FrItemDao extends GenericDao<FrItem, String>  {
	
	/**
	 * 사용자가 등록한 토론글, 그 글에 해당하는 즐겨찾기 합, 찬성합, 반대합
	 * @param registerId
	 * @return
	 */
	public FrItem getCountes(FrSearch frSearch);
	
	
	/**
	 * TODO Javadoc주석작성
	 * @param discussionId
	 * @return
	 */
	public List<String> listUserId(String discussionId);
	
	
	/**
	 * 토론글 일반 리스트
	 * @param frSearch
	 * @return
	 */
	public List<FrItem> list(FrSearch frSearch);
	
	/**
	 * 토론글 일반 목록 개수
	 * @param frSearch
	 * @return
	 */
	public int getCountList(FrSearch frSearch);
	
	
	/**
	 * 토론주제, 토론글 리스트
	 * @param frSearch
	 * @return
	 */
	public List<FrItem> listWithDiscussion(FrSearch frSearch);
	
	/**
	 * 토론주제, 토론글 리스트 개수
	 * @param frSearch
	 * @return
	 */
	public int getCountListWithDiscussion(FrSearch frSearch);
	
	/**
	 * 최신 주제 목록과 주제글
	 * @param frSearch
	 * @return
	 */
	public List<FrItem> listLastWithDiscussion(FrSearch frSearch);
	
	/**
	 *최신 주제 목록과 주제글 개수
	 * @param frSearch
	 * @return
	 */
	public int getCountListLastWithDiscussion(FrSearch frSearch);
	
	/**
	 * 토론글 인기 리스트
	 * @param frSearch
	 * @return
	 */
	public List<FrItem> listPopular(FrSearch frSearch);
	
	/**
	 * 토론글 인기 리스트 개수
	 * @param frSearch
	 * @return
	 */
	public int getCountListPopular(FrSearch frSearch);
	
	/**
	 * 카테고리 랜덤 토론글 조회
	 * @param frSearch
	 * @return
	 */
	public List<FrItem> listItemRandomCategory(FrSearch frSearch);
	
	
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
	 * 조회수 수정
	 * @param itemId
	 */
	public void updateHitCount(String itemId);
	
	/**
	 * 토론의견수 수정
	 * @param itemId
	 */
	public void updateLinereplyCount(String itemId);
	
	/**
	 * 찬성수 수정
	 * @param itemId
	 */
	public void updateAgreementCount(String itemId);
	
	/**
	 * 반대수 수정
	 * @param itemId
	 */
	public void updateOppositionCount(String itemId);
	
	/**
	 * 즐겨찾기 수정
	 * @param itemId
	 */
	public void updateFavoriteCount(String itemId, int favoriteCount);
	
	/**
	 * 토론주제 ID에 해당하는 토론글 삭제
	 * @param discussionId
	 */
	public void removeByDiscussionId(String discussionId);
	
}
