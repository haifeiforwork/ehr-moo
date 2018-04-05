/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.cafe.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.Cafe;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.CafePortletLayout;


/**
 * Cafe 에서 선택된 Portlet 값을 제공 하는 Service
 * 
 * @author 김종철
 * @version $Id: CafePortletLayoutService.java 14298 2011-06-03 02:29:54Z jghong
 *          $
 */
@Transactional
public interface CafePortletLayoutService extends GenericService<CafePortletLayout, String> {

	/**
	 * 해당 Cafe 에 속한 Portlet 목록
	 * 
	 * @param CafeId
	 * @return
	 */
	public List<CafePortletLayout> listLayoutByCafe(String cafeId);

	/**
	 * 처음 Cafe 방문시 Default Portlet List 를 가지고 기본 Portlet Layout을 저장 한다.
	 * 
	 * @param CafeId
	 */
	public void saveDefaultCafePortletLayout(String cafeId);

	/**
	 * 입력 받은Cafe Portlet Layout을 저장한다.
	 * 
	 * @param CafePortletLayoutList
	 */
	public void saveCafePortletLayout(Cafe cafe, List<CafePortletLayout> CafePortletLayoutList);

	/**
	 * TODO Cafe 포틀릿 정보 삭제
	 * 
	 * @param CafeId
	 */
	public void physicalDeleteByCafeId(String cafeId);
}
