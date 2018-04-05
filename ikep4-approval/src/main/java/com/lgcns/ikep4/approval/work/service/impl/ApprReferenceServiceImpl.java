/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.approval.work.dao.ApprReferenceDao;
import com.lgcns.ikep4.approval.work.model.ApprReference;
import com.lgcns.ikep4.approval.work.service.ApprReferenceService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;

/**
 * 검토요청 Service 구현
 *
 * @author 
 * @version $Id: ApprReferenceServiceImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Service("apprReferenceService")
public class ApprReferenceServiceImpl extends GenericServiceImpl<ApprReference, String> implements ApprReferenceService {

	@Autowired
	private ApprReferenceDao apprReferenceDao;

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.service.ApprReferenceService#remove(java.util.Map)
	 */
	public void remove(Map<String, String> map) {
		apprReferenceDao.remove(map);		
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.service.ApprReferenceService#updateRead(java.util.Map)
	 */
	public void updateRead(Map<String, String> map) {
		apprReferenceDao.updateRead(map);
		
	}
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#update(java.lang.Object)
	 */
	public void update(ApprReference object) {
		apprReferenceDao.update(object);
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.service.ApprReferenceService#list(java.lang.String)
	 */
	public List<ApprReference> list(String apprId) {
		List<ApprReference> list = (List<ApprReference>)this.apprReferenceDao.list(apprId);
		return list;
	}

	public boolean exists(Map<String, String> map) {
		return apprReferenceDao.exists(map);
	}

	public ApprReference get(Map<String, String> map) {
		return apprReferenceDao.get(map);
		
	}


}
