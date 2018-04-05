/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.modeler.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.workflow.modeler.model.ProcessModel;

/**
 * ProcessModel DAO
 *
 * @author 이승민(lsm3174@built1.com)
 * @version $Id: ProcessModelDao.java 16245 2011-08-18 04:28:59Z giljae $
 */
public interface ProcessModelDao extends GenericDao<ProcessModel, String> {
	/**
	 * 전체 ProcessModel 리스트
	 * 
	 * @return
	 */
	public List<ProcessModel> selectAll();
	public ProcessModel selectProcessModel(ProcessModel processModel);
	public int deleteProcess(ProcessModel processModel);
}
