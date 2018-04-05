/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.model;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * Knowledge Monitor KnowledgeMonitorStatisticsPK model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorStatisticsPK.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class KnowledgeMonitorStatisticsPK extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -2806193452927789553L;

	/**
	 * 포탈_ID
	 */
	@NotEmpty
	private String portalId;

	/**
	 * 모듈 구분 (SB : BLOG, TC : TeamCOLL, QA : QNA, CV : DIC, WM : MANUAL, CF : CAFE, FR : FORUM, ID : IDEA, BD : BBS )
	 */
	@NotEmpty
	private String moduleCode;

	/**
	 * 등록일
	 */
	@NotEmpty
	private Date registDate;

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
	 * @return the moduleCode
	 */
	public String getModuleCode() {
		return moduleCode;
	}

	/**
	 * @param moduleCode the moduleCode to set
	 */
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
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
