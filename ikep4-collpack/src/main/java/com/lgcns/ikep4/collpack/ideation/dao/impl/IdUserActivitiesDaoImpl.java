/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.collpack.ideation.dao.IdUserActivitiesDao;
import com.lgcns.ikep4.collpack.ideation.model.IdSearch;
import com.lgcns.ikep4.collpack.ideation.model.IdUserActivities;

/**
 * TODO Javadoc주석작성updateOrderInit
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdUserActivitiesDaoImpl.java 12461 2011-05-20 09:49:00Z loverfairy $
 */
@Repository("idUserActivitiesDao")
public class IdUserActivitiesDaoImpl extends GenericDaoSqlmap<IdUserActivities, String> implements IdUserActivitiesDao {
	

	public String create(IdUserActivities obj) {
		return (String) sqlInsert("collpack.ideation.dao.IdUserActivities.create", obj);
	}

	public boolean exists(String userId) {
		
		int count = (Integer) sqlSelectForObject("collpack.ideation.dao.IdUserActivities.exists", userId);
		
		boolean isResult = false;
		if (count > 0) {
			isResult =  true;
		} else {
			isResult =  false;
		}
		
		return isResult;
	}
	
	public IdUserActivities get(String userId) {
		return (IdUserActivities) sqlSelectForObject("collpack.ideation.dao.IdUserActivities.get", userId);
	}
	
	public IdUserActivities getUserInfo(String userId) {
		return (IdUserActivities) sqlSelectForObject("collpack.ideation.dao.IdUserActivities.getUserInfo", userId);
	}

	
	public List<IdUserActivities> list(IdSearch idSearch) {
		
		return sqlSelectForList("collpack.ideation.dao.IdUserActivities.list", idSearch);
	}
	
	
	public void update(IdUserActivities idUserActivities) {
		sqlUpdate("collpack.ideation.dao.IdUserActivities.update", idUserActivities);
	}
	
	public void updateIncrease(IdUserActivities idUserActivities) {
		sqlUpdate("collpack.ideation.dao.IdUserActivities.updateIncrease", idUserActivities);
	}
	
	
	
	
	public void updateItemCount(String userId) {
		sqlUpdate("collpack.ideation.dao.IdUserActivities.updateItemCount", userId);
		
	}
	
	public void updateFavoriteCount(String userId, int favoriteCount) {
		
		IdUserActivities idUserActivities= new IdUserActivities();
		idUserActivities.setUserId(userId);
		idUserActivities.setFavoriteCount(favoriteCount);
		
		sqlUpdate("collpack.ideation.dao.IdUserActivities.updateFavoriteCount", idUserActivities);
	}

	public void updateBusinessItemCount(String userId) {
		sqlUpdate("collpack.ideation.dao.IdUserActivities.updateBusinessItemCount", userId);
	}

	public void updateRecommendItemCount(String userId) {
		sqlUpdate("collpack.ideation.dao.IdUserActivities.updateRecommendItemCount", userId);
		
	}

	public void updateRecommendCount(String userId) {
		sqlUpdate("collpack.ideation.dao.IdUserActivities.updateRecommendCount", userId);
	}
	
	public void updateAdoptiItemCount(String userId) {
		sqlUpdate("collpack.ideation.dao.IdUserActivities.updateAdoptiItemCount", userId);
	}
	
	public void updateAdoptCount(String userId) {
		sqlUpdate("collpack.ideation.dao.IdUserActivities.updateAdoptCount", userId);
	}
	

	public void remove(String userId) {
		sqlDelete("collpack.ideation.dao.IdUserActivities.remove", userId);
	}

	public void updateLinereplyCount(String userId) {
		sqlUpdate("collpack.ideation.dao.IdUserActivities.updateLinereplyCount", userId);
	}
	
	
}
