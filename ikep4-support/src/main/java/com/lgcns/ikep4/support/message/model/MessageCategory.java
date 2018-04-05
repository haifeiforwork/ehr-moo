/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.message.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageCategory.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class MessageCategory extends BaseObject {

	private static final long serialVersionUID = 4416175743475923047L;
	
	 /**
	  * 카테고리ID 
	  */
	private String categoryId ;
	/**
	  * 카테고리명 
	  */
	private String categoryName ;
	/**
	  * 카테고리영문명 
	  */
	private String categoryEnglishName ;
	 
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryEnglishName() {
		return categoryEnglishName;
	}
	public void setCategoryEnglishName(String categoryEnglishName) {
		this.categoryEnglishName = categoryEnglishName;
	}

}
