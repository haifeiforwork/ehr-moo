/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.forum.dao.FrUserActivitiesDao;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.collpack.forum.model.FrUserActivities;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * TODO Javadoc주석작성updateOrderInit
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrUserActivitiesDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository("frUserActivitiesDao")
public class FrUserActivitiesDaoImpl extends GenericDaoSqlmap<FrUserActivities, String> implements FrUserActivitiesDao {
	

	public String create(FrUserActivities obj) {
		return (String) sqlInsert("collpack.fourm.dao.FrUserActivities.create", obj);
	}

	public boolean exists(String userId) {
		
		int count = (Integer) sqlSelectForObject("collpack.fourm.dao.FrUserActivities.exists", userId);
		
		boolean isResult = false;
		if (count > 0) {
			isResult = true;
		} else {
			isResult = false;
		}
		return isResult;
	}
	
	public FrUserActivities get(String id) {
		return (FrUserActivities) sqlSelectForObject("collpack.fourm.dao.FrUserActivities.get", id);
	}

	public FrUserActivities getUserInfo(String userId) {
		return (FrUserActivities) sqlSelectForObject("collpack.fourm.dao.FrUserActivities.getUserInfo", userId);
	}
	
	public List<FrUserActivities> list(FrSearch frSearch) {
		
		return sqlSelectForList("collpack.fourm.dao.FrUserActivities.list", frSearch);
	}
	
	
	public void update(FrUserActivities obj) {
		sqlUpdate("collpack.fourm.dao.FrUserActivities.update", obj);
	}
	
	public void updateIncrease(FrUserActivities obj) {
		sqlUpdate("collpack.fourm.dao.FrUserActivities.updateIncrease", obj);
	}
	
	
	
	public void updateDiscussionCount(String userId) {
		sqlUpdate("collpack.fourm.dao.FrUserActivities.updateDiscussionCount", userId);
		
	}

	public void updateItemCount(String userId) {
		sqlUpdate("collpack.fourm.dao.FrUserActivities.updateItemCount", userId);
		
	}

	public void updateLinereplyCount(String userId) {
		sqlUpdate("collpack.fourm.dao.FrUserActivities.updateLinereplyCount", userId);
		
	}

	public void updateFavoriteCount(String userId) {
		sqlUpdate("collpack.fourm.dao.FrUserActivities.updateFavoriteCount", userId);
		
	}

	public void updateAgreementCount(String userId) {
		sqlUpdate("collpack.fourm.dao.FrUserActivities.updateAgreementCount", userId);
		
	}

	public void updateOppositionCount(String userId) {
		sqlUpdate("collpack.fourm.dao.FrUserActivities.updateOppositionCount", userId);
		
	}

	public void updateRecommendCount(String userId) {
		sqlUpdate("collpack.fourm.dao.FrUserActivities.updateRecommendCount", userId);
		
	}

	public void remove(String id) {
		sqlDelete("collpack.fourm.dao.FrUserActivities.remove", id);
	}
	
	
}
