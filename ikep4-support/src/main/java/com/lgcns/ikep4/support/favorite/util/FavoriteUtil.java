package com.lgcns.ikep4.support.favorite.util;

import java.util.Properties;

import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * FavoriteUtil 클래스
 * 
 * @author 유승목
 * @version $Id: FavoriteUtil.java 17315 2012-02-08 04:56:13Z yruyo $
 */
public final class FavoriteUtil {

	private FavoriteUtil() {
		throw new AssertionError();
	}

	/**
	 * 타켓 아이템의 URL 생성
	 * 
	 * @param module
	 * @param type
	 * @return
	 */
	public static String makeTargetUrl(String module, String typeStr) {

		Properties prop = PropertyLoader.loadProperties("/configuration/common-conf.properties");

		String targetUrl = "";
		String type = typeStr;
		if (StringUtil.isEmpty(type)) {
			type = "";
		}

		if (module.equals("BBS") || module.equals("BD")) {
			targetUrl = prop.getProperty("ikep4.url.detail.bbs");
		} else if (module.equals("Workspace") || module.equals("WS")) {
			if (type.equals("ANNOUNCE")) {
				targetUrl = prop.getProperty("ikep4.url.detail.collaboration.announce");
			} else if (type.equals("WEEKLY")) {
				targetUrl = prop.getProperty("ikep4.url.detail.collaboration.weekly");
			} else {
				targetUrl = prop.getProperty("ikep4.url.detail.collaboration.bbs");
			}
		} else if (module.equals("Blog") || module.equals("SB")) {
			targetUrl = prop.getProperty("ikep4.url.detail.blog");
		} else if (module.equals("MB")) {
			targetUrl = prop.getProperty("ikep4.url.detail.mblog");
		} else if (module.equals("Cafe") || module.equals("CF")) {
			targetUrl = prop.getProperty("ikep4.url.detail.cafe");
		} else if (module.equals("Manual") || module.equals("WM")) {
			targetUrl = prop.getProperty("ikep4.url.detail.manual");
		} else if (module.equals("QA")) {
			targetUrl = prop.getProperty("ikep4.url.detail.qna");
		} else if (module.equals("Forum") || module.equals("FR")) {
			targetUrl = prop.getProperty("ikep4.url.detail.forum");
		} else if (module.equals("Profile") || module.equals("PF")) {
			targetUrl = prop.getProperty("ikep4.url.detail.profile");
		} else if (module.equals("Idea") || module.equals("ID")) {
			targetUrl = prop.getProperty("ikep4.url.detail.idea");
		} else if (module.equals("WW")) {
			targetUrl = prop.getProperty("ikep4.url.detail.who");
		} else if (module.equals("Corporate Voca")|| module.equals("CV")) {
			targetUrl = prop.getProperty("ikep4.url.detail.corvoca");
		} else if (module.equals("KMS")) {
			targetUrl = prop.getProperty("ikep4.url.detail.kms");
		}

		return targetUrl;

	}

}
