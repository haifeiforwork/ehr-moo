/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.collpack.forum.model.FrUserActivities;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrUserActivitiesDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface FrUserActivitiesDao extends GenericDao<FrUserActivities, String>  {
	
	/**
	 * 활동 사용자 목록
	 * @param frSearch
	 * @return
	 */
	public List<FrUserActivities> list(FrSearch frSearch);
	
	
	/**
	 * 사용자 항목 카운트 증가
	 * @param frUserActivities
	 */
	public void updateIncrease(FrUserActivities frUserActivities);
	
	/**
	 * 토론주제 업데이트
	 * @param userId
	 */
	public void updateDiscussionCount(String userId);
	
	/**
	 * 토론글 업데이트
	 * @param userId
	 */
	public void updateItemCount(String userId);
	
	/**
	 * 토론의견 업데이트
	 * @param userId
	 */
	public void updateLinereplyCount(String userId);
	
	/**
	 * 즐겨찾기 개수 업데이트
	 * @param userId
	 */
	public void updateFavoriteCount(String userId);
	
	/**
	 * 찬성수 업데이트
	 * @param userId
	 */
	public void updateAgreementCount(String userId);
	
	/**
	 * 반대수 업데이트
	 * @param userId
	 */
	public void updateOppositionCount(String userId);
	
	/**
	 * 추천수 업데이트
	 * @param userId
	 */
	public void updateRecommendCount(String userId);
	
	/**
	 * user 정보
	 * @param userId
	 * @return
	 */
	public FrUserActivities getUserInfo(String userId);
}
