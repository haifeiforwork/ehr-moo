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
import com.lgcns.ikep4.workflow.admin.model.AdminService;


/**
 * 워크플로우 - 현황관리 - 프로세스 관리 - 프로세스 상세화면
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminServiceDao.java 16245 2011-08-18 04:28:59Z giljae $
 */
public interface AdminServiceDao extends GenericDao<AdminService,String>{
	public List<AdminService> selectAll();
	public String create(AdminService object);
	public boolean exists(String id);
	public AdminService get(String id);
	public void remove(String id);
	public void update(AdminService object);
	/*
	 * 워크플로우 - 현황관리 - 프로세스 관리 - 프로세스 상세화면
	 */
	public AdminService listProcessDetail(Map<String,Object> params);
	/*
	 * 워크플로우 - 현황관리 - 프로세스 관리 - 프로세스 상세화면 적용일자 수정
	 */
	public Integer updateProcessApplyDate(Map<String,Object> params);
}
