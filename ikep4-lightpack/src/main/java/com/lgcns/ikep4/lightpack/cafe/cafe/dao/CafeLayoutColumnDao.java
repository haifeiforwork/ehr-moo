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
import com.lgcns.ikep4.lightpack.cafe.cafe.model.CafeLayoutColumn;

/**
 * 레이아웃 컬럼 정보 DAO Interface
 *
 * @author 이형운
 * @version $Id: CafeLayoutColumnDao.java 16240 2011-08-18 04:08:15Z giljae $
 */
public interface CafeLayoutColumnDao extends GenericDao<CafeLayoutColumn, String> {
	
	/**
	 * Cafe 레이아웃 컬럼을 조회한다. 
	 * @param cafeLayoutColumn Cafe 레이아웃 컬럼 정보
	 * @return Cafe 레이아웃 컬럼 정보
	 */
	public CafeLayoutColumn get(CafeLayoutColumn cafeLayoutColumn);
	
	/**
	 * 해당 컬럼의 레이아웃 존재 유무를 반환
	 * @param CafeLayoutColumn Cafe 레이아웃 컬럼 정보
	 * @return 해당 컬럼의 레이아웃 존재 유무
	 */
	public boolean exists(CafeLayoutColumn CafeLayoutColumn);
	
	/**
	 * Cafe에 사용된 레이아웃의 컬럼 리스트들을 반환한다.
	 * @param layoutId Cafe 레이아웃 ID
	 * @return 레이아웃의 컬럼 리스트
	 */
	public List<CafeLayoutColumn> listByLayoutId(String layoutId);
	
	/**
	 * 레이아웃 컬럼 리스트의 수
	 * @param layoutId Cafe 레이아웃 ID
	 * @return 레이아웃 컬럼 수
	 */
	public Integer countByLayoutId(String layoutId);
	
	/**
	 * 레이아웃 컬럼중 고정 컬럼 의 Col_Index 반환
	 * @param layoutId Cafe 레이아웃 ID
	 * @return 고정 영역 컬럼의 Index 값
	 */
	public Integer getFixedLayoutColumn(String layoutId);
	
	/**
	 * 레이아웃 컬럼 삭제
	 * @param CafeLayoutColumn Cafe 레이아웃 컬럼 정보
	 */
	public void physicalDelete(CafeLayoutColumn CafeLayoutColumn);

}
