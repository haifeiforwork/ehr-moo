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
import com.lgcns.ikep4.support.user.code.dao.ClassCodeDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.ClassCode;
import com.lgcns.ikep4.support.user.code.service.ClassCodeService;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;


/**
 * 클래스 코드 관리 서비스 구현체
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: ClassCodeServiceImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Service("classCodeService")
@Transactional
public class ClassCodeServiceImpl extends GenericServiceImpl<ClassCode, String> implements ClassCodeService {

	@Autowired
	private ClassCodeDao classCodeDao;

	@Autowired
	private I18nMessageService i18nMessageService;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.ClassCodeService#list(com.lgcns
	 * .ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public SearchResult<ClassCode> list(AdminSearchCondition searchCondition) {

		Integer count = classCodeDao.selectCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<ClassCode> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ClassCode>(searchCondition);

		} else {

			List<ClassCode> classCodeList = classCodeDao.selectAll(searchCondition);
			searchResult = new SearchResult<ClassCode>(classCodeList, searchCondition);
		}

		return searchResult;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create
	 * (java.lang.Object)
	 */
	public String create(ClassCode classCode) {

		i18nMessageService.create(classCode.getI18nMessageList());
		classCodeDao.create(classCode);

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#update
	 * (java.lang.Object)
	 */
	public void update(ClassCode classCode) {

		i18nMessageService.update(classCode.getI18nMessageList());
		classCodeDao.update(classCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#delete
	 * (java.io.Serializable)
	 */
	public void delete(String id) {

		i18nMessageService.deleteMessagesByItemId(IKepConstant.ITEM_TYPE_CODE_PORTAL, id);
		classCodeDao.remove(id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#read(java
	 * .io.Serializable)
	 */
	public ClassCode read(String id) {

		return classCodeDao.get(id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#exists
	 * (java.io.Serializable)
	 */
	public boolean exists(String id) {

		return classCodeDao.exists(id);
	}

}
