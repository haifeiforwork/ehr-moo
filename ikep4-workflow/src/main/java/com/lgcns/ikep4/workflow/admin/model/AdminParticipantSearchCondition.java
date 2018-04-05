/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.model;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * 참여자 정보 조회
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminParticipantSearchCondition.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class AdminParticipantSearchCondition extends SearchCondition {

	private static final long serialVersionUID = 231767328317453351L;
	
	private String participantSearchTitle = "";//검색 타이틀(TextBox)
	
	public String getParticipantSearchTitle() {
		return participantSearchTitle;
	}
	public void setParticipantSearchTitle(String participantSearchTitle) {
		this.participantSearchTitle = participantSearchTitle;
	}
	
	@Override
	protected Integer getDefaultPagePerRecord() {
		return 10;
	}
}