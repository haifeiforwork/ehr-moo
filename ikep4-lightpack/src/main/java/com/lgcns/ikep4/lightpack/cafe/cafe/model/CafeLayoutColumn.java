/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.cafe.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 레이아웃 컬럼 정보 VO
 * 
 * @author 이형운
 * @version $Id: CafeLayoutColumn.java 16240 2011-08-18 04:08:15Z giljae $
 */
public class CafeLayoutColumn extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 1830437771582338168L;

	/**
	 * 레이아웃 ID
	 */
	private String layoutId;

	/**
	 * 컬럼_순서(몇번째 컬럼)
	 */
	private Integer colIndex;

	/**
	 * 컬럼 너비
	 */
	private Integer width;

	/**
	 * 고정컬럼 여부(1; 고정 컬럼, 0: 포틀릿 배치 컬럼)
	 */
	private Integer isFixed;

	/**
	 * @return the layoutId
	 */
	public String getLayoutId() {
		return layoutId;
	}

	/**
	 * @param layoutId the layoutId to set
	 */
	public void setLayoutId(String layoutId) {
		this.layoutId = layoutId;
	}

	/**
	 * @return the colIndex
	 */
	public Integer getColIndex() {
		return colIndex;
	}

	/**
	 * @param colIndex the colIndex to set
	 */
	public void setColIndex(Integer colIndex) {
		this.colIndex = colIndex;
	}

	/**
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}

	/**
	 * @return the isFixed
	 */
	public Integer getIsFixed() {
		return isFixed;
	}

	/**
	 * @param isFixed the isFixed to set
	 */
	public void setIsFixed(Integer isFixed) {
		this.isFixed = isFixed;
	}

}
