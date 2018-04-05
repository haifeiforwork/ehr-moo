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
import com.lgcns.ikep4.portal.admin.screen.model.PortalTheme;


/**
 * 포탈 테마 DAO 정의
 * 
 * @author 임종상(malboru80@naver.com)
 * @version $Id: PortalThemeDao.java 18250 2012-04-24 08:36:40Z yu_hs $
 */
public interface PortalThemeDao extends GenericDao<PortalTheme, String> {
	
	/**
	 * 포탈 테마 리스트 반환
	 * @param portalTheme 포탈 테마 모델
	 * @return List<PortalTheme> 테마 목록
	 */
	public List<PortalTheme> listTheme(PortalTheme portalTheme);
	
	/**
	 * 포탈 테마 리스트 반환
	 * @param searchCondition 검색조건 모델 클래스
	 * @return List<PortalTheme> 테마 목록
	 */
	public List<PortalTheme> listBySearchCondition(SearchCondition searchCondition);
	
	/**
	 * 포탈 테마 리스트 개수 반환
	 * @param searchCondition 검색조건 모델 클래스
	 * @return Integer 테마 목록 개수
	 */
	public Integer countBySearchCondition(SearchCondition searchCondition);
	
	/**
	 * 포탈 테마 수정
	 * @param portalTheme 포탈 테마 모델
	 */
	public void updateTheme(PortalTheme portalTheme);
	
	/**
	 * 포탈 테마 삭제 
	 * @param themeId 테마 ID
	 */
	public void removeTheme(String themeId);
	
	/**
	 * 포탈 테마 등록
	 * @param portalTheme 포탈 테마 모델
	 * @return 테마 ID
	 */
	public String createTheme(PortalTheme portalTheme);
	
	/**
	 * 포탈 테마 조회
	 * @param themeId 테마 ID
	 * @return PortalTheme 테마 정보
	 */
	public PortalTheme getTheme(String themeId);
	
	/**
	 * 포탈 디폴트 테마 조회
	 * @return PortalTheme 테마 정보
	 */
	public PortalTheme getDefaultTheme();
	
	/**
	 * 기본 테마 설정 삭제
	 */
	public void clearDefaultTheme(String portalId);
	
	/**
	 * 기본 테마 설정 초기화
	 */
	public void resetDefaultTheme(PortalTheme portalTheme);
}
