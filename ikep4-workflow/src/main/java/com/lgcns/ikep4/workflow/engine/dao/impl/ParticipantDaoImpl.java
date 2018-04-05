/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.engine.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.workflow.engine.dao.ParticipantDao;
import com.lgcns.ikep4.workflow.engine.model.ParticipantBean;


/**
 * TODO Javadoc주석작성
 *
 * @author 박철순(sniper28@naver.com)
 * @version $Id: WorkListDaoImpl.java 오후 3:50:45 
 */
@Repository
public class ParticipantDaoImpl extends GenericDaoSqlmap<ParticipantBean, String> implements ParticipantDao {

	public String create(ParticipantBean object) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public ParticipantBean get(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void insert(ParticipantBean participantBean) {
		// TODO Auto-generated method stub
		sqlInsert("com.lgcns.ikep4.workflow.engine.model.ParticipantBean.insert", participantBean);
		
	}  
	
	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}
	
	public void update(ParticipantBean object) {
		// TODO Auto-generated method stub
		
	}
	

}