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
 * Expert Network ExpertListBody model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkListBody.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class ExpertNetworkListBody extends ExpertNetworkListPK {

	/**
	 *
	 */
	private static final long serialVersionUID = -5827945027506855420L;

	/**
	 * 관리자 입력여부 ( 1:관리자직접입력, 0: 기타)<br/>
	 * 변경 - 관리자 입력여부 ( 0:관리자직접입력, 1: 배치입력, 2: 관리자해제)<br/>
	 * 사내인증전문가 ( matchingScore = 100 )
	 */
	private int isAuthorized;

	/**
	 * 매칭 점수 (0~100) 관리자 직접입력이면 100점으로 함
	 */
	private int matchingScore;

	/**
	 * 등록자 ID
	 */
	private String registerId;
	
	/**
	 * 등록자명
	 */
	private String registerName;

	/**
	 * 등록일
	 */
	private Date registDate;

	
	/**
	 * @return the isAuthorized
	 */
	public int getIsAuthorized() {
		return isAuthorized;
	}

	/**
	 * @param isAuthorized the isAuthorized to set
	 */
	public void setIsAuthorized(int isAuthorized) {
		this.isAuthorized = isAuthorized;
	}

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
	 * @return the registerName
	 */
	public String getRegisterName() {
		return registerName;
	}

	/**
	 * @param registerName the registerName to set
	 */
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
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
