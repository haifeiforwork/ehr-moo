/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapCategoryDao;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapCategory;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapCategoryPK;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.base.model.CategorySortOrderMap;

/**
 * Knowledge Map KnowledgeCategoryDao implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapCategoryDaoImpl.java 16457 2011-08-30 04:20:17Z giljae $
 */
@Repository
public class KnowledgeMapCategoryDaoImpl extends GenericDaoSqlmap<KnowledgeMapCategory, KnowledgeMapCategoryPK> implements KnowledgeMapCategoryDao {
	private static final String NAMESPACE = "collpack.knowledgehub.dao.KnowledgeMapCategory.";

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public KnowledgeMapCategory get(KnowledgeMapCategoryPK id) {
		return (KnowledgeMapCategory)sqlSelectForObject(NAMESPACE + "get", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(KnowledgeMapCategoryPK id) {
		KnowledgeMapCategory category = get(id);
		if (null == category) {
			return false;
		}
		return category.getCategoryId().equals(id.getCategoryId());
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public KnowledgeMapCategoryPK create(KnowledgeMapCategory object) {
		sqlInsert(NAMESPACE + "create", object);
		return (KnowledgeMapCategoryPK)object;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(KnowledgeMapCategory object) {
		sqlUpdate(NAMESPACE + "update", object);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(KnowledgeMapCategoryPK id) {
		sqlDelete(NAMESPACE + "remove", id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapCategoryDao#getRootCategory(java.lang.String)
	 */
	public KnowledgeMapCategory getRootCategory(String portalId) {
		return (KnowledgeMapCategory)sqlSelectForObject(NAMESPACE + "getRootCategory", portalId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapCategoryDao#listByCategoryParentId(java.lang.String)
	 */
	public List<KnowledgeMapCategory> listByCategoryParentId(String CategoryParentId) {
		return (List<KnowledgeMapCategory>)sqlSelectForList(NAMESPACE + "listByCategoryParentId", CategoryParentId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapCategoryDao#listAndChildCountByCategoryParentId(java.lang.String)
	 */
	public List<KnowledgeMapCategory> listAndChildCountByCategoryParentId(String CategoryParentId) {
		return (List<KnowledgeMapCategory>)sqlSelectForList(NAMESPACE + "listAndChildCountByCategoryParentId", CategoryParentId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapCategoryDao#getLastSortOrder(java.lang.String)
	 */
	public int getLastSortOrder(String categoryParentId) {
		return (Integer)sqlSelectForObject(NAMESPACE + "getLastSortOrder", categoryParentId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapCategoryDao#removeByCategoryIdHierarchy(java.lang.String)
	 */
	public int removeByCategoryIdHierarchy(String categoryId) {
		return sqlDelete(NAMESPACE + "removeByCategoryIdHierarchy", categoryId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapCategoryDao#updateSortOrder(com.lgcns.ikep4.support.base.model.CategorySortOrderMap)
	 */
	public void updateSortOrder(CategorySortOrderMap sortOrderMap) {
		sqlUpdate(NAMESPACE + "updateSortOrder", sortOrderMap);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapCategoryDao#updateSortOrderPlus(com.lgcns.ikep4.support.base.model.CategorySortOrderMap)
	 */
	public void updateSortOrderPlus(CategorySortOrderMap sortOrderMap) {
		sqlUpdate(NAMESPACE + "updateSortOrderPlus", sortOrderMap);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapCategoryDao#updateSortOrderMinus(com.lgcns.ikep4.support.base.model.CategorySortOrderMap)
	 */
	public void updateSortOrderMinus(CategorySortOrderMap sortOrderMap) {
		sqlUpdate(NAMESPACE + "updateSortOrderMinus", sortOrderMap);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapCategoryDao#updateSortOrderCategoryParentId(com.lgcns.ikep4.support.base.model.CategorySortOrderMap)
	 */
	public void updateSortOrderCategoryParentId(CategorySortOrderMap sortOrderMap) {
		sqlUpdate(NAMESPACE + "updateSortOrderCategoryParentId", sortOrderMap);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapCategoryDao#countByCategoryName(com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapCategory)
	 */
	public int countByCategoryName(KnowledgeMapCategory knowledgeMapCategory) {
		return (Integer)sqlSelectForObject(NAMESPACE + "countByCategoryName", knowledgeMapCategory);
	}

}
