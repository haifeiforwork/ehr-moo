/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapCategory;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapCategoryPK;
import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.base.model.CategorySortOrderMap;

/**
 * Knowledge Map KnowledgeCategoryDao interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapCategoryDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface KnowledgeMapCategoryDao extends GenericDao<KnowledgeMapCategory, KnowledgeMapCategoryPK> {

	/**
	 * Root Category 반환 (portal별로)
	 * @param portalId
	 * @return
	 */
	KnowledgeMapCategory getRootCategory(String portalId);

	/**
	 * Category List 반환
	 * CategoryParentId에 해당하는 노드들만
	 * @param CategoryParentId
	 * @return
	 */
	List<KnowledgeMapCategory> listByCategoryParentId(String CategoryParentId);

	/**
	 * Category List 반환
	 * CategoryParentId에 해당하는 노드들만
	 * ChildCategory 의 개수 포함
	 * @param CategoryParentId
	 * @return
	 */
	List<KnowledgeMapCategory> listAndChildCountByCategoryParentId(String CategoryParentId);

	/**
	 * categoryParentId 에 속한 노드들의 마지막 정렬순서
	 * @param categoryParentId
	 * @return
	 */
	int getLastSortOrder(String categoryParentId);

	/**
	 * 
	 * Category Delete (categoryId 에 해당하는 모든 자식 삭제 및 자기자신 삭제)
	 * @param categoryId
	 * @return
	 */
	int removeByCategoryIdHierarchy(String categoryId);

	/**
	 * sortOrder (정렬순서) 수정
	 * @param sortOrderMap
	 */
	void updateSortOrder(CategorySortOrderMap sortOrderMap);

	/**
	 * sortOrder (정렬순서) 수정 (일괄 증가)
	 * @param sortOrderMap
	 */
	void updateSortOrderPlus(CategorySortOrderMap sortOrderMap);
	
	/**
	 * sortOrder (정렬순서) 수정 (일괄 감소)
	 * @param sortOrderMap
	 */
	void updateSortOrderMinus(CategorySortOrderMap sortOrderMap);

	/**
	 * sortOrder, categoryParentId 수정
	 * @param sortOrderMap
	 */
	void updateSortOrderCategoryParentId(CategorySortOrderMap sortOrderMap);

	/**
	 * 카테고리명으로 조회 (개수)
	 * @param knowledgeMapCategory
	 * @return int - 조회된 건수
	 */
	int countByCategoryName(KnowledgeMapCategory knowledgeMapCategory);
}
