/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 블로그 포스팅 추천 정보 용 VO
 *
 * @author 이형운
 * @version $Id: SocialBoardItemRecommend.java 16246 2011-08-18 04:48:28Z giljae $
 */
public class SocialBoardItemRecommend extends BaseObject {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = -6482331121454799889L;

	/**
	 * 블로그 포스팅 추천인 아이디
	 */
	private String registerId;
	
	/**
	 * 블로그 포스팅 아이템 ID
	 */
	private String itemId;
	
	/**
	 * 블로그 포스팅 추천 일자
	 */
	private String registDate;

	/**
	 * @return the registerId
	 */
	public String getRegisterId() {
		return registerId;
	}

	/**
	 * @param registerId the registerId to set
	 */
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	/**
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the registDate
	 */
	public String getRegistDate() {
		return registDate;
	}

	/**
	 * @param registDate the registDate to set
	 */
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	
	

}
