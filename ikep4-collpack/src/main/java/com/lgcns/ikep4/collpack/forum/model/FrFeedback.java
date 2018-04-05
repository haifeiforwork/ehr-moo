/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 토론찬반이력 정보
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrFeedback.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class FrFeedback extends BaseObject {
    /**
	 *
	 */
	private static final long serialVersionUID = -4386274249949759829L;

	/**
	 * 토론글 피드백 타입( 0 :  찬성, 1 : 반대)
	 */
	private String feedbackType;

	/**
	 * 토론글 아이템 ID
	 */
    private String itemId;

    /**
     * 찬성반대 등록자 ID
     */
    private String registerId;
    
    /**
     * 찬성반대 등록일시
     */
    private Date registDate;
    
    /**
     * 주제 ID
     */
    private String discussionId;

	public String getFeedbackType() {
		return feedbackType;
	}

	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}

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

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public String getDiscussionId() {
		return discussionId;
	}

	public void setDiscussionId(String discussionId) {
		this.discussionId = discussionId;
	}

    
}