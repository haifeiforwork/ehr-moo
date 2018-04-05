/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalThemeDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalTheme;



/**
 * 포탈 테마 DAO 구현 클래스
 * 
 * @author 임종상(malboru80@naver.com)
 * @version $Id: PortalThemeDaoImpl.java 18250 2012-04-24 08:36:40Z yu_hs $
 */
@Repository("portalThemeDao")
public class PortalThemeDaoImpl extends GenericDaoSqlmap<PortalTheme, String> implements PortalThemeDao {

	/**
	 * sql namespace
	 */
	private static final String NAMESPACE = "portal.admin.screen.portalTheme.";
	
	/**
	 * 포탈 테마 등록
	 * @param portalTheme 포탈 테마 모델
	 * @return 테마 ID
	 */
	public String createTheme(PortalTheme portalTheme) {
		sqlInsert(NAMESPACE + "createTheme", portalTheme);
		return portalTheme.getThemeId();
	}
	
	/**
	 * 포탈 테마 조회
	 * @param themeId 테마 ID
	 * @return PortalTheme 테마 정보
	 */
	public PortalTheme getTheme(String themeId) {
		return (PortalTheme) sqlSelectForObject(NAMESPACE + "getTheme", themeId);
	}
	
	/**
	 * 포탈 테마 리스트 반환
	 * @param portalTheme 포탈 테마 모델
	 * @return List<PortalTheme> 테마 목록
	 */
	public List<PortalTheme> listTheme(PortalTheme portalTheme) {
		return sqlSelectForList(NAMESPACE + "listTheme", portalTheme);
	}
	
	/**
	 * 포탈 테마 리스트 반환
	 * @param searchCondition 검색조건 모델 클래스
	 * @return List<PortalTheme> 테마 목록
	 */
	public List<PortalTheme> listBySearchCondition(SearchCondition searchCondition) {
		return sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);
	}
	
	/**
	 * 포탈 테마 리스트 개수 반환
	 * @param searchCondition 검색조건 모델 클래스
	 * @return Integer 테마 목록 개수
	 */
	public Integer countBySearchCondition(SearchCondition searchCondition) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);

		return count;
	}

	/**
	 * 포탈 테마 수정
	 * @param portalTheme 포탈 테마 모델
	 */
	public void updateTheme(PortalTheme portalTheme) {
		sqlUpdate(NAMESPACE + "updateTheme", portalTheme);
	}
	
	/**
	 * 포탈 테마 삭제 
	 * @param themeId 테마 ID
	 */
	public void removeTheme(String themeId) {
		sqlDelete(NAMESPACE + "removeTheme", themeId);
	}
	
	/**
	 * 포탈 디폴트 테마 조회 
	 */
	public PortalTheme getDefaultTheme() {
		return (PortalTheme) sqlSelectForObject(NAMESPACE + "getDefaultTheme");
	}
	
	@Deprecated
	public void update(PortalTheme object) {}

	@Deprecated
	public void remove(String id) {}

	@Deprecated
	public String create(PortalTheme object) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public PortalTheme get(String id) {
		return null;
	}
	
	public void clearDefaultTheme(String portalId) {
		sqlUpdate(NAMESPACE + "clearDefaultTheme", portalId);
	}
	
	public void resetDefaultTheme(PortalTheme portalTheme) {
		sqlUpdate(NAMESPACE + "resetDefaultTheme", portalTheme);
	}
}