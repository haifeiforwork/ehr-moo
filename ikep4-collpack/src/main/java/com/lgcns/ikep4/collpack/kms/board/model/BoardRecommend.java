/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.board.model;

import java.util.Date;

/**
 * 게시글 추천 모델 클래스
 *
 * @author ${user}
 * @version $$Id: BoardRecommend.java 16236 2011-08-18 02:48:22Z giljae $$
 */
public class BoardRecommend extends BoardRecommendPK { 
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 등록일자
	 */
	private Date registDate;

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	} 
}