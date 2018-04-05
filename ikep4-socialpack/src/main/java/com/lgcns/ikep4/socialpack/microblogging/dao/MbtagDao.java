/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.socialpack.microblogging.model.Mbtag;


/**
 * 
 * Mbtag 관련 처리 DAO
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: MbtagDao.java 16246 2011-08-18 04:48:28Z giljae $
 */

public interface MbtagDao extends GenericDao<Mbtag, Mbtag> {

	/**
	 * 기존에  태그 full Name으로 등록되어 있으면 그 태그아이디를 반환
	 * 
	 * @param mbtag (@나 # 등을 포함하고 있는 태그)
	 * @return  태그아이디
	 */
	public String selectTagIdByTagFullName(Mbtag mbtag);

}
