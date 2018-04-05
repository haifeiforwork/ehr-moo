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

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.approval.work.dao.ApprUserLineDao;
import com.lgcns.ikep4.approval.work.dao.ApprUserLineSubDao;
import com.lgcns.ikep4.approval.work.model.ApprUserLine;
import com.lgcns.ikep4.approval.work.model.ApprUserLineSub;
import com.lgcns.ikep4.approval.work.service.ApprUserLineService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * 결재선 관리 Service 구현
 *
 * @author 
 * @version $Id$
 */
@Service("apprUserLineService")
public class ApprUserLineServiceImpl extends GenericServiceImpl<ApprUserLine, String> implements ApprUserLineService {

	@Autowired
	private ApprUserLineDao		apprUserLineDao;

	@Autowired
	private ApprUserLineSubDao	apprUserLineSubDao;
	
	@Autowired
	private IdgenService idgenService;
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#create(java.lang.Object)
	 */
	public String create(ApprUserLine object) {
		
		String userLineId = idgenService.getNextId();
		// User Set Line Id
		object.setUserLineId(userLineId);
		//object.setUserLineType(0);

		apprUserLineDao.create(object);	
		
		for(ApprUserLineSub apprUserLineSub : object.getApprUserLineSub()){
			apprUserLineSub.setUserLineId(userLineId);
			apprUserLineSubDao.create(apprUserLineSub);
		}
		return object.getUserLineId();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#delete(java.io.Serializable)
	 */
	public void delete(String userLineId) {
		apprUserLineDao.remove(userLineId);

	}
	/*public void delete(List<String> userLineIds) {
		for (int i = 0; i < userLineIds.size(); i++) {
			
			apprUserLineSubDao.remove(userLineIds.get(i));
			apprUserLineDao.remove(userLineIds.get(i));	
		}
	}*/
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
	public ApprUserLine read(String userLineId) {
		ApprUserLine apprUserLine = apprUserLineDao.get(userLineId);
		//intUserLine.setApprUserLineSub(apprUserLineSubdao.getUserLineSubList(defLineId));
		return apprUserLine;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#update(java.lang.Object)
	 */
	public void update(ApprUserLine object) {

		// User Set 결재선 정보 수정
		apprUserLineDao.update(object);
	}



	public List<ApprUserLine> listApprUserLineAll(String	userId) {
		List<ApprUserLine> list = (List<ApprUserLine>)this.apprUserLineDao.listApprUserLineAll(userId);  
		return list;
	}



	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.service.ApprUserLineService#listApprUserLine(java.util.Map)
	 */
	public List<ApprUserLine> listApprUserLine(Map<String, String> map) {
		List<ApprUserLine> list = (List<ApprUserLine>)this.apprUserLineDao.listApprUserLine(map);  
		return list;
	}


	
}
