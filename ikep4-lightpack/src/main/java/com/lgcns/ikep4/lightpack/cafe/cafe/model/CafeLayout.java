/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.cafe.model;

import java.util.List;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 포틀릿_레이아웃 VO
 * 
 * @author 이형운
 * @version $Id: CafeLayout.java 16240 2011-08-18 04:08:15Z giljae $
 */
public class CafeLayout extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -7612700790305767308L;

	/**
	 * 레이아웃 ID
	 */
	private String layoutId;

	/**
	 * 레이아웃_이름
	 */
	private String layoutName;

	/**
	 * 컬럼 수
	 */
	private Integer colCount;

	/**
	 * 이미지 URL
	 */
	private String imageUrl;

	/**
	 * 디폴트 LAYOUT 여부(1:디폴트, 0; 디폴트 아님)
	 */
	private String isDefault;

	/**
	 * 블로그의 해당 레이아웃에 속한 Column 의 List
	 */
	private List<CafeLayoutColumn> cafeLayoutColumnList;

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
	 * @return the layoutName
	 */
	public String getLayoutName() {
		return layoutName;
	}

	/**
	 * @param layoutName the layoutName to set
	 */
	public void setLayoutName(String layoutName) {
		this.layoutName = layoutName;
	}

	/**
	 * @return the colCount
	 */
	public Integer getColCount() {
		return colCount;
	}

	/**
	 * @param colCount the colCount to set
	 */
	public void setColCount(Integer colCount) {
		this.colCount = colCount;
	}

	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * @return the isDefault
	 */
	public String getIsDefault() {
		return isDefault;
	}

	/**
	 * @param isDefault the isDefault to set
	 */
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public List<CafeLayoutColumn> getCafeLayoutColumnList() {
		return cafeLayoutColumnList;
	}

	public void setCafeLayoutColumnList(List<CafeLayoutColumn> cafeLayoutColumnList) {
		this.cafeLayoutColumnList = cafeLayoutColumnList;
	}

}
