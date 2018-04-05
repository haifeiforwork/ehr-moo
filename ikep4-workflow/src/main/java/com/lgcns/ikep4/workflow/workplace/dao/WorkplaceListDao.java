/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.workplace.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.workflow.engine.model.ProcessBean;
import com.lgcns.ikep4.workflow.engine.model.WorkItemBean;
import com.lgcns.ikep4.workflow.workplace.search.WorkplaceSearchCondition;


/**
 * TODO Javadoc주석작성
 *
 * @author 이재경
 * @version $Id: WorkplaceListDao.java 16245 2011-08-18 04:28:59Z giljae $
 */
public interface WorkplaceListDao extends GenericDao<WorkItemBean, String> {
	
	List<?> listBySearchCondition(WorkplaceSearchCondition workplaceSearchCondition);
	
	Integer countBySearchCondition(WorkplaceSearchCondition workplaceSearchCondition); 
	
	ProcessBean readProcInfo(ProcessBean processBean);
}