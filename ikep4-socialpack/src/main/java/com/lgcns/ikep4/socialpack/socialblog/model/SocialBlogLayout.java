/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.model;

import java.util.List;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 소셜 블로그 포틀릿_레이아웃 VO
 *
 * @author 이형운
 * @version $Id: SocialBlogLayout.java 16246 2011-08-18 04:48:28Z giljae $
 */
public class SocialBlogLayout extends BaseObject {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = -5087160831258101612L;

	/**
	 * 레이아웃 ID
	 */
	private String layoutId ;
	
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
	private List<SocialBlogLayoutColumn> socialBlogLayoutColumnList;
	
	/**
	 * 레이아웃 영어 이름
	 */
	private String layoutEnglishName;

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

	/**
	 * @return the socialBlogLayoutColumnList
	 */
	public List<SocialBlogLayoutColumn> getSocialBlogLayoutColumnList() {
		return socialBlogLayoutColumnList;
	}

	/**
	 * @param socialBlogLayoutColumnList the socialBlogLayoutColumnList to set
	 */
	public void setSocialBlogLayoutColumnList(List<SocialBlogLayoutColumn> socialBlogLayoutColumnList) {
		this.socialBlogLayoutColumnList = socialBlogLayoutColumnList;
	}

	public String getLayoutEnglishName() {
		return layoutEnglishName;
	}

	public void setLayoutEnglishName(String layoutEnglishName) {
		this.layoutEnglishName = layoutEnglishName;
	}
	
	
}
