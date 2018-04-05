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
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPageLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortlet;


/**
 * 포탈 포틀릿 DAO 정의
 * 
 * @author 임종상(malboru80@naver.com)
 * @version $Id: PortalPortletDao.java 19021 2012-05-31 06:36:11Z malboru80 $
 */
public interface PortalPortletDao extends GenericDao<PortalPortlet, String> {
	
	/**
	 * 검색 조건에 따른 포틀릿 목록 조회
	 * 
	 * @param searchCondition 검색조건
	 * @return 포틀릿 목록
	 */
	public List<PortalPortlet> listPortalPortletByCondition(SearchCondition searchCondition);
	
	/**
	 * 검색 조건에 따른 포틀릿 갯수 조회
	 * 
	 * @param searchCondition 검색조건
	 * @return 포틀릿 갯수
	 */
	public Integer countPortalPortletByCondition(SearchCondition object);
	
	/**
	 * 시스템 코드로 포틀릿 목록 조회
	 * 
	 * @param systemCode 시스템 코드
	 * @return 포틀릿 목록
	 */
	public List<PortalPortlet> listPortlet(String systemCode) ;
	
	/**
	 * 시스템 코드로 개인 포틀릿 목록 조회 (개인사용자 별이 아닌, 공용이 아닌 포틀릿 목록)
	 * 
	 * @param systemCode 시스템 코드
	 * @return 포틀릿 목록
	 */
	public List<PortalPortlet> listPortletPrivate(String systemCode) ;
	
	/**
	 * 시스템 코드로 개인 포틀릿 목록 조회 (public_option = 0  )
	 * 
	 * @param systemCode 시스템 코드
	 * @param userId 사용자 아이디
	 * @param localeCode 사용자 로케일 코드
	 * @param fieldName 다국어 컬럼 이름
	 * @return 개인 포틀릿 목록
	 */
	public List<PortalPortlet> listPortletCommonCheck(String systemCode, String userId, String localeCode, String fieldName);
	
	/**
	 * 포틀릿 상세 조회 
	 * 
	 * @param portletId 포틀릿 아이디
	 * @return 포틀릿 상세정보
	 */
	public PortalPortlet readPortalPortlet(String portletId);
	
	/**
	 * 포틀릿 상세 조회 
	 * 
	 * @param portletId 포틀릿 아이디
	 * @param portletId 포틀릿 아이디
	 * @return 사용자 Locale이 적용된 포틀릿 상세정보
	 */
	public PortalPortlet readPortalPortlet(String portletId,String userLocale);
	
	/**
	 * 포틀릿 삭제
	 * 
	 * @param portletId 포틀릿 아이디
	 * @return 삭제된 포틀릿 갯수  ( 성공:1 , 실패:0 )
	 */
	public int removePortalPortlet(String portletId);
	
	
	/**
	 * 포틀릿 등록
	 * 
	 * @param portalPortlet 포틀릿 정보
	 * @return 등록된 포틀릿 갯수  ( 성공:1 , 실패:0 )
	 */
	public String createPortalPortlet(PortalPortlet portalPortlet);
	
	/**
	 * 포틀릿 수정
	 * 
	 * @param portalPortlet 포틀릿 정보
	 * @return 수정된 포틀릿 갯수  ( 성공:1 , 실패:0 )
	 */
	public int updatePortalPortlet(PortalPortlet portalPortlet);
	
	/**
	 * 페이지 레이아웃별 포틀릿 목록
	 * 
	 * @param pageLayoutId 페이지 레이아웃 아이디
	 * @param systemCode 시스템 코드
	 * @param localeCode 사용자 로케일 코드
	 * @param fieldName 다국어 컬럼 이름
	 * @return 페이지 레이아웃별 포틀릿 목록
	 */
	public List<PortalPortlet> listPageLayoutPortlet(String pageLayoutId, String systemCode, String localeCode, String fieldName);
	
	/**
	 * 사용자가 사용하는 전체 포틀릿 리스트
	 * 
	 * @param pageLayoutId 페이지 레이아웃 아이디
	 * @param systemCode 시스템 코드
	 * @param userID 사용자 아이디
	 * @param localeCode 사용자 로케일 코드
	 * @param fieldName 다국어 컬럼 이름
	 * @return 사용자 포틀릿 목록
	 */
	public List<PortalPortlet> listActivePortlet(String systemCode, List<PortalPageLayout> pageLayoutList, String userId, String localeCode, String fieldName);
	
	public List<PortalPortlet> listActiveNomovePortlet(String systemCode, List<PortalPageLayout> pageLayoutList, String userId, String localeCode, String fieldName);
	
	/**
	 * 포틀릿 디폴트 셋팅시 사용하는 공통 전체 포틀릿 리스트
	 * 
	 * @param pageLayoutId 페이지 레이아웃 아이디
	 * @param systemCode 시스템 코드
	 * @return 사용자 포틀릿 목록
	 */
	public List<PortalPortlet> listActivePortletSetting(String systemCode, List<PortalPageLayout> pageLayoutList);
	
	/**
	 * portletConfigId 를 제외한 페이지 레이아웃의 포틀릿 리스트 
	 * 
	 * @param pageLayoutId 페이지 레이아웃 아이디
	 * @param systemCode 시스템 코드
	 * @param portletConfigId 포틀릿 컨피그 아이디
	 * @return 포틀릿 목록
	 */
	public List<PortalPortlet> listPageLayoutMovePortlet(String pageLayoutId, String systemCode, String portletConfigId);
	
	public List<PortalPortlet> listPortletShareAll(String portalId);
}
