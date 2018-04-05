/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
//package com.lgcns.ikep4.support.common.sms.constants;
package com.lgcns.ikep4.support.sms.constants;

/**
 * TODO Javadoc주석작성
 * 
 * @author 서혜숙
 * @version $Id: SmsConstants.java 12858 2011-05-24 05:24:26Z shs0420 $
 */
public final class SmsConstants {

	private SmsConstants() {}
	
	/**
	 * SMS 문자 1건당 BYTE수
	 */
	public static final int MAX_SMS_LEN = 80;
	
	/**
	 * LMS 문자 1건당 BYTE수
	 */
	public static final int MAX_LMS_LEN = 2000;


	/**
	 * 한글 아스키코드값
	 */
	public static final int HAN_LIMIT = 256;

	/**
	 * 페이지당 레코드수(디폴트)
	 */
	public static final int LIST_SMS_PAGE_PER_RECORD = 10;
	
	/**
	 * 최근발송내역 레코드수
	 */
	public static final int RECENT_SMS_PAGE_PER_RECORD = 6;	
	
	/**
	 * 발신함 발송내역 레코드수(스크린방식)
	 */
	public static final int LIST_SMS_SCREEN_PAGE_PER_RECORD = 4;
	
	/**
	 * 페이지수
	 */
	public static final int SMS_PAGINATION_BLOCK = 5;
	
	/**
	 * SMS 서비스 코드
	 */
	public static final String SMS_SERVICE_CODE = "SMS01";
	
	/**
	 * LMS 서비스 코드
	 */
	public static final String LMS_SERVICE_CODE = "LMS01";
	
	/**
	 * SMS 메시지 타입
	 */
	public static final String SMS_MESSAGE_TYPE = "A";
	
	/**
	 * LMS 메시지 타입
	 */
	public static final String LMS_MESSAGE_TYPE = "M";
	
	
}
