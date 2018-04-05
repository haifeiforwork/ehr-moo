/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.board.model;

import com.lgcns.ikep4.support.user.group.model.Group;

/**
 * 게시판 관리 정보 입력용 Group 확장 클래스
 *
 * @author 최현식
 * @version $Id: BoardGroup.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class BoardGroup extends Group {

	private static final long serialVersionUID = -9070564411791344573L;

	private Integer hierarchied = 0;

	public Integer getHierarchied() {
		return this.hierarchied;
	}

	public void setHierarchied(Integer hierarchied) {
		this.hierarchied = hierarchied;
	}


}
