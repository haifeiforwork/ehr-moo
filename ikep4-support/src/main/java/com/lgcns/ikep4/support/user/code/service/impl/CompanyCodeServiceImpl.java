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
import com.lgcns.ikep4.support.user.code.dao.CompanyCodeDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.CompanyCode;
import com.lgcns.ikep4.support.user.code.service.CompanyCodeService;


/**
 * 직위 코드 관리 서비스 구현체
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: CompanyCodeServiceImpl.java 16276 2011-08-18 07:09:07Z giljae $
 */
@Service("companyCodeService")
@Transactional
public class CompanyCodeServiceImpl extends GenericServiceImpl<CompanyCode, String> implements CompanyCodeService {

	@Autowired
	private CompanyCodeDao companyCodeDao;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create
	 * (java.lang.Object)
	 */
	public String create(CompanyCode companyCode) {

		String result = companyCodeDao.create(companyCode);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#exists
	 * (java.io.Serializable)
	 */
	public boolean exists(String code) {

		return companyCodeDao.exists(code);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#read(java
	 * .io.Serializable)
	 */
	public CompanyCode read(String companyCodeCode) {

		return companyCodeDao.get(companyCodeCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#delete
	 * (java.io.Serializable)
	 */
	public void delete(String companyCodeCode) {

		companyCodeDao.remove(companyCodeCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#update
	 * (java.lang.Object)
	 */
	public void update(CompanyCode companyCode) {

		companyCodeDao.update(companyCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.CompanyCodeService#list(com
	 * .lgcns.ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public SearchResult<CompanyCode> list(AdminSearchCondition searchCondition) {

		Integer count = companyCodeDao.selectCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<CompanyCode> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<CompanyCode>(searchCondition);

		} else {

			List<CompanyCode> companyCodeList = companyCodeDao.selectAll(searchCondition);
			searchResult = new SearchResult<CompanyCode>(companyCodeList, searchCondition);
		}

		return searchResult;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.CompanyCodeService#getMaxSortOrder
	 * ()
	 */
	public String getMaxSortOrder() {

		return companyCodeDao.getMaxSortOrder();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.CompanyCodeService#goUp(java
	 * .util.Map)
	 */
	public void goUp(Map<String, String> map) {

		CompanyCode upCompanyCode = companyCodeDao.goUp(map);
		String upSortOrder = upCompanyCode.getSortOrder();

		upCompanyCode.setSortOrder((String) map.get("sortOrder"));

		companyCodeDao.updateSortOrder(upCompanyCode);

		CompanyCode companyCode = new CompanyCode();
		companyCode.setCompanyCode((String) map.get("companyCodeCode"));
		companyCode.setSortOrder(upSortOrder);

		companyCodeDao.updateSortOrder(companyCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.CompanyCodeService#goDown(java
	 * .util.Map)
	 */
	public void goDown(Map<String, String> map) {

		CompanyCode downCompanyCode = companyCodeDao.goDown(map);
		String downSortOrder = downCompanyCode.getSortOrder();

		downCompanyCode.setSortOrder((String) map.get("sortOrder"));

		companyCodeDao.updateSortOrder(downCompanyCode);

		CompanyCode companyCode = new CompanyCode();
		companyCode.setCompanyCode((String) map.get("companyCodeCode"));
		companyCode.setSortOrder(downSortOrder);

		companyCodeDao.updateSortOrder(companyCode);
	}
	
	public List<CompanyCode> list(String localeCode) {

		return companyCodeDao.getAll(localeCode);
	}

}