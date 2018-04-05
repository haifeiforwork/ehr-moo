/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.gallery.model;

import java.util.Date;

/**
 * 게시글 조회 모델 클래스
 *
 * @author ${user}
 * @version $$Id: BoardReference.java 5433 2011-04-07 04:39:12Z designtker $$
 */
public class BoardReference extends BoardReferencePK { 
	private static final long serialVersionUID = 2516675716690413604L;
	
	/**
	 * 등록일자
	 */
	private Date registDate;

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public Date getRegistDate() {
		return registDate;
	} 
 
}