package com.lgcns.ikep4.util.excel;
/**
 * 
 * TODO Javadoc주석작성
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: FormattingUtils.java 16247 2011-08-18 04:54:29Z giljae $
 */
public abstract class FormattingUtils
{
  public static String lPadding(String src, int size, char paddingChar)
  {
    int srcLength = 0;

    if (src == null) {
      StringBuffer result = new StringBuffer();
      for (int i = 0; i < size; ++i) {
        result.append(paddingChar);
      }
      return result.toString();
    }
    byte[] srcBytes = src.getBytes();
    srcLength = srcBytes.length;

    if (size == srcLength)
      return src;
    if (size < srcLength) {
      return new String(srcBytes, 0, size);
    }
    int paddingCount = size - srcLength;
    StringBuffer result = new StringBuffer();
    for (int i = 0; i < paddingCount; ++i) {
      result.append(paddingChar);
    }
    result.append(src);
    return result.toString();
  }

  public static String rPadding(String src, int size, char paddingChar)
  {
    int srcLength = 0;

    if (src == null) {
      StringBuffer result = new StringBuffer();
      for (int i = 0; i < size; ++i) {
        result.append(paddingChar);
      }
      return result.toString();
    }
    byte[] srcBytes = src.getBytes();
    srcLength = srcBytes.length;

    if (size == srcLength)
      return src;
    if (size < srcLength) {
      return new String(srcBytes, 0, size);
    }
    int paddingCount = size - srcLength;
    StringBuffer result = new StringBuffer(src);
    for (int i = 0; i < paddingCount; ++i) {
      result.append(paddingChar);
    }
    return result.toString();
  }
}