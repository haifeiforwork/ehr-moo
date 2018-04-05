/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.quartz.model;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * Batch Search condition
 *
 * @author 주길재
 * @version $Id: BatchSearchCondition.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class BatchSearchCondition extends SearchCondition {
	private static final long serialVersionUID = -5454170809993379868L;

	/**
	 * 검색컬럼
	 */
	private String searchColumn;

	/**
	 * 검색어
	 */
	private String searchWord;

	public String getSearchColumn() {
		return searchColumn;
	}

	public void setSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
}
