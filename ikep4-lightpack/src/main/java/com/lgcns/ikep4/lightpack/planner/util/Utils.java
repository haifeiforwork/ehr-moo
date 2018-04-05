/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.web.context.ContextLoader;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//
//import com.lgcns.ikep4.lightpack.planner.web.CalendarController;
//import com.lgcns.ikep4.util.PropertyLoader;
//import com.lgcns.ikep4.portal.main.model.Portal;

/**
 * date 관련 util method
 *
 * @author  신용환(combinet@gmail.com)
 * @version $Id: Utils.java 17315 2012-02-08 04:56:13Z yruyo $
 */
public class Utils {
	@SuppressWarnings("unused")
	private static String PATTERN_DATE = "yyyyMMdd";
	
	private static String PATTERN_DATE_TIME = "yyyyMMddHHmm";

	@SuppressWarnings("unused")
	private static String[] parsePatterns = new String[] {"yyyyMMddHHmm", "yyyy-MM-dd", "yyyyMMdd"};
	
	private static long MILLSECS_PER_DAY = 1000 * 60 * 60 * 24;
	
	private static FastDateFormat fdf = FastDateFormat.getInstance(PATTERN_DATE_TIME);
	
	public static int getDow(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		
		return c.get(Calendar.DAY_OF_WEEK);
	}
	
	public static Date getDowDate(Date d, int dow) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		
		c.set(Calendar.DAY_OF_WEEK, dow);
		return c.getTime();
	}
	
	public static Date getSameDowDate(Date base, Date target) {
		int baseDow = getDow(base);
		int targetDow = getDow(target);
		
		int diff = baseDow - targetDow;
		
		return DateUtils.addDays(target, diff);
	}
	
	public static int getDayDiff(Date a, Date b) {
		long d = DateUtils.truncate(a, Calendar.DATE).getTime() - 
			DateUtils.truncate(b, Calendar.DATE).getTime();
		return (int) (d / MILLSECS_PER_DAY);
	}
	
	public static String addDateTimeByStr(Date dateSrc, Date timeSrc) {
		Calendar c = addDateTimeByCal(dateSrc, timeSrc);
		return fdf.format(c);
	}
	
	public static String addDateTimeByStr(Date dateSrc, Date timeSrc, Date dstart, Date dend) throws ParseException {
		long diff = dend.getTime() - dstart.getTime();
		Calendar c = addDateTimeByCal(dateSrc, timeSrc);
		c.setTimeInMillis(c.getTimeInMillis() + diff);
		c.getTime();
		return fdf.format(c);
	}
	
	public static Date addDateTime(Date dateSrc, Date timeSrc, Date dstart, Date dend) throws ParseException {
		long diff = dend.getTime() - dstart.getTime();
		Calendar c = addDateTimeByCal(dateSrc, timeSrc);
		c.setTimeInMillis(c.getTimeInMillis() + diff);
		return c.getTime();
	}
	
	public static Calendar addDateTimeByCal(Date dateSrc, Date timeSrc) {
		Calendar c = Calendar.getInstance();
		Calendar s = Calendar.getInstance();
		
		c.clear();
		c.setTime(dateSrc);
		
		s.setTime(timeSrc);
		c.set(Calendar.HOUR_OF_DAY, s.get(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE, s.get(Calendar.MINUTE));
		
		return c;
	}
	
	public static Date addDateTime(Date dateSrc, Date timeSrc) {
		Calendar c = Calendar.getInstance();
		Calendar s = Calendar.getInstance();
		
		c.clear();
		c.setTime(dateSrc);
		
		s.setTime(timeSrc);
		c.set(Calendar.HOUR_OF_DAY, s.get(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE, s.get(Calendar.MINUTE));
		
		return c.getTime();
	}

	/**
	 * roption에 cpdate의 요일이 포함 되어있는지 여부
	 * @param cpdate
	 * @param roption
	 * @return
	 */
	public static boolean isContainDow(Date cpdate, String roption) {
		if(roption == null) {
			return false; // 비정상적인 경우
		}
		int dow = getDow(cpdate);
		String dowStr = String.valueOf(dow);
		return roption.indexOf(dowStr) > -1 ? true : false;
	}

	/**
	 * 두 날짜의 월 차를 구함
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int betweenMonth(Date from, Date to) {
		Calendar c = Calendar.getInstance();
		c.setTime(from);
		int fromYear = c.get(Calendar.YEAR);
		int fromMonth = c.get(Calendar.MONTH);
		
		c.setTime(to);
		int toYear = c.get(Calendar.YEAR);
		int toMonth = c.get(Calendar.MONTH);
		
		return (toYear - fromYear) * 12 + toMonth - fromMonth;
	}
	
	/**
	 * 해당일(CUR_DATE) 월에서 마지막 요일(TARGET_DAY)의 일자를 구함
	 * @param cdate
	 * @param dow 1~7(일 ~ 토)
	 * @return
	 */
	public static Date getLastDateByDow(Date cdate, int dow) {
		Calendar c = Calendar.getInstance();
		c.setTime(cdate);
		
		int last = c.getActualMaximum(Calendar.DATE);
		c.set(Calendar.DATE, last);
		int cdow = c.get(Calendar.DAY_OF_WEEK);

		int diff = cdow - dow;
		if(diff < 0) {
			diff += 7;
		}
		c.add(Calendar.DATE, -diff);
		return c.getTime();
	}
	
	public static Date getLastDate(Date cdate) {
		Calendar c = Calendar.getInstance();
		c.setTime(cdate);
		
		int last = c.getActualMaximum(Calendar.DATE);
		c.set(Calendar.DATE, last);
		
		return c.getTime();
	}
	
	public static String getBaseUrl() {
		Properties prop = Utils.loadProperties("/configuration/common-conf.properties", Thread.currentThread().getContextClassLoader());
		
		return prop.getProperty("ikep4.baseUrl");
	}
	
	/**
	 * 클래스 패쓰 상에 있는 리소스를 로딩한다. 리소스는 프로퍼티 파일이어야 하며, "." 또는 "/"로 패키지 패쓰를 모두 담은 이름을
	 * 넘겨주어야 한다. 특히 .properties 확장자가 없어도 무방하다.
	 * 
	 * <pre>
	 * some.pkg.Resource
	 * some.pkg.Resource.properties
	 * some/pkg/Reousrce
	 * some/pkg/Resource.properties
	 * </pre>
	 * 
	 * @param name
	 * @param loader
	 * @return
	 */
	public static Properties loadProperties(String nameParam, ClassLoader loaderParam) {
		String SUFFIX = ".properties";

		String name = nameParam;
		ClassLoader loader = loaderParam;

		if (name == null) {
			throw new IllegalArgumentException("name is null");
		}

		if (name.startsWith("/")) {
			name = name.substring(1);
		}

		if (name.endsWith(SUFFIX)) {
			name = name.substring(0, name.length() - SUFFIX.length());
		}

		Properties result = null;

		InputStream in = null;

		try {
			if (loader == null) {
				loader = ClassLoader.getSystemClassLoader();
			}

			name = name.replace('.', '/');

			if (!name.endsWith(SUFFIX)) {
				name = name.concat(SUFFIX);
			}

			in = loader.getResourceAsStream(name);

			if (in != null) {
				result = new Properties();
				result.load(in);
			}
		} catch (Exception e) {
			result = null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ignore) {
					// 예외 무시
				}
			}
		}

		if (result == null) {
			throw new IllegalArgumentException("Could not load [" + name + "] as a classloader resource");
		}

		return result;
	}
}
