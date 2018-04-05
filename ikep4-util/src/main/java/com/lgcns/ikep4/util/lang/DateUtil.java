package com.lgcns.ikep4.util.lang;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.ibm.icu.util.ChineseCalendar;


/**
 * DateUtil
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: DateUtil.java 19354 2012-06-20 03:45:41Z handul $
 */
public final class DateUtil {

	private DateUtil() {
		throw new AssertionError();
	}

	/**
	 * 현재 날짜 구하기
	 * 
	 * @param format
	 * @return
	 */
	public static String getToday(String formatStr) {

		String format = formatStr;
		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd";
		}

		Date date = new Date();
		SimpleDateFormat sdate = new SimpleDateFormat(format);

		return sdate.format(date);
	}

	/**
	 * 현재 날짜 시간 구하기
	 * 
	 * @param format
	 * @return
	 */
	public static String getTodayDateTime(String formatStr) {

		String format = formatStr;
		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		Date date = new Date();
		SimpleDateFormat sdate = new SimpleDateFormat(format);

		return sdate.format(date);
	}

	/**
	 * Date를 Format 문자열로 바꾸기
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getFmtDateString(Date date, String format) {
		SimpleDateFormat sdate = new SimpleDateFormat(format);

		return sdate.format(date);
	}

	/**
	 * Date를 Format 문자열로 바꾸기
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getFmtDateString(String date, String format) {
		Date todate = toDate(date);

		return getFmtDateString(todate, format);
	}
	
	
	/**
	 * Date를 Format 문자열로 바꾸기
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getFmtDateString(Date date, String format, Locale locale) {
		SimpleDateFormat sdate = new SimpleDateFormat(format, locale);

		return sdate.format(date);
	}
	
	/**
	 * Date를 Format 문자열로 바꾸기
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getFmtDateString(String date, String format, Locale locale) {
		Date todate = toDate(date);

		return getFmtDateString(todate, format, locale);
	}
	
	/**
	 * 구분자가 추가된 날짜 스트링
	 * 
	 * @param date
	 * @param delim
	 * @return
	 */
	public static String getDelimDateString(String date, String delim) {
		String unFmtDate = getUnFmtDateString(date);

		StringBuffer buf = new StringBuffer();

		buf.append(unFmtDate.substring(0, 4));
		buf.append(delim);
		buf.append(unFmtDate.substring(4, 6));
		buf.append(delim);
		buf.append(unFmtDate.substring(6, 8));

		return buf.toString();
	}

	/**
	 * 구분자가 제거된 날짜 스트링
	 * 
	 * @param fmtDate
	 * @return
	 */
	public static String getUnFmtDateString(String fmtDate) {
		boolean isCharater = false;
		boolean isCorrect = true;

		String strDate = "";
		String date = "";
		String result = "";

		if (fmtDate != null) {
			strDate = fmtDate.trim();
		}

		for (int inx = 0; inx < strDate.length(); inx++) {
			if (Character.isLetter(strDate.charAt(inx)) || strDate.charAt(inx) == ' ') {
				isCorrect = false;

				break;
			}
		}

		if (!isCorrect) {
			return "";
		}

		if (strDate.length() != 8) {
			if (strDate.length() != 6 && strDate.length() != 10) {
				return "";
			}

			if (strDate.length() == 6) {
				if (Integer.parseInt(strDate.substring(0, 2)) > 50) {
					date = "19";
				} else {
					date = "20";
				}

				result = date + strDate;
			}

			if (strDate.length() == 10) {
				//result = strDate.substring(0, 4) + strDate.substring(4, 8) + strDate.substring(8, 10);//구분자 제거 버그 
				result = strDate.substring(0, 4) + strDate.substring(5, 7) + strDate.substring(8, 10);//무림제지. 2012.09.03 버그패치.
			}
		} else {
			try {
				Integer.parseInt(strDate);
			} catch (NumberFormatException ne) {
				isCharater = true;
			}

			if (isCharater) {
				date = strDate.substring(0, 2) + strDate.substring(3, 5) + strDate.substring(6, 8);

				if (Integer.parseInt(strDate.substring(0, 2)) > 50) {
					result = "19" + date;
				} else {
					result = "20" + date;
				}
			} else {
				return strDate;
			}
		}

		return result;
	}

	/**
	 * 주어진 date/time과 year년 month월 day일 차이나는 시각을 리턴한다.
	 * 
	 * <pre>
	 *  사용예)
	 *  //현재로부터 10일 전의 date/time
	 *  Date newDate = DateUtil.shift(new Date(), 0,0,-10);
	 * </pre>
	 * 
	 * @param date
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date getRelativeDate(Date date, int year, int month, int day) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		cal.add(Calendar.YEAR, year);
		cal.add(Calendar.MONTH, month);
		cal.add(Calendar.DAY_OF_MONTH, day);

		return cal.getTime();
	}

	/**
	 * 년 월을 더한 날짜 구하기
	 * 
	 * @param date
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getRelativeDate(Date date, int year, int month) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		cal.add(Calendar.YEAR, year);
		cal.add(Calendar.MONTH, month);

		return cal.getTime();
	}

	/**
	 * 일을 더한 날짜 구하기
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getRelativeDate(Date date, int day) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		cal.add(Calendar.DAY_OF_YEAR, day);

		return cal.getTime();
	}

	/**
	 * 현재 시각과 year년 month월 day일 차이나는 시각을 리턴한다.
	 * <p>
	 * 리턴되는 포맷은 'yyyyMMdd' 이다.
	 * 
	 * <pre>
	 *  사용예)
	 *  //현재 시각이 '20031001'이면 10일 전 시각인 '20030921'을 리턴
	 *  String date = DateUtil.getRelativeDate(0,0,-10);
	 * </pre>
	 * 
	 * @param date
	 * @param year
	 * @param month
	 * @param day
	 * @param format
	 * @return
	 */
	public static String getRelativeDateString(Date date, int year, int month, int day, String format) {
		Date relativeDate = getRelativeDate(date, year, month, day);

		return getFmtDateString(relativeDate, format);
	}

	/**
	 * 년/월/일/시/분을 더한 날짜 구하기
	 * 
	 * @param date
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @return
	 */
	public static Date getRelativeDate(Date date, int year, int month, int day, int hour, int minute) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		cal.add(Calendar.YEAR, year);
		cal.add(Calendar.MONTH, month);
		cal.add(Calendar.DAY_OF_MONTH, day);
		cal.add(Calendar.HOUR_OF_DAY, hour);
		cal.add(Calendar.MINUTE, minute);

		return cal.getTime();
	}

	/**
	 * 현재 시각과 year년 month월 day일 hour시 minute분 차이나는 시각을 리턴한다.
	 * <p>
	 * 리턴되는 포맷은 'yyyyMMddhh24mi' 이다.s
	 * 
	 * @param date
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param format
	 * @return
	 */
	public static String getRelativeDateString(Date date, int year, int month, int day, int hour, int minute,
			String format) {
		Date relativeDate = getRelativeDate(date, year, month, day, hour, minute);

		return getFmtDateString(relativeDate, format);
	}

	/**
	 * 특정일과 year년 month월 day일 hour시 minute분 차이나는 시각을 리턴한다.
	 * <p>
	 * 리턴되는 포맷은 'yyyyMMddhh24mi' 이다.
	 * 
	 * @param date
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param format
	 * @return
	 */
	public static String getRelativeDateString(String date, int year, int month, int day, int hour, int minute,
			String format) {
		Calendar cal = toCalendar(date.substring(0, 8), Integer.parseInt(date.substring(8, 10)),
				Integer.parseInt(date.substring(10, 12)));

		cal.add(Calendar.YEAR, year);
		cal.add(Calendar.MONTH, month);
		cal.add(Calendar.DAY_OF_MONTH, day);
		cal.add(Calendar.HOUR_OF_DAY, hour);
		cal.add(Calendar.MINUTE, minute);

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.format(cal.getTime());
	}

	/**
	 * 이후 날짜 구하기
	 * 
	 * @param date
	 * @param days
	 * @param format
	 * @return
	 */
	public static String getNextDate(String date, int days, String formatStr) {
		if (days < 0) {
			return date;
		}

		String format = formatStr;
		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd";
		}

		Calendar cal = toCalendar(date);

		cal.add(Calendar.DATE, days);

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.format(cal.getTime());
	}

	/**
	 * 이전 날짜 구하기(yyyymmdd 형식으로 입력 파라미터를 받음)
	 * 
	 * @param date
	 * @param days
	 * @param format
	 * @return
	 */
	public static String getPrevDate(String date, int days, String formatStr) {

		if (days < 0) {
			return date;
		}

		String format = formatStr;
		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd";
		}

		java.util.Calendar cal = toCalendar(date);

		cal.add(Calendar.DATE, -(days));

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.format(cal.getTime());
	}

	/**
	 * 다음 주 날짜 구하기
	 * 
	 * @param date
	 * @param weeks
	 * @param format
	 * @return
	 */
	public static String getNextWeekDate(String date, int weeks, String format) {
		if (weeks < 0) {
			return date;
		}

		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd";
		}

		java.util.Calendar cal = toCalendar(date);

		cal.add(Calendar.DATE, weeks * 7);

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.format(cal.getTime());
	}

	/**
	 * 이전 주 날짜 구하기
	 * 
	 * @param date
	 * @param weeks
	 * @param format
	 * @return
	 */
	public static String getPrevWeekDate(String date, int weeks, String format) {
		if (weeks < 0) {
			return date;
		}

		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd";
		}

		java.util.Calendar cal = toCalendar(date);

		cal.add(Calendar.DATE, weeks * (-7));

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.format(cal.getTime());
	}

	/**
	 * 다음 달 날짜 구하기
	 * 
	 * @param date
	 * @param months
	 * @param format
	 * @return
	 */
	public static String getNextMonthDate(String date, int months, String format) {
		if (months < 0) {
			return date;
		}

		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd";
		}

		java.util.Calendar cal = toCalendar(date);

		cal.add(Calendar.MONTH, months);

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.format(cal.getTime());
	}

	/**
	 * 이전 달 날짜 구하기
	 * 
	 * @param date
	 * @param months
	 * @param format
	 * @return
	 */
	public static String getPrevMonthDate(String date, int months, String format) {
		if (months < 0) {
			return date;
		}

		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd";
		}

		java.util.Calendar cal = toCalendar(date);

		cal.add(Calendar.MONTH, -(months));

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.format(cal.getTime());
	}

	/**
	 * 입력된 일자를 Calendar 객체로 반환한다.
	 * 
	 * @param argDate 변환할 일자( 1998.01.02, 98.01.02, 19980102, 980102 등 )
	 * @return 해당일자에 해당하는 Calendar
	 */
	public static Calendar toCalendar(String fmtDate) {
		String date = getUnFmtDateString(fmtDate);

		GregorianCalendar calendar = new GregorianCalendar();

		calendar.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)) - 1,
				Integer.parseInt(date.substring(6, 8)));

		return calendar;
	}

	/**
	 * 입력된 일시를 Calendar 객체로 반환한다.
	 * 
	 * @param argDate 변환할 일자( 1998.01.02, 98.01.02, 19980102, 980102 등 )
	 * @return 해당일자에 해당하는 Calendar
	 */
	public static Calendar toCalendar(String fmtDate, int hour, int minute) {

		String date = getUnFmtDateString(fmtDate);

		GregorianCalendar calendar = new GregorianCalendar();

		calendar.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)) - 1,
				Integer.parseInt(date.substring(6, 8)), hour, minute);

		return calendar;
	}

	public static Calendar toCalendar(String fmtDate, int hour, int minute, int second) {
		String date = getUnFmtDateString(fmtDate);

		GregorianCalendar calendar = new GregorianCalendar();

		calendar.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)) - 1,
				Integer.parseInt(date.substring(6, 8)), hour, minute, second);

		return calendar;
	}

	/**
	 * 입력된 일자를 Date 객체로 반환한다.
	 * 
	 * @param argDate 변환할 일자( 1998.01.02, 98.01.02, 19980102, 980102 등 )
	 * @return 해당일자에 해당하는 Calendar
	 */
	public static Date toDate(String fmtDate) {
		return toCalendar(fmtDate).getTime();
	}

	/**
	 * 날짜와 요일을 파라미터로 주고 날짜 속하는 주의 요일에 해당하는 날짜를 yyyyMMdd형태로 구함
	 */
	public static String getWeekDay(String date, int order) {
		String returnDay = null;
		Calendar curr = Calendar.getInstance();
		curr.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)) - 1,
				Integer.parseInt(date.substring(6, 8)));
		int weekday = curr.get(Calendar.DAY_OF_WEEK);

		if (order == weekday) {
			returnDay = date;
		} else {
			curr.add(Calendar.DATE, order - weekday);
			SimpleDateFormat sdate = new SimpleDateFormat("yyyyMMdd");
			returnDay = sdate.format(curr.getTime());
		}
		return returnDay;
	}

	/**
	 * 요일의 날짜 구하기
	 * 
	 * @param date
	 * @param order
	 * @return
	 */
	public static Date getWeekDay(Date date, int order) {
		Date returnDay;
		Calendar curr = Calendar.getInstance();
		curr.setTime(date);
		int weekday = curr.get(Calendar.DAY_OF_WEEK);

		if (order == weekday) {
			returnDay = date;
		} else {
			curr.add(Calendar.DATE, order - weekday);
			returnDay = curr.getTime();
		}
		return returnDay;
	}

	/**
	 * 각 달의 1일의 요일을 구함
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getFirstDay(int year, int month) {
		int firstday = 0;

		Calendar curr = Calendar.getInstance();

		curr.set(year, month - 1, 1);

		firstday = curr.get(Calendar.DAY_OF_WEEK);
		return firstday;
	}

	/**
	 * 각 달의 마지막날짜를 구함
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getLastDate(int year, int month) {

		int yy = year;
		int mm = month;

		switch (mm) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;

		case 4:
		case 6:
		case 9:
		case 11:
			return 30;

		default:
			if (((yy % 4 == 0) && (yy % 100 != 0)) || (yy % 400 == 0)) {
				return (29);
			} else {
				return (28);
			}
		}
	}

	/**
	 * 그날의 weekday를 구함
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekDayCount(String date) {
		Calendar curr = Calendar.getInstance();
		curr.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)) - 1,
				Integer.parseInt(date.substring(6, 8)));
		int weekday = curr.get(Calendar.DAY_OF_WEEK);

		return weekday;
	}

	/**
	 * 그 날이 그 달의 몇번째 week 수인지 구함
	 * 
	 * @param day
	 * @return
	 */
	public static int getWeekCountMonth(int day) {
		int weekCount;
		int remainCount = day - (day / 7) * 7;

		if (remainCount > 0) {
			weekCount = (day / 7) + 1;
		} else {
			weekCount = (day / 7);
		}

		return weekCount;
	}

	/**
	 * 각 달의 week 수를 구함
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getWeekCount(int year, int month) {
		Calendar curr = Calendar.getInstance();
		curr.set(year, month - 1, getLastDate(year, month));
		return curr.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 일을 더한 날짜 구하기
	 * 
	 * @param dateStr
	 * @param val
	 * @param format
	 * @return
	 */
	public static String calcDate(String dateStr, int val, String format) {

		String date = dateStr;
		Calendar curr = Calendar.getInstance();
		date = unFmtDate(date);
		curr.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)) - 1,
				Integer.parseInt(date.substring(6, 8)));
		curr.add(Calendar.DATE, val);
		SimpleDateFormat sdate = new SimpleDateFormat(format);
		return sdate.format(curr.getTime());
	}

	/**
	 * 분을 더한 날짜 구하기
	 * 
	 * @param dateTimeStr
	 * @param val
	 * @param format
	 * @return
	 */
	public static String calcDateTime(String dateTimeStr, int val, String format) {
		String dateTime = dateTimeStr;
		Calendar curr = Calendar.getInstance();
		dateTime = unFmtDate(dateTime);
		curr.set(Integer.parseInt(dateTime.substring(0, 4)), Integer.parseInt(dateTime.substring(4, 6)) - 1,
				Integer.parseInt(dateTime.substring(6, 8)), Integer.parseInt(dateTime.substring(8, 10)),
				Integer.parseInt(dateTime.substring(10, 12)));
		curr.add(Calendar.MINUTE, val);
		SimpleDateFormat sdate = new SimpleDateFormat(format);
		return sdate.format(curr.getTime());
	}

	/**
	 * 포멧을 제거한 날짜 구하기
	 * 
	 * @param fmtdate
	 * @return
	 */
	public static String unFmtDate(String fmtdate) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < fmtdate.length(); i++) {
			if (fmtdate.charAt(i) != '-') {
				buf.append(fmtdate.charAt(i));
			}
		}
		return buf.toString();
	}

	/**
	 * 일주일의 첫 날짜(yyyyMMdd)를 파라미터로 주고 해당 주일의 날짜를 String[]으로 받음.
	 * 
	 * @param firstday
	 * @param format
	 * @return
	 */
	public static String[] getDatesInWeek(String firstday, String format) {
		String[] weekdays = new String[7];
		for (int i = 0; i < 7; i++) {
			weekdays[i] = calcDate(firstday, i, format);
		}
		return weekdays;
	}

	/**
	 * 특정 기간안의 날짜리스트 구하기
	 * 
	 * @param startDate
	 * @param endDate
	 * @param format
	 * @return
	 */
	public static String[] getDatesInPeriod(String startDate, String endDate, String format) {
		int dateDiffCount = getTwoDatesDifference(startDate, endDate);

		String[] days = new String[dateDiffCount + 1];

		for (int i = 0; i < dateDiffCount + 1; i++) {
			days[i] = calcDate(startDate, i, format);
		}

		return days;
	}

	/**
	 * 특정 기간안의 시간리스트 구하기
	 * 
	 * @param startDateTime
	 * @param endDateTime
	 * @param format
	 * @return
	 */
	public static String[] getDateTimesInPeriod(String startDateTime, String endDateTime, String format) {
		int dateTimeDiffCount = getTwoDateTimesDifference(startDateTime, endDateTime);

		String[] dateTimes = new String[dateTimeDiffCount + 1];

		for (int i = 0; i < dateTimes.length; i++) {
			dateTimes[i] = calcDateTime(startDateTime, i * 30, format);
		}

		return dateTimes;
	}

	/**
	 * 두날짜 사이의 일수 구하기
	 * 
	 * @param strDate
	 * @param strComp
	 * @return
	 */
	public static int getTwoDatesDifference(String strDate, String strComp) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		int year = Integer.parseInt(strDate.substring(0, 4));
		int month = Integer.parseInt(strDate.substring(4, 6));
		int day = Integer.parseInt(strDate.substring(6, 8));

		int compYear = Integer.parseInt(strComp.substring(0, 4));
		int compMonth = Integer.parseInt(strComp.substring(4, 6));
		int compDay = Integer.parseInt(strComp.substring(6, 8));

		cal1.set(year, month - 1, day);
		cal2.set(compYear, compMonth - 1, compDay);
		long cal1sec = cal1.getTime().getTime();
		long cal2sec = cal2.getTime().getTime();
		long gap = cal2sec - cal1sec;
		int gapday = Integer.parseInt(String.valueOf((gap / 86400) / 1000));

		return gapday;
	}

	/**
	 * 두날짜 사이의 일수 구하기
	 * 
	 * @param strDate
	 * @param strComp
	 * @return
	 */
	public static long getTwoDateDiff(Date date, Date compareDate, String type) {
		if (date == null) {
			throw new IllegalArgumentException("date is null");
		}
		if (compareDate == null) {
			throw new IllegalArgumentException("compareDate is null");
		}

		Long compared = date.getTime() - compareDate.getTime();

		if (StringUtils.equals(type, "S")) {
			return compared / 1000;

		} else if (StringUtils.equals(type, "M")) {
			return compared / (60 * 1000);

		} else if (StringUtils.equals(type, "H")) {
			return compared / (60 * 60 * 1000);

		} else if (StringUtils.equals(type, "D")) {
			return compared / (24 * 60 * 60 * 1000);

		} else {
			throw new IllegalArgumentException("type is allowed S, M, H, D");
		}

	}

	/**
	 * 두날짜 사이의 시간수 구하기
	 * 
	 * @param strDateTime
	 * @param strCompTime
	 * @return
	 */
	public static int getTwoDateTimesDifference(String strDateTime, String strCompTime) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		int year = Integer.parseInt(strDateTime.substring(0, 4));
		int month = Integer.parseInt(strDateTime.substring(4, 6));
		int day = Integer.parseInt(strDateTime.substring(6, 8));
		int hour = Integer.parseInt(strDateTime.substring(8, 10));
		int minute = Integer.parseInt(strDateTime.substring(10, 12));

		int compYear = Integer.parseInt(strCompTime.substring(0, 4));
		int compMonth = Integer.parseInt(strCompTime.substring(4, 6));
		int compDay = Integer.parseInt(strCompTime.substring(6, 8));
		int compHour = Integer.parseInt(strCompTime.substring(8, 10));
		int compMinute = Integer.parseInt(strCompTime.substring(10, 12));

		cal1.set(year, month - 1, day, hour, minute);
		cal2.set(compYear, compMonth - 1, compDay, compHour, compMinute);
		long cal1sec = cal1.getTime().getTime();
		long cal2sec = cal2.getTime().getTime();
		long gap = cal2sec - cal1sec;
		int gapDateTime = Integer.parseInt(String.valueOf((gap / 1800) / 1000));

		return gapDateTime;
	}

	public static String getTimeInterval(Date sDate, String locale) {
		return getTimeInterval(sDate, new Date(), locale);
	}

	/**
	 * 시간 사이의 간격을 구함. 트위터 등에서 시간을 표시할때 사용함
	 * 
	 * @param sDate
	 * @param eDate
	 * @return
	 */
	public static String getTimeInterval(Date sDate, Date eDate, String localeStr) {

		String diffStr = "";
		long diff = 0;

		String locale = localeStr;
		if (StringUtil.isEmpty(locale)) {
			locale = "en";
		}

		long duration = (eDate.getTime() - sDate.getTime()) / 1000;

		if (duration > (60 * 60 * 24 * 30 * 12)) {
			diff = duration / (60 * 60 * 24 * 30 * 12);
			if (locale.equals("ko")) {
				diffStr = diff + " 년전";
			} else {
				diffStr = diff + " year ago";
			}
		} else if (duration > (60 * 60 * 24 * 30)) {
			diff = duration / (60 * 60 * 24 * 30);
			if (locale.equals("ko")) {
				diffStr = diff + " 달전";
			} else {
				diffStr = diff + " month ago";
			}
		} else if (duration > (60 * 60 * 24)) {
			diff = duration / (60 * 60 * 24);
			if (locale.equals("ko")) {
				diffStr = diff + " 일전";
			} else {
				diffStr = diff + " day ago";
			}
		} else if (duration > (60 * 60)) {
			diff = duration / (60 * 60);
			if (locale.equals("ko")) {
				diffStr = diff + " 시간전";
			} else {
				diffStr = diff + " hour ago";
			}
		} else if (duration > (60)) {
			diff = duration / (60);
			if (locale.equals("ko")) {
				diffStr = diff + " 분전";
			} else {
				diffStr = diff + " minute ago";
			}
		} else {
			if (locale.equals("ko")) {
				diffStr = "1 분전";
			} else {
				diffStr = "1 second ago";
			}
		}

		return diffStr;
	}

	/**
	 * 양력(yyyyMMdd) -> 음력(yyyyMMdd)
	 * 
	 * @param yyyymmdd
	 * @return
	 */
	public static String getFromSolarDateToLunarDate(String yyyymmdd) {
		Calendar cal = Calendar.getInstance();
		ChineseCalendar cc = new ChineseCalendar();

		if (yyyymmdd == null) {
			return "";
		}

		String date = yyyymmdd.trim();
		if (date.length() != 8) {
			if (date.length() == 4) {
				date = date + "0101";
			} else if (date.length() == 6) {
				date = date + "01";
			} else if (date.length() > 8) {
				date = date.substring(0, 8);
			} else {
				return "";
			}
		}

		cal.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
		cal.set(Calendar.MONTH, Integer.parseInt(date.substring(4, 6)) - 1);
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.substring(6)));

		cc.setTimeInMillis(cal.getTimeInMillis());

		// ChinessCalendar.YEAR 는 1~60 까지의 값만 가지고 ,
		// ChinessCalendar.EXTENDEDyear 는 Calendar.YEAR 값과 2637 만큼의 차이를 가집니다.
		int y = cc.get(ChineseCalendar.EXTENDED_YEAR) - 2637;
		int m = cc.get(ChineseCalendar.MONTH) + 1;
		int d = cc.get(ChineseCalendar.DAY_OF_MONTH);

		StringBuffer ret = new StringBuffer();
		if (y < 1000) {
			ret.append("0");
		} else if (y < 100) {
			ret.append("00");
		} else if (y < 10) {
			ret.append("000");
		}
		ret.append(y);

		if (m < 10) {
			ret.append("0");
		}
		ret.append(m);

		if (d < 10) {
			ret.append("0");
		}
		ret.append(d);

		return ret.toString();
	}

	/**
	 * 음력(yyyyMMdd) -> 양력(yyyyMMdd)
	 * 
	 * @param yyyymmdd
	 * @return
	 */
	public static String getFromLunarDateToSolarDate(String yyyymmdd) {
		Calendar cal = Calendar.getInstance();
		ChineseCalendar cc = new ChineseCalendar();

		if (yyyymmdd == null) {
			return "";
		}

		String date = yyyymmdd.trim();
		if (date.length() != 8) {
			if (date.length() == 4) {
				date = date + "0101";
			} else if (date.length() == 6) {
				date = date + "01";
			} else if (date.length() > 8) {
				date = date.substring(0, 8);
			} else {
				return "";
			}
		}

		cc.set(ChineseCalendar.EXTENDED_YEAR, Integer.parseInt(date.substring(0, 4)) + 2637);
		cc.set(ChineseCalendar.MONTH, Integer.parseInt(date.substring(4, 6)) - 1);
		cc.set(ChineseCalendar.DAY_OF_MONTH, Integer.parseInt(date.substring(6)));

		cal.setTimeInMillis(cc.getTimeInMillis());

		int y = cal.get(Calendar.YEAR);
		int m = cal.get(Calendar.MONTH) + 1;
		int d = cal.get(Calendar.DAY_OF_MONTH);

		StringBuffer ret = new StringBuffer();
		if (y < 1000) {
			ret.append("0");
		} else if (y < 100) {
			ret.append("00");
		} else if (y < 10) {
			ret.append("000");
		}
		ret.append(y);

		if (m < 10) {
			ret.append("0");
		}
		ret.append(m);

		if (d < 10) {
			ret.append("0");
		}
		ret.append(d);

		return ret.toString();
	}

	/**
	 * Date 스트링의 유효여부를 체크하고 유효한 Date 스트링을 반환
	 * 
	 * @param yyyymmdd
	 * @return
	 */
	public static String getValidDate(String yyyymmdd) {
		String strYear = "";
		String strMonth = "";
		String strDay = "";
		int validMonthDay = 0;
		String validDate = "";

		if (yyyymmdd.length() == 8) {
			strYear = yyyymmdd.substring(0, 4);
			strMonth = yyyymmdd.substring(4, 6);
			strDay = yyyymmdd.substring(6, 8);

			validMonthDay = getLastDate(Integer.parseInt(strYear), Integer.parseInt(strMonth));

			if (Integer.parseInt(strDay) <= validMonthDay) {
				validDate = yyyymmdd;
			} else {
				validDate = strYear + strMonth + String.valueOf(validMonthDay);
			}

			return validDate;
		} else {
			return "";
		}
	}
}