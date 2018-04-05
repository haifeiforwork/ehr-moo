package com.lgcns.ikep4.util.excel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * TODO Javadoc주석작성
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: ExcelViewModel.java 16247 2011-08-18 04:54:29Z giljae $
 */
public class ExcelViewModel
  implements Serializable
{
  private static final long serialVersionUID = -462689290812595201L;
  private String fileName;
  private String sheetName;
  private String title;
  private List columnList = new ArrayList();
  private List DataList = new ArrayList();
  private ExcelViewStyle titleStyle;
  private ExcelViewStyle headerStyle;
  private ExcelViewStyle dataStyle;
  private int maxRowLimitNewSheet;
  
  public ExcelViewModel()
  {
    setTitleStyle(getDefaultTitleStyle());
    setHeaderStyle(getDefaultHeaderStyle());
    setDataStyle(getDefaultDataStyle());
  }

  public ExcelViewStyle getDefaultTitleStyle()
  {
    ExcelViewStyle defaultTitleStyle = new ExcelViewStyle();
    defaultTitleStyle.setBold(true);
    defaultTitleStyle.setFontName("arial");
    defaultTitleStyle.setPointSize(14);
    return defaultTitleStyle;
  }

  public ExcelViewStyle getDefaultHeaderStyle()
  {
    ExcelViewStyle defaultHeaderStyle = new ExcelViewStyle();
    defaultHeaderStyle.setBold(true);
    defaultHeaderStyle.setFontName("tahoma");
    defaultHeaderStyle.setPointSize(9);
    defaultHeaderStyle.setBackgroundColor("#c0c0c0");
    defaultHeaderStyle.setBorder(1);
    defaultHeaderStyle.setBorderStyle(1);
    defaultHeaderStyle.setAlign(2);
    return defaultHeaderStyle;
  }

  public ExcelViewStyle getDefaultDataStyle()
  {
    ExcelViewStyle defaultDataStyle = new ExcelViewStyle();
    defaultDataStyle.setBold(false);
    defaultDataStyle.setFontName("tahoma");
    defaultDataStyle.setPointSize(9);
    defaultDataStyle.setBorder(1);
    defaultDataStyle.setBorderStyle(1);
    return defaultDataStyle;
  }

  public ExcelViewStyle getTitleStyle()
  {
    if (this.titleStyle != null) {
      return this.titleStyle;
    }

    return getDefaultTitleStyle();
  }

  public void setTitleStyle(ExcelViewStyle titleStyle)
  {
    this.titleStyle = titleStyle;
  }

  public ExcelViewStyle getHeaderStyle()
  {
    if (this.headerStyle != null) {
      return this.headerStyle;
    }

    return getDefaultHeaderStyle();
  }

  public void setHeaderStyle(ExcelViewStyle headerStyle)
  {
    this.headerStyle = headerStyle;
  }

  public ExcelViewStyle getDataStyle()
  {
    if (this.dataStyle != null) {
      return this.dataStyle;
    }

    return getDefaultDataStyle();
  }

  public void setDataStyle(ExcelViewStyle dataStyle)
  {
    this.dataStyle = dataStyle;
  }

  public String getFileName()
  {
    return this.fileName;
  }

  public void setFileName(String fileName)
  {
    this.fileName = fileName;
  }

  public String getSheetName()
  {
    return this.sheetName;
  }

  public void setSheetName(String sheetName)
  {
    this.sheetName = sheetName;
  }

  public List getColumnList()
  {
    return this.columnList;
  }

  public void setColumnList(List columnList)
  {
    this.columnList = columnList;
  }

  public List getDataList()
  {
    return this.DataList;
  }

  public void setDataList(List dataList)
  {
    this.DataList = dataList;
  }

  public ExcelViewModel addColumn(ExcelViewColumnMapper excelViewColumnMapper)
  {
    this.columnList.add(excelViewColumnMapper);
    return this;
  }

  public ExcelViewModel addColumn(String headerName, String columnProperty)
  {
    ExcelViewColumnMapper excelViewColumnMapper = new ExcelViewColumnMapper(headerName, columnProperty);
    return addColumn(excelViewColumnMapper);
  }

  public ExcelViewModel addColumn(String headerName, String columnProperty, int columnWidth)
  {
    ExcelViewColumnMapper excelViewColumnMapper = new ExcelViewColumnMapper(headerName, columnProperty, columnWidth);
    return addColumn(excelViewColumnMapper);
  }

  public ExcelViewModel addColumn(String headerName, String columnProperty, int columnWidth, int columnType)
  {
    ExcelViewColumnMapper excelViewColumnMapper = new ExcelViewColumnMapper(headerName, columnProperty, columnWidth, columnType);
    return addColumn(excelViewColumnMapper);
  }

  public ExcelViewModel addColumn(String headerName, String columnProperty, int columnWidth, int columnType, String columnFormat)
  {
    ExcelViewColumnMapper excelViewColumnMapper = new ExcelViewColumnMapper(headerName, columnProperty, columnWidth, columnType, columnFormat);
    return addColumn(excelViewColumnMapper);
  }

  public ExcelViewModel addColumn(String headerName, String columnProperty, int columnWidth, int columnType, String columnFormat, int columnAlignment)
  {
    ExcelViewColumnMapper excelViewColumnMapper = new ExcelViewColumnMapper(headerName, columnProperty, columnWidth, columnType, columnFormat, columnAlignment);
    return addColumn(excelViewColumnMapper);
  }

  public String getTitle()
  {
    return this.title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

public int getMaxRowLimitNewSheet() {
	return maxRowLimitNewSheet;
}

public void setMaxRowLimitNewSheet(int maxRowLimitNewSheet) {
	this.maxRowLimitNewSheet = maxRowLimitNewSheet;
}
}