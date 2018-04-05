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
import com.lgcns.ikep4.lightpack.cafe.cafe.model.CafeLayout;


/**
 * Cafe 레이아웃 정보를 제공하는 Service Interface
 * 
 * @author 이형운
 * @version $Id: CafeLayoutService.java 16240 2011-08-18 04:08:15Z giljae $
 */
@Transactional
public interface CafeLayoutService extends GenericService<CafeLayout, String> {

	/**
	 * 해당 아이디의 소셜 블로그 레이아웃을 가지고 온다
	 * 
	 * @param ownerId 블로그 소유자 ID
	 * @return 블로그 기본정보 객체
	 */
	public CafeLayout cafeLayoutByOwnerId(Cafe cafe);

	/**
	 * 디폴트 값으로 설정 되어 있는 레이아웃을 가지고 온다.
	 * 
	 * @return 디폴트 레이아웃 정보 객체
	 */
	public CafeLayout defaultCafeLayout();

	/**
	 * 소셜 블로그에 등록된 레이아웃 리스트를 가지고 온다.
	 * 
	 * @param ownerId 블로그 소유자 ID
	 * @return 레이아웃 정보 객체 리스트
	 */
	public List<CafeLayout> listCafeLayoutAll();

}