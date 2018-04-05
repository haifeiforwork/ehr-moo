/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.board.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;


// TODO: Auto-generated Javadoc
/**
 * 게시글 모델 클래스.
 * 
 * @author ${user}
 * @version $$Id: BoardItem.java 16236 2011-08-18 02:48:22Z giljae $$
 */
public class BoardItemTarget extends BaseObject {

	private String targetGroupId;
	
	private String targetGroupName;

	public String getTargetGroupId() {
		return targetGroupId;
	}

	public void setTargetGroupId(String targetGroupId) {
		this.targetGroupId = targetGroupId;
	}

	public String getTargetGroupName() {
		return targetGroupName;
	}

	public void setTargetGroupName(String targetGroupName) {
		this.targetGroupName = targetGroupName;
	}
	
}