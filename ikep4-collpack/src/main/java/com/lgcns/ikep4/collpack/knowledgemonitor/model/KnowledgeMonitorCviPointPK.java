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
 * Knowledge Monitor KnowledgeMonitorCviPointPK model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorCviPointPK.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class KnowledgeMonitorCviPointPK extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -1485251694218123574L;

	/**
	 * 게시물의 아이템ID
	 */
	@NotEmpty
	private String itemId;

	/**
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

}
