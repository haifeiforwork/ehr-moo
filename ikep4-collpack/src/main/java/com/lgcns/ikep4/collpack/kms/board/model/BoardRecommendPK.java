/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.board.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 게시글 추천 PK 모델 클래스
 *
 * @author ${user}
 * @version $$Id: BoardRecommendPK.java 16236 2011-08-18 02:48:22Z giljae $$
 */
public class BoardRecommendPK extends BaseObject { 
	
	private static final long serialVersionUID = -7045230381013541572L;

	/**
	 * 아이템 ID
	 */
	private String itemId;
	
	/**
	 * 등록자 ID
	 */
	private String registerId;
	
	private String score;
	
	private String flag;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	} 
}