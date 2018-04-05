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
 * Mblog2tag VO
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: Mblog2tag.java 16246 2011-08-18 04:48:28Z giljae $
 */

public class Mblog2tag extends BaseObject {

	/**
	 * serial Version UID
	 */
	private static final long serialVersionUID = 2996880849005455187L;

	/**
	 * 마이크로블로깅 게시글 ID
	 */
	private String mblogId;

	/**
	 * 태그 아이디
	 */
	private String mbtagId;

	/**
	 * 포탈 ID
	 */
	private String portalId;
	
	public String getMblogId() {
		return mblogId;
	}

	public void setMblogId(String mblogId) {
		this.mblogId = mblogId;
	}

	public String getMbtagId() {
		return mbtagId;
	}

	public void setMbtagId(String mbtagId) {
		this.mbtagId = mbtagId;
	}
	
	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

}
