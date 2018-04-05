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

import com.lgcns.ikep4.collpack.forum.dao.FrRecommendDao;
import com.lgcns.ikep4.collpack.forum.model.FrRecommend;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * TODO Javadoc주석작성updateOrderInit
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrRecommendDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository("frRecommendDao")
public class FrRecommendDaoImpl extends GenericDaoSqlmap<FrRecommend, String> implements FrRecommendDao {
	
	@Deprecated
	public String create(FrRecommend obj) {
		return null;
	}
	
	public void create(String linereplyId,  String registerId) {
		
		FrRecommend frRecommend = new FrRecommend();
		frRecommend.setLinereplyId(linereplyId);
		frRecommend.setRegisterId(registerId);
		
		sqlInsert("collpack.fourm.dao.FrRecommend.create", frRecommend);
	}
	
	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	public boolean exists(String linereplyId,  String registerId) {
		
		FrRecommend frRecommend = new FrRecommend();
		frRecommend.setLinereplyId(linereplyId);
		frRecommend.setRegisterId(registerId);
		
		int count = (Integer) sqlSelectForObject("collpack.fourm.dao.FrRecommend.exists", frRecommend);
		
		boolean isResult = false;
		if (count > 0) {
			isResult = true;
		} else {
			isResult = false;
		}
		return isResult;
	}
	
	
	@Deprecated
	public FrRecommend get(String id) {
		return null;
	}
	
	
	public int getCountByUserId(String registerId) {
		return (Integer)sqlSelectForObject("collpack.fourm.dao.FrRecommend.getCountByUserId", registerId);
	}

	public List<String> listUserIdByLinereplyId(String linereplyId) {
		return (List)sqlSelectForListOfObject("collpack.fourm.dao.FrRecommend.listUserIdByLinereplyId", linereplyId);
	}

	public List<String> listUserIdByItemId(String itemId) {
		return (List)sqlSelectForListOfObject("collpack.fourm.dao.FrRecommend.listUserIdByItemId", itemId);
	}

	public List<String> listUserIdByDiscussionId(String discussionId) {
		return (List)sqlSelectForListOfObject("collpack.fourm.dao.FrRecommend.listUserIdByDiscussionId", discussionId);
	}

	public List<FrRecommend> list(String linereplyId) {
		
		return sqlSelectForList("collpack.fourm.dao.FrRecommend.list", linereplyId);
	}

	@Deprecated
	public void update(FrRecommend object) {
	}

	@Deprecated
	public void remove(String id) {
	}

	public void remove(String linereplyId, String registerId) {
		
		FrRecommend frRecommend = new FrRecommend();
		frRecommend.setLinereplyId(linereplyId);
		frRecommend.setRegisterId(registerId);
		
		sqlDelete("collpack.fourm.dao.FrRecommend.remove", frRecommend);
	}

	public void removeByLinereplyId(String linereplyId) {
		sqlDelete("collpack.fourm.dao.FrRecommend.removeByLinereplyId", linereplyId);
	}
	
	

	public void removeByItemId(String itemId) {
		sqlDelete("collpack.fourm.dao.FrRecommend.removeByItemId", itemId);
	}

	public void removeByDiscussionId(String discussionId) {
		sqlDelete("collpack.fourm.dao.FrRecommend.removeByDiscussionId", discussionId);
	}
	
	
	
}
