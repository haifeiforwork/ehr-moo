/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.work.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.wfapproval.work.dao.ApFormPortletDao;
import com.lgcns.ikep4.wfapproval.work.model.ApFormPortlet;
import com.lgcns.ikep4.wfapproval.work.service.ApFormPortletService;


/**
 * 결재 양식 포틀릿 Service 구현
 * 
 * @author 박희진(neoheejin@naver.com)
 * @version $Id: ApFormPortletServiceImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Service("ApFormPortletService")
public class ApFormPortletServiceImpl extends GenericServiceImpl<ApFormPortlet, String> implements ApFormPortletService {

	@Autowired
	private ApFormPortletDao apFormPortletDao;
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.wfapproval.work.service.ApFormPortletService#listApFormPortlet(com.lgcns.ikep4.wfapproval.work.model.ApFormPortlet)
	 */
	public List<ApFormPortlet> listApFormPortlet(ApFormPortlet apFormPortlet) {
		
		List<ApFormPortlet> listResult = this.apFormPortletDao.listApFormPortlet(apFormPortlet);
		
		return listResult;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.wfapproval.work.service.ApFormPortletService#addApFormPortlet(com.lgcns.ikep4.wfapproval.work.model.ApFormPortlet)
	 */
	public void addApFormPortlet(ApFormPortlet apFormPortlet) {

		if(this.apFormPortletDao.exists(apFormPortlet)){
			this.apFormPortletDao.update(apFormPortlet);
		}else{
			this.apFormPortletDao.create(apFormPortlet);
		}
	}
}
