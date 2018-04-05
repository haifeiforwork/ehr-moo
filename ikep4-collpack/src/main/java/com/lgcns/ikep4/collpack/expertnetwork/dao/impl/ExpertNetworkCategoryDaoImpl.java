/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkCategoryDao;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkCategory;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkCategoryPK;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.base.model.CategorySortOrderMap;

/**
 * Expert Network ExpertCategoryDao implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkCategoryDaoImpl.java 16321 2011-08-22 00:33:29Z giljae $
 */
@Repository
public class ExpertNetworkCategoryDaoImpl extends GenericDaoSqlmap<ExpertNetworkCategory, ExpertNetworkCategoryPK> implements ExpertNetworkCategoryDao {
	private static final String NAMESPACE = "collpack.expertnetwork.dao.ExpertNetworkCategory.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ExpertNetworkCategory get(ExpertNetworkCategoryPK id) {
		return (ExpertNetworkCategory)sqlSelectForObject(NAMESPACE + "get", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(ExpertNetworkCategoryPK id) {
		ExpertNetworkCategory category = get(id);
		if (null == category) {
			return false;
		}
		return category.getCategoryId().equals(id.getCategoryId());
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public ExpertNetworkCategoryPK create(ExpertNetworkCategory object) {
		sqlInsert(NAMESPACE + "create", object);
		return (ExpertNetworkCategoryPK)object;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(ExpertNetworkCategory object) {
		sqlUpdate(NAMESPACE + "update", object);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(ExpertNetworkCategoryPK id) {
		sqlDelete(NAMESPACE + "remove", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkCategoryDao#getRootCategory(java.lang.String)
	 */
	public ExpertNetworkCategory getRootCategory(String portalId) {
		return (ExpertNetworkCategory)sqlSelectForObject(NAMESPACE + "getRootCategory", portalId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkCategoryDao#listByCategoryParentId(java.lang.String)
	 */
	public List<ExpertNetworkCategory> listByCategoryParentId(String CategoryParentId) {
		return (List<ExpertNetworkCategory>)sqlSelectForList(NAMESPACE + "listByCategoryParentId", CategoryParentId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkCategoryDao#listAndChildCountByCategoryParentId(java.lang.String)
	 */
	public List<ExpertNetworkCategory> listAndChildCountByCategoryParentId(String CategoryParentId) {
		return (List<ExpertNetworkCategory>)sqlSelectForList(NAMESPACE + "listAndChildCountByCategoryParentId", CategoryParentId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkCategoryDao#getLastSortOrder(java.lang.String)
	 */
	public int getLastSortOrder(String categoryParentId) {
		return (Integer)sqlSelectForObject(NAMESPACE + "getLastSortOrder", categoryParentId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkCategoryDao#removeByCategoryIdHierarchy(java.lang.String)
	 */
	public int removeByCategoryIdHierarchy(String categoryId) {
		return sqlDelete(NAMESPACE + "removeByCategoryIdHierarchy", categoryId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkCategoryDao#updateSortOrder(com.lgcns.ikep4.support.base.model.CategorySortOrderMap)
	 */
	public void updateSortOrder(CategorySortOrderMap sortOrderMap) {
		sqlUpdate(NAMESPACE + "updateSortOrder", sortOrderMap);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkCategoryDao#updateSortOrderPlus(com.lgcns.ikep4.support.base.model.CategorySortOrderMap)
	 */
	public void updateSortOrderPlus(CategorySortOrderMap sortOrderMap) {
		sqlUpdate(NAMESPACE + "updateSortOrderPlus", sortOrderMap);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkCategoryDao#updateSortOrderMinus(com.lgcns.ikep4.support.base.model.CategorySortOrderMap)
	 */
	public void updateSortOrderMinus(CategorySortOrderMap sortOrderMap) {
		sqlUpdate(NAMESPACE + "updateSortOrderMinus", sortOrderMap);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkCategoryDao#updateSortOrderCategoryParentId(com.lgcns.ikep4.support.base.model.CategorySortOrderMap)
	 */
	public void updateSortOrderCategoryParentId(CategorySortOrderMap sortOrderMap) {
		sqlUpdate(NAMESPACE + "updateSortOrderCategoryParentId", sortOrderMap);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkCategoryDao#listRandomCategoryByCount(int, java.lang.String)
	 */
	public List<ExpertNetworkCategory> listRandomCategoryByCount(int categoryCount, String portalId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categoryCount", categoryCount);
		map.put("portalId", portalId);

		return (List<ExpertNetworkCategory>)sqlSelectForList(NAMESPACE + "listRandomCategoryByCount", map);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkCategoryDao#countByCategoryName(com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkCategory)
	 */
	public int countByCategoryName(ExpertNetworkCategory expertNetworkCategory) {
		return (Integer)sqlSelectForObject(NAMESPACE + "countByCategoryName", expertNetworkCategory);
	}

}
