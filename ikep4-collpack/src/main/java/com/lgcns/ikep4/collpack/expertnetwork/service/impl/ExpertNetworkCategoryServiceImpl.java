/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkCategoryDao;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkCategory;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkCategoryPK;
import com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkCategoryService;
import com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkTaggingService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.base.model.CategorySortOrderMap;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * Expert Network ExpertCategoryService implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkCategoryServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service
@Transactional
public class ExpertNetworkCategoryServiceImpl extends GenericServiceImpl<ExpertNetworkCategory, ExpertNetworkCategoryPK> implements ExpertNetworkCategoryService {

	private ExpertNetworkCategoryDao expertNetworkCategoryDao;

	@Autowired
	public ExpertNetworkCategoryServiceImpl(ExpertNetworkCategoryDao dao) {
		super(dao);
		this.expertNetworkCategoryDao = dao;
	}

	@Autowired
	private IdgenService idgenService;

	@Autowired
	private ExpertNetworkTaggingService expertNetworkTaggingService;

	/**
	 * Root Category 생성
	 * @param category
	 * @return ExpertCategory
	 */
	private ExpertNetworkCategory createRootCategory(ExpertNetworkCategory category) {
		String categoryId = idgenService.getNextId();
		category.setCategoryId(categoryId);
		category.setCategoryParentId(categoryId);
		category.setCategoryName("RootCategory");
		category.setSortOrder(1);

		expertNetworkCategoryDao.create(category);

		return category;
	}

	/* 
	 * Category 삭제시 해당 Tag를 먼저 삭제해한다.
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#delete(java.io.Serializable)
	 */
	@Override
	public void delete(ExpertNetworkCategoryPK id) {
		expertNetworkTaggingService.deleteByCategoryId(id.getCategoryId());
		super.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkCategoryService#readCreateRootCategory(com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkCategory)
	 */
	public ExpertNetworkCategory readCreateRootCategory(ExpertNetworkCategory category) {
		ExpertNetworkCategory rootCategory = expertNetworkCategoryDao.getRootCategory(category.getPortalId());

		if (null == rootCategory) {
			rootCategory = createRootCategory(category);
		}

		return rootCategory;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkCategoryService#listByCategoryParentId(java.lang.String)
	 */
	public List<ExpertNetworkCategory> listByCategoryParentId(String CategoryParentId) {
		return expertNetworkCategoryDao.listByCategoryParentId(CategoryParentId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkCategoryService#listAndChildCountByCategoryParentId(java.lang.String)
	 */
	public List<ExpertNetworkCategory> listAndChildCountByCategoryParentId(String CategoryParentId) {
		return expertNetworkCategoryDao.listAndChildCountByCategoryParentId(CategoryParentId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkCategoryService#listAndChildCountAndTagsByCategoryParentId(java.lang.String)
	 */
	public List<ExpertNetworkCategory> listAndChildCountAndTagsByCategoryParentId(String CategoryParentId) {
		List<ExpertNetworkCategory> categoryList = expertNetworkCategoryDao.listAndChildCountByCategoryParentId(CategoryParentId);

		// Tag정보
		for (ExpertNetworkCategory item : categoryList) {
			item.setTags(expertNetworkTaggingService.getTagsByCategoryId(item.getCategoryId()));
		}

		return categoryList;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkCategoryService#createWithTags(com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkCategory)
	 */
	public ExpertNetworkCategory createWithTags(ExpertNetworkCategory category) {
		String categoryId = idgenService.getNextId();
		category.setCategoryId(categoryId);

		// sortOrder 생성
		int sortOrder = expertNetworkCategoryDao.getLastSortOrder(category.getCategoryParentId());
		category.setSortOrder(++sortOrder);

		// Category 등록
		this.create(category);

		// Tag정보들 등록
		expertNetworkTaggingService.createTags(categoryId, category.getTags());

		return category;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkCategoryService#updateWithTags(com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkCategory)
	 */
	public void updateWithTags(ExpertNetworkCategory category) {
		// Category 수정
		update(category);

		// Tag정보들 수정
		expertNetworkTaggingService.deleteByCategoryId(category.getCategoryId());
		expertNetworkTaggingService.createTags(category.getCategoryId(), category.getTags());
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkCategoryService#deleteWithTagsHierarchy(java.lang.String)
	 */
	public void deleteWithTagsHierarchy(String categoryId) {
		expertNetworkTaggingService.deleteByCategoryIdHierarchy(categoryId);
		expertNetworkCategoryDao.removeByCategoryIdHierarchy(categoryId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkCategoryService#moveOuterCategory(java.lang.String, java.lang.String, int, java.lang.String, int)
	 */
	public void moveOuterCategory(String sourceId, String sourceParentId, int sourceSortOrder, String targetParentId, int targetSortOrder) {
		CategorySortOrderMap parameterMap = new CategorySortOrderMap();

		parameterMap.setCategoryParentId(sourceParentId);
		parameterMap.setSortOrderFrom(sourceSortOrder + 1);
		expertNetworkCategoryDao.updateSortOrderMinus(parameterMap);

		parameterMap.setCategoryParentId(targetParentId);
		parameterMap.setSortOrderFrom(targetSortOrder);
		expertNetworkCategoryDao.updateSortOrderPlus(parameterMap);

		parameterMap.setCategoryId(sourceId);
		parameterMap.setCategoryParentId(targetParentId);
		parameterMap.setSortOrder(targetSortOrder);
		expertNetworkCategoryDao.updateSortOrderCategoryParentId(parameterMap);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkCategoryService#moveInnerCategory(java.lang.String, java.lang.String, int, int)
	 */
	public void moveInnerCategory(String sourceId, String sourceParentId, int sourceSortOrder, int targetSortOrder) {
		CategorySortOrderMap parameterMap = new CategorySortOrderMap();

		if (sourceSortOrder > targetSortOrder) {
			// 노드가 위로 이동된경우
			parameterMap.setCategoryParentId(sourceParentId);
			parameterMap.setSortOrderFrom(targetSortOrder);
			parameterMap.setSortOrderTo(sourceSortOrder);
			expertNetworkCategoryDao.updateSortOrderPlus(parameterMap);
			
			parameterMap.setCategoryId(sourceId);
			parameterMap.setSortOrder(targetSortOrder);
			expertNetworkCategoryDao.updateSortOrder(parameterMap);
		} else {
			// 노드가 아래로 이동된 경우
			if (1 == (targetSortOrder - sourceSortOrder)) { // 변화 없다
				return;
			}
			
			parameterMap.setCategoryParentId(sourceParentId);
			parameterMap.setSortOrderFrom(sourceSortOrder);
			parameterMap.setSortOrderTo(targetSortOrder - 1);
			expertNetworkCategoryDao.updateSortOrderMinus(parameterMap);

			parameterMap.setCategoryId(sourceId);
			parameterMap.setSortOrder(targetSortOrder - 1);
			expertNetworkCategoryDao.updateSortOrder(parameterMap);
		}
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkCategoryService#countByCategoryName(com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkCategory)
	 */
	public int countByCategoryName(ExpertNetworkCategory expertNetworkCategory) {
		return expertNetworkCategoryDao.countByCategoryName(expertNetworkCategory);
	}

	public ExpertNetworkCategory read(ExpertNetworkCategoryPK pk) {
		return expertNetworkCategoryDao.get(pk);
	}

}
