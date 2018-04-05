/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 
 * 통계조회 등 결과 컬럼이 특별한 경우에 이 모델에 컬럼을 추가하여 사용한다.
 *
 * @author 최성우
 * @version $Id: QueryReturn.java 16246 2011-08-18 04:48:28Z giljae $
 */

public class QueryReturn extends BaseObject {

	/**
	 * serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * DAYS
	 */
	private String days;

	/**
	 * COUNT
	 */
	private int cnt;

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
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
			.append("[days:").append(days)
			.append(",cnt:").append(cnt).append("]");
		return str.toString();
	}
}
