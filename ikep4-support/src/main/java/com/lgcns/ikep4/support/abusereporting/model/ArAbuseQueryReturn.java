/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.abusereporting.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 
 * 조회 결과
 *
 * @author 최성우
 * @version $Id: ArAbuseQueryReturn.java 16258 2011-08-18 05:37:22Z giljae $
 */

public class ArAbuseQueryReturn extends BaseObject {

	/**
	 * serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * KEYWORD
	 */
	private String keyword;

	/**
	 * COUNT
	 */
	private int cnt;


	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder()
			.append("[keyword:").append(keyword)
			.append(",cnt:").append(cnt).append("]");
		return str.toString();
	}
}
