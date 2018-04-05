/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.service;

import java.util.List;

import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkCategory;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkCategoryPK;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * Expert Network ExpertCategoryService interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkCategoryService.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface ExpertNetworkCategoryService extends GenericService<ExpertNetworkCategory, ExpertNetworkCategoryPK> {
	/**
	 *  Category 반환 
	 * @param category
	 * @return
	 */
	ExpertNetworkCategory read(ExpertNetworkCategoryPK pk);
	
	/**
	 * Root Category 반환 (없다면 생성)
	 * @param category
	 * @return ExpertCategory
	 */
	ExpertNetworkCategory readCreateRootCategory(ExpertNetworkCategory category);

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
	 * Category List 반환
	 * CategoryParentId에 해당하는 노드들만
	 * ChildCategory 의 개수 포함
	 * Catagory Tag 정보 포함
	 * @param CategoryParentId
	 * @return List<ExpertCategory>
	 */
	List<ExpertNetworkCategory> listAndChildCountAndTagsByCategoryParentId(String CategoryParentId);

	/**
	 * Category 생성 (Tag도 함께)
	 * Tree에서 생성후 해당 노드의 추가를 위해 편의상 Category 전체를 리턴한다.
	 * @param category
	 * @return ExpertCategory
	 */
	ExpertNetworkCategory createWithTags(ExpertNetworkCategory category);

	/**
	 * Category 수정 (Tag도 함께)
	 * Tag는 몽땅삭제후 다시 입력
	 * @param category
	 */
	void updateWithTags(ExpertNetworkCategory category);

	/**
	 * Category 삭제 (Tag도 함께)
	 * @param categoryId
	 */
	void deleteWithTagsHierarchy(String categoryId);

	/**
	 * Category 이동 (다른 부모 카테고리로 이동)
	 * @param sourceId
	 * @param sourceParentId
	 * @param sourceSortOrder
	 * @param targetParentId
	 * @param targetSortOrder
	 */
	void moveOuterCategory(String sourceId, String sourceParentId, int sourceSortOrder, String targetParentId, int targetSortOrder);

	/**
	 * Category 이동 (동일 부모 카테고리에서 이동)
	 * @param sourceId
	 * @param sourceParentId
	 * @param sourceSortOrder
	 * @param targetSortOrder
	 */
	void moveInnerCategory(String sourceId, String sourceParentId, int sourceSortOrder, int targetSortOrder);

	/**
	 * 카테고리명으로 조회 (개수)
	 * @param expertNetworkCategory
	 * @return int - 조회된 건수
	 */
	int countByCategoryName(ExpertNetworkCategory expertNetworkCategory);

}
