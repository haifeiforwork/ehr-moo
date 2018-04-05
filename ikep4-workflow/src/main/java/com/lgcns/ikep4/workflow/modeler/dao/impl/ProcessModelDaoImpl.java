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

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.workflow.modeler.dao.ProcessModelDao;
import com.lgcns.ikep4.workflow.modeler.model.ProcessModel;


/**
 * ProcessModel 생성, 읽기, 수정, 삭제를 수행한다.
 * 
 * @author 이승민(lsm3174@built1.com)
 * @version $Id: ProcessModelDaoImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Repository("processModelDao")
public class ProcessModelDaoImpl extends GenericDaoSqlmap<ProcessModel, String> implements ProcessModelDao {

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ProcessModel get(String processId) {
		return (ProcessModel) sqlSelectForObject("workflow.modeler.dao.ProcessModel.select", processId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(String processId) {
		String count = (String) sqlSelectForObject("workflow.modeler.dao.ProcessModel.selectCount", processId);
		if (("1").equals(count))
			return true;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(ProcessModel processModel) {
		return (String) sqlInsert("workflow.modeler.dao.ProcessModel.insert", processModel).toString();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(ProcessModel processModel) {
		sqlUpdate("workflow.modeler.dao.ProcessModel.update", processModel);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(String processId) {
		sqlDelete("workflow.modeler.dao.ProcessModel.delete", processId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.modeler.dao.PartitionDao#selectAll()
	 */
	public List<ProcessModel> selectAll() {
		return sqlSelectForList("workflow.modeler.dao.ProcessModel.selectAll");
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.modeler.dao.PartitionDao#selectAll()
	 */
	public ProcessModel selectProcessModel(ProcessModel processModel) {
		return (ProcessModel) sqlSelectForObject("workflow.modeler.dao.ProcessModel.selectProcessModel", processModel);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public int deleteProcess(ProcessModel processModel) {
		return sqlDelete("workflow.modeler.dao.ProcessModel.deleteProcess", processModel);
	}

}
