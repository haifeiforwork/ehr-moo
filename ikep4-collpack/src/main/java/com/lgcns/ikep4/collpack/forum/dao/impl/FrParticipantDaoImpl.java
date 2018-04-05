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

import com.lgcns.ikep4.collpack.forum.dao.FrParticipantDao;
import com.lgcns.ikep4.collpack.forum.model.FrParticipant;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * TODO Javadoc주석작성updateOrderInit
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrParticipantDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository("frParticipantDao")
public class FrParticipantDaoImpl extends GenericDaoSqlmap<FrParticipant, String> implements FrParticipantDao {
	

	public String create(FrParticipant obj) {
		return null;
	}
	

	public void create(String discussionId, String participationType, String registerId) {
		
		FrParticipant frParticipant = new FrParticipant();
		
		frParticipant.setDiscussionId(discussionId);
		frParticipant.setParticipationType(participationType);
		frParticipant.setRegisterId(registerId);
		
		sqlInsert("collpack.fourm.dao.FrParticipant.create", frParticipant);
	}



	@Deprecated
	public boolean exists(String id) {
		return false;
	}
	
	public boolean exists(String discussionId,  String participationType, String registerId) {
		
		FrParticipant frParticipant = new FrParticipant();
		
		frParticipant.setDiscussionId(discussionId);
		frParticipant.setParticipationType(participationType);
		frParticipant.setRegisterId(registerId);
		
		int count = (Integer) sqlSelectForObject("collpack.fourm.dao.FrParticipant.exists", frParticipant);
		
		boolean isResult = false;
		if (count > 0) {
			isResult = true;
		} else {
			isResult = false;
		}
		return isResult;
	}
	
	@Deprecated
	public FrParticipant get(String id) {
		return null;
	}
	

	public FrParticipant get(String discussionId, String participationType, String registerId) {
		
		FrParticipant frParticipant = new FrParticipant();
		
		frParticipant.setDiscussionId(discussionId);
		frParticipant.setParticipationType(participationType);
		frParticipant.setRegisterId(registerId);
		
		return (FrParticipant) sqlSelectForObject("collpack.fourm.dao.FrParticipant.get", frParticipant);
	}

	public List<FrParticipant> list(FrSearch frSearch) {
		return sqlSelectForList("collpack.fourm.dao.FrParticipant.list", frSearch);
	}

	public int getCountList(FrSearch frSearch) {
		return (Integer) sqlSelectForObject("collpack.fourm.dao.FrParticipant.getCountList", frSearch);
	}

	@Deprecated
	public void update(FrParticipant obj) {
	}
	
	@Deprecated
	public void remove(String id) {
	}
	
	public void remove(String discussionId, String participationType, String registerId) {
		
		FrParticipant frParticipant = new FrParticipant();
		
		frParticipant.setDiscussionId(discussionId);
		frParticipant.setParticipationType(participationType);
		frParticipant.setRegisterId(registerId);
		
		sqlDelete("collpack.fourm.dao.FrParticipant.remove", frParticipant);
		
	}

	public void removeByDiscussion(String discussionId) {
		sqlDelete("collpack.fourm.dao.FrParticipant.removeByDiscussion", discussionId);
		
	}
	
	
	
}
