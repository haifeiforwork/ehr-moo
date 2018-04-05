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
 * HistoryKey VO
 *
 * @author 최성우
 * @version $Id: ArHistoryKey.java 16258 2011-08-18 05:37:22Z giljae $
 */

public class ArHistoryKey extends BaseObject {

	/**
	 * serial Version UID
	 */
	private static final long serialVersionUID = 1295721283351347951L;

	/**
	 * ID
	 */
	private String historyId;

	/**
	 * Detecting된 금지어
	 */
	private String keyword;

	public String getHistoryId() {
		return historyId;
	}

	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	
}
