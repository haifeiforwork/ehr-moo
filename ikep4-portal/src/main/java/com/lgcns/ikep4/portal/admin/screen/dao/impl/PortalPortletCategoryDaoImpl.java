/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletCategoryDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletCategory;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;


/**
 * 포틀릿 그룹 DAO 구현 클래스
 * 
 * @author 임종상(malboru80@naver.com)
 * @version $Id: PortalPortletCategoryDaoImpl.java 19022 2012-05-31 06:36:27Z malboru80 $
 */
@Repository("PortalPortletCategoryDao")
public class PortalPortletCategoryDaoImpl extends GenericDaoSqlmap<PortalPortletCategory, String> implements
		PortalPortletCategoryDao {

	/**
	 * sql namespace
	 */
	private static final String NAMESPACE = "portal.admin.screen.portalPortletCategory.";
	
	/**
	 * 포틀릿카테고리 조회
	 * @param portletCategoryId 포틀릿 카테고리 ID
	 * @return PortalPortletCategory 포틀릿 카테고리 정보
	 */
	public PortalPortletCategory getPortletCategory(String portletCategoryId) {
		return (PortalPortletCategory) sqlSelectForObject(NAMESPACE + "getPortletCategory", portletCategoryId);
	}

	/**
	 * 포틀릿카테고리 등록
	 * @param portalPortletCategory 포틀릿 카테고리 모델
	 * @return 포틀릿 카테고리 ID
	 */
	public String createPortletCategory(PortalPortletCategory portalPortletCategory) {
		sqlInsert(NAMESPACE + "createPortletCategory", portalPortletCategory);
		
		return portalPortletCategory.getPortletCategoryId();
	}

	/**
	 * 포틀릿카테고리 리스트 반환
	 * @param systemCode 시스템 코드
	 * @return List<PortalPortletCategory> 포틀릿 카테고리 목록
	 */
	public List<PortalPortletCategory> listPortletCategory(String systemCode) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("systemCode", systemCode);
		
		return sqlSelectForList(NAMESPACE + "listPortletCategory", map);
	}
	
	/**
	 *  포틀릿카테고리 전체 리스트 반환
	 * @return List<PortalPortletCategory> 포틀릿 카테고리 전체목록
	 */
	public List<PortalPortletCategory> listPortletCategoryAll() {
		return sqlSelectForList(NAMESPACE + "listPortletCategoryAll");
	}
	
	/**
	 *  포틀릿카테고리 전체 리스트 반환
	 * @param searchCondition 검색조건 모델 클래스
	 * @return List<PortalPortletCategory> 포틀릿 카테고리 전체목록
	 */
	public List<PortalPortletCategory> listBySearchCondition(AdminSearchCondition searchCondition) {
		return sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);
	}
	
	/**
	 * 포틀릿카테고리 전체 리스트 개수 반환
	 * @param searchCondition 검색조건 모델 클래스
	 * @return Integer 포틀릿카테고리 전체 리스트 개수 반환
	 */
	public Integer countBySearchCondition(AdminSearchCondition searchCondition) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);

		return count;
	}

	/**
	 * 포틀릿카테고리 삭제
	 * @param portletCategoryId 포틀릿 카테고리 ID
	 */
	public void removePortletCategory(String portletCategoryId) {
		sqlDelete(NAMESPACE + "removePortletCategory", portletCategoryId);
	}

	/**
	 * 포틀릿카테고리 수정
	 * @param portalPortletCategory 포틀릿 카테고리 모델
	 */
	public void updatePortletCategory(PortalPortletCategory portalPortletCategory) {
		sqlUpdate(NAMESPACE + "updatePortletCategory", portalPortletCategory);
	}

	@Deprecated
	public PortalPortletCategory get(String id) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public String create(PortalPortletCategory object) {
		return null;
	}

	@Deprecated
	public void update(PortalPortletCategory object) {}

	@Deprecated
	public void remove(String id) {}

	public List<PortalPortletCategory> listPortalPortletCategoryAll(String portalId) {
		return sqlSelectForList(NAMESPACE + "listPortalPortletCategoryAll", portalId);
	}

}
