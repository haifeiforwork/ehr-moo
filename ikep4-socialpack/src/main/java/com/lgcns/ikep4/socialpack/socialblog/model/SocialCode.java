/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 소셜 블로그 내 사용 하는 Code 값 생성 VO
 *
 * @author 이형운
 * @version $Id: SocialCode.java 16246 2011-08-18 04:48:28Z giljae $
 */
public class SocialCode<T> extends BaseObject {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 2610312387243396554L;

	/**
	 * key 값
	 */
	private T key;
	
	/**
	 * value 값
	 */
	private String value; 

	/**
	 * key 값 반환
	 * @return key 값
	 */
	public T getKey() {
		return key;
	} 
	
	/**
	 * value 값 반환
	 * @return value 값
	 */
	public String getValue() {
		return value;
	}  
	 
	/**
	 * key, valu값 받아 Object 생성
	 * @param key key 값
	 * @param value value 값
	 */
	public SocialCode(T key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
}
