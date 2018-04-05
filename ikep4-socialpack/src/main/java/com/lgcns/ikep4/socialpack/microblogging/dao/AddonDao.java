/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.socialpack.microblogging.model.Addon;


/**
 * 
 * Addon 관련 처리 DAO
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: AddonDao.java 16246 2011-08-18 04:48:28Z giljae $
 */

public interface AddonDao extends GenericDao<Addon, String> {
	/**
	 * sequence 반환
	 * 
	 * @return int seq
	 */
	public int getSeq();
	
	/**
	 * 원본url에 대한  Addon 정보 반환
	 * 
	 * @param 원본url
	 * @return  Addon 정보
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
