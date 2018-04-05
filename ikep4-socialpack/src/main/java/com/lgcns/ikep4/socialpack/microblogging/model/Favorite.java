/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 
 * Favorite VO
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: Favorite.java 16246 2011-08-18 04:48:28Z giljae $
 */

public class Favorite extends BaseObject {

	/**
	 * serial Version UID
	 */
	private static final long serialVersionUID = 2761620106360968157L;

	/**
	 * Favorite 지정한 사용자 ID
	 */
	private String userId;

	/**
	 * 마이크로블로깅 게시글 ID
	 */
	private String mblogId;

	/**
	 * Favorite 지정 일시
	 */
	private Date registDate;

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMblogId() {
		return mblogId;
	}

	public void setMblogId(String mblogId) {
		this.mblogId = mblogId;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}
}
