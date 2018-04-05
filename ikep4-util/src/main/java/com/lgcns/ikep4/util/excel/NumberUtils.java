package com.lgcns.ikep4.util.excel;

import java.text.DecimalFormat;
/**
 * 
 * TODO Javadoc주석작성
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: NumberUtils.java 16247 2011-08-18 04:54:29Z giljae $
 */
public class NumberUtils extends org.springframework.util.NumberUtils
{
  public static boolean isNumber(String s)
  {
    return StringUtils.matches(s, "[0-9]*");
  }

  public static boolean isRealNumber(String s)
  {
    return StringUtils.matches(s, "[0-9]*[\\.]?[0-9]*");
  }

  public static String formatMoney(String s)
  {
    return formatNumber(s, "#,##0");
  }

  public static String formatNumber(String s, String pattern)
  {
    if ((!(StringUtils.hasText(s))) || (!(isRealNumber(s)))) {
      return "";
    }
    String result = null;
    try {
      result = formatNumber(parseNumber(s, Double.class), pattern);
      return result; } catch (NumberFormatException nfe) {
    }
    return s;
  }

  public static String formatNumber(Number number)
  {
    return formatNumber(number, "#,##0");
  }

  public static String formatNumber(Number number, String pattern)
  {
    if (number == null) {
      return "";
    }
    String formattedString = null;

    if (!(StringUtils.hasText(pattern))) {
      pattern = "#,##0";
    }

    DecimalFormat df = new DecimalFormat(pattern);
    formattedString = df.format(number);

    return formattedString;
  }

  public static int hexStringToInt(String hex)
  {
    int value = 0;
    if (StringUtils.hasText(hex)) {
      hex = hex.toLowerCase();
      if (hex.startsWith("0x")) {
        hex = hex.substring(2);
      }
      String max = "7fffffff";
      if ((hex.length() > max.length()) || ((hex.length() == max.length()) && (hex.charAt(0) > '7'))) {
        throw new IllegalArgumentException("int??최�?�?2147483647)???�어�?�� ???�니??");
      }

      char[] h = hex.toCharArray();
      for (int i = 0; i < h.length; ++i) {
        value = (int)(value + hexToInt(h[i]) * Math.pow(16.0D, h.length - i - 1));
      }
    }

    return value;
  }

  public static int hexToInt(char c) {
    if ((c >= 'a') && (c <= 'f')) {
      return (c - 'a' + 10);
    }
    if ((c >= 'A') && (c <= 'F')) {
      return (c - 'A' + 10);
    }
    if (c == '0') {
      return 0;
    }

    return (c - '1' + 1);
  }
}