/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.base.model;

/**
 * PortalCountMap model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: PortalCountMap.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class PortalCountMap {

	/**
	 * 포털 ID
	 */
	private String portalId;

	/**
	 * 개수
	 */
	private int count;

	/**
	 * Constructor
	 */
	public PortalCountMap() {
		
	}

	/**
	 * Constructor
	 * @param portalId
	 * @param count
	 */
	public PortalCountMap(String portalId, int count) {
		this.portalId = portalId;
		this.count = count;
	}

	/**
	 * @return the portalId
	 */
	public String getPortalId() {
		return portalId;
	}

	/**
	 * @param portalId the portalId to set
	 */
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

}
