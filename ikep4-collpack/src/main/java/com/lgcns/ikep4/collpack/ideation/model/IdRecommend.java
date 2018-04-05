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
 * 아이디어 제안글 추천이력정보
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdRecommend.java 7008 2011-04-21 02:07:44Z loverfairy $
 */
public class IdRecommend extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -8510898902692416843L;

	/**
	 * 추천 등록자 ID
	 */
	private String registerId;

	/**
	 * 추천일시
	 */
    private Date registDate;

    /**
     *  아이디어 아이템(제안글) ID
     */
    private String itemId;

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

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

}