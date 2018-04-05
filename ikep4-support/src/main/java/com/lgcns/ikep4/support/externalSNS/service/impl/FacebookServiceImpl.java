/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.externalSNS.service.impl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookJsonRestClient;
import com.google.code.facebookapi.ProfileField;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
//import com.lgcns.ikep4.support.externalSNS.base.Constant;
import com.lgcns.ikep4.support.externalSNS.service.FacebookService;
import com.lgcns.ikep4.util.PropertyLoader;


/**
 * 
 * TODO Javadoc주석작성
 *
 * @author 최성우
 * @version $Id: FacebookServiceImpl.java 17315 2012-02-08 04:56:13Z yruyo $
 */
@Service("facebookService")
public class FacebookServiceImpl extends GenericServiceImpl<String, String> implements FacebookService {

	public String createFacebook(String sessionKey, String message) throws FacebookException {
		if (log.isDebugEnabled()) {
			log.debug("================= createFacebook ========================");
			log.debug("create content: " + message);
		}

		//sessionKey = user.getFacebookAuthCode();
		Properties prop = PropertyLoader.loadProperties("/configuration/external-sns.properties");
		String key = prop.getProperty("externalsns.facebook.key");
		String secret = prop.getProperty("externalsns.facebook.secret");
		

		FacebookJsonRestClient facebook = new FacebookJsonRestClient(key, secret, sessionKey);
		FacebookJsonRestClient facebookClient = (FacebookJsonRestClient) facebook;
		
		// 글 등록
		facebookClient.stream_publish(message, null, null, null, null);

		List<Long> longUsers = new ArrayList<Long>();
		longUsers.add(facebook.users_getLoggedInUser());
		
		JSONArray json2 = facebookClient.users_getInfo(longUsers,
                EnumSet.of(ProfileField.UID, ProfileField.EMAIL_HASHES, ProfileField.PROXIED_EMAIL, ProfileField.NAME, ProfileField.SEX, ProfileField.BIRTHDAY));
		if (log.isDebugEnabled()) {
			//log.debug( "#######################################44# : " + json2.toString());
		}

		String uid = "";
		try {
			JSONObject jsonobject = json2.getJSONObject(0);
			if (log.isDebugEnabled()) {
				//log.debug( "#######################################44# jsonobject.getString(uid).toString():" + jsonobject.getString("uid").toString());
			}
			uid = jsonobject.getString("uid").toString();
		} catch (JSONException e) {
			if (log.isDebugEnabled()) {
				log.debug( "JSONException:"+e);
			}
			//e.printStackTrace();
		}
		return uid;
		
	}


}
