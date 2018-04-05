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
 * 인기토론글점수 정보
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrItemScore.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class FrItemScore extends BaseObject {
    /**
	 *
	 */
	private static final long serialVersionUID = -3903187962661617951L;

	/**
	 * 토론글 아이템 ID
	 */
	private String itemId;

	/**
	 * 인기 토론글 점수
	 */
    private int itemScore;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getItemScore() {
        return itemScore;
    }

    public void setItemScore(int itemScore) {
        this.itemScore = itemScore;
    }
}