package com.lgcns.ikep4.util.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;


/**
 * HttpUtil
 * 
 * @author 유승목
 * @version $Id: HttpUtil.java 16247 2011-08-18 04:54:29Z giljae $
 */
public final class HttpUtil {
	
	public static final int RANDOM_SIZE = 10;
	
	private HttpUtil() {
		throw new AssertionError();
	}

	/**
	 * WebApp 의 주소를 반환
	 * 
	 * @param req
	 * @return
	 */
	public static String getWebAppUrl(HttpServletRequest req) {

		StringBuffer requestUrl = req.getRequestURL();
		String servletPath = req.getServletPath();

		return requestUrl.substring(0, requestUrl.indexOf(servletPath));

	}

	/**
	 * 더블 서브밋 체크를 위한 Token 셋팅
	 * 
	 * @param req
	 * @return
	 */
	public static String setToken(HttpServletRequest req) {

		String token = "";

		try {

			HttpSession session = req.getSession(true);

			token = StringUtil.randomStr(RANDOM_SIZE);

			session.setAttribute("token", token);

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return token;
	}

	/**
	 * 더블 서브밋 Token 체크
	 * 
	 * @param req
	 * @return
	 */
	public static boolean isValidToken(HttpServletRequest req) {

		boolean check = false;

		try {

			HttpSession session = req.getSession(true);
			String requestToken = req.getParameter("token");

			String sessionToken = (String) session.getAttribute("token");

			if (requestToken == null || sessionToken == null) {
				check = false;
			} else {
				check = requestToken.equals(sessionToken);
			}

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return check;
	}

	/**
	 * 브라우져 타입 얻어 오기
	 * 
	 * @param request
	 * @return
	 */
	public static String getBrowser(HttpServletRequest request) {

		String header = request.getHeader("User-Agent");

		if (header.indexOf("MSIE") > -1) {
			return "MSIE";
		} else if (header.indexOf("Chrome") > -1) {
			return "Chrome";
		} else if (header.indexOf("Opera") > -1) {
			return "Opera";
		}
		return "Firefox";
	}

	/**
	 * 브라우져 타입별 한글 인코딩된 파일명 가져오기
	 * 
	 * @param filename
	 * @param browser
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getDisposition(String filename, String browser) throws UnsupportedEncodingException {

		String dispositionPrefix = "";
		String encodedFilename = null;

		if (browser.equals("MSIE")) {
			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
		} else if (browser.equals("Firefox")) {
			encodedFilename =

			"\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Opera")) {
			encodedFilename =

			"\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Chrome")) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < filename.length(); i++) {
				char c = filename.charAt(i);
				if (c > '~') {
					sb.append(URLEncoder.encode("" + c, "UTF-8"));
				} else {
					sb.append(c);
				}
			}
			encodedFilename = sb.toString();
		} else {
			throw new IKEP4ApplicationException("", null);
		}

		return dispositionPrefix + encodedFilename;
	}

}
