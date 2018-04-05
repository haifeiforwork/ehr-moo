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

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.workflow.admin.dao.AdminInstanceDao;
import com.lgcns.ikep4.workflow.admin.dao.AdminPartitionDao;
import com.lgcns.ikep4.workflow.admin.dao.AdminProcessDao;
import com.lgcns.ikep4.workflow.admin.model.AdminInstance;
import com.lgcns.ikep4.workflow.admin.model.AdminInstanceSearchCondition;
import com.lgcns.ikep4.workflow.admin.model.AdminPartition;
import com.lgcns.ikep4.workflow.admin.service.InstanceAdministrationService;

/**
 * 워크플로우 - 현황관리 - 인스턴스 관리
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: InstanceAdministrationServiceImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Service("instanceAdministrationService")
public class InstanceAdministrationServiceImpl implements InstanceAdministrationService{
	
	@Autowired
	private AdminPartitionDao adminPartitionDao;
	
	@Autowired
	private AdminProcessDao adminProcessDao;
	
	@Autowired
	private AdminInstanceDao adminInstanceDao;
	
	/*
	* 파티션 조회(ComboBox)
	*/ 
	public List<AdminPartition> listComboPartition(){
		return adminPartitionDao.selectAll();
	}
	
	/*
	* 프로세스 조회(ComboBox)
	*/ 
	public List<String> listComboProcess(String partitionId){
		return adminProcessDao.listComboProcess(partitionId);
	}
	
	/*
	* 인스턴스 리스트 조회
	*/
	public SearchResult<AdminInstance> listInstance(AdminInstanceSearchCondition adminInstanceSearchCondition){
		Integer count = adminInstanceDao.listInstanceCount(adminInstanceSearchCondition);

		adminInstanceSearchCondition.terminateSearchCondition(count);  
		
		SearchResult<AdminInstance> instanceSearchResult = null;
		
		if(adminInstanceSearchCondition.isEmptyRecord()) {
			instanceSearchResult = new SearchResult<AdminInstance>(adminInstanceSearchCondition);
		}else{
			List<AdminInstance> listInstance = adminInstanceDao.listInstance(adminInstanceSearchCondition);
			instanceSearchResult = new SearchResult<AdminInstance>(listInstance, adminInstanceSearchCondition); 
		}  
		return instanceSearchResult;
	}
	
	/*
	* 인스턴스 상태 변경
	*/ 
	public Integer updateInstanceState(Map<String,Object> params){
		return adminInstanceDao.updateInstanceState(params);
	}
}
