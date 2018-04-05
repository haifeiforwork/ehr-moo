package com.lgcns.ikep4.servicepack.survey.util;

import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * Excel파일 읽기, 쓰기 처리 클래스
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: ExcelUtil.java 16244 2011-08-18 04:11:42Z giljae $
 */
@SuppressWarnings("unchecked")
public final class ExcelUtil {
	private ExcelUtil() {
	}
	
	/**
	 * excel 파일 저장 List를 담겨진 객체 정보를, xcel 파일에 저장함
	 * 
	 * @param titleMap
	 * @param dataList
	 * @param fileName
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static String saveExcel(Map<String, String> titleMap, Map<String, String> typeMap,List<Object> dataList, String fileName,
			HttpServletResponse response) {

		try {

			// WorkBook 초기화
			Workbook[] wbs = new Workbook[] { new XSSFWorkbook() };

			for (int i = 0; i < wbs.length; i++) {

				// Sheet 초기화
				Workbook workbook = wbs[i];

				Sheet sheet = workbook.createSheet();

				//DataFormat dataformat = workbook.createDataFormat();
				CellStyle cellStyle = workbook.createCellStyle();

				Row row;
				Cell cell;

				int rownum = 0;
				int column = 0;

				// 타이틀 정보 저장
				row = sheet.createRow(rownum);
				for (String key : titleMap.keySet()) {
					cell = row.createCell(column);
					cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
					cell.setCellStyle(cellStyle);
					cell.setCellValue((String) titleMap.get(key));
					column++;
				}

				rownum++;

				// 객체 List 정보를, Excel Row 정보로 담기
				for (Object data : dataList) {

					try {
						getExcelBody(titleMap, typeMap, workbook, sheet, rownum, data);

					} catch (Exception e) {
						continue;
					}

					rownum++;

				}

				// CellSize 자동 맞춤
				
				column = 0;
				for (int j = 0; j < titleMap.keySet().size(); j++) {
					sheet.autoSizeColumn(column);
					column++;
				}

				// Excel 파일 저장
				OutputStream out = response.getOutputStream();
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
				response.setHeader("Pragma", "no-cache;");
				response.setHeader("Expires", "-1;");

				workbook.write(out);
				
				out.flush();
				out.close();

			}
		} catch (Exception e) {
			
		}
		return null;
	}

	/**
	 * 엑셀 데이터 만들기
	 * @param titleMap
	 * @param typeMap
	 * @param workbook
	 * @param sheet
	 * @param rownum
	 * @param data
	 */
	private static void getExcelBody(Map<String, String> titleMap, Map<String, String> typeMap, Workbook workbook,
			Sheet sheet, int rownum, Object data) {
		DataFormat dataformat;
		CellStyle cellStyle;
		Row row;
		Cell cell;
		int column;
		Map<String, String> dataMap = (LinkedHashMap<String, String>)data;

		column = 0;
		row = sheet.createRow(rownum);

		// 객체 정보를, Excel Cell 정보로 담기
		for (String key : titleMap.keySet()) {

			try {
				cell = row.createCell(column);

				cellStyle = workbook.createCellStyle();
				dataformat = workbook.createDataFormat();

				if (dataMap.get(key) != null) {
					getColumType(typeMap, dataformat, cellStyle, cell, dataMap, key);
				} else {
					cell.setCellValue("");
				}

			} catch (Exception e) {
				continue;
			}

			column++;
		}
	}

	/**
	 * 엑셀 컬럼 타입설정
	 * @param typeMap
	 * @param dataformat
	 * @param cellStyle
	 * @param cell
	 * @param dataMap
	 * @param key
	 * @throws ParseException
	 */
	private static void getColumType(Map<String, String> typeMap, DataFormat dataformat, CellStyle cellStyle,
			Cell cell, Map<String, String> dataMap, String key) throws ParseException {
		if (typeMap.get(key).equals("int") || typeMap.get(key).equals("double")) {
			cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
			cellStyle.setDataFormat(dataformat.getFormat("#,###"));
			cell.setCellStyle(cellStyle);
			cell.setCellValue(Integer.parseInt((String) dataMap.get(key)));

		} else if (typeMap.get(key).equals("java.lang.String")) {
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			cellStyle.setDataFormat(dataformat.getFormat("text"));
			cell.setCellStyle(cellStyle);
			cell.setCellValue((String) dataMap.get(key));

		} else if (typeMap.get(key).equals("boolean")) {
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			cellStyle.setDataFormat(dataformat.getFormat("text"));
			cell.setCellStyle(cellStyle);
			cell.setCellValue((String) dataMap.get(key));

		} else if (typeMap.get(key).equals("java.util.Date")) {
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			cellStyle.setDataFormat(dataformat.getFormat("yyyy/mm/dd hh:mm:ss"));
			cell.setCellStyle(cellStyle);
			DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss 'KST' yyyy",
					Locale.ENGLISH);
			cell.setCellValue(dateFormat.parse((String) dataMap.get(key)));

		} else {
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			cellStyle.setDataFormat(dataformat.getFormat("text"));
			cell.setCellStyle(cellStyle);
			cell.setCellValue("");
		}
	}
}
