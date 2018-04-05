/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgestreaming.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * Knowledge Monitor KnowledgeMonitorModule model
 * 
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorModule.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class KnowledgeUse extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7235259241543453871L;

	/**
	 * 파일 다운로드 카운트
	 */
	private int fileDownloadCount;

	/**
	 * 조횟수 카운트
	 */
	private int searchCount;

	/**
	 * 등록자
	 */
	private String registerId;

	/**
	 * 등록일
	 */
	private Date registDate;
	
	/**
	 * 등록일
	 */
	private String dd;

	public int getFileDownloadCount() {
		return fileDownloadCount;
	}

	public void setFileDownloadCount(int fileDownloadCount) {
		this.fileDownloadCount = fileDownloadCount;
	}

	public int getSearchCount() {
		return searchCount;
	}

	public void setSearchCount(int searchCount) {
		this.searchCount = searchCount;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public String getDd() {
		return dd;
	}

	public void setDd(String dd) {
		this.dd = dd;
	}

}
