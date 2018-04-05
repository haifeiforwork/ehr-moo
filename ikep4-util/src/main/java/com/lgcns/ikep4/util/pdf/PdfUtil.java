package com.lgcns.ikep4.util.pdf;

import java.awt.Insets;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.zefer.pd4ml.PD4ML;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;


/**
 * PDF 다운로드 처리
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ExcelUtil.java 16848 2011-10-20 07:46:53Z giljae $
 */
@SuppressWarnings("unchecked")
public final class PdfUtil {

	static int topValue = 10;

	static int leftValue = 10;

	static int rightValue = 10;

	static int bottomValue = 10;

	private PdfUtil() {
		throw new AssertionError();
	}

	/**
	 * URL요청해서 리턴된 html 페이지를, PDF 파일로 변환함
	 * 
	 * @param response
	 * @param fileUrl
	 * @param downFileName
	 * @return
	 */
	public static void downloadPdfByUrl(HttpServletResponse response, String fileUrl, String downFileName) {

		BufferedOutputStream out = null;

		try {

			PD4ML pd4ml = new PD4ML();

			// pd4ml.setHtmlWidth(userSpaceWidth);
			// pd4ml.addStyle("BODY {margin: 0}", true);
			// pd4ml.setPageSize(pd4ml.changePageOrientation(PD4Constants.A4));

			pd4ml.setPageInsetsMM(new Insets(topValue, leftValue, bottomValue, rightValue));

			pd4ml.useTTF("java:fonts", true);
			// pd4ml.setDefaultTTFs("Dotum", "Gulim", "Batang");

			// Excel 파일 저장
			downFileName = new String(downFileName.getBytes("euc-kr"), "8859_1");
			out = new BufferedOutputStream(response.getOutputStream());

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + downFileName + "\";");
			response.setHeader("Pragma", "no-cache;");
			response.setHeader("Expires", "-1;");

			pd4ml.enableDebugInfo();
			pd4ml.render(new URL(fileUrl), out);

			out.flush();
			out.close();

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception ex) {
					throw new IKEP4ApplicationException("", ex);
				}
			}
		}
	}

	/**
	 * 로컬 html 페이지를, PDF 파일로 변환함, HTML 파일은 UTF-8로 저장되어 있어야 함
	 * 
	 * @param response
	 * @param fileName
	 * @param downFileName
	 * @return
	 */
	public static void downloadPdfByFile(HttpServletResponse response, String fileName, String downFileName) {

		try {
			String htmlDocument = readFile(fileName, "UTF-8");
			downloadPdfByStr(response, htmlDocument, downFileName);
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

	/**
	 * html string을, PDF 파일로 변환함, HTML 파일은 UTF-8로 저장되어 있어야 함
	 * 
	 * @param response
	 * @param htmlDocument
	 * @param downFileName
	 * @return
	 */
	public static void downloadPdfByStr(HttpServletResponse response, String htmlDocument, String downFileName) {

		BufferedOutputStream out = null;

		try {

			PD4ML pd4ml = new PD4ML();

			// pd4ml.setHtmlWidth(userSpaceWidth);
			// pd4ml.addStyle("BODY {margin: 0}", true);
			// pd4ml.setPageSize(pd4ml.changePageOrientation(PD4Constants.A4));

			pd4ml.setPageInsetsMM(new Insets(topValue, leftValue, bottomValue, rightValue));

			pd4ml.useTTF("java:fonts", true);
			// pd4ml.setDefaultTTFs("Dotum", "Gulim", "Batang");

			// Excel 파일 저장
			downFileName = new String(downFileName.getBytes("euc-kr"), "8859_1");
			out = new BufferedOutputStream(response.getOutputStream());

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + downFileName + "\";");
			response.setHeader("Pragma", "no-cache;");
			response.setHeader("Expires", "-1;");

			pd4ml.enableDebugInfo();
			pd4ml.render(new StringReader(htmlDocument), out);

			out.flush();
			out.close();

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception ex) {
					throw new IKEP4ApplicationException("", ex);
				}
			}
		}
	}

	private static String readFile(String path, String encoding) throws IOException {

		File f = new File(path);
		FileInputStream is = new FileInputStream(f);
		BufferedInputStream bis = new BufferedInputStream(is);

		ByteArrayOutputStream fos = new ByteArrayOutputStream();
		byte buffer[] = new byte[2048];

		int read;
		do {
			read = is.read(buffer, 0, buffer.length);
			if (read > 0) {
				fos.write(buffer, 0, read);
			}
		} while (read > -1);

		fos.close();
		bis.close();
		is.close();

		return fos.toString(encoding);
	}

}
