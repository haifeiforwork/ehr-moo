/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaReference.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class QnaReference extends BaseObject {
   
	/**
	 *
	 */
	private static final long serialVersionUID = 1687576387724431727L;

	/**
	 * 게시물 ID
     */
    private String qnaId;
    
    /**
     */
    private String registerId;
    
    
    /**
     */
    private Date registDate;
	
	public String getQnaId() {
		return qnaId;
	}

	public void setQnaId(String qnaId) {
		this.qnaId = qnaId;
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

	

    
}