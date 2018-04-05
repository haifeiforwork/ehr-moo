/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.engine.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.workflow.engine.model.InstanceBean;

/**
 * @author 박철순(sniper28@naver.com)
 * @version $Id:
 */
public interface InstanceDao extends GenericDao<InstanceBean, String> {
	public void startProcess(InstanceBean instanceBean);
	
	public InstanceBean select(InstanceBean instanceBean);
	
	public void update(InstanceBean instanceBean);
	
	public void updateResult(InstanceBean instanceBean);
	
	public void updateKeyData(InstanceBean instanceBean);
	
	public void updateVarSchema(InstanceBean instanceBean);
	
	public void deleteInstance(InstanceBean instanceBean);
	
	
	
	
}
