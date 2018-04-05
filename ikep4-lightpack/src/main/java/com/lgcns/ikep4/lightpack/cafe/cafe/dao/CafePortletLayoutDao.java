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
import com.lgcns.ikep4.lightpack.cafe.cafe.model.CafePortletLayout;


/**
 * TODO Javadoc주석작성
 * 
 * @author 김종철
 * @version $Id: CafePortletLayoutDao.java 16240 2011-08-18 04:08:15Z giljae $
 */
public interface CafePortletLayoutDao extends GenericDao<CafePortletLayout, String> {

	/**
	 * Cafe에서 설정한 포틀릿 목록
	 * 
	 * @param cafeId
	 * @return
	 */
	public List<CafePortletLayout> listByCafe(String cafeId);

	/**
	 * Cafe에서 설정한 포틀릿 목록
	 * 
	 * @param cafeId
	 * @return
	 */
	public List<CafePortletLayout> listLayoutByCafe(String cafeId);

	/**
	 * Cafe에서 설정한 포틀릿 건수 조회
	 * 
	 * @param cafeId
	 * @return
	 */
	public Integer countByCafe(String cafeId);

	/**
	 * 해당 Cafe에서 포틀릿 레이아웃 ID 에 해당 되는 값 삭제
	 * 
	 * @param portletLayoutId
	 */
	public void physicalDelete(String portletLayoutId);

	/**
	 * 해당 Cafe에서 전체 포틀릿 레이아웃을 비운다.
	 * 
	 * @param cafeId
	 */
	public void physicalDeleteByCafeId(String cafeId);

}
