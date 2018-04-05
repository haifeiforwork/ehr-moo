/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.portlet.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * Knowledge Monitor KnowledgeMonitorPortletSearchCondition model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorPortletSearchCondition.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class KnowledgeMonitorPortletSearchCondition extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 3875238062693701965L;

	/**
	 * 포털ID
	 */
	private String portalId;

	/**
	 * 데이터 개수
	 */
	private int recordCount;

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
	 * @return the recordCount
	 */
	public int getRecordCount() {
		return recordCount;
	}

	/**
	 * @param recordCount the recordCount to set
	 */
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

}
