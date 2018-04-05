/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * Expert Network ExpertPolicyPK model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkPolicyPK.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class ExpertNetworkPolicyPK extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 6857934411696612830L;

	/**
	 * 인기 전문가 정책 ID
	 */
	private String id;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

}
