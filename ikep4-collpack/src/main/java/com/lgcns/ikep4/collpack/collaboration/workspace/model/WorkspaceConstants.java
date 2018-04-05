/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.workspace.model;

/**
 * TODO Javadoc주석작성
 * 
 * @author 김종철
 * @version $Id: WorkspaceConstants.java 16236 2011-08-18 02:48:22Z giljae $
 */
public final class WorkspaceConstants {

	private WorkspaceConstants() {
		throw new AssertionError();
	}
	
	public static final String ACL_CLASS_NAME = "Collaboration";
	public static final String ACL_RESOURCE_NAME = "Collaboration";
	public static final String ACL_OPERATION_NAME = "MANAGE";	
	/**
	 * Collaboration 권한그룹(IKEP4_EV_GROUP_TYPE)
	 */
	public static final String GROUP_TYPE_ID = "G0002";

	public static final String ITEM_TYPE_NAME = "WS";

	public static final String WS_PERMISSION_GROUP_MANAGE = "운영진";

	public static final String WS_PERMISSION_GROUP_MEMBER = "정회원";

	public static final String WS_PERMISSION_GROUP_ASSOCIATE = "준회원";

	/**
	 * 워크스페이스 상태
	 */
	public static final String WORKSPACE_STATUS_WAITING_OPEN = "WO"; // 개설대기

	public static final String WORKSPACE_STATUS_WAITING_REJECT = "WR"; // 개설 반려

	public static final String WORKSPACE_STATUS_OPEN = "O"; // 개설

	public static final String WORKSPACE_STATUS_WAITING_CLOSE = "WC"; // 폐쇄대기

	public static final String WORKSPACE_STATUS_CLOSE = "C"; // 폐쇄

	/**
	 * 워크스페이스 개설타입
	 */
	public static final int WORKSPACE_OPEN_TYPE_USER = 0; // 사용자개설

	public static final int WORKSPACE_OPEN_TYPE_ADM = 1; // 관리자개설

	/**
	 * 멤버레벨
	 */
	public static final String MEMBER_STATUS_SYSOP = "1"; // 시샵

	public static final String MEMBER_STATUS_ADMIN = "2"; // 운영진

	public static final String MEMBER_STATUS_MEMBER = "3"; // 정회원

	public static final String MEMBER_STATUS_ASSOCIATE = "4"; // 준회원

	public static final String MEMBER_STATUS_WAIT = "5"; // 가입대기

	/**
	 * 멤버 가입타입
	 */
	public static final String MEMBER_JOIN_TYPE_USER = "0"; // 사용자 가입

	public static final String MEMBER_JOIN_TYPE_ADM = "1"; // 시샵 가입

	/**
	 * 동맹 상태
	 */
	public static final String ALLIANCE_STATUS_WAITING = "0"; // 동맹요청

	public static final String ALLIANCE_STATUS_OPEN = "1"; // 동맹승인

	public static final String ALLIANCE_STATUS_REJECT = "2"; // 동맹거부

	public static final String ALLIANCE_STATUS_CLOSE = "3"; // 동맹종료

	/**
	 * 워크스페이스 타입 조직여부
	 */
	public static final int TYPE_ORANIZATION_FLAG = 0; // 기타 타입
}
