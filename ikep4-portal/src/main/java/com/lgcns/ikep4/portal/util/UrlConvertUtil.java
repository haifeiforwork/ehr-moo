package com.lgcns.ikep4.portal.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * UrlConvertUtil
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: UrlConvertUtil.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class UrlConvertUtil {
	
	/**
	 * UrlConvertUtil public method
	 *
	 */
	public void urlConverter() {
		
	}
	
	/**
	 * url의 파라미터 변환
	 * @param url URL
	 * @param user 사용자 정보
	 * @return String 변환된 URL
	 */
	public static String urlConverter(String url, User user) {
		
		String returnUrl = "";
		
		if(!StringUtil.isEmpty(url)) {
			String tempUrl = url;
			
			Pattern p = Pattern.compile("\\$\\{[a-zA-Z]+\\}");	// ${영문대소문자} 형식으로 제한
			Matcher m = p.matcher(tempUrl);
			
			while(m.find()) {
				String changeValue = getValue(m.group(), user);
			
				tempUrl = tempUrl.replace(m.group(), changeValue);
			}
			
			returnUrl = tempUrl;
		}
		
		return returnUrl;
	}
	
	/**
	 * URL의 파라미터 값을 추출하여 변환
	 * @param group 파라미터 종류
	 * @param user 사용자 정보
	 * @return String 변환된 파라미터 값
	 */
	private static String getValue(String group, User user) {
		
		String returnValue = "";
		
		if(!StringUtil.isEmpty(group)) {
			
			// 추가적으로 parameter로 사용하고자 하는 user의 세션정보가 필요할 경우  if문 추가
			if(group.equals("${userId}")) {
				returnValue = user.getUserId();
			} else if(group.equals("${portalId}")) {
				returnValue = user.getPortalId();
			} else if(group.equals("${empNo}")) {
				returnValue = user.getEmpNo();
			} else if(group.equals("${groupId}")) {
				returnValue = user.getGroupId();
			} else {
				// parameter와 user 세션 정보를 매핑할 수 없다면 그대로 반환
				returnValue = group;
			}
		}
		
		return returnValue;
	}

}
