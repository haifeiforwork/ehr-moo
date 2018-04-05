/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.modeler.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.workflow.modeler.dao.PartitionProcessDao;
import com.lgcns.ikep4.workflow.modeler.model.PartitionProcess;

/**
 * PartitionProcess 생성, 읽기, 수정, 삭제를 수행한다.
 *
 * @author 이승민(lsm3174@built1.com)
 * @version $Id: PartitionProcessDaoImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Repository("partitionProcessDao")
public class PartitionProcessDaoImpl extends GenericDaoSqlmap<PartitionProcess, String> implements PartitionProcessDao {

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public PartitionProcess get(String processId) {
		return (PartitionProcess) sqlSelectForObject("workflow.modeler.dao.PartitionProcess.select", processId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String partitionId) {
		String count = (String) sqlSelectForObject("workflow.modeler.dao.PartitionProcess.selectCount", partitionId);
		if ( ("1").equals(count) )
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(PartitionProcess partitionProcess) {
		return (String) sqlInsert("workflow.modeler.dao.PartitionProcess.insert", partitionProcess).toString();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(PartitionProcess partitionProcess) {
		sqlUpdate("workflow.modeler.dao.PartitionProcess.update", partitionProcess);
		
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String partitionId) {
		sqlDelete("workflow.modeler.dao.PartitionProcess.delete", partitionId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.modeler.dao.PartitionDao#selectAll()
	 */
	public List<PartitionProcess> selectAll() {
		return sqlSelectForList("workflow.modeler.dao.PartitionProcess.selectAll");
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public List<PartitionProcess> selectPartitionProcess(PartitionProcess partitionProcess) {
		return sqlSelectForList("workflow.modeler.dao.PartitionProcess.selectPartitionProcess", partitionProcess);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public int deletePartitionProcess(PartitionProcess partitionProcess) {
		return sqlDelete("workflow.modeler.dao.PartitionProcess.deletePartitionProcess", partitionProcess);
	}

	
}
