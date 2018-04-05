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
 * Addon VO
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: Addon.java 16246 2011-08-18 04:48:28Z giljae $
 */

public class Addon extends BaseObject {


	/**
	 * serial Version UID
	 */
	private static final long serialVersionUID = -8318953993164647179L;

	/**
	 * ADDON_CODE 랜덤스트링(4자)+Sequence(1000부터)
	 */
	private String addonCode;

	/**
	 * 마이크로블로깅 링크 타입 (0 : URL, 1 : 투표, 2: 이미지, 3 :  파일)
	 */
	private String addonType;

	/**
	 * 원본 링크 URL
	 */
	private String sourceLink;

	/**
	 * ADDON_TYPE에 따른 DISPLAY
	 */
	private String displayCode;

	
	public String getAddonCode() {
		return addonCode;
	}

	public void setAddonCode(String addonCode) {
		this.addonCode = addonCode;
	}

	public String getAddonType() {
		return addonType;
	}

	public void setAddonType(String addonType) {
		this.addonType = addonType;
	}

	public String getSourceLink() {
		return sourceLink;
	}

	public void setSourceLink(String sourceLink) {
		this.sourceLink = sourceLink;
	}

	public String getDisplayCode() {
		return displayCode;
	}

	public void setDisplayCode(String displayCode) {
		this.displayCode = displayCode;
	}

}
