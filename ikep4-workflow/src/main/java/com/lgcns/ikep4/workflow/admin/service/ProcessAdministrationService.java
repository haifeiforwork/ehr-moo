/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.workflow.admin.model.AdminActivity;
import com.lgcns.ikep4.workflow.admin.model.AdminActivitySearchCondition;
import com.lgcns.ikep4.workflow.admin.model.AdminPartition;
import com.lgcns.ikep4.workflow.admin.model.AdminProcess;
import com.lgcns.ikep4.workflow.admin.model.AdminProcessSearchCondition;
import com.lgcns.ikep4.workflow.admin.model.AdminService;

/**
 * 워크플로우 - 현황관리 - 프로세스 관리
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: ProcessAdministrationService.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Transactional
public interface ProcessAdministrationService{
	/*
	* 파티션 조회(ComboBox)
	*/ 
	public List<AdminPartition> listComboPartition();
	/*
	* 프로세스 조회(ComboBox)
	*/ 
	public List<String> listComboProcess(String partitionId);
	/*
	* 프로세스 리스트 조회
	*/
	public SearchResult<AdminProcess> listProcess(AdminProcessSearchCondition processSearchCondition);
	/*
	* 프로세스 상태 변경
	*/ 
	public Integer updateProcessState(Map<String,Object> params);
	/*
	 * 액티비티 리스트 조회
	*/ 
	public SearchResult<AdminActivity> listActivity(AdminActivitySearchCondition activitySearchCondition);
	/*
	 * 워크플로우 - 현황관리 - 프로세스 관리 - 프로세스 상세화면
	 */
	public AdminService listProcessDetail(Map<String,Object> params);
	/*
	 * 워크플로우 - 현황관리 - 프로세스 관리 - 프로세스 상세화면 적용일자 수정
	 */
	public Integer updateProcessApplyDate(Map<String,Object> params);
}	
	
	
