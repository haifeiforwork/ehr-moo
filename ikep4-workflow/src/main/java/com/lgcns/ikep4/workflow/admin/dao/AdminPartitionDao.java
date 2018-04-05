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
import com.lgcns.ikep4.workflow.admin.model.AdminPartition;

/**
 * 워크플로우 - 현황관리 - IKEP4_WF_PARTITION
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminPartitionDao.java 16245 2011-08-18 04:28:59Z giljae $
 */
public interface AdminPartitionDao extends GenericDao<AdminPartition,String>{
	public List<AdminPartition> selectAll();
	public String create(AdminPartition object);
	public boolean exists(String id);
	public AdminPartition get(String id);
	public void remove(String id);
	public void update(AdminPartition object);
	/*
	 * 파티션 전체 조회건수
	 */
	public Integer partitionCount();
}
