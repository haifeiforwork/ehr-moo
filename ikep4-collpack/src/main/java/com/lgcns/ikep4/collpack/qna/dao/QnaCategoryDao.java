/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.qna.model.QnaCategory;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaCategoryDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface QnaCategoryDao extends GenericDao<QnaCategory, String>  {
	
	/**
	 * 모든 게시물 반환
	 * 
	 * @return
	 */
	List<QnaCategory> listAll(String portalId);
	
	/**
	 * order 순서 변경 순서 더하기1
	 * @param categoryId
	 * @param categoryOrder
	 */
	void updateOrder(String categoryId, int categoryOrder);
	
	
	/**
	 * order 순서 직접 업데이트
	 * @param categoryId
	 * @param categoryOrder
	 */
	void updateCategoryOrder(String categoryId, int categoryOrder);
	
	
	/**
	 * 카테고리 이름으로 카테고리 ID찾기
	 * @param categoryName
	 * @param portalId TODO
	 * @return
	 */
	String selectCategoryIdByCategoryName(String categoryName, String portalId);
	
	
}
