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
public class BoardAssessItem extends BaseObject {


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1035752613602705789L;

	/** 게시글 ID. */
	private String itemId;

	private int assessItem;
	
	private String assessName;

	private int assessMark;
	
	private int itemDisplay;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public int getAssessItem() {
		return assessItem;
	}

	public void setAssessItem(int assessItem) {
		this.assessItem = assessItem;
	}

	public String getAssessName() {
		return assessName;
	}

	public void setAssessName(String assessName) {
		this.assessName = assessName;
	}

	public int getAssessMark() {
		return assessMark;
	}

	public void setAssessMark(int assessMark) {
		this.assessMark = assessMark;
	}

	public int getItemDisplay() {
		return itemDisplay;
	}

	public void setItemDisplay(int itemDisplay) {
		this.itemDisplay = itemDisplay;
	}
}