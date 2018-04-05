/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.workflow.admin.model.AdminParticipantSearchCondition;
import com.lgcns.ikep4.workflow.admin.model.AdminParticipants;

/**
 * 참여자 정보 조회
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminParticipantsDao.java 16245 2011-08-18 04:28:59Z giljae $
 */
public interface AdminParticipantsDao extends GenericDao<AdminParticipants,String>{
	public List<AdminParticipants> selectAll();
	public String create(AdminParticipants object);
	public boolean exists(String id);
	public AdminParticipants get(String id);
	public void remove(String id);
	public void update(AdminParticipants object);
	public String insertRoleType(Map<String,Object> params);
	/*
	 * 롤 조회
	 */
	public List<AdminParticipants> listRole(AdminParticipantSearchCondition adminParticipantSearchCondition);
	/*
	 * 롤 조회 건수
	 */
	public Integer listRoleCount(AdminParticipantSearchCondition adminParticipantSearchCondition);
}
