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
import com.lgcns.ikep4.portal.admin.screen.dao.PortalLayoutDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalLayout;
import com.lgcns.ikep4.portal.admin.screen.service.PortalLayoutService;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * 레이아웃 Service 구현 클래스
 *
 * @author 임종상(malboru80@naver.com)
 * @version $Id: PortalLayoutServiceImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Service("portalLayoutService")
public class PortalLayoutServiceImpl extends GenericServiceImpl<PortalLayout, String> implements PortalLayoutService {

	@Autowired
	private PortalLayoutDao portalLayoutDao;
	
	@Autowired
	private IdgenService idgenService;
	
	/**
	 * 레이아웃 리스트 조회
	 * @return List<PortalLayout> 레이아웃 리스트
	 */
	public List<PortalLayout> listLayout() {
		return portalLayoutDao.listLayout();
	}
	
	/**
	 * 레이아웃 리스트
	 * @param searchCondition 검색조건 모델 클래스
	 * @return SearchResult<PortalTheme> 테마 목록
	 */
	public SearchResult<PortalLayout> listBySearchCondition(AdminSearchCondition searchCondition) {
		
		Integer count = portalLayoutDao.countBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<PortalLayout> searchResult = null;
		
		if (searchCondition.isEmptyRecord()) {
			
			searchResult = new SearchResult<PortalLayout>(searchCondition);
		} else {

			List<PortalLayout> portalLayoutList = portalLayoutDao.listBySearchCondition(searchCondition);

			searchResult = new SearchResult<PortalLayout>(portalLayoutList, searchCondition);
		}
		
		return searchResult;
		
	}
	
	/**
	 * 레이아웃 등록
	 * @param portalLayout 레이아웃 모델
	 * @return 레이아웃 ID
	 */
	public String createLayout(PortalLayout portalLayout) {
		portalLayout.setLayoutId(idgenService.getNextId());
		
		return portalLayoutDao.createLayout(portalLayout);
	}
	
	/**
	 * 레이아웃 조회
	 * @param layoutId 레이아웃 ID
	 * @return PortalLayout 레이아웃 정보
	 */
	public PortalLayout readLayout(String layoutId) {
		PortalLayout portalLayout = portalLayoutDao.getLayout(layoutId);
		
		return portalLayout;
	}

	/**
	 * 레이아웃 삭제
	 * @param layoutId 레이아웃 ID
	 */
	public void removeLayout(String layoutId) {
		portalLayoutDao.removeLayout(layoutId);
	}

	/**
	 * 레이아웃 수정
	 * @param portalLayout 레이아웃 모델
	 */
	public void updateLayout(PortalLayout portalLayout) {
		portalLayoutDao.updateLayout(portalLayout);
	}
}