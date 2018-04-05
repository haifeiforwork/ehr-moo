/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.knowledgehub.dao.KnowledgeMapCategoryDao;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapCategory;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapCategoryPK;
import com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapCategoryService;
import com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapTaggingService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.base.model.CategorySortOrderMap;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * Knowledge Map KnowledgeCategoryService implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapCategoryServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service
@Transactional
public class KnowledgeMapCategoryServiceImpl extends GenericServiceImpl<KnowledgeMapCategory, KnowledgeMapCategoryPK> implements KnowledgeMapCategoryService {

	private KnowledgeMapCategoryDao knowledgeMapCategoryDao;

	@Autowired
	public KnowledgeMapCategoryServiceImpl(KnowledgeMapCategoryDao dao) {
		super(dao);
		this.knowledgeMapCategoryDao = dao;
	}

	@Autowired
	private IdgenService idgenService;

	@Autowired
	private KnowledgeMapTaggingService knowledgeMapTaggingService;
	
	public KnowledgeMapCategory read(KnowledgeMapCategoryPK pk) {
		
		return knowledgeMapCategoryDao.get(pk);
	}

	/**
	 * Root Category 생성
	 * @param category
	 * @return
	 */
	private KnowledgeMapCategory createRootCategory(KnowledgeMapCategory category) {
		String categoryId = idgenService.getNextId();
		category.setCategoryId(categoryId);
		category.setCategoryParentId(categoryId);
		category.setCategoryName("RootCategory");
		category.setSortOrder(1);

		knowledgeMapCategoryDao.create(category);

		return category;
	}

	/* 
	 * Category 삭제시 해당 Tag를 먼저 삭제해한다.
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#delete(java.io.Serializable)
	 */
	@Override
	public void delete(KnowledgeMapCategoryPK id) {
		knowledgeMapTaggingService.deleteByCategoryId(id.getCategoryId());
		super.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapCategoryService#readCreateRootCategory(com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapCategory)
	 */
	public KnowledgeMapCategory readCreateRootCategory(KnowledgeMapCategory category) {
		KnowledgeMapCategory rootCategory = knowledgeMapCategoryDao.getRootCategory(category.getPortalId());

		if (null == rootCategory) {
			rootCategory = createRootCategory(category);
		}

		return rootCategory;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapCategoryService#listByCategoryParentId(java.lang.String)
	 */
	public List<KnowledgeMapCategory> listByCategoryParentId(String CategoryParentId) {
		return knowledgeMapCategoryDao.listByCategoryParentId(CategoryParentId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapCategoryService#listAndChildCountByCategoryParentId(java.lang.String)
	 */
	public List<KnowledgeMapCategory> listAndChildCountByCategoryParentId(String CategoryParentId) {
		return knowledgeMapCategoryDao.listAndChildCountByCategoryParentId(CategoryParentId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapCategoryService#listAndChildCountAndTagsByCategoryParentId(java.lang.String)
	 */
	public List<KnowledgeMapCategory> listAndChildCountAndTagsByCategoryParentId(String CategoryParentId) {
		List<KnowledgeMapCategory> categoryList = knowledgeMapCategoryDao.listAndChildCountByCategoryParentId(CategoryParentId);

		// Tag정보
		for (KnowledgeMapCategory item : categoryList) {
			item.setTags(knowledgeMapTaggingService.getTagsByCategoryId(item.getCategoryId()));
		}

		return categoryList;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapCategoryService#createWithTags(com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapCategory)
	 */
	public KnowledgeMapCategory createWithTags(KnowledgeMapCategory category) {
		String categoryId = idgenService.getNextId();
		category.setCategoryId(categoryId);

		// sortOrder 생성
		int sortOrder = knowledgeMapCategoryDao.getLastSortOrder(category.getCategoryParentId());
		category.setSortOrder(++sortOrder);

		// Category 등록
		this.create(category);

		// Tag정보들 등록
		knowledgeMapTaggingService.createTags(categoryId, category.getTags());

		return category;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapCategoryService#updateWithTags(com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapCategory)
	 */
	public void updateWithTags(KnowledgeMapCategory category) {
		// Category 수정
		update(category);

		// Tag정보들 수정
		knowledgeMapTaggingService.deleteByCategoryId(category.getCategoryId());
		knowledgeMapTaggingService.createTags(category.getCategoryId(), category.getTags());
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapCategoryService#deleteWithTagsHierarchy(java.lang.String)
	 */
	public void deleteWithTagsHierarchy(String categoryId) {
		knowledgeMapTaggingService.deleteByCategoryIdHierarchy(categoryId);
		knowledgeMapCategoryDao.removeByCategoryIdHierarchy(categoryId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapCategoryService#moveOuterCategory(java.lang.String, java.lang.String, int, java.lang.String, int)
	 */
	public void moveOuterCategory(String sourceId, String sourceParentId, int sourceSortOrder, String targetParentId, int targetSortOrder) {
		CategorySortOrderMap parameterMap = new CategorySortOrderMap();

		parameterMap.setCategoryParentId(sourceParentId);
		parameterMap.setSortOrderFrom(sourceSortOrder + 1);
		knowledgeMapCategoryDao.updateSortOrderMinus(parameterMap);

		parameterMap.setCategoryParentId(targetParentId);
		parameterMap.setSortOrderFrom(targetSortOrder);
		knowledgeMapCategoryDao.updateSortOrderPlus(parameterMap);

		parameterMap.setCategoryId(sourceId);
		parameterMap.setCategoryParentId(targetParentId);
		parameterMap.setSortOrder(targetSortOrder);
		knowledgeMapCategoryDao.updateSortOrderCategoryParentId(parameterMap);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapCategoryService#moveInnerCategory(java.lang.String, java.lang.String, int, int)
	 */
	public void moveInnerCategory(String sourceId, String sourceParentId, int sourceSortOrder, int targetSortOrder) {
		CategorySortOrderMap parameterMap = new CategorySortOrderMap();

		if (sourceSortOrder > targetSortOrder) {
			// 노드가 위로 이동된경우
			parameterMap.setCategoryParentId(sourceParentId);
			parameterMap.setSortOrderFrom(targetSortOrder);
			parameterMap.setSortOrderTo(sourceSortOrder);
			knowledgeMapCategoryDao.updateSortOrderPlus(parameterMap);
			
			parameterMap.setCategoryId(sourceId);
			parameterMap.setSortOrder(targetSortOrder);
			knowledgeMapCategoryDao.updateSortOrder(parameterMap);
		} else {
			// 노드가 아래로 이동된 경우
			if (1 == (targetSortOrder - sourceSortOrder)) { // 변화 없다
				return;
			}
			
			parameterMap.setCategoryParentId(sourceParentId);
			parameterMap.setSortOrderFrom(sourceSortOrder);
			parameterMap.setSortOrderTo(targetSortOrder - 1);
			knowledgeMapCategoryDao.updateSortOrderMinus(parameterMap);

			parameterMap.setCategoryId(sourceId);
			parameterMap.setSortOrder(targetSortOrder - 1);
			knowledgeMapCategoryDao.updateSortOrder(parameterMap);
		}
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapCategoryService#countByCategoryName(com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapCategory)
	 */
	public int countByCategoryName(KnowledgeMapCategory knowledgeMapCategory) {
		return knowledgeMapCategoryDao.countByCategoryName(knowledgeMapCategory);
	}

}
