package com.lgcns.ikep4.portal.util;

/**
 * 브라우져 체크 클래스
 * 
 * @author 임종상
 * @version $Id: BrowserCheck.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class BrowserCheck {

	/**
	 * String[][] iBrowser - 인터넷 브라우저 리스트, 필요한 경우 나중에 추가
	 */
	private String[][] iBrowser;

	/**
	 * Default Constructor
	 */
	public BrowserCheck() {
		iBrowser = new String[][] {
				// {user-agent String , Internet Browser}
				{ "MSIE 9.0", "Internet Explorer 9" }, { "MSIE 8.0", "Internet Explorer 8" },
				{ "MSIE 7.0", "Internet Explorer 7" }, { "MSIE 6.0", "Internet Explorer 6" },
				{ "MSIE 5.5", "Internet Explorer 5.5" }, { "MSIE 5.0", "Internet Explorer 5" },
				{ "MSIE 4.01", "Internet Explorer 4.01" }, { "Firefox", "Firefox" }, { "Chrome", "Chrome" },
				{ "Safari", "Safari" } };
	}

	/**
	 * 사용자의 인터넷 브라우저 정보를 찾는다.
	 * 
	 * @param agentString - user-agent 문자열 request.getHeader("User-Agent")
	 * @return 사용자 인터넷 브라우저
	 */
	public String getInternetBrowser(String agentString) {
		String browser = "Unknown";

		for (int i = 0; i < iBrowser.length; i++) {
			if (agentString.toLowerCase().indexOf(iBrowser[i][0].toLowerCase()) > -1) {
				browser = iBrowser[i][1];

				// Chrome Browser는 애플웹엔진을 사용하므로, useragent에 safari, chrome 둘다
				// 포함되어 있다. 따라서, chrome이면 루프를 빠져나온다.
				if ("chrome".equals(iBrowser[i][0].toLowerCase())) {
					break;
				}
			}
		}

		return browser;
	}
}
