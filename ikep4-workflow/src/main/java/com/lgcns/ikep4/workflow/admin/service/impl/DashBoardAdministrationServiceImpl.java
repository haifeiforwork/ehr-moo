/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.workflow.admin.dao.AdminInstanceDao;
import com.lgcns.ikep4.workflow.admin.dao.AdminPartitionDao;
import com.lgcns.ikep4.workflow.admin.dao.AdminProcessDao;
import com.lgcns.ikep4.workflow.admin.model.AdminProcessDash;
import com.lgcns.ikep4.workflow.admin.service.DashBoardAdministrationService;

/**
 * 워크플로우 - 현황관리 - 데시보드 
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: DashBoardAdministrationServiceImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Service("dashBoardAdministrationService")
public class DashBoardAdministrationServiceImpl implements DashBoardAdministrationService{
	
	@Autowired
	private AdminPartitionDao adminPartitionDao;
	
	@Autowired
	private AdminProcessDao adminProcessDao;
	
	@Autowired
	private AdminInstanceDao adminInstanceDao;
	
	/*
	 * 파티션 전체 조회건수
	 */
	public Integer partitionCount(){
		return adminPartitionDao.partitionCount();
	}
	
	/*
	 * 프로세스 전체 조회건수
	 */
	public Integer processCount(){
		return adminProcessDao.processCount();
	}
	
	/*
	 * 인스턴스 현황 
	 */
	public Map<String,Object> instanceStateCount(){
		return adminInstanceDao.instanceStateCount();
	}
	
	/*
	 * 프로세스별 인스턴스 진행 현황
	 */
	public List<String> listCurrentInstance() {
		return adminInstanceDao.listCurrentInstance();
	}
	
	/*
	 * 프로세스별  인스턴스 누적현황
	*/
	public List<String> listAccumulateInstance(String searchCondition){
		return adminInstanceDao.listAccumulateInstance(searchCondition);
	}
	
	/*
	 * 프로세스별  인스턴스건수
	 */
	public List<AdminProcessDash> processInstanceCount(String searchCondition){
		return (List) adminProcessDao.processInstanceCount(searchCondition);
	}
}
