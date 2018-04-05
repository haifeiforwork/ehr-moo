/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.board.search;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * 게시글 검색조건 모델 클래스
 */
public class RelatedBoardItemSearchCondition extends SearchCondition {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 게시판 ID */
	private String itemId;

	public String getItemId() {
		return this.itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

}