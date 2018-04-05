/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.model;

import java.util.Date;


/**
 * Expert Network ExpertPopularBody model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkPopularBody.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class ExpertNetworkPopularBody extends ExpertNetworkPopularPK {

	/**
	 *
	 */
	private static final long serialVersionUID = 6822066762052468857L;

	/**
	 * 선정 시 기준이 되는 점수(별도 정책에 의해 책정-숫자가 클수록 인기있음)
	 */
	private int score;

	/**
	 * 정렬순서(1,2,....,10,11,,)
	 */
	private int sortOrder;

	/**
	 * 포탈 ID
	 */
	private String portalId;

	/**
	 * 등록일
	 */
	private Date registDate;

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return the sortOrder
	 */
	public int getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * @return the registDate
	 */
	public Date getRegistDate() {
		return registDate;
	}

	/**
	 * @param registDate the registDate to set
	 */
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
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

}
