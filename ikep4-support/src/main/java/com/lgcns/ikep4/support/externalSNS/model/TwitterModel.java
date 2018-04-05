/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.externalSNS.model;
import java.net.URL;
import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 
 * TODO Javadoc주석작성
 *
 * @author 홍정관(hong79@cnspartner.com)
 * @version $Id: TwitterModel.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class TwitterModel extends BaseObject {
	
	/**
	 * serial Version UID
	 */
	private static final long serialVersionUID = 7810278732861455328L;
	
	private String token;
	private String tokenSecret;
	private String userId;
	
	private long id;
	private String name;
	private String screenName;
	private URL url;
	private String text;
	private Date createAt;
	private URL profileImage;
	private String source;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public URL getUrl() {
		return url;
	}
	public void setUrl(URL url) {
		this.url = url;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public URL getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(URL profileImage) {
		this.profileImage = profileImage;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the tokenSecret
	 */
	public String getTokenSecret() {
		return tokenSecret;
	}
	/**
	 * @param tokenSecret the tokenSecret to set
	 */
	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
