package com.lgcns.ikep4.util.ical.model;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.WeekDayList;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * IRecurVO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: IRecur.java 16247 2011-08-18 04:54:29Z giljae $
 */
public class IRecur extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 4143073449046976675L;

	/**
	 * 횟수
	 */
	private int count;

	/**
	 * 간격
	 */
	private int interval;

	/**
	 * 반복설정(SECONDLY, MINUTELY, HOURLY, DAILY, WEEKLY,MONTHLY, YEARLY)
	 */
	private String frequency;

	/**
	 * 종료일
	 */
	private Date until; // 종료일

	/**
	 * 
	 */
	private String weekStartDay;

	/**
	 * 초
	 */
	private List<Integer> secondList; // 초

	/**
	 * 분
	 */
	private List<Integer> minuteList; // 분

	/**
	 * 시간
	 */
	private List<Integer> hourList; // 시간

	/**
	 * 요일
	 */
	private WeekDayList dayList; // 요일

	/**
	 * 월
	 */
	private List<Integer> monthList; // 월

	/**
	 * 달의 일
	 */
	private List<Integer> monthDayList;

	/**
	 * 년의 일
	 */
	private List<Integer> yearDayList;

	/**
	 * 몇째 주/월 위치
	 */
	private List<Integer> setPosList;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public Date getUntil() {
		return until;
	}

	public void setUntil(Date until) {
		this.until = until;
	}

	public String getWeekStartDay() {
		return weekStartDay;
	}

	public void setWeekStartDay(String weekStartDay) {
		this.weekStartDay = weekStartDay;
	}

	public List<Integer> getSecondList() {
		return secondList;
	}

	public void setSecondList(List<Integer> secondList) {
		this.secondList = secondList;
	}

	public List<Integer> getMinuteList() {
		return minuteList;
	}

	public void setMinuteList(List<Integer> minuteList) {
		this.minuteList = minuteList;
	}

	public List<Integer> getHourList() {
		return hourList;
	}

	public void setHourList(List<Integer> hourList) {
		this.hourList = hourList;
	}

	public List<Integer> getMonthDayList() {
		return monthDayList;
	}

	public void setMonthDayList(List<Integer> monthDayList) {
		this.monthDayList = monthDayList;
	}

	public List<Integer> getMonthList() {
		return monthList;
	}

	public void setMonthList(List<Integer> monthList) {
		this.monthList = monthList;
	}

	public WeekDayList getDayList() {
		return dayList;
	}

	public void setDayList(WeekDayList dayList) {
		this.dayList = dayList;
	}

	public List<Integer> getYearDayList() {
		return yearDayList;
	}

	public void setYearDayList(List<Integer> yearDayList) {
		this.yearDayList = yearDayList;
	}

	public List<Integer> getSetPosList() {
		return setPosList;
	}

	public void setSetPosList(List<Integer> setPosList) {
		this.setPosList = setPosList;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
