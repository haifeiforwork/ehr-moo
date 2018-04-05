/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.dictionary.constants;

/**
 * TODO Javadoc주석작성
 * 
 * @author 서혜숙(shs0420@nate.com)
 * @version $Id: DictionaryConstants.java 16236 2011-08-18 02:48:22Z giljae $
 */
public final class DictionaryConstants {

	private DictionaryConstants() {}
	
	/**
	 * 페이지당 레코드수(디폴트)
	 */
	public static final int DICTIONARY_PAGE_PER_RECORD = 10;	
	
	/**
	 * 페이지당 레코드수(태그)
	 */
	public static final int DICTIONARY_TAG_PAGE_PER_RECORD = 10;
	
	/**
	 * 페이지당 레코드수(링크)
	 */
	public static final int DICTIONARY_LINK_PAGE_PER_RECORD = 5;	
	
	/**
	 * 버전 증가
	 */
	public static final Double DICTIONARY_VERSION_INCREASE = 0.1;	

	
	
}
