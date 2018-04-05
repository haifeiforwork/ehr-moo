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
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPageLayoutDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPageLayout;


/**
 * 페이지 레이아웃 DAO 구현 클래스
 *
 * @author 임종상(malboru80@naver.com)
 * @version $Id: PortalPageLayoutDaoImpl.java 19098 2012-06-04 08:44:50Z malboru80 $
 */
@Repository("portalPageLayoutDao")
public class PortalPageLayoutDaoImpl extends GenericDaoSqlmap<PortalPageLayout, String> implements PortalPageLayoutDao {

	/**
	 * 포탈 메인 공통 페이지 레이아웃 리스트 반환
	 * @param pageId 페이지 ID
	 * @return List<PortalPageLayout> 공통 페이지 레이아웃 목록
	 */
	public List<PortalPageLayout> listPageLayout(String pageId) {
		Map<String, String> map = new HashMap<String, String>(); 
		map.put("pageId", pageId);
		
		return sqlSelectForList("portal.admin.screen.portalPageLayout.listPageLayout", map);
	}
	
	/**
	 * owner 페이지 레이아웃 리스트 반환
	 * @param pageId 페이지 ID
	 * @param ownerId 소유자 ID
	 * @return List<PortalPageLayout> owner 페이지 레이아웃 목록
	 */
	public List<PortalPageLayout> listOwnerPageLayout(String pageId, String ownerId) {
		Map<String, String> map = new HashMap<String, String>(); 
		map.put("pageId", pageId);
		map.put("ownerId", ownerId);
		
		return sqlSelectForList("portal.admin.screen.portalPageLayout.listOwnerPageLayout", map);
	}
	
	/**
	 * owner 페이지 레이아웃 count
	 * @param pageId 페이지 ID
	 * @param ownerId 소유자 ID
	 * @return owner 페이지 레이아웃 갯수
	 */
	public Integer countOwnerPageLayout(String pageId, String ownerId) {
		Map<String, String> map = new HashMap<String, String>(); 
		map.put("pageId", pageId);
		map.put("ownerId", ownerId);
		
		Integer count = (Integer) sqlSelectForObject("portal.admin.screen.portalPageLayout.countOwnerPageLayout", map);
		
		return count;
	}
	
	/**
	 * 페이지 레이아웃 생성
	 * @param portalPageLayout 포탈 페이지 레이아웃 model
	 */
	public void createPageLayout(PortalPageLayout portalPageLayout) {
		sqlInsert("portal.admin.screen.portalPageLayout.createPageLayout", portalPageLayout);
	}
	
	/**
	 * 페이지 레이아웃 삭제
	 * @param pageLayoutId 페이지 레이아웃 ID
	 */
	public void removePageLayout(String pageLayoutId) {
		sqlDelete("portal.admin.screen.portalPageLayout.removePageLayout", pageLayoutId);
	}
	
	/**
	 * 전체 페이지 레이아웃 삭제
	 * @param pageLayoutList 페이지 레이아웃 리스트
	 */
	public void removePageLayoutAll(List<PortalPageLayout> pageLayoutList) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("pageLayoutList", pageLayoutList);
		
		sqlDelete("portal.admin.screen.portalPageLayout.removePageLayoutAll", map);
	}
	
	/**
	 * 페이지 레이아웃 수정
	 * @param portalPageLayout
	 */
	public void updatePageLayout(PortalPageLayout portalPageLayout) {
		sqlUpdate("portal.admin.screen.portalPageLayout.updatePageLayout", portalPageLayout);
	}
	
	@Deprecated
	public PortalPageLayout get(String id) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public String create(PortalPageLayout object) {
		return null;
	}

	@Deprecated
	public void update(PortalPageLayout object) {}

	@Deprecated
	public void remove(String id) {}
}
