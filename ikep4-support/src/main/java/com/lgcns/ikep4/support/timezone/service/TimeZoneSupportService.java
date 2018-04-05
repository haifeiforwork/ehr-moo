/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.timezone.service;

import java.util.Date;


/**
 * TimeZoneSupportService 인터페이스
 * 
 * @author 최현식
 * @author modified by 주길재
 * @version $Id: TimeZoneSupportService.java 6293 2011-04-14 08:35:32Z
 *          designtker $
 */
public interface TimeZoneSupportService {
	
	/**
	 * 현재 시스템 시간을 리턴한다.
	 * @return
	 */
	public Date currentServerTime();

	/**
	 * 시스템 시각을 사용자 시간대로 변경. 로그인 사용자 모델 객체(세션변수에 저장되어 있는 모델 객체)에 TimezoneDiff에
	 * 의해서 계산된다.
	 * 
	 * @return 현재 시각을 사용자 시간대로 변경된 시각
	 */
	public Date convertTimeZone();

	/**
	 * 시스템 시각을 사용자 시간대로 변경 후 지정된 패턴에 의해서 문자열로 변경. 시스템 시각을 사용자 시간대로 변경 후 파라미터로
	 * 넘어오는 패턴에 의해서 문자열로 변경시킨다.
	 * 
	 * @param pattern 문자열로 변환용 패턴 ex) yyyy-MM-dd
	 * @return 현재 시각을 사용자 시간대로 변경된 시각
	 */
	public String convertTimeZoneToString(String pattern);

	/**
	 * 시스템 시각을 사용자 시간대로 변경 후 메세지 리소스에 지정된 패턴에 의해서 문자열로 변경.
	 * 
	 * @param keyName 메세지 리소스 키이름
	 * @return 현재 시각을 사용자 시간대로 변경된 시각
	 */
	public String convertTimeZoneToStringByPattern(String keyName);

	/**
	 * 사용자 시간대로 시각 변경 파라미터로 넘어오는 시각과 시간대 오프셋 그리고 로그인 사용자 모델 객체의 TimezoneDiff에
	 * 의해서 날짜를 변경시킨다.
	 * 
	 * @param date 변환 대상 시각
	 * @return 사용자 시간대로 변경된 시스템 시각
	 */
	public Date convertTimeZone(Date date);

	/**
	 * 사용자 시간대로 시각 변경 파라미터로 넘어오는 시각과 시간대 오프셋 그리고 로그인 사용자 모델 객체의 TimezoneDiff에
	 * 의해서 날짜를 변경시킨다.
	 * 
	 * @param date 변환 대상 시각
	 * @param pattern 문자열로 변환용 패턴 ex) yyyy-MM-dd
	 * @return
	 */
	public String convertTimeZoneToString(Date date, String pattern);

	/**
	 * 파라미터로 넘어오는 시각을 유저 타임존 시각으로 변경하고 메세지 리소스에 정의된 날짜 형태의 문자 형태로 반환한다.
	 * 
	 * @param date 변환 대상 시각
	 * @param keyName 메세지 리소스 키이름
	 * @return
	 */
	public String convertTimeZoneToStringByPattern(Date date, String keyName);

	/**
	 * 사용자 시간대로 시각 변경 파라미터로 넘어오는 시각과 시간대 오프셋 그리고 로그인 사용자 모델 객체의 TimezoneDiff에
	 * 의해서 날짜를 변경시킨다. localeCode에 의해서 요일을 표기한다.
	 * 
	 * @param date
	 * @param pattern
	 * @param localeCode
	 * @return 사용자 시간대로 변경된 시스템 시각
	 */
	public String convertTimeZoneToString(Date date, String pattern, String localeCode);

	/**
	 * 사용자 시간대로 시각 변경 파라미터로 넘어오는 시각과 시간대 오프셋 그리고 로그인 사용자 모델 객체의 TimezoneDiff에
	 * 의해서 날짜를 변경시킨다.
	 * 
	 * @param date 변환 대상 시각 ex)2011-04-14
	 * @param pattern 변환 대상 시각의 날짜 패턴 ex)yyyy-MM-dd
	 * @return
	 */
	public Date convertTimeZone(String date, String pattern);

	/**
	 * 사용자 시간대로 시각 변경 파라미터로 넘어오는 시각과 시간대 오프셋 그리고 로그인 사용자 모델 객체의 TimezoneDiff에
	 * 의해서 날짜를 변경시킨다.
	 * 
	 * @param date 변환 대상 시각 ex)2011-04-14
	 * @param pattern 변환 대상 시각의 날짜 패턴 ex) yyyy-MM-dd
	 * @return
	 */
	public String convertTimeZoneToString(String date, String pattern);

	/**
	 * 사용자 시간대로 시각 변경 파라미터로 넘어오는 시각과 시간대 오프셋 그리고 로그인 사용자 모델 객체의 TimezoneDiff에
	 * 의해서 날짜를 변경시킨다. <br/>
	 * localeCode에 의해서 요일을 표기한다.
	 * 
	 * @param date 변환 대상 시각 ex)2011-04-14
	 * @param pattern 문자열로 변환용 패턴 ex) yyyy-MM-dd EEE
	 * @param localeCode en, ko 로케일 코드
	 * @return
	 */
	public String convertTimeZoneToString(String date, String pattern, String localeCode);

	/**
	 * 사용자 시간대를 서버 시간대로 변경 한다.
	 * 
	 * @param date 변환 대상 사용자 시각
	 * @return Date
	 */
	public Date convertServerTimeZone(Date date);

	/**
	 * 사용자 시간대를 서버 시간대로 변경 한다.
	 * 
	 * @param date 변환 대상 사용자 시각
	 * @param pattern 전달된 String 타입의 날짜 패턴 ex) yyyy.MM.dd
	 * @return Date
	 */
	public Date convertServerTimeZone(String date, String pattern);
	
	/**
	 * 사용자 시간대를 서버 시간대로 변경 한다.
	 * 
	 * @param date 변환 대상 사용자 시각
	 * @param useSpace 사용하는 곳이 어떤곳
	 * @return Date
	 */
	/* Start C20111005_74756 */
	public Date convertServerTimeZoneEndDate(Date date, String useSpace);
	/* End C20111005_74756 */
	
	/**
	 * 사용자 시간대를 서버 시간대로 변경 한다.
	 * 
	 * @param date 변환 대상 사용자 시각
	 * @param pattern 변환 대상 사용자 시각의 패턴 ex) yyyy-MM-dd
	 * @return String
	 */
	public String convertServerTimeZoneToString(Date date, String pattern);

	/**
	 * 사용자 시간대를 서버 시간대로 변경 하고 메세지 리소스의 날짜 패턴 형태의 문자 타입으로 반환한다.
	 * 
	 * @param date 변환 대상 사용자 시각
	 * @param keyName 메세지 리소스 키이름
	 * @return String
	 */
	public String convertServerTimeZoneToStringByPattern(Date date, String keyName);

	/**
	 * 사용자 시간대를 서버 시간대로 변경 한다. <br/>
	 * localeCode에 의해서 요일을 표기한다.
	 * 
	 * @param date 변환 대상 사용자 시각
	 * @param pattern 변환 대상 사용자 시각의 패턴 ex) yyyy-MM-dd
	 * @param localeCode en, ko 로케일 코드
	 * @return String
	 */
	public String convertServerTimeZoneToString(Date date, String pattern, String localeCode);

	/**
	 * 사용자 시간대를 서버 시간대로 변경한다.
	 * 
	 * @param date 변환 대상 사용자 시각 ex)2011-04-14
	 * @param pattern 변환 대상 사용자 시각의 패턴 ex) yyyy-MM-dd
	 * @return String
	 */
	public String convertServerTimeZoneToString(String date, String pattern);

	/**
	 * 사용자 시간대를 서버 시간대로 변경한다. <br/>
	 * localeCode에 의해서 요일을 표기한다.
	 * 
	 * @param date 변환 대상 사용자 시각 ex)2011-04-14
	 * @param pattern 변환 대상 사용자 시각의 패턴 ex) yyyy-MM-dd
	 * @param localeCode en, ko 로케일 코드
	 * @return String
	 */
	public String convertServerTimeZoneToString(String date, String pattern, String localeCode);
}
