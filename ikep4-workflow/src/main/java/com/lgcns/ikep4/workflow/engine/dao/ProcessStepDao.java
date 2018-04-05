/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.engine.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.workflow.engine.model.ProcessReqBean;
import com.lgcns.ikep4.workflow.engine.model.ProcessStepBean;

/**
 * @author 박철순(sniper28@naver.com)
 * @version $Id:
 */
public interface ProcessStepDao extends GenericDao<ProcessStepBean, String> {
	
	
	public List<ProcessStepBean> selectStart(ProcessReqBean processReqBean);
	
	public List<ProcessStepBean> select(ProcessReqBean processReqBean);
}
