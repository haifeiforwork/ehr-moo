/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.model.PortalTheme;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;


/**
 * 포탈 테마 Service
 * 
 * @author 임종상(malboru80@naver.com)
 * @version $Id: PortalThemeService.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Transactional
public interface PortalThemeService extends GenericService<PortalTheme, String> {
	
	/**
	 * 테마 리스트
	 * @param portalTheme 테마 모델
	 * @return List<PortalTheme> 테마 목록
	 */
	public List<PortalTheme> listTheme(PortalTheme portalTheme);
	
	/**
	 * 테마 리스트
	 * @param searchCondition 검색조건 모델 클래스
	 * @return SearchResult<PortalTheme> 테마 목록
	 */
	public SearchResult<PortalTheme> listBySearchCondition(AdminSearchCondition searchCondition);

	/**
	 * 테마 수정
	 * @param portalTheme 테마 모델
	 */
	public void updateTheme(PortalTheme portalTheme);

	/**
	 * 테마 삭제
	 * @param themeId 테마 ID
	 */
	public void removeTheme(String themeId);
	
	/**
	 * 테마 생성
	 * @param portalTheme 테마 모델
	 * @return 테마 ID
	 */
	public String createTheme(PortalTheme portalTheme);
	
	/**
	 * 테마 조회
	 * @param themeId 테마 ID
	 * @return PortalTheme 테마 정보
	 */
	public PortalTheme readTheme(String themeId);
	
}
