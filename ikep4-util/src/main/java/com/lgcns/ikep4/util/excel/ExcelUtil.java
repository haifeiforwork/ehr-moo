package com.lgcns.ikep4.util.excel;

import java.beans.PropertyDescriptor;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;


/**
 * Excel파일 읽기, 쓰기 처리 클래스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ExcelUtil.java 18443 2012-05-01 09:46:02Z arthes $
 */
@SuppressWarnings("unchecked")
public final class ExcelUtil {

	private ExcelUtil() {
		throw new AssertionError();
	}

	/**
	 * excel 파일 읽기 excel 파일을 읽어서, 해당 VO객체에 담아서 List를 반환함
	 * 
	 * @param className
	 * @param inp
	 * @return
	 * @throws Exception
	 */
	public static List<Object> readExcel(String className, InputStream inp, int sheetIndex) {

		List<Object> dataList = new ArrayList<Object>();

		try {

			// Excel 파일 열기
			Workbook workbook = WorkbookFactory.create(inp);

			Sheet sheet = workbook.getSheetAt(sheetIndex);

			int rownum = 0;
			int column = 0;

			// 타이틀 정보 읽기(타이틀은 객체의필드명과 동일하여야 함)
			Map titleMap = new HashMap();
			if (sheet.getRow(0) != null) {

				Row row = sheet.getRow(0);
				column = 0;
				for (Cell cell : row) {
					titleMap.put(column, cell.getStringCellValue());
					column++;
				}
			}

			// 객체 필드 타입 정보 저장
			Map<String, String> typeMap = describeType(Class.forName(className).newInstance());

			// Excel Row 정보를, 객체 리스트 정보에 담기
			for (Row row : sheet) {

				try {
					if (rownum > 0){// && row.getCell(0).getCellType() != Cell.CELL_TYPE_BLANK) {

						Map dataMap = new HashMap();

						// Excel Cell 정보를, 객체 정보에 담기

						for (int col = 0; col < titleMap.size(); col++) {

							try {
								Cell cell = row.getCell(col);

								if (typeMap.get(titleMap.get(col)) != null) {

									if (cell != null) {

										// 필드 셋팅
										setFiledForRead(typeMap, titleMap, dataMap, cell, col);

									} else {
										if (typeMap.get(titleMap.get(col)).equals("int")
												|| typeMap.get(titleMap.get(col)).equals("double")) {
											dataMap.put(titleMap.get(col), 0);
										} else if (typeMap.get(titleMap.get(col)).equals("java.lang.String")) {
											dataMap.put(titleMap.get(col), null);
										} else if (typeMap.get(titleMap.get(col)).equals("boolean")) {
											dataMap.put(titleMap.get(col), null);
										} else if (typeMap.get(titleMap.get(col)).equals("java.util.Date")) {
											dataMap.put(titleMap.get(col), null);
										} else {
											dataMap.put(titleMap.get(col), null);
										}
									}
								}
							} catch (Exception e) {
								// ex.printStackTrace();
							}

						}

						// Excel에서 읽은 정보를, 해당 객체에 저장함
						Object data = Class.forName(className).newInstance();
						BeanUtils.populate(data, dataMap);
						dataList.add(data);
					}

					rownum++;

				} catch (Exception e) {
					// ex.printStackTrace();
				}
			}

			inp.close();

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		// 객체 리스트 반환
		return dataList;
	}
	
	
	public static List<Object> readEcmExcel(String className, FileInputStream inp, int sheetIndex) {

		List<Object> dataList = new ArrayList<Object>();

		try {

			// Excel 파일 열기
			Workbook workbook = WorkbookFactory.create(inp);

			Sheet sheet = workbook.getSheetAt(sheetIndex);

			int rownum = 0;
			int column = 0;

			// 타이틀 정보 읽기(타이틀은 객체의필드명과 동일하여야 함)
			Map titleMap = new HashMap();
			if (sheet.getRow(0) != null) {

				Row row = sheet.getRow(0);
				column = 0;
				for (Cell cell : row) {
					titleMap.put(column, cell.getStringCellValue());
					column++;
				}
			}

			// 객체 필드 타입 정보 저장
			Map<String, String> typeMap = describeType(Class.forName(className).newInstance());

			// Excel Row 정보를, 객체 리스트 정보에 담기
			for (Row row : sheet) {

				try {
					if (rownum > 0){// && row.getCell(0).getCellType() != Cell.CELL_TYPE_BLANK) {

						Map dataMap = new HashMap();

						// Excel Cell 정보를, 객체 정보에 담기

						for (int col = 0; col < titleMap.size(); col++) {

							try {
								Cell cell = row.getCell(col);

								if (typeMap.get(titleMap.get(col)) != null) {

									if (cell != null) {

										// 필드 셋팅
										setFiledForRead(typeMap, titleMap, dataMap, cell, col);

									} else {
										if (typeMap.get(titleMap.get(col)).equals("int")
												|| typeMap.get(titleMap.get(col)).equals("double")) {
											dataMap.put(titleMap.get(col), 0);
										} else if (typeMap.get(titleMap.get(col)).equals("java.lang.String")) {
											dataMap.put(titleMap.get(col), null);
										} else if (typeMap.get(titleMap.get(col)).equals("boolean")) {
											dataMap.put(titleMap.get(col), null);
										} else if (typeMap.get(titleMap.get(col)).equals("java.util.Date")) {
											dataMap.put(titleMap.get(col), null);
										} else {
											dataMap.put(titleMap.get(col), null);
										}
									}
								}
							} catch (Exception e) {
								// ex.printStackTrace();
							}

						}

						// Excel에서 읽은 정보를, 해당 객체에 저장함
						Object data = Class.forName(className).newInstance();
						BeanUtils.populate(data, dataMap);
						dataList.add(data);
					}

					rownum++;

				} catch (Exception e) {
					// ex.printStackTrace();
				}
			}

			inp.close();

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		// 객체 리스트 반환
		return dataList;
	}

	/**
	 * 필드 셋팅
	 * 
	 * @param typeMap
	 * @param titleMap
	 * @param dataMap
	 * @param cell
	 * @param col
	 */
	private static void setFiledForRead(Map<String, String> typeMap, Map titleMap, Map dataMap, Cell cell, int col) {

		if (typeMap.get(titleMap.get(col)).equals("int") || typeMap.get(titleMap.get(col)).equals("java.lang.Integer")) {
			
			getIntField(titleMap, dataMap, cell, col);
			
		} else if (typeMap.get(titleMap.get(col)).equals("long")
				|| typeMap.get(titleMap.get(col)).equals("java.lang.Long")) {

			getLongField(titleMap, dataMap, cell, col);

		} else if (typeMap.get(titleMap.get(col)).equals("double")
				|| typeMap.get(titleMap.get(col)).equals("java.lang.Double")) {

			getDoubleField(titleMap, dataMap, cell, col);

		} else if (typeMap.get(titleMap.get(col)).equals("float")
				|| typeMap.get(titleMap.get(col)).equals("java.lang.Float")) {

			getFloatField(titleMap, dataMap, cell, col);

		} else if (typeMap.get(titleMap.get(col)).equals("java.lang.String")) {

			getStringField(titleMap, dataMap, cell, col);

		} else if (typeMap.get(titleMap.get(col)).equals("boolean")) {

			getBooleanField(titleMap, dataMap, cell, col);

		} else if (typeMap.get(titleMap.get(col)).equals("java.util.Date")) {

			getDateField(titleMap, dataMap, cell, col);

		} else {
			dataMap.put(titleMap.get(col), null);
		}

	}

	/**
	 * Int 타입 셀 얻기
	 * 
	 * @param titleMap
	 * @param dataMap
	 * @param cell
	 * @param col
	 */
	private static void getIntField(Map titleMap, Map dataMap, Cell cell, int col) {

		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			dataMap.put(titleMap.get(col), (int) cell.getNumericCellValue());
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			dataMap.put(titleMap.get(col), Integer.parseInt(cell.getStringCellValue()));
		} else {
			dataMap.put(titleMap.get(col), 0);
		}

	}

	/**
	 * Long 타입 셀 얻기
	 * 
	 * @param titleMap
	 * @param dataMap
	 * @param cell
	 * @param col
	 */
	private static void getLongField(Map titleMap, Map dataMap, Cell cell, int col) {

		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			dataMap.put(titleMap.get(col), (long) cell.getNumericCellValue());
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			dataMap.put(titleMap.get(col), Long.parseLong(cell.getStringCellValue()));
		} else {
			dataMap.put(titleMap.get(col), 0);
		}

	}

	/**
	 * Double 타입 셀 얻기
	 * 
	 * @param titleMap
	 * @param dataMap
	 * @param cell
	 * @param col
	 */
	private static void getDoubleField(Map titleMap, Map dataMap, Cell cell, int col) {

		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			dataMap.put(titleMap.get(col), (double) cell.getNumericCellValue());
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			dataMap.put(titleMap.get(col), Double.parseDouble(cell.getStringCellValue()));
		} else {
			dataMap.put(titleMap.get(col), 0);
		}

	}

	/**
	 * Float 타입 셀 얻기
	 * 
	 * @param titleMap
	 * @param dataMap
	 * @param cell
	 * @param col
	 */
	private static void getFloatField(Map titleMap, Map dataMap, Cell cell, int col) {

		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			dataMap.put(titleMap.get(col), (float) cell.getNumericCellValue());
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			dataMap.put(titleMap.get(col), Float.parseFloat(cell.getStringCellValue()));
		} else {
			dataMap.put(titleMap.get(col), 0);
		}

	}

	/**
	 * String 타입 셀 얻기
	 * 
	 * @param titleMap
	 * @param dataMap
	 * @param cell
	 * @param col
	 */
	private static void getStringField(Map titleMap, Map dataMap, Cell cell, int col) {

		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			dataMap.put(titleMap.get(col), String.valueOf((int) cell.getNumericCellValue()));
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			dataMap.put(titleMap.get(col), cell.getStringCellValue());
		} else {
			dataMap.put(titleMap.get(col), null);
		}

	}

	/**
	 * Boolean 타입 셀 얻기
	 * 
	 * @param titleMap
	 * @param dataMap
	 * @param cell
	 * @param col
	 */
	private static void getBooleanField(Map titleMap, Map dataMap, Cell cell, int col) {

		if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			dataMap.put(titleMap.get(col), cell.getBooleanCellValue());
		} else {
			dataMap.put(titleMap.get(col), null);
		}
	}

	/**
	 * Date 타입 셀 얻기
	 * 
	 * @param titleMap
	 * @param dataMap
	 * @param cell
	 * @param col
	 */
	private static void getDateField(Map titleMap, Map dataMap, Cell cell, int col) {

		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			dataMap.put(titleMap.get(col), String.valueOf((int) cell.getNumericCellValue()));
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			dataMap.put(titleMap.get(col), cell.getStringCellValue());
		} else {
			dataMap.put(titleMap.get(col), null);
		}
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

	public static String saveExcel(Map<String, String> titleMap, List<Object> dataList, String fileName,
			HttpServletResponse response) {

		return saveExcel(titleMap, dataList, fileName, response, 0, null);

	}
	
	public static String saveExcel(Map<String, String> titleMap, List<Object> dataList, String fileName,
			HttpServletResponse response, String sheetTitle) {

		return saveExcel(titleMap, dataList, fileName, response, 0, sheetTitle);

	}
	
	public static String saveExcel(Map<String, String> titleMap, List<Object> dataList, String fileNameStr,
			HttpServletResponse response, int sheetIndex) {
		
		return saveExcel(titleMap, dataList, fileNameStr, response, sheetIndex, null);
	}

	public static String saveExcel(Map<String, String> titleMap, List<Object> dataList, String fileNameStr,
			HttpServletResponse response, int sheetIndex, String sheetTitle) {

		try {

			// WorkBook 초기화
			Workbook[] wbs = new Workbook[] { new XSSFWorkbook() };

			for (int i = 0; i < wbs.length; i++) {

				// Sheet 초기화
				Workbook workbook = wbs[i];

				Sheet sheet = null;
				if(sheetIndex == 0 && sheetTitle != null){
					sheet = workbook.createSheet(sheetTitle);
				} else {
					for (int sIndex = 0; sIndex < sheetIndex + 1; sIndex++) {
						sheet = workbook.createSheet();
					}
				}
				
				DataFormat dataformat = workbook.createDataFormat();
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

				// 객체 필드 타입 정보 저장
				Map<String, String> typeMap = null;
				if (dataList.get(0) != null) {
					typeMap = describeType(dataList.get(0));
				}

				// 객체 List 정보를, Excel Row 정보로 담기
				for (Object data : dataList) {

					try {
						Map dataMap = BeanUtils.describe(data);

						column = 0;
						row = sheet.createRow(rownum);

						// 객체 정보를, Excel Cell 정보로 담기
						for (String key : titleMap.keySet()) {

							try {
								cell = row.createCell(column);

								cellStyle = workbook.createCellStyle();
								dataformat = workbook.createDataFormat();

								if (dataMap.get(key) != null) {

									// 필드 셋팅
									setFiledForSave(typeMap, dataMap, cell, cellStyle, dataformat, key);

								} else {
									cell.setCellValue("");
								}

							} catch (Exception e) {
								// ex.printStackTrace();
							}

							column++;
						}

					} catch (Exception e) {
						// ex.printStackTrace();
					}

					rownum++;

				}

				// CellSize 자동 맞춤
				column = 0;
				for (String key : titleMap.keySet()) {
					sheet.autoSizeColumn(column);
					column++;
				}

				// Excel 파일 저장
				String fileName = fileNameStr;
				fileName = new String(fileName.getBytes("euc-kr"), "8859_1");
				BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
				// OutputStream out = response.getOutputStream();

				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
				response.setHeader("Pragma", "no-cache;");
				response.setHeader("Expires", "-1;");

				workbook.write(out);

				out.flush();
				out.close();

			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
		return null;
	}

	/**
	 * 필드 셋팅
	 * 
	 * @param typeMap
	 * @param dataMap
	 * @param cell
	 * @param cellStyle
	 * @param dataformat
	 * @param key
	 * @throws ParseException
	 */
	private static void setFiledForSave(Map<String, String> typeMap, Map dataMap, Cell cell, CellStyle cellStyle,
			DataFormat dataformat, String key) throws ParseException {

		if (typeMap.get(key).equals("int") || typeMap.get(key).equals("java.lang.Integer")) {
			cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
			cellStyle.setDataFormat(dataformat.getFormat("#,###"));
			cell.setCellStyle(cellStyle);
			cell.setCellValue(Integer.parseInt((String) dataMap.get(key)));

		} else if (typeMap.get(key).equals("long") || typeMap.get(key).equals("java.lang.Long")) {
			cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
			cellStyle.setDataFormat(dataformat.getFormat("#,###"));
			cell.setCellStyle(cellStyle);
			cell.setCellValue(Long.parseLong((String) dataMap.get(key)));

		} else if (typeMap.get(key).equals("double") || typeMap.get(key).equals("java.lang.Double")) {
			cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
			cellStyle.setDataFormat(dataformat.getFormat("#,###.##"));
			cell.setCellStyle(cellStyle);
			cell.setCellValue(Double.parseDouble((String) dataMap.get(key)));

		} else if (typeMap.get(key).equals("float") || typeMap.get(key).equals("java.lang.Float")) {
			cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
			cellStyle.setDataFormat(dataformat.getFormat("#,###.##"));
			cell.setCellStyle(cellStyle);
			cell.setCellValue(Float.parseFloat((String) dataMap.get(key)));

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
			DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss 'KST' yyyy", Locale.ENGLISH);
			cell.setCellValue(dateFormat.parse((String) dataMap.get(key)));

		} else {
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			cellStyle.setDataFormat(dataformat.getFormat("text"));
			cell.setCellStyle(cellStyle);
			cell.setCellValue("");
		}
	}

	/**
	 * 객체 필드 타입 정보 얻어오기
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	private static Map<String, String> describeType(Object obj) {

		Map<String, String> description = new HashMap<String, String>();
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(obj);
		for (int i = 0; i < descriptors.length; ++i) {
			String name = descriptors[i].getName();
			if (PropertyUtils.getReadMethod(descriptors[i]) != null) {
				description.put(name, descriptors[i].getPropertyType().getName());
			}
		}
		return description;
	}

}
