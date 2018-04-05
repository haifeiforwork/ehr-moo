/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.workplace.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.workflow.engine.model.DelegateBean;
import com.lgcns.ikep4.workflow.engine.model.WorkItemBean;
import com.lgcns.ikep4.workflow.workplace.dao.PersonalSetDao;
import com.lgcns.ikep4.workflow.workplace.service.PersonalSetService;


/**
 * WorkPlace List Impl 
 * 
 * @author 이재경
 * @version $Id: PersonalSetServiceImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Service("personalSetService")
public class PersonalSetServiceImpl extends GenericServiceImpl<WorkItemBean, String> implements PersonalSetService{
	
	@Autowired
	private PersonalSetDao personalSetDao;	
	
	@Transactional(readOnly = true)
	public int delegateCreate(DelegateBean delegateBean) { 
		return this.personalSetDao.delegateCreate(delegateBean);
	}
	
	@Transactional(readOnly = true)
	public DelegateBean delegateDetail(DelegateBean delegateBean) { 
		return (DelegateBean)this.personalSetDao.delegateDetail(delegateBean);
	}
	
	@Transactional(readOnly = true)
	public int delegateUpdate(DelegateBean delegateBean) { 
		return this.personalSetDao.delegateUpdate(delegateBean);
	}
}
