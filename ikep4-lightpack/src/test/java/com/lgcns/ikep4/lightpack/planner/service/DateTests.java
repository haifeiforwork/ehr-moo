package com.lgcns.ikep4.lightpack.planner.service;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.junit.Ignore;
import org.junit.Test;

import com.lgcns.ikep4.lightpack.planner.service.impl.CalendarServiceImpl;
import com.lgcns.ikep4.lightpack.planner.util.Utils;

public class DateTests {
	
	private static String pattern = "yyyyMMddHHmm";

	private static String[] parsePatterns = new String[] {"yyyyMMddHHmm", "yyyy-MM-dd", "yyyyMMdd"};
	private FastDateFormat fdf = FastDateFormat.getInstance(pattern);
	
	@Test
	public void dateUtils() throws ParseException {
		String ds = "201103201030";
		Date d = DateUtils.parseDate(ds, parsePatterns);
		
		Date ceiling = DateUtils.ceiling(d, Calendar.MONTH);
		String cs = fdf.format(ceiling);
		assertEquals("201104010000", cs);
		
		Date round = DateUtils.round(d, Calendar.MONTH);
		String rs = fdf.format(round);
		assertEquals("201104010000", rs);
		
		Date trunc = DateUtils.truncate(d, Calendar.MONTH);
		String ts = fdf.format(trunc);
		assertEquals("201103010000", ts);
	}
	
	@Test
	public void monthly_a_type() throws ParseException {
		String ds = "201103201030"; // 21 period : 2
		Date d = DateUtils.parseDate(ds, parsePatterns);
		
		Date td = DateUtils.addMonths(d, -2);
		td = DateUtils.setDays(td, 21);
		String tds = fdf.format(td);
		assertEquals("201101211030", tds);
	}
	
	@Test
	public void monthly_b_type() throws ParseException {
		String ds = "201103201030"; // 4번째 화요일  period : 2
		Date d = DateUtils.parseDate(ds, parsePatterns);
		
		Date td = DateUtils.addMonths(d, -2);
		Calendar c = Calendar.getInstance();
		c.setTime(td);
		c.set(Calendar.DAY_OF_WEEK, 3);
		c.set(Calendar.DAY_OF_WEEK_IN_MONTH, 4);

		String tds = fdf.format(c);
		assertEquals("201101251030", tds);
	}
	
//	@Test
//	public void monthly_c_type() throws ParseException {
//		String ds = "201107191030"; // 마지막  화요일  period : 2
//		Date d = DateUtils.parseDate(ds, parsePatterns);
//		Date rd = CalendarServiceImpl.monthly_c_type(d, -2, 3);
//		
//		String tds = fdf.format(rd);
//		assertEquals("201105311030", tds);
//	}
	
	@Test
	public void monthly_d_type() throws ParseException {
		String ds = "201103201030"; // 마지막  화요일  period : 2
		Date d = DateUtils.parseDate(ds, parsePatterns);
		
		Date td = DateUtils.addMonths(d, -2);
		Calendar c = Calendar.getInstance();
		c.setTime(td);
		int last = c.getActualMaximum(Calendar.DATE);
		c.set(Calendar.DATE, last);
		
		String tds = fdf.format(c);
		assertEquals("201101311030", tds);
	}
	
//	@Test
//	public void yearly_b_type() {
//		Calendar c = Calendar.getInstance();
//		c.set(2011, 4-1, 20);
//		Date n = CalendarServiceImpl.yearly_b_type(c.getTime(), 0, 3, 2, 4);
//
//		String s = fdf.format(n);
//		assertEquals("20110418", s.substring(0, 8));
//	}
	
//	@Test
//	public void yearly_c_type() {
//		Calendar c = Calendar.getInstance();
//		c.set(2011, 5-1, 20);
//		
//		Date n = CalendarServiceImpl.yearly_c_type(c.getTime(), 0, 4, 5);
//		String s = fdf.format(n);
//
//		assertEquals("20110525", s.substring(0, 8));
//	}
	
	@Test
	public void copyHHmm() throws ParseException {
		String ds = "201103201306"; 
		Date d = DateUtils.parseDate(ds, parsePatterns);
		
		String ds2 = "201103201020"; 
		Date d2 = DateUtils.parseDate(ds2, parsePatterns);
		
		//d2 = DateUtils.truncate(d2, Calendar.DATE);
		
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d2);
		
		c1.set(Calendar.HOUR_OF_DAY , c.get(Calendar.HOUR_OF_DAY ));
		c1.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
		
		String s = fdf.format(c1);
		assertEquals("201103201306", s);
	}
	
	@Test
	public void sort() {
		String[] ar = new String[] {"2","5","3"};
		
		Arrays.sort(ar);
		assertEquals("5", ar[2]);
	}
	
	@Ignore
	@Test
	public void parse() throws ParseException {
		Date repeatStartDate = DateUtils.parseDate("2011-04-17".replace("-", ""), parsePatterns);
		Date repeatStartDate1 = DateUtils.parseDate("2011-04-17", parsePatterns);
		System.out.println();
	}
	
	@Test
	public void weeklyMultiType_3ea() throws ParseException {
		Date repeatStartDate = DateUtils.parseDate("20110407", parsePatterns);
		int interval = 2;
		Date d = DateUtils.parseDate("201106021030", parsePatterns);
		String[] ropt = "2,5,7".split(",");	// 월, 목, 토
		
		Date prev = CalendarServiceImpl.weeklyMultiType(d, -interval, ropt, repeatStartDate);
		Date next = CalendarServiceImpl.weeklyMultiType(d, interval, ropt, repeatStartDate);
		
		assertEquals("201105301030", fdf.format(prev));
		assertEquals("201106041030", fdf.format(next));
		//////////////////////////////////////////////////////////////////////////
		
		d = DateUtils.parseDate("201105301030", parsePatterns);
		ropt = "2,5,7".split(",");	// 월, 목, 토
		
		prev = CalendarServiceImpl.weeklyMultiType(d, -interval, ropt, repeatStartDate);
		next = CalendarServiceImpl.weeklyMultiType(d, interval, ropt, repeatStartDate);
		
		assertEquals("201105211030", fdf.format(prev));
		assertEquals("201106021030", fdf.format(next));
		//////////////////////////////////////////////////////////////////////////
		
		d = DateUtils.parseDate("201106041030", parsePatterns);
		ropt = "2,5,7".split(",");	// 월, 목, 토
		
		prev = CalendarServiceImpl.weeklyMultiType(d, -interval, ropt, repeatStartDate);
		next = CalendarServiceImpl.weeklyMultiType(d, interval, ropt, repeatStartDate);
		
		assertEquals("201106021030", fdf.format(prev));
		assertEquals("201106131030", fdf.format(next));
	}
	
	@Test
	public void weeklyMultiType_2e() throws ParseException {
		Date repeatStartDate = DateUtils.parseDate("20110407", parsePatterns);
		int interval = 2;
		Date d = DateUtils.parseDate("201105041030", parsePatterns);
		String[] ropt = "2,4".split(",");	// 월, 수
		
		Date prev = CalendarServiceImpl.weeklyMultiType(d, -interval, ropt, repeatStartDate);
		Date next = CalendarServiceImpl.weeklyMultiType(d, interval, ropt, repeatStartDate);
		
		assertEquals("201105021030", fdf.format(prev));
		assertEquals("201105161030", fdf.format(next));
		//////////////////////////////////////////////////////////////////////////
		
		d = DateUtils.parseDate("201105021030", parsePatterns);
		ropt = "2,4".split(",");	// 월, 목, 토
		
		prev = CalendarServiceImpl.weeklyMultiType(d, -interval, ropt, repeatStartDate);
		next = CalendarServiceImpl.weeklyMultiType(d, interval, ropt, repeatStartDate);
		
		assertEquals("201104201030", fdf.format(prev));
		assertEquals("201105041030", fdf.format(next));

	}
	
	@Test 
	public void dropElement() {
		String[] ropt = "2,5,7".split(",");
		String el = "5";
		
		String[] newAr = (String[]) ArrayUtils.removeElement(ropt, el);
		assertTrue(Arrays.equals(new String[] {"2","7"}, newAr));
		
		String s = StringUtils.join(newAr, ",");
		assertEquals("2,7", s);
	}
	
	@Test
	public void getDowDate() {
		Calendar c = Calendar.getInstance();
		
		c.clear();
		c.set(2011, 3, 5);
		c.getTime();
		
		c.set(Calendar.DAY_OF_WEEK, 1);
		String s = fdf.format(c.getTime()).substring(0, 8);
		assertEquals("20110403", s);
	}
	
	@Test
	public void compare() {
		Calendar c = Calendar.getInstance();
		
		c.clear();
		c.set(2011, 3, 5);
		
		Date bd = c.getTime();
		
		c.set(2011, 3, 6);
		
		Date ad = c.getTime();
		
		assertTrue(bd.compareTo(ad) < 0);
	}
	
	@Test
	public void betweenMonth() {
		Calendar c = Calendar.getInstance();
		
		c.set(2010, 11, 20);	// 2010.12.20
		Date fromDate = c.getTime();
		c.set(2011, 0, 31);	// 2011.01.31
		Date toDate = c.getTime();
		int bm = Utils.betweenMonth(fromDate, toDate);
		assertEquals(1, bm);
		
		c.set(2011, 1, 20);	// 2011.02.20
		fromDate = c.getTime();
		c.set(2011, 4, 31);	// 2011.5.31
		toDate = c.getTime();
		bm = Utils.betweenMonth(fromDate, toDate);
		assertEquals(3, bm);
	}
	
	@Test 
	public void fragment() {
		Calendar c = Calendar.getInstance();
		
		c.set(2010, 11, 20);	// 2010.12.20
		Date fromDate = c.getTime();
		long res = DateUtils.getFragmentInDays(fromDate, Calendar.MONTH);
		assertEquals(20, res);
	}
	
	@Test
	public void  DayOfWeekInMonth() {
		Calendar c = Calendar.getInstance();
		
		c.set(2011, 1, 22);	
		assertEquals(4, c.get(Calendar.DAY_OF_WEEK_IN_MONTH));
	}
	
	@Test
	public void getLastDateByDow() {
		Calendar c = Calendar.getInstance();
		
		c.set(2011, 1, 20);	
		Date res = Utils.getLastDateByDow(c.getTime(), 3); 
		assertEquals("20110222", fdf.format(res).substring(0, 8));
		
		res = Utils.getLastDateByDow(c.getTime(), 2);
		assertEquals("20110228", fdf.format(res).substring(0, 8));
	}
	
}
