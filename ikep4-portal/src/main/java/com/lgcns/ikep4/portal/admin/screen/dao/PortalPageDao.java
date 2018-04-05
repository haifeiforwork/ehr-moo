/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPage;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPageSearchCondition;

/**
 * 포탈 페이지 DAO 정의
 *
 * @author 임종상(malboru80@naver.com)
 * @version $Id: PortalPageDao.java 19326 2012-06-19 06:55:36Z yu_hs $
 */
public interface PortalPageDao extends GenericDao<PortalPage, String> {
	
	/**
	 * 페이지 반환
	 * @param pageId 페이지 ID
	 * @param fieldName 필드 이름
	 * @param localeCode 로케일 코드
	 * @return PortalPage 페이지 정보
	 */
	public PortalPage getPage(String pageId, String fieldName, String localeCode);
	
	/**
	 * 페이지 수정
	 * @param portalPage 포탈 페이지 model
	 */
	public void updatePage(PortalPage portalPage);
	
	/**
	 * 페이지 등록
	 * @param portalPage 포탈 페이지 model
	 */
	public void createPage(PortalPage portalPage);
	
	/**
	 * 리스트 카운트 반환
	 * @param searchCondition 검색 조건
	 * @return 리스트 갯수
	 */
	public Integer countPage(PortalPageSearchCondition searchCondition);
	
	/**
	 * 리스트 반환
	 * @param searchCondition 검색 조건
	 * @return  List<PortalPage> 페이지 목록
	 */
	public List<PortalPage> listPage(PortalPageSearchCondition searchCondition);
	
	/**
	 * 페이지 삭제
	 * @param pageId 페이지 ID
	 */
	public void removePage(String pageId);
	
	/**
	 * pageId 조회
	 * @param systemCode 시스템 코드
	 * @return 페이지 ID
	 */
	public String getPageId(String systemCode);
	
	/**
	 * 메인 페이지 공용 포틀릿 좌/우 너비 갱신
	 * @param portalPage
	 */
	public void updateCommonPortletWidth(PortalPage portalPage);
}