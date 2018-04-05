/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.gallery.search;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * 댓글 검색조건 모델 클래스
 */
public class BoardLinereplySearchCondition extends SearchCondition { 
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -728000126730580064L;

	/** 게시판 ID */
	private String targetUserId;
	
	/** 게시물 ID */
	private String itemId;


	/**
	 * Gets the item id.
	 *
	 * @return the item id
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * Sets the item id.
	 *
	 * @param itemId the new item id
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the targetUserId
	 */
	public String getTargetUserId() {
		return targetUserId;
	}

	/**
	 * @param targetUserId the targetUserId to set
	 */
	public void setTargetUserId(String targetUserId) {
		this.targetUserId = targetUserId;
	}
	 
}
