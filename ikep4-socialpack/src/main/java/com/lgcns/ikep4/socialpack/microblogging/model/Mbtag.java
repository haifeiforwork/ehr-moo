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
 * Mbtag VO
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: Mbtag.java 16246 2011-08-18 04:48:28Z giljae $
 */

public class Mbtag extends BaseObject {

	/**
	 * serial Version UID
	 */
	private static final long serialVersionUID = -3176969842660359922L;

	/**
	 * 태그 아이디
	 */
	private String mbtagId;

	/**
	 * 태그 타입 (0: MENTION 1: 해쉬태그)
	 */
	private String mbtagType;

	/**
	 * 태그이름(@,# 제외)
	 */
	private String mbtagName;

	/**
	 * 태그이름(@,# 포함)
	 */
	private String mbtagFullName;

	/**
	 * 포탈 ID
	 */
	private String portalId;

	public String getMbtagId() {
		return mbtagId;
	}

	public void setMbtagId(String mbtagId) {
		this.mbtagId = mbtagId;
	}

	public String getMbtagType() {
		return mbtagType;
	}

	public void setMbtagType(String mbtagType) {
		this.mbtagType = mbtagType;
	}

	public String getMbtagName() {
		return mbtagName;
	}

	public void setMbtagName(String mbtagName) {
		this.mbtagName = mbtagName;
	}

	public String getMbtagFullName() {
		return mbtagFullName;
	}

	public void setMbtagFullName(String mbtagFullName) {
		this.mbtagFullName = mbtagFullName;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}
	
}
