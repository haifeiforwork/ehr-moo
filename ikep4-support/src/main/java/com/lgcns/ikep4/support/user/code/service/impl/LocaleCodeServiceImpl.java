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

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.code.dao.LocaleCodeDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.LocaleCode;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.code.service.LocaleCodeService;


/**
 * 로케일 코드 관리 서비스 구현체
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: LocaleCodeServiceImpl.java 17313 2012-02-08 00:17:29Z yruyo $
 */
@Service("localeCodeService")
@Transactional
public class LocaleCodeServiceImpl extends GenericServiceImpl<LocaleCode, String> implements LocaleCodeService {

	@Autowired
	private LocaleCodeDao localeCodeDao;

	@Autowired
	private I18nMessageService i18nMessageService;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.LocaleCodeService#list(com.
	 * lgcns.ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public SearchResult<LocaleCode> list(AdminSearchCondition searchCondition) {

		Integer count = localeCodeDao.selectCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<LocaleCode> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<LocaleCode>(searchCondition);

		} else {

			List<LocaleCode> localeCodeList = localeCodeDao.selectAll(searchCondition);
			searchResult = new SearchResult<LocaleCode>(localeCodeList, searchCondition);
		}

		return searchResult;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create
	 * (java.lang.Object)
	 */
	public String create(LocaleCode localeCode) {

		i18nMessageService.create(localeCode.getI18nMessageList());
		localeCodeDao.create(localeCode);

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#exists
	 * (java.io.Serializable)
	 */
	public boolean exists(String code) {

		return localeCodeDao.exists(code);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#read(java
	 * .io.Serializable)
	 */
	public LocaleCode read(String code) {

		return localeCodeDao.get(code);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.LocaleCodeService#remove(java
	 * .lang.String)
	 */
	public void remove(String code) {

		i18nMessageService.deleteMessagesByItemId(IKepConstant.ITEM_TYPE_CODE_PORTAL, code);
		localeCodeDao.remove(code);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#update
	 * (java.lang.Object)
	 */
	public void update(LocaleCode localeCode) {

		i18nMessageService.update(localeCode.getI18nMessageList());
		localeCodeDao.update(localeCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.LocaleCodeService#getMaxSortOrder
	 * ()
	 */
	public String getMaxSortOrder() {

		return localeCodeDao.getMaxSortOrder();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.LocaleCodeService#goUp(java
	 * .util.Map)
	 */
	public void goUp(Map<String, String> map) {

		LocaleCode upLocaleCode = localeCodeDao.goUp(map);
		String upSortOrder = upLocaleCode.getSortOrder();

		upLocaleCode.setSortOrder((String) map.get("sortOrder"));

		localeCodeDao.updateSortOrder(upLocaleCode);

		LocaleCode localeCode = new LocaleCode();
		localeCode.setLocaleCode((String) map.get("localeCode"));
		localeCode.setSortOrder(upSortOrder);

		localeCodeDao.updateSortOrder(localeCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.LocaleCodeService#goDown(java
	 * .util.Map)
	 */
	public void goDown(Map<String, String> map) {

		LocaleCode downLocaleCode = localeCodeDao.goDown(map);
		String downSortOrder = downLocaleCode.getSortOrder();

		downLocaleCode.setSortOrder((String) map.get("sortOrder"));

		localeCodeDao.updateSortOrder(downLocaleCode);

		LocaleCode localeCode = new LocaleCode();
		localeCode.setLocaleCode((String) map.get("localeCode"));
		localeCode.setSortOrder(downSortOrder);

		localeCodeDao.updateSortOrder(localeCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.LocaleCodeService#list(java
	 * .lang.String)
	 */
	public List<LocaleCode> list(String localeCode) {

		return localeCodeDao.getAll(localeCode);
	}
	
	public LocaleCode localeInfo(Map<String, String> locale) {
		return localeCodeDao.localeInfo(locale);
	}

}
