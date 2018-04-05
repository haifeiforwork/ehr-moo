/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.addressbook.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.support.addressbook.dao.CategoryDao;
import com.lgcns.ikep4.support.addressbook.model.Category;
import com.lgcns.ikep4.support.addressbook.service.CategoryService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * Service 구현체 클래스
 */
@Service
public class CategoryServiceImpl implements CategoryService {
	/** The board item dao. */
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private IdgenService idgenService;

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.Service#listRecent(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	public List<Category> listCategory(Category categoryBoardId) {
		return this.categoryDao.listCategory(categoryBoardId);
	}
	
	public void insertCategoryNm(List<Category> receiveCategoryNmList) {
		for (int i = 0; i < receiveCategoryNmList.size(); i++) {
			Category category = receiveCategoryNmList.get(i);
			String addNameList = category.getAddNameList();
			String delIdList   = category.getDelIdList();
			String updateIdList   = category.getUpdateIdList();
			String updateNameList = category.getUpdateNameList();
			String boardId = category.getBoardId();
			String alignList   = category.getAlignList();
			
			String[] arrayReceive;
			arrayReceive  = addNameList.split(",");
			
			String[] arrayModifyId;
			arrayModifyId = updateIdList.split(",");
			
			String[] arrayModifyNm;
			arrayModifyNm = updateNameList.split(",");
			
			String[] arrayDelId;
			arrayDelId    = delIdList.split(",");

			
			
			String[] arrayAlignName;
			arrayAlignName    = alignList.split(",");
			
			
			if(!"".equals(addNameList) ){
				for (int j = 0; j < arrayReceive.length; j++) {		
					category.setCategoryId(idgenService.getNextId());
					category.setAddNameList(arrayReceive[j]);
					category.setBoardId(boardId);
					this.categoryDao.createCategoryNm(category);
				}	
			}
			
			if(!"".equals(updateIdList) ){
				for (int j = 0; j < arrayModifyId.length; j++) {		
					category.setUpdateIdList(arrayModifyId[j]);
					category.setUpdateNameList(arrayModifyNm[j]);
					category.setBoardId(boardId);
					this.categoryDao.updateCategoryNm(category);
				}	
			}
			
			if(!"".equals(delIdList) ){
				for (int j = 0; j < arrayDelId.length; j++) {		
					category.setDelIdList(arrayDelId[j]);
					category.setBoardId(boardId);
					this.categoryDao.deleteCategoryNm(category);
				}	
			}
			
			if(!"".equals(arrayAlignName) ){
				for (int j = 0; j < arrayAlignName.length; j++) {	
					category.setCategorySeqId(""+j);
					category.setAlignList(arrayAlignName[j]);
					category.setBoardId(boardId);
					this.categoryDao.updateCategoryAlign(category);
				}	
			}
			
		}
	}
}
