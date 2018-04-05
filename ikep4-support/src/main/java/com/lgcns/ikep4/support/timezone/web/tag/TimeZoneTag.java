/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.timezone.web.tag;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * TimZone 관련 커스텀 태그 <br/>
 * 전달된 date를 사용자 타임존 시각으로 변환한다.
 * 
 * @author 주길재
 * @version $Id: TimeZoneTag.java 16276 2011-08-18 07:09:07Z giljae $
 */
public class TimeZoneTag extends RequestContextAwareTag {
	private static final long serialVersionUID = 7195830273886140850L;

	private Object date;

	private String pattern;

	private Boolean keyString;

	public void setDate(Object date) {
		this.date = date;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setKeyString(Boolean keyString) {
		this.keyString = keyString;
	}

	/**
	 * 유저 타임존 시각으로 변환한다.
	 */
	@Override
	protected int doStartTagInternal() {
		String resultDate = "";
		Date tmpDate = null;
		String patternString = "";

		// 세션에서 User 정보를 읽어온다.
		User user = this.getTimeDifference();

		// Time difference
		String timeDifference = user.getTimeDifference();
		String[] timeDifferences = timeDifference.split("\\.");
		// Integer timeDifference = Integer.parseInt(user.getTimeDifference());

		// Locale Code
		String localeCode = user.getLocaleCode();
		Locale locale = new Locale(localeCode, "");

		if (this.date == null) {
			// date object가 null이면 공백을 넘겨준다.
			return this.error();

		} else if (this.date instanceof String) {
			// pattern이 널이거나 공백이면 변환이 불가하므로 공백을 넘겨준다.
			if (this.pattern == null || "".equals(this.pattern)) {
				return this.error();
			}

			patternString = this.pattern;

			// keyString이 null이 아니고, true이면 리소스에서 날짜 패턴을 가져온다.
			if (this.keyString != null && this.keyString) {
				// patternString = this.messageSource.getMessage(this.pattern,
				// null, "yyyy.MM.dd", locale);
				patternString = this.getRequestContext().getMessage(this.pattern, "yyyy.MM.dd");
			}

			// pattern 형태로 넘어온 String타입의 날짜를 Date 타입으로 convert 한다.
			String[] parsePatterns = new String[1];
			parsePatterns[0] = patternString;
			
			try {
				tmpDate = DateUtils.parseDate(this.date.toString(), parsePatterns);
			}catch(ParseException pe) {
				throw new IKEP4ApplicationException("",pe);
			}

		} else if (this.date instanceof Date) {
			tmpDate = (Date) this.date;

		} else {
			// String, Date type도 아닌경우에는 공백을 넘겨준다.
			return this.error();
		}

		Date convertDate = DateUtils.addHours(tmpDate, (Integer.parseInt(timeDifferences[0]) * -1));

		if (timeDifferences.length > 1) {
			convertDate = DateUtils.addMinutes(convertDate, (Integer.parseInt(timeDifferences[1]) * -1));
		}

		patternString = getPattern(patternString);

		resultDate = DateFormatUtils.format(convertDate, patternString, locale);

		try {
			this.pageContext.getOut().print(resultDate);
		}catch (IOException ie) {
			throw new IKEP4ApplicationException("",ie);
		}

		return 0;
	}
	
	private String getPattern(String pattern) {
		String patternString = pattern;
		
		// pattern이 존재 하지 않으면, default 패턴으로 적용한다.
		if (this.pattern == null || "".equals(this.pattern)) {
			patternString = "yyyy.MM.dd";
		} else {
			// keyString이 null이 아니고, true이면 리소스에서 날짜 패턴을 가져온다.
			if (this.keyString != null && this.keyString) {
				// patternString = this.messageSource.getMessage(this.pattern,
				// null, "yyyy.MM.dd", locale);
				patternString = this.getRequestContext().getMessage(this.pattern, "yyyy.MM.dd");
			} else {
				patternString = this.pattern;
			}
		}
		
		return patternString;
	}

	private Integer error() {
		try {
			this.pageContext.getOut().print("");
		}catch (IOException ie) {
			throw new IKEP4ApplicationException("",ie);
		}
		return 0;
	}

	/**
	 * Get the time difference
	 * 
	 * @return
	 */
	private User getTimeDifference() {
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user",
				RequestAttributes.SCOPE_SESSION);

		if (user == null) {
			throw new IKEP4AjaxException("", new IllegalStateException());
		}

		if (!NumberUtils.isNumber(user.getTimeDifference())) {
			throw new IKEP4AjaxException("", new IllegalStateException());
		}

		if (user.getLocaleCode() == null || "".equals(user.getLocaleCode())) {
			throw new IKEP4AjaxException("", new IllegalStateException());
		}

		return user;

	}
}
