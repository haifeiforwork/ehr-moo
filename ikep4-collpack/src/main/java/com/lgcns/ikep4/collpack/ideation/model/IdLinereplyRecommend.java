/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import java.util.Date;

/**
 * 토론의견추천이력 정보
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdLinereplyRecommend.java 7008 2011-04-21 02:07:44Z loverfairy $
 */
public class IdLinereplyRecommend extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -377084830041350476L;

	/**
	 * 추천 등록자 ID
	 */
	private String registerId;

	/**
	 * 추천일시
	 */
    private Date registDate;

    /**
     * 토론 의견(댓글) ID
     */
    private String linereplyId;

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

    public String getLinereplyId() {
        return linereplyId;
    }

    public void setLinereplyId(String linereplyId) {
        this.linereplyId = linereplyId;
    }
}