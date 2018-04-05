/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.cafe.model;

/**
 * CafeConstants
 * 
 * @author 김종철
 * @version $Id: CafeConstants.java 16240 2011-08-18 04:08:15Z giljae $
 */
public class CafeConstants {

	/**
	 * 카페 상태
	 */
	public static final String CAFE_STATUS_WAITING_OPEN = "WO"; // 개설대기

	public static final String CAFE_STATUS_WAITING_REJECT = "WR"; // 개설 반려

	public static final String CAFE_STATUS_OPEN = "O"; // 개설

	public static final String CAFE_STATUS_WAITING_CLOSE = "WC"; // 폐쇄대기

	public static final String CAFE_STATUS_CLOSE = "C"; // 폐쇄

	/**
	 * 개설타입
	 */
	public static final int CAFE_OPEN_TYPE_USER = 0; // 사용자개설

	public static final int CAFE_OPEN_TYPE_ADM = 1; // 관리자개설

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

}
