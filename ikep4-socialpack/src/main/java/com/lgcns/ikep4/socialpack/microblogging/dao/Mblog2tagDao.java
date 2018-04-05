/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.socialpack.microblogging.model.Mblog2tag;


/**
 * 
 * Mblog2tag 관련 처리 DAO
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: Mblog2tagDao.java 16246 2011-08-18 04:48:28Z giljae $
 */

public interface Mblog2tagDao extends GenericDao<Mblog2tag, Mblog2tag> {

	/**
	 * mblogId에 해당하는 mblog의 모든 tag 반환
	 * 
	 * @param mblogId
	 * @return Mblog2tag List
	 */
	public List<Mblog2tag> selectListByMblogId(String mblogId);

}
