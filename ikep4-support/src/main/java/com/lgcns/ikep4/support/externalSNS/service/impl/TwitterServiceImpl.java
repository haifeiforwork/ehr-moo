/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.externalSNS.service.impl;

import java.util.Properties;

import org.springframework.stereotype.Service;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
//import com.lgcns.ikep4.support.externalSNS.base.Constant;
import com.lgcns.ikep4.support.externalSNS.service.TwitterService;
import com.lgcns.ikep4.util.PropertyLoader;


/**
 * 
 * TODO Javadoc주석작성
 *
 * @author 최성우
 * @version $Id: TwitterServiceImpl.java 17315 2012-02-08 04:56:13Z yruyo $
 */
@Service("twitterService")
public class TwitterServiceImpl extends GenericServiceImpl<String, String> implements TwitterService {

	public String twitterUp(String accessToken, String accessTokenSecret, String content) throws TwitterException {
		
		Properties prop = PropertyLoader.loadProperties("/configuration/external-sns.properties");
		String key = prop.getProperty("externalsns.twitter.key");
		String secret = prop.getProperty("externalsns.twitter.secret");
		
		if (log.isDebugEnabled()) {
			log.debug("================= twitterUp ========================");
			log.debug("twitterUp content: " + content);
			log.debug("twitterUp Constant.CONSUMER_KEY: " + key);
			log.debug("twitterUp Constant.CONSUMER_SECRET: " + secret);
		}
		Status status = null;
		Twitter twitter = new Twitter();
		twitter.setOAuthConsumer(key, secret);


		if (log.isDebugEnabled()) {
			log.debug("twitterUp accessToken: " + accessToken);
			log.debug("twitterUp accessTokenSecret: " + accessTokenSecret);
		}

		twitter.setOAuthAccessToken(accessToken, accessTokenSecret);
		status = twitter.updateStatus(content);

		log.debug("twitterUp status1: " + status.getUser().getScreenName());
		String userId = "";
		
		try{
			userId = status.getUser().getScreenName();
		}catch(Exception e){}

		return userId;
		
	}


}
