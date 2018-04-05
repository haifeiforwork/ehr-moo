package com.lgcns.ikep4.servicepack.usagetracker.util;

import java.util.Calendar;
import java.util.Date;
/**
 * 
 * 날짜util
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: DateUtil.java 16244 2011-08-18 04:11:42Z giljae $
 */
public final class DateUtil {

	
	private DateUtil() {
	}

	/**
	 * 금일
	 * @return
	 */
	public static Date getToday(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	/**
	 * 내일
	 * @param currentDay
	 * @param day
	 * @return
	 */
	public static Date getNextday(Date currentDay,int day){
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDay);
		cal.add(Calendar.DAY_OF_WEEK,day);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 이전주
	 * @param week
	 * @return
	 */
	public static Date prevWeek(int week){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.WEEK_OF_YEAR,(-1)*week);
		return cal.getTime();
	}
	
	/**
	 * 다음주
	 * @param week
	 * @return
	 */
	public static Date nextWeek(int week){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.WEEK_OF_YEAR,week);
		return cal.getTime();
	}
	
	/**
	 * 이전달
	 * @param month
	 * @return
	 */
	public static Date prevMonth(int month){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.MONTH,(-1)*month);
		return cal.getTime();
	}
	
	/**
	 * 이전월
	 * @param month
	 * @return
	 */
	public static Date nextMonth(int month){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.MONTH,month);
		return cal.getTime();
	}
}