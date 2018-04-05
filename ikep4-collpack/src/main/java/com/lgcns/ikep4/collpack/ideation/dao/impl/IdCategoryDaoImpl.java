/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.collpack.ideation.dao.IdCategoryDao;
import com.lgcns.ikep4.collpack.ideation.model.IdCategory;

/**
 * TODO Javadoc주석작성updateOrderInit
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdCategoryDaoImpl.java 12459 2011-05-20 09:48:02Z loverfairy $
 */
@Repository("idCategoryDao")
public class IdCategoryDaoImpl extends GenericDaoSqlmap<IdCategory, String> implements IdCategoryDao {
	

	public List<IdCategory> listAll(String portalId) {
		
		return sqlSelectForList("collpack.ideation.dao.IdCategory.selectAll", portalId);
	}

	public String create(IdCategory obj) {
		return (String) sqlInsert("collpack.ideation.dao.IdCategory.insert", obj);
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	public IdCategory get(String id) {
		return (IdCategory) sqlSelectForObject("collpack.ideation.dao.IdCategory.select", id);
	}


	public String selectCategoryIdByCategoryName(String categoryName, String portalId) {
		
		IdCategory idCategory = new IdCategory();
		idCategory.setCategoryName(categoryName);
		idCategory.setPortalId(portalId);
		
		return (String)sqlSelectForObject("collpack.ideation.dao.IdCategory.selectCategoryIdByCategoryName",idCategory);
	}

	public void update(IdCategory obj) {
		sqlUpdate("collpack.ideation.dao.IdCategory.update", obj);
	}
	
	
	
	public void updateOrder(String categoryId, int categoryOrder) {
		
		IdCategory idCategory = new IdCategory();
		idCategory.setCategoryId(categoryId);
		idCategory.setCategoryOrder(categoryOrder);
		
		sqlUpdate("collpack.ideation.dao.IdCategory.updateOrder", idCategory);
		
	}
	
	
	public void updateCategoryOrder(String categoryId, int categoryOrder) {
		
		IdCategory idCategory = new IdCategory();
		idCategory.setCategoryId(categoryId);
		idCategory.setCategoryOrder(categoryOrder);
		
		sqlUpdate("collpack.ideation.dao.IdCategory.updateCategoryOrder", idCategory);
		
	}


	public void remove(String idId) {
		sqlDelete("collpack.ideation.dao.IdCategory.delete", idId);
	}
	
	
	
	
}
