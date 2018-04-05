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
 * Mblog2addon VO
 *
 * @author 최성우
 * @version $Id: Mblog2addon.java 16246 2011-08-18 04:48:28Z giljae $
 */

public class Mblog2addon extends BaseObject {

	/**
	 *serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 마이크로블로깅 게시글 ID
	 */
	private String mblogId;

	/**
	 * ADDON_CODE 랜덤스트링(4자)+Sequence(1000부터)
	 */
	private String addonCode;

	public String getMblogId() {
		return mblogId;
	}

	public void setMblogId(String mblogId) {
		this.mblogId = mblogId;
	}

	public String getAddonCode() {
		return addonCode;
	}

	public void setAddonCode(String addonCode) {
		this.addonCode = addonCode;
	}
	
}
