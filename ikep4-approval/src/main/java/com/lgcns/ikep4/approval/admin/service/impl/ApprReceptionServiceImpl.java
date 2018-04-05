/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.approval.admin.dao.ApprReceptionDao;
import com.lgcns.ikep4.approval.admin.model.ApprReception;
import com.lgcns.ikep4.approval.admin.service.ApprReceptionService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;

/**
 * 접수 담당자 Service 구현
 *
 * @author jeehye
 * @version $Id: ApprReceptionServiceImpl.java $
 */
@Service("apprReceptionService")
public class ApprReceptionServiceImpl extends GenericServiceImpl<ApprReception, String> implements ApprReceptionService {

	@Autowired
	private ApprReceptionDao apprReceptionDao;

	/**
	 *  접수 담당자 저장
	 * @param apprReception 모델
	 * @return ID
	 */
	public void createApprReceptionSave(ApprReception apprReception) {  
		
		this.apprReceptionDao.deleteApprReception(apprReception.getGroupId());
		
		String [] groupUserIds = StringUtils.split(apprReception.getSelectedGroupUserList(), ",");
		
		if(groupUserIds != null && groupUserIds.length > 0) {
			for(int i=0; i<groupUserIds.length; i++){
				
				apprReception.setReceptionistId(groupUserIds[i]);
				
				this.apprReceptionDao.createApprReceptionSave(apprReception);
		    }
		}
	}
	
	/**
	 * 접수 담당자 목록
	 * @param ApprReception 모델
	 * @return  List
	 */
	public List<ApprReception> listByReception(String groupId){
		
		List<ApprReception> list = (List<ApprReception>)this.apprReceptionDao.listByReception(groupId);  
		return list;
	}
	
	/**
	 * 검토자여부
	 * @param map
	 * @return boolean
	 */
	public boolean existReceptionUser(Map<String, String> map) {  
		
		return this.apprReceptionDao.existReceptionUser(map);
	
	}
	
}
