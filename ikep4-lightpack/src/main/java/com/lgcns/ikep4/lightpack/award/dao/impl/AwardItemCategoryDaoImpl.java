/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.award.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.award.dao.AwardItemCategoryDao;
import com.lgcns.ikep4.lightpack.award.model.AwardItemCategory;
import com.lgcns.ikep4.support.message.model.Message;

/**
 * AwardItemDao 구현체 클래스
 */
@Repository
public class AwardItemCategoryDaoImpl extends GenericDaoSqlmap<AwardItemCategory, String> implements AwardItemCategoryDao {

	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "lightpack.award.dao.awardItemCategory.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.dao.AwardItemCategoryDao#listCategoryAwardItem(java.util.Map)
	 */
	public List<AwardItemCategory> listCategoryAwardItem(AwardItemCategory categoryAwardId) {
		return this.sqlSelectForList(NAMESPACE + "listCategoryAwardItem",categoryAwardId);
	}
	public void createCategoryNm(AwardItemCategory awardItemCategory) {
		this.sqlInsert(NAMESPACE+"createCategoryNm",awardItemCategory);
	}
	
	public void deleteCategoryNm(AwardItemCategory awardItemCategory) {
		this.sqlUpdate(NAMESPACE+"deleteCategoryNm",awardItemCategory);
	}
	
	public void updateCategoryNm(AwardItemCategory awardItemCategory) {
		this.sqlUpdate(NAMESPACE+"updateCategoryNm",awardItemCategory);
	}
	
	public void updateCategoryAlign(AwardItemCategory awardItemCategory) {
		this.sqlUpdate(NAMESPACE+"updateCategoryAlign",awardItemCategory);
	}
	
	@Deprecated
	public String create(AwardItemCategory arg0) {
		return null;
	}



	@Deprecated
	public boolean exists(String arg0) {
		return false;
	}



	@Deprecated
	public AwardItemCategory get(String arg0) {
		return null;
	}



	@Deprecated
	public void remove(String arg0) {
		
	}



	@Deprecated
	public void update(AwardItemCategory arg0) {
		
	}
}
