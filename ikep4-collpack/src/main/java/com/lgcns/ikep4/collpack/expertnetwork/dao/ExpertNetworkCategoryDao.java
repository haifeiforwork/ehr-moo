/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkCategory;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkCategoryPK;
import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.base.model.CategorySortOrderMap;

/**
 * Expert Network ExpertCategoryDao interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkCategoryDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface ExpertNetworkCategoryDao extends GenericDao<ExpertNetworkCategory, ExpertNetworkCategoryPK> {

	/**
	 * Root Category 반환 (portal별로)
	 * @param portalId
	 * @return ExpertCategory
	 */
	ExpertNetworkCategory getRootCategory(String portalId);

	/**
	 * Category List 반환
	 * CategoryParentId에 해당하는 노드들만
	 * @param CategoryParentId
	 * @return List<ExpertCategory>
	 */
	List<ExpertNetworkCategory> listByCategoryParentId(String CategoryParentId);

	/**
	 * Category List 반환
	 * CategoryParentId에 해당하는 노드들만
	 * ChildCategory 의 개수 포함
	 * @param CategoryParentId
	 * @return List<ExpertCategory>
	 */
	List<ExpertNetworkCategory> listAndChildCountByCategoryParentId(String CategoryParentId);

	/**
	 * categoryParentId 에 속한 노드들의 마지막 정렬순서
	 * @param categoryParentId
	 * @return int
	 */
	int getLastSortOrder(String categoryParentId);

	/**
	 * 
	 * Category Delete (categoryId 에 해당하는 모든 자식 삭제 및 자기자신 삭제)
	 * @param categoryId
	 * @return int
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
	 * Root Category 를 제외한 모든 Category 랜덤하게 조회
	 * @param categoryCount - 카테고리 개수
	 * @param portalId - portalId
	 * @return List<ExpertCategory>
	 */
	List<ExpertNetworkCategory> listRandomCategoryByCount(int categoryCount, String portalId);

	/**
	 * 카테고리명으로 조회 (개수)
	 * @param expertNetworkCategory
	 * @return int - 조회된 건수
	 */
	int countByCategoryName(ExpertNetworkCategory expertNetworkCategory);
}
