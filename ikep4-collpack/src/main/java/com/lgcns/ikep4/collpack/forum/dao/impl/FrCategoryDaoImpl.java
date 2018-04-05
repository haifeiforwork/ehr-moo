/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.forum.dao.FrCategoryDao;
import com.lgcns.ikep4.collpack.forum.model.FrCategory;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * TODO Javadoc주석작성updateOrderInit
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrCategoryDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository("frCategoryDao")
public class FrCategoryDaoImpl extends GenericDaoSqlmap<FrCategory, String> implements FrCategoryDao {
	

	public String create(FrCategory obj) {
		return (String) sqlInsert("collpack.fourm.dao.FrCategory.create", obj);
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}
	
	public FrCategory get(String id) {
		return (FrCategory) sqlSelectForObject("collpack.fourm.dao.FrCategory.get", id);
	}

	
	public List<FrCategory> list(String portalId) {
		
		return sqlSelectForList("collpack.fourm.dao.FrCategory.list", portalId);
	}

	public void update(FrCategory obj) {
		sqlUpdate("collpack.fourm.dao.FrCategory.update", obj);
	}
	
	
	
	public void updateOrder(String categoryId, int categoryOrder) {
		
		FrCategory frCategory = new FrCategory();
		frCategory.setCategoryId(categoryId);
		frCategory.setCategoryOrder(categoryOrder);
		
		sqlUpdate("collpack.fourm.dao.FrCategory.updateOrder", frCategory);
		
	}
	
	
	public void remove(String id) {
		sqlDelete("collpack.fourm.dao.FrCategory.remove", id);
	}
	
	
}
