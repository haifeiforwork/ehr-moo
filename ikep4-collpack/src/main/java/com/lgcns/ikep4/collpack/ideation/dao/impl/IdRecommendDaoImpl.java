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
import com.lgcns.ikep4.collpack.ideation.dao.IdRecommendDao;
import com.lgcns.ikep4.collpack.ideation.model.IdRecommend;

/**
 * TODO Javadoc주석작성updateOrderInit
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdRecommendDaoImpl.java 12461 2011-05-20 09:49:00Z loverfairy $
 */
@Repository("idRecommendDao")
public class IdRecommendDaoImpl extends GenericDaoSqlmap<IdRecommend, String> implements IdRecommendDao {
	
	@Deprecated
	public String create(IdRecommend obj) {
		return null;
	}
	
	public void create(String itemId,  String registerId) {
		
		IdRecommend idRecommend = new IdRecommend();
		idRecommend.setItemId(itemId);
		idRecommend.setRegisterId(registerId);
		
		sqlInsert("collpack.ideation.dao.IdRecommend.create", idRecommend);
	}
	
	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	public boolean exists(String itemId,  String registerId) {
		
		IdRecommend idRecommend = new IdRecommend();
		idRecommend.setItemId(itemId);
		idRecommend.setRegisterId(registerId);
		
		int count = (Integer) sqlSelectForObject("collpack.ideation.dao.IdRecommend.exists", idRecommend);
		
		boolean isResult = false;
		if (count > 0) {
			isResult =  true;
		} else {
			isResult =  false;
		}
		
		return isResult;
	}
	
	
	@Deprecated
	public IdRecommend get(String id) {
		return null;
	}
	
	
	public int getCountByUserId(String registerId) {
		return (Integer)sqlSelectForObject("collpack.ideation.dao.IdRecommend.getCountByUserId", registerId);
	}

	public List<String> listUserIdByItemId(String itemId) {
		return (List)sqlSelectForListOfObject("collpack.ideation.dao.IdRecommend.listUserIdByItemId", itemId);
	}


	public List<IdRecommend> list(String itemId) {
		
		return sqlSelectForList("collpack.ideation.dao.IdRecommend.list", itemId);
	}

	@Deprecated
	public void update(IdRecommend object) {
	}

	@Deprecated
	public void remove(String id) {
	}

	public void remove(String itemId, String registerId) {
		
		IdRecommend idRecommend = new IdRecommend();
		idRecommend.setItemId(itemId);
		idRecommend.setRegisterId(registerId);
		
		sqlDelete("collpack.ideation.dao.IdRecommend.remove", idRecommend);
	}


	public void removeByItemId(String itemId) {
		sqlDelete("collpack.ideation.dao.IdRecommend.removeByItemId", itemId);
	}

	
}
