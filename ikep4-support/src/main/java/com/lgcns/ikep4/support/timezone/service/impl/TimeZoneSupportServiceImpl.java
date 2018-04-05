/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.timezone.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.support.timezone.dao.TimeZoneSupportDao;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * TimeZoneSupportService 구현체
 * 
 * @author 최현식
 * @author modified by 주길재
 * @version $Id: TimeZoneSupportServiceImpl.java 6293 2011-04-14 08:35:32Z
 *          designtker $
 */
@Service
public class TimeZoneSupportServiceImpl implements TimeZoneSupportService {

	/** The time zone support dao. */
	@Autowired
	private TimeZoneSupportDao timeZoneSupportDao;

	@Autowired
	protected MessageSource messageSource;
	
	/**
	 * 현재 시스템 시간을 리턴한다.
	 * @return
	 */
	public Date currentServerTime() {
		Date currentServerTime = this.timeZoneSupportDao.getSystemDate();
		
		return currentServerTime;
	}
	
	/**
	 * 시스템 시각을 사용자 시간대로 변경. 로그인 사용자 모델 객체(세션변수에 저장되어 있는 모델 객체)에 TimezoneDiff에
	 * 의해서 계산된다.
	 * 
	 * @return 현재 시각을 사용자 시간대로 변경된 시각
	 */
	public Date convertTimeZone() {
		Date systemDate = this.timeZoneSupportDao.getSystemDate();
		Date convertDate = this.convertUserTime(systemDate, this.getTimeDifference());

		return convertDate;
	}

	/**
	 * 시스템 시간을 사용자 시간대로 변경 후 지정된 패턴에 의해서 문자열로 변경. 시스템 시각을 사용자 시간대로 변경 후 파라미터로
	 * 넘어오는 패턴에 의해서 문자열로 변경시킨다.
	 * 
	 * @param pattern 문자열로 변환용 패턴 ex) yyyy-MM-dd
	 * @return 현재 시각을 사용자 시간대로 변경된 시각
	 */
	public String convertTimeZoneToString(String pattern) {
		Date convertDate = this.convertTimeZone();
		return getDateFormat(convertDate, pattern);
	}

	/**
	 * 시스템 시각을 사용자 시간대로 변경 후 메세지 리소스에 지정된 패턴에 의해서 문자열로 변경. 시스템 시각을 사용자 시간대로 변경 후
	 * 파라미터로 넘어오는 패턴에 의해서 문자열로 변경시킨다.
	 * 
	 * @param keyName 메세지 리소스 키이름
	 * @return 현재 시각을 사용자 시간대로 변경된 시각
	 */
	public String convertTimeZoneToStringByPattern(String keyName) {
		String pattern = this.getPattern(keyName);
		Date convertDate = this.convertTimeZone();
		return getDateFormat(convertDate, pattern);
	}

	/**
	 * 사용자 시간대로 시각 변경 파라미터로 넘어오는 시각과 시간대 오프셋 그리고 로그인 사용자 모델 객체의 TimezoneDiff에
	 * 의해서 날짜를 변경시킨다.
	 * 
	 * @param date 변환 대상 시각
	 * @param pattern 문자열로 변환용 패턴 ex) yyyy-MM-dd
	 * @return 사용자 시간대로 변경된 시스템 시각
	 */
	public Date convertTimeZone(Date date) {
		return this.convertUserTime(date, this.getTimeDifference());
	}

	/**
	 * 사용자 시간대로 시각 변경 파라미터로 넘어오는 시각과 시간대 오프셋 그리고 로그인 사용자 모델 객체의 TimezoneDiff에
	 * 의해서 날짜를 변경시킨다.
	 * 
	 * @param date
	 * @param pattern
	 * @return 사용자 시간대로 변경된 시스템 시각
	 */
	public String convertTimeZoneToString(Date date, String pattern) {
		Date convertDate = this.convertTimeZone(date);
		return getDateFormat(convertDate, pattern);
	}

	/**
	 * 파라미터로 넘어오는 시각을 유저 타임존 시각으로 변경하고 메세지 리소스에 정의된 날짜 형태의 문자 형태로 반환한다.
	 * 
	 * @param date 변환 대상 시각
	 * @param keyName 메세지 리소스 키이름
	 * @return
	 */
	public String convertTimeZoneToStringByPattern(Date date, String keyName) {
		String pattern = this.getPattern(keyName);

		Date convertDate = this.convertTimeZone(date);
		return getDateFormat(convertDate, pattern);
	}

	/**
	 * 사용자 시간대로 시각 변경 파라미터로 넘어오는 시각과 시간대 오프셋 그리고 로그인 사용자 모델 객체의 TimezoneDiff에
	 * 의해서 날짜를 변경시킨다. localeCode에 의해서 요일을 표기한다.
	 * 
	 * @param date
	 * @param pattern
	 * @param localeCode
	 * @return 사용자 시간대로 변경된 시스템 시각
	 */
	public String convertTimeZoneToString(Date date, String pattern, String localeCode) {
		Date convertDate = this.convertTimeZone(date);
		return getDateFormat(convertDate, pattern, localeCode);
	}

	/**
	 * 사용자 시간대로 시각 변경 파라미터로 넘어오는 시각과 시간대 오프셋 그리고 로그인 사용자 모델 객체의 TimezoneDiff에
	 * 의해서 날짜를 변경시킨다.
	 * 
	 * @param date 변환 대상 시각
	 * @param pattern
	 */
	public Date convertTimeZone(String date, String pattern) {
		try {
			String[] parsePatterns = new String[1];
			parsePatterns[0] = pattern;
			Date parseDate = DateUtils.parseDate(date, parsePatterns);
			Date convertDate = this.convertUserTime(parseDate, this.getTimeDifference());

			return convertDate;

		} catch (ParseException ex) {
			throw new IKEP4ApplicationException(ex.getMessage(), ex);
		}
	}

	/**
	 * 사용자 시간대로 시각 변경 파라미터로 넘어오는 시각과 시간대 오프셋 그리고 로그인 사용자 모델 객체의 TimezoneDiff에
	 * 의해서 날짜를 변경시킨다.
	 * 
	 * @param date 변환 대상 시각 ex)2011-04-14
	 * @param pattern 문자열로 변환용 패턴 ex) yyyy-MM-dd
	 * @return
	 */
	public String convertTimeZoneToString(String date, String pattern) {
		Date convertDate = this.convertTimeZone(date, pattern);

		return getDateFormat(convertDate, pattern);
	}

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
	public String convertTimeZoneToString(String date, String pattern, String localeCode) {
		Date convertDate = this.convertTimeZone(date, pattern);

		return getDateFormat(convertDate, pattern, localeCode);
	}

	/**
	 * 사용자 시간대를 서버 시간대로 변경 한다.
	 * 
	 * @param date 변환 대상 사용자 시각
	 * @return Date
	 */
	public Date convertServerTimeZone(Date date) {
		Date convertDate = this.convertServerTime(date, this.getTimeDifference());

		return convertDate;
	}

	/**
	 * 사용자 시간대를 서버 시간대로 변경 한다.
	 * 
	 * @param date 변환 대상 사용자 시각
	 * @return Date
	 */
	public Date convertServerTimeZoneEndDate(Date date, String useSpace) {
		Date convertDate = this.convertServerTime(date, this.getTimeDifference(), useSpace);

		return convertDate;
	}
	
	/**
	 * 사용자 시간대를 서버 시간대로 변경 한다.
	 * 
	 * @param date 변환 대상 사용자 시각
	 * @param pattern 전달된 String 타입의 날짜 패턴
	 * @return Date
	 */
	public Date convertServerTimeZone(String date, String pattern) {
		try {
			String[] parsePatterns = new String[1];
			parsePatterns[0] = pattern;

			Date parseDate = DateUtils.parseDate(date, parsePatterns);

			Date convertDate = this.convertServerTime(parseDate, this.getTimeDifference());

			return convertDate;
		} catch (ParseException ex) {
			throw new IKEP4ApplicationException(ex.getMessage(), ex);
		}
	}

	/**
	 * 사용자 시간대를 서버 시간대로 변경 한다.
	 * 
	 * @param date 변환 대상 사용자 시각
	 * @param pattern 변환 대상 사용자 시각의 패턴 ex) yyyy-MM-dd
	 * @return String
	 */
	public String convertServerTimeZoneToString(Date date, String pattern) {
		Date convertDate = this.convertServerTimeZone(date);

		return getDateFormat(convertDate, pattern);
	}

	/**
	 * 사용자 시간대를 서버 시간대로 변경 한다.
	 * 
	 * @param date 변환 대상 사용자 시각
	 * @param keyName 메세지 리소스 키이름
	 * @return String
	 */
	public String convertServerTimeZoneToStringByPattern(Date date, String keyName) {
		Date convertDate = this.convertServerTimeZone(date);

		String pattern = this.getPattern(keyName);

		return getDateFormat(convertDate, pattern);
	}

	/**
	 * 사용자 시간대를 서버 시간대로 변경 한다. <br/>
	 * localeCode에 의해서 요일을 표기한다.
	 * 
	 * @param date 변환 대상 사용자 시각
	 * @param pattern 변환 대상 사용자 시각의 패턴 ex) yyyy-MM-dd
	 * @param localeCode en, ko 로케일 코드
	 * @return String
	 */
	public String convertServerTimeZoneToString(Date date, String pattern, String localeCode) {
		Date convertDate = this.convertServerTimeZone(date);

		return getDateFormat(convertDate, pattern, localeCode);
	}

	/**
	 * 사용자 시간대를 서버 시간대로 변경한다.
	 * 
	 * @param date 변환 대상 사용자 시각 ex)2011-04-14
	 * @param pattern 변환 대상 사용자 시각의 패턴 ex) yyyy-MM-dd
	 * @return String
	 */
	public String convertServerTimeZoneToString(String date, String pattern) {
		try {
			String[] parsePatterns = new String[1];
			parsePatterns[0] = pattern;
			Date parseDate = DateUtils.parseDate(date, parsePatterns);
			Date convertDate = this.convertServerTimeZone(parseDate);

			return getDateFormat(convertDate, pattern);

		} catch (ParseException ex) {
			throw new IKEP4ApplicationException(ex.getMessage(), ex);
		}
	}

	/**
	 * 사용자 시간대를 서버 시간대로 변경한다. <br/>
	 * localeCode에 의해서 요일을 표기한다.
	 * 
	 * @param date 변환 대상 사용자 시각 ex)2011-04-14
	 * @param pattern 변환 대상 사용자 시각의 패턴 ex) yyyy-MM-dd
	 * @param localeCode en, ko 로케일 코드
	 * @return String
	 */
	public String convertServerTimeZoneToString(String date, String pattern, String localeCode) {
		try {
			String[] parsePatterns = new String[1];
			parsePatterns[0] = pattern;
			Date parseDate = DateUtils.parseDate(date, parsePatterns);
			Date convertDate = this.convertServerTimeZone(parseDate);

			return getDateFormat(convertDate, pattern, localeCode);

		} catch (ParseException ex) {
			throw new IKEP4ApplicationException(ex.getMessage(), ex);
		}
	}

	/**
	 * Gets the time difference. <br/>
	 * 세션에서 유저의 time difference를 읽어온다.
	 * 
	 * @return the time difference
	 */
	private String getTimeDifference() {
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user",
				RequestAttributes.SCOPE_SESSION);

		// User 객체가 존재하지 않을 경우,
		if (user == null) {
			throw new IKEP4ApplicationException("", new IllegalStateException());
		}

		// time difference가 존재하지 않을 경우,
		if (user.getTimeDifference() == null) {
			throw new IKEP4ApplicationException("", new IllegalStateException());
		}

		String timeDifference = user.getTimeDifference();

		return timeDifference;
	}

	/**
	 * 메세지 리소스에 저장된 패턴 정보를 반환한다.
	 * 
	 * @return
	 */
	private String getPattern(String keyName) {
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user",
				RequestAttributes.SCOPE_SESSION);

		// User 객체가 존재하지 않을 경우,
		if (user == null) {
			throw new IKEP4ApplicationException("", new IllegalStateException());
		}

		// user locale code가 존재하지 않을 경우,
		if (user.getLocaleCode() == null) {
			throw new IKEP4ApplicationException("", new IllegalStateException());
		}

		Locale userLocale = new Locale(user.getLocaleCode(), "");
		String pattern = messageSource.getMessage(keyName, null, userLocale);

		return pattern;
	}

	/**
	 * Date 파라메터를 timeDifference를 적용하여 convert한다.
	 * 
	 * @param date
	 * @param timeDifference
	 * @return
	 */
	private Date convertUserTime(Date date, String timeDifference) {
		String[] timeDifferences = timeDifference.split("\\.");
		Date convertDate = DateUtils.addHours(date, (Integer.parseInt(timeDifferences[0]) * -1));
		if (timeDifferences.length > 1) {
			convertDate = DateUtils.addMinutes(convertDate, (Integer.parseInt(timeDifferences[1]) * -1));
		}
		return convertDate;
	}

	/**
	 * 유저타임존 Date 파라메터를 timeDifference를 적용하여 서버 타임존으로 convert 한다.
	 * 
	 * @param date
	 * @param timeDifference
	 * @return
	 */
	private Date convertServerTime(Date date, String timeDifference) {
		String[] timeDifferences = timeDifference.split("\\.");
		Date convertDate = DateUtils.addHours(date, (Integer.parseInt(timeDifferences[0])));

		if (timeDifferences.length > 1) {
			convertDate = DateUtils.addMinutes(convertDate, (Integer.parseInt(timeDifferences[1])));
		}

		return convertDate;
	}

	/**
	 * 유저타임존 Date 파라메터를 timeDifference를 적용하여 서버 타임존으로 convert 한다.
	 * 
	 * @param date
	 * @param timeDifference
	 * @return
	 */
	private Date convertServerTime(Date date, String timeDifference, String useSpace) {
		
		if(useSpace.equals("survey")){
			timeDifference = "23.59";
		}
		
		String[] timeDifferences = timeDifference.split("\\.");
		Date convertDate = DateUtils.addHours(date, (Integer.parseInt(timeDifferences[0])));

		if (timeDifferences.length > 1) {
			convertDate = DateUtils.addMinutes(convertDate, (Integer.parseInt(timeDifferences[1])));
		}

		return convertDate;
	}
	
	private String getDateFormat(Date convertDate, String pattern) {
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user",
				RequestAttributes.SCOPE_SESSION);

		// User 객체가 존재하지 않을 경우,
		if (user == null) {
			throw new IKEP4ApplicationException("", new IllegalStateException());
		}

		// time difference가 존재하지 않을 경우,
		if (user.getLocaleCode() == null) {
			throw new IKEP4ApplicationException("", new IllegalStateException());
		}

		String localeCode = user.getLocaleCode();

		Locale locale = new Locale(localeCode);

		return DateFormatUtils.format(convertDate, pattern, locale);
	}

	private String getDateFormat(Date convertDate, String pattern, String localeCode) {
		Locale locale = new Locale(localeCode);

		return DateFormatUtils.format(convertDate, pattern, locale);
	}

}
