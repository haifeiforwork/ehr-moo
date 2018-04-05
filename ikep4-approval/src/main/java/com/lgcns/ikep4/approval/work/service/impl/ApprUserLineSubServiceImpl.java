/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.approval.work.dao.ApprUserLineSubDao;
import com.lgcns.ikep4.approval.work.model.ApprUserLine;
import com.lgcns.ikep4.approval.work.model.ApprUserLineSub;
import com.lgcns.ikep4.approval.work.service.ApprUserLineSubService;

/**
 * TODO Javadoc주석작성
 *
 * @author 
 * @version $Id$
 */
@Service("apprUserLineSubService")
public class ApprUserLineSubServiceImpl extends GenericServiceImpl<ApprUserLineSub, String> implements ApprUserLineSubService {

	@Autowired
	private ApprUserLineSubDao	apprUserLineSubDao;
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#create(java.lang.Object)
	 */
	public String create(ApprUserLineSub arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#delete(java.io.Serializable)
	 */
	public void delete(String userLineId) {
		apprUserLineSubDao.remove(userLineId);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#exists(java.io.Serializable)
	 */
	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#read(java.io.Serializable)
	 */
	public ApprUserLineSub read(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#update(java.lang.Object)
	 */
	public void update(ApprUserLineSub arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.service.ApprUserLineSubService#listApprUserLineSub(java.lang.String)
	 */
	public List<ApprUserLineSub> listApprUserLineSub(String userLineId) {
		List<ApprUserLineSub> list = (List<ApprUserLineSub>)this.apprUserLineSubDao.listApprUserLineSub(userLineId);  
		return list;
	}

}
