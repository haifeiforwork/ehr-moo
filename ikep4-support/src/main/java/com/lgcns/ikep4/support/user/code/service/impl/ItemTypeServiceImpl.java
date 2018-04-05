/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.code.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.code.dao.ItemTypeDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.ItemType;
import com.lgcns.ikep4.support.user.code.service.ItemTypeService;


/**
 * 아이템타입 코드 관리 서비스 구현체
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: ItemTypeServiceImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Service("itemTypeService")
@Transactional
public class ItemTypeServiceImpl extends GenericServiceImpl<ItemType, String> implements ItemTypeService {

	@Autowired
	private ItemTypeDao itemTypeDao;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create
	 * (java.lang.Object)
	 */
	public String create(ItemType itemType) {

		itemTypeDao.create(itemType);

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#exists
	 * (java.io.Serializable)
	 */
	public boolean exists(String code) {

		return itemTypeDao.exists(code);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#delete
	 * (java.io.Serializable)
	 */
	public void delete(String code) {

		itemTypeDao.remove(code);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#read(java
	 * .io.Serializable)
	 */
	public ItemType read(String code) {

		return itemTypeDao.get(code);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#update
	 * (java.lang.Object)
	 */
	public void update(ItemType itemType) {

		itemTypeDao.update(itemType);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.ItemTypeService#list(com.lgcns
	 * .ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public SearchResult<ItemType> list(AdminSearchCondition searchCondition) {

		Integer count = itemTypeDao.selectCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<ItemType> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ItemType>(searchCondition);

		} else {

			List<ItemType> itemTypeList = itemTypeDao.selectAll(searchCondition);
			searchResult = new SearchResult<ItemType>(itemTypeList, searchCondition);
		}

		return searchResult;
	}

}
