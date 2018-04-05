/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.externalSNS.service;

import org.springframework.transaction.annotation.Transactional;

import twitter4j.TwitterException;

import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * 
 * TODO Javadoc주석작성
 *
 * @author 최성우
 * @version $Id: TwitterService.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Transactional
public interface TwitterService extends GenericService<String, String> {
	
	public String twitterUp			(String accessToken, String accessTokenSecret, String content) throws TwitterException;
}
