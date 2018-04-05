/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.forum.model.FrCategory;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrCategoryDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface FrCategoryDao extends GenericDao<FrCategory, String>  {
	
	/**
	 * 모든 게시물 반환
	 * 
	 * @return
	 */
	public List<FrCategory> list(String portalId);
	
	
	/**
	 * order 순서 업데이트
	 * @param categoryId
	 * @param categoryOrder
	 */
	public void updateOrder(String categoryId, int categoryOrder);
	
	
	
}
