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
import com.lgcns.ikep4.workflow.modeler.dao.PartitionDao;
import com.lgcns.ikep4.workflow.modeler.model.Partition;

/**
 * Partition 생성, 읽기, 수정, 삭제를 수행한다.
 *
 * @author 이승민(lsm3174@built1.com)
 * @version $Id: PartitionDaoImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Repository("partitionDao")
public class PartitionDaoImpl extends GenericDaoSqlmap<Partition, String> implements PartitionDao {

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public Partition get(String partitionId) {
		return (Partition) sqlSelectForObject("workflow.modeler.dao.Partition.select", partitionId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String partitionId) {
		String count = (String) sqlSelectForObject("workflow.modeler.dao.Partition.selectCount", partitionId);
		if ( ("1").equals(count) )
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(Partition partition) {
		return (String) sqlInsert("workflow.modeler.dao.Partition.insert", partition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(Partition partition) {
		sqlUpdate("workflow.modeler.dao.Partition.update", partition);
		
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String partitionId) {
		sqlDelete("workflow.modeler.dao.Partition.delete", partitionId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.modeler.dao.PartitionDao#selectAll()
	 */
	public List<Partition> selectAll() {
		return sqlSelectForList("workflow.modeler.dao.Partition.selectAll");
	}

	
}
