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
import com.lgcns.ikep4.support.user.code.dao.OperationCodeDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.OperationCode;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.code.service.OperationCodeService;


/**
 * OPERATION 코드 관리 서비스 구현체
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: OperationCodeServiceImpl.java 7843 2011-04-26 06:56:48Z yangsae
 *          $
 */
@Service("operationCodeService")
@Transactional
public class OperationCodeServiceImpl extends GenericServiceImpl<OperationCode, String> implements OperationCodeService {

	@Autowired
	private OperationCodeDao operationCodeDao;

	@Autowired
	private I18nMessageService i18nMessageService;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.OperationCodeService#list(com
	 * .lgcns.ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public SearchResult<OperationCode> list(AdminSearchCondition searchCondition) {

		Integer count = operationCodeDao.selectCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<OperationCode> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<OperationCode>(searchCondition);

		} else {

			List<OperationCode> operationCodeList = operationCodeDao.selectAll(searchCondition);
			searchResult = new SearchResult<OperationCode>(operationCodeList, searchCondition);
		}

		return searchResult;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create
	 * (java.lang.Object)
	 */
	public String create(OperationCode operationCode) {

		i18nMessageService.create(operationCode.getI18nMessageList());
		operationCodeDao.create(operationCode);

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#update
	 * (java.lang.Object)
	 */
	public void update(OperationCode operationCode) {

		i18nMessageService.update(operationCode.getI18nMessageList());
		operationCodeDao.update(operationCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#delete
	 * (java.io.Serializable)
	 */
	public void delete(String id) {

		i18nMessageService.deleteMessagesByItemId(IKepConstant.ITEM_TYPE_CODE_PORTAL, id);
		operationCodeDao.remove(id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#read(java
	 * .io.Serializable)
	 */
	public OperationCode read(String id) {

		return operationCodeDao.get(id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#exists
	 * (java.io.Serializable)
	 */
	public boolean exists(String id) {

		return operationCodeDao.exists(id);
	}
}
