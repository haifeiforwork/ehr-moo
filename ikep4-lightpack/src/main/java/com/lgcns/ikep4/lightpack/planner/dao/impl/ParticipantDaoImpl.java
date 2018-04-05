/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.planner.dao.ParticipantDao;
import com.lgcns.ikep4.lightpack.planner.model.Participant;

/**
 * 일정 대상자 관리 DAO 구현
 *
 * @author 신용환(combinet@gmail.com)
 * @version $Id: ParticipantDaoImpl.java 17248 2012-01-20 00:34:22Z yruyo $
 */
@Repository(value = "participantDao")
public class ParticipantDaoImpl extends GenericDaoSqlmap<Participant, String> implements ParticipantDao {

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public Participant get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(Participant participant) {
		return (String) sqlInsert(NAMESPACE + ".insert", participant);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(Participant participant) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String scheduleId) {
		sqlDelete(NAMESPACE + ".delete", scheduleId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ParticipantDao#create(java.util.List)
	 */
	public void create(List<Participant> participants) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("items", participants);
		sqlInsert(NAMESPACE + ".insertList", param);
	}

	public void create(List<Participant> participantList, String scheduleId) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("items", participantList);
		param.put("scheduleId", scheduleId);
		sqlInsert(NAMESPACE + ".insertListWithScheduleId", param);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ParticipantDao#getList(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<Participant> getList(String scheduleId) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".selectList", scheduleId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.dao.ParticipantDao#updateAcceptInfo(java.util.Map)
	 */
	public void updateAcceptInfo(Map<String, String> params) {
		sqlUpdate(NAMESPACE + ".updateAcceptInfo", params);
	}

}
