/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.workflow.admin.model.AdminActivity;
import com.lgcns.ikep4.workflow.admin.model.AdminActivitySearchCondition;

/**
 * 워크플로우 - 현황관리 - IKEP4_WF_ACTIVITY 
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminActivityDao.java 16245 2011-08-18 04:28:59Z giljae $
 */
public interface AdminActivityDao extends GenericDao<AdminActivity,String>{
	public List<AdminActivity> selectAll();
	public String create(AdminActivity object);
	public boolean exists(String id);
	public AdminActivity get(String id);
	public void remove(String id);
	public void update(AdminActivity object);
	/*
	 * 액티비티 리스트 조회
	 */
	public List<AdminActivity> listActivity(AdminActivitySearchCondition activitySearchCondition);
	/*
	 * 액티비티 리스트 조회건수
	 */
	public Integer listActivityCount(AdminActivitySearchCondition activitySearchCondition);
}
