/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.code.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.code.dao.WorkPlaceDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.WorkPlace;
import com.lgcns.ikep4.support.user.code.service.WorkPlaceService;


/**
 * 직위 코드 관리 서비스 구현체
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: WorkPlaceServiceImpl.java 16276 2011-08-18 07:09:07Z giljae $
 */
@Service("workPlaceService")
@Transactional
public class WorkPlaceServiceImpl extends GenericServiceImpl<WorkPlace, String> implements WorkPlaceService {

	@Autowired
	private WorkPlaceDao workPlaceDao;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create
	 * (java.lang.Object)
	 */
	public String create(WorkPlace workPlace) {

		String result = workPlaceDao.create(workPlace);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#exists
	 * (java.io.Serializable)
	 */
	public boolean exists(String code) {

		return workPlaceDao.exists(code);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#read(java
	 * .io.Serializable)
	 */
	public WorkPlace read(String workPlaceCode) {

		return workPlaceDao.get(workPlaceCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#delete
	 * (java.io.Serializable)
	 */
	public void delete(String workPlaceCode) {

		workPlaceDao.remove(workPlaceCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#update
	 * (java.lang.Object)
	 */
	public void update(WorkPlace workPlace) {

		workPlaceDao.update(workPlace);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.WorkPlaceService#list(com
	 * .lgcns.ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public SearchResult<WorkPlace> list(AdminSearchCondition searchCondition) {

		Integer count = workPlaceDao.selectCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<WorkPlace> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<WorkPlace>(searchCondition);

		} else {

			List<WorkPlace> workPlaceList = workPlaceDao.selectAll(searchCondition);
			searchResult = new SearchResult<WorkPlace>(workPlaceList, searchCondition);
		}

		return searchResult;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.WorkPlaceService#getMaxSortOrder
	 * ()
	 */
	public String getMaxSortOrder() {

		return workPlaceDao.getMaxSortOrder();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.WorkPlaceService#goUp(java
	 * .util.Map)
	 */
	public void goUp(Map<String, String> map) {

		WorkPlace upWorkPlace = workPlaceDao.goUp(map);
		String upSortOrder = upWorkPlace.getSortOrder();

		upWorkPlace.setSortOrder((String) map.get("sortOrder"));

		workPlaceDao.updateSortOrder(upWorkPlace);

		WorkPlace workPlace = new WorkPlace();
		workPlace.setWorkPlaceCode((String) map.get("workPlaceCode"));
		workPlace.setSortOrder(upSortOrder);

		workPlaceDao.updateSortOrder(workPlace);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.WorkPlaceService#goDown(java
	 * .util.Map)
	 */
	public void goDown(Map<String, String> map) {

		WorkPlace downWorkPlace = workPlaceDao.goDown(map);
		String downSortOrder = downWorkPlace.getSortOrder();

		downWorkPlace.setSortOrder((String) map.get("sortOrder"));

		workPlaceDao.updateSortOrder(downWorkPlace);

		WorkPlace workPlace = new WorkPlace();
		workPlace.setWorkPlaceCode((String) map.get("workPlaceCode"));
		workPlace.setSortOrder(downSortOrder);

		workPlaceDao.updateSortOrder(workPlace);
	}
	
	public List<WorkPlace> list(String localeCode) {

		return workPlaceDao.getAll(localeCode);
	}

}