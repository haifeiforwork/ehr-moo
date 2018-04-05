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
import com.lgcns.ikep4.lightpack.cafe.cafe.model.CafePortlet;


/**
 * Cafe 에 등록된 포틀릿 정보를 제공하는 Service
 * 
 * @author 김종철
 * @version $Id: CafePortletService.java 16240 2011-08-18 04:08:15Z giljae $
 */
@Transactional
public interface CafePortletService extends GenericService<CafePortlet, String> {

	/**
	 * Cafe 에 등록된 포틀릿의 목록
	 * 
	 * @return
	 */
	public List<CafePortlet> listAllCafe();

	/**
	 * Cafe 에 등록된 포틀릿 중에 디폴트 값으로 설정된 포틀릿의 목록 Cafe 개설시 등록될 포틀릿 종류를 결정
	 * 
	 * @return
	 */
	public List<CafePortlet> listDefaultCafePortlet();

	/**
	 * Cafe 에 등록가능한 포틀릿의 목록
	 * 
	 * @param CafeId
	 * @return
	 */
	public List<CafePortlet> listAllCafePortlet(String cafeId);

}
