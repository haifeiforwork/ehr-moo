/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 인기토론주제점수 정보
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrDiscussionScore.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class FrDiscussionScore extends BaseObject {
    /**
	 *
	 */
	private static final long serialVersionUID = -3665447194401476561L;

	/**
	 * 토론 주제 ID
	 */
	private String discussionId;

	/**
	 * 인기 토론 주제 점수
	 */
    private int discussionScore;

    public String getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(String discussionId) {
        this.discussionId = discussionId;
    }

    public int getDiscussionScore() {
        return discussionScore;
    }

    public void setDiscussionScore(int discussionScore) {
        this.discussionScore = discussionScore;
    }
}