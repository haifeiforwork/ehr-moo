/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletCategoryDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletCategory;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPortletCategoryService;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * 포틀릿 그룹 Service 구현 클래스
 * 
 * @author 임종상(malboru80@naver.com)
 * @version $Id: PortalPortletCategoryServiceImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Service("PortalPortletCategoryService")
public class PortalPortletCategoryServiceImpl extends GenericServiceImpl<PortalPortletCategory, String> implements
		PortalPortletCategoryService {

	@Autowired
	private PortalPortletCategoryDao portalPortletCategoryDao;

	@Autowired
	private IdgenService idgenService;
	
	/**
	 * 시스템별 포틀릿 카테고리 리스트
	 * @param systemCode 시스템 코드
	 * @return List<PortalPortletCategory> 포틀릿 카테고리 목록
	 */
	public List<PortalPortletCategory> listPortletCategory(String systemCode) {
		return portalPortletCategoryDao.listPortletCategory(systemCode);
	}
	
	/**
	 * 포틀릿 카테고리 전체 리스트 
	 * @return List<PortalPortletCategory> 포틀릿 카테고리 전체 목록
	 */
	public List<PortalPortletCategory> listPortletCategoryAll() {
		return portalPortletCategoryDao.listPortletCategoryAll();
	}
	
	/**
	 * 포틀릿 카테고리 전체 리스트
	 * @param searchCondition 검색조건 모델 클래스
	 * @return SearchResult<PortalPortletCategory> 포틀릿 카테고리 전체 목록
	 */
	public SearchResult<PortalPortletCategory> listBySearchCondition(AdminSearchCondition searchCondition) {
		
		Integer count = portalPortletCategoryDao.countBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<PortalPortletCategory> searchResult = null;
		
		if (searchCondition.isEmptyRecord()) {
			
			searchResult = new SearchResult<PortalPortletCategory>(searchCondition);
		} else {

			List<PortalPortletCategory> portalPortletCategoryList = portalPortletCategoryDao.listBySearchCondition(searchCondition);

			searchResult = new SearchResult<PortalPortletCategory>(portalPortletCategoryList, searchCondition);
		}
		
		return searchResult;
		
	}

	/**
	 * 포틀릿 카테고리 생성
	 * @param portalPortletCategory 포틀릿 카테고리 모델
	 * @return 포틀릿 카테고리 ID
	 */
	public String createPortletCategory(PortalPortletCategory portalPortletCategory) {
		portalPortletCategory.setPortletCategoryId(idgenService.getNextId());
		
		return portalPortletCategoryDao.createPortletCategory(portalPortletCategory);
	}

	/**
	 * 포틀릿 카테고리 조회
	 * @param portletCategoryId 포틀릿 카테고리 ID
	 * @return PortalPortletCategory 포틀릿 카테고리 정보
	 */
	public PortalPortletCategory readPortletCategory(String portletCategoryId) {
		PortalPortletCategory portalPortletCategory = portalPortletCategoryDao.getPortletCategory(portletCategoryId);

		return portalPortletCategory;
	}

	/**
	 * 포틀릿 카테고리 삭제
	 * @param portletCategoryId 포틀릿 카테고리 ID
	 */
	public void removePortletCategory(String portletCategoryId) {
		portalPortletCategoryDao.removePortletCategory(portletCategoryId);
	}

	/**
	 * 포틀릿 카테고리 수정
	 * @param portalPortletCategory 포틀릿 카테고리 모델
	 */
	public void updatePortletCategory(PortalPortletCategory portalPortletCategory) {
		portalPortletCategoryDao.updatePortletCategory(portalPortletCategory);
	}
}
