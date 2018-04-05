package com.lgcns.ikep4.util.excel;
/**
 * 
 * TODO Javadoc주석작성
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: ExcelViewColumnMapper.java 16247 2011-08-18 04:54:29Z giljae $
 */
public class ExcelViewColumnMapper
{
  private String headerName;
  private String columnProperty;
  private int columnWidth;
  private int columnType;
  private String columnFormat;
  private int columnAlignment;

  public ExcelViewColumnMapper(String headerName, String columnProperty)
  {
    initialize(headerName, columnProperty, 11, 0, "", 0);
  }

  public ExcelViewColumnMapper(String headerName, String columnProperty, int columnWidth) {
    initialize(headerName, columnProperty, columnWidth, 0, "", 0);
  }

  public ExcelViewColumnMapper(String headerName, String columnProperty, int columnWidth, int columnType) {
    initialize(headerName, columnProperty, columnWidth, columnType, "", 0);
  }

  public ExcelViewColumnMapper(String headerName, String columnProperty, int columnWidth, int columnType, String columnFormat) {
    initialize(headerName, columnProperty, columnWidth, columnType, columnFormat, 0);
  }

  public ExcelViewColumnMapper(String headerName, String columnProperty, int columnWidth, int columnType, String columnFormat, int columnAlignment) {
    initialize(headerName, columnProperty, columnWidth, columnType, columnFormat, columnAlignment);
  }

  protected void initialize(String headerName, String columnProperty, int columnWidth, int columnType, String columnFormat, int columnAlignment) {
    this.headerName = headerName;
    this.columnProperty = columnProperty;
    this.columnWidth = columnWidth;
    this.columnType = columnType;
    this.columnFormat = columnFormat;
    this.columnAlignment = columnAlignment;
  }

  public String getHeaderName()
  {
    return this.headerName;
  }

  public void setHeaderName(String headerName)
  {
    this.headerName = headerName;
  }

  public String getColumnProperty()
  {
    return this.columnProperty;
  }

  public void setColumnProperty(String columnProperty)
  {
    this.columnProperty = columnProperty;
  }

  public int getColumnType()
  {
    return this.columnType;
  }

  public void setColumnType(int columnType)
  {
    this.columnType = columnType;
  }

  public int getColumnWidth()
  {
    return this.columnWidth;
  }

  public void setColumnWidth(int columnWidth)
  {
    this.columnWidth = columnWidth;
  }

  public String getColumnFormat()
  {
    return this.columnFormat;
  }

  public void setColumnFormat(String columnFormat)
  {
    this.columnFormat = columnFormat;
  }

  public int getColumnAlignment()
  {
    return this.columnAlignment;
  }

  public void setColumnAlignment(int columnAlignment)
  {
    this.columnAlignment = columnAlignment;
  }
}