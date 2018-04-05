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
import com.lgcns.ikep4.portal.admin.screen.model.PortalLayout;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;


/**
 * 레이아웃 DAO 정의
 * 
 * @author 임종상(malboru80@naver.com)
 * @version $Id: PortalLayoutDao.java 16243 2011-08-18 04:10:43Z giljae $
 */
public interface PortalLayoutDao extends GenericDao<PortalLayout, String> {
	
	/**
	 * 레이아웃 리스트 반환
	 * @return List<PortalLayout> 레이아웃 목록
	 */
	public List<PortalLayout> listLayout();
	
	/**
	 *  레이아웃 리스트 반환
	 * @param searchCondition 검색조건 모델 클래스
	 * @return List<PortalPortletCategory> 레이아웃 리스트 반환
	 */
	public List<PortalLayout> listBySearchCondition(AdminSearchCondition searchCondition);
	
	/**
	 * 레이아웃 리스트 개수 반환
	 * @param searchCondition 검색조건 모델 클래스
	 * @return Integer 레이아웃 리스트 개수 반환
	 */
	public Integer countBySearchCondition(AdminSearchCondition searchCondition);

	/**
	 * 레이아웃 리스트 반환(공용여부 체크)
	 * @param common 공통여부
	 * @return List<PortalLayout> 레이아웃 목록
	 */
	public List<PortalLayout> listLayoutCommonCheck(int common);
	
	/**
	 * 레이아웃 삭제
	 * @param layoutId 레이아웃 ID
	 */
	public void removeLayout(String layoutId);

	/**
	 * 레이아웃 수정
	 * @param portalLayout 레이아웃 모델
	 */
	public void updateLayout(PortalLayout portalLayout);
	
	/**
	 * 레이아웃 등록
	 * @param portalLayout 레이아웃 모델
	 * @return 레이아웃 ID
	 */
	public String createLayout(PortalLayout portalLayout);
	
	/**
	 * 레이아웃 조회
	 * @param layoutId 레이아웃 ID
	 * @return PortalLayout 레이아웃 정보
	 */
	public PortalLayout getLayout(String layoutId);
	
	/**
	 * 디폴트 레이아웃 조회
	 * @return PortalLayout 레이아웃 정보
	 */
	public PortalLayout getCommonLayout();
}