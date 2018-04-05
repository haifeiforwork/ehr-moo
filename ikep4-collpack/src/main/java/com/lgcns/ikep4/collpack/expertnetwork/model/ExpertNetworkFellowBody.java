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
 * Expert Network ExpertFellowBody model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkFellowBody.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class ExpertNetworkFellowBody extends ExpertNetworkFellowPK {

	/**
	 *
	 */
	private static final long serialVersionUID = 3958772772804893324L;

	/**
	 * 매칭점수(전문분야Tag간의 매칭율) ( 0 ~ 100 )
	 */
	private int matchingScore;
	
	/**
	 * 정렬순서(1,2,....,10,11,,)
	 */
	private int sortOrder;

	/**
	 * 등록일시
	 */
	private Date registDate;

	
	/**
	 * @return the matchingScore
	 */
	public int getMatchingScore() {
		return matchingScore;
	}

	/**
	 * @param matchingScore the matchingScore to set
	 */
	public void setMatchingScore(int matchingScore) {
		this.matchingScore = matchingScore;
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

}
