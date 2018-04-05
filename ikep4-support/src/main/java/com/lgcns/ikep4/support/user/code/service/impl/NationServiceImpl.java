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

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.code.dao.NationDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.model.Nation;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.code.service.NationService;


/**
 * 국가 코드 관리 서비스 구현체
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: NationServiceImpl.java 16276 2011-08-18 07:09:07Z giljae $
 */
@Service("NationService")
@Transactional
public class NationServiceImpl extends GenericServiceImpl<Nation, String> implements NationService {

	@Autowired
	private NationDao nationDao;

	@Autowired
	private I18nMessageService i18nMessageService;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.NationService#list(com.lgcns
	 * .ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public SearchResult<Nation> list(AdminSearchCondition searchCondition) {

		Integer count = nationDao.selectCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Nation> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Nation>(searchCondition);

		} else {

			List<Nation> nationList = nationDao.selectAll(searchCondition);
			searchResult = new SearchResult<Nation>(nationList, searchCondition);
		}

		return searchResult;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create
	 * (java.lang.Object)
	 */
	public String create(Nation nation) {

		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(nation.getI18nMessageList(),
				IKepConstant.ITEM_TYPE_CODE_PORTAL, nation.getNationCode());

		nation.setI18nMessageList(i18nMessageList);

		i18nMessageService.create(i18nMessageList);
		nationDao.create(nation);

		return nation.getNationCode();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#update
	 * (java.lang.Object)
	 */
	public void update(Nation nation) {

		i18nMessageService.update(nation.getI18nMessageList());
		nationDao.update(nation);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#delete
	 * (java.io.Serializable)
	 */
	public void delete(String code) {

		i18nMessageService.deleteMessagesByItemId(IKepConstant.ITEM_TYPE_CODE_PORTAL, code);
		nationDao.remove(code);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#read(java
	 * .io.Serializable)
	 */
	public Nation read(String code) {

		return nationDao.get(code);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#exists
	 * (java.io.Serializable)
	 */
	public boolean exists(String code) {

		return nationDao.exists(code);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.NationService#listAll(java.
	 * lang.String)
	 */
	public List<Nation> listAll(String localeCode) {
		return nationDao.listAll(localeCode);
	}

}
