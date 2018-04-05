package com.lgcns.ikep4.util.excel;

import java.io.Serializable;
/**
 * 
 * TODO Javadoc주석작성
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: ExcelViewStyle.java 16247 2011-08-18 04:54:29Z giljae $
 */
public class ExcelViewStyle extends ExcelModelConstants
  implements Serializable
{
  private boolean bold;
  private boolean italic;
  private boolean underline;
  private String color;
  private String fontName;
  private int pointSize;
  private String backgroundColor;
  private int backgroundPattern;
  private int border;
  private int borderStyle;
  private String borderColor;
  private int align;

  public ExcelViewStyle()
  {
    this.bold = false;
    this.italic = false;
    this.underline = false;
    this.color = "#000000";
    this.fontName = "tahoma";
    this.pointSize = 11;
    this.backgroundColor = null;
    this.backgroundPattern = 1;
    this.border = 0;
    this.borderStyle = 1;
    this.borderColor = "#000000";
    this.align = 0;
  }

  public boolean isBold()
  {
    return this.bold;
  }

  public void setBold(boolean bold)
  {
    this.bold = bold;
  }

  public boolean isItalic()
  {
    return this.italic;
  }

  public void setItalic(boolean italic)
  {
    this.italic = italic;
  }

  public boolean isUnderline()
  {
    return this.underline;
  }

  public void setUnderline(boolean underline)
  {
    this.underline = underline;
  }

  public String getColor()
  {
    return this.color;
  }

  public void setColor(String color)
  {
    this.color = color;
  }

  public String getFontName()
  {
    return this.fontName;
  }

  public void setFontName(String fontName)
  {
    this.fontName = fontName;
  }

  public int getPointSize()
  {
    return this.pointSize;
  }

  public void setPointSize(int pointSize)
  {
    this.pointSize = pointSize;
  }

  public String getBackgroundColor()
  {
    return this.backgroundColor;
  }

  public void setBackgroundColor(String backgroundColor)
  {
    this.backgroundColor = backgroundColor;
  }

  public int getBackgroundPattern()
  {
    return this.backgroundPattern;
  }

  public void setBackgroundPattern(int backgroundPattern)
  {
    this.backgroundPattern = backgroundPattern;
  }

  public int getBorder()
  {
    return this.border;
  }

  public void setBorder(int border)
  {
    this.border = border;
  }

  public int getBorderStyle()
  {
    return this.borderStyle;
  }

  public void setBorderStyle(int borderStyle)
  {
    this.borderStyle = borderStyle;
  }

  public String getBorderColor()
  {
    return this.borderColor;
  }

  public void setBorderColor(String borderColor)
  {
    this.borderColor = borderColor;
  }

  public int getAlign()
  {
    return this.align;
  }

  public void setAlign(int align)
  {
    this.align = align;
  }
}