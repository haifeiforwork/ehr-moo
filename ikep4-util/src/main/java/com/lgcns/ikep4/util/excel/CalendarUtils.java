package com.lgcns.ikep4.util.excel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.util.StringUtils;
/**
 * 
 * TODO Javadoc주석작성
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: CalendarUtils.java 16247 2011-08-18 04:54:29Z giljae $
 */
public class CalendarUtils extends DateUtils
{
  private static final String DATE_PATTERN_yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
  private static final String DEFAULT_DATE_PATTERN = "yyyyMMddHHmmssSSS";

  public static String getFormattedDate(String pattern, Date date)
  {
    if (date == null) {
      return null;
    }
    if (!(StringUtils.hasText(pattern))) {
      pattern = "yyyyMMddHHmmssSSS";
    }
    return new SimpleDateFormat(pattern).format(date);
  }

  public static Date getDate(String pattern, String date)
  {
    if (date == null) {
      return null;
    }
    if (!(StringUtils.hasText(pattern)))
      pattern = "yyyyMMddHHmmssSSS";
    try
    {
      return new SimpleDateFormat(pattern).parse(date);
    } catch (ParseException e) {
      throw new IllegalArgumentException("주어�??�짜�??�싱?????�습?�다. : [pattern : " + pattern + " / date : " + date + " ]");
    }
  }

  public static long getTimesSecond(Date startDate, Date endDate)
  {
    if ((startDate == null) || (endDate == null)) {
      throw new IllegalArgumentException("주어�??�짜�?null ?�니??");
    }

    if (startDate.compareTo(endDate) > 0) {
      throw new IllegalArgumentException("비교?�는 ?�짜???�후�??�못?�었?�니??");
    }

    return Math.round((float)(endDate.getTime() - startDate.getTime()) / 1000.0F);
  }

  public static Date convertTimezone(Date date, TimeZone tz)
  {
    if (date == null) {
      throw new IllegalArgumentException("주어�??�짜�?null ?�니??");
    }
    if (tz == null) {
      throw new IllegalArgumentException("주어�?TimeZone??null ?�니??");
    }

    Calendar cal = Calendar.getInstance();
    cal.setTimeZone(TimeZone.getTimeZone("UTC"));
    cal.setTime(date);
    cal.setTimeZone(tz);

    return cal.getTime();
  }
}