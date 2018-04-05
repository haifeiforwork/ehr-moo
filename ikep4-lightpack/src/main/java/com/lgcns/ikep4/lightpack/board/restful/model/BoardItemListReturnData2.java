package com.lgcns.ikep4.lightpack.board.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="returnData2")
public class BoardItemListReturnData2 {
	
	/**
	 * 카테고리 ID
	 */
	public String categoryId = "";
	
	/**
	 * 카테고리 이름
	 */
	public String categoryName = "";

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
		
	
}