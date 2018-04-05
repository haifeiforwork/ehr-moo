package com.lgcns.ikep4.util.pdf;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletResponse;

import com.chilkatsoft.CkMht;
import com.chilkatsoft.CkString;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;


/**
 * PDF 다운로드 처리
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ExcelUtil.java 16848 2011-10-20 07:46:53Z giljae $
 */
@SuppressWarnings("unchecked")
public final class MhtUtil {

	static int BUFFER_SIZE = 8192;

	private MhtUtil() {
		throw new AssertionError();
	}

	public static void downloadMhtByStr(HttpServletResponse response, String htmlDocument, String downFileName) {

		BufferedOutputStream out = null;
		String mhtString = "";

		try {

			// Create a MHT object...
			System.loadLibrary("chilkat");

			CkMht mht = new CkMht();
			mht.UnlockComponent("anything for 30-day trial");

			CkString strMht = new CkString();
			boolean success = mht.HtmlToMHT(htmlDocument, strMht);
			if (success) {
				// Save the string to a file...
				//strMht.saveToFile("d:/test.mht", "windows-1252");

				// To access the MHT string, do this:
				mhtString = strMht.getString();

				// Excel 파일 저장
				downFileName = new String(downFileName.getBytes("euc-kr"), "8859_1");
				out = new BufferedOutputStream(response.getOutputStream());

				response.setContentType("text/html");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + downFileName + "\";");
				response.setHeader("Pragma", "no-cache;");
				response.setHeader("Expires", "-1;");

				BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(mhtString.getBytes()));

				byte b[] = new byte[BUFFER_SIZE];
				int read = 0;

				while ((read = in.read(b)) != -1) {
					out.write(b, 0, read);
				}

				out.flush();
				out.close();
			} else {
				throw new IKEP4ApplicationException("", null);
			}

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
}
