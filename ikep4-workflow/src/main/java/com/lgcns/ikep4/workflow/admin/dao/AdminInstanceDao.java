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
import com.lgcns.ikep4.workflow.admin.model.AdminInstance;
import com.lgcns.ikep4.workflow.admin.model.AdminInstanceSearchCondition;

/**
 * 워크플로우 - 현황관리 - IKEP4_WF_INSTANCE
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminInstanceDao.java 16245 2011-08-18 04:28:59Z giljae $
 */
public interface AdminInstanceDao extends GenericDao<AdminInstance,String>{
	public List<AdminInstance> selectAll();
	public String create(AdminInstance object);
	public boolean exists(String id);
	public AdminInstance get(String id);
	public void remove(String id);
	public void update(AdminInstance object);
	
	/*
	 * 인스턴스 리스트 조회
	 */
	public List<AdminInstance> listInstance(AdminInstanceSearchCondition adminInstanceSearchCondition);
	/*
	 * 인스턴스 리스트 조회건수
	 */
	public Integer listInstanceCount(AdminInstanceSearchCondition adminInstanceSearchCondition);
	/*
	 * 인스턴스 상태 변경
	 */
	public Integer updateInstanceState(Map<String,Object> params);
	
	/*
	 * 인스턴스 현황 
	 */
	public Map<String,Object> instanceStateCount();
	
	/*
	 * 프로세스별 인스턴스  진행현황
	 */
	public List<String> listCurrentInstance();
	
	/*
	 * 프로세스별  인스턴스 누적현황
	*/
	public List<String> listAccumulateInstance(String searchCondition);
}
