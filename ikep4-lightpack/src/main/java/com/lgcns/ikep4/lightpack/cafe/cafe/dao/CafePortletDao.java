/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.cafe.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.CafePortlet;


/**
 * Cafe 포틀릿 Dao
 * 
 * @author 김종철
 * @version $Id: CafePortletDao.java 16240 2011-08-18 04:08:15Z giljae $
 */
public interface CafePortletDao extends GenericDao<CafePortlet, String> {

	/**
	 * Cafe 에 등록된 포틀릿 전체 목록
	 * 
	 * @param cafePortlet
	 * @return
	 */
	public List<CafePortlet> listAllPortlet(CafePortlet cafePortlet);

	/**
	 * Cafe 에 등록된 포틀릿 갯수
	 * 
	 * @param cafePortlet
	 * @return
	 */
	public Integer countAllPortlet(CafePortlet cafePortlet);

	/**
	 * Cafe 에 등록 가능한 포틀릿 전체 목록 ( 게시판 포함)
	 * 
	 * @param cafeId
	 * @return
	 */
	public List<CafePortlet> listAllCafePortlet(String cafeId);

	/**
	 * Cafe 에 등록 가능한 포틀릿 전체 갯수 ( 게시판 포함)
	 * 
	 * @param cafeId
	 * @return
	 */
	public Integer countAllCafePortlet(String cafeId);
}
