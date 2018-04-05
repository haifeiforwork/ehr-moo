/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.model;


/**
 * Knowledge Monitor KnowledgeMonitorCviPoint model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorCviPoint.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class KnowledgeMonitorCviPoint extends KnowledgeMonitorCviPointBody {

	/**
	 *
	 */
	private static final long serialVersionUID = 8956065185187142034L;

	/**
	 * 배포 수
	 */
	private int distributeCount;

	/**
	 * 순위
	 */
	private int rankNumber;

	/**
	 * 모듈명
	 */
	private String moduleName;

	/**
	 * 사용자명 (Locale 관련)
	 */
	private String userName;

	/**
	 * 사용자 영문명 (Locale 관련)
	 */
	private String userEnglishName;


	/**
	 * @return the distributeCount
	 */
	public int getDistributeCount() {
		return distributeCount;
	}

	/**
	 * @param distributeCount the distributeCount to set
	 */
	public void setDistributeCount(int distributeCount) {
		this.distributeCount = distributeCount;
	}

	/**
	 * @return the rankNumber
	 */
	public int getRankNumber() {
		return rankNumber;
	}

	/**
	 * @param rankNumber the rankNumber to set
	 */
	public void setRankNumber(int rankNumber) {
		this.rankNumber = rankNumber;
	}

	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @param moduleName the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userEnglishName
	 */
	public String getUserEnglishName() {
		return userEnglishName;
	}

	/**
	 * @param userEnglishName the userEnglishName to set
	 */
	public void setUserEnglishName(String userEnglishName) {
		this.userEnglishName = userEnglishName;
	}


}
