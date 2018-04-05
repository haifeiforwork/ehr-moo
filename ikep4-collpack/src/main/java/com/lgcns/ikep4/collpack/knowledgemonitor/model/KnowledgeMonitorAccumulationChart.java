/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * Knowledge Monitor KnowledgeMonitorAccumulationChart model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorAccumulationChart.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class KnowledgeMonitorAccumulationChart extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -2577316811424740159L;

	/**
	 * 기준 (월별:년-월, 주별:년-월-일)
	 */
	private String ymd;

	/**
	 * 공개 항목수
	 */
	private int publicDocCount;

	/**
	 * 전체 항목수
	 */
	private int allDocCount;

	/**
	 * @return the ymd
	 */
	public String getYmd() {
		return ymd;
	}

	/**
	 * @param ymd the ymd to set
	 */
	public void setYmd(String ymd) {
		this.ymd = ymd;
	}

	/**
	 * @return the publicDocCount
	 */
	public int getPublicDocCount() {
		return publicDocCount;
	}

	/**
	 * @param publicDocCount the publicDocCount to set
	 */
	public void setPublicDocCount(int publicDocCount) {
		this.publicDocCount = publicDocCount;
	}

	/**
	 * @return the allDocCount
	 */
	public int getAllDocCount() {
		return allDocCount;
	}

	/**
	 * @param allDocCount the allDocCount to set
	 */
	public void setAllDocCount(int allDocCount) {
		this.allDocCount = allDocCount;
	}
}
