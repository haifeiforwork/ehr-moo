package com.lgcns.ikep4.util.excel;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Pattern;
import jxl.format.RGB;
import jxl.format.UnderlineStyle;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.web.servlet.view.document.AbstractJExcelView;
/**
 * 
 * TODO Javadoc주석작성
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id$
 */
public class DefaultJExcelView extends AbstractJExcelView
{
  private int titleRowPosition;
  private int headerRowPosition;
  private int maxRowLimitNewSheet;

  public DefaultJExcelView()
  {
    this.titleRowPosition = 1;
    this.headerRowPosition = 3;
    this.maxRowLimitNewSheet = ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT;
  }

  public void setTitleRowPosition(int titleRowPosition)
  {
    this.titleRowPosition = titleRowPosition;
  }

  public void setHeaderRowPosition(int headerRowPosition)
  {
    this.headerRowPosition = headerRowPosition;
  }
  

  protected void buildExcelDocument(Map model, WritableWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    if ((model == null) || (model.isEmpty())) {
      return;
    }

    ExcelViewModel excelViewModel = getExcelViewModel(model);

    if (excelViewModel == null) {
      return;
    }

    ExcelViewStyle titleStyle = excelViewModel.getTitleStyle();
    ExcelViewStyle headerStyle = excelViewModel.getHeaderStyle();
    ExcelViewStyle dataStyle = excelViewModel.getDataStyle();

    List columns = excelViewModel.getColumnList();
    int rowStart = this.headerRowPosition + 1;
    BeanWrapper beanWrapper = null;
    List dataList = excelViewModel.getDataList();
    int listCount = dataList.size();
    maxRowLimitNewSheet = excelViewModel.getMaxRowLimitNewSheet();
    
    
    
    Map<Integer,WritableCellFormat> writableCellFormatList = getCashCellFormat(dataStyle, columns);
    createSheet(workbook, excelViewModel, titleStyle, headerStyle, columns,listCount);
    
    WritableCellFormat cellFormat = null;
    WritableCell dataCell = null;
    int sheetNum = 0;
    WritableSheet sheet = null;
    int lineCount=0;
    
    for (int row = 0; row < listCount; ++row) {
    	
    		//System.out.println(lineCount+"------sheetNum--------------------------" + sheetNum );
    	
	    	//MAX_ROW_LIMIT_NEW_SHEET  이상이면 시트를 만든다.
	      if( lineCount>= maxRowLimitNewSheet || row==0 )
	      {
	    	  sheet = workbook.getSheet(sheetNum);;
	    	  sheetNum++;
	    	  lineCount=0;
	      }
	      
	    	
	      Object data = dataList.get(row);
	      beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(data);
	
	      for (int index = 0; index < columns.size(); ++index) {
		        ExcelViewColumnMapper columnMap = (ExcelViewColumnMapper)columns.get(index);
		        Object dataValue = beanWrapper.getPropertyValue(columnMap.getColumnProperty());
		
		        if ((dataValue != null) && (StringUtils.hasText(dataValue.toString()))) {
			          cellFormat = writableCellFormatList.get( index );
			          switch (columnMap.getColumnType() )
			          {
				          case ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING:
				            String label = (String)dataValue;
				            dataCell = new Label(index, rowStart + lineCount, label, cellFormat);
				            break;
				          case ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER:
				            java.lang.Number number = NumberUtils.parseNumber(dataValue.toString(), java.lang.Number.class);
				            dataCell = new jxl.write.Number(index, rowStart + lineCount, number.doubleValue(), cellFormat);
				            break;
				          case ExcelModelConstants.EXCEL_COLUMN_TYPE_DATE:
				          case ExcelModelConstants.EXCEL_COLUMN_TYPE_DATETIME:
				          case ExcelModelConstants.EXCEL_COLUMN_TYPE_TIME:
				            Date dateValue = null;
				            if (dataValue instanceof Date)
				              dateValue = (Date)dataValue;
				            else {
				              dateValue = CalendarUtils.parseDate(dataValue.toString(), new String[] { "yyyyMMddHHmmss", "yyyyMMdd", "yyyy-MM-dd", "HH:mm:ss" });
				            }
				            dataCell = new DateTime(index, rowStart + lineCount, dateValue, cellFormat);
				            break;
				          case ExcelModelConstants.EXCEL_COLUMN_TYPE_CODE:
				            String codeValue = "";
				            dataCell = new Label(index, rowStart + lineCount, codeValue, cellFormat);
				       }
			           sheet.addCell(dataCell);
		        } else {
		          cellFormat = writableCellFormatList.get( ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING );
		          dataCell = new Label(index, rowStart + lineCount, "", cellFormat);
		          sheet.addCell(dataCell);
		        }
	      }
	      
	      lineCount++;
	      
    }

    response.setHeader("Content-disposition", "attachment;filename=" + excelViewModel.getFileName());
  }

private Map<Integer,WritableCellFormat> getCashCellFormat(ExcelViewStyle dataStyle, List columns) throws WriteException, Exception {
	
	Map<Integer,WritableCellFormat> writableCellFormatList = new HashMap<Integer,WritableCellFormat>();
	 
	WritableFont dataFont = getFont(WritableFont.createFont(dataStyle.getFontName()), dataStyle);
    WritableCellFormat dataCellFormat = getCellFormat(dataStyle, dataFont);
    
    for (int index = 0; index <columns.size(); ++index) {
          ExcelViewColumnMapper columnMap = (ExcelViewColumnMapper)columns.get(index);
          WritableCellFormat cellFormat = null;
          
          if(columnMap.getColumnType() == ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING)
          {
            cellFormat = new WritableCellFormat(dataCellFormat);
            cellFormat.setAlignment(getColumnAlignment(columnMap.getColumnAlignment()));
          }else if(columnMap.getColumnType() == ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER){
            cellFormat = getNumberFormat(dataCellFormat, columnMap.getColumnFormat());
            cellFormat.setAlignment(getColumnAlignment(columnMap.getColumnAlignment()));
          }else if(columnMap.getColumnType() == ExcelModelConstants.EXCEL_COLUMN_TYPE_DATE ||
        		  columnMap.getColumnType() == ExcelModelConstants.EXCEL_COLUMN_TYPE_DATETIME  ||
        		  columnMap.getColumnType() == ExcelModelConstants.EXCEL_COLUMN_TYPE_TIME){
        	cellFormat = getDateFormat(dataCellFormat, columnMap.getColumnFormat());
          }else if(columnMap.getColumnType() == ExcelModelConstants.EXCEL_COLUMN_TYPE_CODE){
            cellFormat = new WritableCellFormat(dataCellFormat);
            cellFormat.setAlignment(getColumnAlignment(columnMap.getColumnAlignment()));
          }
          
          writableCellFormatList.put(index,cellFormat);
    }
    
    return writableCellFormatList;
}

private ExcelViewModel getExcelViewModel(Map model) {
	
	ExcelViewModel excelViewModel = new ExcelViewModel();
	
	for (Iterator it = model.entrySet().iterator(); it.hasNext(); ) {
      Map.Entry entry = (Map.Entry)it.next();
      Object value = entry.getValue();

      if (value instanceof ExcelViewModel) {
        excelViewModel = (ExcelViewModel)value;
        break;
      }
    }
	return excelViewModel;
}

private void  createSheet(WritableWorkbook workbook, ExcelViewModel excelViewModel, ExcelViewStyle titleStyle,
		ExcelViewStyle headerStyle, List columns,int listCount) throws WriteException, RowsExceededException {
	
	int sheetNum =  listCount/maxRowLimitNewSheet;
	
	for (int i = 0; i <= sheetNum+1; i++) {
		WritableSheet sheet = workbook.createSheet(excelViewModel.getSheetName()+" "+i, i);

		if( i==0 )
		{	
		    WritableFont titleFont = getFont(WritableFont.createFont(titleStyle.getFontName()), titleStyle);
		    WritableCellFormat titleCellFormat = getCellFormat(titleStyle, titleFont);
		    WritableCell titleCell = new Label(0, this.titleRowPosition, excelViewModel.getTitle(), titleCellFormat);
		    sheet.addCell(titleCell);
		}
		
	    WritableFont headerFont = getFont(WritableFont.createFont(headerStyle.getFontName()), headerStyle);
	    WritableCellFormat headerCellFormat = getCellFormat(headerStyle, headerFont);

	    for (int index = 0; index < columns.size(); ++index) {
	      ExcelViewColumnMapper columnMap = (ExcelViewColumnMapper)columns.get(index);
	      WritableCell headerCell = new Label(index, this.headerRowPosition, columnMap.getHeaderName(), headerCellFormat);
	      sheet.addCell(headerCell);
	      sheet.setColumnView(index, columnMap.getColumnWidth());
	    }
	}
}

  private WritableCellFormat getCellFormat(ExcelViewStyle excelViewStyle, WritableFont font)
    throws WriteException
  {
    WritableCellFormat cellFormat = new WritableCellFormat(font);
    if (excelViewStyle.getBackgroundColor() != null) {
      cellFormat.setBackground(getColour(excelViewStyle.getBackgroundColor()), getPattern(excelViewStyle.getBackgroundPattern()));
    }

    cellFormat.setBorder(getBorder(excelViewStyle.getBorder()), getBorderLineStyle(excelViewStyle.getBorderStyle()), getColour(excelViewStyle.getBorderColor()));

    cellFormat.setAlignment(getColumnAlignment(excelViewStyle.getAlign()));
    return cellFormat;
  }

  private WritableFont getFont(WritableFont.FontName fn, ExcelViewStyle excelViewStyle) throws WriteException {
    WritableFont writableFont = new WritableFont(fn);
    writableFont.setBoldStyle((excelViewStyle.isBold()) ? WritableFont.BOLD : WritableFont.NO_BOLD);
    writableFont.setItalic(excelViewStyle.isItalic());
    writableFont.setUnderlineStyle((excelViewStyle.isUnderline()) ? UnderlineStyle.SINGLE : UnderlineStyle.NO_UNDERLINE);
    writableFont.setPointSize(excelViewStyle.getPointSize());
    writableFont.setColour(getColour(excelViewStyle.getColor()));
    return writableFont;
  }

  private WritableCellFormat getDateFormat(WritableCellFormat defaultFormat, String format) throws Exception {
    String pattern = format;
    if (!StringUtils.hasText(pattern)) {
      pattern = "yyyy-MM-dd";
    }
    DateFormat dateFormat = new DateFormat(pattern);
    WritableCellFormat cellFormat = new WritableCellFormat((WritableFont)defaultFormat.getFont(), dateFormat);
    cellFormat.setBorder(Border.LEFT, defaultFormat.getBorder(Border.LEFT));
    cellFormat.setBorder(Border.RIGHT, defaultFormat.getBorder(Border.RIGHT));
    cellFormat.setBorder(Border.TOP, defaultFormat.getBorder(Border.TOP));
    cellFormat.setBorder(Border.BOTTOM, defaultFormat.getBorder(Border.BOTTOM));

    return cellFormat;
  }

  private WritableCellFormat getNumberFormat(WritableCellFormat defaultFormat, String format) throws Exception {
    String pattern = format;
    if (!(StringUtils.hasText(pattern))) {
      pattern = "###.#";
    }
    NumberFormat numberFormat = new NumberFormat(pattern);
    WritableCellFormat cellFormat = new WritableCellFormat((WritableFont)defaultFormat.getFont(), numberFormat);

    cellFormat.setBorder(Border.LEFT, defaultFormat.getBorder(Border.LEFT));
    cellFormat.setBorder(Border.RIGHT, defaultFormat.getBorder(Border.RIGHT));
    cellFormat.setBorder(Border.TOP, defaultFormat.getBorder(Border.TOP));
    cellFormat.setBorder(Border.BOTTOM, defaultFormat.getBorder(Border.BOTTOM));

    return cellFormat;
  }

  private Alignment getColumnAlignment(int alignment) {
    Alignment align = Alignment.LEFT;

    switch (alignment)
    {
    case 2:
      align = Alignment.CENTRE;
      break;
    case 1:
      align = Alignment.LEFT;
      break;
    case 3:
      align = Alignment.RIGHT;
      break;
    case 4:
      align = Alignment.FILL;
      break;
    case 0:
      align = Alignment.GENERAL;
      break;
    case 5:
      align = Alignment.JUSTIFY;
    }

    return align;
  }

  private Colour getColour(String color) {
    Colour[] colours = Colour.getAllColours();
    RGB colRgb = getRgb(color);

    for (int i = 0; i < colours.length; ++i) {
      RGB rgb = colours[i].getDefaultRGB();
      if ((rgb.getRed() == colRgb.getRed()) && (rgb.getGreen() == colRgb.getGreen()) && (rgb.getBlue() == colRgb.getBlue())) {
        return colours[i];
      }
    }
    return null;
  }

  private RGB getRgb(String color) {
    if ((color == null) || (color.trim().length() != 7) || (!(StringUtils.matches(color, "\\#[0-9a-fA-F]*")))) {
      throw new IllegalArgumentException("��������� ���� �ʽ��ϴ�.");
    }
    String red = color.substring(1, 3);
    String green = color.substring(3, 5);
    String blue = color.substring(5, 7);

    int r = NumberUtils.hexStringToInt(red);
    int g = NumberUtils.hexStringToInt(green);
    int b = NumberUtils.hexStringToInt(blue);
    RGB rgb = new RGB(r, g, b);
    return rgb;
  }

  private Pattern getPattern(int pattern) {
    Pattern p = Pattern.NONE;

    switch (pattern)
    {
    case 0:
      p = Pattern.NONE;
      break;
    case 4:
      p = Pattern.GRAY_25;
      break;
    case 2:
      p = Pattern.GRAY_50;
      break;
    case 3:
      p = Pattern.GRAY_75;
      break;
    case 1:
    default:
      p = Pattern.SOLID;
    }

    return p;
  }

  private Border getBorder(int border) {
    Border b = Border.NONE;

    switch (border)
    {
    case 1:
      b = Border.ALL;
      break;
    case 3:
      b = Border.BOTTOM;
      break;
    case 4:
      b = Border.LEFT;
      break;
    case 0:
      b = Border.NONE;
      break;
    case 5:
      b = Border.RIGHT;
      break;
    case 2:
      b = Border.TOP;
    }

    return b;
  }

  private BorderLineStyle getBorderLineStyle(int borderline) {
    BorderLineStyle b = BorderLineStyle.NONE;

    switch (borderline)
    {
    case 3:
      b = BorderLineStyle.DASHED;
      break;
    case 4:
      b = BorderLineStyle.DOTTED;
      break;
    case 6:
      b = BorderLineStyle.DOUBLE;
      break;
    case 7:
      b = BorderLineStyle.HAIR;
      break;
    case 2:
      b = BorderLineStyle.MEDIUM;
      break;
    case 0:
      b = BorderLineStyle.NONE;
      break;
    case 5:
      b = BorderLineStyle.THICK;
      break;
    case 1:
      b = BorderLineStyle.THIN;
    }

    return b;
  }

public int getMaxRowLimitNewSheet() {
	return maxRowLimitNewSheet;
}

public void setMaxRowLimitNewSheet(int maxRowLimitNewSheet) {
	this.maxRowLimitNewSheet = maxRowLimitNewSheet;
}
}
