/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.approval.admin.dao.ApprSystemDao;
import com.lgcns.ikep4.approval.admin.model.ApprSystem;
import com.lgcns.ikep4.approval.admin.service.ApprSystemService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;

import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;



/**
 * 결재 시스템 관리 서비스 구현체
 * 
 * @author 
 * @version $Id: ApprSystemServiceImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Service("apprSystemService")
public class ApprSystemServiceImpl extends GenericServiceImpl<ApprSystem, String> implements ApprSystemService {

	@Autowired
	private ApprSystemDao apprSystemDao;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create
	 * (java.lang.Object)
	 */
	public String create(ApprSystem apprSystem) {

		String result = apprSystemDao.create(apprSystem);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#exists
	 * (java.io.Serializable)
	 */
	public boolean exists(String systemId) {

		return apprSystemDao.exists(systemId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#read(java
	 * .io.Serializable)
	 */
	public ApprSystem read(String systemId) {

		return apprSystemDao.get(systemId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#delete
	 * (java.io.Serializable)
	 */
	public void delete(String systemId) {

		apprSystemDao.remove(systemId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#update
	 * (java.lang.Object)
	 */
	public void update(ApprSystem apprSystem) {

		apprSystemDao.update(apprSystem);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.service.ApprSystemService#list(com
	 * .lgcns.ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public SearchResult<ApprSystem> list(AdminSearchCondition searchCondition) {

		Integer count = apprSystemDao.selectCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<ApprSystem> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ApprSystem>(searchCondition);

		} else {

			List<ApprSystem> apprSystemList = apprSystemDao.selectAll(searchCondition);

			List<ApprSystem> returnList = new ArrayList<ApprSystem>();
			for (ApprSystem apprSystem : apprSystemList) {
				if (!searchCondition.getUserLocaleCode().equals("ko")) {
					apprSystem.setSystemName(apprSystem.getSystemEnName());
				}
				returnList.add(apprSystem);
			}

			searchResult = new SearchResult<ApprSystem>(returnList, searchCondition);
		}

		return searchResult;
	}

	public List<ApprSystem> selectList(String portalId) {		
		List<ApprSystem> apprSystemList = apprSystemDao.selectList(portalId);
		return apprSystemList;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.ApprSystemService#getMaxSortOrder
	 * ()
	 */
	public Integer getMaxSystemOrder(String portalId) {

		return apprSystemDao.getMaxSystemOrder(portalId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.service.ApprSystemService#goUp(java
	 * .util.Map)
	 */
	public void goUp(Map<String, String> map) {

		ApprSystem upApprSystem = apprSystemDao.goUp(map);
		int upSystemOrder = upApprSystem.getSystemOrder();

		upApprSystem.setSystemOrder(Integer.parseInt(map.get("systemOrder")));

		apprSystemDao.updateSystemOrder(upApprSystem);

		ApprSystem apprSystem = new ApprSystem();
		apprSystem.setSystemId((String) map.get("systemId"));
		apprSystem.setSystemOrder(upSystemOrder);

		apprSystemDao.updateSystemOrder(apprSystem);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.service.ApprSystemService#goDown(java
	 * .util.Map)
	 */
	public void goDown(Map<String, String> map) {

		ApprSystem downApprSystem = apprSystemDao.goDown(map);
		int downSystemOrder = downApprSystem.getSystemOrder();

		downApprSystem.setSystemOrder(Integer.parseInt(map.get("systemOrder")));

		apprSystemDao.updateSystemOrder(downApprSystem);

		ApprSystem ApprSystem = new ApprSystem();
		ApprSystem.setSystemId((String) map.get("systemId"));
		ApprSystem.setSystemOrder(downSystemOrder);

		apprSystemDao.updateSystemOrder(ApprSystem);
	}

}