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

import com.lgcns.ikep4.collpack.forum.dao.FrFeedbackDao;
import com.lgcns.ikep4.collpack.forum.model.FrFeedback;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * TODO Javadoc주석작성updateOrderInit
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrFeedbackDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository("frFeedbackDao")
public class FrFeedbackDaoImpl extends GenericDaoSqlmap<FrFeedback, String> implements FrFeedbackDao {
	

	public String create(FrFeedback obj) {
		return (String) sqlInsert("collpack.fourm.dao.FrFeedback.create", obj);
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}
	
	public boolean exists(String itemId, String feedbackType, String registerId) {
		
		FrFeedback frFeedback = new FrFeedback();
		frFeedback.setItemId(itemId);
		frFeedback.setFeedbackType(feedbackType);
		frFeedback.setRegisterId(registerId);
		
		int count = (Integer) sqlSelectForObject("collpack.fourm.dao.FrFeedback.exists", frFeedback);
		
		boolean isResult = false;
		if (count > 0) {
			isResult = true;
		} else {
			isResult = false;
		}
		return isResult;
		
	}
	
	@Deprecated
	public FrFeedback get(String id) {
		return null;
	}
	
	
	public int getCountByUserIdAsType(String feedbackType, String registerId) {
		
		FrFeedback frFeedback = new FrFeedback();
		frFeedback.setFeedbackType(feedbackType);
		frFeedback.setRegisterId(registerId);
		
		return (Integer) sqlSelectForObject("collpack.fourm.dao.FrFeedback.getCountByUserIdAsType", frFeedback);
	}
	
	
	public List<String> listUserId(String itemId) {
		
		return (List)sqlSelectForListOfObject("collpack.fourm.dao.FrFeedback.listUserId", itemId);
	}
	
	public List<String> listUserIdByDiscussionId(String discussionId) {
		
		return (List)sqlSelectForListOfObject("collpack.fourm.dao.FrFeedback.listUserIdByDiscussionId", discussionId);
	}
	

	public FrFeedback get(String itemId, String feedbackType, String registerId) {
		
		FrFeedback frFeedback = new FrFeedback();
		frFeedback.setItemId(itemId);
		frFeedback.setFeedbackType(feedbackType);
		frFeedback.setRegisterId(registerId);
		
		return (FrFeedback) sqlSelectForObject("collpack.fourm.dao.FrFeedback.get", frFeedback);
	}
	
	public int getCount(String itemId, String feedbackType, String registerId) {
		
		FrFeedback frFeedback = new FrFeedback();
		frFeedback.setItemId(itemId);
		frFeedback.setFeedbackType(feedbackType);
		frFeedback.setRegisterId(registerId);
		
		return (Integer) sqlSelectForObject("collpack.fourm.dao.FrFeedback.getCount", frFeedback);
	}

	
	public List<FrFeedback> list(String itemId) {
		
		return sqlSelectForList("collpack.fourm.dao.FrFeedback.list", itemId);
	}


	@Deprecated
	public void update(FrFeedback obj) {
	}
	
	@Deprecated
	public void remove(String qnaId) {
	}
	
	public void remove(String itemId, String feedbackType, String registerId) {
		
		FrFeedback frFeedback = new FrFeedback();
		frFeedback.setItemId(itemId);
		frFeedback.setFeedbackType(feedbackType);
		frFeedback.setRegisterId(registerId);
		
		sqlDelete("collpack.fourm.dao.FrFeedback.remove", frFeedback);
	}

	public void removeByItemId(String itemId) {
		sqlDelete("collpack.fourm.dao.FrFeedback.removeByItemId", itemId);
		
	}

	public void removeByDiscussionId(String discussionId) {
		sqlDelete("collpack.fourm.dao.FrFeedback.removeByDiscussionId", discussionId);
	}
	
	
	
}
