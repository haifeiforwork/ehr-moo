/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.qna.dao.QnaCategoryDao;
import com.lgcns.ikep4.collpack.qna.model.QnaCategory;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * TODO Javadoc주석작성updateOrderInit
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaCategoryDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository("qnaCategoryDao")
public class QnaCategoryDaoImpl extends GenericDaoSqlmap<QnaCategory, String> implements QnaCategoryDao {
	

	public List<QnaCategory> listAll(String portalId) {
		
		return sqlSelectForList("collpack.qna.dao.QnaCategory.selectAll", portalId);
	}

	public String create(QnaCategory obj) {
		return (String) sqlInsert("collpack.qna.dao.QnaCategory.insert", obj);
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	public QnaCategory get(String id) {
		return (QnaCategory) sqlSelectForObject("collpack.qna.dao.QnaCategory.select", id);
	}


	public String selectCategoryIdByCategoryName(String categoryName, String portalId) {
		
		QnaCategory qnaCategory = new QnaCategory();
		qnaCategory.setCategoryName(categoryName);
		qnaCategory.setPortalId(portalId);
		
		return (String)sqlSelectForObject("collpack.qna.dao.QnaCategory.selectCategoryIdByCategoryName",qnaCategory);
	}

	public void update(QnaCategory obj) {
		sqlUpdate("collpack.qna.dao.QnaCategory.update", obj);
	}
	
	
	
	public void updateOrder(String categoryId, int categoryOrder) {
		
		QnaCategory qnaCategory = new QnaCategory();
		qnaCategory.setCategoryId(categoryId);
		qnaCategory.setCategoryOrder(categoryOrder);
		
		sqlUpdate("collpack.qna.dao.QnaCategory.updateOrder", qnaCategory);
		
	}
	
	
	public void updateCategoryOrder(String categoryId, int categoryOrder) {
		
		QnaCategory qnaCategory = new QnaCategory();
		qnaCategory.setCategoryId(categoryId);
		qnaCategory.setCategoryOrder(categoryOrder);
		
		sqlUpdate("collpack.qna.dao.QnaCategory.updateCategoryOrder", qnaCategory);
		
	}


	public void remove(String qnaId) {
		sqlDelete("collpack.qna.dao.QnaCategory.delete", qnaId);
	}
	
	
	
	
}
