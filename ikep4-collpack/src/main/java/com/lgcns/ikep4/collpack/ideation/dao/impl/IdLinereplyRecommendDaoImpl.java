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
import com.lgcns.ikep4.collpack.ideation.dao.IdLinereplyRecommendDao;
import com.lgcns.ikep4.collpack.ideation.model.IdLinereplyRecommend;

/**
 * TODO Javadoc주석작성updateOrderInit
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdLinereplyRecommendDaoImpl.java 12461 2011-05-20 09:49:00Z loverfairy $
 */
@Repository("idLinereplyRecommendDao")
public class IdLinereplyRecommendDaoImpl extends GenericDaoSqlmap<IdLinereplyRecommend, String> implements IdLinereplyRecommendDao {
	
	@Deprecated
	public String create(IdLinereplyRecommend obj) {
		return null;
	}
	
	public void create(String linereplyId,  String registerId) {
		
		IdLinereplyRecommend idLinereplyRecommend = new IdLinereplyRecommend();
		idLinereplyRecommend.setLinereplyId(linereplyId);
		idLinereplyRecommend.setRegisterId(registerId);
		
		sqlInsert("collpack.ideation.dao.IdLinereplyRecommend.create", idLinereplyRecommend);
	}
	
	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	public boolean exists(String linereplyId,  String registerId) {
		
		IdLinereplyRecommend idLinereplyRecommend = new IdLinereplyRecommend();
		idLinereplyRecommend.setLinereplyId(linereplyId);
		idLinereplyRecommend.setRegisterId(registerId);
		
		int count = (Integer) sqlSelectForObject("collpack.ideation.dao.IdLinereplyRecommend.exists", idLinereplyRecommend);
		
		boolean isResult = false;
		if (count > 0) {
			isResult =  true;
		} else {
			isResult =  false;
		}
		
		return isResult;
	}
	
	
	@Deprecated
	public IdLinereplyRecommend get(String id) {
		return null;
	}
	
	
	public int getCountByUserId(String registerId) {
		return (Integer)sqlSelectForObject("collpack.ideation.dao.IdLinereplyRecommend.getCountByUserId", registerId);
	}

	public List<String> listUserIdByLinereplyId(String linereplyId) {
		return (List)sqlSelectForListOfObject("collpack.ideation.dao.IdLinereplyRecommend.listUserIdByLinereplyId", linereplyId);
	}

	public List<String> listUserIdByItemId(String itemId) {
		return (List)sqlSelectForListOfObject("collpack.ideation.dao.IdLinereplyRecommend.listUserIdByItemId", itemId);
	}


	public List<IdLinereplyRecommend> list(String linereplyId) {
		
		return sqlSelectForList("collpack.ideation.dao.IdLinereplyRecommend.list", linereplyId);
	}

	@Deprecated
	public void update(IdLinereplyRecommend object) {
	}

	@Deprecated
	public void remove(String id) {
	}

	public void remove(String linereplyId, String registerId) {
		
		IdLinereplyRecommend idLinereplyRecommend = new IdLinereplyRecommend();
		idLinereplyRecommend.setLinereplyId(linereplyId);
		idLinereplyRecommend.setRegisterId(registerId);
		
		sqlDelete("collpack.ideation.dao.IdLinereplyRecommend.remove", idLinereplyRecommend);
	}

	public void removeByLinereplyId(String linereplyId) {
		sqlDelete("collpack.ideation.dao.IdLinereplyRecommend.removeByLinereplyId", linereplyId);
	}
	
	

	public void removeByItemId(String itemId) {
		sqlDelete("collpack.ideation.dao.IdLinereplyRecommend.removeByItemId", itemId);
	}

	
}
