/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.socialpack.microblogging.model.Addon;

/**
 * 
 * 마이크로블로그 게시글 내 부가정보(URL축소, 이미지, POLL링크 정보) 관련 서비스
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: AddonService.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Transactional
public interface AddonService extends GenericService<Addon, String> {
	/**
	 * sequence 반환
	 * 
	 * @return int sequence
	 */
	public int getSeq();
	
	/**
	 * 원본url에 대한 Addon 정보 반환
	 * 
	 * @param 원본url
	 * @return Addon 정보
	 */
	public Addon selectBySourceLink(String sourceLink);

	/**
	 * displayCode에 대한  Addon 정보 반환
	 * 
	 * @param displayCode
	 * @return  Addon 정보
	 */
	public Addon selectByDisplayCode(String displayCode);
}
