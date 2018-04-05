/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.tagging.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.tagging.model.Tag;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: TagDictionaryDao.java 16276 2011-08-18 07:09:07Z giljae $
 */
public interface TagDictionaryDao extends GenericDao<Tag, String>  {
	
	/**
	 * 포털아이디로 태그 사전 목록 가져오기
	 * 
	 * @return
	 */
	public List<Tag> listByPortalId(String portalId);
	
	/**
	 * 
	 * @param tag
	 * @return
	 */
	public Tag readByTagName(String tagName, String portalId);
	
	
}
