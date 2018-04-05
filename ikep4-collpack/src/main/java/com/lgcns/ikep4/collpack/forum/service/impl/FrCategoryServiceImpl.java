/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.collpack.forum.dao.FrCategoryDao;
import com.lgcns.ikep4.collpack.forum.dao.FrDiscussionDao;
import com.lgcns.ikep4.collpack.forum.model.FrCategory;
import com.lgcns.ikep4.collpack.forum.service.FrCategoryService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: FrCategoryServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service("frCategoryService")
public class FrCategoryServiceImpl extends GenericServiceImpl<FrCategory, String> implements FrCategoryService {


	@Autowired
	private FrCategoryDao frCategoryDao;
	
	@Autowired
	private FrDiscussionDao frDiscussionDao;
	
	@Autowired
	private IdgenService idgenService;
	

	public String create(FrCategory frCategory) {
		
		String id = idgenService.getNextId();
		
		frCategory.setCategoryId(id);
			
		frCategoryDao.create(frCategory);
		
		return id;
	}
	
	public FrCategory get(String categoryId) {
		
		return frCategoryDao.get(categoryId);
	}
	

	public List<FrCategory> list(String portalId) {
		return frCategoryDao.list(portalId);
	}

	
	public void update(FrCategory frCategory) {
		frCategoryDao.update(frCategory);
	}
	
	
	public void updateCategoryOrder(String categoryIdes) {
		
		String[] categoryIdList = categoryIdes.split(",");
		
		for(int i=0, count = categoryIdList.length; i < count; i++){
			String categoryId = categoryIdList[i];
			
			frCategoryDao.updateOrder(categoryId, i);
		}
		
	}


	public void delete(String categoryId, String portalId) {
		
		frDiscussionDao.remove(categoryId);
		
		frCategoryDao.remove(categoryId);
		
	}
	

}
