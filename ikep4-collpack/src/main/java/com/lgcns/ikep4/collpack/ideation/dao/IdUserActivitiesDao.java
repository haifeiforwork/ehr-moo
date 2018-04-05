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
import com.lgcns.ikep4.collpack.ideation.model.IdSearch;
import com.lgcns.ikep4.collpack.ideation.model.IdUserActivities;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdUserActivitiesDao.java 12238 2011-05-20 01:55:30Z loverfairy $
 */
public interface IdUserActivitiesDao extends GenericDao<IdUserActivities, String>  {
	
	/**
	 * 활동 사용자 목록
	 * @param idSearch
	 * @return
	 */
	public List<IdUserActivities> list(IdSearch idSearch);
	
	
	/**
	 * 사용자 항목 카운트 증가
	 * @param frUserActivities
	 */
	public void updateIncrease(IdUserActivities idUserActivities);
	
	/**
	 * Item count 수정
	 * @param userId
	 */
	public void updateItemCount(String userId);
	
	
	/**
	 * favorite 개수 수정
	 * @param userId
	 * @param count
	 */
	public void updateFavoriteCount(String userId, int favoriteCount);
	
	/**
	 * BusinessItem count  수정
	 * @param userId
	 */
	public void updateBusinessItemCount(String userId);
	
	/**
	 * RecommendItem count  수정
	 * @param userId
	 */
	public void updateRecommendItemCount(String userId);
	
	/**
	 * Recommend count 수정
	 * @param userId
	 */
	public void updateRecommendCount(String userId);
	
	/**
	 * adopt item count 수정
	 * @param userId
	 */
	public void updateAdoptiItemCount(String userId);
	
	
	/**
	 * adopt count 수정
	 * @param userId
	 */
	public void updateAdoptCount(String userId);
	
	/**
	 * 사용자가 쓴 리플 개수 수정
	 * @param userId
	 */
	public void updateLinereplyCount(String userId);
	
	/**
	 * user 정보 
	 * @param userId
	 * @return
	 */
	public IdUserActivities getUserInfo(String userId);
}
