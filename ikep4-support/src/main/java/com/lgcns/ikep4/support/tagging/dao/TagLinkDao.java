/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.tagging.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.tagging.model.Tag;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: TagLinkDao.java 16276 2011-08-18 07:09:07Z giljae $
 */
public interface TagLinkDao extends GenericDao<Tag, String>  {
	
	/**
	 * 
	 * @return
	 */
	public Tag read(String tagId, String tagItemId, String tagItemType);
	
	
	/**
	 * tag link 삭제
	 * @param tagId
	 * @param tagItemId
	 * @param tagItemType
	 */
	public void remove(String tagId, String tagItemId, String tagItemType);
	
	
	public void removeByItem(String tagItemId, String tagItemType);
	
	
}
