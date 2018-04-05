/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.model;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * Knowledge Monitor KnowledgeMonitorModulePK model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorModulePK.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class KnowledgeMonitorModulePK extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 402486244869327872L;

	/*
	 * Validation Group 정의
	 */
	public interface moduleManageUpdate {}  // 관리범위 설정
	public interface ContentPolicyUpdate {}  // CVI, 우수자료 기준관리
	public interface CommentPolicyUpdate {}  // 우수 Comment 기준관리

	/**
	 * 포탈 ID
	 */
	@NotEmpty(groups={moduleManageUpdate.class, ContentPolicyUpdate.class, ContentPolicyUpdate.class})
	private String portalId;

	/**
	 * 모듈 코드 (SB : BLOG, CO :COLLABORATION, QA : QNA, CV : DIC, WM : MANUAL, CF : CAFE, FR : FORUM, ID : IDEA, BD : BBS )
	 */
	@NotEmpty(groups={moduleManageUpdate.class, ContentPolicyUpdate.class, ContentPolicyUpdate.class})
	private String moduleCode;

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
