/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.role.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.role.dao.RoleTypeDao;
import com.lgcns.ikep4.support.user.role.model.RoleType;
import com.lgcns.ikep4.support.user.role.service.RoleTypeService;


/**
 * 롤타입 Service 구현
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: RoleTypeServiceImpl.java 16276 2011-08-18 07:09:07Z giljae $
 */
@Service("roleTypeService")
@Transactional
public class RoleTypeServiceImpl extends GenericServiceImpl<RoleType, String> implements RoleTypeService {

	@Autowired
	private RoleTypeDao roleTypeDao;

	@Autowired
	private I18nMessageService i18nMessageService;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.role.service.RoleTypeService#list(com.lgcns
	 * .ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public SearchResult<RoleType> list(AdminSearchCondition searchCondition) {

		Integer count = roleTypeDao.selectCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<RoleType> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<RoleType>(searchCondition);

		} else {

			List<RoleType> roleTypeList = roleTypeDao.selectAll(searchCondition);
			searchResult = new SearchResult<RoleType>(roleTypeList, searchCondition);
		}

		return searchResult;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create
	 * (java.lang.Object)
	 */
	public String create(RoleType roleType) {

		i18nMessageService.create(roleType.getI18nMessageList());
		roleTypeDao.create(roleType);

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#update
	 * (java.lang.Object)
	 */
	public void update(RoleType roleType) {

		i18nMessageService.update(roleType.getI18nMessageList());
		roleTypeDao.update(roleType);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#delete
	 * (java.io.Serializable)
	 */
	public void delete(String id) {

		i18nMessageService.deleteMessagesByItemId(IKepConstant.ITEM_TYPE_CODE_PORTAL, id);
		roleTypeDao.remove(id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#read(java
	 * .io.Serializable)
	 */
	public RoleType read(String id) {

		return roleTypeDao.get(id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#exists
	 * (java.io.Serializable)
	 */
	public boolean exists(String id) {

		return roleTypeDao.exists(id);
	}
}