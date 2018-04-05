/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.award.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 게시판 권한 레벨 모델 클래스
 *
 * @author 최현식
 * @version $Id: AwardPermission.java 16240 2011-08-18 04:08:15Z giljae $
 */
public class AwardPermission extends BaseObject {
	private static final long serialVersionUID = -1643621623251461860L;
	/** 시스템 관리자 */
	private Boolean isSystemAdmin = Boolean.FALSE;
	/** 쓰기 권한 */
	private Boolean isWritePermission = Boolean.FALSE;
	/** 읽기 권한 */
	private Boolean isReadPermission = Boolean.FALSE;

	public AwardPermission(Boolean isSystemAdmin, Boolean isWritePermission, Boolean isReadPermission) {
		super();
		this.isSystemAdmin = isSystemAdmin;
		this.isWritePermission = isWritePermission;
		this.isReadPermission = isReadPermission;
	}

	public Boolean getIsSystemAdmin() {
		return this.isSystemAdmin;
	}

	public Boolean getIsWritePermission() {
		return this.isWritePermission;
	}

	public Boolean getIsReadPermission() {
		return this.isReadPermission;
	}
}
