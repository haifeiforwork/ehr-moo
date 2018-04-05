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

import com.lgcns.ikep4.collpack.forum.dao.FrReferenceDao;
import com.lgcns.ikep4.collpack.forum.model.FrReference;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * TODO Javadoc주석작성updateOrderInit
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrReferenceDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository("frReferenceDao")
public class FrReferenceDaoImpl extends GenericDaoSqlmap<FrReference, String> implements FrReferenceDao {
	

	public String create(FrReference obj) {
		return (String) sqlInsert("collpack.fourm.dao.FrReference.create", obj);
	}
	
	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	public boolean exists(String itemId,  String registerId) {
		
		FrReference frRecommend = new FrReference();
		frRecommend.setItemId(itemId);
		frRecommend.setRegisterId(registerId);
		
		int count = (Integer) sqlSelectForObject("collpack.fourm.dao.FrReference.exists", frRecommend);
		
		boolean isResult = false;
		if (count > 0) {
			isResult = true;
		} else {
			isResult = false;
		}
		return isResult;
	}
	
	
	@Deprecated
	public FrReference get(String id) {
		return null;
	}

	public List<FrReference> list(String itemId) {
		
		return sqlSelectForList("collpack.fourm.dao.FrReference.list", itemId);
	}
	

	public List<String> listUserId(String itemId) {
		return (List)sqlSelectForListOfObject("collpack.fourm.dao.FrReference.listUserId", itemId);
	}
	
	

	public List<String> listUserIdByDiscussionId(String discussionId) {
		return (List)sqlSelectForListOfObject("collpack.fourm.dao.FrReference.listUserIdByDiscussionId", discussionId);
	}

	@Deprecated
	public void update(FrReference object) {
	}

	@Deprecated
	public void remove(String id) {
	}

	public void remove(String itemId, String registerId) {
		
		FrReference frRecommend = new FrReference();
		frRecommend.setItemId(itemId);
		frRecommend.setRegisterId(registerId);
		
		sqlDelete("collpack.fourm.dao.FrReference.remove", frRecommend);
	}

	public void removebyItemId(String itemId) {
		sqlDelete("collpack.fourm.dao.FrReference.removebyItemId", itemId);
	}

	public void removeByDiscussionId(String discussionId) {
		sqlDelete("collpack.fourm.dao.FrReference.removeByDiscussionId", discussionId);
	}
	
	
	
}
