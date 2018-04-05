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
import com.lgcns.ikep4.workflow.admin.model.AdminProcess;
import com.lgcns.ikep4.workflow.admin.model.AdminProcessDash;
import com.lgcns.ikep4.workflow.admin.model.AdminProcessSearchCondition;

/**
 * 워크플로우 - 현황관리 - IKEP4_WF_PROCESS
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminProcessDao.java 16245 2011-08-18 04:28:59Z giljae $
 */
public interface AdminProcessDao extends GenericDao<AdminProcess,String>{
	public List<AdminProcess> selectAll();
	public String create(AdminProcess object);
	public String createModel(Map<String,Object> params);
	public boolean exists(String id);
	public AdminProcess get(String id);
	public void remove(String id);
	public void update(AdminProcess object);
	/*
	 * 프로세스 조회(ComboBox)
	*/
	public List<String> listComboProcess(String partitionId);
	/*
	 * 프로세스 리스트 조회
	 */
	public List<AdminProcess> listProcess(AdminProcessSearchCondition processSearchCondition);
	/*
	 * 프로세스 리스트 조회건수
	 */
	public Integer listProcessCount(AdminProcessSearchCondition processSearchCondition);
	/*
	 * 프로세스 상태 변경
	 */
	public Integer updateProcessState(Map<String,Object> params);
	/*
	 * 프로세스 전체 조회건수
	 */
	public Integer processCount();
	/*
	 * 프로세스별  인스턴스건수  
	 */
	public List<AdminProcessDash> processInstanceCount(String searchCondition);
	//public String processInstanceCount(String searchCondition);
}
